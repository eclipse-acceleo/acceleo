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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ocl.utilities.ASTNode;

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

	/** The label provider in charge of computing the names of the elements we'll show in this result view. */
	private ILabelProvider labelProvider;

	/**
	 * Constructor.
	 * 
	 * @param labelProvider
	 *            The label provider in charge of computing the names of the elements we'll show in this
	 *            result view.
	 */
	public AcceleoResultContent(ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
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
	 * Gets the generated file for the given path. It returns null if the file isn't generated.
	 * 
	 * @param path
	 *            is the path of the generated file
	 * @return the generated file, or null if the file isn't generated
	 */
	public TraceabilityTargetFile getTargetFile(String path) {
		return targetFiles.get(path);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#fileGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void fileGenerated(AcceleoTextGenerationEvent event) {
		EObject traceabilityElement = event.getTraceabilityInformation();
		if (traceabilityElement instanceof GeneratedFile) {
			GeneratedFile generatedFile = (GeneratedFile)traceabilityElement;
			String targetPath = new Path(generatedFile.getPath()).toString();
			TraceabilityTargetFile targetFile = new TraceabilityTargetFile(targetPath);
			targetFiles.put(targetPath, targetFile);
			for (GeneratedText generatedText : generatedFile.getGeneratedRegions()) {
				EObject eObject = event.getSource();
				if (generatedText.getSourceElement() != null) {
					if (generatedText.getSourceElement().getModelElement() != null) {
						eObject = generatedText.getSourceElement().getModelElement();
					}
				}
				ASTNode astNode = event.getBlock();
				if (generatedText.getModuleElement() != null) {
					if (generatedText.getModuleElement().getModuleElement() instanceof ASTNode) {
						astNode = (ASTNode)generatedText.getModuleElement().getModuleElement();
					}
				}
				onGeneratedText(targetFile, generatedText, eObject, astNode);
			}
		}
	}

	/**
	 * This will be called on each text region of the generated file.
	 * 
	 * @param targetFile
	 *            is the target file
	 * @param generatedText
	 *            is the current text region
	 * @param eObject
	 *            is the model object to link with the text region
	 * @param astNode
	 *            is the template element to link with the text region
	 */
	private void onGeneratedText(TraceabilityTargetFile targetFile, GeneratedText generatedText,
			EObject eObject, ASTNode astNode) {
		if (generatedText.getEndOffset() > generatedText.getStartOffset() && eObject != null
				&& astNode != null) {
			TraceabilityModel model = getOrCreateModelInChildren(targetFile, eObject);
			TraceabilityModel templateRoot = getOrCreateModelInChildren(model, EcoreUtil
					.getRootContainer(astNode));
			EObject currentModuleElement = astNode;
			while (currentModuleElement != null && !(currentModuleElement instanceof ModuleElement)) {
				currentModuleElement = currentModuleElement.eContainer();
			}
			TraceabilityModel templateNode;
			if (currentModuleElement instanceof ModuleElement) {
				TraceabilityModel templateModuleElement = getOrCreateModelInChildren(templateRoot,
						currentModuleElement);
				if (currentModuleElement == astNode) {
					templateNode = templateModuleElement;
				} else {
					templateNode = getOrCreateModelInChildren(templateModuleElement, astNode);
				}
			} else {
				templateNode = getOrCreateModelInChildren(templateRoot, astNode);
			}
			TraceabilityRegion region = new TraceabilityRegion(generatedText.getStartOffset(), generatedText
					.getEndOffset()
					- generatedText.getStartOffset(), astNode);
			templateNode.getRegions().add(region);
			region.setParent(templateNode);
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
		TraceabilityModel newModel;
		if (eObject instanceof Module) {
			newModel = new TraceabilityTemplate((Module)eObject, labelProvider.getText(eObject));
		} else if (eObject instanceof TemplateExpression) {
			newModel = new TraceabilityTemplate((TemplateExpression)eObject, labelProvider.getText(eObject));
		} else {
			newModel = new TraceabilityModel(eObject, labelProvider.getText(eObject));
		}
		Set<TraceabilityModel> toMove = new CompactHashSet<TraceabilityModel>();
		for (TraceabilityModel sibling : parent.getChildren()) {
			if (ancestorOf(newModel.getEObject(), sibling.getEObject())) {
				toMove.add(sibling);
			}
		}
		for (TraceabilityModel next : toMove) {
			addInChildren(newModel, next);
			parent.getChildren().remove(next);
		}
		addInChildren(parent, newModel);
		return newModel;
	}

	/**
	 * Adds the child in the parent children list at the good position.
	 * 
	 * @param parent
	 *            is the parent of the child
	 * @param child
	 *            is the child to put in the children list of the parent
	 */
	private void addInChildren(TraceabilityContainer parent, TraceabilityModel child) {
		int index = -1;
		if (!(child instanceof TraceabilityTemplate)) {
			int i = 0;
			for (TraceabilityModel otherChild : parent.getChildren()) {
				if (otherChild instanceof TraceabilityTemplate) {
					index = i;
					break;
				}
				i++;
			}
		}
		if (index == -1) {
			parent.getChildren().add(child);
		} else {
			parent.getChildren().add(index, child);
		}
		child.setParent(parent);
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#generationEnd(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void generationEnd(AcceleoTextGenerationEvent event) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#listensToGenerationEnd()
	 */
	public boolean listensToGenerationEnd() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#filePathComputed(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void filePathComputed(AcceleoTextGenerationEvent event) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#textGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void textGenerated(AcceleoTextGenerationEvent event) {
	}

}
