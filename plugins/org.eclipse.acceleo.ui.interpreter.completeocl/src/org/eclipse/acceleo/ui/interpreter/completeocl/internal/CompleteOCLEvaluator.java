package org.eclipse.acceleo.ui.interpreter.completeocl.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.ui.interpreter.InterpreterPlugin;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultFactory;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.ocl.AbstractOCLEvaluator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.ocl.pivot.CompleteModel;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.Element;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LanguageExpression;
import org.eclipse.ocl.pivot.Model;
import org.eclipse.ocl.pivot.Namespace;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.Operation;
import org.eclipse.ocl.pivot.Package;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.ocl.pivot.evaluation.AbstractConstraintEvaluator;
import org.eclipse.ocl.pivot.utilities.EnvironmentFactory;
import org.eclipse.ocl.pivot.utilities.MetamodelManager;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.ocl.pivot.values.InvalidValueException;
import org.eclipse.ocl.pivot.values.Value;

public class CompleteOCLEvaluator extends AbstractOCLEvaluator {
	private final EnvironmentFactory environmentFactory;

	private final MetamodelManager metamodelManager;

	private final CompleteModel completeModel;

	public CompleteOCLEvaluator(EnvironmentFactory environmentFactory) {
		this.environmentFactory = environmentFactory;
		this.metamodelManager = environmentFactory.getMetamodelManager();
		this.completeModel = environmentFactory.getCompleteModel();
	}

	@Override
	protected EnvironmentFactory getEnvironmentFactory() {
		return environmentFactory;
	}

	public EvaluationResult evaluateCompleteOCLElement(Element pivotElement, Notifier evaluationTarget) {
		final OCLElement oclElement;
		if (pivotElement instanceof Model) {
			oclElement = createOCLElement(pivotElement);
			for (Package pack : ((Model)pivotElement).getOwnedPackages()) {
				checkCancelled();
				oclElement.getChildren().add(evaluateCompleteOCLExpression(pack, null, evaluationTarget));
			}
		} else if (pivotElement instanceof Package) {
			oclElement = evaluateCompleteOCLExpression((Package)pivotElement, null, evaluationTarget);
		} else if (pivotElement instanceof org.eclipse.ocl.pivot.Class) {
			final Package packaje = ((org.eclipse.ocl.pivot.Class)pivotElement).getOwningPackage();
			final OCLElement packageElement = evaluateCompleteOCLExpression(packaje,
					(org.eclipse.ocl.pivot.Class)pivotElement, evaluationTarget);
			oclElement = packageElement.getChildren().get(0);
		} else if (pivotElement instanceof Constraint) {
			if (evaluationTarget instanceof EObject
					&& checkType((Constraint)pivotElement, (EObject)evaluationTarget)) {
				oclElement = EvaluationResultFactory.eINSTANCE.createConstraintElement();
				oclElement.setElement(pivotElement);
				final ConstraintResult result = evaluateExpression((Constraint)pivotElement,
						(EObject)evaluationTarget);
				((ConstraintElement)oclElement).getConstraintResults().add(result);
			} else {
				return new EvaluationResult(new Status(IStatus.INFO,
						CompleteOCLInterpreterActivator.PLUGIN_ID, "Cannot evaluate " + pivotElement
								+ " on the selected Notifier."));
			}
		} else if (pivotElement instanceof Operation) {
			if (evaluationTarget instanceof EObject
					&& ((Operation)pivotElement).getParameterTypes().get().length == 0
					&& checkType(((Operation)pivotElement).getOwningClass(), (EObject)evaluationTarget)) {
				final LanguageExpression spec = ((Operation)pivotElement).getBodyExpression();
				ExpressionInOCL expression;
				try {
					expression = metamodelManager.parseSpecification(spec);
				} catch (ParserException e) {
					return new EvaluationResult(new Status(IStatus.ERROR,
							CompleteOCLInterpreterActivator.PLUGIN_ID,
							"Unknown error evaluating expression.", e));
				}
				final EvaluationResult result = evaluateExpression(expression, (EObject)evaluationTarget);
				oclElement = EvaluationResultFactory.eINSTANCE.createOperationElement();
				oclElement.setElement(pivotElement);
				((OperationElement)oclElement).getEvaluationResults().add(
						parseOperationResult((EObject)evaluationTarget, result));
			} else {
				return new EvaluationResult(new Status(IStatus.INFO,
						CompleteOCLInterpreterActivator.PLUGIN_ID, "Cannot evaluate " + pivotElement
								+ " on the selected Notifier."));
			}
		} else {
			return new EvaluationResult(new Status(IStatus.ERROR, CompleteOCLInterpreterActivator.PLUGIN_ID,
					"Unknown error evaluating expression."));
		}
		return new EvaluationResult(oclElement);
	}

