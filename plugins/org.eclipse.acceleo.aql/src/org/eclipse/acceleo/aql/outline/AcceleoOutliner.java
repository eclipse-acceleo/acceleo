/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;

/**
 * Outliner service, to provide the list of the various symbols declared in an Acceleo module.
 * 
 * @author Florent Latombe
 */
public class AcceleoOutliner {

	/**
	 * Constructor.
	 */
	public AcceleoOutliner() {
	}

	/**
	 * Provides all the symbols (queries, templates, etc.) declared in the {@link Module} of the given
	 * {@link IAcceleoValidationResult}.
	 * 
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult}.
	 * @return the {@link List} of all the {@link AcceleoSymbol symbols} defined in
	 *         {@code acceleoValidationResults}.
	 */
	public List<AcceleoSymbol> getAllDeclaredSymbols(IAcceleoValidationResult acceleoValidationResult) {
		// The validation result contains type information we need for the outline.
		AcceleoAstOutliner acceleoAstOutliner = new AcceleoAstOutliner(acceleoValidationResult);

		Module acceleoModule = acceleoValidationResult.getAcceleoAstResult().getModule();
		List<AcceleoSymbol> symbols = new ArrayList<>();

		// Display the same outline as in Acceleo 3, that is, the metamodels on which the module is based and
		// the templates/queries.
		for (Metamodel metamodel : acceleoModule.getMetamodels()) {
			AcceleoSymbol symbolForMetamodel = acceleoAstOutliner.doSwitch(metamodel);
			if (symbolForMetamodel != null) {
				symbols.add(symbolForMetamodel);
			}
		}

		for (ModuleElement moduleElement : acceleoModule.getModuleElements()) {
			AcceleoSymbol symbolForModuleElement = acceleoAstOutliner.doSwitch(moduleElement);
			if (symbolForModuleElement != null) {
				symbols.add(symbolForModuleElement);
			}
		}

		return symbols;
	}

}
