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
package org.eclipse.acceleo.aql.completion.proposals.templates;

import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.parser.AcceleoParser;

/**
 * Code templates are pre-made, usually syntactically valid, snippets that can be inserted easily by the user
 * through auto-completion. <br/>
 * TODO: for now they are provided as pure Strings, but we may need something more advanced so we can place
 * the editor cursor at a specific location.
 * 
 * @author Florent Latombe
 */
public final class AcceleoCodeTemplates {

	/**
	 * A space.
	 */
	public static final String SPACE = " ";

	/**
	 * A new line.
	 */
	public static final String NEWLINE = "\n";

	/**
	 * A tabulation.
	 */
	public static final String TABULATION = "\t";

	/**
	 * An asterisk.
	 */
	public static final String STAR = "*";

	/**
	 * Default expression.
	 */
	public static final String DEFAULT_EXPRESSION = "expression";

	// New ModuleElement
	/**
	 * The name of the parameter of a {@link ModuleElement} (query or template).
	 */
	public static final String DEFAULT_NEW_MODULE_ELEMENT_VARIABLE_NAME = "myParameter";
	////

	// New Empty Query
	/**
	 * The visibility of the query.
	 */
	public static final VisibilityKind DEFAULT_NEW_QUERY_VISIBILITY = VisibilityKind.PUBLIC;

	/**
	 * The name of the query.
	 */
	public static final String DEFAULT_NEW_QUERY_NAME = "myQuery";

	/**
	 * The name of the parameter of the query.
	 */
	public static final String DEFAULT_NEW_QUERY_PARAMETER_NAME = DEFAULT_NEW_MODULE_ELEMENT_VARIABLE_NAME;

	/**
	 * The type of the parameter of the query.
	 */
	public static final String DEFAULT_NEW_QUERY_PARAMETER_TYPE = "ecore::EJavaObject";

	/**
	 * The return type of the parameter of the query.
	 */
	public static final String DEFAULT_NEW_QUERY_RETURN_TYPE = "String";

	/**
	 * The body of the query.
	 */
	public static final String DEFAULT_NEW_QUERY_BODY = DEFAULT_NEW_QUERY_PARAMETER_NAME + ".toString()";
	////

	// New Empty Template
	/**
	 * The visibility of the template.
	 */
	public static final VisibilityKind DEFAULT_NEW_TEMPLATE_VISIBILITY = VisibilityKind.PUBLIC;

	/**
	 * The name of the template.
	 */
	public static final String DEFAULT_NEW_TEMPLATE_NAME = "myTemplate";

	/**
	 * The name of the parameter of the template.
	 */
	public static final String DEFAULT_NEW_TEMPLATE_PARAMETER_NAME = DEFAULT_NEW_MODULE_ELEMENT_VARIABLE_NAME;

	/**
	 * The type of the parameter of the template.
	 */
	public static final String DEFAULT_NEW_TEMPLATE_PARAMETER_TYPE = "MyParameterType";

	/**
	 * The body of the template.
	 */
	public static final String DEFAULT_NEW_TEMPLATE_BODY = "My template with a static text.";
	////

	// New Comment
	/**
	 * The body of the comment.
	 */
	public static final String DEFAULT_NEW_COMMENT_BODY = "My comment.";
	////

	// New Module Documentation
	/**
	 * The body of the documentation.
	 */
	public static final String DEFAULT_NEW_MODULE_DOCUMENTATION_BODY = "My module documentation.";

	/**
	 * The {@code @author} value of the documentation.
	 */
	public static final String DEFAULT_NEW_MODULE_DOCUMENTATION_AUTHOR = System.getProperty("user.name");

	/**
	 * The {@code @version} value of the documentation.
	 */
	public static final String DEFAULT_NEW_MODULE_DOCUMENTATION_VERSION = "0.0.1";

	/**
	 * The {@code @since} value of the documentation.
	 */
	public static final String DEFAULT_NEW_MODULE_DOCUMENTATION_SINCE = "0.0.1";
	////

	// New Module Element Documentation
	/**
	 * The body of the documentation.
	 */
	public static final String DEFAULT_NEW_MODULE_ELEMENT_DOCUMENTATION_BODY = "My Query or Template documentation.";

	/**
	 * The {@code @param} value of the documentation.
	 */
	public static final String DEFAULT_NEW_MODULE_ELEMENT_DOCUMENTATION_PARAM_BODY = "the parameter.";
	////

	// New Import
	/**
	 * The body of the import.
	 */
	public static final String DEFAULT_NEW_IMPORT_BODY = "imported::qualified::name";
	////

	// New Module
	/**
	 * The name of the module.
	 */
	public static final String DEFAULT_NEW_MODULE_NAME = "myModule";

	/**
	 * The nsURI of the metamodel.
	 */
	public static final String DEFAULT_NEW_MODULE_NSURI = "http://www.eclipse.org/emf/2002/Ecore";
	////

