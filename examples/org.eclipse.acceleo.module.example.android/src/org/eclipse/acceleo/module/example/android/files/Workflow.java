/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.module.example.android.files;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;

/**
 * Entry point of the 'Workflow' generation module.
 * @generated
 */
public class Workflow {

	/**
	 * The name of the module.
	 * @generated
	 */
	public static final String MODULE_FILE_NAME = "workflow";
	
	/**
	 * The name of the templates that are to be generated.
	 * @generated
	 */
	public static final String[] TEMPLATE_NAMES = { "workflow", };

	/**
	 * The root element of the module.
	 * @generated
	 */
	private Module module;

	/**
	 * The model.
	 * @generated
	 */
	private EObject model;

	/**
	 * The output folder.
	 * @generated
	 */
	private File targetFolder;

	/**
	 * The other arguments.
	 * @generated
	 */
	private List<? extends Object> arguments;
	
	/**
	 * This will hold the list of generation listeners that are to be notified when text is generated.
	 * @generated
	 */
	private List<IAcceleoTextGenerationListener> generationListeners = new ArrayList<IAcceleoTextGenerationListener>();

	/**
	 * This will hold the list of properties files that are to be added to the generation context.
	 * @generated
	 */
	private List<String> propertiesFiles = new ArrayList<String>();

	/**
	 * Constructor.
	 * 
	 * @param modelURI
	 *            is the URI of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public Workflow(URI modelURI, File targetFolder, List<? extends Object> arguments) throws IOException {
    ResourceSet resourceSet = new ResourceSetImpl();
    registerResourceFactories(resourceSet);
    registerPackages(resourceSet);
    addListeners();
    addProperties();
    final URL templateURL;
    if (EMFPlugin.IS_ECLIPSE_RUNNING) {
      URL resourceURL = Workflow.class.getResource(MODULE_FILE_NAME + ".emtl");
      if (resourceURL != null) {
        templateURL = FileLocator.toFileURL(resourceURL);
      } else {
        templateURL = null;
      }
    } else {
      templateURL = Workflow.class.getResource(MODULE_FILE_NAME + ".emtl");
    }
    if (templateURL == null) {
      throw new IOException("'" + MODULE_FILE_NAME + ".emtl' not found");
    } else {
      URI templateURI = createTemplateURI(templateURL.getPath());
      module = (Module)load(templateURI, resourceSet);
      model = load(modelURI, resourceSet);
      this.targetFolder = targetFolder;
      this.arguments = arguments;
    }
  }

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            is the root element of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public Workflow(EObject model, File targetFolder, List<? extends Object> arguments) throws IOException {
    ResourceSet resourceSet = model.eResource().getResourceSet();
    registerResourceFactories(resourceSet);
    registerPackages(resourceSet);
    addListeners();
    addProperties();
    final URL templateURL;
    if (EMFPlugin.IS_ECLIPSE_RUNNING) {
      URL resourceURL = Workflow.class.getResource(MODULE_FILE_NAME + ".emtl");
      if (resourceURL != null) {
        templateURL = FileLocator.toFileURL(resourceURL);
      } else {
        templateURL = null;
      }
    } else {
      templateURL = Workflow.class.getResource(MODULE_FILE_NAME + ".emtl");
    }
    if (templateURL == null) {
      throw new IOException("'" + MODULE_FILE_NAME + ".emtl' not found");
    } else {
      URI templateURI = createTemplateURI(templateURL.getPath());
      module = (Module)load(templateURI, resourceSet);
      this.model = model;
      this.targetFolder = targetFolder;
      this.arguments = arguments;
    }
  }

	/**
	 * Creates the template URI.
	 * 
	 * @param entry
	 *            is the local path of the EMTL file
	 * @generated
	 */
	protected URI createTemplateURI(String entry) {
    return URI.createFileURI(URI.decode(entry));
  }

	/**
	 * Gets the model.
	 * @return the model root element
	 * @generated
	 */
	public EObject getModel() {
    return model;
  }

