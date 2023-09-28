/*******************************************************************************
 * Copyright (c) 2017, 2019 Obeo. 
 *    All rights reserved. This program and the accompanying materials
 *    are made available under the terms of the Eclipse Public License v2.0
 *    which accompanies this distribution, and is available at
 *    http://www.eclipse.org/legal/epl-v20.html
 *     
 *     Contributors:
 *         Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.sirius.services.configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfigurator;
import org.eclipse.acceleo.query.sirius.AqlSiriusUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.internal.session.SessionTransientAttachment;
import org.eclipse.ui.PlatformUI;

/**
 * Configure Sirius {@link ResourceSet}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("restriction")
public class SiriusResourceSetConfigurator implements IResourceSetConfigurator {

    /**
     * The {@link List} of options managed by this configurator.
     */
    public static final List<String> OPTIONS = initOptions();

    /**
     * Mapping of {@link Object} to {@link Session}.
     */
    private final Map<Object, Session> sessions = new HashMap<>();

    /**
     * {@link Set} of {@link Session} that need to be closed.
     */
    private final Set<Session> sessionToClose = new HashSet<>();

    /**
     * Mapping of {@link Session} to {@link SessionTransientAttachment}.
     */
    private final Map<Session, SessionTransientAttachment> transientAttachments = new HashMap<>();

    /**
     * Initializes options.
     * 
     * @return the {@link List} of options.
     */
    private static List<String> initOptions() {
        final List<String> res = new ArrayList<>();

        res.add(AqlSiriusUtils.SIRIUS_SESSION_OPTION);

        return res;
    }

    @Override
    public Map<String, String> getInitializedOptions(Map<String, String> options) {
        final Map<String, String> res = new HashMap<>();

        if (!options.containsKey(AqlSiriusUtils.SIRIUS_SESSION_OPTION)) {
            final String baseURIStr = options.get(AQLUtils.BASE_URI_OPTION);
            final String sessionURIStr = getSessionString(baseURIStr);
            if (sessionURIStr != null) {
                res.put(AqlSiriusUtils.SIRIUS_SESSION_OPTION, sessionURIStr);
            }
        }
        return res;
    }

    /**
     * Gets the {@link Session} option from the base URI string.
     * 
     * @param baseURIStr
     *            the base URI string used to resolve the modeling project
     * @return the {@link Session} option from the base URI string
     */
    protected String getSessionString(final String baseURIStr) {
        final String res;

        if (baseURIStr != null) {
            final URI baseURI = URI.createURI(baseURIStr, true);
            if (baseURI.isPlatformResource()) {
                res = getSessionFromPlatformResource(baseURI);
            } else {
                res = null;
            }
        } else {
            res = null;
        }

        return res;
    }

    /**
     * Gets the {@link Session} path form the given platform resource {@link URI}.
     * 
     * @param basePlatformResourceURI
     *            the platform resource {@link URI}
     * @return the {@link Session} path form the given platform resource {@link URI} if any, <code>null</code> otherwise
     */
    private String getSessionFromPlatformResource(final URI basePlatformResourceURI) {
        final String res;
        final String filePath = basePlatformResourceURI.toPlatformString(true);
        final IResource baseResource = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
        final IProject project = baseResource.getProject();
        final ModelingProject modelingProject = getModelingProject(project);

        if (modelingProject != null) {
            final Session session = modelingProject.getSession();
            if (session != null) {
                final URI sessionURI = session.getSessionResource().getURI();
                res = URI.decode(sessionURI.deresolve(basePlatformResourceURI, false, true, true).toString());
            } else {
                res = getSessionFile(basePlatformResourceURI, project);
            }
        } else {
            res = null;
        }
        return res;
    }

    /**
     * Gets the Sirius Session file in the given {@link IContainer}.
     * 
     * @param basefPlatformResourceURI
     *            the platform resource {@link URI}
     * @param container
     *            the {@link IProject}
     * @return the Sirius Session file in the given {@link IContainer} if any, <code>null</code> otherwise
     */
    private String getSessionFile(URI basefPlatformResourceURI, IContainer container) {
        String res = null;

        try {
            for (IResource member : container.members()) {
                if (member instanceof IContainer) {
                    res = getSessionFile(basefPlatformResourceURI, (IContainer) member);
                    if (res != null) {
                        break;
                    }
                } else if (member instanceof IFile
                    && SiriusUtil.SESSION_RESOURCE_EXTENSION.equals(member.getFileExtension())) {
                        final Session session = SessionManager.INSTANCE.getSession(
                                URI.createPlatformResourceURI(member.getFullPath().toString(), true),
                                new NullProgressMonitor());
                        if (session != null) {
                            final URI sessionURI = session.getSessionResource().getURI();
                            res = URI.decode(
                                    sessionURI.deresolve(basefPlatformResourceURI, false, true, true).toString());
                            break;
                        }
                    }
            }
        } catch (CoreException e) {
            // we ignore this since the option can be set manually afterward
        }

        return res;
    }

    /**
     * Gets the {@link ModelingProject} for the given {@link IProject}.
     * 
     * @param project
     *            the {@link IProject}
     * @return the {@link ModelingProject} for the given {@link IProject} if any, <code>null</code> otherwise
     */
    private ModelingProject getModelingProject(IProject project) {
        ModelingProject modelingProject = null;
        try {
            for (String natureId : project.getDescription().getNatureIds()) {
                IProjectNature nature = project.getNature(natureId);
                if (nature instanceof ModelingProject) {
                    modelingProject = (ModelingProject) nature;
                    break;
                }
            }
        } catch (CoreException e) {
            /* does nothing */
        }
        return modelingProject;
    }

    @Override
    public List<String> getOptions() {
        return OPTIONS;
    }

    @Override
    public Map<String, List<Diagnostic>> validate(IReadOnlyQueryEnvironment queryEnvironment,
            Map<String, String> options) {
        final Map<String, List<Diagnostic>> res = new HashMap<>();

        final String sessionURIStr = options.get(AqlSiriusUtils.SIRIUS_SESSION_OPTION);
        if (sessionURIStr != null) {
            URI sessionURI = URI.createURI(sessionURIStr, false);
            final String basefURIStr = options.get(AQLUtils.BASE_URI_OPTION);
            if (basefURIStr != null) {
                sessionURI = sessionURI.resolve(URI.createURI(basefURIStr, false));
            }
            if (!URIConverter.INSTANCE.exists(sessionURI, Collections.emptyMap())) {
                final List<Diagnostic> diagnostics = new ArrayList<>();
                res.put(AqlSiriusUtils.SIRIUS_SESSION_OPTION, diagnostics);
                diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, AqlSiriusUtils.PLUGIN_ID, 0,
                        "The Sirius session doesn't exist: " + sessionURI.toString(), new Object[] {sessionURI}));
            }
        }

        return res;
    }

    @Override
    public ResourceSet createResourceSetForModels(Object context, Map<String, String> options) {
        ResourceSet created = null;
        final String sessionURIStr = options.get(AqlSiriusUtils.SIRIUS_SESSION_OPTION);
        if (sessionURIStr != null && !sessionURIStr.isEmpty()) {
            URI sessionURI = URI.createURI(sessionURIStr, false);
            final String baseURIStr = options.get(AQLUtils.BASE_URI_OPTION);
            if (baseURIStr != null) {
                sessionURI = sessionURI.resolve(URI.createURI(baseURIStr));
            }
            if (URIConverter.INSTANCE.exists(sessionURI, Collections.emptyMap())) {
                try {
                    final Session session = SessionManager.INSTANCE.getSession(sessionURI, new NullProgressMonitor());
                    sessions.put(context, session);
                    if (!session.isOpen()) {
                        session.open(new NullProgressMonitor());
                        sessionToClose.add(session);
                    }
                    created = session.getTransactionalEditingDomain().getResourceSet();
                    SessionTransientAttachment transiantAttachment = new SessionTransientAttachment(session);
                    created.eAdapters().add(transiantAttachment);
                    transientAttachments.put(session, transiantAttachment);
                    // CHECKSTYLE:OFF
                } catch (Exception e) {
                    // CHECKSTYLE:ON
                    // TODO remove this workaround see https://support.jira.obeo.fr/browse/VP-5389
                    if (PlatformUI.isWorkbenchRunning()) {
                        MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                                "Unable to open Sirius Session",
                                "Check the " + AqlSiriusUtils.SIRIUS_SESSION_OPTION
                                    + " option or try to open the session manually by double clicking the .aird file:\n"
                                    + e.getMessage());
                    }
                }
            } else {
                throw new IllegalArgumentException("The Sirius session doesn't exist: " + sessionURI);
            }
        }
        return created;
    }

    @Override
    public void cleanResourceSetForModels(Object context) {
        final Session session = sessions.remove(context);
        if (session != null) {
            if (session.isOpen()) {
                session.getTransactionalEditingDomain().getResourceSet().eAdapters()
                        .remove(transientAttachments.remove(session));
            }
            if (sessionToClose.remove(session)) {
                session.close(new NullProgressMonitor());
            }
        }
    }

}
