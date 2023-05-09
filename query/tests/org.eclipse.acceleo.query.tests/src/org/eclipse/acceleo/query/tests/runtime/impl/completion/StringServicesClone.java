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
package org.eclipse.acceleo.query.tests.runtime.impl.completion;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.Throw;

/**
 * Utility class used for the test of the documentation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">St&eacute;phane B&eacute;gaudeau</a>
 */
@SuppressWarnings({"checkstyle:javadocmethod" })
public class StringServicesClone {

	/**
	 * Public constructor.
	 */
	public StringServicesClone() {
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string that is the result of a concatenation of a string \"b\" at the end of a self string \"self\".",
		params = {
			@Param(name = "self", value = "The \"self\" string from which we concatenate the string \"b\"."),
			@Param(name = "b", value = "The string that will be concatenated to the string \"self\".") 
		},
		result = "The concatenated String."
	)
	// @formatter:on
	public String concat(String self, String b) {
		return nullToEmpty(self) + nullToEmpty(b);
	}

	@Documentation("Concatenates a string b at the end of a self string \"a\".")
	public String add(String a, String b) {
		return nullToEmpty(a) + nullToEmpty(b);
	}

	public String replace(String self, String subStringRegex, String replacementRegex) {
		return self.replaceFirst(subStringRegex, replacementRegex);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the resulting string of a substitution of all occurrence of substring" +
				"<code>subStringRegex</code> in self by substring <code>replacementRegex</code>." +
				"<code>subStringRegex</code> and <code>replacementRegex</code> are treated as regular expressions.",
		params = {
			@Param(name = "self", value = "The \"self\" string from which we concatenate the string \"b\".")
		}
	)
	// @formatter:on
	public String replaceAll(String self, String subStringRegex, String replacementRegex) {
		return self.replaceAll(subStringRegex, replacementRegex);
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if a \"self\" string contains the string \"b\".",
		result = "true if the self string \"self\" contains the string \"b\". False otherwise. Throws" +
				 "NullPointerException if \"self\" or \"b\" is null."
	)
	// @formatter:on
	public String prefix(String self, String prefix) {
		return nullToEmpty(prefix) + nullToEmpty(self);
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if a \"self\" string contains the string \"b\".",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "If one of the parameter is null."),
			@Throw(type = IllegalAccessException.class, value = "If things go wrong."),
		}
	)
	// @formatter:on
	public Boolean contains(String self, String b) throws NullPointerException, IllegalAccessException {
		return Boolean.valueOf(self.contains(b));
	}

	private String nullToEmpty(String s) {
		return s == null ? "" : s;
	}

}
