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
package org.eclipse.acceleo.query;

import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.ClassTypeLiteral;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.eclipse.acceleo.query.parser.QueryParser;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.ECrossReferenceAdapterCrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.impl.ResourceSetRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.services.configurator.IOptionProvider;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfigurator;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfiguratorDescriptor;
import org.eclipse.acceleo.query.services.configurator.IServicesConfigurator;
import org.eclipse.acceleo.query.services.configurator.IServicesConfiguratorDescriptor;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.CollectionType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AQLUtils {

	/**
	 * Adds the parsing end position to the {@link AstResult}. This can be useful when an {@link Expression}
	 * ends with a parenthesis expression that has no node in the AST.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class AcceleoAQLResult {

		/**
		 * The {@link AstResult}.
		 */
		private final AstResult astResult;

		/**
		 * The end position of the parsed expression.
		 */
		private final int endPosition;

		/**
		 * Constructor.
		 * 
		 * @param astResult
		 *            the {@link AstResult}
		 * @param endPosition
		 *            the end position of the parsed expression
		 */
		public AcceleoAQLResult(AstResult astResult, int endPosition) {
			super();
			this.astResult = astResult;
			this.endPosition = endPosition;
		}

		/**
		 * Gets the {@link AstResult}.
		 * 
		 * @return the {@link AstResult}
		 */
		public AstResult getAstResult() {
			return astResult;
		}

		/**
		 * Gets the expression end position.
		 * 
		 * @return the expression end position
		 */
		public int getEndPosition() {
			return endPosition;
		}

	}

	/**
	 * The {@link List} of {@link #registerServicesConfigurator(IServicesConfiguratorDescriptor) registered}
	 * {@link IServicesConfiguratorDescriptor}.
	 */
	private static final Map<String, List<IServicesConfiguratorDescriptor>> SERVICES_CONFIGURATORS = new LinkedHashMap<String, List<IServicesConfiguratorDescriptor>>();

	/**
	 * The {@link List} of {@link #registerServicesConfigurator(IResourceSetConfiguratorDescriptor)
	 * registered} {@link IResourceSetConfiguratorDescriptor}.
	 */
	private static final List<IResourceSetConfiguratorDescriptor> RESOURCE_SET_CONFIGURATORS = new ArrayList<>();

	/**
	 * The install {@link ECrossReferenceAdapter} option.
	 */
	public static final String INSTALL_CROSS_REFERENCE_ADAPTER_OPTION = "InstallCrossReferenceAdapter";

	/**
	 * The install {@link ECrossReferenceAdapter} option.
	 */
	public static final String BASE_URI_OPTION = "BaseURI";

	/**
	 * The install {@link ECrossReferenceAdapter} option.
	 */
	public static final String PROPERTIES_URIS_OPTION = "PropertiesURIs";

	/**
	 * The AQL language name for {@link IServicesConfigurator}.
	 */
	public static final String AQL_LANGUAGE = "org.eclipse.acceleo.query";

	/**
	 * The mapping from {@link Object key} to installed {@link ECrossReferenceAdapter}.
	 */
	private static final Map<Object, ECrossReferenceAdapter> CROSS_REFERENCE_ADAPTERS = new HashMap<>();

	/**
	 * Constructor.
	 */
	private AQLUtils() {
		// nothing to do here
	}

	/**
	 * Computes the {@link List} of available types for the given {@link List} of registered
	 * {@link EPackage#getNsURI() nsURI}.
	 * 
	 * @param uris
	 *            the {@link List} of registered {@link EPackage#getNsURI() nsURI}
	 * @return the {@link List} of available types for the given {@link List} of registered
	 *         {@link EPackage#getNsURI() nsURI}
	 */
	public static List<String> computeAvailableTypes(List<String> uris, boolean includePrimitiveTypes,
			boolean includeSequenceTypes, boolean includeSetTypes) {
		final Set<String> types = new HashSet<>();

		if (includePrimitiveTypes) {
			types.add(AstSerializer.STRING_TYPE);
			types.add(AstSerializer.INTEGER_TYPE);
			types.add(AstSerializer.REAL_TYPE);
			types.add(AstSerializer.BOOLEAN_TYPE);
		}

		if (uris != null) {
			for (String nsURI : uris) {
				final EPackage ePkg = EPackageRegistryImpl.INSTANCE.getEPackage(nsURI);
				if (ePkg != null) {
					types.addAll(getEClassifiers(ePkg));
				}
			}
		}

		final List<String> res = new ArrayList<>(types.size() * 3);
		for (String type : types) {
			res.add(type);
			if (includeSequenceTypes) {
				res.add("Sequence(" + type + ")");
			}
			if (includeSetTypes) {
				res.add("OrderedSet(" + type + ")");
			}
		}
		Collections.sort(res);

		return res;
	}

	/**
	 * Gets the {@link List} of all classifiers in the given {@link EPackage}.
	 * 
	 * @param ePkg
	 *            the {@link EPackage}
	 * @return the {@link List} of all classifiers in the given {@link EPackage}
	 */
	private static List<String> getEClassifiers(EPackage ePkg) {
		final List<String> res = new ArrayList<>();

		for (EClassifier eClassifier : ePkg.getEClassifiers()) {
			res.add(ePkg.getName() + "::" + eClassifier.getName());
		}
		for (EPackage child : ePkg.getESubpackages()) {
			res.addAll(getEClassifiers(child));
		}

		return res;
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AcceleoAQLResult}
	 */
	public static AcceleoAQLResult parseWhileAqlExpression(String expression) {
		final AcceleoAQLResult result;

		if (expression != null && expression.length() > 0) {
			AstBuilderListener astBuilder = new AstBuilderListener();
			CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
			QueryLexer lexer = new QueryLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			lexer.removeErrorListeners();
			lexer.addErrorListener(astBuilder.getErrorListener());
			TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
			QueryParser parser = new QueryParser(tokens);
			parser.addParseListener(astBuilder);
			parser.removeErrorListeners();
			parser.addErrorListener(astBuilder.getErrorListener());
			// parser.setTrace(true);
			parser.expression();
			result = new AcceleoAQLResult(astBuilder.getAstResult(), rewindWhiteSpaces(expression, parser
					.getCurrentToken().getStartIndex()));

		} else {
			org.eclipse.acceleo.query.ast.ErrorExpression errorExpression = (org.eclipse.acceleo.query.ast.ErrorExpression)EcoreUtil
					.create(AstPackage.eINSTANCE.getErrorExpression());
			List<org.eclipse.acceleo.query.ast.Error> aqlErrors = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			aqlErrors.add(errorExpression);
			final Positions<ASTNode> aqlPositions = new Positions<>();
			if (expression != null) {
				aqlPositions.setIdentifierStartPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierStartLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierStartColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndColumns(errorExpression, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"missing expression", new Object[] {errorExpression }));
			result = new AcceleoAQLResult(new AstResult(errorExpression, aqlPositions, aqlErrors, diagnostic),
					0);
		}

		return result;
	}

	/**
	 * Get the position after rewinding white spaces.
	 * 
	 * @param text
	 *            the input {@link String}
	 * @param position
	 *            the current position
	 * @return the position after rewinding white spaces
	 */
	private static int rewindWhiteSpaces(String text, int position) {
		final int res;
		if (text != null && !text.isEmpty()) {
			int index = position - 1;
			if (Character.isWhitespace(text.charAt(index))) {
				do {
					index--;
				} while (index >= 0 && Character.isWhitespace(text.charAt(index)));
				res = index + 1;
			} else {
				res = position;
			}
		} else {
			res = position;
		}

		return res;
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	public static AstResult parseWhileAqlTypeLiteral(String expression) {
		final AstResult result;

		if (expression != null && expression.length() > 0) {
			AstBuilderListener astBuilder = new AstBuilderListener();
			CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
			QueryLexer lexer = new QueryLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			lexer.removeErrorListeners();
			lexer.addErrorListener(astBuilder.getErrorListener());
			TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
			QueryParser parser = new QueryParser(tokens);
			parser.addParseListener(astBuilder);
			parser.removeErrorListeners();
			parser.addErrorListener(astBuilder.getErrorListener());
			// parser.setTrace(true);
			parser.typeLiteral();
			result = astBuilder.getAstResult();
		} else {
			ErrorTypeLiteral errorTypeLiteral = (ErrorTypeLiteral)EcoreUtil.create(AstPackage.eINSTANCE
					.getErrorTypeLiteral());
			List<org.eclipse.acceleo.query.ast.Error> errs = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			errs.add(errorTypeLiteral);
			final Positions<ASTNode> aqlPositions = new Positions<>();
			if (expression != null) {
				aqlPositions.setIdentifierStartPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierStartLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierStartColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndColumns(errorTypeLiteral, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"missing type literal", new Object[] {errorTypeLiteral }));
			result = new AstResult(errorTypeLiteral, aqlPositions, errs, diagnostic);
		}

		return result;
	}

	/**
	 * Gets the AQL {@link String} representation of the given {@link IType}.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return the AQL {@link String} representation of the given {@link IType}
	 */
	public static String getAqlTypeString(IType type) {
		final StringBuilder res = new StringBuilder();

		if (type instanceof EClassifierType) {
			res.append(((EClassifierType)type).getType().getEPackage().getName());
			res.append(AstSerializer.ECORE_SEPARATOR);
			res.append(((EClassifierType)type).getType().getName());
		} else if (type instanceof SequenceType) {
			res.append("Sequence(");
			res.append(getAqlTypeString(((SequenceType)type).getCollectionType()));
			res.append(")");
		} else if (type instanceof EClassifierSetLiteralType) {
			res.append("{");
			final StringJoiner joiner = new StringJoiner(" | ");
			for (EClassifier eClassifier : ((EClassifierSetLiteralType)type).getEClassifiers()) {
				joiner.add(eClassifier.getEPackage().getName() + AstSerializer.ECORE_SEPARATOR + eClassifier
						.getName());
			}
			res.append(joiner.toString());
			res.append("}");
		} else if (type instanceof SetType) {
			res.append("OrderedSet(");
			res.append(getAqlTypeString(((SetType)type).getCollectionType()));
			res.append(")");
		} else if (type instanceof ClassType) {
			final Class<?> cls = ((ClassType)type).getType();
			if (cls == Double.class) {
				res.append(AstSerializer.REAL_TYPE);
			} else {
				res.append(cls.getSimpleName());
			}
		}

		return res.toString();
	}

	/**
	 * Gets the AQL {@link String} representation of the given {@link Set} of {@link IType}.
	 * 
	 * @param types
	 *            the {@link Set} of {@link IType}
	 * @return the AQL {@link String} representation of the given {@link Set} of {@link IType}
	 */
	public static String getAqlTypeString(Set<IType> types) {
		final StringBuilder res = new StringBuilder();

		if (types != null && !types.isEmpty()) {
			if (types.size() == 1) {
				res.append(getAqlTypeString(types.iterator().next()));
			} else {
				final StringJoiner joiner = new StringJoiner(" | ", "{", "}");
				for (IType type : types) {
					joiner.add(getAqlTypeString(type));
				}
				res.append(joiner.toString());
			}
		}

		return res.toString();
	}

	/**
	 * Gets the line number and the column number for each offsets of the given text.
	 * 
	 * @param text
	 *            the String
	 * @return the line number and the column number for each offsets of the given text
	 */
	public static int[][] getLinesAndColumns(String text) {
		final int[][] res = new int[text.length()][2];
		try (LineNumberReader reader = new LineNumberReader(new StringReader(text))) {
			int column = 0;
			for (int i = 0; i < res.length; i++) {
				final int previousLine = reader.getLineNumber();
				reader.read();
				if (previousLine != reader.getLineNumber()) {
					column = 0;
				} else {
					column++;
				}
				res[i][0] = reader.getLineNumber();
				res[i][1] = column;
			}
		} catch (IOException e) {
			// can't happen: we are reading from memory
		}
		return res;
	}

	/**
	 * Gets the {@link List} of registered {@link IServicesConfigurator}.
	 * 
	 * @param language
	 *            the language name
	 * @return the {@link List} of {@link #registerServicesConfigurator(IServicesConfiguratorDescriptor)
	 *         registered} {@link IServicesConfigurator}
	 */
	public static List<IServicesConfigurator> getServicesConfigurators(String language) {
		final List<IServicesConfigurator> res = new ArrayList<>();

		synchronized(SERVICES_CONFIGURATORS) {
			List<IServicesConfiguratorDescriptor> configurators = SERVICES_CONFIGURATORS.get(language);
			if (configurators != null) {
				synchronized(configurators) {
					for (IServicesConfiguratorDescriptor descriptor : configurators) {
						final IServicesConfigurator configurator = descriptor.getServicesConfigurator();
						if (configurator != null) {
							res.add(configurator);
						}
					}
				}
			}
		}

		return res;
	}

	/**
	 * Registers the given {@link IServicesConfiguratorDescriptor}.
	 * 
	 * @param configurator
	 *            the {@link IServicesConfiguratorDescriptor} to register
	 */
	public static void registerServicesConfigurator(IServicesConfiguratorDescriptor configurator) {
		if (configurator != null) {
			synchronized(SERVICES_CONFIGURATORS) {
				final List<IServicesConfiguratorDescriptor> configurators = SERVICES_CONFIGURATORS
						.computeIfAbsent(configurator.getLanguage(),
								l -> new ArrayList<IServicesConfiguratorDescriptor>());
				synchronized(configurators) {
					configurators.add(configurator);
				}
			}
		}
	}

	/**
	 * Unregister the given {@link IServicesConfiguratorDescriptor}.
	 * 
	 * @param configuratorDescriptor
	 *            the {@link IServicesConfiguratorDescriptor} to unregister
	 */
	public static void unregisterServicesConfigurator(
			IServicesConfiguratorDescriptor configuratorDescriptor) {
		if (configuratorDescriptor != null) {
			synchronized(SERVICES_CONFIGURATORS) {
				final List<IServicesConfiguratorDescriptor> configurators = SERVICES_CONFIGURATORS.get(
						configuratorDescriptor.getLanguage());
				if (configurators != null) {
					synchronized(configurators) {
						if (configurators.remove(configuratorDescriptor) && configurators.isEmpty()) {
							SERVICES_CONFIGURATORS.remove(configuratorDescriptor.getLanguage());
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the {@link List} of registered {@link IResourceSetConfigurator}.
	 * 
	 * @return the {@link List} of {@link #registerResourceSetConfigurator(IResourceSetConfiguratorDescriptor)
	 *         registered} {@link IResourceSetConfigurator}
	 */
	public static List<IResourceSetConfigurator> getResourceSetConfigurators() {
		final List<IResourceSetConfigurator> res = new ArrayList<>();

		synchronized(RESOURCE_SET_CONFIGURATORS) {
			for (IResourceSetConfiguratorDescriptor descriptor : RESOURCE_SET_CONFIGURATORS) {
				final IResourceSetConfigurator configurator = descriptor.getResourceSetConfigurator();
				if (configurator != null) {
					res.add(configurator);
				}
			}
		}

		return res;
	}

	/**
	 * Registers the given {@link IResourceSetConfiguratorDescriptor}.
	 * 
	 * @param configurator
	 *            the {@link IResourceSetConfiguratorDescriptor} to register
	 */
	public static void registerResourceSetConfigurator(IResourceSetConfiguratorDescriptor configurator) {
		if (configurator != null) {
			synchronized(RESOURCE_SET_CONFIGURATORS) {
				RESOURCE_SET_CONFIGURATORS.add(configurator);
			}
		}
	}

	/**
	 * Unregister the given {@link IResourceSetConfiguratorDescriptor}.
	 * 
	 * @param configuratorDescriptor
	 *            the {@link IResourceSetConfiguratorDescriptor} to unregister
	 */
	public static void unregisterResourceSetConfigurator(
			IResourceSetConfiguratorDescriptor configuratorDescriptor) {
		if (configuratorDescriptor != null) {
			synchronized(RESOURCE_SET_CONFIGURATORS) {
				RESOURCE_SET_CONFIGURATORS.remove(configuratorDescriptor);
			}
		}
	}

	/**
	 * Create a new {@link ResourceSet} suitable for loading {@link EObject} from the given options.
	 * 
	 * @param exceptions
	 *            the {@link List} of resulting exceptions (filled by this method)
	 * @param key
	 *            the {@link Object} key
	 * @param defaultResourceSet
	 *            the default {@link ResourceSet} to use if none is created
	 * @param options
	 *            the {@link Map} of existing options.
	 * @return the {@link ResourceSet} suitable for loading {@link EObject} from the given options if any, the
	 *         default {@link ResourceSet} otherwise
	 * @see #cleanResourceSetForModels(Object)
	 */
	public static ResourceSet createResourceSetForModels(List<Exception> exceptions, Object key,
			ResourceSet defaultResourceSet, Map<String, String> options) {
		ResourceSet res = null;

		for (IResourceSetConfigurator configurator : getResourceSetConfigurators()) {
			try {
				res = configurator.createResourceSetForModels(key, options);
				if (res != null) {
					break;
				}
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				exceptions.add(e);
			}
		}

		if (res == null) {
			res = defaultResourceSet;
		}

		if (Boolean.valueOf(options.get(INSTALL_CROSS_REFERENCE_ADAPTER_OPTION))) {
			final ECrossReferenceAdapter adapter = new ECrossReferenceAdapter();
			adapter.setTarget(res);
			res.eAdapters().add(adapter);
			CROSS_REFERENCE_ADAPTERS.put(key, adapter);
		}

		return res;
	}

	/**
	 * Cleans the {@link #createResourceSetForModels(List, Object, ResourceSet, Map) created}
	 * {@link ResourceSet} for the given {@link Object} key.
	 * 
	 * @param key
	 *            the {@link Object} key
	 * @param resourceSet
	 *            the {@link ResourceSet} created using
	 *            {@link #createResourceSetForModels(List, Object, ResourceSet, Map)}
	 */
	public static void cleanResourceSetForModels(Object key, ResourceSet resourceSet) {
		final ECrossReferenceAdapter adapter = CROSS_REFERENCE_ADAPTERS.remove(key);
		if (adapter != null) {
			adapter.unsetTarget(resourceSet);
			resourceSet.eAdapters().remove(adapter);
		}
		for (IResourceSetConfigurator configurator : getResourceSetConfigurators()) {
			configurator.cleanResourceSetForModels(key);
		}
	}

	/**
	 * Gets the {@link Map} of initialized options.
	 * 
	 * @param language
	 *            the language name
	 * @param options
	 *            the {@link Map} of existing options.
	 * @return the {@link Map} of initialized options
	 */
	public static Map<String, String> getInitializedOptions(String language, Map<String, String> options) {
		final Map<String, String> res = new LinkedHashMap<>();

		for (IOptionProvider provider : getOptionProviders(AQL_LANGUAGE)) {
			res.putAll(provider.getInitializedOptions(options));
		}
		for (IOptionProvider provider : getOptionProviders(language)) {
			res.putAll(provider.getInitializedOptions(options));
		}

		return res;
	}

	/**
	 * Gets the {@link Map} of initialized options from the given EObject.
	 * 
	 * @param language
	 *            the language name
	 * @param options
	 *            the {@link Map} of existing options.
	 * @param eObj
	 *            the {@link EObject}
	 * @return the {@link Map} of initialized options
	 */
	public static Map<String, String> getInitializedOptions(String language, Map<String, String> options,
			EObject eObj) {
		final Map<String, String> res = new LinkedHashMap<>();

		for (IOptionProvider provider : getOptionProviders(AQL_LANGUAGE)) {
			res.putAll(provider.getInitializedOptions(options));
		}
		for (IOptionProvider provider : getOptionProviders(language)) {
			res.putAll(provider.getInitializedOptions(options));
		}

		return res;
	}

	/**
	 * Gets the {@link List} of possible option names.
	 * 
	 * @param language
	 *            the language name
	 * @return the {@link List} of possible option names
	 */
	public static Set<String> getPossibleOptionNames(String language) {
		final Set<String> res = new LinkedHashSet<>();

		for (IOptionProvider provider : getOptionProviders(AQL_LANGUAGE)) {
			res.addAll(provider.getOptions());
		}
		for (IOptionProvider provider : getOptionProviders(language)) {
			res.addAll(provider.getOptions());
		}

		res.add(INSTALL_CROSS_REFERENCE_ADAPTER_OPTION);

		return res;
	}

	/**
	 * Gets the {@link List} of {@link IOptionProvider}.
	 * 
	 * @param language
	 *            the language name
	 * @return the {@link List} of {@link IOptionProvider}
	 */
	private static List<IOptionProvider> getOptionProviders(String language) {
		final List<IOptionProvider> res = new ArrayList<>();

		res.addAll(getServicesConfigurators(AQL_LANGUAGE));
		res.addAll(getServicesConfigurators(language));
		res.addAll(getResourceSetConfigurators());

		return res;
	}

	/**
	 * Creates a new {@link IQualifiedNameQueryEnvironment} for the given language name and options.
	 * 
	 * @param language
	 *            the language name
	 * @param options
	 *            the {@link Map} of options
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for models
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @return a new {@link IQualifiedNameQueryEnvironment} for the given language name and options
	 * @see #cleanServices(String, IReadOnlyQueryEnvironment, ResourceSet)
	 */
	public static IQualifiedNameQueryEnvironment newQualifiedNameEnvironmentDefaultServices(String language,
			Map<String, String> options, IQualifiedNameResolver resolver, ResourceSet resourceSetForModels,
			boolean forWorkspace) {
		final ECrossReferenceAdapterCrossReferenceProvider crossReferenceProvider = new ECrossReferenceAdapterCrossReferenceProvider(
				ECrossReferenceAdapter.getCrossReferenceAdapter(resourceSetForModels));
		final ResourceSetRootEObjectProvider rootProvider = new ResourceSetRootEObjectProvider(
				resourceSetForModels);

		final Properties properties = getProperties(resourceSetForModels.getURIConverter(), options);

		final IQualifiedNameQueryEnvironment queryEnvironment = Query
				.newQualifiedNameEnvironmentWithDefaultServices(resolver, crossReferenceProvider,
						rootProvider, properties, forWorkspace);

		for (IServicesConfigurator configurator : getServicesConfigurators(AQL_LANGUAGE)) {
			ServiceUtils.registerServices(queryEnvironment, configurator.getServices(queryEnvironment,
					resourceSetForModels, options, forWorkspace));
		}
		for (IServicesConfigurator configurator : getServicesConfigurators(language)) {
			ServiceUtils.registerServices(queryEnvironment, configurator.getServices(queryEnvironment,
					resourceSetForModels, options, forWorkspace));
		}

		return queryEnvironment;
	}

	/**
	 * Gets the {@link Properties} from the given options using the given {@link URIConverter}.
	 * 
	 * @param uriConverter
	 *            the {@link URIConverter}
	 * @param options
	 *            the map of options
	 * @return the {@link Properties} from the given options using the given {@link URIConverter}
	 */
	private static Properties getProperties(final URIConverter uriConverter, Map<String, String> options) {
		final Properties properties = new Properties();
		final String propertiesURIsOption = options.get(PROPERTIES_URIS_OPTION);
		if (propertiesURIsOption != null) {
			final String[] uris = propertiesURIsOption.split(",");
			final String baseURIString = options.get(BASE_URI_OPTION);
			final URI baseURI;
			if (baseURIString != null) {
				baseURI = URI.createURI(baseURIString, true);
			} else {
				baseURI = null;
			}
			for (String uriString : uris) {
				URI uri = URI.createURI(uriString, true);
				if (uri.isRelative() && baseURI != null) {
					uri = uri.resolve(baseURI);
				}
				try (final InputStream is = uriConverter.createInputStream(uri)) {
					properties.load(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	/**
	 * Cleans the services for the given language and {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @param language
	 *            the language name
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for models
	 */
	public static void cleanServices(String language, IReadOnlyQueryEnvironment queryEnvironment,
			ResourceSet resourceSetForModels) {
		for (IServicesConfigurator configurator : getServicesConfigurators(AQL_LANGUAGE)) {
			configurator.cleanServices(queryEnvironment, resourceSetForModels);
		}
		for (IServicesConfigurator configurator : getServicesConfigurators(language)) {
			configurator.cleanServices(queryEnvironment, resourceSetForModels);
		}
	}

	/**
	 * Gets the {@link Set} of {@link IType} for the given {@link TypeLiteral}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param typeExpression
	 *            the {@link Expression}
	 * @return the {@link Set} of {@link IType} for the given {@link TypeLiteral}
	 */
	public static Set<IType> getTypes(IReadOnlyQueryEnvironment queryEnvironment,
			final TypeLiteral typeExpression) {
		final Set<IType> res = new LinkedHashSet<>();

		if (typeExpression instanceof EClassifierTypeLiteral) {
			final EClassifierTypeLiteral typeLiteral = (EClassifierTypeLiteral)typeExpression;
			for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getTypes(typeLiteral
					.getEPackageName(), typeLiteral.getEClassifierName())) {
				res.add(new EClassifierType(queryEnvironment, eClassifier));
			}
		} else if (typeExpression instanceof TypeSetLiteral) {
			final TypeSetLiteral typeLiteral = (TypeSetLiteral)typeExpression;
			for (TypeLiteral type : typeLiteral.getTypes()) {
				if (type instanceof EClassifierTypeLiteral) {
					for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getTypes(
							((EClassifierTypeLiteral)type).getEPackageName(), ((EClassifierTypeLiteral)type)
									.getEClassifierName())) {
						res.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				}
			}
		} else if (typeExpression instanceof CollectionTypeLiteral) {
			for (IType elementType : getTypes(queryEnvironment, ((CollectionTypeLiteral)typeExpression)
					.getElementType())) {
				if (List.class.isAssignableFrom(((CollectionTypeLiteral)typeExpression).getValue())) {
					res.add(new SequenceType(queryEnvironment, elementType));
				} else if (List.class.isAssignableFrom(((CollectionTypeLiteral)typeExpression).getValue())) {
					res.add(new SetType(queryEnvironment, elementType));
				} else {
					res.add(new CollectionType(queryEnvironment, elementType));
				}
			}
		} else if (typeExpression instanceof ClassTypeLiteral) {
			res.add(new ClassType(queryEnvironment, ((ClassTypeLiteral)typeExpression).getValue()));
		}

		return res;
	}

}
