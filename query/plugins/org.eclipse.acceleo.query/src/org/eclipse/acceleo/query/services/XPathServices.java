/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EClassImpl.FeatureSubsetSupplier;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Services on {@link EObject}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class XPathServices extends AbstractServiceProvider {

	/**
	 * {@link EClass} containment message.
	 */
	private static final String ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S = "Only EClass can be contained into other EClasses not %s";

	/**
	 * Can't contain directly or indirectly message.
	 */
	private static final String S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S = "%s can't contain directly or indirectly %s";

	/**
	 * Ancestors {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class AncestorsService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private AncestorsService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> eClasses = services.getEClasses(argTypes.get(0));
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					if (eCls == EcorePackage.eINSTANCE.getEObject()) {
						if (argTypes.size() == 1) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, eCls)));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierLiteralType) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, ((EClassifierLiteralType)argTypes.get(1)).getType())));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierSetLiteralType) {
							for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
									.getEClassifiers()) {
								result.add(new SequenceType(queryEnvironment, new EClassifierType(
										queryEnvironment, eClassifier)));
							}
						} else if (argTypes.size() == 2) {
							result.addAll(super.getType(call, services, validationResult, queryEnvironment,
									argTypes));
						}
					} else {
						result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
					}
				}
			} else {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S, argTypes.get(0))));
			}

			return result;
		}

		/**
		 * Gets the {@link IType} of elements returned by the service when the receiver type is not the
		 * {@link EObject} {@link EClass}.
		 * 
		 * @param services
		 *            the {@link ValidationServices}
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param argTypes
		 *            arguments {@link IType}
		 * @param receiverEClass
		 *            the receiver type can't be {@link EObject} {@link EClass}
		 * @return the {@link IType} of elements returned by the service when the receiver type is not the
		 *         {@link EObject} {@link EClass}
		 */
		private Set<IType> getTypeForSpecificType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getAllContainingEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							containingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing("%s can't be contained",
							argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = new LinkedHashSet<IType>();
				if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
					for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
							.getEClassifiers()) {
						filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				} else if (argTypes.get(1) instanceof EClassifierLiteralType) {
					filterTypes.add(argTypes.get(1));
				} else {
					addEObjectEClass(filterTypes, queryEnvironment);
				}
				for (IType filterType : filterTypes) {
					for (EClass containingEClass : queryEnvironment.getEPackageProvider()
							.getAllContainingEClasses(receiverEClass)) {
						final Set<IType> intersectionTypes = services.intersection(new EClassifierType(
								queryEnvironment, containingEClass), filterType);
						for (IType type : intersectionTypes) {
							result.add(new SequenceType(queryEnvironment, type));
						}
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S, argTypes.get(1), argTypes.get(0))));
				}
			}

			return result;
		}

	}

	/**
	 * FollowingSiblings {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class FollowingSiblingsService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private FollowingSiblingsService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> eClasses = services.getEClasses(argTypes.get(0));
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					if (eCls == EcorePackage.eINSTANCE.getEObject()) {
						if (argTypes.size() == 1) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, eCls)));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierLiteralType) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, ((EClassifierLiteralType)argTypes.get(1)).getType())));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierSetLiteralType) {
							for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
									.getEClassifiers()) {
								result.add(new SequenceType(queryEnvironment, new EClassifierType(
										queryEnvironment, eClassifier)));
							}
						} else if (argTypes.size() == 2) {
							result.addAll(super.getType(call, services, validationResult, queryEnvironment,
									argTypes));
						}
					} else {
						result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
					}
				}
			} else {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						"Only EClass can have following siblings not %s", argTypes.get(0))));
			}

			return result;
		}

		/**
		 * Gets the {@link IType} of elements returned by the service when the receiver type is not the
		 * {@link EObject} {@link EClass}.
		 * 
		 * @param services
		 *            the {@link ValidationServices}
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param argTypes
		 *            arguments {@link IType}
		 * @param receiverEClass
		 *            the receiver type can't be {@link EObject} {@link EClass}
		 * @return the {@link IType} of elements returned by the service when the receiver type is not the
		 *         {@link EObject} {@link EClass}
		 */
		private Set<IType> getTypeForSpecificType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass followingEClass : queryEnvironment.getEPackageProvider()
						.getFollowingSiblingsEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							followingEClass)));
				}
				// for siblings root of a resource (any EObject)
				result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
						EcorePackage.eINSTANCE.getEObject())));
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = new LinkedHashSet<IType>();
				if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
					for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
							.getEClassifiers()) {
						filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				} else if (argTypes.get(1) instanceof EClassifierLiteralType) {
					filterTypes.add(argTypes.get(1));
				} else {
					addEObjectEClass(filterTypes, queryEnvironment);
				}
				for (IType filterType : filterTypes) {
					for (EClass followingEClass : queryEnvironment.getEPackageProvider()
							.getFollowingSiblingsEClasses(receiverEClass)) {
						final Set<IType> intersectionTypes = services.intersection(new EClassifierType(
								queryEnvironment, followingEClass), filterType);
						for (IType type : intersectionTypes) {
							result.add(new SequenceType(queryEnvironment, type));
						}
					}
					// for siblings root of a resource (filtered EObject)
					result.add(new SequenceType(queryEnvironment, services.lower(filterType, filterType)));
				}
			}

			return result;
		}

	}

	/**
	 * PrecedingSiblings {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class PrecedingSiblingsService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private PrecedingSiblingsService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> eClasses = services.getEClasses(argTypes.get(0));
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					if (eCls == EcorePackage.eINSTANCE.getEObject()) {
						if (argTypes.size() == 1) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, eCls)));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierLiteralType) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, ((EClassifierLiteralType)argTypes.get(1)).getType())));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierSetLiteralType) {
							for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
									.getEClassifiers()) {
								result.add(new SequenceType(queryEnvironment, new EClassifierType(
										queryEnvironment, eClassifier)));
							}
						} else if (argTypes.size() == 2) {
							result.addAll(super.getType(call, services, validationResult, queryEnvironment,
									argTypes));
						}
					} else {
						result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
					}
				}
			} else {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						"Only EClass can have preceding siblings not %s", argTypes.get(0))));
			}

			return result;
		}

		/**
		 * Gets the {@link IType} of elements returned by the service when the receiver type is not the
		 * {@link EObject} {@link EClass}.
		 * 
		 * @param services
		 *            the {@link ValidationServices}
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param argTypes
		 *            arguments {@link IType}
		 * @param receiverEClass
		 *            the receiver type can't be {@link EObject} {@link EClass}
		 * @return the {@link IType} of elements returned by the service when the receiver type is not the
		 *         {@link EObject} {@link EClass}
		 */
		private Set<IType> getTypeForSpecificType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass precedingEClass : queryEnvironment.getEPackageProvider()
						.getPrecedingSiblingsEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							precedingEClass)));
				}
				// for siblings root of a resource (any EObject)
				result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
						EcorePackage.eINSTANCE.getEObject())));
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = new LinkedHashSet<IType>();
				if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
					for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
							.getEClassifiers()) {
						filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				} else if (argTypes.get(1) instanceof EClassifierLiteralType) {
					filterTypes.add(argTypes.get(1));
				} else {
					addEObjectEClass(filterTypes, queryEnvironment);
				}
				for (IType filterType : filterTypes) {
					for (EClass precedingEClass : queryEnvironment.getEPackageProvider()
							.getPrecedingSiblingsEClasses(receiverEClass)) {
						final Set<IType> intersectionTypes = services.intersection(new EClassifierType(
								queryEnvironment, precedingEClass), filterType);
						for (IType type : intersectionTypes) {
							result.add(new SequenceType(queryEnvironment, type));
						}
					}
					// for siblings root of a resource (filtered EObject)
					result.add(new SequenceType(queryEnvironment, services.lower(filterType, filterType)));
				}
			}

			return result;
		}

	}

	/**
	 * Siblings {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class SiblingsService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private SiblingsService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> eClasses = services.getEClasses(argTypes.get(0));
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					if (eCls == EcorePackage.eINSTANCE.getEObject()) {
						if (argTypes.size() == 1) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, eCls)));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierLiteralType) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, ((EClassifierLiteralType)argTypes.get(1)).getType())));
						} else if (argTypes.size() == 2 && argTypes.get(
								1) instanceof EClassifierSetLiteralType) {
							for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
									.getEClassifiers()) {
								result.add(new SequenceType(queryEnvironment, new EClassifierType(
										queryEnvironment, eClassifier)));
							}
						} else if (argTypes.size() == 2) {
							result.addAll(super.getType(call, services, validationResult, queryEnvironment,
									argTypes));
						}
					} else {
						result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
					}
				}
			} else {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						"Only EClass can have siblings not %s", argTypes.get(0))));
			}

			return result;
		}

		/**
		 * Gets the {@link IType} of elements returned by the service when the receiver type is not the
		 * {@link EObject} {@link EClass}.
		 * 
		 * @param services
		 *            the {@link ValidationServices}
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param argTypes
		 *            arguments {@link IType}
		 * @param receiverEClass
		 *            the receiver type can't be {@link EObject} {@link EClass}
		 * @return the {@link IType} of elements returned by the service when the receiver type is not the
		 *         {@link EObject} {@link EClass}
		 */
		private Set<IType> getTypeForSpecificType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass siblingEClass : queryEnvironment.getEPackageProvider().getSiblingsEClasses(
						receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							siblingEClass)));
				}
				// for siblings root of a resource (any EObject)
				result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
						EcorePackage.eINSTANCE.getEObject())));
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = new LinkedHashSet<IType>();
				if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
					for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1))
							.getEClassifiers()) {
						filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				} else if (argTypes.get(1) instanceof EClassifierLiteralType) {
					filterTypes.add(argTypes.get(1));
				} else {
					addEObjectEClass(filterTypes, queryEnvironment);
				}
				for (IType filterType : filterTypes) {
					for (EClass siblingEClass : queryEnvironment.getEPackageProvider().getSiblingsEClasses(
							receiverEClass)) {
						final Set<IType> intersectionTypes = services.intersection(new EClassifierType(
								queryEnvironment, siblingEClass), filterType);
						for (IType type : intersectionTypes) {
							result.add(new SequenceType(queryEnvironment, type));
						}
					}
					// for siblings root of a resource (filtered EObject)
					result.add(new SequenceType(queryEnvironment, services.lower(filterType, filterType)));
				}
			}

			return result;
		}
	}

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 */
	public XPathServices(IReadOnlyQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	@Override
	protected IService<Method> getService(Method publicMethod, boolean forWorkspace) {
		final IService<Method> result;
		if ("ancestors".equals(publicMethod.getName())) {
			result = new AncestorsService(publicMethod, this, forWorkspace);
		} else if ("followingSiblings".equals(publicMethod.getName())) {
			result = new FollowingSiblingsService(publicMethod, this, forWorkspace);
		} else if ("precedingSiblings".equals(publicMethod.getName())) {
			result = new PrecedingSiblingsService(publicMethod, this, forWorkspace);
		} else if ("siblings".equals(publicMethod.getName())) {
			result = new SiblingsService(publicMethod, this, forWorkspace);
		} else {
			result = new JavaMethodService(publicMethod, this, forWorkspace);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the full set of object's ancestors.",
		params = {
			@Param(name = "self", value = "The EObject we seek the ancestors of")
		},
		result = "Sequence containing the full set of the receiver's ancestors",
		examples = {
			@Example(expression = "eClass.ancestors()", result = "Sequence{parentEPackage, grandParentEPackage}")
		}
	)
	// @formatter:on
	public List<EObject> ancestors(EObject self) {
		// TODO lazy collection + predicate
		final List<EObject> result = new ArrayList<EObject>();

		EObject container = self.eContainer();
		while (container != null) {
			result.add(container);
			container = container.eContainer();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's ancestors of the given type.",
		params = {
			@Param(name = "self", value = "The EObject we seek the ancestors of"),
			@Param(name = "filter", value = "The filtering EClass")
		},
		result = "Sequence containing the set of the receiver's ancestors of the given type",
		examples = {
			@Example(expression = "eClass.ancestors(ecore::EPackage)", result = "Sequence{parentEPackage, grandParentEPackage}")
		}
	)
	// @formatter:on
	public List<EObject> ancestors(EObject self, EClass filter) {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(filter);

		return ancestors(self, filters);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's ancestors of any given types.",
		params = {
			@Param(name = "self", value = "The EObject we seek the ancestors of"),
			@Param(name = "filters", value = "The set of filtering EClasses")
		},
		result = "Sequence containing the set of the receiver's ancestors of any given types",
		examples = {
			@Example(expression = "eClass.ancestors({ecore::EPackage | ecore::EClass})", result = "Sequence{parentEPackage, grandParentEPackage}")
		}
	)
	// @formatter:on
	public List<EObject> ancestors(EObject self, Set<EClass> filters) {
		// TODO lazy collection + predicate
		final List<EObject> result = new ArrayList<EObject>();

		EObject container = self.eContainer();
		while (container != null) {
			if (eIsInstanceOf(container, filters)) {
				result.add(container);
			}
			container = container.eContainer();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the full set of object's following siblings.",
		params = {
			@Param(name = "self", value = "The EObject we seek the following siblings of")
		},
		result = "Sequence containing the full set of the receiver's following siblings",
		examples = {
			@Example(expression = "eClass3.followingSiblings()", result = "Sequence{eClass4, eClass5}")
		}
	)
	// @formatter:on
	public List<EObject> followingSiblings(EObject self) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(self);
		if (container != null) {
			result = getContentsFrom(container, self);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's following siblings of the given type.",
		params = {
			@Param(name = "self", value = "The EObject we seek the following siblings of"),
			@Param(name = "filter", value = "The filtering EClass")
		},
		result = "Sequence containing the set of the receiver's following siblings of the given type",
		examples = {
			@Example(expression = "eClass3.followingSiblings(ecore::EClass)", result = "Sequence{eClass4, eClass5}")
		}
	)
	// @formatter:on
	public List<EObject> followingSiblings(EObject self, EClass filter) {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(filter);

		return followingSiblings(self, filters);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's following siblings of any given types.",
		params = {
			@Param(name = "self", value = "The EObject we seek the following siblings of"),
			@Param(name = "filters", value = "The set of filtering EClasses")
		},
		result = "Sequence containing the set of the receiver's following siblings of any given types",
		examples = {
			@Example(expression = "eClass3.followingSiblings({ecore::EPackage | ecore::EClass})", result = "Sequence{eClass4, eClass5}")
		}
	)
	// @formatter:on
	public List<EObject> followingSiblings(EObject eObject, Set<EClass> filters) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(eObject);
		if (container instanceof EObject) {
			result = new ArrayList<EObject>();
			final EStructuralFeature containingFeature = eObject.eContainingFeature();
			final List<EStructuralFeature> followingFeatures = getFollowingFeatures((EObject)container,
					containingFeature, filters);
			boolean collect = followingFeatures.size() > 0 && followingFeatures.get(0) != containingFeature;
			for (EStructuralFeature feature : followingFeatures) {
				final Object value = ((EObject)container).eGet(feature);
				if (value instanceof Collection<?>) {
					for (Object child : (Collection<?>)value) {
						if (child == eObject) {
							collect = true;
						} else if (collect && child instanceof EObject && eIsInstanceOf((EObject)child,
								filters)) {
							result.add((EObject)child);
						}
					}
				} else if (value == eObject) {
					collect = true;
				} else if (collect && value instanceof EObject && eIsInstanceOf((EObject)value, filters)) {
					result.add((EObject)value);
				}
			}
		} else if (container instanceof Resource) {
			result = new ArrayList<EObject>();
			for (EObject eObj : getRootsFrom((Resource)container, eObject)) {
				if (eIsInstanceOf(eObj, filters)) {
					result.add(eObj);
				}
			}
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	/**
	 * Gets the following {@link EStructuralFeature} for the given container and containing feature.
	 * 
	 * @param container
	 *            the {@link EObject} container
	 * @param containingFeature
	 *            the containing {@link EStructuralFeature}
	 * @param filters
	 *            the {@link EClass} filters
	 * @return the following {@link EStructuralFeature} for the given container and containing feature
	 */
	private List<EStructuralFeature> getFollowingFeatures(EObject container,
			EStructuralFeature containingFeature, Set<EClass> filters) {
		final List<EStructuralFeature> result = new ArrayList<EStructuralFeature>();

		final Set<EStructuralFeature> filteredFeatures = new LinkedHashSet<EStructuralFeature>();
		for (EClass eCls : filters) {
			filteredFeatures.addAll(queryEnvironment.getEPackageProvider().getContainingEStructuralFeatures(
					eCls));
		}
		final EStructuralFeature[] containmentFeatures = ((FeatureSubsetSupplier)((EObject)container).eClass()
				.getEAllStructuralFeatures()).containments();
		boolean collect = false;
		for (EStructuralFeature containmentFeature : containmentFeatures) {
			if (containmentFeature == containingFeature) {
				collect = true;
			}
			if (collect && filteredFeatures.contains(containmentFeature)) {
				result.add(containmentFeature);
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the full set of object's preceding siblings.",
		params = {
			@Param(name = "self", value = "The EObject we seek the preceding siblings of")
		},
		result = "Sequence containing the full set of the receiver's preceding siblings",
		examples = {
			@Example(expression = "eClass3.precedingSiblings()", result = "Sequence{eClass1, eClass2}")
		}
	)
	// @formatter:on
	public List<EObject> precedingSiblings(EObject self) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(self);
		if (container != null) {
			result = getContentsUntil(container, self);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's preceding siblings of the given type.",
		params = {
			@Param(name = "self", value = "The EObject we seek the preceding siblings of"),
			@Param(name = "filter", value = "The filtering EClass")
		},
		result = "Sequence containing the set of the receiver's preceding siblings of the given type",
		examples = {
			@Example(expression = "eClass3.precedingSiblings(ecore::EClass)", result = "Sequence{eClass1, eClass2}")
		}
	)
	// @formatter:on
	public List<EObject> precedingSiblings(EObject self, EClass filter) {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(filter);

		return precedingSiblings(self, filters);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's preceding siblings of any given types.",
		params = {
			@Param(name = "self", value = "The EObject we seek the preceding siblings of"),
			@Param(name = "filters", value = "The set of filtering EClasses")
		},
		result = "Sequence containing the set of the receiver's preceding siblings of any given types",
		examples = {
			@Example(expression = "eClass3.precedingSiblings({ecore::EPackage | ecore::EClass})", result = "Sequence{eClass1, eClass2}")
		}
	)
	// @formatter:on
	public List<EObject> precedingSiblings(EObject eObject, Set<EClass> filters) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(eObject);
		if (container instanceof EObject) {
			result = new ArrayList<EObject>();
			final EStructuralFeature containingFeature = eObject.eContainingFeature();
			final List<EStructuralFeature> precedingFeatures = getPrecedingFeatures((EObject)container,
					containingFeature, filters);
			finish: for (EStructuralFeature feature : precedingFeatures) {
				final Object value = ((EObject)container).eGet(feature);
				if (value instanceof Collection<?>) {
					for (Object child : (Collection<?>)value) {
						if (child == eObject) {
							break finish;
						} else if (child instanceof EObject && eIsInstanceOf((EObject)child, filters)) {
							result.add((EObject)child);
						}
					}
				} else if (value == eObject) {
					break finish;
				} else if (value instanceof EObject && eIsInstanceOf((EObject)value, filters)) {
					result.add((EObject)value);
				}
			}
		} else if (container instanceof Resource) {
			result = new ArrayList<EObject>();
			for (EObject eObj : getRootsUntil((Resource)container, eObject)) {
				if (eIsInstanceOf(eObj, filters)) {
					result.add(eObj);
				}
			}
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	/**
	 * Gets the preceding {@link EStructuralFeature} for the given container and containing feature.
	 * 
	 * @param container
	 *            the {@link EObject} container
	 * @param containingFeature
	 *            the containing {@link EStructuralFeature}
	 * @param filters
	 *            the {@link EClass} filters
	 * @return the preceding {@link EStructuralFeature} for the given container and containing feature
	 */
	private List<EStructuralFeature> getPrecedingFeatures(EObject container,
			EStructuralFeature containingFeature, Set<EClass> filters) {
		final List<EStructuralFeature> result = new ArrayList<EStructuralFeature>();

		final Set<EStructuralFeature> filteredFeatures = new LinkedHashSet<EStructuralFeature>();
		for (EClass eCls : filters) {
			filteredFeatures.addAll(queryEnvironment.getEPackageProvider().getContainingEStructuralFeatures(
					eCls));
		}
		final EStructuralFeature[] containmentFeatures = ((FeatureSubsetSupplier)((EObject)container).eClass()
				.getEAllStructuralFeatures()).containments();
		for (EStructuralFeature containmentFeature : containmentFeatures) {
			if (filteredFeatures.contains(containmentFeature)) {
				result.add(containmentFeature);
			}
			if (containmentFeature == containingFeature) {
				break;
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the full set of object's siblings.",
		params = {
			@Param(name = "self", value = "The EObject we seek the siblings of")
		},
		result = "Sequence containing the full set of the receiver's siblings",
		examples = {
			@Example(expression = "eClass3.siblings()", result = "Sequence{eClass1, eClass2, eClass4, eClass5}")
		}
	)
	// @formatter:on
	public List<EObject> siblings(EObject self) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(self);
		if (container != null) {
			result = getContentsExcluding(container, self);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's siblings of the given type.",
		params = {
			@Param(name = "self", value = "The EObject we seek the siblings of"),
			@Param(name = "filter", value = "The filtering EClass")
		},
		result = "Sequence containing the set of the receiver's siblings of the given type",
		examples = {
			@Example(expression = "eClass3.siblings(ecore::EClass)", result = "Sequence{eClass1, eClass2, eClass4, eClass5}")
		}
	)
	// @formatter:on
	public List<EObject> siblings(final EObject self, final EClass filter) {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(filter);

		return siblings(self, filters);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a Sequence containing the set of object's siblings of any given types.",
		params = {
			@Param(name = "self", value = "The EObject we seek the siblings of"),
			@Param(name = "filters", value = "The set of filtering EClasses")
		},
		result = "Sequence containing the set of the receiver's siblings of any given types",
		examples = {
			@Example(expression = "eClass3.siblings({ecore::EPackage | ecore::EClass})", result = "Sequence{eClass1, eClass2, eClass1, eClass2}")
		}
	)
	// @formatter:on
	public List<EObject> siblings(final EObject eObject, final Set<EClass> filters) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(eObject);
		if (container instanceof EObject) {
			result = new ArrayList<EObject>();
			final EStructuralFeature containingFeature = eObject.eContainingFeature();
			final List<EStructuralFeature> features = getFeatures((EObject)container, containingFeature,
					filters);
			for (EStructuralFeature containmentFeature : features) {
				final Object value = ((EObject)container).eGet(containmentFeature);
				if (value instanceof Collection<?>) {
					for (Object child : (Collection<?>)value) {
						if (child != eObject && child instanceof EObject && eIsInstanceOf((EObject)child,
								filters)) {
							result.add((EObject)child);
						}
					}
				} else if (value != eObject && value instanceof EObject && eIsInstanceOf((EObject)value,
						filters)) {
					result.add((EObject)value);
				}
			}
		} else if (container instanceof Resource) {
			result = new ArrayList<EObject>();
			for (EObject eObj : getRootsExcluding((Resource)container, eObject)) {
				if (eIsInstanceOf(eObj, filters)) {
					result.add(eObj);
				}
			}
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	/**
	 * Gets {@link EStructuralFeature} for the given container and containing feature.
	 * 
	 * @param container
	 *            the {@link EObject} container
	 * @param containingFeature
	 *            the containing {@link EStructuralFeature}
	 * @param filters
	 *            the {@link EClass} filters
	 * @return {@link EStructuralFeature} for the given container and containing feature
	 */
	private List<EStructuralFeature> getFeatures(EObject container, EStructuralFeature containingFeature,
			Set<EClass> filters) {
		final List<EStructuralFeature> result = new ArrayList<EStructuralFeature>();

		final Set<EStructuralFeature> filteredFeatures = new LinkedHashSet<EStructuralFeature>();
		for (EClass eCls : filters) {
			filteredFeatures.addAll(queryEnvironment.getEPackageProvider().getContainingEStructuralFeatures(
					eCls));
		}
		final EStructuralFeature[] containmentFeatures = ((FeatureSubsetSupplier)((EObject)container).eClass()
				.getEAllStructuralFeatures()).containments();
		for (EStructuralFeature containmentFeature : containmentFeatures) {
			if (filteredFeatures.contains(containmentFeature)) {
				result.add(containmentFeature);
			}
		}

		return result;
	}

	/**
	 * Obtains the container of an object for the purpose of accessing its siblings. This is often the
	 * {@link EObject#eContainer() eContainer}, but for top-level objects it may be the
	 * {@link EObject#eResource() eResource}.
	 * 
	 * @param object
	 *            an object
	 * @return its logical container
	 */
	private Object getContainer(EObject object) {
		Object result = object.eContainer();

		if (result == null && object instanceof InternalEObject) {
			// maybe it's a resource root
			result = ((InternalEObject)object).eDirectResource();
		}

		return result;
	}

	/**
	 * Gets the contents of the given {@link #getContainer(EObject) container} except the given
	 * {@link EObject} to exclude.
	 * 
	 * @param container
	 *            a container of objects
	 * @param toExclude
	 *            the {@link EObject} to exclude
	 * @return the contents of the given {@link #getContainer(EObject) container} except the given
	 *         {@link EObject} to exclude
	 */
	private List<EObject> getContentsExcluding(Object container, EObject toExclude) {
		final List<EObject> contents;

		if (container instanceof EObject) {
			contents = getContentsExcluding((EObject)container, toExclude);
		} else if (container instanceof Resource) {
			contents = getRootsExcluding((Resource)container, toExclude);
		} else {
			contents = Collections.emptyList();
		}

		return contents;
	}

	/**
	 * Gets the contents of the given {@link #getContainer(EObject) container} until the given {@link EObject}
	 * excluded.
	 * 
	 * @param container
	 *            a container of objects
	 * @param until
	 *            the {@link EObject} to exclude
	 * @return the contents of the given {@link #getContainer(EObject) container} until the given
	 *         {@link EObject} excluded
	 */
	private List<EObject> getContentsUntil(Object container, EObject until) {
		final List<EObject> contents;

		if (container instanceof EObject) {
			contents = getContentsUntil((EObject)container, until);
		} else if (container instanceof Resource) {
			contents = getRootsUntil((Resource)container, until);
		} else {
			contents = Collections.emptyList();
		}

		return contents;
	}

	/**
	 * Gets the contents of the given {@link #getContainer(EObject) container} from the given {@link EObject}
	 * excluded to the end.
	 * 
	 * @param container
	 *            a container of objects
	 * @param from
	 *            the {@link EObject} to start from excluded
	 * @return the contents of the given {@link #getContainer(EObject) container} from the given
	 *         {@link EObject} excluded to the end
	 */
	private List<EObject> getContentsFrom(Object container, EObject from) {
		final List<EObject> contents;

		if (container instanceof EObject) {
			contents = getContentsFrom((EObject)container, from);
		} else if (container instanceof Resource) {
			contents = getRootsFrom((Resource)container, from);
		} else {
			contents = Collections.emptyList();
		}

		return contents;
	}

	/**
	 * Elements held by a reference with containment=true and derived=true are not returned by
	 * {@link EObject#eContents()}. This allows us to return the list of all contents from an EObject
	 * <b>including</b> those references.
	 * 
	 * @param container
	 *            the containing {@link EObject}
	 * @param toExcluse
	 *            the {@link EObject} to exclude
	 * @return The list of all the content of a given EObject, derived containment references included
	 */
	private List<EObject> getContentsExcluding(EObject container, EObject toExcluse) {
		// TODO lazy collection
		final List<EObject> result = new ArrayList<EObject>();

		final EStructuralFeature[] containmentFeatures = ((FeatureSubsetSupplier)container.eClass()
				.getEAllStructuralFeatures()).containments();
		for (final EStructuralFeature feature : containmentFeatures) {
			final Object value = container.eGet(feature);
			if (value instanceof Collection<?>) {
				for (Object child : (Collection<?>)value) {
					if (child != toExcluse && child instanceof EObject) {
						result.add((EObject)child);
					}
				}
			} else if (value != toExcluse && value instanceof EObject) {
				result.add((EObject)value);
			}
		}

		return result;
	}

	/**
	 * Gets the content of the given {@link EObject} until the given contained {@link EObject}.
	 * 
	 * @param container
	 *            the {@link EObject} we seek the content of
	 * @param until
	 *            the {@link EObject} to exclude
	 * @return the content of the given {@link EObject} until the given contained {@link EObject}
	 */
	private List<EObject> getContentsUntil(EObject container, EObject until) {
		// TODO lazy collection
		final List<EObject> result = new ArrayList<EObject>();

		final EStructuralFeature[] containmentFeatures = ((FeatureSubsetSupplier)container.eClass()
				.getEAllStructuralFeatures()).containments();
		finish: for (final EStructuralFeature feature : containmentFeatures) {
			final Object value = container.eGet(feature);
			if (value instanceof Collection<?>) {
				for (Object child : (Collection<?>)value) {
					if (child == until) {
						break finish;
					} else if (child instanceof EObject) {
						result.add((EObject)child);
					}
				}
			} else if (value == until) {
				break finish;
			} else if (value instanceof EObject) {
				result.add((EObject)value);
			}
		}

		return result;
	}

	/**
	 * Gets the content of the given {@link EObject} from the given contained {@link EObject} until the end.
	 * 
	 * @param container
	 *            the {@link EObject} we seek the content of
	 * @param until
	 *            the {@link EObject} start excluded
	 * @return the content of the given {@link EObject} from the given contained {@link EObject} until the end
	 */
	private List<EObject> getContentsFrom(EObject container, EObject until) {
		// TODO lazy collection
		final List<EObject> result = new ArrayList<EObject>();

		boolean collect = false;
		final EStructuralFeature[] containmentFeatures = ((FeatureSubsetSupplier)container.eClass()
				.getEAllStructuralFeatures()).containments();
		for (final EStructuralFeature feature : containmentFeatures) {
			final Object value = container.eGet(feature);
			if (value instanceof Collection<?>) {
				for (Object child : (Collection<?>)value) {
					if (child == until) {
						collect = true;
					} else if (collect && child instanceof EObject) {
						result.add((EObject)child);
					}
				}
			} else if (value == until) {
				collect = true;
			} else if (collect && value instanceof EObject) {
				result.add((EObject)value);
			}
		}

		return result;
	}

	/**
	 * Like the standard {@link Resource#getContents()} method except that we retrieve only objects that do
	 * not have containers (i.e., they are not cross-resource-contained). This is an important distinction
	 * because we want only peers (or "siblings") of an object that is a root (having no container), and those
	 * are defined as the other objects that are also roots.
	 * 
	 * @param resource
	 *            a resource from which to get root objects
	 * @param toExclude
	 *            the {@link EObject} to exclude
	 * @return the root objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code resource}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private List<EObject> getRootsExcluding(Resource resource, EObject toExclude) {
		// TODO use lazy collection
		final List<EObject> result = new ArrayList<EObject>();

		for (EObject eObj : resource.getContents()) {
			if (eObj != toExclude && eObj.eContainer() == null) {
				result.add(eObj);
			}
		}

		return result;
	}

	/**
	 * Gets the content root of the given resource until the given {@link EObject}.
	 * 
	 * @param resource
	 *            a resource from which to get root objects
	 * @param until
	 *            the {@link EObject} to exclude
	 * @return the content root of the given resource until the given {@link EObject}
	 */
	private List<EObject> getRootsUntil(Resource resource, EObject until) {
		// TODO use lazy collection
		final List<EObject> result = new ArrayList<EObject>();

		for (EObject eObj : resource.getContents()) {
			if (eObj == until) {
				break;
			} else if (eObj.eContainer() == null) {
				result.add(eObj);
			}
		}

		return result;
	}

	/**
	 * Gets the content root of the given resource from the given {@link EObject} until the end.
	 * 
	 * @param resource
	 *            a resource from which to get root objects
	 * @param from
	 *            the {@link EObject} to start excluded
	 * @return the content root of the given resource from the given {@link EObject} until the end
	 */
	private List<EObject> getRootsFrom(Resource resource, EObject from) {
		// TODO use lazy collection
		final List<EObject> result = new ArrayList<EObject>();

		boolean collect = false;
		for (EObject eObj : resource.getContents()) {
			if (eObj == from) {
				collect = true;
			} else if (collect && eObj.eContainer() == null) {
				result.add(eObj);
			}
		}

		return result;
	}

	/**
	 * Add the IType instance corresponding to ecore::EObject to the set using the given
	 * {@link IReadOnlyQueryEnvironment} to resolve it.
	 * 
	 * @param queryEnvironment
	 *            types the Set to update.
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return the {@link EObject} {@link EClassifier} for the given {@link IReadOnlyQueryEnvironment} if any,
	 *         <code>null</code> otherwise
	 */
	private static void addEObjectEClass(Set<IType> types, IReadOnlyQueryEnvironment queryEnvironment) {
		for (EClassifier classifier : queryEnvironment.getEPackageProvider().getTypes("ecore", "EObject")) {
			types.add(new EClassifierType(queryEnvironment, classifier));
		}
	}

	/**
	 * Tells if the given {@link EObject} is an instance of one of the given {@link EClass}.
	 * 
	 * @param value
	 *            the {@link EObject} to check
	 * @param types
	 *            the {@link Set} of {@link EClass}
	 * @return <code>true</code> if the given {@link EObject} is an instance of one of the given
	 *         {@link EClass}, <code>false</code> otherwise
	 */
	private boolean eIsInstanceOf(EObject value, Set<EClass> types) {
		for (EClass type : types) {
			if (type.isSuperTypeOf(((EObject)value).eClass()) || type.isInstance(value)) {
				return true;
			}
		}
		return false;
	}

}
