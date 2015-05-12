/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.completion;

import java.lang.reflect.Method;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IService;

/**
 * An {@link IService} proposal.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServiceCompletionProposal implements ICompletionProposal {

	/**
	 * The proposed {@link IService}.
	 */
	private final IService service;

	/**
	 * Constructor.
	 * 
	 * @param service
	 *            the proposed {@link IService}
	 */
	public ServiceCompletionProposal(IService service) {
		this.service = service;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		return service.getServiceMethod().getName() + "()";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getCursorOffset()
	 */
	@Override
	public int getCursorOffset() {
		Method serviceMethod = service.getServiceMethod();
		int namelength = serviceMethod.getName().length();
		if (serviceMethod.getParameterTypes().length == 1) {
			/*
			 * if we have only one parameter we return the offset: self.serviceCall()^
			 */
			return namelength + 2;
		} else {
			/*
			 * if we more than one parameter we return the offset: self.serviceCall(^)
			 */
			return namelength + 1;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getObject()
	 */
	@Override
	public IService getObject() {
		return service;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getProposal();
	}

}
