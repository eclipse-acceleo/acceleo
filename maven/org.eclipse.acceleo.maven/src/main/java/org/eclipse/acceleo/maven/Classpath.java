/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.maven;

import java.util.List;

public class Classpath {
	
	private List<Entry> entries;

	public Classpath(List<Entry> entries) {
		this.entries = entries;
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
}
