package org.eclipse.acceleo.model.tests.unit;

import junit.framework.TestCase;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.util.MtlAdapterFactory;

/*
 * TODO This is but a skeleton for the tests of MtlAdapterFactory.
 * Set as "generated NOT" and override each test if you overrode the default generated
 * behavior.
 */
/**
 * Tests the behavior of the {@link MtlAdapterFactory generated adapter factory}
 * for package mtl.
 * 
 * @generated
 */
public class MtlAdapterFactoryTest extends TestCase {
	/**
	 * Ensures that creating adapters for {@link Module} can be done through the
	 * AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModuleAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createModuleAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createModule()));
	}

	/**
	 * Ensures that creating adapters for {@link ModuleElement} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModuleElementAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createModuleElementAdapter());
		assertNull(adapterFactory
				.createAdapter(new org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl() {
					// empty implementation
				}));
	}

	/**
	 * Ensures that creating adapters for {@link TemplateExpression} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateExpressionAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createTemplateExpressionAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createTemplateExpression()));
	}

	/**
	 * Ensures that creating adapters for {@link Block} can be done through the
	 * AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link InitSection} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateInitSectionAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createInitSectionAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createInitSection()));
	}

	/**
	 * Ensures that creating adapters for {@link Template} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createTemplateAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createTemplate()));
	}

	/**
	 * Ensures that creating adapters for {@link TemplateInvocation} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateInvocationAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createTemplateInvocationAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createTemplateInvocation()));
	}

	/**
	 * Ensures that creating adapters for {@link Query} can be done through the
	 * AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateQueryAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createQueryAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createQuery()));
	}

	/**
	 * Ensures that creating adapters for {@link QueryInvocation} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateQueryInvocationAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createQueryInvocationAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createQueryInvocation()));
	}

	/**
	 * Ensures that creating adapters for {@link ProtectedAreaBlock} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateProtectedAreaBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createProtectedAreaBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link ForBlock} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateForBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createForBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createForBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link IfBlock} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateIfBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createIfBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createIfBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link LetBlock} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateLetBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createLetBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createLetBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link FileBlock} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateFileBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createFileBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createFileBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link TraceBlock} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTraceBlockAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createTraceBlockAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createTraceBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link Macro} can be done through the
	 * AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateMacroAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createMacroAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createMacro()));
	}

	/**
	 * Ensures that creating adapters for {@link MacroInvocation} can be done
	 * through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateMacroInvocationAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createMacroInvocationAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createMacroInvocation()));
	}

	/**
	 * Ensures that creating adapters for {@link TypedModel} can be done through
	 * the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTypedModelAdapter() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertNull(adapterFactory.createTypedModelAdapter());
		assertNull(adapterFactory.createAdapter(MtlFactory.eINSTANCE
				.createTypedModel()));
	}

	/**
	 * Ensures that the AdapterFactory knows all classes of package mtl.
	 * 
	 * @generated
	 */
	public void testIsFactoryForType() {
		MtlAdapterFactory adapterFactory = new MtlAdapterFactory();
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createModule()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createTemplateExpression()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createInitSection()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createTemplate()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createTemplateInvocation()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createQuery()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createQueryInvocation()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createForBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createIfBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createLetBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createFileBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createTraceBlock()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createMacro()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createMacroInvocation()));
		assertTrue(adapterFactory.isFactoryForType(MtlFactory.eINSTANCE
				.createTypedModel()));
		assertTrue(adapterFactory.isFactoryForType(MtlPackage.eINSTANCE));
		org.eclipse.emf.ecore.EClass eClass = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
				.createEClass();
		assertFalse(adapterFactory.isFactoryForType(eClass));
		assertFalse(adapterFactory.isFactoryForType(new Object()));
	}
}
