/*******************************************************************************
 * Copyright (c) 2025 Obeo. 
 *    All rights reserved. This program and the accompanying materials
 *    are made available under the terms of the Eclipse Public License v2.0
 *    which accompanies this distribution, and is available at
 *    http://www.eclipse.org/legal/epl-v20.html
 *     
 *     Contributors:
 *         Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.sirius;

import java.util.Collections;
import java.util.Map;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

/**
 * AQL Sirius utility class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AqlSiriusUtils {

    /**
     * The Plug-in ID.
     */
    public static final String PLUGIN_ID = "org.eclipse.acceleo.query.sirius";

    /**
     * The Sirius session option.
     */
    public static final String SIRIUS_SESSION_OPTION = "SiriusSession";

    /**
     * Constructor.
     */
    private AqlSiriusUtils() {
        // nothing to do here
    }

    /**
     * Gets the Sirius {@link Session} form the given option {@link Map}.
     * 
     * @param options
     *            the option {@link Map}
     * @param monitor
     *            the {@link IProgressMonitor}
     * @return the Sirius {@link Session} form the given option {@link Map} if nay, <code>null</code> otherwise
     */
    public static Session getSession(Map<String, String> options, IProgressMonitor monitor) {
        final Session res;
        final String sessionURIStr = options.get(AqlSiriusUtils.SIRIUS_SESSION_OPTION);
        if (sessionURIStr != null && !sessionURIStr.isEmpty()) {
            URI sessionURI = URI.createURI(sessionURIStr, false);
            final String baseURIStr = options.get(AQLUtils.BASE_URI_OPTION);
            if (baseURIStr != null) {
                sessionURI = sessionURI.resolve(URI.createURI(baseURIStr));
            }
            if (URIConverter.INSTANCE.exists(sessionURI, Collections.emptyMap())) {
                res = SessionManager.INSTANCE.getSession(sessionURI, monitor);
            } else {
                throw new IllegalArgumentException("The Sirius session doesn't exist: " + sessionURI);
            }
        } else {
            res = null;
        }

        return res;
    }

}
