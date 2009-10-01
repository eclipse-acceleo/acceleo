/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.cst;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.internal.parser.IAcceleoParserProblemsConstants;
import org.eclipse.acceleo.internal.parser.cst.utils.ParserUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.InitSection;
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * The main class of the CST creator. Creates a CST model from a Acceleo file. You just have to launch the
 * 'parse' method...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CSTParser {
	/** Key of the error message that should be logged when the parser misses a character. */
	private static final String MISSING_CHARACTER_KEY = "Parser.MissingCharacter"; //$NON-NLS-1$

	/** Key of the error message that should be logged when a statement header isn't closed. */
	private static final String INVALID_STMT_HEADER = "CSTParser.InvalidStatementHeader"; //$NON-NLS-1$

	/** Key of the error message that should be logged when a statement isn't closed. */
	private static final String INVALID_STMT = "CSTParser.InvalidStatement"; //$NON-NLS-1$

	/**
	 * The source buffer to parse.
	 */
	protected final AcceleoSourceBuffer source;

	/**
	 * To parse a 'literal' block in the text.
	 */
	protected final SequenceBlock pLiteral;

	/**
	 * To parse a 'comment' block in the text.
	 */
	protected final SequenceBlock pComment;

	/**
	 * To parse a 'parenthesis' block in the text.
	 */
	protected final SequenceBlock pParenthesis;

	/**
	 * To parse a 'brackets' block in the text.
	 */
	protected final SequenceBlock pBrackets;

	/**
	 * To parse a 'comma' sequence in the text.
	 */
	protected final Sequence pComma;

	/**
	 * To parse a 'semicolon' sequence in the text.
	 */
	protected final Sequence pSemicolon;

	/**
	 * To parse a 'pipe' sequence in the text.
	 */
	protected final Sequence pPipe;

	/**
	 * To parse a 'guard' sequence in the text.
	 */
	protected final Sequence pGuard;

	/**
	 * To parse a 'module' block in the text.
	 */
	protected final SequenceBlock pModule;

	/**
	 * To parse an 'import' block in the text.
	 */
	protected final SequenceBlock pImport;

	/**
	 * To parse a 'template' block in the text.
	 */
	protected final SequenceBlock pTemplate;

	/**
	 * To parse a 'macro' block in the text.
	 */
	protected final SequenceBlock pMacro;

	/**
	 * To parse a 'query' block in the text.
	 */
	protected final SequenceBlock pQuery;

	/**
	 * To parse a block statement in the text : 'for', 'if'...
	 */
	protected final CSTParserBlock pBlock;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            is the source buffer to parse
	 */
	public CSTParser(AcceleoSourceBuffer source) {
		this.source = source;
		Sequence literalEscape = new Sequence(IAcceleoConstants.LITERAL_ESCAPE);
		pLiteral = new SequenceBlock(new Sequence(IAcceleoConstants.LITERAL_BEGIN), new Sequence(
				IAcceleoConstants.LITERAL_END), literalEscape, false, null);
		pComment = ParserUtils
				.createAcceleoSequenceBlock(false, IAcceleoConstants.COMMENT, false, null, null);
		pParenthesis = new SequenceBlock(new Sequence(IAcceleoConstants.PARENTHESIS_BEGIN), new Sequence(
				IAcceleoConstants.PARENTHESIS_END), null, true, new SequenceBlock[] {pLiteral });
		pBrackets = new SequenceBlock(new Sequence(IAcceleoConstants.BRACKETS_BEGIN), new Sequence(
				IAcceleoConstants.BRACKETS_END), null, true, new SequenceBlock[] {pLiteral });
		pComma = new Sequence(IAcceleoConstants.COMMA_SEPARATOR);
		pSemicolon = new Sequence(IAcceleoConstants.SEMICOLON_SEPARATOR);
		pPipe = new Sequence(IAcceleoConstants.PIPE_SEPARATOR);
		pGuard = new Sequence(IAcceleoConstants.GUARD);
		pModule = ParserUtils.createAcceleoSequenceBlock(true, IAcceleoConstants.MODULE, false,
				new SequenceBlock[] {pLiteral }, null);
		pImport = ParserUtils.createAcceleoSequenceBlock(true, IAcceleoConstants.IMPORT, false,
				new SequenceBlock[] {pLiteral }, null);
		pTemplate = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.TEMPLATE, false,
				new SequenceBlock[] {pLiteral, pParenthesis }, new SequenceBlock[] {pComment });
		pMacro = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.MACRO, true,
				new SequenceBlock[] {pLiteral, pParenthesis, }, new SequenceBlock[] {pComment });
		pQuery = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.QUERY, false,
				new SequenceBlock[] {pLiteral, pParenthesis, }, new SequenceBlock[] {pComment });
		pBlock = new CSTParserBlock(this);
	}

	/**
	 * Gets the source buffer to parse.
	 * 
	 * @return the source buffer
	 */
	public AcceleoSourceBuffer getSource() {
		return source;
	}

	/**
	 * Gets the block parser, for the statement : 'for', 'if'...
	 * 
	 * @return the block parser
	 */
	public CSTParserBlock getPBlock() {
		return pBlock;
	}

	/**
	 * To store the position in the CST node.
	 * 
	 * @param eCSTNode
	 *            is the node to update
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 */
	protected void setPositions(CSTNode eCSTNode, int posBegin, int posEnd) {
		if (eCSTNode instanceof TextExpression || eCSTNode instanceof Block) {
			eCSTNode.setStartPosition(posBegin);
			eCSTNode.setEndPosition(posEnd);
		} else {
			int[] pos = source.trim(posBegin, posEnd);
			if (pos[0] == pos[1] && posBegin != posEnd) {
				eCSTNode.setStartPosition(posBegin);
				eCSTNode.setEndPosition(posEnd);
			} else {
				eCSTNode.setStartPosition(pos[0]);
				eCSTNode.setEndPosition(pos[1]);
			}
		}
	}

	/**
	 * Creates a CST model for the file.
	 * 
	 * @return the root object of the CST model
	 */
	public Module parse() {
		if (source.getBuffer() == null || source.getBuffer().length() == 0) {
			log(AcceleoParserMessages.getString("CSTParser.EmptyBuffer"), 0, -1); //$NON-NLS-1$
		} else {
			Region bH = pModule.searchBeginHeader(source.getBuffer(), 0, source.getBuffer().length());
			if (bH.b() == -1) {
				log(AcceleoParserMessages.getString("CSTParser.MissingModule"), 0, -1); //$NON-NLS-1$
			} else {
				Region eH = pModule.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, source.getBuffer()
						.length());
				if (eH.b() == -1) {
					log(AcceleoParserMessages.getString("CSTParser.MissingModuleEnd"), bH.b(), bH.e()); //$NON-NLS-1$
				} else {
					Module eModule = CstFactory.eINSTANCE.createModule();
					setPositions(eModule, bH.b(), source.getBuffer().length());
					parseModuleHeader(bH.e(), eH.b(), eModule);
					parseModuleBody(eH.e(), source.getBuffer().length(), eModule);
					return eModule;
				}
			}
		}
		return null;
	}

	/**
	 * Reads the information of the 'module' header in the text, and modifies the properties of the current
	 * CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseModuleHeader(int posBegin, int posEnd, Module eModule) {
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
		if (bH.b() == -1) {
			log(AcceleoParserMessages.getString("CSTParser.MissingMetamodel"), posBegin, posEnd); //$NON-NLS-1$
			String name = source.getBuffer().substring(posBegin, posEnd).trim();
			eModule.setName(name);
			if (source.getFile() != null && !checkModuleDefinition(name, source.getFile())) {
				log(AcceleoParserMessages.getString("CSTParser.InvalidModuleDefinition", name), posBegin, //$NON-NLS-1$
						posEnd);
			}
		} else {
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				parseModuleHeaderTypedModels(bH.e(), eH.b(), eModule);
				int eExtend = parseModuleHeaderExtends(eH.e(), posEnd, eModule);
				if (source.getBuffer().substring(eExtend, posEnd).trim().length() > 0) {
					log(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, eExtend, posEnd);
				}
			}
			String name = source.getBuffer().substring(posBegin, bH.b()).trim();
			eModule.setName(name);
			if (source.getFile() != null && !checkModuleDefinition(name, source.getFile())) {
				log(
						AcceleoParserMessages.getString("CSTParser.InvalidModuleDefinition", name), posBegin, bH.b()); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Validates the name of the module. It must be defined in its own file.
	 * 
	 * @param name
	 *            is the module name
	 * @param file
	 *            is the file that contains the module name
	 * @return true if the module name is valid
	 */
	private boolean checkModuleDefinition(String name, File file) {
		StringTokenizer st = new StringTokenizer(name + "." + IAcceleoConstants.MTL_FILE_EXTENSION, "::"); //$NON-NLS-1$ //$NON-NLS-2$
		String[] segments = new String[st.countTokens()];
		for (int i = 0; i < segments.length; i++) {
			segments[i] = st.nextToken();
		}
		boolean result = true;
		File current = file;
		for (int i = segments.length - 1; result && i >= 0; i--) {
			if (current == null || current.getName() == null || !current.getName().equals(segments[i])) {
				result = false;
			} else {
				current = current.getParentFile();
			}
		}
		return result;
	}

	/**
	 * Creates new typed models in the CST model by reading the following text between the given delimiters. A
	 * comma separator is considered between each typed model.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the module of the CST model
	 */
	private void parseModuleHeaderTypedModels(int posBegin, int posEnd, Module eModule) {
		int currentPos = posBegin;
		while (currentPos != posEnd) {
			Region comma = pComma.search(source.getBuffer(), currentPos, posEnd, null,
					new SequenceBlock[] {pLiteral });
			TypedModel typedModel = CstFactory.eINSTANCE.createTypedModel();
			int e;
			if (comma.b() == -1) {
				e = posEnd;
			} else {
				e = comma.b();
			}
			setPositions(typedModel, currentPos, e);
			String ePackageKey = source.getBuffer().substring(currentPos, e).trim();
			EPackage ePackage = ModelUtils.getEPackage(ePackageKey);
			if (ePackage == null && ePackageKey.startsWith(IAcceleoConstants.LITERAL_BEGIN)
					&& ePackageKey.endsWith(IAcceleoConstants.LITERAL_END)) {
				ePackageKey = ePackageKey.substring(IAcceleoConstants.LITERAL_BEGIN.length(), ePackageKey
						.length()
						- IAcceleoConstants.LITERAL_END.length());
				ePackage = ModelUtils.getEPackage(ePackageKey);
				if (ePackage == null) {
					try {
						ePackageKey = registerEcore(ePackageKey);
					} catch (WrappedException ex) {
						ePackage = null;
					}
					ePackage = ModelUtils.getEPackage(ePackageKey);
					if (ePackage == null) {
						log(AcceleoParserMessages.getString("CSTParser.MetamodelNotFound"), currentPos, e); //$NON-NLS-1$
					}
				}
			}
			if (comma.b() == -1) {
				currentPos = posEnd;
			} else {
				currentPos = comma.e();
			}
			if (ePackage != null) {
				typedModel.getTakesTypesFrom().add(ePackage);
				typedModel.getTakesTypesFrom().addAll(getAllSubpackages(ePackage));
			}
			eModule.getInput().add(typedModel);
		}
	}

	/**
	 * Register the given ecore file. It loads the ecore file and browses the elements, it means the root
	 * EPackage and its descendants.
	 * 
	 * @param pathName
	 *            is the path of the ecore file
	 * @return the NsURI of the ecore root package, or the given path name if it isn't possible to find the
	 *         corresponding NsURI
	 */
	private String registerEcore(String pathName) {
		EObject eObject;
		if (pathName != null && pathName.endsWith(".ecore")) { //$NON-NLS-1$
			ResourceSet resourceSet = new ResourceSetImpl();
			URI metaURI = URI.createURI(pathName, false);
			try {
				eObject = ModelUtils.load(metaURI, resourceSet);
			} catch (IOException e) {
				eObject = null;
			} catch (WrappedException e) {
				eObject = null;
			}
			if (!(eObject instanceof EPackage)) {
				resourceSet = new ResourceSetImpl();
				metaURI = URI.createPlatformResourceURI(pathName, false);
				try {
					eObject = ModelUtils.load(metaURI, resourceSet);
				} catch (IOException e) {
					eObject = null;
				} catch (WrappedException e) {
					eObject = null;
				}
				if (!(eObject instanceof EPackage)) {
					resourceSet = new ResourceSetImpl();
					metaURI = URI.createPlatformPluginURI(pathName, false);
					try {
						eObject = ModelUtils.load(metaURI, resourceSet);
					} catch (IOException e) {
						eObject = null;
					} catch (WrappedException e) {
						eObject = null;
					}
				}
			}
		} else {
			eObject = null;
		}
		if (eObject instanceof EPackage) {
			EPackage ePackage = (EPackage)eObject;
			registerEcorePackageHierarchy(ePackage);
			return ePackage.getNsURI();
		} else {
			return pathName;
		}

	}

	/**
	 * Register the given EPackage and its descendants.
	 * 
	 * @param ePackage
	 *            is the root package to register
	 */
	private void registerEcorePackageHierarchy(EPackage ePackage) {
		for (EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				ePackage.getEFactoryInstance().create((EClass)eClassifier);
				break;
			}
		}
		if (ePackage.getESuperPackage() == null && ePackage.eResource() != null) {
			ePackage.eResource().setURI(URI.createURI(ePackage.getNsURI()));
		}
		EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
		for (EPackage subPackage : ePackage.getESubpackages()) {
			registerEcorePackageHierarchy(subPackage);
		}
	}

	/**
	 * Reads eventually the extends clause in the module header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseModuleHeaderExtends(int posBegin, int posEnd, Module eModule) {
		int bExtend = ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.EXTENDS, true);
		if (bExtend != posBegin) {
			while (bExtend != posEnd) {
				Region comma = pComma.search(source.getBuffer(), bExtend, posEnd, null,
						new SequenceBlock[] {pLiteral });
				if (comma.b() == -1) {
					ModuleExtendsValue eModuleExtendsValue = CstFactory.eINSTANCE.createModuleExtendsValue();
					setPositions(eModuleExtendsValue, bExtend, posEnd);
					eModuleExtendsValue.setName(source.getBuffer().substring(bExtend, posEnd).trim());
					eModule.getExtends().add(eModuleExtendsValue);
					bExtend = posEnd;
				} else {
					ModuleExtendsValue eModuleExtendsValue = CstFactory.eINSTANCE.createModuleExtendsValue();
					setPositions(eModuleExtendsValue, bExtend, comma.b());
					eModuleExtendsValue.setName(source.getBuffer().substring(bExtend, comma.b()).trim());
					eModule.getExtends().add(eModuleExtendsValue);
					bExtend = comma.e();
				}
			}
		}
		return bExtend;
	}

	/**
	 * Gets recursively the sub packages of a package.
	 * 
	 * @param ePackage
	 *            is the root package to visit
	 * @return all the sub packages
	 */
	private Collection<EPackage> getAllSubpackages(EPackage ePackage) {
		Collection<EPackage> result = new ArrayList<EPackage>();
		for (Iterator<EPackage> iterator = ePackage.getESubpackages().iterator(); iterator.hasNext();) {
			EPackage eSubpackage = iterator.next();
			result.add(eSubpackage);
			result.addAll(getAllSubpackages(eSubpackage));
		}
		return result;
	}

	/**
	 * Reads the information of the 'module' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseModuleBody(int posBegin, int posEnd, Module eModule) {
		int currentPosBegin = posBegin;
		SequenceBlock[] pModuleElements = new SequenceBlock[] {pComment, pImport, pTemplate, pMacro, pQuery };
		Region[] positions = Region.createPositions(pModuleElements.length);
		while (currentPosBegin > -1 && currentPosBegin < posEnd) {
			int i = ParserUtils.getNextSequence(source.getBuffer(), currentPosBegin, posEnd, pModuleElements,
					positions);
			if (i == -1) {
				if (source.getBuffer().substring(currentPosBegin, posEnd).trim().length() > 0) {
					log(AcceleoParserMessages.getString("CSTParser.InvalidModel"), currentPosBegin, posEnd); //$NON-NLS-1$
				}
				currentPosBegin = -1;
			} else {
				SequenceBlock pModuleElement = pModuleElements[i];
				Region bH = positions[i];
				if (source.getBuffer().substring(currentPosBegin, bH.b()).trim().length() > 0) {
					log(
							AcceleoParserMessages.getString("CSTParser.InvalidModuleElement"), currentPosBegin, bH.b()); //$NON-NLS-1$
				}
				if (pModuleElement == pComment) {
					currentPosBegin = parseCommentEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pImport) {
					currentPosBegin = parseImportEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pTemplate) {
					currentPosBegin = parseTemplateEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pMacro) {
					currentPosBegin = parseMacroEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pQuery) {
					currentPosBegin = parseQueryEnding(bH, posEnd, eModule);
				} else {
					currentPosBegin = -1; // never
				}
			}
		}
	}

	/**
	 * Reads the following text as a new 'comment' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseCommentEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pComment.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			log(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.COMMENT), beginHeader
					.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pComment.getEndHeaderBody()) {
				Comment eComment = CstFactory.eINSTANCE.createComment();
				setPositions(eComment, beginHeader.b(), eH.e());
				eComment.setBody(source.getBuffer().substring(beginHeader.e(), eH.b()));
				eModule.getOwnedModuleElement().add(eComment);
				posBegin = eH.e();
			} else {
				Region eB = pComment.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					log(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.COMMENT), beginHeader
							.b(), beginHeader.e());
					posBegin = -1;
				} else {
					Comment eComment = CstFactory.eINSTANCE.createComment();
					setPositions(eComment, beginHeader.b(), eB.e());
					eComment.setBody(source.getBuffer().substring(eH.e(), eB.b()));
					eModule.getOwnedModuleElement().add(eComment);
					posBegin = eB.e();
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'import' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseImportEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pImport.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			log(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.IMPORT), beginHeader
					.b(), beginHeader.e());
			posBegin = -1;
		} else {
			ModuleImportsValue eModuleImportsValue = CstFactory.eINSTANCE.createModuleImportsValue();
			setPositions(eModuleImportsValue, beginHeader.e(), eH.b());
			eModuleImportsValue.setName(source.getBuffer().substring(beginHeader.e(), eH.b()).trim());
			eModule.getImports().add(eModuleImportsValue);
			posBegin = eH.e();
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'template' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseTemplateEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pTemplate.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			log(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.TEMPLATE), beginHeader
					.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pTemplate.getEndHeaderBody()) {
				posBegin = eH.e();
				Template eTemplate = CstFactory.eINSTANCE.createTemplate();
				setPositions(eTemplate, beginHeader.b(), eH.e());
				eModule.getOwnedModuleElement().add(eTemplate);
				parseTemplateHeader(beginHeader.e(), eH.b(), eTemplate);
			} else {
				Region eB = pTemplate.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					log(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.TEMPLATE),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					Template eTemplate = CstFactory.eINSTANCE.createTemplate();
					setPositions(eTemplate, beginHeader.b(), eB.e());
					eModule.getOwnedModuleElement().add(eTemplate);
					parseTemplateHeader(beginHeader.e(), eH.b(), eTemplate);
					parseTemplateBody(eH.e(), eB.b(), eTemplate);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'macro' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseMacroEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pMacro.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			log(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.MACRO), beginHeader
					.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pMacro.getEndHeaderBody()) {
				posBegin = eH.e();
				Macro eMacro = CstFactory.eINSTANCE.createMacro();
				setPositions(eMacro, beginHeader.b(), eH.e());
				eModule.getOwnedModuleElement().add(eMacro);
				parseMacroHeader(beginHeader.e(), eH.b(), eMacro);
			} else {
				Region eB = pMacro.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					log(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.MACRO), beginHeader
							.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					Macro eMacro = CstFactory.eINSTANCE.createMacro();
					setPositions(eMacro, beginHeader.b(), eB.e());
					eModule.getOwnedModuleElement().add(eMacro);
					parseMacroHeader(beginHeader.e(), eH.b(), eMacro);
					parseMacroBody(eH.e(), eB.b(), eMacro);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'query' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseQueryEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pQuery.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			log(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.QUERY), beginHeader
					.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pQuery.getEndHeaderBody()) {
				posBegin = eH.e();
				Query eQuery = CstFactory.eINSTANCE.createQuery();
				setPositions(eQuery, beginHeader.b(), eH.e());
				eModule.getOwnedModuleElement().add(eQuery);
				parseQueryHeader(beginHeader.e(), eH.b(), eQuery);
			} else {
				Region eB = pQuery.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					log(
							AcceleoParserMessages.getString("CSTParser.InvalidQuery"), beginHeader.b(), beginHeader.e()); //$NON-NLS-1$
					posBegin = -1;
				} else {
					log(AcceleoParserMessages.getString("CSTParser.InvalidQuery"), beginHeader.b(), eB.e()); //$NON-NLS-1$
					posBegin = eB.e();
					Query eQuery = CstFactory.eINSTANCE.createQuery();
					setPositions(eQuery, beginHeader.b(), eB.e());
					eModule.getOwnedModuleElement().add(eQuery);
					parseQueryHeader(beginHeader.e(), eH.b(), eQuery);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'template' header in the text, and modifies the properties of the current
	 * CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTemplate
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseTemplateHeader(int posBegin, int posEnd, Template eTemplate) {
		int posShift = shiftVisibility(posBegin, posEnd, eTemplate);
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posShift, posEnd);
		if (bH.b() == -1) {
			log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posShift, posEnd);
			String name = source.getBuffer().substring(posShift, posEnd).trim();
			if (!ParserUtils.isIdentifier(name)) {
				log(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, posEnd);
			}
			eTemplate.setName(name);
		} else {
			String name = source.getBuffer().substring(posShift, bH.b()).trim();
			if (!ParserUtils.isIdentifier(name)) {
				log(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, bH.b());
			}
			eTemplate.setName(name);
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				Module eModule = getModule(eTemplate);
				Variable[] eVariables = createVariablesCommaSeparator(bH.e(), eH.b(), eModule);
				for (int i = 0; eVariables != null && i < eVariables.length; i++) {
					Variable eVariable = eVariables[i];
					if (eVariable != null) {
						eTemplate.getParameter().add(eVariable);
					}
				}
				int currentPosBegin = eH.e();
				int currentPosEnd = parseTemplateHeaderIndexOfGuardOrBrackets(currentPosBegin, posEnd);
				currentPosBegin = parseTemplateHeaderOverrides(currentPosBegin, currentPosEnd, eTemplate);
				currentPosEnd = posEnd;
				currentPosBegin = parseTemplateHeaderGuard(currentPosBegin, currentPosEnd, posEnd, eTemplate);
				currentPosBegin = shiftInitSectionBody(currentPosBegin, currentPosEnd, eTemplate);
				if (source.getBuffer().substring(currentPosBegin, posEnd).trim().length() > 0) {
					log(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, currentPosBegin, posEnd);
				}
			}
		}
	}

	/**
	 * Gets the index of the next guard or the index of the next brackets.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the next guard or the index of the next brackets
	 */
	private int parseTemplateHeaderIndexOfGuardOrBrackets(int posBegin, int posEnd) {
		int currentPosEnd = posEnd;
		Region guard = pGuard.search(source.getBuffer(), posBegin, currentPosEnd, null,
				new SequenceBlock[] {pLiteral });
		if (guard.b() > -1) {
			currentPosEnd = guard.b();
		} else {
			Region bracket = pBrackets.search(source.getBuffer(), posBegin, currentPosEnd, null,
					new SequenceBlock[] {pLiteral });
			if (bracket.b() > -1) {
				currentPosEnd = bracket.b();
			}
		}
		return currentPosEnd;
	}

	/**
	 * Reads eventually the overrides clause in the template header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTemplate
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseTemplateHeaderOverrides(int posBegin, int posEnd, Template eTemplate) {
		int currentPosBegin = posBegin;
		int bOverride = ParserUtils.shiftKeyword(source.getBuffer(), currentPosBegin, posEnd,
				IAcceleoConstants.OVERRIDES, true);
		if (bOverride != currentPosBegin) {
			currentPosBegin = bOverride;
			while (currentPosBegin != posEnd) {
				Region comma = pComma.search(source.getBuffer(), currentPosBegin, posEnd, null,
						new SequenceBlock[] {pLiteral });
				if (comma.b() == -1) {
					TemplateOverridesValue eTemplateOverridesValue = CstFactory.eINSTANCE
							.createTemplateOverridesValue();
					setPositions(eTemplateOverridesValue, currentPosBegin, posEnd);
					eTemplateOverridesValue.setName(source.getBuffer().substring(currentPosBegin, posEnd)
							.trim());
					eTemplate.getOverrides().add(eTemplateOverridesValue);
					currentPosBegin = posEnd;
				} else {
					TemplateOverridesValue eTemplateOverridesValue = CstFactory.eINSTANCE
							.createTemplateOverridesValue();
					setPositions(eTemplateOverridesValue, currentPosBegin, comma.b());
					eTemplateOverridesValue.setName(source.getBuffer().substring(currentPosBegin, comma.b())
							.trim());
					eTemplate.getOverrides().add(eTemplateOverridesValue);
					currentPosBegin = comma.e();
				}
			}
		}
		return currentPosBegin;
	}

	/**
	 * Reads eventually the guard clause in the template header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param headerPosEnd
	 *            is the ending index of the header
	 * @param eTemplate
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseTemplateHeaderGuard(int posBegin, int posEnd, int headerPosEnd, Template eTemplate) {
		int currentPos = posBegin;
		int bGuard = ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd,
				IAcceleoConstants.GUARD, false);
		if (bGuard != currentPos) {
			currentPos = bGuard;
			if (ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd,
					IAcceleoConstants.PARENTHESIS_BEGIN, false) == currentPos) {
				log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, currentPos, posEnd);
				currentPos = headerPosEnd;
			} else {
				Region bHParenthesis = pParenthesis.searchBeginHeader(source.getBuffer(), currentPos, posEnd);
				Region eHParenthesis = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(),
						bHParenthesis, posEnd);
				if (eHParenthesis.b() == -1) {
					log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bHParenthesis.b(),
							bHParenthesis.e());
					currentPos = headerPosEnd;
				} else {
					ModelExpression eGuard = CstFactory.eINSTANCE.createModelExpression();
					setPositions(eGuard, bHParenthesis.e(), eHParenthesis.b());
					eTemplate.setGuard(eGuard);
					pBlock.parseExpressionHeader(bHParenthesis.e(), eHParenthesis.b(), eGuard);
					currentPos = eHParenthesis.e();
				}
			}
		}
		return currentPos;
	}

	/**
	 * Reads eventually the following visibility in the text. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModuleElement
	 *            is the current node of the CST
	 * @return the ending index of the keyword, or the beginning index if it doesn't exist
	 */
	private int shiftVisibility(int posBegin, int posEnd, ModuleElement eModuleElement) {
		int posShift = ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.VISIBILITY_KIND_PRIVATE, true);
		int b = posBegin;
		if (b != posShift) {
			eModuleElement.setVisibility(VisibilityKind.PRIVATE);
		} else {
			posShift = ParserUtils.shiftKeyword(source.getBuffer(), posShift, posEnd,
					IAcceleoConstants.VISIBILITY_KIND_PROTECTED, true);
			if (b != posShift) {
				eModuleElement.setVisibility(VisibilityKind.PROTECTED);
			} else {
				posShift = ParserUtils.shiftKeyword(source.getBuffer(), posShift, posEnd,
						IAcceleoConstants.VISIBILITY_KIND_PUBLIC, true);
				if (b != posShift) {
					eModuleElement.setVisibility(VisibilityKind.PUBLIC);
				}
			}
		}
		return posShift;
	}

	/**
	 * Reads eventually the following text as a new 'init section' (header and body) in the CST model.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the init section, or the beginning index if it doesn't exist
	 */
	protected int shiftInitSectionBody(int posBegin, int posEnd, Block eBlock) {
		if (ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd, IAcceleoConstants.BRACKETS_BEGIN,
				false) != posBegin) {
			Region bHBrackets = pBrackets.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
			Region eHBrackets = pBrackets
					.searchEndHeaderAtBeginHeader(source.getBuffer(), bHBrackets, posEnd);
			int posResult;
			if (eHBrackets.b() == -1) {
				log(
						AcceleoParserMessages.getString("CSTParser.MissingClosingBracket"), bHBrackets.b(), bHBrackets.e()); //$NON-NLS-1$
				posResult = posEnd;
			} else {
				InitSection eInitSection = CstFactory.eINSTANCE.createInitSection();
				setPositions(eInitSection, bHBrackets.b(), eHBrackets.e());
				eBlock.setInit(eInitSection);
				boolean semicolonFound = shiftInitSectionBodyCreatesVariables(bHBrackets.e(), eHBrackets.b(),
						eInitSection);
				if (!semicolonFound) {
					log(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.SEMICOLON_SEPARATOR), bHBrackets.e(), eHBrackets.b());
				}
				posResult = eHBrackets.e();
			}
			return posResult;
		} else {
			return posBegin;
		}
	}

	/**
	 * Creates new variables in the CST model by reading the text in the init section. A semicolon separator
	 * is considered between each variable.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eInitSection
	 *            is the current node of the CST model
	 * @return true if one (or more) semicolon is found
	 */
	private boolean shiftInitSectionBodyCreatesVariables(int posBegin, int posEnd, InitSection eInitSection) {
		List<Region> positions = pSemicolon.split(source.getBuffer(), posBegin, posEnd, true, null, null);
		Module eModule = getModule(eInitSection);
		boolean semicolonFound = false;
		for (int i = 0; i < positions.size(); i++) {
			Region variablePos = positions.get(i);
			String text = source.getBuffer().substring(variablePos.b(), variablePos.e()).trim();
			if (text.equals(IAcceleoConstants.SEMICOLON_SEPARATOR)) {
				if (!semicolonFound) {
					semicolonFound = true;
				}
			} else if (text.length() > 0) {
				Variable eVariable = createVariable(variablePos.b(), variablePos.e(), eModule);
				if (eVariable != null) {
					eInitSection.getVariable().add(eVariable);
				}
			}
		}
		return semicolonFound;
	}

	/**
	 * Creates new variables in the CST model by reading the following text between the given delimiters. A
	 * comma separator is considered between each variable.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the module of the CST model, it is used to get the model types...
	 * @return the new variables
	 */
	protected Variable[] createVariablesCommaSeparator(int posBegin, int posEnd, Module eModule) {
		List<Region> positions = pComma.split(source.getBuffer(), posBegin, posEnd, false, null, null);
		Variable[] eVariables = new Variable[positions.size()];
		List<String> variableNames = new ArrayList<String>();
		for (int i = 0; i < eVariables.length; i++) {
			Region variablePos = positions.get(i);
			Variable eVariable = createVariable(variablePos.b(), variablePos.e(), eModule);
			eVariables[i] = eVariable;
			if (eVariable != null) {
				if (!variableNames.contains(eVariable.getName())) {
					variableNames.add(eVariable.getName());
				} else {
					log(
							AcceleoParserMessages.getString(
									"CSTParser.DuplicatedVariable", new Object[] {eVariable.getName() }), variablePos.b(), variablePos.e()); //$NON-NLS-1$
				}
			}
		}
		return eVariables;
	}

	/**
	 * Creates a new variable in the CST model by reading the following text between the given delimiters.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the module of the CST model, it is used to get the model types...
	 * @return the new variable
	 */
	public Variable createVariable(int posBegin, int posEnd, Module eModule) {
		String variableBuffer = source.getBuffer().substring(posBegin, posEnd);
		Variable eVariable;
		int bDot = variableBuffer.indexOf(IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR);
		if (bDot == -1) {
			log(
					AcceleoParserMessages.getString("CSTParser.InvalidVariable", variableBuffer.trim()), posBegin, //$NON-NLS-1$
					posEnd);
			eVariable = null;
		} else {
			String name = variableBuffer.substring(0, bDot);
			String type = variableBuffer.substring(bDot
					+ IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length());
			eVariable = CstFactory.eINSTANCE.createVariable();
			setPositions(eVariable, posBegin, posEnd);
			int bInit = variableBuffer.indexOf(IAcceleoConstants.VARIABLE_INIT_SEPARATOR, bDot
					+ IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length());
			if (bInit != -1) {
				type = variableBuffer.substring(bDot
						+ IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length(), bInit);
				String initExpression = variableBuffer.substring(bInit
						+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length());
				ModelExpression eInitExpression = CstFactory.eINSTANCE.createModelExpression();
				setPositions(eInitExpression, posBegin + bInit
						+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd);
				eVariable.setInitExpression(eInitExpression);
				eInitExpression.setBody(initExpression);
				pBlock.parseExpressionHeader(posBegin + bInit
						+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd, eInitExpression);
			}
			eVariable.setName(name.trim());
			eVariable.setType(type.trim());
		}
		return eVariable;
	}

	/**
	 * Gets the current module for the given object of the CST model.
	 * 
	 * @param eObject
	 *            is an object of the CST model
	 * @return the current module
	 */
	protected Module getModule(EObject eObject) {
		EObject current = eObject;
		while (current != null) {
			if (current instanceof Module) {
				return (Module)current;
			}
			current = current.eContainer();
		}
		return null;
	}

	/**
	 * Reads the information of the 'template' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTemplate
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseTemplateBody(int posBegin, int posEnd, Template eTemplate) {
		pBlock.parse(posBegin, posEnd, eTemplate);
	}

	/**
	 * Reads the information of the 'query' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eQuery
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseQueryHeader(int posBegin, int posEnd, Query eQuery) {
		int posShift = shiftVisibility(posBegin, posEnd, eQuery);
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posShift, posEnd);
		if (bH.b() == -1) {
			log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posShift, posEnd);
			String name = source.getBuffer().substring(posShift, posEnd).trim();
			if (!ParserUtils.isIdentifier(name)) {
				log(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, posEnd);
			}
			eQuery.setName(name);
		} else {
			String name = source.getBuffer().substring(posShift, bH.b()).trim();
			if (!ParserUtils.isIdentifier(name)) {
				log(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, bH.b());
			}
			eQuery.setName(name);
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				Module eModule = getModule(eQuery);
				Variable[] eVariables = createVariablesCommaSeparator(bH.e(), eH.b(), eModule);
				for (int i = 0; eVariables != null && i < eVariables.length; i++) {
					Variable eVariable = eVariables[i];
					if (eVariable != null) {
						eQuery.getParameter().add(eVariable);
					}
				}
				int currentPosBegin = eH.e();
				int eDot = ParserUtils.shiftKeyword(source.getBuffer(), currentPosBegin, posEnd,
						IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, false);
				if (eDot == currentPosBegin) {
					log(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR), currentPosBegin, posEnd);
				}
				String bodyText = source.getBuffer().substring(eDot, posEnd);
				int bInit = bodyText.indexOf(IAcceleoConstants.VARIABLE_INIT_SEPARATOR);
				if (bInit != -1) {
					String type = bodyText.substring(0, bInit).trim();
					eQuery.setType(type);
					String initExpression = bodyText.substring(bInit
							+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length());
					ModelExpression eExpression = CstFactory.eINSTANCE.createModelExpression();
					setPositions(eExpression, eDot + bInit
							+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd);
					eQuery.setExpression(eExpression);
					eExpression.setBody(initExpression);
					pBlock.parseExpressionHeader(eDot + bInit
							+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd, eExpression);
				} else {
					log(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.VARIABLE_INIT_SEPARATOR), currentPosBegin, posEnd);
				}
			}
		}
	}

	/**
	 * Reads the information of the 'macro' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eMacro
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseMacroHeader(int posBegin, int posEnd, Macro eMacro) {
		int posShift = shiftVisibility(posBegin, posEnd, eMacro);
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posShift, posEnd);
		if (bH.b() == -1) {
			log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posShift, posEnd);
			String name = source.getBuffer().substring(posShift, posEnd).trim();
			if (!ParserUtils.isIdentifier(name)) {
				log(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, posEnd);
			}
			eMacro.setName(name);
		} else {
			String name = source.getBuffer().substring(posShift, bH.b()).trim();
			if (!ParserUtils.isIdentifier(name)) {
				log(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, bH.b());
			}
			eMacro.setName(name);
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				log(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				Module eModule = getModule(eMacro);
				Variable[] eVariables = createVariablesCommaSeparator(bH.e(), eH.b(), eModule);
				for (int i = 0; eVariables != null && i < eVariables.length; i++) {
					Variable eVariable = eVariables[i];
					if (eVariable != null) {
						eMacro.getParameter().add(eVariable);
					}
				}
				int currentPosBegin = eH.e();
				int eDot = ParserUtils.shiftKeyword(source.getBuffer(), currentPosBegin, posEnd,
						IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, false);
				if (eDot == currentPosBegin) {
					log(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR), currentPosBegin, posEnd);
				}
				String type = source.getBuffer().substring(eDot, posEnd).trim();
				eMacro.setType(type);
			}
		}
	}

	/**
	 * Reads the information of the 'macro' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eMacro
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseMacroBody(int posBegin, int posEnd, Macro eMacro) {
		pBlock.parse(posBegin, posEnd, eMacro);
	}

	/**
	 * To log a new problem.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	private void log(String message, int posBegin, int posEnd) {
		source.log(message, posBegin, posEnd);
	}

}
