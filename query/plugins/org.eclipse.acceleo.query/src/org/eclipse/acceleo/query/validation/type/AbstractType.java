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
package org.eclipse.acceleo.query.validation.type;

import com.google.common.collect.ImmutableMap;

/**
 * Abstract implementation of {@link IType}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public abstract class AbstractType implements IType {
	/** Associates primitive java classes to their wrapper. */
	private static final ImmutableMap<Class<?>, Class<?>> PRIMITIVE_WRAPPERS;

	static {
		//@formatter:off
		PRIMITIVE_WRAPPERS = ImmutableMap.<Class<?>, Class<?>> builder()
				.put(byte.class, Byte.class)
				.put(short.class, Short.class)
				.put(char.class, Character.class)
				.put(int.class, Integer.class)
				.put(long.class, Long.class)
				.put(float.class, Float.class)
				.put(double.class, Double.class)
				.put(boolean.class, Boolean.class)
				.build();
		//@formatter:on
	}

	/**
	 * Returns the primitve wrapper for the given class if any, or the class itself if it isn't a primitive.
	 * 
	 * @param source
	 *            The class to check for a primitive wrapper.
	 * @return The primitve wrapper for the given class if any, or the class itself if it isn't a primitive.
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
		return getClass() == obj.getClass() && ((AbstractType)obj).getType().equals(getType());
	}
}
