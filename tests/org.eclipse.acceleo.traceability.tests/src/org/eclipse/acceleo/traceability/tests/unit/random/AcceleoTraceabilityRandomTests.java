/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit.random;

import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.acceleo.traceability.tests.unit.random.collection.CollectionSourceCollectionParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.collection.CollectionSourceNoParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.collection.CollectionSourceObjectParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.data.BagData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.BooleanData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.CollectionData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.ExpressionData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.FloatData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.IntData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.ObjectData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.OrderedSetData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.SequenceData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.SetData;
import org.eclipse.acceleo.traceability.tests.unit.random.data.StringData;
import org.eclipse.acceleo.traceability.tests.unit.random.operations.AbstractOperation;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceIntIntParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceIntParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceNoParameters;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceStringIntParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceStringParameter;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceStringStringParameter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@SuppressWarnings("nls")
@RunWith(Theories.class)
public class AcceleoTraceabilityRandomTests extends AbstractTraceabilityTest {

	private static int c = 0;

	@BeforeClass
	public static void initialize() {
		c = 0;
	}

	@AfterClass
	public static void conclude() {
		System.err.println("Number of successes: " + c);
	}

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
			.append("\n")
			.append("[query public queryFloat1(eClass : EClass) : Real = 1.7/]\n")
			.append("\n")
			.append("[query public queryFloat2(eClass : EClass) : Real = eClass.name.size() * 1.7/]\n")
			.append("\n")
			.append("[query public queryBoolean1(eClass : EClass) : Boolean = true/]\n")
			.append("\n")
			.append("");
	
	/**################################################################################################
	 *
	 * 									Arguments
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
	@DataPoint public static SequenceData sequenceIntValue = new SequenceData("Sequence{1, 2, 3}");
	@DataPoint public static SequenceData sequenceBooleanValue = new SequenceData("Sequence{true, true, false}");
	@DataPoint public static SequenceData sequenceFloatValue = new SequenceData("Sequence{17.8, 5.77, -3.5}");
	@DataPoint public static SequenceData sequenceEObjectValue = new SequenceData("Sequence{eClass, eClass.eContainer()}");
	@DataPoint public static SequenceData sequenceEObjectPropertyCallValue = new SequenceData("Sequence{eClass.name, eClass.eContainer().oclAsType(EPackage).name}");
	@DataPoint public static SequenceData sequenceEObjectPropertyValue = new SequenceData("eClass.eContainer().oclAsType(EPackage).eClassifiers.name");
	@DataPoint public static SequenceData sequenceSequenceStringValue = new SequenceData("Sequence{Sequence{'a', 'b', 'c'}, Sequence{'c', 'b', 'a'}, Sequence{'b', 'c', 'a'}}");
	@DataPoint public static SequenceData sequenceBagStringValue = new SequenceData("Sequence{Bag{'a', 'b', 'c'}, Bag{'c', 'b', 'a'}, Bag{'b', 'c', 'a'}}");
	@DataPoint public static SequenceData sequenceSetStringValue = new SequenceData("Sequence{Set{'a', 'b', 'c'}, Set{'c', 'b', 'a'}, Set{'b', 'c', 'a'}}");
	@DataPoint public static SequenceData sequenceOrderedSetStringValue = new SequenceData("Sequence{OrderedSet{'a', 'b', 'c'}, OrderedSet{'c', 'b', 'a'}, OrderedSet{'b', 'c', 'a'}}");
	@DataPoint public static SequenceData sequenceStringLiteralAndTemplateValue = new SequenceData("Sequence{'a', 'This is my template: ' + eClass.template1(), eClass.template2()}");
	@DataPoint public static SequenceData sequenceStringLiteralAndTemplateAndStringQueryValue = new SequenceData("Sequence{'a', 'This is my template: ' + eClass.template1(), eClass.template2(), eClass.queryString1()}");
	
	/** Data points Bag */
	@DataPoint public static BagData bagStringValue = new BagData("Bag{'a', 'b', 'c'}");
	@DataPoint public static BagData bagIntValue = new BagData("Bag{1, 2, 3}");
	@DataPoint public static BagData bagBooleanValue = new BagData("Bag{true, false, true}");
	@DataPoint public static BagData bagFloatValue = new BagData("Bag{17.8, 5.77, -3.5}");
	@DataPoint public static BagData bagEObjectValue = new BagData("Bag{eClass, eClass.eContainer()}");
	@DataPoint public static BagData bagEObjectPropertyCallValue = new BagData("Bag{eClass.name, eClass.eContainer().oclAsType(EPackage).name}");
	@DataPoint public static BagData bagEObjectPropertyValue = new BagData("eClass.eContainer().oclAsType(EPackage).eClassifiers.name->asBag()");
	@DataPoint public static BagData bagBagStringValue = new BagData("Bag{Bag{'a', 'b', 'c'}, Bag{'c', 'b', 'a'}, Bag{'b', 'c', 'a'}}");
	@DataPoint public static BagData bagSetStringValue = new BagData("Bag{Set{'a', 'b', 'c'}, Set{'c', 'b', 'a'}, Set{'b', 'c', 'a'}}");
	@DataPoint public static BagData bagSequenceStringValue = new BagData("Bag{Sequence{'a', 'b', 'c'}, Sequence{'c', 'b', 'a'}, Sequence{'b', 'c', 'a'}}");
	@DataPoint public static BagData bagOrderedSetStringValue = new BagData("Bag{OrderedSet{'a', 'b', 'c'}, OrderedSet{'c', 'b', 'a'}, OrderedSet{'b', 'c', 'a'}}");
	@DataPoint public static BagData bagStringLiteralAndTemplateValue = new BagData("Bag{'a', 'This is my template: ' + eClass.template1(), eClass.template2()}");
	@DataPoint public static BagData bagStringLiteralAndTemplateAndStringQueryValue = new BagData("Bag{'a', 'This is my template: ' + eClass.template1(), eClass.template2(), eClass.queryString1()}");
	
