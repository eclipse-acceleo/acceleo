/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * This exception will be used to inform of an unmanaged case.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class MigrationException extends UnsupportedOperationException {

	/** Serial version UID. Used for deserialization. */
	private static final long serialVersionUID = 2837889618509353463L;

	/**
	 * Instantiates an evaluation exception given its error message.
	 * 
	 * @param unmanagedObject
	 *            The unmanaged element
	 */
	public MigrationException(Object unmanagedObject) {
		super("Not managed yet: " + getQualifiedType(unmanagedObject));
	}

	private static String getQualifiedType(Object unmanagedObject) {
		if (unmanagedObject instanceof EObject) {
			List<EPackage> ancestors = new ArrayList<>();
			EPackage cursor = ((EObject)unmanagedObject).eClass().getEPackage();
			while (cursor instanceof EPackage) {
				ancestors.add(0, cursor);
				cursor = cursor.getESuperPackage();
			}
			StringBuffer res = new StringBuffer();
			for (EPackage ePackage : ancestors) {
				res.append(ePackage.getName() + "::");
			}
			res.append(((EObject)unmanagedObject).eClass().getName());
			return res.toString();
		}
		return unmanagedObject.getClass().getName();
	}
}
