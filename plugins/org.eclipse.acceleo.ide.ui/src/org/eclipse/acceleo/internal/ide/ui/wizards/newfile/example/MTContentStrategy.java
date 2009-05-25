/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;

/**
 * To initialize automatically an Acceleo template file from an old MT template file, by copying and modifying
 * the text of the example into the new template.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class MTContentStrategy extends AbstractM2TContentStrategy {

	/**
	 * The mapping rules.
	 * <p>
	 * mappings[i] is the text before and mappings[i+1] is the text after.
	 * </p>
	 */
	private final String[] mappings = {
			"<% metamodel * %>",
			"[comment metamodel $1/]",

			"<% import * %>",
			"[comment import $1/]",

			"file = \" <%*%> \"" + END_LINE,
			"file = \"$1\"",

			"file = \" <%*%> *\"" + END_LINE,
			"file = \"$1.concat($2)\"",

			"<% script name = \"$\" type = \"$\" %>" + END_LINE,
			"[template public $1 (v$2 : $2)]",

			"<% script type = \"$\" name = \"$\" %>" + END_LINE,
			"[template public $2 (v$1 : $1)]",

			"<% script type = \"$\" name = \"$\" file = \"*\" %>" + END_LINE,
			"[template public $2 (v$1 : $1)]\n" + "	[file ($3, false)]\n		[$2Sub()/]\n	[/file]\n"
					+ "\n[template private $2Sub (v$1 : $1)]",

			"<% script name = \"$\" type = \"$\" file = \"*\" %>" + END_LINE,
			"[template public $1 (v$2 : $2)]\n" + "	[file ($3, false)]\n		[$1Sub()/]\n	[/file]\n"
					+ "\n[template private $1Sub (v$2 : $2)]",

			"<% script type = \"$\" name = \"$\" file = \"*\" post = \"*\" %>" + END_LINE,
			"[template public $2 (v$1 : $1)]\n" + "	[file ($3, false)]\n		[$2Sub().$4/]\n	[/file]\n"
					+ "\n[template private $2Sub (v$1 : $1)]",

			"<% script name = \"$\" type = \"$\" file = \"*\" post = \"*\" %>" + END_LINE,
			"[template public $1 (v$2 : $2)]\n" + "	[file ($3, false)]\n		[$1Sub().$4/]\n	[/file]\n"
					+ "\n[template private $1Sub (v$2 : $2)]",

			"<% script name = \"$\" type = \"$\" * %>" + END_LINE, "[template public $1 (v$2 : $2)$3]",

			"<% script type = \"$\" name = \"$\" * %>" + END_LINE, "[template public $2 (v$1 : $1)$3]",

			"<% for (*.filter(\"$\")*) { %>" + END_LINE, "[for (v$2 : $2 | $1.oclAsType($2)$3)]",

			"<% for (*) { %>" + END_LINE, "[for (v : EObject | $1)]",

			"<% for * { %>" + END_LINE, "[for $1]",

			"<% if * { %>" + END_LINE, "[if $1]",

			"<% } else if * { %>" + END_LINE, "[else if $1]",

			"<% } else { %>" + END_LINE, "[else]",

			"<% startUserCode * %>" + END_LINE, "[protected ('TODO')]",

			"<% endUserCode * %>" + END_LINE, "[/protected]",

			"<%-- * --%>" + END_LINE, "[comment $1 /]",

			"<% * %>" + END_LINE, "[$1/]",

			"[*&&*]" + END_LINE, "[$1and $2]",

			"[*||*]" + END_LINE, "[$1or $2]",

			"[*!=*]" + END_LINE, "[$1<> $2]",

			"[*==*]" + END_LINE, "[$1== $2]",

			"[*\"*\"*\"*\"*]" + END_LINE, "[$1'$2'$3'$4'$5]",

			"[*\"*\"*]" + END_LINE, "[$1'$2'$3]",

			"[*.sep(\"*\")*]" + END_LINE, "[$1 separator('$2')$3]",

			"[*current(\"$\")*]" + END_LINE, "[$1v$2$3]",

			"[*current()*]" + END_LINE, "[$1v$2]",

			"[*toLowerCase()*]" + END_LINE, "[$1toLower()$2]",

			"[*toUpperCase()*]" + END_LINE, "[$1toUpper()$2]",

			"[*toU1Case()*]" + END_LINE, "[$1toUpperFirst()$2]",

			"[*toL1Case()*]" + END_LINE, "[$1toLowerFirst()$2]",

			"[*filter(\"$\")*]" + END_LINE, "[$1oclAsType($2)$3]", };

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
		return "*.mt"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.AbstractM2TContentStrategy#modifyM2TContent(java.lang.StringBuffer,
	 *      boolean)
	 */
	@Override
	protected void modifyM2TContent(StringBuffer text, String moduleName, boolean templateIsMain) {
		for (int i = 0; i < mappings.length - 1; i += 2) {
			String initialString = mappings[i];
			String replacingString = mappings[i + 1];
			replaceAll(text, initialString, replacingString);
		}
		int offset = 0;
		while (offset > -1) {
			offset = replaceNext(text, "[ } /]" + END_LINE, offset, "");
			if (offset > -1) {
				int beginLine = text.lastIndexOf("\n", offset) + 1;
				String tab;
				if (beginLine > -1 && offset < text.length() && beginLine < offset) {
					tab = text.substring(beginLine, offset);
					if (tab.trim().length() != 0) {
						tab = "";
					}
				} else {
					tab = "";
				}
				int offsetIf = text.lastIndexOf('\n' + tab + "[if", offset);
				int offsetFor = text.lastIndexOf('\n' + tab + "[for", offset);
				if (offsetIf == -1 && offsetFor == -1) {
					offsetIf = text.lastIndexOf("[if", offset);
					offsetFor = text.lastIndexOf("[for", offset);
				}
				String newText;
				if (offsetFor > offsetIf) {
					newText = "[/for]";
				} else {
					newText = "[/if]";
				}
				text.insert(offset, newText);
				offset += newText.length();
			}
		}
		offset = 0;
		final String templateBeginTag = "[template";
		offset = replaceNext(text, templateBeginTag, offset, templateBeginTag);
		if (offset > -1) {
			while (offset > -1) {
				offset = replaceNext(text, templateBeginTag, offset, "");
				if (offset > -1) {
					if (offset > 0 && text.charAt(offset - 1) == '\n') {
						offset--;
						text.deleteCharAt(offset);
					}
					String newText = "[/template]\n\n" + templateBeginTag;
					text.insert(offset, newText);
					offset += newText.length();
				}
			}
			text.append("\n[/template]");

		}
		if (templateIsMain) {
			offset = 0;
			while (offset > -1) {
				offset = replaceNext(text, "[file (", offset, "[comment @main /]\n\t[file (");
			}
		}
		modifyTemplateInvocation(text, "\\[[a-zA-Z_]+");
		modifyTemplateInvocation(text, "\\.a-zA-Z_]+");
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
		Pattern p = Pattern.compile(pattern + "[ ]*/\\]");
		Matcher m = p.matcher(text);
		offset = 0;
		while (offset > -1 && m.find(offset)) {
			int b = m.start();
			offset = m.end();
			String string = text.substring(b, offset).trim();
			Pattern pID = Pattern.compile("[a-zA-Z_]+");
			Matcher mID = pID.matcher(string);
			if (mID.find()) {
				string = string.substring(mID.start(), mID.end());
			}
			if (text.indexOf("public " + string + " (") > -1 || text.indexOf("public " + string + "(") > -1) {
				text.insert(offset - 2, "()");
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
		int iMetamodel = text.indexOf("metamodel");
		int iImport = text.indexOf("import");
		int b;
		if (iMetamodel > -1 && iMetamodel < iImport) {
			b = iMetamodel + "metamodel".length();
		} else if (iImport > -1) {
			b = iImport + "import".length();
		} else {
			b = 0;
		}
		int e = text.indexOf("\n", b);
		if (e == -1) {
			e = text.length();
		}
		return text.substring(b, e).trim();
	}

}
