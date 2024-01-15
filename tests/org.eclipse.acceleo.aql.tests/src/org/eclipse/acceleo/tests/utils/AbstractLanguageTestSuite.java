/*******************************************************************************
 *  Copyright (c) 2016, 2024 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.aql.validation.quickfixes.AcceleoQuickFixesSwitch;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.parser.quickfixes.IAstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.IAstResourceChange;
import org.eclipse.acceleo.query.parser.quickfixes.IAstTextReplacement;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Run a folder with templates as a test suite JUnit.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public abstract class AbstractLanguageTestSuite {

	/**
	 * Copy buffer size.
	 */
	private static final int BUFFER_SIZE = 8192;

	/**
	 * UTF-8 content.
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * The default EOL character to use.
	 */
	public static final char DEFAULT_END_OF_LINE_CHARACTER = '\n';

	/**
	 * The {@link MemoryURIHandler} that check we don't have adherence to {@link File}.
	 */
	private static MemoryURIHandler uriHandler = new MemoryURIHandler();

	/**
	 * The {@link AcceleoAstResult}.
	 */
	protected final AcceleoAstResult astResult;

	/**
	 * The {@link AcceleoAstResult} for MS Windows end line (\r\n).
	 */
	protected final AcceleoAstResult astResultWindowsEndLine;

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	protected static IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link IQualifiedNameQueryEnvironment} for MS Windows end line (\r\n).
	 */
	protected static IQualifiedNameQueryEnvironment queryEnvironmentWindowsEndLine;

	/**
	 * The {@link ResourceSet} for models.
	 */
	protected static ResourceSet resourceSetForModels;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	protected final AcceleoEvaluator evaluator;

	/**
	 * The {@link AcceleoEvaluator} for MS Windows end line (\r\n).
	 */
	protected final AcceleoEvaluator evaluatorWindowsEndLine;

	/**
	 * The memory destination {@link String}.
	 */
	protected final String memoryDestinationString;

	/**
	 * The memoty destination {@link URI}.
	 */
	protected final URI memoryDestination;

	/**
	 * The {@link ModuleAstSerializer}.
	 */
	private final ModuleAstSerializer moduleAstSerializer = new ModuleAstSerializer();

	/**
	 * The test folder path.
	 */
	private final String testFolderPath;

	/**
	 * The module qualified name.
	 */
	private final String qualifiedName;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private final IAcceleoValidationResult validationResult;

	/**
	 * The {@link IAcceleoValidationResult} for MS Windows end line (\r\n).
	 */
	private final IAcceleoValidationResult validationResultWindowsEndLine;

	/**
	 * The {@link Module} text.
	 */
	private final String moduleText;

	/**
	 * The {@link Module} text for MS Windows end line (\r\n).
	 */
	private final String moduleTextWindowsEndLine;

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public AbstractLanguageTestSuite(String testFolder) throws IOException {
		this.memoryDestinationString = "acceleotests://" + testFolder + "/";
		this.memoryDestination = URI.createURI(memoryDestinationString);
		this.testFolderPath = testFolder;
		final File testFolderFile = new File(testFolderPath);
		final File moduleFile = getModuleFile(testFolderFile);

		try (InputStream is = new FileInputStream(moduleFile)) {
			moduleText = AcceleoUtil.getContent(is, StandardCharsets.UTF_8.name());
		}

		try (InputStream is = new FileInputStream(moduleFile)) {
			moduleTextWindowsEndLine = AcceleoUtil.getContent(is, StandardCharsets.UTF_8.name()).replaceAll(
					"\n", "\r\n");
		}

		final Path rootPath = testFolderFile.toPath().getName(0);
		final URL[] urls = new URL[] {testFolderFile.toPath().getName(0).toUri().toURL() };

		final ClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
		final IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(classLoader,
				AcceleoParser.QUALIFIER_SEPARATOR);
		final IQualifiedNameResolver resolverWindowsEndLine = new ClassLoaderQualifiedNameResolver(
				classLoader, AcceleoParser.QUALIFIER_SEPARATOR);

		// TODO get options form ??? or list all possible options ?
		// don't add any options ?
		final Map<String, String> options = new LinkedHashMap<>();
		final ArrayList<Exception> exceptions = new ArrayList<>();
		// the ResourceSet will not be used
		resourceSetForModels = AQLUtils.createResourceSetForModels(exceptions, this, getResourceSet(),
				options);
		// TODO report exceptions

		queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(options, resolver, resourceSetForModels,
				true);
		queryEnvironmentWindowsEndLine = AcceleoUtil.newAcceleoQueryEnvironment(options,
				resolverWindowsEndLine, resourceSetForModels, true);

		evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine(), "\n");
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(new JavaLoader(AcceleoParser.QUALIFIER_SEPARATOR, false));

		// We generate with Unix end line even if the module source has been converted to Windows end line
		evaluatorWindowsEndLine = new AcceleoEvaluator(queryEnvironmentWindowsEndLine.getLookupEngine(),
				"\n");
		resolverWindowsEndLine.addLoader(new ModuleLoaderWindowsEndLine(new AcceleoParser(),
				evaluatorWindowsEndLine));
		resolverWindowsEndLine.addLoader(new JavaLoader(AcceleoParser.QUALIFIER_SEPARATOR, false));

		String namespace = rootPath.relativize(testFolderFile.toPath()).toString().replace(File.separator,
				"::") + "::";
		qualifiedName = namespace + moduleFile.getName().substring(0, moduleFile.getName().lastIndexOf('.'));

		final Object resolved = resolver.resolve(qualifiedName);
		if (resolved instanceof Module) {
			astResult = ((Module)resolved).getAst();
		} else {
			astResult = null;
		}

		final Object resolvedWindowsEndLine = resolverWindowsEndLine.resolve(qualifiedName);
		if (resolvedWindowsEndLine instanceof Module) {
			astResultWindowsEndLine = ((Module)resolvedWindowsEndLine).getAst();
		} else {
			astResultWindowsEndLine = null;
		}

		final AcceleoValidator validator = new AcceleoValidator(queryEnvironment);
		validationResult = validator.validate(astResult, qualifiedName);

		final AcceleoValidator validatorWindowsEndLine = new AcceleoValidator(queryEnvironmentWindowsEndLine);
		validationResultWindowsEndLine = validatorWindowsEndLine.validate(astResultWindowsEndLine,
				qualifiedName);
	}

	@After
	public void after() {
		uriHandler.clear();
	}

	@AfterClass
	public static void afterClass() {
		AQLUtils.cleanResourceSetForModels(queryEnvironment.getLookupEngine().getResolver(),
				resourceSetForModels);
		AcceleoUtil.cleanServices(queryEnvironment, resourceSetForModels);

		AQLUtils.cleanResourceSetForModels(queryEnvironmentWindowsEndLine.getLookupEngine().getResolver(),
				resourceSetForModels);
		AcceleoUtil.cleanServices(queryEnvironmentWindowsEndLine, resourceSetForModels);
	}

	/**
	 * Gets the test folder path.
	 * 
	 * @return the test folder path
	 */
	protected String getTestFolderPath() {
		return testFolderPath;
	}

	/**
	 * Gets the {@link ResourceSet}.
	 * 
	 * @return the {@link ResourceSet}
	 */
	protected ResourceSet getResourceSet() {
		ResourceSetImpl res = new ResourceSetImpl();

		res.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		res.getURIConverter().getURIHandlers().add(0, uriHandler);

		return res;
	}

	/**
	 * Tests the parsing by comparing a textual representation of the AST.
	 * 
	 * @throws IOException
	 *             if the expected AST file can't be read
	 * @throws FileNotFoundException
	 *             if the expected AST file can't be found
	 */
	@Test
	public void parsing() throws FileNotFoundException, IOException {
		final File expectedASTFile = getExpectedASTFile(new File(testFolderPath));
		final String actualAst = moduleAstSerializer.serialize(astResult);
		if (!expectedASTFile.exists()) {
			final File actualASTFile = getActualASTFile(new File(testFolderPath));
			if (!actualASTFile.exists()) {
				actualASTFile.createNewFile();
			}
			setContent(new FileOutputStream(actualASTFile), UTF_8, actualAst);
			fail("file doesn't exist.");
		}
		try (FileInputStream stream = new FileInputStream(expectedASTFile)) {
			final String expectedAst = AcceleoUtil.getContent(stream, UTF_8);
			assertEquals(expectedAst, actualAst);
		}
	}

	/**
	 * Tests the parsing by comparing a textual representation of the AST for MS Windows end line (\r\n).
	 * 
	 * @throws IOException
	 *             if the expected AST file can't be read
	 * @throws FileNotFoundException
	 *             if the expected AST file can't be found
	 */
	@Test
	public void parsingWindowsEndLine() throws FileNotFoundException, IOException {
		final File expectedASTFileWindowsEndLine = getExpectedASTFileWindowsEndLine(new File(testFolderPath));
		final String actualAst = moduleAstSerializer.serialize(astResultWindowsEndLine);
		if (!expectedASTFileWindowsEndLine.exists()) {
			final File actualASTFileWindowsEndLine = getActualASTFileWindowsEndLine(new File(testFolderPath));
			if (!actualASTFileWindowsEndLine.exists()) {
				actualASTFileWindowsEndLine.createNewFile();
			}
			setContent(new FileOutputStream(actualASTFileWindowsEndLine), UTF_8, actualAst);
			fail("file doesn't exist.");
		}
		try (FileInputStream stream = new FileInputStream(expectedASTFileWindowsEndLine)) {
			final String expectedAst = AcceleoUtil.getContent(stream, UTF_8);
			assertEquals(expectedAst, actualAst);
		}
	}

	/**
	 * Tests the validation of a template.
	 * 
	 * @throws FileNotFoundException
	 *             if the file can't be found
	 * @throws IOException
	 *             if the given stream can't be written to
	 */
	@Test
	public void validation() throws FileNotFoundException, IOException {
		final List<IValidationMessage> messages = validationResult.getValidationMessages();
		final String actualContent = getValidationContent(messages);
		final File expectedFile = getExpectedValidationFile(new File(testFolderPath));
		final File actualFile = getActualValidationFile(new File(testFolderPath));

		if (!expectedFile.exists()) {
			if (!actualFile.exists() && !expectedFile.exists()) {
				actualFile.createNewFile();
			}
			try (FileOutputStream stream = new FileOutputStream(actualFile);) {
				setContent(stream, UTF_8, actualContent);
			}
			fail("file doesn't exist.");
		} else {
			String expectedContent = "";
			try (FileInputStream stream = new FileInputStream(expectedFile);) {
				expectedContent = AcceleoUtil.getContent(stream, UTF_8);
			}
			assertEquals(expectedContent, actualContent);
		}
	}

	/**
	 * Tests the validation of a template for MS Windows end line (\r\n).
	 * 
	 * @throws FileNotFoundException
	 *             if the file can't be found
	 * @throws IOException
	 *             if the given stream can't be written to
	 */
	@Test
	public void validationWindowsEndLine() throws FileNotFoundException, IOException {
		final List<IValidationMessage> messages = validationResultWindowsEndLine.getValidationMessages();
		final String actualContent = getValidationContent(messages);
		final File expectedFileWindowsEndLine = getExpectedValidationFileWindowsEndLine(new File(
				testFolderPath));
		final File actualFileWindowsEndLine = getActualValidationFileWindowsEndLine(new File(testFolderPath));

		if (!expectedFileWindowsEndLine.exists()) {
			if (!actualFileWindowsEndLine.exists() && !expectedFileWindowsEndLine.exists()) {
				actualFileWindowsEndLine.createNewFile();
			}
			try (FileOutputStream stream = new FileOutputStream(actualFileWindowsEndLine);) {
				setContent(stream, UTF_8, actualContent);
			}
			fail("file doesn't exist.");
		} else {
			String expectedContent = "";
			try (FileInputStream stream = new FileInputStream(expectedFileWindowsEndLine);) {
				expectedContent = AcceleoUtil.getContent(stream, UTF_8);
			}
			assertEquals(expectedContent, actualContent);
		}
	}

	/**
	 * Tests the quick fixes.
	 * 
	 * @throws FileNotFoundException
	 *             if the file can't be found
	 * @throws IOException
	 *             if the given stream can't be written to
	 */
	@Test
	public void quickFixes() throws FileNotFoundException, IOException {
		final String actualContent = getQuickFixesContent(validationResult, "\n", moduleText);
		final File expectedFile = getExpectedQuickFixesFile(new File(testFolderPath));
		final File actualFile = getActualQuickFixesFile(new File(testFolderPath));

		if (!expectedFile.exists()) {
			if (!actualFile.exists() && !expectedFile.exists()) {
				actualFile.createNewFile();
			}
			try (FileOutputStream stream = new FileOutputStream(actualFile);) {
				setContent(stream, UTF_8, actualContent);
			}
			fail("file doesn't exist.");
		} else {
			String expectedContent = "";
			try (FileInputStream stream = new FileInputStream(expectedFile);) {
				expectedContent = AcceleoUtil.getContent(stream, UTF_8);
			}
			assertEquals(getPortableString(expectedContent), getPortableString(actualContent));
		}
	}

	/**
	 * Tests the quick fixes for MS Windows end line (\r\n).
	 * 
	 * @throws FileNotFoundException
	 *             if the file can't be found
	 * @throws IOException
	 *             if the given stream can't be written to
	 */
	@Test
	public void quickFixesWindowsEndLine() throws FileNotFoundException, IOException {
		final String actualContent = getQuickFixesContent(validationResultWindowsEndLine, "\r\n",
				moduleTextWindowsEndLine).replaceAll("\r\n", "\n");
		final File expectedFile = getExpectedQuickFixesFileWindowsEndLine(new File(testFolderPath));
		final File actualFile = getActualQuickFixesFileWindowsEndLine(new File(testFolderPath));

		if (!expectedFile.exists()) {
			if (!actualFile.exists() && !expectedFile.exists()) {
				actualFile.createNewFile();
			}
			try (FileOutputStream stream = new FileOutputStream(actualFile);) {
				setContent(stream, UTF_8, actualContent);
			}
			fail("file doesn't exist.");
		} else {
			String expectedContent = "";
			try (FileInputStream stream = new FileInputStream(expectedFile);) {
				expectedContent = AcceleoUtil.getContent(stream, UTF_8);
			}
			assertEquals(getPortableString(expectedContent), getPortableString(actualContent));
		}
	}

	/**
	 * Serializes the given {@link List} of {@link IValidationMessage}.
	 * 
	 * @param messages
	 *            the {@link List} of {@link IValidationMessage}
	 * @return the string representation of the given {@link List} of {@link IValidationMessage}
	 */
	private String getValidationContent(List<IValidationMessage> messages) {
		final String res;

		if (messages.size() != 0) {
			final StringBuilder builder = new StringBuilder();
			for (IValidationMessage message : messages) {
				builder.append(message.getLevel());
				builder.append(" ");
				builder.append(message.getMessage());
				builder.append(" - ");
				builder.append(message.getStartPosition());
				builder.append(" ");
				builder.append(message.getEndPosition());
				builder.append(DEFAULT_END_OF_LINE_CHARACTER);
			}
			res = builder.substring(0, builder.length() - 1);
		} else {
			res = "";
		}

		return res;
	}

	/**
	 * Serializes the {@link IAstQuickFix} for the given {@link IValidationResult}.
	 * 
	 * @param result
	 *            the {@link List} of {@link IValidationMessage}
	 * @param endLine
	 *            the end line {@link String}
	 * @param text
	 *            the module text
	 * @return the string representation of the {@link IAstQuickFix} for the given {@link IValidationResult}
	 */
	private String getQuickFixesContent(IAcceleoValidationResult result, String endLine, String text) {
		final String res;

		final List<EObject> eObjects = new ArrayList<>();
		final Module module = result.getAcceleoAstResult().getModule();
		eObjects.add(module);
		final Iterator<EObject> it = module.eAllContents();
		while (it.hasNext()) {
			eObjects.add(it.next());
		}

		final AcceleoQuickFixesSwitch quickFixesSwitch = new AcceleoQuickFixesSwitch(queryEnvironment, result,
				qualifiedName, text, endLine);

		final StringBuilder builder = new StringBuilder();
		for (EObject eObject : eObjects) {
			List<IAstQuickFix> quickFixes = quickFixesSwitch.getQuickFixes((ASTNode)eObject);
			if (!quickFixes.isEmpty()) {
				builder.append(eObject.eResource().getURIFragment(eObject));
				builder.append(DEFAULT_END_OF_LINE_CHARACTER);
				for (IAstQuickFix quickFix : quickFixes) {
					builder.append(quickFix.getName());
					builder.append(DEFAULT_END_OF_LINE_CHARACTER);
					for (IAstResourceChange resourceChange : quickFix.getResourceChanges()) {
						builder.append(resourceChange.toString());
						builder.append(DEFAULT_END_OF_LINE_CHARACTER);
					}
					for (IAstTextReplacement replacement : quickFix.getTextReplacements()) {
						builder.append(replacement.toString());
						builder.append(DEFAULT_END_OF_LINE_CHARACTER);
					}
				}
			}
		}
		if (builder.length() != 0) {
			res = builder.substring(0, builder.length() - 1);
		} else {
			res = builder.toString();
		}

		return res;
	}

	/**
	 * Gets the module file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the module file from the test folder path
	 */
	protected final File getModuleFile(File testFolder) {
		return getTemplateFileInternal(testFolder);
	}

	/**
	 * Gets the expected AST file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected AST file from the test folder path
	 */
	protected File getFragmentsFolder(File testFolder) {
		return new File(testFolder + File.separator + "fragments");
	}

	/**
	 * Gets the expected AST file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected AST file from the test folder path
	 */
	protected File getExpectedASTFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-ast.txt");
	}

	/**
	 * Gets the actual AST file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual AST file from the test folder path
	 */
	protected File getActualASTFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-ast.txt");
	}

	/**
	 * Gets the expected AST file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected AST file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getExpectedASTFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-expected-ast.txt");
	}

	/**
	 * Gets the actual AST file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual AST file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getActualASTFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-actual-ast.txt");
	}

	/**
	 * Gets the expected validation file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected validation file from the test folder path
	 */
	protected File getExpectedValidationFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-validation.txt");
	}

	/**
	 * Gets the actual validation file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual validation file from the test folder path
	 */
	protected File getActualValidationFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-validation.txt");
	}

	/**
	 * Gets the expected validation file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected validation file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getExpectedValidationFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-expected-validation.txt");
	}

	/**
	 * Gets the actual validation file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual validation file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getActualValidationFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-actual-validation.txt");
	}

	/**
	 * Gets the expected quick fixes file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected quick fixes file from the test folder path
	 */
	protected File getExpectedQuickFixesFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-quickFixes.txt");
	}

	/**
	 * Gets the actual quick fixes file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual quick fixes file from the test folder path
	 */
	protected File getActualQuickFixesFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-quickFixes.txt");
	}

	/**
	 * Gets the expected quick fixes file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected quick fixes file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getExpectedQuickFixesFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-expected-quickFixes.txt");
	}

	/**
	 * Gets the actual quick fixes file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual quick fixes file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getActualQuickFixesFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-actual-quickFixes.txt");
	}

	/**
	 * Gets the expected generated file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected generated file from the test folder path
	 */
	protected File getExpectedGeneratedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-generation.txt");
	}

	/**
	 * Gets the actual generated file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual generated file from the test folder path
	 */
	protected File getActualGeneratedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-generation.txt");
	}

	/**
	 * Gets the model file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the model file from the test folder path
	 */
	protected File getModelFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + ".xmi");
	}

	/**
	 * Gets the template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the template file from the test folder path
	 */
	private static File getTemplateFileInternal(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + ".mtl");
	}

	/**
	 * Gets the {@link Collection} of test folders from the given folder path.
	 * 
	 * @param folderPath
	 *            the folder path
	 * @return the {@link Collection} of test folders from the given folder path
	 */
	public static Collection<Object[]> retrieveTestFolders(String folderPath) {
		Collection<Object[]> parameters = new ArrayList<Object[]>();

		File folder = new File(folderPath);
		final File[] children = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				final boolean res;

				if (pathname.isDirectory() && pathname.canRead()) {
					res = getTemplateFileInternal(pathname).exists();
				} else {
					res = false;
				}

				return res;
			}

		});
		Arrays.sort(children);
		for (File child : children) {
			parameters.add(new Object[] {child.getPath() });
		}

		return parameters;
	}

	/**
	 * Sets the given content to the given {@link OutputStream}.
	 * 
	 * @param stream
	 *            the {@link OutputStream}
	 * @param charsetName
	 *            the charset name
	 * @param content
	 *            the content to write
	 * @throws UnsupportedEncodingException
	 *             if the given charset is not supported
	 * @throws IOException
	 *             if the given stream can't be written to
	 */
	public static void setContent(OutputStream stream, String charsetName, String content)
			throws UnsupportedEncodingException, IOException {
		stream.write(content.getBytes(charsetName));
		stream.flush();
	}

	/**
	 * Copies all bytes from a source {@link URI} to a destination {@link URI}.
	 * 
	 * @param sourceURI
	 *            the source {@link URI}
	 * @param destURI
	 *            the destination {@link URI}
	 * @return the number of copied bytes
	 * @throws IOException
	 *             if the copy can't be done
	 */
	protected static long copy(URIConverter uriConverter, URI sourceURI, URI destURI) throws IOException {
		try (InputStream source = uriConverter.createInputStream(sourceURI);
				OutputStream dest = uriConverter.createOutputStream(destURI);) {
			long nread = 0L;
			byte[] buf = new byte[BUFFER_SIZE];
			int n;
			while ((n = source.read(buf)) > 0) {
				dest.write(buf, 0, n);
				nread += n;
			}
			return nread;
		}
	}

	/**
	 * Gets the portable version of the given {@link String}.
	 * 
	 * @param textContent
	 *            the text content
	 * @return the portable version of the given {@link String}
	 */
	protected String getPortableString(String textContent) {
		String res;

		res = textContent.replaceAll("/home/.*/tests", "/home/.../tests"); // remove folder prefix
		res = res.replaceAll("file:/.*/tests", "file:/.../tests"); // remove folder prefix
		res = res.replaceAll("Aucun fichier ou dossier de ce type", "No such file or directory"); // replace
																									// localized
																									// message
		res = res.replaceAll("20[^ ]* [^ ]* - Lost", "20...date and time... - Lost"); // strip lost user doc
																						// date
		res = res.replaceAll("@[a-f0-9]{5,8}[, )]", "@00000000 "); // object address in toString()
		res = res.replaceAll(
				"(\\tat [a-zA-Z0-9$./]+((<|&lt;)init(>|&gt;))?\\((Unknown Source|Native Method|[a-zA-Z0-9$./]+java:[0-9]+)\\)\n?)+",
				"...STACK..."); // strip stack traces
		res = res.replaceAll("127.0.0.100:12.345", "127.0.0.100:12 345"); // localized port...
		res = res.replaceAll("127.0.0.100:12,345", "127.0.0.100:12 345"); // localized port...

		return res;
	}

}
