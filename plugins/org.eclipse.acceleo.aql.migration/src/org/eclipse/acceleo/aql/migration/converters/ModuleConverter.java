/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.AcceleoFactory;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.migration.IModuleResolver;
import org.eclipse.acceleo.aql.migration.MigrationException;
import org.eclipse.acceleo.aql.migration.converters.utils.TypeUtils;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.EcorePackage;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;

/**
 * A converter dedicated to MTL elements.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class ModuleConverter extends AbstractConverter {

	/**
	 * The new line string.
	 */
	private static final String NEW_LINE = "\n";

	/**
	 * A converter dedicated to expressions.
	 */
	private ExpressionConverter expressionConverter = new ExpressionConverter();

	/**
	 * The module resolver, for imports/extends.
	 */
	private IModuleResolver moduleResolver;

	/**
	 * The {@link List} of service class to copy.
	 */
	private final List<String> serviceClassToCopy = new ArrayList<>();

	/**
	 * Creates the converter using the given module resolver.
	 * 
	 * @param moduleResolver
	 *            the module resolver
	 */
	public ModuleConverter(IModuleResolver moduleResolver) {
		this.moduleResolver = moduleResolver;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.migration.converters.AbstractConverter#convert(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Object convert(EObject input) {
		Object res = null;
		switch (input.eClass().getClassifierID()) {
			case MtlPackage.MODULE:
				res = caseModule((org.eclipse.acceleo.model.mtl.Module)input);
				break;
			case MtlPackage.TEMPLATE:
				res = caseTemplate((org.eclipse.acceleo.model.mtl.Template)input);
				break;
			case MtlPackage.QUERY:
				res = caseQuery((org.eclipse.acceleo.model.mtl.Query)input);
				break;
			case MtlPackage.DOCUMENTATION:
			case MtlPackage.MODULE_DOCUMENTATION:
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION:
				res = caseDocumentation((org.eclipse.acceleo.model.mtl.Documentation)input);
				break;
			case MtlPackage.COMMENT:
				res = caseComment((org.eclipse.acceleo.model.mtl.Comment)input);
				break;
			case MtlPackage.FILE_BLOCK:
				res = caseFileBlock((FileBlock)input);
				break;
			case MtlPackage.FOR_BLOCK:
				res = caseForBlock((ForBlock)input);
				break;
			case MtlPackage.IF_BLOCK:
				res = caseIfBlock((IfBlock)input);
				break;
			case MtlPackage.LET_BLOCK:
				res = caseLetBlock((LetBlock)input);
				break;
			case MtlPackage.PROTECTED_AREA_BLOCK:
				res = caseProtectedAreaBlock((ProtectedAreaBlock)input);
				break;
			case EcorePackage.VARIABLE:
				res = caseVariable((org.eclipse.ocl.ecore.Variable)input);
				break;
			case EcorePackage.STRING_LITERAL_EXP:
				res = caseText((StringLiteralExp)input);
				break;
			case MtlPackage.TYPED_MODEL:
				res = caseTypedModel((TypedModel)input);
				break;
			default:
				if (input instanceof OCLExpression) {
					res = expressionConverter.convertToStatement((OCLExpression)input);
				} else {
					throw new MigrationException(input);
				}
				break;
		}
		return res;
	}

	private Object caseModule(org.eclipse.acceleo.model.mtl.Module inputModule) {
		final Module outputModule = AcceleoFactory.eINSTANCE.createModule();
		outputModule.setName(inputModule.getName());
		map(inputModule.getInput(), outputModule.getMetamodels());
		map(inputModule.getOwnedModuleElement(), outputModule.getModuleElements());

		// extends
		if (!inputModule.getExtends().isEmpty()) {
			ModuleReference moduleReference = AcceleoFactory.eINSTANCE.createModuleReference();
			// only one module can be extended
			org.eclipse.acceleo.model.mtl.Module extendedModule = inputModule.getExtends().get(0);
			if (extendedModule.getNsURI() != null) {
				moduleReference.setQualifiedName(extendedModule.getNsURI());
			} else {
				moduleReference.setQualifiedName(moduleResolver.getQualifiedName(inputModule,
						extendedModule));
			}
			outputModule.setExtends(moduleReference);
		}

		// imports
		for (org.eclipse.acceleo.model.mtl.Module importedModule : inputModule.getImports()) {
			Import outputImport = AcceleoFactory.eINSTANCE.createImport();
			ModuleReference moduleReference = AcceleoFactory.eINSTANCE.createModuleReference();
			if (importedModule.getNsURI() != null) {
				moduleReference.setQualifiedName(importedModule.getNsURI());
			} else {
				moduleReference.setQualifiedName(moduleResolver.getQualifiedName(inputModule,
						importedModule));
			}
			outputImport.setModule(moduleReference);
			outputModule.getImports().add(outputImport);
		}

		// add imports for invoke()
		addInvokeImports(inputModule, outputModule);

		return outputModule;
	}

	private void addInvokeImports(org.eclipse.acceleo.model.mtl.Module inputModule,
			final Module outputModule) {
		// TODO this is sub optimal, we should have access to the output Module when migrating
		// OperationCallExp
		final Set<String> knownImports = new HashSet<String>();
		for (Import imp : outputModule.getImports()) {
			knownImports.add(imp.getModule().getQualifiedName());
		}

		final List<String> imports = new ArrayList<String>();
		final Iterator<EObject> it = inputModule.eAllContents();
		while (it.hasNext()) {
			final EObject eObj = it.next();
			if (eObj instanceof OperationCallExp && expressionConverter.isInvokeCall(
					(OperationCallExp)eObj)) {
				final OperationCallExp call = (OperationCallExp)eObj;
				if (call.getArgument().get(0) instanceof StringLiteralExp) {
					final String serviceClassName = ((org.eclipse.ocl.expressions.StringLiteralExp<EClassifier>)call
							.getArgument().get(0)).getStringSymbol();
					final String toImport = serviceClassName.replace(".", AcceleoParser.QUALIFIER_SEPARATOR);
					if (!knownImports.contains(toImport)) {
						knownImports.add(toImport);
						serviceClassToCopy.add(serviceClassName);
						imports.add(toImport);
					}
				}
			}
		}
		Collections.sort(imports);

		for (String toImport : imports) {
			final Import imp = AcceleoFactory.eINSTANCE.createImport();
			final ModuleReference moduleRef = AcceleoFactory.eINSTANCE.createModuleReference();
			imp.setModule(moduleRef);
			moduleRef.setQualifiedName(toImport);
			outputModule.getImports().add(imp);
		}
	}

	private Object caseTemplate(org.eclipse.acceleo.model.mtl.Template inputTemplate) {
		List<EObject> res = new ArrayList<>();
		Template outputTemplate = AcceleoFactory.eINSTANCE.createTemplate();
		if (inputTemplate.getDocumentation() != null) {
			outputTemplate.setDocumentation((Documentation)convert(inputTemplate.getDocumentation()));
		}
		outputTemplate.setName(inputTemplate.getName());
		outputTemplate.setMain(inputTemplate.isMain());
		outputTemplate.setVisibility(VisibilityKind.getByName(inputTemplate.getVisibility().getName()
				.toLowerCase()));

		// parameters
		map(inputTemplate.getParameter(), outputTemplate.getParameters());

		// post
		if (inputTemplate.getPost() != null) {
			outputTemplate.setPost(expressionConverter.convertToExpression(inputTemplate.getPost(), true));
		}

		// guard
		if (inputTemplate.getGuard() != null) {
			outputTemplate.setGuard(expressionConverter.convertToExpression(inputTemplate.getGuard(), true));
		}

		// main comment
		if (inputTemplate.isMain()) {
			Comment mainComment = AcceleoFactory.eINSTANCE.createComment();
			CommentBody commentBody = AcceleoFactory.eINSTANCE.createCommentBody();
			commentBody.setValue("@main");
			mainComment.setBody(commentBody);
			res.add(mainComment);
		}
		res.add(outputTemplate);

		// statements
		Block body = AcceleoFactory.eINSTANCE.createBlock();
		outputTemplate.setBody(body);
		map(inputTemplate.getBody(), body.getStatements());
		return res;
	}

	private Object caseQuery(org.eclipse.acceleo.model.mtl.Query inputQuery) {
		Query outputQuery = AcceleoFactory.eINSTANCE.createQuery();
		if (inputQuery.getDocumentation() != null) {
			outputQuery.setDocumentation((Documentation)convert(inputQuery.getDocumentation()));
		}
		outputQuery.setName(inputQuery.getName());
		outputQuery.setVisibility(VisibilityKind.getByName(inputQuery.getVisibility().getName()
				.toLowerCase()));
		map(inputQuery.getParameter(), outputQuery.getParameters());
		outputQuery.setBody(expressionConverter.convertToExpression(inputQuery.getExpression(), false));
		outputQuery.setType(createAstResult(TypeUtils.createTypeLiteral(inputQuery.getType())));
		return outputQuery;
	}

	private Object caseFileBlock(FileBlock input) {
		FileStatement output = AcceleoFactory.eINSTANCE.createFileStatement();
		if (input.getCharset() != null) {
			output.setCharset(expressionConverter.convertToExpression(input.getCharset(), false));
		}
		output.setUrl(expressionConverter.convertToExpression(input.getFileUrl(), false));
		output.setMode(OpenModeKind.getByName(input.getOpenMode().getName().toLowerCase()));
		Block body = AcceleoFactory.eINSTANCE.createBlock();
		output.setBody(body);
		map(input.getBody(), body.getStatements());
		return output;
	}

	private Object caseLetBlock(LetBlock input) {
		if (!input.getElseLet().isEmpty()) {
			throw new MigrationException(input.getElseLet().get(0));
		}
		LetStatement output = AcceleoFactory.eINSTANCE.createLetStatement();

		Binding binding = AcceleoFactory.eINSTANCE.createBinding();
		output.getVariables().add(binding);
		binding.setName(input.getLetVariable().getName());
		binding.setType(createAstResult(TypeUtils.createTypeLiteral(input.getLetVariable().getType())));
		binding.setInitExpression(expressionConverter.convertToExpression((OCLExpression)input
				.getLetVariable().getInitExpression(), false));

		Block body = AcceleoFactory.eINSTANCE.createBlock();
		output.setBody(body);
		map(input.getBody(), body.getStatements());
		return output;
	}

	private Object caseForBlock(ForBlock input) {
		ForStatement output = AcceleoFactory.eINSTANCE.createForStatement();
		Block body = AcceleoFactory.eINSTANCE.createBlock();
		output.setBody(body);
		Binding binding = AcceleoFactory.eINSTANCE.createBinding();
		output.setBinding(binding);
		org.eclipse.ocl.ecore.Variable loopVariable = input.getLoopVariable();
		if (loopVariable != null) {
			binding.setName(loopVariable.getName());
			binding.setType(createAstResult(TypeUtils.createTypeLiteral(loopVariable.getType())));
		} else {
			// TODO manage implicit for block
			throw new MigrationException(input);
		}
		binding.setInitExpression(expressionConverter.convertToExpression(input.getIterSet(), false));
		map(input.getBody(), body.getStatements());
		OCLExpression each = input.getEach();
		if (each != null) {
			output.setSeparator(expressionConverter.convertToExpression(each, false));
		}
		return output;
	}

	private IfStatement caseIfBlock(IfBlock input) {
		IfStatement output = AcceleoFactory.eINSTANCE.createIfStatement();
		output.setCondition(expressionConverter.convertToExpression(input.getIfExpr(), false));

		Block thenBlock = AcceleoFactory.eINSTANCE.createBlock();
		output.setThen(thenBlock);
		map(input.getBody(), thenBlock.getStatements());

		List<org.eclipse.acceleo.model.mtl.Block> inputElseBlocks = new ArrayList<>();
		inputElseBlocks.addAll(input.getElseIf());
		if (input.getElse() != null) {
			inputElseBlocks.add(input.getElse());
		}

		if (!inputElseBlocks.isEmpty()) {
			IfStatement current = output;
			for (org.eclipse.acceleo.model.mtl.Block inputElseBlock : inputElseBlocks) {
				Block elseBlock = AcceleoFactory.eINSTANCE.createBlock();
				current.setElse(elseBlock);
				if (inputElseBlock instanceof IfBlock) {
					current = caseIfBlock((IfBlock)inputElseBlock);
					elseBlock.getStatements().add(current);
				} else {
					map(inputElseBlock.getBody(), elseBlock.getStatements());
				}
			}
		}
		return output;
	}

	private Object caseText(StringLiteralExp input) {
		TextStatement output = AcceleoFactory.eINSTANCE.createTextStatement();
		output.setValue(input.getStringSymbol());
		return output;
	}

	private Object caseTypedModel(TypedModel input) {
		List<EObject> res = new ArrayList<>();
		for (EPackage ePackage : input.getTakesTypesFrom()) {
			Metamodel metamodel = AcceleoFactory.eINSTANCE.createMetamodel();
			if (ePackage.eIsProxy()) {
				// we create a dummy EPackage
				EPackage dummy = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEPackage();
				dummy.setNsURI(EcoreUtil.getURI(ePackage).toString().split("#")[0]);
				metamodel.setReferencedPackage(dummy);
			} else {
				metamodel.setReferencedPackage(ePackage);
			}
			res.add(metamodel);
		}
		return res;
	}

	private Object caseVariable(org.eclipse.ocl.ecore.Variable inputVariable) {
		Variable outputVariable = AcceleoFactory.eINSTANCE.createVariable();
		outputVariable.setName(inputVariable.getName());
		outputVariable.setType(createAstResult(TypeUtils.createTypeLiteral(inputVariable.getType())));
		return outputVariable;
	}

	private Object caseDocumentation(org.eclipse.acceleo.model.mtl.Documentation input) {
		Documentation output = AcceleoFactory.eINSTANCE.createModuleElementDocumentation();
		CommentBody body = AcceleoFactory.eINSTANCE.createCommentBody();
		StringBuilder formatValue = new StringBuilder();
		formatValue.append(NEW_LINE);
		for (String line : input.getBody().getValue().split(NEW_LINE)) {
			if (!line.trim().isEmpty()) {
				formatValue.append(" * " + line + NEW_LINE);
			}
		}
		formatValue.append("*");
		body.setValue(formatValue.toString());
		output.setBody(body);
		return output;
	}

	private Object caseComment(org.eclipse.acceleo.model.mtl.Comment inputComment) {
		Comment outputComment = AcceleoFactory.eINSTANCE.createComment();
		CommentBody commentBody = AcceleoFactory.eINSTANCE.createCommentBody();
		commentBody.setValue(inputComment.getBody().getValue().substring(1));
		outputComment.setBody(commentBody);
		return outputComment;
	}

	private Object caseProtectedAreaBlock(ProtectedAreaBlock input) {
		ProtectedArea output = AcceleoFactory.eINSTANCE.createProtectedArea();
		output.setId(expressionConverter.convertToExpression(input.getMarker(), false));
		Block body = AcceleoFactory.eINSTANCE.createBlock();
		output.setBody(body);
		map(input.getBody(), body.getStatements());
		return output;
	}

	/**
	 * Gets the {@link List} of service class to copy.
	 * 
	 * @return the {@link List} of service class to copy
	 */
	public List<String> getServiceClassToCopy() {
		return serviceClassToCopy;
	}

}
