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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlExtension;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

/**
 * This will be used to display the quick outline to the user.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QuickOutlineControl extends PopupDialog implements IInformationControl, IInformationControlExtension, IInformationControlExtension2, DisposeListener {
	/** Acceleo Editor to which this quick outline dialog is tied : selections will take place in this editor. */
	protected final AcceleoEditor editor;

	/** Adapter factory used by the content and label providers. */
	private AdapterFactory adapterFactory;

	/** The filtered tree we're displaying. */
	private FilteredTree filteredTree;

	/** Actual viewer displaying the outline. */
	private TreeViewer treeViewer;

	/**
	 * Creates an information control with the given shell as parent.
	 * 
	 * @param parentShell
	 *            the parent of this control's shell.
	 * @param shellStyle
	 *            The shell style.
	 * @param acceleoEditor
	 *            Editor to which this outline view is tied.
	 */
	public QuickOutlineControl(Shell parentShell, int shellStyle, AcceleoEditor acceleoEditor) {
		super(parentShell, shellStyle, true, true, false, true, true, null, null);
		create();
		editor = acceleoEditor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public void addDisposeListener(DisposeListener listener) {
		getShell().addDisposeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#addFocusListener(org.eclipse.swt.events.FocusListener)
	 */
	public void addFocusListener(FocusListener listener) {
		getShell().addFocusListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#computeSizeHint()
	 */
	public Point computeSizeHint() {
		return getShell().getSize();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#dispose()
	 */
	public final void dispose() {
		filteredTree.dispose();
		close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControlExtension#hasContents()
	 */
	public boolean hasContents() {
		// FIXME check TreeViewer filtered content and return false if no visible children
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#isFocusControl()
	 */
	public boolean isFocusControl() {
		return getShell() == Display.getCurrent().getActiveShell();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public void removeDisposeListener(DisposeListener listener) {
		getShell().removeDisposeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#removeFocusListener(org.eclipse.swt.events.FocusListener)
	 */
	public void removeFocusListener(FocusListener listener) {
		getShell().removeFocusListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setBackgroundColor(org.eclipse.swt.graphics.Color)
	 */
	public void setBackgroundColor(Color background) {
		applyBackgroundColor(background, getContents());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setFocus()
	 */
	public void setFocus() {
		getShell().forceFocus();
		filteredTree.setFocus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setForegroundColor(org.eclipse.swt.graphics.Color)
	 */
	public void setForegroundColor(Color foreground) {
		applyForegroundColor(foreground, getContents());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setInformation(java.lang.String)
	 */
	public void setInformation(String information) {
		// We're implementing IInformationControlExtension2, this will not be called
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControlExtension2#setInput(java.lang.Object)
	 */
	public void setInput(Object input) {
		treeViewer.setInput(input);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setLocation(org.eclipse.swt.graphics.Point)
	 */
	public void setLocation(Point location) {
		// Only override the shell's location if it's not persisted by the PopupDialog
		if (!getPersistLocation()) {
			getShell().setLocation(location);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setSize(int, int)
	 */
	public void setSize(int width, int height) {
		getShell().setSize(width, height);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setSizeConstraints(int, int)
	 */
	public void setSizeConstraints(int maxWidth, int maxHeight) {
		// We'll use the dialog's size
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControl#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		if (visible) {
			open();
		} else {
			saveDialogBounds(getShell());
			getShell().setVisible(false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
	 */
	public void widgetDisposed(DisposeEvent event) {
		adapterFactory = null;
		filteredTree = null;
		treeViewer = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.PopupDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		createTreeViewer(parent);

		addDisposeListener(this);
		return treeViewer.getControl();
	}

	/**
	 * Creates the outline's tree viewer.
	 * 
	 * @param parent
	 *            parent composite.
	 */
	protected void createTreeViewer(Composite parent) {
		createFilteredTree(parent);
		treeViewer = filteredTree.getViewer();
		final Tree tree = treeViewer.getTree();

		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.CR || event.character == SWT.KEYPAD_CR) {
					TreeItem[] selection = tree.getSelection();
					if (selection[0].getData() instanceof CSTNode) {
						CSTNode node = (CSTNode)selection[0].getData();
						editor.selectRange(node.getStartPosition(), node.getEndPosition());
						dispose();
					}
				} else if (event.character == SWT.ESC) {
					dispose();
				}
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent event) {
				if (tree.equals(event.getSource())) {
					if (tree.getSelectionCount() != 1 || event.button != 1) {
						return;
					}

					TreeItem[] selection = tree.getSelection();
					if (selection[0].getData() instanceof CSTNode) {
						CSTNode node = (CSTNode)selection[0].getData();
						editor.selectRange(node.getStartPosition(), node.getEndPosition());
						dispose();
					}
				}
			}
		});

		treeViewer.setContentProvider(new QuickOutlineContentProvider(getAdapterFactory()));
		treeViewer.setLabelProvider(new AcceleoOutlinePageLabelProvider(getAdapterFactory()));

		// We want to remove everything that's not "top level elements" from the outline view
		treeViewer.addFilter(new ViewerFilter() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
			 *      java.lang.Object, java.lang.Object)
			 */
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return element instanceof Template || element instanceof Query || element instanceof Macro;
			}
		});
	}

	/**
	 * Externalized here due to the number of "catch" clauses the reflective call to a constructor requires.
	 * 
	 * @param parent
	 *            Parent composite of the filtered tree.
	 */
	private void createFilteredTree(Composite parent) {
		Constructor<FilteredTree> filteredTreeConstructor = null;
		try {
			filteredTreeConstructor = FilteredTree.class.getConstructor(Composite.class, int.class,
					PatternFilter.class, boolean.class);
			filteredTree = filteredTreeConstructor.newInstance(parent, Integer.valueOf(SWT.SINGLE
					| SWT.H_SCROLL | SWT.V_SCROLL), new QuickOutlinePatternFilter(), Boolean.TRUE);
		} catch (NoSuchMethodException e) {
			// Eclipse < 3.5
			try {
				filteredTreeConstructor = FilteredTree.class.getConstructor(Composite.class, int.class,
						PatternFilter.class);
				filteredTree = filteredTreeConstructor.newInstance(parent, Integer.valueOf(SWT.SINGLE
						| SWT.H_SCROLL | SWT.V_SCROLL), new QuickOutlinePatternFilter());
			} catch (NoSuchMethodException ee) {
				// shouldn't happen
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, ee.getMessage()));
			} catch (IllegalArgumentException ee) {
				// shouldn't happen
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, ee.getMessage()));
			} catch (InstantiationException ee) {
				// shouldn't happen
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, ee.getMessage()));
			} catch (IllegalAccessException ee) {
				// shouldn't happen
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, ee.getMessage()));
			} catch (InvocationTargetException ee) {
				// shouldn't happen
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, ee.getMessage()));
			}
		} catch (IllegalArgumentException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage()));
		} catch (InstantiationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage()));
		} catch (IllegalAccessException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage()));
		} catch (InvocationTargetException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage()));
		}
	}

	/**
	 * Returns the adapter factory used by this viewer.
	 * 
	 * @return The adapter factory used by this viewer.
	 */
	protected AdapterFactory getAdapterFactory() {
		if (adapterFactory == null) {
			List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
			factories.add(new AcceleoOutlinePageItemProviderAdapterFactory());
			factories.add(new ResourceItemProviderAdapterFactory());
			factories.add(new EcoreItemProviderAdapterFactory());
			factories.add(new ReflectiveItemProviderAdapterFactory());
			adapterFactory = new ComposedAdapterFactory(factories);
		}
		return adapterFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.PopupDialog#hasTitleArea()
	 */
	@Override
	protected boolean hasTitleArea() {
		return false;
	}
}
