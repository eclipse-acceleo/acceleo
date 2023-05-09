/*******************************************************************************
 *  Copyright (c) 2016, 2023 Obeo. 
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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.impl.ECrossReferenceAdapterCrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.impl.ResourceSetRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
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
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	protected final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	protected final AcceleoEvaluator evaluator;

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
	 * The {@link ResourceSet} for models.
	 */
	protected final ResourceSet resourceSetForModels = getResourceSet();

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

		final Path rootPath = testFolderFile.toPath().getName(0);
		final URL[] urls = new URL[] {testFolderFile.toPath().getName(0).toUri().toURL() };

		final ClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
		final IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(classLoader,
				AcceleoParser.QUALIFIER_SEPARATOR);

		final ECrossReferenceAdapterCrossReferenceProvider crossReferenceProvider = new ECrossReferenceAdapterCrossReferenceProvider(
				ECrossReferenceAdapter.getCrossReferenceAdapter(resourceSetForModels));
		final ResourceSetRootEObjectProvider rootProvider = new ResourceSetRootEObjectProvider(
				resourceSetForModels);
		queryEnvironment = org.eclipse.acceleo.query.runtime.Query
				.newQualifiedNameEnvironmentWithDefaultServices(resolver, crossReferenceProvider,
						rootProvider);

		evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine());
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(new JavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));

		String namespace = rootPath.relativize(testFolderFile.toPath()).toString().replace(File.separator,
				"::") + "::";
		qualifiedName = namespace + moduleFile.getName().substring(0, moduleFile.getName().lastIndexOf('.'));
		final Object resolved = resolver.resolve(qualifiedName);
		if (resolved instanceof Module) {
			astResult = ((Module)resolved).getAst();
		} else {
			astResult = null;
		}
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
			stream.close();
		}
	}

	/**
	 * Tests the validation by comparing the validated template.
	 * 
	 * @throws FileNotFoundException
	 *             if the file can't be found
	 * @throws IOException
	 *             if the given stream can't be written to
	 */
	@Test
	public void validation() throws FileNotFoundException, IOException {
		AcceleoValidator validator = new AcceleoValidator(queryEnvironment);
		final List<IValidationMessage> messages = validator.validate(astResult, qualifiedName)
				.getValidationMessages();
		final String actualContent = getValidationContent(messages);
		final File expectedFile = getExpectedValidatedFile(new File(testFolderPath));
		final File actualFile = getActualValidatedFile(new File(testFolderPath));

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
	 * Serializes the given {@link List} of {@link IValidationMessage}.
	 * 
	 * @param messages
	 *            the {@link List} of {@link IValidationMessage}
	 * @return the string representation of the given {@link List} of {@link IValidationMessage
	 */
	private String getValidationContent(List<IValidationMessage> messages) {
		final String res;

		if (!messages.isEmpty()) {
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
	 * Gets the expected validated template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected template file from the test folder path
	 */
	protected File getExpectedValidatedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-validation.txt");
	}

	/**
	 * Gets the actual validated template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path
	 */
	protected File getActualValidatedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-validation.txt");
	}

	/**
	 * Gets the actual validated template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path
	 */
	protected File getExpectedRuntimeMessageFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-runtimeMessages.txt");
	}

	/**
	 * Gets the actual validated template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path
	 */
	protected File getActualRuntimeMessageFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-runtimeMessages.txt");
	}

	/**
	 * Gets the expected generated file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected template file from the test folder path
	 */
	protected File getExpectedGeneratedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-generation.txt");
	}

	/**
	 * Gets the actual generated file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path
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

}
