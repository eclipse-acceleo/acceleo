/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.assertion;

import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.unit.core.generation.QueryGenerationHelper;
import org.eclipse.acceleo.unit.core.generation.TemplateGenerationHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * The acceleo assert class contains all specific static asset methods for acceleo.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoAssert {

	/**
	 * The constructor.
	 */
	private AcceleoAssert() {
		// never used
	}

	/**
	 * Assert that the template result matches.
	 * 
	 * @param helper
	 *            the acceleo template helper.
	 * @param matcher
	 *            the result matcher.
	 */
	public static void assertThatTemplateResultFrom(TemplateGenerationHelper helper, Matcher<String> matcher) {

		if (helper.hasGenerateFiles()) {
			// helper validator
			if (!helper.isTraceabilityEnable()) {
				throw new IllegalArgumentException("The helper must enable the traceability."); //$NON-NLS-1$
			}

			List<GeneratedFile> generatedFiles = helper.getGeneratedFiles();
			if (generatedFiles.size() <= 0) {
				throw new IllegalArgumentException(
						"The template file must generate one on serveral files. (File tag missing?)"); //$NON-NLS-1$
			}

			for (GeneratedFile generatedFile : generatedFiles) {
				Map<String, String> generatedFilesContent = helper.getGeneratedFilesContent();
				String content = generatedFilesContent.get(generatedFile.getPath());
				assertThat(content, matcher);
			}
		} else {
			assertThat(helper.getGeneratedText(), matcher);
		}
	}

	/**
	 * Asserts that the traceability information matches.
	 * 
	 * @param helper
	 *            the template helper.
	 * @param beginPosition
	 *            The start position of the generated content.
	 * @param generatedContent
	 *            the generated content.
	 * @param templateElementMatcher
	 *            the matcher of the template element that has produced the generated content.
	 * @param modelElementMatcher
	 *            the matcher of the model element that had been used to produce the generated content.
	 */
	public static void assertThatGeneratedArea(TemplateGenerationHelper helper, int beginPosition,
			String generatedContent, Matcher<EObject> templateElementMatcher,
			Matcher<EObject> modelElementMatcher) {
		assertThatGeneratedArea(helper, beginPosition, beginPosition + generatedContent.length(),
				generatedContent, templateElementMatcher, modelElementMatcher);
	}

	/**
	 * Asserts that the traceability information matches.
	 * 
	 * @param helper
	 *            the template helper.
	 * @param beginPosition
	 *            The start position of the generated content.
	 * @param endPosition
	 *            The end position of the generated content.
	 * @param generatedContent
	 *            the generated content.
	 * @param templateElementMatcher
	 *            the matcher of the template element that has produced the generated content.
	 * @param modelElementMatcher
	 *            the matcher of the model element that had been used to produce the generated content.
	 */
	public static void assertThatGeneratedArea(TemplateGenerationHelper helper, int beginPosition,
			int endPosition, String generatedContent, Matcher<EObject> templateElementMatcher,
			Matcher<EObject> modelElementMatcher) {

		// index validator
		if (beginPosition < 0 || endPosition <= beginPosition) {
			throw new IllegalArgumentException("The index is not valid"); //$NON-NLS-1$
		}

		// helper validator
		if (!helper.isTraceabilityEnable()) {
			throw new IllegalArgumentException("The helper must enable the traceability."); //$NON-NLS-1$
		}

		if (!helper.hasGenerateFiles()) {
			throw new IllegalArgumentException(
					"This assert (assertThatGeneratedArea) can not work with generate(). (use generateFile())"); //$NON-NLS-1$
		}

		List<GeneratedFile> generatedFiles = helper.getGeneratedFiles();
		if (generatedFiles.size() <= 0) {
			throw new IllegalArgumentException(
					"The template file must generate one on serveral files. (File tag missing?)"); //$NON-NLS-1$
		}

		for (GeneratedFile generatedFile : generatedFiles) {
			EList<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();

			GeneratedText areaOfConcern = null;
			for (GeneratedText generatedText : generatedRegions) {

				Map<String, String> generatedFilesContent = helper.getGeneratedFilesContent();
				String content = generatedFilesContent.get(generatedFile.getPath());

				assertThat(content.substring(beginPosition, endPosition), is(generatedContent));

				if (generatedText.getStartOffset() == beginPosition
						&& generatedText.getEndOffset() == endPosition) {
					areaOfConcern = generatedText;
					break;
				}
			}

			if (areaOfConcern != null) {
				assertThat(areaOfConcern.getModuleElement().getModuleElement(), templateElementMatcher);
				assertThat(areaOfConcern.getSourceElement().getModelElement(), modelElementMatcher);
			} else {
				throw new IllegalArgumentException("The index is not valid"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Check that we have an IllegalArgumentException during the generate().
	 * 
	 * @param helper
	 *            the template helper.
	 */
	public static void assertThatGenerateThrowIAE(TemplateGenerationHelper helper) {
		boolean hasFailed = false;
		try {
			helper.generate();
		} catch (IllegalArgumentException e) {
			hasFailed = true;
		}
		assertTrue(hasFailed);
	}

	/**
	 * Check that we have an IllegalArgumentException during the generateFile().
	 * 
	 * @param helper
	 *            the template helper.
	 */
	public static void assertThatGenerateFileThrowIAE(TemplateGenerationHelper helper) {
		boolean hasFailed = false;
		try {
			helper.generateFile();
		} catch (IllegalArgumentException e) {
			hasFailed = true;
		}
		assertTrue(hasFailed);
	}

	/**
	 * Assert that the query result matches.
	 * 
	 * @param helper
	 *            the acceleo template helper.
	 * @param matcher
	 *            the result matcher.
	 */
	public static void assertThatQueryResultFrom(QueryGenerationHelper helper,
			Matcher<? extends Object> matcher) {
		if (!matcher.matches(helper.getGeneratedObject())) {
			Description description = new StringDescription();
			description.appendText(""); //$NON-NLS-1$
			description.appendText("\nExpected: "); //$NON-NLS-1$
			description.appendDescriptionOf(matcher);
			description.appendText("\n     got: "); //$NON-NLS-1$
			description.appendValue(helper.getGeneratedObject());
			description.appendText("\n"); //$NON-NLS-1$
			throw new java.lang.AssertionError(description.toString());
		}
	}

}
