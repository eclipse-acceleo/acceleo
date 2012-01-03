/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.unit;

import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Comment} class.
 * 
 * @generated
 */
public class CommentTest extends AbstractCstTest {

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Comment comment = CstFactory.eINSTANCE.createComment();
		comment.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(comment.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)comment.getStartPosition())
				.intValue());

		comment.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)comment.getStartPosition()).intValue());
		assertEquals(((Integer)comment.getStartPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertTrue(comment.eIsSet(feature));

		comment.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)comment.getStartPosition())
				.intValue());
		assertEquals(((Integer)comment.getStartPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertFalse(comment.eIsSet(feature));

		comment.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)comment.getStartPosition()).intValue());
		assertEquals(((Integer)comment.getStartPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertTrue(comment.eIsSet(feature));

		comment.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)comment.getStartPosition())
				.intValue());
		assertEquals(((Integer)comment.getStartPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertFalse(comment.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Comment comment = CstFactory.eINSTANCE.createComment();
		comment.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(comment.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)comment.getEndPosition())
				.intValue());

		comment.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)comment.getEndPosition()).intValue());
		assertEquals(((Integer)comment.getEndPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertTrue(comment.eIsSet(feature));

		comment.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)comment.getEndPosition())
				.intValue());
		assertEquals(((Integer)comment.getEndPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertFalse(comment.eIsSet(feature));

		comment.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)comment.getEndPosition()).intValue());
		assertEquals(((Integer)comment.getEndPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertTrue(comment.eIsSet(feature));

		comment.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)comment.getEndPosition())
				.intValue());
		assertEquals(((Integer)comment.getEndPosition()).intValue(), ((Integer)comment.eGet(feature))
				.intValue());
		assertFalse(comment.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Name();
		Comment comment = CstFactory.eINSTANCE.createComment();
		comment.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(comment.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), comment.getName());

		comment.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, comment.getName());
		assertEquals(comment.getName(), comment.eGet(feature));
		assertTrue(comment.eIsSet(feature));

		comment.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), comment.getName());
		assertEquals(comment.getName(), comment.eGet(feature));
		assertFalse(comment.eIsSet(feature));

		comment.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, comment.getName());
		assertEquals(comment.getName(), comment.eGet(feature));
		assertTrue(comment.eIsSet(feature));

		comment.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), comment.getName());
		assertEquals(comment.getName(), comment.eGet(feature));
		assertFalse(comment.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Visibility();
		Comment comment = CstFactory.eINSTANCE.createComment();
		comment.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.VisibilityKind visibilityValue = (org.eclipse.acceleo.parser.cst.VisibilityKind)feature
				.getDefaultValue();
		for (org.eclipse.acceleo.parser.cst.VisibilityKind aVisibilityKind : org.eclipse.acceleo.parser.cst.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(comment.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), comment.getVisibility());

		comment.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, comment.getVisibility());
		assertEquals(comment.getVisibility(), comment.eGet(feature));
		assertTrue(comment.eIsSet(feature));

		comment.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), comment.getVisibility());
		assertEquals(comment.getVisibility(), comment.eGet(feature));
		assertFalse(comment.eIsSet(feature));

		comment.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, comment.getVisibility());
		assertEquals(comment.getVisibility(), comment.eGet(feature));
		assertTrue(comment.eIsSet(feature));

		comment.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), comment.getVisibility());
		assertEquals(comment.getVisibility(), comment.eGet(feature));
		assertFalse(comment.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getComment_Body();
		Comment comment = CstFactory.eINSTANCE.createComment();
		comment.eAdapters().add(new MockEAdapter());
		java.lang.String bodyValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(comment.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), comment.getBody());

		comment.setBody(bodyValue);
		assertTrue(notified);
		notified = false;
		assertEquals(bodyValue, comment.getBody());
		assertEquals(comment.getBody(), comment.eGet(feature));
		assertTrue(comment.eIsSet(feature));

		comment.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), comment.getBody());
		assertEquals(comment.getBody(), comment.eGet(feature));
		assertFalse(comment.eIsSet(feature));

		comment.eSet(feature, bodyValue);
		assertTrue(notified);
		notified = false;
		assertEquals(bodyValue, comment.getBody());
		assertEquals(comment.getBody(), comment.eGet(feature));
		assertTrue(comment.eIsSet(feature));

		comment.setBody(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), comment.getBody());
		assertEquals(comment.getBody(), comment.eGet(feature));
		assertFalse(comment.eIsSet(feature));
	}

}
