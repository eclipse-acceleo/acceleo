/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * EMTL/XMI Resource. The position of each AST node is serialized in a specific annotation. We compute the
 * specific annotation content before saving the resource. We visit the annotation content after loading the
 * resource to set the transient positions of every AST nodes.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class EMtlResourceImpl extends XMIResourceImpl {

	/**
	 * The specific annotation used to store the positions of the AST nodes.
	 */
	private static final String POSITIONS_ANNOTATION_NAME = "positions"; //$NON-NLS-1$

	/**
	 * Constructor.
	 * 
	 * @param uri
	 *            is the URI of the resource
	 */
	public EMtlResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doLoad(java.io.InputStream, java.util.Map)
	 */
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		super.doLoad(inputStream, options);
		EAnnotation positions = getPositions(false);
		if (positions != null) {
			restorePositions(positions);
			getContents().remove(positions);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doSave(java.io.OutputStream, java.util.Map)
	 */
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		EAnnotation positions = getPositions(true);
		savePositions(positions);
		try {
			super.doSave(outputStream, options);
		} finally {
			getContents().remove(positions);
		}
	}

	/**
	 * Computes the specific annotation content before saving the resource. It will store every AST nodes
	 * positions.
	 * 
	 * @param positions
	 *            is the annotation root element where to store the positions
	 */
	private void savePositions(EAnnotation positions) {
		Iterator<EObject> contents = getContents().iterator();
		while (contents.hasNext()) {
			EObject content = contents.next();
			if (content instanceof Module) {
				Module eModule = (Module)content;
				TreeIterator<EObject> eAllContents = eModule.eAllContents();
				while (eAllContents.hasNext()) {
					EObject eObject = eAllContents.next();
					if (eObject instanceof ASTNode) {
						int start = ((ASTNode)eObject).getStartPosition();
						int end = ((ASTNode)eObject).getEndPosition();
						savePosition(positions, (ASTNode)eObject, start, end);
					}
				}
			}
		}
	}

	/**
	 * Computes the specific annotation content before saving the resource. It will store the given AST node
	 * position.
	 * 
	 * @param positions
	 *            is the annotation root element where to store the positions
	 * @param node
	 *            the node to store
	 * @param start
	 *            the real offset of the AST node
	 * @param end
	 *            the last character position of the AST node
	 */
	private void savePosition(EAnnotation positions, ASTNode node, int start, int end) {
		EAnnotation position = EcoreFactory.eINSTANCE.createEAnnotation();
		position.setSource(POSITIONS_ANNOTATION_NAME + "." + positions.getEAnnotations().size()); //$NON-NLS-1$
		position.getReferences().add(node);
		position.getDetails().put("start", String.valueOf(start)); //$NON-NLS-1$
		position.getDetails().put("end", String.valueOf(end)); //$NON-NLS-1$
		positions.getEAnnotations().add(position);
	}

	/**
	 * Visitor on the specific annotation content after loading the resource to set the transient positions of
	 * every AST nodes.
	 * 
	 * @param positions
	 *            is the annotation root element where to read the positions
	 */
	private void restorePositions(EAnnotation positions) {
		Iterator<EAnnotation> positionsIt = positions.getEAnnotations().iterator();
		while (positionsIt.hasNext()) {
			EAnnotation position = positionsIt.next();
			ASTNode node = (ASTNode)position.getReferences().get(0);
			if (node.getStartPosition() == -1 && node.getEndPosition() == -1) {
				int start = Integer.parseInt(position.getDetails().get("start")); //$NON-NLS-1$
				int end = Integer.parseInt(position.getDetails().get("end")); //$NON-NLS-1$
				node.setStartPosition(start);
				node.setEndPosition(end);
			}
		}
	}

	/**
	 * Gets or creates the specific annotation used to store the positions of the AST nodes.
	 * 
	 * @param create
	 *            indicates if the annotation must be created
	 * @return the specific annotation
	 */
	private EAnnotation getPositions(boolean create) {
		EAnnotation positions = null;
		Iterator<EObject> contents = getContents().iterator();
		while (positions == null && contents.hasNext()) {
			EObject content = contents.next();
			if (content instanceof EAnnotation) {
				if (POSITIONS_ANNOTATION_NAME.equals(((EAnnotation)content).getSource())) {
					positions = (EAnnotation)content;
				}
			}
		}
		if (positions == null && create) {
			positions = EcoreFactory.eINSTANCE.createEAnnotation();
			positions.setSource(POSITIONS_ANNOTATION_NAME);
			getContents().add(positions);
		}
		return positions;
	}

}
