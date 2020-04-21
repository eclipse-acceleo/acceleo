/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion;

import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.parser.AcceleoParser;

/**
 * Code templates are pre-made, usually syntactically valid, snippets that can be inserted easily by the user
 * through auto-completion. <br/>
 * FIXME: for now they are provided as pure Strings, but we may need something more advanced so we can place
 * the editor curser at a specific location.
 * 
 * @author Florent Latombe
 */
public final class AcceleoCodeTemplates {

	/**
	 * A space.
	 */
	private static final String SPACE = " ";

	// New Empty Query
	/**
	 * The visibility of the query.
	 */
	private static final VisibilityKind DEFAULT_NEW_QUERY_VISIBILITY = VisibilityKind.PUBLIC;

	/**
	 * The name of the query.
	 */
	private static final String DEFAULT_NEW_QUERY_NAME = "myQuery";

	/**
	 * The name of the parameter of the query.
	 */
	private static final String DEFAULT_NEW_QUERY_PARAMETER_NAME = "myParameter";

	/**
	 * The type of the parameter of the query.
	 */
	private static final String DEFAULT_NEW_QUERY_PARAMETER_TYPE = "MyParameterType";

	/**
	 * The return type of the parameter of the query.
	 */
	private static final String DEFAULT_NEW_QUERY_RETURN_TYPE = "MyQueryReturnType";

	/**
	 * The body of the query.
	 */
	private static final String DEFAULT_NEW_QUERY_BODY = "null";
	////

	// New Empty Template
	/**
	 * The visibility of the template.
	 */
	private static final VisibilityKind DEFAULT_NEW_TEMPLATE_VISIBILITY = VisibilityKind.PUBLIC;

	/**
	 * The name of the template.
	 */
	private static final String DEFAULT_NEW_TEMPLATE_NAME = "myTemplate";

	/**
	 * The name of the parameter of the template.
	 */
	private static final String DEFAULT_NEW_TEMPLATE_PARAMETER_NAME = "myParameter";

	/**
	 * The type of the parameter of the template.
	 */
	private static final String DEFAULT_NEW_TEMPLATE_PARAMETER_TYPE = "MyParameterType";

	/**
	 * The body of the template.
	 */
	private static final String DEFAULT_NEW_TEMPLATE_BODY = "My template with a static text.";

	/**
	 * Code template for creating a new empty Acceleo Query.<br/>
	 * <code>
	 * [query public myQuery(myParameter : MyParameterType) : MyQueryReturnType = null /]
	 * </code>
	 */
	public static final String CODE_TEMPLATE_NEW_ACCELEO_QUERY = AcceleoParser.QUERY_START
			+ DEFAULT_NEW_QUERY_VISIBILITY + SPACE + DEFAULT_NEW_QUERY_NAME + "("
			+ DEFAULT_NEW_QUERY_PARAMETER_NAME + " : " + DEFAULT_NEW_QUERY_PARAMETER_TYPE + ") : "
			+ DEFAULT_NEW_QUERY_RETURN_TYPE + " = " + DEFAULT_NEW_QUERY_BODY + AcceleoParser.QUERY_END;

	/**
	 * Code template for creating a new empty Acceleo Template.<br/>
	 * <code>
	 * [template public myTemplate(myParameter : MyParameterType)]
	 *     My template with a static text.
	 * [/template]
	 * </code>
	 */
	public static final String CODE_TEMPLATE_NEW_ACCELEO_TEMPLATE = AcceleoParser.TEMPLATE_HEADER_START
			+ SPACE + DEFAULT_NEW_TEMPLATE_VISIBILITY + SPACE + DEFAULT_NEW_TEMPLATE_NAME + "("
			+ DEFAULT_NEW_TEMPLATE_PARAMETER_NAME + " : " + DEFAULT_NEW_TEMPLATE_PARAMETER_TYPE + ")"
			+ AcceleoParser.TEMPLATE_HEADER_END + "\n" + "\t" + DEFAULT_NEW_TEMPLATE_BODY + "\n"
			+ AcceleoParser.TEMPLATE_END;

	private AcceleoCodeTemplates() {
		// Utility class.
	}
}
