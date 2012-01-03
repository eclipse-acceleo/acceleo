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
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.search.ui.text.Match;

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
		final List<AcceleoPositionedQuery> list = findAllPositionedQueries(acceleoEditor);
		input = list.toArray(new AcceleoPositionedQuery[list.size()]);
	}

	/**
	 * Compute a partial input. The partial input contains the list of all the Acceleo positioned query but
	 * without their positions. Use computeOccurrencesOfQuery to compute the positions of the given query.
	 * 
	 * @param query
	 *            The selected query.
	 */
	public static void computePartialInput(final Query query) {
		final List<AcceleoPositionedQuery> positionedQueryList = new ArrayList<AcceleoPositionedQuery>();

		acceleoEditor.getContent().getResolvedASTNode(0, acceleoEditor.getContent().getText().length());
		final Module astModule = acceleoEditor.getContent().getAST();

		// This is another alternative to resolve all the OCL expressions of the AST.
		// final Module astModule = AcceleoRenameModuleUtils.getModuleFromFile(acceleoEditor.getFile());
		final List<ModuleElement> moduleElementList = astModule.getOwnedModuleElement();

		for (ModuleElement moduleElement : moduleElementList) {
			if (moduleElement instanceof Query) {
				positionedQueryList.add(new AcceleoPositionedQuery((Query)moduleElement));
			}
		}

		boolean isInTheList = false;

		for (AcceleoPositionedQuery acceleoPositionedQuery : positionedQueryList) {
			if (checkQueryEqual(acceleoPositionedQuery.getQuery(), query)) {
				isInTheList = true;
				break;
			}
		}

		if (!isInTheList) {
			positionedQueryList.add(new AcceleoPositionedQuery(query));
		}

		input = positionedQueryList.toArray(new AcceleoPositionedQuery[positionedQueryList.size()]);
	}

	/**
	 * Check if two queries have the same name and positions.
	 * 
	 * @param q1
	 *            The first query.
	 * @param q2
	 *            The second query.
	 * @return True if the two queries are equal according to our criteria.
	 */
	private static boolean checkQueryEqual(final Query q1, final Query q2) {
		boolean result;
		// We cannot have both null
		if (q1 != null && q2 != null) {
			result = q1.getName().equals(q2.getName());
			result = result && (q1.getStartPosition() == q2.getStartPosition());
			result = result && (q1.getEndPosition() == q2.getEndPosition());
		} else {
			result = false;
		}
		return result;
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
		for (Match match : list) {
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

		for (ModuleElement moduleElement : moduleElementList) {
			if (moduleElement instanceof Query) {
				queryList.add((Query)moduleElement);
			}
		}

		// step 2 : for each queries, look for all occurrences
		for (Query query : queryList) {
			final AcceleoPositionedQuery positionedQuery = new AcceleoPositionedQuery(query);

			// We find all the occurrences of the given query in the workspace.
			final List<Match> list = OpenDeclarationUtils.findOccurrences(editor, query);

			positionedQuery.setQueryMatches(list);

			// step 3 : find the position definition of the current query
			for (Match match : list) {
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
