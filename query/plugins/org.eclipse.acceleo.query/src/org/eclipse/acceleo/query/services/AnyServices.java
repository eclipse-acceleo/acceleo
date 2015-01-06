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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

/**
 * The purpose of this class is to allow execution of <code>Any</code> operations.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public class AnyServices extends AbstractServiceProvider {

	/**
	 * The Class constructor.
	 */
	public AnyServices() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider#getService(java.lang.reflect.Method)
	 */
	@Override
	protected IService getService(Method publicMethod) {
		final IService result;

		if ("oclAsType".equals(publicMethod.getName())) {
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
					final Set<IType> result = new LinkedHashSet<IType>();

					result.add(new EClassifierType(((EClassifierLiteralType)argTypes.get(1)).getType()));

					return result;
				}
			};
		} else {
			result = new Service(publicMethod, this);
		}

		return result;
	}

	/**
	 * Indicates whether the object <code>o1</code> is the same as the object <code>o2</code>. For more
	 * information refer to the {@link Object#equals(Object)} method.
	 * 
	 * @param o1
	 *            the reference object to compare
	 * @param o2
	 *            the reference object with which to compare
	 * @return <code>true</code> if the object <code>o1</code> is the same as the object <code>o2</code>,
	 *         <code>false</code> otherwise.
	 */
	public Boolean equals(Object o1, Object o2) {
		return o1.equals(o2);
	}

	/**
	 * Indicates whether the object <code>o1</code> is a different object from the object <code>o2</code>.
	 * 
	 * @param o1
	 *            the reference object to compare
	 * @param o2
	 *            the reference object with which to compare
	 * @return <code>true</code> if the object <code>o1</code> is not the same as the object <code>o2</code>,
	 *         <code>false</code> otherwise.
	 */
	public Boolean differs(Object o1, Object o2) {
		return !o1.equals(o2);
	}

	/**
	 * Indicates whether the comparable object <code>o1</code> is less than the comparable object
	 * <code>o2</code>.
	 * 
	 * @param o1
	 *            the reference object to compare
	 * @param o2
	 *            the reference object with which to compare
	 * @param <T>
	 *            the compared element type
	 * @return <code>true</code> if the object <code>o1</code> is less than the object <code>o2</code>,
	 *         <code>false</code> otherwise.
	 */
	public <T extends Comparable<T>> Boolean lessThan(T o1, T o2) {
		return o1.compareTo(o2) < 0;
	}

	/**
	 * Indicates whether the comparable object <code>o1</code> is greater than the comparable object
	 * <code>o2</code>.
	 * 
	 * @param o1
	 *            the reference object to compare
	 * @param o2
	 *            the reference object with which to compare
	 * @param <T>
	 *            the compared element type
	 * @return <code>true</code> if the object <code>o1</code> is greater than the object <code>o2</code>,
	 *         <code>false</code> otherwise.
	 */
	public <T extends Comparable<T>> Boolean greaterThan(T o1, T o2) {
		return o1.compareTo(o2) > 0;
	}

	/**
	 * Indicates whether the comparable object <code>o1</code> is less than or equal to the comparable object
	 * <code>o2</code>.
	 * 
	 * @param o1
	 *            the reference object to compare
	 * @param o2
	 *            the reference object with which to compare
	 * @param <T>
	 *            the compared element type
	 * @return <code>true</code> if the object <code>o1</code> is less than or equal to the object
	 *         <code>o2</code>, <code>false</code> otherwise.
	 */
	public <T extends Comparable<T>> Boolean lessThanEqual(T o1, T o2) {
		return o1.compareTo(o2) <= 0;
	}

	/**
	 * Indicates whether the comparable object <code>o1</code> is greater than or equal to the comparable
	 * object <code>o2</code>.
	 * 
	 * @param o1
	 *            the reference object to compare
	 * @param o2
	 *            the reference object with which to compare
	 * @param <T>
	 *            the compared element type
	 * @return <code>true</code> if the object <code>o1</code> is greater than or equal to the object
	 *         <code>o2</code>, <code>false</code> otherwise.
	 */
	public <T extends Comparable<T>> Boolean greaterThanEqual(T o1, T o2) {
		return o1.compareTo(o2) >= 0;
	}

	//
	// /**
	// * Handles calls to the "eContainer" operation. This will retrieve the very first container in the
	// * hierarchy that is of type <em>filter</em>.
	// *
	// * @param o1
	// * the reference EObject we seek to retrieve a feature value of.
	// * @param filter
	// * Types of the container we seek to retrieve.
	// * @return The first container of type <em>filter</em>.
	// */
	// public Object eContainer(EObject o1, EClassifier filter) {
	// EObject container = o1.eContainer();
	// while (container != null && !filter.isInstance(container)) {
	// container = container.eContainer();
	// }
	// return container;
	// }

	/**
	 * Returns the string representation of self object to which we concatenated the string "s".
	 * 
	 * @param self
	 *            The self object from which we want the string representation.
	 * @param s
	 *            The string we want to concatenate to the self object representation.
	 * @return The string representation of self for which we added the string "s".
	 */
	public String add(Object self, String s) {
		return toString(self) + toString(s);
	}

	/**
	 * Returns the self string to which we concatenated the any object string representation.
	 * 
	 * @param self
	 *            The string to which the any object string representation will be concatenated to.
	 * @param any
	 *            The self object we want to concatenate to the self string.
	 * @return The string representation of self for which we added the string "s".
	 */
	public String add(String self, Object any) {
		return toString(self) + toString(any);
	}

	/**
	 * oclAsType is a mere return with the dynamic typing.
	 * 
	 * @param object
	 *            the casted object.
	 * @param type
	 *            the filter.
	 * @return the parameter
	 */
	public Object oclAsType(Object object, Object type) {
		return object;
	}

	/**
	 * Evaluates to <code>true</code> if the type of the object o1 conforms to the type "classifier". That is,
	 * o1 is of type "classifier" or a subtype of "classifier".
	 * 
	 * @param object
	 *            the reference Object we seek to test.
	 * @param type
	 *            the expected supertype classifier.
	 * @return <code>true</code> if the object o1 is a kind of the classifier, <code>false</code> otherwise.
	 */
	public Boolean oclIsKindOf(Object object, Object type) {
		Boolean result;
		if (type instanceof EClass) {
			EClass eClass = (EClass)type;
			if (object instanceof EObject) {
				result = eClass.isSuperTypeOf(((EObject)object).eClass());
			} else {
				result = false;
			}
		} else if (type instanceof EEnum) {
			if (object instanceof EEnumLiteral) {
				result = ((EEnumLiteral)object).getEEnum().equals(type);
			} else if (object instanceof Enumerator) {
				EEnumLiteral literal = ((EEnum)type).getEEnumLiteral(((Enumerator)object).getName());
				result = literal.getEEnum().equals(type);
			} else {
				result = false;
			}
		} else if (type instanceof EDataType) {
			result = ((EClassifier)type).getInstanceClass().isAssignableFrom(object.getClass());
		} else if (type instanceof Class<?>) {
			result = ((Class<?>)type).isAssignableFrom(object.getClass());
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Evaluates to <code>true</code> if the object o1 if of the type "classifier" but not a subtype of the
	 * "classifier".
	 * 
	 * @param object
	 *            the reference Object we seek to test.
	 * @param type
	 *            the expected supertype classifier.
	 * @return <code>true</code> if the object o1 is a kind of the classifier, <code>false</code> otherwise.
	 */
	public Boolean oclIsTypeOf(Object object, Object type) {
		Boolean result;
		if (type instanceof EClass) {
			EClass eClass = (EClass)type;
			if (object instanceof EObject) {
				result = eClass.equals(((EObject)object).eClass());
			} else {
				result = false;
			}
		} else if (type instanceof EEnum) {
			if (object instanceof EEnumLiteral) {
				result = ((EEnumLiteral)object).getEEnum().equals(type);
			} else if (object instanceof Enumerator) {
				EEnumLiteral literal = ((EEnum)type).getEEnumLiteral(((Enumerator)object).getName());
				result = literal.getEEnum().equals(type);
			} else {
				result = false;
			}
		} else if (type instanceof EDataType) {
			result = ((EClassifier)type).getInstanceClass().isAssignableFrom(object.getClass());
		} else if (type instanceof Class<?>) {
			result = ((Class<?>)type).equals(object.getClass());
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Collections need special handling when generated from Acceleo.
	 * 
	 * @param object
	 *            The object we wish the String representation of.
	 * @return String representation of the given Object. For Collections, this will be the concatenation of
	 *         all contained Objects' toString.
	 */
	public String toString(Object object) {
		final StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				buffer.append(toString(childrenIterator.next()));
			}
		} else if (object != null) {
			buffer.append(object.toString());
		}
		// else return empty String
		return buffer.toString();
	}
}
