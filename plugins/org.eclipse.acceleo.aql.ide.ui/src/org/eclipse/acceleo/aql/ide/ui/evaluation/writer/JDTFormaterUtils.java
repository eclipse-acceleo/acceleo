/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.evaluation.writer;

import java.util.Map;

import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

/**
 * Utility class to format Java code using the JDT. WARNING this class depend on an optional dependency to the
 * JDT and should only be loaded when the JDT is present.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class JDTFormaterUtils {

	/**
	 * Gets the formated code from the given Java code.
	 * 
	 * @param fileURI
	 *            the file {@link URI}
	 * @param code
	 *            the Java code
	 * @return the formated code from the given Java code
	 */
	public static String getFormatedCode(URI fileURI, String code, String lineSeparator) {
		final String res;

		final Map<String, String> options;
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileURI
				.path()));
		if (file != null) {
			final IProject project = file.getProject();
			if (project != null) {
				final IJavaProject javaProject = JavaCore.create(project);
				if (javaProject != null) {
					options = javaProject.getOptions(true);
				} else {
					options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
				}

			} else {
				options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
			}
		} else {
			options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
		}

		final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);
		// retrieve the source to format
		final TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT
				| CodeFormatter.F_INCLUDE_COMMENTS, code, 0, code.length(), 0, lineSeparator);
		if (edit != null) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, JDTFormaterUtils.class,
					"can't format code for: " + fileURI));
			// apply the format edit
			IDocument document = new Document(code);
			try {
				edit.apply(document);
			} catch (MalformedTreeException | BadLocationException e) {
				AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, JDTFormaterUtils.class,
						"can't format code for: " + fileURI, e));
			}
			res = document.get();
		} else {
			res = code;
		}

		return res;
	}

}
