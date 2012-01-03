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

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.MarkerUtilities;

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
	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchResult#getFileMatchAdapter()
	 */
	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchResult#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return AcceleoUIActivator.getImageDescriptor("icons/AcceleoEditor.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.ISearchResult#getLabel()
	 */
	public String getLabel() {
		int matchCount = this.getMatchCount();
		EObject declaration = this.query.getDeclaration();
		return AcceleoUIMessages.getString("AcceleoReferencesSearch.Result.Label", declaration, Integer //$NON-NLS-1$
				.valueOf(matchCount));
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
		if (editor instanceof AcceleoEditor && ((AcceleoEditor)editor).getFile() != null
				&& match.getElement() instanceof IFile) {
			IFile matchedFile = (IFile)match.getElement();
			return ((AcceleoEditor)editor).getFile().equals(matchedFile);
		}
		return false;
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
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchResult#removeAll()
	 */
	@Override
	public void removeAll() {
		// Remove all the search markers
		Object[] elements = this.getElements();
		for (Object object : elements) {
			if (object instanceof ReferenceEntry) {
				ReferenceEntry entry = (ReferenceEntry)object;
				this.removeMarker(entry);
			}
		}

		super.removeAll();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchResult#removeMatch(org.eclipse.search.ui.text.Match)
	 */
	@Override
	public void removeMatch(Match match) {
		Object element = match.getElement();
		if (element instanceof ReferenceEntry) {
			ReferenceEntry entry = (ReferenceEntry)element;
			this.removeMarker(entry);
		}
		super.removeMatch(match);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchResult#removeMatches(org.eclipse.search.ui.text.Match[])
	 */
	@Override
	public void removeMatches(Match[] matches) {
		for (Match match : matches) {
			Object element = match.getElement();
			if (element instanceof ReferenceEntry) {
				ReferenceEntry entry = (ReferenceEntry)element;
				this.removeMarker(entry);
			}
		}
		super.removeMatches(matches);
	}

	/**
	 * Remove the marker of the given entry.
	 * 
	 * @param entry
	 *            The entry
	 */
	private void removeMarker(ReferenceEntry entry) {
		IFile file = entry.getTemplateFile();
		try {
			IMarker[] markers = file.findMarkers(NewSearchUI.SEARCH_MARKER, false, IResource.DEPTH_INFINITE);
			for (IMarker iMarker : markers) {
				int charStart = MarkerUtilities.getCharStart(iMarker);
				int charEnd = MarkerUtilities.getCharEnd(iMarker);
				if (charStart == entry.getRegion().getOffset()
						&& charEnd == entry.getRegion().getOffset() + entry.getRegion().getLength()) {
					iMarker.delete();
				}
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
	}
}
