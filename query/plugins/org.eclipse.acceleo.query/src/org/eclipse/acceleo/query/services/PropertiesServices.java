/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;

//@formatter:off
@ServiceProvider(
	value = "Property services"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class PropertiesServices {

	/**
	 * {@link Properties}.
	 */
	private final Properties properties;

	/**
	 * Constructor.
	 * 
	 * @param properties
	 *            {@link Properties}
	 */
	public PropertiesServices(Properties properties) {
		this.properties = properties;
	}

	// @formatter:off
	@Documentation(
		value = "Get the property value for the given key.",
		params = {
			@Param(name = "key", value = "The property key")
		},
		result = "The property value",
		examples = {
			@Example(expression = "'property1'.getProperty()", result = "a message")
		}
	)
	// @formatter:on
	public String getProperty(String key) {
		final String propertyValue = properties.getProperty(key);

		return format(propertyValue, new Object[0]);
	}

	// @formatter:off
	@Documentation(
		value = "Get the property value for the given key.",
		params = {
			@Param(name = "key", value = "The property key")
		},
		result = "The property value",
		examples = {
			@Example(expression = "'property1'.getProperty()", result = "a message")
		}
	)
	// @formatter:on
	public String getProperty(String key, List<Object> arguments) {
		final String propertyValue = properties.getProperty(key);

		return format(propertyValue, arguments.toArray(new Object[arguments.size()]));
	}

	/**
	 * Formats the given property value with the given arguments.
	 * 
	 * @param propertyValue
	 *            the property value
	 * @param arguments
	 * @return the formated value
	 */
	private String format(String propertyValue, Object[] arguments) {
		final String res;

		if (propertyValue != null) {
			res = MessageFormat.format(propertyValue, arguments);
		} else {
			res = "";
		}

		return res;
	}

}
