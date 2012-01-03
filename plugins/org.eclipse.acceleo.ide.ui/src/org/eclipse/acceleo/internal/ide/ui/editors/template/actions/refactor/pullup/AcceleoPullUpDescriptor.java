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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * The acceleo pull up descriptor.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpDescriptor extends RefactoringDescriptor {

	/**
	 * Refactoring ID.
	 */
	public static final String REFACTORING_ID = "org.eclipse.acceleo.ide.ui.refactor.pullup"; //$NON-NLS-1$

	/**
	 * Arguments for the refactoring initialisation.
	 */
	private final Map<String, String> fArguments;

	/**
	 * The refactoring descriptor.
	 * 
	 * @param project
	 *            The project
	 * @param description
	 *            The description
	 * @param comment
	 *            The comment
	 * @param arguments
	 *            The arguments
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public AcceleoPullUpDescriptor(String project, String description, String comment, Map arguments) {
		super(REFACTORING_ID, project, description, comment, RefactoringDescriptor.STRUCTURAL_CHANGE
				| RefactoringDescriptor.MULTI_CHANGE);
		fArguments = arguments;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.RefactoringDescriptor#createRefactoring(org.eclipse.ltk.core.refactoring.RefactoringStatus)
	 */
	@Override
	public Refactoring createRefactoring(RefactoringStatus status) throws CoreException {
		final AcceleoPullUpRefactoring refactoring = new AcceleoPullUpRefactoring();
		status.merge(refactoring.initialize(fArguments));
		return refactoring;
	}

}
