package org.eclipse.acceleo.ui.interpreter.completeocl;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;

public interface IEvaluationExporter {
	public void export(String outputPath, OCLElement resultRoot);
}
