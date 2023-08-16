/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link AQLUtils}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLUtilsTests {

	@Test
	public void computeAvailableTypesNullURIsFalseFalseFalse() {
		final List<String> res = AQLUtils.computeAvailableTypes(null, false, false, false);

		assertEquals(0, res.size());
	}

	@Test
	public void computeAvailableTypesNullURIsFalseFalseTrue() {
		final List<String> res = AQLUtils.computeAvailableTypes(null, false, false, true);

		assertEquals(0, res.size());
	}

	@Test
	public void computeAvailableTypesNullURIsFalseTrueTrue() {
		final List<String> res = AQLUtils.computeAvailableTypes(null, false, true, true);

		assertEquals(0, res.size());
	}

	@Test
	public void computeAvailableTypesNullURIsTrueTrueTrue() {
		final List<String> res = AQLUtils.computeAvailableTypes(null, true, true, true);

		assertEquals(12, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("OrderedSet(Boolean)", res.get(2));
		assertEquals("OrderedSet(Integer)", res.get(3));
		assertEquals("OrderedSet(Real)", res.get(4));
		assertEquals("OrderedSet(String)", res.get(5));
		assertEquals("Real", res.get(6));
		assertEquals("Sequence(Boolean)", res.get(7));
		assertEquals("Sequence(Integer)", res.get(8));
		assertEquals("Sequence(Real)", res.get(9));
		assertEquals("Sequence(String)", res.get(10));
		assertEquals("String", res.get(11));
	}

	@Test
	public void computeAvailableTypesNullURIsTrueTrueFalse() {
		final List<String> res = AQLUtils.computeAvailableTypes(null, true, true, false);

		assertEquals(8, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("Real", res.get(2));
		assertEquals("Sequence(Boolean)", res.get(3));
		assertEquals("Sequence(Integer)", res.get(4));
		assertEquals("Sequence(Real)", res.get(5));
		assertEquals("Sequence(String)", res.get(6));
		assertEquals("String", res.get(7));
	}

	@Test
	public void computeAvailableTypesNullURIsTrueFalseTrue() {
		final List<String> res = AQLUtils.computeAvailableTypes(null, true, false, true);

		assertEquals(8, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("OrderedSet(Boolean)", res.get(2));
		assertEquals("OrderedSet(Integer)", res.get(3));
		assertEquals("OrderedSet(Real)", res.get(4));
		assertEquals("OrderedSet(String)", res.get(5));
		assertEquals("Real", res.get(6));
		assertEquals("String", res.get(7));
	}

	@Test
	public void computeAvailableTypesEmptyURIsFalseFalseFalse() {
		final List<String> uris = new ArrayList<>();
		final List<String> res = AQLUtils.computeAvailableTypes(uris, false, false, false);

		assertEquals(0, res.size());
	}

	@Test
	public void computeAvailableTypesEmptyURIsFalseFalseTrue() {
		final List<String> uris = new ArrayList<>();
		final List<String> res = AQLUtils.computeAvailableTypes(uris, false, false, true);

		assertEquals(0, res.size());
	}

	@Test
	public void computeAvailableTypesEmptyURIsFalseTrueTrue() {
		final List<String> uris = new ArrayList<>();
		final List<String> res = AQLUtils.computeAvailableTypes(uris, false, true, true);

		assertEquals(0, res.size());
	}

	@Test
	public void computeAvailableTypesEmptyURIsTrueTrueTrue() {
		final List<String> uris = new ArrayList<>();
		final List<String> res = AQLUtils.computeAvailableTypes(uris, true, true, true);

		assertEquals(12, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("OrderedSet(Boolean)", res.get(2));
		assertEquals("OrderedSet(Integer)", res.get(3));
		assertEquals("OrderedSet(Real)", res.get(4));
		assertEquals("OrderedSet(String)", res.get(5));
		assertEquals("Real", res.get(6));
		assertEquals("Sequence(Boolean)", res.get(7));
		assertEquals("Sequence(Integer)", res.get(8));
		assertEquals("Sequence(Real)", res.get(9));
		assertEquals("Sequence(String)", res.get(10));
		assertEquals("String", res.get(11));
	}

	@Test
	public void computeAvailableTypesEmptyURIsTrueTrueFalse() {
		final List<String> uris = new ArrayList<>();
		final List<String> res = AQLUtils.computeAvailableTypes(uris, true, true, false);

		assertEquals(8, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("Real", res.get(2));
		assertEquals("Sequence(Boolean)", res.get(3));
		assertEquals("Sequence(Integer)", res.get(4));
		assertEquals("Sequence(Real)", res.get(5));
		assertEquals("Sequence(String)", res.get(6));
		assertEquals("String", res.get(7));
	}

	@Test
	public void computeAvailableTypesEmptyURIsTrueFalseTrue() {
		final List<String> uris = new ArrayList<>();
		final List<String> res = AQLUtils.computeAvailableTypes(uris, true, false, true);

		assertEquals(8, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("OrderedSet(Boolean)", res.get(2));
		assertEquals("OrderedSet(Integer)", res.get(3));
		assertEquals("OrderedSet(Real)", res.get(4));
		assertEquals("OrderedSet(String)", res.get(5));
		assertEquals("Real", res.get(6));
		assertEquals("String", res.get(7));
	}

	@Test
	public void computeAvailableTypesURIsFalseFalseFalse() {
		final List<String> uris = new ArrayList<>();
		uris.add(EcorePackage.eINSTANCE.getNsURI());
		final List<String> res = AQLUtils.computeAvailableTypes(uris, false, false, false);

		assertEquals(53, res.size());
		assertEquals("ecore::EAnnotation", res.get(0));
		assertEquals("ecore::EAttribute", res.get(1));
		assertEquals("ecore::EBigDecimal", res.get(2));
		assertEquals("ecore::EBigInteger", res.get(3));
		assertEquals("ecore::EBoolean", res.get(4));
		assertEquals("ecore::EBooleanObject", res.get(5));
		assertEquals("ecore::EByte", res.get(6));
		assertEquals("ecore::EByteArray", res.get(7));
		assertEquals("ecore::EByteObject", res.get(8));
		assertEquals("ecore::EChar", res.get(9));
		assertEquals("ecore::ECharacterObject", res.get(10));
		assertEquals("ecore::EClass", res.get(11));
		assertEquals("ecore::EClassifier", res.get(12));
		assertEquals("ecore::EDataType", res.get(13));
		assertEquals("ecore::EDate", res.get(14));
		assertEquals("ecore::EDiagnosticChain", res.get(15));
		assertEquals("ecore::EDouble", res.get(16));
		assertEquals("ecore::EDoubleObject", res.get(17));
		assertEquals("ecore::EEList", res.get(18));
		assertEquals("ecore::EEnum", res.get(19));
		assertEquals("ecore::EEnumLiteral", res.get(20));
		assertEquals("ecore::EEnumerator", res.get(21));
		assertEquals("ecore::EFactory", res.get(22));
		assertEquals("ecore::EFeatureMap", res.get(23));
		assertEquals("ecore::EFeatureMapEntry", res.get(24));
		assertEquals("ecore::EFloat", res.get(25));
		assertEquals("ecore::EFloatObject", res.get(26));
		assertEquals("ecore::EGenericType", res.get(27));
		assertEquals("ecore::EInt", res.get(28));
		assertEquals("ecore::EIntegerObject", res.get(29));
		assertEquals("ecore::EInvocationTargetException", res.get(30));
		assertEquals("ecore::EJavaClass", res.get(31));
		assertEquals("ecore::EJavaObject", res.get(32));
		assertEquals("ecore::ELong", res.get(33));
		assertEquals("ecore::ELongObject", res.get(34));
		assertEquals("ecore::EMap", res.get(35));
		assertEquals("ecore::EModelElement", res.get(36));
		assertEquals("ecore::ENamedElement", res.get(37));
		assertEquals("ecore::EObject", res.get(38));
		assertEquals("ecore::EOperation", res.get(39));
		assertEquals("ecore::EPackage", res.get(40));
		assertEquals("ecore::EParameter", res.get(41));
		assertEquals("ecore::EReference", res.get(42));
		assertEquals("ecore::EResource", res.get(43));
		assertEquals("ecore::EResourceSet", res.get(44));
		assertEquals("ecore::EShort", res.get(45));
		assertEquals("ecore::EShortObject", res.get(46));
		assertEquals("ecore::EString", res.get(47));
		assertEquals("ecore::EStringToStringMapEntry", res.get(48));
		assertEquals("ecore::EStructuralFeature", res.get(49));
		assertEquals("ecore::ETreeIterator", res.get(50));
		assertEquals("ecore::ETypeParameter", res.get(51));
		assertEquals("ecore::ETypedElement", res.get(52));
	}

	@Test
	public void computeAvailableTypesURIsTrueTrueTrue() {
		final List<String> uris = new ArrayList<>();
		uris.add(EcorePackage.eINSTANCE.getNsURI());
		final List<String> res = AQLUtils.computeAvailableTypes(uris, true, true, true);

		assertEquals(171, res.size());
		assertEquals("Boolean", res.get(0));
		assertEquals("Integer", res.get(1));
		assertEquals("OrderedSet(Boolean)", res.get(2));
		assertEquals("OrderedSet(Integer)", res.get(3));
		assertEquals("OrderedSet(Real)", res.get(4));
		assertEquals("OrderedSet(String)", res.get(5));
		assertEquals("OrderedSet(ecore::EAnnotation)", res.get(6));
		assertEquals("OrderedSet(ecore::EAttribute)", res.get(7));
		assertEquals("OrderedSet(ecore::EBigDecimal)", res.get(8));
		assertEquals("OrderedSet(ecore::EBigInteger)", res.get(9));
		assertEquals("OrderedSet(ecore::EBoolean)", res.get(10));
		assertEquals("OrderedSet(ecore::EBooleanObject)", res.get(11));
		assertEquals("OrderedSet(ecore::EByte)", res.get(12));
		assertEquals("OrderedSet(ecore::EByteArray)", res.get(13));
		assertEquals("OrderedSet(ecore::EByteObject)", res.get(14));
		assertEquals("OrderedSet(ecore::EChar)", res.get(15));
		assertEquals("OrderedSet(ecore::ECharacterObject)", res.get(16));
		assertEquals("OrderedSet(ecore::EClass)", res.get(17));
		assertEquals("OrderedSet(ecore::EClassifier)", res.get(18));
		assertEquals("OrderedSet(ecore::EDataType)", res.get(19));
		assertEquals("OrderedSet(ecore::EDate)", res.get(20));
		assertEquals("OrderedSet(ecore::EDiagnosticChain)", res.get(21));
		assertEquals("OrderedSet(ecore::EDouble)", res.get(22));
		assertEquals("OrderedSet(ecore::EDoubleObject)", res.get(23));
		assertEquals("OrderedSet(ecore::EEList)", res.get(24));
		assertEquals("OrderedSet(ecore::EEnum)", res.get(25));
		assertEquals("OrderedSet(ecore::EEnumLiteral)", res.get(26));
		assertEquals("OrderedSet(ecore::EEnumerator)", res.get(27));
		assertEquals("OrderedSet(ecore::EFactory)", res.get(28));
		assertEquals("OrderedSet(ecore::EFeatureMap)", res.get(29));
		assertEquals("OrderedSet(ecore::EFeatureMapEntry)", res.get(30));
		assertEquals("OrderedSet(ecore::EFloat)", res.get(31));
		assertEquals("OrderedSet(ecore::EFloatObject)", res.get(32));
		assertEquals("OrderedSet(ecore::EGenericType)", res.get(33));
		assertEquals("OrderedSet(ecore::EInt)", res.get(34));
		assertEquals("OrderedSet(ecore::EIntegerObject)", res.get(35));
		assertEquals("OrderedSet(ecore::EInvocationTargetException)", res.get(36));
		assertEquals("OrderedSet(ecore::EJavaClass)", res.get(37));
		assertEquals("OrderedSet(ecore::EJavaObject)", res.get(38));
		assertEquals("OrderedSet(ecore::ELong)", res.get(39));
		assertEquals("OrderedSet(ecore::ELongObject)", res.get(40));
		assertEquals("OrderedSet(ecore::EMap)", res.get(41));
		assertEquals("OrderedSet(ecore::EModelElement)", res.get(42));
		assertEquals("OrderedSet(ecore::ENamedElement)", res.get(43));
		assertEquals("OrderedSet(ecore::EObject)", res.get(44));
		assertEquals("OrderedSet(ecore::EOperation)", res.get(45));
		assertEquals("OrderedSet(ecore::EPackage)", res.get(46));
		assertEquals("OrderedSet(ecore::EParameter)", res.get(47));
		assertEquals("OrderedSet(ecore::EReference)", res.get(48));
		assertEquals("OrderedSet(ecore::EResource)", res.get(49));
		assertEquals("OrderedSet(ecore::EResourceSet)", res.get(50));
		assertEquals("OrderedSet(ecore::EShort)", res.get(51));
		assertEquals("OrderedSet(ecore::EShortObject)", res.get(52));
		assertEquals("OrderedSet(ecore::EString)", res.get(53));
		assertEquals("OrderedSet(ecore::EStringToStringMapEntry)", res.get(54));
		assertEquals("OrderedSet(ecore::EStructuralFeature)", res.get(55));
		assertEquals("OrderedSet(ecore::ETreeIterator)", res.get(56));
		assertEquals("OrderedSet(ecore::ETypeParameter)", res.get(57));
		assertEquals("OrderedSet(ecore::ETypedElement)", res.get(58));
		assertEquals("Real", res.get(59));
		assertEquals("Sequence(Boolean)", res.get(60));
		assertEquals("Sequence(Integer)", res.get(61));
		assertEquals("Sequence(Real)", res.get(62));
		assertEquals("Sequence(String)", res.get(63));
		assertEquals("Sequence(ecore::EAnnotation)", res.get(64));
		assertEquals("Sequence(ecore::EAttribute)", res.get(65));
		assertEquals("Sequence(ecore::EBigDecimal)", res.get(66));
		assertEquals("Sequence(ecore::EBigInteger)", res.get(67));
		assertEquals("Sequence(ecore::EBoolean)", res.get(68));
		assertEquals("Sequence(ecore::EBooleanObject)", res.get(69));
		assertEquals("Sequence(ecore::EByte)", res.get(70));
		assertEquals("Sequence(ecore::EByteArray)", res.get(71));
		assertEquals("Sequence(ecore::EByteObject)", res.get(72));
		assertEquals("Sequence(ecore::EChar)", res.get(73));
		assertEquals("Sequence(ecore::ECharacterObject)", res.get(74));
		assertEquals("Sequence(ecore::EClass)", res.get(75));
		assertEquals("Sequence(ecore::EClassifier)", res.get(76));
		assertEquals("Sequence(ecore::EDataType)", res.get(77));
		assertEquals("Sequence(ecore::EDate)", res.get(78));
		assertEquals("Sequence(ecore::EDiagnosticChain)", res.get(79));
		assertEquals("Sequence(ecore::EDouble)", res.get(80));
		assertEquals("Sequence(ecore::EDoubleObject)", res.get(81));
		assertEquals("Sequence(ecore::EEList)", res.get(82));
		assertEquals("Sequence(ecore::EEnum)", res.get(83));
		assertEquals("Sequence(ecore::EEnumLiteral)", res.get(84));
		assertEquals("Sequence(ecore::EEnumerator)", res.get(85));
		assertEquals("Sequence(ecore::EFactory)", res.get(86));
		assertEquals("Sequence(ecore::EFeatureMap)", res.get(87));
		assertEquals("Sequence(ecore::EFeatureMapEntry)", res.get(88));
		assertEquals("Sequence(ecore::EFloat)", res.get(89));
		assertEquals("Sequence(ecore::EFloatObject)", res.get(90));
		assertEquals("Sequence(ecore::EGenericType)", res.get(91));
		assertEquals("Sequence(ecore::EInt)", res.get(92));
		assertEquals("Sequence(ecore::EIntegerObject)", res.get(93));
		assertEquals("Sequence(ecore::EInvocationTargetException)", res.get(94));
		assertEquals("Sequence(ecore::EJavaClass)", res.get(95));
		assertEquals("Sequence(ecore::EJavaObject)", res.get(96));
		assertEquals("Sequence(ecore::ELong)", res.get(97));
		assertEquals("Sequence(ecore::ELongObject)", res.get(98));
		assertEquals("Sequence(ecore::EMap)", res.get(99));
		assertEquals("Sequence(ecore::EModelElement)", res.get(100));
		assertEquals("Sequence(ecore::ENamedElement)", res.get(101));
		assertEquals("Sequence(ecore::EObject)", res.get(102));
		assertEquals("Sequence(ecore::EOperation)", res.get(103));
		assertEquals("Sequence(ecore::EPackage)", res.get(104));
		assertEquals("Sequence(ecore::EParameter)", res.get(105));
		assertEquals("Sequence(ecore::EReference)", res.get(106));
		assertEquals("Sequence(ecore::EResource)", res.get(107));
		assertEquals("Sequence(ecore::EResourceSet)", res.get(108));
		assertEquals("Sequence(ecore::EShort)", res.get(109));
		assertEquals("Sequence(ecore::EShortObject)", res.get(110));
		assertEquals("Sequence(ecore::EString)", res.get(111));
		assertEquals("Sequence(ecore::EStringToStringMapEntry)", res.get(112));
		assertEquals("Sequence(ecore::EStructuralFeature)", res.get(113));
		assertEquals("Sequence(ecore::ETreeIterator)", res.get(114));
		assertEquals("Sequence(ecore::ETypeParameter)", res.get(115));
		assertEquals("Sequence(ecore::ETypedElement)", res.get(116));
		assertEquals("String", res.get(117));
		assertEquals("ecore::EAnnotation", res.get(118));
		assertEquals("ecore::EAttribute", res.get(119));
		assertEquals("ecore::EBigDecimal", res.get(120));
		assertEquals("ecore::EBigInteger", res.get(121));
		assertEquals("ecore::EBoolean", res.get(122));
		assertEquals("ecore::EBooleanObject", res.get(123));
		assertEquals("ecore::EByte", res.get(124));
		assertEquals("ecore::EByteArray", res.get(125));
		assertEquals("ecore::EByteObject", res.get(126));
		assertEquals("ecore::EChar", res.get(127));
		assertEquals("ecore::ECharacterObject", res.get(128));
		assertEquals("ecore::EClass", res.get(129));
		assertEquals("ecore::EClassifier", res.get(130));
		assertEquals("ecore::EDataType", res.get(131));
		assertEquals("ecore::EDate", res.get(132));
		assertEquals("ecore::EDiagnosticChain", res.get(133));
		assertEquals("ecore::EDouble", res.get(134));
		assertEquals("ecore::EDoubleObject", res.get(135));
		assertEquals("ecore::EEList", res.get(136));
		assertEquals("ecore::EEnum", res.get(137));
		assertEquals("ecore::EEnumLiteral", res.get(138));
		assertEquals("ecore::EEnumerator", res.get(139));
		assertEquals("ecore::EFactory", res.get(140));
		assertEquals("ecore::EFeatureMap", res.get(141));
		assertEquals("ecore::EFeatureMapEntry", res.get(142));
		assertEquals("ecore::EFloat", res.get(143));
		assertEquals("ecore::EFloatObject", res.get(144));
		assertEquals("ecore::EGenericType", res.get(145));
		assertEquals("ecore::EInt", res.get(146));
		assertEquals("ecore::EIntegerObject", res.get(147));
		assertEquals("ecore::EInvocationTargetException", res.get(148));
		assertEquals("ecore::EJavaClass", res.get(149));
		assertEquals("ecore::EJavaObject", res.get(150));
		assertEquals("ecore::ELong", res.get(151));
		assertEquals("ecore::ELongObject", res.get(152));
		assertEquals("ecore::EMap", res.get(153));
		assertEquals("ecore::EModelElement", res.get(154));
		assertEquals("ecore::ENamedElement", res.get(155));
		assertEquals("ecore::EObject", res.get(156));
		assertEquals("ecore::EOperation", res.get(157));
		assertEquals("ecore::EPackage", res.get(158));
		assertEquals("ecore::EParameter", res.get(159));
		assertEquals("ecore::EReference", res.get(160));
		assertEquals("ecore::EResource", res.get(161));
		assertEquals("ecore::EResourceSet", res.get(162));
		assertEquals("ecore::EShort", res.get(163));
		assertEquals("ecore::EShortObject", res.get(164));
		assertEquals("ecore::EString", res.get(165));
		assertEquals("ecore::EStringToStringMapEntry", res.get(166));
		assertEquals("ecore::EStructuralFeature", res.get(167));
		assertEquals("ecore::ETreeIterator", res.get(168));
		assertEquals("ecore::ETypeParameter", res.get(169));
		assertEquals("ecore::ETypedElement", res.get(170));
	}

	@Test
	public void getAqlTypeStringNull() {
		final String res = AQLUtils.getAqlTypeString(null);

		assertEquals("", res);
	}

	@Test
	public void getAqlTypeStringString() {
		final IType type = new ClassType(null, String.class);

		final String res = AQLUtils.getAqlTypeString(type);

		assertEquals("String", res);
	}

	@Test
	public void getAqlTypeStringInteger() {
		final IType type = new ClassType(null, Integer.class);

		final String res = AQLUtils.getAqlTypeString(type);

		assertEquals("Integer", res);
	}

	@Test
	public void getAqlTypeStringReal() {
		final IType type = new ClassType(null, Double.class);

		final String res = AQLUtils.getAqlTypeString(type);

		assertEquals("Real", res);
	}

	@Test
	public void getAqlTypeStringBoolean() {
		final IType type = new ClassType(null, Boolean.class);

		final String res = AQLUtils.getAqlTypeString(type);

		assertEquals("Boolean", res);
	}

	@Test
	public void getAqlTypeStringEClassifier() {
		final IType type = new EClassifierType(null, EcorePackage.eINSTANCE.getEPackage());

		final String res = AQLUtils.getAqlTypeString(type);

		assertEquals("ecore::EPackage", res);
	}

	@Test
	public void getAqlTypeStringSequence() {
		final IType type = new EClassifierType(null, EcorePackage.eINSTANCE.getEPackage());

		final String res = AQLUtils.getAqlTypeString(new SequenceType(null, type));

		assertEquals("Sequence(ecore::EPackage)", res);
	}

	@Test
	public void getAqlTypeStringOrderedSet() {
		final IType type = new EClassifierType(null, EcorePackage.eINSTANCE.getEPackage());

		final String res = AQLUtils.getAqlTypeString(new SetType(null, type));

		assertEquals("OrderedSet(ecore::EPackage)", res);
	}

	@Test
	public void getAqlTypeStringEClassifierSetLiteral() {
		final Set<EClassifier> eClassifiers = new LinkedHashSet<>();
		eClassifiers.add(EcorePackage.eINSTANCE.getEPackage());
		eClassifiers.add(EcorePackage.eINSTANCE.getEString());
		eClassifiers.add(EcorePackage.eINSTANCE.getEOperation());
		final IType type = new EClassifierSetLiteralType(null, eClassifiers);

		final String res = AQLUtils.getAqlTypeString(type);

		assertEquals("{ecore::EPackage | ecore::EString | ecore::EOperation}", res);
	}

}
