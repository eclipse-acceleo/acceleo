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
package org.eclipse.acceleo.internal.ide.ui.wizards.module.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;

/**
 * To initialize automatically an Acceleo module file from an old MT template file, by copying and modifying
 * the text of the example into the new module.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class Acceleo2InitializationStrategy extends AbstractAcceleoInitializationStrategy {
	/**
	 * The mapping rules.
	 * <p>
	 * mappings[i] is the text before and mappings[i+1] is the text after.
	 * </p>
	 */
	private final String[] mappings = {"<% metamodel * %>", //$NON-NLS-1$
			"[comment metamodel $1/]", //$NON-NLS-1$

			"<% import * %>", //$NON-NLS-1$
			"[comment import $1/]", //$NON-NLS-1$

			"file = \" <%*%> \"" + END_LINE, //$NON-NLS-1$
			"file = \"$1\"", //$NON-NLS-1$

			"file = \" <%*%> *\"" + END_LINE, //$NON-NLS-1$
			"file = \"$1.concat($2)\"", //$NON-NLS-1$

			"<% script name = \"$\" type = \"$\" %>" + END_LINE, //$NON-NLS-1$
			"[template public $1 (v$2 : $2)]", //$NON-NLS-1$

			"<% script type = \"$\" name = \"$\" %>" + END_LINE, //$NON-NLS-1$
			"[template public $2 (v$1 : $1)]", //$NON-NLS-1$

			"<% script type = \"$\" name = \"$\" file = \"*\" %>" + END_LINE, //$NON-NLS-1$
			"[template public $2 (v$1 : $1)]\n" + "	[file ($3, false)]\n		[$2Sub()/]\n	[/file]\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ "\n[template private $2Sub (v$1 : $1)]", //$NON-NLS-1$

			"<% script name = \"$\" type = \"$\" file = \"*\" %>" + END_LINE, //$NON-NLS-1$
			"[template public $1 (v$2 : $2)]\n" + "	[file ($3, false)]\n		[$1Sub()/]\n	[/file]\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ "\n[template private $1Sub (v$2 : $2)]", //$NON-NLS-1$

			"<% script type = \"$\" name = \"$\" file = \"*\" post = \"*\" %>" + END_LINE, //$NON-NLS-1$
			"[template public $2 (v$1 : $1)]\n" + "	[file ($3, false)]\n		[$2Sub().$4/]\n	[/file]\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ "\n[template private $2Sub (v$1 : $1)]", //$NON-NLS-1$

			"<% script name = \"$\" type = \"$\" file = \"*\" post = \"*\" %>" + END_LINE, //$NON-NLS-1$
			"[template public $1 (v$2 : $2)]\n" + "	[file ($3, false)]\n		[$1Sub().$4/]\n	[/file]\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ "\n[template private $1Sub (v$2 : $2)]", //$NON-NLS-1$

			"<% script name = \"$\" type = \"$\" * %>" + END_LINE, "[template public $1 (v$2 : $2)$3]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% script type = \"$\" name = \"$\" * %>" + END_LINE, "[template public $2 (v$1 : $1)$3]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% for (*.filter(\"$\")*) { %>" + END_LINE, "[for (v$2 : $2 | $1.oclAsType($2)$3)]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% for (*) { %>" + END_LINE, "[for (v : EObject | $1)]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% for * { %>" + END_LINE, "[for $1]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% if * { %>" + END_LINE, "[if $1]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% } else if * { %>" + END_LINE, "[else if $1]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% } else { %>" + END_LINE, "[else]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% startUserCode * %>" + END_LINE, "[protected ('TODO')]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% endUserCode * %>" + END_LINE, "[/protected]", //$NON-NLS-1$ //$NON-NLS-2$

			"<%-- * --%>" + END_LINE, "[comment $1 /]", //$NON-NLS-1$ //$NON-NLS-2$

			"<% * %>" + END_LINE, "[$1/]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*&&*]" + END_LINE, "[$1and $2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*||*]" + END_LINE, "[$1or $2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*!=*]" + END_LINE, "[$1<> $2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*==*]" + END_LINE, "[$1== $2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*\"*\"*\"*\"*]" + END_LINE, "[$1'$2'$3'$4'$5]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*\"*\"*]" + END_LINE, "[$1'$2'$3]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*.sep(\"*\")*]" + END_LINE, "[$1 separator('$2')$3]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*current(\"$\")*]" + END_LINE, "[$1v$2$3]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*current()*]" + END_LINE, "[$1v$2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*toLowerCase()*]" + END_LINE, "[$1toLower()$2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*toUpperCase()*]" + END_LINE, "[$1toUpper()$2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*toU1Case()*]" + END_LINE, "[$1toUpperFirst()$2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*toL1Case()*]" + END_LINE, "[$1toLowerFirst()$2]", //$NON-NLS-1$ //$NON-NLS-2$

			"[*filter(\"$\")*]" + END_LINE, "[$1oclAsType($2)$3]", }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("MTContentStrategy.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getInitialFileNameFilter()
	 */
	public String getInitialFileNameFilter() {
		return "*.mt "; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.AbstractM2TContentStrategy#
	 *      modifyM2TContent(java.lang.StringBuffer, java.lang.String)
	 */
	@Override
	protected void modifyM2TContent(StringBuffer text, String moduleName) {
		for (int i = 0; i < mappings.length - 1; i += 2) {
			String initialString = mappings[i];
			String replacingString = mappings[i + 1];
			replaceAll(text, initialString, replacingString);
		}
		int offset = 0;
		while (offset > -1) {
			offset = replaceNext(text, "[ } /]" + END_LINE, offset, ""); //$NON-NLS-1$ //$NON-NLS-2$
			if (offset > -1) {
				int beginLine = text.lastIndexOf("\n", offset) + 1; //$NON-NLS-1$
				String tab;
				if (beginLine > -1 && offset < text.length() && beginLine < offset) {
					tab = text.substring(beginLine, offset);
					if (tab.trim().length() != 0) {
						tab = ""; //$NON-NLS-1$
					}
				} else {
					tab = ""; //$NON-NLS-1$
				}
				int offsetIf = text.lastIndexOf('\n' + tab + "[if", offset); //$NON-NLS-1$
				int offsetFor = text.lastIndexOf('\n' + tab + "[for", offset); //$NON-NLS-1$
				if (offsetIf == -1 && offsetFor == -1) {
					offsetIf = text.lastIndexOf("[if", offset); //$NON-NLS-1$
					offsetFor = text.lastIndexOf("[for", offset); //$NON-NLS-1$
				}
				String newText;
				if (offsetFor > offsetIf) {
					newText = "[/for]"; //$NON-NLS-1$
				} else {
					newText = "[/if]"; //$NON-NLS-1$
				}
				text.insert(offset, newText);
				offset += newText.length();
			}
		}
		offset = 0;
		final String templateBeginTag = "[template"; //$NON-NLS-1$
		offset = replaceNext(text, templateBeginTag, offset, templateBeginTag);
		if (offset > -1) {
			while (offset > -1) {
				offset = replaceNext(text, templateBeginTag, offset, ""); //$NON-NLS-1$
				if (offset > -1) {
					if (offset > 0 && text.charAt(offset - 1) == '\n') {
						offset--;
						text.deleteCharAt(offset);
					}
					String newText = "[/template]\n\n" + templateBeginTag; //$NON-NLS-1$
					text.insert(offset, newText);
					offset += newText.length();
				}
			}
			text.append("\n[/template]"); //$NON-NLS-1$

		}
		offset = 0;
		while (offset > -1) {
			offset = replaceNext(text, "[file (", offset, "[comment @main /]\n\t[file ("); //$NON-NLS-1$ //$NON-NLS-2$
		}
		modifyTemplateInvocation(text, "\\[[a-zA-Z_]+"); //$NON-NLS-1$
		modifyTemplateInvocation(text, "\\.a-zA-Z_]+"); //$NON-NLS-1$
		text.insert(0, "[module " + moduleName + "('" + getMetamodelURI(text) + "')/]\n\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Modify a template invocation by adding "()" before " /]".
	 * 
	 * @param text
	 *            is the text to modify
	 * @param pattern
	 *            is the pattern to search
	 */
	private void modifyTemplateInvocation(StringBuffer text, String pattern) {
		int offset;
		Pattern p = Pattern.compile(pattern + "[ ]*/\\]"); //$NON-NLS-1$
		Matcher m = p.matcher(text);
		offset = 0;
		while (offset > -1 && m.find(offset)) {
			int b = m.start();
			offset = m.end();
			String string = text.substring(b, offset).trim();
			Pattern pID = Pattern.compile("[a-zA-Z_]+"); //$NON-NLS-1$
			Matcher mID = pID.matcher(string);
			if (mID.find()) {
				string = string.substring(mID.start(), mID.end());
			}
			if (text.indexOf("public " + string + " (") > -1 || text.indexOf("public " + string + "(") > -1) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				text.insert(offset - 2, "()"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Gets the meta-model URI.
	 * 
	 * @param text
	 *            is the text
	 * @return the meta-model URI
	 */
	private String getMetamodelURI(StringBuffer text) {
		int iMetamodel = text.indexOf("metamodel"); //$NON-NLS-1$
		int iImport = text.indexOf("import"); //$NON-NLS-1$
		int b;
		if (iMetamodel > -1 && iMetamodel < iImport) {
			b = iMetamodel + "metamodel".length(); //$NON-NLS-1$
		} else if (iImport > -1) {
			b = iImport + "import".length(); //$NON-NLS-1$
		} else {
			b = 0;
		}
		int e = text.indexOf("\n", b); //$NON-NLS-1$
		if (e == -1) {
			e = text.length();
		}
		return text.substring(b, e).trim();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceQuery()
	 */
	public boolean forceQuery() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceTemplate()
	 */
	public boolean forceTemplate() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceDocumentation()
	 */
	public boolean forceDocumentation() {
		return true;
	}
}
