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
package org.eclipse.acceleo.internal.ide.ui.editors.template.utils;

/**
 * The list of the image available in the org.eclipse.acceleo.ide.ui bundle.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IAcceleoContantsImage {

	/**
	 * The root directory for all icons.
	 */
	String ICONS = "icons/"; //$NON-NLS-1$

	/**
	 * The 'Acceleo Active Region' icon.
	 */
	String ACCELEO_ACTIVE_REGION = ICONS + "AcceleoActiveRegion.gif"; //$NON-NLS-1$

	/**
	 * The 'Acceleo Editor' icon.
	 */
	String ACCELEO_EDITOR = ICONS + "AcceleoEditor.gif"; //$NON-NLS-1$

	/**
	 * The 'Acceleo Editor Main' icon.
	 */
	String ACCELEO_EDITOR_MAIN = ICONS + "AcceleoEditorMain.gif"; //$NON-NLS-1$

	/**
	 * The list of the image available for the quickfix.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 * @noextend This interface is not intended to be extended by clients.
	 * @noimplement This interface is not intended to be implemented by clients.
	 */
	public interface QuickFix {
		/**
		 * The root directory for all the icons for the quickfix.
		 */
		String QUICKFIX = ICONS + "quickfix/"; //$NON-NLS-1$

		/**
		 * The 'Quick fix create query' icon.
		 */
		String QUICK_FIX_CREATE_QUERY = QUICKFIX + "QuickFixCreateQuery.gif"; //$NON-NLS-1$

		/**
		 * The 'Quick fix create template' icon.
		 */
		String QUICK_FIX_CREATE_TEMPLATE = QUICKFIX + "QuickFixCreateTemplate.gif"; //$NON-NLS-1$
	}

	/**
	 * The list of the image available for the editor.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 * @noextend This interface is not intended to be extended by clients.
	 * @noimplement This interface is not intended to be implemented by clients.
	 */
	public interface TemplateEditor {

		/**
		 * The root directory for all the icons of the editor.
		 */
		String TEMPLATE_EDITOR = ICONS + "template-editor/"; //$NON-NLS-1$

		/**
		 * The 'AcceleoDoc browser' icon.
		 */
		String ACCELEODOC_BROWSER = TEMPLATE_EDITOR + "AcceleoDocBrowser.gif"; //$NON-NLS-1$

		/**
		 * The 'AcceleoDoc open declaration' icon.
		 */
		String ACCELEODOC_OPEN_DECLARATION = TEMPLATE_EDITOR + "AcceleoDocOpenDeclaration.gif"; //$NON-NLS-1$

		/**
		 * The 'AcceleoDoc open in view' icon.
		 */
		String ACCELEODOC_OPEN_IN_VIEW = TEMPLATE_EDITOR + "AcceleoDocOpenInView.png"; //$NON-NLS-1$

		/**
		 * The 'public query' icon.
		 */
		String QUERY_PUBLIC = TEMPLATE_EDITOR + "Query.gif"; //$NON-NLS-1$

		/**
		 * The 'protected query' icon.
		 */
		String QUERY_PROTECTED = TEMPLATE_EDITOR + "Query_protected.gif"; //$NON-NLS-1$

		/**
		 * The 'private query' icon.
		 */
		String QUERY_PRIVATE = TEMPLATE_EDITOR + "Query_private.gif"; //$NON-NLS-1$

		/**
		 * The list of the image available for the actions.
		 * 
		 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
		 * @noextend This interface is not intended to be extended by clients.
		 * @noimplement This interface is not intended to be implemented by clients.
		 */
		public interface Actions {
			/**
			 * The root directory for all icons of the actions.
			 */
			String ACTIONS = TEMPLATE_EDITOR + "actions/"; //$NON-NLS-1$

			/**
			 * The 'show whitespace characters action' icon.
			 */
			String SHOW_WHITESPACE_CHARACTERS_ACTION = "ShowWhitespaceCharactersAction.gif"; //$NON-NLS-1$
		}

		/**
		 * The list of the image available for the completion.
		 * 
		 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
		 * @noextend This interface is not intended to be extended by clients.
		 * @noimplement This interface is not intended to be implemented by clients.
		 */
		public interface Completion {

			/**
			 * The root directory for all icons of the completion.
			 */
			String COMPLETION = TEMPLATE_EDITOR + "completion/"; //$NON-NLS-1$

			/**
			 * The 'Enum Literal' icon.
			 */
			String ENUM_LITERAL = COMPLETION + "EnumLiteral.gif"; //$NON-NLS-1$

			/**
			 * The 'For Block' icon.
			 */
			String FOR_BLOCK = COMPLETION + "ForBlock.gif"; //$NON-NLS-1$

			/**
			 * The 'If Block' icon.
			 */
			String IF_BLOCK = COMPLETION + "IfBlock.gif"; //$NON-NLS-1$

			/**
			 * The 'keyword' icon.
			 */
			String KEYWORD = COMPLETION + "Keyword.gif"; //$NON-NLS-1$

			/**
			 * The 'public macro' icon.
			 */
			String MACRO_PUBLIC = COMPLETION + "Macro.gif"; //$NON-NLS-1$

			/**
			 * The 'module' icon.
			 */
			String MODULE = COMPLETION + "Module.gif"; //$NON-NLS-1$

			/**
			 * The 'operation' icon.
			 */
			String OPERATION = COMPLETION + "Operation.gif"; //$NON-NLS-1$

			/**
			 * The 'pattern' icon.
			 */
			String PATTERN = COMPLETION + "Pattern.gif"; //$NON-NLS-1$

			/**
			 * The 'property' icon.
			 */
			String PROPERTY = COMPLETION + "Property.gif"; //$NON-NLS-1$

			/**
			 * The 'public query' icon.
			 */
			String COMPLETION_QUERY_PUBLIC = COMPLETION + "Completion_Query.gif"; //$NON-NLS-1$

			/**
			 * The 'protected query' icon.
			 */
			String COMPLETION_QUERY_PROTECTED = COMPLETION + "Completion_Query_protected.gif"; //$NON-NLS-1$

			/**
			 * The 'private query' icon.
			 */
			String COMPLETION_QUERY_PRIVATE = COMPLETION + "Completion_Query_private.gif"; //$NON-NLS-1$

			/**
			 * The 'public template' icon.
			 */
			String TEMPLATE_PUBLIC = COMPLETION + "Template.gif"; //$NON-NLS-1$

			/**
			 * The 'protected template' icon.
			 */
			String TEMPLATE_PROTECTED = COMPLETION + "Template_protected.gif"; //$NON-NLS-1$

			/**
			 * The 'private template' icon.
			 */
			String TEMPLATE_PRIVATE = COMPLETION + "Template_private.gif"; //$NON-NLS-1$

			/**
			 * The 'type' icon.
			 */
			String TYPE = COMPLETION + "Type.gif"; //$NON-NLS-1$

			/**
			 * The 'URI' icon.
			 */
			String URI = COMPLETION + "URI.gif"; //$NON-NLS-1$

			/**
			 * The 'variable' icon.
			 */
			String VARIABLE = COMPLETION + "Variable.gif"; //$NON-NLS-1$
		}

		/**
		 * The list of the image available for the outline.
		 * 
		 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
		 * @noextend This interface is not intended to be extended by clients.
		 * @noimplement This interface is not intended to be implemented by clients.
		 */
		public interface Outline {
			/**
			 * The root directory for all icons for the outline.
			 */
			String OUTLINE = TEMPLATE_EDITOR + "outline/"; //$NON-NLS-1$

			/**
			 * The 'Hide non public' icon.
			 */
			String HIDE_NON_PUBLIC = OUTLINE + "HideNonPublic.png"; //$NON-NLS-1$

			/**
			 * The 'Hide queries' icon.
			 */
			String HIDE_QUERIES = OUTLINE + "HideQueries.gif"; //$NON-NLS-1$

			/**
			 * The 'Hide templates' icon.
			 */
			String HIDE_TEMPLATES = OUTLINE + "HideTemplates.gif"; //$NON-NLS-1$

			/**
			 * The 'Sort' icon.
			 */
			String SORT = OUTLINE + "Sort.gif"; //$NON-NLS-1$
		}
	}

}
