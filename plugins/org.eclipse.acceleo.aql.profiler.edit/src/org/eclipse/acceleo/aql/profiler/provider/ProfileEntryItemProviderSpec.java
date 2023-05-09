/*******************************************************************************
 * Copyright (c) 2008, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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

	private static final NumberFormat FORMAT = initFormat();

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public ProfileEntryItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	private static NumberFormat initFormat() {
		final NumberFormat res = new DecimalFormat();

		res.setMaximumIntegerDigits(3);
		res.setMaximumFractionDigits(2);

		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.provider.ProfileEntryItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		EObject monitored = ((ProfileEntry)object).getMonitored();

		final Object res;
		if (monitored != null) {
			res = ProfilerEditPlugin.LABEL_PROVIDER.getImage(monitored);
		} else {
			res = super.getImage(object);
		}

		return res;
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

		final String text;
		final String eClass;
		if (monitored != null) {
			text = ProfilerEditPlugin.LABEL_PROVIDER.getText(monitored);
			eClass = monitored.eClass().getName();
		} else {
			text = "INTERNAL";
			eClass = "";
		}

		return FORMAT.format(profileEntry.getPercentage()) + "% / " + profileEntry.getDuration() + "ms / " //$NON-NLS-1$ //$NON-NLS-2$
				+ profileEntry.getCount() + " times " + eClass + " [" //$NON-NLS-1$ //$NON-NLS-2$
				+ text + "]"; //$NON-NLS-1$
	}
}
