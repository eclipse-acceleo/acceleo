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
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.util.MtlSwitch;

/*
 * TODO This is but a skeleton for the tests of MtlSwitch.
 * Set as "generated NOT" and override each test if you overrode the default generated
 * behavior.
 */
/**
 * Tests the behavior of the {@link MtlSwitch generated switch} for package mtl.
 * 
 * @generated
 */
public class MtlSwitchTest extends TestCase {
	/**
	 * Ensures that the generated switch knows {@link Module}.
	 * 
	 * @generated
	 */
	public void testCaseModule() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseModule(MtlFactory.eINSTANCE.createModule()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createModule()));
	}

	/**
	 * Ensures that the generated switch knows {@link ModuleElement}.
	 * 
	 * @generated
	 */
	public void testCaseModuleElement() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch
				.caseModuleElement(new org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl() {
					// empty implementation
				}));
		assertNull(mtlswitch
				.doSwitch(new org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl() {
					// empty implementation
				}));
	}

	/**
	 * Ensures that the generated switch knows {@link TemplateExpression}.
	 * 
	 * @generated
	 */
	public void testCaseTemplateExpression() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseTemplateExpression(MtlFactory.eINSTANCE
				.createTemplateExpression()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE
				.createTemplateExpression()));
	}

	/**
	 * Ensures that the generated switch knows {@link Block}.
	 * 
	 * @generated
	 */
	public void testCaseBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseBlock(MtlFactory.eINSTANCE.createBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link InitSection}.
	 * 
	 * @generated
	 */
	public void testCaseInitSection() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseInitSection(MtlFactory.eINSTANCE
				.createInitSection()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createInitSection()));
	}

	/**
	 * Ensures that the generated switch knows {@link Template}.
	 * 
	 * @generated
	 */
	public void testCaseTemplate() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch
				.caseTemplate(MtlFactory.eINSTANCE.createTemplate()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createTemplate()));
	}

	/**
	 * Ensures that the generated switch knows {@link TemplateInvocation}.
	 * 
	 * @generated
	 */
	public void testCaseTemplateInvocation() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseTemplateInvocation(MtlFactory.eINSTANCE
				.createTemplateInvocation()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE
				.createTemplateInvocation()));
	}

	/**
	 * Ensures that the generated switch knows {@link Query}.
	 * 
	 * @generated
	 */
	public void testCaseQuery() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseQuery(MtlFactory.eINSTANCE.createQuery()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createQuery()));
	}

	/**
	 * Ensures that the generated switch knows {@link QueryInvocation}.
	 * 
	 * @generated
	 */
	public void testCaseQueryInvocation() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseQueryInvocation(MtlFactory.eINSTANCE
				.createQueryInvocation()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE
				.createQueryInvocation()));
	}

	/**
	 * Ensures that the generated switch knows {@link ProtectedAreaBlock}.
	 * 
	 * @generated
	 */
	public void testCaseProtectedAreaBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseProtectedAreaBlock(MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link ForBlock}.
	 * 
	 * @generated
	 */
	public void testCaseForBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch
				.caseForBlock(MtlFactory.eINSTANCE.createForBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createForBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link IfBlock}.
	 * 
	 * @generated
	 */
	public void testCaseIfBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseIfBlock(MtlFactory.eINSTANCE.createIfBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createIfBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link LetBlock}.
	 * 
	 * @generated
	 */
	public void testCaseLetBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch
				.caseLetBlock(MtlFactory.eINSTANCE.createLetBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createLetBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link FileBlock}.
	 * 
	 * @generated
	 */
	public void testCaseFileBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseFileBlock(MtlFactory.eINSTANCE
				.createFileBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createFileBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link TraceBlock}.
	 * 
	 * @generated
	 */
	public void testCaseTraceBlock() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseTraceBlock(MtlFactory.eINSTANCE
				.createTraceBlock()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createTraceBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link Macro}.
	 * 
	 * @generated
	 */
	public void testCaseMacro() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseMacro(MtlFactory.eINSTANCE.createMacro()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createMacro()));
	}

	/**
	 * Ensures that the generated switch knows {@link MacroInvocation}.
	 * 
	 * @generated
	 */
	public void testCaseMacroInvocation() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseMacroInvocation(MtlFactory.eINSTANCE
				.createMacroInvocation()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE
				.createMacroInvocation()));
	}

	/**
	 * Ensures that the generated switch knows {@link TypedModel}.
	 * 
	 * @generated
	 */
	public void testCaseTypedModel() {
		MtlSwitch<?> mtlswitch = new MtlSwitch<Object>();
		assertNull(mtlswitch.caseTypedModel(MtlFactory.eINSTANCE
				.createTypedModel()));
		assertNull(mtlswitch.doSwitch(MtlFactory.eINSTANCE.createTypedModel()));
	}
}
