/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation of {@link IType}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public abstract class AbstractType implements IType {
	/** Associates primitive java classes to their wrapper. */
	private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPERS;

	static {
		final Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

		map.put(byte.class, Byte.class);
		map.put(short.class, Short.class);
		map.put(char.class, Character.class);
		map.put(int.class, Integer.class);
		map.put(long.class, Long.class);
		map.put(float.class, Float.class);
		map.put(double.class, Double.class);
		map.put(boolean.class, Boolean.class);

		PRIMITIVE_WRAPPERS = Collections.unmodifiableMap(map);
	}

	/**
	 * Returns the primitive wrapper for the given class if any, or the class itself if it isn't a primitive.
	 * 
	 * @param source
	 *            The class to check for a primitive wrapper.
	 * @return The primitive wrapper for the given class if any, or the class itself if it isn't a primitive.
	 * @since 4.1
	 */
	protected Class<?> wrapPrimitive(Class<?> source) {
		Class<?> wrapper = PRIMITIVE_WRAPPERS.get(source);
		if (wrapper != null) {
			return wrapper;
		}
		return source;
	}

	/**
	 * AQL allows for widening conversions (int to long for example) but not narrowing ones (long to int) and
	 * considers auto-boxing. This extends the basic java {@link Class#isAssignableFrom(Class)
	 * "Class.isAssignableFrom"} with these rules.
	 * 
	 * @param toType
	 *            The type into which we're trying to convert another.
	 * @param fromType
	 *            The source type from which to convert.
	 * @return <code>true</code> if <code>toType</code> can be assigned from <code>fromType</code> when
	 *         considering auto-boxing and primitive widening rules.
	 * @since 4.1
	 */
	protected boolean isAssignableFrom(Class<?> toType, Class<?> fromType) {
		final boolean result;

		final Class<?> wrappedToType = wrapPrimitive(toType);
		final Class<?> wrappedFromType = wrapPrimitive(fromType);
		if (toType == null) {
			result = false;
		} else if (fromType == null) {
			result = true;
		} else {
			result = wrappedToType.isAssignableFrom(wrappedFromType);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getType().hashCode();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return ((AbstractType)obj).getType().equals(getType());
	}
}
