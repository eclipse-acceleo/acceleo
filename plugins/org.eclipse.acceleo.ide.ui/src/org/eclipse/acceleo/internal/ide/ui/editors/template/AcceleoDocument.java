/*******************************************************************************
 * Copyright (c) 2008, 2012 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sven Efftinge - initial API and implementation
 *     Michael Clay
 *     Jan Koehnlein
 *     Stephane Begaudeau - Fork in Acceleo
 *
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.Position;

/**
 * Subclassing document to prevent the concurrent modification exception from the jface annotation model.
 * Shamelessly stolen from Xtext.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocument extends Document {
	/**
	 * The positions lock.
	 */
	private ReadWriteLock positionsLock = new ReentrantReadWriteLock();

	/**
	 * The positions read lock.
	 */
	private Lock positionsReadLock = positionsLock.readLock();

	/**
	 * The position write lock.
	 */
	private Lock positionsWriteLock = positionsLock.writeLock();

	@Override
	public Position[] getPositions(String category, int offset, int length, boolean canStartBefore,
			boolean canEndAfter) throws BadPositionCategoryException {
		positionsReadLock.lock();
		try {
			return super.getPositions(category, offset, length, canStartBefore, canEndAfter);
		} finally {
			positionsReadLock.unlock();
		}
	}

	@Override
	public Position[] getPositions(String category) throws BadPositionCategoryException {
		positionsReadLock.lock();
		try {
			return super.getPositions(category);
		} finally {
			positionsReadLock.unlock();
		}
	}

	@Override
	public void addPosition(Position position) throws BadLocationException {
		positionsWriteLock.lock();
		try {
			super.addPosition(position);
		} finally {
			positionsWriteLock.unlock();
		}
	}

	@Override
	public void addPosition(String category, Position position) throws BadLocationException,
			BadPositionCategoryException {
		positionsWriteLock.lock();
		try {
			super.addPosition(category, position);
		} finally {
			positionsWriteLock.unlock();
		}
	}

	@Override
	public void removePosition(Position position) {
		positionsWriteLock.lock();
		try {
			super.removePosition(position);
		} finally {
			positionsWriteLock.unlock();
		}
	}

	@Override
	public void removePosition(String category, Position position) throws BadPositionCategoryException {
		positionsWriteLock.lock();
		try {
			super.removePosition(category, position);
		} finally {
			positionsWriteLock.unlock();
		}
	}
}
