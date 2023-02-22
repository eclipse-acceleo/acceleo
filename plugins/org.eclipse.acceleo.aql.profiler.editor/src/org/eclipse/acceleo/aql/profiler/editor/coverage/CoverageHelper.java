/*******************************************************************************
 * Copyright (c) 2020, 2023 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.editor.coverage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Block;
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * A helper dedicated to coverage computations.
 * 
 * @author wpiers
 */
public class CoverageHelper {

	/**
	 * The coverage annotation ID.
	 */
	private static final String FULL_COVERAGE_ANNOTATION_ID = "org.eclipse.acceleo.aql.fullCoverageAnnotation"; //$NON-NLS-1$

	/**
	 * The no coverage annotation ID.
	 */
	private static final String NO_COVERAGE_ANNOTATION_ID = "org.eclipse.acceleo.aql.noCoverageAnnotation"; //$NON-NLS-1$

	/**
	 * The profile resource giving coverage informations.
	 */
	private ProfileResource profileResource;

	/**
	 * A cache of computed coverage reports.
	 */
	private Map<Module, CoverageReport> cache;

	/**
	 * The currently annotated modules.
	 */
	private Set<ITextEditor> textEditors;

	/**
	 * A coverage report.
	 */
	private class CoverageReport {
		Set<EObject> allElements;

		Set<EObject> usedElements;
	}

	/**
	 * Creates a coverage helper from the given profile resource & acceleo env factory.
	 * 
	 * @param profileResource
	 *            the profile resource
	 * @param resourceFactory
	 *            the resource factory
	 */
	public CoverageHelper(ProfileResource profileResource, AcceleoEnvResourceFactory resourceFactory) {
		super();
		this.profileResource = profileResource;
		this.textEditors = new HashSet<>();
		this.cache = new HashMap<Module, CoverageHelper.CoverageReport>();
	}

	/**
	 * Computes the global usage of a module.
	 * 
	 * @param module
	 *            the module
	 * @return the module usage in percentage
	 */
	public int computeUsage(Module module) {
		CoverageReport report = getCoverageReport(module);
		return report.usedElements.size() * 100 / report.allElements.size();
	}

	/**
	 * Computes the coverage report for a given module.
	 * 
	 * @param module
	 *            the module
	 * @return the coverage report
	 */
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

	/**
	 * Returns <true> if an element is relevant for the coverage computation.
	 * 
	 * @param moduleElement
	 *            the module element to check
	 * @return <true> if the element is involved in the coverage
	 */
	private static boolean isRelevant(EObject moduleElement) {
		return moduleElement instanceof ASTNode && !(moduleElement instanceof Comment
				|| moduleElement instanceof CommentBody || moduleElement instanceof Import
				|| moduleElement instanceof ModuleReference || moduleElement instanceof Block
				|| moduleElement instanceof Variable);
	}

	/**
	 * Attaches annotations from the given module to the given editor.
	 * 
	 * @param editor
	 *            the editor
	 * @param module
	 *            the module
	 */
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
		modelex.removeAnnotationModel(this.getClass());
		modelex.addAnnotationModel(this.getClass(), createAnnotationModel(module));
		textEditors.add(editor);
	}

	/**
	 * Creates the annotations related to a given module.
	 * 
	 * @param module
	 *            the module
	 * @return the annotation model
	 */
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

	/**
	 * Clears annotations in opened editors.
	 */
	public void clearAnnotations() {
		for (ITextEditor editor : textEditors) {
			IDocumentProvider provider = editor.getDocumentProvider();
			if (provider == null) {
				return;
			}
			IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());
			if (!(model instanceof IAnnotationModelExtension)) {
				return;
			}
			IAnnotationModelExtension modelex = (IAnnotationModelExtension)model;
			modelex.removeAnnotationModel(this.getClass());
			modelex.addAnnotationModel(this.getClass(), new AnnotationModel());
		}
	}
}
