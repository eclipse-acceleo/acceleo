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
package org.eclipse.acceleo.tests;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.tests.utils.AbstractTemplatesTestSuite;
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

		final AcceleoEnvironment acceleoEnvironment = new AcceleoEnvironment();
		final AcceleoParser parser = new AcceleoParser(acceleoEnvironment.getQueryEnvironment());
		final AcceleoAstResult parsingResult = parser.parse(source);
		final Module module = parsingResult.getModule();
		acceleoEnvironment.registerModule("org::eclipse::acceleo::tests::" + module.getName(), module);
		final List<ICompletionProposal> proposals = completor.getProposals(acceleoEnvironment, source,
				position);
		final String actualCompletion = serialize(proposals);
		final File expectedCompletionFile = getExpectedCompletionFile();
		if (!expectedCompletionFile.exists()) {
			final File actualCompletionFile = getActualCompletionFile();
			if (!actualCompletionFile.exists()) {
				actualCompletionFile.createNewFile();
			}
			try (final OutputStream stream = new FileOutputStream(actualCompletionFile)) {
				AbstractTemplatesTestSuite.setContent(stream, AbstractTemplatesTestSuite.UTF_8,
						actualCompletion);
			}
			fail("file doesn't exist.");
		}

		try (final InputStream stream = new FileInputStream(expectedCompletionFile)) {
			String expectedCompletion = AbstractTemplatesTestSuite.getContent(stream,
					AbstractTemplatesTestSuite.UTF_8);
			assertEquals(expectedCompletion, actualCompletion);
		}
	}

	/**
	 * Serializes the given {@link List} of {@link ICompletionProposal}.
	 * 
	 * @param proposals
	 *            the {@link List} of {@link ICompletionProposal}
	 * @return the serialized {@link List} of {@link ICompletionProposal}
	 */
	private String serialize(List<ICompletionProposal> proposals) {
		final StringBuilder builder = new StringBuilder();

		Collections.sort(proposals, new Comparator<ICompletionProposal>() {

			public int compare(ICompletionProposal o1, ICompletionProposal o2) {
				return (o1.getProposal() + o1.getDescription()).compareTo(o2.getProposal()
						+ o2.getDescription());
			};

		});

		for (ICompletionProposal proposal : proposals) {
			builder.append(proposal.getProposal());
			builder.append(' ');
			builder.append(proposal.getCursorOffset());
			builder.append('\n');
			builder.append(proposal.getDescription());
			builder.append('\n');
			builder.append('\n');
		}

		return builder.toString();
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

		final Map<String, Integer> testToPosition = new LinkedHashMap<String, Integer>();
		final StringBuilder builder = new StringBuilder();
		try (final InputStream stream = new FileInputStream(MODULE)) {
			String content = AbstractTemplatesTestSuite.getContent(stream, AbstractTemplatesTestSuite.UTF_8);
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
