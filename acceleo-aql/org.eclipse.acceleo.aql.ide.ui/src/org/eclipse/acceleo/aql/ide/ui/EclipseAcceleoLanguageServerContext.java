/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.ide.Activator;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServerContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.lsp4e.LSPEclipseUtils;

/**
 * Eclipse-specific implementation of {@link AcceleoLanguageServerContext}.
 * 
 * @author Florent Latombe
 */
public class EclipseAcceleoLanguageServerContext implements AcceleoLanguageServerContext {

	/**
	 * The size of the buffer we use to read {@link IFile Acceleo documents}.
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * The {@link Path} to the workspace of the runtime Eclipse instance that hosts the
	 * {@link AcceleoLanguageServer}.
	 */
	private final Path pathToWorkspace;

	/**
	 * The constructor.
	 * 
	 * @param pathToWorkspace
	 *            the (non-{@code null}) path to the workspace of the runtime Eclipse instance that hosts the
	 *            {@link AcceleoLanguageServer}.
	 */
	public EclipseAcceleoLanguageServerContext(Path pathToWorkspace) {
		this.pathToWorkspace = Objects.requireNonNull(pathToWorkspace);
	}

	@Override
	public IAcceleoEnvironment createAcceleoEnvironmentFor(URI acceleoDocumentUri) {
		final IAcceleoEnvironment acceleoEnvironment = new AcceleoEnvironment(new DefaultGenerationStrategy(),
				org.eclipse.emf.common.util.URI.createURI(pathToWorkspace.toString()));
		final IProject project = LSPEclipseUtils.findResourceFor(acceleoDocumentUri.toString()).getProject();
		acceleoEnvironment.setModuleResolver(Activator.getPlugin().createQualifiedNameResolver(
				acceleoEnvironment.getQueryEnvironment(), project));
		return acceleoEnvironment;
	}

	@Override
	public Map<URI, String> getAllAcceleoDocumentsIn(String folderUri) {
		final Map<URI, String> acceleoDocumentsMap = new LinkedHashMap<>();

		IResource workspaceFolder = LSPEclipseUtils.findResourceFor(folderUri);
		try {
			EclipseAcceleoFileFinder acceleoFileFinder = new EclipseAcceleoFileFinder();
			workspaceFolder.accept(acceleoFileFinder);
			for (IFile acceleoDocument : acceleoFileFinder.getAcceleoDocuments()) {
				URI acceleoDocumentUri = acceleoDocument.getLocationURI().normalize();
				String acceleoDocumentContents = readWorkspaceFile(acceleoDocument);
				acceleoDocumentsMap.put(acceleoDocumentUri, acceleoDocumentContents);
			}
		} catch (CoreException coreException) {
			throw new RuntimeException("There was an issue while exploring the files of workspace folder "
					+ workspaceFolder.getLocation().toString(), coreException);
		}

		return acceleoDocumentsMap;
	}

	/**
	 * Reads the whole contents of an {@link IFile} as a {@link String} while conserving the line separators
	 * of the file.
	 * 
	 * @param workspaceFileToRead
	 *            the (non-{@code null}) {@link IFile} to read.
	 * @return the {@link String} of its contents.
	 * @throws CoreException
	 *             if there was error while reading the contents of {@code workspaceFileToRead}.
	 */
	private static String readWorkspaceFile(IFile workspaceFileToRead) throws CoreException {
		InputStream inputStream = workspaceFileToRead.getContents();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int length;
		try {
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString(workspaceFileToRead.getCharset());
		} catch (IOException ioException) {
			throw new RuntimeException("There was an issue while reading the contents of file "
					+ workspaceFileToRead.getLocation().toString(), ioException);
		}
	}

}
