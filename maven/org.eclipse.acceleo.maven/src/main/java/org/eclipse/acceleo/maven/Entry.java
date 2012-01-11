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
package org.eclipse.acceleo.maven;

public class Entry {
	private String input;
	private String output;

	public Entry(String input, String output) {
		this.input = input;
		this.output = output;
	}
	
	public String getInput() {
		return input;
	}
	
	public String getOutput() {
		return output;
	}
}
