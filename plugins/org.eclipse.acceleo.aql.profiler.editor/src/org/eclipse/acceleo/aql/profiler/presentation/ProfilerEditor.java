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
package org.eclipse.acceleo.aql.profiler.presentation;

import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.acceleo.aql.profiler.editor.AcceleoEnvResourceFactory;
import org.eclipse.acceleo.aql.profiler.provider.ProfilerItemProviderAdapterFactorySpec;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.presentation.EcoreEditorPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * This class implements the profiler editor.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ProfilerEditor extends EcoreEditor {

	/**
	 * Initialize the Profiler metamodele.
	 */
	@SuppressWarnings("unused")
	private static final ProfilerPackage PKG = ProfilerPackage.eINSTANCE;

	/** The current sort status of the view. */
	protected ProfilerSortStatus sortStatus = new ProfilerSortStatus();

	private AcceleoEnvResourceFactory acceleoEnvResourceFactory;

	/**
	 * Constructor.
	 */
	public ProfilerEditor() {
		super();

		adapterFactory.addAdapterFactory(new ProfilerItemProviderAdapterFactorySpec());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#createPages()
	 */
	@Override
	public void createPages() {
		super.createPages();

		selectionViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				final Object selected = ((TreeSelection)event.getSelection()).getFirstElement();
				EObject monitored = ((ProfileEntry)selected).getMonitored();
				if (selected instanceof ProfileEntry && monitored != null) {
					selectInEditor(monitored);
				}
			}
		});

		selectionViewer.setComparator(new ViewerComparator() {
			/* (non-Javadoc) */
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				int ret = 0;
				if (e1 instanceof ProfileEntry && e2 instanceof ProfileEntry) {
					ProfileEntry entry1 = (ProfileEntry)e1;
					ProfileEntry entry2 = (ProfileEntry)e2;
					switch (sortStatus.getSortOrder()) {
						case ProfilerSortStatus.SORT_BY_CREATION_TIME:
							ret = (int)(entry1.getCreateTime() - entry2.getCreateTime());
							break;
						case ProfilerSortStatus.SORT_BY_TIME:
							ret = (int)(entry2.getDuration() - entry1.getDuration());
							break;
						default:
							// nothing to do here
							break;
					}
				}
				return ret;
			}
		});

		selectionViewer.setContentProvider(new ProfileEditorContentProvider(adapterFactory));

		IToolBarManager toolBarManager = getActionBars().getToolBarManager();
		toolBarManager.add(new ProfilerSortAction(sortStatus, selectionViewer));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#getContentOutlinePage()
	 */
	@Override
	public IContentOutlinePage getContentOutlinePage() {
		if (contentOutlinePage == null) {
			/**
			 * The content outline is just a tree.
			 * 
			 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
			 */
			@SuppressWarnings("synthetic-access")
			class MyContentOutlinePage extends ContentOutlinePage {
				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
				 */
				@Override
				public void createControl(Composite parent) {
					super.createControl(parent);
					contentOutlineViewer = getTreeViewer();
					contentOutlineViewer.addSelectionChangedListener(this);

					// Set up the tree viewer.
					//
					contentOutlineViewer.setContentProvider(new ProfilerOutlineContentProvider(
							adapterFactory));
					contentOutlineViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
					contentOutlineViewer.setInput(editingDomain.getResourceSet());

					// Make sure our popups work.
					//
					createContextMenuFor(contentOutlineViewer);

					if (!editingDomain.getResourceSet().getResources().isEmpty()) {
						// Select the root object in the view.
						//
						contentOutlineViewer.setSelection(StructuredSelection.EMPTY, true);
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#makeContributions(org.eclipse.jface.action.IMenuManager,
				 *      org.eclipse.jface.action.IToolBarManager, org.eclipse.jface.action.IStatusLineManager)
				 */
				@Override
				public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager,
						IStatusLineManager statusLineManager) {
					super.makeContributions(menuManager, toolBarManager, statusLineManager);
					contentOutlineStatusLineManager = statusLineManager;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#setActionBars(org.eclipse.ui.IActionBars)
				 */
				@Override
				public void setActionBars(IActionBars actionBars) {
					super.setActionBars(actionBars);
					getActionBarContributor().shareGlobalActions(this, actionBars);
				}
			}

			contentOutlinePage = new MyContentOutlinePage();

			// Listen to selection so that we can handle it is a special way.
			//
			contentOutlinePage.addSelectionChangedListener(new ISelectionChangedListener() {
				// This ensures that we handle selections correctly.
				//
				public void selectionChanged(SelectionChangedEvent event) {
					handleContentOutlineSelection(event.getSelection());
				}
			});
		}

		return contentOutlinePage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#setStatusLineManager(org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void setStatusLineManager(ISelection selection) {
		IStatusLineManager statusLineManager = null;
		if (currentViewer != null && currentViewer == contentOutlineViewer) {
			statusLineManager = contentOutlineStatusLineManager;
		} else {
			statusLineManager = getActionBars().getStatusLineManager();
		}

		if (statusLineManager != null) {
			if (selection instanceof IStructuredSelection) {
				Collection<?> collection = ((IStructuredSelection)selection).toList();
				switch (collection.size()) {
					case 0:
						statusLineManager.setMessage(getString("_UI_NoObjectSelected")); //$NON-NLS-1$
						break;
					case 1:
						String text = new AdapterFactoryItemDelegator(adapterFactory).getText(collection
								.iterator().next());
						statusLineManager.setMessage(getString("_UI_SingleObjectSelected", text)); //$NON-NLS-1$
						break;
					default:
						final Iterator<?> it = collection.iterator();
						double percentage = 0;
						long duration = 0;
						long count = 0;
						while (it.hasNext()) {
							final Object select = it.next();
							if (select instanceof ProfileEntry) {
								final ProfileEntry entry = (ProfileEntry)select;
								percentage += entry.getPercentage();
								duration += entry.getDuration();
								count += entry.getCount();
							}
						}
						final NumberFormat format = new DecimalFormat();
						format.setMaximumIntegerDigits(3);
						format.setMaximumFractionDigits(2);
						final String statusString = Integer.toString(collection.size()) + " [" //$NON-NLS-1$
								+ format.format(percentage) + "% / " + duration + "ms / " + count + " times]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						statusLineManager.setMessage(getString("_UI_MultiObjectSelected", statusString)); //$NON-NLS-1$
						break;
				}
			} else {
				statusLineManager.setMessage(""); //$NON-NLS-1$
			}
		}
	}

	/**
	 * This looks up a string in the plugin's plugin.properties file.
	 * 
	 * @param key
	 *            message key in the properties file
	 * @return the message string
	 */
	private static String getString(String key) {
		return ProfilerEditorPlugin.INSTANCE.getString(key);
	}

	/**
	 * This looks up a string in plugin.properties, making a substitution.
	 * 
	 * @param key
	 *            message key in the properties file
	 * @param s1
	 *            the object to substitute
	 * @return the message string
	 */
	private static String getString(String key, Object s1) {
		return EcoreEditorPlugin.INSTANCE.getString(key, new Object[] {s1 });
	}

	/**
	 * A customized content provider which allow filtering parts of the tree, and still display filtered parts
	 * children.
	 */
	private final class ProfileEditorContentProvider extends AdapterFactoryContentProvider {
		private ProfileEditorContentProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		@Override
		public Object[] getChildren(Object object) {
			if (object instanceof ProfileEntry) {
				return internalGetChildren((ProfileEntry)object).toArray();
			}
			return super.getChildren(object);
		}

		/**
		 * Returns the children of the given profile entry. Skips filtered types.
		 * 
		 * @param profileEntry
		 *            the root
		 * @return the children
		 */
		private Collection<ProfileEntry> internalGetChildren(ProfileEntry profileEntry) {
			List<ProfileEntry> children = new ArrayList<ProfileEntry>();
			for (ProfileEntry child : profileEntry.getCallees()) {
				if (isSkipped(child.getMonitored())) {
					children.addAll(internalGetChildren(child));
				} else {
					children.add(child);
				}
			}
			return children;
		}

		/**
		 * Checks whether a profileEntry monitored element needs to be displayed or not.
		 * 
		 * @param object
		 *            the element
		 * @return <true> if the element must not appear in the editor
		 */
		private boolean isSkipped(Object object) {
			return object instanceof TextStatement || object instanceof ExpressionStatement
					|| object instanceof Block;
		}

		@Override
		public Object[] getElements(Object object) {
			return getChildren(object);
		}
	}

	/**
	 * Selection changed listener.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	@SuppressWarnings("synthetic-access")
	private final class ChangementListener implements ISelectionChangedListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
			setSelection(selectionChangedEvent.getSelection());
			if (currentViewer != contentOutlineViewer && contentOutlineViewer != null && !contentOutlineViewer
					.getControl().isDisposed()) {
				if (contentOutlineViewer.getContentProvider() == null) {
					contentOutlineViewer.setContentProvider(new ProfilerOutlineContentProvider(
							adapterFactory));
				}
				contentOutlineViewer.setInput(((TreeSelection)selectionChangedEvent.getSelection())
						.getFirstElement());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#setCurrentViewer(org.eclipse.jface.viewers.Viewer)
	 */
	@SuppressWarnings("synthetic-access")
	@Override
	public void setCurrentViewer(Viewer viewer) {
		// If it is changing...
		//
		if (currentViewer != viewer) {
			if (selectionChangedListener == null) {
				// Create the listener on demand.
				//
				selectionChangedListener = new ChangementListener();
			}

			// Stop listening to the old one.
			//
			if (currentViewer != null) {
				currentViewer.removeSelectionChangedListener(selectionChangedListener);
			}

			// Start listening to the new one.
			//
			if (viewer != null) {
				viewer.addSelectionChangedListener(selectionChangedListener);
			}

			// Remember it.
			//
			currentViewer = viewer;

			// Set the editors selection based on the current viewer's
			// selection.
			//
			if (currentViewer == null) {
				setSelection(StructuredSelection.EMPTY);
			} else {
				setSelection(currentViewer.getSelection());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#showOutlineView()
	 */
	@Override
	protected boolean showOutlineView() {
		return true;
	}

	@Override
	public void init(IEditorSite site, IEditorInput editorInput) {
		super.init(site, editorInput);
		if (editorInput instanceof FileEditorInput) {
			FileEditorInput fip = (FileEditorInput)editorInput;
			IProject project = fip.getFile().getProject();
			acceleoEnvResourceFactory = new AcceleoEnvResourceFactory(project);
			getEditingDomain().getResourceSet().getResourceFactoryRegistry().getProtocolToFactoryMap().put(
					"acceleoenv", acceleoEnvResourceFactory); //$NON-NLS-1$
		}
	}

	private void selectInEditor(EObject monitored) {
		Resource eResource = monitored.eResource();
		if (eResource != null && !eResource.getContents().isEmpty() && eResource.getContents().get(
				0) instanceof Module) {
			org.eclipse.acceleo.Module module = (org.eclipse.acceleo.Module)eResource.getContents().get(0);
			URL sourceURL = acceleoEnvResourceFactory.getEnvironment().getModuleSourceURL(module);
			if (sourceURL != null) {
				try {
					IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(sourceURL
							.toURI());
					if (files.length > 0) {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage();
						if (monitored instanceof ASTNode) {
							ASTNode astNode = (ASTNode)monitored;
							AcceleoAstResult ast = module.getAst();
							int start = ast.getStartPosition(astNode);
							int end = ast.getEndPosition(astNode);
							IEditorPart editor = IDE.openEditor(page, files[0]);
							if (editor instanceof TextEditor) {
								((TextEditor)editor).selectAndReveal(start, end - start);
							}
						}
					}
				} catch (URISyntaxException e) {
					ProfilerEditorPlugin.getPlugin().log(e);
				} catch (PartInitException e) {
					ProfilerEditorPlugin.getPlugin().log(e);
				}
			}
		}
	}

}