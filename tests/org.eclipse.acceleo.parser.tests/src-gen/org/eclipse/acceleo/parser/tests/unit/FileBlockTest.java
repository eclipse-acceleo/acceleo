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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link FileBlock} class.
 * 
 * @generated
 */
public class FileBlockTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(fileBlock.eIsSet(feature));
		assertTrue(fileBlock.getBody().isEmpty());

		fileBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(fileBlock.getBody().contains(bodyValue));
		assertSame(fileBlock.getBody(), fileBlock.eGet(feature));
		assertSame(fileBlock.getBody(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(fileBlock.getBody().isEmpty());
		assertSame(fileBlock.getBody(), fileBlock.eGet(feature));
		assertSame(fileBlock.getBody(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(fileBlock.getBody().contains(bodyValue));
		assertSame(fileBlock.getBody(), fileBlock.eGet(feature));
		assertSame(fileBlock.getBody(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(fileBlock.eIsSet(feature));
		assertNull(fileBlock.getInit());

		fileBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, fileBlock.getInit());
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature));
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(fileBlock.getInit());
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature));
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, fileBlock.getInit());
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature));
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, fileBlock.getInit());
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature));
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(fileBlock.getInit());
		assertSame(feature.getDefaultValue(), fileBlock.getInit());
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature));
		assertSame(fileBlock.getInit(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>fileUrl</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testFileUrl() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getFileBlock_FileUrl();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression fileUrlValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(fileBlock.eIsSet(feature));
		assertNull(fileBlock.getFileUrl());

		fileBlock.setFileUrl(fileUrlValue);
		assertTrue(notified);
		notified = false;
		assertSame(fileUrlValue, fileBlock.getFileUrl());
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature));
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(fileBlock.getFileUrl());
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature));
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.setFileUrl(fileUrlValue);
		assertTrue(notified);
		notified = false;
		assertSame(fileUrlValue, fileBlock.getFileUrl());
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature));
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, fileUrlValue);
		assertTrue(notified);
		notified = false;
		assertSame(fileUrlValue, fileBlock.getFileUrl());
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature));
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setFileUrl(null);
		assertTrue(notified);
		notified = false;
		assertNull(fileBlock.getFileUrl());
		assertSame(feature.getDefaultValue(), fileBlock.getFileUrl());
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature));
		assertSame(fileBlock.getFileUrl(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>uniqId</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testUniqId() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getFileBlock_UniqId();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression uniqIdValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(fileBlock.eIsSet(feature));
		assertNull(fileBlock.getUniqId());

		fileBlock.setUniqId(uniqIdValue);
		assertTrue(notified);
		notified = false;
		assertSame(uniqIdValue, fileBlock.getUniqId());
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature));
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(fileBlock.getUniqId());
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature));
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.setUniqId(uniqIdValue);
		assertTrue(notified);
		notified = false;
		assertSame(uniqIdValue, fileBlock.getUniqId());
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature));
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, uniqIdValue);
		assertTrue(notified);
		notified = false;
		assertSame(uniqIdValue, fileBlock.getUniqId());
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature));
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature, false));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setUniqId(null);
		assertTrue(notified);
		notified = false;
		assertNull(fileBlock.getUniqId());
		assertSame(feature.getDefaultValue(), fileBlock.getUniqId());
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature));
		assertSame(fileBlock.getUniqId(), fileBlock.eGet(feature, false));
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(fileBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getStartPosition())
				.intValue());

		fileBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)fileBlock.getStartPosition()).intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)fileBlock.getStartPosition()).intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(fileBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getEndPosition())
				.intValue());

		fileBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)fileBlock.getEndPosition()).intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)fileBlock.getEndPosition()).intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature))
				.intValue());
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>openMode</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testOpenMode() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getFileBlock_OpenMode();
		FileBlock fileBlock = CstFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.OpenModeKind openModeValue = (org.eclipse.acceleo.parser.cst.OpenModeKind)feature
				.getDefaultValue();
		for (org.eclipse.acceleo.parser.cst.OpenModeKind aOpenModeKind : org.eclipse.acceleo.parser.cst.OpenModeKind.VALUES) {
			if (openModeValue.getValue() != aOpenModeKind.getValue()) {
				openModeValue = aOpenModeKind;
				break;
			}
		}

		assertFalse(fileBlock.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), fileBlock.getOpenMode());

		fileBlock.setOpenMode(openModeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(openModeValue, fileBlock.getOpenMode());
		assertEquals(fileBlock.getOpenMode(), fileBlock.eGet(feature));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), fileBlock.getOpenMode());
		assertEquals(fileBlock.getOpenMode(), fileBlock.eGet(feature));
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, openModeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(openModeValue, fileBlock.getOpenMode());
		assertEquals(fileBlock.getOpenMode(), fileBlock.eGet(feature));
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setOpenMode(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), fileBlock.getOpenMode());
		assertEquals(fileBlock.getOpenMode(), fileBlock.eGet(feature));
		assertFalse(fileBlock.eIsSet(feature));
	}

}
