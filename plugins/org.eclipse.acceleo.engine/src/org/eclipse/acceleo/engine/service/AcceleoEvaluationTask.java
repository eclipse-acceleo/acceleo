/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.interpreter.CompilationResult;
import org.eclipse.acceleo.common.interpreter.EvaluationResult;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.generation.IAcceleoEngine2;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.types.OCLStandardLibrary;
import org.eclipse.ocl.util.Bag;

/**
 * This class aims at providing the necessary API to evaluate an Acceleo query that was compiled from a simple
 * String. The resulting {@link CompilationResult} will be set to reference either the compiled
 * {@link org.eclipse.acceleo.model.mtl.Module} itself, or one of its queries if we fed its name to this task.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public class AcceleoEvaluationTask implements Callable<EvaluationResult> {
	/** Context of this evaluation. */
	private EvaluationContext context;

	/**
	 * Instantiates the evaluation task for the given evaluation context.
	 * 
	 * @param context
	 *            The evaluation context.
	 */
	public AcceleoEvaluationTask(EvaluationContext context) {
		this.context = context;
	}

	/**
	 * Evaluates the given evaluation context.
	 * <p>
	 * This method does not make any sanity check regarding the content of the EvaluationContext; this must be
	 * done beforehand.
	 * </p>
	 * 
	 * @param context
	 *            The context we are to evaluate.
	 * @return The result of this evaluation.
	 */
	private static Object evaluateQuery(EvaluationContext context) {
		// Sanity checks have been made in #call
		final Query query = (Query)context.getCompilationResult().getCompiledExpression();
		final List<Variable> parameters = query.getParameter();

		final List<Object> arguments = Lists.<Object> newArrayList(context.getTargetEObject());
		final Iterator<Variable> paramIterator = parameters.iterator();
		// Ignore the very first element, it correspond to the query's target
		if (paramIterator.hasNext()) {
			paramIterator.next();
		}
		while (paramIterator.hasNext()) {
			final Variable parameter = paramIterator.next();
			boolean foundMatch = false;
			final Iterator<Map.Entry<String, Collection<Object>>> variableIterator = context.getVariables()
					.asMap().entrySet().iterator();
			while (!foundMatch && variableIterator.hasNext()) {
				final Map.Entry<String, Collection<Object>> variable = variableIterator.next();
				if (variable.getKey().equals(parameter.getName())) {
					final List<Object> values = (List<Object>)variable.getValue();
					arguments.add(values.listIterator(values.size()).previous());
					foundMatch = true;
				}
			}
			if (!foundMatch) {
				/*
				 * We were not provided a value for this variable. Use null and let Acceleo fail later on if
				 * the variable is used without value.
				 */
				arguments.add(null);
			}
		}

		final IAcceleoEngine2 engine = new AcceleoEngine();
		final IAcceleoGenerationStrategy strategy = new PreviewStrategy();

		final Object result = engine.evaluate(query, arguments, strategy, new BasicMonitor());

		return result;
	}

	/**
	 * Returns the OCL type of the given Object.
	 * 
	 * @param env
	 *            the ecore environment from which to seek types.
	 * @param obj
	 *            The Object we need an OCL type for.
	 * @return The OCL type of the given Object.
	 */
	private static EClassifier getOCLType(EcoreEnvironment env, Object obj) {
		OCLStandardLibrary<EClassifier> library = env.getOCLStandardLibrary();
		EClassifier oclType = library.getOclAny();
		if (obj instanceof Number) {
			if (obj instanceof BigDecimal || obj instanceof Double || obj instanceof Float) {
				oclType = library.getReal();
			} else {
				oclType = library.getInteger();
			}
		} else if (obj instanceof String) {
			oclType = library.getString();
		} else if (obj instanceof Boolean) {
			oclType = library.getBoolean();
		} else if (obj instanceof EObject) {
			oclType = env.getUMLReflection().asOCLType(((EObject)obj).eClass());
		} else if (obj instanceof Collection<?>) {
			if (obj instanceof LinkedHashSet<?>) {
				oclType = library.getOrderedSet();
			} else if (obj instanceof Set<?>) {
				oclType = library.getSet();
			} else if (obj instanceof Bag<?>) {
				oclType = library.getBag();
			} else {
				oclType = library.getSequence();
			}
		}
		return oclType;
	}

	/**
	 * Tries and infer the OCL type of the given Collection's content.
	 * 
	 * @param env
	 *            the ecore environment from which to seek types.
	 * @param coll
	 *            Collection for which we need an OCL type.
	 * @return The inferred OCL type. OCLAny if we could not infer anything more sensible.
	 */
	private static EClassifier inferCollectionContentOCLType(EcoreEnvironment env, Collection<?> coll) {
		if (coll.isEmpty()) {
			return env.getOCLStandardLibrary().getOclAny();
		}

		Set<EClassifier> types = new HashSet<EClassifier>();
		for (Object child : coll) {
			types.add(getOCLType(env, child));
		}

		Iterator<EClassifier> iterator = types.iterator();

		EClassifier elementType = iterator.next();
		while (iterator.hasNext()) {
			elementType = env.getUMLReflection().getCommonSuperType(elementType, iterator.next());
		}

		if (elementType == null) {
			elementType = env.getOCLStandardLibrary().getOclAny();
		}

		return elementType;
	}

	/**
	 * Tries and infer the OCL type of the given Object.
	 * 
	 * @param obj
	 *            Object for which we need an OCL type.
	 * @return The inferred OCL type. OCLAny if we could not infer anything more sensible.
	 */
	private static String inferOCLType(Object obj) {
		String oclType = "OCLAny"; //$NON-NLS-1$
		final EcoreEnvironment env = (EcoreEnvironment)new EcoreEnvironmentFactory().createEnvironment();
		if (obj instanceof Collection<?>) {
			EClassifier elementType = inferCollectionContentOCLType(env, (Collection<?>)obj);
			CollectionKind kind = CollectionKind.SEQUENCE_LITERAL;
			if (obj instanceof LinkedHashSet<?>) {
				kind = CollectionKind.ORDERED_SET_LITERAL;
			} else if (obj instanceof Set<?>) {
				kind = CollectionKind.SET_LITERAL;
			} else if (obj instanceof Bag<?>) {
				kind = CollectionKind.BAG_LITERAL;
			}
			oclType = env.getTypeResolver().resolveCollectionType(kind, elementType).getName();
		} else {
			oclType = getOCLType(env, obj).getName();
		}
		return oclType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public EvaluationResult call() throws Exception {
		CompilationResult compilationResult = context.getCompilationResult();

		EvaluationResult shortcutResult = null;
		if (compilationResult == null || !(compilationResult.getCompiledExpression() instanceof Query)) {
			shortcutResult = new EvaluationResult(new Status(IStatus.ERROR, AcceleoEnginePlugin.PLUGIN_ID,
					AcceleoEngineMessages.getString("AcceleoEvaluationTask.UnresolvedCompilationProblem"))); //$NON-NLS-1$
		} else if (compilationResult.getStatus() != null
				&& compilationResult.getStatus().getSeverity() == IStatus.ERROR) {
			// We're trying to evaluate while there are compilation errors.
			// No need to go any further.
			shortcutResult = new EvaluationResult(null);
		}
		if (shortcutResult != null) {
			return shortcutResult;
		}

		// We would have returned already if this was false
		assert compilationResult != null;

		Object compiledExpression = compilationResult.getCompiledExpression();
		if (compiledExpression instanceof EObject) {
			Module module = null;
			EObject eObject = (EObject)compiledExpression;
			if (eObject instanceof Module) {
				module = (Module)eObject;
			} else {
				EObject parent = eObject.eContainer();
				while (parent != null && !(parent instanceof Module)) {
					parent = parent.eContainer();
				}

				if (parent instanceof Module) {
					module = (Module)parent;
				}
			}

			if (module != null) {
				AcceleoModulePropertiesAdapter adapter = new AcceleoModulePropertiesAdapter();
				adapter.addProperty(IAcceleoConstants.DISABLE_DYNAMIC_MODULES);
				module.eAdapters().add(adapter);
			}
		}

		boolean notificationsState = false;
		boolean debugMessagesState = false;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			// Disable Acceleo notifications and debug messages
			notificationsState = AcceleoPreferences.areNotificationsEnabled();
			AcceleoPreferences.switchNotifications(false);
			debugMessagesState = AcceleoPreferences.isDebugMessagesEnabled();
			AcceleoPreferences.switchDebugMessages(false);
		}

		// Add our log listener so as to "remember" the Acceleo errors
		final EvaluationLogListener evaluationListener = new EvaluationLogListener();
		if (Platform.isRunning()) {
			Platform.addLogListener(evaluationListener);
		}

		try {
			final Object result = evaluateQuery(context);

			final IStatus resultStatus = createResultStatus(result);

			IStatus accumulatedProblems = evaluationListener.getAccumulatedProblems();
			if (accumulatedProblems instanceof MultiStatus
					&& accumulatedProblems.getSeverity() != IStatus.ERROR) {
				((MultiStatus)accumulatedProblems).add(resultStatus);
			} else if (accumulatedProblems != null && accumulatedProblems.getSeverity() != IStatus.ERROR) {
				accumulatedProblems = new MultiStatus(AcceleoEnginePlugin.PLUGIN_ID, 1, new IStatus[] {
						accumulatedProblems, resultStatus, }, AcceleoEngineMessages
						.getString("AcceleoEvaluationTask.MultipleProblems"), null); //$NON-NLS-1$
			} else if (accumulatedProblems == null) {
				accumulatedProblems = resultStatus;
			}

			return new EvaluationResult(result, accumulatedProblems);
		} finally {
			if (EMFPlugin.IS_ECLIPSE_RUNNING) {
				AcceleoPreferences.switchNotifications(notificationsState);
				AcceleoPreferences.switchDebugMessages(debugMessagesState);
			}
			if (Platform.isRunning()) {
				Platform.removeLogListener(evaluationListener);
			}
		}
	}

	/**
	 * This will create the status that allows the interpreter to display the type and size of the resulting
	 * object.
	 * 
	 * @param result
	 *            The result of the evaluation.
	 * @return Status that allows the interpreter to display the type and size of the result.
	 */
	private IStatus createResultStatus(Object result) {
		final String oclType;
		if (result == null) {
			oclType = "OCLVoid"; //$NON-NLS-1$
		} else {
			oclType = inferOCLType(result);
		}
		String size = null;
		if (result instanceof String) {
			size = String.valueOf(((String)result).length());
		} else if (result instanceof Collection<?>) {
			size = String.valueOf(((Collection<?>)result).size());
		}

		String message = AcceleoEngineMessages.getString("AcceleoEvaluationTask.ResultType", oclType); //$NON-NLS-1$
		if (size != null) {
			message += ' ' + AcceleoEngineMessages.getString("AcceleoEvaluationTask.ResultSize", size); //$NON-NLS-1$
		}

		return new Status(IStatus.OK, AcceleoEnginePlugin.PLUGIN_ID, message);
	}

	/**
	 * This listener will be used in order to accumulate the problems logged during an evaluation in a
	 * MultiStatus and return them afterwards.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected static final class EvaluationLogListener implements ILogListener {
		/** The accumulated status. */
		private IStatus evaluationStatus;

		/**
		 * Returns the accumulated status of this log listener.
		 * 
		 * @return The accumulated status of this log listener.
		 */
		public IStatus getAccumulatedProblems() {
			return evaluationStatus;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus,
		 *      java.lang.String)
		 */
		public void logging(IStatus status, String plugin) {
			if (status.getPlugin().startsWith("org.eclipse.acceleo") //$NON-NLS-1$
					|| status.getPlugin().startsWith("org.eclipse.ocl") //$NON-NLS-1$
					|| AcceleoEnginePlugin.PLUGIN_ID.equals(status.getPlugin())) {
				addStatus(status);
			}
		}

		/**
		 * Adds the given status too the accumulated status.
		 * 
		 * @param status
		 *            Status we are to add to the accumulated status.
		 */
		private void addStatus(IStatus status) {
			if (evaluationStatus == null) {
				evaluationStatus = status;
			} else {
				if (evaluationStatus instanceof MultiStatus) {
					((MultiStatus)evaluationStatus).add(status);
				} else {
					evaluationStatus = new MultiStatus(AcceleoEnginePlugin.PLUGIN_ID, 1,
							new IStatus[] {evaluationStatus, }, AcceleoEngineMessages
									.getString("AcceleoEvaluationTask.MultipleProblems"), null); //$NON-NLS-1$
				}
			}
		}
	}
}