	/** Data points Set */
	@DataPoint public static SetData setStringValue = new SetData("Set{'a', 'b', 'c'}");
	@DataPoint public static SetData setIntValue = new SetData("Set{1, 2, 3}");
	@DataPoint public static SetData setBooleanValue = new SetData("Set{true, false, false}");
	@DataPoint public static SetData setFloatValue = new SetData("Set{25.2, 3.14, -3.54}");
	@DataPoint public static SetData setEObjectValue = new SetData("Set{eClass, eClass.eContainer()}");
	@DataPoint public static SetData setEObjectPropertyCallValue = new SetData("Set{eClass.name, eClass.eContainer().oclAsType(EPackage).name}");
	@DataPoint public static SetData setEObjectPropertyValue = new SetData("eClass.eContainer().oclAsType(EPackage).eClassifiers.name->asSet()");
	@DataPoint public static SetData setSetStringValue = new SetData("Set{Set{'a', 'b', 'c'}, Set{'c', 'b', 'a'}, Set{'b', 'c', 'a'}}");
	@DataPoint public static SetData setSequenceStringValue = new SetData("Set{Sequence{'a', 'b', 'c'}, Sequence{'c', 'b', 'a'}, Sequence{'b', 'c', 'a'}}");
	@DataPoint public static SetData setBagStringValue = new SetData("Set{Bag{'a', 'b', 'c'}, Bag{'c', 'b', 'a'}, Bag{'b', 'c', 'a'}}");
	@DataPoint public static SetData setOrderedSetStringValue = new SetData("Set{OrderedSet{'a', 'b', 'c'}, OrderedSet{'c', 'b', 'a'}, OrderedSet{'b', 'c', 'a'}}");
	@DataPoint public static SetData setStringLiteralAndTemplateValue = new SetData("Set{'a', 'This is my template: ' + eClass.template1(), eClass.template2()}");
	@DataPoint public static SetData setStringLiteralAndTemplateAndStringQueryValue = new SetData("Set{'a', 'This is my template: ' + eClass.template1(), eClass.template2(), eClass.queryString1()}");
	
