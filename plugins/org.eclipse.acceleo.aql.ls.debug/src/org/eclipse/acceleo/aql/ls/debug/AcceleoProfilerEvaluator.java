/*******************************************************************************
 * Copyright (c) 2020, 2024 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug;

import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.profiler.IProfiler;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
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
	private IProfiler profiler;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param newLine
	 *            the new line {@link String}
	 * @param profiler
	 *            the {@link IProfiler}
	 */
	public AcceleoProfilerEvaluator(IQualifiedNameQueryEnvironment queryEnvironment, String newLine,
			IProfiler profiler) {
		super(queryEnvironment.getLookupEngine(), newLine);
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
		Object res = super.doSwitch(eObject);
		profiler.stop();
		return res;
	}

}
