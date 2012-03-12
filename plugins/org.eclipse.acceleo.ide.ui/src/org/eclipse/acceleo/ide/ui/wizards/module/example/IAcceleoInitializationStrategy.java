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

import org.eclipse.core.resources.IFile;

/**
 * An internal extension point is defined to specify multiple example strategies. It is used to initialize
 * automatically a template file from an example in the "New>Acceleo>Acceleo Module" wizard. The extension
 * point "org.eclipse.acceleo.ide.ui.initialization" requires a fully qualified name of a Java class
 * implementing this interface.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.3
 */
public interface IAcceleoInitializationStrategy {

	/**
	 * The template kind.
	 */
	String TEMPLATE_KIND = "template"; //$NON-NLS-1$

	/**
	 * The query kind.
	 */
	String QUERY_KIND = "query"; //$NON-NLS-1$

	/**
	 * The identifier of the internal extension point specifying the implementation to use for example
	 * strategy. It is used to initialize automatically a template file in the Acceleo project.
	 */
	String INITIALIZATION_STRATEGY_EXTENSION_ID = "org.eclipse.acceleo.ide.ui.initialization"; //$NON-NLS-1$

	/**
	 * Gets the description of the strategy (Displayed in the "New>Acceleo>Acceleo Module" wizard).
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
	 */
	boolean forceMetamodelType();

	/**
	 * Indicates if the template created by this strategy will generate a file.
	 * 
	 * @return true if the template created by this strategy will generate a file
	 */
	boolean forceHasFile();

	/**
	 * Indicates if this strategy defines a main annotation (@main).
	 * 
	 * @return true if this strategy defines a main annotation
	 */
	boolean forceHasMain();

	/**
	 * Indicates if this strategy can define the use of a query.
	 * 
	 * @return <code>true</code> if this strategy can define a new query.
	 */
	boolean forceQuery();

	/**
	 * Indicates if this strategy can define the use of a template.
	 * 
	 * @return <code>true</code> if this strategy can define a new template.
	 */
	boolean forceTemplate();

	/**
	 * Indicates if this strategy force the use of the documentation.
	 * 
	 * @return <code>true</code> if this strategy must use the documentation, <code>false</code> otherwise.
	 */
	boolean forceDocumentation();

	/**
	 * Configure the strategy.
	 * 
	 * @param moduleElementKind
	 *            The kind of module element (TEMPLATE_KIND or QUERY_KIND).
	 * @param hasFileBlock
	 *            indicates if a file block must be generated
	 * @param isMain
	 *            indicates if a main annotation (@main) must be generated
	 * @param generateDocumentation
	 *            Indicates if the documentation should be generated
	 */
	void configure(String moduleElementKind, boolean hasFileBlock, boolean isMain,
			boolean generateDocumentation);

	/**
	 * Gets the new template content, using the example file and the wizard information.
	 * 
	 * @param exampleFile
	 *            is the selected example
	 * @param moduleName
	 *            is the module name
	 * @param metamodelURI
	 *            is the metamodel URI
	 * @param metamodelFileType
	 *            is the main metamodel type
	 * @return the new template content
	 */
	String getContent(IFile exampleFile, String moduleName, List<String> metamodelURI,
			String metamodelFileType);
}
