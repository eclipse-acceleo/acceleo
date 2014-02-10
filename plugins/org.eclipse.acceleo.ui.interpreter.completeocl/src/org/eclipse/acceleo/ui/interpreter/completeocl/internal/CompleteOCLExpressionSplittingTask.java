/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.SplitExpression;
import org.eclipse.acceleo.ui.interpreter.language.SubExpression;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ocl.examples.pivot.CallExp;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.IfExp;
import org.eclipse.ocl.examples.pivot.LetExp;
import org.eclipse.ocl.examples.pivot.LiteralExp;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.OperationCallExp;
import org.eclipse.ocl.examples.pivot.Package;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.Variable;
import org.eclipse.ocl.examples.pivot.VariableExp;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.util.AbstractExtendingVisitor;
import org.eclipse.ocl.examples.pivot.util.Visitable;

/**
 * This class aims at providing the necessary API to split an OCL query that was compiled from a simple
 * String.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public class CompleteOCLExpressionSplittingTask implements Callable<SplitExpression> {
	/** The current context. */
	private EvaluationContext context;

	/** Current metaModel Manager. */
	private final MetaModelManager metaModelManager;

	/**
	 * Instantiates the splitting task for the given evaluation context.
	 * 
	 * @param context
	 *            The current context.
	 * @param metaModelManager
	 *            The Current MetaModel Manager.
	 */
	public CompleteOCLExpressionSplittingTask(EvaluationContext context, MetaModelManager metaModelManager) {
		this.context = context;
		this.metaModelManager = metaModelManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public SplitExpression call() throws Exception {
		checkCancelled();

		CompilationResult compilationResult = context.getCompilationResult();
		if (compilationResult == null
				|| (compilationResult.getStatus() != null && compilationResult.getStatus().getSeverity() != IStatus.OK)) {
			return null;
		}

		final SplitExpression result;
		Object expression = compilationResult.getCompiledExpression();
		if (expression instanceof Root) {
			result = splitCompleteOCLExpression((Root)expression);
		} else {
			// Cannot split an expression that is not a pivot Root
			result = null;
		}

		return result;
	}

	private SplitExpression splitCompleteOCLExpression(Root root) {
		final List<SubExpression> children = new ArrayList<SubExpression>();
		for (Package pack : root.getNestedPackage()) {
			checkCancelled();
			children.add(splitCompleteOCLExpression(pack));
		}
		return new SplitExpression(root, children, "All Expressions");
	}

	private SubExpression splitCompleteOCLExpression(Package pack) {
		final SubExpression packageStep = new SubExpression(pack, "Package - " + pack.getName());

		// OCL creates multiple "Type" expression if there are more than one on the same target class.
		// Make sure we regroup them accordingly.
		final Map<Type, Set<Type>> types = new LinkedHashMap<Type, Set<Type>>();
		for (Type type : pack.getOwnedType()) {
			final Type key = metaModelManager.getTypeServer(type).getPivotType();
			Set<Type> relatedTypes = types.get(key);
			if (relatedTypes == null) {
				relatedTypes = new LinkedHashSet<Type>();
				types.put(key, relatedTypes);
			}
			relatedTypes.add(type);
		}

		checkCancelled();

		for (Map.Entry<Type, Set<Type>> entry : types.entrySet()) {
			checkCancelled();
			packageStep.addSubStep(splitCompleteOCLExpression(entry));
		}

		for (Constraint constraint : pack.getOwnedRule()) {
			checkCancelled();
			packageStep.addSubStep(splitCompleteOCLExpression(constraint));
		}

		return packageStep;
	}

	private SubExpression splitCompleteOCLExpression(Map.Entry<Type, Set<Type>> relatedTypes) {
		final SubExpression typeStep = new SubExpression(relatedTypes.getKey(), "Context - "
				+ relatedTypes.getKey());
		for (Type type : relatedTypes.getValue()) {
			checkCancelled();
			for (Constraint constraint : type.getOwnedInvariant()) {
				checkCancelled();
				typeStep.addSubStep(splitCompleteOCLExpression(constraint));
			}
			for (Operation operation : type.getOwnedOperation()) {
				checkCancelled();
				// We don't need to split an operation with parameters here
				if (operation.getParameterTypes().get().length == 0) {
					typeStep.addSubStep(splitCompleteOCLExpression(operation));
				}
			}
		}
		return typeStep;
	}

	private SubExpression splitCompleteOCLExpression(Constraint constraint) {
		final SubExpression constraintSteps = new SubExpression(constraint, "Constraint - "
				+ constraint.getName());
		constraintSteps.addSubStep(splitExpression(constraint.getSpecification().getExpressionInOCL()));
		return constraintSteps;
	}

	private SubExpression splitCompleteOCLExpression(Operation operation) {
		final SubExpression operationSteps = new SubExpression(operation, "Operation - "
				+ operation.getName());
		operationSteps.addSubStep(splitExpression(operation.getBodyExpression().getExpressionInOCL()));
		return operationSteps;
	}

	private SubExpression splitExpression(ExpressionInOCL expression) {
		SplittingVisitor visitor = new SplittingVisitor(expression);
		visitor.visitExpressionInOCL(expression);

		SubExpression expressionStep = new SubExpression(expression);
		for (SubExpression subStep : visitor.getSubExpressions()) {
			expressionStep.addSubStep(subStep);
		}
		return expressionStep;
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
	 * The only purpose of this visitor is to split the given expression into its individual components.
	 * 
	 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
	 */
	private static class SplittingVisitor extends AbstractExtendingVisitor<SubExpression, ExpressionInOCL> {
		/**
		 * Some sub-expressions can have sub-steps of theirs own (for example, an operation call contains all
		 * of its arguments as sub-steps). We'll push each such "parent" sub-expression on top of this stack,
		 * whereas the "leaf" sub-expressions will be added as a sub-step of the parent currently on the tip
		 * of this stack.
		 */
		private LinkedList<SubExpression> expressionStack = new LinkedList<SubExpression>();

		/**
		 * This splits the OCL Expression to several sub-steps.
		 * 
		 * @param expressionInOCL
		 *            The OCL expression to evaluate.
		 */
		public SplittingVisitor(ExpressionInOCL expressionInOCL) {
			super(expressionInOCL);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitExpressionInOCL(ExpressionInOCL)
		 */
		@Override
		public SubExpression visitExpressionInOCL(ExpressionInOCL object) {
			expressionStack.addFirst(new SubExpression(object.getBodyExpression()));
			return object.getBodyExpression().accept(this);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visiting(Visitable)
		 */
		public SubExpression visiting(Visitable visitable) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitOperationCallExp(OperationCallExp)
		 */
		@Override
		public SubExpression visitOperationCallExp(OperationCallExp object) {
			for (OCLExpression expression : object.getArgument()) {
				addAndVisitSubStep(expression);
			}
			SubExpression result = super.visitOperationCallExp(object);
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitLetExp(LetExp)
		 */
		@Override
		public SubExpression visitLetExp(LetExp object) {
			addChild(new SubExpression(object));

			// variable definition should not be displayed
			object.getVariable().accept(this);

			return addAndVisitSubStep(object.getIn());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitVariable(Variable)
		 */
		@Override
		public SubExpression visitVariable(Variable object) {
			return addAndVisitSubStep(object.getInitExpression());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitIfExp(IfExp)
		 */
		@Override
		public SubExpression visitIfExp(IfExp object) {
			addChild(new SubExpression(object));
			SubExpression result = super.visitIfExp(object);

			addAndVisitSubStep(object.getCondition());
			addAndVisitSubStep(object.getThenExpression());
			addAndVisitSubStep(object.getElseExpression());

			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitVariableExp(VariableExp)
		 */
		@Override
		public SubExpression visitVariableExp(VariableExp object) {
			addChild(new SubExpression(object));
			return super.visitVariableExp(object);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitLiteralExp(LiteralExp)
		 */
		@Override
		public SubExpression visitLiteralExp(LiteralExp object) {
			addChild(new SubExpression(object));
			return super.visitLiteralExp(object);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.examples.pivot.util.Visitor#visitCallExp(CallExp)
		 */
		@Override
		public SubExpression visitCallExp(CallExp object) {
			addChild(new SubExpression(object));
			return addAndVisitSubStep(object.getSource());
		}

		/**
		 * Returns all sub-expressions registered by this visitor.
		 * 
		 * @return All sub-expressions registered by this visitor.
		 */
		public List<SubExpression> getSubExpressions() {
			return expressionStack.pop().getSubSteps();
		}

		/**
		 * This will, in order :
		 * <ul>
		 * <li>Add the given expression as a sub-step of the expression currently at the tip of the expression
		 * stack,</li>
		 * <li>Push the given expression on top of the expression stack so that all of its own sub-steps can
		 * be registered,</li>
		 * <li>Visit the given expression to register its sub-steps,</li>
		 * <li>Pop the given expression out of the expression stack.</li>
		 * </ul>
		 * 
		 * @param subStep
		 *            The expression which sub-steps to register.
		 * @return The SubExpression instance corresponding to the given expression.
		 */
		private SubExpression addAndVisitSubStep(OCLExpression subStep) {
			final SubExpression subExpression = new SubExpression(subStep);
			addChild(subExpression);
			expressionStack.addFirst(subExpression);
			subStep.accept(this);
			expressionStack.pop();
			return subExpression;
		}

		/**
		 * Adds the given expression to the children of the expression currently at the tip of the expression
		 * stack.
		 * 
		 * @param subExpression
		 *            The sub-expression to add.
		 */
		private void addChild(SubExpression subExpression) {
			SubExpression parent = expressionStack.peek();
			if (!parent.getExpression().equals(subExpression.getExpression())) {
				parent.addSubStep(subExpression);
			}
		}
	}
}
