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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Services on {@link EObject}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
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
				final IType filterType = argTypes.get(1);
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getAllContainingEClasses(receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containingEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S, filterType, argTypes.get(0))));
				}
			}

			return result;
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
		public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvoronment,
				List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (argTypes.get(0).getType() instanceof EClass) {
				final EClass eCls = (EClass)argTypes.get(0).getType();
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					result.add(new EClassifierType(queryEnvoronment,
							((EClassifierLiteralType)argTypes.get(1)).getType()));
				} else {
					result.addAll(getTypeForSpecificType(services, queryEnvoronment, argTypes, eCls));
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
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getFollowingSiblingsEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							containingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't have following siblings", argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getFollowingSiblingsEClasses(receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containingEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't be a following sibling of %s", filterType, argTypes.get(0))));
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
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getPrecedingSiblingsEClasses(receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							containingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't have preceding siblings", argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getPrecedingSiblingsEClasses(receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containingEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't be a preceding sibling of %s", filterType, argTypes.get(0))));
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
				for (EClass containingEClass : queryEnvironment.getEPackageProvider().getSiblingsEClasses(
						receiverEClass)) {
					result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
							containingEClass)));
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing("%s can't have siblings",
							argTypes.get(0))));
				}
			} else if (argTypes.size() == 2) {
				final IType filterType = argTypes.get(1);
				for (EClass containingEClass : queryEnvironment.getEPackageProvider().getSiblingsEClasses(
						receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containingEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't be a sibling of %s", filterType, argTypes.get(0))));
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
		} else if ("ancestors".equals(publicMethod.getName())) {
			result = new AncestorsService(publicMethod, this);
		} else if ("eInverse".equals(publicMethod.getName())) {
			result = new EInverseService(publicMethod, this);
		} else if ("followingSiblings".equals(publicMethod.getName())) {
			result = new FollowingSiblingsService(publicMethod, this);
		} else if ("precedingSiblings".equals(publicMethod.getName())) {
			result = new PrecedingSiblingsService(publicMethod, this);
		} else if ("siblings".equals(publicMethod.getName())) {
			result = new SiblingsService(publicMethod, this);
		} else {
			result = new Service(publicMethod, this);
		}

		return result;
	}

	/**
	 * Returns a list of the {@link EObject} recursively contained in the specified root eObject.
	 * 
	 * @param eObject
	 *            the root of the content tree
	 * @return the recursive content of the specified eObject.
	 */
	public List<EObject> eAllContents(EObject eObject) {
		final List<EObject> result = Lists.newArrayList();

		final Iterator<EObject> it = eObject.eAllContents();
		while (it.hasNext()) {
			result.add(it.next());
		}

		return result;
	}

	/**
	 * Returns a list of the {@link EObject} recursively contained in the specified root eObject and that are
	 * instances of the specified EClass.
	 * 
	 * @param eObject
	 *            the root of the content tree
	 * @param type
	 *            the type used to select elements.
	 * @return the recursive content of the specified eObject.
	 */
	public List<EObject> eAllContents(EObject eObject, final EClass type) {
		// TODO optimize by pruning dead branches according to EClasses.
		final List<EObject> result = Lists.newArrayList();

		final Iterator<EObject> it = eObject.eAllContents();
		while (it.hasNext()) {
			final EObject input = it.next();
			if (type.isSuperTypeOf(input.eClass())) {
				result.add(input);
			}
		}

		return result;
	}

	/**
	 * Returns the contents of the specified {@link EObject} instance.
	 * 
	 * @param eObject
	 *            the eObject which content is requested.
	 * @return the content of the specified eObject.
	 */
	public List<EObject> eContents(EObject eObject) {
		return eObject.eContents();
	}

	/**
	 * Returns a list made of the instances of the specified type in the contents of the specified eObject.
	 * 
	 * @param eObject
	 *            the eObject which content is requested.
	 * @param type
	 *            the type filter
	 * @return the filtered content of the specified eObject
	 */
	public List<EObject> eContents(EObject eObject, final EClass type) {
		final List<EObject> result = Lists.newArrayList();

		for (EObject input : eObject.eContents()) {
			if (type.isSuperTypeOf(input.eClass())) {
				result.add(input);
			}
		}

		return result;
	}

	/**
	 * the container of the specified eObject.
	 * 
	 * @param eObject
	 *            the eObject which container is requested.
	 * @return the container of the specified eObject.
	 */
	public EObject eContainer(EObject eObject) {
		return eObject.eContainer();
	}

	/**
	 * <p>
	 * Returns the first container of the receiver that if of the given type.
	 * </p>
	 * 
	 * @param eObject
	 *            the eObject which container is seeked.
	 * @param type
	 *            the type filter.
	 * @return the first container of the receiver that if of the given type.
	 */
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

	/**
	 * <p>
	 * Returns self or the first container of the receiver that if of the given type.
	 * </p>
	 * 
	 * @param eObject
	 *            the eObject which container is seeked.
	 * @param type
	 *            the type filter.
	 * @return self or the first container of the receiver that if of the given type.
	 */
	public EObject eContainerOrSelf(EObject eObject, EClass type) {
		final EObject result;

		if (type.isSuperTypeOf(eObject.eClass())) {
			result = eObject;
		} else {
			result = eContainer(eObject, type);
		}

		return result;
	}

	/**
	 * Returns the {@link EClass} of the specified {@link EObject}.
	 * 
	 * @param eObject
	 *            the eObject which {@link EClass} is seeked.
	 * @return the {@link EClass} of the specified {@link EObject}.
	 */
	public EClass eClass(EObject eObject) {
		return eObject.eClass();
	}

	public void setCrossReferencer(CrossReferenceProvider crossReferencer) {
		this.crossReferencer = crossReferencer;
	}

	/**
	 * Returns the sequence containing the full set of inverse references.
	 * 
	 * @param self
	 *            The EObject we seek the inverse references of.
	 * @return The sequence containing the full set of inverse references.
	 */
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

	/**
	 * Returns the elements of the given type from the set of the inverse references of the receiver.
	 * 
	 * @param self
	 *            The EObject we seek the inverse references of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return The sequence containing the full set of inverse references.
	 */
	public Set<EObject> eInverse(EObject self, EClassifier filter) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null || filter == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (EStructuralFeature.Setting setting : settings) {
				if (filter.isInstance(setting.getEObject())) {
					result.add(setting.getEObject());
				}
			}
		}

		return result;
	}

	/**
	 * Returns the elements from the set of the inverse {@link EStructuralFeature#getName() feature name} of
	 * the receiver.
	 * 
	 * @param self
	 *            The EObject we seek the inverse references of.
	 * @param featureName
	 *            the {@link EStructuralFeature#getName() feature name}.
	 * @return The sequence containing the full set of inverse references.
	 */
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

	/**
	 * Returns a Sequence containing the full set of <code>object</code>'s ancestors.
	 * 
	 * @param object
	 *            The EObject we seek the ancestors of.
	 * @return Sequence containing the full set of the receiver's ancestors.
	 */
	public List<EObject> ancestors(EObject object) {
		return ancestors(object, null);
	}

	/**
	 * Returns a Sequence containing the full set of <code>object</code>'s ancestors.
	 * 
	 * @param object
	 *            The EObject we seek the ancestors of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of the receiver's ancestors.
	 */
	public List<EObject> ancestors(EObject object, EClassifier filter) {
		final List<EObject> result = new ArrayList<EObject>();

		EObject container = object.eContainer();
		while (container != null) {
			if (filter == null || filter.isInstance(container)) {
				result.add(container);
			}
			container = container.eContainer();
		}

		return result;
	}

	/**
	 * Handles calls to the operation "eGet". This will fetch the value of the feature named
	 * <em>featureName</em> on <em>source</em>.
	 * 
	 * @param source
	 *            The EObject we seek to retrieve a feature value of.
	 * @param featureName
	 *            Name of the feature which value we need to retrieve.
	 * @return Value of the given feature on the given object.
	 */
	public Object eGet(EObject source, String featureName) {
		for (EStructuralFeature feature : source.eClass().getEAllStructuralFeatures()) {
			if (feature.getName().equals(featureName)) {
				return source.eGet(feature);
			}
		}

		return null;
	}

	/**
	 * Returns a Sequence containing all following siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the following siblings of.
	 * @return Sequence containing the sought set of the receiver's following siblings.
	 */
	public List<EObject> followingSiblings(EObject eObject) {
		return siblings(eObject, null, false);
	}

	/**
	 * Returns a Sequence containing all following siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the following siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the sought set of the receiver's following siblings.
	 */
	public List<EObject> followingSiblings(EObject eObject, EClassifier filter) {
		return siblings(eObject, filter, false);
	}

	/**
	 * Returns a Sequence containing all preceding siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the preceding siblings of.
	 * @return Sequence containing the sought set of the receiver's preceding siblings.
	 */
	public List<EObject> precedingSiblings(EObject eObject) {
		return siblings(eObject, null, true);
	}

	/**
	 * Returns a Sequence containing all preceding siblings of <code>source</code>.
	 * 
	 * @param eObject
	 *            The EObject we seek the preceding siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the sought set of the receiver's preceding siblings.
	 */
	public List<EObject> precedingSiblings(EObject eObject, EClassifier filter) {
		return siblings(eObject, filter, true);
	}

	/**
	 * Returns a Sequence containing the full set of <code>source</code>'s siblings.
	 * 
	 * @param eObject
	 *            The EObject we seek the siblings of.
	 * @return Sequence containing the full set of the receiver's siblings.
	 */
	public List<EObject> siblings(EObject eObject) {
		return siblings(eObject, null);
	}

	/**
	 * Returns a Sequence containing the full set of <code>source</code>'s siblings.
	 * 
	 * @param eObject
	 *            The EObject we seek the siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of the receiver's siblings.
	 */
	public List<EObject> siblings(final EObject eObject, final EClassifier filter) {
		final List<EObject> result;

		Object container = getContainer(eObject);
		if (container != null) {
			result = Lists.newArrayList();
			for (EObject input : getContents(container)) {
				if (input != eObject
						&& (filter == null || filter.isInstance(input.eClass()) || filter.equals(input
								.eClass()))) {
					result.add(input);
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
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @param preceding
	 *            If <code>true</code>, we'll return the preceding siblings of <em>source</em>. Otherwise,
	 *            this will return its followingSiblings.
	 * @return Sequence containing the sought set of the receiver's siblings.
	 */
	private List<EObject> siblings(final EObject eObject, final EClassifier filter, Boolean preceding) {
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
				if (filter == null || filter.isInstance(input.eClass()) || filter.equals(input.eClass())) {
					result.add(input);
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

		for (ListIterator<EObject> iter = result.listIterator(); iter.hasNext();) {
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

}
