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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile;

import org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Configuring a new template created by the 'AcceleoNewTemplatesWizard' wizard.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */

public class CreateTemplateData {

	/**
	 * The path in the workspace of the template container.
	 */
	private String templateContainer = ""; //$NON-NLS-1$

	/**
	 * The short name of the template file (without file extension). For example, 'generate' is the short name
	 * of the file 'generate.acceleo'.
	 */
	private String templateShortName = ""; //$NON-NLS-1$

	/**
	 * Indicates if a file block must be generated.
	 */
	private boolean templateHasFileBlock;

	/**
	 * Indicates if the template is initialized.
	 */
	private boolean templateIsInitialized;

	/**
	 * Indicates if a main annotation (@main) must be generated.
	 */
	private boolean templateIsMain;

	/**
	 * The metamodel URI(s) which defines the available types in the template.
	 */
	private String templateMetamodel = ""; //$NON-NLS-1$

	/**
	 * The type for which some files are generated.
	 */
	private String templateFileType = ""; //$NON-NLS-1$

	/**
	 * The example path.
	 */
	private String templateExamplePath = ""; //$NON-NLS-1$

	/**
	 * The example strategy.
	 */
	private String templateExampleStrategy = ""; //$NON-NLS-1$

	/**
	 * Generated files extension.
	 */
	private String generatedFileExtension;

	/**
	 * The default content of the template.
	 */
	private String templateContent;

	/**
	 * Constructor.
	 */
	public CreateTemplateData() {
	}

	/**
	 * The path in the workspace of the template container.
	 * 
	 * @return the template container
	 */
	public String getTemplateContainer() {
		return templateContainer;
	}

	/**
	 * The short name of the template file.
	 * 
	 * @return the short name of the template file
	 */
	public String getTemplateShortName() {
		return templateShortName;
	}

	/**
	 * Gets the metamodel URI which defines the available types in the new template. It cans return multiple
	 * metamodels by using a comma separator.
	 * 
	 * @return the metamodel URI
	 */
	public String getTemplateMetamodel() {
		return templateMetamodel;
	}

	/**
	 * The type for which some files are generated.
	 * 
	 * @return the type name
	 */
	public String getTemplateFileType() {
		return templateFileType;
	}

	/**
	 * Generated files extension.
	 * 
	 * @return the generated file extension
	 */
	public String getGeneratedFileExtension() {
		return generatedFileExtension;
	}

