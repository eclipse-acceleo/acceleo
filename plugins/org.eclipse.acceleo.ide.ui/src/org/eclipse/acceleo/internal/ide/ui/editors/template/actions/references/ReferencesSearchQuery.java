/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
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
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoUIResourceSet;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.NewSearchUI;
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
	 * Indicates if we have to search outside of the current file.
	 */
	private boolean searchOutsideOfCurrentFile;

	/**
	 * Indicates if we should show the result in the Acceleo editor with a search marker.
	 */
	private boolean showInEditor;

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
		this.searchResult = new ReferencesSearchResult(this);
		this.searchOutsideOfCurrentFile = true;
		this.showInEditor = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            the Acceleo editor
	 * @param declaration
	 *            the declaration for which we seek references
	 * @param searchOutsideCurrentFile
	 *            Indicates if we have to search outside of the current file.
	 */
	public ReferencesSearchQuery(AcceleoEditor editor, EObject declaration, boolean searchOutsideCurrentFile) {
		this.declaration = declaration;
		this.editor = editor;
		this.searchResult = new ReferencesSearchResult(this);
		this.searchOutsideOfCurrentFile = searchOutsideCurrentFile;
		this.showInEditor = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            the Acceleo editor
	 * @param declaration
	 *            the declaration for which we seek references
	 * @param searchOutsideCurrentFile
	 *            Indicates if we have to search outside of the current file.
	 * @param showResultInEditor
	 *            Indicates if we should show the result in the Acceleo editor with a search marker.
	 */
	public ReferencesSearchQuery(AcceleoEditor editor, EObject declaration, boolean searchOutsideCurrentFile,
			boolean showResultInEditor) {
		this.declaration = declaration;
		this.editor = editor;
		this.searchResult = new ReferencesSearchResult(this);
		this.searchOutsideOfCurrentFile = searchOutsideCurrentFile;
		this.showInEditor = showResultInEditor;
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
		return searchResult;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchQuery#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		if (declaration != null) {
			findReferencesForFile(monitor);
		}
		return Status.OK_STATUS;
	}

	/**
	 * Compute the references in the workspace of the current declaration.
	 * 
	 * @param monitor
	 *            Progress monitor on which we'll display progress information to the user.
	 */
	private void findReferencesForFile(IProgressMonitor monitor) {
		List<URI> allURIs = new ArrayList<URI>();
		IProject project = null;
		if (editor.getContent().getFile() != null && editor.getFile() != null) {
			final AcceleoProject acceleoProject = new AcceleoProject(editor.getContent().getFile()
					.getProject());
			final IPath outputFilePath = acceleoProject.getOutputFilePath(editor.getContent().getFile());
			if (outputFilePath == null) {
				return;
			}
			final String path = outputFilePath.toString();
			URI newResourceURI = URI.createPlatformResourceURI(path, false);
			allURIs.add(newResourceURI);
			if (this.searchOutsideOfCurrentFile) {
				project = editor.getFile().getProject();
				for (URI uri : new AcceleoProject(project).getOutputFiles()) {
					if (!allURIs.contains(uri)) {
						allURIs.add(uri);
					}
				}
			}
		}
		if (this.searchOutsideOfCurrentFile) {
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (int i = 0; i < projects.length; i++) {
				try {
					if (!monitor.isCanceled() && projects[i] != project && projects[i].isAccessible()
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
		}

		for (URI uri : allURIs) {
			try {
				if (!monitor.isCanceled() && this.resourceAtURIExist(uri)) {
					AcceleoUIResourceSet.getResource(uri);
				}
			} catch (IOException e) {
				// do nothing
			} catch (WrappedException e) {
				// do nothing
			}
		}
		if (!monitor.isCanceled()) {
			List<Resource> resources = AcceleoUIResourceSet.getResources();
			for (Resource resource : resources) {
				if (resource.getContents().size() > 0 && resource.getContents().get(0) instanceof Module) {
					if (resource.getURI() != null && resource.getURI().isPlatform()) {
						scanModuleForDeclaration((Module)resource.getContents().get(0));
					}
				}
			}
		}
	}

	/**
	 * Test if the resource at the given URI exist.
	 * 
	 * @param uri
	 *            The URI.
	 * @return true if the resource exist.
	 */
	private boolean resourceAtURIExist(final URI uri) {
		boolean result = true;

		try {
			result = AcceleoWorkspaceUtil.getWorkspaceFile(uri.toString()).exists();
		} catch (IOException e) {
			// do nothing
		}

		return result;
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
			if (mtlPath != null) {
				IFile mtlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(mtlPath);
				if (mtlFile.exists()) {
					StringBuffer acceleoText = FileContent.getFileContent(mtlFile.getLocation().toFile());
					scanModuleForDeclaration(mtlFile, acceleoText, module);
				}
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

		/*
		 * If the astNode is formed like this : myVariable.myAttribute.mySuperTemplate(). It is interpreted by
		 * OCL as an implicit collect : myVariable.myAttribute->collect(a|a.mySuperTemplate()) but there is a
		 * problem with the parsing of the implicit collect. Therefore in order to have a valid result we will
		 * here correct the error with an additional small parsing. The error is that OCL sees an implicit
		 * collect and creates its body but the body doesn't have any start or end position and because of
		 * that we cannot precisely determine where to start/end the highlighting of all the occurrences of a
		 * template or where to start/end the renaming of all occurrences of a template during the
		 * refactoring.
		 */

		if (astNode instanceof IteratorExp && (((IteratorExp)astNode).getBody().getStartPosition() == -1)
				&& (((IteratorExp)astNode).getBody().getEndPosition() == -1)) {
			((IteratorExp)astNode).getBody().setStartPosition(((IteratorExp)astNode).getStartPosition());
			((IteratorExp)astNode).getBody().setEndPosition(((IteratorExp)astNode).getEndPosition());
		}

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
			IRegion region = createRegion(astNode);
			String message;
			if (region.getOffset() + region.getLength() <= acceleoText.length()) {
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

			if (this.showInEditor) {
				// create a marker in the acceleo editor to show the result.
				try {
					IMarker marker = mtlFile.createMarker(NewSearchUI.SEARCH_MARKER);
					marker.setAttribute(IMarker.CHAR_START, region.getOffset());
					marker.setAttribute(IMarker.CHAR_END, region.getOffset() + region.getLength());
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
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
		boolean result = false;
		if (o1.eClass().getName().equals(o2.eClass().getName())) {
			if (o1 instanceof Template && o2 instanceof Template) {
				final Template t1 = (Template)o1;
				final Template t2 = (Template)o2;
				result = this.isMatchingTemplate(t1, t2);
			} else if (o1 instanceof Query && o2 instanceof Query) {
				final Query q1 = (Query)o1;
				final Query q2 = (Query)o2;
				result = this.isMatchingQuery(q1, q2);
			} else if (o1 instanceof ModuleElement && o2 instanceof ModuleElement
					&& ((ModuleElement)o1).getName() != null) {
				result = ((ModuleElement)o1).getName().equals(((ModuleElement)o2).getName());
			} else if (o1 instanceof org.eclipse.ocl.ecore.Variable
					&& o2 instanceof org.eclipse.ocl.ecore.Variable) {
				final Variable v1 = (Variable)o1;
				final Variable v2 = (Variable)o2;
				result = this.isMatchingVariable(v1, v2);
			} else {
				Module m1 = null;
				Module m2 = null;
				boolean validSearchInFile = o1 instanceof ASTNode && o2 instanceof ASTNode
						&& !searchOutsideOfCurrentFile;

				EObject parent1 = o1.eContainer();
				EObject parent2 = o2.eContainer();
				while (parent1 != null) {
					if (parent1 instanceof Module) {
						m1 = (Module)parent1;
					}
					parent1 = parent1.eContainer();
				}

				while (parent2 != null) {
					if (parent2 instanceof Module) {
						m2 = (Module)parent2;
					}
					parent2 = parent2.eContainer();
				}

				if (m1 != null && m2 != null && m1.getName().equals(m2.getName()) && validSearchInFile) {
					ASTNode astNode1 = (ASTNode)o1;
					ASTNode astNode2 = (ASTNode)o2;
					result = astNode1.getStartPosition() == astNode2.getStartPosition()
							&& astNode1.getEndPosition() == astNode2.getEndPosition();
				} else {
					result = EcoreUtil.equals(o1, o2);
				}
			}
		} else if (o1 instanceof VariableExp && o2 instanceof Variable) {
			VariableExp vx = (VariableExp)o1;
			org.eclipse.ocl.expressions.Variable<EClassifier, EParameter> referredVariable = vx
					.getReferredVariable();
			if (referredVariable instanceof Variable) {
				Variable vTemp = (Variable)referredVariable;
				Variable v2 = (Variable)o2;
				result = isMatchingVariable(vTemp, v2);
			}
		} else if (o1 instanceof Variable && o2 instanceof VariableExp) {
			VariableExp vx = (VariableExp)o2;
			org.eclipse.ocl.expressions.Variable<EClassifier, EParameter> referredVariable = vx
					.getReferredVariable();
			if (referredVariable instanceof Variable) {
				Variable vTemp = (Variable)referredVariable;
				Variable v1 = (Variable)o1;
				result = isMatchingVariable(v1, vTemp);
			}
		} else {
			result = EcoreUtil.equals(o1, o2);
		}
		return result;
	}

	/**
	 * Indicates if the given AST objects are matching.
	 * 
	 * @param v1
	 *            is the first variable
	 * @param v2
	 *            is the second variable
	 * @return true if the element names are the same and if they are in the same module element
	 */
	private boolean isMatchingVariable(final Variable v1, final Variable v2) {
		boolean result = false;

		if (v1.getName() != null) {
			result = v1.getName().equals(v2.getName());
		}

		// If the two variables have the same name, check their container
		if (result) {
			ModuleElement container1 = getContainingModuleElement(v1);
			ModuleElement container2 = getContainingModuleElement(v2);
			if (container1 != null && container2 != null) {
				result = result && container1.getStartPosition() == container2.getStartPosition()
						&& container1.getEndPosition() == container2.getEndPosition();
			}
		}

		return result;
	}

	/**
	 * Returns the containing module element of the given variable.
	 * 
	 * @param v
	 *            The variable
	 * @return The containing module element of the given variable
	 */
	private ModuleElement getContainingModuleElement(Variable v) {
		ModuleElement moduleElement = null;

		EObject eContainer = v.eContainer();
		while (!(eContainer instanceof ModuleElement)) {
			if (eContainer == null) {
				break;
			}
			eContainer = eContainer.eContainer();
		}

		if (eContainer instanceof ModuleElement) {
			moduleElement = (ModuleElement)eContainer;
		}

		return moduleElement;
	}

	/**
	 * Indicates if the given AST objects are matching.
	 * 
	 * @param template1
	 *            is the first template
	 * @param template2
	 *            is the second template
	 * @return true if the element names are the same and if they have the same parameters.
	 */
	private boolean isMatchingTemplate(final Template template1, final Template template2) {
		boolean result = false;

		if (template1.getName() != null) {
			result = template1.getName().equals(template2.getName());
		}

		final EList<Variable> t1Parameters = template1.getParameter();
		final EList<Variable> t2Parameters = template2.getParameter();

		if (t1Parameters.size() == t2Parameters.size()) {
			for (int i = 0; i < t1Parameters.size(); i++) {
				Variable var1 = t1Parameters.get(i);
				Variable var2 = t2Parameters.get(i);

				if (var1.getName() != null && var2.getName() != null
						&& !var1.getName().equals(var2.getName())) {
					result = false;
					break;
				}
				if ((var1.getType() != null && var2.getType() != null)
						&& (var1.getType().getName() != null && !var1.getType().getName().equals(
								var2.getType().getName()))) {
					result = false;
					break;
				}
			}

			URI uri1 = EcoreUtil.getURI(template1);
			URI uri2 = EcoreUtil.getURI(template2);
			if (result && uri1 != null && uri2 != null) {
				result = uri1.equals(uri2);
				result = result && (template1.getStartPosition() == template2.getStartPosition())
						&& (template1.getEndPosition() == template2.getEndPosition());
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Indicates if the given AST objects are matching.
	 * 
	 * @param query1
	 *            is the first query
	 * @param query2
	 *            is the second query
	 * @return true if the element names are the same and if they have the same parameters.
	 */
	private boolean isMatchingQuery(final Query query1, final Query query2) {
		boolean result = false;

		if (query1.getName() != null) {
			result = query1.getName().equals(query2.getName());
		}

		final EList<Variable> q1Parameters = query1.getParameter();
		final EList<Variable> q2Parameters = query2.getParameter();

		if (q1Parameters.size() == q2Parameters.size()) {
			for (int i = 0; i < q1Parameters.size(); i++) {
				Variable var1 = q1Parameters.get(i);
				Variable var2 = q2Parameters.get(i);

				if (var1.getName() != null && var2.getName() != null
						&& !var1.getName().equals(var2.getName())) {
					result = false;
					break;
				}
				if ((var1.getType() != null && var2.getType() != null)
						&& (var1.getType().getName() != null && !var1.getType().getName().equals(
								var2.getType().getName()))) {
					result = false;
					break;
				}
			}

			URI uri1 = EcoreUtil.getURI(query1);
			URI uri2 = EcoreUtil.getURI(query2);
			if (result && uri1 != null && uri2 != null) {
				result = uri1.equals(uri2);
				result = result && (query1.getStartPosition() == query2.getStartPosition())
						&& (query1.getEndPosition() == query2.getEndPosition());
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Creates a region for the given module element.
	 * 
	 * @param astNode
	 *            is the module element
	 * @return a region in the text, or Region(0,0) if it isn't available
	 */
	private IRegion createRegion(EObject astNode) {
		IRegion result = null;
		if (astNode instanceof ASTNode) {
			int b = ((ASTNode)astNode).getStartPosition();
			if (b > -1) {
				int e = ((ASTNode)astNode).getEndPosition();
				if (e >= b) {
					result = new Region(b, e - b);
				}
			}
		}
		if (result != null) {
			return result;
		}
		return new Region(0, 0);
	}

	/**
	 * Returns the declaration.
	 * 
	 * @return The declaration.
	 */
	public EObject getDeclaration() {
		return this.declaration;
	}
}
