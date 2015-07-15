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
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IEPackageProvider2;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

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
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
				List<IType> argTypes) {
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

		@Override
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
				List<IType> argTypes) {
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
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
				List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
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

			final Set<IType> containedTypes = new LinkedHashSet<IType>();
			for (EClass contained : queryEnvironment.getEPackageProvider().getContainedEClasses(
					receiverEClass)) {
				containedTypes.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
						contained)));
			}
			if (argTypes.size() == 1) {
				result.addAll(containedTypes);
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s doesn't contain any other EClass", argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass containedEClass : queryEnvironment.getEPackageProvider().getContainedEClasses(
						receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containedEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't contain %s direclty", argTypes.get(0), filterType)));
				}
			}

			return result;
		}

	}

	/**
	 * EAllContents {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class EAllContentsService extends FilterService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private EAllContentsService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
				List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
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

			final Set<IType> containedTypes = new LinkedHashSet<IType>();
			for (EClass contained : queryEnvironment.getEPackageProvider().getAllContainedEClasses(
					receiverEClass)) {
				containedTypes.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
						contained)));
			}
			if (argTypes.size() == 1) {
				result.addAll(containedTypes);
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s doesn't contain any other EClass", argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass containedEClass : queryEnvironment.getEPackageProvider().getAllContainedEClasses(
						receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containedEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't contain %s direclty or indirectly", argTypes.get(0), filterType)));
				}
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

		@Override
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
				List<IType> argTypes) {
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
	 * A cross referencer needed to realize the service eInverse().
	 */
	private CrossReferenceProvider crossReferencer;

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
	public EObjectServices(IReadOnlyQueryEnvironment queryEnvironment) {
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

		if ("eContents".equals(publicMethod.getName())) {
			result = new EContentsService(publicMethod, this);
		} else if ("eAllContents".equals(publicMethod.getName())) {
			result = new EAllContentsService(publicMethod, this);
		} else if ("eContainer".equals(publicMethod.getName())) {
			result = new EContainerService(publicMethod, this);
		} else if ("eContainerOrSelf".equals(publicMethod.getName())) {
			result = new EContainerOrSelfService(publicMethod, this);
		} else if ("eInverse".equals(publicMethod.getName())) {
			result = new EInverseService(publicMethod, this);
		} else {
			result = new Service(publicMethod, this);
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
		final List<EObject> result = Lists.newArrayList();

		final Iterator<EObject> it = eObject.eAllContents();
		while (it.hasNext()) {
			result.add(it.next());
		}

		return result;
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
		final Set<EStructuralFeature> features = ((IEPackageProvider2)queryEnvironment.getEPackageProvider())
				.getAllContainingEStructuralFeatures(type);

		return eAllContents(eObject, type, features);
	}

	/**
	 * Returns a list of the {@link EObject} recursively contained in the specified root eObject and that are
	 * instances of the specified {@link EClass} or one of its {@link EClass#getEAllSuperTypes() super types}.
	 * 
	 * @param eObject
	 *            the root {@link EObject} to visit.
	 * @param type
	 *            the filtering {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the recursive filtered content of the given {@link EObject}
	 */
	private List<EObject> eAllContents(EObject eObject, EClass type, Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		if (!features.isEmpty()) {
			for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
				if (features.contains(feature)) {
					final Object child = eObject.eGet(feature);
					if (child != null) {
						result.addAll(eAllContentsVisitChild(child, type, feature.isMany(), features));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Visits a given child for {@link EObjectServices#eAllContents(EObject, EClass, Set)}.
	 * 
	 * @param child
	 *            the child to visit
	 * @param type
	 *            the filtering {@link EClass}
	 * @param isMany
	 *            <code>true</code> if the containing {@link EStructuralFeature}
	 *            {@link EStructuralFeature#isMany() is many}, <code>false</code> otherwise
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the recursive filtered content of the given child and the child itself if it match the type
	 */
	private List<EObject> eAllContentsVisitChild(Object child, EClass type, boolean isMany,
			Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		if (isMany) {
			if (child instanceof Collection<?>) {
				result.addAll(eAllContentsVisitCollectionChild((Collection<?>)child, type, features));
			} else {
				throw new IllegalStateException("don't know what to do with " + child.getClass());
			}
		} else if (child instanceof EObject) {
			if (type.isSuperTypeOf(((EObject)child).eClass())) {
				result.add((EObject)child);
			}
			result.addAll(eAllContents((EObject)child, type, features));
		}

		return result;
	}

	/**
	 * Visits a given {@link Collection} child for {@link EObjectServices#eAllContents(EObject, EClass, Set)}.
	 * 
	 * @param child
	 *            the child to visit
	 * @param type
	 *            the filtering {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the recursive filtered content of the given child and the child itself if it match the type
	 */
	private List<EObject> eAllContentsVisitCollectionChild(Collection<?> child, final EClass type,
			Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		for (Object object : (Collection<?>)child) {
			if (object instanceof EObject) {
				if (type.isSuperTypeOf(((EObject)object).eClass())) {
					result.add((EObject)object);
				}
				result.addAll(eAllContents((EObject)object, type, features));
			}
		}

		return result;
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
		return eObject.eContents();
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
		final Set<EStructuralFeature> features = ((IEPackageProvider2)queryEnvironment.getEPackageProvider())
				.getContainingEStructuralFeatures(type);

		return eContents(eObject, type, features);
	}

	/**
	 * Returns a list of the {@link EObject} directly contained in the specified root eObject and that are
	 * instances of the specified {@link EClass} or one of its {@link EClass#getEAllSuperTypes() super types}.
	 * 
	 * @param eObject
	 *            the root {@link EObject} to visit.
	 * @param type
	 *            the filtering {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the recursive filtered content of the given {@link EObject}
	 */
	private List<EObject> eContents(EObject eObject, EClass type, Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		if (!features.isEmpty()) {
			for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
				if (features.contains(feature)) {
					final Object child = eObject.eGet(feature);
					if (child != null) {
						result.addAll(eContentsVisitChild(child, type, feature.isMany(), features));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Visits a given child for {@link EObjectServices#eContents(EObject, EClass, Set)}.
	 * 
	 * @param child
	 *            the child to visit
	 * @param type
	 *            the filtering {@link EClass}
	 * @param isMany
	 *            <code>true</code> if the containing {@link EStructuralFeature}
	 *            {@link EStructuralFeature#isMany() is many}, <code>false</code> otherwise
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the child itself if it match the type
	 */
	private Collection<? extends EObject> eContentsVisitChild(Object child, EClass type, boolean isMany,
			Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		if (isMany) {
			if (child instanceof Collection<?>) {
				result.addAll(eContentsVisitCollectionChild((Collection<?>)child, type, features));
			} else {
				throw new IllegalStateException("don't know what to do with " + child.getClass());
			}
		} else if (child instanceof EObject) {
			if (type.isSuperTypeOf(((EObject)child).eClass())) {
				result.add((EObject)child);
			}
		}

		return result;
	}

	/**
	 * Visits a given {@link Collection} child for {@link EObjectServices#eContents(EObject, EClass, Set)}.
	 * 
	 * @param child
	 *            the child to visit
	 * @param type
	 *            the filtering {@link EClass}
	 * @param features
	 *            the set of {@link EStructuralFeature} to navigate
	 * @return the child itself if it match the type
	 */
	private List<EObject> eContentsVisitCollectionChild(Collection<?> child, final EClass type,
			Set<EStructuralFeature> features) {
		final List<EObject> result = Lists.newArrayList();

		for (Object object : (Collection<?>)child) {
			if (object instanceof EObject) {
				if (type.isSuperTypeOf(((EObject)object).eClass())) {
					result.add((EObject)object);
				}
			}
		}

		return result;
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
		while (current != null && !type.isSuperTypeOf(current.eClass())) {
			current = current.eContainer();
		}
		if (current != null && type.isSuperTypeOf(current.eClass())) {
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

		if (type.isSuperTypeOf(eObject.eClass())) {
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

	public void setCrossReferencer(CrossReferenceProvider crossReferencer) {
		this.crossReferencer = crossReferencer;
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
	public Object eGet(EObject eObject, String featureName) {
		for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			if (feature.getName().equals(featureName)) {
				return eObject.eGet(feature);
			}
		}

		return null;
	}

}