	// New Binding
	/**
	 * The name of the variable.
	 */
	public static final String DEFAULT_NEW_BINDING_VARIABLE_NAME = "myVariable";

	/**
	 * The type literal of the variable.
	 */
	public static final String DEFAULT_NEW_BINDING_VARIABLE_TYPE = "String";
	////

	/**
	 * Code template for creating a new empty Acceleo Query.<br/>
	 * <code>
	 * [query public myQuery(myParameter : ecore::EJavaObject) : String = myParameter.toString() /]
	 * </code>
	 */
	public static final String NEW_QUERY = AcceleoParser.QUERY_START + DEFAULT_NEW_QUERY_VISIBILITY + SPACE
			+ DEFAULT_NEW_QUERY_NAME + "(" + DEFAULT_NEW_QUERY_PARAMETER_NAME + " : "
			+ DEFAULT_NEW_QUERY_PARAMETER_TYPE + ") : " + DEFAULT_NEW_QUERY_RETURN_TYPE + " = "
			+ DEFAULT_NEW_QUERY_BODY + AcceleoParser.QUERY_END;

	/**
	 * Code template for creating a new empty Acceleo Template.<br/>
	 * <code>
	 * [template public myTemplate(myParameter : MyParameterType)]
	 *     My template with a static text.
	 * [/template]
	 * </code>
	 */
	public static final String NEW_TEMPLATE = AcceleoParser.TEMPLATE_HEADER_START
			+ DEFAULT_NEW_TEMPLATE_VISIBILITY + SPACE + DEFAULT_NEW_TEMPLATE_NAME + "("
			+ DEFAULT_NEW_TEMPLATE_PARAMETER_NAME + " : " + DEFAULT_NEW_TEMPLATE_PARAMETER_TYPE + ")"
			+ AcceleoParser.TEMPLATE_HEADER_END + NEWLINE + TABULATION + DEFAULT_NEW_TEMPLATE_BODY + NEWLINE
			+ AcceleoParser.TEMPLATE_END;

	/**
	 * Code template for creating a new empty Acceleo Module.<br/>
	 * <code>
	 * [module public myModule('http://www.eclipse.org/emf/2002/Ecore')/]
	 * </code>
	 */
	public static final String NEW_MODULE = AcceleoParser.MODULE_HEADER_START + DEFAULT_NEW_MODULE_NAME + "('"
			+ DEFAULT_NEW_MODULE_NSURI + "')" + AcceleoParser.MODULE_HEADER_END;

	/**
	 * Code template for creating a new Acceleo comment.<br/>
	 * <code>
	 * [comment My comment./]
	 * </code>
	 */
	public static final String NEW_COMMENT = AcceleoParser.COMMENT_START + DEFAULT_NEW_COMMENT_BODY
			+ AcceleoParser.COMMENT_END;

	/**
	 * Code template for creating a new Acceleo {@link BlockComment}.<br/>
	 * <code>
	 * [comment]
	 * My comment.
	 * [/comment]
	 * </code>
	 */
	public static final String NEW_BLOCK_COMMENT = AcceleoParser.BLOCK_COMMENT_START + NEWLINE
			+ DEFAULT_NEW_COMMENT_BODY + NEWLINE + AcceleoParser.BLOCK_COMMENT_END;

	/**
	 * Code template for creating a new Acceleo main comment.<br/>
	 * <code>
	 * [comment @main/]
	 * </code>
	 */
	public static final String NEW_COMMENT_MAIN = AcceleoParser.COMMENT_START + AcceleoParser.MAIN_TAG
			+ AcceleoParser.COMMENT_END;

	/**
	 * Code template for creating a new Acceleo {@link ForStatement}.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [for (myVariable | expression)]
	 *   
	 * [/for]
	 * </code>
	 * </pre>
	 */
	public static final String NEW_FOR_STATEMENT = AcceleoParser.FOR_HEADER_START + SPACE
			+ AcceleoParser.OPEN_PARENTHESIS + DEFAULT_NEW_BINDING_VARIABLE_NAME + SPACE + AcceleoParser.PIPE
			+ SPACE + DEFAULT_EXPRESSION + AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.FOR_HEADER_END
			+ NEWLINE + SPACE + SPACE + NEWLINE + AcceleoParser.FOR_END;

	/**
	 * Code template for creating a new Acceleo {@link IfStatement}.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [if (expression)]
	 *   
	 * [/if]
	 * </code>
	 * </pre>
	 */
	public static final String NEW_IF_STATEMENT = AcceleoParser.IF_HEADER_START + SPACE
			+ AcceleoParser.OPEN_PARENTHESIS + DEFAULT_EXPRESSION + AcceleoParser.CLOSE_PARENTHESIS
			+ AcceleoParser.IF_HEADER_END + NEWLINE + SPACE + SPACE + NEWLINE + AcceleoParser.IF_END;

