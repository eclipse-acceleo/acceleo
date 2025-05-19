/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.annotations.api.documentation.Throw;

//@formatter:off
@ServiceProvider(
	value = "Services available for Strings"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class StringServices {

	/**
	 * New line.
	 */
	public static final String NEW_LINE = "\n";

	/**
	 * New line.
	 */
	public static final String WINDOWS_NEW_LINE = "\r\n";

	public static final Pattern NEW_LINE_PATTERN = Pattern.compile(WINDOWS_NEW_LINE + "|" + NEW_LINE);

	public static final Pattern EMPTY_LINE_PATTERN = Pattern.compile("(" + WINDOWS_NEW_LINE + "|" + NEW_LINE
			+ ")" + "(" + WINDOWS_NEW_LINE + "|" + NEW_LINE + ")");

	/**
	 * Public constructor.
	 */
	public StringServices() {
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string that is the result of the concatenation of the current string and the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String."),
			@Param(name = "b", value = "The String that will be appended at the end of the current String.")
		},
		result = "The concatenated String.",
		examples = {
			@Example(expression = "'Hello'.concat('World')", result = "HelloWorld")
		},
		comment = "This operation behaves like '+' between two strings."
	)
	// @formatter:on
	public String concat(String self, String b) {
		return nullToEmpty(self) + nullToEmpty(b);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string that is the result of the concatenation of the current string and the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String."),
			@Param(name = "b", value = "The String that will be appended at the end of the current String.")
		},
		result = "The concatenated String.",
		examples = {
			@Example(expression = "'Hello' + 'World'", result = "HelloWorld")
		},
		comment = "This operation behaves like '+' between two strings."
	)
	// @formatter:on
	public String add(String self, String b) {
		return nullToEmpty(self) + nullToEmpty(b);
	}

	// @formatter:off
	@Documentation(
		value = "Replaces the first substring of the current String that matches the regular expression \"regex\" " +
	            "with the String \"replacement\".",
		params = {
			@Param(name = "self", value = "The current String."),
			@Param(name = "regex", value = "The regular expression used to find the substring to replace in the current String."),
			@Param(name = "replacement", value = "The replacement String.")
		},
		result = "Returns the resulting String of a substitution of the first substring matching the given regex by the given replacement",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"regex\" or \"replacement\" is null."),
			@Throw(type = PatternSyntaxException.class, value = "If the regular expression's syntax is invalid")
		},
		examples = {
			@Example(expression = "'Hello'.replace('(.*)ll', 'Wh')", result = "'Who'")
		}
	)
	// @formatter:on
	public String replaceFirst(String self, String regex, String replacement) {
		return replace(self, regex, replacement);
	}

	// @formatter:off
		@Documentation(
			value = "Replaces the first substring of the current String that matches the regular expression \"regex\" " +
		            "with the String \"replacement\".",
			params = {
				@Param(name = "self", value = "The current String."),
				@Param(name = "regex", value = "The regular expression used to find the substring to replace in the current String."),
				@Param(name = "replacement", value = "The replacement String.")
			},
			result = "Returns the resulting String of a substitution of the first substring matching the given regex by the given replacement",
			exceptions = {
				@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"regex\" or \"replacement\" is null."),
				@Throw(type = PatternSyntaxException.class, value = "If the regular expression's syntax is invalid")
			},
			examples = {
				@Example(expression = "'Hello'.replace('(.*)ll', 'Wh')", result = "'Who'")
			}
		)
		// @formatter:on
	public String replace(String self, String regex, String replacement) {
		return nullToEmpty(self).replaceFirst(nullToEmpty(regex), nullToEmpty(replacement));
	}

	// @formatter:off
	@Documentation(
		value = "Replaces each substring of the current String that matches the given regular expression \"regex\" " +
	            "with the String \"replacement\".",
		params = {
			@Param(name = "self", value = "The current String."),
			@Param(name = "regex", value = "The regular expression used to find all the substrings to replace in the current String."),
			@Param(name = "replacement", value = "The replacement String.")
		},
		result = "Returns the resulting String of a substitution of all the substrings matching the given regex by the given replacement",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"regex\" or \"replacement\" is null."),
			@Throw(type = PatternSyntaxException.class, value = "If the regular expression's syntax is invalid")
		},
		examples = {
			@Example(expression = "'TestTest'.replace('.st', 'erminated')", result = "'TerminatedTerminated'")
		}
	)
	// @formatter:on
	public String replaceAll(String self, String regex, String replacement) {
		return nullToEmpty(self).replaceAll(nullToEmpty(regex), nullToEmpty(replacement));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the current String prefixed with the given \"prefix\".",
		params = {
			@Param(name = "self", value = "The current String that will be prefixed"),
			@Param(name = "prefix", value = "The String that will be prepended before the current String")
		},
		result = "The current String prefixed with the given \"prefix\"",
		examples = {
			@Example(expression = "'World'.prefix('Hello')", result = "'HelloWorld'")
		}
	)
	// @formatter:on
	public String prefix(String self, String prefix) {
		return nullToEmpty(prefix) + nullToEmpty(self);
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" if the current String contains the String \"b\"",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String that we will look for in the current String")
		},
		result = "\"true\" if the current String contains the String \"b\", \"false\" otherwise",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		},
		examples = {
			@Example(expression = "'Hello'.contains('llo')", result = "true")
		}
	)
	// @formatter:on
	public Boolean contains(String self, String b) {
		return Boolean.valueOf(nullToEmpty(self).contains(nullToEmpty(b)));
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" if the current String matches the given \"regex\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "regex", value = "The regex used for the match")
		},
		result = "\"true\" if \"self\" matches the given regex, \"false\" otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"regex\" is null.")
		},
		examples = {
			@Example(expression = "'Hello'.matches('*llo')", result = "true")
		}
	)
	// @formatter:on
	public Boolean matches(String self, String regex) {
		return Boolean.valueOf(nullToEmpty(self).matches(nullToEmpty(regex)));
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if the current String ends with the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String that may be at the end of the current String")
		},
		result = "\"true\" if the current String ends with the string \"b\", \"false\" otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		},
		examples = {
			@Example(expression = "'Hello'.endsWidth('llo')", result = "true")
		}
	)
	// @formatter:on
	public Boolean endsWith(String self, String b) {
		return Boolean.valueOf(nullToEmpty(self).endsWith(nullToEmpty(b)));
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if the current String starts with the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String that may be at the beginning of the current String")
		},
		result = "\"true\" if the current String starts with the string \"b\", \"false\" otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		},
		examples = {
			@Example(expression = "'Hello'.startsWith('Hell')", result = "true")
		}
	)
	// @formatter:on
	public Boolean startsWith(String self, String b) {
		return Boolean.valueOf(nullToEmpty(self).startsWith(nullToEmpty(b)));
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if the current String is equals to the String \"b\" without considering case in the comparison.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String to compare with the current String")
		},
		result = "\"true\" if the current String is equal to the string \"b\", without considering case, \"false\" otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		},
		examples = {
			@Example(expression = "'Hello'.equalsIgnoreCase('hello')", result = "true")
		}
	)
	// @formatter:on
	public Boolean equalsIgnoreCase(String self, String b) {
		return Boolean.valueOf(nullToEmpty(self).equalsIgnoreCase(nullToEmpty(b)));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the \"n\" first characters of the current String, or the current String itself if its size is less than \"n\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "n", value = "The number of characters that must be retrieved from the beginning of the current String.")
		},
		result = "The \"n\" first characters of the current String",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"n\" is null."),
			@Throw(type = IndexOutOfBoundsException.class, value = "If \"n\" is not a valid index of self (i.e it is inferior to 0).")
		},
		examples = {
			@Example(expression = "'HelloWorld'.first(5)", result = "'Hello'")
		}
	)
	// @formatter:on
	public String first(String self, Integer n) {
		final String result;

		if (self == null || self.length() < n) {
			result = self;
		} else {
			result = self.substring(0, n);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the \"n\" last characters of the current String, or the current String if its size is less than \"n\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "n", value = "The number of characters that must be retrieved from the end of the current String")
		},
		result = "The \"n\" last characters of the current String",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"n\" is null."),
			@Throw(type = IndexOutOfBoundsException.class, value = "If \"n\" is not a valid index of self (i.e it is greater than the size of the current String).")
		},
		examples = {
			@Example(expression = "'HelloWorld'.last(5)", result = "'World'")
		}
	)
	// @formatter:on
	public String last(String self, Integer n) {
		final String result;

		if (self == null || self.length() < n) {
			result = self;
		} else {
			result = self.substring(self.length() - n, self.length());
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the last occurrence of \"subString\" in the current String, \"-1\" if " +
	            "the current String doesn't contain this particular substring. The index referential is 1 as in OCL and not 0.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "subString", value = "The substring that we want to find in the current String")
		},
		result = "The index of the last occurrence of subString in the current String or -1 if not found",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"subString\" is null.")
		},
		examples = {
			@Example(expression = "'HelloHello'.lastIndex('World')", result = "6")
		}
	)
	// @formatter:on
	public Integer lastIndex(String self, String subString) {
		int index = nullToEmpty(self).lastIndexOf(nullToEmpty(subString)) + 1;

		if (index == 0) {
			index = -1;
		}

		return Integer.valueOf(index);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the first occurrence \"subString\" in the current String, or -1 if \"subString\" is not " +
	            "in the current String. The index referential is 1 as in OCL and not 0.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "subString", value = "The substring that we want to find in the current String")
		},
		result = "The index of the first occurrence of subString in the current String or -1 if not found",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"subString\" is null.")
		},
		examples = {
			@Example(expression = "'HelloHello'.index('Hello')", result = "1")
		}
	)
	// @formatter:on
	public Integer index(String self, String b) {
		int index = nullToEmpty(self).indexOf(nullToEmpty(b)) + 1;

		if (index == 0) {
			index = -1;
		}

		return Integer.valueOf(index);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the first occurrence \"subString\" in the current String from the given index, or -1 if \"subString\" is not " +
	            "in the current String. The index referential is 1 as in OCL and not 0.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "subString", value = "The substring that we want to find in the current String"),
			@Param(name = "indexString", value = "The starting index from which the substring will be searched")
		},
		result = "The index of the first occurrence of subString in the current String or -1 if not found",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String, \"subString\" or index is null.")
		},
		examples = {
			@Example(expression = "'HelloHello'.index('Hello', 2)", result = "6")
		}
	)
	// @formatter:on
	public Integer index(String self, String subString, Integer index) {
		int indexResult = nullToEmpty(self).indexOf(nullToEmpty(subString), index) + 1;

		if (indexResult == 0) {
			indexResult = -1;
		}

		return Integer.valueOf(indexResult);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the last occurrence \"subString\" in the current String searching backward from the given index, " +
	            "or -1 if \"subString\" is not in the current String. The index referential is 1 as in OCL and not 0.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "subString", value = "The substring that we want to find in the current String"),
			@Param(name = "indexString", value = "The starting index from which the substring will be searched")
		},
		result = "The index of the last occurrence of subString in the current String or -1 if not found",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String, \"subString\" or index is null.")
		},
		examples = {
			@Example(expression = "'HelloHello'.lastIndex('Hello', 7)", result = "1")
		}
	)
	// @formatter:on
	public Integer lastIndex(String self, String subString, Integer index) {
		int indexResult = nullToEmpty(self).lastIndexOf(nullToEmpty(subString), index) + 1;

		if (indexResult == 0) {
			indexResult = -1;
		}

		return Integer.valueOf(indexResult);

	}

	// @formatter:off
	@Documentation(
		value = "Returns the current String with all characters transformed to lower case.",
		params = {
			@Param(name = "self", value = "The current String from which we want to convert all characters to lower case.")
		},
		result = "The current String with all upper case characters converted to lower case.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is \"null\".")
		},
		examples = {
			@Example(expression = "'HelloWorld'.toLower()", result = "'helloworld'")
		}
	)
	// @formatter:on
	public String toLower(String self) {
		final String result;

		if (self == null) {
			result = null;
		} else {
			result = self.toLowerCase();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the self string with the first characters transformed to lower case.",
		params = {
			@Param(name = "self", value = "The current String from which we want to convert the first character to lower case.")
		},
		result = "The current String with the first character transformed to lower case.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is \"null\".")
		},
		examples = {
			@Example(expression = "'HelloWorld'.toLowerFirst()", result = "'helloWorld'")
		}
	)
	// @formatter:on
	public String toLowerFirst(String self) {
		final String resultString;

		if (self == null) {
			resultString = null;
		} else if (self.length() == 0) {
			resultString = self;
		} else if (self.length() == 1) {
			resultString = self.toLowerCase();
		} else {
			resultString = Character.toLowerCase(self.charAt(0)) + self.substring(1);
		}

		return resultString;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the current String with all characters transformed to upper case.",
		params = {
			@Param(name = "self", value = "The current String from which we want to convert all characters to upper case.")
		},
		result = "The current String with all lower case characters converted to upper case.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is \"null\".")
		},
		examples = {
			@Example(expression = "'HelloWorld'.toUpper()", result = "'HELLOWORLD'")
		}
	)
	// @formatter:on
	public String toUpper(String self) {
		final String result;

		if (self == null) {
			result = null;
		} else {
			result = self.toUpperCase();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the current String with the first characters transformed to upper case.",
		params = {
			@Param(name = "self", value = "The current String from which we want to convert the first character to upper case.")
		},
		result = "The current String with the first character transformed to upper case.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is \"null\".")
		},
		examples = {
			@Example(expression = "'helloworld'.toUpperFirst()", result = "'Helloworld'")
		}
	)
	// @formatter:on
	public String toUpperFirst(String self) {
		final String resultString;

		if (self == null) {
			resultString = null;
		} else if (self.length() == 0) {
			resultString = self;
		} else if (self.length() == 1) {
			resultString = self.toUpperCase();
		} else {
			resultString = Character.toUpperCase(self.charAt(0)) + self.substring(1);
		}

		return resultString;
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" if self consists only of alphabetical characters, \"false\" otherwise.",
		params = {
			@Param(name = "self", value = "The string we want to ensure it consists only of alphabetical characters.")
		},
		result = "\"true\" if self consists only of alphabetical characters, \"false\" otherwise.",
		examples = {
			@Example(expression = "'abc123'.isAlpha()", result = "false"),
			@Example(expression = "'abcdef'.isAlpha()", result = "true"),
		}
	)
	// @formatter:on
	public Boolean isAlpha(String self) {
		Boolean result = Boolean.TRUE;

		if (self != null) {
			final char[] chars = self.toCharArray();
			for (final char c : chars) {
				if (!Character.isLetter(c)) {
					result = Boolean.FALSE;
					break;
				}
			}
		} else {
			result = Boolean.FALSE;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" if self consists only of alphanumeric characters, \"false\" otherwise.",
		params = {
			@Param(name = "self", value = "The string we want to ensure it consists only of alphanumeric characters.")
		},
		result = "\"true\" if self consists only of alphanumeric characters, \"false\" otherwise.",
		examples = {
			@Example(expression = "'abc123'.isAlphaNum()", result = "true"),
			@Example(expression = "'abcdef'.isAlphaNum()", result = "true"),
		}
	)
	// @formatter:on
	public Boolean isAlphaNum(String self) {
		Boolean result = Boolean.TRUE;

		if (self != null) {
			final char[] chars = self.toCharArray();
			for (final char c : chars) {
				if (!Character.isLetterOrDigit(c)) {
					result = Boolean.FALSE;
					break;
				}
			}
		} else {
			result = Boolean.FALSE;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Return the length of the current String.",
		params = {
			@Param(name = "self", value = "The current String")
		},
		result = "The length of the specified string",
		examples = {
			@Example(expression = "'HelloWorld'.size()", result = "10")
		}
	)
	// @formatter:on
	public Integer size(String self) {
		final int result;

		if (self == null || self.isEmpty()) {
			result = 0;
		} else {
			result = nullToEmpty(self).length();
		}

		return Integer.valueOf(result);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string containing all characters from self starting from index lower up to the end of the string " +
	            "included. The lower parameter should be contained between 1 and self.size() included. Lower cannot be greater " +
				"than the size of the String.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "lower", value = "The lower bound")
		},
		result = "A string containing all characters from self starting from index lower included.",
		examples = {
			@Example(expression = "'HelloWorld'.substring(5)", result = "'World'"),
			@Example(expression = "'HelloWorld'.substring(1)", result = "'HelloWorld'"),
		}
	)
	// @formatter:on
	public String substring(String self, Integer lower) {
		final String result;

		if (self == null) {
			result = null;
		} else {
			result = self.substring(lower.intValue() - 1);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string containing all characters from self starting from index lower up to index upper " +
	            "included. Both lower and upper parameters should be contained between 1 and self.size() included. Lower " +
				"cannot be greater than upper.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "lower", value = "The lower bound"),
			@Param(name = "upper", value = "The upper bound")
		},
		result = "a string containing all characters from self starting from index lower up to index upper " +
		         "included. Both lower and upper parameters should be contained between 1 and self.size() " +
				 "included. lower cannot be greater than upper",
		examples = {
				@Example(expression = "'HelloWorld'.substring(1, 5)", result = "'Hello'")
		}
	)
	// @formatter:on
	public String substring(String self, Integer lower, Integer upper) {
		final String result;

		if (self == null) {
			result = null;
		} else {
			result = self.substring(lower.intValue() - 1, upper.intValue());
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns an integer of value equal to self",
		params = {
			@Param(name = "self", value = "The current String")
		},
		result = "An integer of value equal to self",
		exceptions = {
			@Throw(type = NumberFormatException.class, value = "Thrown if self does not represent an integer")
		},
		examples = {
			@Example(expression = "'42'.toInteger()", result = "42")
		}
	)
	// @formatter:on
	public Integer toInteger(String self) {
		return Integer.valueOf(nullToEmpty(self));
	}

	// @formatter:off
	@Documentation(
		value = "Returns a real of value equal to self",
		params = {
			@Param(name = "self", value = "The current String")
		},
		result = "A real of value equal to self",
		exceptions = {
			@Throw(type = NumberFormatException.class, value = "Thrown if self does not represent a real")
		},
		examples = {
			@Example(expression = "'41.9'.toReal()", result = "41.9")
		}
	)
	// @formatter:on
	public Double toReal(String self) {
		return Double.valueOf(nullToEmpty(self));
	}

	// @formatter:off
	@Documentation(
		value = "Returns an integer that is either negative, zero or positive depending on whether s1 is alphabetically " +
	            "less than, equal to or greater than self. Note that upper case letters come before lower case ones, so " +
				"that 'AA' is closer to 'AC' than it is to 'Ab'.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "s1", value = "The other String")
		},
		result = "An integer that is either negative, zero or positive depending on whether s1 is alphabetically " +
		         "less than, equal to or greater than self. Note that upper case letters come before lower case " +
				 "ones, so that 'AA' is closer to 'AC' than it is to 'Ab'",
		examples = {
			@Example(expression = "'strcmp operation'.strcmp('strcmp')", result = "10"),
			@Example(expression = "'strcmp operation'.strcmp('strcmp operation')", result = "0"),
			@Example(expression = "'strcmp operation'.strcmp('strtok')", result = "-17")
		}
	)
	// @formatter:on
	public Integer strcmp(String self, String s1) {
		return Integer.valueOf(nullToEmpty(self).compareTo(nullToEmpty(s1)));
	}

	// @formatter:off
	@Documentation(
		value = "Searches r in self.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "r", value = "The String to search")
		},
		result = "\"true\" if r is found, \"false\" otherwise",
		examples = {
			@Example(expression = "'HelloWorld'.strstr('World')", result = "true")
		}
	)
	// @formatter:on
	public Boolean strstr(String self, String r) {
		return Boolean.valueOf(nullToEmpty(self).indexOf(nullToEmpty(r)) > -1);
	}

	// @formatter:off
	@Documentation(
		value = "Substitutes the first occurrence of the substring \"r\" in self by \"t\" and returns the resulting string." +
		        " Will return self if it contains no occurrence of the substring r.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "r", value = "The String to replace"),
			@Param(name = "t", value = "The replacement String")
		},
		result = "A new String",
		examples = {
			@Example(expression = "'WorldWorld'.substitute('World', 'Hello')", result = "'HelloWorld'")
		}
	)
	// @formatter:on
	public String substituteFirst(String self, String r, String t) {
		return substitute(self, r, t);
	}

	// @formatter:off
	@Documentation(
		value = "Substitutes the first occurrence of the substring \"r\" in self by \"t\" and returns the resulting string." +
		        " Will return self if it contains no occurrence of the substring r.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "r", value = "The String to replace"),
			@Param(name = "t", value = "The replacement String")
		},
		result = "A new String",
		examples = {
			@Example(expression = "'WorldWorld'.substitute('World', 'Hello')", result = "'HelloWorld'")
		}
	)
	// @formatter:on
	public String substitute(String self, String r, String t) {
		return Pattern.compile(nullToEmpty(r), Pattern.LITERAL).matcher(nullToEmpty(self)).replaceFirst(
				Matcher.quoteReplacement(nullToEmpty(t)));
	}

	// @formatter:off
	@Documentation(
		value = "Substitutes all occurrences of the substring \"r\" in self by \"t\" and returns the resulting string." +
	            " Will return self if it contains no occurrence of the substring r.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "r", value = "The String to replace"),
			@Param(name = "t", value = "The replacement String")
		},
		result = "A new String",
		examples = {
			@Example(expression = "'WorldWorld'.substituteAll('World', 'Hello')", result = "'HelloHello'")
		}
	)
	// @formatter:on
	public String substituteAll(String self, String r, String t) {
		return Pattern.compile(nullToEmpty(r), Pattern.LITERAL).matcher(nullToEmpty(self)).replaceAll(Matcher
				.quoteReplacement(nullToEmpty(t)));
	}

	// @formatter:off
	@Documentation(
		value = "Trims the given String.",
		params = {
			@Param(name = "self", value = "The String to trim")
		},
		result = "The trimmed String",
		examples = {
			@Example(expression = "'  Hello World   '.trim()", result = "'Hello World'")
		}
	)
	// @formatter:on
	public String trim(String self) {
		final String result;

		if (self == null) {
			result = null;
		} else if (self.isEmpty()) {
			result = self;
		} else {
			result = nullToEmpty(self).trim();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Removes all line separators.",
		params = {
			@Param(name = "self", value = "The String to trim")
		},
		result = "The String with removed line separators",
		examples = {
				@Example(expression = "'Hello\\nWorld'.removeLineSeparators()", result = "'HelloWorld'"),
				@Example(expression = "'Hello\\r\\nWorld'.removeLineSeparators()", result = "'HelloWorld'")
		}
	)
	// @formatter:on
	public String removeLineSeparators(String self) {
		final String res;

		if (self != null) {
			res = NEW_LINE_PATTERN.matcher(self).replaceAll("");
		} else {
			res = null;
		}

		return res;
	}

	// @formatter:off
	@Documentation(
		value = "Removes all empty lines.",
		params = {
			@Param(name = "self", value = "The String to trim")
		},
		result = "The String with removed empty lines",
		examples = {
				@Example(expression = "'Hello\\n\\nWorld'.removeEmptyLines()", result = "'HelloWorld'"),
				@Example(expression = "'Hello\\r\\n\\r\\nWorld'.removeEmptyLines()", result = "'HelloWorld'"),
				@Example(expression = "'Hello\\n\\r\\nWorld'.removeEmptyLines()", result = "'HelloWorld'"),
				@Example(expression = "'Hello\\r\\n\\nWorld'.removeEmptyLines()", result = "'HelloWorld'")
		}
	)
	// @formatter:on
	public String removeEmptyLines(String self) {
		final String res;

		if (self != null) {
			res = EMPTY_LINE_PATTERN.matcher(self).replaceAll("");
		} else {
			res = null;
		}

		return res;
	}

	// @formatter:off
	@Documentation(
		value = "Splits the current String by whitespace delimiter into a collection of String",
		params = {
			@Param(name = "self", value = "The current String")
		},
		result = "The collection of substrings of the current String delimited by whitespaces",
		examples = {
			@Example(expression = "'a, b, c, d'.tokenize()", result = "['a,', 'b,', 'c,', 'd']")
		}
	)
	// @formatter:on
	public List<String> tokenize(String self) {
		final List<String> segments = new ArrayList<String>();
		final StringTokenizer tokenizer = new StringTokenizer(nullToEmpty(self));

		while (tokenizer.hasMoreTokens()) {
			segments.add(tokenizer.nextToken());
		}

		return segments;
	}

	// @formatter:off
	@Documentation(
		value = "Splits the current String by using the given \"delimiter\" into a collection of String",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "delimiter", value = "The current String")
		},
		result = "The collection of substrings of the current String delimited by the given \"delimiter\"",
		examples = {
			@Example(expression = "'a, b, c, d'.tokenize(', ')", result = "['a', 'b', 'c', 'd']")
		}
	)
	// @formatter:on
	public List<String> tokenize(String self, String delimiter) {
		final List<String> segments = new ArrayList<String>();

		StringTokenizer tokenizer = new StringTokenizer(nullToEmpty(self), nullToEmpty(delimiter));
		while (tokenizer.hasMoreTokens()) {
			segments.add(tokenizer.nextToken());
		}

		return segments;
	}

	// @formatter:off
	@Documentation(
		value = "Gets the character at the given index of the given String.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "index", value = "The index")
		},
		result = "The character at the given index",
		examples = {
			@Example(expression = "'cat'.at(2)", result = "'a'")
		}
	)
	// @formatter:on
	public String at(String str, Integer index) {
		final String res;

		if (str == null) {
			res = null;
		} else {
			return String.valueOf(str.charAt(index - 1));
		}

		return res;
	}

	// @formatter:off
	@Documentation(
		value = "Converts the given String in a Sequence of Strings representing each character.",
		params = {
			@Param(name = "self", value = "The current String"),
		},
		result = "The Sequence of Strings representing each caracter",
		examples = {
			@Example(expression = "'cat'.characters()", result = "Sequence{'c', 'a', 't'}")
		}
	)
	// @formatter:on
	public List<String> characters(String str) {
		final List<String> res = new ArrayList<String>();

		final String string = nullToEmpty(str);
		for (int i = 0; i < string.length(); i++) {
			res.add(String.valueOf(string.charAt(i)));
		}

		return res;
	}

	// @formatter:off
	@Documentation(
		value = "Gets the platform line separator.",
		params = {
			@Param(name = "obj", value = "Any object"),
		},
		result = "'\\n' (Unix) or '\\r\\n' (Dos) or '\\r' (Mac Os Classic)",
		examples = {
			@Example(expression = "obj.lineSeparator()", result = "'\\n'")
		}
	)
	// @formatter:on
	public String lineSeparator(Object obj) {
		return System.getProperty("line.separator");
	}

	// @formatter:off
	@Documentation(
		value = "Gets the boolean value of the given String.",
		params = {
			@Param(name = "value", value = "The current String"),
		},
		result = "true if then passed String equals ignoring case to 'true', false otherwise",
		examples = {
				@Example(expression = "'true'.toBoolean()", result = "true"),
				@Example(expression = "'True'.toBoolean()", result = "true"),
				@Example(expression = "'Some String'.toBoolean()", result = "false")
		}
	)
	// @formatter:on
	public Boolean toBoolean(String value) {
		return Boolean.valueOf(value);
	}

	/**
	 * Gets the empty {@link String} if the given {@link String} is <code>null</code>.
	 * 
	 * @param str
	 *            the {@link String}
	 * @return the empty {@link String} if the given {@link String} is <code>null</code>
	 */
	private String nullToEmpty(String str) {
		final String res;

		if (str == null) {
			res = "";
		} else {
			res = str;
		}

		return res;
	}

}
