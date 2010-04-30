/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.PlatformUI;

/**
 * This class is a data object class used to store a query and its positions in a mtl file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPositionedQuery {
	/**
	 * The input for the combo viewer.
	 */
	private static AcceleoPositionedQuery[] input;

	/**
	 * The editor.
	 */
	private static AcceleoEditor acceleoEditor;

	/**
	 * The query.
	 */
	private Query fQuery;

	/**
	 * All the matches of the query.
	 */
	private List<Match> fMatches;

	/**
	 * The position of the definition of the query.
	 */
	private Match fDefinitionMatch;

	/**
	 * The constructor.
	 * 
	 * @param query
	 *            The query.
	 */
	public AcceleoPositionedQuery(final Query query) {
		this.fQuery = query;
		this.fMatches = new ArrayList<Match>();
	}

	/**
	 * Compute the complete input based on the current file in the acceleo editor.<br />
	 * getInput will provide the list of all queries and all their positions.
	 */
	public static void computeCompleteInput() {
		final AcceleoEditor editor = (AcceleoEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		final List<AcceleoPositionedQuery> list = findAllPositionedQueries(editor);
		input = list.toArray(new AcceleoPositionedQuery[list.size()]);
	}

	/**
	 * Compute a partial input. The partial input contains the list of all the Acceleo positioned query but
	 * without their positions. Use computeOccurrencesOfQuery to compute the positions of the given query.
	 */
	public static void computePartialInput() {
		final List<AcceleoPositionedQuery> positionedQueryList = new ArrayList<AcceleoPositionedQuery>();
		final AcceleoEditor editor = (AcceleoEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();

		final Module astModule = editor.getContent().getAST();
		final List<ModuleElement> moduleElementList = astModule.getOwnedModuleElement();

		for (Iterator<ModuleElement> iterator = moduleElementList.iterator(); iterator.hasNext();) {
			final ModuleElement moduleElement = (ModuleElement)iterator.next();
			if (moduleElement instanceof Query) {
				positionedQueryList.add(new AcceleoPositionedQuery((Query)moduleElement));
			}
		}

		input = positionedQueryList.toArray(new AcceleoPositionedQuery[positionedQueryList.size()]);
	}

	/**
	 * Initialize the list of matches of the query. It is not needed to call that method if you used
	 * computeCompleteInput but it's necessary after computePartialInput.
	 */
	public void computeOccurrencesOfQuery() {
		// We find all the occurrences of the query in the workspace.
		final List<Match> list = OpenDeclarationUtils.findOccurrences(acceleoEditor, this.fQuery);

		this.setQueryMatches(list);

		// step 3 : find the position definition of the current query
		for (Iterator<Match> iterator2 = list.iterator(); iterator2.hasNext();) {
			final Match match = (Match)iterator2.next();

			if (match.getLength() > this.getQuery().getName().length()) {
				this.setQueryDefinitionMatch(match);
			}
		}
	}

	/**
	 * Returns the input. Use computeInput first, otherwise the result will be null.
	 * 
	 * @return The input.
	 */
	public static AcceleoPositionedQuery[] getInput() {
		return input;
	}

	/**
	 * Returns the query.
	 * 
	 * @return The query.
	 */
	public Query getQuery() {
		return this.fQuery;
	}

	/**
	 * Returns the name of the query.
	 * 
	 * @return The name of the query.
	 */
	public String getQueryName() {
		return this.fQuery.getName();
	}

	/**
	 * Sets the list of queries.
	 * 
	 * @param matches
	 *            The list of matches.
	 */
	public void setQueryMatches(final List<Match> matches) {
		this.fMatches = matches;
	}

	/**
	 * Returns the matches of all the occurrences of the query in the workspace.
	 * 
	 * @return The matches of all the occurrences of the query in the workspace.
	 */
	public List<Match> getQueryMatches() {
		return this.fMatches;
	}

	/**
	 * Sets the match of the query definition.
	 * 
	 * @param match
	 *            the match of the query definition.
	 */
	public void setQueryDefinitionMatch(final Match match) {
		this.fDefinitionMatch = match;
	}

	/**
	 * Returns the position of the name in the query definition.
	 * 
	 * @return the position of the name in the query definition.
	 */
	public Match getQueryDefinitionMatch() {
		return this.fDefinitionMatch;
	}

	/**
	 * Returns all the acceleo positioned query from the current editor.
	 * 
	 * @param editor
	 *            The acceleo editor.
	 * @return All the acceleo positioned query from the current editor.
	 */
	private static List<AcceleoPositionedQuery> findAllPositionedQueries(final AcceleoEditor editor) {
		final List<AcceleoPositionedQuery> positionedQueriesList = new ArrayList<AcceleoPositionedQuery>();

		// step 1 : look for all queries
		final List<Query> queryList = new ArrayList<Query>();

		final Module astModule = editor.getContent().getAST();
		final List<ModuleElement> moduleElementList = astModule.getOwnedModuleElement();

		for (Iterator<ModuleElement> iterator = moduleElementList.iterator(); iterator.hasNext();) {
			final ModuleElement moduleElement = (ModuleElement)iterator.next();
			if (moduleElement instanceof Query) {
				queryList.add((Query)moduleElement);
			}
		}

		// step 2 : for each queries, look for all occurrences
		for (Iterator<Query> iterator = queryList.iterator(); iterator.hasNext();) {
			final Query query = (Query)iterator.next();
			final AcceleoPositionedQuery positionedQuery = new AcceleoPositionedQuery(query);

			// We find all the occurrences of the given query in the workspace.
			final List<Match> list = OpenDeclarationUtils.findOccurrences(editor, query);

			positionedQuery.setQueryMatches(list);

			// step 3 : find the position definition of the current query
			for (Iterator<Match> iterator2 = list.iterator(); iterator2.hasNext();) {
				final Match match = (Match)iterator2.next();

				if (match.getLength() > query.getName().length()) {
					positionedQuery.setQueryDefinitionMatch(match);
				}

			}

			positionedQueriesList.add(positionedQuery);
		}

		return positionedQueriesList;
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
