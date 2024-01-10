/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation.quickfixes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.ErrorModuleReference;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.quickfixes.AstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.AstQuickFixesSwitch;
import org.eclipse.acceleo.query.parser.quickfixes.AstTextReplacement;
import org.eclipse.acceleo.query.parser.quickfixes.IAstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.IAstTextReplacement;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

public class AqlQuickFixesSwitch extends AstQuickFixesSwitch {

	/**
	 * A space.
	 */
	private static final String SPACE = " ";

	/**
	 * The {@link Pattern} to find empty module metamodel part of the {@link Module}.
	 */
	private static final Pattern EMPTY_MODULE_METAMODEL_PATTERN = Pattern.compile("\\(\\s*\\)");

	/**
	 * The {@link Pattern} to find empty parameter part of a {@link Template} or {@link Query}.
	 */
	private static final Pattern EMPTY_PARAMETER_PATTERN = Pattern.compile("\\(\\s*\\)");

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private IAcceleoValidationResult validationResult;

	/**
	 * the {@link Module}.
	 */
	private final Module module;

	/**
	 * The {@link Module} qualified name.
	 */
	private String moduleQualifiedName;

	/**
	 * the text representation of the {@link Module}.
	 */
	private String moduleText;

	/**
	 * Line and column for each offset in {@link #moduleText}.
	 */
	final int[][] linesAndColumns;

	/**
	 * The {@link Positions}.
	 */
	private final Positions<ASTNode> positions;

