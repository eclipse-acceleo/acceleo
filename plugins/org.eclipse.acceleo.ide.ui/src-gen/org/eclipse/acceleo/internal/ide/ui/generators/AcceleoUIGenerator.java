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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.BundleURLConverter;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;

/**
 * The Acceleo UI Generator will handle the generation of all the files created by the Acceleo UI.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
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
	 * The Acceleo module that will generate the Acceleo module file.
	 */
	private static Module moduleGenerator;

	/**
	 * The Acceleo module that will generate the .settings/org.eclipse.jdt.core.prefs file.
	 */
	private static Module projectSettings;

	/**
	 * The Acceleo module that will generate the .classpath file.
	 */
	private static Module projectClasspath;

	/**
	 * The Acceleo module that will generate the MANIFEST.MF file.
	 */
	private static Module projectManifest;

	/**
	 * The Acceleo module that will generate the build.properties file.
	 */
	private static Module buildProperties;

	/**
	 * The Acceleo module that will generate the Activator file.
	 */
	private static Module activator;

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
		generate(acceleoJavaClassGenerator, acceleoMainClass, outputContainer,
				IAcceleoGenerationConstants.ACCELEO_JAVA_CLASS_GENERATOR_URI,
				IAcceleoGenerationConstants.ACCELEO_JAVA_CLASS_TEMPLATE_URI);
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
		generate(antRunnerGenerator, acceleoMainClass, outputContainer,
				IAcceleoGenerationConstants.ANT_RUNNER_GENERATOR_URI,
				IAcceleoGenerationConstants.ANT_RUNNER_TEMPLATE_URI);
		generate(antReadMeGenerator, acceleoMainClass, outputContainer,
				IAcceleoGenerationConstants.ANT_RUNNER_READ_ME_GENERATOR_URI,
				IAcceleoGenerationConstants.ANT_RUNNER_READ_ME_TEMPLATE_URI);
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
		generate(buildAcceleoGenerator, acceleoProject, outputContainer,
				IAcceleoGenerationConstants.BUILD_ACCELEO_GENERATOR_URI,
				IAcceleoGenerationConstants.BUILD_ACCELEO_TEMPLATE_URI);
	}

	/**
	 * Generates the Acceleo module.
	 * 
	 * @param acceleoModule
	 *            the Acceleo module.
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateAcceleoModule(AcceleoModule acceleoModule, IContainer outputContainer) {
		generate(moduleGenerator, acceleoModule, outputContainer,
				IAcceleoGenerationConstants.ACCELEO_MODULE_GENERATOR_URI,
				IAcceleoGenerationConstants.ACCELEO_MODULE_TEMPLATE_URI);
	}

	/**
	 * Generates the settings file.
	 * 
	 * @param acceleoProject
	 *            the Acceleo project
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateProjectSettings(AcceleoProject acceleoProject, IContainer outputContainer) {
		generate(projectSettings, acceleoProject, outputContainer,
				IAcceleoGenerationConstants.PROJECT_SETTINGS_GENERATOR_URI,
				IAcceleoGenerationConstants.PROJECT_SETTINGS_TEMPLATE_URI);
	}

	/**
	 * Generates the classpath file.
	 * 
	 * @param acceleoProject
	 *            the Acceleo project
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateProjectClasspath(AcceleoProject acceleoProject, IContainer outputContainer) {
		generate(projectClasspath, acceleoProject, outputContainer,
				IAcceleoGenerationConstants.PROJECT_CLASSPATH_GENERATOR_URI,
				IAcceleoGenerationConstants.PROJECT_CLASSPATH_TEMPLATE_URI);
	}

	/**
	 * Generates the MANIFEST.MF file.
	 * 
	 * @param acceleoProject
	 *            the Acceleo project
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateProjectManifest(AcceleoProject acceleoProject, IContainer outputContainer) {
		generate(projectManifest, acceleoProject, outputContainer,
				IAcceleoGenerationConstants.PROJECT_MANIFEST_GENERATOR_URI,
				IAcceleoGenerationConstants.PROJECT_MANIFEST_TEMPLATE_URI);
	}

	/**
	 * Generates the build.properties file.
	 * 
	 * @param acceleoProject
	 *            the Acceleo project
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateBuildProperties(AcceleoProject acceleoProject, IContainer outputContainer) {
		generate(buildProperties, acceleoProject, outputContainer,
				IAcceleoGenerationConstants.PROJECT_BUILD_GENERATOR_URI,
				IAcceleoGenerationConstants.PROJECT_BUILD_TEMPLATE_URI);
	}

	/**
	 * Generates the activator file.
	 * 
	 * @param acceleoProject
	 *            the Acceleo project
	 * @param outputContainer
	 *            The output container.
	 */
	public void generateActivator(AcceleoProject acceleoProject, IContainer outputContainer) {
		generate(activator, acceleoProject, outputContainer,
				IAcceleoGenerationConstants.PROJECT_ACTIVATOR_GENERATOR_URI,
				IAcceleoGenerationConstants.PROJECT_ACTIVATOR_TEMPLATE_URI);
	}

	/**
	 * Launch the generation.
	 * 
	 * @param module
	 *            The module
	 * @param eObject
	 *            The EObject
	 * @param outputContainer
	 *            The output container
	 * @param generatorURI
	 *            The generator uri
	 * @param templateURI
	 *            The template uri
	 */
	private void generate(Module module, EObject eObject, IContainer outputContainer, String generatorURI,
			String templateURI) {
		Module moduleTmp = module;
		try {
			if (moduleTmp == null) {
				ResourceSet resourceSet = new ResourceSetImpl();
				registerPackages(resourceSet);
				registerResourceFactories(resourceSet);
				resourceSet.setURIConverter(createURIConverter());
				resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());

				URI moduleURI = this.convertToURI(generatorURI);
				EObject load = ModelUtils.load(moduleURI, resourceSet);

				if (load instanceof Module) {
					moduleTmp = (Module)load;
				}
			}

			if (moduleTmp != null) {
				String templateName = templateURI;
				File generationRoot = outputContainer.getLocation().toFile();
				new AcceleoService(new DefaultStrategy()).doGenerate(moduleTmp, templateName, eObject,
						generationRoot, new BasicMonitor());

				outputContainer.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			}
		} catch (IOException e) {
			AcceleoUIActivator.log(e, true);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Creates the URI Converter we'll use to load our modules. Take note that this should never be used out
	 * of Eclipse.
	 * 
	 * @return The created URI Converter.
	 */
	protected URIConverter createURIConverter() {
		return new ExtensibleURIConverterImpl() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#normalize(org.eclipse.emf.common.util.URI)
			 */
			@Override
			public URI normalize(URI uri) {
				URI normalized = getURIMap().get(uri);
				if (normalized == null) {
					BundleURLConverter conv = new BundleURLConverter(uri.toString());
					if (conv.resolveBundle() != null) {
						normalized = URI.createURI(conv.resolveAsPlatformPlugin());
						getURIMap().put(uri, normalized);
					}
				}
				if (normalized != null) {
					return normalized;
				}
				return super.normalize(uri);
			}
		};
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
			return URI.createURI(URI.decode(entry), false);
		}
		return URI.createFileURI(URI.decode(entry));
	}

	/**
	 * This will update the resource set's package registry with all usual EPackages.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	private void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(GenModelPackage.eINSTANCE.getNsURI(), GenModelPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
				ExpressionsPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
		resourceSet.getPackageRegistry().put(AcceleowizardmodelPackage.eINSTANCE.getNsURI(),
				AcceleowizardmodelPackage.eINSTANCE);
	}

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 */
	private EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}

	/**
	 * This will update the resource set's resource factory registry with all usual factories.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	private void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}
}
