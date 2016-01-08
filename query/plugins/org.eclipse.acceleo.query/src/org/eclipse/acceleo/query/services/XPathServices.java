/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
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
		private AncestorsService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierLiteralType) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierSetLiteralType) {
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
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes, final EClass receiverEClass) {
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
				final Set<IType> filterTypes = Sets.newLinkedHashSet();
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
						final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
								containingEClass), filterType);
						if (lowerType != null) {
							result.add(new SequenceType(queryEnvironment, lowerType));
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
		 */
		private FollowingSiblingsService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierLiteralType) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierSetLiteralType) {
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
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes, final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass followingEClass : queryEnvironment.getEPackageProvider()
						.getFollowingSiblingsEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							followingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't have following siblings", argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = Sets.newLinkedHashSet();
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
						final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
								followingEClass), filterType);
						if (lowerType != null) {
							result.add(new SequenceType(queryEnvironment, lowerType));
						}
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't be a following sibling of %s", argTypes.get(1), argTypes.get(0))));
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
		 */
		private PrecedingSiblingsService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierLiteralType) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierSetLiteralType) {
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
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes, final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass precedingEClass : queryEnvironment.getEPackageProvider()
						.getPrecedingSiblingsEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							precedingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't have preceding siblings", argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = Sets.newLinkedHashSet();
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
						final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
								precedingEClass), filterType);
						if (lowerType != null) {
							result.add(new SequenceType(queryEnvironment, lowerType));
						}
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't be a preceding sibling of %s", argTypes.get(1), argTypes.get(0))));
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
		 */
		private SiblingsService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierLiteralType) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierSetLiteralType) {
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
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes, final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.size() == 1) {
				for (EClass siblingEClass : queryEnvironment.getEPackageProvider().getSiblingsEClasses(
						receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							siblingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing("%s can't have siblings",
							argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final Set<IType> filterTypes = Sets.newLinkedHashSet();
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
						final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
								siblingEClass), filterType);
						if (lowerType != null) {
							result.add(new SequenceType(queryEnvironment, lowerType));
						}
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't be a sibling of %s", argTypes.get(1), argTypes.get(0))));
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider#getService(java.lang.reflect.Method)
	 */
	@Override
	protected IService getService(Method publicMethod) {
		final IService result;
		if ("ancestors".equals(publicMethod.getName())) {
			result = new AncestorsService(publicMethod, this);
		} else if ("followingSiblings".equals(publicMethod.getName())) {
			result = new FollowingSiblingsService(publicMethod, this);
		} else if ("precedingSiblings".equals(publicMethod.getName())) {
			result = new PrecedingSiblingsService(publicMethod, this);
		} else if ("siblings".equals(publicMethod.getName())) {
			result = new SiblingsService(publicMethod, this);
		} else {
			result = new JavaMethodService(publicMethod, this);
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
		// TODO lazy collection
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
		filters.add(null);

		return ancestors(self, filters);
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
	public List<EObject> ancestors(EObject self, EClassifier filter) {
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
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
	public List<EObject> ancestors(EObject self, Set<EClassifier> filters) {
		// TODO lazy collection + predicate
		final List<EObject> result = new ArrayList<EObject>();

		EObject container = self.eContainer();
		while (container != null) {
			for (EClassifier filter : filters) {
				if (filter == null || filter.isInstance(container)) {
					result.add(container);
					break;
				}
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
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
		filters.add(null);

		return siblings(self, filters, false);
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
	public List<EObject> followingSiblings(EObject self, EClassifier filter) {
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
		filters.add(filter);

		return siblings(self, filters, false);
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
	public List<EObject> followingSiblings(EObject self, Set<EClassifier> filters) {
		return siblings(self, filters, false);
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
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
		filters.add(null);

		return siblings(self, filters, true);
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
	public List<EObject> precedingSiblings(EObject self, EClassifier filter) {
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
		filters.add(filter);

		return siblings(self, filters, true);
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
	public List<EObject> precedingSiblings(EObject self, Set<EClassifier> filters) {
		return siblings(self, filters, true);
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
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
		filters.add(null);

		return siblings(self, filters);
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
	public List<EObject> siblings(final EObject self, final EClassifier filter) {
		final Set<EClassifier> filters = Sets.newLinkedHashSet();
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
	public List<EObject> siblings(final EObject eObject, final Set<EClassifier> filters) {
		// TODO lazy collection
		final List<EObject> result;

		Object container = getContainer(eObject);
		if (container != null) {
			result = Lists.newArrayList();
			for (EObject input : getContents(container)) {
				for (EClassifier filter : filters) {
					if (input != eObject
							&& (filter == null || filter.isInstance(input.eClass()) || filter.equals(input
									.eClass()))) {
						result.add(input);
						break;
					}
				}
			}
		} else {
			result = Collections.<EObject> emptyList();
		}

		return result;
	}

	/**
	 * Returns a Sequence containing either all preceding siblings of <code>source</code>, or all of its
	 * following siblings.
	 * 
	 * @param eObject
	 *            The EObject we seek the siblings of.
	 * @param filters
	 *            {@link Set} of types of the EObjects we seek to retrieve.
	 * @param preceding
	 *            If <code>true</code>, we'll return the preceding siblings of <em>source</em>. Otherwise,
	 *            this will return its followingSiblings.
	 * @return Sequence containing the sought set of the receiver's siblings.
	 */
	private List<EObject> siblings(final EObject eObject, final Set<EClassifier> filters, Boolean preceding) {
		// TODO lazy collection
		final List<EObject> result;

		final Object container = getContainer(eObject);
		if (container != null) {
			final List<EObject> siblings = getContents(container);
			int startIndex = 0;
			int endIndex = siblings.size();

			if (preceding) {
				endIndex = siblings.indexOf(eObject);
			} else {
				startIndex = siblings.indexOf(eObject) + 1;
			}

			result = Lists.newArrayList();
			for (EObject input : siblings.subList(startIndex, endIndex)) {
				for (EClassifier filter : filters) {
					if (filter == null || filter.isInstance(input.eClass()) || filter.equals(input.eClass())) {
						result.add(input);
						break;
					}
				}
			}
		} else {
			result = Collections.<EObject> emptyList();
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
	 * Obtains the contents of a container, as determined by {@link #getContainer(EObject)}.
	 * 
	 * @param container
	 *            a container of objects
	 * @return the contained objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code container}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private List<EObject> getContents(Object container) {
		final List<EObject> contents;

		if (container instanceof EObject) {
			contents = getContents((EObject)container);
		} else if (container instanceof Resource) {
			contents = getRoots((Resource)container);
		} else {
			contents = Collections.<EObject> emptyList();
		}

		return contents;
	}

	/**
	 * Elements held by a reference with containment=true and derived=true are not returned by
	 * {@link EObject#eContents()}. This allows us to return the list of all contents from an EObject
	 * <b>including</b> those references.
	 * 
	 * @param eObject
	 *            The EObject we seek the content of.
	 * @return The list of all the content of a given EObject, derived containment references included.
	 */
	private List<EObject> getContents(EObject eObject) {
		final List<EObject> result = new ArrayList<EObject>(eObject.eContents());
		for (final EReference reference : eObject.eClass().getEAllReferences()) {
			if (reference.isContainment() && reference.isDerived()) {
				final Object value = eObject.eGet(reference);
				if (value instanceof Collection<?>) {
					for (Object newValue : (Collection<?>)value) {
						if (!result.contains(newValue) && newValue instanceof EObject) {
							result.add((EObject)newValue);
						}
					}
				} else if (!result.contains(value) && value instanceof EObject) {
					result.add((EObject)value);
				}
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
	 * @return the root objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code resource}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private List<EObject> getRoots(Resource resource) {
		// optimize for the vast majority of cases in which none of the objects are cross-resource-contained
		// (i.e., they are all roots)
		List<EObject> result = resource.getContents();

		ListIterator<EObject> iter = result.listIterator();
		while (iter.hasNext()) {
			if (iter.next().eContainer() != null) {
				// don't include this in the result

				// need to copy the result so that we don't modify the resource
				int where = iter.previousIndex();
				List<EObject> newResult = new ArrayList<EObject>(result.size() - 1);
				newResult.addAll(result.subList(0, where));
				result = newResult;

				// continue adding roots
				while (iter.hasNext()) {
					EObject next = iter.next();
					if (next.eContainer() == null) {
						result.add(next);
					}
				}
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

}