	/**
	 * Updates the registry used for looking up a package based namespace, in the resource set.
	 * 
	 * @param resourceSet
	 *            is the resource set
	 * @generated
	 */
	private void registerPackages(ResourceSet resourceSet) {
    resourceSet.getPackageRegistry().put(org.eclipse.acceleo.module.example.android.AndroidPackage.eINSTANCE.getNsURI(), org.eclipse.acceleo.module.example.android.AndroidPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(org.eclipse.acceleo.module.example.android.widgets.WidgetsPackage.eINSTANCE.getNsURI(), org.eclipse.acceleo.module.example.android.widgets.WidgetsPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(), org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE.getNsURI(), org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
    resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", getOCLStdLibPackage());
  }
	
	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 * @generated
	 */
	private EPackage getOCLStdLibPackage() {
    EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
    EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
    return (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary().getBag());
  }
	
	/**
	 * Updates the registry used for looking up resources factory in the given resource set.
	 *
	 * @param resourceSet
	 *            The resource set that is to be updated.
	 * @generated
	 */
	private void registerResourceFactories(ResourceSet resourceSet) {
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emtl", new org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl());
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
  }

	/**
	 * The main method.
	 * 
	 * @param args
	 *            are the arguments
	 * @generated
	 */
	public static void main(String[] args) {
    try {
      if (args.length < 2) {
        System.out.println("Arguments not valid : {model, folder}.");
      } else {
        URI modelURI = URI.createFileURI(args[0]);
        File folder = new File(args[1]);
        List<String> arguments = new ArrayList<String>();
        for (int i = 2; i < args.length; i++) {
          arguments.add(args[i]);
        }
        Workflow generator = new Workflow(modelURI, folder, arguments);
        generator.doGenerate(new BasicMonitor());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

	/**
	 * Launches the generation.
	 * 
	 * @param monitor
	 *             This will be used to display progress information to the user.
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public void doGenerate(Monitor monitor) throws IOException {
    if (!targetFolder.exists()) {
      targetFolder.mkdirs();
    }
    AcceleoService service = new AcceleoService(getGenerationStrategy());
    registerListeners(service);
    registerProperties(service);
    for (int i = 0; i < TEMPLATE_NAMES.length; i++) {
      service.doGenerate(module, TEMPLATE_NAMES[i], model, arguments, targetFolder, monitor);
    }
    service.dispose();
  }
	
	/**
	 * If you need to change the way files are generated, this is your entry point.
	 * <p>
	 * The default is {@link org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy}; it generates
	 * files on the fly. If you only need to preview the results, return a new
	 * {@link org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy}. Both of these aren't aware
	 * of the running Eclipse and can be used standalone.
	 * </p>
	 * <p>
	 * If you need the file generation to be aware of the workspace (A typical example is when you wanna override
	 * files that are under clear case or any other VCS that could forbid the overriding), then return a new
	 * {@link org.eclipse.acceleo.engine.generation.strategy.WorkspaceAwareStrategy}. <b>Note</b>, however, that
	 * this <b>cannot</b> be used standalone.
	 * </p>
	 * <p>
	 * All three of these default strategies support merging through JMerge.
	 * </p>
	 */
	public IAcceleoGenerationStrategy getGenerationStrategy() {
		return new DefaultStrategy();
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
	 * @generated
	 */
	private EObject load(URI modelURI, ResourceSet resourceSet) throws IOException {
    EObject result = null;
    final Resource modelResource = createResource(modelURI, resourceSet);
    final Map<String, String> options = new HashMap<String, String>();
    options.put(XMLResource.OPTION_ENCODING, System.getProperty("file.encoding"));
    modelResource.load(options);
    if (modelResource.getContents().size() > 0) {
      result = modelResource.getContents().get(0);
    }
    return result;
  }

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 * @generated
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
	 * Generation listeners can be added for notification through this.
	 *
	 * @generated
	 */
	private void addListeners() {
    /*
     * TODO : add listeners to the "generationListener" field here. Make sure you remove the
     * @generated annotation or set it to "@generated NOT".
     */
  }

	/**
	 * If the generation modules need properties files, this is where to add them. Take note that the first
	 * added properties files will take precedence over subsequent ones if they contain conflicting keys.
	 *
	 * @generated
	 */
	private void addProperties() {
    /*
     * TODO : add file pathes to the "propertiesFiles" field here. properties files can be added with
     * relative or absolute pathes, or their path can represent a platform scheme URI. Make sure you remove
     * the @generated annotation or set it to "@generated NOT".
     */
  }
	
	/**
	 * This is in charge of registering all listeners against the given service instance.
	 *
	 * @generated
	 */
	private void registerListeners(AcceleoService service) {
    for (IAcceleoTextGenerationListener listener : generationListeners) {
      service.addListener(listener);
    }
  }

	/**
	 * This will register all properties files against the given service instance.
	 *
	 * @generated
	 */
	private void registerProperties(AcceleoService service) {
    try {
      for (String propertyFile : propertiesFiles) {
        service.addPropertiesFile(propertyFile);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
