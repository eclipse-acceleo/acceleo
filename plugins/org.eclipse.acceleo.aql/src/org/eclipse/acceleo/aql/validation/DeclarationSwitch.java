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
import java.util.stream.Collectors;

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
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.validation.type.IType;
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
			final List<Object> res = new ArrayList<>();

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
			final List<Object> res = new ArrayList<>();

			final List<IService<?>> services = acceleoValidationResult.getDeclarationIService(call);
			if (withCompatibleServices) {
				for (IService<?> service : services) {
					res.addAll(getCompatibleServices(service));
				}
			} else {
				res.addAll(services);
			}

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
			final List<Object> res = new ArrayList<>();

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
			final List<Object> res = new ArrayList<>();

			final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
			final Object resolved = resolver.resolve(moduleReference.getQualifiedName());
			if (resolved != null) {
				res.add(resolved);
			}

			return res;
		}

		@Override
		public List<Object> caseTemplate(Template template) {
			final List<Object> res = new ArrayList<>();

			final IService<Template> templateService = new TemplateService(template, null, null, null);
			if (withCompatibleServices) {
				res.addAll(getCompatibleServices(templateService));
			} else {
				res.add(templateService);
			}

			return res;
		}

		@Override
		public List<Object> caseQuery(Query query) {
			final List<Object> res = new ArrayList<>();

			final IService<Query> queryService = new QueryService(query, null, null, null);
			if (withCompatibleServices) {
				res.addAll(getCompatibleServices(queryService));
			} else {
				res.add(queryService);
			}

			return res;
		}

		@Override
		public List<Object> caseVariable(Variable variable) {
			final List<Object> res = new ArrayList<>();

			res.add(variable);

			return res;
		}

		@Override
		public List<Object> caseModule(Module module) {
			final List<Object> res = new ArrayList<>();

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
	 * Tells if we should also return all compatible {@link IService}.
	 */
	private final boolean withCompatibleServices;

	/**
	 * The context qualified name.
	 */
	private String contextQualifiedName;

	/**
	 * Constructor.
	 * 
	 * @param acceleoValidationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param withCompatibleServices
	 *            tells if we should also return all compatible {@link IService}
	 */
	public DeclarationSwitch(IAcceleoValidationResult acceleoValidationResult,
			IQualifiedNameQueryEnvironment queryEnvironment, boolean withCompatibleServices) {
		super();
		addSwitch(new AQLDeclarationSwitch());
		addSwitch(new AcceleoDeclarationSwitch());
		this.acceleoValidationResult = acceleoValidationResult;
		this.queryEnvironment = queryEnvironment;
		this.withCompatibleServices = withCompatibleServices;
	}

	/**
	 * Gets the {@link List} of declarations {@link Object} for the given {@link EObject}.
	 * 
	 * @param contextQualifiedName
	 *            the context qualified name
	 * @param eObject
	 *            the {@link EObject} to get the declaration from
	 * @return the {@link List} of declarations {@link Object} for the given {@link EObject}
	 */
	public List<Object> getDeclarations(String contextQualifiedName, EObject eObject) {
		this.contextQualifiedName = contextQualifiedName;
		return doSwitch(eObject);
	}

	/**
	 * Gets all compatible services for the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @return all compatible services for the given {@link IService}.
	 */
	private List<IService<?>> getCompatibleServices(IService<?> service) {
		final List<IService<?>> res = new ArrayList<>();

		final IQualifiedNameLookupEngine lookupEngine = queryEnvironment.getLookupEngine();
		lookupEngine.pushImportsContext(contextQualifiedName, contextQualifiedName);
		try {
			final List<IService<?>> possibleServices = lookupEngine.getRegisteredServices().stream().filter(
					s -> s.getName().equals(service.getName()) && s.getNumberOfParameters() == service
							.getNumberOfParameters()).collect(Collectors.toList());

			List<IService<?>> currentCompatibleServices = new ArrayList<>();
			currentCompatibleServices.add(service);
			List<IService<?>> newCompatibleServices = new ArrayList<>();
			do {
				for (IService<?> currentCompatibleService : currentCompatibleServices) {
					final List<IType> serviceParameterTypes = currentCompatibleService.getParameterTypes(
							queryEnvironment);
					nextservice: for (IService<?> registeredService : possibleServices) {
						final List<IType> registeredServiceParameterTypes = registeredService
								.getParameterTypes(queryEnvironment);
						for (int i = 0; i < currentCompatibleService.getNumberOfParameters(); i++) {
							if (!serviceParameterTypes.get(i).isAssignableFrom(registeredServiceParameterTypes
									.get(i)) && !registeredServiceParameterTypes.get(i).isAssignableFrom(
											serviceParameterTypes.get(i))) {
								continue nextservice;
							}
						}
						if (!res.contains(registeredService)) {
							newCompatibleServices.add(registeredService);
						}
					}
				}
				res.addAll(newCompatibleServices);
				currentCompatibleServices = newCompatibleServices;
				newCompatibleServices = new ArrayList<>();
			} while (!currentCompatibleServices.isEmpty());
		} finally {
			lookupEngine.popContext(contextQualifiedName);
		}

		return res;
	}

}
