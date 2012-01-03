/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.color;

import org.eclipse.swt.graphics.RGB;

/**
 * This enum will contain the various color constants of Acceleo.
 * <p>
 * Developper note : the color preferences of Acceleo may have been exported by the user, <b>color identifier
 * are thus API !</b>
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public enum AcceleoColor {
	/** This color will be used for the hover information and has no associated preference constant. */
	BLACK(new RGB(0, 0, 0)),

	/** This will be used wherever we need the default "template-red" color. No associated preference. */
	RED(new RGB(192, 0, 0)),

	/** Color constant for Acceleo Comments. */
	COMMENT("org.eclipse.acceleo.comment.color", new RGB(63, 127, 95)), //$NON-NLS-1$

	/** Color constant for default Acceleo colors. */
	DEFAULT("org.eclipse.acceleo.default.color", new RGB(0, 0, 0)), //$NON-NLS-1$

	/** Color constant for Acceleo For statements. */
	FOR("org.eclipse.acceleo.for.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo If statements. */
	IF("org.eclipse.acceleo.if.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Keywords. */
	KEYWORD("org.eclipse.acceleo.keyword.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Let statements. */
	LET("org.eclipse.acceleo.let.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Literals. */
	LITERAL("org.eclipse.acceleo.literal.color", new RGB(63, 127, 127)), //$NON-NLS-1$

	/** Color constant for Acceleo Macros. */
	MACRO("org.eclipse.acceleo.macro.color", new RGB(192, 0, 0)), //$NON-NLS-1$

	/** Color constant for Acceleo Module names. */
	MODULE_NAME("org.eclipse.acceleo.module.name.color", new RGB(80, 80, 255)), //$NON-NLS-1$

	/** Color constant for Acceleo OCL expressions (iteration, before, after...). */
	OCL_EXPRESSION("org.eclipse.acceleo.ocl.expression.color", new RGB(80, 80, 255)), //$NON-NLS-1$

	/** Color constant for Acceleo OCL keywords (iteration, before, after...). */
	OCL_KEYWORD("org.eclipse.acceleo.ocl.keyword.color", new RGB(80, 80, 255)), //$NON-NLS-1$

	/** Color constant for Acceleo Protected Areas. */
	PROTECTED_AREA("org.eclipse.acceleo.protected.color", new RGB(130, 160, 190)), //$NON-NLS-1$

	/** Color constant for Acceleo Queries. */
	QUERY("org.eclipse.acceleo.query.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Query names. */
	QUERY_NAME("org.eclipse.acceleo.query.name.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Queries' parameters. */
	QUERY_PARAMETER("org.eclipse.acceleo.query.parameter.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Query return types. */
	QUERY_RETURN("org.eclipse.acceleo.query.return.color", new RGB(127, 0, 85)), //$NON-NLS-1$

	/** Color constant for Acceleo Templates. */
	TEMPLATE("org.eclipse.acceleo.template.color", new RGB(192, 0, 0)), //$NON-NLS-1$

	/** Color constant for Acceleo Template names. */
	TEMPLATE_NAME("org.eclipse.acceleo.template.name.color", new RGB(192, 0, 0)), //$NON-NLS-1$

	/** Color constant for OCL expressions located in template signatures. */
	TEMPLATE_OCL_EXPRESSION("org.eclipse.acceleo.template.ocl.expression.color", new RGB(192, 0, 0)), //$NON-NLS-1$

	/** Color constant for OCL keywords located in template signatures. */
	TEMPLATE_OCL_KEYWORD("org.eclipse.acceleo.template.ocl.keyword.color", new RGB(192, 0, 0)), //$NON-NLS-1$

	/** Color constant for Acceleo Templates' parameters. */
	TEMPLATE_PARAMETER("org.eclipse.acceleo.template.parameter.color", new RGB(192, 0, 0)), //$NON-NLS-1$

	/** Color constant for Acceleo variable declarations. */
	VARIABLE("org.eclipse.acceleo.variable.color", new RGB(80, 80, 255)); //$NON-NLS-1$

	/** Preference Key of this Color. */
	private String preferenceKey;

	/** RGB value of this Color. */
	private RGB defaultRGB;

	/**
	 * Default constructor.
	 * 
	 * @param preferenceKey
	 *            Preference key for this Color.
	 * @param defaultRGB
	 *            Default RGB value for this Color.
	 */
	private AcceleoColor(String preferenceKey, RGB defaultRGB) {
		this.preferenceKey = preferenceKey;
		this.defaultRGB = defaultRGB;
	}

	/**
	 * This constructor will create a Color with no associated preference.
	 * 
	 * @param defaultRGB
	 *            Default RGB value for this Color.
	 */
	private AcceleoColor(RGB defaultRGB) {
		this("", defaultRGB); //$NON-NLS-1$
	}

	/**
	 * Returns the preference key of this Color.
	 * 
	 * @return The preference key of this Color.
	 */
	public String getPreferenceKey() {
		return preferenceKey;
	}

	/**
	 * Returns the default RGB value of this Color.
	 * 
	 * @return The default RGB value of this Color.
	 */
	public RGB getDefault() {
		return defaultRGB;
	}

	/**
	 * Returns the color corresponding to the given preference key.
	 * 
	 * @param preferenceKey
	 *            Preference key for which we seek the color.
	 * @return The color corresponding to the given preference key.
	 */
	public static AcceleoColor getColor(String preferenceKey) {
		if (preferenceKey == null) {
			throw new NullPointerException();
		}

		for (AcceleoColor color : values()) {
			if (preferenceKey.equals(color.getPreferenceKey())) {
				return color;
			}
		}
		return null;
	}
}
