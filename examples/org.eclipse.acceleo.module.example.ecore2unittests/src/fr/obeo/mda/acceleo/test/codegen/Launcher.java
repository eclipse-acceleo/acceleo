/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package fr.obeo.mda.acceleo.test.codegen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;

/**
 * This class can be used to launch the generation of the whole module.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class Launcher {
	/** Id of the plugin containing the generation modules. */
	private static final String MODULE_PLUGIN_ID = "fr.obeo.mda.acceleo.test.codegen"; //$NON-NLS-1$

	/**
	 * Contains the names of all modules that are to be loaded for generation and their corresponding
	 * templates.
	 */
	private static final Map<String, String[]> generationModules = new HashMap<String, String[]>();

	/**
	 * This will be populated with all <b>loaded</b> modules that are to be generated.
	 */
	private static final Map<Module, Set<String>> loadedModules = new HashMap<Module, Set<String>>();

	/**
	 * Additional arguments that can be passed on to the generation engine.
	 */
	// Note that arguments are ignored in this implementation.
	@SuppressWarnings("unused")
	private List<? extends Object> arguments;

	/**
	 * Root element of the model that will be used as input of the generation.
	 */
	private EObject model;

	/**
	 * Folder that will be used as the root of all generated files.
	 */
	private File targetFolder;

	{
		generationModules.put("AbstractTestClass", new String[] {"abstractTestClass",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("AdapterFactoryTest", new String[] {"adapterFactoryTest",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("ClassTest", new String[] {"classTest",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("CommonFiles", new String[] {"manifest",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("EnumerationTest", new String[] {"enumerationTest",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("FactoryTest", new String[] {"factoryTest",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("SwitchTest", new String[] {"switchTest",}); //$NON-NLS-1$ //$NON-NLS-2$
		generationModules.put("TestSuite", new String[] {"testSuite",}); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This constructor will prepare the generation launcher with all given arguments.
	 * 
	 * @param modelURI
	 *            URI of the model that will be used as the generation source.
	 * @param targetFolder
	 *            Folder that is to be used as the root of all generated files.
	 * @param arguments
	 *            Additional arguments that can be passed on to the generation engine.
	 * @throws IOException
	 *             This will be thrown if we cannot load one of the specified generation modules.
	 */
	public Launcher(URI modelURI, File targetFolder, List<? extends Object> arguments) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);
		loadModules(resourceSet);
		model = load(modelURI, resourceSet);
		this.targetFolder = targetFolder;
		this.arguments = arguments;
	}

	/**
	 * This method can be called in standalone mode to launch the generation.
	 * <p>
	 * The <code>args</code> array is expected to contain at least two elements.
	 * <ul>
	 * <li>args[0] should contain the input model's path.</li>
	 * <li>args[1] should contain the folder meant to be the root of all generated files.</li>
	 * </ul>
	 * All further indices of this array will be considered to be arguments that are to be passed on to the
	 * generation engine.
	 * </p>
	 * 
	 * @param args
	 *            Arguments of the call. Should contain at least two elements as described above.
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				System.out.println("Invalid arguments : {modelPath, generationFolder}."); //$NON-NLS-1$ 
			} else {
				URI modelURI = URI.createFileURI(args[0]);
				File folder = new File(args[1]);
				List<String> arguments = new ArrayList<String>();
				for (int i = 2; i < args.length; i++) {
					arguments.add(args[i]);
				}
				Launcher generator = new Launcher(modelURI, folder, arguments);
				generator.doGenerate(new BasicMonitor());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launches the generation for all loaded modules.
	 * 
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void doGenerate(Monitor monitor) throws IOException {
		if (!targetFolder.exists()) {
			targetFolder.mkdirs();
		}
		new AcceleoService().doGenerate(loadedModules, model, targetFolder, monitor);
	}

	/**
	 * Returns the root element of the input model.
	 * 
	 * @return Root element of the input model.
	 */
	public EObject getModel() {
		return model;
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 */
	private Resource createResource(URI modelURI, ResourceSet resourceSet) {
		String fileExtension = modelURI.fileExtension();
		if (fileExtension == null || fileExtension.length() == 0) {
			fileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;
		}
		final Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
		final Object resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);
		if (resourceFactory != null) {
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
					resourceFactory);
		} else {
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
					new XMIResourceFactoryImpl());
		}
		return resourceSet.createResource(modelURI);
	}

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 */
	private EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		return (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary().getBag());
	}

	/**
	 * Loads a model from an {@link org.eclipse.emf.common.util.URI URI} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The model loaded from the URI.
	 * @throws IOException
	 *             If the given file does not exist.
	 */
	private EObject load(URI modelURI, ResourceSet resourceSet) throws IOException {
		EObject result = null;
		final Resource modelResource = createResource(modelURI, resourceSet);
		final Map<String, String> options = new HashMap<String, String>();
		modelResource.load(options);
		if (modelResource.getContents().size() > 0) {
			result = modelResource.getContents().get(0);
		}
		return result;
	}

	/**
	 * This will load all modules specified in {@link #generationModules}.
	 * 
	 * @param resourceSet
	 *            The ResourceSet in which modules are to be loaded.
	 * @throws IOException
	 *             This will be thrown if we cannot load one of the specified generation modules.
	 */
	@SuppressWarnings("unchecked")
	private void loadModules(ResourceSet resourceSet) throws IOException {
		for (Map.Entry<String, String[]> entry : generationModules.entrySet()) {
			final URL moduleURL;
			if (EMFPlugin.IS_ECLIPSE_RUNNING) {
				final Enumeration<URL> emtlFiles = Platform.getBundle(MODULE_PLUGIN_ID).findEntries("/", //$NON-NLS-1$
						entry.getKey() + ".emtl", true); //$NON-NLS-1$
				moduleURL = FileLocator.toFileURL(emtlFiles.nextElement());
			} else {
				moduleURL = Launcher.class.getResource(entry.getKey() + ".emtl"); //$NON-NLS-1$
			}
			if (moduleURL == null) {
				throw new IOException("'" + entry.getKey() + ".emtl' not found"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				URI moduleURI = URI.createFileURI(URI.decode(moduleURL.getPath()));
				Module module = (Module)load(moduleURI, resourceSet);
				Set<String> templates = new LinkedHashSet<String>(entry.getValue().length);
				for (String template : entry.getValue()) {
					templates.add(template);
				}
				loadedModules.put(module, templates);
			}
		}
	}

	/**
	 * Updates the resource set registry used for looking up a package based namespaces.
	 * 
	 * @param resourceSet
	 *            ResourceSet which registry is to be updated.
	 */
	private void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(
				org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage.eINSTANCE.getNsURI(),
				org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.emf.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
	}

	/**
	 * Updates the resource set registry used for looking up resource factories.
	 * 
	 * @param resourceSet
	 *            ResourceSet which registry is to be updated.
	 */
	private void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emtl", //$NON-NLS-1$
				new org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
	}
}
