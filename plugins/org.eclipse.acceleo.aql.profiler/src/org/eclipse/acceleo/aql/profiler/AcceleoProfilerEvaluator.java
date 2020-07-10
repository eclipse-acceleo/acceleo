package org.eclipse.acceleo.aql.profiler;

import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
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
		profiler.start(eObject);
		// TODO //profiler.loop(loopElement);
		Object res = super.doSwitch(eObject);
		profiler.stop();
		return res;
	}

}
