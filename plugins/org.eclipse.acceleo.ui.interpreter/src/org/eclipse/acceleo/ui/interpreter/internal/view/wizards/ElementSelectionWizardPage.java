/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.view.wizards;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

/**
 * This wizard page displays a {@link ResourceSet}'s content, prompting the user to select one or more
 * elements from it.
 * <p>
 * Users are allowed to load new {@link Resource}s into the resource set.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class ElementSelectionWizardPage extends WizardPage implements IMenuListener {
	/** The editing domain on which to operate. */
	protected final EditingDomain editingDomain;

	private EObject result;

	/**
	 * Instantiates this wizard page given the editing domain on which to operate.
	 * 
	 * @param editingDomain
	 *            The editing domain on which to operate.
	 */
	public ElementSelectionWizardPage(EditingDomain editingDomain) {
		super("Variable value selection");
		setDescription("Please select the elements for which you need a variable.");
		setPageComplete(false);

		this.editingDomain = editingDomain;
	}

	/**
	 * Creates the adapter factory that will be used by the content and label providers of this page's viewer.
	 * 
	 * @return The adapter factory that will be used by the content and label providers of this page's viewer.
	 */
	protected static AdapterFactory createAdapterFactory() {
		AdapterFactory adaptertFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		return adaptertFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		final TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		AdapterFactory adapterFactory = createAdapterFactory();
		viewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		createContextMenu(viewer);

		viewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				ISelection selection = viewer.getSelection();
				if (selection instanceof IStructuredSelection && !selection.isEmpty()
						&& ((IStructuredSelection)selection).getFirstElement() instanceof EObject) {
					result = (EObject)((IStructuredSelection)selection).getFirstElement();
					setPageComplete(true);
				}
			}
		});

		viewer.setInput(editingDomain.getResourceSet());

		setControl(viewer.getControl());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	public void menuAboutToShow(IMenuManager manager) {
		manager.add(new LoadResourceAction(editingDomain));
	}

	/**
	 * Creates the context menu of the ResourceSet's TreeViewer.
	 * 
	 * @param viewer
	 *            The resourceSet's TreeViewer.
	 */
	private void createContextMenu(TreeViewer viewer) {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	public EObject getResult() {
		return result;
	}
}
