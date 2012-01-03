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
package org.eclipse.acceleo.engine.generation;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.Monitor;

/**
 * Base interface for all implementation of an Acceleo evaluation engine.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface IAcceleoEngine {
	/**
	 * Registers a listener to be notified for any text generation that will take place in this engine
	 * evaluation process.
	 * 
	 * @param listener
	 *            The new listener that is to be registered for notification.
	 * @since 0.8
	 */
	void addListener(IAcceleoTextGenerationListener listener);

	/**
	 * This can be used to add custom properties to the engine. These will be available through the
	 * getProperty() services, <em>note</em> however that there can only be a single pair with a given key,
	 * and these properties will <em>always</em> take precedence over properties file-defined pairs.
	 * 
	 * @param customProperties
	 *            Pairs that are to be added to the generation context.
	 * @since 3.0
	 */
	void addProperties(Map<String, String> customProperties);

	/**
	 * Registers properties files so that their key/value pairs can be accessed through the getProperty()
	 * services at generation time.
	 * <p>
	 * When we search for a given pair, the properties holders will be iterated through in order of addition :
	 * the very first pair of the sought key will be returned. In other words, the <em>first</em> added
	 * properties holder takes precedence over all subsequent ones.
	 * </p>
	 * 
	 * @param resourcePath
	 *            Qualified path to the properties file that is to be added to the generation context.
	 * @throws MissingResourceException
	 *             This will be thrown if we cannot locate the properties file in the current classpath.
	 * @since 3.0
	 */
	void addProperties(String resourcePath) throws MissingResourceException;

	/**
	 * Evaluates the given Acceleo Template with the given arguments.
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
	 * and generate a line &quot;processing class &lt;className&gt;&quot; for the argument.
	 * </p>
	 * 
	 * @param template
	 *            The Acceleo template which is to be evaluated.
	 * @param arguments
	 *            List of the template's arguments.
	 * @param generationRoot
	 *            This will be used as the root for the generated files.
	 * @param strategy
	 *            The generation strategy that's to be used by this engine.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file paths to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 * @since 3.0
	 */
	Map<String, String> evaluate(Template template, List<? extends Object> arguments, File generationRoot,
			IAcceleoGenerationStrategy strategy, Monitor monitor);

	/**
	 * Removes a listener from the notification loops.
	 * 
	 * @param listener
	 *            The listener that is to be removed from this engine's notification loops.
	 * @since 0.8
	 */
	void removeListener(IAcceleoTextGenerationListener listener);
}
