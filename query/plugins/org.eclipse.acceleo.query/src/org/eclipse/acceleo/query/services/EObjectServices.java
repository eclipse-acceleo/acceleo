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

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IRootEObjectProvider;
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
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;

//@formatter:off
@ServiceProvider(
	value = "Services available for EObjects"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class EObjectServices extends AbstractServiceProvider {

	/**
	 * {@link EClass} containment message.
	 */
	private static final String ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S = "Only EClass can be contained into other EClasses not %s";

	/**
	 * Can't contain directly or indirectly message.
	 */
	private static final String S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S = "%s can't contain directly or indirectly %s";

	/**
	 * Filtered eAllContents {@link Iterator}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class FilteredContentIterator extends AbstractTreeIterator<EObject> {

		/**
		 * Generated serial version UID.
		 */
		private static final long serialVersionUID = -1537663884310088034L;

		/**
		 * The {@link Set} of {@link EStructuralFeature} to navigate.
		 */
		private final Set<EStructuralFeature> features;

		/**
		 * Constructor.
		 * 
		 * @param object
		 *            the root object
		 * @param includeRoot
		 *            <code>true</code> to include root, <code>false</code> otherwise
		 * @param features
		 *            the {@link Set} of {@link EStructuralFeature} to navigate
		 */
		private FilteredContentIterator(Object object, boolean includeRoot, Set<EStructuralFeature> features) {
			super(object, includeRoot);
			this.features = features;
		}

		@Override
		public Iterator<EObject> getChildren(Object object) {
			final List<EObject> result = Lists.newArrayList();
			if (object instanceof EObject) {
				EObject host = (EObject)object;
				EStructuralFeature[] eStructuralFeatures = ((EClassImpl.FeatureSubsetSupplier)host.eClass()
						.getEAllStructuralFeatures()).containments();
				if (eStructuralFeatures != null) {
					for (EStructuralFeature feat : eStructuralFeatures) {
						if (features.contains(feat)) {
							addChildren(result, host, feat);
						}
					}
				}

			}
			return result.iterator();
		}

		/**
		 * Add the children of the given {@link EObject} and the given {@link EStructuralFeature}.
		 * 
		 * @param result
		 *            the
		 * @param eObject
		 * @param feature
		 */
		private void addChildren(final List<EObject> result, EObject eObject, EStructuralFeature feature) {
			Object value = eObject.eGet(feature);
			if (feature.isMany()) {
				if (value instanceof Collection<?>) {
					for (EObject childElement : Iterables.filter((Collection<?>)value, EObject.class)) {
						result.add(childElement);
					}
				} else {
					throw new IllegalStateException("don't know what to do with " + value.getClass());
				}
			} else if (value instanceof EObject) {
				result.add((EObject)value);

			}
		}
	}

	/**
	 * EContainer {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class EContainerService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private EContainerService(Method serviceMethod, Object serviceInstance) {
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
						result.add(argTypes.get(0));
					} else if (argTypes.size() == 2) {
						result.add(new EClassifierType(queryEnvironment, ((EClassifierLiteralType)argTypes
								.get(1)).getType()));
					}
				} else {
					result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
				}
			} else {
				result.add(services.nothing(ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S,
						argTypes.get(0)));
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
				for (EClass containingEClass : queryEnvironment.getEPackageProvider().getContainingEClasses(
						receiverEClass)) {
					result.add(new EClassifierType(queryEnvironment, containingEClass));
				}
				if (result.isEmpty()) {
					result.add(services.nothing("%s can't be contained", argTypes.get(0)));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getAllContainingEClasses(receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containingEClass), filterType);
					if (lowerType != null) {
						result.add(lowerType);
					}
				}
				if (result.isEmpty()) {
					result.add(services.nothing(S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S, filterType,
							argTypes.get(0)));
				}
			}

			return result;
		}

	}

	/**
	 * EContainerOrSelf {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class EContainerOrSelfService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private EContainerOrSelfService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.JavaMethodService#getType(org.eclipse.acceleo.query.ast.Call,
		 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IValidationResult,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
		 */
		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					result.add(new EClassifierType(queryEnvironment,
							((EClassifierLiteralType)argTypes.get(1)).getType()));
				} else {
					result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
				}
			} else {
				result.add(services.nothing(ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S,
						argTypes.get(0)));
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

			final IType lowerSelfType = services.lower(argTypes.get(0), argTypes.get(1));
			if (lowerSelfType != null) {
				result.add(lowerSelfType);
			}
			final IType filterType = argTypes.get(1);
			for (EClass containingEClass : queryEnvironment.getEPackageProvider().getAllContainingEClasses(
					receiverEClass)) {
				final IType lowerType = services.lower(
						new EClassifierType(queryEnvironment, containingEClass), filterType);
				if (lowerType != null) {
					result.add(lowerType);
				}
			}
			if (result.isEmpty()) {
				result.add(services.nothing(S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S, filterType, argTypes
						.get(0)));
			}

			return result;
		}

	}

	/**
	 * EContents {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class EContentsService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private EContentsService(Method serviceMethod, Object serviceInstance) {
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
						for (EClassifier eClsFilter : ((EClassifierSetLiteralType)argTypes.get(1))
								.getEClassifiers()) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, eClsFilter)));
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
						"Only EClass can contain other EClasses not %s", argTypes.get(0))));
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
				final Set<IType> containedTypes = new LinkedHashSet<IType>();
				for (EClass contained : queryEnvironment.getEPackageProvider().getContainedEClasses(
						receiverEClass)) {
					containedTypes.add(new SequenceType(queryEnvironment, new EClassifierType(
							queryEnvironment, contained)));
				}
				result.addAll(containedTypes);
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s doesn't contain any other EClass", argTypes.get(0))));
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
					final Collection<EClassifier> eObjectEClasses = queryEnvironment.getEPackageProvider()
							.getTypes("ecore", "EObject");
					for (EClassifier eObjectEClass : eObjectEClasses) {
						filterTypes.add(new EClassifierType(queryEnvironment, eObjectEClass));
					}
				}
				for (IType filterType : filterTypes) {
					for (EClass containedEClass : queryEnvironment.getEPackageProvider()
							.getContainedEClasses(receiverEClass)) {
						final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
								containedEClass), filterType);
						if (lowerType != null) {
							result.add(new SequenceType(queryEnvironment, lowerType));
						}
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't contain %s direclty", argTypes.get(0), argTypes.get(1))));
				}
			}

			return result;
		}

	}

	/**
	 * AllInstances {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class AllInstancesService extends EAllContentsService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private AllInstancesService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.services.EAllContentsService#getType(org.eclipse.acceleo.query.ast.Call,
		 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IValidationResult,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
		 */
		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnv, List<IType> argTypes) {
			final Set<IType> result;

			if (rootProvider == null) {
				result = Sets.newLinkedHashSet();
				result.add(new SequenceType(queryEnv, services.nothing("No IRootEObjectProvider registered")));
			} else {
				List<IType> newArgTypes = Lists.newArrayList(argTypes);
				final Collection<EClassifier> eObjectEClasses = queryEnv.getEPackageProvider().getTypes(
						"ecore", "EObject");
				for (EClassifier eObjectEClass : eObjectEClasses) {
					newArgTypes.add(0, new EClassifierType(queryEnv, eObjectEClass));
				}

				result = super.getType(call, services, validationResult, queryEnv, newArgTypes);
			}

			return result;
		}
	}

	/**
	 * EInverse {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class EInverseService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private EInverseService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 * @param filterIndex
		 *            the index of the filtering {@link IType}
		 * @since 4.0.0
		 */
		private EInverseService(Method serviceMethod, Object serviceInstance, int filterIndex) {
			super(serviceMethod, serviceInstance, filterIndex);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1 || !(argTypes.get(1).getType() instanceof EClass)) {
						result.add(new SetType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2) {
						result.add(new SetType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
					}
				} else {
					result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
				}
			} else {
				result.add(new SetType(queryEnvironment, services.nothing(
						"Only EClass can have inverse not %s", argTypes.get(0))));
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

			final Set<EClass> inverseEClasses = queryEnvironment.getEPackageProvider().getInverseEClasses(
					receiverEClass);
			if (argTypes.size() == 1 || !(argTypes.get(1).getType() instanceof EClass)) {
				for (EClass inverseEClass : inverseEClasses) {
					result.add(new SetType(queryEnvironment, new EClassifierType(queryEnvironment,
							inverseEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SetType(queryEnvironment, services.nothing("%s don't have inverse",
							argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass inverseEClass : inverseEClasses) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							inverseEClass), filterType);
					if (lowerType != null) {
						result.add(new SetType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SetType(queryEnvironment, services.nothing("%s don't have inverse to %s",
							argTypes.get(0), filterType)));
				}
			}

			return result;
		}
	}

	/**
	 * The cross referencer needed to realize the service eInverse().
	 */
	private final CrossReferenceProvider crossReferencer;

	/**
	 * The root provider needed to realize the service allInstances().
	 */
	private final IRootEObjectProvider rootProvider;

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param crossReferencer
	 *            the cross referencer needed to realize the service eInverse()
	 * @param rootProvider
	 *            the root provider needed to realize the service allInstances()
	 */
	public EObjectServices(IReadOnlyQueryEnvironment queryEnvironment,
			CrossReferenceProvider crossReferencer, IRootEObjectProvider rootProvider) {
		this.queryEnvironment = queryEnvironment;
		this.crossReferencer = crossReferencer;
		this.rootProvider = rootProvider;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider#getService(java.lang.reflect.Method)
	 */
	@Override
	protected IService getService(Method publicMethod) {
		final IService result;

		if ("eContents".equals(publicMethod.getName())) {
			result = new EContentsService(publicMethod, this);
		} else if ("eAllContents".equals(publicMethod.getName())) {
			result = new EAllContentsService(publicMethod, this);
		} else if ("eContainer".equals(publicMethod.getName())) {
			result = new EContainerService(publicMethod, this);
		} else if ("eContainerOrSelf".equals(publicMethod.getName())) {
			result = new EContainerOrSelfService(publicMethod, this);
		} else if ("eInverse".equals(publicMethod.getName())) {
			if (publicMethod.getParameterTypes().length == 2
					&& publicMethod.getParameterTypes()[1] == String.class) {
				// no filter for eInverse(EObject, String)
				result = new EInverseService(publicMethod, this, 10);
			} else {
				result = new EInverseService(publicMethod, this);
			}
		} else if ("allInstances".equals(publicMethod.getName())) {
			result = new AllInstancesService(publicMethod, this);
		} else {
			result = new JavaMethodService(publicMethod, this);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence of the EObjects recursively contained in the specified root eObject.",
		params = {
			@Param(name = "eObject", value = "The root of the content tree")
		},
		result = "The recursive content of the specified eObject.",
		examples = {
			@Example(
				expression = "anEPackage.eAllContents()",
				result = "Sequence{firstEClass, firstEAttribute, secondEClass, firstDataType}"
			)
		}
	)
	// @formatter:on
	public List<EObject> eAllContents(EObject eObject) {
		return Lists.newArrayList(eObject.eAllContents());
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence of the EObjects recursively contained in the specified root eObject and that are " +
				"instances of the specified EClass",
		params = {
			@Param(name = "eObject", value = "The root of the content tree"),
			@Param(name = "type", value = "The type used to select elements")
		},
		result = "The recursive content of the specified eObject.",
		examples = {
			@Example(
				expression = "anEPackage.eAllContents(ecore::EClass)",
				result = "Sequence{firstEClass, secondEClass}"
			)
		}
	)
	// @formatter:on
	public List<EObject> eAllContents(EObject eObject, final EClass type) {
		if (type == EcorePackage.eINSTANCE.getEObject()) {
			return eAllContents(eObject);
		}
		final Set<EClass> types = Sets.newLinkedHashSet();
		types.add(type);

		return eAllContents(eObject, types);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence of the EObjects recursively contained in the specified root eObject and that are " +
				"instances of the specified EClass",
		params = {
			@Param(name = "eObject", value = "The root of the content tree"),
			@Param(name = "types", value = "The set of types used to select elements")
		},
		result = "The recursive content of the specified eObject.",
		examples = {
			@Example(
				expression = "anEPackage.eAllContents({ecore::EPackage | ecore::EClass})",
				result = "Sequence{ePackage, eClass, ...}"
			)
		}
	)
	// @formatter:on
	public List<EObject> eAllContents(EObject eObject, final Set<EClass> types) {
		final List<EObject> result;

		if (types != null) {
			final Set<EStructuralFeature> features = Sets.newLinkedHashSet();
			for (EClass type : types) {
				features.addAll(queryEnvironment.getEPackageProvider().getAllContainingEStructuralFeatures(
						type));
			}
			result = eAllContents(eObject, types, features);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	/**
	 * Returns a list of the {@link EObject} recursively contained in the specified root eObject and that are
	 * instances of the specified {@link EClass} or one of its {@link EClass#getEAllSuperTypes() super types}.
	 * 
	 * @param eObject
	 *            the root {@link EObject} to visit.
	 * @param types
	 *            the filtering {@link Set} of {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the recursive filtered content of the given {@link EObject}
	 */
	private List<EObject> eAllContents(EObject eObject, Set<EClass> types,
			final Set<EStructuralFeature> features) {
		List<EObject> allChildrens = Lists.newArrayList();
		if (eObject == null) {
			throw new NullPointerException();
		}
		if (!features.isEmpty()) {

			final AbstractTreeIterator<EObject> treeIterator = new FilteredContentIterator(eObject, false,
					features);
			while (treeIterator.hasNext()) {
				EObject child = treeIterator.next();
				if (eIsInstanceOf(child, types)) {
					allChildrens.add(child);
				}
			}

		}

		return allChildrens;

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

	// @formatter:off
	@Documentation(
		value = "Returns the contents of the specified EObject instance.",
		params = {
			@Param(name = "eObject", value = "The eObject which content is requested.")
		},
		result = "The content of the specified eObject.",
		examples = {
			@Example(
				expression = "anEPackage.eContents()",
				result = "Sequence{firstEClass, secondEClass, firstDataType}"
			)
		}
	)
	// @formatter:on
	public List<EObject> eContents(EObject eObject) {
		return Lists.newArrayList(eObject.eContents());
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence made of the instances of the specified type in the contents of the specified eObject.",
		params = {
			@Param(name = "eObject", value = "The eObject which content is requested."),
			@Param(name = "type", value = "The type filter.")
		},
		result = "The filtered content of the specified eObject.",
		examples = {
			@Example(
				expression = "anEPackage.eContents(ecore::EDataType)",
				result = "Sequence{firstDataType}"
			)
		}
	)
	// @formatter:on
	public List<EObject> eContents(EObject eObject, final EClass type) {
		if (type == EcorePackage.eINSTANCE.getEObject()) {
			return eContents(eObject);
		}
		final Set<EClass> eClasses = new LinkedHashSet<EClass>();
		eClasses.add(type);

		return eContents(eObject, eClasses);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence made of the instances of the specified types in the contents of the specified eObject.",
		params = {
			@Param(name = "eObject", value = "The eObject which content is requested."),
			@Param(name = "types", value = "The Set of types filter.")
		},
		result = "The filtered content of the specified eObject.",
		examples = {
			@Example(
				expression = "anEPackage.eContents({ecore::EPackage | ecore::EClass})",
				result = "Sequence{SubEPackage, eClass, ... }"
			)
		}
	)
	// @formatter:on
	public List<EObject> eContents(EObject eObject, final Set<EClass> types) {
		final List<EObject> result;

		if (types != null) {
			final Set<EStructuralFeature> features = Sets.newLinkedHashSet();
			for (EClass type : types) {
				features.addAll(queryEnvironment.getEPackageProvider().getContainingEStructuralFeatures(type));
			}
			result = eContents(eObject, types, features);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	/**
	 * Returns a list of the {@link EObject} directly contained in the specified root eObject and that are
	 * instances of the specified {@link EClass} or one of its {@link EClass#getEAllSuperTypes() super types}.
	 * 
	 * @param eObject
	 *            the root {@link EObject} to visit.
	 * @param types
	 *            the filtering {@link Set} of {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the filtered content of the given {@link EObject}
	 */
	private List<EObject> eContents(EObject eObject, Set<EClass> types, Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		if (!features.isEmpty()) {
			final EStructuralFeature[] containmentFeatures = ((EClassImpl.FeatureSubsetSupplier)eObject
					.eClass().getEAllStructuralFeatures()).containments();
			for (EStructuralFeature feature : containmentFeatures) {
				if (features.contains(feature)) {
					final Object child = eObject.eGet(feature);
					if (child != null) {
						eContentsVisitChild(result, child, types, feature.isMany(), features);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Visits a given child for {@link EObjectServices#eContents(EObject, Set, Set)}.
	 * 
	 * @param result
	 *            the resulting {@link List} to populate
	 * @param child
	 *            the child to visit
	 * @param types
	 *            the filtering {@link Set} of {@link EClass}
	 * @param isMany
	 *            <code>true</code> if the containing {@link EStructuralFeature}
	 *            {@link EStructuralFeature#isMany() is many}, <code>false</code> otherwise
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 */
	private void eContentsVisitChild(List<EObject> result, Object child, Set<EClass> types, boolean isMany,
			Set<EStructuralFeature> features) {
		if (isMany) {
			if (child instanceof Collection<?>) {
				eContentsVisitCollectionChild(result, (Collection<?>)child, types, features);
			} else {
				throw new IllegalStateException("don't know what to do with " + child.getClass());
			}
		} else if (child instanceof EObject) {
			if (eIsInstanceOf((EObject)child, types)) {
				result.add((EObject)child);
			}
		}
	}

	/**
	 * Visits a given {@link Collection} child for {@link EObjectServices#eContents(EObject, Set, Set)}.
	 * 
	 * @param result
	 *            the resulting {@link List} to populate
	 * @param child
	 *            the child to visit
	 * @param types
	 *            the filtering {@link Set} of {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 */
	private void eContentsVisitCollectionChild(List<EObject> result, Collection<?> child,
			final Set<EClass> types, Set<EStructuralFeature> features) {
		for (Object object : (Collection<?>)child) {
			if (object instanceof EObject) {
				if (eIsInstanceOf((EObject)object, types)) {
					result.add((EObject)object);
				}
			}
		}
	}

	// @formatter:off
	@Documentation(
		value = "Returns the container of the specified EObject",
		params = {
			@Param(name = "eObject", value = "The eObject which container is requested.")
		},
		result = "The container of the specified eObject.",
		examples = {
			@Example(
				expression = "firstEAttribute.eContainer()",
				result = "firstEClass"
			)
		}
	)
	// @formatter:on
	public EObject eContainer(EObject eObject) {
		return eObject.eContainer();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the first container of the specified EObject that matches the given type",
		params = {
			@Param(name = "eObject", value = "The eObject which container is requested."),
			@Param(name = "type", value = "The type filter.")
		},
		result = "The first container of the specified eObject that matches the given type.",
		examples = {
			@Example(
				expression = "firstEAttribute.eContainer(ecore::EPackage)",
				result = "anEPackage"
			)
		}
	)
	// @formatter:on
	public EObject eContainer(EObject eObject, EClass type) {
		final EObject result;

		EObject current = eObject.eContainer();
		while (current != null && !type.isSuperTypeOf(current.eClass()) && !type.isInstance(current)) {
			current = current.eContainer();
		}
		if (current != null && (type.isSuperTypeOf(current.eClass()) || type.isInstance(current))) {
			result = current;
		} else {
			result = null;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns self or the first container of the specified EObject that matches the given type",
		params = {
			@Param(name = "eObject", value = "The eObject which container is requested."),
			@Param(name = "type", value = "The type filter.")
		},
		result = "Self or the first container of the specified eObject that matches the given type.",
		examples = {
			@Example(
				expression = "firstEAttribute.eContainerOrSelf(ecore::EAttribute)",
				result = "firstEAttribute"
			)
		}
	)
	// @formatter:on
	public EObject eContainerOrSelf(EObject eObject, EClass type) {
		final EObject result;

		if (type.isSuperTypeOf(eObject.eClass()) || type.isInstance(eObject)) {
			result = eObject;
		} else {
			result = eContainer(eObject, type);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the EClass of the specified EObject",
		params = {
			@Param(name = "eObject", value = "The eObject which EClass is requested.")
		},
		result = "The EClass of the specified EObject"
	)
	// @formatter:on
	public EClass eClass(EObject eObject) {
		return eObject.eClass();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the containing feature of the specified EObject",
		params = {
			@Param(name = "eObject", value = "The eObject which containing feature is requested.")
		},
		result = "The containing feature of the specified EObject"
	)
	// @formatter:on
	public EStructuralFeature eContainingFeature(EObject eObject) {
		return eObject.eContainingFeature();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the containment feature of the specified EObject",
		params = {
			@Param(name = "eObject", value = "The eObject which containment feature is requested.")
		},
		result = "The containment feature of the specified EObject"
	)
	// @formatter:on
	public EReference eContainmentFeature(EObject eObject) {
		return eObject.eContainmentFeature();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the set containing the inverse references.",
		params = {
			@Param(name = "eObject", value = "The eObject which inverse references are requested.")
		},
		result = "The set of the inverse references"
	)
	// @formatter:on
	public Set<EObject> eInverse(EObject self) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				result.add(setting.getEObject());
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the elements of the given type from the set of the inverse references of the receiver.",
		params = {
			@Param(name = "eObject", value = "The eObject which inverse references are requested."),
			@Param(name = "type", value = "The type filter."),
		},
		result = "The set of the inverse references"
	)
	// @formatter:on
	public Set<EObject> eInverse(EObject self, EClassifier type) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null || type == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				if (type.isInstance(setting.getEObject())) {
					result.add(setting.getEObject());
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the elements from the set of the inverse references of the receiver that are referencing the receiver " +
				"using a feature with the given name.",
		params = {
			@Param(name = "eObject", value = "The eObject which inverse references are requested."),
			@Param(name = "featureName", value = "The feature name."),
		},
		result = "The set of the inverse references"
	)
	// @formatter:on
	public Set<EObject> eInverse(EObject self, String featureName) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				if (setting.getEStructuralFeature().getName().equals(featureName)) {
					result.add(setting.getEObject());
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Handles calls to the operation \"eGet\". This will fetch the value of the feature named " +
				"\"featureName\" on \"source\"",
		params = {
			@Param(name = "eObject", value = "The eObject we seek to retrieve a feature value of."),
			@Param(name = "featureName", value = "The name of the feature which value we need to retrieve."),
		},
		result = "The value of the given feature on the given EObject"
	)
	// @formatter:on
	public Object eGet(EObject eObject, final String featureName) {
		if (eObject == null || featureName == null) {
			throw new NullPointerException();
		}
		Optional<EStructuralFeature> feature = Iterables.tryFind(
				eObject.eClass().getEAllStructuralFeatures(), new Predicate<EStructuralFeature>() {
					@Override
					public boolean apply(EStructuralFeature input) {
						return input != null && featureName.equals(input.getName());
					}
				});

		Object result = null;
		if (feature.isPresent()) {
			result = eObject.eGet(feature.get());
		}

		if (result instanceof Set<?>) {
			result = Sets.newLinkedHashSet((Set<?>)result);
		} else if (result instanceof EMap<?, ?>) {
			result = new BasicEMap<Object, Object>(((EMap<?, ?>)result).map());
		} else if (result instanceof Collection<?>) {
			result = Lists.newArrayList((Collection<?>)result);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns all instances of the EClass",
		params = {
			@Param(name = "type", value = "The EClass"),
		},
		result = "all instances of the EClass"
	)
	// @formatter:on
	public List<EObject> allInstances(EClass type) {
		final List<EObject> result;

		if (type != null) {
			final Set<EClass> types = Sets.newLinkedHashSet();
			types.add(type);
			result = allInstances(types);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns all instances of any EClass from the OrderedSet",
		params = {
			@Param(name = "types", value = "The OrderedSet of EClass"),
		},
		result = "all instances of any EClass from the OrderedSet"
	)
	// @formatter:on
	public List<EObject> allInstances(Set<EClass> types) {
		final List<EObject> result = Lists.newArrayList();

		if (rootProvider != null) {
			for (EObject root : rootProvider.getRoots()) {
				result.addAll(eAllContents(root, types));
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the list of all EObjects cross-referenced from the receiver.",
		params = {
			@Param(name = "eObject", value = "The eObject of which we need the cross-references."),
		},
		result = "The list of all EObjects cross-referenced from the receiver."
	)
	// @formatter:on
	public Object eCrossReferences(EObject eObject) {
		return Lists.newArrayList(eObject.eCrossReferences());
	}
}