	/**
	 * The new line {@link String}.
	 */
	private final String newLine;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}.
	 * @param validationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param moduleQualifiedName
	 *            the {@link Module} qualified name
	 * @param moduleText
	 *            the text representation of the {@link Module}
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AqlQuickFixesSwitch(IQualifiedNameQueryEnvironment queryEnvironment,
			IAcceleoValidationResult validationResult, String moduleQualifiedName, String moduleText,
			String newLine) {
		super(validationResult.getAcceleoAstResult().getPositions());
		this.queryEnvironment = queryEnvironment;
		this.validationResult = validationResult;
		this.module = validationResult.getAcceleoAstResult().getModule();
		this.moduleQualifiedName = moduleQualifiedName;
		this.moduleText = moduleText;
		this.linesAndColumns = AQLUtils.getLinesAndColumns(moduleText);
		this.newLine = newLine;

		this.positions = validationResult.getAcceleoAstResult().getPositions();
	}

	@Override
	public List<IAstQuickFix> caseVarRef(VarRef varRef) {
		final List<IAstQuickFix> res = new ArrayList<>();

		final ModuleElement moduleElement = AcceleoUtil.getContainingModuleElement(varRef);
		if (moduleElement != null) {
			res.addAll(getAddParameterQuickFixes(varRef, moduleElement));
		}

		// add surrounding LetStatement
		final Statement containingStatement = AcceleoUtil.getContainingStatement(varRef);
		if (containingStatement != null) {
			res.addAll(getSurroundWithLetStatementQuickFix(varRef, containingStatement));
		}

		return res;
	}

	private List<IAstQuickFix> getSurroundWithLetStatementQuickFix(VarRef varRef,
			final Statement containingStatement) {
		final List<IAstQuickFix> res = new ArrayList<>();
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI uri = resolver.getSourceURI(moduleQualifiedName);
		final LetStatement letStatement = AcceleoPackage.eINSTANCE.getAcceleoFactory().createLetStatement();
		final Block body = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();
		final TextStatement textStatement = AcceleoPackage.eINSTANCE.getAcceleoFactory()
				.createTextStatement();

		final int offsetStart = positions.getStartPositions(containingStatement);
		final int lineStart = positions.getStartLines(containingStatement);
		final int columnStart = positions.getStartColumns(containingStatement);
		final int offsetEnd = positions.getEndPositions(containingStatement);
		final int lineEnd = positions.getEndLines(containingStatement);
		final int columnEnd = positions.getEndColumns(containingStatement);

		// use a TextStatement to serialize exactly the original code
		final String indent = moduleText.substring(offsetStart - columnStart, offsetStart);
		final String text = indent + moduleText.substring(positions.getStartPositions(containingStatement),
				positions.getEndPositions(containingStatement)).replaceAll("\\R\\s*", "$0" + SPACE + SPACE)
				+ newLine + indent;
		textStatement.setValue(text);
		body.getStatements().add(textStatement);
		letStatement.setBody(body);
		final Binding binding = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBinding();
		binding.setName(varRef.getVariableName());
		final AstResult bindingType = parseWhileAqlTypeLiteral(AstSerializer.STRING_TYPE);
		binding.setType(bindingType);
		binding.setTypeAql(bindingType.getAst());
		final org.eclipse.acceleo.Expression initExpression = AcceleoPackage.eINSTANCE.getAcceleoFactory()
				.createExpression();
		final AstResult initExpressionAstResult = parseWhileAqlExpression("''");
		initExpression.setAst(initExpressionAstResult);
		initExpression.setAql(initExpressionAstResult.getAst());
		binding.setInitExpression(initExpression);
		letStatement.getVariables().add(binding);

		final IAstQuickFix fix = new AstQuickFix("Surround with Let: " + varRef.getVariableName());
		final String replacement = new AcceleoAstSerializer(newLine).serialize(letStatement);
		final AstTextReplacement textReplacement = new AstTextReplacement(uri, replacement, offsetStart,
				lineStart, columnStart, offsetEnd, lineEnd, columnEnd);
		fix.getTextReplacements().add(textReplacement);
		res.add(fix);

		return res;
	}

	private List<IAstQuickFix> getAddParameterQuickFixes(VarRef varRef, final ModuleElement moduleElement) {
		final List<IAstQuickFix> res = new ArrayList<>();

		final IAstQuickFix fix = new AstQuickFix("Add parameter " + varRef.getVariableName());
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI uri = resolver.getSourceURI(moduleQualifiedName);
		if (moduleElement instanceof Template) {
			final Template template = (Template)moduleElement;
			if (!template.getParameters().isEmpty()) {
				final Variable lastParameter = template.getParameters().get(template.getParameters().size()
						- 1);
				final int offset = positions.getEndPositions(lastParameter);
				final int line = positions.getEndLines(lastParameter);
				final int column = positions.getEndColumns(lastParameter);
				final AstTextReplacement textReplacement = new AstTextReplacement(uri, AcceleoParser.COMMA
						+ SPACE + varRef.getVariableName() + AcceleoParser.COLON + SPACE
						+ AstSerializer.STRING_TYPE, offset, line, column, offset, line, column);
				fix.getTextReplacements().add(textReplacement);
				res.add(fix);
			} else {
				final int templateStartOffest = positions.getStartPositions(template);
				final Matcher matcher = EMPTY_PARAMETER_PATTERN.matcher(moduleText.substring(
						templateStartOffest, positions.getEndPositions(template)));
				if (matcher.find()) {
					final int offset = templateStartOffest + matcher.start();
					final int line = linesAndColumns[offset][0];
					final int column = linesAndColumns[offset][1];
					final AstTextReplacement textReplacement = new AstTextReplacement(uri, varRef
							.getVariableName() + AcceleoParser.COLON + SPACE + AstSerializer.STRING_TYPE,
							offset, line, column, offset, line, column);
					fix.getTextReplacements().add(textReplacement);
					res.add(fix);
				}
			}
		} else if (moduleElement instanceof Query) {
			final Query query = (Query)moduleElement;
			if (!query.getParameters().isEmpty()) {
				final Variable lastParameter = query.getParameters().get(query.getParameters().size() - 1);
				final int offset = positions.getEndPositions(lastParameter);
				final int line = positions.getEndLines(lastParameter);
				final int column = positions.getEndColumns(lastParameter);
				final AstTextReplacement textReplacement = new AstTextReplacement(uri, AcceleoParser.COMMA
						+ SPACE + varRef.getVariableName() + AcceleoParser.COLON + SPACE
						+ AstSerializer.STRING_TYPE, offset, line, column, offset, line, column);
				fix.getTextReplacements().add(textReplacement);
				res.add(fix);
			} else {
				final int queryStartOffest = positions.getStartPositions(query);
				final Matcher matcher = EMPTY_PARAMETER_PATTERN.matcher(moduleText.substring(queryStartOffest,
						positions.getEndPositions(query)));
				if (matcher.find()) {
					final int offset = queryStartOffest + matcher.start();
					final int line = linesAndColumns[offset][0];
					final int column = linesAndColumns[offset][1];
					final AstTextReplacement textReplacement = new AstTextReplacement(uri, varRef
							.getVariableName() + AcceleoParser.COLON + SPACE + AstSerializer.STRING_TYPE,
							offset, line, column, offset, line, column);
					fix.getTextReplacements().add(textReplacement);
					res.add(fix);
				}
			}
		}

		return res;
	}

	@Override
	public List<IAstQuickFix> caseErrorCall(ErrorCall errorCall) {
		final List<IAstQuickFix> res = new ArrayList<>();

		if (errorCall.isMissingEndParenthesis()) {
			final IAstQuickFix fix = new AstQuickFix("Add missing closing parenthesis");
			final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
			final URI uri = resolver.getSourceURI(moduleQualifiedName);
			final int offset = positions.getEndPositions(errorCall);
			final int line = positions.getEndLines(errorCall);
			final int column = positions.getEndColumns(errorCall);
			final AstTextReplacement textReplacement = new AstTextReplacement(uri, ")", offset, line, column,
					offset, line, column);
			fix.getTextReplacements().add(textReplacement);
			res.add(fix);
		}

		return res;
	}

	@Override
	public List<IAstQuickFix> caseCall(Call call) {
		final List<IAstQuickFix> res = new ArrayList<>();

		final IType returnType = new ClassType(queryEnvironment, String.class);
		final List<Set<IType>> argumentTypes = new ArrayList<>();
		for (Expression argument : call.getArguments()) {
			argumentTypes.add(validationResult.getPossibleTypes(argument));
		}
		final CombineIterator<IType> it = new CombineIterator<>(argumentTypes);
		while (it.hasNext()) {
			res.addAll(getAddServiceQuickFixes(call.getServiceName(), returnType, it.next()));
		}

		return res;
	}

	@Override
	public List<IAstQuickFix> caseEClassifierTypeLiteral(EClassifierTypeLiteral eClassifierTypeLiteral) {
		final List<IAstQuickFix> res;

		final String ePackageName = eClassifierTypeLiteral.getEPackageName();
		if (queryEnvironment.getEPackageProvider().getEPackage(ePackageName).isEmpty()) {
			res = getAddMetamodelsQuickFixes(ePackageName);
		} else {
			res = Collections.emptyList();
		}
		return res;
	}

	@Override
	public List<IAstQuickFix> caseEnumLiteral(EnumLiteral enumLiteral) {
		final List<IAstQuickFix> res;

		final String ePackageName = enumLiteral.getEPackageName();
		if (queryEnvironment.getEPackageProvider().getEPackage(ePackageName).isEmpty()) {
			res = getAddMetamodelsQuickFixes(ePackageName);
		} else {
			res = Collections.emptyList();
		}

		return res;
	}

	/**
	 * Gets the {@link List} of {@link IAstQuickFix} to add needed metamodels according to the given
	 * {@link EPackage#getName() EPackage name}.
	 * 
	 * @param ePackageName
	 *            the {@link EPackage#getName() EPackage name}
	 * @return the {@link List} of {@link IAstQuickFix} to add needed metamodels according to the given
	 *         {@link EPackage#getName() EPackage name}
	 */
	private List<IAstQuickFix> getAddMetamodelsQuickFixes(final String ePackageName) {
		final List<IAstQuickFix> res = new ArrayList<>();

		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI uri = resolver.getSourceURI(moduleQualifiedName);
		for (Object obj : EPackage.Registry.INSTANCE.values()) {
			final EPackage ePkg;
			if (obj instanceof EPackage.Descriptor) {
				ePkg = ((EPackage.Descriptor)obj).getEPackage();
			} else if (obj instanceof EPackage) {
				ePkg = (EPackage)obj;
			} else {
				// this should not happen
				continue;
			}
			if (ePkg.getName().equals(ePackageName)) {
				final IAstQuickFix fix = new AstQuickFix("Add " + ePkg.getNsURI());
				final int offset;
				final int line;
				final int column;
				if (module.getMetamodels().isEmpty()) {
					final String moduleHeader = moduleText.substring(module.getStartHeaderPosition(), module
							.getEndHeaderPosition());
					final Matcher matcher = EMPTY_MODULE_METAMODEL_PATTERN.matcher(moduleHeader);
					if (matcher.find()) {
						offset = module.getStartHeaderPosition() + matcher.start();
						line = linesAndColumns[offset][0];
						column = linesAndColumns[offset][1];
						final AstTextReplacement textReplacement = new AstTextReplacement(uri,
								AcceleoParser.QUOTE + ePkg.getNsURI() + AcceleoParser.QUOTE, offset, line,
								column, offset, line, column);
						fix.getTextReplacements().add(textReplacement);
						res.add(fix);
					}
				} else {
					final Metamodel lastMetamodel = module.getMetamodels().get(module.getMetamodels().size()
							- 1);
					offset = positions.getEndPositions(lastMetamodel);
					line = positions.getEndLines(lastMetamodel);
					column = positions.getEndColumns(lastMetamodel);
					final AstTextReplacement textReplacement = new AstTextReplacement(uri, AcceleoParser.COMMA
							+ SPACE + AcceleoParser.QUOTE + ePkg.getNsURI() + AcceleoParser.QUOTE, offset,
							line, column, offset, line, column);
					fix.getTextReplacements().add(textReplacement);
					res.add(fix);
				}
			}
		}

		return res;
	}

	private List<IAstQuickFix> getAddServiceQuickFixes(String serviceName, IType returnType,
			List<IType> argumentTypes) {
		final List<IAstQuickFix> res = new ArrayList<>();

		final String signature = getSignature(serviceName, argumentTypes);
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final URI uri = resolver.getSourceURI(moduleQualifiedName);
		if (isQueryModule(moduleQualifiedName)) {
			final IAstQuickFix fix = new AstQuickFix("Add query " + signature + " to this module");
			if (uri != null && "file".equals(uri.getScheme())) {
				fix.getTextReplacements().add(getQueryReplacement(uri, module, serviceName, returnType,
						argumentTypes));
			}
			res.add(fix);
		} else {
			final IAstQuickFix fix = new AstQuickFix("Add template " + signature + " to this module");
			if (uri != null && "file".equals(uri.getScheme())) {
				fix.getTextReplacements().add(getTemplateReplacement(uri, module, serviceName, returnType,
						argumentTypes));
			}
			res.add(fix);
		}

		final List<String> qualifiedNames = new ArrayList<>();
		if (module.getExtends() != null && !(module.getExtends() instanceof ErrorModuleReference)) {
			qualifiedNames.add(module.getExtends().getQualifiedName());
		}
		for (Import imp : module.getImports()) {
			if (!(imp instanceof ErrorImport)) {
				if (!(imp.getModule() instanceof ErrorModuleReference) && validationResult
						.getValidationMessages(imp.getModule()).isEmpty()) {
					qualifiedNames.add(imp.getModule().getQualifiedName());
				}
			}
		}

		for (String qName : qualifiedNames) {
			final Object resolved = resolver.resolve(qName);
			if (resolved instanceof Module) {
				if (isQueryModule(qName)) {
					final IAstQuickFix fix = new AstQuickFix("Add query " + signature + " to " + qName);
					final URI sourceURI = resolver.getSourceURI(qName);
					if (sourceURI != null && "file".equals(sourceURI.getScheme())) {
						fix.getTextReplacements().add(getQueryReplacement(sourceURI, (Module)resolved,
								serviceName, returnType, argumentTypes));
					}
					res.add(fix);
				} else {
					final IAstQuickFix fix = new AstQuickFix("Add template " + signature + " to " + qName);
					final URI sourceURI = resolver.getSourceURI(qName);
					if (sourceURI != null && "file".equals(sourceURI.getScheme())) {
						fix.getTextReplacements().add(getTemplateReplacement(sourceURI, (Module)resolved,
								serviceName, returnType, argumentTypes));
					}
					res.add(fix);
				}
			} else if (resolved instanceof Class<?>) {
				final URI sourceURI = resolver.getSourceURI(qName);
				if (sourceURI != null && "file".equals(sourceURI.getScheme())) {
					final IAstQuickFix fix = new AstQuickFix("Add service " + signature + " to " + qName);
					try (InputStream is = sourceURI.toURL().openStream()) {
						final String classContents = AcceleoUtil.getContent(is, StandardCharsets.UTF_8
								.name());
						fix.getTextReplacements().add(getServiceReplacement(sourceURI, (Class<?>)resolved,
								classContents, serviceName, returnType, argumentTypes));
						res.add(fix);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		return res;
	}

	private IAstTextReplacement getQueryReplacement(URI sourceURI, Module module, String serviceName,
			IType returnType, List<IType> argumentTypes) {
		final Query query = AcceleoPackage.eINSTANCE.getAcceleoFactory().createQuery();
		query.setVisibility(VisibilityKind.PUBLIC);
		query.setName(serviceName);
		query.setType(parseWhileAqlTypeLiteral(getAqlTypeString(returnType)));
		org.eclipse.acceleo.Expression body = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpression();
		body.setAst(parseWhileAqlExpression("null"));
		query.setBody(body);
		int i = 0;
		for (IType type : argumentTypes) {
			final Variable parameter = AcceleoPackage.eINSTANCE.getAcceleoFactory().createVariable();
			parameter.setName("parameter" + i++);
			parameter.setType(parseWhileAqlTypeLiteral(getAqlTypeString(type)));
			query.getParameters().add(parameter);
		}

		final int offset = module.getAst().getEndPosition(module);
		final int line = module.getAst().getEndLine(module);
		final int column = module.getAst().getEndColumn(module);
		final String replacement;
		if (column != 0) {
			replacement = newLine + newLine + new AcceleoAstSerializer(newLine).serialize(query);
		} else {
			replacement = newLine + new AcceleoAstSerializer(newLine).serialize(query);
		}
		final IAstTextReplacement res = new AstTextReplacement(sourceURI, replacement, offset, line, column,
				offset, line, column);

		return res;
	}

	private IAstTextReplacement getTemplateReplacement(URI sourceURI, Module module, String serviceName,
			IType returnType, List<IType> argumentTypes) {
		final Template template = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTemplate();
		template.setVisibility(VisibilityKind.PUBLIC);
		template.setName(serviceName);
		int i = 0;
		for (IType type : argumentTypes) {
			final Variable parameter = AcceleoPackage.eINSTANCE.getAcceleoFactory().createVariable();
			parameter.setName("parameter" + i++);
			parameter.setType(parseWhileAqlTypeLiteral(getAqlTypeString(type)));
			template.getParameters().add(parameter);
		}
		Block body = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();
		template.setBody(body);

		final int offset = module.getAst().getEndPosition(module);
		final int line = module.getAst().getEndLine(module);
		final int column = module.getAst().getEndColumn(module);
		final String replacement;
		if (column != 0) {
			replacement = newLine + newLine + new AcceleoAstSerializer(newLine).serialize(template);
		} else {
			replacement = newLine + new AcceleoAstSerializer(newLine).serialize(template);
		}
		final IAstTextReplacement res = new AstTextReplacement(sourceURI, replacement, offset, line, column,
				offset, line, column);

		return res;
	}

	private IAstTextReplacement getServiceReplacement(URI sourceURI, Class<?> cls, String classContent,
			String serviceName, IType returnType, List<IType> argumentTypes) {

		final StringBuilder replacement = new StringBuilder();
		replacement.append("\tpublic ");
		try {
			if (cls.getConstructors().length == 0) {
				replacement.append("static ");
			}
		} catch (SecurityException e) {
			// nothing to do here
		}
		replacement.append(getJavaStringType(returnType));
		replacement.append(" ");
		replacement.append(serviceName);
		replacement.append("(");
		final StringJoiner joiner = new StringJoiner(AcceleoParser.COMMA + SPACE);
		int i = 0;
		for (IType type : argumentTypes) {
			joiner.add(getJavaStringType(type) + " parameter" + i++);
		}
		replacement.append(joiner.toString());
		replacement.append(") {" + newLine);
		replacement.append("\t\treturn null;" + newLine);
		replacement.append("\t}" + newLine);

		final int[][] classLinesAndColumns = AQLUtils.getLinesAndColumns(classContent);
		final int offset = classContent.lastIndexOf("}") - 1;
		final int line = classLinesAndColumns[offset][0];
		final int column = classLinesAndColumns[offset][1];
		final IAstTextReplacement res = new AstTextReplacement(sourceURI, replacement.toString(), offset,
				line, column, offset, line, column);

		return res;
	}

	/**
	 * Gets the Java {@link String} representation of the given {@link IType}.
	 * 
	 * @param type
	 * @return the Java {@link String} representation of the given {@link IType}
	 */
	private String getJavaStringType(IType type) {
		final String res;

		if (type instanceof ICollectionType) {
			res = ((ICollectionType)type).getType().getName() + "<" + getJavaStringType(
					((ICollectionType)type).getCollectionType()) + ">";
		} else if (type instanceof ClassType) {
			res = ((ClassType)type).getType().getSimpleName();
		} else if (type instanceof EClassifierType) {
			final EClassifier eClassifier = ((EClassifierType)type).getType();
			if (eClassifier.getInstanceClass() != null) {
				res = eClassifier.getInstanceClass().getSimpleName();
			} else {
				res = eClassifier.getName();
			}
		} else {
			throw new IllegalStateException("unknown type.");
		}

		return res;
	}

	private boolean isQueryModule(String qualifiedName) {
		return qualifiedName.contains(AcceleoParser.QUALIFIER_SEPARATOR + "requests"
				+ AcceleoParser.QUALIFIER_SEPARATOR);
	}

	private String getSignature(String serviceName, List<IType> argumentTypes) {
		final StringBuilder res = new StringBuilder();

		res.append(serviceName);
		res.append(AcceleoParser.OPEN_PARENTHESIS);
		final StringJoiner joiner = new StringJoiner(AcceleoParser.COMMA + SPACE);

		for (IType argumentType : argumentTypes) {
			joiner.add(argumentType.toString());
		}
		res.append(joiner.toString());
		res.append(AcceleoParser.CLOSE_PARENTHESIS);

		return res.toString();
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	protected AstResult parseWhileAqlExpression(String expression) {
		return AQLUtils.parseWhileAqlExpression(expression).getAstResult();
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	protected AstResult parseWhileAqlTypeLiteral(String expression) {
		return AQLUtils.parseWhileAqlTypeLiteral(expression);
	}

	/**
	 * Gets the AQL {@link String} representation of the given {@link IType}.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return the AQL {@link String} representation of the given {@link IType}
	 */
	protected String getAqlTypeString(IType type) {
		return AQLUtils.getAqlTypeString(type);
	}

}
