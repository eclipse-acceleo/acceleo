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

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Other;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
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
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

//@formatter:off
@ServiceProvider(
	value = "Services available for all types"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
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
				public Set<IType> getType(Call call, ValidationServices services,
						IValidationResult validationResult, IReadOnlyQueryEnvironment environment,
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

	// @formatter:off
	@Documentation(
		value = "Indicates whether the object \"o1\" i\"the same as the object \"o2\". For more " +
	            "information refer to the Object#equals(Object) method.",
	    params = {
			@Param(name = "o1", value = "The object to compare for equality"),
			@Param(name = "o2", value = "The reference object with which to compare")
		},
		result = "true\" if the object \"o1\" is the same as the object \"o2\", " +
		         "\"false\" otherwise",
		examples = {
			@Example(expression = "'Hello'.equals('World')", result = "false"),
			@Example(expression = "'Hello'.equals('Hello')", result = "true")
		}
	)
	// @formatter:on
	public Boolean equals(Object o1, Object o2) {
		final boolean result;

		if (o1 == null) {
			result = o2 == null;
		} else {
			result = o1.equals(o2);
		}

		return Boolean.valueOf(result);
	}

	// @formatter:off
	@Documentation(
		value = "Indicates whether the object \"o1\" is a different object from the object \"o2\".",
		params = {
			@Param(name = "o1", value = "The object to compare"),
			@Param(name = "o2", value = "The reference object with which to compare")
		},
		result = "\"true\" if the object \"o1\" is not the same as the object \"o2\", " +
				 "\"false\" otherwise.",
		examples = {
			@Example(expression = "'Hello'.differs('World')", result = "true"),
			@Example(expression = "'Hello'.differs('Hello')", result = "false")
		}
	)
	// @formatter:on
	public Boolean differs(Object o1, Object o2) {
		return Boolean.valueOf(!equals(o1, o2));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the concatenation of self (as a String) and the given string \"s\".",
		params = {
			@Param(name = "self", value = "The current object at the end of which to append \"s\"."),
			@Param(name = "s", value = "The string we want to append at the end of the current object's string representation.")
		},
		result = "The string representation of self for which we added the string \"s\".",
		examples = {
			@Example(expression = "42.add(' times')", result = "'42 times'")
		}
	)
	// @formatter:on
	public String add(Object self, String s) {
		final String result;

		if (s == null) {
			result = toString(self);
		} else {
			result = toString(self) + s;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the concatenation of the current string and the given object \"any\" (as a String).",
		params = {
			@Param(name = "self", value = "The current string."),
			@Param(name = "any", value = "The object we want to append, as a string, at the end of the current string.")
		},
		result = "The current string with the object \"any\" appended (as a String).",
		examples = {
			@Example(expression = "'times '.add(42)", result = "'times 42'")
		}
	)
	// @formatter:on
	public String add(String self, Object any) {
		final String result;

		if (self == null) {
			result = toString(any);
		} else {
			result = self + toString(any);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Casts the current object to the given type.",
		params = {
			@Param(name = "object", value = "The object to cast"),
			@Param(name = "type", value = "The type to cast the object to")
		},
		result = "The current object cast to a \"type\"",
		examples = {
			@Example(
				expression = "anEPackage.oclAsType(ecore::EPackage)", result = "anEPackage",
				others = {
					@Other(
						language = Other.ACCELEO_3, expression = "anEPackage.oclAsType(ecore::EPackage)", result = "anEPackage"
					)
				}
			),
			@Example(
				expression = "anEPackage.oclAsType(ecore::EClass)", result = "anEPackage",
				others = {
					@Other(
						language = Other.ACCELEO_3, expression = "anEPackage.oclAsType(ecore::EClass)", result = "oclInvalid"
					)
				}
			),
		},
		comment = "Contrary to Acceleo 3, the type is ignored, the given object will be returned directly."
	)
	// @formatter:on
	public Object oclAsType(Object object, Object type) {
		return object;
	}

	// @formatter:off
	@Documentation(
		value = "Evaluates to \"true\" if the type of the object o1 conforms to the type \"classifier\". That is, " +
				"o1 is of type \"classifier\" or a subtype of \"classifier\".",
		params = {
			@Param(name = "object", value = "The reference Object we seek to test."),
			@Param(name = "type", value = "The expected supertype classifier.")
		},
		result = "\"true\" if the object o1 is a kind of the classifier, \"false\" otherwise.",
		examples = {
			@Example(expression = "anEPackage.oclIsKindOf(ecore::EPackage)", result = "true"),
			@Example(expression = "anEPackage.oclIsKindOf(ecore::ENamedElement)", result = "true")
		}
	)
	// @formatter:on
	public Boolean oclIsKindOf(Object object, Object type) {
		Boolean result;
		Class<?> cls = null;
		if (type instanceof EClassifier && !(object instanceof DynamicEObjectImpl)) {
			cls = checkRegistered((EClassifier)type);
		}
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
			if (object != null && cls != null) {
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

	// @formatter:off
	@Documentation(
		value = "Evaluates to \"true\" if the object o1 if of the type \"classifier\" but not a subtype of the " +
				"\"classifier\".",
		params = {
			@Param(name = "object", value = "The reference Object we seek to test."),
			@Param(name = "type", value = "The expected type classifier.")
		},
		result = "\"true\" if the object o1 is a type of the classifier, \"false\" otherwise.",
		examples = {
			@Example(expression = "anEPackage.oclIsKindOf(ecore::EPackage)", result = "true"),
			@Example(expression = "anEPackage.oclIsKindOf(ecore::ENamedElement)", result = "false")
		}
	)
	// @formatter:on
	public Boolean oclIsTypeOf(Object object, Object type) {
		Boolean result;
		Class<?> cls = null;
		if (type instanceof EClassifier && !(object instanceof DynamicEObjectImpl)) {
			cls = checkRegistered((EClassifier)type);
		}
		if (type instanceof EClass) {
			EClass eClass = (EClass)type;
			if (object instanceof EObject) {
				result = eClass == ((EObject)object).eClass();
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
			if (object != null && cls != null) {
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

	// @formatter:off
	@Documentation(
		value = "Returns a string representation of the current object.",
		params = {
			@Param(name = "self", value = "The current object"),
			@Param(name = "", value = "")
		},
		result = "a String representation of the given Object. For Collections, this will be the concatenation of " +
				 "all contained Objects' toString.",
		examples = {
			@Example(expression = "42.toString()", result = "'42'")
		}
	)
	// @formatter:on
	public String toString(Object object) {
		final StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				buffer.append(toString(childrenIterator.next()));
			}
		} else if (object != null && !(object instanceof Nothing)) {
			final String toString = object.toString();
			if (toString != null) {
				buffer.append(toString);
			}
		}
		// else return empty String
		return buffer.toString();
	}
}
