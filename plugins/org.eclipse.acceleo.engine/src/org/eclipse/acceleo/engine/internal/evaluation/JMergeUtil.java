/*******************************************************************************
 * Copyright (c) 2009 Obeo.
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

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
	 * This can be used to support JMerge for the merging of generated content (<code>content</code>) with the
	 * old file content. Old content will be read from either <code>target</code> or <code>oldContent</code>
	 * according to the value of <code>previewMode</code>
	 * 
	 * @param target
	 *            Target file of the generation.
	 * @param content
	 *            The new content with which we should merge the old.
	 * @param previewMode
	 *            <code>true</code> if we are only previewing, <code>false</code> if we should merge with the
	 *            target file content then override it.
	 * @param oldContent
	 *            Previous content of the generation. Can be <code>null</code> if we are not in preview mode.
	 * @return A new StringWriter with the merged content. This will only be returned when in preview mode.
	 * @throws IOException
	 *             Throw if we couldn't append data to the target file.
	 */
	public static Writer mergeFileContent(File target, String content, boolean previewMode, String oldContent)
			throws IOException {
		Writer newWriter = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			final FileWriter writer = new FileWriter(target);
			writer.append(content);
			writer.close();
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
				if (!previewMode) {
					jMerger.setTargetCompilationUnit(jMerger
							.createCompilationUnitForInputStream(new FileInputStream(target)));
				} else {
					jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForContents(oldContent));
				}
				jMerger.merge();
				newContent = jMerger.getTargetCompilationUnit().getContents();
			} else {
				// FIXME log, couldn't find emf-merge.xml
			}
		}
		if (!previewMode) {
			final FileWriter writer = new FileWriter(target);
			writer.append(newContent);
			writer.close();
		} else {
			newWriter = new StringWriter();
			newWriter.append(newContent);
			newWriter.flush();
		}
		return newWriter;
	}
}
