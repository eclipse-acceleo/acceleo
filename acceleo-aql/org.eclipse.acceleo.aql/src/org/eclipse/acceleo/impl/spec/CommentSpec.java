package org.eclipse.acceleo.impl.spec;

import org.eclipse.acceleo.impl.CommentImpl;

public class CommentSpec extends CommentImpl {
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
