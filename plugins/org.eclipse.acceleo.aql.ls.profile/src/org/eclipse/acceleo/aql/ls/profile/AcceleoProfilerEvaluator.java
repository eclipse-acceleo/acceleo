/*******************************************************************************
 * Copyright (c) 2020 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.profile;

import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.profiler.Profiler;
import org.eclipse.emf.ecore.EObject;

/**
 * An evaluator dedicated to profiling.
 * 
 * @author wpiers
 */
public class AcceleoProfilerEvaluator extends AcceleoEvaluator {

	/**
	 * The Acceleo Profiler.
	 */
	private Profiler profiler;

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the acceleo environment.
	 * @param profiler
	 *            the profiler
	 */
	public AcceleoProfilerEvaluator(IAcceleoEnvironment environment, Profiler profiler) {
		super(environment, environment.getQueryEnvironment().getLookupEngine());
		this.profiler = profiler;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.Switch#doSwitch(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Object doSwitch(EObject eObject) {
		profiler.start(eObject);
		// TODO loop elements are not used, so we ignore them
		// profiler.loop(loopElement);
		Object res = super.doSwitch(eObject);
		profiler.stop();
		return res;
	}

}
