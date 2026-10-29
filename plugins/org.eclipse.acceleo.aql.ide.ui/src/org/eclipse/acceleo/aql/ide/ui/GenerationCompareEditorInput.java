/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.contentmergeviewer.IFlushable;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * The generation {@link CompareEditorInput} can be used to merge the {@link PreviewWriterFactory#getPreview()
 * preview}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class GenerationCompareEditorInput extends CompareEditorInput {

	/**
	 * Generated {@link ResourceNode}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static class GeneratedResourceNode extends ResourceNode {

		/**
		 * The preview {@link Map}.
		 */
		private final Map<URI, String> preview;

		/**
		 * Constructor.
		 * 
		 * @param preview
		 *            the preview {@link Map}
		 * @param resource
		 *            the {@link IResource}
		 */
		public GeneratedResourceNode(Map<URI, String> preview, IResource resource) {
			super(resource);
			this.preview = preview;
			final URI uri = URI.createFileURI(resource.getLocation().toString());
			final String contents = preview.get(uri);
			if (contents != null) {
				setContent(contents.getBytes());
			}
		}

		@Override
		public boolean isEditable() {
			return false;
		}

		@Override
		public boolean isReadOnly() {
			return true;
		}

		@Override
		protected IStructureComparator createChild(IResource child) {
			return new GeneratedResourceNode(preview, child);
		}
	}

	/**
	 * A {@link ResourceNode} that change the {@link IFile} contents on commit.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static class CommitResourceNode extends ResourceNode {

		/**
		 * Contents flushed by the viewer, awaiting commit.
		 */
		private byte[] pendingContents;

		/**
		 * Constructor.
		 * 
		 * @param resource
		 *            the {@link IResource}
		 */
		public CommitResourceNode(IResource resource) {
			super(resource);
		}

		@Override
		public void setContent(byte[] contents) {
			pendingContents = contents;
			super.setContent(contents);
		}

		/**
		 * Writes buffered changes to the workspace.
		 *
		 * @param monitor
		 *            the progress monitor
		 * @throws CoreException
		 *             if the file cannot be written
		 */
		public void commit(IProgressMonitor monitor) throws CoreException {
			if (pendingContents != null) {
				final IFile file = (IFile)getResource();
				if (file.exists()) {
					file.setContents(new ByteArrayInputStream(pendingContents), true, true, monitor);
				} else {
					file.create(new ByteArrayInputStream(pendingContents), true, monitor);
				}
				pendingContents = null;
			}
		}
	}

	/**
	 * The mapping from the generated {@link URI} to its contents.
	 */
	private Map<URI, String> preview;

	/**
	 * The target {@link IContainer};
	 */
	private IContainer target;

	/**
	 * Tells if this input has differences.
	 */
	private boolean hasDifferences = false;

	/**
	 * The root {@link DiffNode}.
	 */
	private DiffNode diffRoot;

	/**
	 * Workspace nodes whose buffered changes must be committed.
	 */
	private final Map<IFile, CommitResourceNode> commitNodes = new LinkedHashMap<>();

	/**
	 * The active content viewer, flushed explicitly before committing resources.
	 */
	private Viewer contentViewer;

	public GenerationCompareEditorInput(CompareConfiguration configuration, Map<URI, String> preview,
			IContainer target) {
		super(configuration);
		configuration.setLeftEditable(true);
		final Image acceleoImage = AcceleoUIPlugin.getDefault().getImageRegistry().get(
				AcceleoUIPlugin.ACCELEO_EDITOR);
		configuration.setLeftImage(acceleoImage);
		configuration.setLeftLabel("Acceleo generation");
		configuration.setRightEditable(true);
		final Image folderImage = PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_FOLDER);
		configuration.setRightImage(folderImage);
		configuration.setRightLabel("Workdspace");
		setTitle("Generation comparison");
		this.preview = preview;
		this.target = target;
		computeDiff();
	}

	private void computeDiff() {
		final IProgressMonitor monitor = new NullProgressMonitor();
		final Set<IResource> knownResources = new HashSet<>();
		final Map<IResource, DiffNode> nodes = new LinkedHashMap<>();
		knownResources.add(target);

		for (Entry<URI, String> entry : preview.entrySet()) {
			final URI uri = entry.getKey();
			final String generatedContents = entry.getValue();
			final Path location = new Path(URI.decode(uri.devicePath()));
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(location);
			IResource currentResource = file;
			while (currentResource != null && !knownResources.contains(currentResource)) {
				knownResources.add(currentResource);
				final DiffNode diffNode;
				final ITypedElement left = new GeneratedResourceNode(preview, currentResource);
				final CommitResourceNode right = new CommitResourceNode(currentResource);
				if (currentResource instanceof IContainer) {
					if (currentResource.exists()) {
						diffNode = new DiffNode(Differencer.NO_CHANGE, null, left, right);
					} else {
						diffNode = new DiffNode(Differencer.ADDITION, null, left, right);
					}
					nodes.put(currentResource, diffNode);
					currentResource = currentResource.getParent();
				} else if (currentResource instanceof IFile) {
					if (currentResource.exists()) {
						String fileContent = null;
						try (InputStream inputStream = ((IFile)currentResource).getContents(true)) {
							fileContent = AcceleoUtil.getContent(inputStream, file.getCharset());
						} catch (IOException | CoreException e) {
							AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
									"Can't read the content of " + currentResource.getFullPath() + ".", e));
						}
						if (!generatedContents.equals(fileContent)) {
							diffNode = new DiffNode(Differencer.CHANGE, null, left, right);
							nodes.put(currentResource, diffNode);
							commitNodes.put(file, right);
							hasDifferences = true;
							currentResource = currentResource.getParent();
						} else {
							currentResource = null;
						}
					} else {
						right.setContent(generatedContents.getBytes());
						try {
							right.commit(monitor);
						} catch (CoreException e) {
							AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
									"Can't write the content of " + currentResource.getFullPath() + ".", e));
						}
						currentResource = null;
					}
				} else {
					throw new IllegalStateException("An IResource should be an IContainer or an IFile.");
				}
			}
		}

		final DiffNode res;
		if (hasDifferences) {
			ITypedElement left = new GeneratedResourceNode(preview, target);
			ITypedElement right = new ResourceNode(target);
			res = new DiffNode(Differencer.NO_CHANGE, null, left, right);
			try {
				addChildren(target, res, nodes);
				for (IResource resource : nodes.keySet()) {
					if (resource instanceof IContainer) {
						addChildren((IContainer)resource, nodes.get(resource), nodes);
					}
				}
			} catch (CoreException e) {
				AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
						"Couldn't compare target folder " + target.getFullPath() + ".", e));
			}
		} else {
			res = null;
		}

		diffRoot = res;
	}

	/**
	 * Adds children to the given of the given parent {@link DiffNode}.
	 * 
	 * @param parentResource
	 *            the parent {@link IResource}
	 * @param parentNode
	 *            the parent {@link DiffNode}
	 * @param nodes
	 *            the mapping from {@link IResource} to their {@link DiffNode}
	 * @throws CoreException
	 *             if member resource can't be accessed
	 */
	protected void addChildren(IContainer parentResource, DiffNode parentNode,
			final Map<IResource, DiffNode> nodes) throws CoreException {
		for (IResource child : parentResource.members()) {
			final DiffNode node = nodes.get(child);
			if (node != null) {
				parentNode.add(node);
				node.setAncestor(parentNode);
				node.setParent(parentNode);
			}
		}
	}

	@Override
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		return diffRoot;
	}

	@Override
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input, Composite parent) {
		contentViewer = super.findContentViewer(oldViewer, input, parent);
		return contentViewer;
	}

	@Override
	public void saveChanges(IProgressMonitor monitor) throws CoreException {
		// Flush the active viewer directly rather than relying only on the switching pane's adapter.
		if (contentViewer != null && !contentViewer.getControl().isDisposed()) {
			final IFlushable flushable = Adapters.adapt(contentViewer, IFlushable.class);
			if (flushable != null) {
				flushable.flush(monitor);
			}
		}
		super.saveChanges(monitor);
		// Keep the input dirty until persistence succeeds, including when the viewers are already clean.
		setDirty(isSaveNeeded());
		ResourcesPlugin.getWorkspace().run(progress -> {
			for (CommitResourceNode node : commitNodes.values()) {
				node.commit(progress);
			}
		}, monitor);
		setDirty(false);
	}

	@Override
	public boolean isSaveNeeded() {
		return super.isSaveNeeded() || commitNodes.values().stream().anyMatch(
				node -> node.pendingContents != null);
	}

	@Override
	protected void handleDispose() {
		contentViewer = null;
		super.handleDispose();
	}

	/**
	 * Tells if this input has differences.
	 * 
	 * @return <code>true</code> if this input has differences, <code>false</code> otherwise
	 */
	public boolean hasDifferences() {
		return hasDifferences;
	}

}
