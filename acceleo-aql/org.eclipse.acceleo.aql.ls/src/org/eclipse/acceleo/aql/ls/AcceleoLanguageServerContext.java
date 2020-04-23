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
package org.eclipse.acceleo.aql.ls;

import java.net.URI;
import java.util.Map;

import org.eclipse.acceleo.aql.IAcceleoEnvironment;

/**
 * The context of the {@link AcceleoLanguageServer} provided by the runtime that hosts the server. It is used
 * for the integration of the Acceleo editor features into the host IDE.
 * 
 * @author Florent Latombe
 */
public interface AcceleoLanguageServerContext {

	/**
	 * Creates the {@link IAcceleoEnvironment} for an Acceleo document designated by its {@link URI}.
	 * 
	 * @param acceleoDocumentUri
	 *            the (non-{@code null}) {@link URI} of an Acceleo document.
	 * @return the newly-created {@link IAcceleoEnvironment} for the designated Acceleo document.
	 */
	IAcceleoEnvironment createAcceleoEnvironmentFor(URI acceleoDocumentUri);

	/**
	 * Recursively navigates the folder designated by the given {@link URI} to find all the Acceleo text
	 * documents.
	 * 
	 * @param folderUri
	 *            the {@link String URI} to navigate.
	 * @return the {@link Map} of each {@link URI Acceleo text document URI} to its {@link String contents}.
	 */
	Map<URI, String> getAllAcceleoDocumentsIn(String folderUri);

}
