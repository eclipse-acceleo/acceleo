/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * Text hover for an annotation in the template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoHover implements IAnnotationHover, ITextHover {

	/**
	 * The template editor.
	 */
	protected AcceleoEditor editor;

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            is the editor
	 */
	public AcceleoHover(AcceleoEditor editor) {
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.IAnnotationHover#getHoverInfo(org.eclipse.jface.text.source.ISourceViewer,
	 *      int)
	 */
	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		return getHoverText(sourceViewer.getAnnotationModel(), sourceViewer.getDocument(), lineNumber);
	}

	/**
	 * Hover text for an annotation at the given line.
	 * 
	 * @param model
	 *            is the annotation model
	 * @param document
	 *            is the current document
	 * @param lineNumber
	 *            is the line number
	 * @return hover text of the annotation
	 */
	protected String getHoverText(IAnnotationModel model, IDocument document, int lineNumber) {
		StringBuffer text = null;
		Iterator<Annotation> annotations = findAnnotations(model, document, lineNumber).iterator();
		while (annotations.hasNext()) {
			Annotation annotation = annotations.next();
			String msg = null;
			if (annotation instanceof MarkerAnnotation) {
				MarkerAnnotation markerAnnotation = (MarkerAnnotation)annotation;
				IMarker marker = markerAnnotation.getMarker();
				if (marker != null) {
					msg = MarkerUtilities.getMessage(marker);
				}
			}
			if (msg != null) {
				if (text == null) {
					text = new StringBuffer(msg);
				} else {
					text.append('\n');
					text.append(msg);
				}
			}
		}

		if (text != null) {
			return text.toString();
		}
		return null;
	}

	/**
	 * Returns all the annotations at the given line.
	 * 
	 * @param model
	 *            is the annotation model
	 * @param document
	 *            is the current document
	 * @param lineNumber
	 *            is the line number
	 * @return all the annotations
	 */
	private List<Annotation> findAnnotations(IAnnotationModel model, IDocument document, int lineNumber) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		IAnnotationModel annotationModel;
		if (model == null && editor != null) {
			annotationModel = editor.getDocumentProvider().getAnnotationModel(editor.getEditorInput());
		} else {
			annotationModel = model;
		}
		if (annotationModel != null) {
			synchronized(getLockObject(annotationModel)) {
				for (Iterator<?> it = annotationModel.getAnnotationIterator(); it.hasNext();) {
					Annotation annotation = (Annotation)it.next();
					Position position = annotationModel.getPosition(annotation);
					try {
						if (document != null && position != null
								&& document.getLineOfOffset(position.offset) == lineNumber) {
							annotations.add(annotation);
						}
					} catch (BadLocationException e) {
						// continue
					}
				}
			}
		}
		return annotations;
	}

	/**
	 * Returns the lock object for the given annotation model.
	 * 
	 * @param annotationModel
	 *            the annotation model
	 * @return the annotation model's lock object
	 */
	private Object getLockObject(final IAnnotationModel annotationModel) {
		if (annotationModel instanceof ISynchronizable) {
			final Object lock = ((ISynchronizable)annotationModel).getLockObject();
			if (lock != null) {
				return lock;
			}
		}
		return annotationModel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHover#getHoverInfo(org.eclipse.jface.text.ITextViewer,
	 *      org.eclipse.jface.text.IRegion)
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null && editor != null && editor.getContent() != null) {
			ASTNode astNode = editor.getContent().getResolvedASTNode(hoverRegion.getOffset(),
					hoverRegion.getOffset() + hoverRegion.getLength());
			if (astNode != null) {
				EObject eObject = OpenDeclarationUtils.findDeclarationFromAST(astNode);
				if (eObject instanceof IteratorExp && editor.getContent().getOCLEnvironment() != null) {
					eObject = OpenDeclarationUtils.findIteratorEOperation(editor.getContent()
							.getOCLEnvironment(), (IteratorExp)eObject);
				}
				if (eObject != null) {
					return getInfo(eObject);
				}
			}
		}
		return null;
	}

	/**
	 * Gets the info text for the given eObject.
	 * 
	 * @param eObject
	 *            is an object of the MTL syntax
	 * @return the text
	 */
	private String getInfo(EObject eObject) {
		Documentation documentation = null;
		if (eObject instanceof Template) {
			documentation = ((Template)eObject).getDocumentation();
		}
		if (eObject instanceof Module) {
			documentation = ((Module)eObject).getDocumentation();
		}
		if (eObject instanceof Query) {
			documentation = ((Query)eObject).getDocumentation();
		}
		if (eObject instanceof Macro) {
			documentation = ((Macro)eObject).getDocumentation();
		}
		if (documentation != null) {
			return AcceleoUIDocumentationUtils.getTextFrom(documentation);
		}
		return AcceleoUIDocumentationUtils.getSignatureFrom(eObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		if (offset > -1) {
			return new Region(offset, 0);
		}
		return null;
	}

}
