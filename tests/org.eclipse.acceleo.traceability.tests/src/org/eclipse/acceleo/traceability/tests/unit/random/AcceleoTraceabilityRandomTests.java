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
package org.eclipse.acceleo.traceability.tests.unit.random;

import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.random.data.BagData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.BooleanData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.FloatData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.IntData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.OrderedSetData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.SequenceData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.SetData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.StringData;
import org.eclipse.acceleo.traceability.tests.unit.random.operations.StringSourceIntParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.operations.StringSourceNoParameters;
import org.eclipse.acceleo.traceability.tests.unit.random.operations.StringSourceStringParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.operations.StringSourceStringStringParameter;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@SuppressWarnings("nls")
@RunWith(Theories.class)
public class AcceleoTraceabilityRandomTests extends AbstractTraceabilityTest {

	//@formatter:off
	
	protected StringBuffer moduleBufferBegin = new StringBuffer("")
			.append("[comment encoding = UTF-8 /]\n")
			.append("[module randomtheories('http://www.eclipse.org/emf/2002/Ecore')/]\n")
			.append("")
			.append("[template public main(eClass : EClass)]\n")
			.append("[file (eClass.name + '.txt', false, 'UTF-8')][");

	protected StringBuffer moduleBufferEnd = new StringBuffer ("")
			.append("/][/file]\n")
			.append("[/template]\n")
			.append("\n")
			.append("[template public template1(eClass : EClass)]template one[/template]\n")
			.append("\n")
			.append("[template public template2(eClass : EClass)]template two[/template]\n")
			.append("\n")
			.append("[query public queryString1(eClass : EClass) : String = 'queryString1'/]\n")
			.append("\n")
			.append("[query public queryString2(eClass : EClass) : String = 'queryString2'/]\n")
			.append("\n")
			.append("[query public queryInt1(eClass : EClass) : Integer = 17/]\n")
			.append("\n")
			.append("[query public queryInt2(eClass : EClass) : Integer = eClass.name.size()/]\n")
			.append("");
	
	/**################################################################################################
	 *
	 * 											Attributes
	 *
	 *################################################################################################*/

	/** Data points Strings */
	@DataPoint public static StringData stringLiteral = new StringData("'This is a string literal'");
	@DataPoint public static StringData propertyValue = new StringData("eClass.name");
	@DataPoint public static StringData stringLiteralConcatenation = new StringData("'This is a ' + 'concatenation'");
	@DataPoint public static StringData templateResult = new StringData("eClass.template1()");
	@DataPoint public static StringData templateAndStringLiteralConcatenation = new StringData("'This is my template: ' + eClass.template1()");
	@DataPoint public static StringData templateAndTemplateConcatenation = new StringData("eClass.template1() + eClass.template2()");
	@DataPoint public static StringData queryStringResult = new StringData("eClass.queryString1()");
	@DataPoint public static StringData queryStringAndStringLiteralConcatenation = new StringData("'This is my query: ' + eClass.queryString1()");
	@DataPoint public static StringData queryStringAndQueryStringConcatenation = new StringData("eClass.queryString1() + eClass.queryString2()");
	@DataPoint public static StringData queryStringAndTemplateConcatenation = new StringData("eClass.template1() + eClass.queryString1()");
	@DataPoint public static StringData templateAndQueryStringConcatenation = new StringData("eClass.queryString1() + eClass.template1()");
	@DataPoint public static StringData sequenceToString = new StringData("Sequence{'a', 'b', 'c'}->toString()");
	@DataPoint public static StringData bagToString = new StringData("Bag{'a', 'b', 'c'}->toString()");
	@DataPoint public static StringData setToString = new StringData("Set{'a', 'b', 'c'}->toString()");
	@DataPoint public static StringData orderedsetToString = new StringData("OrderedSet{'a', 'b', 'c'}->toString()");
	@DataPoint public static StringData intNumberToString = new StringData("17.toString()");
	@DataPoint public static StringData floatNumberToString = new StringData("(-5.3).toString()");
	
	/** Data points Int */
	@DataPoint public static IntData intNumber = new IntData("17");
	@DataPoint public static IntData intNumberAddition = new IntData("17 + 5");
	@DataPoint public static IntData stringLiteralSize = new IntData("'What is my size ?'.size()");
	@DataPoint public static IntData propertyValueSize = new IntData("eClass.name.size()");
	@DataPoint public static IntData templateResultSize = new IntData("eClass.template1().size()");
	@DataPoint public static IntData queryStringResultSize = new IntData("eClass.queryString1().size()");
	@DataPoint public static IntData queryIntResult = new IntData("eClass.queryInt1()");
	@DataPoint public static IntData queryIntAndQueryIntAddition = new IntData("eClass.queryInt1() + eClass.queryInt2()");
	
