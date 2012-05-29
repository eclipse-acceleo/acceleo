/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.compatibility.parser.ast.ocl.environment;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoEnvironment;
import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoTypeResolver;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.AbstractTypeChecker;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.TypeChecker;
import org.eclipse.ocl.TypeResolver;
import org.eclipse.ocl.ecore.BagType;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.OrderedSetType;
import org.eclipse.ocl.ecore.PrimitiveType;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.ecore.SequenceType;
import org.eclipse.ocl.ecore.SetType;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.TypeExp;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.options.ParsingOptions;
import org.eclipse.ocl.types.AnyType;
import org.eclipse.ocl.types.CollectionType;
import org.eclipse.ocl.types.OCLStandardLibrary;
import org.eclipse.ocl.types.TupleType;
import org.eclipse.ocl.types.TypeType;
import org.eclipse.ocl.utilities.PredefinedType;
import org.eclipse.ocl.utilities.TypedElement;
import org.eclipse.ocl.utilities.UMLReflection;

/**
 * This class will not compile under Eclipse Ganymede with OCL 1.2 installed. It requires OCL 1.3 and
 * shouldn't be called or instantiated under previous versions.
 * <p>
 * This code is meant to be called in Eclipse 3.5 and later only, and will <em>not</em> compile in earlier
 * versions. That is expected and will not provoke runtime errors.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironmentGalileo extends AcceleoEnvironment {
	/** Will contain the names of the relational operators (<, >, <=, =>). */
	private static final Set<String> RELATIONAL_OPERATORS;

	/** Type checker created for this environment. */
	private AcceleoTypeChecker typeChecker;

	static {
		RELATIONAL_OPERATORS = new java.util.HashSet<String>();
		RELATIONAL_OPERATORS.add(PredefinedType.LESS_THAN_NAME);
		RELATIONAL_OPERATORS.add(PredefinedType.LESS_THAN_EQUAL_NAME);
		RELATIONAL_OPERATORS.add(PredefinedType.GREATER_THAN_NAME);
		RELATIONAL_OPERATORS.add(PredefinedType.GREATER_THAN_EQUAL_NAME);
	}

	/**
	 * Delegates instantiation to the super constructor.
	 * 
	 * @param parent
	 *            Parent for this Acceleo environment.
	 */
	public AcceleoEnvironmentGalileo(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		super(parent);
		setOption(ParsingOptions.USE_BACKSLASH_ESCAPE_PROCESSING, Boolean.TRUE);
	}

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	public AcceleoEnvironmentGalileo(Resource oclEnvironmentResource) {
		super(oclEnvironmentResource);
		setOption(ParsingOptions.USE_BACKSLASH_ESCAPE_PROCESSING, Boolean.TRUE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#createTypeChecker()
	 */
	@Override
	public TypeChecker<EClassifier, EOperation, EStructuralFeature> createTypeChecker() {
		if (typeChecker == null) {
			typeChecker = new AcceleoTypeChecker(this);
		}
		return typeChecker;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoEnvironment#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		typeChecker.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#createTypeResolver()
	 */
	@Override
	protected TypeResolver<EClassifier, EOperation, EStructuralFeature> createTypeResolver() {
		return new AcceleoTypeResolver(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#createTypeResolver(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	protected TypeResolver<EClassifier, EOperation, EStructuralFeature> createTypeResolver(Resource resource) {
		return new AcceleoTypeResolver(this, resource);
	}

	/**
	 * This will allow us to type our standard and non standard operations.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	public class AcceleoTypeChecker extends AbstractTypeChecker<EClassifier, EOperation, EStructuralFeature, EParameter> {
		/**
		 * As we will infer type information for the non standard library, we'll have to keep references to
		 * the modified types.
		 */
		private final Map<EClassifier, Set<EClassifier>> alteredTypes = new HashMap<EClassifier, Set<EClassifier>>();

		/**
		 * A cache for the previously found EOperations.
		 */
		private final Map<EOperationSignatureElement, EOperation> eOperationCache = new HashMap<AcceleoEnvironmentGalileo.AcceleoTypeChecker.EOperationSignatureElement, EOperation>();

		/**
		 * This will map a duet EClassifier-featureName to the actual EStructuralFeature in the subtypes
		 * hierarchy. Keys of this map will be <code>EClassifier.hashCode() + featureName.hasCode()</code>.
		 */
		private final Map<Long, EStructuralFeature> hierarchyFeatureCache = new HashMap<Long, EStructuralFeature>();

		/** This will allow us to maintain the subtypes hierarchy of our metamodel. */
		private final Map<EClassifier, Set<EClassifier>> subTypes = new HashMap<EClassifier, Set<EClassifier>>();

		/**
		 * Delegates instantiation to the super constructor.
		 * 
		 * @param environment
		 *            The environment to which belongs this checker.
		 */
		public AcceleoTypeChecker(
				Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> environment) {
			super(environment);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#commonSuperType(java.lang.Object, java.lang.Object,
		 *      java.lang.Object)
		 */
		@Override
		public EClassifier commonSuperType(Object problemObject, EClassifier type1, EClassifier type2) {
			/*
			 * OCL tells us that a Collection is not an Object, we'll circumvent this limitation in Acceleo's
			 * case.
			 */
			EClassifier oclType1 = null;
			EClassifier oclType2 = null;
			if (type1 != null) {
				oclType1 = getEnvironment().getUMLReflection().getOCLType(type1);
			}
			if (type2 != null) {
				oclType2 = getEnvironment().getUMLReflection().getOCLType(type2);
			}

			final EClassifier commonSuperType;
			if (oclType1 == getEnvironment().getOCLStandardLibrary().getOclAny()) {
				commonSuperType = oclType1;
			} else if (oclType2 == getEnvironment().getOCLStandardLibrary().getOclAny()) {
				commonSuperType = oclType2;
			} else if (oclType1 instanceof CollectionType<?, ?>
					&& !(oclType2 instanceof CollectionType<?, ?>)) {
				commonSuperType = getEnvironment().getOCLStandardLibrary().getOclAny();
			} else if (oclType2 instanceof CollectionType<?, ?>
					&& !(oclType1 instanceof CollectionType<?, ?>)) {
				commonSuperType = getEnvironment().getOCLStandardLibrary().getOclAny();
			} else {
				commonSuperType = super.commonSuperType(problemObject, type1, type2);
			}
			return commonSuperType;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#exactTypeMatch(java.lang.Object, java.lang.Object)
		 */
		@Override
		public boolean exactTypeMatch(EClassifier type1, EClassifier type2) {
			boolean match = false;
			Set<EClassifier> alteredType1 = alteredTypes.get(type1);
			Set<EClassifier> alteredType2 = alteredTypes.get(type2);
			if (alteredType1 != null) {
				for (EClassifier alteredType : alteredType1) {
					if (alteredType == type2) {
						match = true;
						break;
					}
				}
			}
			if (!match && alteredType2 != null) {
				for (EClassifier alteredType : alteredType2) {
					if (alteredType == type1) {
						match = true;
						break;
					}
				}
			}
			if (!match) {
				return super.exactTypeMatch(type1, type2);
			}
			return true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#findOperationMatching(java.lang.Object, java.lang.String,
		 *      java.util.List)
		 */
		@Override
		public EOperation findOperationMatching(EClassifier owner, String name,
				List<? extends TypedElement<EClassifier>> args) {
			EOperationSignatureElement operationSignatureElement = new EOperationSignatureElement(owner,
					name, args);
			EOperation operation = eOperationCache.get(operationSignatureElement);

			if (operation == null) {
				List<? extends TypedElement<EClassifier>> arguments = args;
				if (arguments == null) {
					arguments = Collections.emptyList();
				}

				UMLReflection<?, EClassifier, EOperation, EStructuralFeature, ?, EParameter, ?, ?, ?, ?> uml = getEnvironment()
						.getUMLReflection();
				List<EOperation> operations = getOperations(owner, name);
				List<EOperation> matches = null;

				List<EOperation> perfectMatches = new ArrayList<EOperation>();

				for (EOperation oper : operations) {
					if (name.equals(uml.getName(oper))
							&& matchArgs(owner, uml.getParameters(oper), arguments)) {

						if (uml.getOwningClassifier(oper) == owner) {
							perfectMatches.add(oper);
						}

						if (matches == null) {
							// assume a small number of redefinitions
							matches = new java.util.ArrayList<EOperation>(3);
						}

						matches.add(oper);
					}
				}

				for (EOperation eOperation : perfectMatches) {
					if (eOperation.getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
						eOperationCache.put(operationSignatureElement, eOperation);
						return eOperation; // obviously the most specific definition
					}
				}

				if (perfectMatches.size() > 0) {
					operation = perfectMatches.get(0);
				} else {
					if (matches != null) {
						if (matches.size() == 1) {
							operation = matches.get(0);
						} else if (!matches.isEmpty()) {
							operation = mostSpecificRedefinition(matches, uml);
						}
					}
				}

				if (operation == null) {
					// special handling for null and invalid values, whose types conform
					// to all others
					OCLStandardLibrary<EClassifier> lib = getEnvironment().getOCLStandardLibrary();
					if ((owner == lib.getOclVoid()) || (owner == lib.getOclInvalid())) {
						operation = findOperationForVoidOrInvalid(owner, name, arguments);
					}
				}
				eOperationCache.put(operationSignatureElement, operation);
			}

			return operation;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#getOperations(java.lang.Object)
		 */
		@Override
		public List<EOperation> getOperations(EClassifier owner) {
			/*
			 * This will (probably) never be used again. OCL may call it, but #getOperations(EClassifier,
			 * String) is preferred.
			 */
			final List<EOperation> result = new ArrayList<EOperation>(super.getOperations(owner));
			if (!(owner instanceof PrimitiveType)) {
				result.addAll(getUMLReflection().getOperations(EcorePackage.eINSTANCE.getEObject()));
			}
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#getRelationship(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int getRelationship(EClassifier type1, EClassifier type2) {
			/*
			 * OCL tells us that a Collection is not an Object, we'll circumvent this limitation in Acceleo's
			 * case.
			 */
			EClassifier oclType1 = null;
			EClassifier oclType2 = null;
			if (type1 != null) {
				oclType1 = getEnvironment().getUMLReflection().getOCLType(type1);
			}
			if (type2 != null) {
				oclType2 = getEnvironment().getUMLReflection().getOCLType(type2);
			}

			int relationship;
			if (oclType1 == oclType2) {
				relationship = UMLReflection.SAME_TYPE;
			} else if (oclType1 != null
					&& org.eclipse.ocl.ecore.AnyType.class.isAssignableFrom(oclType1.getClass())) {
				relationship = UMLReflection.STRICT_SUPERTYPE;
			} else if (oclType2 != null
					&& org.eclipse.ocl.ecore.AnyType.class.isAssignableFrom(oclType2.getClass())) {
				relationship = UMLReflection.STRICT_SUBTYPE;
			} else {
				relationship = super.getRelationship(type1, type2);
			}

			// If everything fails, let's try to see if we don't have two instances of the same metatype.
			if (relationship == UMLReflection.UNRELATED_TYPE && oclType1 != null && oclType2 != null) {
				URI uri1 = EcoreUtil.getURI(oclType1);
				URI uri2 = EcoreUtil.getURI(oclType2);
				if (uri1 != null && uri1.equals(uri2)) {
					relationship = UMLReflection.SAME_TYPE;
				}
			}

			return relationship;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#getResultType(java.lang.Object, java.lang.Object,
		 *      java.lang.Object, java.util.List)
		 */
		@Override
		public EClassifier getResultType(Object problemObject, EClassifier owner, EOperation operation,
				List<? extends TypedElement<EClassifier>> args) {
			EClassifier type = super.getResultType(problemObject, owner, operation, args);
			if (operation.getEAnnotation("MTL non-standard") == null) { //$NON-NLS-1$
				return type;
			}

			final String operationName = operation.getName();
			// Handles all operations which can return a typed sequence as their result.
			if (args.size() > 0 && args.get(0) instanceof TypeExp) {
				boolean isParameterizedCollection = AcceleoNonStandardLibrary.OPERATION_EOBJECT_EALLCONTENTS
						.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_ECONTENTS.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_COLLECTION_FILTER.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_ANCESTORS.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_SIBLINGS.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_EINVERSE.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_PRECEDINGSIBLINGS
								.equals(operationName);
				isParameterizedCollection = isParameterizedCollection
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_FOLLOWINGSIBLINGS
								.equals(operationName);

				if (isParameterizedCollection) {
					final org.eclipse.ocl.ecore.CollectionType alteredSequence;
					if (type instanceof SequenceType || type instanceof OrderedSetType
							|| type instanceof BagType || type instanceof SetType) {
						alteredSequence = (org.eclipse.ocl.ecore.CollectionType)EcoreUtil.copy(type);
					} else if (owner instanceof org.eclipse.ocl.ecore.CollectionType) {
						alteredSequence = (org.eclipse.ocl.ecore.CollectionType)EcoreUtil.copy(owner);
					} else {
						alteredSequence = (org.eclipse.ocl.ecore.CollectionType)EcoreUtil.copy(type);
					}
					alteredSequence.setElementType(((TypeExp)args.get(0)).getReferredType());
					Set<EClassifier> altered = alteredTypes.get(type);
					if (altered == null) {
						altered = new CompactHashSet<EClassifier>();
						alteredTypes.put(type, altered);
					}
					altered.add(alteredSequence);
					type = alteredSequence;
				} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_CURRENT.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_EOBJECT_ECONTAINER.equals(operationName)) {
					type = ((TypeExp)args.get(0)).getReferredType();
				}
			} else if (args.size() > 0 && args.get(0) instanceof StringLiteralExp
					&& AcceleoNonStandardLibrary.OPERATION_EOBJECT_EGET.equals(operationName)) {
				final String featureName = ((StringLiteralExp)args.get(0)).getStringSymbol();

				EStructuralFeature feature = null;
				if (owner instanceof EClass) {
					for (EStructuralFeature childFeature : ((EClass)owner).getEAllStructuralFeatures()) {
						if (childFeature.getName().equals(featureName)) {
							feature = childFeature;
							break;
						}
					}
				}

				if (feature == null) {
					createSubTypesHierarchy(owner);

					feature = findFeatureInSubTypesHierarchy(owner, featureName);
				}

				if (feature != null) {
					type = inferTypeFromFeature(feature);
					final Long key = Long.valueOf(owner.hashCode() + featureName.hashCode());
					if (!hierarchyFeatureCache.containsKey(key)) {
						hierarchyFeatureCache.put(key, feature);
					}
				}
			} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_REVERSE.equals(operationName)) {
				// the special case of reverse
				// the return type is the same as the type of the owner
				final org.eclipse.ocl.ecore.CollectionType alteredSequence;
				if (type instanceof SequenceType || type instanceof OrderedSetType) {
					alteredSequence = (org.eclipse.ocl.ecore.CollectionType)EcoreUtil.copy(owner);
					Set<EClassifier> altered = alteredTypes.get(type);
					if (altered == null) {
						altered = new CompactHashSet<EClassifier>();
						alteredTypes.put(type, altered);
					}
					altered.add(alteredSequence);
					type = alteredSequence;
				}
			} else if (args.size() > 0) {
				boolean shouldBeConsidered = AcceleoNonStandardLibrary.OPERATION_COLLECTION_REMOVE_ALL
						.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_COLLECTION_ADD_ALL.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_COLLECTION_DROP.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_COLLECTION_DROP_RIGHT.equals(operationName);

				if (shouldBeConsidered) {
					// The special case of add all, remove all, drop and drop right
					// the return type is the same as the type of the owner
					final org.eclipse.ocl.ecore.CollectionType alteredSequence;
					alteredSequence = (org.eclipse.ocl.ecore.CollectionType)EcoreUtil.copy(owner);
					Set<EClassifier> altered = alteredTypes.get(type);
					if (altered == null) {
						altered = new CompactHashSet<EClassifier>();
						alteredTypes.put(type, altered);
					}
					altered.add(alteredSequence);
					type = alteredSequence;
				}
			}

			return type;
		}

		/**
		 * Gets rid of caches. This is set as protected to be accessible from the enclosing environment.
		 */
		protected void dispose() {
			for (Set<EClassifier> alteredTypesValuesSet : alteredTypes.values()) {
				alteredTypesValuesSet.clear();
			}
			for (Set<EClassifier> subTypesValuesSet : subTypes.values()) {
				subTypesValuesSet.clear();
			}
			alteredTypes.clear();
			subTypes.clear();
			hierarchyFeatureCache.clear();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#resolve(java.lang.Object)
		 */
		@Override
		protected EClassifier resolve(EClassifier type) {
			return getEnvironment().getTypeResolver().resolve(type);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#resolveCollectionType(org.eclipse.ocl.expressions.CollectionKind,
		 *      java.lang.Object)
		 */
		@Override
		protected CollectionType<EClassifier, EOperation> resolveCollectionType(CollectionKind kind,
				EClassifier elementType) {
			return getEnvironment().getTypeResolver().resolveCollectionType(kind, elementType);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.AbstractTypeChecker#resolveTupleType(org.eclipse.emf.common.util.EList)
		 */
		@Override
		protected TupleType<EOperation, EStructuralFeature> resolveTupleType(
				EList<? extends TypedElement<EClassifier>> parts) {
			return getEnvironment().getTypeResolver().resolveTupleType(parts);
		}

		/**
		 * Creates and stores the subtypes hierarchy of <code>classifier</code>.
		 * 
		 * @param classifier
		 *            The classifier we need the subtypes hierarchy of.
		 */
		private void createSubTypesHierarchy(EClassifier classifier) {
			if (subTypes.get(classifier) == null) {
				final Set<EClassifier> hierarchy = new CompactHashSet<EClassifier>();

				ECrossReferenceAdapter referencer = getCrossReferencer(classifier);
				for (EStructuralFeature.Setting setting : referencer.getInverseReferences(classifier, false)) {
					if (setting.getEStructuralFeature() == EcorePackage.eINSTANCE.getEClass_ESuperTypes()) {
						EClassifier subType = (EClassifier)setting.getEObject();
						hierarchy.add(subType);
						createSubTypesHierarchy(subType);
					}
				}

				subTypes.put(classifier, hierarchy);
			}
		}

		/**
		 * Goes down the <code>base</code> classifier's subtypes hierachy in search of a feature named
		 * <code>featureName</code> and returns it.
		 * 
		 * @param base
		 *            The starting point of the hierachy lookup.
		 * @param featureName
		 *            Name of the sought feature.
		 * @return The feature named <code>featureName</code> in <code>base</code>'s hierarchy.
		 */
		private EStructuralFeature findFeatureInSubTypesHierarchy(EClassifier base, String featureName) {
			EStructuralFeature feature = null;
			for (EClassifier subType : subTypes.get(base)) {
				feature = findFeatureInType(subType, featureName);
				if (feature == null) {
					feature = findFeatureInSubTypesHierarchy(subType, featureName);
				}
				if (feature != null) {
					break;
				}
			}
			return feature;
		}

		/**
		 * Searches the given type for a feature named <code>featureName</code>.
		 * 
		 * @param type
		 *            The type in which to search the feature.
		 * @param featureName
		 *            Name of the sought feature.
		 * @return The feature named <code>featureName</code> in <code>type</code> if it exists,
		 *         <code>null</code> otherwise.
		 */
		private EStructuralFeature findFeatureInType(EClassifier type, String featureName) {
			final Long key = Long.valueOf(type.hashCode() + featureName.hashCode());
			if (hierarchyFeatureCache.containsKey(key)) {
				return hierarchyFeatureCache.get(key);
			}

			EStructuralFeature feature = null;
			for (EObject child : type.eContents()) {
				if (child instanceof EStructuralFeature
						&& ((EStructuralFeature)child).getName().equals(featureName)) {
					feature = (EStructuralFeature)child;
					hierarchyFeatureCache.put(key, feature);
					break;
				}
			}

			return feature;
		}

		/**
		 * The <tt>OclVoid</tt> and <tt>OclInvalid</tt> types are defined as conforming to all other types.
		 * Therefore, we can try a little harder to match certain operations that it is useful to support,
		 * such as <tt>{@literal =}</tt> and <tt>{@literal <>}</tt>.
		 * 
		 * @param owner
		 *            the classifier to search (void or invalid)
		 * @param name
		 *            the name of the operation
		 * @param args
		 *            a list of arguments to match against the operation signature, as either expressions or
		 *            variables
		 * @return the matching operation, or <code>null</code> if not found
		 */
		private EOperation findOperationForVoidOrInvalid(EClassifier owner, String name,
				List<? extends TypedElement<EClassifier>> args) {

			EOperation result = null;

			if (args.size() == 1) {
				EClassifier argType = args.get(0).getType();

				if (argType != owner) {
					// let us search the type of the argument to determine whether
					// we can find this operation
					result = findOperationMatching(argType, name, args);
				}
			}

			return result;
		}

		/**
		 * This will retrieve (and create if needed) a cross referencer adapter for the resource
		 * <code>scope</code> is in.
		 * 
		 * @param scope
		 *            Object that will serve as the scope of our new cross referencer.
		 * @return The <code>scope</code>'s resource cross referencer.
		 */
		private ECrossReferenceAdapter getCrossReferencer(EObject scope) {
			ECrossReferenceAdapter referencer = null;
			for (Adapter adapter : scope.eResource().eAdapters()) {
				if (adapter instanceof ECrossReferenceAdapter) {
					referencer = (ECrossReferenceAdapter)adapter;
					break;
				}
			}
			if (referencer == null) {
				referencer = new ECrossReferenceAdapter();
				scope.eResource().eAdapters().add(referencer);
			}
			return referencer;
		}

		/**
		 * Obtains the implicit root class specified as an option in the environment, if it is specified and
		 * it is a class.
		 * <p>
		 * Copied from org.eclipse.ocl.AbstractTypeChecker.
		 * </p>
		 * 
		 * @return The implicit root class, if any.
		 * @see org.eclipse.ocl.AbstractTypeChecker#getImplicitRootClass()
		 */
		private EClassifier getImplicitRootClass() {
			EClassifier result = null;

			Object value = ParsingOptions.getValue(getEnvironment(), ParsingOptions
					.implicitRootClass(getEnvironment()));
			if (value instanceof EClassifier) {
				result = (EClassifier)value;
			}

			// check that, if there is a value for this option, it is a class
			if ((result != null) && !getEnvironment().getUMLReflection().isClass(result)) {
				result = null;
			}

			return result;
		}

		/**
		 * This will return the list of EOperations going by the given name on the given classifier. This
		 * should be preferred to {@link #getOperations(EClassifier)} at all times.
		 * 
		 * @param owner
		 *            The classifier on which to seek EOperations.
		 * @param name
		 *            The name filter for the classifier's operations.
		 * @return The list of EOperations going by the given name on the given classifier.
		 */
		private List<EOperation> getOperations(EClassifier owner, String name) {
			final List<EOperation> result;

			if (owner instanceof TypeType<?, ?>) {
				@SuppressWarnings("unchecked")
				final TypeType<EClassifier, EOperation> source = (TypeType<EClassifier, EOperation>)owner;
				return getTypeTypeOperations(source, name);
			}
			if (owner instanceof PredefinedType<?>) {
				@SuppressWarnings("unchecked")
				final PredefinedType<EOperation> source = (PredefinedType<EOperation>)owner;
				result = getPredefinedTypeOperations(source, name);
			} else {
				// it's a user type. Try to convert it to an OCL standard type
				EClassifier oclTypeOwner = getUMLReflection().asOCLType(owner);

				if (oclTypeOwner instanceof PredefinedType<?>) {
					@SuppressWarnings("unchecked")
					final PredefinedType<EOperation> source = (PredefinedType<EOperation>)oclTypeOwner;
					result = getPredefinedTypeOperations(source, name);
				} else {
					result = getUserTypeOperations(oclTypeOwner, name);
				}
			}

			if (getEnvironment() instanceof AcceleoEnvironment) {
				final List<EOperation> additionalOperations = ((AcceleoEnvironment)getEnvironment())
						.getAdditionalOperations(owner, name);
				result.addAll(additionalOperations);
			} else {
				final List<EOperation> additionalOperations = getEnvironment().getAdditionalOperations(owner);
				if (additionalOperations != null) {
					for (EOperation candidate : additionalOperations) {
						if (name.equals(candidate.getName())) {
							result.add(candidate);
						}
					}
				}
			}

			return result;
		}

		/**
		 * Returns the list of EOperations going by the given name on the given OCL PredefinedType.
		 * 
		 * @param owner
		 *            The classifier on which to seek EOperations.
		 * @param name
		 *            The name filter for the classifier's operations.
		 * @return The list of EOperations going by the given name on the given classifier.
		 */
		private List<EOperation> getPredefinedTypeOperations(PredefinedType<EOperation> owner, String name) {
			final List<EOperation> result = new ArrayList<EOperation>();

			if (owner instanceof AnyType<?> && RELATIONAL_OPERATORS.contains(name)) {
				return result;
			}

			final List<EOperation> candidates = owner.oclOperations();

			for (EOperation candidate : candidates) {
				if (name.equals(candidate.getName())) {
					result.add(candidate);
				}
			}

			return result;
		}

		/**
		 * Returns the list of EOperations going by the given name on the given OCL TypeType.
		 * 
		 * @param owner
		 *            The classifier on which to seek EOperations.
		 * @param name
		 *            The name filter for the classifier's operations.
		 * @return The list of EOperations going by the given name on the given classifier.
		 */
		private List<EOperation> getTypeTypeOperations(TypeType<EClassifier, EOperation> owner, String name) {
			final List<EOperation> result = new ArrayList<EOperation>();
			final List<EOperation> candidates = owner.oclOperations();

			for (EOperation candidate : candidates) {
				if (name.equals(candidate.getName())) {
					result.add(candidate);
				}
			}

			// also include the static operations of the referred type
			for (EOperation operation : getOperations(owner.getReferredType(), name)) {
				if (getUMLReflection().isStatic(operation)) {
					result.add(operation);
				}
			}

			return result;
		}

		/**
		 * Returns the list of EOperations going by the given name on the given classifier (known as being a
		 * user type, i.e not a predefined OCL type).
		 * 
		 * @param owner
		 *            The classifier on which to seek EOperations.
		 * @param name
		 *            The name filter for the classifier's operations.
		 * @return The list of EOperations going by the given name on the given classifier.
		 */
		private List<EOperation> getUserTypeOperations(EClassifier owner, String name) {
			final List<EOperation> result = new ArrayList<EOperation>();

			final Iterable<EOperation> userTypeCandidates = getUMLReflection().getOperations(owner);
			final Iterable<EOperation> oclAnyCandidates = getOperations(getOCLStandardLibrary().getOclAny(),
					name);

			final Iterable<EOperation> candidates;

			EClassifier implictBaseClassifier = getImplicitRootClass();
			if (implictBaseClassifier != null && implictBaseClassifier != owner) {
				final Iterable<EOperation> implicitRootCandidates = getUMLReflection().getOperations(
						implictBaseClassifier);
				candidates = Iterables.concat(userTypeCandidates, oclAnyCandidates, implicitRootCandidates);
			} else {
				candidates = Iterables.concat(userTypeCandidates, oclAnyCandidates);
			}

			for (EOperation candidate : candidates) {
				if (name.equals(candidate.getName())) {
					result.add(candidate);
				}
			}

			return result;
		}

		/**
		 * Tries and determine the static type of the given <code>feature</code>'s value.
		 * 
		 * @param feature
		 *            Feature we need a static type of.
		 * @return The determined type for this feature.
		 */
		@SuppressWarnings("unchecked")
		private EClassifier inferTypeFromFeature(EStructuralFeature feature) {
			EClassifier type = feature.getEType();
			// FIXME handle lists
			if (feature.isMany()) {
				if (feature.isOrdered() && feature.isUnique()) {
					type = EcoreFactory.eINSTANCE.createOrderedSetType();
				} else if (feature.isOrdered() && !feature.isUnique()) {
					type = EcoreFactory.eINSTANCE.createSequenceType();
				} else if (!feature.isOrdered() && feature.isUnique()) {
					type = EcoreFactory.eINSTANCE.createSetType();
				} else {
					type = EcoreFactory.eINSTANCE.createBagType();
				}
				((CollectionType<EClassifier, EOperation>)type).setElementType(feature.getEType());
			}
			return type;
		}

		/**
		 * Finds the most specific redefinition in a given list of features from a classifier hierarchy.
		 * 
		 * @param <F>
		 *            The generic type for the feature
		 * @param features
		 *            the definitions of a feature; must have at least one element
		 * @param uml
		 *            the UML introspector to use in determining the classifiers that define the various
		 *            feature definitions
		 * @return the most specific redefinition of the list of features
		 */
		private <F> F mostSpecificRedefinition(List<? extends F> features,
				UMLReflection<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, ?> uml) {

			Map<EClassifier, F> redefinitions = new java.util.HashMap<EClassifier, F>();

			for (F next : features) {
				redefinitions.put(uml.getOwningClassifier(next), next);
			}

			List<EClassifier> classifiers = new java.util.ArrayList<EClassifier>(redefinitions.keySet());

			// remove all classifiers that are ancestors of another classifier
			// in the map
			outer: while (true) {
				for (EClassifier next : classifiers) {
					if (classifiers.removeAll(uml.getAllSupertypes(next))) {
						continue outer; // don't want a concurrent modification
					}
				}

				break outer;
			}

			// there will at least be one remaining
			return redefinitions.get(classifiers.get(0));
		}

		/**
		 * An utility class to store the data used to find an operation.
		 * 
		 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
		 */
		private class EOperationSignatureElement {

			/**
			 * The arguments of the operation.
			 */
			private List<? extends TypedElement<EClassifier>> arguments;

			/**
			 * The owner of the operation.
			 */
			private EClassifier classifier;

			/**
			 * The name of the operation.
			 */
			private String name;

			/**
			 * The constructor.
			 * 
			 * @param classifier
			 *            The owner of the operation.
			 * @param name
			 *            The name of the operation.
			 * @param arguments
			 *            The arguments of the operation.
			 */
			public EOperationSignatureElement(EClassifier classifier, String name,
					List<? extends TypedElement<EClassifier>> arguments) {
				super();
				this.classifier = classifier;
				this.name = name;
				this.arguments = arguments;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see java.lang.Object#equals(java.lang.Object)
			 */
			@Override
			public boolean equals(Object arg0) {
				if (arg0 instanceof EOperationSignatureElement) {
					EOperationSignatureElement operationSignatureElement = (EOperationSignatureElement)arg0;
					boolean result = operationSignatureElement.getArguments().equals(arguments);
					result = result && operationSignatureElement.getName().equals(name);
					result = result && operationSignatureElement.getClassifier().equals(classifier);
					return result;
				}
				return false;
			}

			/**
			 * Returns the arguments.
			 * 
			 * @return the arguments
			 */
			public List<? extends TypedElement<EClassifier>> getArguments() {
				return arguments;
			}

			/**
			 * Returns the classifier.
			 * 
			 * @return the classifier
			 */
			public EClassifier getClassifier() {
				return classifier;
			}

			/**
			 * Returns the name.
			 * 
			 * @return the name
			 */
			public String getName() {
				return name;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see java.lang.Object#hashCode()
			 */
			@Override
			public int hashCode() {
				// CHECKSTYLE:OFF
				int hashcode = name.hashCode() * 9;
				hashcode += classifier.hashCode() * 13;
				hashcode += arguments.hashCode() * 17;
				// CHECKSTYLE:ON
				return hashcode;
			}

		}
	}
}
