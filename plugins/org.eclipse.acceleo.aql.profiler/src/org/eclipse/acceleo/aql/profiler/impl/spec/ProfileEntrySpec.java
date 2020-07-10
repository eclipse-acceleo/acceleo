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
package org.eclipse.acceleo.aql.profiler.impl.spec;

import org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl;

/**
 * Specializes the {@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl <em>ProfileEntry</em>}
 * implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProfileEntrySpec extends ProfileEntryImpl {
	/**
	 * Time in millisecond of the last start since the epoch.
	 */
	private long lastStart;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#start()
	 */
	@Override
	public void start() {
		++count;
		lastStart = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#stop()
	 */
	@Override
	public void stop() {
		duration += System.currentTimeMillis() - lastStart;
	}
}
