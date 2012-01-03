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
package org.eclipse.acceleo.model.ocl.provider;

import org.eclipse.acceleo.model.mtl.provider.MtlEditPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.ocl.ecore.Constraint;

/**
 * Specializes the ReflectiveItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class OclItemProviderSpec extends ReflectiveItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public OclItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.ReflectiveItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, MtlEditPlugin.INSTANCE.getImage("full/obj16/ocl/OCLExpression")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.provider.ReflectiveItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		if (object instanceof Constraint) {
			Constraint constraint = (Constraint)object;
			if ((constraint.getConstrainedElements() == null || constraint.getConstrainedElements().size() == 0)
					&& constraint.getName() == null) {
				return ""; //$NON-NLS-1$
			}
		}
		return object.toString();
	}
}
