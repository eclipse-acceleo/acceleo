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
package org.eclipse.acceleo.common;

/**
 * Concrete syntax constants for text explicit mode only.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IAcceleoConstants {
	/**
	 * Extension of the lost files.
	 * 
	 * @since 0.8
	 */
	String ACCELEO_LOST_FILE_EXTENSION = ".lost"; //$NON-NLS-1$

	/**
	 * ID of the Acceleo Project Nature.
	 * 
	 * @since 0.8
	 */
	String ACCELEO_NATURE_ID = "org.eclipse.acceleo.ide.ui.acceleoNature"; //$NON-NLS-1$

	/**
	 * Extension of the concrete syntax file.
	 */
	String MTL_FILE_EXTENSION = "mtl"; //$NON-NLS-1$

	/**
	 * Extension of the abstract syntax file.
	 */
	String EMTL_FILE_EXTENSION = "emtl"; //$NON-NLS-1$

	/**
	 * Module block keyword.
	 * <p>
	 * [module ... /]
	 */
	String MODULE = "module"; //$NON-NLS-1$

	/**
	 * Comment block keyword.
	 * <p>
	 * [comment ... ] ... [/comment]
	 */
	String COMMENT = "comment"; //$NON-NLS-1$

	/**
	 * Import block keyword.
	 * <p>
	 * [import ... /]
	 */
	String IMPORT = "import"; //$NON-NLS-1$

	/**
	 * Extends keyword.
	 * <p>
	 * [module ... extends ... /]
	 * <p>
	 */
	String EXTENDS = "extends"; //$NON-NLS-1$

	/**
	 * Overrides keyword.
	 * <p>
	 * [template ... overrides ... ] ... [/template]
	 */
	String OVERRIDES = "overrides"; //$NON-NLS-1$

	/**
	 * Template block keyword.
	 * <p>
	 * [template ... ] ... [/template]
	 */
	String TEMPLATE = "template"; //$NON-NLS-1$

	/**
	 * Public visibility keyword.
	 * <p>
	 * [template public ... ] ... [/template]
	 */
	String VISIBILITY_KIND_PUBLIC = "public"; //$NON-NLS-1$

	/**
	 * Protected visibility keyword.
	 * <p>
	 * [template protected ... ] ... [/template]
	 */
	String VISIBILITY_KIND_PROTECTED = "protected"; //$NON-NLS-1$

	/**
	 * Private visibility keyword.
	 * <p>
	 * [template private ... ] ... [/template]
	 */
	String VISIBILITY_KIND_PRIVATE = "private"; //$NON-NLS-1$

	/**
	 * Query block keyword.
	 * <p>
	 * [query ... ] ... [/query]
	 */
	String QUERY = "query"; //$NON-NLS-1$

	/**
	 * For block keyword.
	 * <p>
	 * [for ... ] ... [/for]
	 */
	String FOR = "for"; //$NON-NLS-1$

	/**
	 * Before keyword.
	 * <p>
	 * [for ... before (...) ] ... [/for]
	 */
	String BEFORE = "before"; //$NON-NLS-1$

	/**
	 * After keyword.
	 * <p>
	 * [for ... after (...) ] ... [/for]
	 */
	String AFTER = "after"; //$NON-NLS-1$

	/**
	 * Separator keyword.
	 * <p>
	 * [for ... separator (...) ] ... [/for]
	 */
	String SEPARATOR = "separator"; //$NON-NLS-1$

	/**
	 * Guard keyword.
	 * <p>
	 * [template ... <b>?</b> (...) ] ... [/template]
	 */
	String GUARD = "?"; //$NON-NLS-1$

	/**
	 * Postcondition keyword.
	 * <p>
	 * [template ... <b>post</b> (...) ] ... [/template]
	 * 
	 * @since 3.0
	 */
	String POST = "post"; //$NON-NLS-1$

	/**
	 * If block keyword.
	 * <p>
	 * [if ... ] ... [/if]
	 */
	String IF = "if"; //$NON-NLS-1$

	/**
	 * Else If keyword.
	 * <p>
	 * [elseif ... /]
	 */
	String ELSE_IF = "elseif"; //$NON-NLS-1$

	/**
	 * Else keyword.
	 * <p>
	 * [else]
	 */
	String ELSE = "else"; //$NON-NLS-1$

	/**
	 * Let block keyword.
	 * <p>
	 * [let ... ] ... [/let]
	 */
	String LET = "let"; //$NON-NLS-1$

	/**
	 * Else Let keyword.
	 * <p>
	 * [elselet ... /]
	 */
	String ELSE_LET = "elselet"; //$NON-NLS-1$

	/**
	 * Trace block keyword.
	 * <p>
	 * [trace ... ] ... [/trace]
	 */
	String TRACE = "trace"; //$NON-NLS-1$

	/**
	 * Macro block keyword.
	 * <p>
	 * [macro ... ] ... [/macro]
	 */
	String MACRO = "macro"; //$NON-NLS-1$

	/**
	 * File block keyword.
	 * <p>
	 * [file ... ] ... [/file]
	 */
	String FILE = "file"; //$NON-NLS-1$

	/**
	 * Protected Area keyword.
	 * <p>
	 * [protected ... ] ... [/protected]
	 */
	String PROTECTED_AREA = "protected"; //$NON-NLS-1$

	/**
	 * Beginning delimiter of a literal expression.
	 * <p>
	 * <b>'</b>...'
	 */
	String LITERAL_BEGIN = "'"; //$NON-NLS-1$

	/**
	 * Ending delimiter of a literal expression.
	 * <p>
	 * '...<b>'</b>
	 */
	String LITERAL_END = "'"; //$NON-NLS-1$

	/**
	 * Escape character in a literal expression.
	 * <p>
	 * ' <b>\ '</b> '
	 */
	String LITERAL_ESCAPE = "\\\'"; //$NON-NLS-1$

	/**
	 * Beginning delimiter of an area which is not static.
	 * <p>
	 * <b>[</b> ... ]
	 */
	String DEFAULT_BEGIN = "["; //$NON-NLS-1$

	/**
	 * Ending delimiter of an area which is not static.
	 * <p>
	 * [ ... <b>]</b>
	 */
	String DEFAULT_END = "]"; //$NON-NLS-1$

	/**
	 * Ending delimiter of the header and the body.
	 * <p>
	 * [ ... <b>/</b>]
	 */
	String DEFAULT_END_BODY_CHAR = "/"; //$NON-NLS-1$

	/**
	 * Beginning delimiter of an invocation.
	 * <p>
	 * <b>[</b> ... ]
	 */
	String INVOCATION_BEGIN = DEFAULT_BEGIN;

	/**
	 * Ending delimiter of an invocation.
	 * <p>
	 * [ ... <b>/]</b>
	 */
	String[] INVOCATION_END = new String[] {DEFAULT_END_BODY_CHAR, DEFAULT_END, };

	/**
	 * Beginning delimiter of parenthesis.
	 * <p>
	 * <b>(</b> ... )
	 */
	String PARENTHESIS_BEGIN = "("; //$NON-NLS-1$

	/**
	 * Ending delimiter of parenthesis.
	 * <p>
	 * ( ... <b>)</b>
	 */
	String PARENTHESIS_END = ")"; //$NON-NLS-1$

	/**
	 * Beginning delimiter of brackets.
	 * <p>
	 * <b>{</b> ... }
	 */
	String BRACKETS_BEGIN = "{"; //$NON-NLS-1$

	/**
	 * Ending delimiter of brackets.
	 * <p>
	 * { ... <b>}</b>
	 */
	String BRACKETS_END = "}"; //$NON-NLS-1$

	/**
	 * Variable declaration separator between the name and the type.
	 * <p>
	 * c <b>:</b> Class
	 */
	String VARIABLE_DECLARATION_SEPARATOR = ":"; //$NON-NLS-1$

	/**
	 * Separator between the type and its namespace.
	 * <p>
	 * package <b>::</b> EClass
	 * 
	 * @since 3.0
	 */
	String NAMESPACE_SEPARATOR = "::"; //$NON-NLS-1$

	/**
	 * Variable declaration separator between the type and the init expression.
	 * <p>
	 * i : Integer <b>=</b> 0
	 */
	String VARIABLE_INIT_SEPARATOR = "="; //$NON-NLS-1$

	/**
	 * Comma separator between variables.
	 * <p>
	 * c1:Class <b>,</b> c2:Class
	 */
	String COMMA_SEPARATOR = ","; //$NON-NLS-1$

	/**
	 * Semicolon separator between variables.
	 * <p>
	 * c1:Class<b>;</b>
	 * <p>
	 * c2:Class<b>;</b>
	 */
	String SEMICOLON_SEPARATOR = ";"; //$NON-NLS-1$

	/**
	 * Pipe separator between the local variable and the expression in a for statement.
	 * <p>
	 * [for (c:Class <b>|</b> p->allClasses()) ] ... [/for]
	 */
	String PIPE_SEPARATOR = "|"; //$NON-NLS-1$

	/**
	 * OCL : To invoke a method on each object of a list.
	 * <p>
	 * p.allClasses.name
	 */
	String OCL_CALL_SIMPLE = "."; //$NON-NLS-1$

	/**
	 * OCL : To invoke a method on the whole list.
	 * <p>
	 * p.allClasses->size()
	 */
	String OCL_CALL_LIST = "->"; //$NON-NLS-1$

	/**
	 * OCL : The 'self' keyword.
	 */
	String SELF = "self"; //$NON-NLS-1$

	/**
	 * OCL : The 'super' keyword.
	 */
	String SUPER = "super"; //$NON-NLS-1$

	/**
	 * Comment/Documentation : Encoding keyword.
	 * <p>
	 * [comment encoding = UTF-8 /] or [**encoding = UTF-8 /]
	 * 
	 * @since 0.8
	 */
	String ENCODING = "encoding"; //$NON-NLS-1$

	/**
	 * Comment : The 'main' tag.
	 */
	String TAG_MAIN = "@main"; //$NON-NLS-1$

	/**
	 * Comment : The 'fixme' tag.
	 * 
	 * @since 3.1
	 */
	String TAG_FIXME = "@FIXME"; //$NON-NLS-1$

	/**
	 * Comment : The 'todo' tag.
	 * 
	 * @since 3.1
	 */
	String TAG_TODO = "@TODO"; //$NON-NLS-1$

	/**
	 * Documentation : The tag 'author'.
	 * 
	 * @since 3.1
	 */
	String TAG_AUTHOR = "@author"; //$NON-NLS-1$

	/**
	 * Documentation : The tag 'deprecated'.
	 * 
	 * @since 3.1
	 */
	String TAG_DEPRECATED = "@deprecated"; //$NON-NLS-1$

	/**
	 * Documentation : The tag 'inheritDoc'.
	 * 
	 * @since 3.1
	 */
	String TAG_INHERITDOC = "@inheritDoc"; //$NON-NLS-1$

	/**
	 * Documentation : The tag 'param'.
	 * 
	 * @since 3.1
	 */
	String TAG_PARAM = "@param"; //$NON-NLS-1$

	/**
	 * Documentation : The tag 'since'.
	 * 
	 * @since 3.1
	 */
	String TAG_SINCE = "@since"; //$NON-NLS-1$

	/**
	 * Documentation : The tag 'version'.
	 * 
	 * @since 3.1
	 */
	String TAG_VERSION = "@version"; //$NON-NLS-1$

	/**
	 * Beginning delimiter of a documentation area.
	 * <p>
	 * [<b>**</b> ...
	 * 
	 * @since 3.1
	 */
	String DOCUMENTATION_BEGIN = "**"; //$NON-NLS-1$

	/**
	 * Delimiter of the beginning of the indentation of a new line.
	 * <p>
	 * [**<br/>
	 * <b>*<br/>
	 * *<br/>
	 * *</b> ...
	 * 
	 * @since 3.1
	 */
	String DOCUMENTATION_NEW_LINE = "*"; //$NON-NLS-1$

	/**
	 * The XMI content type id.
	 * 
	 * @since 3.1
	 */
	String XMI_CONTENT_TYPE = "org.eclipse.acceleo.model.content.emtl.xmi"; //$NON-NLS-1$

	/**
	 * The binary content type id.
	 * 
	 * @since 3.1
	 */
	String BINARY_CONTENT_TYPE = "org.eclipse.acceleo.model.content.emtl.binary"; //$NON-NLS-1$

	/**
	 * The Java nature.
	 * 
	 * @since 3.1
	 */
	String JAVA_NATURE_ID = "org.eclipse.jdt.core.javanature"; //$NON-NLS-1$

	/**
	 * The plugin nature.
	 * 
	 * @since 3.1
	 */
	String PLUGIN_NATURE_ID = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

	/**
	 * The ecore file extension.
	 * 
	 * @since 3.1
	 */
	String ECORE_FILE_EXTENSION = "ecore"; //$NON-NLS-1$

	/**
	 * The name of the annotation used to disable dynamic modules.
	 * 
	 * @since 3.2
	 */
	String DISABLE_DYNAMIC_MODULES = "DISABLE_DYNAMIC_MODULES"; //$NON-NLS-1$

	/**
	 * The javadoc tag used by Java services classes to specify the nsURI.
	 * 
	 * @since 3.4
	 */
	String JAVADOC_TAG_NS_URI = "@nsURI"; //$NON-NLS-1$

	/**
	 * The name of the file where the full path of the file not to generate is located.
	 * 
	 * @since 3.4
	 */
	String DO_NOT_GENERATE_FILENAME = ".do_not_generate"; //$NON-NLS-1$
}
