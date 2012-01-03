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
package org.eclipse.acceleo.internal.traceability.engine;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.types.CollectionType;
import org.eclipse.ocl.types.PrimitiveType;
import org.eclipse.ocl.types.TypesPackage;

/**
 * This class will provide convenience methods used by both the evaluation visitor and the operation visitor.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public final class TraceabilityVisitorUtil {
	/** This will contain references towards the Class representing OCL primitive types. */
	private static final List<Class<?>> PRIMITIVE_CLASSES;

	/** This will contain references towards the EClassifiers representing OCL primitive types. */
	private static final List<EClassifier> PRIMITIVE_CLASSIFIERS;

	static {
		PRIMITIVE_CLASSES = new ArrayList<Class<?>>();
		PRIMITIVE_CLASSIFIERS = new ArrayList<EClassifier>();

		PRIMITIVE_CLASSES.add(Character.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEChar());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getECharacterObject());
		PRIMITIVE_CLASSES.add(String.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEString());

		PRIMITIVE_CLASSES.add(Short.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEShort());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEShortObject());
		PRIMITIVE_CLASSES.add(Integer.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEInt());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEIntegerObject());
		PRIMITIVE_CLASSES.add(Long.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getELong());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getELongObject());
		PRIMITIVE_CLASSES.add(BigInteger.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEBigInteger());

		PRIMITIVE_CLASSES.add(Float.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEFloat());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEFloatObject());
		PRIMITIVE_CLASSES.add(Double.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEDouble());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEDoubleObject());
		PRIMITIVE_CLASSES.add(BigDecimal.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEBigDecimal());

		PRIMITIVE_CLASSES.add(Boolean.class);
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEBoolean());
		PRIMITIVE_CLASSIFIERS.add(EcorePackage.eINSTANCE.getEBooleanObject());
	}

	/** Utility classes don't need to be instantiated. */
	private TraceabilityVisitorUtil() {
		// hides default constructor
	}

	/**
	 * This will check whether the given collection contains only primitive values.
	 * 
	 * @param collection
	 *            The collection we are to check.
	 * @return <code>true</code> if <code>collection</code> contains only primitive values as returned by
	 *         {@link #isPrimitive(Object)}.
	 */
	public static boolean isPrimitive(Collection<?> collection) {
		boolean isPrimitiveCollection = true;
		Iterator<?> valueIterator = collection.iterator();
		while (valueIterator.hasNext() && isPrimitiveCollection) {
			isPrimitiveCollection = isPrimitive(valueIterator.next());
		}
		return isPrimitiveCollection;
	}

	/**
	 * This will check whether the given value is an OCL primitive.
	 * 
	 * @param value
	 *            The value to check.
	 * @return <code>true</code> if this value is an OCL primitive.
	 */
	public static boolean isPrimitive(Object value) {
		if (value == null) {
			return false;
		}

		boolean result = false;
		if (value instanceof EClassifier) {
			result = isPrimitive((EClassifier)value);
		} else if (value instanceof Collection<?>) {
			result = isPrimitive((Collection<?>)value);
		} else if (value instanceof Enumerator) {
			result = true;
		} else {
			Class<?> valueClass = value.getClass();
			if (valueClass.isPrimitive()) {
				result = true;
			} else {
				result = PRIMITIVE_CLASSES.contains(valueClass);
			}
		}
		return result;
	}

	/**
	 * This will check whether the given classifier is considered primitive in OCL terms.
	 * 
	 * @param classifier
	 *            The classifier to check.
	 * @return <code>true</code> if this classifier translates into an OCL primitive.
	 */
	public static boolean isPrimitive(EClassifier classifier) {
		if (classifier instanceof PrimitiveType<?>) {
			return true;
		}
		return PRIMITIVE_CLASSIFIERS.contains(classifier);
	}

	/**
	 * This will check whether the given classifier is considered as a primitive collection in OCL terms.
	 * 
	 * @param classifier
	 *            The classifier to check.
	 * @return <code>true</code> if this classifier translates into an OCL primitive.
	 */
	public static boolean isPrimitiveCollection(EClassifier classifier) {
		if (TypesPackage.eINSTANCE.getCollectionType().isInstance(classifier)) {
			return isPrimitive(((CollectionType<?, ?>)classifier).getElementType());
		}
		return false;
	}
}
