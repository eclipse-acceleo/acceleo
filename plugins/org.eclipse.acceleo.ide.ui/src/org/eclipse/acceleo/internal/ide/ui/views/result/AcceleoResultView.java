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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;
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
	 * The identifier of the result active region marker. It is often the text range synchronized with an
	 * EObject or a template element.
	 */
	private static final String RESULT_ACTIVE_REGION_MARKER_ID = "org.eclipse.acceleo.ide.ui.activeRegion"; //$NON-NLS-1$

	/**
	 * The result view content.
	 */
	private AcceleoResultContent content;

	/**
	 * The resource change listener to detect that the view must be refreshed.
	 */
	private IResourceChangeListener resourceChangeListener;

	/**
	 * The selection listener to synchronize the selection in the view.
	 */
	private ISelectionListener selectionListener;

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
			}
			return super.getElements(element);
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
			}

			String result;
			if (element instanceof String) {
				result = (String)element;
			} else if (element instanceof TraceabilityTemplate) {
				result = ((TraceabilityTemplate)element).getLabel();
			} else if (element instanceof TraceabilityModel) {
				result = ((TraceabilityModel)element).getLabel();
			} else if (element instanceof TraceabilityRegion) {
				result = ((TraceabilityRegion)element).getLabel();
			} else {
				result = super.getText(element);
			}
			return result;
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
				} else if (element instanceof TraceabilityTemplate) {
					image = AcceleoUIActivator
							.getDefault()
							.getImage(
									"icons/template-editor/" + ((TraceabilityTemplate)element).getEObject().eClass().getName() + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$
					if (image == null) {
						image = getImage(((TraceabilityTemplate)element).getTemplateElement());
					}
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
			AcceleoService.removeStaticListener(content);
		}
		content = new AcceleoResultContent((ILabelProvider)getTreeViewer().getLabelProvider());
		AcceleoService.addStaticListener(content);
		if (resourceChangeListener == null) {
			resourceChangeListener = new IResourceChangeListener() {
				public void resourceChanged(IResourceChangeEvent event) {
					refresh();
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
		}
		if (getSite().getPage() != null && selectionListener == null) {
			selectionListener = new ISelectionListener() {
				public void selectionChanged(IWorkbenchPart part, ISelection selection) {
					if (getContent() != null && selection instanceof TextSelection
							&& ((TextSelection)selection).getOffset() >= 0 && getSite().getPage() != null) {
						IEditorPart editor = getSite().getPage().getActiveEditor();
						IFile file;
						if (editor != null && editor.getEditorInput() != null) {
							file = (IFile)editor.getEditorInput().getAdapter(IFile.class);
						} else {
							file = null;
						}
						// Best user experience : We propagate the current selection in the tree viewer only
						// if the text length is strictly greater than 0
						if (file != null && ((TextSelection)selection).getLength() > 0) {
							int offset = ((TextSelection)selection).getOffset()
									+ ((TextSelection)selection).getLength() - 1;
							expandElementsAt(file, offset);
						}
					}
				}
			};
			getSite().getPage().addPostSelectionListener(selectionListener);
		}
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
				// if eclipse is closing the tree can be disposed
				final TreeViewer treeViewer = getViewer();
				final Status status = new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
				if (treeViewer == null || treeViewer.getTree() == null || treeViewer.getTree().isDisposed()) {
					return status;
				}

				treeViewer.refresh();
				return status;
			}
		};
		refreshUIJob.setPriority(Job.DECORATE);
		refreshUIJob.setSystem(true);
		refreshUIJob.setProgressGroup(new NullProgressMonitor(), 1);
		final int schedule = 2000;
		refreshUIJob.schedule(schedule);
		refreshUIJob = null;
	}

	/**
	 * Expands the traceability element at the given offset, in the traceability information of the given
	 * file. It expands the nearest template element and all its ancestors in the view.
	 * <p>
	 * Here is an example of expanded elements, in order :
	 * </p>
	 * <p>
	 * {IWorspaceRoot, IContainer, IFile, TraceabilityTargetFile, TraceabilityModel, ...,
	 * TraceabilityTemplate}
	 * </p>
	 * 
	 * @param generatedFile
	 *            is the generated file where to search the offset
	 * @param offset
	 *            to search in the sub regions
	 */
	private void expandElementsAt(IFile generatedFile, int offset) {
		List<Object> elements = getElementsAt(getContent().getTargetFile(
				generatedFile.getLocation().toString()), offset);
		if (elements.size() > 0) {
			Object last = elements.get(elements.size() - 1);
			TreeViewer treeViewer = getTreeViewer();
			treeViewer.setExpandedElements(elements.toArray());
			treeViewer.setSelection(new StructuredSelection(elements), true);
			treeViewer.setSelection(new StructuredSelection(last), true);
			Widget item = treeViewer.testFindItem(last);
			if (item instanceof TreeItem) {
				((TreeItem)item).setExpanded(false);
			}
		}
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
			AcceleoService.removeStaticListener(content);
			content = null;
		}
		if (resourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
			resourceChangeListener = null;
		}
		if (selectionListener != null && getSite().getPage() != null) {
			getSite().getPage().removePostSelectionListener(selectionListener);
			selectionListener = null;
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
				&& ((TreeSelection)event.getSelection()).getFirstElement() instanceof AbstractTraceabilityElement) {
			AbstractTraceabilityElement element = (AbstractTraceabilityElement)((TreeSelection)event
					.getSelection()).getFirstElement();
			AbstractTraceabilityElement fileElement = element;
			while (fileElement != null && !(fileElement instanceof TraceabilityTargetFile)) {
				fileElement = fileElement.getParent();
			}
			IEditorPart part = null;
			if (fileElement instanceof TraceabilityTargetFile) {
				IFile file = ((TraceabilityTargetFile)fileElement).getTargetFile();
				if (file != null && file.isAccessible()) {
					try {
						file.deleteMarkers(RESULT_ACTIVE_REGION_MARKER_ID, true, 1);
						reportActiveRegions(file, FileContent.getFileContent(file.getLocation().toFile()),
								element);
						part = IDE.openEditor(getSite().getPage(), file);
					} catch (PartInitException e) {
						AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
					} catch (CoreException e) {
						AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
					}
				}
			}
			if (part instanceof ITextEditor) {
				ITextEditor editor = (ITextEditor)part;
				if (element instanceof TraceabilityRegion) {
					TraceabilityRegion region = (TraceabilityRegion)element;
					editor.setHighlightRange(region.getTargetFileOffset(), region.getTargetFileLength(), true);
				} else if (element instanceof TraceabilityTemplate) {
					int b = getMin((TraceabilityTemplate)element);
					int e = getMax((TraceabilityTemplate)element);
					editor.setHighlightRange(b, e - b, true);
				} else if (element instanceof TraceabilityModel) {
					int b = getMin((TraceabilityModel)element);
					int e = getMax((TraceabilityModel)element);
					editor.setHighlightRange(b, e - b, true);
				}
			}
		} else {
			super.handleDoubleClick(event);
		}
	}

	/**
	 * Adds all the active region markers on the generated file. It means all the regions that have been
	 * synchronized with the given traceability element.
	 * 
	 * @param file
	 *            is the file (it is supposed to be a generated file)
	 * @param buffer
	 *            is the content of the file, it is used to compute the line number of the offset
	 * @param element
	 *            is the traceability element
	 * @throws CoreException
	 *             when an issue occurs when creating the markers
	 */
	private void reportActiveRegions(IFile file, StringBuffer buffer, AbstractTraceabilityElement element)
			throws CoreException {
		if (element instanceof TraceabilityModel) {
			TraceabilityModel model = (TraceabilityModel)element;
			if (model instanceof TraceabilityTemplate || !hasDirectlyActiveRegion(model)) {
				for (TraceabilityRegion region : model.getRegions()) {
					reportActiveRegions(file, buffer, region);
				}
				for (TraceabilityModel child : model.getChildren()) {
					reportActiveRegions(file, buffer, child);
				}
			} else {
				for (TraceabilityRegion region : model.getRegions()) {
					reportActiveRegions(file, buffer, region);
				}
				for (TraceabilityModel child : model.getChildren()) {
					if (child instanceof TraceabilityTemplate) {
						reportActiveRegions(file, buffer, child);
					}
				}
			}
		} else if (element instanceof TraceabilityContainer) {
			for (TraceabilityModel child : ((TraceabilityContainer)element).getChildren()) {
				reportActiveRegions(file, buffer, child);
			}
		} else if (element instanceof TraceabilityRegion) {
			TraceabilityRegion region = (TraceabilityRegion)element;
			reportActiveRegion(file, buffer, region);
		}
	}

	/**
	 * Indicates if the given traceability model element is directly synchronized with a region of the
	 * generated text. It is true when it contains a region or a template element in its children (at the
	 * first level only).
	 * 
	 * @param model
	 *            is the element to test
	 * @return true if the given traceability model element is directly synchronized with a text region
	 */
	private boolean hasDirectlyActiveRegion(TraceabilityModel model) {
		boolean hasActiveRegion = model.getRegions().size() > 0;
		if (!hasActiveRegion) {
			for (TraceabilityModel child : model.getChildren()) {
				if (child instanceof TraceabilityTemplate) {
					hasActiveRegion = true;
					break;
				}
			}
		}
		return hasActiveRegion;
	}

	/**
	 * Adds an active region marker on the generated file.
	 * 
	 * @param file
	 *            is the file (it is supposed to be a generated file)
	 * @param buffer
	 *            is the content of the file, it is used to compute the line number of the offset
	 * @param region
	 *            is the traceability region with a beginning offset and an ending offset
	 * @throws CoreException
	 *             when an issue occurs when creating a marker
	 */
	private void reportActiveRegion(IFile file, StringBuffer buffer, TraceabilityRegion region)
			throws CoreException {
		Map<String, Object> map = new HashMap<String, Object>();
		String objectToString = ""; //$NON-NLS-1$
		String featureToString = ""; //$NON-NLS-1$
		String templateToString = ""; //$NON-NLS-1$
		AbstractTraceabilityElement current = region.getParent();
		while (current != null) {
			if (featureToString.length() == 0 && current instanceof TraceabilityTemplate
					&& ((TraceabilityTemplate)current).getTemplateElement() instanceof ModuleElement) {
				featureToString = current.toString();
			} else if (templateToString.length() == 0 && current instanceof TraceabilityTemplate
					&& ((TraceabilityTemplate)current).getTemplateElement() instanceof Module) {
				templateToString = current.toString();
			} else if (objectToString.length() == 0 && current instanceof TraceabilityModel
					&& !(current instanceof TraceabilityTemplate)) {
				objectToString = current.toString();
			}
			current = current.getParent();
		}
		map.put(IMarker.MESSAGE, AcceleoUIMessages.getString("AcceleoResultView.ActiveRegionMarkerMessage", //$NON-NLS-1$
				new Object[] {objectToString, featureToString, templateToString, }));
		map.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_INFO));
		map.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_NORMAL));
		int begin = region.getTargetFileOffset();
		int end = begin + region.getTargetFileLength();
		int line = FileContent.lineNumber(buffer, begin);
		map.put(IMarker.CHAR_START, Integer.valueOf(begin));
		map.put(IMarker.CHAR_END, Integer.valueOf(end));
		map.put(IMarker.LINE_NUMBER, Integer.valueOf(line));
		MarkerUtilities.createMarker(file, map, RESULT_ACTIVE_REGION_MARKER_ID);
	}

	/**
	 * Gets the minimum position of the given element in the text.
	 * 
	 * @param model
	 *            is the element to search
	 * @return the lower index
	 */
	private int getMin(TraceabilityModel model) {
		int min = 0;
		if (model instanceof TraceabilityTemplate || !hasDirectlyActiveRegion(model)) {
			for (TraceabilityRegion region : model.getRegions()) {
				int b = region.getTargetFileOffset();
				if (b > -1 && (b < min || min == 0)) {
					min = b;
				}
			}
			for (TraceabilityModel child : model.getChildren()) {
				int b = getMin(child);
				if (b > -1 && (b < min || min == 0)) {
					min = b;
				}
			}
		} else {
			for (TraceabilityRegion region : model.getRegions()) {
				int b = region.getTargetFileOffset();
				if (b > -1 && (b < min || min == 0)) {
					min = b;
				}
			}
			for (TraceabilityModel child : model.getChildren()) {
				if (child instanceof TraceabilityTemplate) {
					int b = getMin(child);
					if (b > -1 && (b < min || min == 0)) {
						min = b;
					}
				}
			}
		}
		return min;
	}

	/**
	 * Gets the maximum position of the given element in the text.
	 * 
	 * @param model
	 *            is the element to search
	 * @return the upper index
	 */
	private int getMax(TraceabilityModel model) {
		int max = 0;
		if (model instanceof TraceabilityTemplate || !hasDirectlyActiveRegion(model)) {
			for (TraceabilityRegion region : model.getRegions()) {
				int b = region.getTargetFileOffset();
				int e = b + region.getTargetFileLength();
				if (e > max) {
					max = e;
				}
			}
			for (TraceabilityModel child : model.getChildren()) {
				int e = getMax(child);
				if (e > max) {
					max = e;
				}
			}
		} else {
			for (TraceabilityRegion region : model.getRegions()) {
				int b = region.getTargetFileOffset();
				int e = b + region.getTargetFileLength();
				if (e > max) {
					max = e;
				}
			}
			for (TraceabilityModel child : model.getChildren()) {
				if (child instanceof TraceabilityTemplate) {
					int e = getMax(child);
					if (e > max) {
						max = e;
					}
				}
			}
		}
		return max;
	}

	/**
	 * Gets the traceability element at the given offset, in the descendants of the given traceability
	 * container. It returns the nearest template element and all its ancestors in the view.
	 * <p>
	 * Here is an example of result :
	 * </p>
	 * <p>
	 * {IWorspaceRoot, IContainer, IFile, TraceabilityTargetFile, TraceabilityModel, ...,
	 * TraceabilityTemplate}
	 * </p>
	 * 
	 * @param current
	 *            is the root element where to search the offset
	 * @param offset
	 *            to search in the sub regions
	 * @return the nearest template element and all its ancestors in the view
	 */
	private List<Object> getElementsAt(TraceabilityContainer current, int offset) {
		List<Object> result = new ArrayList<Object>();
		if (current != null) {
			if (current instanceof TraceabilityModel) {
				for (TraceabilityRegion region : ((TraceabilityModel)current).getRegions()) {
					int b = region.getTargetFileOffset();
					int e = b + region.getTargetFileLength();
					if (offset >= b && offset < e) {
						result.add(current);
						computeViewAncestors(current, result);
						break;
					}
				}
			}
			if (result.size() == 0) {
				for (TraceabilityModel child : current.getChildren()) {
					result = getElementsAt(child, offset);
					if (result.size() != 0) {
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Computes the result view ancestors of the given traceability element.
	 * 
	 * @param current
	 *            is the current element to reveal in the view, the method will compute its ancestors
	 * @param result
	 *            is the ancestors list, it's an input/output parameter
	 */
	private void computeViewAncestors(TraceabilityContainer current, List<Object> result) {
		AbstractTraceabilityElement parent = current.getParent();
		while (parent != null) {
			result.add(0, parent);
			if (parent instanceof TraceabilityTargetFile) {
				break;
			}
			parent = parent.getParent();
		}
		if (parent instanceof TraceabilityTargetFile) {
			IFile targetFile = ((TraceabilityTargetFile)parent).getTargetFile();
			if (targetFile != null) {
				result.add(0, targetFile);
				IContainer container = targetFile.getParent();
				while (container != null) {
					result.add(0, container);
					container = container.getParent();
				}
			}
		}
	}

}
