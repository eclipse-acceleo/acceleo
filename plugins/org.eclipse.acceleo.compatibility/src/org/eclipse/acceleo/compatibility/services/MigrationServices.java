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
package org.eclipse.acceleo.compatibility.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * This will be used by migrated Acceleo.org scripts when they were using problematic services.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class MigrationServices {
	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return EObject corresponding to the receiver.
	 */
	public EObject adaptToEObject(Object receiver) {
		final EObject adapted;

		if (receiver instanceof EObject) {
			adapted = (EObject)receiver;
		} else if (receiver instanceof Collection<?> && ((Collection<?>)receiver).size() > 0) {
			adapted = (EObject)((Collection<?>)receiver).iterator().next();
		} else {
			// String, Boolean, Int, Double, empty list
			adapted = null;
		}

		return adapted;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return List corresponding to the receiver.
	 */
	public List<?> adaptToList(Object receiver) {
		final List<Object> adapted = new ArrayList<Object>();

		if (receiver != null) {
			adapted.add(receiver);
		}

		return adapted;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return String corresponding to the receiver.
	 */
	public String adaptToString(Object receiver) {
		String adapted = ""; //$NON-NLS-1$

		if (receiver != null) {
			adapted = receiver.toString();
		}

		return adapted;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return Boolean corresponding to the receiver.
	 */
	public Boolean adaptToBoolean(Object receiver) {
		final Boolean adapted;

		if (receiver instanceof EObject) {
			adapted = Boolean.TRUE;
		} else if (receiver instanceof Collection<?>) {
			adapted = ((Collection<?>)receiver).size() > 0;
		} else if (receiver instanceof String) {
			adapted = "true".equalsIgnoreCase((String)receiver); //$NON-NLS-1$
		} else if (receiver instanceof Boolean) {
			adapted = (Boolean)receiver;
		} else if (receiver instanceof Integer) {
			adapted = ((Integer)receiver).intValue() > 0;
		} else if (receiver instanceof Double) {
			adapted = ((Double)receiver).doubleValue() > 0d;
		} else {
			adapted = Boolean.FALSE;
		}

		return adapted;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return Integer corresponding to the receiver.
	 */
	public Integer adaptToInteger(Object receiver) {
		final Integer adapted;

		if (receiver instanceof EObject) {
			adapted = Integer.valueOf(1);
		} else if (receiver instanceof Collection<?>) {
			adapted = Integer.valueOf(((Collection<?>)receiver).size());
		} else if (receiver instanceof String) {
			adapted = Integer.parseInt((String)receiver);
		} else if (receiver instanceof Boolean && ((Boolean)receiver).booleanValue()) {
			adapted = Integer.valueOf(1);
		} else if (receiver instanceof Integer) {
			adapted = (Integer)receiver;
		} else if (receiver instanceof Double) {
			adapted = ((Double)receiver).intValue();
		} else {
			// null, false
			adapted = Integer.valueOf(0);
		}

		return adapted;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return Double corresponding to the receiver.
	 */
	public Double adaptToDouble(Object receiver) {
		final Double adapted;

		if (receiver instanceof EObject) {
			adapted = Double.valueOf(1d);
		} else if (receiver instanceof Collection<?>) {
			adapted = Double.valueOf(Integer.valueOf(((Collection<?>)receiver).size()).doubleValue());
		} else if (receiver instanceof String) {
			adapted = Double.parseDouble((String)receiver);
		} else if (receiver instanceof Boolean && ((Boolean)receiver).booleanValue()) {
			adapted = Double.valueOf(1d);
		} else if (receiver instanceof Integer) {
			adapted = Double.valueOf(((Integer)receiver).doubleValue());
		} else if (receiver instanceof Double) {
			adapted = (Double)receiver;
		} else {
			// null, false
			adapted = Double.valueOf(0d);
		}

		return adapted;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return Object corresponding to the receiver.
	 */
	public Object adaptToENode(Object receiver) {
		return receiver;
	}

	/**
	 * Mimics the behavior of the "adapt" service from Acceleo.org.
	 * 
	 * @param receiver
	 *            The object that is to be adapted
	 * @return List corresponding to the receiver.
	 */
	public List<?> adaptToENodeList(Object receiver) {
		return adaptToList(receiver);
	}
}
