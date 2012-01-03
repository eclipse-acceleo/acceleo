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
package org.eclipse.acceleo.ide.ui.wizards.newfile.example;

import java.util.StringTokenizer;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * Default implementation of the "org.eclipse.acceleo.ide.ui.example" extension point. It is used to
 * initialize automatically a template file from an example, by copying the text of the example into the new
 * template.
 * 
 * @see IAcceleoExampleStrategy
 * @deprecated This class is now unused in Acceleo and as such, it is deprecated. Please see
 *             {@link org.eclipse.acceleo.ide.ui.wizards.module.example.AcceleoCopyExampleInitializationStrategy}
 *             now.
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
@Deprecated
public class AcceleoCopyExampleContentStrategy implements IAcceleoExampleStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("AcceleoCopyExampleContentStrategy.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getInitialFileNameFilter()
	 */
	public String getInitialFileNameFilter() {
		return "*.java"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceMetamodelURI()
	 */
	public boolean forceMetamodelURI() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceHasFile()
	 * @since 3.0
	 */
	public boolean forceHasFile() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceHasMain()
	 * @since 3.0
	 */
	public boolean forceHasMain() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceMetamodelType()
	 * @since 3.0
	 */
	public boolean forceMetamodelType() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getContent(org.eclipse.core.resources.IFile,
	 *      java.lang.String, boolean, boolean, java.lang.String, java.lang.String)
	 */
	public String getContent(IFile exampleFile, String moduleName, boolean templateHasFileBlock,
			boolean templateIsMain, String metamodelURI, String metamodelFileType) {
		String var;
		if (metamodelFileType != null && metamodelFileType.length() > 0) {
			var = 'a' + String.valueOf(metamodelFileType);
		} else {
			var = "arg"; //$NON-NLS-1$
		}
		String fileExtension;
		if (exampleFile != null && exampleFile.getFileExtension() != null) {
			fileExtension = ".concat('." + exampleFile.getFileExtension() + "')"; //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			fileExtension = ""; //$NON-NLS-1$
		}
		String defaultEncoding;
		try {
			if (exampleFile != null) {
				defaultEncoding = exampleFile.getCharset();
			} else {
				defaultEncoding = "UTF-8"; //$NON-NLS-1$
			}
		} catch (CoreException e) {
			defaultEncoding = "UTF-8"; //$NON-NLS-1$
		}
		StringBuffer buffer = new StringBuffer("[comment encoding = "); //$NON-NLS-1$
		buffer.append(defaultEncoding);
		buffer.append(" /]\n"); //$NON-NLS-1$
		buffer.append("[module " + moduleName + "('"); //$NON-NLS-1$ //$NON-NLS-2$
		StringTokenizer st = new StringTokenizer(metamodelURI, ","); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			buffer.append(st.nextToken().trim());
			if (st.hasMoreTokens()) {
				buffer.append("', '"); //$NON-NLS-1$
			}
		}
		buffer.append("')/]\n\n"); //$NON-NLS-1$
		buffer.append("[template public " + moduleName + "(" + var + " : " + metamodelFileType + ")]\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		if (templateIsMain) {
			buffer.append("\t\n"); //$NON-NLS-1$
			buffer.append("\t[comment @main /]\n"); //$NON-NLS-1$
		}
		if (templateHasFileBlock) {
			buffer.append("\t[file (" + var + ".name" + fileExtension + ", false, '" + defaultEncoding + "')]\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		if (exampleFile != null && exampleFile.exists()) {
			StringBuffer text = readExampleContent(exampleFile);
			buffer.append(text);
		} else {
			buffer.append("\t[" + var + ".name/]\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (templateHasFileBlock) {
			buffer.append("\t[/file]\n"); //$NON-NLS-1$
		}
		if (templateIsMain) {
			buffer.append("\t\n"); //$NON-NLS-1$
		}
		buffer.append("[/template]\n"); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * Reads the text of the example file.
	 * 
	 * @param exampleFile
	 *            is the example file
	 * @return the example file text
	 */
	protected StringBuffer readExampleContent(IFile exampleFile) {
		StringBuffer text = FileContent.getFileContent(exampleFile.getLocation().toFile());
		char[] chars = new char[text.length()];
		text.getChars(0, text.length(), chars, 0);
		text = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '[') {
				text.append("['['/]"); //$NON-NLS-1$
			} else if (c == ']') {
				text.append("[']'/]"); //$NON-NLS-1$
			} else {
				text.append(c);
			}
		}
		return text;
	}

}
