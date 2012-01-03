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
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline;

import java.util.List;

import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;

/**
 * This class is used to act as a mock of the "import declaration" node in the tree viewer of the outline
 * view. It has a package visibility because there is ABSOLUTELY nobody that could be interested in that node
 * outside of this package.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
final/* package */class AcceleoOutlineImportContainer {
	/**
	 * The module.
	 */
	private Module mod;

	/**
	 * The constructor.
	 * 
	 * @param module
	 *            The module
	 */
	public AcceleoOutlineImportContainer(final Module module) {
		this.mod = module;
	}

	/**
	 * Returns the imports of the module.
	 * 
	 * @return The imports of the module
	 */
	public List<ModuleImportsValue> getImports() {
		return this.mod.getImports();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#toString()
	 */
	@Override
	public String toString() {
		return "import declaration"; //$NON-NLS-1$
	}
}
