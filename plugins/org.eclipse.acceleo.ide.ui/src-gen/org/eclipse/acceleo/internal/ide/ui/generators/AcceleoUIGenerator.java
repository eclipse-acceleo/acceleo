/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.generators;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * The Acceleo UI Generator will handle the generation of all the files created by the Acceleo UI.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoUIGenerator {

	/**
	 * The Acceleo module that will generate the build.acceleo file.
	 */
	private static Module buildAcceleoGenerator;

	/**
	 * The Acceleo module used for the generation of the Java class.
	 */
	private static Module acceleoJavaClassGenerator;

	/**
	 * The Acceleo module that will generate the ant runner for the main template.
	 */
	private static Module antRunnerGenerator;

	/**
	 * The Acceleo module that will generate the and read me file.
	 */
	private static Module antReadMeGenerator;

	/**
	 * The sole instance.
	 */
	private static AcceleoUIGenerator instance;

	/**
	 * The constructor.
	 */
	private AcceleoUIGenerator() {
		// prevent instantiation
	}

	/**
	 * Returns the sole instance.
	 * 
	 * @return The sole instance.
	 */
	public static AcceleoUIGenerator getDefault() {
		if (instance == null) {
			instance = new AcceleoUIGenerator();
		}
		return instance;
	}

	/**
	 * Generates the Java main class for the generation.
	 * 
	 * @param acceleoMainClass
	 *            The utility class that holds all the information for the generation.
	 * @param outputContainer
	 *            The output folder.
	 */
	public void generateJavaClass(AcceleoMainClass acceleoMainClass, IContainer outputContainer) {
		try {
			if (acceleoJavaClassGenerator == null) {
				ResourceSet resourceSet = new ResourceSetImpl();
				URI moduleURI = this.convertToURI(IAcceleoGenerationConstants.ACCELEO_JAVA_CLASS_GENERATOR_URI);
				EObject load = ModelUtils.load(moduleURI, resourceSet);

				if (load instanceof Module) {
					acceleoJavaClassGenerator = (Module)load;
				}
			}

			if (acceleoJavaClassGenerator != null) {
				String templateName = IAcceleoGenerationConstants.ACCELEO_JAVA_CLASS_TEMPLATE_URI;
				File generationRoot = outputContainer.getLocation().toFile();
				new AcceleoService(new DefaultStrategy()).doGenerate(acceleoJavaClassGenerator, templateName,
						acceleoMainClass, generationRoot, new BasicMonitor());
				outputContainer.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			}
		} catch (IOException e) {
			AcceleoUIActivator.log(e, true);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Generates the Ant files.
	 * 
	 * @param acceleoMainClass
	 *            The Acceleo main class.
	 * @param outputContainer
	 *            The output folder.
	 */
	public void generateAntFiles(AcceleoMainClass acceleoMainClass, IContainer outputContainer) {
		try {
			if (antRunnerGenerator == null || antReadMeGenerator == null) {
				ResourceSet resourceSet = new ResourceSetImpl();
				URI loadModuleURI = this.convertToURI(IAcceleoGenerationConstants.ANT_RUNNER_GENERATOR_URI);
				EObject load = ModelUtils.load(loadModuleURI, resourceSet);

				URI loadReadMeModuleURI = this
						.convertToURI(IAcceleoGenerationConstants.ANT_RUNNER_READ_ME_GENERATOR_URI);
				EObject loadReadMe = ModelUtils.load(loadReadMeModuleURI, resourceSet);

				if (load instanceof Module && loadReadMe instanceof Module) {
					antRunnerGenerator = (Module)load;
					antReadMeGenerator = (Module)loadReadMe;
				}
			}

			if (antRunnerGenerator != null && antReadMeGenerator != null) {
				String templateNameWriter = IAcceleoGenerationConstants.ANT_RUNNER_TEMPLATE_URI;
				String templateNameReadMe = IAcceleoGenerationConstants.ANT_RUNNER_READ_ME_TEMPLATE_URI;

				File generationRoot = outputContainer.getLocation().toFile();
				new AcceleoService(new DefaultStrategy()).doGenerate(antRunnerGenerator, templateNameWriter,
						acceleoMainClass, generationRoot, new BasicMonitor());
				new AcceleoService(new DefaultStrategy()).doGenerate(antReadMeGenerator, templateNameReadMe,
						acceleoMainClass, generationRoot, new BasicMonitor());
				outputContainer.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			}
		} catch (IOException e) {
			AcceleoUIActivator.log(e, true);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Generates the build.acceleo file.
	 * 
	 * @param acceleoProject
	 *            The Acceleo project
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateBuildAcceleo(AcceleoProject acceleoProject, IContainer outputContainer) {
		try {
			if (buildAcceleoGenerator == null) {
				ResourceSet resourceSet = new ResourceSetImpl();
				URI moduleURI = this.convertToURI(IAcceleoGenerationConstants.BUILD_ACCELEO_GENERATOR_URI);

				EObject load = ModelUtils.load(moduleURI, resourceSet);

				if (load instanceof Module) {
					buildAcceleoGenerator = (Module)load;
				}
			}

			if (buildAcceleoGenerator != null) {
				String templateName = IAcceleoGenerationConstants.BUILD_ACCELEO_TEMPLATE_URI;
				File generationRoot = outputContainer.getLocation().toFile();
				new AcceleoService(new DefaultStrategy()).doGenerate(buildAcceleoGenerator, templateName,
						acceleoProject, generationRoot, new BasicMonitor());
				outputContainer.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			}
		} catch (IOException e) {
			AcceleoUIActivator.log(e, true);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * @param moduleURI
	 * @return
	 */
	private URI convertToURI(String moduleURI) {
		URL url = this.convertModuleURI(moduleURI);
		return this.createTemplateURI(url.toString());
	}

	/**
	 * Convert the module URI.
	 * 
	 * @param moduleURI
	 *            The module URI.
	 * @return The module URL.
	 */
	private URL convertModuleURI(String moduleURI) {
		URL moduleURL = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			try {
				moduleURL = AcceleoWorkspaceUtil.getResourceURL(getClass(), moduleURI);
			} catch (IOException e) {
				// Swallow this, we'll try and locate the module through the class loader
			}
		}
		if (moduleURL == null) {
			moduleURL = getClass().getResource(moduleURI);
		}
		return moduleURL;
	}

	/**
	 * Creates the URI that will be used to resolve the template that is to be launched.
	 * 
	 * @param entry
	 *            The path towards the template file. Could be a jar or file scheme URI, or we'll assume it
	 *            represents a relative path.
	 * @return The actual URI from which the template file can be resolved.
	 */
	private URI createTemplateURI(String entry) {
		if (entry.startsWith("file:") || entry.startsWith("jar:")) { //$NON-NLS-1$ //$NON-NLS-2$ 
			return URI.createURI(URI.decode(entry));
		}
		return URI.createFileURI(URI.decode(entry));
	}
}
