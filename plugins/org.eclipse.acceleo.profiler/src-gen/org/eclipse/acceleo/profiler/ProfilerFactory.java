/**
 * <copyright>
 * </copyright>
 *
 * $Id: ProfilerFactory.java,v 1.2 2010/05/04 07:38:00 lgoubet Exp $
 */
package org.eclipse.acceleo.profiler;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.profiler.ProfilerPackage
 * @generated
 * @since 3.0
 */
public interface ProfilerFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ProfilerFactory eINSTANCE = org.eclipse.acceleo.profiler.impl.ProfilerFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Profile Entry</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Profile Entry</em>'.
	 * @generated
	 */
	ProfileEntry createProfileEntry();

	/**
	 * Returns a new object of class '<em>Loop Profile Entry</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Loop Profile Entry</em>'.
	 * @generated
	 */
	LoopProfileEntry createLoopProfileEntry();

	/**
	 * Returns a new object of class '<em>Profile Resource</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Profile Resource</em>'.
	 * @generated
	 */
	ProfileResource createProfileResource();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProfilerPackage getProfilerPackage();

} // ProfilerFactory
