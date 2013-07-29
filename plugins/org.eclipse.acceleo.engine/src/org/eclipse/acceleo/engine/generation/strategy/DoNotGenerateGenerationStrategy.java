/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.generation.strategy;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;
import org.eclipse.acceleo.engine.generation.writers.AcceleoNullWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This generation strategy can support partial generation if we are in Eclipse. It will look for a file named
 * "do_not_generate.acceleo" at the root of the project where the files are generated in order to see if the
 * fullpath of the file to generate is located in this file. If the file is present in the file, it will not
 * be generated otherwise, Acceleo will delegate everything back to the default generation strategy.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.3
 */
public class DoNotGenerateGenerationStrategy extends AbstractGenerationStrategy {

	/**
	 * The original generation strategy to which we will delegate most of the work.
	 */
	private IAcceleoGenerationStrategy delegate;

	/**
	 * The constructor.
	 * 
	 * @param generationStrategy
	 *            The generation strategy to which most of the behavior will be delegated.
	 */
	public DoNotGenerateGenerationStrategy(IAcceleoGenerationStrategy generationStrategy) {
		this.delegate = generationStrategy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean)
	 */
	@Override
	public AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags) throws IOException {
		if (EMFPlugin.IS_ECLIPSE_RUNNING && !this.shouldGenerate(file)) {
			return new AcceleoNullWriter();
		}
		return this.delegate.createWriterFor(file, previous, appendMode, hasJMergeTags);
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
		if (EMFPlugin.IS_ECLIPSE_RUNNING && !this.shouldGenerate(file)) {
			return new AcceleoNullWriter();
		}
		return this.delegate.createWriterFor(file, previous, appendMode, hasJMergeTags, charset);
	}

	/**
	 * This will check whether the given File should be generated according to the do_not_generate.acceleo
	 * file.
	 * 
	 * @param file
	 *            The file we need to check.
	 * @return <code>true</code> if this file should be generated, <code>false</code> otherwise.
	 */
	private boolean shouldGenerate(File file) {
		IFile workspaceFile = null;

		IFile[] filesForLocationURI = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(
				file.toURI());
		for (IFile iFile : filesForLocationURI) {
			IProject project = iFile.getProject();
			if (project.isAccessible()) {
				workspaceFile = iFile;
				break;
			}
		}

		if (workspaceFile != null) {
			IFile doNotGenerateFile = workspaceFile.getProject().getFile(
					IAcceleoConstants.DO_NOT_GENERATE_FILENAME);
			if (doNotGenerateFile.exists()) {
				try {
					List<String> readLines = Files.readLines(doNotGenerateFile.getLocation().toFile(),
							Charsets.US_ASCII);
					for (String line : readLines) {
						if (line.trim().equals(workspaceFile.getFullPath().toString())) {
							return false;
						}
					}
				} catch (IOException e) {
					AcceleoEnginePlugin.log(e, true);
				}
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#awaitCompletion()
	 */
	@Override
	public void awaitCompletion() throws InterruptedException {
		delegate.awaitCompletion();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createLostFile(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public Map<String, StringWriter> createLostFile(String originalPath, Map<String, String> lostCode) {
		return delegate.createLostFile(originalPath, lostCode);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createLostFiles(java.util.Map)
	 */
	@Override
	public Map<String, StringWriter> createLostFiles(Map<String, Map<String, String>> lostCode) {
		return delegate.createLostFiles(lostCode);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#dispose()
	 */
	@Override
	public void dispose() {
		delegate.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#flushWriter(java.lang.String,
	 *      java.io.Writer)
	 */
	@Override
	public void flushWriter(String filePath, Writer writer) throws IOException {
		delegate.flushWriter(filePath, writer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#flushWriters(java.util.Map)
	 */
	@Override
	public void flushWriters(Map<String, Writer> currentWriters) throws IOException {
		delegate.flushWriters(currentWriters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#preparePreview(java.util.Map)
	 */
	@Override
	public Map<String, String> preparePreview(Map<String, Writer> currentWriters) {
		return delegate.preparePreview(currentWriters);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#willReturnPreview()
	 */
	@Override
	public boolean willReturnPreview() {
		return delegate.willReturnPreview();
	}

}
