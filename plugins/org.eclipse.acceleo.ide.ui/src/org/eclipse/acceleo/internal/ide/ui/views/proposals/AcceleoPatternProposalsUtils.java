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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * Utility class to find all the 'pattern' proposals existing in the current Eclipse instance.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class AcceleoPatternProposalsUtils {

	/**
	 * All the 'pattern' proposals existing in the current Eclipse instance. An internal extension point is
	 * defined to specify a new Pattern proposal in the current Acceleo template completion.
	 */
	private static List<IAcceleoPatternProposal> patternProposals;

	/**
	 * Constructor.
	 */
	private AcceleoPatternProposalsUtils() {
	}

	/**
	 * Gets all the 'pattern' proposals existing in the current Eclipse instance. An internal extension point
	 * is defined to specify a new Pattern proposal in the current Acceleo template completion.
	 * 
	 * @return all the 'pattern' proposals
	 */
	@SuppressWarnings("unchecked")
	public static List<IAcceleoPatternProposal> getPatternProposals() {
		if (patternProposals == null) {
			patternProposals = new ArrayList<IAcceleoPatternProposal>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry
					.getExtensionPoint(IAcceleoPatternProposal.PATTERN_PROPOSAL_EXTENSION_ID);
			if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
				IExtension[] extensions = extensionPoint.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] members = extension.getConfigurationElements();
					for (int j = 0; j < members.length; j++) {
						IConfigurationElement member = members[j];
						String patternClass = member.getAttribute("class"); //$NON-NLS-1$
						if (patternClass != null) {
							try {
								Bundle bundle = Platform.getBundle(member.getNamespaceIdentifier());
								@SuppressWarnings("cast")
								Class<IAcceleoPatternProposal> c = (Class<IAcceleoPatternProposal>)bundle
										.loadClass(patternClass);
								IAcceleoPatternProposal patternProposal = c.newInstance();
								patternProposals.add(patternProposal);
							} catch (ClassNotFoundException e) {
								IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
										IStatus.OK, e.getMessage(), e);
								AcceleoUIActivator.getDefault().getLog().log(status);
							} catch (InstantiationException e) {
								IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
										IStatus.OK, e.getMessage(), e);
								AcceleoUIActivator.getDefault().getLog().log(status);
							} catch (IllegalAccessException e) {
								IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
										IStatus.OK, e.getMessage(), e);
								AcceleoUIActivator.getDefault().getLog().log(status);
							}
						} else {
							IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
									IStatus.OK, AcceleoUIMessages.getString(
											"AcceleoPatternProposalsUtils.MissingPatternClass", //$NON-NLS-1$
											IAcceleoPatternProposal.PATTERN_PROPOSAL_EXTENSION_ID), null);
							AcceleoUIActivator.getDefault().getLog().log(status);
						}
					}
				}
			}
		}
		return patternProposals;
	}
}