	/** Data points OrderedSet */
	@DataPoint public static OrderedSetData orderedsetStringValue = new OrderedSetData("OrderedSet{'a', 'b', 'c'}");
	@DataPoint public static OrderedSetData orderedsetIntValue = new OrderedSetData("OrderedSet{1, 2, 3}");
	@DataPoint public static OrderedSetData orderedsetBooleanValue = new OrderedSetData("OrderedSet{true, false, true}");
	@DataPoint public static OrderedSetData orderedsetFloatValue = new OrderedSetData("OrderedSet{12.25, 1.44, -3.5}");
	@DataPoint public static OrderedSetData orderedsetEObjectValue = new OrderedSetData("OrderedSet{eClass, eClass.eContainer()}");
	@DataPoint public static OrderedSetData orderedsetEObjectPropertyCallValue = new OrderedSetData("OrderedSet{eClass.name, eClass.eContainer().oclAsType(EPackage).name}");
	@DataPoint public static OrderedSetData orderedsetEObjectPropertyValue = new OrderedSetData("eClass.eContainer().oclAsType(EPackage).eClassifiers.name->asOrderedSet()");
	@DataPoint public static OrderedSetData orderedsetOrderedSetStringValue = new OrderedSetData("OrderedSet{OrderedSet{'a', 'b', 'c'}, OrderedSet{'c', 'b', 'a'}, OrderedSet{'b', 'c', 'a'}}");
	@DataPoint public static OrderedSetData orderedsetSetStringValue = new OrderedSetData("OrderedSet{Set{'a', 'b', 'c'}, Set{'c', 'b', 'a'}, Set{'c', 'b', 'a'}}");
	@DataPoint public static OrderedSetData orderedsetBagStringValue = new OrderedSetData("Bag{OrderedSet{'a', 'b', 'c'}, Bag{'c', 'b', 'a'}, Bag{'c', 'b', 'a'}}");
	@DataPoint public static OrderedSetData orderedsetSequenceStringValue = new OrderedSetData("OrderedSet{Sequence{'a', 'b', 'c'}, Sequence{'c', 'b', 'a'}, Sequence{'c', 'b', 'a'}}");
	@DataPoint public static OrderedSetData orderedsetStringLiteralAndTemplateValue = new OrderedSetData("OrderedSet{'a', 'This is my template: ' + eClass.template1(), eClass.template2()}");
	@DataPoint public static OrderedSetData orderedsetStringLiteralAndTemplateAndStringQueryValue = new OrderedSetData("OrderedSet{'a', 'This is my template: ' + eClass.template1(), eClass.template2(), eClass.queryString1()}");
	
	/** Data points Expression */
	@DataPoint public static ExpressionData expressionToStringSize = new ExpressionData("toString().size()");
	@DataPoint public static ExpressionData expressionToString = new ExpressionData("toString()");
	@DataPoint public static ExpressionData expressionOclIsUndefined = new ExpressionData("oclIsUndefined()");
	@DataPoint public static ExpressionData expressionOclIsKindOfString = new ExpressionData("oclIsKindOf(String)");
	@DataPoint public static ExpressionData expressionOclIsKindOfBoolean = new ExpressionData("oclIsKindOf(Boolean)");
	@DataPoint public static ExpressionData expressionOclIsKindOfInteger = new ExpressionData("oclIsKindOf(Integer)");
	@DataPoint public static ExpressionData expressionOclIsTypeOfString = new ExpressionData("oclIsTypeOf(String)");
	@DataPoint public static ExpressionData expressionOclIsTypeOfBoolean = new ExpressionData("oclIsTypeOf(Boolean)");
	@DataPoint public static ExpressionData expressionOclIsTypeOfInteger = new ExpressionData("oclIsTypeOf(Integer)");
	@DataPoint public static ExpressionData expressionOclIsInvalid = new ExpressionData("oclIsInvalid()");
	
	/**################################################################################################
	 *
	 * 									String Operations
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
	
	@DataPoint public static StringSourceStringIntParameter strtok = new StringSourceStringIntParameter(".strtok");
	
	@DataPoint public static StringSourceIntIntParameter substringOcl = new StringSourceIntIntParameter(".substring");
	
	/**################################################################################################
	 *
	 * 									String Tests
	 *
	 *################################################################################################*/
	
	@Theory
	public void testIsAlpha(StringData source) {
		this.noParameters(isAlpha, source);
	}
	
	@Theory
	public void testIsAlphaNum(StringData source) {
		this.noParameters(isAlphanum, source);
	}
	
