/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.maven.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultWriterFactory;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MavenTests {

	@Test
	public void maven() {
		// parsing/resolution
		final String moduleQualifiedName = "org::eclipse::acceleo::aql::maven::tests::main";

		final IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(getClass()
				.getClassLoader(), AcceleoParser.QUALIFIER_SEPARATOR);
		final Map<String, String> options = new HashMap<>();
		final ArrayList<Exception> exceptions = new ArrayList<>();
		final ResourceSet resourceSetForModels = AQLUtils.createResourceSetForModels(exceptions, resolver,
				new ResourceSetImpl(), options);

		resourceSetForModels.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		final IQualifiedNameQueryEnvironment queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(
				options, resolver, resourceSetForModels, false);
		AcceleoEvaluator evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine(), "\n");

		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(new JavaLoader(AcceleoParser.QUALIFIER_SEPARATOR, false));

		final Object resolved = resolver.resolve(moduleQualifiedName);
		final Module mainModule;
		if (resolved instanceof Module) {
			mainModule = (Module)resolved;
		} else {
			mainModule = null;
		}

		assertTrue(mainModule != null);

		// validation
		final AcceleoValidator acceleoValidator = new AcceleoValidator(queryEnvironment);
		final IAcceleoValidationResult acceleoValidationResult = acceleoValidator.validate(mainModule
				.getAst(), moduleQualifiedName);

		assertEquals(0, acceleoValidationResult.getValidationMessages().size());

		// evaluation
		final URI uri = URI.createURI(getClass().getClassLoader().getResource(
				"org/eclipse/acceleo/aql/maven/tests/anydsl.ecore").toString());
		final URI targetURI = URI.createFileURI("/tmp/");
		final Resource resource = resourceSetForModels.getResource(uri, true);
		final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(resourceSetForModels
				.getURIConverter(), new DefaultWriterFactory());
		final URI logURI = AcceleoUtil.getlogURI(targetURI, options.get(AcceleoUtil.LOG_URI_OPTION));

		AcceleoUtil.generate(evaluator, queryEnvironment, mainModule, resource, strategy, targetURI, logURI);

		assertEquals(Diagnostic.OK, evaluator.getGenerationResult().getDiagnostic().getSeverity());
		assertEquals(0, evaluator.getGenerationResult().getDiagnostic().getChildren().size());
		assertEquals(0, evaluator.getGenerationResult().getGeneratedFiles().size());
		assertEquals(0, evaluator.getGenerationResult().getLostFiles().size());

	}

}
