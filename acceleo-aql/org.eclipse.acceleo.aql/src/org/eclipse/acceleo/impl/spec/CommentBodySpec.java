package org.eclipse.acceleo.impl.spec;

import org.eclipse.acceleo.impl.CommentBodyImpl;

public class CommentBodySpec extends CommentBodyImpl {
	@Override
	public String toString() {
		return "comment " + getStartPosition() + '-' + getEndPosition();
	}
}
