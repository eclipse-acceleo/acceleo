/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.converters;

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.aql.migration.MigrationException;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * An abstract converter which helps the mapping between Acceleo 3 & Acceleo 4.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public abstract class AbstractConverter {

	/**
	 * Converts the Acceleo3 input into its Acceleo 4 equivalent.
	 * 
	 * @param input
	 *            the EMTL element
	 * @return the AST element
	 */
	protected abstract Object convert(EObject input);

	/**
	 * Converts all input elements and put them into the output list.
	 * 
	 * @param inputCollection
	 *            the input elements
	 * @param outputList
	 *            the output list
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	protected void map(Collection<? extends EObject> inputCollection, List outputList) {
		for (EObject inputElement : inputCollection) {
			Object outputElement = convert(inputElement);
			if (outputElement == null) {
				throw new MigrationException(inputElement);
			}
			if (outputElement instanceof Collection<?>) {
				outputList.addAll((Collection<?>)outputElement);
			} else {
				outputList.add(outputElement);
			}
		}
	}

	/**
	 * Wraps the given expression into an AST result.
	 * 
	 * @param astExpression
	 *            the expression to wrap
	 * @return the ast result
	 */
	protected static AstResult createAstResult(org.eclipse.acceleo.query.ast.Expression astExpression) {
		return new AstResult(astExpression, null, null, Diagnostic.OK_INSTANCE);
	}

}
