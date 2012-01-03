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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * The 'FileBlocks' selection dialog is used to initialize the main template content. The new template will
 * call the selected elements.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class FileBlocksContentProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public FileBlocksContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object object) {
		Object[] result;
		if (object instanceof String) {
			result = new Object[] {(String)object };
		} else if (object instanceof List<?>) {
			result = ((List<?>)object).toArray();
		} else if (object instanceof Object[]) {
			result = (Object[])object;
		} else {
			result = super.getElements(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object object) {
		boolean result;
		if (object instanceof FileBlocksProjectHandler) {
			result = hasValidModuleElement(((FileBlocksProjectHandler)object).getModules());
		} else if (object instanceof Module) {
			result = hasValidModuleElement((Module)object);
		} else if (object instanceof ModuleElement) {
			result = false;
		} else {
			result = super.hasChildren(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object object) {
		Object[] result;
		if (object instanceof FileBlocksProjectHandler) {
			List<Module> outputModules = new ArrayList<Module>();
			for (Module module : ((FileBlocksProjectHandler)object).getModules()) {
				if (hasValidModuleElement(module)) {
					outputModules.add(module);
				}
			}
			result = outputModules.toArray();
		} else if (object instanceof Module) {
			result = getValidModuleElements((Module)object).toArray();
		} else if (object instanceof ModuleElement) {
			result = new Object[] {};
		} else {
			result = super.getChildren(object);
		}
		return result;
	}

	/**
	 * Indicates if there is at least one valid module in the array.
	 * 
	 * @param modules
	 *            are the modules
	 * @return true if there is at least one valid module
	 */
	private boolean hasValidModuleElement(Module[] modules) {
		for (Module module : modules) {
			if (hasValidModuleElement(module)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if there is at least one valid module element in the module.
	 * 
	 * @param module
	 *            is the module
	 * @return true if there is at least one valid module element
	 */
	private boolean hasValidModuleElement(Module module) {
		for (ModuleElement moduleElement : module.getOwnedModuleElement()) {
			if (isSignificant(moduleElement)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the valid module elements of the given module.
	 * 
	 * @param module
	 *            is the module
	 * @return the valid module elements
	 */
	private List<ModuleElement> getValidModuleElements(Module module) {
		List<ModuleElement> result = new ArrayList<ModuleElement>();
		for (ModuleElement moduleElement : module.getOwnedModuleElement()) {
			if (isSignificant(moduleElement)) {
				result.add(moduleElement);
			}
		}
		return result;
	}

	/**
	 * Indicates if the given module element is significant. It is 'significant' if it is a template and when
	 * it contains a file block.
	 * 
	 * @param moduleElement
	 *            is the module element
	 * @return true if the given module element is valid
	 */
	private boolean isSignificant(ModuleElement moduleElement) {
		if (moduleElement.getVisibility() != VisibilityKind.PRIVATE && moduleElement instanceof Template) {
			Iterator<EObject> iChildren = moduleElement.eAllContents();
			while (iChildren.hasNext()) {
				EObject iChild = iChildren.next();
				if (iChild instanceof FileBlock) {
					return true;
				}
			}
		}
		return false;
	}

}
