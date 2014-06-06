/*******************************************************************************
 * Copyright (c) 2008, 2014 Obeo.
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.Set;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.generation.IAcceleoEngine;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.internal.utils.AcceleoEngineRegistry;
import org.eclipse.acceleo.engine.internal.utils.AcceleoListenerDescriptor;
import org.eclipse.acceleo.engine.internal.utils.AcceleoTraceabilityRegistryListenerUils;
import org.eclipse.acceleo.engine.internal.utils.DefaultEngineSelector;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

/**
 * This class provides utility methods to launch the generation of an Acceleo template.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoService {
	/** This list will hold listeners that are to be used for any and all generations. */
	private static final List<IAcceleoTextGenerationListener> STATIC_LISTENERS = new ArrayList<IAcceleoTextGenerationListener>();

	/** This message will be set for all NPE thrown because of null arguments for this utility's methods. */
	private static final String TEMPLATE_CALL_NPE = AcceleoEngineMessages
			.getString("AcceleoService.NullArguments"); //$NON-NLS-1$

	/** The key of the undefined template message. */
	private static final String UNDEFINED_TEMPLATE = "AcceleoService.UndefinedTemplate"; //$NON-NLS-1$

	/** The engine we'll use for all generations through this service instance. */
	private IAcceleoEngine generationEngine;

	/** The current generation strategy. */
	private final IAcceleoGenerationStrategy strategy;

	/**
	 * The listeners added to the initial engine.
	 * 
	 * @since 3.1
	 **/
	private final List<IAcceleoTextGenerationListener> addedListeners = new ArrayList<IAcceleoTextGenerationListener>();

	/**
	 * The properties files added to the initial engine.
	 * 
	 * @since 3.1
	 **/
	private final List<String> addedPropertiesfiles = new ArrayList<String>();

	/**
	 * The properties added to the initial engine.
	 * 
	 * @since 3.1
	 **/
	private final List<Map<String, String>> addedProperties = new ArrayList<Map<String, String>>();

	/**
	 * The generation ID.
	 */
	private String generationID;

	/**
	 * Indicates if we should deactivate the traceability.
	 */
	private boolean deactivateTraceability;

	/**
	 * Indicates if the generation has occurred.
	 */
	private boolean generationHasOccurred;

	/**
	 * Indicates if the generation has started.
	 */
	private boolean generationIsOccurring;

	/**
	 * The list of descriptor used during this generation.
	 */
	private List<AcceleoListenerDescriptor> descriptorsUsed;

	/**
	 * Indicates if this generation uses a forced traceability.
	 */
	private boolean forceTraceability;

	/**
	 * Instantiates an instance of the service with a default generation strategy.
	 * 
	 * @since 3.0
	 */
	public AcceleoService() {
		this(null);
	}

	/**
	 * Instantiates an instance of the service given the generation strategy that is to be used for this
	 * generation.
	 * 
	 * @param generationStrategy
	 *            Generation strategy that'll be used for this generation.
	 * @since 3.0
	 */
	public AcceleoService(IAcceleoGenerationStrategy generationStrategy) {
		if (generationStrategy == null) {
			strategy = new DefaultStrategy();
		} else {
			strategy = generationStrategy;
		}
		createEngine();
	}

	/**
	 * Registers a listener to be notified for any text generation. This will have to be removed manually
	 * through {@link #removeListener(IAcceleoTextGenerationListener)} if the listeners mustn't be used for a
	 * given generation.
	 * 
	 * @param listener
	 *            The new listener that is to be registered for notification.
	 * @since 3.0
	 */
	public static void addStaticListener(IAcceleoTextGenerationListener listener) {
		STATIC_LISTENERS.add(listener);
	}

	/**
	 * Removes a static listener from the notification loops.
	 * 
	 * @param listener
	 *            The listener that is to be removed from the notification loops.
	 * @since 3.0
	 */
	public static void removeStaticListener(IAcceleoTextGenerationListener listener) {
		STATIC_LISTENERS.remove(listener);
	}

	/**
	 * Registers a listener to be notified for any text generation that will take place in this engine
	 * evaluation process.
	 * 
	 * @param listener
	 *            The new listener that is to be registered for notification.
	 * @since 0.8
	 */
	public void addListener(IAcceleoTextGenerationListener listener) {
		generationEngine.addListener(listener);
		this.addedListeners.add(listener);
	}

	/**
	 * This will add custom key/value pairs to the generation context so that they can be accessed through the
	 * getProperty() services at generation time.
	 * <p>
	 * <b>Note</b> that such properties always take precedence over properties defined in a properties file.
	 * </p>
	 * 
	 * @param customProperties
	 *            key/value pairs that are to be added to the generation context.
	 * @since 3.0
	 */
	public void addProperties(Map<String, String> customProperties) {
		generationEngine.addProperties(customProperties);
		this.addedProperties.add(customProperties);
	}

	/**
	 * Adds the given properties file to the generation context so that its key/value pairs can be accessed
	 * through the getProperty() services at generation time.
	 * <p>
	 * <b>Note</b> that the first properties file added to this list will take precedence over subsequent
	 * ones.
	 * </p>
	 * <p>
	 * The given path can be either absolute or relative. If it represent an URI of platform scheme, we'll
	 * resolve this path against the current workspace.
	 * </p>
	 * <p>
	 * For example, if plugin A adds "a.properties" which contains a key "a.b.c" and calls a launcher
	 * contained by a second plugin B which itself contains "b.properties" containing key "a.b.c" :
	 * 
	 * <pre>
	 * getProperty('a.b.c')
	 * </pre>
	 * 
	 * will result in the value from a.properties being printed, whereas
	 * 
	 * <pre>
	 * getProperty('b.properties', 'a.b.c')
	 * </pre>
	 * 
	 * will return the value from b.properties.
	 * </p>
	 * <p>
	 * Take note that properties added through {@link #addProperties(Map)} will always take precedence over
	 * properties defined in a file.
	 * </p>
	 * 
	 * @param propertiesFile
	 *            Qualified path to the properties file that is to be added to the generation context.
	 * @throws MissingResourceException
	 *             This will be thrown if we cannot locate the properties file in the current classpath.
	 * @since 3.0
	 */
	public void addPropertiesFile(String propertiesFile) throws MissingResourceException {
		generationEngine.addProperties(propertiesFile);
		this.addedPropertiesfiles.add(propertiesFile);
	}

	/**
	 * This can be used to add custom properties to the engine. These will be available through the
	 * getProperty() services.
	 * 
	 * @param resourceBundle
	 *            The resource bundle.
	 * @param fileName
	 *            The name of the properties file.
	 * @since 3.1
	 */
	public void addProperties(PropertyResourceBundle resourceBundle, String fileName) {
		if (generationEngine instanceof AcceleoEngine) {
			AcceleoEngine engine = (AcceleoEngine)generationEngine;
			engine.addProperties(resourceBundle, fileName);
		}
	}

	/**
	 * Properly disposes of everything that could have been loaded from this service.
	 * 
	 * @deprecated This has no real use.
	 * @since 3.0
	 */
	@Deprecated
	public void dispose() {
		// empty implementation
	}

	/**
	 * This can be used to launch the generation of multiple Acceleo templates given their names and their
	 * containing modules.
	 * <p>
	 * Keep in mind that this can only be used with single-argument templates. Any attempt to call to a
	 * template with more than one argument through this method will throw {@link AcceleoEvaluationException}
	 * s.
	 * </p>
	 * <p>
	 * The input model will be iterated over for objects matching the templates' parameter types.
	 * </p>
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param templates
	 *            This map will be used to locate templates of the given names in the associated module.
	 * @param model
	 *            Input model for this generation.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. Cannot be <code>null</code> except if
	 *            <code>preview</code> is <code>true</code> in which case no files will be generated.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	public Map<String, String> doGenerate(Map<Module, Set<String>> templates, EObject model,
			File generationRoot, Monitor monitor) {
		return doGenerate(templates, model, true, generationRoot, monitor);
	}

	/**
	 * This can be used to launch the generation of multiple Acceleo templates given their names and their
	 * containing modules.
	 * <p>
	 * Keep in mind that this can only be used with single-argument templates. Any attempt to call to a
	 * template with more than one argument through this method will throw {@link AcceleoEvaluationException}
	 * s.
	 * </p>
	 * 
	 * @param templates
	 *            This map will be used to locate templates of the given names in the associated module.
	 * @param model
	 *            Input model for this generation.
	 * @param recursive
	 *            if <code>true</code> the input model will be iterated over for objects matching the
	 *            templates' parameter types
	 * @param generationRoot
	 *            This will be used as the root for the generated files. Cannot be <code>null</code> except if
	 *            <code>preview</code> is <code>true</code> in which case no files will be generated.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	public Map<String, String> doGenerate(Map<Module, Set<String>> templates, EObject model,
			boolean recursive, File generationRoot, Monitor monitor) {
		if (templates == null || model == null
				|| (!(strategy instanceof PreviewStrategy) && generationRoot == null)) {
			throw new NullPointerException(TEMPLATE_CALL_NPE);
		}
		Map<EClassifier, Set<Template>> templateTypes = new HashMap<EClassifier, Set<Template>>();
		for (Map.Entry<Module, Set<String>> entry : templates.entrySet()) {
			for (String templateName : entry.getValue()) {
				List<Template> templateList = findTemplates(entry.getKey(), templateName, 1);

				for (Template template : templateList) {
					EClassifier templateType = template.getParameter().get(0).getType();
					if (templateTypes.containsKey(templateType)) {
						templateTypes.get(templateType).add(template);
					} else {
						Set<Template> temp = new CompactHashSet<Template>();
						temp.add(template);
						templateTypes.put(templateType, temp);
					}
				}
			}
		}

		final Map<String, String> previewResult = new HashMap<String, String>();

		// Start
		this.generationIsOccurring = true;
		this.prepareGeneration(monitor, generationRoot);

		// Calls all templates with each of their potential arguments
		final List<Object> arguments = new ArrayList<Object>();
		// The input model itself is a potential argument
		arguments.add(model);
		for (Map.Entry<EClassifier, Set<Template>> entry : templateTypes.entrySet()) {
			if (entry.getKey().isInstance(model)) {
				for (Template template : entry.getValue()) {
					previewResult.putAll(doGenerateTemplate(template, arguments, generationRoot, monitor));
					generationHasOccurred = true;
				}
			}
		}
		if (recursive) {
			final TreeIterator<EObject> targetElements = model.eAllContents();
			while (targetElements.hasNext()) {
				final EObject potentialTarget = targetElements.next();
				for (Map.Entry<EClassifier, Set<Template>> entry : templateTypes.entrySet()) {
					if (entry.getKey().isInstance(potentialTarget)) {
						arguments.clear();
						arguments.add(potentialTarget);
						for (Template template : entry.getValue()) {
							previewResult.putAll(doGenerateTemplate(template, arguments, generationRoot,
									monitor));
							generationHasOccurred = true;
						}
					}
				}
			}
		}

		// End
		this.generationIsOccurring = false;
		this.finalizeGeneration();

		this.clearCaches();
		return previewResult;
	}

	/**
	 * Launches the generation of an Acceleo template given its name and containing module.
	 * <p>
	 * This is a convenience method that can only be used with single argument templates. The input model will
	 * be iterated over for objects matching the template's parameter type.
	 * </p>
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the template that is to be generated.
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param blockTraceability
	 *            This will indicate if we should deactivate the traceability (even if a listener register
	 *            thanks to the extension point requires it).
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.1
	 */
	public Map<String, String> doGenerate(Module module, String templateName, EObject model,
			File generationRoot, boolean blockTraceability, Monitor monitor) {
		this.deactivateTraceability = blockTraceability;
		Map<String, String> result = new HashMap<String, String>();
		List<Template> templates = findTemplates(module, templateName, 1);

		// Start
		this.generationIsOccurring = true;
		this.prepareGeneration(monitor, generationRoot);

		for (Template template : templates) {
			result.putAll(doGenerate(template, model, generationRoot, monitor));
		}

		// End
		this.generationIsOccurring = false;
		this.finalizeGeneration();
		return result;
	}

	/**
	 * Launches the generation of an Acceleo template given its name and containing module.
	 * <p>
	 * This is a convenience method that can only be used with single argument templates. The input model will
	 * be iterated over for objects matching the template's parameter type.
	 * </p>
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the template that is to be generated.
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	public Map<String, String> doGenerate(Module module, String templateName, EObject model,
			File generationRoot, Monitor monitor) {
		Map<String, String> result = new HashMap<String, String>();
		List<Template> templates = findTemplates(module, templateName, 1);

		// Start
		this.generationIsOccurring = true;
		this.prepareGeneration(monitor, generationRoot);

		for (Template template : templates) {
			result.putAll(doGenerate(template, model, generationRoot, monitor));
		}

		// End
		this.generationIsOccurring = false;
		this.finalizeGeneration();
		return result;
	}

	/**
	 * Launches the generation of an Acceleo template given its name and containing module.
	 * <p>
	 * This is a convenience method that can be used with multiple argument templates. The input model will be
	 * iterated over for objects matching the template's <b>first</b> parameter type. The template will then
	 * be called with these objects as first arguments, and the given list of <code>arguments</code> for the
	 * remaining template parameters.
	 * </p>
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the template that is to be generated.
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param arguments
	 *            Arguments of the template call, excluding the very first one (<code>model</code> object).
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 */
	public Map<String, String> doGenerate(Module module, String templateName, EObject model,
			List<? extends Object> arguments, File generationRoot, Monitor monitor) {
		return doGenerate(module, templateName, model, true, arguments, generationRoot, monitor);
	}

	/**
	 * Launches the generation of an Acceleo template given its name and containing module.
	 * <p>
	 * This is a convenience method that can be used with multiple argument templates. The template will then
	 * be called with these objects as first arguments, and the given list of <code>arguments</code> for the
	 * remaining template parameters.
	 * </p>
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the template that is to be generated.
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param recursive
	 *            if <code>true</code> the input model will be iterated over for objects matching the
	 *            template's <b>first</b> parameter type
	 * @param arguments
	 *            Arguments of the template call, excluding the very first one (<code>model</code> object).
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 */
	public Map<String, String> doGenerate(Module module, String templateName, EObject model,
			boolean recursive, List<? extends Object> arguments, File generationRoot, Monitor monitor) {

		// Start
		boolean shouldNotify = false;
		if (!this.generationIsOccurring) {
			this.generationIsOccurring = true;
			shouldNotify = true;
			this.prepareGeneration(monitor, generationRoot);
		}

		if (model == null || arguments == null
				|| (!(strategy instanceof PreviewStrategy) && generationRoot == null)) {
			throw new NullPointerException(TEMPLATE_CALL_NPE);
		}
		final List<Template> templates = findTemplates(module, templateName, arguments.size() + 1);
		// #findTemplate never returns private templates.

		List<Template> mainTemplates = new ArrayList<Template>();
		for (Template template : templates) {
			if (template.isMain()) {
				mainTemplates.add(template);
			}
		}

		final Map<String, String> previewResult = new HashMap<String, String>();

		for (Template template : mainTemplates) {
			// Calls the template with each potential arguments
			final EClassifier argumentType = template.getParameter().get(0).getType();
			if (argumentType.eIsProxy()) {
				throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
						"AcceleoService.TypeIsProxy", templateName)); //$NON-NLS-1$
			}

			// The input model itself is a potential argument
			if (argumentType.isInstance(model)) {
				final List<Object> actualArguments = new ArrayList<Object>();
				actualArguments.add(model);
				actualArguments.addAll(arguments);
				previewResult.putAll(doGenerateTemplate(template, actualArguments, generationRoot, monitor));
				generationHasOccurred = true;
			}
			if (recursive) {
				final TreeIterator<EObject> targetElements = model.eAllContents();
				while (targetElements.hasNext()) {
					final EObject potentialTarget = targetElements.next();
					if (argumentType.isInstance(potentialTarget)) {
						final List<Object> actualArguments = new ArrayList<Object>();
						actualArguments.add(potentialTarget);
						actualArguments.addAll(arguments);
						previewResult.putAll(doGenerateTemplate(template, actualArguments, generationRoot,
								monitor));
						generationHasOccurred = true;
					}
				}
			}
			// There is a possibility that "model" is but one of the resource's roots.
			if (model.eResource() != null) {
				List<EObject> roots = new ArrayList<EObject>(model.eResource().getContents());
				roots.remove(model);
				for (EObject root : roots) {
					if (argumentType.isInstance(root)) {
						final List<Object> actualArguments = new ArrayList<Object>();
						actualArguments.add(root);
						actualArguments.addAll(arguments);
						previewResult.putAll(doGenerateTemplate(template, actualArguments, generationRoot,
								monitor));
						generationHasOccurred = true;
					}
				}
			}
		}

		// End
		if (shouldNotify) {
			shouldNotify = false;
			this.generationIsOccurring = false;
			this.finalizeGeneration();
		}

		return previewResult;
	}

	/**
	 * Launches the generation of an Acceleo template given its name and containing module.
	 * <p>
	 * This is a convenience method that can be used with multiple argument templates. The input model will be
	 * iterated over for objects matching the template's <b>first</b> parameter type. The template will then
	 * be called with these objects as first arguments, and the given list of <code>arguments</code> for the
	 * remaining template parameters.
	 * </p>
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the template that is to be generated.
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param arguments
	 *            Arguments of the template call, excluding the very first one (<code>model</code> object).
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param blockTraceability
	 *            Indicates if we should deactivate the traceability.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.1
	 */
	public Map<String, String> doGenerate(Module module, String templateName, EObject model,
			List<? extends Object> arguments, File generationRoot, boolean blockTraceability, Monitor monitor) {
		this.deactivateTraceability = blockTraceability;
		return doGenerate(module, templateName, model, arguments, generationRoot, monitor);
	}

	/**
	 * Launches the generation of a single-argument Acceleo template for all matching EObjects in the given
	 * model.
	 * <p>
	 * This is a convenience method that can only be used with single argument templates. Any attempt at
	 * calling other templates through this method will throw {@link AcceleoEvaluationException}s. The input
	 * model will be iterated over for objects matching the template's parameter type.
	 * </p>
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param template
	 *            The template that is to be generated
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	public Map<String, String> doGenerate(Template template, EObject model, File generationRoot,
			Monitor monitor) {
		return doGenerate(template, model, true, generationRoot, monitor);
	}

	/**
	 * Launches the generation of a single-argument Acceleo template for all matching EObjects in the given
	 * model.
	 * <p>
	 * This is a convenience method that can only be used with single argument templates. Any attempt at
	 * calling other templates through this method will throw {@link AcceleoEvaluationException}s.
	 * </p>
	 * 
	 * @param template
	 *            The template that is to be generated
	 * @param model
	 *            Input model for this Acceleo template.
	 * @param recursive
	 *            if <code>true</code> the input model will be iterated over for objects matching the
	 *            template's <b>first</b> parameter type
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	public Map<String, String> doGenerate(Template template, EObject model, boolean recursive,
			File generationRoot, Monitor monitor) {
		boolean shouldNotify = false;
		if (!generationIsOccurring) {
			// Start
			this.generationIsOccurring = true;
			shouldNotify = true;
			this.prepareGeneration(monitor, generationRoot);
		}

		if (template == null || model == null
				|| (!(strategy instanceof PreviewStrategy) && generationRoot == null)) {
			throw new NullPointerException(TEMPLATE_CALL_NPE);
		}
		if (template.getVisibility() != VisibilityKind.PUBLIC) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalTemplateInvocation")); //$NON-NLS-1$
		}
		if (template.getParameter().size() != 1) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.VoidArguments")); //$NON-NLS-1$
		}

		final Map<String, String> previewResult = new HashMap<String, String>();

		// Calls the template with each potential arguments
		final EClassifier argumentType = template.getParameter().get(0).getType();
		final List<Object> arguments = new ArrayList<Object>();

		// The input model itself is a potential argument
		if (argumentType.isInstance(model)) {
			arguments.add(model);
			previewResult.putAll(doGenerateTemplate(template, arguments, generationRoot, monitor));
			generationHasOccurred = true;
		}
		if (recursive) {
			final TreeIterator<EObject> targetElements = model.eAllContents();
			while (targetElements.hasNext()) {
				final EObject potentialTarget = targetElements.next();
				if (argumentType.isInstance(potentialTarget)) {
					arguments.clear();
					arguments.add(potentialTarget);
					previewResult.putAll(doGenerateTemplate(template, arguments, generationRoot, monitor));
					generationHasOccurred = true;
				}
			}
		}

		if (shouldNotify) {
			// End
			this.generationIsOccurring = false;
			shouldNotify = false;
			this.finalizeGeneration();
		}

		this.clearCaches();
		return previewResult;
	}

	/**
	 * Launches the generation of an Acceleo template with the given arguments.
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for each class of the input model.
	 * </p>
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the template that is to be generated.
	 * @param arguments
	 *            Arguments that must be passed on to the template for evaluation.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 */
	public Map<String, String> doGenerateTemplate(Module module, String templateName,
			List<? extends Object> arguments, File generationRoot, Monitor monitor) {
		Map<String, String> result = new HashMap<String, String>();
		List<Template> templates = findTemplates(module, templateName, arguments);
		// filter only main templates

		// Start
		this.generationIsOccurring = true;
		this.prepareGeneration(monitor, generationRoot);

		for (Template template : templates) {
			result.putAll(doGenerateTemplate(template, arguments, generationRoot, monitor));
		}

		// End
		this.generationIsOccurring = false;
		this.finalizeGeneration();

		return result;
	}

	/**
	 * Launches the generation of an Acceleo template with the given arguments.
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line <tt>&quot;processing class &lt;className&gt;&quot;</tt> for each class of the input
	 * model.
	 * </p>
	 * 
	 * @param template
	 *            The template that is to be generated
	 * @param arguments
	 *            Arguments that must be passed on to the template for evaluation.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param blockTraceability
	 *            This will indicate if we should deactivate the traceability (even if listener register
	 *            thanks to the extension point requires it).
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.1
	 */
	public Map<String, String> doGenerateTemplate(Template template, List<? extends Object> arguments,
			File generationRoot, boolean blockTraceability, Monitor monitor) {
		this.deactivateTraceability = blockTraceability;
		return doGenerateTemplate(template, arguments, generationRoot, monitor);
	}

	/**
	 * Launches the generation of an Acceleo template with the given arguments.
	 * <p>
	 * <tt>generationRoot</tt> will be used as the root of all generated files. For example, a template such
	 * as
	 * 
	 * <pre>
	 * [template generate(c:EClass)]
	 * [file(log.log, true)]processing class [c.name/][/file]
	 * [/template]
	 * </pre>
	 * 
	 * evaluated with <tt>file:\\c:\</tt> as <tt>generationRoot</tt> would create the file <tt>c:\log.log</tt>
	 * and generate a line <tt>&quot;processing class &lt;className&gt;&quot;</tt> for each class of the input
	 * model.
	 * </p>
	 * 
	 * @param template
	 *            The template that is to be generated
	 * @param arguments
	 *            Arguments that must be passed on to the template for evaluation.
	 * @param generationRoot
	 *            This will be used as the root for the generated files. This can be <code>null</code>, in
	 *            which case the user home directory will be used as root.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	public Map<String, String> doGenerateTemplate(Template template, List<? extends Object> arguments,
			File generationRoot, Monitor monitor) {
		try {
			return generationEngine.evaluate(template, arguments, generationRoot, strategy, monitor);
		} finally {
			// do nothing, just catch everything
		}
	}

	/**
	 * Prepare the generation and send an event indicating the start of the generation to all the listeners.
	 * 
	 * @param monitor
	 *            The basic monitor
	 * @param generationRoot
	 *            The generation root.
	 * @since 3.1
	 */
	public void doPrepareGeneration(Monitor monitor, File generationRoot) {
		this.generationIsOccurring = true;
		this.prepareGeneration(monitor, generationRoot);
	}

	/**
	 * Prepare the generation and send an event indicating the start of the generation to all the listeners.
	 * 
	 * @param monitor
	 *            The basic monitor
	 * @param generationRoot
	 *            The generation root.
	 */
	private void prepareGeneration(Monitor monitor, File generationRoot) {
		generationHasOccurred = false;
		for (IAcceleoTextGenerationListener listener : STATIC_LISTENERS) {
			generationEngine.addListener(listener);
		}

		descriptorsUsed = new ArrayList<AcceleoListenerDescriptor>();

		List<AcceleoListenerDescriptor> descriptors = AcceleoTraceabilityRegistryListenerUils
				.getListenerDescriptors();
		for (AcceleoListenerDescriptor acceleoListenerDescriptor : descriptors) {
			if (acceleoListenerDescriptor.getNature() == null) {
				// If we are in stand alone, only use the descriptors without nature
				descriptorsUsed.add(acceleoListenerDescriptor);
			} else if (EMFPlugin.IS_ECLIPSE_RUNNING && generationRoot != null) {
				// Check the nature of the output project
				IPath location = new Path(generationRoot.getAbsolutePath());
				IContainer[] containers = ResourcesPlugin.getWorkspace().getRoot()
						.findContainersForLocationURI(location.toFile().toURI());
				for (IContainer container : containers) {
					IProject project = container.getProject();
					try {
						if (project != null && project.isAccessible()
								&& project.hasNature(acceleoListenerDescriptor.getNature())) {
							descriptorsUsed.add(acceleoListenerDescriptor);
						}
					} catch (CoreException e) {
						AcceleoEnginePlugin.log(e, false);
					}
				}
			}
		}

		forceTraceability = false;
		if (!deactivateTraceability) {
			for (AcceleoListenerDescriptor acceleoListenerDescriptor : descriptorsUsed) {
				IAcceleoTextGenerationListener listener = acceleoListenerDescriptor.getTraceabilityListener();
				if (listener instanceof AbstractAcceleoTextGenerationListener) {
					AbstractAcceleoTextGenerationListener textGenerationListener = (AbstractAcceleoTextGenerationListener)listener;
					textGenerationListener.setGenerationID(generationID);
				}

				// If one of the listeners wants to force the traceability it will have it.
				if (EMFPlugin.IS_ECLIPSE_RUNNING && !AcceleoPreferences.isTraceabilityEnabled()
						&& acceleoListenerDescriptor.isForceTraceability()) {
					AcceleoPreferences.switchTraceability(true);
					forceTraceability = true;
				}
			}

			// We create the engine once again if someone has forced the traceability
			if (forceTraceability) {
				createEngine();

				// We restore all the content of the previous engine
				for (IAcceleoTextGenerationListener listener : this.addedListeners) {
					generationEngine.addListener(listener);
				}
				for (Map<String, String> properties : this.addedProperties) {
					generationEngine.addProperties(properties);
				}
				for (String propertiesFiles : this.addedPropertiesfiles) {
					generationEngine.addProperties(propertiesFiles);
				}
			}
		}

		// We add the listeners contributed thanks to the extension point.
		for (AcceleoListenerDescriptor acceleoListenerDescriptor : descriptorsUsed) {
			generationEngine.addListener(acceleoListenerDescriptor.getTraceabilityListener());
		}

		// notify every listeners that the generation is starting.
		List<IAcceleoTextGenerationListener> listeners = new ArrayList<IAcceleoTextGenerationListener>();
		listeners.addAll(STATIC_LISTENERS);
		listeners.addAll(addedListeners);
		for (AcceleoListenerDescriptor acceleoListenerDescriptor : descriptorsUsed) {
			listeners.add(acceleoListenerDescriptor.getTraceabilityListener());
		}

		for (IAcceleoTextGenerationListener listener : listeners) {
			if (listener instanceof AbstractAcceleoTextGenerationListener) {
				AbstractAcceleoTextGenerationListener abstractListener = (AbstractAcceleoTextGenerationListener)listener;
				abstractListener.generationStart(monitor, generationRoot);
			}
		}
	}

	/**
	 * Indicates that the generation has been completed and send an event indicating the end of the
	 * generation. It also removes all the listeners just when the generation is over.
	 * 
	 * @since 3.1
	 */
	public void finalizeGeneration() {
		// notify every listeners that the generation is over.
		List<IAcceleoTextGenerationListener> listeners = new ArrayList<IAcceleoTextGenerationListener>();
		listeners.addAll(STATIC_LISTENERS);
		listeners.addAll(addedListeners);
		for (AcceleoListenerDescriptor acceleoListenerDescriptor : descriptorsUsed) {
			listeners.add(acceleoListenerDescriptor.getTraceabilityListener());
		}

		for (IAcceleoTextGenerationListener listener : listeners) {
			if (listener instanceof AbstractAcceleoTextGenerationListener) {
				AbstractAcceleoTextGenerationListener abstractListener = (AbstractAcceleoTextGenerationListener)listener;
				abstractListener.generationCompleted();
			}
		}

		for (IAcceleoTextGenerationListener listener : STATIC_LISTENERS) {
			generationEngine.removeListener(listener);
		}
		for (AcceleoListenerDescriptor acceleoListenerDescriptor : descriptorsUsed) {
			generationEngine.removeListener(acceleoListenerDescriptor.getTraceabilityListener());
		}
		for (IAcceleoTextGenerationListener listener : addedListeners) {
			generationEngine.removeListener(listener);
		}

		if (EMFPlugin.IS_ECLIPSE_RUNNING && forceTraceability) {
			AcceleoPreferences.switchTraceability(false);
		}

		strategy.dispose();
	}

	/**
	 * Clear the traceability caches.
	 * 
	 * @since 3.1
	 */
	public void clearCaches() {
		// Clear the cache
		addedListeners.clear();
		addedProperties.clear();
		addedPropertiesfiles.clear();
	}

	/**
	 * Removes a listener from the notification loops.
	 * 
	 * @param listener
	 *            The listener that is to be removed from this engine's notification loops.
	 * @since 0.8
	 */
	public void removeListener(IAcceleoTextGenerationListener listener) {
		generationEngine.removeListener(listener);
	}

	/**
	 * Instantiates the engine that will be used by this service for the generation.
	 */
	private void createEngine() {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			generationEngine = new DefaultEngineSelector().selectEngine(AcceleoEngineRegistry
					.getRegisteredCreators());
		}
		if (generationEngine == null) {
			generationEngine = new AcceleoEngine();
		}
	}

	/**
	 * This will iterate through the module's elements to find public templates named <tt>templateName</tt>
	 * with the given count of arguments.
	 * 
	 * @param module
	 *            The module in which we seek a template <tt>templateName</tt>.
	 * @param templateName
	 *            Name of the sought template.
	 * @param argumentCount
	 *            Number of arguments of the sought template.
	 * @return The templates of this name contained by <tt>module</tt>. Will fail in
	 *         {@link AcceleoEvaluationException} if none can be found.
	 */
	private List<Template> findTemplates(Module module, String templateName, int argumentCount) {
		List<Template> templates = new ArrayList<Template>();
		for (ModuleElement element : module.getOwnedModuleElement()) {
			if (element instanceof Template) {
				Template template = (Template)element;
				if (template.getVisibility() == VisibilityKind.PUBLIC
						&& templateName.equals(template.getName())
						&& template.getParameter().size() == argumentCount) {
					templates.add(template);
				}
			}
		}
		if (templates.isEmpty()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(UNDEFINED_TEMPLATE,
					templateName, module.getName()));
		}
		return templates;
	}

	/**
	 * This will iterate through the module's elements to find public templates which argument types
	 * correspond to the given list of argument values.
	 * 
	 * @param module
	 *            The module in which we seek the template.
	 * @param templateName
	 *            Name of the sought template.
	 * @param arguments
	 *            Values of the argument we wish to pass on to the template.
	 * @return The templates of this name with matching arguments contained by <tt>module</tt>. Will fail in
	 *         {@link AcceleoEvaluationException} if none can be found.
	 */
	private List<Template> findTemplates(Module module, String templateName, List<? extends Object> arguments) {
		List<Template> templates = new ArrayList<Template>();
		for (ModuleElement element : module.getOwnedModuleElement()) {
			if (element instanceof Template) {
				Template template = (Template)element;
				if (template.getVisibility() == VisibilityKind.PUBLIC
						&& templateName.equals(template.getName())
						&& template.getParameter().size() == arguments.size()) {
					boolean parameterMatch = true;
					for (int i = 0; i < template.getParameter().size(); i++) {
						if (!template.getParameter().get(i).getType().isInstance(arguments.get(i))) {
							parameterMatch = false;
						}
					}
					if (parameterMatch) {
						templates.add(template);
					}
				}
			}
		}
		if (templates.isEmpty()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(UNDEFINED_TEMPLATE,
					templateName, module.getName()));
		}
		return templates;
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
	 * Indicates if the generator has executed a template.
	 * 
	 * @return <code>true</code> if the generator has been used, <code>false</code> otherwise.
	 * @since 3.1
	 */
	public boolean hasGenerationOccurred() {
		return this.generationHasOccurred;
	}
}
