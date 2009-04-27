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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * The template content outline page. This content outline page will be presented to the user via the standard
 * Content Outline View (the user decides whether their workbench window contains this view) whenever that
 * source editor is active.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePage extends ContentOutlinePage {

	/**
	 * The editor.
	 */
	protected AcceleoEditor editor;

	/**
	 * The factory for creating adapters.
	 */
	protected AdapterFactory adapterFactory;

	/**
	 * The editing domain.
	 */
	protected AdapterFactoryEditingDomain editingDomain;

	/**
	 * The item provider of the outline page.
	 */
	protected AcceleoOutlinePageItemProviderAdapterFactory outlinePageItemProvider;

	/**
	 * The job instance to refresh the outline view.
	 */
	private RefreshViewJob refreshViewJob = new RefreshViewJob();

	/**
	 * The job class to refresh the outline view.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private class RefreshViewJob {

		/**
		 * The element to refresh.
		 */
		Object element;

		/**
		 * The job.
		 */
		Job refreshJob;

		/**
		 * Refreshes the view for the given element.
		 * 
		 * @param elem
		 *            is the element to refresh
		 */
		public void refreshView(Object elem) {
			if (refreshJob != null) {
				refreshJob.cancel();
			}
			this.element = elem;
			refreshJob = new Job("Acceleo") { //$NON-NLS-1$

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					getTreeViewer().getControl().getDisplay().asyncExec(new Runnable() {
						public void run() {
							refreshContainer(element);
						}
					});
					return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
				}
			};
			refreshJob.setPriority(Job.DECORATE);
			refreshJob.schedule(1000);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            is the editor
	 */
	public AcceleoOutlinePage(AcceleoEditor editor) {
		super();
		this.editor = editor;
		outlinePageItemProvider = new AcceleoOutlinePageItemProviderAdapterFactory(editor);
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(outlinePageItemProvider);
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new EcoreItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		adapterFactory = new ComposedAdapterFactory(factories);
		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack,
				new HashMap<Resource, Boolean>());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new AcceleoOutlinePageContentProvider(adapterFactory));
		viewer.setLabelProvider(new AcceleoOutlinePageLabelProvider(adapterFactory));
		setInput(editor.getContent().getCST());
	}

	/**
	 * Updates the input model of the outline view.
	 * 
	 * @param root
	 *            is the root element of the new input model
	 */
	private void setInput(EObject root) {
		if (root != null && root.eContents().size() > 0) {
			if (root.eContents().size() == 1) {
				getTreeViewer().setInput((root.eContents().get(0)).eContents());
			} else {
				getTreeViewer().setInput(root.eContents());
			}
		} else {
			getTreeViewer().setInput(null);
		}
	}

	/**
	 * Refreshes the given element and its children in the outline view.
	 * 
	 * @param element
	 *            is the element to refresh
	 */
	public void refresh(final Object element) {
		if (element instanceof EObject && ((EObject)element).eContainer() != null) {
			refreshViewJob.refreshView(((EObject)element).eContainer());
		} else {
			refreshViewJob.refreshView(element);
		}
	}

	/**
	 * Refreshes the given container and its children in the outline view.
	 * 
	 * @param element
	 *            is the container of the modified element
	 */
	private void refreshContainer(final Object element) {
		if (element instanceof Module) {
			getTreeViewer().setInput(element);
		} else {
			Object[] elements = getTreeViewer().getExpandedElements();
			TreePath[] treePaths = getTreeViewer().getExpandedTreePaths();
			getTreeViewer().refresh();
			getTreeViewer().setExpandedElements(elements);
			getTreeViewer().setExpandedTreePaths(treePaths);
		}
		getTreeViewer().expandToLevel(element, 2);
	}

}
