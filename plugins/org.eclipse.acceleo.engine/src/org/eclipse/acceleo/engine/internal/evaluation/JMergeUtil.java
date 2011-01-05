/*******************************************************************************
 * Copyright (c) 2009, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.evaluation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;

/**
 * This class will be used to provide JMerge support when generating MTL files. Keep this in a separate class
 * to avoid non-optional dependency on org.eclipse.emf.ecore.codegen.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class JMergeUtil {
	/**
	 * Utility classes don't need to be instantiated.
	 */
	private JMergeUtil() {
		// Prevents instantiation
	}

	/**
	 * This can be used to support JMerge for the merging of generated <code>content</code> with the old file
	 * content. Old content will be read from the <code>target</code> File, new content will be read from
	 * <code>content</code>. The merged content will be not be written to disk.
	 * <p>
	 * <b>Note</b> that JMerge will <u>not</u> be called if eclipse is not running.
	 * </p>
	 * 
	 * @param target
	 *            Target file of the generation.
	 * @param content
	 *            The new content with which we should merge the old.
	 * @param charset
	 *            Encoding that should be used to read the target file. Can be <code>null</code>.
	 * @return The merged content.
	 * @throws IOException
	 *             Throw if we couldn't append data to the target file.
	 */
	public static String mergeFileContent(File target, String content, String charset) throws IOException {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			return content;
		}
		String newContent = content;
		if (target.getName().endsWith(".java")) { //$NON-NLS-1$
			// FIXME With this kind of URI, JMerge support is only accessible within Eclipse
			String jmergeFile = URI.createPlatformPluginURI(
					"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
			JControlModel model = new JControlModel();
			model.initialize(new ASTFacadeHelper(), jmergeFile);
			if (model.canMerge()) {
				JMerger jMerger = new JMerger(model);
				jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForContents(content));
				// JMerge takes care of buffering the input stream we feed it
				jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForInputStream(
						new FileInputStream(target), charset));
				jMerger.merge();
				newContent = jMerger.getTargetCompilationUnit().getContents();
			} else {
				// FIXME log, couldn't find emf-merge.xml
			}
		}
		return newContent;
	}

	/**
	 * This can be used to support JMerge for the merging of generated <code>content</code> with old generated
	 * content. Old content will be read from <code>oldContent</code> while new content will be read from
	 * <code>content</code>. The target file's only purpose is to check if we can merge such files (only
	 * *.java for now).
	 * <p>
	 * <b>Note</b> that JMerge will <u>not</u> be called if eclipse is not running.
	 * </p>
	 * 
	 * @param target
	 *            Target file of the generation.
	 * @param content
	 *            The new content with which we should merge the old.
	 * @param oldContent
	 *            Previous content of the generation. Can be <code>null</code> if we are not in preview mode.
	 * @return The merged content.
	 * @throws IOException
	 *             Throw if we couldn't append data to the target file.
	 */
	public static String mergeContent(File target, String content, String oldContent) throws IOException {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			return content;
		}
		String newContent = content;
		if (target.getName().endsWith(".java")) { //$NON-NLS-1$
			// FIXME With this kind of URI, JMerge support is only accessible within Eclipse
			String jmergeFile = URI.createPlatformPluginURI(
					"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
			JControlModel model = new JControlModel();
			model.initialize(new ASTFacadeHelper(), jmergeFile);
			if (model.canMerge()) {
				JMerger jMerger = new JMerger(model);
				jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForContents(content));
				jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForContents(oldContent));
				jMerger.merge();
				newContent = jMerger.getTargetCompilationUnit().getContents();
			} else {
				// FIXME log, couldn't find emf-merge.xml
			}
		}
		return newContent;
	}
}
