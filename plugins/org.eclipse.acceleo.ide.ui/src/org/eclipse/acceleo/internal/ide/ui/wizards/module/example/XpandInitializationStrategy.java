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
 * To initialize automatically an Acceleo module file from an Xpand template file, by copying and modifying
 * the text of the example into the new module.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class XpandInitializationStrategy extends AbstractAcceleoInitializationStrategy {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("XpandContentStrategy.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getInitialFileNameFilter()
	 */
	public String getInitialFileNameFilter() {
		return "*.xpt"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.AbstractM2TContentStrategy#
	 *      modifyM2TContent(java.lang.StringBuffer, java.lang.String)
	 */
	@Override
	protected void modifyM2TContent(StringBuffer text, String moduleName) {
		int offset = text.indexOf("/*"); //$NON-NLS-1$
		if (offset > -1) {
			int end = text.indexOf("*/", offset); //$NON-NLS-1$
			if (end > -1) {
				text.replace(offset, end + 2, "[comment " + text.substring(offset + 2, end) + "/]"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		String[] mappings = getMappings(moduleName);
		for (int i = 0; i < mappings.length - 1; i += 2) {
			String initialString = mappings[i];
			String replacingString = mappings[i + 1];
			replaceAll(text, initialString, replacingString);
		}
		modifyTemplateInvocation(text, "\\[[a-zA-Z_\\:]+"); //$NON-NLS-1$
		modifyTemplateInvocation(text, "\\.a-zA-Z_\\:]+"); //$NON-NLS-1$
	}

	/**
	 * Gets the mapping rules.
	 * <p>
	 * mappings[i] is the text before and mappings[i+1] is the text after.
	 * </p>
	 * 
	 * @param moduleName
	 *            the module name
	 * @return the mapping rules
	 */
	private String[] getMappings(String moduleName) {
		return new String[] {"-\u00BB" + END_LINE, "\u00BB", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB IMPORT \"*\" \u00BB \u00AB IMPORT \"*\" \u00BB \u00AB IMPORT \"*\" \u00BB", //$NON-NLS-1$
				'[' + "module " + moduleName + "('$1', '$2', '$3')/]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB IMPORT \"*\" \u00BB \u00AB IMPORT \"*\" \u00BB", //$NON-NLS-1$ 
				"[module " + moduleName + "('$1', '$2')/]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB IMPORT \"*\" \u00BB" + END_LINE, "[module " + moduleName + "('$1')/]", //$NON-NLS-1$ //$NON-NLS-2$  //$NON-NLS-3$

				"\u00AB EXTENSION * \u00BB" + END_LINE, "[import $1/]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB DEFINE * ( * ) FOR * :: * \u00BB" + END_LINE, "[template public $1 (v$4 : $4, $2)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB DEFINE * ( * ) FOR * \u00BB" + END_LINE, "[template public $1 (v$3 : EObject, $2)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB DEFINE * FOR * :: * \u00BB" + END_LINE, "[template public $1 (v$3 : $3)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB DEFINE * FOR * \u00BB" + END_LINE, "[template public $1 (v$2 : EObject)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB ENDDEFINE \u00BB" + END_LINE, "[/template]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB FOREACH * AS * \u00BB" + END_LINE, "[for ($2 : EObject | $1)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB ENDFOREACH \u00BB" + END_LINE, "[/for]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB IF * \u00BB" + END_LINE, "[if ($1)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB ELSE IF * \u00BB" + END_LINE, "[else if ($1)]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB ELSE * \u00BB" + END_LINE, "[else]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB ENDIF \u00BB" + END_LINE, "[/if]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB LET * AS * \u00BB" + END_LINE, "[let $2 : EObject = $1]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB ENDLET \u00BB" + END_LINE, "[/let]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB REM \u00BB * \u00AB ENDREM \u00BB", "[comment $1 /]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB EXPAND * \u00BB" + END_LINE, "[$1/]", //$NON-NLS-1$ //$NON-NLS-2$

				"\u00AB * \u00BB" + END_LINE, "[$1/]", //$NON-NLS-1$ //$NON-NLS-2$

				"[*\"*\"*\"*\"*]" + END_LINE, "[$1'$2'$3'$4'$5]", //$NON-NLS-1$ //$NON-NLS-2$

				"[*\"*\"*]" + END_LINE, "[$1'$2'$3]", }; //$NON-NLS-1$ //$NON-NLS-2$
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
			Pattern pID = Pattern.compile("[a-zA-Z_\\:]+"); //$NON-NLS-1$
			Matcher mID = pID.matcher(string);
			if (mID.find()) {
				string = string.substring(mID.start(), mID.end());
			}
			if (string.lastIndexOf(":") > -1) { //$NON-NLS-1$
				string = string.substring(string.lastIndexOf(":") + 1); //$NON-NLS-1$
			}
			Pattern pDeclare = Pattern.compile("public[ \t]+" + string + "[ \t]*\\("); //$NON-NLS-1$ //$NON-NLS-2$
			Matcher mDeclare = pDeclare.matcher(text);
			if (mDeclare.find()) {
				text.insert(offset - 2, "()"); //$NON-NLS-1$
			}
		}
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
