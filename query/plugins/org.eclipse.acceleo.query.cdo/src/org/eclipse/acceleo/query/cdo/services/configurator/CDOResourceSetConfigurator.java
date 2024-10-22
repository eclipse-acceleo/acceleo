/*******************************************************************************
 *  Copyright (c) 2017, 2024 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.cdo.services.configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.query.cdo.AqlCDOUtils;
import org.eclipse.acceleo.query.cdo.providers.configuration.AqlCDOURIHandler;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfigurator;
import org.eclipse.acceleo.query.services.configurator.IServicesConfigurator;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.spi.cdo.InternalCDOView;
import org.eclipse.net4j.connector.IConnector;

/**
 * Sirius {@link IServicesConfigurator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CDOResourceSetConfigurator implements IResourceSetConfigurator {

    /**
     * The {@link List} of options managed by this configurator.
     */
    public static final List<String> OPTIONS = initOptions();

    /**
     * Mapping of {@link Object} to {@link IConnector}.
     */
    private Map<Object, IConnector> connectors = new HashMap<Object, IConnector>();

    /**
     * Mapping of {@link Object} to {@link CDOTransaction}.
     */
    private Map<Object, CDOTransaction> transactions = new HashMap<Object, CDOTransaction>();

    /**
     * Initializes options.
     * 
     * @return the {@link List} of options.
     */
    private static List<String> initOptions() {
        final List<String> res = new ArrayList<String>();

        res.add(AqlCDOUtils.CDO_SERVER_OPTION);
        res.add(AqlCDOUtils.CDO_REPOSITORY_OPTION);
        res.add(AqlCDOUtils.CDO_BRANCH_OPTION);
        res.add(AqlCDOUtils.CDO_LOGIN_OPTION);
        res.add(AqlCDOUtils.CDO_PASSWORD_OPTION);

        return res;
    }

    @Override
    public List<String> getOptions() {
        return OPTIONS;
    }

    @Override
    public Map<String, String> getInitializedOptions(Map<String, String> options) {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, String> getInitializedOptions(Map<String, String> options, EObject eObj) {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, List<Diagnostic>> validate(IReadOnlyQueryEnvironment queryEnvironment,
            Map<String, String> options) {
        return Collections.emptyMap();
    }

    @Override
    public ResourceSet createResourceSetForModels(Object context, Map<String, String> options) {
        final ResourceSet res;

        final String cdoServer = options.get(AqlCDOUtils.CDO_SERVER_OPTION);
        if (cdoServer != null) {
            final String repository = options.get(AqlCDOUtils.CDO_REPOSITORY_OPTION);
            final String branch = options.get(AqlCDOUtils.CDO_BRANCH_OPTION);
            final String login = options.get(AqlCDOUtils.CDO_LOGIN_OPTION);
            final String password = options.get(AqlCDOUtils.CDO_PASSWORD_OPTION);
            final IConnector connector = AqlCDOUtils.getConnector(cdoServer);
            connectors.put(context, connector);
            final CDOSession session = AqlCDOUtils.openSession(connector, repository, login, password);
            final CDOTransaction transaction = AqlCDOUtils.openTransaction(session, branch);
            transactions.put(context, transaction);
            res = transaction.getResourceSet();
            res.getURIConverter().getURIHandlers().add(0, new AqlCDOURIHandler((InternalCDOView) transaction));
        } else {
            res = null;
        }

        return res;
    }

    @Override
    public void cleanResourceSetForModels(Object context) {
        final CDOTransaction transaction = transactions.get(context);
        if (transaction != null) {
            final CDOSession session = transaction.getSession();
            transaction.close();
            session.close();
            connectors.remove(context).close();
        }
    }

}
