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
package org.eclipse.acceleo.internal.ide.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.DrillDownComposite;

/**
 * File selection composite for the ResourceSelectionDialogue.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TreeSelectionComposite extends Composite {
	/**
	 * Sizing constants for width.
	 */
	private static final int SIZING_SELECTION_PANE_WIDTH = 320;

	/**
	 * Sizing constants for height.
	 */
	private static final int SIZING_SELECTION_PANE_HEIGHT = 300;

	/**
	 * The tree viewer.
	 */
	protected TreeViewer treeViewer;

	/**
	 * The listener to notify of events.
	 */
	private Listener listener;

	/**
	 * Enable user to type in new resource name.
	 */
	private boolean allowNewResourceName = true;

	/**
	 * Last selection made by user.
	 */
	private IResource selectedResource;

	/**
	 * Handle on parts.
	 */
	private Text resourceNameField;

	/**
	 * The content provider of the tree viewer.
	 */
	private ITreeContentProvider contentProvider;

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param contentProvider
	 *            A content provider for the tree viewer.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is required.
	 * @param allowNewResourceName
	 *            Enable the user to type in a new resource name instead of just selecting from the existing
	 *            ones.
	 */
	public TreeSelectionComposite(Composite parent, ITreeContentProvider contentProvider, Listener listener,
			boolean allowNewResourceName) {
		this(parent, contentProvider, listener, allowNewResourceName, null);
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param contentProvider
	 *            A content provider for the tree viewer.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is required.
	 * @param allowNewResourceName
	 *            Enable the user to type in a new resource name instead of just selecting from the existing
	 *            ones.
	 * @param message
	 *            The text to present to the user.
	 */
	public TreeSelectionComposite(Composite parent, ITreeContentProvider contentProvider, Listener listener,
			boolean allowNewResourceName, String message) {
		this(parent, contentProvider, listener, allowNewResourceName, message, SIZING_SELECTION_PANE_HEIGHT,
				SIZING_SELECTION_PANE_WIDTH);
	}

	/**
	 * Creates a new instance of the widget.
	 * 
	 * @param parent
	 *            The parent widget of the group.
	 * @param contentProvider
	 *            A content provider for the tree viewer.
	 * @param listener
	 *            A listener to forward events to. Can be null if no listener is required.
	 * @param allowNewResourceName
	 *            Enable the user to type in a new resource name instead of just selecting from the existing
	 *            ones.
	 * @param message
	 *            The text to present to the user.
	 * @param heightHint
	 *            height hint for the drill down composite
	 * @param widthHint
	 *            width hint for the drill down composite
	 */
	public TreeSelectionComposite(Composite parent, ITreeContentProvider contentProvider, Listener listener,
			boolean allowNewResourceName, String message, int heightHint, int widthHint) {
		super(parent, SWT.NONE);
		this.listener = listener;
		this.contentProvider = contentProvider;
		this.allowNewResourceName = allowNewResourceName;
		if (message != null) {
			createContents(message, heightHint, widthHint);
		} else if (allowNewResourceName) {
			createContents(
					AcceleoUIMessages.getString("TreeSelectionComposite.allowResourceCreation"), heightHint, widthHint); //$NON-NLS-1$
		} else {
			createContents(
					AcceleoUIMessages.getString("TreeSelectionComposite.resourceSelecitonOnly"), heightHint, widthHint); //$NON-NLS-1$
		}
	}

	/**
	 * The resource selection has changed in the tree view. Update the resource name field value and notify
	 * all listeners.
	 * 
	 * @param resource
	 *            The container that changed
	 */
	public void resourceSelectionChanged(IResource resource) {
		selectedResource = resource;

		if (allowNewResourceName) {
			if (resource == null) {
				resourceNameField.setText(""); //$NON-NLS-1$
			} else {
				String text = TextProcessor.process(resource.getFullPath().makeRelative().toString());
				resourceNameField.setText(text);
				resourceNameField.setToolTipText(text);
			}
		}

		// fire an event so the parent can update its controls
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			changeEvent.widget = this;
			listener.handleEvent(changeEvent);
		}
	}

	/**
	 * Creates the contents of the composite.
	 * 
	 * @param message
	 *            the message
	 */
	public void createContents(String message) {
		createContents(message, SIZING_SELECTION_PANE_HEIGHT, SIZING_SELECTION_PANE_WIDTH);
	}

	/**
	 * Creates the contents of the composite.
	 * 
	 * @param message
	 *            the message
	 * @param heightHint
	 *            the hint height
	 * @param widthHint
	 *            the hint width
	 */
	public void createContents(String message, int heightHint, int widthHint) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(this, SWT.WRAP);
		label.setText(message);
		label.setFont(this.getFont());

		if (allowNewResourceName) {
			resourceNameField = new Text(this, SWT.SINGLE | SWT.BORDER);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.widthHint = widthHint;
			resourceNameField.setLayoutData(gd);
			resourceNameField.addListener(SWT.Modify, listener);
			resourceNameField.setFont(this.getFont());
		} else {
			// filler...
			new Label(this, SWT.NONE);
		}

		createTreeViewer(heightHint);
		Dialog.applyDialogFont(this);
	}

	/**
	 * Returns a new drill down viewer for this dialog.
	 * 
	 * @param heightHint
	 *            height hint for the drill down composite
	 */
	protected void createTreeViewer(int heightHint) {
		// Create drill down.
		DrillDownComposite drillDown = new DrillDownComposite(this, SWT.BORDER);
		GridData spec = new GridData(SWT.FILL, SWT.FILL, true, true);
		spec.widthHint = SIZING_SELECTION_PANE_WIDTH;
		spec.heightHint = heightHint;
		drillDown.setLayoutData(spec);

		// Create tree viewer inside drill down.
		treeViewer = new TreeViewer(drillDown, SWT.NONE);
		drillDown.setChildTree(treeViewer);
		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
		treeViewer.setComparator(new ViewerComparator());
		treeViewer.setUseHashlookup(true);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				resourceSelectionChanged((IResource)selection.getFirstElement()); // allow null
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection)selection).getFirstElement();
					if (item == null) {
						return;
					}
					if (treeViewer.getExpandedState(item)) {
						treeViewer.collapseToLevel(item, 1);
					} else {
						treeViewer.expandToLevel(item, 1);
					}
				}
			}
		});

		// This has to be done after the viewer has been laid out
		treeViewer.setInput(ResourcesPlugin.getWorkspace());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.dialog.AbstractResourceComposite#getResourceFullPath()
	 */
	public IPath getResourceFullPath() {
		IPath res = null;
		if (allowNewResourceName) {
			String pathName = resourceNameField.getText();
			if (pathName != null && pathName.length() > 0) {
				// The user may not have made this absolute so do it for them
				res = (new Path(TextProcessor.deprocess(pathName))).makeAbsolute();
			}
		} else if (selectedResource != null) {
			res = selectedResource.getFullPath();
		}
		return res;
	}

	/**
	 * Gives focus to one of the widgets in the group, as determined by the group.
	 */
	public void setInitialFocus() {
		if (allowNewResourceName) {
			resourceNameField.setFocus();
		} else {
			treeViewer.getTree().setFocus();
		}
	}

	/**
	 * Set the initial selection.
	 * 
	 * @param resource
	 *            the resource to select
	 */
	public void setSelectedResource(IResource resource) {
		selectedResource = resource;

		// expand to and select the specified resource
		List<IContainer> itemsToExpand = new ArrayList<IContainer>();
		IContainer parent = resource.getParent();
		while (parent != null) {
			itemsToExpand.add(0, parent);
			parent = parent.getParent();
		}
		treeViewer.setExpandedElements(itemsToExpand.toArray());
		treeViewer.setSelection(new StructuredSelection(resource), true);
	}

}
