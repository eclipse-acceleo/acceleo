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

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.Throw;

/**
 * This class provides methods for String queries.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
@SuppressWarnings({"checkstyle:javadocmethod" })
public class StringServices {

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
		result = "The concatenated String."
	)
	// @formatter:on
	public String concat(String self, String b) {
		return Strings.nullToEmpty(self) + Strings.nullToEmpty(b);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a string that is the result of the concatenation of the current string and the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String."),
			@Param(name = "b", value = "The String that will be appended at the end of the current String.")
		},
		result = "The concatenated String."
	)
	// @formatter:on
	public String add(String self, String b) {
		return Strings.nullToEmpty(self) + Strings.nullToEmpty(b);
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
		}
	)
	// @formatter:on
	public String replace(String self, String regex, String replacement) {
		return self.replaceFirst(regex, replacement);
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
		}
	)
	// @formatter:on
	public String replaceAll(String self, String regex, String replacement) {
		return self.replaceAll(regex, replacement);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the current String prefixed with the given \"prefix\".",
		params = {
			@Param(name = "self", value = "The current String that will be prefixed"),
			@Param(name = "prefix", value = "The String that will be prepended before the current String")
		},
		result = "The current String prefixed with the given \"prefix\""
	)
	// @formatter:on
	public String prefix(String self, String prefix) {
		return Strings.nullToEmpty(prefix) + Strings.nullToEmpty(self);
	}

	// @formatter:off
	@Documentation(
		value = "Returns <code>true</code> if the current String contains the String \"b\"",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String that we will look for in the current String")
		},
		result = "<code>true</code> if the current String contains the String \"b\", <code>false</code> otherwise",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		}
	)
	// @formatter:on
	public Boolean contains(String self, String b) {
		return Boolean.valueOf(self.contains(b));
	}

	// @formatter:off
	@Documentation(
		value = "Returns <code>true</code> if the current String matches the given <code>regex</code>.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "regex", value = "The regex used for the match")
		},
		result = "<code>true</code> if <code>self</code> matches the given regex, <code>false</code> otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"regex\" is null.")
		}
	)
	// @formatter:on
	public Boolean matches(String self, String regex) {
		return Boolean.valueOf(self.matches(regex));
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if the current String ends with the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String that may be at the end of the current String")
		},
		result = "<code>true</code> if the current String ends with the string \"b\", <code>false</code> otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		}
	)
	// @formatter:on
	public Boolean endsWith(String self, String b) {
		return Boolean.valueOf(self.endsWith(b));
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if the current String starts with the string \"b\".",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String that may be at the beginning of the current String")
		},
		result = "<code>true</code> if the current String starts with the string \"b\", <code>false</code> otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		}
	)
	// @formatter:on
	public Boolean startsWith(String self, String b) {
		return Boolean.valueOf(self.startsWith(b));
	}

	// @formatter:off
	@Documentation(
		value = "Returns true if the current String is equals to the String \"b\" without considering case in the comparison.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "b", value = "The String to compare with the current String")
		},
		result = "<code>true</code> if the current String is equal to the string \"b\", without considering case, <code>false</code> otherwise.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"b\" is null.")
		}
	)
	// @formatter:on
	public Boolean equalsIgnoreCase(String self, String b) {
		return Boolean.valueOf(self.equalsIgnoreCase(b));
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
		}
	)
	// @formatter:on
	public String first(String self, Integer n) {
		String result = null;
		if (self.length() < n) {
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
		}
	)
	// @formatter:on
	public String last(String self, Integer n) {
		String result = null;
		if (self.length() < n) {
			result = self;
		} else {
			result = self.substring(self.length() - n, self.length());
		}
		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the last occurrence of \"subString\" in the current String, <code>-1</code> if " +
	            "the current String doesn't contain this particular substring. The index referential is 1 as in OCL and not 0.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "subString", value = "The substring that we want to find in the current String")
		},
		result = "The index of the last occurrence of subString in the current String or -1 if not found",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if the current String or \"subString\" is null.")
		}
	)
	// @formatter:on
	public Integer lastIndex(String self, String subString) {
		int index = self.lastIndexOf(subString) + 1;
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
		}
	)
	// @formatter:on
	public Integer index(String self, String b) {
		int index = self.indexOf(b) + 1;
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
		}
	)
	// @formatter:on
	public Integer index(String self, String subString, Integer index) {
		int indexResult = self.indexOf(subString, index) + 1;
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
		}
	)
	// @formatter:on
	public Integer lastIndex(String self, String subString, Integer index) {
		int indexResult = self.lastIndexOf(subString, index) + 1;
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
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is <code>null</code>.")
		}
	)
	// @formatter:on
	public String toLower(String self) {
		return self.toLowerCase();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the self string with the first characters transformed to lower case.",
		params = {
			@Param(name = "self", value = "The current String from which we want to convert the first character to lower case.")
		},
		result = "The current String with the first character transformed to lower case.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is <code>null</code>.")
		}
	)
	// @formatter:on
	public String toLowerFirst(String self) {
		final String resultString;

		if (self.length() == 0) {
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
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is <code>null</code>.")
		}
	)
	// @formatter:on
	public String toUpper(String self) {
		return self.toUpperCase();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the current String with the first characters transformed to upper case.",
		params = {
			@Param(name = "self", value = "The current String from which we want to convert the first character to upper case.")
		},
		result = "The current String with the first character transformed to upper case.",
		exceptions = {
			@Throw(type = NullPointerException.class, value = "Thrown if \"self\" is <code>null</code>.")
		}
	)
	// @formatter:on
	public String toUpperFirst(String self) {
		final String resultString;

		if (self.length() == 0) {
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
		value = "Returns <code>true</code> if self consists only of alphabetical characters, <code>false</code> otherwise.",
		params = {
			@Param(name = "self", value = "The string we want to ensure it consists only of alphabetical characters.")
		},
		result = "<code>true</code> if self consists only of alphabetical characters, <code>false</code> otherwise."
	)
	// @formatter:on
	public Boolean isAlpha(String self) {
		final char[] chars = self.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetter(c)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	// @formatter:off
	@Documentation(
		value = "Returns <code>true</code> if self consists only of alphanumeric characters, <code>false</code> otherwise.",
		params = {
			@Param(name = "self", value = "The string we want to ensure it consists only of alphanumeric characters.")
		},
		result = "<code>true</code> if self consists only of alphanumeric characters, <code>false</code> otherwise."
	)
	// @formatter:on
	public Boolean isAlphaNum(String self) {
		final char[] chars = self.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	// @formatter:off
	@Documentation(
		value = "Return the length of the current String.",
		params = {
			@Param(name = "self", value = "The current String")
		},
		result = "The length of the specified string"
	)
	// @formatter:on
	public Integer size(String self) {
		return Integer.valueOf(self.length());
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
				 "included. lower cannot be greater than upper"
	)
	// @formatter:on
	public String substring(String self, Integer lower, Integer upper) {
		return self.substring(lower.intValue() - 1, upper.intValue());
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
		}
	)
	// @formatter:on
	public Integer toInteger(String self) {
		return Integer.valueOf(self);
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
		}
	)
	// @formatter:on
	public Double toReal(String self) {
		return Double.valueOf(self);
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
				 "ones, so that 'AA' is closer to 'AC' than it is to 'Ab'"
	)
	// @formatter:on
	public Integer strcmp(String self, String s1) {
		return Integer.valueOf(self.compareTo(s1));
	}

	// @formatter:off
	@Documentation(
		value = "Searches r in self.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "r", value = "The String to search")
		},
		result = "<code>true</code> if r is found, <code>false</code> otherwise"
	)
	// @formatter:on
	public Boolean strstr(String self, String r) {
		return Boolean.valueOf(self.indexOf(r) > -1);
	}

	// @formatter:off
	@Documentation(
		value = "Substitutes substring r in self by substring t and returns the resulting string. Will return self if it " +
	            "contains no occurrence of the substring r.",
		params = {
			@Param(name = "self", value = "The current String"),
			@Param(name = "r", value = "The String to replace"),
			@Param(name = "t", value = "The replacement String")
		},
		result = "A new String"
	)
	// @formatter:on
	public String substitute(String self, String r, String t) {
		return Pattern.compile(r, Pattern.LITERAL).matcher(self).replaceAll(Matcher.quoteReplacement(t));
	}

	// @formatter:off
	@Documentation(
		value = "Trims the given String.",
		params = {
			@Param(name = "self", value = "The String to trim")
		},
		result = "The trimmed String"
	)
	// @formatter:on
	public String trim(String self) {
		return Strings.nullToEmpty(self).trim();
	}

}
