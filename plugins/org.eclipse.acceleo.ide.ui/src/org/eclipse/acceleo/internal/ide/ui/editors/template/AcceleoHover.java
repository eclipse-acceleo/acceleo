/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.texteditor.MarkerAnnotation;

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
				try {
					msg = (String)((MarkerAnnotation)annotation).getMarker().getAttribute(IMarker.MESSAGE);
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
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
		} else {
			return null;
		}
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
			for (Iterator<?> it = annotationModel.getAnnotationIterator(); it.hasNext();) {
				Annotation annotation = (Annotation)it.next();
				Position position = annotationModel.getPosition(annotation);
				try {
					if (document != null && document.getLineOfOffset(position.offset) == lineNumber) {
						annotations.add(annotation);
					}
				} catch (BadLocationException e) {
					// continue
				}
			}
		}
		return annotations;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHover#getHoverInfo(org.eclipse.jface.text.ITextViewer,
	 *      org.eclipse.jface.text.IRegion)
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null && editor != null && editor.getContent() != null) {
			ASTNode astNode = editor.getContent().getASTNode(hoverRegion.getOffset(),
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
		StringBuffer res = new StringBuffer();
		res.append("["); //$NON-NLS-1$
		res.append(eObject.eClass().getName());
		res.append("]  "); //$NON-NLS-1$
		final String variableSep = " : "; //$NON-NLS-1$
		if (eObject instanceof Variable) {
			res.append(((Variable)eObject).getName());
			if (((Variable)eObject).getType() != null) {
				res.append(variableSep);
				res.append(((Variable)eObject).getType().getName());
			}
		} else if (eObject instanceof Module) {
			res.append(((Module)eObject).getName());
		} else if (eObject instanceof ModuleElement) {
			ModuleElement element = (ModuleElement)eObject;
			res.append(element.getName());
			if (element instanceof Template) {
				res.append('(');
				boolean first = true;
				for (Variable iVariable : ((Template)element).getParameter()) {
					if (first) {
						first = false;
					} else {
						res.append(',');
					}
					if (iVariable.getType() != null) {
						res.append(iVariable.getType().getName());
					}
				}
				res.append(')');
			} else if (element instanceof Macro) {
				res.append('(');
				boolean first = true;
				for (Variable iVariable : ((Macro)element).getParameter()) {
					if (first) {
						first = false;
					} else {
						res.append(',');
					}
					if (iVariable.getType() != null) {
						res.append(iVariable.getType().getName());
					}
				}
				res.append(')');
				if (((Macro)element).getType() != null) {
					res.append(variableSep);
					res.append(((Macro)element).getType().getName());
				}
			} else if (element instanceof Query) {
				res.append('(');
				boolean first = true;
				for (Variable iVariable : ((Query)element).getParameter()) {
					if (first) {
						first = false;
					} else {
						res.append(',');
					}
					if (iVariable.getType() != null) {
						res.append(iVariable.getType().getName());
					}
				}
				res.append(')');
				if (((Query)element).getType() != null) {
					res.append(variableSep);
					res.append(((Query)element).getType().getName());
				}
			}
		} else if (eObject instanceof EOperation) {
			EOperation eOperation = (EOperation)eObject;
			res.append(eOperation.getName());
			res.append('(');
			boolean first = true;
			for (EParameter eParameter : eOperation.getEParameters()) {
				if (first) {
					first = false;
				} else {
					res.append(',');
				}
				if (eParameter.getEType() != null) {
					res.append(eParameter.getEType().getName());
				}
			}
			res.append(')');
			if (eOperation.getEType() != null) {
				res.append(variableSep);
				res.append(eOperation.getEType().getName());
			}
		} else if (eObject instanceof EStructuralFeature) {
			res.append(((EStructuralFeature)eObject).getName());
			if (((EStructuralFeature)eObject).getEType() != null) {
				res.append(variableSep);
				res.append(((EStructuralFeature)eObject).getEType().getName());
				res.append(" ["); //$NON-NLS-1$
				res.append(((EStructuralFeature)eObject).getLowerBound());
				res.append(".."); //$NON-NLS-1$
				if (((EStructuralFeature)eObject).getUpperBound() == -1) {
					res.append("*"); //$NON-NLS-1$
				} else {
					res.append(((EStructuralFeature)eObject).getUpperBound());
				}
				res.append("]"); //$NON-NLS-1$
			}
		} else if (eObject instanceof EClass) {
			res.append(((EClass)eObject).getName());
		}
		if (res.length() > 0) {
			return res.toString();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		if (offset > -1) {
			return new Region(offset, 0);
		} else {
			return null;
		}
	}

}
