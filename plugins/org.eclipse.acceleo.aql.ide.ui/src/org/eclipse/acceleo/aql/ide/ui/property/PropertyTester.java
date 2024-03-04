/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.property;

import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.core.resources.IFile;

public class PropertyTester extends org.eclipse.core.expressions.PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final boolean res;

		switch (property) {
			case "isMain":
				if (receiver instanceof IFile) {
					res = AcceleoPlugin.isAcceleoMain((IFile)receiver);
				} else {
					res = false;
				}
				break;

			default:
				throw new IllegalStateException("Unkown property" + property);
		}

		return res;
	}

}
