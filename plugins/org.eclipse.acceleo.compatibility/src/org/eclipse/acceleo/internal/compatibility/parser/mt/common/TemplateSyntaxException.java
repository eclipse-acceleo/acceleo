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
package org.eclipse.acceleo.internal.compatibility.parser.mt.common;

import org.eclipse.acceleo.compatibility.model.mt.core.Template;

/**
 * Template syntax exception.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TemplateSyntaxException extends Exception {

	/** Serial used for unserialization checks. */
	private static final long serialVersionUID = 1;

	/**
	 * The script.
	 */
	protected final Template template;

	/**
	 * Position of the syntax error.
	 */
	protected final Region pos;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            is the message
	 * @param template
	 *            is the template that contains this syntax error
	 * @param pos
	 *            is the position of the syntax error
	 */
	public TemplateSyntaxException(String message, Template template, Region pos) {
		super(message);
		this.template = template;
		this.pos = pos;
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            is the message
	 * @param template
	 *            is the template that contains this syntax error
	 * @param pos
	 *            is the position of the syntax error
	 */
	public TemplateSyntaxException(String message, Template template, int pos) {
		super(message);
		this.template = template;
		this.pos = new Region(pos, pos + 1);
	}

	/**
	 * Returns the position of the syntax error.
	 * 
	 * @return the position of the syntax error
	 */
	public Region getPos() {
		return pos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer text = new StringBuffer();
		if (template != null) {
			text.append("Syntax error ["); //$NON-NLS-1$
			text.append(template.getName());
			text.append("] : "); //$NON-NLS-1$
		}
		text.append(getMessage());
		return text.toString();
	}
}
