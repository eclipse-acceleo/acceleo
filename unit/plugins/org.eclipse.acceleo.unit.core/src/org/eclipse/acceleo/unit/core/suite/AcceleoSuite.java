/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.suite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.unit.core.runner.AcceleoRunner;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

/**
 * The acceleo suite class extend the Junit Suite class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoSuite extends Suite {

	/**
	 * Runner list.
	 */
	private List<Runner> runners = new ArrayList<Runner>();

	/**
	 * The constructor.
	 * 
	 * @param klass
	 *            the root class
	 * @throws InitializationError
	 *             When the initialization fails
	 */
	public AcceleoSuite(Class<?> klass) throws InitializationError {
		super(klass, Collections.<Runner> emptyList());
		AcceleoRunner acceleoRunner = new AcceleoRunner(klass);
		runners.add(acceleoRunner);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.Suite#getChildren()
	 */
	@Override
	protected List<Runner> getChildren() {
		return this.runners;
	}
}
