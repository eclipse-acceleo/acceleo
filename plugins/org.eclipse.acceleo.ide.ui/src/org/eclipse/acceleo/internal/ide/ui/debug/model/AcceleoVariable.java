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

import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;

/**
 * An Acceleo variable in a variable of an Acceleo Stack Frame. They are shown in the Variables view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoVariable extends AbstractDebugElement implements IVariable, Comparable<AcceleoVariable> {

	/**
	 * The default type.
	 */
	protected static final int DEFAULT_TYPE = 0;

	/**
	 * The class type.
	 */
	protected static final int CLASS_TYPE = 1;

	/**
	 * The reference type.
	 */
	protected static final int REFERENCE_TYPE = 2;

	/**
	 * The attribute type.
	 */
	protected static final int ATTRIBUTE_TYPE = 3;

	/**
	 * The name of the variable.
	 */
	private String name;

	/**
	 * The real value.
	 */
	private Object value;

	/**
	 * The type of this Acceleo value. The default type is DEFAULT_TYPE.
	 */
	private int type;

	/**
	 * Constructor. It constructs a variable contained in the given stack frame with the given name.
	 * 
	 * @param frame
	 *            is the owning stack frame
	 * @param name
	 *            is the variable name
	 * @param aValue
	 *            is the value
	 * @param type
	 *            is the type of the variable
	 */
	public AcceleoVariable(AcceleoStackFrame frame, String name, Object aValue, int type) {
		this(frame.getDebugTarget(), name, aValue, type);
	}

	/**
	 * Constructor. It constructs a variable contained in the given debug target with the given name.
	 * 
	 * @param aDebugTarget
	 *            is the debug target
	 * @param name
	 *            is the variable name
	 * @param aValue
	 *            is the value
	 * @param type
	 *            is the type of the variable
	 */
	public AcceleoVariable(IDebugTarget aDebugTarget, String name, Object aValue, int type) {
		super(aDebugTarget);
		this.name = name;
		this.value = aValue;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getValue()
	 */
	public IValue getValue() throws DebugException {
		return new AcceleoValue(getDebugTarget(), value, type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getName()
	 */
	public String getName() throws DebugException {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#getReferenceTypeName()
	 */
	public String getReferenceTypeName() throws DebugException {
		String ret = ""; //$NON-NLS-1$
		if (value != null) {
			ret = getSimpleName(value.getClass());
		}
		return ret;
	}

	/**
	 * Gets the simple name of the given class. As an example, 'String' is the simple name of the class
	 * 'java.lang.String'.
	 * 
	 * @param c
	 *            is a class
	 * @return the simple name of the class
	 */
	private String getSimpleName(Class<?> c) {
		String cName = c.getName();
		int i = cName.lastIndexOf("."); //$NON-NLS-1$
		if (i > -1) {
			cName = cName.substring(i + 1);
		}
		return cName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IVariable#hasValueChanged()
	 */
	public boolean hasValueChanged() throws DebugException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(java.lang.String)
	 */
	public void setValue(String expression) throws DebugException {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#setValue(org.eclipse.debug.core.model.IValue)
	 */
	public void setValue(IValue value) throws DebugException {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#supportsValueModification()
	 */
	public boolean supportsValueModification() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(java.lang.String)
	 */
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IValueModification#verifyValue(org.eclipse.debug.core.model.IValue)
	 */
	public boolean verifyValue(IValue aValue) throws DebugException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(AcceleoVariable arg0) {
		int ret = 0;
		if (arg0.name != null && name != null) {
			ret = name.compareTo(arg0.name);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.debug.model.AbstractDebugElement#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class adapter) {
		Object adapted = null;
		if (adapter == EObject.class && this.value instanceof EObject) {
			adapted = this.value;
		} else if (adapter == Variable.class) {
			adapted = new Variable(name, value);
		}
		if (adapted != null) {
			return adapted;
		}
		return super.getAdapter(adapter);
	}

}
