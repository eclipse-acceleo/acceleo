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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
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
	 * Log message used when accessing an unknown feature.
	 */
	private static final String UNKNOWN_FEATURE = "Feature %s not found in EClass %s";

	/**
	 * Log message used when accessing a feature on a JavaObject.
	 */
	private static final String NON_EOBJECT_FEATURE_ACCESS = "Attempt to access feature (%s) on a non ModelObject value (%s).";

	/**
	 * Illegal state message.
	 */
	private static final String DON_T_KNOW_WHAT_TO_DO_WITH = "don't know what to do with ";

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
		private FilteredContentIterator(Object object, boolean includeRoot,
				Set<EStructuralFeature> features) {
			super(object, includeRoot);
			this.features = features;
		}

		@Override
		public Iterator<EObject> getChildren(Object object) {
			final List<EObject> result = new ArrayList<EObject>();
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
					for (Object childElement : (Collection<?>)value) {
						if (childElement instanceof EObject) {
							result.add((EObject)childElement);
						}
					}
				} else {
					throw new IllegalStateException(DON_T_KNOW_WHAT_TO_DO_WITH + value.getClass());
				}
			} else if (value instanceof EObject) {
				result.add((EObject)value);

			}
		}
	}

	private static final class EObjectFeatureAccess extends JavaMethodService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param method
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		EObjectFeatureAccess(Method method, Object serviceInstance, boolean forWorkspace) {
			super(method, serviceInstance, forWorkspace);
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
			final String featureName = ((StringLiteral)call.getArguments().get(1)).getValue();
			final Set<IType> result = featureAccessTypes(services, queryEnvironment, argTypes.get(0),
					featureName);

			return result;
		}

		/**
		 * Gets the type of a feature access.
		 * 
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param receiverTypes
		 *            the target types to gets the feature from
		 * @param featureName
		 *            the feature name
		 * @return the type of a feature access
		 */
		public Set<IType> featureAccessTypes(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, IType receiverType, String featureName) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> eClasses = services.getEClasses(receiverType);
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					EStructuralFeature feature = eCls.getEStructuralFeature(featureName);
					if (feature == null) {
						result.add(services.nothing(UNKNOWN_FEATURE, featureName, eCls.getName()));
					} else {
						final EClassifierType featureBasicType = new EClassifierType(queryEnvironment, feature
								.getEType());
						if (feature.isMany()) {
							result.add(new SequenceType(queryEnvironment, featureBasicType));
						} else {
							result.add(featureBasicType);
						}
					}
				}
			} else {
				if (receiverType.getType() != null) {
					result.add(services.nothing(NON_EOBJECT_FEATURE_ACCESS, featureName, receiverType
							.getType().toString()));
				} else {
					result.add(services.nothing(NON_EOBJECT_FEATURE_ACCESS, featureName, "null"));
				}
			}

			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.Map)
		 */
		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<IType> knownReceiverTypes = new LinkedHashSet<IType>();
			for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				if (knownReceiverTypes.add(entry.getKey().get(0))) {
					result.addAll(entry.getValue());
				}
			}

			return result;
		}

		@Override
		public List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
				Set<IType> receiverTypes) {
			return getEStructuralFeatureProposals(queryEnvironment, receiverTypes);
		}

		/**
		 * Gets the {@link List} of {@link EFeatureCompletionProposal} for {@link EStructuralFeature}.
		 * 
		 * @param receiverTypes
		 *            the receiver types.
		 * @return the {@link List} of {@link EFeatureCompletionProposal} for {@link EStructuralFeature}
		 */
		public List<ICompletionProposal> getEStructuralFeatureProposals(
				IReadOnlyQueryEnvironment queryEnvironment, Set<IType> receiverTypes) {
			final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
			final Set<EClass> eClasses = new LinkedHashSet<EClass>();

			for (IType iType : receiverTypes) {
				if (iType.getType() instanceof EClass) {
					eClasses.add((EClass)iType.getType());
				} else if (iType.getType() instanceof Class<?>) {
					final Set<EClassifier> eClassifiers = queryEnvironment.getEPackageProvider()
							.getEClassifiers((Class<?>)iType.getType());
					if (eClassifiers != null) {
						for (EClassifier eClassifier : eClassifiers) {
							if (eClassifier instanceof EClass) {
								eClasses.add((EClass)eClassifier);
							}
						}
					}
				}
			}

			for (EStructuralFeature feature : queryEnvironment.getEPackageProvider().getEStructuralFeatures(
					eClasses)) {
				result.add(new EFeatureCompletionProposal(feature));
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
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private EContainerService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
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
							result.add(new EClassifierType(queryEnvironment, eCls));
						} else if (argTypes.size() == 2) {
							result.add(new EClassifierType(queryEnvironment, ((EClassifierLiteralType)argTypes
									.get(1)).getType()));
						}
					} else {
						result.addAll(getTypeForSpecificType(call, services, queryEnvironment, argTypes,
								eCls));
					}
				}
			} else {
				result.add(services.nothing(ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S, argTypes
						.get(0)));
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
		private Set<IType> getTypeForSpecificType(Call call, ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
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
				final Set<IType> filterTypes = new LinkedHashSet<>();
				if (call != null) {
					final Expression typeExpression = call.getArguments().get(1);
					if (typeExpression instanceof EClassifierTypeLiteral
							|| typeExpression instanceof TypeSetLiteral) {
						filterTypes.addAll(getTypes(queryEnvironment, typeExpression));
					} else {
						filterTypes.add(argTypes.get(1));
					}
				} else {
					filterTypes.add(argTypes.get(1));
				}
				for (EClass containingEClass : queryEnvironment.getEPackageProvider()
						.getAllContainingEClasses(receiverEClass)) {
					for (IType filterType : filterTypes) {
						final Set<IType> intersectionTypes = services.intersection(new EClassifierType(
								queryEnvironment, containingEClass), filterType);
						result.addAll(intersectionTypes);
					}
				}
				if (result.isEmpty()) {
					result.add(services.nothing(S_CAN_T_CONTAIN_DIRECTLY_OR_INDIRECTLY_S, filterTypes,
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
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private EContainerOrSelfService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
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

			final Set<EClass> eClasses = services.getEClasses(argTypes.get(0));
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					if (eCls == EcorePackage.eINSTANCE.getEObject()) {
						result.add(new EClassifierType(queryEnvironment, ((EClassifierLiteralType)argTypes
								.get(1)).getType()));
					} else {
						result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
					}
				}
			} else {
				result.add(services.nothing(ONLY_E_CLASS_CAN_BE_CONTAINED_INTO_OTHER_E_CLASSES_NOT_S, argTypes
						.get(0)));
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

			final Set<IType> intersectionSelfTypes = services.intersection(argTypes.get(0), argTypes.get(1));
			result.addAll(intersectionSelfTypes);
			final IType filterType = argTypes.get(1);
			for (EClass containingEClass : queryEnvironment.getEPackageProvider().getAllContainingEClasses(
					receiverEClass)) {
				final Set<IType> intersectionTypes = services.intersection(new EClassifierType(
						queryEnvironment, containingEClass), filterType);
				result.addAll(intersectionTypes);
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
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private EContentsService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
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
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
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
				final Set<IType> filterTypes = new LinkedHashSet<IType>();
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
					for (EClass containedEClass : queryEnvironment.getEPackageProvider().getContainedEClasses(
							receiverEClass)) {
						final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
								containedEClass), filterType);
						if (lowerType != null) {
							result.add(new SequenceType(queryEnvironment, lowerType));
						}
					}
				}
				if (result.isEmpty()) {
					result.add(new SequenceType(queryEnvironment, services.nothing(
							"%s can't contain %s directly", argTypes.get(0), argTypes.get(1))));
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
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private AllInstancesService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
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
				result = new LinkedHashSet<IType>();
				result.add(new SequenceType(queryEnv, services.nothing(
						"No IRootEObjectProvider registered")));
			} else {
				List<IType> newArgTypes = new ArrayList<IType>(argTypes);
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
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		private EInverseService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
			super(serviceMethod, serviceInstance, forWorkspace);
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
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 * @since 4.0.0
		 */
		private EInverseService(Method serviceMethod, Object serviceInstance, int filterIndex,
				boolean forWorkspace) {
			super(serviceMethod, serviceInstance, filterIndex, forWorkspace);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> eClasses = services.getEClasses(argTypes.get(0));
			if (!eClasses.isEmpty()) {
				for (EClass eCls : eClasses) {
					if (eCls == EcorePackage.eINSTANCE.getEObject()) {
						if (argTypes.size() == 1 || !(argTypes.get(1).getType() instanceof EClass)) {
							result.add(new SetType(queryEnvironment, new EClassifierType(queryEnvironment,
									eCls)));
						} else if (argTypes.size() == 2) {
							result.add(new SetType(queryEnvironment, new EClassifierType(queryEnvironment,
									((EClassifierLiteralType)argTypes.get(1)).getType())));
						}
					} else {
						result.addAll(getTypeForSpecificType(call, services, queryEnvironment, argTypes,
								eCls));
					}
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
		 * @param call
		 *            the {@link Call}
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
		private Set<IType> getTypeForSpecificType(Call call, ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final EClass receiverEClass) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClass> inverseEClasses = queryEnvironment.getEPackageProvider().getInverseEClasses(
					receiverEClass);
			if (argTypes.size() == 1 || !(argTypes.get(1).getType() instanceof EClass)) {
				result.addAll(getTypeForSpecificTypeNoFilterOrName(call, services, queryEnvironment, argTypes,
						inverseEClasses));
			} else if (argTypes.size() == 2) {
				result.addAll(getTypeForSpecificTypeFilter(services, queryEnvironment, argTypes,
						inverseEClasses));
			}

			return result;
		}

		/**
		 * Computes {@link IType} for {@link EObjectServices#eInverse(EObject) eInverse(EObject)} and
		 * {@link EObjectServices#eInverse(EObject, String) eInverse(EObject, String)}.
		 * 
		 * @param call
		 *            the {@link Call}
		 * @param services
		 *            the {@link ValidationServices}
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param argTypes
		 *            arguments {@link IType}
		 * @param inverseEClasses
		 *            the {@link Set} of inverse {@link EClass}
		 * @return the {@link Set} of possible {@link IType}
		 */
		private Set<IType> getTypeForSpecificTypeNoFilterOrName(Call call, ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final Set<EClass> inverseEClasses) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final String featureName;
			if (call.getArguments().size() == 2 && call.getArguments().get(1) instanceof StringLiteral) {
				featureName = ((StringLiteral)call.getArguments().get(1)).getValue();
			} else {
				featureName = null;
			}
			for (EClass inverseEClass : inverseEClasses) {
				if (featureName == null || inverseEClass.getEStructuralFeature(featureName) != null) {
					result.add(new SetType(queryEnvironment, new EClassifierType(queryEnvironment,
							inverseEClass)));
				}
			}
			if (result.isEmpty()) {
				result.add(new SetType(queryEnvironment, services.nothing("%s don't have inverse", argTypes
						.get(0))));
			}

			return result;
		}

		/**
		 * Computes {@link IType} for {@link EObjectServices#eInverse(EObject, EClassifier) eInverse(EObject,
		 * EClassifier)}.
		 * 
		 * @param services
		 *            the {@link ValidationServices}
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param argTypes
		 *            arguments {@link IType}
		 * @param inverseEClasses
		 *            the {@link Set} of inverse {@link EClass}
		 * @return the {@link Set} of possible {@link IType}
		 */
		private Set<IType> getTypeForSpecificTypeFilter(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes,
				final Set<EClass> inverseEClasses) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final IType filterType = argTypes.get(1);
			for (EClass inverseEClass : inverseEClasses) {
				final IType lowerType = services.lower(new EClassifierType(queryEnvironment, inverseEClass),
						filterType);
				if (lowerType != null) {
					result.add(new SetType(queryEnvironment, lowerType));
				}
			}
			if (result.isEmpty()) {
				result.add(new SetType(queryEnvironment, services.nothing("%s don't have inverse to %s",
						argTypes.get(0), filterType)));
			}

			return result;
		}
	}

	/**
	 * EGet {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class EGetService extends JavaMethodService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param method
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 */
		EGetService(Method method, Object serviceInstance, boolean forWorkspace) {
			super(method, serviceInstance, forWorkspace);
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

			if (call.getArguments().get(1) instanceof StringLiteral) {
				final String featureName = ((StringLiteral)call.getArguments().get(1)).getValue();
				final EClass eCls = (EClass)argTypes.get(0).getType();
				final EStructuralFeature feature = eCls.getEStructuralFeature(featureName);
				if (feature != null) {
					if (feature.isMany()) {
						result.add(new SetType(queryEnvironment, new EClassifierType(queryEnvironment, feature
								.getEType())));
					} else {
						result.add(new EClassifierType(queryEnvironment, feature.getEType()));
					}
				} else {
					result.add(services.nothing("EStructuralFeature %s not found for %s", featureName,
							argTypes.get(0)));
				}
			} else {
				result.add(new ClassType(queryEnvironment, Object.class));
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
	public EObjectServices(IReadOnlyQueryEnvironment queryEnvironment, CrossReferenceProvider crossReferencer,
			IRootEObjectProvider rootProvider) {
		this.queryEnvironment = queryEnvironment;
		this.crossReferencer = crossReferencer;
		this.rootProvider = rootProvider;
	}

	@Override
	protected IService<Method> getService(Method publicMethod, boolean forWorkspace) {
		final IService<Method> result;

		if ("eContents".equals(publicMethod.getName())) {
			result = new EContentsService(publicMethod, this, forWorkspace);
		} else if ("eAllContents".equals(publicMethod.getName())) {
			result = new EAllContentsService(publicMethod, this, forWorkspace);
		} else if ("eContainer".equals(publicMethod.getName())) {
			result = new EContainerService(publicMethod, this, forWorkspace);
		} else if ("eContainerOrSelf".equals(publicMethod.getName())) {
			result = new EContainerOrSelfService(publicMethod, this, forWorkspace);
		} else if ("eInverse".equals(publicMethod.getName())) {
			if (publicMethod.getParameterTypes().length == 2 && publicMethod
					.getParameterTypes()[1] == String.class) {
				// no filter for eInverse(EObject, String)
				result = new EInverseService(publicMethod, this, 10, forWorkspace);
			} else {
				result = new EInverseService(publicMethod, this, forWorkspace);
			}
		} else if ("allInstances".equals(publicMethod.getName())) {
			result = new AllInstancesService(publicMethod, this, forWorkspace);
		} else if (AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME.equals(publicMethod.getName())) {
			result = new EObjectFeatureAccess(publicMethod, this, forWorkspace);
		} else if ("eGet".equals(publicMethod.getName())) {
			result = new EGetService(publicMethod, this, forWorkspace);
		} else {
			result = new JavaMethodService(publicMethod, this, forWorkspace);
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
		final ArrayList<EObject> res = new ArrayList<EObject>();

		final TreeIterator<EObject> it = eObject.eAllContents();
		while (it.hasNext()) {
			res.add(it.next());
		}

		return res;
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
		final Set<EClass> types = new LinkedHashSet<EClass>();
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
			final Set<EStructuralFeature> features = new LinkedHashSet<EStructuralFeature>();
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
		List<EObject> allChildrens = new ArrayList<EObject>();
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
		return new ArrayList<EObject>(eObject.eContents());
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
			final Set<EStructuralFeature> features = new LinkedHashSet<EStructuralFeature>();
			for (EClass type : types) {
				features.addAll(queryEnvironment.getEPackageProvider().getContainingEStructuralFeatures(
						type));
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
		final List<EObject> result = new ArrayList<EObject>();

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
				throw new IllegalStateException(DON_T_KNOW_WHAT_TO_DO_WITH + child.getClass());
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
		result = "The EClass of the specified EObject",
		examples = {
			@Example(
				expression = "anEObject.eClass()",
				result = "anEClass"
			)
		}
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
		result = "The containing feature of the specified EObject",
		examples = {
			@Example(
				expression = "anEObject.eContainingFeature()",
				result = "anEStructuralFeature"
			)
		}
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
		result = "The containment feature of the specified EObject",
		examples = {
			@Example(
				expression = "anEObject.eContainmentFeature()",
				result = "anEReference"
			)
		}
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
		result = "The set of the inverse references",
		examples = {
			@Example(
				expression = "anEObject.eInverse()",
				result = "OrderedSet{firstReferencingEObject, secondReferencingEObject...}"
			)
		}
	)
	// @formatter:on
	public Set<EObject> eInverse(EObject self) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null) {
			result = Collections.emptySet();
		} else {
			result = new LinkedHashSet<EObject>();
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
		result = "The set of the inverse references",
		examples = {
			@Example(
				expression = "anEObject.eInverse(anEClass)",
				result = "OrderedSet{firstReferencingEObject, secondReferencingEObject...}"
			)
		}
	)
	// @formatter:on
	public Set<EObject> eInverse(EObject self, EClassifier type) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null || type == null) {
			result = Collections.emptySet();
		} else {
			result = new LinkedHashSet<EObject>();
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
		result = "The set of the inverse references",
		examples = {
			@Example(
				expression = "anEObject.eInverse(aFeatureName)",
				result = "OrderedSet{firstReferencingEObject, secondReferencingEObject...}"
			)
		}
	)
	// @formatter:on
	public Set<EObject> eInverse(EObject self, String featureName) {
		final Set<EObject> result;

		final Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(self);
		if (settings == null) {
			result = Collections.emptySet();
		} else {
			result = new LinkedHashSet<EObject>();
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
		result = "The value of the given feature on the given EObject",
		examples = {
			@Example(
				expression = "anEObject.eGet(aFeatureName)",
				result = "aValue"
			)
		}
	)
	// @formatter:on
	public Object eGet(EObject eObject, final String featureName) {
		if (eObject == null || featureName == null) {
			throw new NullPointerException();
		}
		final EStructuralFeature feature = eObject.eClass().getEStructuralFeature(featureName);

		Object result = null;
		if (feature != null) {
			result = eObject.eGet(feature);
		}

		if (result instanceof Set<?>) {
			result = new LinkedHashSet<Object>((Set<?>)result);
		} else if (result instanceof EMap<?, ?>) {
			result = new BasicEMap<Object, Object>(((EMap<?, ?>)result).map());
		} else if (result instanceof Collection<?>) {
			result = new ArrayList<Object>((Collection<?>)result);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Handles calls to the operation \"eGet\". This will fetch the value of the given feature on \"source\"",
		params = {
			@Param(name = "eObject", value = "The eObject we seek to retrieve a feature value of."),
			@Param(name = "feature", value = "The feature which value we need to retrieve."),
		},
		result = "The value of the given feature on the given EObject",
		examples = {
				@Example(
					expression = "anEObject.eGet(aFeature)",
					result = "aValue"
				)
			}
	)
	// @formatter:on
	public Object eGet(EObject eObject, final EStructuralFeature feature) {
		if (eObject == null || feature == null) {
			throw new NullPointerException();
		}
		final Object result;

		final Object value = eObject.eGet(feature);
		if (value instanceof Set<?>) {
			result = new LinkedHashSet<Object>((Set<?>)value);
		} else if (value instanceof EMap<?, ?>) {
			result = new BasicEMap<Object, Object>(((EMap<?, ?>)value).map());
		} else if (value instanceof Collection<?>) {
			result = new ArrayList<Object>((Collection<?>)value);
		} else {
			result = value;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Handles calls to the operation \"eGet\". This will fetch the value of the given feature on \"source\"; " +
				"the value is optionally resolved before it is returned.",
		params = {
			@Param(name = "eObject", value = "The eObject we seek to retrieve a feature value of."),
			@Param(name = "feature", value = "The feature which value we need to retrieve."),
			@Param(name = "resolve", value = "whether to resolve the value or not."),
		},
		result = "The value of the given feature on the given EObject",
		examples = {
				@Example(
					expression = "anEObject.eGet(aFeature, true)",
					result = "aValue"
				)
			}
	)
	// @formatter:on
	public Object eGet(EObject eObject, final EStructuralFeature feature, final boolean resolve) {
		if (eObject == null || feature == null) {
			throw new NullPointerException();
		}

		final Object result;

		final Object value = eObject.eGet(feature, resolve);
		if (value instanceof Set<?>) {
			result = new LinkedHashSet<Object>((Set<?>)value);
		} else if (value instanceof EMap<?, ?>) {
			result = new BasicEMap<Object, Object>(((EMap<?, ?>)value).map());
		} else if (value instanceof Collection<?>) {
			result = new ArrayList<Object>((Collection<?>)value);
		} else {
			result = value;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns all instances of the EClass",
		params = {
			@Param(name = "type", value = "The EClass"),
		},
		result = "all instances of the EClass",
		examples = {
			@Example(
				expression = "anEClass.allInstances()",
				result = "Sequence{firstEObject,secondEObject...}"
			)
		}
	)
	// @formatter:on
	public List<EObject> allInstances(EClass type) {
		final List<EObject> result;

		if (type != null) {
			final Set<EClass> types = new LinkedHashSet<EClass>();
			types.add(type);
			result = allInstances(types);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns all instances of each EClass from the OrderedSet",
		params = {
			@Param(name = "types", value = "The OrderedSet of EClass"),
		},
		result = "all instances of each EClass from the OrderedSet",
		examples = {
			@Example(
				expression = "{ecore::EPackage | ecore::EClass}->allInstances()",
				result = "Sequence{ePackage, eClass, ...}"
			)
		}
	)
	// @formatter:on
	public List<EObject> allInstances(Set<EClass> types) {
		final List<EObject> result = new ArrayList<EObject>();

		if (rootProvider != null && types != null) {
			for (EObject root : rootProvider.getRoots()) {
				if (eIsInstanceOf(root, types)) {
					result.add(root);
				}
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
		result = "The list of all EObjects cross-referenced from the receiver.",
		examples = {
			@Example(
				expression = "anEObject.eCrossReferences()",
				result = "Sequence{firstReferencedEObject, secondReferencedEObject...}"
			)
		}
	)
	// @formatter:on
	public Object eCrossReferences(EObject eObject) {
		return new ArrayList<Object>(eObject.eCrossReferences());
	}

	/**
	 * Gets the value of the given {@link EStructuralFeature#getName() feature name} on the given
	 * {@link EObject}.
	 * 
	 * @param self
	 *            the {@link EObject}
	 * @param featureName
	 *            the {@link EStructuralFeature#getName() feature name}
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value of the specified feature in the specified object.
	 */
	public Object aqlFeatureAccess(EObject self, String featureName) {
		final Object result;

		if (self == null) {
			result = null;
		} else {
			EClass eClass = ((EObject)self).eClass();
			EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
			if (feature == null) {
				final String message = String.format(UNKNOWN_FEATURE, featureName, eClass.getName());
				result = new Nothing(message);
			} else {
				result = ((EObject)self).eGet(feature);
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link IType} for the given {@link Expression}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param typeExpression
	 *            the {@link Expression}
	 * @return the {@link Set} of {@link IType} for the given {@link Expression}
	 */
	public static Set<IType> getTypes(IReadOnlyQueryEnvironment queryEnvironment,
			final Expression typeExpression) {
		final Set<IType> filterTypes = new LinkedHashSet<>();

		if (typeExpression instanceof EClassifierTypeLiteral) {
			final EClassifierTypeLiteral typeLiteral = (EClassifierTypeLiteral)typeExpression;
			for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getTypes(typeLiteral
					.getEPackageName(), typeLiteral.getEClassifierName())) {
				filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
			}
		} else if (typeExpression instanceof TypeSetLiteral) {
			final TypeSetLiteral typeLiteral = (TypeSetLiteral)typeExpression;
			for (TypeLiteral type : typeLiteral.getTypes()) {
				if (type instanceof EClassifierTypeLiteral) {
					for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getTypes(
							((EClassifierTypeLiteral)type).getEPackageName(), ((EClassifierTypeLiteral)type)
									.getEClassifierName())) {
						filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				}
			}
		}

		return filterTypes;
	}

}
