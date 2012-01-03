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
package org.eclipse.acceleo.internal.ide.ui.debug.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

/**
 * An Acceleo value represents the value of an Acceleo variable. It is shown in the Variables view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoValue extends AbstractDebugElement implements IValue {

	/**
	 * The real value.
	 */
	private Object value;

	/**
	 * The type of this Acceleo value.
	 */
	private int type;

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            is the debug target
	 * @param value
	 *            is the real value
	 * @param type
	 *            is the type of the value
	 */
	public AcceleoValue(IDebugTarget target, Object value, int type) {
		super(target);
		this.value = value;
		this.type = type;
	}

	/**
	 * Returns an image for the element, or null if a default image should be used.
	 * 
	 * @return an image or null
	 * @throws DebugException
	 *             when an issue occurs
	 */
	public Image getImage() throws DebugException {
		Image ret = null;
		if (type == AcceleoVariable.CLASS_TYPE) {
			ret = AcceleoUIActivator.getDefault().getImage("icons/debug/class.gif"); //$NON-NLS-1$
		} else if (type == AcceleoVariable.ATTRIBUTE_TYPE) {
			ret = AcceleoUIActivator.getDefault().getImage("icons/debug/attribute.gif"); //$NON-NLS-1$
		} else if (type == AcceleoVariable.REFERENCE_TYPE) {
			ret = AcceleoUIActivator.getDefault().getImage("icons/debug/reference.gif"); //$NON-NLS-1$
		} else {
			if (value instanceof Boolean) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/boolean.gif"); //$NON-NLS-1$
			} else if (value instanceof Double) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/double.gif"); //$NON-NLS-1$
			} else if (value instanceof EObject) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/class.gif"); //$NON-NLS-1$
			} else if (value instanceof Integer) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/int.gif"); //$NON-NLS-1$
			} else if (value instanceof String) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/string.gif"); //$NON-NLS-1$
			} else if (value instanceof List<?>) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/list.gif"); //$NON-NLS-1$
			} else if (value == null) {
				ret = AcceleoUIActivator.getDefault().getImage("icons/debug/null.gif"); //$NON-NLS-1$
			}
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		String ret = ""; //$NON-NLS-1$
		if (value != null) {
			if (value instanceof EObject) {
				ret = ((EObject)value).eClass().getName();
			} else {
				ret = value.getClass().getName().substring(value.getClass().getName().lastIndexOf('.') + 1);
			}
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getValueString()
	 */
	public String getValueString() throws DebugException {
		String ret = ""; //$NON-NLS-1$
		if (value != null) {
			if (value instanceof EObject) {
				EObject eObject = (EObject)value;
				ret = getEObjectValue(eObject);
			} else if (value instanceof Collection<?>) {
				ret = toSimpleString((Collection<?>)value);
			} else {
				ret = value.toString();
			}
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValue#isAllocated()
	 */
	public boolean isAllocated() throws DebugException {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValue#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		IVariable[] ret = null;
		if (value != null) {
			if (value instanceof EObject) {
				ret = this.computeEObjectVariables((EObject)value);
			} else if (value instanceof Collection<?>) {
				ret = this.computeCollectionVariables((Collection<?>)value);
			} else {
				ret = this.computeVariables(value);
			}
		} else {
			ret = new IVariable[0];
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValue#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		return getVariables().length > 0;
	}

	/**
	 * Computes the variables for an EObject.
	 * 
	 * @param object
	 *            the EObject
	 * @return the variables
	 */
	private IVariable[] computeEObjectVariables(EObject object) {
		List<IVariable> variables = new ArrayList<IVariable>();
		List<EStructuralFeature> structuralFeatures = object.eClass().getEAllStructuralFeatures();
		for (Iterator<EStructuralFeature> iterFeatures = structuralFeatures.iterator(); iterFeatures
				.hasNext();) {
			EStructuralFeature currentFeature = iterFeatures.next();
			String featureName = currentFeature.getName();
			Object featureValue = object.eGet(currentFeature);
			int referenceType;
			if (currentFeature instanceof EReference) {
				referenceType = AcceleoVariable.REFERENCE_TYPE;
			} else {
				referenceType = AcceleoVariable.ATTRIBUTE_TYPE;
			}
			if ((featureValue instanceof Collection<?> && ((Collection<?>)featureValue).size() > 0)
					|| (!(featureValue instanceof Collection<?>) && featureValue != null)) {
				variables
						.add(new AcceleoVariable(getDebugTarget(), featureName, featureValue, referenceType));
			}
		}
		return variables.toArray(new IVariable[variables.size()]);
	}

	/**
	 * Computes the variables for a collection.
	 * 
	 * @param collection
	 *            the collection
	 * @return the variables
	 * @throws DebugException
	 *             when an issue occurs
	 */
	private IVariable[] computeCollectionVariables(Collection<?> collection) throws DebugException {
		List<IVariable> variables = new ArrayList<IVariable>();
		for (Iterator<?> iterObjects = collection.iterator(); iterObjects.hasNext();) {
			Object element = iterObjects.next();
			if (element instanceof EObject) {
				variables.add(this.eObjectToVariable((EObject)element));
			} else if (element instanceof Collection<?>) {
				variables.add(new AcceleoVariable(getDebugTarget(), toSimpleString((Collection<?>)element),
						element, AcceleoVariable.DEFAULT_TYPE));
			} else {
				variables.add(new AcceleoVariable(getDebugTarget(),
						"" + element, element, AcceleoVariable.DEFAULT_TYPE)); //$NON-NLS-1$
			}
		}
		return variables.toArray(new IVariable[variables.size()]);
	}

	/**
	 * Computes the variables for an object.
	 * 
	 * @param object
	 *            the object
	 * @return an empty array
	 */
	private IVariable[] computeVariables(Object object) {
		List<IVariable> variables = new ArrayList<IVariable>();
		return variables.toArray(new IVariable[variables.size()]);
	}

	/**
	 * Transforms an EObject into a variable.
	 * 
	 * @param eObject
	 *            the EObject to transform
	 * @return the new variable
	 */
	private IVariable eObjectToVariable(EObject eObject) {
		String variableName = eObject.eClass().getName();
		return new AcceleoVariable(getDebugTarget(), variableName, eObject, AcceleoVariable.CLASS_TYPE);
	}

	/**
	 * Returns a value representing the given EObject.
	 * 
	 * @param eObject
	 *            the EObject
	 * @return a value representing the given EObject
	 */
	private String getEObjectValue(EObject eObject) {
		String variableName = eObject.eClass().getName();
		EStructuralFeature feature = eObject.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
		if (feature != null) {
			Object variableNameObject = eObject.eGet(feature);
			String variableValue;
			if (variableNameObject != null) {
				variableValue = variableNameObject.toString();
			} else {
				variableValue = ""; //$NON-NLS-1$
			}
			variableName = variableName + ':' + variableValue;
		}
		return variableName;
	}

	/**
	 * Transforms a collection into a string.
	 * 
	 * @param collection
	 *            the collection
	 * @return the string
	 */
	private String toSimpleString(Collection<?> collection) {
		List<String> strings = new ArrayList<String>(collection.size());
		Iterator<?> iterValues = collection.iterator();
		while (iterValues.hasNext()) {
			Object currentValue = iterValues.next();
			if (currentValue instanceof EObject) {
				EObject eObject = (EObject)currentValue;
				String typeName = eObject.eClass().getName();
				String label = ""; //$NON-NLS-1$
				EStructuralFeature structuralFeature = eObject.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
				if (structuralFeature != null) {
					Object nameObject = eObject.eGet(structuralFeature);
					if (nameObject != null) {
						label = nameObject.toString();
					} else {
						label = ""; //$NON-NLS-1$
					}
				}
				strings.add(typeName + ':' + label);
			} else if (currentValue instanceof Collection<?>) {
				strings.add("Collection"); //$NON-NLS-1$
			} else {
				String typeName = currentValue.getClass().getName().substring(
						currentValue.getClass().getName().lastIndexOf('.') + 1);
				String label;
				label = currentValue.toString();
				strings.add(typeName + ':' + label);
			}
		}
		StringBuffer buffer = new StringBuffer("["); //$NON-NLS-1$
		Iterator<String> iterStrings = strings.iterator();
		boolean first = true;
		while (iterStrings.hasNext()) {
			if (first) {
				first = false;
			} else {
				buffer.append(", "); //$NON-NLS-1$
			}
			buffer.append(iterStrings.next());
		}
		buffer.append(']');
		return buffer.toString();
	}
}
