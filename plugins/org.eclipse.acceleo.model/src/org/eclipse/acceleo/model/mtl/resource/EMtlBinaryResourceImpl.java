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
package org.eclipse.acceleo.model.mtl.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.utils.AcceleoASTNodeAdapter;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * EMTL/Binary Resource. The position of each AST node is serialized in a specific annotation. We compute the
 * specific annotation content before saving the resource. We visit the annotation content after loading the
 * resource to set the transient positions of every AST nodes.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class EMtlBinaryResourceImpl extends BinaryResourceImpl {
	/**
	 * The specific annotation used to store the positions of the AST nodes.
	 */
	private static final String POSITIONS_ANNOTATION_NAME = "positions"; //$NON-NLS-1$

	/** Holds the prefix of all temporary variables' names. */
	private static final String VARIABLE_PREFIX = "temp"; //$NON-NLS-1$

	/** This will hold all variables names when fixing ambiguities. */
	private List<String> variableNames;

	/**
	 * Indicates if the position should be trimmed.
	 * 
	 * @since 3.2
	 */
	private boolean trimPosition;

	/**
	 * Constructor.
	 * 
	 * @param uri
	 *            is the URI of the resource
	 */
	public EMtlBinaryResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doLoad(java.io.InputStream, java.util.Map)
	 */
	@Override
	public void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		Map<Object, Object> actualOptions = new HashMap<Object, Object>();
		if (options != null) {
			actualOptions.putAll(options);
		}
		super.doLoad(inputStream, actualOptions);

		if (!trimPosition) {
			EAnnotation positions = getPositions(false);
			if (positions != null) {
				restorePositions(positions);
				getContents().remove(positions);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doSave(java.io.OutputStream, java.util.Map)
	 */
	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		// deactivate the notifications to work with EMF transaction
		eSetDeliver(false);

		EAnnotation positions = null;
		if (!trimPosition) {
			positions = getPositions(true);
			fixVariablesAndPositions(positions);
		}
		try {
			super.doSave(outputStream, options);
		} finally {
			if (!trimPosition && positions != null) {
				getContents().remove(positions);
			}
		}

		// re-activate the notifications
		eSetDeliver(true);
	}

	/**
	 * Iterates over the whole content tree of the current resource and fixes {@link ASTNode}s' positions and
	 * temporary Variable names.
	 * 
	 * @param positions
	 *            is the annotation root element where to store the positions
	 */
	private void fixVariablesAndPositions(EAnnotation positions) {
		variableNames = new ArrayList<String>();
		Iterator<EObject> contentsIterator = getContents().iterator();
		while (contentsIterator.hasNext()) {
			EObject content = contentsIterator.next();
			if (content instanceof Module) {
				Module eModule = (Module)content;
				TreeIterator<EObject> eAllContents = eModule.eAllContents();
				while (eAllContents.hasNext()) {
					EObject eObject = eAllContents.next();
					fixVariableAmbiguities(eObject);
					savePositions(eObject, positions);
				}
			}
		}
		variableNames.clear();
		variableNames = null;
	}

	/**
	 * Alters names of temporary variables to unique names.
	 * 
	 * @param element
	 *            Element which name is to be changed if it is a temporary variable.
	 */
	private void fixVariableAmbiguities(EObject element) {
		if (element instanceof Variable<?, ?> && ((Variable<?, ?>)element).getName() != null
				&& ((Variable<?, ?>)element).getName().startsWith(VARIABLE_PREFIX)) {
			final String varName = ((Variable<?, ?>)element).getName();
			if (varName != null && !varName.matches("temp\\d+")) { //$NON-NLS-1$
				return;
			}
			if (variableNames.contains(varName)) {
				final String lastName = variableNames.get(variableNames.size() - 1);
				int lastIndex = Integer.valueOf(lastName.substring(VARIABLE_PREFIX.length())).intValue();
				do {
					lastIndex++;
				} while (variableNames.contains(VARIABLE_PREFIX + lastIndex));
				((Variable<?, ?>)element).setName(VARIABLE_PREFIX + lastIndex);
				variableNames.add(VARIABLE_PREFIX + lastIndex);
			} else {
				variableNames.add(varName);
			}
		}
	}

	/**
	 * Computes the specific annotation content before saving the resource. It will store every AST nodes
	 * positions.
	 * 
	 * @param element
	 *            Element which position is to be saved.
	 * @param positions
	 *            is the annotation root element where to store the positions
	 */
	private void savePositions(EObject element, EAnnotation positions) {
		if (element instanceof ASTNode) {
			int start = ((ASTNode)element).getStartPosition();
			int end = ((ASTNode)element).getEndPosition();
			Adapter adapter = EcoreUtil.getAdapter(element.eAdapters(), AcceleoASTNodeAdapter.class);
			int line = 0;
			if (adapter instanceof AcceleoASTNodeAdapter) {
				line = ((AcceleoASTNodeAdapter)adapter).getLine();
			}
			savePosition(positions, (ASTNode)element, start, end, line);
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
	 * @param line
	 *            the number of the line on which this AST node starts.
	 */
	private void savePosition(EAnnotation positions, ASTNode node, int start, int end, int line) {
		EAnnotation position = EcoreFactory.eINSTANCE.createEAnnotation();
		position.setSource(POSITIONS_ANNOTATION_NAME + "." + positions.getEAnnotations().size()); //$NON-NLS-1$
		position.getReferences().add(node);
		position.getDetails().put("start", String.valueOf(start)); //$NON-NLS-1$
		position.getDetails().put("end", String.valueOf(end)); //$NON-NLS-1$
		position.getDetails().put("line", String.valueOf(line)); //$NON-NLS-1$
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
				try {
					int start = Integer.parseInt(position.getDetails().get("start")); //$NON-NLS-1$
					int end = Integer.parseInt(position.getDetails().get("end")); //$NON-NLS-1$
					String lineValue = position.getDetails().get("line"); //$NON-NLS-1$
					int line = 0;
					if (lineValue != null) {
						line = Integer.parseInt(lineValue);
					}
					node.setStartPosition(start);
					node.setEndPosition(end);
					node.eAdapters().add(new AcceleoASTNodeAdapter(line));
				} catch (NumberFormatException e) {
					// odd but not critical
				}
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
		Iterator<EObject> children = getContents().iterator();
		while (positions == null && children.hasNext()) {
			EObject child = children.next();
			if (child instanceof EAnnotation) {
				if (POSITIONS_ANNOTATION_NAME.equals(((EAnnotation)child).getSource())) {
					positions = (EAnnotation)child;
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

	/**
	 * Sets the boolean indicating if the position should be trimmed.
	 * 
	 * @param trimPosition
	 *            <code>true</code> to trim the position, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public void setTrimPosition(boolean trimPosition) {
		this.trimPosition = trimPosition;
	}
}
