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
		super(environment);
		this.profiler = profiler;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.Switch#doSwitch(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Object doSwitch(EObject eObject) {
		boolean profile = filter(eObject);
		if (!profile) {
			profiler.start(eObject);
			// TODO loop elements are not used, so we ignore them
			// profiler.loop(loopElement);
		}
		Object res = super.doSwitch(eObject);
		if (!profile) {
			profiler.stop();
		}
		return res;
	}

	/**
	 * Checks whether the element must be excluded from the profiling or not.
	 * 
	 * @param eObject
	 *            the element to check
	 * @return <true> if the element must not appear in the profiling result.
	 */
	private boolean filter(EObject eObject) {
		return false;
		// return eObject instanceof TextStatement || eObject instanceof ExpressionStatement
		// || eObject instanceof Block;
	}
}