	@Theory
	public void testToLowerFirst(StringData source) {
		this.noParameters(toLowerFirst, source);
	}

	@Theory
	public void testToUpperFirst(StringData source) {
		this.noParameters(toUpperFirst, source);
	}
	
	@Theory
	public void testToUpper(StringData source) {
		this.noParameters(toUpper, source);
	}
	
	@Theory
	public void testTrim(StringData source) {
		this.noParameters(trim, source);
	}
	
	@Theory
	public void testIndex(StringData source, StringData param) {
		this.oneParameter(index, source, param);
	}
	
	@Theory
	public void testStrcmp(StringData source, StringData param) {
		this.oneParameter(strcmp, source, param);
	}
	
	@Theory
	public void testContains(StringData source, StringData param) {
		this.oneParameter(contains, source, param);
	}
	
	@Theory
	public void testEndsWith(StringData source, StringData param) {
		this.oneParameter(index, source, param);
	}
	
	@Theory
	public void testEqualsIgnoreCase(StringData source, StringData param) {
		this.oneParameter(equalsIgnoreCase, source, param);
	}
	
	@Theory
	public void testLastIndex(StringData source, StringData param) {
		this.oneParameter(lastIndex, source, param);
	}
	
	@Theory
	public void testMatches(StringData source, StringData param) {
		this.oneParameter(matches, source, param);
	}
	
	@Theory
	public void testStartsWith(StringData source, StringData param) {
		this.oneParameter(startsWith, source, param);
	}
	
	@Theory
	public void testTokenize(StringData source, StringData param) {
		this.oneParameter(tokenize, source, param);
	}
	
	@Theory
	public void testConcat(StringData source, StringData param) {
		this.oneParameter(concat, source, param);
	}
	
	@Theory
	public void testFirst(StringData source, IntData param) {
		this.oneParameter(first, source, param);
	}
	
	@Theory
	public void testLast(StringData source, IntData param) {
		this.oneParameter(last, source, param);
	}
	
	@Theory
	public void testSubstring(StringData source, IntData param) {
		this.oneParameter(substring, source, param);
	}
	
	@Theory
	public void testSubstitute(StringData source, StringData param1, StringData param2) {
		this.twoParameters(substitute, source, param1, param2);
	}
	
	@Theory
	public void testSubstituteAll(StringData source, StringData param1, StringData param2) {
		this.twoParameters(substituteAll, source, param1, param2);
	}
	
	@Theory
	public void testReplace(StringData source, StringData param1, StringData param2) {
		this.twoParameters(replace, source, param1, param2);
	}
	
	@Theory
	public void testReplaceAll(StringData source, StringData param1, StringData param2) {
		this.twoParameters(replaceAll, source, param1, param2);
	}
	
	@Theory
	public void testStrtok(StringData source, StringData param1, IntData param2) {
		this.twoParameters(strtok, source, param1, param2);
	}

	@Theory
	public void testSubstringOcl(StringData source, IntData param1, IntData param2) {
		this.twoParameters(substringOcl, source, param1, param2);
	}
	
	
	/**################################################################################################
	 *
	 * 									Collection Operations
	 *
	 *################################################################################################*/
	
	@DataPoint public static CollectionSourceNoParameter asBag = new CollectionSourceNoParameter("->asBag");
	@DataPoint public static CollectionSourceNoParameter asSet = new CollectionSourceNoParameter("->asSet");
	@DataPoint public static CollectionSourceNoParameter asOrderedSet = new CollectionSourceNoParameter("->asOrderedSet");
	@DataPoint public static CollectionSourceNoParameter asSequence = new CollectionSourceNoParameter("->asSequence");
	@DataPoint public static CollectionSourceNoParameter flatten = new CollectionSourceNoParameter("->flatten");
	@DataPoint public static CollectionSourceNoParameter isEmpty = new CollectionSourceNoParameter("->isEmpty");
	@DataPoint public static CollectionSourceNoParameter notEmpty = new CollectionSourceNoParameter("->notEmpty");
	@DataPoint public static CollectionSourceNoParameter sizeCollection = new CollectionSourceNoParameter("->size");
	@DataPoint public static CollectionSourceNoParameter sum = new CollectionSourceNoParameter("->sum");
	
