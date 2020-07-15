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
package org.eclipse.acceleo.aql.profiler.provider;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * Specializes the ProfileEntryItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProfileEntryItemProviderSpec extends ProfileEntryItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public ProfileEntryItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.provider.ProfileEntryItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		EObject monitored = ((ProfileEntry)object).getMonitored();

		return ProfilerEditPlugin.LABEL_PROVIDER.getImage(monitored);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.provider.ProfileEntryItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		final ProfileEntry profileEntry = (ProfileEntry)object;
		final EObject monitored = profileEntry.getMonitored();
		final NumberFormat format = new DecimalFormat();
		format.setMaximumIntegerDigits(3);
		format.setMaximumFractionDigits(2);

		return format.format(profileEntry.getPercentage()) + "% / " + profileEntry.getDuration() + "ms / " //$NON-NLS-1$ //$NON-NLS-2$
				+ profileEntry.getCount() + " times " + monitored.eClass().getName() + " [" //$NON-NLS-1$ //$NON-NLS-2$
				+ ProfilerEditPlugin.LABEL_PROVIDER.getText(monitored) + "]"; //$NON-NLS-1$
	}
}
