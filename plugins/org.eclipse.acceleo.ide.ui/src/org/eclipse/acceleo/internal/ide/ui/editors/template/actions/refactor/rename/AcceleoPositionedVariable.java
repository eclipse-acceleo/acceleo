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
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferenceEntry;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchQuery;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchResult;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.search.ui.text.Match;

/**
 * This class is a data object class used to store a variable and its positions in a mtl file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPositionedVariable {

	/**
	 * The variable.
	 */
	private Variable fVariable;

	/**
	 * All the matches of the variable.
	 */
	private List<Match> fMatches;

	/**
	 * The position of the definition of the variable.
	 */
	private Match fDefinitionMatch;

	/**
	 * The constructor.
	 * 
	 * @param variable
	 *            The variable.
	 * @param editor
	 *            The editor.
	 */
	public AcceleoPositionedVariable(final Variable variable, final AcceleoEditor editor) {
		this.fVariable = variable;
		this.fMatches = new ArrayList<Match>();
		this.findAllPositionedVariables(editor);
	}

	/**
	 * The constructor.
	 * 
	 * @param variableExp
	 *            The variable exp.
	 * @param editor
	 *            The editor.
	 */
	public AcceleoPositionedVariable(final VariableExp variableExp, final AcceleoEditor editor) {
		this.fVariable = (Variable)variableExp.getReferredVariable();
		this.fMatches = new ArrayList<Match>();
		this.findAllPositionedVariables(editor);
	}

	/**
	 * Returns the variable.
	 * 
	 * @return The variable.
	 */
	public Variable getVariable() {
		return this.fVariable;
	}

	/**
	 * Returns the name of the variable.
	 * 
	 * @return The name of the variable.
	 */
	public String getVariableName() {
		return this.fVariable.getName();
	}

	/**
	 * Sets the list of variables.
	 * 
	 * @param matches
	 *            The list of matches.
	 */
	public void setVariableMatches(final List<Match> matches) {
		this.fMatches = matches;
	}

	/**
	 * Returns the matches of all the occurrences of the variable in the template.
	 * 
	 * @return The matches of all the occurrences of the variable in the template.
	 */
	public List<Match> getVariableMatches() {
		return this.fMatches;
	}

	/**
	 * Sets the match of the variable definition.
	 * 
	 * @param match
	 *            the match of the variable definition.
	 */
	public void setVariableDefinitionMatch(final Match match) {
		this.fDefinitionMatch = match;
	}

	/**
	 * Returns the position of the name in the variable definition.
	 * 
	 * @return the position of the name in the variable definition.
	 */
	public Match getVariableDefinitionMatch() {
		return this.fDefinitionMatch;
	}

	/**
	 * Sets all the matches of the current variable in the editor.
	 * 
	 * @param editor
	 *            The acceleo editor.
	 */
	private void findAllPositionedVariables(final AcceleoEditor editor) {
		final List<Match> list = new ArrayList<Match>();

		final ReferencesSearchQuery searchQuery = new ReferencesSearchQuery(editor, this.fVariable, false);
		searchQuery.run(new NullProgressMonitor());

		final ReferencesSearchResult result = (ReferencesSearchResult)searchQuery.getSearchResult();
		final Object[] array = result.getElements();

		for (int i = 0; i < array.length; i++) {
			if (((ReferenceEntry)array[i]).getRegion() != null) {
				list.add(new Match(array[i], ((ReferenceEntry)array[i]).getRegion().getOffset(),
						((ReferenceEntry)array[i]).getRegion().getLength()));
			}
		}

		this.setVariableMatches(list);

		for (Match match : list) {
			if (match.getLength() > this.fVariable.getName().length()) {
				this.setVariableDefinitionMatch(match);
			}
		}
	}
}
