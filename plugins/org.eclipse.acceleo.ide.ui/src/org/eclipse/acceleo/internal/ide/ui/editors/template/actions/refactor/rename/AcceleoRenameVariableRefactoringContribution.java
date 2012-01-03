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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import java.util.Map;

import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;

/**
 * The refactoring contribution.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameVariableRefactoringContribution extends RefactoringContribution {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.RefactoringContribution#createDescriptor(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.util.Map, int)
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	public RefactoringDescriptor createDescriptor(final String id, final String project,
			final String description, final String comment, final Map arguments, final int flags)
			throws IllegalArgumentException {
		return new AcceleoRenameVariableDescriptor(project, description, comment, arguments);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.RefactoringContribution#retrieveArgumentMap(org.eclipse.ltk.core.refactoring.RefactoringDescriptor)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map retrieveArgumentMap(final RefactoringDescriptor descriptor) {
		if (descriptor instanceof AcceleoRenameVariableDescriptor) {
			return ((AcceleoRenameVariableDescriptor)descriptor).getArguments();
		}
		return super.retrieveArgumentMap(descriptor);
	}

}