	/** Data points Float */
	@DataPoint public static FloatData floatNumber = new FloatData("(-5.3)");
	@DataPoint public static FloatData queryFloatResult = new FloatData("eClass.queryFloat1()");
	@DataPoint public static FloatData queryFloatAndQueryFloatAddition = new FloatData("eClass.queryFloat1() + eClass.queryFloat2()");
	
	/** Data points Boolean */
	@DataPoint public static BooleanData booleanTrueValue = new BooleanData("true");
	@DataPoint public static BooleanData booleanFalseValue = new BooleanData("false");
	@DataPoint public static BooleanData queryBooleanResult = new BooleanData("eClass.queryBoolean1()");
	
	/** Data points Sequence */
	@DataPoint public static SequenceData sequenceStringValue = new SequenceData("Sequence{'a', 'b', 'c'}");
	
	/** Data points Bag */
	@DataPoint public static BagData bagStringValue = new BagData("Bag{'a', 'b', 'c'}");
	
	/** Data points Set */
	@DataPoint public static SetData setStringValue = new SetData("Set{'a', 'b', 'c'}");
	
	/** Data points OrderedSet */
	@DataPoint public static OrderedSetData orderedsetStringValue = new OrderedSetData("OrderedSet{'a', 'b', 'c'}");
	

	
	/**################################################################################################
	 *
	 * 											String Operations
	 *
	 *################################################################################################*/
	@DataPoint public static StringSourceNoParameters isAlpha = new StringSourceNoParameters(".isAlpha");
	@DataPoint public static StringSourceNoParameters isAlphanum = new StringSourceNoParameters(".isAlphanum");
	@DataPoint public static StringSourceNoParameters size = new StringSourceNoParameters(".size");
	@DataPoint public static StringSourceNoParameters toLowerFirst = new StringSourceNoParameters(".toLowerFirst");
	@DataPoint public static StringSourceNoParameters toUpperFirst = new StringSourceNoParameters(".toUpperFirst");
	@DataPoint public static StringSourceNoParameters toUpper = new StringSourceNoParameters(".toUpper");
	@DataPoint public static StringSourceNoParameters trim = new StringSourceNoParameters(".trim");
	
	@DataPoint public static StringSourceStringParameter index = new StringSourceStringParameter(".index");
	@DataPoint public static StringSourceStringParameter strcmp = new StringSourceStringParameter(".strcmp");
	@DataPoint public static StringSourceStringParameter contains = new StringSourceStringParameter(".contains");
	@DataPoint public static StringSourceStringParameter endsWith = new StringSourceStringParameter(".endsWith");
	@DataPoint public static StringSourceStringParameter equalsIgnoreCase = new StringSourceStringParameter(".equalsIgnoreCase");
	@DataPoint public static StringSourceStringParameter lastIndex = new StringSourceStringParameter(".lastIndex");
	@DataPoint public static StringSourceStringParameter matches = new StringSourceStringParameter(".matches");
	@DataPoint public static StringSourceStringParameter startsWith = new StringSourceStringParameter(".startsWith");
	@DataPoint public static StringSourceStringParameter tokenize = new StringSourceStringParameter(".tokenize");
	@DataPoint public static StringSourceStringParameter concat = new StringSourceStringParameter(".concat");
	
	@DataPoint public static StringSourceIntParameter first = new StringSourceIntParameter(".first");
	@DataPoint public static StringSourceIntParameter last = new StringSourceIntParameter(".last");
	@DataPoint public static StringSourceIntParameter substring = new StringSourceIntParameter(".substring");
	
	@DataPoint public static StringSourceStringStringParameter substitute = new StringSourceStringStringParameter(".substitute");
	@DataPoint public static StringSourceStringStringParameter substituteAll = new StringSourceStringStringParameter(".substituteAll");
	@DataPoint public static StringSourceStringStringParameter replace = new StringSourceStringStringParameter(".replace");
	@DataPoint public static StringSourceStringStringParameter replaceAll = new StringSourceStringStringParameter(".replaceAll");
}
