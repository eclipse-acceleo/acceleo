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

public class AcceleoProfiler extends AcceleoDebugger {

	public static final String PROFILE_DESTINATION = "profileDestination";

	private String modelURI;

	public AcceleoProfiler(IDSLDebugEventProcessor target) {
		super(target);
	}

	@Override
	public void initialize(boolean noDebug, Map<String, Object> arguments) {
		super.initialize(noDebug, arguments);
		this.modelURI = (String)arguments.get(PROFILE_DESTINATION);
	}

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
