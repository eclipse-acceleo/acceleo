/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit.random.string;

import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.acceleo.traceability.tests.unit.random.AcceleoTraceabilityRandomTests;
import org.eclipse.acceleo.traceability.tests.unit.random.AcceleoTraceabilityTheoriesSuite;
import org.eclipse.acceleo.traceability.tests.unit.random.data.StringData;
import org.eclipse.acceleo.traceability.tests.unit.random.operations.StringSourceStringParameter;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@SuppressWarnings("nls")
@RunWith(AcceleoTraceabilityTheoriesSuite.class)
public class StringSourceStringParameterTest extends AcceleoTraceabilityRandomTests {

	private StringSourceStringParameter operation;

	public StringSourceStringParameterTest(StringSourceStringParameter operation) {
		this.operation = operation;
	}

	@Theory
	public void stringSourceStringParameter(StringData source, StringData param) {
		StringBuffer moduleBuffer = this.moduleBufferBegin.append(source.getValue()).append(
				operation.getValue());
		moduleBuffer.append("(").append(param.getValue()).append(")").append(this.moduleBufferEnd);
		AcceleoTraceabilityListener acceleoTraceabilityListener = this.parseAndGenerate(
				"data/random/randomtheories.mtl", moduleBuffer, "main", "data/random/model.ecore", true);
		List<GeneratedFile> generatedFiles = acceleoTraceabilityListener.getGeneratedFiles();
		assertThat(generatedFiles.size(), is(4));
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertThat("operation: " + operation.getValue() + " /// source: " + source.getValue(),
					generatedRegions.size(), not(equalTo(0)));
		}
	}
}
