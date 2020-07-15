/*******************************************************************************
 * Copyright (c) 2020 Huawei.
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
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.profiler.Profiler;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger#generateNoDebug(org.eclipse.acceleo.aql.IAcceleoEnvironment,
	 *      org.eclipse.acceleo.Module, org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	protected void generateNoDebug(IAcceleoEnvironment environment, Module module, Resource model) {
		Profiler profiler = new Profiler();
		AcceleoEvaluator evaluator = new AcceleoProfilerEvaluator(environment, profiler);
		AcceleoUtil.generate(evaluator, environment, module, model);
		try {
			profiler.save(modelURI);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
