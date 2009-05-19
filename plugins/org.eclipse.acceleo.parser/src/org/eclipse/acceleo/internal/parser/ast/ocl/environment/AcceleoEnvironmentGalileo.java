/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.AbstractTypeChecker;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.TypeChecker;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.ecore.SequenceType;
import org.eclipse.ocl.ecore.TypeExp;
import org.eclipse.ocl.expressions.CollectionKind;
import org.eclipse.ocl.types.CollectionType;
import org.eclipse.ocl.types.TupleType;
import org.eclipse.ocl.utilities.TypedElement;

/**
 * This class will not compile under Eclipse Ganymede with OCL 1.2 installed. It requires OCL 1.3 and
 * shouldn't be called or instantiated under previous versions.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironmentGalileo extends AcceleoEnvironment {
	/**
	 * Delegates instantiation to the super constructor.
	 * 
	 * @param parent
	 *            Parent for this Acceleo environment.
	 */
	protected AcceleoEnvironmentGalileo(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		super(parent);
		// FIXME LGO setOption(ParsingOptions.USE_BACKSLASH_ESCAPE_PROCESSING, Boolean.TRUE);
	}

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	protected AcceleoEnvironmentGalileo(Resource oclEnvironmentResource) {
		super(oclEnvironmentResource);
		// FIXME LGO setOption(ParsingOptions.USE_BACKSLASH_ESCAPE_PROCESSING, Boolean.TRUE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironment#createTypeChecker()
	 */
	@Override
	protected TypeChecker<EClassifier, EOperation, EStructuralFeature> createTypeChecker() {
		return new AcceleoTypeChecker(this);
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
		 * @see org.eclipse.ocl.AbstractTypeChecker#getResultType(java.lang.Object, java.lang.Object,
		 *      java.lang.Object, java.util.List)
		 */
		@Override
		public EClassifier getResultType(Object problemObject, EClassifier owner, EOperation operation,
				List<? extends TypedElement<EClassifier>> args) {
			EClassifier type = super.getResultType(problemObject, owner, operation, args);
			if (args.size() > 0 && operation.getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
				final String operationName = operation.getName();
				final boolean isParameterizedOperation = AcceleoNonStandardLibrary.OPERATION_OCLANY_EALLCONTENTS
						.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_OCLANY_ANCESTORS.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_OCLANY_SIBLINGS.equals(operationName)
						|| AcceleoNonStandardLibrary.OPERATION_OCLANY_EINVERSE.equals(operationName);
				if (isParameterizedOperation && args.get(0) instanceof TypeExp) {
					final SequenceType alteredSequence = (SequenceType)EcoreUtil.copy(type);
					alteredSequence.setElementType(((TypeExp)args.get(0)).getReferredType());
					Set<EClassifier> altered = alteredTypes.get(type);
					if (altered == null) {
						altered = new HashSet<EClassifier>();
						alteredTypes.put(type, altered);
					}
					altered.add(alteredSequence);
					type = alteredSequence;
				}
			}
			return type;
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
	}
}
