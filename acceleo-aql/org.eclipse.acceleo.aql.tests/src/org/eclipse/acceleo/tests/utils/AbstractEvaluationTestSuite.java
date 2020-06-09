/*******************************************************************************
 * Copyright (c) 2016, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.junit.Test;

/**
 * Tests the evalation of a {@link Module} using {@link AcceleoEvaluator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractEvaluationTestSuite extends AbstractLanguageTestSuite {

	/**
	 * Copy buffer size.
	 */
	private static final int BUFFER_SIZE = 8192;

	/**
	 * The {@link Resource}.
	 */
	private final Resource model;

	/**
	 * The {@link AcceleoEvaluator}.
	 */
	private final AcceleoEvaluator evaluator;

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
		final ResourceSet rs = getResourceSet();
		model = getModel(modelFile, rs);
		evaluator = new AcceleoEvaluator(environment);
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
		if (URIConverter.INSTANCE.exists(modelURI, null)) {
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
		final List<URI> expectedGeneratedFiles = getExpectedGeneratedFiles(generatedFolderURI);
		final List<URI> unexpectedGeneratedFiles = new ArrayList<URI>();
		AcceleoUtil.generate(evaluator, environment, module, model);

		assertGenerationMessages(environment.getGenerationResult());

		// assert generated content
		final GenerationResult result = environment.getGenerationResult();
		for (URI memoryGeneratedURI : result.getGeneratedFiles()) {
			final URI generatedURI = URI.createURI(memoryGeneratedURI.toString().substring(
					memoryDestinationString.length())).resolve(generatedFolderURI);
			final URI expectedURI = URI.createURI(generatedURI.toString() + "-expected.txt");
			expectedGeneratedFiles.remove(expectedURI);
			final URI actualURI = URI.createURI(generatedURI.toString() + "-actual.txt");
			if (URIConverter.INSTANCE.exists(expectedURI, null)) {
				final String expectedContent;
				try (InputStream expectedStream = URIConverter.INSTANCE.createInputStream(expectedURI)) {
					expectedContent = getContent(expectedStream, UTF_8); // TODO test other encoding
				}
				final String actualContent;
				try (InputStream actualStream = URIConverter.INSTANCE.createInputStream(memoryGeneratedURI)) {
					actualContent = getContent(actualStream, UTF_8); // TODO test other encoding
				}
				assertEquals(expectedContent, actualContent);
			} else {
				copy(memoryGeneratedURI, actualURI);
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
				return name.endsWith("-expected.txt");
			}
		});

		if (expectedFileNames != null) {
			for (String expectedFileName : expectedFileNames) {
				res.add(URI.createURI(expectedFileName).resolve(generatedFolderURI));
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
				expectedContent = getContent(stream, UTF_8);
			}
			assertEquals(expectedContent, actualContent);
		}
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

	/**
	 * Gets the portable version of the given {@link String}.
	 * 
	 * @param textContent
	 *            the text content
	 * @return the portable version of the given {@link String}
	 */
	private String getPortableString(String textContent) {
		String res;

		res = textContent.replaceAll("/home/.*/M2Doc", "/home/.../M2Doc"); // remove folder prefix
		res = res.replaceAll("file:/.*/M2Doc", "file:/.../M2Doc"); // remove folder prefix
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
	private static long copy(URI sourceURI, URI destURI) throws IOException {
		try (InputStream source = URIConverter.INSTANCE.createInputStream(sourceURI);
				OutputStream dest = URIConverter.INSTANCE.createOutputStream(destURI);) {
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

}
