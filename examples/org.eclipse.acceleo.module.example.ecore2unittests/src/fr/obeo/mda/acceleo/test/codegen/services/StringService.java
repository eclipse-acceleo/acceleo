package fr.obeo.mda.acceleo.test.codegen.services;

public class StringService {
	public String convertToPackageString(String s) {
		StringBuffer result = new StringBuffer(String.valueOf(s.charAt(0)));
		for (int i = 1; i < s.length(); i++) {
			final char next = s.charAt(i);
			if (Character.isUpperCase(next)) {
				result.append('_');
			}
			result.append(Character.toUpperCase(next));
		}
		return result.toString();
	}
}
