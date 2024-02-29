/*******************************************************************************
 * Copyright (c) 2016, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultURIWriterFactory;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Test;

/**
 * Tests the evalation of a {@link Module} using {@link AcceleoEvaluator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractEvaluationTestSuite extends AbstractLanguageTestSuite {

	private static final String ACTUAL_SUFFIT = "-actual.txt";

	private static final String EXPECTED_SUFFIX = "-expected.txt";

	private static final String PROTECTED_AREA_SUFFIX = "-protectedArea.txt";

	/**
	 * The {@link Resource}.
	 */
	private final Resource model;

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	protected AbstractEvaluationTestSuite(String testFolder) throws IOException {
		super(testFolder);
		final File modelFile = getModelFile(new File(getTestFolderPath()));
		model = getModel(modelFile, resourceSetForModels);
	}

	/**
	 * Gets the {@link Resource}.
	 * 
	 * @param modelFile
	 *            the model {@link File}
	 * @param rs
	 *            the {@link ResourceSet}
	 * @return the {@link Resource}
	 */
	protected Resource getModel(File modelFile, ResourceSet rs) {
		final Resource res;

		final URI modelURI = URI.createFileURI(modelFile.getAbsolutePath());
		if (rs.getURIConverter().exists(modelURI, null)) {
			res = rs.getResource(modelURI, true);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Tests the generation by comparing the result of the generation.
	 * 
	 * @throws IOException
	 *             if a file can't be read or written
	 */
	@Test
	public void evaluation() throws IOException {
		final Module module = astResult.getModule();
		final URI generatedFolderURI = URI.createURI("generated/").resolve(model.getURI());

		// copy protected areas to memory destination
		final URI protectedAreaFolderURI = URI.createURI("protected-area/").resolve(model.getURI());
		for (URI protectedAreaURI : getProtectedAreaFiles(protectedAreaFolderURI)) {
			final URI relativeURI = protectedAreaURI.deresolve(protectedAreaFolderURI);
			final URI destinationURI = URI.createURI(relativeURI.resolve(memoryDestination).toString()
					.replaceAll(PROTECTED_AREA_SUFFIX, ""));
			copy(resourceSetForModels.getURIConverter(), protectedAreaURI, destinationURI);
		}

		final List<URI> expectedGeneratedFiles = getExpectedGeneratedFiles(generatedFolderURI);
		final List<URI> unexpectedGeneratedFiles = new ArrayList<URI>();
		final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(model.getResourceSet()
				.getURIConverter(), new DefaultURIWriterFactory());
		AcceleoUtil.generate(evaluator, queryEnvironment, module, model, strategy, memoryDestination, null);

		assertGenerationMessages(evaluator.getGenerationResult());

		// assert generated content
		final GenerationResult result = evaluator.getGenerationResult();
		final List<URI> generatedAndLost = new ArrayList<>();
		generatedAndLost.addAll(result.getGeneratedFiles());
		generatedAndLost.addAll(result.getLostFiles());
		for (URI memoryGeneratedURI : generatedAndLost) {
			final URI generatedURI = URI.createURI(memoryGeneratedURI.toString().substring(
					memoryDestinationString.length())).resolve(generatedFolderURI);
			final URI expectedURI = URI.createURI(generatedURI.toString() + EXPECTED_SUFFIX);
			expectedGeneratedFiles.remove(expectedURI);
			final URI actualURI = URI.createURI(generatedURI.toString() + ACTUAL_SUFFIT);
			if (resourceSetForModels.getURIConverter().exists(expectedURI, null)) {
				final String expectedContent;
				try (InputStream expectedStream = resourceSetForModels.getURIConverter().createInputStream(
						expectedURI)) {
					expectedContent = AcceleoUtil.getContent(expectedStream, UTF_8); // TODO test other
																						// encoding
				}
				final String actualContent;
				try (InputStream actualStream = resourceSetForModels.getURIConverter().createInputStream(
						memoryGeneratedURI)) {
					actualContent = AcceleoUtil.getContent(actualStream, UTF_8); // TODO test other encoding
				}
				assertEquals(getPortableString(expectedContent), getPortableString(actualContent));
			} else {
				copy(resourceSetForModels.getURIConverter(), memoryGeneratedURI, actualURI);
				unexpectedGeneratedFiles.add(actualURI);
			}
		}

		if (!unexpectedGeneratedFiles.isEmpty()) {
			fail("unexpected generated file: " + Arrays.deepToString(unexpectedGeneratedFiles.toArray()));
		}
		if (!expectedGeneratedFiles.isEmpty()) {
			fail("expected generated file are missing: " + Arrays.deepToString(expectedGeneratedFiles.toArray(
					new URI[expectedGeneratedFiles.size()])));
		}
	}

	/**
	 * Tests the generation by comparing the result of the generation.
	 * 
	 * @throws IOException
	 *             if a file can't be read or written
	 */
	@Test
	public void evaluationWindowsEndLine() throws IOException {
		final Module module = astResultWindowsEndLine.getModule();
		final URI generatedFolderURI = URI.createURI("generated/").resolve(model.getURI());

		// copy protected areas to memory destination
		final URI protectedAreaFolderURI = URI.createURI("protected-area/").resolve(model.getURI());
		for (URI protectedAreaURI : getProtectedAreaFiles(protectedAreaFolderURI)) {
			final URI relativeURI = protectedAreaURI.deresolve(protectedAreaFolderURI);
			final URI destinationURI = URI.createURI(relativeURI.resolve(memoryDestination).toString()
					.replaceAll(PROTECTED_AREA_SUFFIX, ""));
			copy(resourceSetForModels.getURIConverter(), protectedAreaURI, destinationURI);
		}

		final List<URI> expectedGeneratedFiles = getExpectedGeneratedFiles(generatedFolderURI);
		final List<URI> unexpectedGeneratedFiles = new ArrayList<URI>();
		final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(model.getResourceSet()
				.getURIConverter(), new DefaultURIWriterFactory());
		AcceleoUtil.generate(evaluatorWindowsEndLine, queryEnvironmentWindowsEndLine, module, model, strategy,
				memoryDestination, null);

		assertGenerationMessagesWindowsEndLine(evaluatorWindowsEndLine.getGenerationResult());

		// assert generated content
		final GenerationResult result = evaluatorWindowsEndLine.getGenerationResult();
		final List<URI> generatedAndLost = new ArrayList<>();
		generatedAndLost.addAll(result.getGeneratedFiles());
		generatedAndLost.addAll(result.getLostFiles());
		for (URI memoryGeneratedURI : generatedAndLost) {
			final URI generatedURI = URI.createURI(memoryGeneratedURI.toString().substring(
					memoryDestinationString.length())).resolve(generatedFolderURI);
			final URI expectedURI = URI.createURI(generatedURI.toString() + EXPECTED_SUFFIX);
			expectedGeneratedFiles.remove(expectedURI);
			final URI actualURI = URI.createURI(generatedURI.toString() + ACTUAL_SUFFIT);
			if (resourceSetForModels.getURIConverter().exists(expectedURI, null)) {
				final String expectedContent;
				try (InputStream expectedStream = resourceSetForModels.getURIConverter().createInputStream(
						expectedURI)) {
					expectedContent = AcceleoUtil.getContent(expectedStream, UTF_8); // TODO test other
																						// encoding
				}
				final String actualContent;
				try (InputStream actualStream = resourceSetForModels.getURIConverter().createInputStream(
						memoryGeneratedURI)) {
					actualContent = AcceleoUtil.getContent(actualStream, UTF_8); // TODO test other encoding
				}
				assertEquals(getPortableString(expectedContent), getPortableString(actualContent));
			} else {
				copy(resourceSetForModels.getURIConverter(), memoryGeneratedURI, actualURI);
				unexpectedGeneratedFiles.add(actualURI);
			}
		}

		if (!unexpectedGeneratedFiles.isEmpty()) {
			fail("unexpected generated file: " + Arrays.deepToString(unexpectedGeneratedFiles.toArray()));
		}
		if (!expectedGeneratedFiles.isEmpty()) {
			fail("expected generated file are missing: " + Arrays.deepToString(expectedGeneratedFiles.toArray(
					new URI[expectedGeneratedFiles.size()])));
		}
	}

	private List<URI> getExpectedGeneratedFiles(URI generatedFolderURI) {
		final List<URI> res = new ArrayList<URI>();

		final File generatedFolder = new File(generatedFolderURI.toFileString());

		final String[] expectedFileNames = generatedFolder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(EXPECTED_SUFFIX);
			}
		});

		if (expectedFileNames != null) {
			for (String expectedFileName : expectedFileNames) {
				res.add(URI.createURI(expectedFileName).resolve(generatedFolderURI));
			}
		}

		return res;
	}

	private List<URI> getProtectedAreaFiles(URI protectedAreaFolderURI) {
		final List<URI> res = new ArrayList<URI>();

		final File generatedFolder = new File(protectedAreaFolderURI.toFileString());

		final String[] expectedFileNames = generatedFolder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(PROTECTED_AREA_SUFFIX);
			}
		});

		if (expectedFileNames != null) {
			for (String expectedFileName : expectedFileNames) {
				res.add(URI.createURI(expectedFileName).resolve(protectedAreaFolderURI));
			}
		}

		return res;
	}

	/**
	 * Asserts the runtime messages.
	 * 
	 * @param generationResult
	 *            the {@link GenerationResult}
	 * @throws IOException
	 */
	private void assertGenerationMessages(GenerationResult generationResult) throws IOException {
		final String actualContent = getRuntimeMessages(generationResult.getDiagnostic());

		final File expectedFile = getExpectedRuntimeMessageFile(new File(getTestFolderPath()));
		final File actualFile = getActualRuntimeMessageFile(new File(getTestFolderPath()));

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
	 * Asserts the runtime messages for MS Windows end line (\r\n).
	 * 
	 * @param generationResult
	 *            the {@link GenerationResult}
	 * @throws IOException
	 */
	private void assertGenerationMessagesWindowsEndLine(GenerationResult generationResult)
			throws IOException {
		final String actualContent = getRuntimeMessages(generationResult.getDiagnostic());

		final File expectedFile = getExpectedRuntimeMessageFileWindowsEndLine(new File(getTestFolderPath()));
		final File actualFile = getActualRuntimeMessageFileWindowsEndLine(new File(getTestFolderPath()));

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
	 * Gets the actual validated template file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getExpectedRuntimeMessageFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-expected-runtimeMessages.txt");
	}

	/**
	 * Gets the actual validated template file from the test folder path for MS Windows end line (\r\n).
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path for MS Windows end line (\r\n)
	 */
	protected File getActualRuntimeMessageFileWindowsEndLine(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName()
				+ "-WindowsEndLine-actual-runtimeMessages.txt");
	}

	/**
	 * Gets the {@link String} representation of the given {@link Diagnostic}.
	 * 
	 * @param diagnostic
	 *            the {@link Diagnostic}
	 * @return the {@link String} representation of the given {@link Diagnostic}
	 */
	private String getRuntimeMessages(Diagnostic diagnostic) {
		final StringBuilder builder = new StringBuilder();

		walkDiagnostic(builder, "", diagnostic);

		return getPortableString(builder.toString());
	}

	private void walkDiagnostic(StringBuilder builder, String prefix, Diagnostic diagnostic) {
		builder.append(prefix + " (" + diagnostic.getSource() + " " + diagnostic.getCode() + " " + diagnostic
				.getSeverity() + ") " + diagnostic.getMessage() + "[" + getDataString(diagnostic.getData())
				+ "]\n");
		for (Diagnostic child : diagnostic.getChildren()) {
			walkDiagnostic(builder, prefix + "  ", child);
		}
	}

	/**
	 * Gets the {@link Diagnostic} data {@link String}.
	 * 
	 * @param data
	 *            the {@link List} of data
	 * @return the {@link Diagnostic} data {@link String}
	 */
	@SuppressWarnings("unchecked")
	private String getDataString(List<?> data) {
		final StringBuilder builder = new StringBuilder();

		if (data != null) {
			for (Object datum : data) {
				if (datum instanceof Map) {
					builder.append("[");
					for (Entry<Object, Object> entry : ((Map<Object, Object>)datum).entrySet()) {
						builder.append("(" + entry.getKey() + ", " + entry.getValue() + "), ");
					}
					builder.append("]");
				} else if (datum != null) {
					builder.append(datum.toString());
				} else {
					builder.append("null");
				}
			}
		}

		return builder.toString();
	}

}
