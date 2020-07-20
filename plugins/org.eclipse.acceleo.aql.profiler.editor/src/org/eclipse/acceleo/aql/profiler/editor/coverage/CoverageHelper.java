package org.eclipse.acceleo.aql.profiler.editor.coverage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileResource;
import org.eclipse.acceleo.aql.profiler.editor.AcceleoEnvResourceFactory;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class CoverageHelper {

	private static final String FULL_COVERAGE_ANNOTATION_ID = "org.eclipse.acceleo.aql.fullCoverageAnnotation"; //$NON-NLS-1$

	private static final String NO_COVERAGE_ANNOTATION_ID = "org.eclipse.acceleo.aql.noCoverageAnnotation"; //$NON-NLS-1$

	private ProfileResource profileResource;

	private Map<Module, CoverageReport> cache;

	private Map<Module, IFile> allModules;

	private class CoverageReport {
		Set<EObject> allElements;

		Set<EObject> usedElements;
	}

	public CoverageHelper(ProfileResource profileResource, AcceleoEnvResourceFactory resourceFactory) {
		super();
		this.profileResource = profileResource;
		this.cache = new HashMap<Module, CoverageHelper.CoverageReport>();
		this.allModules = new HashMap<Module, IFile>();
		for (ProfileEntry entry : profileResource.getEntries()) {
			if (entry.getMonitored() instanceof Module) {
				Module module = (Module)entry.getMonitored();
				IFile sourceFile = resourceFactory.getSourceFile(module);
				allModules.put(module, sourceFile);
			}
		}
	}

	public int computeUsage(Module module) {
		CoverageReport report = getCoverageReport(module);
		return report.usedElements.size() * 100 / report.allElements.size();
	}

	private CoverageReport getCoverageReport(Module module) {
		CoverageReport report = cache.get(module);
		if (report == null) {
			report = new CoverageReport();
			// Compute all relevant elements
			report.allElements = new HashSet<EObject>();
			for (Iterator<EObject> iterator = module.eAllContents(); iterator.hasNext();) {
				EObject moduleElement = iterator.next();
				if (isRelevant(moduleElement)) {
					report.allElements.add(moduleElement);
				}
			}

			// Compute all elements in use & in the previous list
			report.usedElements = new HashSet<EObject>();
			for (Iterator<EObject> iterator = profileResource.eAllContents(); iterator.hasNext();) {
				EObject profileEntry = iterator.next();
				if (profileEntry instanceof ProfileEntry) {
					EObject monitored = ((ProfileEntry)profileEntry).getMonitored();
					if (report.allElements.contains(monitored)) {
						report.usedElements.add(monitored);
					}
				}
			}
		}
		return report;
	}

	private static boolean isRelevant(EObject moduleElement) {
		return !(moduleElement instanceof Comment || moduleElement instanceof CommentBody
				|| moduleElement instanceof Import || moduleElement instanceof ModuleReference
				|| moduleElement instanceof Expression || moduleElement instanceof Variable
				|| moduleElement instanceof VariableDeclaration);
	}

	public void attach(ITextEditor editor, Module module) {
		IDocumentProvider provider = editor.getDocumentProvider();
		if (provider == null) {
			return;
		}
		IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());
		if (!(model instanceof IAnnotationModelExtension)) {
			return;
		}
		IAnnotationModelExtension modelex = (IAnnotationModelExtension)model;
		IAnnotationModel coverageModel = modelex.getAnnotationModel(this);
		if (coverageModel == null || cache.get(module) == null) {
			// There is no annotation or the current annotations are out of sync
			modelex.removeAnnotationModel(this);
			modelex.addAnnotationModel(this, createAnnotationModel(module));
		}
	}

	private IAnnotationModel createAnnotationModel(Module module) {
		AnnotationModel res = new AnnotationModel();
		AcceleoAstResult astResult = module.getAst();
		CoverageReport report = getCoverageReport(module);
		for (EObject element : report.allElements) {
			if (element instanceof ASTNode) {
				ASTNode astNode = (ASTNode)element;
				int offset = astResult.getStartPosition(astNode);
				int length = astResult.getEndPosition(astNode) - offset;
				if (offset > 0 && length > 0) {
					Position position = new Position(offset, length);
					if (report.usedElements.contains(element)) {
						res.addAnnotation(new Annotation(FULL_COVERAGE_ANNOTATION_ID, false, null), position);
					} else {
						res.addAnnotation(new Annotation(NO_COVERAGE_ANNOTATION_ID, false, null), position);
					}
				}
			}
		}
		return res;
	}
}
