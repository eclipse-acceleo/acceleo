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
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.Namespace;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.Package;
import org.eclipse.ocl.examples.pivot.ParserException;
import org.eclipse.ocl.examples.pivot.PivotConstants;
import org.eclipse.ocl.examples.pivot.Property;
import org.eclipse.ocl.examples.pivot.PropertyCallExp;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.TupleType;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.utilities.ConstraintEvaluator;

public class CompleteOCLEvaluator extends AbstractOCLEvaluator {
	private final MetaModelManager metaModelManager;

	public CompleteOCLEvaluator(MetaModelManager metaModelManager) {
		this.metaModelManager = metaModelManager;
	}

	@Override
	protected MetaModelManager getMetaModelManager() {
		return metaModelManager;
	}

	public EvaluationResult evaluateCompleteOCLElement(Element pivotElement, Notifier evaluationTarget) {
		final OCLElement oclElement;
		if (pivotElement instanceof Root) {
			oclElement = createOCLElement(pivotElement);
			for (Package pack : ((Root)pivotElement).getNestedPackage()) {
				checkCancelled();
				oclElement.getChildren().add(evaluateCompleteOCLExpression(pack, null, evaluationTarget));
			}
		} else if (pivotElement instanceof Package) {
			oclElement = evaluateCompleteOCLExpression((Package)pivotElement, null, evaluationTarget);
		} else if (pivotElement instanceof Type) {
			final Package packaje = ((Type)pivotElement).getPackage();
			final OCLElement packageElement = evaluateCompleteOCLExpression(packaje, (Type)pivotElement,
					evaluationTarget);
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
					&& checkType(((Operation)pivotElement).getOwningType(), (EObject)evaluationTarget)) {
				final EvaluationResult result = evaluateExpression(((Operation)pivotElement)
						.getBodyExpression().getExpressionInOCL(), (EObject)evaluationTarget);

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
			targetType = metaModelManager.getPivotOf(Type.class, target.eClass());
		} catch (ParserException e) {
			InterpreterPlugin
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, CompleteOCLInterpreterActivator.PLUGIN_ID, e.getMessage(),
							e));
			return false;
		}
		if (metaModelManager.conformsTo(targetType, type, null)) {
			return true;
		} else {
			return false;
		}
	}

	private Map<Type, Set<Type>> getRelatedTypes(Package packaje) {
		final Map<Type, Set<Type>> types = new LinkedHashMap<Type, Set<Type>>();
		for (Type type : packaje.getOwnedType()) {
			final Type key = metaModelManager.getTypeServer(type).getPivotType();
			Set<Type> relatedTypes = types.get(key);
			if (relatedTypes == null) {
				relatedTypes = new LinkedHashSet<Type>();
				types.put(key, relatedTypes);
			}
			relatedTypes.add(type);
		}
		return types;
	}

	private Map<Type, Set<Type>> getRelatedTypes(Type type) {
		final Package packaje = type.getPackage();
		if (packaje == null) {
			return Collections.singletonMap(type, Collections.singleton(type));
		}

		final Type key = metaModelManager.getTypeServer(type).getPivotType();
		final Map<Type, Set<Type>> types = new LinkedHashMap<Type, Set<Type>>();
		for (Type candidate : packaje.getOwnedType()) {
			final Type candidateKey = metaModelManager.getTypeServer(candidate).getPivotType();
			if (key == candidateKey) {
				Set<Type> relatedTypes = types.get(key);
				if (relatedTypes == null) {
					relatedTypes = new LinkedHashSet<Type>();
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
	private OCLElement evaluateCompleteOCLExpression(Package compiledExpression, Type type,
			Notifier evaluationTarget) {
		final OCLElement oclElement = createOCLElement(compiledExpression);

		// OCL creates multiple "Type" expression if there are more than one on the same target class.
		// Make sure we regroup them accordingly.
		final Map<Type, Set<Type>> types;
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
		for (Map.Entry<Type, Set<Type>> entry : types.entrySet()) {
			checkCancelled();
			oclElement.getChildren().add(checkTypeAndEvaluate(entry, candidates));
		}

		return oclElement;
	}

	private OCLElement checkTypeAndEvaluate(Map.Entry<Type, Set<Type>> relatedTypes,
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
	private List<OCLElement> evaluateCompleteOCLExpression(Map.Entry<Type, Set<Type>> relatedTypes,
			List<EObject> conformTargets) {
		final List<OCLElement> childrenResult = new ArrayList<OCLElement>();
		for (Type type : relatedTypes.getValue()) {
			checkCancelled();
			for (Constraint constraint : type.getOwnedInvariant()) {
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

			for (Operation operation : type.getOwnedOperation()) {
				checkCancelled();
				if (operation.getParameterTypes().get().length != 0) {
					continue;
				}
				final List<OCLResult> results = new ArrayList<OCLResult>(conformTargets.size());
				for (EObject target : conformTargets) {
					final EvaluationResult childResult = evaluateExpression(operation.getBodyExpression()
							.getExpressionInOCL(), target);
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
		final ExpressionInOCL expressionInOCL = constraint.getSpecification().getExpressionInOCL();
		final OCLExpression expression = getConstraintExpression(expressionInOCL);
		final EvaluationResult result = internalEvaluateExpression(expression, evaluationTarget);
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

	private static OCLExpression getConstraintExpression(ExpressionInOCL constraintSpecification) {
		final OCLExpression body = constraintSpecification.getBodyExpression();
		if (body instanceof PropertyCallExp) {
			Property referredProperty = ((PropertyCallExp)body).getReferredProperty();
			if ((referredProperty != null) && (referredProperty.getOwningType() instanceof TupleType)
					&& PivotConstants.STATUS_PART_NAME.equals(referredProperty.getName())) {
				return ((PropertyCallExp)body).getSource();
			}
		}
		return body;
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
	private class OCLConstraintParser extends ConstraintEvaluator<ConstraintResult> {

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