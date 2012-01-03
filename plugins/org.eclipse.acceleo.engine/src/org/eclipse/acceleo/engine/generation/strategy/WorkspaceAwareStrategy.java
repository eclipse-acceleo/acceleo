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
import java.util.Iterator;
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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Display;

/**
 * This generation strategy can be used to generate files while being aware of those present in the workspace.
 * It will create ask the workspace if these files can be created/overriden beforehand. This is the most
 * memory-expensive strategy; however it can be used with strict VCSs like clearcase that require this
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

		if (file.isDirectory()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationContext.FileNameIsDirectory", file)); //$NON-NLS-1$
		}

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
			final IFile workspaceFile = AcceleoWorkspaceUtil.getWorkspaceFile(targetFile);
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
		EditValidator validator = new EditValidator(validateFiles);
		// sync exec : we're waiting for the validation's result.
		Display.getDefault().syncExec(validator);

		if (validator.getValidationResult().isOK()) {
			/*
			 * The VCS has done its job, yet the user might have removed some files from the list. We'll
			 * filter out those that are still read-only to handle this.
			 */
			final Iterator<Map.Entry<IFile, Writer>> iterator = needsValidation.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<IFile, Writer> next = iterator.next();
				if (next.getKey().isReadOnly()) {
					iterator.remove();
				}
			}

			for (Writer writer : needsValidation.values()) {
				try {
					writer.close();
				} catch (IOException e) {
					AcceleoEnginePlugin.log(e, false);
				}
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
			final IFile workspaceFile = AcceleoWorkspaceUtil.getWorkspaceFile(targetFile);
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
		EditValidator validator = new EditValidator(validateFiles);
		// sync exec : we're waiting for the validation's result.
		Display.getDefault().syncExec(validator);

		if (validator.getValidationResult().isOK()) {
			/*
			 * The VCS has done its job, yet the user might have removed some files from the list. We'll
			 * filter out those that are still read-only to handle this.
			 */
			final Iterator<IFile> iterator = needsValidation.iterator();
			while (iterator.hasNext()) {
				IFile next = iterator.next();
				if (next.isReadOnly()) {
					iterator.remove();
				}
			}

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

	/**
	 * This will be used to ask the workspace to validate file modifications.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class EditValidator implements Runnable {
		/** This will contain the list of files which modification needs validation. */
		private IFile[] files;

		/** Result of the validation. */
		private IStatus validationResult;

		/**
		 * Instantiates this runnable given the list of files we seek to modify.
		 * 
		 * @param files
		 *            The files we seek to modify.
		 */
		public EditValidator(IFile[] files) {
			this.files = files;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			validationResult = ResourcesPlugin.getWorkspace().validateEdit(files,
					Display.getDefault().getActiveShell());
		}

		/**
		 * Returns the result of this validation.
		 * 
		 * @return The result of this validation.
		 */
		public IStatus getValidationResult() {
			return validationResult;
		}
	}
}