	/**
	 * Gets the content of the template.
	 * 
	 * @return the textual content of the template
	 */
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * Gets the template example path.
	 * 
	 * @return the template example path
	 */
	public String getTemplateExamplePath() {
		if (templateIsInitialized) {
			return templateExamplePath;
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Gets the template example strategy.
	 * 
	 * @return the template example strategy
	 */
	public String getTemplateExampleStrategy() {
		if (templateIsInitialized) {
			return templateExampleStrategy;
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Indicates if the template is a file.
	 * 
	 * @return true if the template is a file
	 */
	public boolean getTemplateHasFileBlock() {
		return templateHasFileBlock;
	}

	/**
	 * Indicates if the template must be initialized.
	 * 
	 * @return true if the template must be initialized
	 */
	public boolean getTemplateIsInitialized() {
		return templateIsInitialized;
	}

	/**
	 * Indicates if the template is a main entry point (@main annotation).
	 * 
	 * @return true if the template is a main entry point
	 */
	public boolean getTemplateIsMain() {
		return templateIsMain;
	}

	/**
	 * Sets the template container.
	 * 
	 * @param templateContainer
	 *            is the template container
	 */
	public void setTemplateContainer(String templateContainer) {
		if (templateContainer != null) {
			this.templateContainer = templateContainer;
		} else {
			this.templateContainer = ""; //$NON-NLS-1$
		}
	}

	/**
	 * Sets the template name.
	 * 
	 * @param templateShortName
	 *            is the template name
	 */
	public void setTemplateShortName(String templateShortName) {
		if (templateShortName != null) {
			this.templateShortName = templateShortName;
		} else {
			this.templateShortName = ""; //$NON-NLS-1$
		}
	}

	/**
	 * Sets the template metamodel URI.
	 * 
	 * @param templateMetamodel
	 *            is the template metamodel URI
	 */
	public void setTemplateMetamodel(String templateMetamodel) {
		if (templateMetamodel != null) {
			this.templateMetamodel = templateMetamodel;
		} else {
			this.templateMetamodel = ""; //$NON-NLS-1$
		}
	}

	/**
	 * Sets the template file type.
	 * 
	 * @param templateFileType
	 *            is the root metamodel type for the code generation
	 */
	public void setTemplateFileType(String templateFileType) {
		if (templateFileType != null) {
			this.templateFileType = templateFileType;
		} else {
			this.templateFileType = ""; //$NON-NLS-1$
		}
	}

	/**
	 * Indicates if the template has a file block.
	 * 
	 * @param templateHasFileBlock
	 *            indicates if the template has a file block
	 */
	public void setTemplateHasFileBlock(boolean templateHasFileBlock) {
		this.templateHasFileBlock = templateHasFileBlock;
	}

	/**
	 * Indicates if the template is a main entry point (@main annotation).
	 * 
	 * @param templateIsMain
	 *            indicates if the template is a main entry point
	 */
	public void setTemplateIsMain(boolean templateIsMain) {
		this.templateIsMain = templateIsMain;
	}

	/**
	 * Indicates if the template is initialized.
	 * 
	 * @param templateInitialized
	 *            indicates if the template is initialized
	 */
	public void setTemplateIsInitialized(boolean templateInitialized) {
		this.templateIsInitialized = templateInitialized;
	}

	/**
	 * Sets the template example path.
	 * 
	 * @param templateExamplePath
	 *            is the template exemple file path
	 */
	public void setTemplateExamplePath(String templateExamplePath) {
		if (templateExamplePath != null) {
			this.templateExamplePath = templateExamplePath;
		} else {
			this.templateExamplePath = ""; //$NON-NLS-1$
		}
	}

	/**
	 * Sets the template example strategy.
	 * 
	 * @param templateExampleStrategy
	 *            is the template example strategy
	 */
	public void setTemplateExampleStrategy(String templateExampleStrategy) {
		if (templateExampleStrategy != null) {
			this.templateExampleStrategy = templateExampleStrategy;
		} else {
			this.templateExampleStrategy = ""; //$NON-NLS-1$
		}
	}

	/**
	 * A java file can be found next to the template file. It provides a programmatical way to launch a code
	 * generation. This method returns the short name of the java file (without file extension). For example,
	 * 'Generate' is the java short name of the file 'generate.acceleo'.
	 * 
	 * @return the java name of the template
	 */
	public String getTemplateJavaClassShortName() {
		if (templateShortName.length() > 0) {
			return Character.toUpperCase(templateShortName.charAt(0)) + templateShortName.substring(1);
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Create the template content from the example.
	 */
	public void createExampleStrategy() {
		IPath path = new Path(this.templateExamplePath);
		if (path.getFileExtension() != null) {
			generatedFileExtension = path.getFileExtension();
		} else {
			generatedFileExtension = "txt"; //$NON-NLS-1$
		}
		IAcceleoExampleStrategy strategy = getExampleStrategy();
		IFile templateExampleFile;
		if (path.segmentCount() >= 2) {
			templateExampleFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (!templateExampleFile.exists()) {
				templateExampleFile = null;
			}
		} else {
			templateExampleFile = null;
		}
		if (strategy != null) {
			templateContent = strategy.getContent(templateExampleFile, templateShortName,
					templateHasFileBlock, templateIsMain, templateMetamodel, templateFileType);
		} else {
			templateContent = ""; //$NON-NLS-1$
		}
	}

	/**
	 * Gets the selected example strategy. It will be used to initialize automatically the template content
	 * from the example. The default behavior is to copy the content of the example in a simple template, but
	 * the automation can be more powerfull.
	 * 
	 * @return the selected example strategy, or null if selection is empty
	 */
	public IAcceleoExampleStrategy getExampleStrategy() {
		if (templateExampleStrategy == null || templateExampleStrategy.length() == 0) {
			templateExampleStrategy = AcceleoUIMessages
					.getString("AcceleoCopyExampleContentStrategy.Description"); //$NON-NLS-1$
		}
		return null;
	}

}
