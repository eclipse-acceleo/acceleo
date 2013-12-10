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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.acceleo.common.utils.AcceleoCollections;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.SetMultimap;

/**
 * This will test the behavior of file template invocations evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorTemplateInvocationTest extends AbstractAcceleoEvaluationVisitorTest {
	/** This is the file name that will be used for the dummy file block. */
	private static final String FILE_NAME = "validFile.url"; //$NON-NLS-1$

	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/** Constant output of the template block tested here. */
	private static final String TEMPLATE_OUTPUT = "templateBodyOutput"; //$NON-NLS-1$

	/** This will need to be set through {@link #mapTemplates()} before any call to the visitor. */
	private SetMultimap<String, Template> templates;

	/**
	 * Tests the evaluation of a template invocation which body evaluates to null.
	 */
	@Test
	public void testTemplateInvocationNullExpression() {
		final TemplateInvocation invocation = getDummyTemplateInvocation();
		invocation.getArgument().add(createOCLExpression("self", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);
		invocation.getDefinition().getBody().clear();
		invocation.getDefinition().getBody().add(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$
		mapTemplates();

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a template invocation with a null result.", OUTPUT //$NON-NLS-1$
				+ OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a template invocation which body evaluates to an undefined value (through an
	 * NPE).
	 */
	@Test
	public void testTemplateInvocationUndefinedExpression() {
		final TemplateInvocation invocation = getDummyTemplateInvocation();
		invocation.getArgument().add(createOCLExpression("self", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);
		invocation.getDefinition().getBody().clear();
		invocation.getDefinition().getBody().add(
				createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$
		mapTemplates();

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a template invocation with an undefined result.", //$NON-NLS-1$
				OUTPUT + invalidObject.toString() + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a template invocation which parameter evaluates to null.
	 */
	@Test
	public void testTemplateInvocationNullParameter() {
		final TemplateInvocation invocation = getDummyTemplateInvocation();
		invocation.getArgument().add(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$
		mapTemplates();

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a template invocation with a null parameter.", //$NON-NLS-1$
				OUTPUT + TEMPLATE_OUTPUT + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a template invocation which parameter evaluates to an undefined value (through
	 * an NPE).
	 */
	@Test
	public void testTemplateInvocationUndefinedParameter() {
		final TemplateInvocation invocation = getDummyTemplateInvocation();
		invocation.getArgument().add(createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$
		mapTemplates();

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a template invocation with an undefined parameter.", //$NON-NLS-1$
				OUTPUT + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a valid template invocation.
	 */
	@Test
	public void testTemplateInvocation() {
		final TemplateInvocation invocation = getDummyTemplateInvocation();

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$
		mapTemplates();

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a valid template invocation.", OUTPUT //$NON-NLS-1$
				+ TEMPLATE_OUTPUT + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests that arguments of a template invocation are properly replaced with their old values when their
	 * scope ends.
	 */
	@Test
	public void testTemplateInvocationArgumentScope() {
		final Resource res = new ResourceImpl();
		final TemplateInvocation invocation = getDummyTemplateInvocation();
		final Template template = getParentTemplate(invocation);
		final FileBlock file = (FileBlock)template.getBody().get(0);
		res.getContents().add(template.eContainer());
		invocation.getArgument().add(createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEString());
		res.getContents().add(param);
		invocation.getDefinition().getParameter().add(param);
		final VariableExp variableExp = EcoreFactory.eINSTANCE.createVariableExp();
		variableExp.setReferredVariable(param);
		invocation.getDefinition().getBody().clear();
		invocation.getDefinition().getBody().add(variableExp);

		final TemplateInvocation subInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		subInvocation.getArgument().add(
				createOCLExpression("eSuperTypes->last().name", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$
		final Template subTemplate = MtlFactory.eINSTANCE.createTemplate();
		((Module)template.eContainer()).getOwnedModuleElement().add(subTemplate);
		subTemplate.setName("sub"); //$NON-NLS-1$
		final Variable subParam = EcoreFactory.eINSTANCE.createVariable();
		subParam.setName("s"); //$NON-NLS-1$
		subParam.setType(EcorePackage.eINSTANCE.getEString());
		res.getContents().add(subParam);
		subInvocation.setDefinition(subTemplate);
		subInvocation.getDefinition().getParameter().add(subParam);
		final VariableExp subVariableExp = EcoreFactory.eINSTANCE.createVariableExp();
		subVariableExp.setReferredVariable(subParam);
		subTemplate.getBody().add(subVariableExp);
		file.getBody().add(file.getBody().size() - 1, subInvocation);

		final Variable templateParam = EcoreFactory.eINSTANCE.createVariable();
		templateParam.setName("s"); //$NON-NLS-1$
		templateParam.setType(EcorePackage.eINSTANCE.getEString());
		res.getContents().add(templateParam);
		template.getParameter().add(templateParam);
		final VariableExp templateVariableExp = EcoreFactory.eINSTANCE.createVariableExp();
		templateVariableExp.setReferredVariable(templateParam);
		file.getBody().add(file.getBody().size() - 1, templateVariableExp);

		templates.put(subTemplate.getName(), subTemplate);

		/*
		 * At this point we have a template with a parameter "s : EString" containing a string literal, a
		 * template with a parameter "s : EString", a second template with parameter "s : EString", a Variable
		 * expression referring to the value of param "s", and a last string literal.
		 */
		final EClass clazz = (EClass)getTestPackage().getEClassifiers().get(2);
		evaluationVisitor.getEvaluationEnvironment().add("self", clazz); //$NON-NLS-1$
		evaluationVisitor.getEvaluationEnvironment().add("s", "templateVariableValue"); //$NON-NLS-1$ //$NON-NLS-2$
		mapTemplates();

		evaluationVisitor.visitExpression(template);
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a valid template invocation.", OUTPUT //$NON-NLS-1$
				+ clazz.getESuperTypes().get(0).getName()
				+ clazz.getESuperTypes().get(1).getName()
				+ "templateVariableValue" + OUTPUT, entry.getValue().toString()); //$NON-NLS-1$
	}

	/**
	 * Creates a dummy template invocation containing a single string literal as its expression.
	 * 
	 * @return Dummy template invocation.
	 */
	private TemplateInvocation getDummyTemplateInvocation() {
		final Template container = getDummyTemplate();
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + FILE_NAME + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		container.getBody().add(mtlFileBlock);

		final TemplateInvocation dummy = MtlFactory.eINSTANCE.createTemplateInvocation();
		final Template dummyTemplate = MtlFactory.eINSTANCE.createTemplate();
		dummyTemplate.setName("dummy"); //$NON-NLS-1$
		dummyTemplate.getBody().add(createOCLStringLiteralExpression(TEMPLATE_OUTPUT));
		((Module)mtlFileBlock.eContainer().eContainer()).getOwnedModuleElement().add(dummyTemplate);
		dummy.setDefinition(dummyTemplate);

		mtlFileBlock.getBody().add(dummy);

		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));

		templates.put(dummyTemplate.getName(), dummyTemplate);
		templates.put(container.getName(), container);

		return dummy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.evaluation.AbstractAcceleoEvaluationVisitorTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		super.setUp();
		templates = AcceleoCollections.newCompactLinkedHashSetMultimap();
	}

	/**
	 * This will use reflection to map templates in the environment without going through the pain of
	 * re-parsing a module and instantiating the factory.
	 */
	private void mapTemplates() {
		final AcceleoEvaluationEnvironment evaluationEnvironment = (AcceleoEvaluationEnvironment)evaluationVisitor
				.getEvaluationEnvironment();
		try {
			final Field templatesField = AcceleoEvaluationEnvironment.class.getDeclaredField("templates"); //$NON-NLS-1$
			templatesField.setAccessible(true);
			templatesField.set(evaluationEnvironment, templates);
		} catch (SecurityException e) {
			fail("Couldn't retrieve 'templates' field of the evaluation environment"); //$NON-NLS-1$
		} catch (NoSuchFieldException e) {
			fail("Couldn't retrieve 'templates' field of the evaluation environment"); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			fail("Couldn't retrieve 'templates' field of the evaluation environment"); //$NON-NLS-1$
		} catch (IllegalAccessException e) {
			fail("Couldn't retrieve 'templates' field of the evaluation environment"); //$NON-NLS-1$
		}
	}
}
