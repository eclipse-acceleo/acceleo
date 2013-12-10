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

import java.io.File;
import java.util.Map;

import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.junit.Test;

/**
 * This will test the behavior of query invocations evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorQueryInvocationTest extends AbstractAcceleoEvaluationVisitorTest {
	/** This is the file name that will be used for the dummy file block. */
	private static final String FILE_NAME = "validFile.url"; //$NON-NLS-1$

	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/** Constant output of the query block tested here. */
	private static final String QUERY_OUTPUT = "queryBodyOutput"; //$NON-NLS-1$

	/**
	 * Tests the evaluation of a query invocation which expression evaluates to null.
	 */
	@Test
	public void testQueryInvocationNullExpression() {
		final QueryInvocation invocation = getDummyQueryInvocation();
		invocation.getArgument().add(createOCLExpression("self", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);
		invocation.getDefinition().setExpression(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with an undefined result.", OUTPUT //$NON-NLS-1$
				+ OUTPUT, entry.getValue().toString());

		// A second call in the same conditions should result in the same result (cache coverage)
		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with an undefined result.", OUTPUT //$NON-NLS-1$
				+ OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a query invocation which expression evaluates to an undefined value (through an
	 * NPE).
	 */
	@Test
	public void testQueryInvocationUndefinedExpression() {
		final QueryInvocation invocation = getDummyQueryInvocation();
		invocation.getArgument().add(createOCLExpression("self", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);
		invocation.getDefinition().setExpression(
				createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with an undefined result.", OUTPUT //$NON-NLS-1$
				+ OUTPUT, entry.getValue().toString());

		// A second call in the same conditions should result in the same result (cache coverage)
		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with an undefined result.", OUTPUT //$NON-NLS-1$
				+ OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a query invocation which parameter evaluates to null.
	 */
	@Test
	public void testQueryInvocationNullParameter() {
		final QueryInvocation invocation = getDummyQueryInvocation();
		invocation.getArgument().add(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with a null parameter.", //$NON-NLS-1$
				OUTPUT + QUERY_OUTPUT + OUTPUT, entry.getValue().toString());

		// A second call in the same conditions should result in the same result (cache coverage)
		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with a null parameter.", //$NON-NLS-1$
				OUTPUT + QUERY_OUTPUT + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a query invocation which parameter evaluates to an undefined value (through an
	 * NPE).
	 */
	@Test
	public void testQueryInvocationUndefinedParameter() {
		final QueryInvocation invocation = getDummyQueryInvocation();
		invocation.getArgument().add(createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with an undefined parameter.", //$NON-NLS-1$
				OUTPUT + OUTPUT, entry.getValue().toString());

		// A second call in the same conditions should result in the same result (cache coverage)
		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a query invocation with an undefined parameter.", //$NON-NLS-1$
				OUTPUT + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a valid query invocation.
	 */
	@Test
	public void testQueryInvocation() {
		final QueryInvocation invocation = getDummyQueryInvocation();

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(invocation));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a valid query invocation.", OUTPUT + QUERY_OUTPUT //$NON-NLS-1$
				+ OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a valid query invocation. A query invoked twice with the same arguments should
	 * return the same cached result.
	 */
	@Test
	public void testQueryInvocationResultCache() {
		final QueryInvocation invocation = getDummyQueryInvocation();
		invocation.getArgument().add(createOCLExpression("self", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		final Variable param = EcoreFactory.eINSTANCE.createVariable();
		param.setName("s"); //$NON-NLS-1$
		param.setType(EcorePackage.eINSTANCE.getEClass());
		invocation.getDefinition().getParameter().add(param);
		invocation.getDefinition().setExpression(
				createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the first eclass of the test package
		final EClass clazz = (EClass)getTestPackage().getEClassifiers().get(2);
		evaluationVisitor.getEvaluationEnvironment().add("self", clazz); //$NON-NLS-1$

		final Object reference = evaluationVisitor.visitAcceleoQueryInvocation(invocation);
		final Object result = evaluationVisitor.visitAcceleoQueryInvocation(invocation);
		assertSame("Unexpected result of a query invocation.", clazz.getESuperTypes().get(0), reference); //$NON-NLS-1$
		assertSame("Unexpected result of a query evaluated twice with the same arguments.", reference, result); //$NON-NLS-1$
	}

	/**
	 * Tests that arguments of a query invocation are properly replaced with their old values when their scope
	 * ends.
	 */
	@Test
	public void testQueryInvocationArgumentScope() {
		final Resource res = new ResourceImpl();
		final QueryInvocation invocation = getDummyQueryInvocation();
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
		invocation.getDefinition().setExpression(variableExp);
		invocation.getDefinition().setType(EcorePackage.eINSTANCE.getEClass());

		final QueryInvocation subInvocation = MtlFactory.eINSTANCE.createQueryInvocation();
		subInvocation.getArgument().add(
				createOCLExpression("eSuperTypes->last().name", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$
		final Query subQuery = MtlFactory.eINSTANCE.createQuery();
		((Module)template.eContainer()).getOwnedModuleElement().add(subQuery);
		subQuery.setName("sub"); //$NON-NLS-1$
		final Variable subParam = EcoreFactory.eINSTANCE.createVariable();
		subParam.setName("s"); //$NON-NLS-1$
		subParam.setType(EcorePackage.eINSTANCE.getEString());
		res.getContents().add(subParam);
		subInvocation.setDefinition(subQuery);
		subInvocation.getDefinition().getParameter().add(subParam);
		final VariableExp subVariableExp = EcoreFactory.eINSTANCE.createVariableExp();
		subVariableExp.setReferredVariable(subParam);
		subQuery.setExpression(subVariableExp);
		subQuery.setType(EcorePackage.eINSTANCE.getEClass());
		file.getBody().add(file.getBody().size() - 1, subInvocation);

		final Variable templateParam = EcoreFactory.eINSTANCE.createVariable();
		templateParam.setName("s"); //$NON-NLS-1$
		templateParam.setType(EcorePackage.eINSTANCE.getEString());
		res.getContents().add(templateParam);
		template.getParameter().add(templateParam);
		final VariableExp templateVariableExp = EcoreFactory.eINSTANCE.createVariableExp();
		templateVariableExp.setReferredVariable(templateParam);
		file.getBody().add(file.getBody().size() - 1, templateVariableExp);

		/*
		 * At this point we have a template with a parameter "s : EString" containing a string literal, a
		 * query with a parameter "s : EString", a second query with parameter "s : EString", a Variable
		 * expression referring to the value of param "s", and a last string literal.
		 */
		final EClass clazz = (EClass)getTestPackage().getEClassifiers().get(2);
		evaluationVisitor.getEvaluationEnvironment().add("self", clazz); //$NON-NLS-1$
		evaluationVisitor.getEvaluationEnvironment().add("s", "templateVariableValue"); //$NON-NLS-1$ //$NON-NLS-2$

		evaluationVisitor.visitExpression(template);
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from a valid query invocation.", OUTPUT //$NON-NLS-1$
				+ clazz.getESuperTypes().get(0).getName()
				+ clazz.getESuperTypes().get(1).getName()
				+ "templateVariableValue" + OUTPUT, entry.getValue().toString()); //$NON-NLS-1$
	}

	/**
	 * Creates a dummy query invocation containing a single string literal as its expression.
	 * 
	 * @return Dummy query invocation.
	 */
	private QueryInvocation getDummyQueryInvocation() {
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + FILE_NAME + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		getDummyTemplate().getBody().add(mtlFileBlock);

		final QueryInvocation dummy = MtlFactory.eINSTANCE.createQueryInvocation();
		final Query dummyQuery = MtlFactory.eINSTANCE.createQuery();
		dummyQuery.setName("dummy"); //$NON-NLS-1$
		dummyQuery.setExpression(createOCLStringLiteralExpression(QUERY_OUTPUT));
		dummyQuery.setType(EcorePackage.eINSTANCE.getEString());
		((Module)mtlFileBlock.eContainer().eContainer()).getOwnedModuleElement().add(dummyQuery);
		dummy.setDefinition(dummyQuery);

		mtlFileBlock.getBody().add(dummy);

		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));

		return dummy;
	}
}
