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
package org.eclipse.acceleo.internal.ide.ui.views.proposals;

import org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The 'ProposalsBrowser' view is used to update the settings of the current 'pattern' completion proposal.
 * This label provider displays the proposals Patterns available in the workbench. This label provider wraps
 * an AdapterFactory and it delegates its JFace provider interfaces to corresponding adapter-implemented item
 * provider interfaces.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ProposalsBrowserPatternsLabelProvider extends AdapterFactoryLabelProvider {

	/**
	 * Construct an instance that wraps this factory.
	 * 
	 * @param adapterFactory
	 *            should yield adapters that implement the various item label provider interfaces.
	 */
	public ProposalsBrowserPatternsLabelProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		String result;
		if (object == null) {
			result = ""; //$NON-NLS-1$
		} else if (object instanceof String) {
			result = (String)object;
		} else if (object instanceof IAcceleoPatternProposal) {
			result = ((IAcceleoPatternProposal)object).getDescription();
		} else {
			result = super.getText(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object object) {
		if (object instanceof IAcceleoPatternProposal) {
			return ((IAcceleoPatternProposal)object).getImage();
		}
		return super.getImage(object);
	}

}
