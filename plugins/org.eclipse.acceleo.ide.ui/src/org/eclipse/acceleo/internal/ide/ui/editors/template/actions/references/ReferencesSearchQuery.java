/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.text.Match;

/**
 * This class is the query looking for References in Acceleo templates.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferencesSearchQuery implements ISearchQuery {
	/**
	 * The result where to store matches.
	 */
	private ReferencesSearchResult searchResult;

	/**
	 * The declaration for which we seek references.
	 */
	private EObject declaration;

	/**
	 * The Acceleo Editor.
	 */
	private AcceleoEditor editor;

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            the Acceleo editor
	 * @param declaration
	 *            the declaration for which we seek references
	 */
	public ReferencesSearchQuery(AcceleoEditor editor, EObject declaration) {
		this.declaration = declaration;
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#canRerun()
	 */
	public boolean canRerun() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#canRunInBackground()
	 */
	public boolean canRunInBackground() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#getLabel()
	 */
	public String getLabel() {
		return AcceleoUIMessages.getString("AcceleoReferencesSearch.Query.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#getSearchResult()
	 */
	public ISearchResult getSearchResult() {
		if (searchResult == null) {
			searchResult = new ReferencesSearchResult(this);
		}
		return searchResult;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		if (declaration != null) {
			findReferencesForFile();
		}
		return Status.OK_STATUS;
	}

	/**
	 * Compute the references in the workspace of the current declaration.
	 */
	private void findReferencesForFile() {
		List<URI> allURIs = new ArrayList<URI>();
		IProject project;
		if (editor.getContent().getFile() != null && editor.getFile() != null) {
			URI newResourceURI = URI.createPlatformResourceURI(new AcceleoProject(editor.getContent()
					.getFile().getProject()).getOutputFilePath(editor.getContent().getFile()).toString(),
					false);
			allURIs.add(newResourceURI);
			project = editor.getFile().getProject();
			for (URI uri : new AcceleoProject(project).getOutputFiles()) {
				if (!allURIs.contains(uri)) {
					allURIs.add(uri);
				}
			}
		} else {
			project = null;
		}
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			try {
				if (projects[i] != project && projects[i].isAccessible()
						&& projects[i].hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
					AcceleoProject acceleoProject = new AcceleoProject(projects[i]);
					for (URI uri : acceleoProject.getOutputFiles()) {
						if (!allURIs.contains(uri)) {
							allURIs.add(uri);
						}
					}
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		ResourceSet newResourceSet = new ResourceSetImpl();
		for (URI uri : allURIs) {
			try {
				ModelUtils.load(uri, newResourceSet);
			} catch (IOException e) {
				// do nothing
			}
		}
		EcoreUtil.resolveAll(newResourceSet);
		for (Resource resource : newResourceSet.getResources()) {
			if (resource.getContents().size() > 0 && resource.getContents().get(0) instanceof Module) {
				scanModuleForDeclaration((Module)resource.getContents().get(0));
			}
		}
		for (Resource resource : newResourceSet.getResources()) {
			resource.unload();
		}
	}

	/**
	 * Seek references to the declaration in the given module.
	 * 
	 * @param module
	 *            the module
	 */
	private void scanModuleForDeclaration(Module module) {
		IPath emtlPath = new Path(module.eResource().getURI().toPlatformString(true));
		if (emtlPath.segmentCount() > 1 && ResourcesPlugin.getWorkspace().getRoot().exists(emtlPath)) {
			IFile emtlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(emtlPath);
			IPath mtlPath = new AcceleoProject(emtlFile.getProject()).getInputFilePath(emtlPath);
			IFile mtlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(mtlPath);
			if (mtlFile.exists()) {
				StringBuffer acceleoText = FileContent.getFileContent(mtlFile.getLocation().toFile());
				scanModuleForDeclaration(mtlFile, acceleoText, module);
			}
		}
	}

	/**
	 * Seek references to the declaration in the given Acceleo file, starting by the root element of the AST
	 * model.
	 * 
	 * @param mtlFile
	 *            is the file
	 * @param acceleoText
	 *            is the current modified text of the Acceleo file
	 * @param module
	 *            is the root element of the AST model
	 */
	private void scanModuleForDeclaration(IFile mtlFile, StringBuffer acceleoText, Module module) {
		addASTNode(mtlFile, acceleoText, module);
		Iterator<EObject> it = module.eAllContents();
		while (it.hasNext()) {
			addASTNode(mtlFile, acceleoText, it.next());
		}
	}

	/**
	 * Try to seek references to the declaration in the given Acceleo file, at the given AST node.
	 * 
	 * @param mtlFile
	 *            is the file
	 * @param acceleoText
	 *            is the current modified text of the Acceleo file
	 * @param astNode
	 *            is the current AST node
	 */
	private void addASTNode(IFile mtlFile, StringBuffer acceleoText, EObject astNode) {
		boolean isRef = isMatching(astNode, declaration);
		if (!isRef) {
			for (EObject eObj : astNode.eCrossReferences()) {
				if (isMatching(eObj, declaration)) {
					isRef = true;
					break;
				}
			}
		}
		if (isRef) {
			IRegion region;
			if (astNode instanceof ASTNode) {
				region = createRegion((ASTNode)astNode);
			} else {
				region = new Region(0, 0);
			}
			String message;
			if (region != null && region.getOffset() > -1
					&& region.getOffset() + region.getLength() <= acceleoText.length()) {
				message = acceleoText.substring(region.getOffset(), region.getOffset() + region.getLength());
				if (message.startsWith(IAcceleoConstants.DEFAULT_BEGIN)
						&& message.indexOf(IAcceleoConstants.DEFAULT_END) > -1) {
					message = message.substring(0, message.indexOf(IAcceleoConstants.DEFAULT_END) + 1);
				}
			} else {
				message = ""; //$NON-NLS-1$
			}
			searchResult.addMatch(new Match(new ReferenceEntry(mtlFile, astNode, editor, message), region
					.getOffset(), region.getLength()));
		}
	}

	/**
	 * Indicates if the given AST objects are matching.
	 * 
	 * @param o1
	 *            is the first object
	 * @param o2
	 *            is the second object
	 * @return true if the element names are the same
	 */
	private boolean isMatching(EObject o1, EObject o2) {
		boolean result;
		if (o1.eClass().getName().equals(o2.eClass().getName())) {
			if (o1 instanceof ModuleElement && o2 instanceof ModuleElement) {
				result = ((ModuleElement)o1).getName().equals(((ModuleElement)o2).getName());
			} else if (o1 instanceof org.eclipse.ocl.ecore.Variable
					&& o2 instanceof org.eclipse.ocl.ecore.Variable) {
				result = ((org.eclipse.ocl.ecore.Variable)o1).getName().equals(
						((org.eclipse.ocl.ecore.Variable)o2).getName());
			} else {
				result = EcoreUtil.equals(o1, o2);
			}
		} else {
			result = EcoreUtil.equals(o1, o2);
		}
		return result;
	}

	/**
	 * Creates a region for the given module element.
	 * 
	 * @param astNode
	 *            is the module element
	 * @return a region in the text
	 */
	private IRegion createRegion(ASTNode astNode) {
		int b = astNode.getStartPosition();
		if (b > -1) {
			int e = astNode.getEndPosition();
			return new Region(b, e - b);
		} else {
			return null;
		}
	}
}
