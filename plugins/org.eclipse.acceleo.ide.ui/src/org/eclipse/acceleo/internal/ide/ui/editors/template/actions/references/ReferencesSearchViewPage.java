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
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.IShowInTargetList;

/**
 * This class is the page referenced by the extention point org.eclipse.search.searchResultViewPages. It is
 * used to show ReferencesSearchResult
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferencesSearchViewPage extends AbstractTextSearchViewPage implements IAdaptable {

	/**
	 * The show in target list constant.
	 */
	private static final IShowInTargetList SHOW_IN_TARGET_LIST = new IShowInTargetList() {
		public String[] getShowInTargetIds() {
			return new String[] {IPageLayout.ID_PROJECT_EXPLORER };
		}
	};

	/**
	 * The TreeContentProvider to use.
	 */
	private ReferencesTreeContentProvider treeContentProvider;

	/**
	 * Constructor.
	 */
	public ReferencesSearchViewPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_TREE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchViewPage#clear()
	 */
	@Override
	protected void clear() {
		if (treeContentProvider != null) {
			treeContentProvider.clear();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchViewPage#configureTableViewer(org.eclipse.jface.viewers.TableViewer)
	 */
	@Override
	protected void configureTableViewer(TableViewer viewer) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchViewPage#configureTreeViewer(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setUseHashlookup(true);
		viewer.setSorter(new ReferenceSorter());
		viewer.setLabelProvider(new ReferenceLabelProvider());
		treeContentProvider = new ReferencesTreeContentProvider(viewer);
		viewer.setContentProvider(treeContentProvider);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchViewPage#elementsChanged(java.lang.Object[])
	 */
	@Override
	protected void elementsChanged(Object[] objects) {
		if (treeContentProvider != null) {
			treeContentProvider.elementsChanged(objects);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (IShowInTargetList.class.equals(adapter)) {
			return SHOW_IN_TARGET_LIST;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.search.ui.text.AbstractTextSearchViewPage#showMatch(org.eclipse.search.ui.text.Match,
	 *      int, int, boolean)
	 */
	@Override
	protected void showMatch(Match match, int currentOffset, int currentLength, boolean activate) {
		ReferenceEntry entry = (ReferenceEntry)match.getElement();
		EObject eObject = entry.getMatch();
		URI fileURI;
		if (eObject.eResource() != null) {
			fileURI = eObject.eResource().getURI();
		} else if (entry.getTemplateFile() != null) {
			AcceleoProject acceleoProject = new AcceleoProject(entry.getTemplateFile().getProject());
			fileURI = URI.createPlatformResourceURI(acceleoProject.getOutputFilePath(entry.getTemplateFile())
					.toString(), false);
		} else {
			fileURI = null;
		}
		if (fileURI != null) {
			IFile moduleFile = entry.getTemplateFile();
			try {
				IDE.openEditor(getSite().getPage(), moduleFile);
				OpenDeclarationUtils.showEObject(getSite().getPage(), fileURI, new Region(currentOffset,
						currentLength), eObject);
			} catch (PartInitException e) {
				AcceleoUIActivator.log(e, false);
			}
		}
	}
}
