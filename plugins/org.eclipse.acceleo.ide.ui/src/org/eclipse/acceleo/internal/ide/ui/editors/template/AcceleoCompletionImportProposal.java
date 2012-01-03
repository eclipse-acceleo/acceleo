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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.io.IOException;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoUIResourceSet;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * An implementation of the <code>ICompletionProposal</code> interface for better performances on the
 * 'imports' section. We store the URI of the imported EMTL file. The EMTL model is loaded only when it is
 * necessary, it means only when we apply the completion proposal. We load the EMTL file to get the ID of the
 * module ('or::eclipse::myGenerator').
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class AcceleoCompletionImportProposal implements ICompletionProposal {

	/**
	 * The string to be inserted into the document. This string has a default value but it cans also be
	 * modified by reading the corresponding module ID in the EMTL file.
	 */
	private String dynamicReplacementString;

	/**
	 * The EMTL file that contains the best string to be inserted into the document.
	 */
	private URI emtlURI;

	/**
	 * The string to be displayed.
	 **/
	private String displayString;

	/**
	 * The replacement offset.
	 */
	private int replacementOffset;

	/**
	 * The replacement length.
	 */
	private int replacementLength;

	/**
	 * The image to be displayed.
	 */
	private Image image;

	/**
	 * Creates a new completion proposal.
	 * 
	 * @param emtlURI
	 *            the EMTL file that contains the string to be inserted into the document
	 * @param replacementOffset
	 *            the offset of the text to be replaced
	 * @param replacementLength
	 *            the length of the text to be replaced
	 * @param image
	 *            the image to display
	 * @param displayString
	 *            the string to display
	 */
	public AcceleoCompletionImportProposal(URI emtlURI, int replacementOffset, int replacementLength,
			Image image, String displayString) {
		this.dynamicReplacementString = new Path(emtlURI.lastSegment()).removeFileExtension().lastSegment();
		this.emtlURI = emtlURI;
		this.replacementOffset = replacementOffset;
		this.replacementLength = replacementLength;
		this.image = image;
		this.displayString = displayString;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#apply(org.eclipse.jface.text.IDocument)
	 */
	public void apply(IDocument document) {
		try {
			EObject eObject;
			try {
				eObject = AcceleoUIResourceSet.getResource(emtlURI);
			} catch (IOException e) {
				eObject = null;
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
			}
			if (eObject instanceof Module && ((Module)eObject).getNsURI() != null
					&& ((Module)eObject).getNsURI().length() > 0) {
				dynamicReplacementString = ((Module)eObject).getNsURI();
			}
			document.replace(replacementOffset, replacementLength, dynamicReplacementString);
		} catch (BadLocationException x) {
			// ignore
			AcceleoUIActivator.log(x, true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getSelection(org.eclipse.jface.text.IDocument)
	 */
	public Point getSelection(IDocument document) {
		return new Point(replacementOffset + dynamicReplacementString.length(), 0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getContextInformation()
	 */
	public IContextInformation getContextInformation() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getImage()
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getDisplayString()
	 */
	public String getDisplayString() {
		return displayString;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getAdditionalProposalInfo()
	 */
	public String getAdditionalProposalInfo() {
		return "Project Name: " + getModuleProjectName() + "<br/>Path: " + getModulePackage(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Gets the module project name for the EMTL file URI.
	 * 
	 * @return the module project name, it means the first significant segment of the URI
	 */
	private String getModuleProjectName() {
		String result;
		String path = emtlURI.toString();
		String prefix = "platform:/resource/"; //$NON-NLS-1$
		if (path.startsWith(prefix)) {
			path = path.substring(prefix.length());
		} else {
			prefix = "platform:/plugin/"; //$NON-NLS-1$
			if (path.startsWith(prefix)) {
				path = path.substring(prefix.length());
			}
		}
		IPath relativePath = new Path(path);
		if (relativePath.segmentCount() > 1) {
			if (emtlURI.isPlatformPlugin()) {
				result = relativePath.segment(0) + " [plugin]"; //$NON-NLS-1$
			} else {
				result = relativePath.segment(0);
			}
		} else {
			result = "[others]"; //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Gets the module package for the EMTL file URI.
	 * 
	 * @return the module package
	 */
	private String getModulePackage() {
		String result;
		String path = emtlURI.toString();
		String prefix = "platform:/resource/"; //$NON-NLS-1$
		if (path.startsWith(prefix)) {
			path = path.substring(prefix.length());
		} else {
			prefix = "platform:/plugin/"; //$NON-NLS-1$
			if (path.startsWith(prefix)) {
				path = path.substring(prefix.length());
			}
		}
		IPath relativePath = new Path(path);
		if (relativePath.segmentCount() > 2) {
			result = relativePath.removeFirstSegments(1).removeLastSegments(1).toString();
		} else {
			result = ""; //$NON-NLS-1$
		}
		return result;
	}
}
