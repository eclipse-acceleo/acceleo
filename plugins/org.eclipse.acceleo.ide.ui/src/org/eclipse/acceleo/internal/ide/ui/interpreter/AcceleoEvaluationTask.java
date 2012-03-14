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
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.generation.IAcceleoEngine2;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.internal.debug.IDebugAST;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.engine.service.AcceleoModulePropertiesAdapter;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterFile;
import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.types.OCLStandardLibrary;
import org.eclipse.ocl.util.Bag;

/**
 * This will be used by the interpreter in order to evaluate Acceleo expressions from the interpreter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationTask implements Callable<EvaluationResult> {
	/** Context of this evaluation as passed from the interpreter. */
	private EvaluationContext context;

	/**
	 * Instantiates our evaluation task given the current evaluation context.
	 * 
	 * @param context
	 *            The current interpreter evaluation context.
	 */
	public AcceleoEvaluationTask(EvaluationContext context) {
		this.context = context;
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
		EcoreEnvironment env = (EcoreEnvironment)new EcoreEnvironmentFactory().createEnvironment();
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
		checkCancelled();

		CompilationResult compilationResult = context.getCompilationResult();
		EvaluationResult shortcutResult = null;
		if (compilationResult == null || compilationResult.getCompiledExpression() == null) {
			shortcutResult = new EvaluationResult(new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
					AcceleoUIMessages.getString("acceleo.interpreter.unresolved.compilation.issue"))); //$NON-NLS-1$
		} else if (compilationResult.getCompiledExpression() instanceof ModuleElement
				&& compilationResult.getStatus() != null
				&& compilationResult.getStatus().getSeverity() == IStatus.ERROR) {
			// We're trying to evaluate a module element whilst there are compilation problems. No use trying
			// any further.
			shortcutResult = new EvaluationResult(null);
		}
		if (shortcutResult != null) {
			return shortcutResult;
		}

		checkCancelled();

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

		List<EObject> target = context.getTargetEObjects();
		if (target == null) {
			target = Collections.emptyList();
		}
		final ResourceSet resourceSet;
		if (compiledExpression instanceof EObject) {
			resourceSet = ((EObject)compiledExpression).eResource().getResourceSet();
		} else {
			resourceSet = new ResourceSetImpl();
		}
		for (EObject targetEObject : new ArrayList<EObject>(target)) {
			if (targetEObject.eIsProxy()) {
				checkCancelled();
				EObject resolved = EcoreUtil.resolve(targetEObject, resourceSet);
				if (resolved != null && !resolved.eIsProxy()) {
					target.remove(targetEObject);
					target.add(resolved);
				}
			}
		}

		final EvaluationLogListener evaluationListener = new EvaluationLogListener();
		final boolean notificationsState = AcceleoPreferences.areNotificationsEnabled();
		AcceleoPreferences.switchNotifications(false);
		final boolean debugMessagesState = AcceleoPreferences.isDebugMessagesEnabled();
		AcceleoPreferences.switchDebugMessages(false);
		Platform.addLogListener(evaluationListener);

		try {
			checkCancelled();

			Object result = null;
			if (compiledExpression instanceof ModuleElement) {
				ModuleElement moduleElement = (ModuleElement)compiledExpression;

				result = evaluateModuleElement(moduleElement, target);
			} else if (compiledExpression instanceof OCLExpression) {
				OCLExpression expression = (OCLExpression)compiledExpression;

				result = evaluateOCLExpression(expression, target);
			}

			checkCancelled();

			IStatus accumulatedProblems = evaluationListener.getAccumulatedProblems();
			final IStatus resultStatus = createResultStatus(result);

			if (accumulatedProblems instanceof MultiStatus
					&& accumulatedProblems.getSeverity() != IStatus.ERROR) {
				((MultiStatus)accumulatedProblems).add(resultStatus);
			} else if (accumulatedProblems != null && accumulatedProblems.getSeverity() != IStatus.ERROR) {
				accumulatedProblems = new MultiStatus(AcceleoUIActivator.PLUGIN_ID, 1, new IStatus[] {
						accumulatedProblems, resultStatus, }, AcceleoUIMessages
						.getString("acceleo.interpreter.evaluation.issue"), null); //$NON-NLS-1$
			} else if (accumulatedProblems == null) {
				accumulatedProblems = resultStatus;
			}

			return new EvaluationResult(result, accumulatedProblems);
		} finally {
			AcceleoPreferences.switchDebugMessages(debugMessagesState);
			Platform.removeLogListener(evaluationListener);
			AcceleoPreferences.switchNotifications(notificationsState);
		}
	}

	/**
	 * Throws a new {@link CancellationException} if the current thread has been cancelled.
	 */
	private void checkCancelled() {
		if (Thread.currentThread().isInterrupted()) {
			throw new CancellationException();
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

		String message = AcceleoUIMessages.getString("acceleo.interpreter.result.type", oclType); //$NON-NLS-1$
		if (size != null) {
			message += ' ' + AcceleoUIMessages.getString("acceleo.interpreter.result.size", size); //$NON-NLS-1$
		}

		return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, message);
	}

	/**
	 * Evaluates the given module element with the current context and returns the result.
	 * 
	 * @param moduleElement
	 *            The module element we are to evaluate.
	 * @param target
	 *            The list of selected EObjects. Will be used as the evaluation's target (i.e : the "self"
	 *            element).
	 * @return The result of this evaluation.
	 */
	private Object evaluateModuleElement(ModuleElement moduleElement, List<EObject> target) {
		// Prepare arguments
		List<Object> arguments = new ArrayList<Object>();
		List<Variable> variables = context.getVariables();
		Iterator<EObject> targetItr = target.iterator();

		List<org.eclipse.ocl.ecore.Variable> parameters = Collections.emptyList();
		if (moduleElement instanceof Template) {
			parameters = ((Template)moduleElement).getParameter();
		} else if (moduleElement instanceof Query) {
			parameters = ((Query)moduleElement).getParameter();
		}
		for (org.eclipse.ocl.ecore.Variable param : parameters) {
			boolean found = false;
			Iterator<Variable> vars = variables.iterator();
			while (!found && vars.hasNext()) {
				Variable next = vars.next();
				if (param.getName().equals(next.getName())) {
					arguments.add(next.getValue());
					vars.remove();
					found = true;
				}
			}
			if (!found && targetItr.hasNext()) {
				arguments.add(targetItr.next());
				found = true;
			}
			if (!found) {
				// Is it our "root" dummy template's parameter?
				if ("currentModel".equals(param.getName()) && target.size() > 0) { //$NON-NLS-1$
					arguments.add(EcoreUtil.getRootContainer(target.get(0)));
				} else {
					// FIXME throw exception from here
				}
			}
		}

		Object result = null;
		IAcceleoEngine2 engine = new AcceleoEngine();
		IAcceleoGenerationStrategy strategy = new AcceleoInterpreterStrategy();
		final IDebugAST debugger = AcceleoEvaluationVisitor.getDebug();
		AcceleoEvaluationVisitor.setDebug(null);
		if (moduleElement instanceof Template) {
			result = engine.evaluate((Template)moduleElement, arguments, strategy, new BasicMonitor());
		} else if (moduleElement instanceof Query) {
			result = engine.evaluate((Query)moduleElement, arguments, strategy, new BasicMonitor());
		}
		AcceleoEvaluationVisitor.setDebug(debugger);

		Map<String, String> preview = strategy.preparePreview(null);
		Set<InterpreterFile> generatedFiles = null;
		if (preview != null && !preview.isEmpty()) {
			generatedFiles = new LinkedHashSet<InterpreterFile>();
			for (Map.Entry<String, String> file : preview.entrySet()) {
				generatedFiles.add(new InterpreterFile(file.getKey(), file.getValue()));
			}
		}

		if (result != null && generatedFiles != null) {
			if (result instanceof String && ((String)result).length() > 0) {
				final List<Object> actualResult = new ArrayList<Object>();
				actualResult.add(result);
				actualResult.addAll(generatedFiles);
				result = actualResult;
			} else {
				result = generatedFiles;
			}
		} else if (result == null) {
			result = generatedFiles;
		}

		return result;
	}

	/**
	 * Evaluates the given OCL expression with the current context and returns the result.
	 * 
	 * @param oclExpression
	 *            The OCL expression we are to evaluate.
	 * @param target
	 *            The list of selected EObjects. Will be used as the evaluation's target (i.e : the "self"
	 *            element).
	 * @return The result of this evaluation.
	 */
	private Object evaluateOCLExpression(OCLExpression oclExpression, List<EObject> target) {
		final Module module = (Module)EcoreUtil.getRootContainer(oclExpression);
		// We won't have listeners or property files here
		List<IAcceleoTextGenerationListener> listeners = Collections.emptyList();
		// The strategy will allow us to retrieve "file" results
		IAcceleoGenerationStrategy strategy = new AcceleoInterpreterStrategy();
		org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory factory = new org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory(
				null, module, listeners,
				new org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup(), strategy,
				new BasicMonitor());
		OCL ocl = OCL.newInstance(factory);
		org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> evaluationVisitor = (org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>)factory
				.createEvaluationVisitor(ocl.getEnvironment(), ocl.getEvaluationEnvironment(), ocl
						.getExtentMap());

		Object actualArgument = target;
		if (target.size() == 1) {
			actualArgument = target.get(0);
		}
		ocl.getEvaluationEnvironment().add("self", actualArgument); //$NON-NLS-1$
		// AcceleoSourceViewer.DUMMY_TEMPLATE_TARGET_PARAM
		ocl.getEvaluationEnvironment().add("thisEObject", actualArgument); //$NON-NLS-1$

		EObject modelRoot = null;
		if (target.size() > 0) {
			modelRoot = EcoreUtil.getRootContainer(target.get(0));
		}
		// AcceleoSourceViewer.DUMMY_TEMPLATE_MODEL_PARAM
		ocl.getEvaluationEnvironment().add("currentModel", modelRoot); //$NON-NLS-1$

		for (Variable variable : context.getVariables()) {
			Object value = variable.getValue();
			if (value instanceof EObject && ((EObject)value).eIsProxy()) {
				value = EcoreUtil.resolve((EObject)value, oclExpression.eResource().getResourceSet());
			}
			ocl.getEvaluationEnvironment().add(variable.getName(), value);
		}

		final IDebugAST debugger = AcceleoEvaluationVisitor.getDebug();
		AcceleoEvaluationVisitor.setDebug(null);
		Object result = evaluationVisitor.visitExpression(oclExpression);
		AcceleoEvaluationVisitor.setDebug(debugger);

		Map<String, String> preview = factory.getEvaluationPreview();
		Set<InterpreterFile> generatedFiles = null;
		if (preview != null && !preview.isEmpty()) {
			generatedFiles = new LinkedHashSet<InterpreterFile>();
			for (Map.Entry<String, String> file : preview.entrySet()) {
				generatedFiles.add(new InterpreterFile(file.getKey(), file.getValue()));
			}
		}

		if (result != null && generatedFiles != null) {
			if (result instanceof String && ((String)result).length() > 0) {
				final List<Object> actualResult = new ArrayList<Object>();
				actualResult.add(result);
				actualResult.addAll(generatedFiles);
				result = actualResult;
			} else {
				result = generatedFiles;
			}
		} else if (result == null) {
			result = generatedFiles;
		}

		return result;
	}

	/**
	 * This listener will be used in order to accumulate the problems logged during an evaluation in a
	 * MultiStatus and return them afterwards.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected final class EvaluationLogListener implements ILogListener {
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
			if (status.getPlugin().startsWith("org.eclipse.acceleo") || status.getPlugin().startsWith("org.eclipse.ocl")) { //$NON-NLS-1$ //$NON-NLS-2$
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
					evaluationStatus = new MultiStatus(AcceleoUIActivator.PLUGIN_ID, 1,
							new IStatus[] {evaluationStatus, }, AcceleoUIMessages
									.getString("acceleo.interpreter.evaluation.issue"), null); //$NON-NLS-1$
				}
			}
		}
	}
}
