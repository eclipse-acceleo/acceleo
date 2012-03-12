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
package org.eclipse.acceleo.ide.ui.wizards.module.example;

import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * Default implementation of the "org.eclipse.acceleo.ide.ui.initialization" extension point. It is used to
 * initialize automatically a module file from an example, by copying the text of the example into the a
 * template or a new query.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.3
 */
public class AcceleoCopyExampleInitializationStrategy implements IAcceleoInitializationStrategy {

	/**
	 * The module element kind.
	 */
	private String elementKind;

	/**
	 * Indicates if the template has a file block.
	 */
	private boolean templateHasFileBlock;

	/**
	 * Indicates if the template is main.
	 */
	private boolean templateIsMain;

	/**
	 * Indicates if the documentation should be generated.
	 */
	private boolean shouldGenerateDocumentation;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("AcceleoCopyExampleContentStrategy.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getInitialFileNameFilter()
	 */
	public String getInitialFileNameFilter() {
		return "*.java"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceMetamodelURI()
	 */
	public boolean forceMetamodelURI() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceMetamodelType()
	 */
	public boolean forceMetamodelType() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceHasFile()
	 */
	public boolean forceHasFile() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceHasMain()
	 */
	public boolean forceHasMain() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceQuery()
	 */
	public boolean forceQuery() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceTemplate()
	 */
	public boolean forceTemplate() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceDocumentation()
	 */
	public boolean forceDocumentation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#configure(java.lang.String,
	 *      boolean, boolean, boolean)
	 */
	public void configure(String moduleElementKind, boolean hasFileBlock, boolean isMain,
			boolean generateDocumentation) {
		this.elementKind = moduleElementKind;
		this.templateHasFileBlock = hasFileBlock;
		this.templateIsMain = isMain;
		this.shouldGenerateDocumentation = generateDocumentation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getContent(org.eclipse.core.resources.IFile,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getContent(IFile exampleFile, String moduleName, List<String> metamodelURI,
			String metamodelFileType) {
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
		if (shouldGenerateDocumentation) {
			buffer.append("[**\n * The documentation of the module.\n */]\n"); //$NON-NLS-1$
		}
		buffer.append("[module " + moduleName + "('"); //$NON-NLS-1$ //$NON-NLS-2$
		int cpt = 1;
		for (String uri : metamodelURI) {
			buffer.append(uri);
			if (cpt < metamodelURI.size()) {
				buffer.append("', '"); //$NON-NLS-1$
			}
			cpt++;
		}
		buffer.append("')/]\n\n"); //$NON-NLS-1$

		if (IAcceleoInitializationStrategy.TEMPLATE_KIND.equals(elementKind)) {
			if (shouldGenerateDocumentation) {
				buffer.append("[**\n * The documentation of the template.\n * @param " + var + "\n */]\n"); //$NON-NLS-1$ //$NON-NLS-2$
			}
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
		}

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
