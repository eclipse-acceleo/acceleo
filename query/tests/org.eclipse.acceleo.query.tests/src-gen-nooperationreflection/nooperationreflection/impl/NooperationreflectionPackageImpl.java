/**
 */
package nooperationreflection.impl;

import nooperationreflection.NoOperationReflection;
import nooperationreflection.NooperationreflectionFactory;
import nooperationreflection.NooperationreflectionPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NooperationreflectionPackageImpl extends EPackageImpl implements NooperationreflectionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass noOperationReflectionEClass = null;

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
	 * @see nooperationreflection.NooperationreflectionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NooperationreflectionPackageImpl() {
		super(eNS_URI, NooperationreflectionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NooperationreflectionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NooperationreflectionPackage init() {
		if (isInited) return (NooperationreflectionPackage)EPackage.Registry.INSTANCE.getEPackage(NooperationreflectionPackage.eNS_URI);

		// Obtain or create and register package
		NooperationreflectionPackageImpl theNooperationreflectionPackage = (NooperationreflectionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NooperationreflectionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NooperationreflectionPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theNooperationreflectionPackage.createPackageContents();

		// Initialize created meta-data
		theNooperationreflectionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNooperationreflectionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NooperationreflectionPackage.eNS_URI, theNooperationreflectionPackage);
		return theNooperationreflectionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNoOperationReflection() {
		return noOperationReflectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NooperationreflectionFactory getNooperationreflectionFactory() {
		return (NooperationreflectionFactory)getEFactoryInstance();
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		noOperationReflectionEClass = createEClass(NO_OPERATION_REFLECTION);
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(noOperationReflectionEClass, NoOperationReflection.class, "NoOperationReflection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = addEOperation(noOperationReflectionEClass, ecorePackage.getEString(), "eOperationNoReflection", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "message", 1, 1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //NooperationreflectionPackageImpl
