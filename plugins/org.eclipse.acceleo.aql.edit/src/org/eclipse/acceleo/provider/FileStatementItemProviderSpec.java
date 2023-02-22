/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.provider;

import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.provider.utils.ASTUtils;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the FileStatementItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FileStatementItemProviderSpec extends FileStatementItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory the adapter factory
	 */
	public FileStatementItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		final String res;

		if (((FileStatement) object).getUrl() != null) {
			res = ASTUtils.serialize(((FileStatement) object).getUrl());
		} else {
			res = super.getText(object);
		}

		return res;
	}
}
