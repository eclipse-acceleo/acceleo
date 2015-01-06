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

public class ModelObjectType extends Type {

	private final Set<FeatureDescription> features;

	public ModelObjectType(Set<FeatureDescription> features) {
		super(TypeId.MODELOBJECT);
		this.features = features;
	}

	ModelObjectType(Set<FeatureDescription> features, TypeId id) {
		super(id);
		this.features = features;
	}

	public Set<FeatureDescription> getFeatures() {
		return features;
	}

	@Override
	public Type merge(Type type) {
		if (type instanceof ModelObjectType) {
			Set<FeatureDescription> newSet = new HashSet<FeatureDescription>(features);
			newSet.addAll(((ModelObjectType)type).features);
			return new ModelObjectType(newSet);
		} else {
			return new Any();
		}
	}
}
