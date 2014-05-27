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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.google.common.io.Files;

/**
 * Exports all the complete OCL Interpreter view results. This view contains logs and statistics results.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public abstract class AbstractExporter implements IEvaluationExporter {
	/** The list of all successful constraints. */
	protected final List<ConstraintElement> constraintsSuccess = new ArrayList<ConstraintElement>();

	/** The list of all errors constraints. */
	protected final List<ConstraintElement> constraintsErrors = new ArrayList<ConstraintElement>();

	/** The list of all warnings constraints. */
	protected final List<ConstraintElement> constraintsWarnings = new ArrayList<ConstraintElement>();

	/** The list of all infos constraints. */
	protected final List<ConstraintElement> constraintsInfos = new ArrayList<ConstraintElement>();

	/** The list of all successful operations. */
	protected final List<OperationElement> operationsSuccess = new ArrayList<OperationElement>();

	/** The list of all errors operations. */
	protected final List<OperationElement> operationsErrors = new ArrayList<OperationElement>();

	/** The list of all warnings operations. */
	protected final List<OperationElement> operationsWarnings = new ArrayList<OperationElement>();

	/** The list of all infos operations. */
	protected final List<OperationElement> operationsInfos = new ArrayList<OperationElement>();

	/**
	 * Clears all the lists of the current class.
	 */
	private void clearLists() {
		constraintsSuccess.clear();
		constraintsErrors.clear();
		constraintsWarnings.clear();
		constraintsInfos.clear();
		operationsSuccess.clear();
		operationsErrors.clear();
		operationsWarnings.clear();
		operationsInfos.clear();
	}

	/**
	 * The invocation of this method fills the appender string with the initial contents of the new exported
	 * evaluation results file.
	 * 
	 * @param s
	 *            the string appender.
	 * @param outputFileName
	 *            The target file name or null if not known and not to be reported.
	 * @param resultRoot
	 *            The evaluation result model.
	 * @throws IOException
	 *             threw when an IO exception occurs.
	 */
	protected abstract void createContents(Appendable s, String outputFileName, OCLElement resultRoot)
			throws IOException;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.IEvaluationExporter#export(java.lang.String,
	 * org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement)
	 */
	public void export(String outputPath, OCLElement resultRoot, IProgressMonitor monitor)
			throws IOException, CoreException {
		StringBuilder s = new StringBuilder();
		File targetFile = new File(outputPath);
		try {
			export(s, targetFile.getName(), resultRoot);

			byte[] byteArrayInputStream = s.toString().getBytes(Charset.forName("UTF-8"));
			if (targetFile.isAbsolute()) {
				Files.write(byteArrayInputStream, targetFile);
			} else {
				final InputStream contentStream = new ByteArrayInputStream(byteArrayInputStream);
				final IFile exportedIFile = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(new Path(outputPath));
				exportedIFile.create(contentStream, true, monitor);
			}
		} catch (IOException e) { /* StringBuilder doesn't throw IOExceptions */
		}
	}

	/**
	 * Exports using a string appender all results (logs and statistics).
	 * 
	 * @param s
	 *            the string appender used to export all results.
	 * @param outputFileName
	 *            the target file name.
	 * @param resultRoot
	 *            the root of the evaluation model.
	 * @throws IOException
	 *             threw when an IO exception occurs.
	 */
	public void export(Appendable s, String outputFileName, OCLElement resultRoot) throws IOException {
		populateAllLists(resultRoot);
		createContents(s, outputFileName, resultRoot);
		clearLists();
	}

	/**
	 * Returns the total number of evaluated constraints.
	 * 
	 * @return the total number of evaluated constraints.
	 */
	protected int getConstraintsCount() {
		return constraintsErrors.size() + constraintsInfos.size() + constraintsSuccess.size()
				+ constraintsWarnings.size();
	}

	/**
	 * Returns the total number of evaluated operations.
	 * 
	 * @return the total number of evaluated operations.
	 */
	protected int getOperationsCount() {
		return operationsErrors.size() + operationsInfos.size() + operationsSuccess.size()
				+ operationsWarnings.size();
	}

	/**
	 * Returns the message of the given ocl result.
	 * 
	 * @param result
	 *            the given ocl result.
	 * @return the message of the given ocl result.
	 */
	protected String getMessage(OCLResult result) {
		if (result == null) {
			return null;
		}
		StringWriter message = new StringWriter();
		if (result instanceof ConstraintResult) {
			message.append(((ConstraintResult)result).getMessage());
		}

		return message.toString();
	}

	/**
	 * Returns the severity of a result.
	 * 
	 * @param result
	 *            the result.
	 * @return the severity of the result.
	 */
	protected String getSeverity(Result result) {
		if (result != null && result instanceof ConstraintResult) {
			return ((ConstraintResult)result).getSeverity().toString();
		}
		return null;
	}

	/**
	 * Populates all lists with operations and constraints results.
	 * 
	 * @param oclElement
	 *            the ocl root element of the evaluation model.
	 */
	private void populateAllLists(OCLElement oclElement) {
		for (OCLElement child : oclElement.getChildren()) {
			if (child instanceof ConstraintElement) {
				populateConstraintsLists((ConstraintElement)child);
			} else if (child instanceof OperationElement) {
				populateOperationsLists((OperationElement)child);
			} else {
				for (OCLElement element : child.getChildren()) {
					populateAllLists(element);
				}
			}
		}
	}

	/**
	 * Populates lists with only constraints evaluation results.
	 * 
	 * @param constraintElement
	 *            the constraint Element.
	 */
	private void populateConstraintsLists(ConstraintElement constraintElement) {
		Severity severity = constraintElement.getWorstSeverity();
		if (severity != null) {
			switch (severity.getValue()) {
				case Severity.OK_VALUE:
					constraintsSuccess.add(constraintElement);
					break;
				case Severity.ERROR_VALUE:
					constraintsErrors.add(constraintElement);
					break;
				case Severity.WARNING_VALUE:
					constraintsWarnings.add(constraintElement);
					break;
				case Severity.INFO_VALUE:
					constraintsInfos.add(constraintElement);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Populates lists with only operations evaluation results.
	 * 
	 * @param operationElement
	 *            the operation Element.
	 */
	private void populateOperationsLists(OperationElement operationElement) {
		Severity severity = operationElement.getWorstSeverity();
		if (severity != null) {
			switch (severity.getValue()) {
				case Severity.OK_VALUE:
					operationsSuccess.add(operationElement);
					break;
				case Severity.ERROR_VALUE:
					operationsErrors.add(operationElement);
					break;
				case Severity.WARNING_VALUE:
					operationsWarnings.add(operationElement);
					break;
				case Severity.INFO_VALUE:
					operationsInfos.add(operationElement);
					break;
				default:
					break;
			}
		}
	}
}
