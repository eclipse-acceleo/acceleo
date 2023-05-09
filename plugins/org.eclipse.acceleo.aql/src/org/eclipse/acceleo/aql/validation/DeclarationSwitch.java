/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Declaration;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;

/**
 * Gets the declaration of an {@link EObject}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DeclarationSwitch extends ComposedSwitch<List<Object>> {

	/**
	 * AQL declaration switch.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private class AQLDeclarationSwitch extends AstSwitch<List<Object>> {

		@Override
		public List<Object> caseVarRef(VarRef varRef) {
			List<Object> res = new ArrayList<>();

			final Declaration declaration = acceleoValidationResult.getDeclaration(varRef);
			if (declaration != null) {
				res.add(declaration);
			} else {
				final Variable variable = acceleoValidationResult.getDeclarationVariable(varRef);
				if (variable != null) {
					res.add(variable);
				} else {
					// variable reference is unresolved
				}
			}

			return res;
		}

		@Override
		public List<Object> caseCall(Call call) {
			List<Object> res = new ArrayList<>();

			List<IService<?>> services = acceleoValidationResult.getDeclarationIService(call);
			res.addAll(services);

			return res;
		}

		@Override
		public List<Object> caseStringLiteral(StringLiteral stringLiteral) {
			final List<Object> res;
			if (isInFeatureAccessCall(stringLiteral)) {
				res = caseCall((Call)stringLiteral.eContainer());
			} else {
				res = Collections.emptyList();
			}

			return res;
		}

		private boolean isInFeatureAccessCall(StringLiteral stringLiteral) {
			final boolean res;

			final EObject container = stringLiteral.eContainer();
			if (container instanceof Call) {
				final Call call = (Call)container;
				res = AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME.equals(call.getServiceName()) && call
						.getArguments().size() == 2 && call.getArguments().get(1) == stringLiteral;
			} else {
				res = false;
			}

			return res;
		}

		@Override
		public List<Object> caseDeclaration(Declaration declaration) {
			List<Object> res = new ArrayList<>();

			res.add(declaration);

			return res;
		}

		@Override
		public List<Object> defaultCase(EObject object) {
			return Collections.emptyList();
		}
	}

	/**
	 * Acceleo declaration switch.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private class AcceleoDeclarationSwitch extends AcceleoSwitch<List<Object>> {

		@Override
		public List<Object> caseModuleReference(ModuleReference moduleReference) {
			List<Object> res = new ArrayList<>();

			final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
			final Object resolved = resolver.resolve(moduleReference.getQualifiedName());
			if (resolved != null) {
				res.add(resolved);
			}

			return res;
		}

		@Override
		public List<Object> caseTemplate(Template template) {
			List<Object> res = new ArrayList<>();

			res.add(new TemplateService(template, null, null, null));

			return res;
		}

		@Override
		public List<Object> caseQuery(Query query) {
			List<Object> res = new ArrayList<>();

			res.add(new QueryService(query, null, null, null));

			return res;
		}

		@Override
		public List<Object> caseVariable(Variable variable) {
			List<Object> res = new ArrayList<>();

			res.add(variable);

			return res;
		}

		@Override
		public List<Object> caseModule(Module module) {
			List<Object> res = new ArrayList<>();

			res.add(module);

			return res;
		}

		@Override
		public List<Object> defaultCase(EObject object) {
			return Collections.emptyList();
		}
	}

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private final IAcceleoValidationResult acceleoValidationResult;

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 */
	public DeclarationSwitch(IAcceleoValidationResult acceleoValidationResult,
			IQualifiedNameQueryEnvironment queryEnvironment) {
		super();
		addSwitch(new AQLDeclarationSwitch());
		addSwitch(new AcceleoDeclarationSwitch());
		this.acceleoValidationResult = acceleoValidationResult;
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * Gets the {@link List} of declarations {@link Object} for the given {@link EObject}.
	 * 
	 * @param eObject
	 *            the {@link EObject} to get the declaration from
	 * @return the {@link List} of declarations {@link Object} for the given {@link EObject}
	 */
	public List<Object> getDeclarations(EObject eObject) {
		return doSwitch(eObject);
	}

}
