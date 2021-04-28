/*******************************************************************************
 * Copyright (c) 2020, 2021 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.profile;

import java.io.IOException;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.aql.profiler.Profiler;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * A redefinition of the {@link AcceleoDebugger} dedicated to profiling.
 * 
 * @author wpiers
 */
public class AcceleoProfiler extends AcceleoDebugger {

	public static final String PROFILE_MODEL = "profileModel";

	private String modelURI;

	public AcceleoProfiler(IDSLDebugEventProcessor target) {
		super(target);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger#initialize(boolean, java.util.Map)
	 */
	@Override
	public void initialize(boolean noDebug, Map<String, Object> arguments) {
		super.initialize(noDebug, arguments);
		this.modelURI = (String)arguments.get(PROFILE_MODEL);
	}

	@Override
	protected void generateNoDebug(IQualifiedNameQueryEnvironment queryEnvironment, Module module,
			Resource model) {
		Profiler profiler = new Profiler();
		AcceleoEvaluator evaluator = new AcceleoProfilerEvaluator(queryEnvironment, profiler);
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		resolver.clearLoaders();
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));

		AcceleoUtil.generate(evaluator, queryEnvironment, module, model, getDestination());
		try {
			profiler.save(modelURI);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
