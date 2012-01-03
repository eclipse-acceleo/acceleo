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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.Metamodel;
import org.eclipse.acceleo.compatibility.model.mt.core.Service;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.MTFileContent;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;
import org.eclipse.core.resources.IFile;

/**
 * The utility class to parse an Acceleo template file. We create an EMF model for each template. The project
 * parser is responsible to aggregate all these EMF models.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class TemplateParser {

	/**
	 * No public access to the constructor.
	 */
	private TemplateParser() {
		// nothing to do here
	}

	/**
	 * Parse the given file and fill the template.
	 * 
	 * @param file
	 *            is the file to parse
	 * @param template
	 *            the current template to fill
	 * @param root
	 *            the root element of the model to create
	 * @param problems
	 *            are the problems
	 */
	public static void parseTemplate(IFile file, Template template, ResourceSet root,
			List<TemplateSyntaxException> problems) {
		String text = MTFileContent.getFileContent(file.getLocation().toFile()).toString();
		TemplateConstants.getDefault().initConstants(text);
		template.setBeginTag(TemplateConstants.getDefault().getFeatureBegin());
		template.setEndTag(TemplateConstants.getDefault().getFeatureEnd());
		if (text != null && text.length() > 0) {
			Service service = ServiceParser.createImplicitService(file, root);
			if (service != null) {
				template.getImports().add(service);
			}
			parseImports(text, template, root, problems);
			ScriptParser.parseScripts(0, text, template, problems);
		}

	}

	/**
	 * Parse the import section of the file and fill the template.
	 * 
	 * @param text
	 *            is the text to parse
	 * @param template
	 *            the current template to fill
	 * @param root
	 *            the root element of the model to create
	 * @param problems
	 *            are the problems
	 */
	private static void parseImports(String text, Template template, ResourceSet root,
			List<TemplateSyntaxException> problems) {
		final List<String> importValues = new ArrayList<String>();
		int end = TextSearch.indexIn(text, TemplateConstants.getDefault().getScriptBegin(),
				new Region(0, text.length()), null, TemplateConstants.getDefault().getInhibsScriptContent())
				.b();
		if (end == -1) {
			end = text.length();
		}
		int pos = 0;
		while (pos > -1 && pos < end) {
			final Region bComment = TextSearch.indexIn(text,
					TemplateConstants.getDefault().getCommentBegin(), new Region(pos, end));
			final Region bImports = TextSearch.indexIn(text, TemplateConstants.getDefault().getImportBegin(),
					new Region(pos, end));
			if (bComment.b() > -1 && (bImports.b() == -1 || bComment.b() <= bImports.b())) {
				final Region eComment = TextSearch.blockIndexEndIn(text, TemplateConstants.getDefault()
						.getCommentBegin(), TemplateConstants.getDefault().getCommentEnd(), new Region(
						bComment.b(), end), false, null, null);
				if (eComment.b() > -1) {
					pos = eComment.e();
				} else {
					problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages
							.getString("TemplateSyntaxError.MissingCommentEndTag"), template, bComment.b())); //$NON-NLS-1$
					pos = end;
				}
			} else if (bImports.b() > -1) {
				final Region eImports = TextSearch.indexIn(text, TemplateConstants.getDefault()
						.getImportEnd(), new Region(bImports.e(), end));
				if (eImports.b() > -1) {
					final Region[] imports = TextSearch.splitPositionsIn(text, new Region(bImports.e(),
							eImports.b()), new String[] {"\n" }, false, null, null); //$NON-NLS-1$
					for (int i = 0; i < imports.length; i++) {
						Region importPos = imports[i];
						importPos = TextSearch.trim(text, importPos.b(), importPos.e());
						if (importPos.b() > -1 && importPos.e() > importPos.b()) {
							parseImport(text, importPos, template, root, problems, importValues);
						}
					}
					pos = eImports.e();
				} else {
					problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages
							.getString("TemplateSyntaxError.InvalidImportSequence"), template, bImports.b())); //$NON-NLS-1$
					pos = end;
				}
			} else {
				pos = end;
			}
		}
	}

	/**
	 * Parse one import value of the file and fill the template. It checks the duplicated import values.
	 * 
	 * @param text
	 *            is the text to parse
	 * @param range
	 *            is the position of the import value
	 * @param template
	 *            the current template to fill
	 * @param root
	 *            the root element of the model to create
	 * @param problems
	 *            are the problems
	 * @param importValuesFound
	 *            is the import values already found
	 */
	private static void parseImport(String text, Region range, Template template, ResourceSet root,
			List<TemplateSyntaxException> problems, final List<String> importValuesFound) {
		if (TextSearch.indexIn(text, TemplateConstants.getDefault().getImportWord(), range).b() == range.b()) {
			final Region valuePos = TextSearch.trim(text, range.b()
					+ TemplateConstants.getDefault().getImportWord().length(), range.e());
			if (valuePos.b() > -1) {
				final String value = text.substring(valuePos.b(), valuePos.e()).trim();
				if (importValuesFound.contains(value)) {
					problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages.getString(
							"TemplateSyntaxError.DuplicateValue", new Object[] {"import", }), template, //$NON-NLS-1$ //$NON-NLS-2$
							valuePos));
				} else {
					importValuesFound.add(value);
					try {
						parseImport(value, valuePos, importValuesFound.size(), template, root);
					} catch (final TemplateSyntaxException e) {
						problems.add(e);
					}
				}
			} else {
				problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.EmptyImport"), template, range.b() //$NON-NLS-1$
						+ TemplateConstants.getDefault().getImportWord().length()));
			}
		} else if (TextSearch.indexIn(text, TemplateConstants.getDefault().getModelTypeWord(), range).b() == range
				.b()) {
			final Region valuePos = TextSearch.trim(text, range.b()
					+ TemplateConstants.getDefault().getModelTypeWord().length(), range.e());
			if (valuePos.b() > -1) {
				final String value = text.substring(valuePos.b(), valuePos.e()).trim();
				if (importValuesFound.contains(value)) {
					problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages.getString(
							"TemplateSyntaxError.DuplicateValue", new Object[] {"metamodel", }), template, //$NON-NLS-1$ //$NON-NLS-2$
							valuePos));
				} else {
					importValuesFound.add(value);
					try {
						parseImport(value, valuePos, importValuesFound.size(), template, root);
					} catch (final TemplateSyntaxException e) {
						problems.add(e);
					}
				}
			} else {
				problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages.getString(
						"TemplateSyntaxError.EmptyValue", new Object[] {"metamodel", }), template, range.b() //$NON-NLS-1$ //$NON-NLS-2$
						+ TemplateConstants.getDefault().getModelTypeWord().length()));
			}
		} else {
			problems.add(new TemplateSyntaxException(AcceleoCompatibilityMessages.getString(
					"TemplateSyntaxError.MissingKeyWord", new Object[] {"import", }), template, range.b())); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Parse one import value of the file and fill the template.
	 * 
	 * @param value
	 *            is the import value
	 * @param valuePos
	 *            is the position of the value in the file
	 * @param num
	 *            is the number of this import in the section
	 * @param template
	 *            the current template to fill
	 * @param root
	 *            the root element of the model to create
	 * @throws TemplateSyntaxException
	 *             when a syntax issue occurs
	 */
	private static void parseImport(String value, Region valuePos, int num, Template template,
			ResourceSet root) throws TemplateSyntaxException {
		if (!parseMetamodelImport(value, num, template, root)) {
			Template importedTemplate = null;
			Iterator<Resource> resources = root.getResources().iterator();
			while (importedTemplate == null && resources.hasNext()) {
				Resource resource = resources.next();
				if (resource instanceof Template) {
					if (value.equals(((Template)resource).getName())) {
						importedTemplate = (Template)resource;
					}
				}
			}
			if (importedTemplate != null) {
				template.getImports().add(importedTemplate);
			} else {
				Service importedService = ServiceParser.createImportedService(value, root);
				if (importedService != null) {
					template.getImports().add(importedService);
				} else {
					throw new TemplateSyntaxException(AcceleoCompatibilityMessages.getString(
							"TemplateSyntaxError.UnresolvedImport", new Object[] {value, }), template, //$NON-NLS-1$
							valuePos);
				}
			}
		}
	}

	/**
	 * Try to parse the first import value and create a metamodel import.
	 * 
	 * @param value
	 *            is the import value
	 * @param num
	 *            is the number of the import
	 * @param template
	 *            the current template to fill
	 * @param root
	 *            the root element of the model to create
	 * @return true if a metamodel import has been detected
	 * @throws TemplateSyntaxException
	 *             when a syntax issue occurs
	 */
	private static boolean parseMetamodelImport(String value, int num, Template template, ResourceSet root)
			throws TemplateSyntaxException {
		if (num == 1) {
			String uri = value.trim();
			Metamodel importedMetamodel = null;
			Iterator<Resource> resources = root.getResources().iterator();
			while (importedMetamodel == null && resources.hasNext()) {
				Resource resource = resources.next();
				if (resource instanceof Metamodel) {
					if (uri.equals(((Metamodel)resource).getName())) {
						importedMetamodel = (Metamodel)resource;
					}
				}
			}
			if (importedMetamodel == null) {
				importedMetamodel = CoreFactory.eINSTANCE.createMetamodel();
				importedMetamodel.setName(uri);
				root.getResources().add(importedMetamodel);
			}
			template.getImports().add(importedMetamodel);
			return true;
		}
		return false;
	}

}
