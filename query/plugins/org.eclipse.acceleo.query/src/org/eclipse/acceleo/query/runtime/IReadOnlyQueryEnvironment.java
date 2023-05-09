/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;


/**
 * An IQueryEnvironment contains all informations needed to execute query.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IReadOnlyQueryEnvironment {

	/**
	 * Return the {@link ILookupEngine} query {@link IService}.
	 * 
	 * @return the {@link ILookupEngine} query {@link IService}
	 */
	ILookupEngine getLookupEngine();

	/**
	 * Return the IEPackageProvider providing access to registered ecore packages.
	 * 
	 * @return the EPackageProvider providing access to registered ecore packages.
	 */
	IEPackageProvider getEPackageProvider();

}
