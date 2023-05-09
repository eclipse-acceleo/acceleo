/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.presentation;

/**
 * Sort status class for the profiler view.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProfilerSortStatus {
	/**
	 * Constant to sort viewer by time.
	 */
	protected static final int SORT_BY_CREATION_TIME = 0;

	/**
	 * Constant to unsort viewer.
	 */
	protected static final int SORT_BY_TIME = 1;

	/**
	 * Constant to unsort viewer.
	 */
	protected int sortOrder = SORT_BY_CREATION_TIME;

	/**
	 * Getter for the current sort order.
	 * 
	 * @return the current sort order
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * Setter for the current sort order.
	 * 
	 * @param sortOreder
	 *            the new sort order
	 */
	public void setSortOrder(int sortOreder) {
		this.sortOrder = sortOreder;
	}
}
