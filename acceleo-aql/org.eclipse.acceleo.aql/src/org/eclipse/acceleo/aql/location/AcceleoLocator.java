/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.location;

import java.util.List;

import org.eclipse.acceleo.aql.IAcceleoEnvironment;

/**
 * Locator service, to find the various usages (declaration, definition, references, etc.) of AST elements in
 * the Acceleo sources.
 * 
 * @author Florent Latombe
 */
public class AcceleoLocator {

	/**
	 * Provides the list of {@link AcceleoLocationLink} corresponding to the declaring location(s) of the
	 * Acceleo element found at the given position in the given source.
	 * 
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment}.
	 * @param source
	 *            the (non-{@code null}) source module.
	 * @param position
	 *            the (non-{@code null}) caret position in the source.
	 * @return the {@link List} of {@link AcceleoLocationLink} corresponding to the declaration location(s) of
	 *         the element at the given position of the source.
	 */
	public List<AcceleoLocationLink> getDeclarationLocations(IAcceleoEnvironment acceleoEnvironment,
			String source, int position) {
		// TODO: implement
		// What we probably want to do is something like this:
		// 1) Using the source and the position, find which element from the AST we are interested in.
		// 2) Find in the AST where that element is declared.
		// 3) If the AST/Parser has some form of trace between the AST and the parsed text, then return the
		// corresponding location.
		// Not sure yet how cross-file etc. is done in Acceleo.
		throw new UnsupportedOperationException("TODO: implement goto declaration");
	}

	/**
	 * Provides the list of {@link AcceleoLocationLink} corresponding to the definition location(s) of the
	 * Acceleo element found at the given position in the given source.
	 * 
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment}.
	 * @param source
	 *            the (non-{@code null}) source module.
	 * @param position
	 *            the (non-{@code null}) caret position in the source.
	 * @return the {@link List} of {@link AcceleoLocationLink} corresponding to the definition location(s) of
	 *         the element at the given position of the source.
	 */
	public List<AcceleoLocationLink> getDefinitionLocations(IAcceleoEnvironment acceleoEnvironment,
			String source, int position) {
		// TODO: implement
		// What we probably want to do is something like this:
		// 1) Using the source and the position, find which element from the AST we are interested in.
		// 2) Find in the AST where that element is defined.
		// 3) If the AST/Parser has some form of trace between the AST and the parsed text, then return the
		// corresponding location.
		// Not sure yet how cross-file etc. is done in Acceleo.
		throw new UnsupportedOperationException("TODO: implement goto definition");
	}
}
