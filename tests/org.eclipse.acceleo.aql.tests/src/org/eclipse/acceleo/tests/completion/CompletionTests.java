/*******************************************************************************
 * Copyright (c) 2017, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.completion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.runtime.impl.ECrossReferenceAdapterCrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.impl.ResourceSetRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.tests.utils.AbstractLanguageTestSuite;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test the Acceleo AQL completion.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public class CompletionTests {

	/**
	 * The test {@link Pattern}.
	 */
	private static final Pattern TEST_PATTERN = Pattern.compile("<<([a-zA-Z0-9_]+)>>");

	/**
	 * The root folder.
	 */
	private static final String ROOT = "resources" + File.separator + "completion";

	/**
	 * The root folder.
	 */
	private static final String MODULE = "resources" + File.separator + "completion" + File.separator
			+ "completion.mtl";

	/**
	 * The test name.
	 */
	private final String testName;

	/**
	 * The source module to complete.
	 */
	private final String source;

	/**
	 * The position for the completion.
	 */
	private final int position;

	/**
	 * Constructor.
	 * 
	 * @param testName
	 *            the test name
	 * @param source
	 *            the module source
	 * @param position
	 *            the completion position
	 */
	public CompletionTests(String testName, String source, Integer position) {
		this.testName = testName;
		this.source = source;
		this.position = position;
	}

	/**
	 * Tests the completion.
	 * 
	 * @throws FileNotFoundException
	 *             if expected completion file can't be found
	 * @throws IOException
	 *             if the expected file can't be read or the actual file can't be written
	 */
	@Test
	public void completion() throws FileNotFoundException, IOException {
		final AcceleoCompletor completor = new AcceleoCompletor();

		final IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(getClass()
				.getClassLoader(), AcceleoParser.QUALIFIER_SEPARATOR);

		final ResourceSet resourceSetForModels = new ResourceSetImpl(); // this will not be used
		final ECrossReferenceAdapterCrossReferenceProvider crossReferenceProvider = new ECrossReferenceAdapterCrossReferenceProvider(
				ECrossReferenceAdapter.getCrossReferenceAdapter(resourceSetForModels));
		final ResourceSetRootEObjectProvider rootProvider = new ResourceSetRootEObjectProvider(
				resourceSetForModels);
		final IQualifiedNameQueryEnvironment queryEnvironment = org.eclipse.acceleo.query.runtime.Query
				.newQualifiedNameEnvironmentWithDefaultServices(resolver, crossReferenceProvider,
						rootProvider);

		final AcceleoEvaluator evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine());
		final AcceleoParser parser = new AcceleoParser();
		resolver.addLoader(new ModuleLoader(parser, evaluator));
		resolver.addLoader(new JavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));

		final AcceleoAstResult parsingResult = parser.parse(source, "org::eclipse::acceleo::tests::");
		final Module module = parsingResult.getModule();
		resolver.register("org::eclipse::acceleo::tests::" + module.getName(), module);
		final List<AcceleoCompletionProposal> completionProposals = completor.getProposals(queryEnvironment,
				module.getName(), source, position);
		final String actualCompletion = serialize(completionProposals);
		final File expectedCompletionFile = getExpectedCompletionFile();
		if (!expectedCompletionFile.exists()) {
			final File actualCompletionFile = getActualCompletionFile();
			if (!actualCompletionFile.exists()) {
				actualCompletionFile.createNewFile();
			}
			try (OutputStream stream = new FileOutputStream(actualCompletionFile)) {
				AbstractLanguageTestSuite.setContent(stream, AbstractLanguageTestSuite.UTF_8,
						actualCompletion);
			}
			fail("file doesn't exist.");
		}

		try (InputStream stream = new FileInputStream(expectedCompletionFile)) {
			String expectedCompletion = AbstractLanguageTestSuite.getContent(stream,
					AbstractLanguageTestSuite.UTF_8);
			assertEquals(expectedCompletion, actualCompletion);
		}
	}

	/**
	 * Serializes the given {@link List} of {@link AcceleoCompletionProposal}.
	 * 
	 * @param proposals
	 *            the {@link List} of {@link AcceleoCompletionProposal}
	 * @return the serialized {@link List} of {@link AcceleoCompletionProposal}
	 */
	private String serialize(List<AcceleoCompletionProposal> proposals) {
		final StringBuilder builder = new StringBuilder();

		for (AcceleoCompletionProposal proposal : proposals) {
			builder.append("* Label:" + AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append(proposal.getLabel());
			builder.append(AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append("* Description:" + AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append(proposal.getDescription());
			builder.append(AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append("* Text:" + AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append(proposal.getText());
			builder.append(AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append("* Type (optional):" + AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			String typeAsString = "null";
			if (proposal.getAcceleoType() != null) {
				typeAsString = EcoreUtil.getURI(proposal.getAcceleoType()).toString();
			}
			builder.append(typeAsString);
			builder.append(AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
			builder.append(AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER);
		}

		return builder.toString().replaceAll("(\\r\\n)|\\r|\\n", Character.toString(
				AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER));
	}

	/**
	 * Gets the expected completion file.
	 * 
	 * @return the expected completion file
	 */
	protected File getExpectedCompletionFile() {
		return new File(ROOT + File.separator + testName + "-expected-completion.txt");
	}

	/**
	 * Gets the actual completion file.
	 * 
	 * @return the actual completion file
	 */
	protected File getActualCompletionFile() {
		return new File(ROOT + File.separator + testName + "-actual-completion.txt");
	}

	/**
	 * Gets the {@link List} of {testName, source, position} tuples.
	 * 
	 * @return the {@link List} of {testName, source, position} tuples
	 * @throws IOException
	 *             if the module can't be read
	 * @throws FileNotFoundException
	 *             if the module can't be found
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> retrieveTests() throws FileNotFoundException, IOException {
		final List<Object[]> res = new ArrayList<Object[]>();

		final Map<String, Integer> testToPosition = new TreeMap<String, Integer>();
		final StringBuilder builder = new StringBuilder();
		try (InputStream stream = new FileInputStream(MODULE)) {
			String content = AbstractLanguageTestSuite.getContent(stream, AbstractLanguageTestSuite.UTF_8);
			final Matcher matcher = TEST_PATTERN.matcher(content);
			int lastMatchEnd = 0;
			while (matcher.find()) {
				builder.append(content.substring(lastMatchEnd, matcher.start()));
				lastMatchEnd = matcher.end();
				testToPosition.put(matcher.group(1), builder.length());
			}
			builder.append(content.substring(lastMatchEnd, content.length()));
		}

		final String source = builder.toString();

		for (Entry<String, Integer> entry : testToPosition.entrySet()) {
			res.add(new Object[] {entry.getKey(), source, entry.getValue() });
		}

		return res;
	}
}
