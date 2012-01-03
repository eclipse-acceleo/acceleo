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
package org.eclipse.acceleo.internal.compatibility.parser.mt.ast.core;

import java.util.List;

import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.Script;
import org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.StatementParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse new scripts.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ScriptParser {

	/**
	 * No public access to the constructor.
	 */
	private ScriptParser() {
		// nothing to do here
	}

	/**
	 * Parse and create new scripts in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param template
	 *            is the current template where to put the new script descriptor
	 * @param problems
	 *            are the syntax problems
	 */
	public static void parseScripts(int offset, String buffer, Template template,
			List<TemplateSyntaxException> problems) {
		ScriptDescriptor descriptor = null;
		Region end = new Region(0, 0);
		while (end.e() > -1 && end.e() < buffer.length()) {
			final Region begin = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getScriptBegin(),
					new Region(end.e(), buffer.length()), null, TemplateConstants.getDefault()
							.getInhibsScriptContent());
			if (begin.b() > -1) {
				// Create a new script
				try {
					newScript(offset, buffer, new Region(end.e(), begin.b()), template, descriptor);
				} catch (final TemplateSyntaxException e) {
					problems.add(e);
				}
				// Clear informations
				descriptor = null;
				// Prepare new script
				end = TextSearch.blockIndexEndIn(buffer, TemplateConstants.getDefault().getScriptBegin(),
						TemplateConstants.getDefault().getScriptEnd(),
						new Region(begin.b(), buffer.length()), false, TemplateConstants.getDefault()
								.getSpec(), TemplateConstants.getDefault().getInhibsScriptDecla());
				if (end.e() == -1) {
					problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages
							.getString("TemplateSyntaxError.MissingScriptEndTag"), template, begin.b())); //$NON-NLS-1$
				} else {
					try {
						descriptor = ScriptDescriptorParser.createScriptDescriptor(offset, buffer,
								new Region(begin.e(), end.b()), template);
					} catch (final TemplateSyntaxException e) {
						problems.add(e);
					}
				}
			} else { // -1
				// Create a new script
				try {
					newScript(offset, buffer, new Region(end.e(), buffer.length()), template, descriptor);
				} catch (final TemplateSyntaxException e) {
					problems.add(e);
				}
				end = new Region(buffer.length(), buffer.length());
			}
		}
	}

	/**
	 * Create a new script in the template for the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the script descriptor to create
	 * @param template
	 *            is the current template where to put the new script descriptor
	 * @param descriptor
	 *            is the current descriptor (the one to link with the script to create)
	 * @throws TemplateSyntaxException
	 *             if an issue occurs
	 */
	private static void newScript(int offset, String buffer, Region range, Template template,
			ScriptDescriptor descriptor) throws TemplateSyntaxException {
		if (descriptor != null && range.b() > 0) {
			Region limitsTextTemplate = StatementParser.formatTemplate(buffer, range, 2);
			Script script = CoreFactory.eINSTANCE.createScript();
			template.getScripts().add(script);
			script.setDescriptor(descriptor);
			script.getStatements().addAll(
					StatementParser.createStatement(offset, buffer, limitsTextTemplate, template));
			script.setBegin(limitsTextTemplate.b());
			script.setEnd(limitsTextTemplate.e());
		}
	}
}
