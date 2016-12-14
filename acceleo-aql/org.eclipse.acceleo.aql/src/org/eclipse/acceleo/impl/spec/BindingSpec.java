package org.eclipse.acceleo.impl.spec;

import org.eclipse.acceleo.impl.BindingImpl;

public class BindingSpec extends BindingImpl {
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getName());
		if (getType() != null) {
			builder.append(" : " + getType().getEPackage().getName() + "::" + getType().getName());
		}
		if (getInitExpression() != null) {
			builder.append(" = " + getInitExpression());
		}
		return builder.toString();
	}
}
