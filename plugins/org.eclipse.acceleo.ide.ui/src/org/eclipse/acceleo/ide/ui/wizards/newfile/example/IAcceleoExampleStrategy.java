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

import org.eclipse.core.resources.IFile;

/**
 * An internal extension point is defined to specify multiple example strategies. It is used to initialize
 * automatically a template file from an example in the "New>Acceleo>Acceleo Templates" wizard. The extension
 * point "org.eclipse.acceleo.ide.ui.example" requires a fully qualified name of a Java class implementing
 * this interface.
 * 
 * @see AcceleoCopyExampleContentStrategy
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @deprecated Please use
 *             {@link org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy} now.
 */
@Deprecated
public interface IAcceleoExampleStrategy {

	/**
	 * The identifier of the internal extension point specifying the implementation to use for example
	 * strategy. It is used to initialize automatically a template file in the Acceleo project.
	 */
	String EXAMPLE_STRATEGY_EXTENSION_ID = "org.eclipse.acceleo.ide.ui.example"; //$NON-NLS-1$

	/**
	 * Gets the description of the strategy (Displayed in the "New>Acceleo>Acceleo Templates" wizard).
	 * 
	 * @return the description of the strategy
	 */
	String getDescription();

	/**
	 * Gets the initial file name filter for this strategy.
	 * 
	 * @return the initial file name filter
	 */
	String getInitialFileNameFilter();

	/**
	 * Indicates if this strategy defines itself the meta-model URI of the template to create.
	 * 
	 * @return true if this strategy defines itself the meta-model URI
	 */
	boolean forceMetamodelURI();

	/**
	 * Indicates if this strategy defines itself the meta-model type of the template to create.
	 * 
	 * @return true if this strategy defines itself the meta-model type
	 * @since 3.0
	 */
	boolean forceMetamodelType();

	/**
	 * Indicates if the template created by this strategy will generate a file.
	 * 
	 * @return true if the template created by this strategy will generate a file
	 * @since 3.0
	 */
	boolean forceHasFile();

	/**
	 * Indicates if this strategy defines a main annotation (@main).
	 * 
	 * @return true if this strategy defines a main annotation
	 * @since 3.0
	 */
	boolean forceHasMain();

	/**
	 * Gets the new template content, using the example file and the wizard information.
	 * 
	 * @param exampleFile
	 *            is the selected example
	 * @param moduleName
	 *            is the module name
	 * @param templateHasFileBlock
	 *            indicates if a file block must be generated
	 * @param templateIsMain
	 *            indicates if a main annotation (@main) must be generated
	 * @param metamodelURI
	 *            is the metamodel URI
	 * @param metamodelFileType
	 *            is the main metamodel type
	 * @return the new template content
	 */
	String getContent(IFile exampleFile, String moduleName, boolean templateHasFileBlock,
			boolean templateIsMain, String metamodelURI, String metamodelFileType);

}
