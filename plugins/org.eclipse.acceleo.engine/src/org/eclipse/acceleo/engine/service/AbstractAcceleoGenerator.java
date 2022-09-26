/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoServicesRegistry;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.AcceleoServicesEclipseUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.BundleURLConverter;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.DoNotGenerateGenerationStrategy;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoaderService;
import org.eclipse.acceleo.engine.service.properties.BasicAcceleoPropertiesLoaderService;
import org.eclipse.acceleo.engine.service.properties.BundleAcceleoPropertiesLoaderService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceFactoryRegistry;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin.ExtensionProcessor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.osgi.framework.Bundle;

/**
 * This will be used as the base class for all generated launchers (the java classes generated beside mtl
 * modules).
 * <p>
 * The basic behavior of subclasses will be to call a template which first argument is taken from a model
 * (we'll iterate on the whole content of the model/EObject passed in our constructor to find elements
 * matching the first parameter of said template) and optional arguments.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public abstract class AbstractAcceleoGenerator {
	/** If the generation requires any additional arguments, they'll be stored here. */
	protected List<? extends Object> generationArguments;

	/**
	 * This will hold the list of generation listeners that are to be notified when text is generated.
	 */
	protected List<IAcceleoTextGenerationListener> generationListeners = new ArrayList<IAcceleoTextGenerationListener>(
			1);

	/** The model on which to iterate for this generation. */
	protected EObject model;

	/** The root element of the generation module for this launcher. */
	protected Module module;

	/**
	 * This will hold the list of properties files that are to be added to the generation context.
	 * 
	 * @deprected return a list of properties files directly through {@link #getProperties()} instead.
	 */
	@Deprecated
	protected List<String> propertiesFiles = new ArrayList<String>(1);

	/** The folder that will be considered "root" of the generated files. */
	protected File targetFolder;

	/**
	 * This will be used to know which resource to <u>not</u> unload from the resourceSet post generation.
	 * 
	 * @since 3.0
	 */
	protected Set<Resource> originalResources = new CompactHashSet<Resource>();

	/**
	 * The properties loader service is used to retrieve properties.
	 * 
	 * @since 3.1
	 */
	protected AbstractAcceleoPropertiesLoaderService acceleoPropertiesLoaderService;

	/**
	 * The original resource factory registry of the resource set containing the model if the generation is
	 * launched from {@link #initialize(EObject, File, List)}. This attribute is used only by the
	 * {@link AbstractAcceleoGenerator} to restore the resource factory registry after the generation. It
	 * should <b>not</b> be used for anything else.
	 * 
	 * @since 3.1
	 */
	protected Resource.Factory.Registry resourceFactoryRegistry;

	/**
	 * The generation ID is a unique identifier describing all the parameters of the generation. It is
	 * computed thanks to
	 * {@link org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil#computeLaunchConfigID(String, String, String, String, List)}
	 * .
	 * 
	 * @since 3.1
	 */
	protected String generationID;

	/**
	 * Allows clients to add a generation listener to this generator instance.
	 * 
	 * @param listener
	 *            The listener that is to be registered for this generator.
	 * @since 3.0
	 */
	public void addGenerationListener(IAcceleoTextGenerationListener listener) {
		generationListeners.add(listener);
	}

	/**
	 * Launches the generation described by this instance.
	 * 
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @throws IOException
	 *             This will be thrown if any of the output files cannot be saved to disk.
	 */
	public void doGenerate(Monitor monitor) throws IOException {
		generate(monitor);
	}

	/**
	 * Launches the generation described by this instance.
	 * 
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @return This will return a preview of the generation when the generation strategy feeds it to us.
	 * @throws IOException
	 *             This will be thrown if any of the output files cannot be saved to disk.
	 */
	public Map<String, String> generate(Monitor monitor) throws IOException {
		return generate(monitor, true);
	}

	/**
	 * Launches the generation described by this instance.
	 * 
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @param recursive
	 *            if <code>true</code> the {@link AbstractAcceleoGenerator#getModel() model} will be iterated
	 *            over
	 * @return This will return a preview of the generation when the generation strategy feeds it to us.
	 * @throws IOException
	 *             This will be thrown if any of the output files cannot be saved to disk.
	 * @since 3.5
	 */
	public Map<String, String> generate(Monitor monitor, boolean recursive) throws IOException {
		boolean notificationsState = false;
		if (EMFPlugin.IS_ECLIPSE_RUNNING && !AcceleoPreferences.areNotificationsForcedDisabled()) {
			notificationsState = AcceleoPreferences.areNotificationsEnabled();
			AcceleoPreferences.switchNotifications(true);
		}
		File target = getTargetFolder();
		checkTargetExists(target);
		AcceleoService service = createAcceleoService();
		String[] templateNames = getTemplateNames();
		Map<String, String> result = new HashMap<String, String>();

		// Start
		service.doPrepareGeneration(monitor, target);

		acceleoPropertiesLoaderService = getPropertiesLoaderService(service);
		if (acceleoPropertiesLoaderService != null) {
			acceleoPropertiesLoaderService.initializeService(getProperties());
		}

		for (int i = 0; i < templateNames.length; i++) {
			result.putAll(service.doGenerate(getModule(), templateNames[i], getModel(), recursive,
					getArguments(), target, monitor));
		}

		// End
		service.finalizeGeneration();

		postGenerate(getModule().eResource().getResourceSet());
		originalResources.clear();
		service.clearCaches();

		if (!service.hasGenerationOccurred()) {
			if (EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoPreferences.isDebugMessagesEnabled()) {
				AcceleoEnginePlugin.log(AcceleoEngineMessages
						.getString("AcceleoService.NoGenerationHasOccurred"), false); //$NON-NLS-1$				
			} else if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
				System.err.println(AcceleoEngineMessages.getString("AcceleoService.NoGenerationHasOccurred")); //$NON-NLS-1$
			}
		}
		if (EMFPlugin.IS_ECLIPSE_RUNNING && !AcceleoPreferences.areNotificationsForcedDisabled()) {
			AcceleoPreferences.switchNotifications(notificationsState);
		}
		return result;
	}

	/**
	 * Checks that the target folder exists or can be created on disk.
	 * 
	 * @param target
	 *            target folder for this generation.
	 * @throws IOException
	 *             if the folder doesn't exist and cannot be created.
	 * @since 3.6
	 */
	public void checkTargetExists(File target) throws IOException {
		if (!target.exists() && !target.mkdirs()) {
			throw new IOException("target directory " + target + " couldn't be created."); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * If the generation template called from this launcher needs any additional arguments, and you wish to
	 * change what was given the constructor, you can alter them here.
	 * 
	 * @return The list of additional arguments for this generation.
	 */
	public List<? extends Object> getArguments() {
		return generationArguments;
	}

	/**
	 * If this generator needs to listen to text generation events, listeners can be returned from here.
	 * 
	 * @return List of listeners that are to be notified when text is generated through this launch.
	 */
	public List<IAcceleoTextGenerationListener> getGenerationListeners() {
		return new ArrayList<IAcceleoTextGenerationListener>();
	}

	/**
	 * If you need to change the way files are generated, this is your entry point.
	 * <p>
	 * The default is {@link org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy}; it generates
	 * files on the fly. If you only need to preview the results, return a new
	 * {@link org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy}. Both of these aren't aware of
	 * the running Eclipse and can be used standalone.
	 * </p>
	 * <p>
	 * If you need the file generation to be aware of the workspace (A typical example is when you wanna
	 * override files that are under clear case or any other VCS that could forbid the overriding), then
	 * return a new {@link org.eclipse.acceleo.engine.generation.strategy.WorkspaceAwareStrategy}.
	 * <b>Note</b>, however, that this <b>cannot</b> be used standalone.
	 * </p>
	 * <p>
	 * All three of these default strategies support merging through JMerge.
	 * </p>
	 * 
	 * @return The generation strategy that is to be used for generations launched through this launcher.
	 */
	public IAcceleoGenerationStrategy getGenerationStrategy() {
		return new DefaultStrategy();
	}

	/**
	 * If you wish to launch the generation on something else than {@link #model}, alter it here.
	 * 
	 * @return The root EObject on which to iterate for this generation.
	 */
	public EObject getModel() {
		return model;
	}

	/**
	 * If you wish to launch the generation on another module than {@link #module}, alter it here.
	 * 
	 * @return The module on which we'll seek the generation templates to launch.
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * This will be called in order to find and load the module that will be launched through this launcher.
	 * We expect this name not to contain file extension, and the module to be located beside the launcher.
	 * 
	 * @return The name of the module that is to be launched.
	 */
	public abstract String getModuleName();

	/**
	 * If the module(s) called by this launcher require properties files, return their qualified path from
	 * here.Take note that the first added properties files will take precedence over subsequent ones if they
	 * contain conflicting keys.
	 * <p>
	 * Properties need to be in source folders, the path that we expect to get as a result of this call are of
	 * the form &lt;package>.&lt;properties file name without extension>. For example, if you have a file
	 * named "messages.properties" in package "org.eclipse.acceleo.sample", the path that needs be returned by
	 * a call to {@link #getProperties()} is "org.eclipse.acceleo.sample.messages".
	 * </p>
	 * 
	 * @return The list of properties file we need to add to the generation context.
	 */
	public List<String> getProperties() {
		return new ArrayList<String>();
	}

	/**
	 * Adds a properties file in the list of properties files.
	 * 
	 * @param propertiesFile
	 *            The properties file to add.
	 * @since 3.1
	 */
	public void addPropertiesFile(String propertiesFile) {
		// do nothing
	}

	/**
	 * If you wish to generate files in another folder than {@link #targetFolder}, alter it here.
	 * 
	 * @return The root directory in which to generate files.
	 */
	public File getTargetFolder() {
		return targetFolder;
	}

	/**
	 * This will be used to get the list of templates that are to be launched by this launcher.
	 * 
	 * @return The list of templates to call on the module {@link #getModuleName()}.
	 */
	public abstract String[] getTemplateNames();

	/**
	 * This will initialize all required information for this generator.
	 * 
	 * @param element
	 *            We'll iterate over the content of this element to find Objects matching the first parameter
	 *            of the template we need to call.
	 * @param folder
	 *            This will be used as the output folder for this generation : it will be the base path
	 *            against which all file block URLs will be resolved.
	 * @param arguments
	 *            If the template which will be called requires more than one argument taken from the model,
	 *            pass them here.
	 * @throws IOException
	 *             This can be thrown in two scenarios : the module cannot be found, or it cannot be loaded.
	 */
	public void initialize(EObject element, File folder, List<? extends Object> arguments) throws IOException {
		ResourceSet resourceSet = new AcceleoResourceSetImpl();
		resourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);

		resourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
		resourceSet.setResourceFactoryRegistry(new AcceleoResourceFactoryRegistry(resourceFactoryRegistry));

		originalResources.addAll(resourceSet.getResources());

		URIConverter uriConverter = createURIConverter();
		if (uriConverter != null) {
			resourceSet.setURIConverter(uriConverter);
		}

		// make sure that metamodel projects in the workspace override those in plugins
		resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());

		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		addListeners();
		addProperties();

		String moduleName = getModuleName();
		if (moduleName.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
			moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
		}
		if (!moduleName.endsWith('.' + IAcceleoConstants.EMTL_FILE_EXTENSION)) {
			moduleName += '.' + IAcceleoConstants.EMTL_FILE_EXTENSION;
		}

		URL moduleURL = findModuleURL(moduleName);

		if (moduleURL == null) {
			throw new IOException("'" + getModuleName() + ".emtl' not found"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		URI moduleURI = createTemplateURI(moduleURL.toString());
		moduleURI = URI.createURI(moduleURI.toString(), true);
		module = (Module)ModelUtils.load(moduleURI, resourceSet);
		model = element;
		targetFolder = folder;
		generationArguments = arguments;

		this.postInitialize();
	}

	/**
	 * This will initialize all required information for this generator.
	 * 
	 * @param modelURI
	 *            URI where the model on which this generator will be used is located.
	 * @param folder
	 *            This will be used as the output folder for this generation : it will be the base path
	 *            against which all file block URLs will be resolved.
	 * @param arguments
	 *            If the template which will be called requires more than one argument taken from the model,
	 *            pass them here.
	 * @throws IOException
	 *             This can be thrown in three scenarios : the module cannot be found, it cannot be loaded, or
	 *             the model cannot be loaded.
	 */
	public void initialize(URI modelURI, File folder, List<?> arguments) throws IOException {
		ResourceSet modulesResourceSet = new AcceleoResourceSetImpl();
		modulesResourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);

		URIConverter uriConverter = createURIConverter();
		if (uriConverter != null) {
			modulesResourceSet.setURIConverter(uriConverter);
		}

		Map<URI, URI> uriMap = EcorePlugin.computePlatformURIMap();

		// make sure that metamodel projects in the workspace override those in plugins
		modulesResourceSet.getURIConverter().getURIMap().putAll(uriMap);

		registerResourceFactories(modulesResourceSet);
		registerPackages(modulesResourceSet);

		ResourceSet modelResourceSet = new AcceleoResourceSetImpl();
		modelResourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
		if (uriConverter != null) {
			modelResourceSet.setURIConverter(uriConverter);
		}

		// make sure that metamodel projects in the workspace override those in plugins
		modelResourceSet.getURIConverter().getURIMap().putAll(uriMap);

		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			URLClassLoader classLoader = (URLClassLoader)(Thread.currentThread().getContextClassLoader());
			ExtensionProcessor.process(classLoader);
			new AcceleoEnginePlugin().parseExtensionPoints();
			// processStandaloneClassPath(modulesResourceSet);
		}

		registerResourceFactories(modelResourceSet);
		registerPackages(modelResourceSet);

		addListeners();
		addProperties();

		String moduleName = getModuleName();
		if (moduleName.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
			moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
		}
		if (!moduleName.endsWith('.' + IAcceleoConstants.EMTL_FILE_EXTENSION)) {
			moduleName += '.' + IAcceleoConstants.EMTL_FILE_EXTENSION;
		}

		URL moduleURL = findModuleURL(moduleName);

		if (moduleURL == null) {
			throw new IOException("'" + getModuleName() + ".emtl' not found"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		URI moduleURI = createTemplateURI(moduleURL.toString());
		moduleURI = URI.createURI(moduleURI.toString(), true);
		module = (Module)ModelUtils.load(moduleURI, modulesResourceSet);

		URI newModelURI = URI.createURI(modelURI.toString(), true);
		model = ModelUtils.load(newModelURI, modelResourceSet);
		targetFolder = folder;
		generationArguments = arguments;

		this.postInitialize();
	}

	/**
	 * This method is called after the initialization of the generator.
	 * 
	 * @since 3.3
	 */
	protected void postInitialize() {
		// do nothing by default
	}

	/**
	 * Checks whether the given EPackage class is located in the workspace.
	 * 
	 * @param ePackageClass
	 *            The EPackage class we need to take into account.
	 * @return <code>true</code> if the given class has been loaded from a dynamically installed bundle,
	 *         <code>false</code> otherwise.
	 * @since 3.1
	 */
	public boolean isInWorkspace(Class<? extends EPackage> ePackageClass) {
		return EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoWorkspaceUtil.INSTANCE.isInDynamicBundle(ePackageClass);
	}

	/**
	 * This will update the resource set's package registry with all usual EPackages.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	public void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
				ExpressionsPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
	}

	/**
	 * This will update the resource set's resource factory registry with all usual factories.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	public void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}

	/**
	 * Sets the generation ID.
	 * 
	 * @param generationID
	 *            The generation ID.
	 * @since 3.1
	 */
	public void setGenerationID(String generationID) {
		this.generationID = generationID;
	}

	/**
	 * If you need to listen to generation events, generation listeners can be added for notification through
	 * this.Listeners can be added to the {@link #generationListeners} list.
	 * 
	 * @deprecated return a list of generation listeners directly through {@link #getGenerationListeners()}
	 *             instead.
	 */
	@Deprecated
	protected void addListeners() {
		// empty implementation
	}

	/**
	 * If the generation modules need properties files, this is where to add them. Take note that the first
	 * added properties files will take precedence over subsequent ones if they contain conflicting keys.
	 * 
	 * @deprecated return a list of properties files path directly through {@link #getProperties()} instead.
	 */
	@Deprecated
	protected void addProperties() {
		// empty implementation
	}

	/**
	 * This will be used prior to each generation to create the instance of {@link AcceleoService} that will
	 * be used to interface with the generation engine.
	 * <p>
	 * This default implementation should be sufficient for all cases, it will delegate creation of the
	 * generation strategy to {@link #getGenerationStrategy()}, register the generation listeners as returned
	 * by {@link #getGenerationListeners()}, then register all properties as returned by [@link
	 * #getProperties()} in the generation context.
	 * </p>
	 * 
	 * @return The Acceleo service that is to be used for generations launched through this launcher.
	 */
	protected AcceleoService createAcceleoService() {
		IAcceleoGenerationStrategy generationStrategy = getGenerationStrategy();
		DoNotGenerateGenerationStrategy doNotGenerateGenerationStrategy = new DoNotGenerateGenerationStrategy(
				generationStrategy);
		AcceleoService service = new AcceleoService(doNotGenerateGenerationStrategy);
		for (IAcceleoTextGenerationListener listener : getGenerationListeners()) {
			service.addListener(listener);
		}
		for (IAcceleoTextGenerationListener listener : generationListeners) {
			service.addListener(listener);
		}

		service.setGenerationID(generationID);

		return service;
	}

	/**
	 * This will be called by the engine when the generation stops. By default, we'll use this opportunity to
	 * unload the resources we've loaded in the resource set.
	 * 
	 * @param resourceSet
	 *            The resource set from which to unload resources.
	 * @since 3.0
	 */
	protected void postGenerate(ResourceSet resourceSet) {
		AcceleoServicesRegistry.INSTANCE.clearRegistry();
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			AcceleoServicesEclipseUtil.clearRegistry();
		}
		List<Resource> unload = new ArrayList<Resource>(resourceSet.getResources());
		unload.removeAll(originalResources);
		for (Resource res : unload) {
			res.unload();
			resourceSet.getResources().remove(res);
		}

		clearPackageRegistry();

		resourceSet.setResourceFactoryRegistry(resourceFactoryRegistry);
	}

	/**
	 * Clears the package registry from the package registrations coming from the workspace.
	 * 
	 * @since 3.1
	 */
	protected void clearPackageRegistry() {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			AcceleoPackageRegistry instance = AcceleoPackageRegistry.INSTANCE;
			Set<Entry<String, Object>> entrySet = new LinkedHashSet<Map.Entry<String, Object>>(instance
					.entrySet());
			for (Entry<String, Object> entry : entrySet) {
				Object object = entry.getValue();
				if (object instanceof EPackage) {
					EPackage ePackage = (EPackage)object;
					if (AcceleoWorkspaceUtil.INSTANCE.isInDynamicBundle(ePackage.getClass())) {
						instance.remove(entry.getKey());
					} else if (ePackage.eResource() != null && ePackage.eResource().getResourceSet() != null
							&& ePackage.eResource().getResourceSet().getPackageRegistry() == instance) {
						instance.remove(entry.getKey());
					}
				}
			}
		}
	}

	/**
	 * Returns the Acceleo properties loader service which will handle the AcceleoPropertiesLoader to load and
	 * save the properties files.
	 * 
	 * @param acceleoService
	 *            The Acceleo service
	 * @return The Acceleo properties loader service which will handle the AcceleoPropertiesLoader to load and
	 *         save the properties files.
	 * @since 3.1
	 */
	public AbstractAcceleoPropertiesLoaderService getPropertiesLoaderService(AcceleoService acceleoService) {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			Bundle bundle = AcceleoWorkspaceUtil.getBundle(getClass());
			return new BundleAcceleoPropertiesLoaderService(acceleoService, bundle);
		}
		return new BasicAcceleoPropertiesLoaderService(acceleoService);
	}

	/**
	 * Creates the URI Converter we'll use to load our modules. Take note that this should never be used out
	 * of Eclipse.
	 * 
	 * @return The created URI Converter.
	 * @since 3.0
	 */
	protected URIConverter createURIConverter() {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			return null;
		}

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
	 * Creates the URI that will be used to resolve the template that is to be launched.
	 * 
	 * @param entry
	 *            The path towards the template file. Could be a jar or file scheme URI, or we'll assume it
	 *            represents a relative path.
	 * @return The actual URI from which the template file can be resolved.
	 */
	protected URI createTemplateURI(String entry) {
		if (entry.startsWith("file:") || entry.startsWith("jar:")) { //$NON-NLS-1$ //$NON-NLS-2$ 
			return URI.createURI(URI.decode(entry));
		}
		return URI.createFileURI(URI.decode(entry));
	}

	/**
	 * Clients can override this if the default behavior doesn't properly find the emtl module URL.
	 * 
	 * @param moduleName
	 *            Name of the module we're searching for.
	 * @return The template's URL. This will use Eclipse-specific behavior if possible, and use the class
	 *         loader otherwise.
	 */
	protected URL findModuleURL(String moduleName) {
		URL moduleURL = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			try {
				moduleURL = AcceleoWorkspaceUtil.getResourceURL(getClass(), moduleName);
			} catch (IOException e) {
				// Swallow this, we'll try and locate the module through the class loader
			}
		}
		if (moduleURL == null) {
			moduleURL = getClass().getResource(moduleName);
		}
		return moduleURL;
	}

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 */
	protected EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}
}
