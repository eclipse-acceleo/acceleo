/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultFactory;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EvaluationResultPackageImpl extends EPackageImpl implements EvaluationResultPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oclElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oclResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum severityEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType evaluationResultEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EvaluationResultPackageImpl() {
		super(eNS_URI, EvaluationResultFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EvaluationResultPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EvaluationResultPackage init() {
		if (isInited)
			return (EvaluationResultPackage)EPackage.Registry.INSTANCE
					.getEPackage(EvaluationResultPackage.eNS_URI);

		// Obtain or create and register package
		EvaluationResultPackageImpl theEvaluationResultPackage = (EvaluationResultPackageImpl)(EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof EvaluationResultPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new EvaluationResultPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEvaluationResultPackage.createPackageContents();

		// Initialize created meta-data
		theEvaluationResultPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEvaluationResultPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EvaluationResultPackage.eNS_URI, theEvaluationResultPackage);
		return theEvaluationResultPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOCLElement() {
		return oclElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOCLElement_Element() {
		return (EReference)oclElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOCLElement_Children() {
		return (EReference)oclElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraintElement() {
		return constraintElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintElement_ConstraintResults() {
		return (EReference)constraintElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationElement() {
		return operationElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationElement_EvaluationResults() {
		return (EReference)operationElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOCLResult() {
		return oclResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOCLResult_EvaluationTarget() {
		return (EReference)oclResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOCLResult_InterpreterResult() {
		return (EAttribute)oclResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEvaluationResult() {
		return evaluationResultEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraintResult() {
		return constraintResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstraintResult_Message() {
		return (EAttribute)constraintResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstraintResult_Severity() {
		return (EAttribute)constraintResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSeverity() {
		return severityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResultFactory getEvaluationResultFactory() {
		return (EvaluationResultFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		oclElementEClass = createEClass(OCL_ELEMENT);
		createEReference(oclElementEClass, OCL_ELEMENT__ELEMENT);
		createEReference(oclElementEClass, OCL_ELEMENT__CHILDREN);

		constraintElementEClass = createEClass(CONSTRAINT_ELEMENT);
		createEReference(constraintElementEClass, CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS);

		operationElementEClass = createEClass(OPERATION_ELEMENT);
		createEReference(operationElementEClass, OPERATION_ELEMENT__EVALUATION_RESULTS);

		oclResultEClass = createEClass(OCL_RESULT);
		createEReference(oclResultEClass, OCL_RESULT__EVALUATION_TARGET);
		createEAttribute(oclResultEClass, OCL_RESULT__INTERPRETER_RESULT);

		constraintResultEClass = createEClass(CONSTRAINT_RESULT);
		createEAttribute(constraintResultEClass, CONSTRAINT_RESULT__MESSAGE);
		createEAttribute(constraintResultEClass, CONSTRAINT_RESULT__SEVERITY);

		// Create enums
		severityEEnum = createEEnum(SEVERITY);

		// Create data types
		evaluationResultEDataType = createEDataType(EVALUATION_RESULT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		constraintElementEClass.getESuperTypes().add(this.getOCLElement());
		operationElementEClass.getESuperTypes().add(this.getOCLElement());
		constraintResultEClass.getESuperTypes().add(this.getOCLResult());

		// Initialize classes and features; add operations and parameters
		initEClass(oclElementEClass, OCLElement.class,
				"OCLElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getOCLElement_Element(),
				theEcorePackage.getEObject(),
				null,
				"element", null, 1, 1, OCLElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getOCLElement_Children(),
				this.getOCLElement(),
				null,
				"children", null, 0, -1, OCLElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(oclElementEClass, this.getSeverity(), "getWorstSeverity", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(constraintElementEClass, ConstraintElement.class,
				"ConstraintElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getConstraintElement_ConstraintResults(),
				this.getConstraintResult(),
				null,
				"constraintResults", null, 0, -1, ConstraintElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(operationElementEClass, OperationElement.class,
				"OperationElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getOperationElement_EvaluationResults(),
				this.getOCLResult(),
				null,
				"evaluationResults", null, 0, -1, OperationElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(oclResultEClass, OCLResult.class,
				"OCLResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getOCLResult_EvaluationTarget(),
				theEcorePackage.getEObject(),
				null,
				"evaluationTarget", null, 1, 1, OCLResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getOCLResult_InterpreterResult(),
				this.getEvaluationResult(),
				"interpreterResult", null, 1, 1, OCLResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(constraintResultEClass, ConstraintResult.class,
				"ConstraintResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getConstraintResult_Message(),
				theEcorePackage.getEString(),
				"message", null, 0, 1, ConstraintResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getConstraintResult_Severity(),
				this.getSeverity(),
				"severity", null, 1, 1, ConstraintResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(severityEEnum, Severity.class, "Severity"); //$NON-NLS-1$
		addEEnumLiteral(severityEEnum, Severity.OK);
		addEEnumLiteral(severityEEnum, Severity.INFO);
		addEEnumLiteral(severityEEnum, Severity.WARNING);
		addEEnumLiteral(severityEEnum, Severity.ERROR);

		// Initialize data types
		initEDataType(evaluationResultEDataType, EvaluationResult.class,
				"EvaluationResult", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //EvaluationResultPackageImpl