	@DataPoint public static CollectionSourceObjectParameter count = new CollectionSourceObjectParameter("->count");
	@DataPoint public static CollectionSourceObjectParameter excludes = new CollectionSourceObjectParameter("->excludes");
	@DataPoint public static CollectionSourceObjectParameter excluding = new CollectionSourceObjectParameter("->excluding");
	@DataPoint public static CollectionSourceObjectParameter includes = new CollectionSourceObjectParameter("->includes");
	@DataPoint public static CollectionSourceObjectParameter including = new CollectionSourceObjectParameter("->including");
	
	@DataPoint public static CollectionSourceObjectParameter any = new CollectionSourceObjectParameter("->any");
	@DataPoint public static CollectionSourceObjectParameter collect = new CollectionSourceObjectParameter("->collect");
	@DataPoint public static CollectionSourceObjectParameter forAll = new CollectionSourceObjectParameter("->forAll");
	@DataPoint public static CollectionSourceObjectParameter isUnique = new CollectionSourceObjectParameter("->isUnique");
	@DataPoint public static CollectionSourceObjectParameter select = new CollectionSourceObjectParameter("->select");
	@DataPoint public static CollectionSourceObjectParameter reject = new CollectionSourceObjectParameter("->reject");
	@DataPoint public static CollectionSourceObjectParameter sortedBy = new CollectionSourceObjectParameter("->sortedBy");
	
	@DataPoint public static CollectionSourceCollectionParameter excludesAll = new CollectionSourceCollectionParameter("->excludesAll");
	@DataPoint public static CollectionSourceCollectionParameter includesAll = new CollectionSourceCollectionParameter("->includesAll");
	@DataPoint public static CollectionSourceCollectionParameter product = new CollectionSourceCollectionParameter("->product");
		
	/**################################################################################################
	 *
	 * 									Collection Tests
	 *
	 *################################################################################################*/
	
	@Theory
	public void testAsBag(CollectionData source) {
		this.noParameters(asBag, source);
	}

	@Theory
	public void testAsOrderedSet(CollectionData source) {
		this.noParameters(asOrderedSet, source);
	}
	
	@Theory
	public void testAsSet(CollectionData source) {
		this.noParameters(asSet, source);
	}
	
	@Theory
	public void testAsSequence(CollectionData source) {
		this.noParameters(asSequence, source);
	}
	
	@Theory
	public void testFlatten(CollectionData source) {
		this.noParameters(flatten, source);
	}
	
	@Theory
	public void testIsEmpty(CollectionData source) {
		this.noParameters(isEmpty, source);
	}
	
	@Theory
	public void testNotEmpty(CollectionData source) {
		this.noParameters(notEmpty, source);
	}
	
	@Theory
	public void testSizeCollection(CollectionData source) {
		this.noParameters(sizeCollection, source);
	}
	
	@Theory
	public void testSum(CollectionData source) {
		this.noParameters(sum, source);
	}
	
	@Theory
	public void testCount(CollectionData source, ObjectData param) {
		this.oneParameter(count, source, param);
	}
	
	@Theory
	public void testExcludes(CollectionData source, ObjectData param) {
		this.oneParameter(excludes, source, param);
	}
	
	@Theory
	public void testExcluding(CollectionData source, ObjectData param) {
		this.oneParameter(excluding, source, param);
	}
	
	@Theory
	public void testIncludes(CollectionData source, ObjectData param) {
		this.oneParameter(includes, source, param);
	}
	
	@Theory
	public void testIncluding(CollectionData source, ObjectData param) {
		this.oneParameter(including, source, param);
	}
	
	@Theory
	public void testAny(CollectionData source, ExpressionData param) {
		this.oneParameter(any, source, param);
	}

	@Theory
	public void testCollect(CollectionData source, ExpressionData param) {
		this.oneParameter(collect, source, param);
	}
	
	@Theory
	public void testIsUnique(CollectionData source, ExpressionData param) {
		this.oneParameter(isUnique, source, param);
	}
	
	@Theory
	public void testForAll(CollectionData source, ExpressionData param) {
		this.oneParameter(forAll, source, param);
	}
	
	@Theory
	public void testSelect(CollectionData source, ExpressionData param) {
		this.oneParameter(select, source, param);
	}
	