	private boolean checkType(Constraint constraint, EObject target) {
		final Namespace namespace = constraint.getContext();
		return namespace instanceof Type && checkType((Type)namespace, target);
	}

	private boolean checkType(Type type, EObject target) {
		final Type targetType;
		try {
			targetType = metamodelManager.getASOf(Type.class, target.eClass());
		} catch (ParserException e) {
			InterpreterPlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, CompleteOCLInterpreterActivator.PLUGIN_ID, e.getMessage(),
							e));
			return false;
		}
		if (completeModel.conformsTo(targetType, null, type, null)) {
			return true;
		} else {
			return false;
		}
	}

	private Map<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> getRelatedTypes(Package packaje) {
		final Map<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> types = new LinkedHashMap<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>>();
		for (org.eclipse.ocl.pivot.Class type : packaje.getOwnedClasses()) {
			final org.eclipse.ocl.pivot.Class key = completeModel.getCompleteClass(type).getPrimaryClass();
			Set<org.eclipse.ocl.pivot.Class> relatedTypes = types.get(key);
			if (relatedTypes == null) {
				relatedTypes = new LinkedHashSet<org.eclipse.ocl.pivot.Class>();
				types.put(key, relatedTypes);
			}
			relatedTypes.add(type);
		}
		return types;
	}

	private Map<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> getRelatedTypes(
			org.eclipse.ocl.pivot.Class type) {
		final Package packaje = type.getOwningPackage();
		if (packaje == null) {
			return Collections.singletonMap(type, Collections.singleton(type));
		}

		final org.eclipse.ocl.pivot.Class key = completeModel.getCompleteClass(type).getPrimaryClass();
		final Map<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> types = new LinkedHashMap<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>>();
		for (org.eclipse.ocl.pivot.Class candidate : packaje.getOwnedClasses()) {
			final org.eclipse.ocl.pivot.Class candidateKey = completeModel.getCompleteClass(candidate)
					.getPrimaryClass();
			if (key == candidateKey) {
				Set<org.eclipse.ocl.pivot.Class> relatedTypes = types.get(key);
				if (relatedTypes == null) {
					relatedTypes = new LinkedHashSet<org.eclipse.ocl.pivot.Class>();
					types.put(key, relatedTypes);
				}
				relatedTypes.add(candidate);
			}
		}
		return types;
	}

	/**
	 * Walks through the given package's types and evaluate all invariants they define.
	 * 
	 * @param compiledExpression
	 *            The pivot 'Package' expression.
	 * @param type
	 *            if we only need to evaluate the constraints of this type.
	 * @param evaluationTarget
	 *            The evaluation target.
	 * @return The composite result of this evaluation.
	 */
	private OCLElement evaluateCompleteOCLExpression(Package compiledExpression,
			org.eclipse.ocl.pivot.Class type, Notifier evaluationTarget) {
		final OCLElement oclElement = createOCLElement(compiledExpression);

		// OCL creates multiple "Type" expression if there are more than one on the same target class.
		// Make sure we regroup them accordingly.
		final Map<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> types;
		if (type != null) {
			types = getRelatedTypes(type);
		} else {
			types = getRelatedTypes(compiledExpression);
		}

		checkCancelled();

		final Iterable<EObject> candidates;
		if (evaluationTarget instanceof EObject) {
			candidates = Collections.singleton((EObject)evaluationTarget);
		} else if (evaluationTarget instanceof Resource) {
			candidates = new AllContentsIterable((Resource)evaluationTarget);
		} else {
			candidates = Collections.emptyList();
		}
		for (Map.Entry<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> entry : types
				.entrySet()) {
			checkCancelled();
			oclElement.getChildren().add(checkTypeAndEvaluate(entry, candidates));
		}

		return oclElement;
	}

	private OCLElement checkTypeAndEvaluate(
			Map.Entry<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> relatedTypes,
			Iterable<EObject> targetCandidates) {
		final OCLElement typeElement = createOCLElement(relatedTypes.getKey());
		final List<EObject> conformingTargets = new ArrayList<EObject>();
		for (EObject candidate : targetCandidates) {
			checkCancelled();
			if (checkType(relatedTypes.getKey(), candidate)) {
				conformingTargets.add(candidate);
			} else {
				// This type does not conform to the selected target.
			}
		}
		typeElement.getChildren().addAll(evaluateCompleteOCLExpression(relatedTypes, conformingTargets));
		return typeElement;
	}

	private OCLElement createOCLElement(EObject expression) {
		final OCLElement element = EvaluationResultFactory.eINSTANCE.createOCLElement();
		element.setElement(expression);
		return element;
	}

	/**
	 * Evaluates all invariants and operations contained within the given Complete OCL Types.
	 * 
	 * @param relatedTypes
	 *            All types related to the same target EClass.
	 * @param conformTargets
	 *            List of EObject conform to this type on which to evaluate the constraints.
	 * @return The composite result of this evaluation.
	 */
	private List<OCLElement> evaluateCompleteOCLExpression(
			Map.Entry<org.eclipse.ocl.pivot.Class, Set<org.eclipse.ocl.pivot.Class>> relatedTypes,
			List<EObject> conformTargets) {
		final List<OCLElement> childrenResult = new ArrayList<OCLElement>();
		for (org.eclipse.ocl.pivot.Class type : relatedTypes.getValue()) {
			checkCancelled();
			for (Constraint constraint : type.getOwnedInvariants()) {
				checkCancelled();
				final List<ConstraintResult> results = new ArrayList<ConstraintResult>(conformTargets.size());
				for (EObject target : conformTargets) {
					results.add(evaluateExpression(constraint, target));
				}

				if (!results.isEmpty()) {
					final ConstraintElement constraintElement = EvaluationResultFactory.eINSTANCE
							.createConstraintElement();
					constraintElement.setElement(constraint);
					constraintElement.getConstraintResults().addAll(results);
					childrenResult.add(constraintElement);
				}
			}

			for (Operation operation : type.getOwnedOperations()) {
				checkCancelled();
				if (operation.getParameterTypes().get().length != 0) {
					continue;
				}
				final List<OCLResult> results = new ArrayList<OCLResult>(conformTargets.size());
				for (EObject target : conformTargets) {
					final LanguageExpression spec = operation.getBodyExpression();
					EvaluationResult childResult;
					try {
						ExpressionInOCL expression = metamodelManager.parseSpecification(spec);
						childResult = evaluateExpression(expression, target);
					} catch (ParserException e) {
						childResult = new EvaluationResult(new Status(IStatus.ERROR,
								CompleteOCLInterpreterActivator.PLUGIN_ID,
								"Unknown error evaluating expression.", e));
					}
					results.add(parseOperationResult(target, childResult));
				}

				if (!results.isEmpty()) {
					final OperationElement operationElement = EvaluationResultFactory.eINSTANCE
							.createOperationElement();
					operationElement.setElement(operation);
					operationElement.getEvaluationResults().addAll(results);
					childrenResult.add(operationElement);
				}
			}
		}
		return childrenResult;
	}

	private ConstraintResult evaluateExpression(Constraint constraint, EObject evaluationTarget) {
		final LanguageExpression spec = constraint.getOwnedSpecification();
		ExpressionInOCL expressionInOCL = null;
		EvaluationResult result;
		try {
			expressionInOCL = metamodelManager.parseSpecification(spec);
			final OCLExpression expression = AbstractConstraintEvaluator
					.getConstraintExpression(expressionInOCL);
			result = internalEvaluateExpression(expression, evaluationTarget);
		} catch (ParserException e) {
			result = new EvaluationResult(new Status(IStatus.ERROR,
					CompleteOCLInterpreterActivator.PLUGIN_ID, "Unknown error evaluating expression.", e));
		}
		return new OCLConstraintParser(expressionInOCL).parse(evaluationTarget, result);
	}

	private OCLResult parseOperationResult(EObject evaluationTarget, EvaluationResult result) {
		final OCLResult operationResult = EvaluationResultFactory.eINSTANCE.createOCLResult();
		operationResult.setEvaluationTarget(evaluationTarget);
		operationResult.setInterpreterResult(result);
		return operationResult;
	}

	/**
	 * Throws a new {@link CancellationException} if the current thread has been cancelled.
	 */
	private void checkCancelled() {
		if (Thread.currentThread().isInterrupted()) {
			throw new CancellationException();
		}
	}

	private static class AllContentsIterable implements Iterable<EObject> {
		private Resource resource;

		public AllContentsIterable(Resource resource) {
			this.resource = resource;
		}

		public Iterator<EObject> iterator() {
			return resource.getAllContents();
		}
	}

	/*
	 * FIXME there is no API to retrieve the evaluation result except for within sub-classes of this. We'd
	 * most likely be better off copy/pasting the methods we need.
	 */
	private class OCLConstraintParser extends AbstractConstraintEvaluator<ConstraintResult> {

		private EObject currentevaluationTarget;

		public OCLConstraintParser(ExpressionInOCL expression) {
			super(expression);
		}

		public ConstraintResult parse(EObject evaluationTarget, EvaluationResult result) {
			currentevaluationTarget = evaluationTarget;
			final ConstraintResult constraintResult = EvaluationResultFactory.eINSTANCE
					.createConstraintResult();
			constraintResult.setEvaluationTarget(evaluationTarget);
			final Object evaluationResult = result.getEvaluationResult();
			if (evaluationResult instanceof Value) {
				constraintResult.setInterpreterResult(new EvaluationResult(unwrap((Value)evaluationResult)));
			} else {
				constraintResult.setInterpreterResult(result);
			}
			if (getConstraintResultStatus(evaluationResult)) {
				constraintResult.setSeverity(Severity.OK);
			} else {
				switch (getConstraintResultSeverity(evaluationResult)) {
					case Diagnostic.INFO:
						constraintResult.setSeverity(Severity.INFO);
						break;
					case Diagnostic.WARNING:
						constraintResult.setSeverity(Severity.WARNING);
						break;
					case Diagnostic.ERROR:
						constraintResult.setSeverity(Severity.ERROR);
						break;
					default:
						constraintResult.setSeverity(Severity.ERROR);
						break;
				}
				constraintResult.setMessage(getConstraintResultMessage(evaluationResult));
			}
			return constraintResult;
		}

		@Override
		protected String getObjectLabel() {
			AdapterFactory factory = new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			IItemLabelProvider provider = (IItemLabelProvider)factory.adapt(currentevaluationTarget,
					IItemLabelProvider.class);
			if (provider != null) {
				return provider.getText(currentevaluationTarget);
			}
			return null;
		}

		@Override
		protected ConstraintResult handleExceptionResult(Throwable e) {
			return null;
		}

		@Override
		protected ConstraintResult handleFailureResult(Object result) {
			return null;
		}

		@Override
		protected ConstraintResult handleInvalidResult(InvalidValueException e) {
			return null;
		}

		@Override
		protected ConstraintResult handleSuccessResult() {
			return null;
		}

		@Override
		protected ConstraintResult handleInvalidExpression(String message) {
			return null;
		}
	}
}
