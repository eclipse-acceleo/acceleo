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
package org.eclipse.acceleo.aql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Utility class for Acceleo.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AcceleoUtil {

	/**
	 * Constructor.
	 */
	private AcceleoUtil() {
		// utility class can't be instantiated
	}

	/**
	 * Gets the {@link Template#isMain() main} {@link Template} of the given {@link Module}.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the {@link Template#isMain() main} {@link Template} of the given {@link Module} if any,
	 *         <code>null</code> otherwise
	 */
	public static Template getMainTemplate(Module module) {
		Template res = null;

		for (ModuleElement moduleElement : module.getModuleElements()) {
			if (moduleElement instanceof Template && ((Template)moduleElement).isMain()) {
				res = (Template)moduleElement;
				break;
			}
		}

		return res;
	}

	/**
	 * Generates with the given {@link AcceleoEvaluator} and {@link IAcceleoEnvironment}.
	 * 
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param acceleoEnvironment
	 *            the {@link IAcceleoEnvironment}
	 * @param module
	 *            the {@link Module}
	 * @param model
	 *            the {@link Resource} containing the model
	 */
	public static void generate(AcceleoEvaluator evaluator, IAcceleoEnvironment acceleoEnvironment,
			Module module, Resource model) {
		final IQueryEnvironment queryEnvironment = acceleoEnvironment.getQueryEnvironment();

		final EObjectServices services = new EObjectServices(queryEnvironment, null, null);
		final Template main = getMainTemplate(module);
		// TODO more than one parameter is allowed ?
		// TODO not EClass type ?
		// TODO more than one EClass type ?
		final String parameterName = main.getParameters().get(0).getName();
		// TODO use IType ?
		final EClass parameterType = (EClass)((TypeLiteral)main.getParameters().get(0).getType().getAst())
				.getValue();
		final List<EObject> values = new ArrayList<EObject>();
		for (EObject root : model.getContents()) {
			if (parameterType.isInstance(root)) {
				values.add(root);
			}
			values.addAll(services.eAllContents(root, parameterType));
		}

		final Map<String, Object> variables = new HashMap<String, Object>();
		for (EObject value : values) {
			variables.put(parameterName, value);
			evaluator.generate(module, variables);
		}
	}

}
