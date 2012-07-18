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
package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.utilities.ExpressionInOCL;
import org.eclipse.ocl.utilities.TypedElement;

/**
 * This implementation of an {@link org.eclipse.ocl.utilities.UMLReflection} allows us to remove the method
 * {@link org.eclipse.emf.ecore.EObject#eAllContents()} from the context.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoUMLReflection implements org.eclipse.ocl.utilities.UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> {
	/** Keeps a reference to the {@link org.eclipse.emf.ecore.EObject#eAllContents()} method. */
	private static final EOperation EOBJECT_EALLCONTENTS;

	/** We will delegate all calls to this implementation. */
	protected final org.eclipse.ocl.utilities.UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> delegate;

	static {
		EOperation temp = null;
		final EClass eObject = EcorePackage.eINSTANCE.getEObject();
		for (EOperation operation : eObject.getEOperations()) {
			if ("eAllContents".equals(operation.getName()) //$NON-NLS-1$
					&& operation.getEType() == EcorePackage.eINSTANCE.getETreeIterator()) {
				temp = operation;
				break;
			}
		}
		EOBJECT_EALLCONTENTS = temp;
	}

	/**
	 * Instantiates an UML Reflection given the one to which all calls are to be redirected.
	 * 
	 * @param delegate
	 *            The UML Reflection to which calls are to be redirected.
	 */
	public AcceleoUMLReflection(
			org.eclipse.ocl.utilities.UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> delegate) {
		super();
		this.delegate = delegate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#asOCLType(java.lang.Object)
	 */
	public EClassifier asOCLType(EClassifier modelType) {
		return delegate.asOCLType(modelType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#createCallOperationAction(java.lang.Object)
	 */
	public CallOperationAction createCallOperationAction(EOperation operation) {
		return delegate.createCallOperationAction(operation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#createConstraint()
	 */
	public Constraint createConstraint() {
		return delegate.createConstraint();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#createExpressionInOCL()
	 */
	public ExpressionInOCL<EClassifier, EParameter> createExpressionInOCL() {
		return delegate.createExpressionInOCL();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#createOperation(java.lang.String, java.lang.Object,
	 *      java.util.List, java.util.List)
	 */
	public EOperation createOperation(String name, EClassifier resultType, List<String> paramNames,
			List<EClassifier> paramTypes) {
		return delegate.createOperation(name, resultType, paramNames, paramTypes);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#createProperty(java.lang.String, java.lang.Object)
	 */
	public EStructuralFeature createProperty(String name, EClassifier resultType) {
		return delegate.createProperty(name, resultType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#createSendSignalAction(java.lang.Object)
	 */
	public SendSignalAction createSendSignalAction(EClassifier signal) {
		return delegate.createSendSignalAction(signal);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getAllSupertypes(java.lang.Object)
	 */
	public Collection<? extends EClassifier> getAllSupertypes(EClassifier classifier) {
		return delegate.getAllSupertypes(classifier);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getAssociationClass(java.lang.Object)
	 */
	public EClassifier getAssociationClass(EStructuralFeature property) {
		return delegate.getAssociationClass(property);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getAttributes(java.lang.Object)
	 */
	public List<EStructuralFeature> getAttributes(EClassifier classifier) {
		return delegate.getAttributes(classifier);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getClassifiers(java.lang.Object)
	 */
	public List<EClassifier> getClassifiers(EPackage pkg) {
		return delegate.getClassifiers(pkg);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getCommonSuperType(java.lang.Object, java.lang.Object)
	 */
	public EClassifier getCommonSuperType(EClassifier type1, EClassifier type2) {
		return delegate.getCommonSuperType(type1, type2);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getConstrainedElements(java.lang.Object)
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	/*
	 * !Do not use generics on the return type of this override, API broke between OCL 1.2 and 3.0 and we
	 * can't cope with both while maintaining a generic signature!
	 */
	public List getConstrainedElements(Constraint constraint) {
		return delegate.getConstrainedElements(constraint);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getConstraint(org.eclipse.ocl.utilities.ExpressionInOCL)
	 */
	public Constraint getConstraint(ExpressionInOCL<EClassifier, EParameter> specification) {
		return delegate.getConstraint(specification);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getConstraintName(java.lang.Object)
	 */
	public String getConstraintName(Constraint constraint) {
		return delegate.getConstraintName(constraint);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getDescription(java.lang.Object)
	 */
	public String getDescription(Object namedElement) {
		return delegate.getDescription(namedElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getEnumeration(java.lang.Object)
	 */
	public EClassifier getEnumeration(EEnumLiteral enumerationLiteral) {
		return delegate.getEnumeration(enumerationLiteral);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getEnumerationLiteral(java.lang.Object, java.lang.String)
	 */
	public EEnumLiteral getEnumerationLiteral(EClassifier enumerationType, String literalName) {
		return delegate.getEnumerationLiteral(enumerationType, literalName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getEnumerationLiterals(java.lang.Object)
	 */
	public List<EEnumLiteral> getEnumerationLiterals(EClassifier enumerationType) {
		return delegate.getEnumerationLiterals(enumerationType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getMemberEnds(java.lang.Object)
	 */
	public List<EStructuralFeature> getMemberEnds(EClassifier associationClass) {
		return delegate.getMemberEnds(associationClass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getName(java.lang.Object)
	 */
	public String getName(Object namedElement) {
		return delegate.getName(namedElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getNestedPackages(java.lang.Object)
	 */
	public List<EPackage> getNestedPackages(EPackage pkg) {
		return delegate.getNestedPackages(pkg);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getNestingPackage(java.lang.Object)
	 */
	public EPackage getNestingPackage(EPackage pkg) {
		return delegate.getNestingPackage(pkg);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getOCLType(java.lang.Object)
	 */
	public EClassifier getOCLType(Object metaElement) {
		return delegate.getOCLType(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getOperation(java.lang.Object)
	 */
	public EOperation getOperation(CallOperationAction callOperationAction) {
		return delegate.getOperation(callOperationAction);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getOperations(java.lang.Object)
	 */
	public synchronized List<EOperation> getOperations(EClassifier classifier) {
		final List<EOperation> operations = new ArrayList<EOperation>(delegate.getOperations(classifier));
		operations.remove(EOBJECT_EALLCONTENTS);
		return operations;
	}

	/**
	 * Obtains all of the operations going by the given name that are defined by the specified classifier.
	 * This should always be preferred to {@link #getOperations(EClassifier)}.
	 * <p>
	 * Base implementation copied from org.eclipse.ocl.utilities.UMLReflection.
	 * </p>
	 * 
	 * @param classifier
	 *            A classifier in the model.
	 * @param name
	 *            The name filter for the classifier's operations.
	 * @return The operations applicable to the specified classifier, or an empty list if none.
	 * @see org.eclipse.ocl.utilities.UMLReflection#getOperations(EClassifier)
	 */
	public synchronized List<EOperation> getOperations(EClassifier classifier, String name) {
		final List<EOperation> result = new ArrayList<EOperation>();

		if (classifier instanceof EClass) {
			final List<EOperation> candidates = ((EClass)classifier).getEOperations();
			for (EOperation candidate : candidates) {
				if (name != null && candidate != null && name.equals(candidate.getName())) {
					result.add(candidate);
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getOwningClassifier(java.lang.Object)
	 */
	public EClassifier getOwningClassifier(Object feature) {
		return delegate.getOwningClassifier(feature);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getPackage(java.lang.Object)
	 */
	public EPackage getPackage(EClassifier classifier) {
		return delegate.getPackage(classifier);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getParameters(java.lang.Object)
	 */
	public List<EParameter> getParameters(EOperation operation) {
		return delegate.getParameters(operation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getQualifiedName(java.lang.Object)
	 */
	public String getQualifiedName(Object namedElement) {
		return delegate.getQualifiedName(namedElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getQualifiers(java.lang.Object)
	 */
	public List<EStructuralFeature> getQualifiers(EStructuralFeature property) {
		return delegate.getQualifiers(property);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getRelationship(java.lang.Object, java.lang.Object)
	 */
	public int getRelationship(EClassifier type1, EClassifier type2) {
		return delegate.getRelationship(type1, type2);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getSignal(java.lang.Object)
	 */
	public EClassifier getSignal(SendSignalAction sendSignalAction) {
		return delegate.getSignal(sendSignalAction);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getSignals(java.lang.Object)
	 */
	public List<EClassifier> getSignals(EClassifier owner) {
		return delegate.getSignals(owner);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getSpecification(java.lang.Object)
	 */
	public ExpressionInOCL<EClassifier, EParameter> getSpecification(Constraint constraint) {
		return delegate.getSpecification(constraint);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getStereotype(java.lang.Object)
	 */
	public String getStereotype(Constraint constraint) {
		return delegate.getStereotype(constraint);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#getStereotypeApplication(java.lang.Object,
	 *      java.lang.Object)
	 */
	public Object getStereotypeApplication(Object baseElement, EClassifier stereotype) {
		return delegate.getStereotypeApplication(baseElement, stereotype);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isAssociationClass(java.lang.Object)
	 */
	public boolean isAssociationClass(EClassifier type) {
		return delegate.isAssociationClass(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isClass(java.lang.Object)
	 */
	public boolean isClass(Object metaElement) {
		return delegate.isClass(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isClassifier(java.lang.Object)
	 */
	public boolean isClassifier(Object metaElement) {
		return delegate.isClassifier(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isComparable(java.lang.Object)
	 */
	public boolean isComparable(EClassifier type) {
		return delegate.isComparable(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isConstraint(java.lang.Object)
	 */
	public boolean isConstraint(Object metaElement) {
		// should never be called as it appeared in helios and will then be overriden
		assert false;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isDataType(java.lang.Object)
	 */
	public boolean isDataType(Object metaElement) {
		return delegate.isDataType(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isEnumeration(java.lang.Object)
	 */
	public boolean isEnumeration(EClassifier type) {
		return delegate.isEnumeration(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isMany(java.lang.Object)
	 */
	public boolean isMany(Object metaElement) {
		return delegate.isMany(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isOperation(java.lang.Object)
	 */
	public boolean isOperation(Object metaElement) {
		return delegate.isOperation(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isPackage(java.lang.Object)
	 */
	public boolean isPackage(Object metaElement) {
		// should never be called as it appeared in helios and will then be overriden
		assert false;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isProperty(java.lang.Object)
	 */
	public boolean isProperty(Object metaElement) {
		return delegate.isProperty(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isQuery(java.lang.Object)
	 */
	public boolean isQuery(EOperation operation) {
		return delegate.isQuery(operation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isStatic(java.lang.Object)
	 */
	public boolean isStatic(Object feature) {
		return delegate.isStatic(feature);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#isStereotype(java.lang.Object)
	 */
	public boolean isStereotype(EClassifier type) {
		return delegate.isStereotype(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#setConstraintName(java.lang.Object, java.lang.String)
	 */
	public void setConstraintName(Constraint constraint, String name) {
		delegate.setConstraintName(constraint, name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#setIsStatic(java.lang.Object, boolean)
	 */
	public boolean setIsStatic(Object feature, boolean isStatic) {
		// should never be called as it appeared in helios and will then be overriden
		assert false;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#setName(org.eclipse.ocl.utilities.TypedElement,
	 *      java.lang.String)
	 */
	public void setName(TypedElement<EClassifier> element, String name) {
		delegate.setName(element, name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#setSpecification(java.lang.Object,
	 *      org.eclipse.ocl.utilities.ExpressionInOCL)
	 */
	public void setSpecification(Constraint constraint, ExpressionInOCL<EClassifier, EParameter> specification) {
		delegate.setSpecification(constraint, specification);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#setStereotype(java.lang.Object, java.lang.String)
	 */
	public void setStereotype(Constraint constraint, String stereotype) {
		delegate.setStereotype(constraint, stereotype);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#setType(org.eclipse.ocl.utilities.TypedElement,
	 *      java.lang.Object)
	 */
	public void setType(TypedElement<EClassifier> element, EClassifier type) {
		delegate.setType(element, type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#addConstrainedElement(java.lang.Object,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void addConstrainedElement(Constraint constraint, EObject constrainedElement) {
		// should never be called as it appeared in helios and will then be overriden
		assert false;
	}
}
