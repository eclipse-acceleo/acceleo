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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.search.ui.text.Match;

/**
 * This class is a data object class used to store a template and its positions in a mtl file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoPositionedTemplate {

	/**
	 * The input for the combo viewer.
	 */
	private static AcceleoPositionedTemplate[] input;

	/**
	 * The editor.
	 */
	private static AcceleoEditor acceleoEditor;

	/**
	 * The template.
	 */
	private Template fTemplate;

	/**
	 * All the matches of the template.
	 */
	private List<Match> fMatches;

	/**
	 * The position of the definition of the template.
	 */
	private Match fDefinitionMatch;

	/**
	 * The constructor.
	 * 
	 * @param template
	 *            The template.
	 */
	public AcceleoPositionedTemplate(final Template template) {
		this.fTemplate = template;
		this.fMatches = new ArrayList<Match>();
	}

	/**
	 * Compute the complete input based on the current file in the acceleo editor.<br />
	 * getInput will provide the list of all templates and all their positions.
	 */
	public static void computeCompleteInput() {
		final List<AcceleoPositionedTemplate> list = findAllPositionedTemplates();
		input = list.toArray(new AcceleoPositionedTemplate[list.size()]);
	}

	/**
	 * Compute a partial input. The partial input contains the list of all the Acceleo positioned template but
	 * without their positions. Use computeOccurrencesOfTemplate to compute the positions of the given
	 * template.
	 * 
	 * @param template
	 *            The selected template.
	 */
	public static void computePartialInput(final Template template) {
		final List<AcceleoPositionedTemplate> positionedTemplatesList = new ArrayList<AcceleoPositionedTemplate>();

		acceleoEditor.getContent().getResolvedASTNode(0, acceleoEditor.getContent().getText().length());
		final Module astModule = acceleoEditor.getContent().getAST();

		// This is another alternative to resolve all the OCL expressions of the AST.
		// final Module astModule = AcceleoRenameModuleUtils.getModuleFromFile(acceleoEditor.getFile());
		final List<ModuleElement> moduleElementList = astModule.getOwnedModuleElement();

		for (ModuleElement moduleElement : moduleElementList) {
			if (moduleElement instanceof Template) {
				positionedTemplatesList.add(new AcceleoPositionedTemplate((Template)moduleElement));
			}
		}

		if (template != null) {
			boolean isInTheList = false;

			for (AcceleoPositionedTemplate acceleoPositionedTemplate : positionedTemplatesList) {
				if (OpenDeclarationUtils
						.checkTemplateEqual(acceleoPositionedTemplate.getTemplate(), template)) {
					isInTheList = true;
					break;
				}
			}

			if (!isInTheList) {
				positionedTemplatesList.add(new AcceleoPositionedTemplate(template));
			}
		}

		input = positionedTemplatesList
				.toArray(new AcceleoPositionedTemplate[positionedTemplatesList.size()]);
	}

	/**
	 * Initialize the list of matches of the template. It is not needed to call that method if you used
	 * computeCompleteInput but it's necessary after computePartialInput.
	 */
	public void computeOccurrencesOfTemplate() {
		// We find all the occurrences of the template in the current workspace.
		final List<Match> list = OpenDeclarationUtils.findOccurrences(acceleoEditor, this.fTemplate);

		this.setTemplateMatches(list);

		// step 3 : find the position definition of the current template
		for (Match match : list) {
			if (match.getLength() > this.getTemplate().getName().length()) {
				this.setTemplateDefinitionMatch(match);
			}
		}
	}

	/**
	 * Returns the input. Use computeInput first, otherwise the result will be null.
	 * 
	 * @return The input.
	 */
	public static AcceleoPositionedTemplate[] getInput() {
		return input;
	}

	/**
	 * Returns the template.
	 * 
	 * @return The template.
	 */
	public Template getTemplate() {
		return this.fTemplate;
	}

	/**
	 * Returns the name of the template.
	 * 
	 * @return The name of the template.
	 */
	public String getTemplateName() {
		return this.fTemplate.getName();
	}

	/**
	 * Sets the list of matches.
	 * 
	 * @param matches
	 *            The list of matches.
	 */
	public void setTemplateMatches(final List<Match> matches) {
		this.fMatches = matches;
	}

	/**
	 * Returns the matches of all the occurrences of the template in the workspace.
	 * 
	 * @return The matches of all the occurrences of the template in the workspace.
	 */
	public List<Match> getTemplateMatches() {
		return this.fMatches;
	}

	/**
	 * Sets the match of the template definition.
	 * 
	 * @param match
	 *            the match of the template definition.
	 */
	public void setTemplateDefinitionMatch(final Match match) {
		this.fDefinitionMatch = match;
	}

	/**
	 * Returns the position of the name in the template definition.
	 * 
	 * @return the position of the name in the template definition.
	 */
	public Match getTemplateDefinitionMatch() {
		return this.fDefinitionMatch;
	}

	/**
	 * Returns all the acceleo positioned template from the current editor.
	 * 
	 * @return All the acceleo positioned template from the current editor.
	 */
	private static List<AcceleoPositionedTemplate> findAllPositionedTemplates() {
		final List<AcceleoPositionedTemplate> positionedTemplatesList = new ArrayList<AcceleoPositionedTemplate>();

		// step 1 : look for all templates
		final List<Template> templateList = new ArrayList<Template>();

		final Module astModule = acceleoEditor.getContent().getAST();
		final List<ModuleElement> moduleElementList = astModule.getOwnedModuleElement();

		for (ModuleElement moduleElement : moduleElementList) {
			if (moduleElement instanceof Template) {
				templateList.add((Template)moduleElement);
			}
		}

		// step 2 : for each templates, look for all occurrences
		for (Template template : templateList) {
			final AcceleoPositionedTemplate positionedTemplate = new AcceleoPositionedTemplate(template);

			// We find all the occurrences of the current template in the workspace
			final List<Match> list = OpenDeclarationUtils.findOccurrences(acceleoEditor, template);
			positionedTemplate.setTemplateMatches(list);

			// step 3 : find the position definition of the current template
			for (Match match : list) {
				if (match.getLength() > template.getName().length()) {
					positionedTemplate.setTemplateDefinitionMatch(match);
				}
			}
			positionedTemplatesList.add(positionedTemplate);
		}
		return positionedTemplatesList;
	}

	/**
	 * Sets the acceleo editor.
	 * 
	 * @param editor
	 *            The editor.
	 */
	public static void setAcceleoEditor(final AcceleoEditor editor) {
		acceleoEditor = editor;
	}

}
