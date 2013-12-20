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
package org.eclipse.acceleo.engine.tests.unit.evaluation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.helper.OCLHelper;
import org.junit.Before;

/**
 * Abstract class for the Acceleo evaluation visitor unit tests.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
public abstract class AbstractAcceleoEvaluationVisitorTest extends AbstractAcceleoTest {
	/** The evaluation visitor that is to be unit tested. */
	protected AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> evaluationVisitor;

	/** Factory instantiated at setUp for the evaluation environment. */
	protected AcceleoEnvironmentFactory factory;

	/** This can be used by subclasses to create OCLExpressions. */
	protected OCL ocl;

	/** Instance of the "oclInvalid" standard library object. */
	protected Object invalidObject;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		// Reusing the generic engine test template. This is only used for setup.
		return "data/GenericEngine/generic_engine.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "EvaluationVisitor"; //$NON-NLS-1$
	}

	/**
	 * Wraps the System line separator in an OCL "StringLiteralExp".
	 * 
	 * @return The created OCLExpression.
	 */
	protected OCLExpression createOCLLineSeparator() {
		return createOCLStringLiteralExpression(System.getProperty("line.separator")); //$NON-NLS-1$
	}

	/**
	 * Wraps the given String literal in an OCL "StringLiteralExp". This is mainly used for special characters
	 * such as the carriage return or line feed.
	 * 
	 * @return The created OCLExpression.
	 */
	protected OCLExpression createOCLStringLiteralExpression(String literal) {
		final StringLiteralExp literalExpression = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE
				.createStringLiteralExp();
		literalExpression.setStringSymbol(literal);
		return literalExpression;
	}

	/**
	 * Creates an OCLExpression for <code>expression</code>. Expression context should be set beforehand
	 * through <code>ocl.getEvaluationEnvironment().add(String, Object)</code>.
	 * 
	 * @param expression
	 *            The expression that should be parsed as an OCLExpression.
	 * @return The parsed OCLExpression.
	 */
	protected OCLExpression createOCLExpression(String expression) {
		return createOCLExpression(expression, EcorePackage.eINSTANCE.getEClassifier());
	}

	/**
	 * Creates an OCLExpression for <code>expression</code> on object <code>self</code>. Expression context
	 * should be set beforehand through <code>ocl.getEvaluationEnvironment().add(String, Object)</code>.
	 * 
	 * @param expression
	 *            The expression that should be parsed as an OCLExpression.
	 * @param self
	 *            Parsing context of the expression.
	 * @return The parsed OCLExpression.
	 */
	protected OCLExpression createOCLExpression(String expression, EClassifier self) {
		final OCLHelper<EClassifier, EOperation, EStructuralFeature, Constraint> helper = ocl
				.createOCLHelper();
		helper.setContext(self);
		try {
			return (OCLExpression)helper.createQuery(expression);
		} catch (ParserException e) {
			fail("Unable to parse expression \"" + expression + '"' + " on classifier " + self); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// As this is called via JUnit, we cannot be here
		return null;
	}

	/**
	 * This can be used to create a dummy template in a dummy module so that the visitor will be able to set
	 * error messages accurately.
	 * 
	 * @return A dummy template.
	 */
	protected Template getDummyTemplate() {
		final Module dummy = MtlFactory.eINSTANCE.createModule();
		dummy.setName("testModule"); //$NON-NLS-1$
		final Template template = MtlFactory.eINSTANCE.createTemplate();
		template.setName("testTemplate"); //$NON-NLS-1$
		dummy.getOwnedModuleElement().add(template);
		return template;
	}

	/**
	 * returns the generation preview for this test.
	 * 
	 * @return The generation preview for this test.
	 */
	protected Map<String, String> getPreview() {
		return factory.getEvaluationPreview();
	}

	/**
	 * This will create a package that can be used for the tests. It will be structured as such :
	 * 
	 * <pre>
	 * EPackage package
	 * |----EClass ClassA
	 * |    |----EAttribute attributeA1 : EString
	 * |    |----EAttribute attributeA2 : EInt
	 * |    `----EReference referenceA1 : ClassB
	 * |----EClass ClassB
	 * |    |----EAttribute attributeB1 : EBoolean
	 * |    |----EAttribute attributeB2 : EString
	 * |    `----EReference referenceB1 : EObject
	 * `----EClass ClassC -&gt; ClassA, ENamedElement
	 * </pre>
	 * 
	 * @return The test package.
	 */
	protected EPackage getTestPackage() {
		final EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		final EClass classA = EcoreFactory.eINSTANCE.createEClass();
		final EClass classB = EcoreFactory.eINSTANCE.createEClass();
		final EClass classC = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attributeA1 = EcoreFactory.eINSTANCE.createEAttribute();
		final EAttribute attributeA2 = EcoreFactory.eINSTANCE.createEAttribute();
		final EAttribute attributeB1 = EcoreFactory.eINSTANCE.createEAttribute();
		final EAttribute attributeB2 = EcoreFactory.eINSTANCE.createEAttribute();
		final EReference referenceA1 = EcoreFactory.eINSTANCE.createEReference();
		final EReference referenceB1 = EcoreFactory.eINSTANCE.createEReference();
		pack.setName("package"); //$NON-NLS-1$
		classA.setName("ClassA"); //$NON-NLS-1$
		classB.setName("ClassB"); //$NON-NLS-1$
		classC.setName("ClassC"); //$NON-NLS-1$
		attributeA1.setName("attributeA1"); //$NON-NLS-1$
		attributeA2.setName("attributeA2"); //$NON-NLS-1$
		attributeB1.setName("attributeB1"); //$NON-NLS-1$
		attributeB2.setName("attributeB2"); //$NON-NLS-1$
		referenceA1.setName("referenceA1"); //$NON-NLS-1$
		referenceB1.setName("referenceB1"); //$NON-NLS-1$
		pack.getEClassifiers().add(classA);
		pack.getEClassifiers().add(classB);
		pack.getEClassifiers().add(classC);
		classA.getEStructuralFeatures().add(attributeA1);
		classA.getEStructuralFeatures().add(attributeA2);
		classA.getEStructuralFeatures().add(referenceA1);
		classB.getEStructuralFeatures().add(attributeB1);
		classB.getEStructuralFeatures().add(attributeB2);
		classB.getEStructuralFeatures().add(referenceB1);
		classC.getESuperTypes().add(classA);
		classC.getESuperTypes().add(EcorePackage.eINSTANCE.getENamedElement());
		attributeA1.setEType(EcorePackage.eINSTANCE.getEString());
		attributeA2.setEType(EcorePackage.eINSTANCE.getEInt());
		attributeB1.setEType(EcorePackage.eINSTANCE.getEBoolean());
		attributeB2.setEType(EcorePackage.eINSTANCE.getEString());
		referenceA1.setEType(classB);
		referenceB1.setEType(EcorePackage.eINSTANCE.getEObject());
		return pack;
	}

	/**
	 * Returns the parent Template of a given block.
	 * 
	 * @param block
	 *            The block we seek the containing Template of.
	 * @return The parent Template of a given block.
	 */
	protected Template getParentTemplate(EObject block) {
		EObject container = block.eContainer();
		while (container != null && !(container instanceof Template)) {
			container = container.eContainer();
		}
		return (Template)container;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		super.setUp();
		// only used for initialization
		this.init("EvaluationVisitor"); //$NON-NLS-1$
		factory = new AcceleoEnvironmentFactory(generationRoot, module,
				new ArrayList<IAcceleoTextGenerationListener>(), new AcceleoPropertiesLookup(),
				previewStrategy, new BasicMonitor());
		ocl = OCL.newInstance(factory);
		evaluationVisitor = (AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>)factory
				.createEvaluationVisitor(ocl.getEnvironment(), ocl.getEvaluationEnvironment(), ocl
						.getExtentMap());
		invalidObject = ((AcceleoEnvironment)ocl.getEnvironment()).getOCLStandardLibraryReflection()
				.getInvalid();
	}
}
