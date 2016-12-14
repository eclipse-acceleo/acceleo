package org.eclipse.acceleo.impl.spec;

import org.eclipse.acceleo.impl.DocumentationImpl;

public class DocumentationSpec extends DocumentationImpl {
	@Override
	public String toString() {
		String result = "comment";
		if (getBody() != null) {
			result += ' ' + getBody().getValue();
		}
		result += ' ' + getStartPosition() + '-' + getEndPosition();
		return result;
	}
}
