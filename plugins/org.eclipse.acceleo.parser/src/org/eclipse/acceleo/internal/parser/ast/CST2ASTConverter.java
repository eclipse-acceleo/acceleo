/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.ast;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.AcceleoASTNodeAdapter;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.internal.parser.ast.ocl.OCLParser;
import org.eclipse.acceleo.model.mtl.CommentBody;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.Documentation;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateExpression;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * The main class used to transform a CST model to an AST model. This class is not able to run the 'Resolve'
 * step.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CST2ASTConverter {
	/** Line separator of the unix platforms. */
	protected static final String UNIX_LINE_SEPARATOR = "\n"; //$NON-NLS-1$

	/** Line separator of the mac platforms. */
	protected static final String MAC_LINE_SEPARATOR = "\r"; //$NON-NLS-1$

	/** Line separator of the dos platforms. */
	protected static final String DOS_LINE_SEPARATOR = "\r\n"; //$NON-NLS-1$

	/**
	 * The factory used to create the objects of the AST model.
	 */
	protected ASTFactory factory;

	/**
	 * The AST provider will allow logging and line number retrieval.
	 */
	protected IASTProvider astProvider;

	/**
	 * Indicates if the current CST to AST action has been canceled.
	 */
	protected boolean isCanceled;

	/**
	 * Constructor.
	 */
	public CST2ASTConverter() {
		super();
		factory = new ASTFactory();
		astProvider = null;
		isCanceled = false;
	}

	/**
	 * Gets the OCL parser (from the OCL plug-in).
	 * 
	 * @return the OCL parser, can be null
	 */
	public OCLParser getOCL() {
		return factory.getOCL();
	}

	/**
	 * Sets the log Handler to save AST logging messages.
	 * 
	 * @param theASTProvider
	 *            is the new log handler
	 */
	public void setASTProvider(IASTProvider theASTProvider) {
		astProvider = theASTProvider;
		factory.setLogHandler(astProvider);
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
	protected void logProblem(String message, int posBegin, int posEnd) {
		if (astProvider != null) {
			astProvider.logProblem(message, posBegin, posEnd);
		}
	}

	/**
	 * To log a new warning.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	protected void logWarning(String message, int posBegin, int posEnd) {
		if (astProvider != null) {
			astProvider.logWarning(message, posBegin, posEnd);
		}
	}

	/**
	 * To log a new information.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	protected void logInfo(String message, int posBegin, int posEnd) {
		if (astProvider != null) {
			astProvider.logInfo(message, posBegin, posEnd);
		}
	}

	/**
	 * Creates an AST model in the given resource. This method is not able to run the 'Resolve' step.
	 * 
	 * @param rootCST
	 *            is the root element of the input module (CST)
	 * @param resourceAST
	 *            is the resource to be updated with the output model (AST)
	 */
	public void createAST(org.eclipse.acceleo.parser.cst.Module rootCST, Resource resourceAST) {
		if (rootCST != null) {
			org.eclipse.acceleo.model.mtl.Module oModule = factory.getOrCreateModule(rootCST);
			resourceAST.getContents().add(0, oModule);
			factory.initOCL(resourceAST);
			transformStepCopy(rootCST);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Module' of the input
	 * model.
	 * 
	 * @param iModule
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Module'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Module iModule) {
		org.eclipse.acceleo.model.mtl.Module oModule = factory.getOrCreateModule(iModule);
		if (iModule != null && oModule != null) {
			String ioName = iModule.getName();
			if (ioName != null && !"".equals(ioName)) { //$NON-NLS-1$
				oModule.setName(ioName);
			}
			// Now a module in the AST has a start and an end position for its header (we can't know the
			// ending position of the header here, it will be resolved later)
			oModule.setStartHeaderPosition(iModule.getStartPosition());
			oModule.setEndHeaderPosition(-1);
			// We copy the documentation of the module
			transformStepCopyModuleDocumentation(iModule, oModule);
			transformStepCopyOwnedModuleElement(iModule, oModule);
			Iterator<org.eclipse.acceleo.parser.cst.TypedModel> iInputIt = iModule.getInput().iterator();
			while (iInputIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.TypedModel iNext = iInputIt.next();
				org.eclipse.acceleo.model.mtl.TypedModel oNext = factory.getOrCreateTypedModel(iNext);
				if (oNext != null) {
					oModule.getInput().add(oNext);
				}
				transformStepCopy(iNext);

			}

			if (iModule.getExtends() != null && iModule.getExtends().size() > 1) {
				int posBegin = iModule.getExtends().get(0).getStartPosition();
				int posEnd = iModule.getExtends().get(iModule.getExtends().size() - 1).getEndPosition();
				this.logWarning(AcceleoParserMessages.getString("AcceleoParser.Warning.MultipleExtends"), //$NON-NLS-1$
						posBegin, posEnd);
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for the 'org.eclipse.acceleo.parser.cst.Documentation' of the
	 * module element of the input.
	 * 
	 * @param iModule
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Module'
	 * @param oModule
	 *            is the ouput object of type 'org.eclipse.acceleo.model.mtl.Module'
	 */
	private void transformStepCopyModuleDocumentation(org.eclipse.acceleo.parser.cst.Module iModule,
			org.eclipse.acceleo.model.mtl.Module oModule) {
		if (iModule.getDocumentation() != null) {
			Documentation iDocumentation = iModule.getDocumentation();
			org.eclipse.acceleo.model.mtl.Documentation oDocumentation = factory
					.getOrCreateDocumentation(iDocumentation);
			if (oDocumentation != null) {
				org.eclipse.acceleo.model.mtl.CommentBody oCommentBody = MtlFactory.eINSTANCE
						.createCommentBody();
				oDocumentation.setName(iDocumentation.getName());
				oDocumentation.setStartPosition(iDocumentation.getStartPosition());
				oDocumentation.setEndPosition(iDocumentation.getEndPosition());

				oCommentBody.setStartPosition(-1);
				oCommentBody.setEndPosition(-1);
				oCommentBody.setValue(iDocumentation.getBody());

				oDocumentation.setBody(oCommentBody);
				oModule.setDocumentation(oDocumentation);
				oDocumentation.setDocumentedElement(oModule);

				if (oDocumentation.getBody().getValue().contains(IAcceleoConstants.TAG_DEPRECATED)) {
					oModule.setDeprecated(true);
				}
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.TypedModel' of the
	 * input model.
	 * 
	 * @param iTypedModel
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TypedModel'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.TypedModel iTypedModel) {
		org.eclipse.acceleo.model.mtl.TypedModel oTypedModel = factory.getOrCreateTypedModel(iTypedModel);
		if (iTypedModel != null && oTypedModel != null) {
			Iterator<EPackage> iTakesTypesFromIt = iTypedModel.getTakesTypesFrom().iterator();
			while (iTakesTypesFromIt.hasNext()) {
				EPackage ioNext = iTakesTypesFromIt.next();
				oTypedModel.getTakesTypesFrom().add(ioNext);
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Template' of the
	 * input model.
	 * 
	 * @param iTemplate
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Template'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Template iTemplate) {
		org.eclipse.acceleo.model.mtl.Template oTemplate = factory.getOrCreateTemplate(iTemplate);
		if (iTemplate != null && oTemplate != null) {
			org.eclipse.acceleo.parser.cst.VisibilityKind iVisibility = iTemplate.getVisibility();
			org.eclipse.acceleo.model.mtl.VisibilityKind oVisibility = org.eclipse.acceleo.model.mtl.VisibilityKind
					.get(iVisibility.getValue());
			oTemplate.setVisibility(oVisibility);
			transformStepCopyPositions(iTemplate, oTemplate);
			String ioName = iTemplate.getName();
			if (ioName != null && !"".equals(ioName)) { //$NON-NLS-1$
				oTemplate.setName(ioName);
			}
			org.eclipse.acceleo.parser.cst.InitSection iInit = iTemplate.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oTemplate.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iGuard = iTemplate.getGuard();
			transformStepCopy(iGuard);

			org.eclipse.acceleo.parser.cst.ModelExpression iPost = iTemplate.getPost();
			transformStepCopy(iPost);

			transformStepCopyBody(iTemplate, oTemplate);
			Iterator<org.eclipse.acceleo.parser.cst.Variable> iParameterIt = iTemplate.getParameter()
					.iterator();
			while (iParameterIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iParameterIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oTemplate.getParameter().add(oNext);
				}
				transformStepCopy(iNext);

			}
			// If the main annotation is in a template with a protected or private visibility we log an error
			boolean isMain = false;
			Iterator<EObject> iChildren = iTemplate.eAllContents();
			while (!isMain && iChildren.hasNext()) {
				EObject iChild = iChildren.next();
				if (iChild instanceof Comment && ((Comment)iChild).getBody() != null
						&& ((Comment)iChild).getBody().indexOf(IAcceleoConstants.TAG_MAIN) > -1) {
					if (VisibilityKind.PUBLIC_VALUE != oTemplate.getVisibility().getValue()) {
						this.logWarning(AcceleoParserMessages
								.getString("CSTParser.InvalidVisibilityOfMainTemplate"), //$NON-NLS-1$
								((Comment)iChild).getStartPosition(), ((Comment)iChild).getEndPosition());
					}
					isMain = true;
					oTemplate.setMain(isMain);
				}
			}
			// We log a warning if there are multiple overrides.
			if (iTemplate.getOverrides().size() > 1) {
				List<TemplateOverridesValue> overrides = iTemplate.getOverrides();
				this.logWarning(
						AcceleoParserMessages.getString("AcceleoParser.Warning.MultipleOverrides"), overrides.get(0) //$NON-NLS-1$
								.getStartPosition(), overrides.get(overrides.size() - 1).getEndPosition());
			}
			// We log an info if there is an override.
			if (iTemplate.getOverrides().size() > 0) {
				List<TemplateOverridesValue> overrides = iTemplate.getOverrides();
				String message = AcceleoParserMessages.getString("AcceleoParser.Info.TemplateOverride", //$NON-NLS-1$
						iTemplate.getName(), overrides.get(0).getName());
				this.logInfo(AcceleoParserInfo.TEMPLATE_OVERRIDE + message, overrides.get(0)
						.getStartPosition(), overrides.get(overrides.size() - 1).getEndPosition());
			}

			if (oTemplate.isDeprecated()) {
				logWarning(AcceleoParserMessages.getString("CST2ASTConverterWithResolver.DeprecatedTemplate", //$NON-NLS-1$
						oTemplate.getName()), oTemplate.getStartPosition(), oTemplate.getEndPosition());
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Variable' of the
	 * input model.
	 * 
	 * @param iVariable
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Variable'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Variable iVariable) {
		org.eclipse.ocl.ecore.Variable oVariable = factory.getOrCreateVariable(iVariable);
		if (iVariable != null && oVariable != null) {
			transformStepCopyPositions(iVariable, oVariable);
			String ioName = iVariable.getName();
			if (ioName != null && !"".equals(ioName)) { //$NON-NLS-1$
				oVariable.setName(ioName);
			}
			String ioType = iVariable.getType();
			EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(OCLParser.ANNOTATION_SOURCE);
			eAnnotation.getDetails().put(OCLParser.ANNOTATION_KEY_TYPE, ioType);
			oVariable.getEAnnotations().add(eAnnotation);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.ModelExpression' of
	 * the input model.
	 * 
	 * @param iModelExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ModelExpression'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		// wait step OCL
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.TextExpression' of
	 * the input model.
	 * 
	 * @param iTextExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TextExpression'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.TextExpression iTextExpression) {
		org.eclipse.ocl.ecore.StringLiteralExp oTextExpression = factory
				.getOrCreateStringLiteralExp(iTextExpression);
		if (iTextExpression != null && oTextExpression != null) {
			transformStepCopyPositions(iTextExpression, oTextExpression);
			transformFormattedText(iTextExpression, oTextExpression);
			if ("".equals(oTextExpression.getStringSymbol())) { //$NON-NLS-1$
				EcoreUtil.remove(oTextExpression);
			}
		}
	}

	/**
	 * We give this method a list of CST elements and the index of a text expression that is either preceded
	 * by (<code>checkStartIndex</code> is <code>true</code>) or followed by (<code>checkStartIndex</code> is
	 * <code>false</code>) a line of which we need to determine relevance. Specifically, a line which
	 * <em>only contains whitespaces</em> is considered relevant, yet a line containing only whitespaces and
	 * comments, whitespace and block opening or whitespaces and block endings is <em>not</em> considered to
	 * be a relevant line. The text at the given <em>index</em> in the <em>bodyContent</em> list will have its
	 * starting/ending whitespaces trimmed if the line before/after it isn't a relevant one for generation.
	 * 
	 * @param bodyContent
	 *            This list contains the whole set of CST elements of the block containing the line that is to
	 *            be checked.
	 * @param index
	 *            Index of the text expression which we need to check.
	 * @param checkStartPosition
	 *            if <code>true</code>, this will check the line on which the given text expression starts,
	 *            otherwise the text's ending line will be checked instead.
	 * @return <code>true</code> iff the given text expression is to be trimmed.
	 */
	private boolean isRelevantLine(List<CSTNode> bodyContent, int index, boolean checkStartPosition) {
		TextExpression expression = (TextExpression)bodyContent.get(index);
		// Does the expression span multiple lines?
		boolean isSingleLineExpression = isSingleLineExpression(expression);
		boolean result = true;
		// if not, we can shortcut this if the expression contains anything other than whitespaces
		// likewise, if our parent block is single line, this _is_ a relevant line
		if (isSingleLineExpression((CSTNode)expression.eContainer())
				|| (isSingleLineExpression && !isEmpty(expression.getValue()))) {
			return true;
		}

		/*
		 * We need to check if what's _before_ the text expression is relevant in two cases: a) the text is
		 * single line and comprised of whitespace only or b) the text starts with an empty line and
		 * checkStartposition is true.
		 */
		if (index > 0
				&& (isSingleLineExpression || (checkStartPosition && startsInEmptyLine(expression.getValue())))) {
			result = containsRelevantExpressionsOnLine(bodyContent, index - 1, true);
		}

		/*
		 * We need to check if what's _after_ the text expression is relevant in two cases: a) the text is
		 * single line and comprised of whitespace only or b) the text ends with an empty line and
		 * checkStartposition is false.
		 */
		if (index < bodyContent.size() - 1
				&& (isSingleLineExpression || (!checkStartPosition && endsInEmptyLine(expression.getValue())))) {
			result = containsRelevantExpressionsOnLine(bodyContent, index + 1, false);
		}

		return result;
	}

	/**
	 * This will look on the line of the expression at <code>index</code> in bodyContent and check whether
	 * there are relevant expressions on it. Comments, block headers and block footers are not considered
	 * relevant.
	 * 
	 * @param bodyContent
	 *            This list contains the whole set of CST elements of the block containing the line that is to
	 *            be checked.
	 * @param index
	 *            Index of the text expression which we need to check.
	 * @param lookbehind
	 *            If <code>true</code>, this method will iterate over the template elements located
	 *            <u>before</u> the element <code>index</code> and all previous until we reach the end of
	 *            line.
	 * @return <code>true</code> if this line contains relevant characters, <code>false</code> otherwise.
	 */
	private boolean containsRelevantExpressionsOnLine(List<? extends CSTNode> bodyContent, int index,
			boolean lookbehind) {
		boolean result = false;
		int increment = 1;
		if (lookbehind) {
			increment = -1;
		}

		int nextIndex = index;
		CSTNode nextNode = bodyContent.get(nextIndex);
		while (nextIndex + increment > 0 && nextIndex + increment < bodyContent.size() - 1
				&& isSingleLineExpression(nextNode)) {
			if (nextNode instanceof Comment) {
				nextIndex += increment;
			} else if (nextNode instanceof ProtectedAreaBlock) {
				result = true;
				break;
			} else if (nextNode instanceof TextExpression && isEmpty(((TextExpression)nextNode).getValue())) {
				nextIndex += increment;
			} else if (nextNode instanceof Block) {
				List<TemplateExpression> nextBody = ((Block)nextNode).getBody();
				if (nextBody.size() == 0) {
					nextIndex += increment;
				} else {
					int startIndex = 0;
					if (lookbehind) {
						startIndex = nextBody.size() - 1;
					}
					if (containsRelevantExpressionsOnLine(nextBody, startIndex, lookbehind)) {
						result = true;
						break;
					}
					nextIndex += increment;
				}
			} else {
				result = true;
				break;
			}
			nextNode = bodyContent.get(nextIndex);
		}

		if (result) {
			return result;
		}

		if (nextNode instanceof Comment) {
			result = false;
		} else if (nextNode instanceof ProtectedAreaBlock) {
			result = true;
		} else if (nextNode instanceof TextExpression) {
			if (lookbehind && endsInEmptyLine(((TextExpression)nextNode).getValue())) {
				result = false;
			} else if (!lookbehind && startsInEmptyLine(((TextExpression)nextNode).getValue())) {
				result = false;
			} else {
				result = true;
			}
		} else if (nextNode instanceof Block) {
			List<TemplateExpression> nextBody = ((Block)nextNode).getBody();
			if (nextBody.size() == 0) {
				result = false;
			} else {
				int startIndex = 0;
				if (lookbehind) {
					startIndex = nextBody.size() - 1;
				}
				if (containsRelevantExpressionsOnLine(nextBody, startIndex, lookbehind)) {
					result = true;
				}
			}
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * This will return <code>true</code> iff the start position of the given <code>block</code> is on the
	 * same line than is its end position.
	 * 
	 * @param node
	 *            The node we need to check.
	 * @return <code>true</code> iff the given <code>block</code> is a single line block.
	 */
	private boolean isSingleLineExpression(CSTNode node) {
		if (node instanceof TextExpression) {
			return !((TextExpression)node).getValue().contains(UNIX_LINE_SEPARATOR)
					&& !((TextExpression)node).getValue().contains(MAC_LINE_SEPARATOR);
		}
		return astProvider.getLineOfOffset(node.getStartPosition()) == astProvider.getLineOfOffset(node
				.getEndPosition() - 1);
	}

	/**
	 * This is only to be used with the last text expression of a template. Specifically, it will check
	 * whether the text expression is composed of two new line characters, and if the first has been trimmed.
	 * 
	 * @param textValue
	 *            Actual value of the text expression.
	 * @param transformedValue
	 *            value after we shifted its beginning.
	 * @return <code>true</code>iff the text expression is composed of two new line characters and the first
	 *         has been trimmed.
	 */
	private boolean shouldKeepLastNewLine(String textValue, String transformedValue) {
		String lineSeparator;
		if (textValue.contains(DOS_LINE_SEPARATOR)) {
			lineSeparator = DOS_LINE_SEPARATOR;
		} else if (textValue.contains(UNIX_LINE_SEPARATOR)) {
			lineSeparator = UNIX_LINE_SEPARATOR;
		} else if (textValue.contains(MAC_LINE_SEPARATOR)) {
			lineSeparator = MAC_LINE_SEPARATOR;
		} else {
			return false;
		}
		return textValue.equals(lineSeparator + lineSeparator) && transformedValue.equals(lineSeparator);
	}

	/**
	 * This will check the very first line of <code>text</code> and return <code>true</code> if it only
	 * contains whitespaces, <code>false</code> otherwise.
	 * 
	 * @param text
	 *            Text which is to be checked.
	 * @return <code>true</code> if the first line of <code>text</code> contains only whitespaces.
	 */
	private boolean startsInEmptyLine(String text) {
		int endIndex = text.length();
		if (text.contains(DOS_LINE_SEPARATOR)) {
			endIndex = text.indexOf(DOS_LINE_SEPARATOR);
		} else if (text.contains(UNIX_LINE_SEPARATOR)) {
			endIndex = text.indexOf(UNIX_LINE_SEPARATOR);
		} else if (text.contains(MAC_LINE_SEPARATOR)) {
			endIndex = text.indexOf(MAC_LINE_SEPARATOR);
		}
		boolean endsInEmptyLine = true;
		for (int i = 0; i < endIndex; i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				endsInEmptyLine = false;
				break;
			}
		}
		return endsInEmptyLine;
	}

	/**
	 * Returns <code>true</code> if <em>text</em> is only comprised of whitespace characters as returned by
	 * {@link Character#isWhitespace(char)}.
	 * 
	 * @param text
	 *            Text which is to be checked.
	 * @return <code>true</code> if <em>text</em> is only comprised of whitespace characters.
	 */
	private boolean isEmpty(String text) {
		boolean endsInEmptyLine = true;
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				endsInEmptyLine = false;
				break;
			}
		}
		return endsInEmptyLine;
	}

	/**
	 * This will check the very last line of <code>text</code> and return <code>true</code> if it only
	 * contains whitespaces, <code>false</code> otherwise.
	 * 
	 * @param text
	 *            Text which is to be checked.
	 * @return <code>true</code> if the last line of <code>text</code> contains only whitespaces.
	 */
	private boolean endsInEmptyLine(String text) {
		int startIndex = 0;
		if (text.contains(DOS_LINE_SEPARATOR)) {
			startIndex = text.lastIndexOf(DOS_LINE_SEPARATOR) + 2;
		} else if (text.contains(UNIX_LINE_SEPARATOR)) {
			startIndex = text.lastIndexOf(UNIX_LINE_SEPARATOR) + 1;
		} else if (text.contains(MAC_LINE_SEPARATOR)) {
			startIndex = text.lastIndexOf(MAC_LINE_SEPARATOR) + 1;
		}
		boolean endsInEmptyLine = true;
		for (int i = startIndex; i < text.length(); i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				endsInEmptyLine = false;
				break;
			}
		}
		return endsInEmptyLine;
	}

	/**
	 * Deletes the characters to ignore at the beginning and at the end of the given text. (Indentation
	 * strategy method)
	 * 
	 * @param iTextExpression
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TextExpression'
	 * @param oTextExpression
	 *            is the output object of type 'org.eclipse.ocl.ecore.StringLiteralExp'
	 */
	@SuppressWarnings("unchecked")
	private void transformFormattedText(org.eclipse.acceleo.parser.cst.TextExpression iTextExpression,
			org.eclipse.ocl.ecore.StringLiteralExp oTextExpression) {
		String ioValue = iTextExpression.getValue();
		// shortcut
		if (ioValue == null || iTextExpression.eContainingFeature() != CstPackage.eINSTANCE.getBlock_Body()
				|| iTextExpression.eContainer() == null) {
			oTextExpression.setStringSymbol(ioValue);
			return;
		}

		List<CSTNode> eBody = (List<CSTNode>)iTextExpression.eContainer().eGet(
				CstPackage.eINSTANCE.getBlock_Body());
		if (eBody != null && eBody.size() > 0) {
			int index = eBody.indexOf(iTextExpression);
			int shiftBegin;
			if (index == 0 && iTextExpression.eContainer() instanceof ProtectedAreaBlock) {
				shiftBegin = 0;
			} else if (index == 00 && isSingleLineExpression((CSTNode)iTextExpression.eContainer())) {
				shiftBegin = 0;
			} else if (index > 0 && !isRelevantLine(eBody, index, true)) {
				shiftBegin = shiftBegin(ioValue);
			} else if (index == 0
					|| (eBody.get(index - 1) instanceof Block
							&& !(eBody.get(index - 1) instanceof ProtectedAreaBlock) && !isSingleLineExpression(eBody
							.get(index - 1)))) {
				/*
				 * Ignore the carriage return directly following a block iff the latter isn't either a
				 * Protected area or a single line block
				 */
				shiftBegin = shiftBegin(ioValue);
			} else {
				shiftBegin = 0;
			}
			if (shiftBegin > ioValue.length()) {
				oTextExpression.setStartPosition(oTextExpression.getStartPosition() + ioValue.length());
				ioValue = ""; //$NON-NLS-1$
			} else if (shiftBegin > 0) {
				oTextExpression.setStartPosition(oTextExpression.getStartPosition() + shiftBegin);
				ioValue = ioValue.substring(shiftBegin);
			}
			int shiftEnd;
			if (index == eBody.size() - 1 && iTextExpression.eContainer() instanceof Template) {
				boolean keepNewLine = shouldKeepLastNewLine(iTextExpression.getValue(), ioValue);
				shiftEnd = shiftEnd(ioValue, keepNewLine);
			} else if (index == eBody.size() - 1
					&& !isSingleLineExpression((CSTNode)iTextExpression.eContainer())) {
				shiftEnd = shiftEnd(ioValue, true);
			} else if (!isRelevantLine(eBody, index, false)) {
				shiftEnd = shiftEnd(ioValue, true);
			} else if (index + 1 < eBody.size() && eBody.get(index + 1) instanceof Block
					&& !isSingleLineExpression(eBody.get(index + 1))) {
				shiftEnd = shiftEnd(ioValue, true);
			} else {
				shiftEnd = 0;
			}
			if (shiftEnd > ioValue.length()) {
				oTextExpression.setEndPosition(oTextExpression.getEndPosition() - ioValue.length());
				ioValue = ""; //$NON-NLS-1$
			} else if (shiftEnd > 0) {
				oTextExpression.setEndPosition(oTextExpression.getEndPosition() - shiftEnd);
				ioValue = ioValue.substring(0, ioValue.length() - shiftEnd);
			}
		}
		oTextExpression.setStringSymbol(ioValue);
	}

	/**
	 * Gets the characters to ignore at the beginning of the given text.
	 * 
	 * @param ioValue
	 *            is the text to format
	 * @return the number of characters to ignore
	 */
	private int shiftBegin(String ioValue) {
		int shiftBegin = -1;
		for (int b = 0; shiftBegin == -1 && b < ioValue.length(); b++) {
			char c = ioValue.charAt(b);
			if (c == '\n') {
				shiftBegin = b + 1;
			} else if (c == '\r' && (b == ioValue.length() - 1 || ioValue.charAt(b + 1) != '\n')) {
				shiftBegin = b + 1;
			} else if (!Character.isWhitespace(c)) {
				shiftBegin = 0;
			}
		}
		if (shiftBegin == -1) {
			shiftBegin = ioValue.length();
		}
		return shiftBegin;
	}

	/**
	 * Gets the characters to ignore at the end of the given text.
	 * 
	 * @param ioValue
	 *            is the text to format
	 * @param keepPreviousReturn
	 *            indicates if the we have to keep the new line character
	 * @return the number of characters to ignore
	 */
	private int shiftEnd(String ioValue, boolean keepPreviousReturn) {
		int shiftEnd = -1;
		for (int e = ioValue.length() - 1; shiftEnd == -1 && e >= 0; e--) {
			char c = ioValue.charAt(e);
			if (c == '\n') {
				if (keepPreviousReturn) {
					shiftEnd = ioValue.length() - (e + 1);
				} else if (e > 0 && ioValue.charAt(e - 1) == '\r') {
					shiftEnd = ioValue.length() - (e - 1);
				} else {
					shiftEnd = ioValue.length() - e;
				}
			} else if (!Character.isWhitespace(c)) {
				shiftEnd = 0;
			}
		}
		if (shiftEnd == -1) {
			shiftEnd = ioValue.length();
		}
		return shiftEnd;
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Block' of the input
	 * model.
	 * 
	 * @param iBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Block'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Block iBlock) {
		org.eclipse.acceleo.model.mtl.Block oBlock = factory.getOrCreateBlock(iBlock);
		if (iBlock != null && oBlock != null) {
			transformStepCopyPositions(iBlock, oBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			transformStepCopyBody(iBlock, oBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.InitSection' of the
	 * input model.
	 * 
	 * @param iInitSection
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.InitSection'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.InitSection iInitSection) {
		org.eclipse.acceleo.model.mtl.InitSection oInitSection = factory.getOrCreateInitSection(iInitSection);
		if (iInitSection != null && oInitSection != null) {
			transformStepCopyPositions(iInitSection, oInitSection);
			Iterator<org.eclipse.acceleo.parser.cst.Variable> iVariableIt = iInitSection.getVariable()
					.iterator();
			while (iVariableIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iVariableIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oInitSection.getVariable().add(oNext);
				}
				transformStepCopy(iNext);

			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.ProtectedAreaBlock'
	 * of the input model.
	 * 
	 * @param iProtectedAreaBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ProtectedAreaBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.ProtectedAreaBlock iProtectedAreaBlock) {
		org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oProtectedAreaBlock = factory
				.getOrCreateProtectedAreaBlock(iProtectedAreaBlock);
		if (iProtectedAreaBlock != null && oProtectedAreaBlock != null) {
			transformStepCopyPositions(iProtectedAreaBlock, oProtectedAreaBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iProtectedAreaBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oProtectedAreaBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iMarker = iProtectedAreaBlock.getMarker();
			transformStepCopy(iMarker);

			transformStepCopyBody(iProtectedAreaBlock, oProtectedAreaBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.ForBlock' of the
	 * input model.
	 * 
	 * @param iForBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.ForBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.ForBlock iForBlock) {
		org.eclipse.acceleo.model.mtl.ForBlock oForBlock = factory.getOrCreateForBlock(iForBlock);
		if (iForBlock != null && oForBlock != null) {
			transformStepCopyPositions(iForBlock, oForBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iForBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oForBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iIterSet = iForBlock.getIterSet();
			transformStepCopy(iIterSet);

			org.eclipse.acceleo.parser.cst.ModelExpression iBefore = iForBlock.getBefore();
			transformStepCopy(iBefore);

			org.eclipse.acceleo.parser.cst.ModelExpression iEach = iForBlock.getEach();
			transformStepCopy(iEach);

			org.eclipse.acceleo.parser.cst.ModelExpression iAfter = iForBlock.getAfter();
			transformStepCopy(iAfter);

			org.eclipse.acceleo.parser.cst.ModelExpression iGuard = iForBlock.getGuard();
			transformStepCopy(iGuard);

			transformStepCopyBody(iForBlock, oForBlock);
			org.eclipse.acceleo.parser.cst.Variable iLoopVariable = iForBlock.getLoopVariable();
			org.eclipse.ocl.ecore.Variable oLoopVariable = factory.getOrCreateVariable(iLoopVariable);
			if (oLoopVariable != null) {
				oForBlock.setLoopVariable(oLoopVariable);
			}
			transformStepCopy(iLoopVariable);

		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.IfBlock' of the
	 * input model.
	 * 
	 * @param iIfBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.IfBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.IfBlock iIfBlock) {
		org.eclipse.acceleo.model.mtl.IfBlock oIfBlock = factory.getOrCreateIfBlock(iIfBlock);
		if (iIfBlock != null && oIfBlock != null) {
			org.eclipse.acceleo.parser.cst.Block iElse = iIfBlock.getElse();
			if (iElse instanceof org.eclipse.acceleo.parser.cst.Template) {
				// Never
				org.eclipse.acceleo.model.mtl.Template oElse = factory
						.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ProtectedAreaBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oElse = factory
						.getOrCreateProtectedAreaBlock((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.ForBlock oElse = factory
						.getOrCreateForBlock((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.IfBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.IfBlock oElse = factory
						.getOrCreateIfBlock((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.LetBlock oElse = factory
						.getOrCreateLetBlock((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.FileBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.FileBlock oElse = factory
						.getOrCreateFileBlock((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.TraceBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.TraceBlock oElse = factory
						.getOrCreateTraceBlock((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.Macro) {
				// Never
				org.eclipse.acceleo.model.mtl.Macro oElse = factory
						.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iElse);
			} else {
				org.eclipse.acceleo.model.mtl.Block oElse = factory.getOrCreateBlock(iElse);
				if (oElse != null) {
					oIfBlock.setElse(oElse);
				}
				transformStepCopy(iElse);
			}

			transformStepCopyPositions(iIfBlock, oIfBlock);
			Iterator<org.eclipse.acceleo.parser.cst.IfBlock> iElseIfIt = iIfBlock.getElseIf().iterator();
			while (iElseIfIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.IfBlock iNext = iElseIfIt.next();
				org.eclipse.acceleo.model.mtl.IfBlock oNext = factory.getOrCreateIfBlock(iNext);
				if (oNext != null) {
					oIfBlock.getElseIf().add(oNext);
				}
				transformStepCopy(iNext);

			}
			org.eclipse.acceleo.parser.cst.InitSection iInit = iIfBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oIfBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iIfExpr = iIfBlock.getIfExpr();
			transformStepCopy(iIfExpr);

			transformStepCopyBody(iIfBlock, oIfBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.LetBlock' of the
	 * input model.
	 * 
	 * @param iLetBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.LetBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.LetBlock iLetBlock) {
		org.eclipse.acceleo.model.mtl.LetBlock oLetBlock = factory.getOrCreateLetBlock(iLetBlock);
		if (iLetBlock != null && oLetBlock != null) {
			org.eclipse.acceleo.parser.cst.Block iElse = iLetBlock.getElse();
			if (iElse instanceof org.eclipse.acceleo.parser.cst.Template) {
				// Never
				org.eclipse.acceleo.model.mtl.Template oElse = factory
						.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ProtectedAreaBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oElse = factory
						.getOrCreateProtectedAreaBlock((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.ForBlock oElse = factory
						.getOrCreateForBlock((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.ForBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.IfBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.IfBlock oElse = factory
						.getOrCreateIfBlock((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.IfBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.LetBlock oElse = factory
						.getOrCreateLetBlock((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.LetBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.FileBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.FileBlock oElse = factory
						.getOrCreateFileBlock((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.FileBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.TraceBlock) {
				// Never
				org.eclipse.acceleo.model.mtl.TraceBlock oElse = factory
						.getOrCreateTraceBlock((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.TraceBlock)iElse);
			} else if (iElse instanceof org.eclipse.acceleo.parser.cst.Macro) {
				// Never
				org.eclipse.acceleo.model.mtl.Macro oElse = factory
						.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iElse);
			} else {
				org.eclipse.acceleo.model.mtl.Block oElse = factory.getOrCreateBlock(iElse);
				if (oElse != null) {
					oLetBlock.setElse(oElse);
				}
				transformStepCopy(iElse);
			}

			transformStepCopyPositions(iLetBlock, oLetBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iLetBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oLetBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			Iterator<org.eclipse.acceleo.parser.cst.LetBlock> iElseLetIt = iLetBlock.getElseLet().iterator();
			while (iElseLetIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.LetBlock iNext = iElseLetIt.next();
				org.eclipse.acceleo.model.mtl.LetBlock oNext = factory.getOrCreateLetBlock(iNext);
				if (oNext != null) {
					oLetBlock.getElseLet().add(oNext);
				}
				transformStepCopy(iNext);

			}

			org.eclipse.acceleo.parser.cst.Variable iLetVariable = iLetBlock.getLetVariable();
			org.eclipse.ocl.ecore.Variable oLetVariable = factory.getOrCreateVariable(iLetVariable);
			if (oLetVariable != null) {
				oLetBlock.setLetVariable(oLetVariable);
			}
			transformStepCopy(iLetVariable);

			transformStepCopyBody(iLetBlock, oLetBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.FileBlock' of the
	 * input model.
	 * 
	 * @param iFileBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.FileBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.FileBlock iFileBlock) {
		org.eclipse.acceleo.model.mtl.FileBlock oFileBlock = factory.getOrCreateFileBlock(iFileBlock);
		if (iFileBlock != null && oFileBlock != null) {
			org.eclipse.acceleo.parser.cst.OpenModeKind iOpenMode = iFileBlock.getOpenMode();
			org.eclipse.acceleo.model.mtl.OpenModeKind oOpenMode = org.eclipse.acceleo.model.mtl.OpenModeKind
					.get(iOpenMode.getValue());
			oFileBlock.setOpenMode(oOpenMode);
			transformStepCopyPositions(iFileBlock, oFileBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iFileBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oFileBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iFileUrl = iFileBlock.getFileUrl();
			transformStepCopy(iFileUrl);

			org.eclipse.acceleo.parser.cst.ModelExpression iFileCharset = iFileBlock.getCharset();
			transformStepCopy(iFileCharset);

			transformStepCopyBody(iFileBlock, oFileBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.TraceBlock' of the
	 * input model.
	 * 
	 * @param iTraceBlock
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.TraceBlock'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.TraceBlock iTraceBlock) {
		org.eclipse.acceleo.model.mtl.TraceBlock oTraceBlock = factory.getOrCreateTraceBlock(iTraceBlock);
		if (iTraceBlock != null && oTraceBlock != null) {
			transformStepCopyPositions(iTraceBlock, oTraceBlock);
			org.eclipse.acceleo.parser.cst.InitSection iInit = iTraceBlock.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oTraceBlock.setInit(oInit);
			}
			transformStepCopy(iInit);

			org.eclipse.acceleo.parser.cst.ModelExpression iModelElement = iTraceBlock.getModelElement();
			transformStepCopy(iModelElement);

			transformStepCopyBody(iTraceBlock, oTraceBlock);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Macro' of the input
	 * model.
	 * 
	 * @param iMacro
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Macro'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Macro iMacro) {
		org.eclipse.acceleo.model.mtl.Macro oMacro = factory.getOrCreateMacro(iMacro);
		if (iMacro != null && oMacro != null) {
			org.eclipse.acceleo.parser.cst.VisibilityKind iVisibility = iMacro.getVisibility();
			org.eclipse.acceleo.model.mtl.VisibilityKind oVisibility = org.eclipse.acceleo.model.mtl.VisibilityKind
					.get(iVisibility.getValue());
			oMacro.setVisibility(oVisibility);
			transformStepCopyPositions(iMacro, oMacro);
			String ioName = iMacro.getName();
			if (ioName != null && !"".equals(ioName)) { //$NON-NLS-1$
				oMacro.setName(ioName);
			}
			org.eclipse.acceleo.parser.cst.InitSection iInit = iMacro.getInit();
			org.eclipse.acceleo.model.mtl.InitSection oInit = factory.getOrCreateInitSection(iInit);
			if (oInit != null) {
				oMacro.setInit(oInit);
			}
			transformStepCopy(iInit);

			transformStepCopyBody(iMacro, oMacro);
			Iterator<org.eclipse.acceleo.parser.cst.Variable> iParameterIt = iMacro.getParameter().iterator();
			while (iParameterIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iParameterIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oMacro.getParameter().add(oNext);
				}
				transformStepCopy(iNext);

			}

			String ioType = iMacro.getType();
			EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(OCLParser.ANNOTATION_SOURCE);
			eAnnotation.getDetails().put(OCLParser.ANNOTATION_KEY_TYPE, ioType);
			oMacro.getEAnnotations().add(eAnnotation);
			if (oMacro.isDeprecated()) {
				logWarning(AcceleoParserMessages.getString("CST2ASTConverterWithResolver.DeprecatedMacro", //$NON-NLS-1$
						oMacro.getName()), oMacro.getStartPosition(), oMacro.getEndPosition());
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Query' of the input
	 * model.
	 * 
	 * @param iQuery
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Query'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Query iQuery) {
		org.eclipse.acceleo.model.mtl.Query oQuery = factory.getOrCreateQuery(iQuery);
		if (iQuery != null && oQuery != null) {
			org.eclipse.acceleo.parser.cst.VisibilityKind iVisibility = iQuery.getVisibility();
			org.eclipse.acceleo.model.mtl.VisibilityKind oVisibility = org.eclipse.acceleo.model.mtl.VisibilityKind
					.get(iVisibility.getValue());
			oQuery.setVisibility(oVisibility);
			transformStepCopyPositions(iQuery, oQuery);
			String ioName = iQuery.getName();
			if (ioName != null && !"".equals(ioName)) { //$NON-NLS-1$
				oQuery.setName(ioName);
			}
			org.eclipse.acceleo.parser.cst.ModelExpression iExpression = iQuery.getExpression();
			transformStepCopy(iExpression);

			Iterator<org.eclipse.acceleo.parser.cst.Variable> iParameterIt = iQuery.getParameter().iterator();
			while (iParameterIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.Variable iNext = iParameterIt.next();
				org.eclipse.ocl.ecore.Variable oNext = factory.getOrCreateVariable(iNext);
				if (oNext != null) {
					oQuery.getParameter().add(oNext);
				}
				transformStepCopy(iNext);
			}

			String ioType = iQuery.getType();
			EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(OCLParser.ANNOTATION_SOURCE);
			eAnnotation.getDetails().put(OCLParser.ANNOTATION_KEY_TYPE, ioType);
			oQuery.getEAnnotations().add(eAnnotation);

			if (oQuery.isDeprecated()) {
				logWarning(AcceleoParserMessages.getString("CST2ASTConverterWithResolver.DeprecatedQuery", //$NON-NLS-1$
						oQuery.getName()), oQuery.getStartPosition(), oQuery.getEndPosition());
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each reference 'ownedModuleElement' of an input module.
	 * 
	 * @param iModule
	 *            is the input module
	 * @param oModule
	 *            is the output module
	 */
	private void transformStepCopyOwnedModuleElement(org.eclipse.acceleo.parser.cst.Module iModule,
			org.eclipse.acceleo.model.mtl.Module oModule) {
		if (!isCanceled) {
			Set<String> allSignatures = new CompactHashSet<String>();
			Iterator<org.eclipse.acceleo.parser.cst.ModuleElement> iOwnedModuleElementIt = iModule
					.getOwnedModuleElement().iterator();
			while (iOwnedModuleElementIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.ModuleElement iNext = iOwnedModuleElementIt.next();
				StringBuilder signature = new StringBuilder();
				signature.append(iNext.getName());
				if (iNext instanceof org.eclipse.acceleo.parser.cst.Template) {
					org.eclipse.acceleo.model.mtl.Template oNext = factory
							.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iNext);
					if (oNext != null) {
						computeDepreciation(oModule, oNext);
						oModule.getOwnedModuleElement().add(oNext);
					}

					transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iNext);
					signature.append('(');
					boolean first = true;
					for (Variable iVariable : ((org.eclipse.acceleo.parser.cst.Template)iNext).getParameter()) {
						if (first) {
							first = false;
						} else {
							signature.append(',');
						}
						signature.append(iVariable.getType());
					}
					signature.append(')');
					if (((org.eclipse.acceleo.parser.cst.Template)iNext).getGuard() != null) {
						signature.append(((org.eclipse.acceleo.parser.cst.Template)iNext).getGuard()
								.getBody());
					}
					// Remark : The postcondition expression isn't significant to detect a conflict issue
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Macro) {
					org.eclipse.acceleo.model.mtl.Macro oNext = factory
							.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iNext);

					if (oNext != null) {
						computeDepreciation(oModule, oNext);
						oModule.getOwnedModuleElement().add(oNext);
					}

					transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iNext);
					signature.append('(');
					boolean first = true;
					for (Variable iVariable : ((org.eclipse.acceleo.parser.cst.Macro)iNext).getParameter()) {
						if (first) {
							first = false;
						} else {
							signature.append(',');
						}
						signature.append(iVariable.getType());
					}
					signature.append(')');
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Query) {
					org.eclipse.acceleo.model.mtl.Query oNext = factory
							.getOrCreateQuery((org.eclipse.acceleo.parser.cst.Query)iNext);
					if (oNext != null) {
						computeDepreciation(oModule, oNext);
						oModule.getOwnedModuleElement().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Query)iNext);
					signature.append('(');
					boolean first = true;
					for (Variable iVariable : ((org.eclipse.acceleo.parser.cst.Query)iNext).getParameter()) {
						if (first) {
							first = false;
						} else {
							signature.append(',');
						}
						signature.append(iVariable.getType());
					}
					signature.append(')');
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Documentation) {
					org.eclipse.acceleo.model.mtl.Documentation oNext = factory
							.getOrCreateDocumentation((org.eclipse.acceleo.parser.cst.Documentation)iNext);
					if (oNext != null) {
						oModule.getOwnedModuleElement().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Documentation)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Comment) {
					org.eclipse.acceleo.model.mtl.Comment oNext = factory
							.getOrCreateComment((org.eclipse.acceleo.parser.cst.Comment)iNext);
					if (oNext != null) {
						oModule.getOwnedModuleElement().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Comment)iNext);
				}
				String sign = signature.toString();
				if (allSignatures.contains(sign)) {
					this.logProblem(AcceleoParserMessages.getString("CST2ASTConverter.SignatureConflict", //$NON-NLS-1$
							new Object[] {sign }), iNext.getStartPosition(), iNext.getEndPosition());
				} else {
					if (!(iNext instanceof Comment)) {
						allSignatures.add(sign);
					}
				}
			}
		}
	}

	/**
	 * Compute if the documented element should be marked as depreciated.
	 * 
	 * @param oModule
	 *            The module
	 * @param oNext
	 *            the new module element
	 */
	private void computeDepreciation(Module oModule, org.eclipse.acceleo.model.mtl.DocumentedElement oNext) {
		boolean depreciated = false;
		depreciated = oModule.isDeprecated();
		if (!depreciated) {
			EList<ModuleElement> ownedModuleElement = oModule.getOwnedModuleElement();
			if (ownedModuleElement.size() > 0) {
				ModuleElement moduleElement = ownedModuleElement.get(ownedModuleElement.size() - 1);
				org.eclipse.acceleo.model.mtl.Documentation documentation = null;
				if (moduleElement instanceof org.eclipse.acceleo.model.mtl.Documentation) {
					documentation = (org.eclipse.acceleo.model.mtl.Documentation)moduleElement;
				}
				if (documentation != null && documentation.getBody() != null
						&& documentation.getBody().getValue() != null) {
					depreciated = documentation.getBody().getValue().contains(
							IAcceleoConstants.TAG_DEPRECATED);
				}
			}
		}
		oNext.setDeprecated(depreciated);
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Comment' of the
	 * input model.
	 * 
	 * @param iComment
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Comment'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Comment iComment) {
		org.eclipse.acceleo.model.mtl.Comment oComment = factory.getOrCreateComment(iComment);
		if (oComment != null && iComment != null) {
			oComment.setStartPosition(iComment.getStartPosition());
			oComment.setEndPosition(iComment.getEndPosition());
			oComment.setName(iComment.getName());

			CommentBody oCommentBody = MtlFactory.eINSTANCE.createCommentBody();
			oCommentBody.setValue(iComment.getBody());
			oCommentBody.setStartPosition(-1);
			oCommentBody.setEndPosition(-1);

			oComment.setBody(oCommentBody);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each 'org.eclipse.acceleo.parser.cst.Documentation' of
	 * the input model.
	 * 
	 * @param iDocumentation
	 *            is the input object of type 'org.eclipse.acceleo.parser.cst.Documentation'
	 */
	private void transformStepCopy(org.eclipse.acceleo.parser.cst.Documentation iDocumentation) {
		org.eclipse.acceleo.model.mtl.Documentation oDocumentation = factory
				.getOrCreateDocumentation(iDocumentation);
		if (oDocumentation != null && iDocumentation != null) {
			oDocumentation.setStartPosition(iDocumentation.getStartPosition());
			oDocumentation.setEndPosition(iDocumentation.getEndPosition());
			oDocumentation.setName(iDocumentation.getName());

			CommentBody oCommentBody = MtlFactory.eINSTANCE.createCommentBody();
			oCommentBody.setValue(iDocumentation.getBody());
			oCommentBody.setStartPosition(-1);
			oCommentBody.setEndPosition(-1);

			oDocumentation.setBody(oCommentBody);
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for each reference 'body' of an input block.
	 * 
	 * @param iBlock
	 *            is the input block
	 * @param oBlock
	 *            is the output block
	 */
	private void transformStepCopyBody(org.eclipse.acceleo.parser.cst.Block iBlock,
			org.eclipse.acceleo.model.mtl.Block oBlock) {
		if (!isCanceled) {
			Iterator<org.eclipse.acceleo.parser.cst.TemplateExpression> iBodyIt = iBlock.getBody().iterator();
			while (iBodyIt.hasNext()) {
				org.eclipse.acceleo.parser.cst.TemplateExpression iNext = iBodyIt.next();
				if (iNext instanceof org.eclipse.acceleo.parser.cst.Template) {
					org.eclipse.acceleo.model.mtl.Template oNext = factory
							.getOrCreateTemplate((org.eclipse.acceleo.parser.cst.Template)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Template)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.TextExpression) {
					org.eclipse.ocl.ecore.StringLiteralExp oNext = factory
							.getOrCreateStringLiteralExp((org.eclipse.acceleo.parser.cst.TextExpression)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.TextExpression)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.ProtectedAreaBlock) {
					org.eclipse.acceleo.model.mtl.ProtectedAreaBlock oNext = factory
							.getOrCreateProtectedAreaBlock((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.ProtectedAreaBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
					org.eclipse.acceleo.model.mtl.ForBlock oNext = factory
							.getOrCreateForBlock((org.eclipse.acceleo.parser.cst.ForBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.ForBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.IfBlock) {
					org.eclipse.acceleo.model.mtl.IfBlock oNext = factory
							.getOrCreateIfBlock((org.eclipse.acceleo.parser.cst.IfBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.IfBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
					org.eclipse.acceleo.model.mtl.LetBlock oNext = factory
							.getOrCreateLetBlock((org.eclipse.acceleo.parser.cst.LetBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.LetBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.FileBlock) {
					org.eclipse.acceleo.model.mtl.FileBlock oNext = factory
							.getOrCreateFileBlock((org.eclipse.acceleo.parser.cst.FileBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.FileBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.TraceBlock) {
					org.eclipse.acceleo.model.mtl.TraceBlock oNext = factory
							.getOrCreateTraceBlock((org.eclipse.acceleo.parser.cst.TraceBlock)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.TraceBlock)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Macro) {
					org.eclipse.acceleo.model.mtl.Macro oNext = factory
							.getOrCreateMacro((org.eclipse.acceleo.parser.cst.Macro)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Macro)iNext);
				} else if (iNext instanceof org.eclipse.acceleo.parser.cst.Block) {
					org.eclipse.acceleo.model.mtl.Block oNext = factory
							.getOrCreateBlock((org.eclipse.acceleo.parser.cst.Block)iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
					transformStepCopy((org.eclipse.acceleo.parser.cst.Block)iNext);
				} else if (!(iNext instanceof org.eclipse.acceleo.parser.cst.Comment)) {
					org.eclipse.acceleo.model.mtl.TemplateExpression oNext = factory
							.getOrCreateTemporaryTemplateExpression(iNext);
					if (oNext != null) {
						oBlock.getBody().add(oNext);
					}
				}
			}
		}
	}

	/**
	 * The step 'StepCopy' of the transformation for the positions of each 'CST' node.
	 * 
	 * @param iNode
	 *            is the input node
	 * @param oNode
	 *            is the output node
	 */
	private void transformStepCopyPositions(org.eclipse.acceleo.parser.cst.CSTNode iNode,
			org.eclipse.ocl.utilities.ASTNode oNode) {
		int ioStartPosition = iNode.getStartPosition();
		oNode.setStartPosition(ioStartPosition);
		int ioEndPosition = iNode.getEndPosition();
		oNode.setEndPosition(ioEndPosition);
		// Set up the adapter that'll be in charge of maintaining line information
		oNode.eAdapters().add(new AcceleoASTNodeAdapter(astProvider.getLineOfOffset(ioStartPosition)));
	}

	/**
	 * The way to cancel the current CST to AST action.
	 * 
	 * @param cancel
	 *            is the 'cancel' state of the current action, you must use 'true' to disable the action and
	 *            'false' to enable it
	 */
	public void canceling(boolean cancel) {
		isCanceled = cancel;
	}

	/**
	 * Clears some cache of the CST2ASTConverter.
	 */
	public void clear() {
		this.factory.clear();
	}

}
