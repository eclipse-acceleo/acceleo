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
package org.eclipse.acceleo.engine.generation;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.engine.event.AcceleoTextGenerationListener;
import org.eclipse.acceleo.model.mtl.Template;

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
	 */
	void addListener(AcceleoTextGenerationListener listener);

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
	 * @param preview
	 *            If <code>true</code>, no files will be generated and a Map mapping file pathes to their
	 *            generated content will be returned.
	 * @return if <code>preview</code> is set to <code>true</code>, no files will be generated. Instead, a Map
	 *         mapping all file pathes to the potential content will be returned. This returned map will be
	 *         empty otherwise.
	 */
	Map<String, StringWriter> evaluate(Template template, List<? extends Object> arguments,
			File generationRoot, boolean preview);

	/**
	 * Removes a listener from the notification loops.
	 * 
	 * @param listener
	 *            The listener that is to be removed from this engine's notification loops.
	 */
	void removeListener(AcceleoTextGenerationListener listener);
}