	/**
	 * Code template for creating a new Acceleo {@link FileStatement}.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [file (expression, overwrite, 'UTF-8')]
	 *   
	 * [/file]
	 * </code>
	 * </pre>
	 */
	public static final String NEW_FILE_STATEMENT = AcceleoParser.FILE_HEADER_START + SPACE
			+ AcceleoParser.OPEN_PARENTHESIS + DEFAULT_EXPRESSION + AcceleoParser.COMMA + SPACE
			+ OpenModeKind.OVERWRITE.getName() + AcceleoParser.COMMA + SPACE + "'UTF-8'"
			+ AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.FILE_HEADER_END + NEWLINE + SPACE + SPACE
			+ NEWLINE + AcceleoParser.FILE_END;

	/**
	 * Code template for creating a new Acceleo {@link ProtectedArea}.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [protected (expression)]
	 *   
	 * [/protected]
	 * </code>
	 * </pre>
	 */
	public static final String NEW_PROTECTED_AREA_STATEMENT = AcceleoParser.PROTECTED_AREA_HEADER_START
			+ SPACE + AcceleoParser.OPEN_PARENTHESIS + DEFAULT_EXPRESSION + AcceleoParser.CLOSE_PARENTHESIS
			+ AcceleoParser.PROTECTED_AREA_HEADER_END + NEWLINE + SPACE + SPACE + NEWLINE
			+ AcceleoParser.PROTECTED_AREA_END;

	/**
	 * Code template for creating a new Acceleo {@link ForStatement}.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [let myVariable = expression]
	 *   
	 * [/let]
	 * </code>
	 * </pre>
	 */
	public static final String NEW_LET_STATEMENT = AcceleoParser.LET_HEADER_START + SPACE
			+ DEFAULT_NEW_BINDING_VARIABLE_NAME + SPACE + AcceleoParser.EQUAL + SPACE + DEFAULT_EXPRESSION
			+ AcceleoParser.LET_HEADER_END + NEWLINE + SPACE + SPACE + NEWLINE + AcceleoParser.LET_END;

	/**
	 * Code template for creating a new Acceleo module documentation.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [**
	 *  * My module documentation.
	 *  * 
	 *  * @author {@literal <}user.name{@literal >}
	 *  * @version 0.0.1
	 *  * @since 0.0.1
	 * *{@literal /}]
	 * </code>
	 * </pre>
	 */
	public static final String NEW_MODULE_DOCUMENTATION = AcceleoParser.DOCUMENTATION_START + NEWLINE + SPACE
			+ STAR + SPACE + DEFAULT_NEW_MODULE_DOCUMENTATION_BODY + NEWLINE + SPACE + STAR + SPACE + NEWLINE
			+ SPACE + STAR + SPACE + AcceleoParser.AUTHOR_TAG + DEFAULT_NEW_MODULE_DOCUMENTATION_AUTHOR
			+ NEWLINE + SPACE + STAR + SPACE + AcceleoParser.VERSION_TAG
			+ DEFAULT_NEW_MODULE_DOCUMENTATION_VERSION + NEWLINE + SPACE + STAR + SPACE
			+ AcceleoParser.SINCE_TAG + DEFAULT_NEW_MODULE_DOCUMENTATION_SINCE + NEWLINE + STAR
			+ AcceleoParser.DOCUMENTATION_END;

	/**
	 * Code template for creating a new import.<br/>
	 * <code>
	 * [import imported::module::qualified::name/]
	 * </code>
	 */
	public static final String NEW_IMPORT = AcceleoParser.IMPORT_START + DEFAULT_NEW_IMPORT_BODY
			+ AcceleoParser.IMPORT_END;

	/**
	 * Code template for creating a new module element documentation.<br/>
	 * 
	 * <pre>
	 * <code>
	 * [**
	 *  * My Query or Template documentation.
	 *  * 
	 *  * @param myParameter the parameter.
	 * *{@literal /}] 
	 * </code>
	 * </pre>
	 */
	public static final String NEW_MODULE_ELEMENT_DOCUMENTATION = AcceleoParser.DOCUMENTATION_START + NEWLINE
			+ SPACE + STAR + SPACE + DEFAULT_NEW_MODULE_ELEMENT_DOCUMENTATION_BODY + NEWLINE + SPACE + STAR
			+ SPACE + NEWLINE + SPACE + STAR + SPACE + AcceleoParser.PARAM_TAG
			+ DEFAULT_NEW_MODULE_ELEMENT_VARIABLE_NAME + SPACE
			+ DEFAULT_NEW_MODULE_ELEMENT_DOCUMENTATION_PARAM_BODY + NEWLINE + STAR
			+ AcceleoParser.DOCUMENTATION_END;

	private AcceleoCodeTemplates() {
		// Utility class.
	}
}
