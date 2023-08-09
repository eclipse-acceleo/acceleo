/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.outline;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.validation.AcceleoValidationUtils;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * An {@link AcceleoSwitch} to produce the 'main' {@link AcceleoSymbol} representing an element from the AST
 * of an Acceleo module.
 * 
 * @author Florent Latombe
 */
public class AcceleoAstOutliner extends AcceleoSwitch<AcceleoSymbol> {

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private final IAcceleoValidationResult acceleoValidationResult;

	/**
	 * Constructor.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult} resulting from validating the Acceleo contents of the
	 *            elements we want to outline.
	 */
	public AcceleoAstOutliner(IAcceleoValidationResult acceleoValidationResult) {
		this.acceleoValidationResult = acceleoValidationResult;
	}

	/**
	 * Creates a new {@link AcceleoSymbol} based on the known {@link IAcceleoValidationResult}.
	 * 
	 * @param semanticElement
	 *            the (non-{@code null}) {@link AcceleoASTNode} represented by the created symbol.
	 * @param symbolName
	 *            the (non-{@code null}) name of the created symbol.
	 * @param symbolDetails
	 *            the (maybe-{@code null}) additional details of the symbol.
	 * @return the newly-created non-{@code null} {@link AcceleoSymbol}.
	 */
	private AcceleoSymbol createSymbol(AcceleoASTNode semanticElement, String symbolName,
			String symbolDetails) {
		return new AcceleoSymbol(semanticElement, this.acceleoValidationResult, symbolName, symbolDetails);
	}

	/**
	 * For a {@link Metamodel}, the symbol displays the namespace URI.
	 * 
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseMetamodel(org.eclipse.acceleo.Metamodel)
	 */
	@Override
	public AcceleoSymbol caseMetamodel(Metamodel metamodel) {
		String details = metamodel.getReferencedPackage().getNsURI();
		return this.createSymbol(metamodel, metamodel.getReferencedPackage().getName(), details);
	}

	@Override
	public AcceleoSymbol caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		return this.createSymbol(errorMetamodel, "", "");
	}

	/**
	 * For a {@link Query}, the symbol displays the types of the parameters, the return type, and has children
	 * to represent the parameters (name and type).
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseQuery(org.eclipse.acceleo.Query)
	 */
	@Override
	public AcceleoSymbol caseQuery(Query query) {
		final AcceleoSymbol res;

		// Symbol name: "QueryName(TypeOfParameter1, TypeOfParameter2)"
		String symbolName = query.getName();
		if (!query.getParameters().isEmpty()) {
			symbolName += "(";
			symbolName += AcceleoValidationUtils.getVariablesListRepresentation(query.getParameters(),
					this.acceleoValidationResult);
			symbolName += ")";
		}

		// Symbol details: return type.
		String symbolDetails = AcceleoValidationUtils.getPossibleTypesRepresentation(query,
				this.acceleoValidationResult);

		if (symbolName != null) {
			AcceleoSymbol symbolForQuery = this.createSymbol(query, symbolName, symbolDetails);

			// Children represent the parameters of the query.
			for (Variable parameter : query.getParameters()) {
				AcceleoSymbol symbolForParameter = this.doSwitch(parameter);
				symbolForQuery.getChildren().add(symbolForParameter);
			}
			res = symbolForQuery;
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * For a {@link Variable}, the symbol displays the name and type.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseVariable(org.eclipse.acceleo.Variable)
	 */
	@Override
	public AcceleoSymbol caseVariable(Variable variable) {
		String symbolName = variable.getName();
		String symbolDetails = AcceleoValidationUtils.getPossibleTypesRepresentation(variable,
				this.acceleoValidationResult);

		AcceleoSymbol symbolForVariable = this.createSymbol(variable, symbolName, symbolDetails);
		return symbolForVariable;
	}

	/**
	 * For a {@link Template}, the symbol displays the types of the parameters, the return type (String), and
	 * has children to represent the parameters (name and type) and the blocks of its body (if, for, etc.).
	 * 
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseTemplate(org.eclipse.acceleo.Template)
	 */
	@Override
	public AcceleoSymbol caseTemplate(Template template) {
		final AcceleoSymbol res;

		// Symbol name: "TemplateName(TypeOfParameter1, TypeOfParameter2)"
		String symbolName = template.getName();
		if (!template.getParameters().isEmpty()) {
			symbolName += "(";
			symbolName += AcceleoValidationUtils.getVariablesListRepresentation(template.getParameters(),
					this.acceleoValidationResult);
			symbolName += ")";
		}

		// Symbol details: return type.
		String symbolDetails = "String";
		if (symbolName != null) {
			AcceleoSymbol symbolForTemplate = this.createSymbol(template, symbolName, symbolDetails);

			// Children for the parameters.
			for (Variable parameter : template.getParameters()) {
				AcceleoSymbol symbolForParameter = this.doSwitch(parameter);
				symbolForTemplate.getChildren().add(symbolForParameter);
			}

			// TODO: complete this if we want the Outline View to have the same information as in Acceleo 3.
			// i.e. have file/if/for/protected statements appear as children of the template they are in.
			// Block bodyBlock = template.getBody();
			// if (bodyBlock != null) {
			// AcceleoSymbol symbolForBody = this.createSymbol(bodyBlock, "Template Body", "");
			// symbolForTemplate.getChildren().add(symbolForBody);
			// bodyBlock.getStatements().forEach(bodyStatement -> {
			// symbolForBody.getChildren().add(this.doSwitch(bodyStatement));
			// // TODO: implement cases for let, for, if, etc.
			// });
			// }
			res = symbolForTemplate;
		} else {
			res = null;
		}

		return res;
	}

}
