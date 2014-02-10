package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.OperationElementItemProvider;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.util.SeverityOverlay;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.ocl.examples.pivot.NamedElement;

public class OperationElementItemProviderSpec extends OperationElementItemProvider {
	public OperationElementItemProviderSpec(AdapterFactory  adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public String getText(Object object) {
		final OperationElement operationElement = (OperationElement)object;
		return "Operation - " + ((NamedElement)operationElement.getElement()).getName();
	}
	
	@Override
	public Object getImage(Object object) {
		final OperationElement operationElement = (OperationElement)object;
		final Severity worstSeverity = operationElement.getWorstSeverity();
		if (worstSeverity == Severity.ERROR) {
			return SeverityOverlay.overlaySeverityOn(worstSeverity, super.getImage(object));
		}
		return super.getImage(object);
	}
}
