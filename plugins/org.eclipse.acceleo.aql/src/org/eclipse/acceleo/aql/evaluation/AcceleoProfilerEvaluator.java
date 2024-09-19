/*******************************************************************************
 * Copyright (c) 2020, 2024 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import org.eclipse.acceleo.aql.profiler.IProfiler;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
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
	private final IProfiler profiler;

	/**
	 * Constructor.
	 * 
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param newLine
	 *            the new line {@link String}
	 * @param profiler
	 *            the {@link IProfiler}
	 */
	public AcceleoProfilerEvaluator(IQualifiedNameLookupEngine lookupEngine, String newLine,
			IProfiler profiler) {
		super(lookupEngine, newLine);
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
		try {
			return super.doSwitch(eObject);
		} finally {
			profiler.stop();
		}
	}

	/**
	 * Gets the attached {@link IProfiler}.
	 * 
	 * @return the attached {@link IProfiler}
	 */
	public IProfiler getProfiler() {
		return profiler;
	}

}
