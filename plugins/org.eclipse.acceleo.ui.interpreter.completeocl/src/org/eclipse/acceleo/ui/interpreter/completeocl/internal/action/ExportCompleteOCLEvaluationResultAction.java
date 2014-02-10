package org.eclipse.acceleo.ui.interpreter.completeocl.internal.action;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.acceleo.ui.interpreter.completeocl.IEvaluationExporter;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.internal.CompleteOCLEvaluator;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.util.Pivotable;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;

public class ExportCompleteOCLEvaluationResultAction extends Action {
	private final XtextResource resource;

	private final Resource target;

	private final MetaModelManager metaModelManager;

	public ExportCompleteOCLEvaluationResultAction(XtextResource resource, Resource target,
			MetaModelManager metaModelManager) {
		super("Export Evaluation Result", IAction.AS_PUSH_BUTTON);
		setToolTipText("Evaluate the current compilation result against the currently selected resource");
		this.resource = resource;
		this.target = target;
		this.metaModelManager = metaModelManager;
	}

	@Override
	public void run() {
		final FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		final String selectedPath = fileDialog.open();

		if (selectedPath != null) {
			// FIXME!
			final IEvaluationExporter exporter = new IEvaluationExporter() {
				public void export(String outputPath, OCLElement resultRoot) {
					Resource res = new XMIResourceImpl(URI.createFileURI(outputPath));
					res.getContents().add(resultRoot);
					try {
						res.save(new HashMap<String, Object>());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};

			final IParseResult parseResult = resource.getParseResult();

			EvaluationResult evalutionResult = null;
			if (parseResult != null && parseResult.getRootASTElement() instanceof Pivotable) {
				final Element pivotElement = ((Pivotable)parseResult.getRootASTElement()).getPivot();

				if (pivotElement instanceof Root) {
					evalutionResult = new CompleteOCLEvaluator(metaModelManager).evaluateCompleteOCLElement(
							pivotElement, target);
				}
			}

			if (evalutionResult != null
					&& (evalutionResult.getStatus() == null || evalutionResult.getStatus().isOK())) {
				if (evalutionResult.getEvaluationResult() instanceof OCLElement) {
					exporter.export(selectedPath, (OCLElement)evalutionResult.getEvaluationResult());
				}
			}
		}
	}

	@Override
	public boolean isEnabled() {
		return resource != null && !resource.getParseResult().hasSyntaxErrors() && target != null;
	}
}
