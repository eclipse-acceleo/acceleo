/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.generation.strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;
import org.eclipse.acceleo.engine.generation.writers.AcceleoWorkspaceFileWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;

/**
 * This generation strategy can be used to generate files while being aware of those present in the workspace.
 * It will create ask the workspace if these files can be created/overriden beforehand. This is the most
 * memory-expansive strategy; however it can be used with strict VCSs like clearcase that require this
 * workspace check.
 * <p>
 * <b>Note</b> That this <u>cannot</u> be used in standalone mode.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class WorkspaceAwareStrategy extends AbstractGenerationStrategy {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean)
	 */
	@Override
	public AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags) throws IOException {
		return createWriterFor(file, previous, appendMode, hasJMergeTags, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean,
	 *      java.lang.String)
	 */
	@Override
	public AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags, String charset) throws IOException {
		final AbstractAcceleoWriter writer;
		boolean fileExisted = file.exists();

		if (charset != null) {
			if (Charset.isSupported(charset)) {
				writer = new AcceleoWorkspaceFileWriter(file, appendMode, hasJMergeTags, charset);
			} else {
				final String message = AcceleoEngineMessages.getString(
						"AcceleoGenerationStrategy.UnsupportedCharset", charset); //$NON-NLS-1$
				AcceleoEnginePlugin.log(message, false);
				writer = new AcceleoWorkspaceFileWriter(file, appendMode, hasJMergeTags);
			}
		} else {
			writer = new AcceleoWorkspaceFileWriter(file, appendMode, hasJMergeTags);
		}
		if (appendMode && fileExisted) {
			writer.append(LINE_SEPARATOR);
		}
		return writer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#flushWriters(java.util.Map)
	 */
	@Override
	public void flushWriters(Map<String, Writer> preview) throws IOException {
		// All files that existed, yet will change, will be contained here
		final Map<IFile, Writer> needsValidation = new HashMap<IFile, Writer>();

		for (Map.Entry<String, Writer> entry : preview.entrySet()) {
			final File targetFile = new File(entry.getKey());
			final IFile workspaceFile = AcceleoWorkspaceUtil.INSTANCE.getWorkspaceFile(targetFile);
			if (!targetFile.getParentFile().exists()) {
				if (!targetFile.getParentFile().mkdirs()) {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							"AcceleoEvaluationContext.FolderCreationError", targetFile.getParentFile())); //$NON-NLS-1$
				}
			}
			/*
			 * If the target is not in the workspace, or if it did not exist, then close the writer :
			 * AcceleoWorkspaceFileWriters will create their target on closing.
			 */
			if (workspaceFile == null || !targetFile.exists() || !targetFile.canRead()) {
				// File is outside of the workspace and can be saved as is
				entry.getValue().close();
			} else if (((AcceleoWorkspaceFileWriter)entry.getValue()).hasChanged()) {
				needsValidation.put(workspaceFile, entry.getValue());
			} else {
				/*
				 * The file exists in the workspace, yet it didn't change with this generation. Simply ignore
				 * it.
				 */
			}
		}

		final IFile[] validateFiles = needsValidation.keySet().toArray(new IFile[needsValidation.size()]);
		final IStatus validationStatus = ResourcesPlugin.getWorkspace().validateEdit(validateFiles,
				IWorkspace.VALIDATE_PROMPT);
		if (validationStatus.isOK()) {
			for (Writer writer : needsValidation.values()) {
				writer.close();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createLostFiles(java.util.Map)
	 */
	@Override
	public Map<String, StringWriter> createLostFiles(Map<String, Map<String, String>> lostCode) {
		// All files that existed in the workspace will be contained here
		final List<IFile> needsValidation = new ArrayList<IFile>();
		final Map<String, Map<String, String>> validatedLostFiles = new HashMap<String, Map<String, String>>();

		for (Map.Entry<String, Map<String, String>> entry : lostCode.entrySet()) {
			final File targetFile = new File(entry.getKey().concat(
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final IFile workspaceFile = AcceleoWorkspaceUtil.INSTANCE.getWorkspaceFile(targetFile);
			if (!targetFile.getParentFile().exists()) {
				if (!targetFile.getParentFile().mkdirs()) {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							"AcceleoEvaluationContext.FolderCreationError", targetFile.getParentFile())); //$NON-NLS-1$
				}
			}
			// If the target is not in the workspace, or if it did not exist, simply create the lost file.
			if (workspaceFile == null || !targetFile.exists() || !targetFile.canRead()) {
				// File is outside of the workspace and can be saved as is
				internalCreateLostFile(entry.getKey(), entry.getValue());
			} else {
				// We always append to the end of lost files, we must then ask confirmation for all
				needsValidation.add(workspaceFile);
			}
		}

		final IFile[] validateFiles = needsValidation.toArray(new IFile[needsValidation.size()]);
		final IStatus validationStatus = ResourcesPlugin.getWorkspace().validateEdit(validateFiles,
				IWorkspace.VALIDATE_PROMPT);
		if (validationStatus.isOK()) {
			for (Map.Entry<String, Map<String, String>> entry : validatedLostFiles.entrySet()) {
				internalCreateLostFile(entry.getKey(), entry.getValue());
			}
		}

		return null;
	}

	/**
	 * This can be used to create a single lost file.
	 * 
	 * @param originalPath
	 *            Path of the original file.
	 * @param lostAreas
	 *            protected areas that have been lost by this generation.
	 */
	private void internalCreateLostFile(String originalPath, Map<String, String> lostAreas) {
		StringBuilder lostContent = new StringBuilder();
		for (final String lostAreaContent : lostAreas.values()) {
			lostContent.append(lostAreaContent);
			lostContent.append(LINE_SEPARATOR);
		}
		Writer writer = null;
		try {
			final File lostFile = new File(originalPath.concat(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			writer = new BufferedWriter(new FileWriter(lostFile, true));
			writer.append(LINE_SEPARATOR).append(Calendar.getInstance().getTime().toString()).append(
					LINE_SEPARATOR);
			writer.append("================================================================================"); //$NON-NLS-1$
			writer.append(LINE_SEPARATOR);
			writer.append(lostContent);
		} catch (final IOException e) {
			final String errorMessage = AcceleoEngineMessages.getString(
					"AcceleoEvaluationContext.LostContent", originalPath, lostContent); //$NON-NLS-1$
			AcceleoEnginePlugin.log(errorMessage, false);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					AcceleoEnginePlugin.log(e, false);
				}
			}
		}
	}
}
