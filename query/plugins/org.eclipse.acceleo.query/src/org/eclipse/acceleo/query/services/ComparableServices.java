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

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;

//@formatter:off
@ServiceProvider(
	value = "Services available for Comparables"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class ComparableServices {

	/**
	 * {@link Comparable#compareTo(Object) Compares} <code>a</code> to <code>b</code> and takes care of
	 * <code>null</code>.
	 * 
	 * @param a
	 *            first {@link Comparable} can be <code>null</code>
	 * @param b
	 *            second {@link Comparable} can be <code>null</code>
	 * @return {@link Comparable#compareTo(Object) Compares} <code>a</code> to <code>b</code> and takes care
	 *         of <code>null</code>
	 * @param <T>
	 *            the kind of {@link Comparable}
	 */
	private <T extends Comparable<? super T>> int safeCompare(T a, T b) {
		final int result;

		if (a == null) {
			if (b == null) {
				result = 0;
			} else {
				result = -b.compareTo(a);
			}
		} else {
			result = a.compareTo(b);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Compares \"a\" to \"b\" and return \"true\" if \"a\" is less than \"b\".",
	    params = {
			@Param(name = "a", value = "The first comparable (can be null)"),
			@Param(name = "b", value = "The second comparable (can be null)")
		},
		result = "\"true\" \"a\" is less than \"b\", \"false\" otherwise.",
		examples = {
				@Example(expression = "'Hello' < 'Hello'", result = "false"),
				@Example(expression = "'Hello' < 'World'", result = "true")
		}
	)
	// @formatter:on
	public <T extends Comparable<? super T>> Boolean lessThan(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) < 0);
	}

	// @formatter:off
	@Documentation(
		value = "Compares \"a\" to \"b\" and return \"true\" if \"a\" is less than or equal to \"b\".",
	    params = {
			@Param(name = "a", value = "The first comparable (can be null)"),
			@Param(name = "b", value = "The second comparable (can be null)")
		},
		result = "\"true\" \"a\" is less than or equal to \"b\", \"false\" otherwise.",
		examples = {
			@Example(expression = "'Hello' <='Hello'", result = "true"),
			@Example(expression = "'Hello' <='World'", result = "true")
		}
	)
	// @formatter:on
	public <T extends Comparable<? super T>> Boolean lessThanEqual(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) <= 0);
	}

	// @formatter:off
	@Documentation(
		value = "Compares \"a\" to \"b\" and return \"true\" if \"a\" is greater than \"b\".",
	    params = {
			@Param(name = "a", value = "The first comparable (can be null)"),
			@Param(name = "b", value = "The second comparable (can be null)")
		},
		result = "\"true\" \"a\" is greater than \"b\", \"false\" otherwise.",
		examples = {
			@Example(expression = "'Hello' > 'Abc'", result = "true"),
			@Example(expression = "'Hello' > 'Hello'", result = "false")
		}
	)
	// @formatter:on
	public <T extends Comparable<? super T>> Boolean greaterThan(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) > 0);
	}

	// @formatter:off
	@Documentation(
		value = "Compares \"a\" to \"b\" and return \"true\" if \"a\" is greater than or equal to \"b\".",
	    params = {
			@Param(name = "a", value = "The first comparable (can be null)"),
			@Param(name = "b", value = "The second comparable (can be null)")
		},
		result = "\"true\" \"a\" is greater than or equal to \"b\", \"false\" otherwise.",
		examples = {
			@Example(expression = "'Hello' >= 'Abc'", result = "true"),
			@Example(expression = "'Hello' >= 'Hello'", result = "true")
		}
	)
	// @formatter:on
	public <T extends Comparable<? super T>> Boolean greaterThanEqual(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) >= 0);
	}

}
