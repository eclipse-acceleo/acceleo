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

/**
 * This class provides methods for String queries.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public class StringServices {

	/**
	 * Public constructor.
	 */
	public StringServices() {
	}

	/**
	 * Returns a string that is the result of a concatenation of a string "b" at the end of a self string
	 * "self".
	 * 
	 * @param self
	 *            the "self" string from which we concatenate the string "b".
	 * @param b
	 *            the string that will be concatenated to the string "self".
	 * @return The concatenated String. Throws NullPointerException if "self" or "b" is null.
	 */
	public String concat(String self, String b) {
		return self + b;
	}

	/**
	 * Concatenates a string b at the end of a self string "a".
	 * 
	 * @param a
	 *            the self string from which we concatenate the string "b".
	 * @param b
	 *            the string that will be concatenated to the string "a".
	 * @return The concatenated String. Throws NullPointerException if "a" or "b" is null.
	 */
	public String add(String a, String b) {
		return a + b;
	}

	/**
	 * Returns the resulting string of a substitution of the first occurrence of substring
	 * <code>subStringRegex</code> in self by substring <code>replacementRegex</code>.
	 * <code>subStringRegex</code> and <code>replacementRegex</code> are treated as regular expressions.
	 * 
	 * @param self
	 *            the "self" string from which we concatenate the string "b".
	 * @param subStringRegex
	 *            the substring regular expression that must find the first substring to replace in the self
	 *            string.
	 * @param replacementRegex
	 *            the regular expression used to replaced a found substring.
	 * @return Returns the resulting string of a substitution of the first occurrence of substring
	 *         <code>subStringRegex</code> in self by substring <code>replacementRegex</code>. If there is no
	 *         occurrence of the substring, the original string is returned. Throws NullPointerException if
	 *         "self" or "subStringRegex" or "replacementRegex" is null.
	 */
	public String replace(String self, String subStringRegex, String replacementRegex) {
		return self.replaceFirst(subStringRegex, replacementRegex);
	}

	/**
	 * Returns the resulting string of a substitution of all occurrence of substring
	 * <code>subStringRegex</code> in self by substring <code>replacementRegex</code>.
	 * <code>subStringRegex</code> and <code>replacementRegex</code> are treated as regular expressions.
	 * 
	 * @param self
	 *            the "self" string from which we concatenate the string "b".
	 * @param subStringRegex
	 *            the substring regular expression that must find all substring to replace in the self string.
	 * @param replacementRegex
	 *            the regular expression used to replaced a found substring.
	 * @return Returns the resulting string of a substitution of all occurrence of substring
	 *         <code>subStringRegex</code> in self by substring <code>replacementRegex</code>. If there is no
	 *         occurrence of the substring, the original string is returned. Throws NullPointerException if
	 *         "self" or "subStringRegex" or "replacementRegex" is null.
	 */
	public String replaceAll(String self, String subStringRegex, String replacementRegex) {
		return self.replaceAll(subStringRegex, replacementRegex);
	}

	/**
	 * Returns the self String prefixed by the String given as argument.
	 * 
	 * @param self
	 *            the "self" string that will be append at the end of the given prefix.
	 * @param prefix
	 *            the string prefix that will be append at the beginning of the self string.
	 * @return the self String prefixed by the String given as argument. Throws NullPointerException if "self"
	 *         or "b" is null.
	 */
	public String prefix(String self, String prefix) {
		return prefix + self;
	}

	/**
	 * Returns true if a "self" string contains the string "b".
	 * 
	 * @param self
	 *            The self string from which we want to know if it contains "b".
	 * @param b
	 *            The string that we look for in a.
	 * @return true if the self string "self" contains the string "b". False otherwise. Throws
	 *         NullPointerException if "self" or "b" is null.
	 */
	public Boolean contains(String self, String b) {
		return self.contains(b);
	}

	/**
	 * Returns <code>true</code> if <code>self</code> matches the given <code>regex</code>.
	 * 
	 * @param self
	 *            The self string from which we want to know if it matches the given regex.
	 * @param regex
	 *            The regex the self string must respect.
	 * @return <code>true</code> if <code>self</code> matches the given <code>regex</code>. False otherwise.
	 *         Throws NullPointerException if "self" or "regex" is null.
	 */
	public Boolean matches(String self, String regex) {
		return self.matches(regex);
	}

	/**
	 * Returns true if a "self" ends with the string "b".
	 * 
	 * @param self
	 *            The "self" string from which we want to know if it ends with "b" string.
	 * @param b
	 *            The string that must constitute the end of the self string.
	 * @return true if the self string a contains the string b. False otherwise. Throws NullPointerException
	 *         if "self" or "b" is null.
	 */
	public Boolean endsWith(String self, String b) {
		return self.endsWith(b);
	}

	/**
	 * Returns true if a "self" starts with the string "b".
	 * 
	 * @param self
	 *            The "self" string from which we want to know if it starts with "b" string.
	 * @param b
	 *            The string that must constitute the start of the self string.
	 * @return true if the self string a contains the string b. False otherwise. Throws NullPointerException
	 *         if "self" or "b" is null.
	 */
	public Boolean startsWith(String self, String b) {
		return self.startsWith(b);
	}

	/**
	 * Returns true if a "self" string is equals to a string "b" without considering casing in the comparison.
	 * 
	 * @param self
	 *            The self string to compare to b.
	 * @param b
	 *            The string compared to a.
	 * @return true if a self string "self" is equals to a string "b" without considering casing in the
	 *         comparison. False otherwise. Throws NullPointerException if "self" or "b" is null.
	 */
	public Boolean equalsIgnoreCase(String self, String b) {
		return self.equalsIgnoreCase(b);
	}

	/**
	 * Returns the first n characters of self, or self if size of self is less than n.
	 * 
	 * @param n
	 *            The number of first characters that must be retrieved from self.
	 * @param self
	 *            The self string from which we want to retrieve its "n" first characters.
	 * @return the first n characters of self, or self if size of self is less than n. Throws
	 *         NullPointerException if "self" or "n" is null. Throws StringIndexOutOfBoundsException if "n" is
	 *         not a valid index of self (i.e it is inferior to 0).
	 */
	public String first(String self, Integer n) {
		String result = null;
		if (self.length() < n) {
			result = self;
		} else {
			result = self.substring(0, n);
		}
		return result;
	}

	/**
	 * Returns last n characters of self, or self if its size is less than n.
	 * 
	 * @param n
	 *            The number of last characters that must be retrieved from self.
	 * @param self
	 *            The self string from which we want to retrieve its "n" last characters.
	 * @return the last n characters of self, or self if size of self is less than n. Throws
	 *         NullPointerException if "self" or "n" is null. Throws IllegalArgumentException if "n" is not a
	 *         valid index of self(i.e it is inferior to 0).
	 */
	public String last(String self, Integer n) {
		String result = null;
		if (n < 0) {
			throw new IllegalArgumentException("n cannot be inferior to 0");
		} else if (self.length() < n) {
			result = self;
		} else {
			result = self.substring(self.length() - n, self.length());
		}
		return result;
	}

	/**
	 * Returns the index of the last occurrence of <code>substring</code> in self, <code>-1</code> if self
	 * doesn't contain this particular substring. The index referential is 1 like in OCL and not 0.
	 * 
	 * @param self
	 *            The self string from which we want to retrieve the last index of the "b" string in it.
	 * @param b
	 *            The string from which we want to retrieve its last index in the self string.
	 * @return the index of the last occurrence of <code>substring</code> in self, <code>-1</code> if self
	 *         doesn't contain this particular substring. Throws IllegalArgumentException if "self" or "b" is
	 *         null.
	 */
	public Integer lastIndex(String self, String b) {
		int index = self.lastIndexOf(b) + 1;
		if (index == 0) {
			index = -1;
		}
		return index;
	}

	/**
	 * Returns the index of substring "b" in self, or -1 if "b" is not in self. The index referential is 1
	 * like in OCL and not 0.
	 * 
	 * @param self
	 *            The self string from which we want to retrieve the index of the "b" string in it.
	 * @param b
	 *            The string from which we want to retrieve its index in the self string.
	 * @return The index of substring b in self, or -1 if r is not in self. Throws NullPointerException if
	 *         "self" or "b" is null.
	 */
	public Integer index(String self, String b) {
		int index = self.indexOf(b) + 1;
		if (index == 0) {
			index = -1;
		}
		return index;
	}

	/**
	 * Returns the index of the subString "b" starting from the given index. The index referential is 1 like
	 * in OCL and not 0. If index is superior to self string length, then the considered index must be the
	 * self string length.
	 * 
	 * @param self
	 *            The self string from which we want to retrieve the index of the "b" string in it.
	 * @param b
	 *            The string from which we want to retrieve its index in the self string.
	 * @param index
	 *            the starting index in the self string from which we have to start the index search of the
	 *            given string "b".
	 * @return The index of substring b in self, or -1 if r is not in self. Throws NullPointerException if
	 *         "self" or "b" or "index" is null.
	 */
	public Integer index(String self, String b, Integer index) {
		int indexResult = self.indexOf(b, index) + 1;
		if (indexResult == 0) {
			indexResult = -1;
		}
		return indexResult;
	}

	/**
	 * Returns the index of the first occurrence of <code>substring</code> in self starting from
	 * <code>index</code> but backward. The index referential is 1 like in OCL and not 0. If index is superior
	 * to self string length, then the considered index must be the self string length.
	 * 
	 * @param self
	 *            The self string from which we want to retrieve the index of the "b" string in it.
	 * @param b
	 *            The string from which we want to retrieve its index in the self string.
	 * @param index
	 *            the starting index in the self string from which we have to start the backward index search
	 *            of the given string "b".
	 * @return the index of the first occurrence of <code>substring</code> in self starting from
	 *         <code>index</code> but backward, <code>-1</code> if self doesn't contain this particular
	 *         substring. Throws NullPointerException if "self" or "b" or "index" is null.
	 */
	public Integer lastIndex(String self, String b, Integer index) {
		int indexResult = self.lastIndexOf(b, index) + 1;
		if (indexResult == 0) {
			indexResult = -1;
		}
		return indexResult;

	}

	/**
	 * Returns the self string with all characters transformed to lower case one.
	 * 
	 * @param self
	 *            The self string from which we want to convert all characters into lower case ones.
	 * @return The self string with all upper case characters converted to lower case one. Throws
	 *         NullPointerException if "self" is null.
	 */
	public String toLower(String self) {
		return self.toLowerCase();
	}

	/**
	 * Returns the self string with the first characters transformed to lower case one.
	 * 
	 * @param self
	 *            The self string from which we want to convert the first characters into lower case one.
	 * @return the self string with the first character transformed to lower case one. Throws
	 *         NullPointerException if "self" is null.
	 */
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

	/**
	 * Returns the self {@link String} with all characters transformed to upper case one.
	 * 
	 * @param self
	 *            The self string from which we want to convert all characters into upper case ones.
	 * @return The self {@link String} with all lower case characters converted to upper case one. Throws
	 *         {@link NullPointerException} if "self" is <code>null</code>.
	 */
	public String toUpper(String self) {
		return self.toUpperCase();
	}

	/**
	 * Returns the self {@link String} with the first characters transformed to upper case one.
	 * 
	 * @param self
	 *            The self {@link String} from which we want to convert the first characters into upper case
	 *            one.
	 * @return the self {@link String} with the first character transformed to upper case one. Throws
	 *         {@link NullPointerException} if "self" is <code>null</code>.
	 */
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

	/**
	 * Returns <code>true</code> if self consists only of alphabetical characters, <code>false</code>
	 * otherwise.
	 * 
	 * @param self
	 *            The string we want to ensure it consists only of alphabetical characters.
	 * @return <code>true</code> if self consists only of alphabetical characters, <code>false</code>
	 *         otherwise.
	 */
	public Boolean isAlpha(String self) {
		final char[] chars = self.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns <code>true</code> if self consists only of alphanumeric characters, <code>false</code>
	 * otherwise.
	 * 
	 * @param self
	 *            The string we want to ensure it consists only of alphanumeric characters.
	 * @return <code>true</code> if self consists only of alphanumeric characters, <code>false</code>
	 *         otherwise.
	 */
	public Boolean isAlphaNum(String self) {
		final char[] chars = self.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * return the length of the specified string.
	 * 
	 * @param str
	 *            the string which length is requested.
	 * @return the length of the specified string.
	 */
	public Integer size(String str) {
		return str.length();
	}

	/**
	 * Returns a string containing all characters from self starting from index lower up to index upper
	 * included. Both lower and upper parameters should be contained between 1 and self.size() included. lower
	 * cannot be greater than upper.
	 * 
	 * @param self
	 *            the current {@link String}
	 * @param lower
	 *            the lower bound
	 * @param upper
	 *            the uppper bound
	 * @return a string containing all characters from self starting from index lower up to index upper
	 *         included. Both lower and upper parameters should be contained between 1 and self.size()
	 *         included. lower cannot be greater than upper
	 */
	public String substring(String self, Integer lower, Integer upper) {
		return self.substring(lower.intValue() - 1, upper.intValue());
	}
}
