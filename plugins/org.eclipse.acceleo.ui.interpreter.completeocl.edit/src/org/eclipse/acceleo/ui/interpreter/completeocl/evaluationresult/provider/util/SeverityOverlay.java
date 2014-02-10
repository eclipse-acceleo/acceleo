package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.provider.EvaluationResultEditPlugin;
import org.eclipse.emf.edit.provider.ComposedImage;

public final class SeverityOverlay {
	private SeverityOverlay() {
		// Hides default constructor
	}
	
	public static Object overlaySeverityOn(Severity severity, Object image) {
		List<Object> images = new ArrayList<Object>(2);
		images.add(image);
		switch (severity) {
			case OK:
				images.add(EvaluationResultEditPlugin.INSTANCE.getImage("full/ovr16/success_ovr"));
				break;
			case INFO:
				images.add(EvaluationResultEditPlugin.INSTANCE.getImage("full/ovr16/info_ovr"));
				break;
			case WARNING:
				images.add(EvaluationResultEditPlugin.INSTANCE.getImage("full/ovr16/warning_ovr"));
				break;
			case ERROR:
				images.add(EvaluationResultEditPlugin.INSTANCE.getImage("full/ovr16/error_ovr"));
				break;
			default:
				images.add(EvaluationResultEditPlugin.INSTANCE.getImage("full/ovr16/error_ovr"));
				break;
		}
		return new ComposedImage(images);
	}
}