	@Theory
	public void testReject(CollectionData source, ExpressionData param) {
		this.oneParameter(reject, source, param);
	}
	
	@Theory
	public void testSortedBy(CollectionData source, ExpressionData param) {
		this.oneParameter(sortedBy, source, param);
	}
	
	@Theory
	public void testExcludesAll(CollectionData source, CollectionData param) {
		this.oneParameter(excludesAll, source, param);
	}
	
	@Theory
	public void testIncludesAll(CollectionData source, CollectionData param) {
		this.oneParameter(includesAll, source, param);
	}
	
	@Theory
	public void testProduct(CollectionData source, CollectionData param) {
		this.oneParameter(product, source, param);
	}
	
	/**################################################################################################
	 *
	 * 									Utility Methods
	 *
	 *################################################################################################*/
	
	private void noParameters(AbstractOperation operation, ObjectData source) {
		c++;
		StringBuffer moduleBuffer = this.moduleBufferBegin.append(source.getValue()).append(
				operation.getValue());
		moduleBuffer.append("()").append(this.moduleBufferEnd);
		AcceleoTraceabilityListener acceleoTraceabilityListener = this.parseAndGenerate(
				"data/random/randomtheories.mtl", moduleBuffer, "main", "data/random/model.ecore", true);
		List<GeneratedFile> generatedFiles = acceleoTraceabilityListener.getGeneratedFiles();
		assertThat(generatedFiles.size(), is(4));
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertThat(source.getValue() + operation.getValue() + "()",generatedRegions.size(), not(equalTo(0)));
		}
	}
	
	private void oneParameter(AbstractOperation operation, ObjectData source, ObjectData param) {
		c++;
		StringBuffer moduleBuffer = this.moduleBufferBegin.append(source.getValue()).append(
				operation.getValue());
		moduleBuffer.append("(").append(param.getValue()).append(")").append(this.moduleBufferEnd);
		AcceleoTraceabilityListener acceleoTraceabilityListener = this.parseAndGenerate(
				"data/random/randomtheories.mtl", moduleBuffer, "main", "data/random/model.ecore", true);
		List<GeneratedFile> generatedFiles = acceleoTraceabilityListener.getGeneratedFiles();
		assertThat(generatedFiles.size(), is(4));
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertThat(source.getValue() + operation.getValue() + "(" + param.getValue() + ")",
					generatedRegions.size(), not(equalTo(0)));
		}
	}
	
	private void oneParameter(AbstractOperation operation, ObjectData source, ExpressionData param) {
		c++;
		StringBuffer moduleBuffer = this.moduleBufferBegin.append(source.getValue()).append(
				operation.getValue());
		moduleBuffer.append("(").append(param.getValue()).append(")").append(this.moduleBufferEnd);
		AcceleoTraceabilityListener acceleoTraceabilityListener = this.parseAndGenerate(
				"data/random/randomtheories.mtl", moduleBuffer, "main", "data/random/model.ecore", true);
		List<GeneratedFile> generatedFiles = acceleoTraceabilityListener.getGeneratedFiles();
		assertThat(generatedFiles.size(), is(4));
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertThat(source.getValue() + operation.getValue() + "(" + param.getValue() + ")",
					generatedRegions.size(), not(equalTo(0)));
		}
	}
	
	private void twoParameters(AbstractOperation operation, ObjectData source, ObjectData param1, ObjectData param2) {
		c++;
		StringBuffer moduleBuffer = this.moduleBufferBegin.append(source.getValue()).append(
				operation.getValue());
		moduleBuffer.append("(").append(param1.getValue()).append(", ").append(param2.getValue()).append(")")
				.append(this.moduleBufferEnd);
		AcceleoTraceabilityListener acceleoTraceabilityListener = this.parseAndGenerate(
				"data/random/randomtheories.mtl", moduleBuffer, "main", "data/random/model.ecore", true);
		List<GeneratedFile> generatedFiles = acceleoTraceabilityListener.getGeneratedFiles();
		assertThat(generatedFiles.size(), is(4));
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertThat(source.getValue() + operation.getValue() + "(" + param1.getValue() + "," + param2.getValue()+ ")",
					generatedRegions.size(), not(equalTo(0)));
		}
	}
}
