/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.language.acceleo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoParserInfos;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.acceleo.parser.AcceleoParserWarnings;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.ui.interpreter.AcceleoInterpreterPlugin;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * This will be used by the interpreter in order to compile Acceleo expressions from the interpreter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoCompilationTask implements Callable<CompilationResult> {
	/** Acceleo's compilation task is deeply tied to its Viewer. */
	private AcceleoSourceViewer acceleoSource;

	/** Current interpreter context. */
	private final InterpreterContext context;

	/**
	 * Instantiates our compilation task given the current interpreter context.
	 * 
	 * @param acceleoSource
	 *            The Source viewer on which our expression id displayed.
	 * @param context
	 *            The current interpreter context.
	 */
	public AcceleoCompilationTask(AcceleoSourceViewer acceleoSource, InterpreterContext context) {
		this.acceleoSource = acceleoSource;
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public CompilationResult call() throws Exception {
		String fullExpression = acceleoSource.rebuildFullExpression(context);

		Resource resource = ModelUtils.createResource(
				URI.createURI("http://acceleo.eclipse.org/default.emtl"), new ResourceSetImpl()); //$NON-NLS-1$

		AcceleoSourceBuffer source = new AcceleoSourceBuffer(new StringBuffer(fullExpression));

		AcceleoParser parser = new AcceleoParser();
		parser.parse(source, resource, Collections.<URI> emptyList());

		ASTNode selectedNode = null;
		if (!resource.getContents().isEmpty()) {
			Module module = (Module)resource.getContents().get(0);

			ISelection selection = context.getSelection();
			if (selection instanceof ITextSelection && ((ITextSelection)selection).getLength() > 0) {
				ITextSelection textSelection = (ITextSelection)selection;
				int startOffset = textSelection.getOffset() + acceleoSource.getGap();
				int endOffset = startOffset + textSelection.getLength();

				if (textSelection.getText().startsWith("[")) { //$NON-NLS-1$
					startOffset++;
				}
				if (textSelection.getText().endsWith("/]")) { //$NON-NLS-1$
					endOffset -= 2;
				}

				selectedNode = getChildrenCandidate(module, startOffset, endOffset);

				if (textSelection.getLength() == 0) {
					while (selectedNode != null && !(selectedNode instanceof ModuleElement)) {
						selectedNode = (ASTNode)selectedNode.eContainer();
					}
				}
			} else {
				if (!module.getOwnedModuleElement().isEmpty()) {
					selectedNode = module.getOwnedModuleElement().get(0);
				}
			}
		}

		IStatus problems = parseProblems(source.getProblems(), source.getWarnings(), source.getInfos());
		return new CompilationResult(selectedNode, problems);
	}

	/**
	 * Gets the nearest AST child at the given position. It browses the children of the given candidate and
	 * returns the nearest children if it exists.
	 * 
	 * @param candidate
	 *            is the current candidate to browse
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest AST node in the children of the current candidate
	 */
	private ASTNode getChildrenCandidate(EObject candidate, int posBegin, int posEnd) {
		ASTNode child = null;

		Iterator<EObject> itContents = candidate.eContents().iterator();
		while (itContents.hasNext()) {
			EObject eContent = itContents.next();
			if (eContent instanceof ASTNode) {
				ASTNode astNode = (ASTNode)eContent;
				int startPosition = astNode.getStartPosition();
				int endPosition = astNode.getEndPosition();
				if (startPosition > -1 && endPosition > -1) {
					if (startPosition <= posBegin && endPosition >= posEnd) {
						ASTNode childCandidate = getChildrenCandidate(astNode, posBegin, posEnd);
						if (childCandidate != null) {
							child = childCandidate;
						} else {
							child = astNode;
						}
						break;
					}
				}
			}
		}

		return child;
	}

	/**
	 * Compiles the given list of Acceleo parser messages into a single MultiStatus.
	 * 
	 * @param errors
	 *            List of the errors that arose during the compilation.
	 * @param warnings
	 *            List of the warnings that arose during the compilation.
	 * @param infos
	 *            List of the infos that arose during the compilation.
	 * @return A single MultiStatus referenging all issues.
	 */
	private IStatus parseProblems(AcceleoParserProblems errors, AcceleoParserWarnings warnings,
			AcceleoParserInfos infos) {
		List<IStatus> problems = new ArrayList<IStatus>();

		for (AcceleoParserProblem error : errors.getList()) {
			problems.add(new Status(IStatus.ERROR, AcceleoInterpreterPlugin.PLUGIN_ID, error.getMessage()));
		}
		for (AcceleoParserWarning warning : warnings.getList()) {
			problems.add(new Status(IStatus.WARNING, AcceleoInterpreterPlugin.PLUGIN_ID, warning.getMessage()));
		}
		for (AcceleoParserInfo info : infos.getList()) {
			problems.add(new Status(IStatus.INFO, AcceleoInterpreterPlugin.PLUGIN_ID, info.getMessage()));
		}

		if (problems.isEmpty()) {
			return null;
		}

		MultiStatus status = new MultiStatus(AcceleoInterpreterPlugin.PLUGIN_ID, 1,
				"Problems encountered while compiling expression", null);
		for (IStatus child : problems) {
			status.add(child);
		}
		return status;
	}
}
