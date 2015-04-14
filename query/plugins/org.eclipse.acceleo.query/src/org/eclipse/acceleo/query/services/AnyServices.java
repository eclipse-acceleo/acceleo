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

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.ClassType;
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
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 */
	public AnyServices(IReadOnlyQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	@Override
	protected IService getService(Method publicMethod) {
		final IService result;

		if ("oclAsType".equals(publicMethod.getName())) {
			result = new FilterService(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, IReadOnlyQueryEnvironment environment,
						List<IType> argTypes) {
					final Set<IType> result = new LinkedHashSet<IType>();

					final Object type = argTypes.get(1).getType();
					if (type instanceof EClassifier) {
						result.add(new EClassifierType(environment, (EClassifier)type));
					} else if (type instanceof Class) {
						result.add(new ClassType(environment, (Class<?>)type));
					} else if (type != null) {
						result.add(services.nothing("Don't know what kind of type is %s", type));
					} else {
						result.add(services.nothing("Don't know what kind of type is %s", "null"));
					}

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
		final boolean result;

		if (o1 == null) {
			result = o2 == null;
		} else {
			result = o1.equals(o2);
		}

		return Boolean.valueOf(result);
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
		return Boolean.valueOf(!equals(o1, o2));
	}

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
			checkRegistered((EClass)type);
			EClass eClass = (EClass)type;
			if (object instanceof EObject) {
				result = eClass.isSuperTypeOf(((EObject)object).eClass());
			} else {
				result = false;
			}
		} else if (type instanceof EEnum) {
			checkRegistered((EEnum)type);
			if (object instanceof EEnumLiteral) {
				result = ((EEnumLiteral)object).getEEnum().equals(type);
			} else if (object instanceof Enumerator) {
				EEnumLiteral literal = ((EEnum)type).getEEnumLiteral(((Enumerator)object).getName());
				result = literal.getEEnum().equals(type);
			} else {
				result = false;
			}
		} else if (type instanceof EDataType) {
			final Class<?> cls = checkRegistered((EDataType)type);
			if (object != null) {
				result = cls.isAssignableFrom(object.getClass());
			} else {
				result = false;
			}
		} else if (object != null && type instanceof Class<?>) {
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
			checkRegistered((EClass)type);
			EClass eClass = (EClass)type;
			if (object instanceof EObject) {
				result = eClass == ((EObject)object).eClass();
			} else {
				result = false;
			}
		} else if (type instanceof EEnum) {
			checkRegistered((EEnum)type);
			if (object instanceof EEnumLiteral) {
				result = ((EEnumLiteral)object).getEEnum().equals(type);
			} else if (object instanceof Enumerator) {
				EEnumLiteral literal = ((EEnum)type).getEEnumLiteral(((Enumerator)object).getName());
				result = literal.getEEnum().equals(type);
			} else {
				result = false;
			}
		} else if (type instanceof EDataType) {
			final Class<?> cls = checkRegistered((EDataType)type);
			if (object != null) {
				result = cls.isAssignableFrom(object.getClass());
			} else {
				result = false;
			}
		} else if (object != null && type instanceof Class<?>) {
			result = ((Class<?>)type).equals(object.getClass());
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Checks if the given {@link EClassifier} is registered.
	 * 
	 * @param type
	 *            the {@link EClassifier} to check
	 * @return the registered {@link Class} if any.
	 * @throws IllegalArgumentException
	 *             if the type is not registered
	 */
	private Class<?> checkRegistered(EClassifier type) throws IllegalArgumentException {
		final Class<?> result = queryEnvironment.getEPackageProvider().getClass(type);

		if (result == null) {
			throw new IllegalArgumentException(String.format(
					"%s is not registered in the current environment", type));
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
		} else if (object != null && object != EvaluationServices.NOTHING) {
			final String toString = object.toString();
			if (toString != null) {
				buffer.append(toString);
			}
		}
		// else return empty String
		return buffer.toString();
	}
}
