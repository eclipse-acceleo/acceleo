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

import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.FilePath;
import org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.ExpressionParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.StatementParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a script descriptor.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ScriptDescriptorParser {

	/**
	 * No public access to the constructor.
	 */
	private ScriptDescriptorParser() {
		// nothing to do here
	}

	/**
	 * Creates a new script descriptor in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the script descriptor to create
	 * @param template
	 *            is the current template where to put the new script descriptor
	 * @return the new script descriptor
	 * @throws TemplateSyntaxException
	 *             if an issue occurs
	 */
	public static ScriptDescriptor createScriptDescriptor(int offset, String buffer, Region range,
			Template template) throws TemplateSyntaxException {
		FilePath templateFile = CoreFactory.eINSTANCE.createFilePath();
		Expression templatePost = null;
		String templateType = null;
		String templateName = null;
		String templateDescription = null;
		Region rangeFileTemplate = null;
		Region rangePostExpression = null;
		Region[] properties = TextSearch.splitPositionsIn(buffer, range, TemplateConstants.getDefault()
				.getScriptPropertiesSeparator(), false, TemplateConstants.getDefault().getSpec(),
				TemplateConstants.getDefault().getInhibsScriptDecla());
		for (int i = 0; i < properties.length; i += 2) {
			String key = buffer.substring(properties[i].b(), properties[i].e());
			if ((i + 1) < properties.length) {
				String value = buffer.substring(properties[i + 1].b(), properties[i + 1].e());
				if (value.length() >= 2 && value.startsWith(TemplateConstants.getDefault().getLiteral()[0])
						&& value.endsWith(TemplateConstants.getDefault().getLiteral()[1])) {
					value = value.substring(1, value.length() - 1);
					if (key.equals(TemplateConstants.getDefault().getScriptType())) {
						templateType = value;
					} else if (key.equals(TemplateConstants.getDefault().getScriptName())) {
						templateName = value;
					} else if (key.equals(TemplateConstants.getDefault().getScriptDesc())) {
						templateDescription = value;
					} else if (key.equals(TemplateConstants.getDefault().getScriptFile())) {
						rangeFileTemplate = new Region(properties[i + 1].b() + 1, properties[i + 1].e() - 1);
						templateFile.getStatements().addAll(
								StatementParser.createStatement(offset, buffer, rangeFileTemplate, template));
					} else if (key.equals(TemplateConstants.getDefault().getScriptPost())) {
						rangePostExpression = new Region(properties[i + 1].b() + 1, properties[i + 1].e() - 1);
						templatePost = ExpressionParser.createExpression(offset, buffer, rangePostExpression,
								template);
					} else {
						throw new TemplateSyntaxException(
								AcceleoCompatibilityMessages.getString(
										"TemplateSyntaxError.InvalidKey", new Object[] {key, }), template, properties[i].b()); //$NON-NLS-1$
					}
				} else {
					throw new TemplateSyntaxException(
							AcceleoCompatibilityMessages.getString(
									"TemplateSyntaxError.InvalidKeyValue", new Object[] {key, }), template, properties[i].e()); //$NON-NLS-1$
				}
			} else {
				throw new TemplateSyntaxException(
						AcceleoCompatibilityMessages.getString(
								"TemplateSyntaxError.MissingKeyValue", new Object[] {key, }), template, properties[i].e()); //$NON-NLS-1$
			}
		}
		if (templateType == null) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingType"), template, range.b()); //$NON-NLS-1$
		} else if (templateName == null) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingName"), template, range.b()); //$NON-NLS-1$
		}
		ScriptDescriptor scriptDescriptor = CoreFactory.eINSTANCE.createScriptDescriptor();
		scriptDescriptor.setType(templateType);
		scriptDescriptor.setName(templateName);
		scriptDescriptor.setDescription(templateDescription);
		scriptDescriptor.setFile(templateFile);
		scriptDescriptor.setPost(templatePost);
		scriptDescriptor.setBegin(range.b());
		scriptDescriptor.setEnd(range.e());
		return scriptDescriptor;
	}
}
