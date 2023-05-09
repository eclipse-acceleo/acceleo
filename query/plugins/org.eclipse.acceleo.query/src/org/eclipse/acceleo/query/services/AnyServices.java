/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

//@formatter:off
@ServiceProvider(
	value = "Services available for all types"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class AnyServices extends AbstractServiceProvider {

	/**
	 * Line separator constant.
	 */
	private static final String LINE_SEP = System.getProperty("line.separator");

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
	protected IService<Method> getService(Method publicMethod) {
		final IService<Method> result;

		if ("oclAsType".equals(publicMethod.getName())) {
			result = new OCLAsTypeService(publicMethod, this);
		} else {
			result = new JavaMethodService(publicMethod, this);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Indicates whether the object \"o1\" is the same as the object \"o2\". For more " +
	            "information refer to the Object#equals(Object) method.",
	    params = {
			@Param(name = "o1", value = "The object to compare for equality"),
			@Param(name = "o2", value = "The reference object with which to compare")
		},
		result = "true\" if the object \"o1\" is the same as the object \"o2\", " +
		         "\"false\" otherwise",
		examples = {
			@Example(expression = "'Hello' = 'World'", result = "false"),
			@Example(expression = "'Hello' = 'Hello'", result = "true")
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
			@Example(expression = "'Hello' <> 'World'", result = "true"),
			@Example(expression = "'Hello' <> 'Hello'", result = "false")
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
			@Example(expression = "42 + ' times'", result = "'42 times'")
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
			@Example(expression = "'times ' + 42", result = "'times 42'")
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
		if (oclIsKindOf(object, type)) {
			return object;
		}
		throw new ClassCastException(object + " cannot be cast to " + type);
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
		if (object == null && type != null) {
			// OCL considers "null" (OclVoid) to be compatible with everything.
			// AQL considers it incompatible with anything.
			result = false;
		} else if (type instanceof EClass) {
			EClass eClass = (EClass)type;
			if (object instanceof EObject) {
				result = eClass.isInstance(object);
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
			result = ((EDataType)type).isInstance(object);
		} else if (object != null && type instanceof Class<?>) {
			result = ((Class<?>)type).isInstance(object);
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
			@Example(expression = "anEPackage.oclIsTypeOf(ecore::EPackage)", result = "true"),
			@Example(expression = "anEPackage.oclIsTypeOf(ecore::ENamedElement)", result = "false")
		}
	)
	// @formatter:on
	public Boolean oclIsTypeOf(Object object, Object type) {
		Boolean result;
		if (object == null && type != null) {
			// OCL considers "null" (OclVoid) to be compatible with everything.
			// AQL considers it incompatible with anything.
			result = false;
		} else if (type instanceof EClass) {
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
			result = ((EDataType)type).isInstance(object);
		} else if (object != null && type instanceof Class<?>) {
			result = ((Class<?>)type).equals(object.getClass());
		} else {
			result = false;
		}
		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string representation of the current object.",
		params = {
			@Param(name = "self", value = "The current object")
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

	// @formatter:off
	@Documentation(
		value = "Returns a string representation of the current environment.",
		params = {
			@Param(name = "self", value = "The current object")
		},
		result = "a string representation of the current environment.",
		examples = {
			@Example(expression = "42.trace()", result = "'Metamodels:\n\thttp://www.eclipse.org/emf/2002/Ecore\n" +
					"Services:\n\torg.eclipse.acceleo.query.services.AnyServices\n\t\tpublic java.lang.String org." +
					"eclipse.acceleo.query.services.AnyServices.add(java.lang.Object,java.lang.String)\n\t\t...\nreceiver: 42\n'")
		}
	)
	// @formatter:on
	public String trace(Object object) {
		final StringBuilder result = new StringBuilder();

		result.append("Metamodels:" + LINE_SEP);
		for (EPackage ePgk : queryEnvironment.getEPackageProvider().getRegisteredEPackages()) {
			result.append("\t" + ePgk.getNsURI() + LINE_SEP);
		}
		result.append("Services:" + LINE_SEP);
		final List<IService<?>> services = new ArrayList<IService<?>>(queryEnvironment.getLookupEngine()
				.getRegisteredServices());
		Collections.sort(services, new Comparator<IService<?>>() {

			/**
			 * {@inheritDoc}
			 *
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(IService<?> service1, IService<?> service2) {
				final int result;

				if (service1.getPriority() < service2.getPriority()) {
					result = -1;
				} else if (service1.getPriority() > service2.getPriority()) {
					result = 1;
				} else {
					result = service1.getName().compareTo(service2.getName());
				}
				return result;
			}

		});
		for (IService<?> service : services) {
			result.append("\t\t" + service.getLongSignature() + LINE_SEP);
		}
		result.append("receiver: ");
		result.append(toString(object) + LINE_SEP);

		return result.toString();
	}

	private static class OCLAsTypeService extends FilterService {

		OCLAsTypeService(Method publicMethod, Object serviceInstance) {
			super(publicMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment environment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final IType receiverType = argTypes.get(0);
			final IType filterType = argTypes.get(1);
			if (services.lower(receiverType, filterType) != null) {
				Object resultType = filterType.getType();
				if (resultType instanceof EClassifier) {
					result.add(new EClassifierType(environment, (EClassifier)resultType));
				} else if (resultType instanceof Class) {
					result.add(new ClassType(environment, (Class<?>)resultType));
				} else if (resultType != null) {
					result.add(services.nothing("Unknown type %s", resultType));
				} else {
					result.add(services.nothing("Unknown type %s", "null"));
				}
			} else {
				if (receiverType instanceof EClassifierType && !environment.getEPackageProvider()
						.isRegistered(((EClassifierType)receiverType).getType())) {
					result.add(services.nothing("%s is not registered within the current environment.",
							receiverType));
				} else if (filterType instanceof EClassifierType && !environment.getEPackageProvider()
						.isRegistered(((EClassifierType)filterType).getType())) {
					result.add(services.nothing("%s is not registered within the current environment.",
							filterType));
				} else {
					result.add(services.nothing("%s is not compatible with type %s", receiverType,
							filterType));
					result.addAll(services.intersection(receiverType, filterType));
				}
			}

			return result;
		}

		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final StringBuilder builder = new StringBuilder();

			for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				for (IType possibleType : entry.getValue()) {
					final IType rawType;
					if (possibleType instanceof ICollectionType) {
						rawType = ((ICollectionType)possibleType).getCollectionType();
					} else {
						rawType = possibleType;
					}
					if (rawType instanceof NothingType) {
						builder.append("\n");
						builder.append(((NothingType)rawType).getMessage());
					} else {
						result.add(possibleType);
					}
				}
			}

			if (result.isEmpty()) {
				final IType resultType = allTypes.entrySet().iterator().next().getValue().iterator().next();
				final NothingType nothing = services.nothing("Nothing will be left after calling %s:"
						+ builder.toString(), getName());
				if (resultType instanceof SequenceType) {
					result.add(new SequenceType(queryEnvironment, nothing));
				} else if (resultType instanceof SetType) {
					result.add(new SetType(queryEnvironment, nothing));
				} else {
					result.add(nothing);
				}
			}

			return result;
		}
	}
}
