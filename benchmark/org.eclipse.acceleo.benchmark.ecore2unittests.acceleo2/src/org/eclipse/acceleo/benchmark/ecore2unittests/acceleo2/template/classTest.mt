<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.commonEcore
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.classTestHelper
%>

<%script type="GenClass" name="filename"%>
src-gen/<%testPackage().replaceAll("\.", "/")%>/unit/<%ecoreClass.name.toU1Case()%>Test.java

<%script type="GenClass" name="classTest" file="<%filename%>"%>
package <%testPackage()%>.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import <%modelPackage()%>.<%genPackage.modelFactoryClassName()%>;
import <%modelPackage()%>.<%ecoreClass.name.toU1Case()%>;

/**
 * Tests the behavior of the {@link <%ecoreClass.name.toU1Case()%>} class.
 * 
 * @generated
 */
public class <%ecoreClass.name.toU1Case()%>Test extends Abstract<%genPackage.prefix.toU1Case()%>Test {
	<%generateMultiValuedReference.sep("\n")%>

	<%generateUniqueReference.sep("\n")%>

	<%generateUniqueAttribute.sep("\n")%>
	
	<%generateUnchangeableFeature.sep("\n")%>
}

<%script type="GenClass" name="generateMultiValuedReference"%>
<%for (ecoreClass.eAllReferences[many && changeable && !derived && eContainingClass.ePackage.nsURI != "http://www.eclipse.org/emf/2002/Ecore"]) {%>
<%current(1).multiValuedReference(current)%>
<%}%>

