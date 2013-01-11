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
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions.HideNonPublicAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions.HideQueriesAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions.HideTemplatesAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions.SortElementAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions.SortTypeAction;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
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
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * The template content outline page. This content outline page will be presented to the user via the standard
 * Content Outline View (the user decides whether their workbench window contains this view) whenever that
 * source editor is active.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePage extends Page implements IContentOutlinePage, ISelectionChangedListener {
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

	/** List of listeners registered for selection change against this page. */
	private ListenerList selectionChangedListeners = new ListenerList();

	/** Actual viewer displayed in the outline. */
	private TreeViewer treeViewer;

	/**
	 * The job instance to refresh the outline view.
	 */
	private RefreshViewJob refreshViewJob = new RefreshViewJob();

	/**
	 * The job class to refresh the outline view.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	class RefreshViewJob {

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
					if (!getTreeViewer().getControl().isDisposed()) {
						getTreeViewer().getControl().getDisplay().asyncExec(new Runnable() {
							public void run() {
								refreshContainer(element);
							}
						});
					} else {
						/*
						 * Assume that this was completed successfully : happened because the user closed the
						 * editor or switched perspective.
						 */
					}
					return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
				}
			};
			refreshJob.setPriority(Job.DECORATE);
			refreshJob.setSystem(true);
			final int schedule = 2000;
			refreshJob.schedule(schedule);
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
		outlinePageItemProvider = new AcceleoOutlinePageItemProviderAdapterFactory();
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
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.addSelectionChangedListener(this);
		treeViewer.setContentProvider(new AcceleoOutlinePageContentProvider(adapterFactory));

		// Handle decorations coming from org.eclipse.ui.decorators.
		ILabelProvider lp = new AcceleoOutlinePageLabelProvider(adapterFactory);
		ILabelDecorator decorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();

		treeViewer.setLabelProvider(new DecoratingLabelProvider(lp, decorator));
		setInput(editor.getContent().getCST());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.Page#setActionBars(org.eclipse.ui.IActionBars)
	 */
	@Override
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);
		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		toolBarManager.add(new SortElementAction(this.treeViewer));
		toolBarManager.add(new SortTypeAction(this.treeViewer));
		toolBarManager.add(new HideNonPublicAction(this.treeViewer));
		toolBarManager.add(new HideQueriesAction(this.treeViewer));
		toolBarManager.add(new HideTemplatesAction(this.treeViewer));
		actionBars.updateActionBars();
	}

	/**
	 * Fires a selection changed event.
	 * 
	 * @param selection
	 *            the new selection
	 */
	protected void fireSelectionChanged(ISelection selection) {
		// create an event
		final SelectionChangedEvent event = new SelectionChangedEvent(this, selection);

		// fire the event
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener)listeners[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.Page#getControl()
	 */
	@Override
	public Control getControl() {
		if (treeViewer == null) {
			return null;
		}
		return treeViewer.getControl();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		if (treeViewer == null) {
			return StructuredSelection.EMPTY;
		}
		return treeViewer.getSelection();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.Page#init(org.eclipse.ui.part.IPageSite)
	 */
	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		pageSite.setSelectionProvider(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		fireSelectionChanged(event.getSelection());
	}

	/**
	 * Sets focus to a part in the page.
	 */
	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		if (treeViewer != null) {
			treeViewer.setSelection(selection);
		}
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
	protected void refreshContainer(final Object element) {
		if (element instanceof Module) {
			int selection = -1;
			if (getTreeViewer().getControl() instanceof Scrollable
					&& !((Scrollable)getTreeViewer().getControl()).isDisposed()
					&& ((Scrollable)getTreeViewer().getControl()).getVerticalBar() != null) {
				selection = ((Scrollable)getTreeViewer().getControl()).getVerticalBar().getSelection();
			}
			if (!this.getTreeViewer().getTree().isDisposed()) {
				TreePath[] treePaths = getTreeViewer().getExpandedTreePaths();
				getTreeViewer().setInput(element);
				getTreeViewer().setExpandedTreePaths(treePaths);
				if (selection > -1 && getTreeViewer().getControl() instanceof Scrollable
						&& ((Scrollable)getTreeViewer().getControl()).getVerticalBar() != null) {
					((Scrollable)getTreeViewer().getControl()).getVerticalBar().setSelection(selection);
				}
			}
		} else {
			if (!getTreeViewer().getTree().isDisposed()) {
				TreePath[] treePaths = getTreeViewer().getExpandedTreePaths();
				getTreeViewer().refresh(element);
				getTreeViewer().setExpandedTreePaths(treePaths);
			}
		}
	}

	/**
	 * Returns this page's tree viewer.
	 * 
	 * @return this page's tree viewer, or <code>null</code> if <code>createControl</code> has not been called
	 *         yet
	 */
	protected TreeViewer getTreeViewer() {
		return treeViewer;
	}

}
