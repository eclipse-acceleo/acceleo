/*******************************************************************************
 * Copyright (c) 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl;

import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LanguageExpression;
import org.eclipse.ocl.pivot.Operation;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.ParserException;

/**
 * Exports all the complete OCL Interpreter view results as an html file.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public class HTMLExporter extends AbstractExporter {
	protected final OCL ocl;

	public HTMLExporter(OCL ocl) {
		this.ocl = ocl;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.acceleo.ui.interpreter.completeocl.AbstractExporter#createContents(java.lang.Appendable,
	 * java.lang.String, org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement)
	 */
	@Override
	public void createContents(Appendable html, String outputFileName, OCLElement resultRoot)
			throws IOException {
		appendHeader(html);

		appendGeneralInformation(html, outputFileName);

		html.append("\n"); //$NON-NLS-1$
		html.append("\n"); //$NON-NLS-1$

		int constraintstotal = getConstraintsCount();
		int operationstotal = getOperationsCount();
		appendMetrics(html, constraintstotal, operationstotal);

		appendConstraintsLogs(html, constraintstotal);

		appendOperationsLogs(html);

		appendFooter(html);
	}

	/**
	 * Appends the target html file header.
	 * 
	 * @param html
	 *                 the string appender.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendHeader(Appendable html) throws IOException {
		html.append("<html>\n"); //$NON-NLS-1$
		html.append("\t<head></head>\n"); //$NON-NLS-1$
		html.append("\t<body>\n"); //$NON-NLS-1$
	}

	/**
	 * Appends the target html file general information.
	 * 
	 * @param html
	 *                           the string appender.
	 * @param outputFileName
	 *                           the target file name.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendGeneralInformation(Appendable html, String outputFileName) throws IOException {
		html.append("\t\t<h1>1. GENERAL INFORMATION</h1>\n"); //$NON-NLS-1$
		html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$

		if (outputFileName != null) {
			html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
			html.append("\t\t\t\t<td><b>Output file name: </b></td>\n"); //$NON-NLS-1$
			html.append("\t\t\t\t<td>" + outputFileName + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
			html.append("\t\t\t</tr>\n"); //$NON-NLS-1$
		}

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Author: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + System.getProperty("user.name") + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t</table>\n"); //$NON-NLS-1$
	}

	/**
	 * Appends the constraints and the operations metrics.
	 * 
	 * @param html
	 *                             the string appender
	 * @param constraintstotal
	 *                             the total number of evaluated constraints.
	 * @param operationstotal
	 *                             the total number of evaluated operations.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendMetrics(Appendable html, int constraintstotal, int operationstotal)
			throws IOException {
		html.append("\t\t<h1>2. METRICS</h1>\n"); //$NON-NLS-1$
		html.append("\n"); //$NON-NLS-1$
		html.append("\t\t<h1>2.1. Constraints METRICS</h1>\n"); //$NON-NLS-1$
		html.append("\n"); //$NON-NLS-1$
		html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Total number of evaluated constraints: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + constraintstotal + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Success: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + constraintsSuccess.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Infos: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + constraintsInfos.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Warnings: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + constraintsWarnings.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Errors: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + constraintsErrors.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t</table>\n"); //$NON-NLS-1$

		html.append("\n"); //$NON-NLS-1$

		html.append("\t\t<h1>2.2. Statistics METRICS</h1>\n"); //$NON-NLS-1$
		html.append("\n"); //$NON-NLS-1$
		html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Total number of evaluated operations: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + operationstotal + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Success: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + operationsSuccess.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Infos: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + operationsInfos.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Warnings: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + operationsWarnings.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td><b>Number of Errors: </b></td>\n"); //$NON-NLS-1$
		html.append("\t\t\t\t<td>" + operationsErrors.size() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		html.append("\t\t\t</tr>\n"); //$NON-NLS-1$

		html.append("\t\t</table>\n"); //$NON-NLS-1$
	}

	/**
	 * Appends the constraints logs.
	 * 
	 * @param html
	 *                             the string appender.
	 * @param constraintstotal
	 *                             the total number of evaluated constraints.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendConstraintsLogs(Appendable html, int constraintstotal) throws IOException {
		html.append("\t\t<h1>3. LOGS</h1>\n"); //$NON-NLS-1$

		if (constraintsSuccess.size() == constraintstotal) {
			html.append(
					"<p>No log to display: all constraints has been successfully evaluated on the current model.</p>\n"); //$NON-NLS-1$
		} else {
			int section = 1;
			if (!constraintsInfos.isEmpty()) {
				html.append("\t\t<h2>3." + section + ". Infos</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
				html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
				appendConstraintsTitlesTable(html);
				for (ConstraintElement infoElement : constraintsInfos) {
					appendConstraintLog(html, infoElement, Severity.INFO.getLiteral());
				}
				html.append("\t\t</table>\n"); //$NON-NLS-1$
				section++;
			}
			if (!constraintsWarnings.isEmpty()) {
				html.append("\t\t<h2>3." + section + ". Warnings</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
				html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
				appendConstraintsTitlesTable(html);
				for (ConstraintElement warningElement : constraintsWarnings) {
					appendConstraintLog(html, warningElement, Severity.WARNING.getLiteral());
				}
				html.append("\t\t</table>\n"); //$NON-NLS-1$
				section++;
			}
			if (!constraintsErrors.isEmpty()) {
				html.append("\t\t<h2>3." + section + ". Errors</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
				html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
				appendConstraintsTitlesTable(html);
				for (ConstraintElement errorElement : constraintsErrors) {
					appendConstraintLog(html, errorElement, Severity.ERROR.getLiteral());
				}
				html.append("\t\t</table>\n"); //$NON-NLS-1$
				section++;
			}
		}
	}

	/**
	 * The constraints titles table appender.
	 * 
	 * @param s
	 *              the string appender.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendConstraintsTitlesTable(Appendable s) throws IOException {
		s.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Context</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Invariant</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Expression</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Evaluation Target</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Severity</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Message</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t</tr>\n"); //$NON-NLS-1$
	}

	/**
	 * Appends the target file with all the constraints evaluation results of a specific constraint element.
	 * 
	 * @param s
	 *                              the string appender.
	 * @param constraintElement
	 *                              the given constraint constraint element.
	 * @param severity
	 *                              the severity.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendConstraintLog(Appendable s, ConstraintElement constraintElement, String severity)
			throws IOException {
		EObject element = constraintElement.getElement();
		if (element instanceof Constraint) {
			Constraint constraint = ((Constraint)element);
			String context = constraint.getContext().toString();
			String identifier = constraint.getName();
			try {
				final LanguageExpression spec = constraint.getOwnedSpecification();
				ExpressionInOCL expressionInOCL = ocl.parseSpecification(constraint, spec);
				String expression = expressionInOCL.toString();
				for (ConstraintResult result : constraintElement.getConstraintResults()) {
					s.append("\t\t\t<tr>\n"); //$NON-NLS-1$
					appendConstraintsResult(s, context, identifier, expression, severity, result);
					s.append("\t\t\t</tr>\n"); //$NON-NLS-1$
				}
			} catch (ParserException e) {
				// swallow
			}

		}
	}

	/**
	 * Appends a specific result of a specific constraint element.
	 * 
	 * @param s
	 *                       the string appender.
	 * @param context
	 *                       the given constraint element context.
	 * @param identifier
	 *                       the given constraint element identifier.
	 * @param expression
	 *                       the given constraint element expression.
	 * @param severity
	 *                       the given constraint element severity.
	 * @param result
	 *                       the given constraint element result.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendConstraintsResult(Appendable s, String context, String identifier, String expression,
			String severity, OCLResult result) throws IOException {
		if (context != null) {
			s.append("\t\t\t\t<td>" + context + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			s.append("\t\t\t\t<td>" //$NON-NLS-1$
					+ "The context of this constraint is unattainable" //$NON-NLS-1$
					+ "</td>\n"); //$NON-NLS-1$
		}
		if (identifier != null) {
			s.append("\t\t\t\t<td>" + identifier + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			s.append("\t\t\t\t<td>" //$NON-NLS-1$
					+ "The identifier of this constraint is unattainable" //$NON-NLS-1$
					+ "</td>\n"); //$NON-NLS-1$
		}
		if (expression != null) {
			s.append("\t\t\t\t<td>" + expression + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			s.append("\t\t\t\t<td>" //$NON-NLS-1$
					+ "The Expression of this constraint is unattainable"//$NON-NLS-1$
					+ "</td>\n"); //$NON-NLS-1$
		}
		s.append("\t\t\t\t<td>" + getEvaluationTargetText(result.getEvaluationTarget()) + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$

		s.append("\t\t\t\t<td>" + severity + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		s.append("\t\t\t\t<td>" + StringEscapeUtils.escapeHtml4(getMessage(result)) + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Appends the operations (statistics) logs.
	 * 
	 * @param html
	 *                            the string appender.
	 * @param operationstotal
	 *                            the total number of evaluated operations.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendOperationsLogs(Appendable html) throws IOException {
		html.append("\t\t<h1>4. STATISTICS</h1>\n"); //$NON-NLS-1$

		int section = 1;
		if (operationsSuccess.isEmpty() && operationsInfos.isEmpty() && operationsWarnings.isEmpty()
				&& operationsErrors.isEmpty()) {
			html.append(
					"<p>No log to display: there is no operation to evaluate on the current model.</p>\n"); //$NON-NLS-1$
			return;
		}
		if (!operationsSuccess.isEmpty()) {
			html.append("\t\t<h2>4." + section + ". Success</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
			html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
			appendOperationsTitlesTable(html);
			for (OperationElement successElement : operationsSuccess) {
				appendOperationLog(html, successElement, Severity.OK.getLiteral());
			}
			html.append("\t\t</table>\n"); //$NON-NLS-1$
			section++;
		}
		if (!operationsInfos.isEmpty()) {
			html.append("\t\t<h2>4." + section + ". Infos</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
			html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
			appendOperationsTitlesTable(html);
			for (OperationElement infoElement : operationsInfos) {
				appendOperationLog(html, infoElement, Severity.INFO.getLiteral());
			}
			html.append("\t\t</table>\n"); //$NON-NLS-1$
			section++;
		}
		if (!operationsWarnings.isEmpty()) {
			html.append("\t\t<h2>4." + section + ". Warnings</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
			html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
			appendOperationsTitlesTable(html);
			for (OperationElement warningElement : operationsWarnings) {
				appendOperationLog(html, warningElement, Severity.WARNING.getLiteral());
			}
			html.append("\t\t</table>\n"); //$NON-NLS-1$
			section++;
		}
		if (!operationsErrors.isEmpty()) {
			html.append("\t\t<h2>4." + section + ". Errors</h2>\n"); //$NON-NLS-1$ //$NON-NLS-2$
			html.append("\t\t<table border=\"1\">\n"); //$NON-NLS-1$
			appendOperationsTitlesTable(html);
			for (OperationElement errorElement : operationsErrors) {
				appendOperationLog(html, errorElement, Severity.ERROR.getLiteral());
			}
			html.append("\t\t</table>\n"); //$NON-NLS-1$
			section++;
		}
	}

	/**
	 * The operations titles table appender.
	 * 
	 * @param s
	 *              the string appender.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendOperationsTitlesTable(Appendable s) throws IOException {
		s.append("\t\t\t<tr>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Context</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Operation</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Expression</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Evaluation Target</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Severity</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t\t<td><b>Result</b></td>\n"); //$NON-NLS-1$
		s.append("\t\t\t</tr>\n"); //$NON-NLS-1$
	}

	/**
	 * Appends the target file with all the operations evaluation results of a specific operation element.
	 * 
	 * @param s
	 *                             the string appender.
	 * @param operationElement
	 *                             the given operation operation element.
	 * @param severity
	 *                             the severity.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendOperationLog(Appendable s, OperationElement operationElement, String severity)
			throws IOException {
		EObject element = operationElement.getElement();
		if (element instanceof Operation) {
			Operation operation = ((Operation)element);
			String context = operation.getOwningClass().toString();
			String identifier = operation.getName();
			try {
				final LanguageExpression spec = operation.getBodyExpression();
				ExpressionInOCL expressionInOCL = ocl.parseSpecification(operation, spec);
				String expression = expressionInOCL.toString();
				for (OCLResult result : operationElement.getEvaluationResults()) {
					s.append("\t\t\t<tr>\n"); //$NON-NLS-1$
					appendOperationsResult(s, context, identifier, expression, severity, result);
					s.append("\t\t\t</tr>\n"); //$NON-NLS-1$
				}
			} catch (ParserException e) {
				// swallow
			}
		}
	}

	/**
	 * Appends a specific result of a specific operation element.
	 * 
	 * @param s
	 *                       the string appender.
	 * @param context
	 *                       the given operation element context.
	 * @param identifier
	 *                       the given operation element identifier.
	 * @param expression
	 *                       the given operation element expression.
	 * @param severity
	 *                       the given operation element severity.
	 * @param result
	 *                       the given operation element result.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendOperationsResult(Appendable s, String context, String identifier, String expression,
			String severity, OCLResult result) throws IOException {
		if (context != null) {
			s.append("\t\t\t\t<td>" + context + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			s.append("\t\t\t\t<td>" //$NON-NLS-1$
					+ "The context of this operation is unattainable" //$NON-NLS-1$
					+ "</td>\n"); //$NON-NLS-1$
		}
		if (identifier != null) {
			s.append("\t\t\t\t<td>" + identifier + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			s.append("\t\t\t\t<td>" //$NON-NLS-1$
					+ "The identifier of this operation is unattainable" //$NON-NLS-1$
					+ "</td>\n"); //$NON-NLS-1$
		}

		if (expression != null) {
			s.append("\t\t\t\t<td>" + expression + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			s.append("\t\t\t\t<td>" //$NON-NLS-1$
					+ "The Expression of this operation is unattainable"//$NON-NLS-1$
					+ "</td>\n"); //$NON-NLS-1$
		}
		s.append("\t\t\t\t<td>" + getEvaluationTargetText(result.getEvaluationTarget()) + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$

		s.append("\t\t\t\t<td>" + severity + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		s.append("\t\t\t\t<td>" + result.getInterpreterResult().getEvaluationResult() + "</td>\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Appends the target html file footer.
	 * 
	 * @param html
	 *                 the string appender.
	 * @throws IOException
	 *                         threw when an IO Exception occurs.
	 */
	private void appendFooter(Appendable html) throws IOException {
		html.append("</body>\n"); //$NON-NLS-1$
		html.append("</html>\n"); //$NON-NLS-1$
	}

	/**
	 * The label text for the adapted evaluation target.
	 * 
	 * @param evaluationTarget
	 *                             The evaluation target.
	 * @return The label text for the adapted evaluation target.
	 */
	private String getEvaluationTargetText(EObject evaluationTarget) {
		AdapterFactory factory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		IItemLabelProvider provider = (IItemLabelProvider)factory.adapt(evaluationTarget,
				IItemLabelProvider.class);
		if (provider != null) {
			return provider.getText(evaluationTarget);
		}
		return evaluationTarget.toString();
	}
}
