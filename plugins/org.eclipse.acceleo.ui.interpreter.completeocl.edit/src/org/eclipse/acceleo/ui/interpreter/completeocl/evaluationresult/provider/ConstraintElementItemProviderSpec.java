package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.ConstraintElementItemProvider;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.util.SeverityOverlay;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.ocl.examples.pivot.NamedElement;

public class ConstraintElementItemProviderSpec extends ConstraintElementItemProvider {
	public ConstraintElementItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public String getText(Object object) {
		final ConstraintElement constraintElement = (ConstraintElement)object;
		return "Constraint - " + ((NamedElement)constraintElement.getElement()).getName();
	}
	
	@Override
	public boolean hasChildren(Object object) {
		final ConstraintElement constraintElement = (ConstraintElement)object;
		return !constraintElement.getConstraintResults().isEmpty() && constraintElement.getWorstSeverity() != Severity.OK;
	}
	
	@Override
	public Collection<?> getChildren(Object object) {
		final Collection<?> children = super.getChildren(object);
		final Iterator<?> iterator = children.iterator();
		while (iterator.hasNext()) {
			final Object child = iterator.next();
			if (child instanceof ConstraintResult && ((ConstraintResult) child).getSeverity() == Severity.OK) {
				iterator.remove();
			}
		}
		return children;
	}
	
	@Override
	public Object getImage(Object object) {
		final ConstraintElement constraintElement = (ConstraintElement)object;
		return SeverityOverlay.overlaySeverityOn(constraintElement.getWorstSeverity(), super.getImage(object));
	}
}
