package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.FileBlock;

/**
 * Tests the behavior of the {@link FileBlock} class.
 * 
 * @generated
 */
public class FileBlockTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
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
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

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
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getFileBlock_FileUrl();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression fileUrlValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

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
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getFileBlock_UniqId();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression uniqIdValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

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
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(fileBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getStartPosition()).intValue());

		fileBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)fileBlock.getStartPosition()).intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getStartPosition()).intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)fileBlock.getStartPosition()).intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getStartPosition()).intValue());
		assertEquals(((Integer)fileBlock.getStartPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(fileBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getEndPosition()).intValue());

		fileBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)fileBlock.getEndPosition()).intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getEndPosition()).intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertFalse(fileBlock.eIsSet(feature));

		fileBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)fileBlock.getEndPosition()).intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertTrue(fileBlock.eIsSet(feature));

		fileBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)fileBlock.getEndPosition()).intValue());
		assertEquals(((Integer)fileBlock.getEndPosition()).intValue(), ((Integer)fileBlock.eGet(feature)).intValue());
		assertFalse(fileBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>openMode</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testOpenMode() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getFileBlock_OpenMode();
		FileBlock fileBlock = MtlFactory.eINSTANCE.createFileBlock();
		fileBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.OpenModeKind openModeValue = (org.eclipse.acceleo.model.mtl.OpenModeKind)feature.getDefaultValue();
		for (org.eclipse.acceleo.model.mtl.OpenModeKind aOpenModeKind : org.eclipse.acceleo.model.mtl.OpenModeKind.VALUES) {
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

