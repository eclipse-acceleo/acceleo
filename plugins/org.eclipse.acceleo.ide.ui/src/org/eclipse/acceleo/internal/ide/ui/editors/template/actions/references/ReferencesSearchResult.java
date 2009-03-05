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

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/**
 * This class store results of the query. It is also an adapter.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferencesSearchResult extends AbstractTextSearchResult implements IEditorMatchAdapter, IFileMatchAdapter {
	/**
	 * The query.
	 */
	private ReferencesSearchQuery query;

	/**
	 * Constructor.
	 * 
	 * @param query
	 *            the query to use
	 */
	public ReferencesSearchResult(ReferencesSearchQuery query) {
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchResult#getEditorMatchAdapter()
	 */
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchResult#getFileMatchAdapter()
	 */
	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchResult#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchResult#getLabel()
	 */
	public String getLabel() {
		return AcceleoUIMessages.getString("AcceleoReferencesSearch.Result.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchResult#getQuery()
	 */
	public ISearchQuery getQuery() {
		return query;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchResult#getTooltip()
	 */
	public String getTooltip() {
		return AcceleoUIMessages.getString("AcceleoReferencesSearch.Result.Tooltip"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.IEditorMatchAdapter#computeContainedMatches(org.eclipse.search.ui.text.AbstractTextSearchResult,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public Match[] computeContainedMatches(AbstractTextSearchResult result, IEditorPart editor) {
		IFile file = null;
		if (editor.getEditorInput() instanceof IFileEditorInput) {
			file = ((IFileEditorInput)editor.getEditorInput()).getFile();
		}
		return result.getMatches(file);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.IEditorMatchAdapter#isShownInEditor(org.eclipse.search.ui.text.Match,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public boolean isShownInEditor(Match match, IEditorPart editor) {
		if ((editor instanceof AcceleoEditor) && match.getElement() instanceof IFile) {
			AcceleoEditor acceleoEditor = (AcceleoEditor)editor;
			IFile matchedFile = (IFile)match.getElement();
			return acceleoEditor.getFile().equals(matchedFile);
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.IFileMatchAdapter#computeContainedMatches(org.eclipse.search.ui.text.AbstractTextSearchResult,
	 *      org.eclipse.core.resources.IFile)
	 */
	public Match[] computeContainedMatches(AbstractTextSearchResult result, IFile file) {
		return result.getMatches(file);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.IFileMatchAdapter#getFile(java.lang.Object)
	 */
	public IFile getFile(Object element) {
		if (element instanceof IFile) {
			return (IFile)element;
		} else {
			return null;
		}
	}
}
