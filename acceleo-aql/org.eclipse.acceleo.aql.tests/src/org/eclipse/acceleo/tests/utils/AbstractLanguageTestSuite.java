/*******************************************************************************
 *  Copyright (c) 2016, 2020 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.resolver.FileSystemModuleResolver;
import org.eclipse.acceleo.aql.resolver.IModuleResolver;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	 * The {@link IAcceleoEnvironment}.
	 */
	protected final IAcceleoEnvironment environment;

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
		this.environment = new AcceleoEnvironment(new DefaultGenerationStrategy(), memoryDestination);
		this.testFolderPath = testFolder;
		final File testFolderFile = new File(testFolderPath);
		final File moduleFile = getModuleFile(testFolderFile);

		Path rootPath = testFolderFile.toPath().getName(0);
		IModuleResolver moduleResolver = new FileSystemModuleResolver(rootPath.toAbsolutePath(), environment
				.getQueryEnvironment());
		environment.setModuleResolver(moduleResolver);

		String namespace = rootPath.relativize(testFolderFile.toPath()).toString().replace(File.separator,
				"::") + "::";
		qualifiedName = namespace + moduleFile.getName().substring(0, moduleFile.getName().lastIndexOf('.'));
		Module module = environment.resolveModule(qualifiedName);
		astResult = module.getAst();
	}

	/**
	 * Registers {@link MemoryURIHandler}.
	 */
	@BeforeClass
	public static void beforeClass() {
		URIConverter.INSTANCE.getURIHandlers().add(0, uriHandler);
	}

	/**
	 * Removes {@link MemoryURIHandler}.
	 */
	@AfterClass
	public static void afterClass() {
		uriHandler.clear();
		URIConverter.INSTANCE.getURIHandlers().remove(uriHandler);
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
			final String expectedAst = getContent(stream, UTF_8);
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
		AcceleoValidator validator = new AcceleoValidator(environment);
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
				expectedContent = getContent(stream, UTF_8);
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
	 * Gets the content of the given {@link InputStream}.
	 * 
	 * @param stream
	 *            the {@link InputStream}
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
	 * @return a {@link CharSequence} of the content of the given {@link InputStream}
	 * @throws IOException
	 *             if the {@link InputStream} can't be read
	 */
	public static String getContent(InputStream stream, String charsetName) throws IOException {
		final int len = 8192;
		StringBuilder res = new StringBuilder(len);
		if (len != 0) {
			try (InputStreamReader input = new InputStreamReader(new BufferedInputStream(stream),
					charsetName)) {
				char[] buffer = new char[len];
				int length = input.read(buffer);
				while (length != -1) {
					res.append(buffer, 0, length);
					length = input.read(buffer);
				}
				input.close();
			}
		}
		return res.toString();
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
