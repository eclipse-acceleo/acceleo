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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
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
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ocl.ecore.CollectionItem;
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
	 * Tries and find a file with the given name under the given container (recursively).
	 * 
	 * @param container
	 *            The container under which to seek for a file.
	 * @param fileName
	 *            Name of the file we seek.
	 * @return The IFile of name <code>fileName</code> under the given <code>container</code>.
	 *         <code>null</code> if none could be found.
	 */
	private static IFile findChild(IContainer container, String fileName) {
		IFile result = null;
		try {
			final IResource[] members = container.members();
			for (int i = 0; i < members.length && result == null; i++) {
				final IResource child = members[i];
				if (child instanceof IContainer) {
					result = findChild((IContainer)child, fileName);
				} else if (child instanceof IFile && child.getName().equals(fileName)) {
					result = (IFile)child;
				}
			}
		} catch (CoreException e) {
			// FIXME log
		}
		return result;
	}

	/**
	 * Tries and find an Acceleo compiled module (emtl file) corresponding to the given module (mtl file),
	 * then loads it as an EMF resource.
	 * 
	 * @param moduleFile
	 *            The module file of which we seek the compiled version.
	 * @param resourceSet
	 *            The resource set in which to load the module.
	 * @return The Acceleo compiled module (emtl file) corresponding to the given module (mtl file).
	 *         <code>null</code> if none.
	 */
	private static Resource getModule(IFile moduleFile, ResourceSet resourceSet) {
		IFile compiledModule = null;
		if (IAcceleoConstants.MTL_FILE_EXTENSION.equals(moduleFile.getFileExtension())) {
			final IProject project = moduleFile.getProject();
			if (project != null) {
				final String compiledName = moduleFile.getFullPath().removeFileExtension().addFileExtension(
						IAcceleoConstants.EMTL_FILE_EXTENSION).lastSegment();
				compiledModule = findChild(project, compiledName);
			}
		}

		Resource module = null;
		if (compiledModule != null) {
			String path = compiledModule.getFullPath().toString();
			for (Resource resource : resourceSet.getResources()) {
				if (resource.getURI().toString().equals(path)) {
					return resource;
				}
			}
			try {
				module = ModelUtils.load(URI.createPlatformResourceURI(path, true), resourceSet).eResource();
			} catch (IOException e) {
				// FIXME log
			}
		}
		return module;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public CompilationResult call() throws Exception {
		checkCancelled();

		String fullExpression = acceleoSource.rebuildFullExpression(context);

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI
				.createURI("http://acceleo.eclipse.org/default.emtl"), resourceSet); //$NON-NLS-1$

		AcceleoSourceBuffer source = new AcceleoSourceBuffer(new StringBuffer(fullExpression));

		List<URI> dependencies = new ArrayList<URI>();
		if (acceleoSource.getModuleImport() != null) {
			dependencies.addAll(computeImportList(acceleoSource.getModuleImport(), resourceSet));
		}

		AcceleoParser parser = new AcceleoParser();
		parser.parse(source, resource, dependencies);

		checkCancelled();

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
			}

			if (selectedNode == null && module != null && !module.getOwnedModuleElement().isEmpty()) {
				selectedNode = module.getOwnedModuleElement().get(0);
			}
		}

		checkCancelled();

		IStatus problems = parseProblems(source.getProblems(), source.getWarnings(), source.getInfos());
		return new CompilationResult(selectedNode, problems);
	}

	/**
	 * Computes the import list given the <b>initial</b> imported file. This will recursively walk the import
	 * tree of that initial file.
	 * 
	 * @param initialImport
	 *            The initial imported file from which to compute the imort list.
	 * @param resourceSet
	 *            The resource set in which the list will be loaded.
	 * @return The whole import list.
	 */
	public Set<URI> computeImportList(IFile initialImport, ResourceSet resourceSet) {
		Resource moduleImport = getModule(initialImport, resourceSet);
		if (moduleImport != null) {
			EcoreUtil.resolveAll(resourceSet);
		}

		final Set<URI> dependencies = new LinkedHashSet<URI>();
		for (Resource res : resourceSet.getResources()) {
			dependencies.add(res.getURI());
		}

		return dependencies;
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
			ASTNode astNode = null;
			if (eContent instanceof ASTNode) {
				astNode = (ASTNode)eContent;
			} else if (eContent instanceof CollectionItem) {
				astNode = ((CollectionItem)eContent).getItem();
			}
			if (astNode != null) {
				int startPosition = astNode.getStartPosition();
				int endPosition = astNode.getEndPosition();
				if (startPosition > -1 && endPosition > -1 && startPosition <= posBegin
						&& endPosition >= posEnd) {
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
			problems.add(new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, error.getMessage()));
		}
		for (AcceleoParserWarning warning : warnings.getList()) {
			problems.add(new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, warning.getMessage()));
		}
		for (AcceleoParserInfo info : infos.getList()) {
			problems.add(new Status(IStatus.INFO, AcceleoUIActivator.PLUGIN_ID, info.getMessage()));
		}

		if (problems.isEmpty()) {
			return null;
		}

		MultiStatus status = new MultiStatus(AcceleoUIActivator.PLUGIN_ID, 1, AcceleoUIMessages
				.getString("acceleo.interpreter.compilation.issue"), null); //$NON-NLS-1$
		for (IStatus child : problems) {
			status.add(child);
		}
		return status;
	}
}
