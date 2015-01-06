/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.type;

import java.util.HashSet;
import java.util.Set;

public class EClassType extends ModelObjectType {

	private static final Set<FeatureDescription> eClassFeatureSet = new HashSet<FeatureDescription>();

	static {
		eClassFeatureSet.add(new FeatureDescription("name", new PrimitiveType(TypeId.STRING)));
	}

	public EClassType() {
		super(eClassFeatureSet, TypeId.ECLASS);
	}

}