<%script type="GenClass" name="multiValuedReference"%>
/**
	 * Tests the behavior of reference <code><%args(0).name%></code>'s accessors.
	 * 
	 * @generated
 	 */
	public void test<%args(0).name.toU1Case()%>() {
		EStructuralFeature feature = <%args(0).eContainingClass.qualifiedModelPackageClassName()%>.eINSTANCE.get<%args(0).eContainingClass.name.toU1Case()%>_<%args(0).name.toU1Case()%>();
		<%ecoreClass.name.toU1Case()%> <%ecoreClass.name.toL1Case()%> = <%genPackage.modelFactoryClassName()%>.eINSTANCE.create<%ecoreClass.name.toU1Case()%>();
		<%ecoreClass.name.toL1Case()%>.eAdapters().add(new MockEAdapter());
		<%args(0).eType.qualifiedGeneratedName()%> <%args(0).name%>Value = <%if (args(0).eType.filter("EClass").instantiatableSubClass().nFirst() == null) {%>new <%args(0).eType.qualifiedGeneratedImplementation()%>(){}<%}else{%><%args(0).eType.filter("EClass").instantiatableSubClass().qualifiedModelFactoryClassName()%>.eINSTANCE.create<%args(0).eType.filter("EClass").instantiatableSubClass().name%>()<%}%>;
		List<<%args(0).eType.qualifiedGeneratedName()%>> list<%args(0).name.toU1Case()%> = new ArrayList<<%args(0).eType.qualifiedGeneratedName()%>>(1);
		list<%args(0).name.toU1Case()%>.add(<%args(0).name%>Value);

		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		assertTrue(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>().isEmpty());

		<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>().add(<%args(0).name%>Value);
		assertTrue(notified);
		notified = false;
		assertTrue(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>().contains(<%args(0).name%>Value));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertTrue(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>

		<%ecoreClass.name.toL1Case()%>.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>().isEmpty());
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertFalse(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>

		<%ecoreClass.name.toL1Case()%>.eSet(feature, list<%args(0).name.toU1Case()%>);
		assertTrue(notified);
		notified = false;
		assertTrue(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>().contains(<%args(0).name%>Value));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertTrue(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>
	}

<%script type="GenClass" name="generateUniqueReference"%>
<%for (ecoreClass.eAllReferences[!many && changeable && !derived && eContainingClass.ePackage.nsURI != "http://www.eclipse.org/emf/2002/Ecore"]) {%>
<%current(1).uniqueReference(current)%>
<%}%>

<%script type="GenClass" name="uniqueReference"%>
/**
	 * Tests the behavior of reference <code><%args(0).name%></code>'s accessors.
	 * 
	 * @generated
 	 */
	public void test<%args(0).name.toU1Case()%>() {
		EStructuralFeature feature = <%args(0).eContainingClass.qualifiedModelPackageClassName()%>.eINSTANCE.get<%args(0).eContainingClass.name.toU1Case()%>_<%args(0).name.toU1Case()%>();
		<%ecoreClass.name.toU1Case()%> <%ecoreClass.name.toL1Case()%> = <%genPackage.modelFactoryClassName()%>.eINSTANCE.create<%ecoreClass.name.toU1Case()%>();
		<%ecoreClass.name.toL1Case()%>.eAdapters().add(new MockEAdapter());
		<%args(0).eType.qualifiedGeneratedName()%> <%args(0).name%>Value = <%if (args(0).eType.filter("EClass").instantiatableSubClass().nFirst == null) {%>new <%args(0).eType.qualifiedGeneratedName()%>(){}<%}else{%><%args(0).eType.filter("EClass").instantiatableSubClass().qualifiedModelFactoryClassName()%>.eINSTANCE.create<%args(0).eType.filter("EClass").instantiatableSubClass().name%>()<%}%>;

		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		assertNull(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());

		<%ecoreClass.name.toL1Case()%>.set<%args(0).name.toU1Case()%>(<%args(0).name%>Value);
		assertTrue(notified);
		notified = false;
		assertSame(<%args(0).name%>Value, <%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertTrue(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>

		<%ecoreClass.name.toL1Case()%>.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertFalse(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>

		<%ecoreClass.name.toL1Case()%>.set<%args(0).name.toU1Case()%>(<%args(0).name%>Value);
		assertTrue(notified);
		notified = false;
		assertSame(<%args(0).name%>Value, <%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertTrue(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>

		<%ecoreClass.name.toL1Case()%>.eSet(feature, <%args(0).name%>Value);
		assertTrue(notified);
		notified = false;
		assertSame(<%args(0).name%>Value, <%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertTrue(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>

		<%ecoreClass.name.toL1Case()%>.set<%args(0).name.toU1Case()%>(null);
		assertTrue(notified);
		notified = false;
		assertNull(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());
		assertSame(feature.getDefaultValue(), <%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>());
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature));
		assertSame(<%ecoreClass.name.toL1Case()%>.get<%args(0).name.toU1Case()%>(), <%ecoreClass.name.toL1Case()%>.eGet(feature, false));
		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		[<%if (args(0).eOpposite) {%>
		<%for (args(0).eOpposite) {%>
		assertFalse(<%args(0).name%>Value.get<%name.toU1Case()%>()<%if (many) {%>.contains(<%ecoreClass.name.toL1Case()%>)<%}else{%> == <%ecoreClass.name.toL1Case()%><%}%>);
		<%}%>
		<%}%>
	}

<%script type="GenClass" name="generateUniqueAttribute"%>
<%for (ecoreClass.eAllAttributes[!many && changeable && !derived && eContainingClass.ePackage.nsURI != "http://www.eclipse.org/emf/2002/Ecore"]) {%>
<%current(1).uniqueAttribute(current)%>
<%}%>

<%script type="GenClass" name="uniqueAttribute"%>
/**
	 * Tests the behavior of attribute <code><%args(0).name%></code>'s accessors.
	 * 
	 * @generated
 	 */
	public void test<%args(0).name.toU1Case()%>() {
		EStructuralFeature feature = <%args(0).eContainingClass.qualifiedModelPackageClassName()%>.eINSTANCE.get<%args(0).eContainingClass.name.toU1Case()%>_<%args(0).name.toU1Case()%>();
		<%ecoreClass.name.toU1Case()%> <%ecoreClass.name.toL1Case()%> = <%genPackage.modelFactoryClassName()%>.eINSTANCE.create<%ecoreClass.name.toU1Case()%>();
		<%ecoreClass.name.toL1Case()%>.eAdapters().add(new MockEAdapter());
		<%if (args(0).eType.filter("EEnum")) {%>
		<%args(0).eType.qualifiedGeneratedName()%> <%args(0).name%>Value = (<%args(0).eType.qualifiedGeneratedName()%>)feature.getDefaultValue();
		for (<%args(0).eType.qualifiedGeneratedName()%> a<%args(0).eType.name%> : <%args(0).eType.qualifiedGeneratedName()%>.VALUES) {
			if (<%args(0).name%>Value.getValue() != a<%args(0).eType.name%>.getValue()) {
				<%args(0).name%>Value = a<%args(0).eType.name%>;
				break;
			}
		}
		<%}else{%>
		<%args(0).eType.instanceClassName%> <%args(0).name%>Value = <%if (args(0).eType.isPrimitive()) {%>get<%args(0).eType.instanceClassName.toU1Case()%>DistinctFromDefault(feature)<%}else{%>(<%args(0).eType.instanceClassName%>)getValueDistinctFromDefault(feature)<%}%>;
		<%}%>

		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		assertEquals(<%args(0).defaultValueGetter()%>, <%ecoreClass.valueGetter(args(0))%>);

		<%ecoreClass.name.toL1Case()%>.set<%args(0).name.toU1Case()%>(<%args(0).name%>Value);
		assertTrue(notified);
		notified = false;
		assertEquals(<%args(0).name%>Value, <%ecoreClass.valueGetter(args(0))%>);
		assertEquals(<%ecoreClass.valueGetter(args(0))%>, <%ecoreClass.reflectiveGetter(args(0))%>);
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));

		<%ecoreClass.name.toL1Case()%>.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(<%args(0).defaultValueGetter()%>, <%ecoreClass.valueGetter(args(0))%>);
		assertEquals(<%ecoreClass.valueGetter(args(0))%>, <%ecoreClass.reflectiveGetter(args(0))%>);
		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));

		<%ecoreClass.name.toL1Case()%>.eSet(feature, <%args(0).name%>Value);
		assertTrue(notified);
		notified = false;
		assertEquals(<%args(0).name%>Value, <%ecoreClass.valueGetter(args(0))%>);
		assertEquals(<%ecoreClass.valueGetter(args(0))%>, <%ecoreClass.reflectiveGetter(args(0))%>);
		assertTrue(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));

		<%ecoreClass.name.toL1Case()%>.set<%args(0).name.toU1Case()%>(<%if (args(0).eType.isPrimitive()) {%>((<%if (args(0).eType.instanceClassName == "int") {%>Integer<%}else{%><%args(0).eType.instanceClassName.toU1Case()%><%}%>)feature.getDefaultValue()).<%args(0).eType.instanceClassName%>Value()<%}else{%>null<%}%>);
		assertTrue(notified);
		notified = false;
		assertEquals(<%args(0).defaultValueGetter()%>, <%ecoreClass.valueGetter(args(0))%>);
		assertEquals(<%ecoreClass.valueGetter(args(0))%>, <%ecoreClass.reflectiveGetter(args(0))%>);
		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
	}

<%script type="GenClass" name="generateUnchangeableFeature"%>
<%for (ecoreClass.eAllStructuralFeatures[(derived || !changeable) && eContainingClass.ePackage.nsURI != "http://www.eclipse.org/emf/2002/Ecore"]) {%>
<%current(1).unchangeableFeature(current)%>
<%}%>

<%script type="GenClass" name="unchangeableFeature"%>
/**
	 * Tests the behavior of <%if (args(0).filter("EReference")) {%>reference<%}else{%>attribute<%}%> <code><%args(0).name%></code>'s getter.
	 * 
	 * @generated
 	 */
	public void test<%args(0).name.toU1Case()%>() {
		EStructuralFeature feature = <%args(0).eContainingClass.qualifiedModelPackageClassName()%>.eINSTANCE.get<%args(0).eContainingClass.name.toU1Case()%>_<%args(0).name.toU1Case()%>();
		<%ecoreClass.name.toU1Case()%> <%ecoreClass.name.toL1Case()%> = <%genPackage.modelFactoryClassName()%>.eINSTANCE.create<%ecoreClass.name.toU1Case()%>();
		<%ecoreClass.name.toL1Case()%>.eAdapters().add(new MockEAdapter());

		assertFalse(<%ecoreClass.name.toL1Case()%>.eIsSet(feature));
		<%if (!args(0).derived) {%>
		assertSame(<%if (args(0).filter("EReference")) {%><%args(0).filter("EReference").defaultValueGetter()%><%}else{%><%args(0).filter("EAttribute").defaultValueGetter()%><%}%>, <%ecoreClass.name.toL1Case()%>.<%args(0).getterPrefix()%><%args(0).name.toU1Case()%>());
		<%}else{%>
		try {
			<%ecoreClass.name.toL1Case()%>.<%args(0).getterPrefix()%><%args(0).name.toU1Case()%>();
			<%ecoreClass.reflectiveGetter(args(0))%>;
		} catch (UnsupportedOperationException e) {
			fail("Getter for derived feature <%args(0).name%> hasn't been implemented.");
		}
		<%}%>

		// TODO This is <%if (args(0).derived) {%>a derived<%}else{%>an unchangeable<%}%> feature. Set as "generated NOT" and implement test
	}