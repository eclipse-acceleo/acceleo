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
package org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix;

import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ocl.ecore.CollectionLiteralExp;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Quick fix resolution on the Acceleo warning marker. It will change a comparison between a scalar element
 * and a collection by
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoCreateComparisonResolution implements IMarkerResolution2 {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("AcceleoCreateComparisonResolution.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getImage()
	 */
	public Image getImage() {
		return AcceleoUIActivator.getDefault().getImage("icons/quickfix/QuickFixResolveWarning.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	public String getLabel() {
		return AcceleoUIMessages.getString("AcceleoCreateComparisonResolution.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	public void run(IMarker marker) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null
				&& window.getActivePage().getActiveEditor() instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)window.getActivePage().getActiveEditor();
			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			if (document != null && editor.getContent() != null) {
				int newOffset = createWarningResolution(document, editor.getContent(), marker);
				if (newOffset > -1) {
					editor.selectAndReveal(newOffset, 0);
				}
			}
		}
	}

	/**
	 * Resolves the warning bu replacing the problem with a potential solution.
	 * 
	 * @param document
	 *            The document from the editor
	 * @param content
	 *            The content of the editor
	 * @param marker
	 *            The marker that we are trying to resolve
	 * @return The new offset of the resolved area.
	 */
	private int createWarningResolution(IDocument document, AcceleoSourceContent content, IMarker marker) {
		try {
			String message = marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
			int posBegin = marker.getAttribute(IMarker.CHAR_START, -1);
			int posEnd = marker.getAttribute(IMarker.CHAR_END, posBegin);
			if (message != null && posBegin > -1 && posEnd > -1) {
				ASTNode astNode = content.getASTNode(posBegin, posEnd);
				final int equals = 60;
				if (astNode instanceof OperationCallExp
						&& equals == ((OperationCallExp)astNode).getOperationCode()) {
					OperationCallExp operationCallExp = (OperationCallExp)astNode;
					OCLExpression<EClassifier> source = operationCallExp.getSource();
					List<OCLExpression<EClassifier>> arguments = operationCallExp.getArgument();
					if (source instanceof CollectionLiteralExp && arguments.size() == 1) {
						OCLExpression<EClassifier> oclExpression = arguments.get(0);

						// Creation of the replacement "collection->contains(argument)"
						String collection = document.get().substring(source.getStartPosition(),
								source.getEndPosition());
						String argument = document.get().substring(oclExpression.getStartPosition(),
								oclExpression.getEndPosition());
						String replacement = collection + "->includes(" + argument + ")"; //$NON-NLS-1$ //$NON-NLS-2$

						document.replace(source.getStartPosition(), oclExpression.getEndPosition()
								- source.getStartPosition(), replacement);
						return oclExpression.getEndPosition();
					}
				}
			}
		} catch (BadLocationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
		return -1;
	}

	/**
	 * Go to the offset where to put the new module element.
	 * 
	 * @param document
	 *            is the document
	 * @param content
	 *            is the content
	 * @param offset
	 *            is the offset
	 * @return the offset where to put the new module element
	 */
	protected int newOffset(IDocument document, AcceleoSourceContent content, int offset) {
		return document.getLength();
	}

}
