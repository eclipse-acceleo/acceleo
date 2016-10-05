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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.documentation;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AcceleoRefactoringUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ocl.ecore.Variable;

/**
 * This action will launch the refactoring process which will create a comment block for the current element
 * if the element is (or is inside) a template or query, or for the containing module otherwise.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class GenerateDocumentationAction extends AbstractRefactoringAction {

	/**
	 * The space character.
	 */
	private static final String SPACE = " "; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	public GenerateDocumentationAction() {
		super(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringAction#launchRefactoring()
	 */
	@Override
	protected void launchRefactoring() {
		if (this.fWindow != null && AcceleoRefactoringUtils.allResourceSaved() && !this.editor.isDirty()) {
			EObject container = OpenDeclarationUtils.findResolvedDeclaration(this.editor);

			while (!(container instanceof Template) && !(container instanceof Query)
					&& !(container instanceof Module) && container != null) {
				container = container.eContainer();
			}

			if (container instanceof Template) {
				Template template = (Template)container;
				this.generateTemplateDocumentation(template);
			} else if (container instanceof Query) {
				Query query = (Query)container;
				this.generateQueryDocumentation(query);
			} else if (container instanceof Module) {
				Module module = (Module)container;
				this.generateModuleDocumentation(module);
			}
			if (container != null) {
				this.editor.doSave(new NullProgressMonitor());
			}
		}
	}

	/**
	 * Returns the line delimiter for this document, <code>System.getProperty("line.separator")</code> if none
	 * can be found.
	 * 
	 * @param document
	 *            The document.
	 * @return the line delimiter for this document.
	 */
	private String getLineDelimiter(IDocument document) {
		String lineDelimiter = System.getProperty("line.separator"); //$NON-NLS-1$
		if (document != null && document.getNumberOfLines() > 0) {
			try {
				lineDelimiter = document.getLineDelimiter(0);
			} catch (BadLocationException e) {
				// Won't happen since we guarded against it
			}
		}
		return lineDelimiter;
	}

	/**
	 * Generate the documentation of the module.
	 * 
	 * @param module
	 *            The module
	 */
	private void generateModuleDocumentation(Module module) {
		String username = System.getProperty("user.name"); //$NON-NLS-1$

		IDocument document = this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput());
		String lineDelimiter = getLineDelimiter(document);
		int startPosition = module.getStartHeaderPosition() - lineDelimiter.length();

		StringBuffer newBuffer = new StringBuffer();
		newBuffer.append(lineDelimiter + IAcceleoConstants.DEFAULT_BEGIN
				+ IAcceleoConstants.DOCUMENTATION_BEGIN + lineDelimiter);
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE + SPACE + lineDelimiter);
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE + SPACE
				+ IAcceleoConstants.TAG_AUTHOR + SPACE + username + lineDelimiter);
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE
				+ IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.DEFAULT_END);

		try {
			document.replace(startPosition, 0, newBuffer.toString());
		} catch (BadLocationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Generate the documentation of the query.
	 * 
	 * @param query
	 *            the query
	 */
	private void generateQueryDocumentation(final Query query) {
		IDocument document = this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput());
		String lineDelimiter = getLineDelimiter(document);
		int startPosition = query.getStartPosition() - lineDelimiter.length();

		StringBuffer newBuffer = new StringBuffer();
		newBuffer.append(lineDelimiter + IAcceleoConstants.DEFAULT_BEGIN
				+ IAcceleoConstants.DOCUMENTATION_BEGIN + lineDelimiter);
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE + SPACE + lineDelimiter);
		newBuffer.append(this.generateParameterDocumentation(query.getParameter(), lineDelimiter));
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE
				+ IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.DEFAULT_END);

		try {
			document.replace(startPosition, 0, newBuffer.toString());
		} catch (BadLocationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Generate the part of the documentation for the parameters.
	 * 
	 * @param eList
	 *            The variable list.
	 * @param lineDelimiter
	 *            the separator to use between lines.
	 * @return The part of the documentation for the parameters
	 */
	private StringBuffer generateParameterDocumentation(EList<Variable> eList, String lineDelimiter) {
		StringBuffer result = new StringBuffer();

		for (Variable variable : eList) {
			result.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE + SPACE
					+ IAcceleoConstants.TAG_PARAM + SPACE + variable.getName() + SPACE + lineDelimiter);
		}

		return result;
	}

	/**
	 * Generate the documentation of the template.
	 * 
	 * @param template
	 *            The template
	 */
	private void generateTemplateDocumentation(final Template template) {
		IDocument document = this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput());
		String lineDelimiter = getLineDelimiter(document);
		int startPosition = template.getStartPosition() - lineDelimiter.length();

		StringBuffer newBuffer = new StringBuffer();
		newBuffer.append(lineDelimiter + IAcceleoConstants.DEFAULT_BEGIN
				+ IAcceleoConstants.DOCUMENTATION_BEGIN + lineDelimiter);
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE + SPACE + lineDelimiter);
		newBuffer.append(this.generateParameterDocumentation(template.getParameter(), lineDelimiter));
		newBuffer.append(SPACE + IAcceleoConstants.DOCUMENTATION_NEW_LINE
				+ IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.DEFAULT_END);

		try {
			document.replace(startPosition, 0, newBuffer.toString());
		} catch (BadLocationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
	}
}
