package org.eclipse.acceleo.impl.spec;

import org.eclipse.acceleo.impl.FileStatementImpl;


public class FileStatementSpec extends FileStatementImpl {
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("file(");
		if (getUrl() != null) {
			builder.append(getUrl().toString());
			builder.append(", ");
		}
		if (getMode() != null) {
			builder.append(getMode().toString().toLowerCase());
			builder.append(", ");
		}
		if (getCharset() != null) {
			builder.append(getCharset().toString());
			builder.append(", ");
		}
		builder.append(')');
		return builder.toString();
	}
}
