/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl.internal.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.acceleo.ui.interpreter.InterpreterPlugin;
import org.eclipse.acceleo.ui.interpreter.completeocl.internal.CompleteOCLInterpreterActivator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.edit.ui.action.LoadResourceAction.LoadResourceDialog;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog can be used to import a Complete OCL resource's content within the expression section of the
 * interpreter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ImportCompleteOCLResourceDialog extends LoadResourceDialog {
	/** Extensions of the files we allow users to import. */
	protected static final String[] FILE_EXTENSIONS = new String[] {"ocl", }; //$NON-NLS-1$

	/** The viewer into which we'll import the content of the selected resource. */
	private TextViewer viewer;

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            The parent shell.
	 * @param viewer
	 *            The viewer into which we need to import a Complete OCL resource content.
	 */
	public ImportCompleteOCLResourceDialog(Shell parent, TextViewer viewer) {
		super(parent);
		this.viewer = viewer;
		int shellStyle = getShellStyle();
		int newShellStyle = shellStyle & ~(SWT.MULTI);
		setShellStyle(newShellStyle);
	}

	/**
	 * Overridden in order to filter the proposed files according to {@link #FILE_EXTENSIONS}.
	 * 
	 * @see org.eclipse.emf.common.ui.dialogs.ResourceDialog.prepareBrowseFileSystemButton(Button)
	 */
	@Override
	protected void prepareBrowseFileSystemButton(Button browseFileSystemButton) {
		// Prepend all extensions with "*." to respect format.
		final String[] extensionsFilter = new String[FILE_EXTENSIONS.length];
		for (int i = 0; i < FILE_EXTENSIONS.length; i++) {
			extensionsFilter[i] = "*." + FILE_EXTENSIONS[i]; //$NON-NLS-1$
		}
		browseFileSystemButton.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fileDialog = new FileDialog(getShell(), style);
				fileDialog.setFilterExtensions(extensionsFilter);
				fileDialog.open();

				String filterPath = fileDialog.getFilterPath();
				String fileName = fileDialog.getFileName();
				if (fileName != null) {
					uriField.setText(URI.createFileURI(filterPath + File.separator + fileName).toString());
				}
			}
		});
	}

	/**
	 * Overridden in order to filter the proposed files according to {@link #FILE_EXTENSIONS}.
	 * 
	 * @see org.eclipse.emf.common.ui.dialogs.ResourceDialog.prepareBrowseWorkspaceButton(Button)
	 */
	@Override
	protected void prepareBrowseWorkspaceButton(Button browseWorkspaceButton) {
		browseWorkspaceButton.addSelectionListener(new BrowseWorkspaceSelectionListener());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.ui.dialogs.ResourceDialog.processResources()
	 */
	@Override
	protected boolean processResources() {
		final List<URI> uris = getURIs();
		if (!uris.isEmpty()) {
			// We only accept single resource selection
			copyContentToViewer(uris.get(0));
		}

		return true;
	}

	/**
	 * Load the content of the file denoted by the given URI and copy it into the {@link #viewer}'s document.
	 * <p>
	 * <b>Note</b> that this will not check if the file is a valid Complete OCL resource. We made sure that it
	 * had the "ocl" extension, but we won't do any more verification than that. This will simply make a
	 * verbatim copy of the file content into the target viewer.
	 * </p>
	 * 
	 * @param uri
	 *            The selected uri.
	 */
	private void copyContentToViewer(URI uri) {
		BufferedReader reader = null;
		try {
			InputStream fileContent = getFileContent(uri);
			reader = new BufferedReader(new InputStreamReader(fileContent));

			StringBuffer stringContent = new StringBuffer();
			String line = reader.readLine();
			while (line != null) {
				stringContent.append(line);
				stringContent.append('\n');
				line = reader.readLine();
			}

			viewer.getDocument().set(stringContent.toString());
		} catch (IOException e) {
			final IStatus status = new Status(IStatus.ERROR, CompleteOCLInterpreterActivator.PLUGIN_ID,
					e.getMessage(), e);
			InterpreterPlugin.getDefault().getLog().log(status);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// Should have been caught by the outer try
				}
			}
		}
	}

	/**
	 * Tries and retrieve the content of the file denoted by the given URI.
	 * 
	 * @param uri
	 *            The uri which target file we need to load.
	 * @return An open input stream towards the file at the given URI. The caller will need to close that
	 *         input stream when it's done with it.
	 * @throws IOException
	 *             Thrown if we cannot resolve the URI or load the target file.
	 */
	private InputStream getFileContent(URI uri) throws IOException {
		final URIConverter converter = new ExtensibleURIConverterImpl();
		final URIHandler handler = converter.getURIHandler(uri);
		if (handler != null) {
			return handler.createInputStream(uri, new HashMap<String, Object>());
		}
		throw new IOException("Could not load " + uri); //$NON-NLS-1$
	}

	/**
	 * Mostly copied from the anonymous implementation in
	 * org.eclipse.emf.common.ui.dialogs.ResourceDialog.prepareBrowseWorkspaceButton(Button). This only adds a
	 * viewer filter to the opened dialog.
	 */
	// Copied class. Suppress all warning since we remain as close as possible to original.
	@SuppressWarnings("all")
	protected class BrowseWorkspaceSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent event) {
			final ViewerFilter extensionFilter = new ViewerFilter() {
				@Override
				public boolean select(Viewer filteredViewer, Object parentElement, Object element) {
					if (element instanceof IFile) {
						return Arrays.asList(FILE_EXTENSIONS).contains(((IFile)element).getFileExtension());
					}
					return true;
				}
			};
			IFile file = null;

			if (isSave()) {
				String path = getContextPath();
				file = WorkspaceResourceDialog.openNewFile(getShell(), null, null, path != null ? new Path(
						path) : null, Arrays.asList(extensionFilter));
			} else {
				IFile[] files = WorkspaceResourceDialog.openFileSelection(getShell(), null, null, false,
						getContextSelection(), Arrays.asList(extensionFilter));
				if (files.length != 0) {
					file = files[0];
				}
			}

			if (file != null) {
				uriField.setText(URI.createPlatformResourceURI(file.getFullPath().toString(), true)
						.toString());
			}
		}

		private String getContextPath() {
			return context != null && context.isPlatformResource() ? URI.createURI(".").resolve(context) //$NON-NLS-1$
					.path().substring(9) : null;
		}

		private Object[] getContextSelection() {
			String path = getContextPath();
			if (path != null) {
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource resource = root.findMember(path);
				if (resource != null && resource.isAccessible()) {
					return new Object[] {resource, };
				}
			}
			return null;
		}
	}
}
