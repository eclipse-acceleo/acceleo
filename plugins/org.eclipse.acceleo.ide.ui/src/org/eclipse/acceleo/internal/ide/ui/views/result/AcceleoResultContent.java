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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;

/**
 * This is the result view content. This class knows the generated files. It is notified whenever text is
 * generated from an Acceleo template element.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoResultContent implements IAcceleoTextGenerationListener {

	/**
	 * The generated files. The key is the full path of the file and the value its traceability data.
	 */
	private Map<String, TraceabilityTargetFile> targetFiles = new HashMap<String, TraceabilityTargetFile>();

	/**
	 * The current generated file. It changes when 'filePathComputed' is called.
	 */
	private TraceabilityTargetFile targetFile;

	/**
	 * The current offset in the current generated file.
	 */
	private int targetFileOffset;

	/**
	 * Constructor.
	 */
	public AcceleoResultContent() {
	}

	/**
	 * Gets the generated files.
	 * 
	 * @return the generated files
	 */
	public TraceabilityTargetFile[] getTargetFiles() {
		Collection<TraceabilityTargetFile> values = targetFiles.values();
		return values.toArray(new TraceabilityTargetFile[values.size()]);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#filePathComputed(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void filePathComputed(AcceleoTextGenerationEvent event) {
		String targetPath = new Path(event.getText()).toString();
		targetFile = new TraceabilityTargetFile(targetPath);
		targetFiles.put(targetPath, targetFile);
		targetFileOffset = 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#textGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void textGenerated(AcceleoTextGenerationEvent event) {
		if (event.getText() != null && event.getText().length() > 0 && targetFile != null) {
			if (event.getSource() != null) {
				TraceabilityModel model = getOrCreateModelInChildren(targetFile, event.getSource());
				TraceabilityRegion region = null;
				if (model.getRegions().size() > 0) {
					TraceabilityRegion lastRegion = model.getRegions().get(model.getRegions().size() - 1);
					if (event.getBlock() == lastRegion.getAstNode()
							&& event.getSource() == model.getEObject()
							&& lastRegion.getTargetFileOffset() + lastRegion.getTargetFileLength() == targetFileOffset) {
						region = lastRegion;
					}
				}
				// TODO JMU ENLARGE
				region = new TraceabilityRegion(targetFileOffset, event.getText().length(), event.getBlock());
				targetFileOffset += event.getText().length();
				model.getRegions().add(region);
				region.setParent(model);
			}
		}
	}

	/**
	 * Gets or creates an EMF model traceability data by visiting the children of the given node.
	 * 
	 * @param parent
	 *            is the node to browse to find or create an EMF model traceability data
	 * @param eObject
	 *            is the current EMF object synchronized with the generated text
	 * @return an EMF model traceability data
	 */
	private TraceabilityModel getOrCreateModelInChildren(TraceabilityContainer parent, EObject eObject) {
		for (TraceabilityModel child : parent.getChildren()) {
			TraceabilityModel result = null;
			if (child.getEObject() == eObject) {
				result = child;
			} else if (ancestorOf(child.getEObject(), eObject)) {
				result = getOrCreateModelInChildren(child, eObject);
			}
			if (result != null) {
				return result;
			}
		}
		TraceabilityModel newModel = new TraceabilityModel(eObject);
		Set<TraceabilityModel> toMove = new HashSet<TraceabilityModel>();
		for (TraceabilityModel sibling : parent.getChildren()) {
			if (ancestorOf(newModel.getEObject(), sibling.getEObject())) {
				toMove.add(sibling);
			}
		}
		for (TraceabilityModel next : toMove) {
			newModel.getChildren().add(next);
			next.setParent(newModel);
			parent.getChildren().remove(next);
		}
		parent.getChildren().add(newModel);
		newModel.setParent(parent);
		return newModel;
	}

	/**
	 * Indicates if the first object is an ancestor of the second one (in the model that contains the second
	 * object).
	 * 
	 * @param ancestor
	 *            is the ancestor to find
	 * @param object
	 *            is the object to test
	 * @return true if the first object is an ancestor of the second one
	 */
	private boolean ancestorOf(EObject ancestor, EObject object) {
		EObject current = object.eContainer();
		while (current != null) {
			if (current == ancestor) {
				return true;
			}
			current = current.eContainer();
		}
		return false;
	}

}
