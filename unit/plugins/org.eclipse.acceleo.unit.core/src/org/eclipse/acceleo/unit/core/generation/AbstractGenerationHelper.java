/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.generation;

import java.io.File;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.unit.core.runner.AcceleoCacheLoader;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * The acceleo generation helper abstract class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public abstract class AbstractGenerationHelper {

	/**
	 * The module file path.
	 */
	protected String modulePath;

	/**
	 * The module instance.
	 */
	protected Module module;

	/**
	 * The model element.
	 */
	protected EObject modelElement;

	/**
	 * The template generated content.
	 */
	protected boolean generated;

	/**
	 * The signature index.
	 */
	protected int index;

	/**
	 * The qualified name.
	 */
	protected String qualifiedName;

	/**
	 * The constructor.
	 * 
	 * @param modulePath
	 *            the module path.
	 * @param index
	 *            the signature index.
	 * @param qualifiedName
	 *            the qualified name.
	 */
	public AbstractGenerationHelper(String modulePath, int index, String qualifiedName) {
		this.modulePath = modulePath;
		this.index = index;
		this.qualifiedName = qualifiedName;
		generated = false;
	}

	/**
	 * Generate all test case.
	 */
	public abstract void generate();

	/**
	 * Loads the module.
	 * 
	 * @return The module loaded
	 */
	protected Module loadModule() {
		EObject eObject;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			eObject = AcceleoCacheLoader.load(URI.createURI("platform:/plugin/" + modulePath)); //$NON-NLS-1$
		} else {
			String path = System.getProperty("user.dir"); //$NON-NLS-1$
			File filePath = new File(path);
			filePath = new File(filePath, "../" + modulePath); //$NON-NLS-1$

			eObject = AcceleoCacheLoader.load(URI.createFileURI(filePath.getAbsolutePath()));
		}

		if (eObject instanceof Module) {
			return (Module)eObject;
		}
		return null;
	}

	/**
	 * Sets the model element.
	 * 
	 * @param modelElement
	 *            the modelElement to set
	 */
	public void setModelElement(EObject modelElement) {
		this.modelElement = modelElement;
	}

	/**
	 * Model element getter.
	 * 
	 * @return the root element
	 */
	public EObject getModelElement() {
		return modelElement;
	}

	/**
	 * Index getter.
	 * 
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Get if the template is generated.
	 * 
	 * @return the templateGenerated
	 */
	public boolean hasGenerate() {
		return generated;
	}

	/**
	 * Get the qualified name.
	 * 
	 * @return the qualified name
	 */
	public String getQualifiedName() {
		return qualifiedName;
	}

}
