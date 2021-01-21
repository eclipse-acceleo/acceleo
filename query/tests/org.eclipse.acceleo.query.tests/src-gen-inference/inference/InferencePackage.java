/**
 */
package inference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see inference.InferenceFactory
 * @model kind="package"
 * @generated
 */
public interface InferencePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "inference";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/inference";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "inference";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	InferencePackage eINSTANCE = inference.impl.InferencePackageImpl.init();

	/**
	 * The meta object id for the '{@link inference.impl.OImpl <em>O</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.OImpl
	 * @see inference.impl.InferencePackageImpl#getO()
	 * @generated
	 */
	int O = 0;

	/**
	 * The number of structural features of the '<em>O</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int O_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>O</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int O_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link inference.impl.AImpl <em>A</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.AImpl
	 * @see inference.impl.InferencePackageImpl#getA()
	 * @generated
	 */
	int A = 1;

	/**
	 * The number of structural features of the '<em>A</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int A_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>A</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int A_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link inference.impl.BImpl <em>B</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.BImpl
	 * @see inference.impl.InferencePackageImpl#getB()
	 * @generated
	 */
	int B = 2;

	/**
	 * The number of structural features of the '<em>B</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B_FEATURE_COUNT = A_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>B</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int B_OPERATION_COUNT = A_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link inference.impl.CImpl <em>C</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.CImpl
	 * @see inference.impl.InferencePackageImpl#getC()
	 * @generated
	 */
	int C = 3;

	/**
	 * The feature id for the '<em><b>CAttr</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C__CATTR = B_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>C</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C_FEATURE_COUNT = B_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>C</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int C_OPERATION_COUNT = B_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link inference.impl.XImpl <em>X</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.XImpl
	 * @see inference.impl.InferencePackageImpl#getX()
	 * @generated
	 */
	int X = 4;

	/**
	 * The number of structural features of the '<em>X</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int X_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>X</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int X_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link inference.impl.YImpl <em>Y</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.YImpl
	 * @see inference.impl.InferencePackageImpl#getY()
	 * @generated
	 */
	int Y = 5;

	/**
	 * The number of structural features of the '<em>Y</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int Y_FEATURE_COUNT = B_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Y</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int Y_OPERATION_COUNT = B_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link inference.impl.ZImpl <em>Z</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see inference.impl.ZImpl
	 * @see inference.impl.InferencePackageImpl#getZ()
	 * @generated
	 */
	int Z = 6;

	/**
	 * The feature id for the '<em><b>CAttr</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int Z__CATTR = C__CATTR;

	/**
	 * The number of structural features of the '<em>Z</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int Z_FEATURE_COUNT = C_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Z</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int Z_OPERATION_COUNT = C_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>bool</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see java.lang.Boolean
	 * @see inference.impl.InferencePackageImpl#getbool()
	 * @generated
	 */
	int BOOL = 7;

	/**
	 * Returns the meta object for class '{@link inference.O <em>O</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>O</em>'.
	 * @see inference.O
	 * @generated
	 */
	EClass getO();

	/**
	 * Returns the meta object for class '{@link inference.A <em>A</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>A</em>'.
	 * @see inference.A
	 * @generated
	 */
	EClass getA();

	/**
	 * Returns the meta object for class '{@link inference.B <em>B</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>B</em>'.
	 * @see inference.B
	 * @generated
	 */
	EClass getB();

	/**
	 * Returns the meta object for class '{@link inference.C <em>C</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>C</em>'.
	 * @see inference.C
	 * @generated
	 */
	EClass getC();

	/**
	 * Returns the meta object for the attribute '{@link inference.C#getCAttr <em>CAttr</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>CAttr</em>'.
	 * @see inference.C#getCAttr()
	 * @see #getC()
	 * @generated
	 */
	EAttribute getC_CAttr();

	/**
	 * Returns the meta object for class '{@link inference.X <em>X</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>X</em>'.
	 * @see inference.X
	 * @generated
	 */
	EClass getX();

	/**
	 * Returns the meta object for class '{@link inference.Y <em>Y</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Y</em>'.
	 * @see inference.Y
	 * @generated
	 */
	EClass getY();

	/**
	 * Returns the meta object for class '{@link inference.Z <em>Z</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Z</em>'.
	 * @see inference.Z
	 * @generated
	 */
	EClass getZ();

	/**
	 * Returns the meta object for data type '{@link java.lang.Boolean <em>bool</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>bool</em>'.
	 * @see java.lang.Boolean
	 * @model instanceClass="java.lang.Boolean"
	 * @generated
	 */
	EDataType getbool();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InferenceFactory getInferenceFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link inference.impl.OImpl <em>O</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.OImpl
		 * @see inference.impl.InferencePackageImpl#getO()
		 * @generated
		 */
		EClass O = eINSTANCE.getO();

		/**
		 * The meta object literal for the '{@link inference.impl.AImpl <em>A</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.AImpl
		 * @see inference.impl.InferencePackageImpl#getA()
		 * @generated
		 */
		EClass A = eINSTANCE.getA();

		/**
		 * The meta object literal for the '{@link inference.impl.BImpl <em>B</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.BImpl
		 * @see inference.impl.InferencePackageImpl#getB()
		 * @generated
		 */
		EClass B = eINSTANCE.getB();

		/**
		 * The meta object literal for the '{@link inference.impl.CImpl <em>C</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.CImpl
		 * @see inference.impl.InferencePackageImpl#getC()
		 * @generated
		 */
		EClass C = eINSTANCE.getC();

		/**
		 * The meta object literal for the '<em><b>CAttr</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute C__CATTR = eINSTANCE.getC_CAttr();

		/**
		 * The meta object literal for the '{@link inference.impl.XImpl <em>X</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.XImpl
		 * @see inference.impl.InferencePackageImpl#getX()
		 * @generated
		 */
		EClass X = eINSTANCE.getX();

		/**
		 * The meta object literal for the '{@link inference.impl.YImpl <em>Y</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.YImpl
		 * @see inference.impl.InferencePackageImpl#getY()
		 * @generated
		 */
		EClass Y = eINSTANCE.getY();

		/**
		 * The meta object literal for the '{@link inference.impl.ZImpl <em>Z</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see inference.impl.ZImpl
		 * @see inference.impl.InferencePackageImpl#getZ()
		 * @generated
		 */
		EClass Z = eINSTANCE.getZ();

		/**
		 * The meta object literal for the '<em>bool</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.lang.Boolean
		 * @see inference.impl.InferencePackageImpl#getbool()
		 * @generated
		 */
		EDataType BOOL = eINSTANCE.getbool();

	}

} // InferencePackage
