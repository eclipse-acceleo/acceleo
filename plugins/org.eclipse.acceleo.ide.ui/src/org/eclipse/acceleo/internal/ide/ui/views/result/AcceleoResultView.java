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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.navigator.ResourceNavigator;

/**
 * This view shows the files obtained from the last code generation. The code generation creates a model on
 * the fly with the traceability information. The view is connected to the model using a content provider. The
 * view uses a label provider to define how model objects should be presented in the view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoResultView extends ResourceNavigator {

	/**
	 * The result view content.
	 */
	private AcceleoResultContent content;

	/**
	 * The resource change listener to detect that the view must be refreshed.
	 */
	private IResourceChangeListener resourceChangeListener;

	/**
	 * The background job to refresh the view.
	 */
	private UIJob refreshUIJob;

	/**
	 * The resource filter.
	 */
	private AcceleoResultFilesFilter previewFilesFilter = new AcceleoResultFilesFilter(this);

	/**
	 * A content provider which combines the generated workspace resources and the EMF objects positions in
	 * the text.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private class AcceleoPreviewContentProvider extends WorkbenchContentProvider {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getElements(java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object element) {
			if (element instanceof String) {
				return new Object[] {(String)element };
			} else {
				return super.getElements(element);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object element) {
			Object[] result;
			if (element instanceof IFile) {
				IPath path = ((IFile)element).getFullPath();
				List<TraceabilityContainer> children = new ArrayList<TraceabilityContainer>();
				if (content != null) {
					for (TraceabilityTargetFile targetFile : content.getTargetFiles()) {
						if (path.isPrefixOf(targetFile.getTargetFileFullPath())) {
							children.addAll(targetFile.getChildren());
						}
					}
				}
				result = children.toArray();
			} else if (element instanceof TraceabilityModel) {
				List<Object> children = new ArrayList<Object>();
				children.addAll(((TraceabilityModel)element).getChildren());
				children.addAll(((TraceabilityModel)element).getRegions());
				result = children.toArray();
			} else if (element instanceof TraceabilityRegion) {
				result = new Object[] {};
			} else {
				result = super.getChildren(element);
			}
			return result;
		}

	}

	/**
	 * A label provider which combines workspace resources and EMF objects.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private class AcceleoPreviewLabelProvider extends DecoratingLabelProvider {

		/**
		 * The workspace resources label provider.
		 */
		private WorkbenchLabelProvider resourcesLabelProvider;

		/**
		 * Constructor.
		 */
		public AcceleoPreviewLabelProvider() {
			super(new AdapterFactoryLabelProvider(createAdapterFactory()), getPlugin().getWorkbench()
					.getDecoratorManager().getLabelDecorator());
			resourcesLabelProvider = new WorkbenchLabelProvider();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.DecoratingLabelProvider#dispose()
		 */
		@Override
		public void dispose() {
			super.dispose();
			resourcesLabelProvider.dispose();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.DecoratingLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			String text = resourcesLabelProvider.getText(element);
			if (text != null && text.length() > 0) {
				return text;
			} else {
				String result;
				if (element instanceof String) {
					result = (String)element;
				} else if (element instanceof TraceabilityModel) {
					result = getText(((TraceabilityModel)element).getEObject());
				} else if (element instanceof TraceabilityRegion) {
					result = ((TraceabilityRegion)element).toString();
				} else {
					result = super.getText(element);
				}
				return result;
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.DecoratingLabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			Image image = resourcesLabelProvider.getImage(element);
			if (image == null) {
				if (element instanceof String) {
					image = AcceleoUIActivator.getDefault().getImage("icons/AcceleoPreview.gif"); //$NON-NLS-1$
				} else if (element instanceof TraceabilityModel) {
					image = getImage(((TraceabilityModel)element).getEObject());
				} else if (element instanceof TraceabilityRegion) {
					image = AcceleoUIActivator.getDefault().getImage("icons/AcceleoRegion.gif"); //$NON-NLS-1$
				} else {
					image = super.getImage(element);
				}
			}
			return image;
		}
	}

	public AcceleoResultContent getContent() {
		return content;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.navigator.ResourceNavigator#initContentProvider(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	protected void initContentProvider(TreeViewer viewer) {
		viewer.setContentProvider(new AcceleoPreviewContentProvider());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.navigator.ResourceNavigator#initLabelProvider(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	protected void initLabelProvider(TreeViewer viewer) {
		viewer.setLabelProvider(new AcceleoPreviewLabelProvider());
	}

	/**
	 * Returns a factory built with all the {@link AdapterFactory} instances available in the global registry.
	 * 
	 * @return a factory built with all the {@link AdapterFactory} instances available in the global registry.
	 */
	private AdapterFactory createAdapterFactory() {
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		return new ComposedAdapterFactory(factories);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.navigator.ResourceNavigator#initFilters(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	protected void initFilters(TreeViewer viewer) {
		viewer.addFilter(previewFilesFilter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.navigator.ResourceNavigator#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		if (content != null) {
			AcceleoService.removeListener(content);
		}
		content = new AcceleoResultContent();
		AcceleoService.addListener(content);
		resourceChangeListener = new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				refresh();
			}
		};
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	/**
	 * The background job is scheduled to refresh the view.
	 */
	private void refresh() {
		if (refreshUIJob != null) {
			refreshUIJob.cancel();
		}
		refreshUIJob = new UIJob("Acceleo") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				getViewer().refresh();
				return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
			}
		};
		refreshUIJob.setPriority(Job.DECORATE);
		refreshUIJob.setSystem(true);
		final int schedule = 2000;
		refreshUIJob.schedule(schedule);
		refreshUIJob = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.navigator.ResourceNavigator#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (content != null) {
			AcceleoService.removeListener(content);
			content = null;
		}
		if (resourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
			resourceChangeListener = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.navigator.ResourceNavigator#handleDoubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	@Override
	protected void handleDoubleClick(DoubleClickEvent event) {
		if (event.getSelection() instanceof TreeSelection
				&& ((TreeSelection)event.getSelection()).getFirstElement() instanceof TraceabilityElement) {
			TraceabilityElement element = (TraceabilityElement)((TreeSelection)event.getSelection())
					.getFirstElement();
			TraceabilityElement fileElement = element;
			while (fileElement != null && !(fileElement instanceof TraceabilityTargetFile)) {
				fileElement = fileElement.getParent();
			}
			IEditorPart part = null;
			if (fileElement instanceof TraceabilityTargetFile) {
				IFile file = ((TraceabilityTargetFile)fileElement).getTargetFile();
				try {
					part = IDE.openEditor(getSite().getPage(), file);
				} catch (PartInitException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
			if (part instanceof ITextEditor) {
				ITextEditor editor = (ITextEditor)part;
				if (element instanceof TraceabilityRegion) {
					TraceabilityRegion region = (TraceabilityRegion)element;
					editor.selectAndReveal(region.getTargetFileOffset(), region.getTargetFileLength());
				} else if (element instanceof TraceabilityModel
						&& ((TraceabilityModel)element).getRegions().size() > 0) {
					TraceabilityRegion first = ((TraceabilityModel)element).getRegions().get(0);
					TraceabilityRegion last = ((TraceabilityModel)element).getRegions().get(
							((TraceabilityModel)element).getRegions().size() - 1);
					int b = first.getTargetFileOffset();
					int e = last.getTargetFileOffset() + last.getTargetFileLength();
					editor.selectAndReveal(b, e - b);
				}
			}
		} else {
			super.handleDoubleClick(event);
		}
	}

}
