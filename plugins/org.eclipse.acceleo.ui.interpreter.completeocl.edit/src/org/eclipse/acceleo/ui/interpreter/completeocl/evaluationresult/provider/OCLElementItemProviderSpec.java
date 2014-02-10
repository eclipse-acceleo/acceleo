package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.OCLElementItemProvider;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.util.SeverityOverlay;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.ocl.examples.pivot.NamedElement;
import org.eclipse.ocl.examples.pivot.Package;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.Type;

public class OCLElementItemProviderSpec extends OCLElementItemProvider {
	public OCLElementItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	@Override
	public String getText(Object object) {
		final OCLElement element = (OCLElement)object;
		if (element.getElement() instanceof Root) {
			return "All Results";
		} else if (element.getElement() instanceof Package) {
			return "Package - " + ((Package)element.getElement()).getName();
		} else if (element.getElement() instanceof Type) {
			return "Context - " + ((Type)element.getElement()).getName();
		} else if (element.getElement() instanceof NamedElement) {
			return ((NamedElement)element.getElement()).getName();
		}
		return super.getText(object);
	}
	
	@Override
	public Object getImage(Object object) {
		final OCLElement element = (OCLElement)object;
		if (element.getChildren().isEmpty()) {
			return super.getImage(object);
		}
		return SeverityOverlay.overlaySeverityOn(element.getWorstSeverity(), super.getImage(object));
	}
}
