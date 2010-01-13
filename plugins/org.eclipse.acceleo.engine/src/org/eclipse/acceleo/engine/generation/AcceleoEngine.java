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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.Variable;

/**
 * This class can be used to launch the generation of an Acceleo template.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.9
 */
public class AcceleoEngine implements IAcceleoEngine {
	/** Externalized name of the "self" context variable to avoid too many distinct uses. */
	private static final String SELF_CONTEXT_VARIABLE_NAME = "context0"; //$NON-NLS-1$

	/** Externalized name of the "self" OCL variable to avoid too many distinct uses. */
	private static final String SELF_VARIABLE_NAME = "self"; //$NON-NLS-1$

	/**
	 * This will contain the custom properties for this engine, properties that will always take precedence
	 * over those contained within {@link #loadedProperties} no matter what.
	 */
	protected final Properties customProperties = new Properties();

	/**
	 * This will hold the list of all listeners registered for notification on text generation from this
	 * engine.
	 */
	protected final List<IAcceleoTextGenerationListener> listeners = new ArrayList<IAcceleoTextGenerationListener>();

	/** This will hold the list of properties accessible from the generation context for this engine instance. */
	protected final Map<File, Properties> loadedProperties = new LinkedHashMap<File, Properties>();

	/** Holds a reference to the ocl instance. */
	private OCL ocl;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#addListener(org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener)
	 * @since 0.8
	 */
	public void addListener(IAcceleoTextGenerationListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(java.io.File)
	 * @since 0.9
	 */
	public void addProperties(File propertiesHolder) throws IOException {
		final Properties property = new Properties();
		final InputStream stream = new FileInputStream(propertiesHolder);
		try {
			property.load(stream);
		} finally {
			stream.close();
		}
		property.put(IAcceleoConstants.PROPERTY_KEY_FILE_NAME, propertiesHolder.getName());
		loadedProperties.put(propertiesHolder, property);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(java.util.Map)
	 * @since 0.9
	 */
	public void addProperties(Map<String, String> customProps) {
		customProperties.putAll(customProps);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#evaluate(org.eclipse.acceleo.model.mtl.Template,
	 *      java.util.List, java.io.File,
	 *      org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy,
	 *      org.eclipse.emf.common.util.Monitor)
	 */
	public Map<String, String> evaluate(Template template, List<? extends Object> arguments,
			File generationRoot, IAcceleoGenerationStrategy strategy, Monitor monitor) {
		if (template == null || arguments == null) {
			throw new NullPointerException(AcceleoEngineMessages.getString("AcceleoEngine.NullArguments")); //$NON-NLS-1$
		}
		if (template.getVisibility() != VisibilityKind.PUBLIC) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalTemplateInvocation")); //$NON-NLS-1$
		}
		if (template.getParameter().size() != arguments.size()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalArguments")); //$NON-NLS-1$
		}

		// We need to create an OCL instance for each generation since the environment factory is contextual
		AbstractAcceleoEnvironmentFactory factory = createEnvironmentFactory(generationRoot, (Module)template
				.eContainer(), strategy, monitor);
		ocl = OCL.newInstance(factory);

		try {
			doEvaluate(template, arguments);
		} catch (AcceleoEvaluationCancelledException e) {
			// All necessary disposal should have been made
		}

		hookGenerationEnd(factory);

		Map<String, String> result = Collections.<String, String> emptyMap();
		if (strategy.willReturnPreview()) {
			result = factory.getEvaluationPreview();
		}
		factory.dispose();

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#removeCustomProperties(java.util.Set)
	 * @since 0.9
	 */
	public void removeCustomProperties(Set<String> customPropertyKeys) {
		for (String key : customPropertyKeys) {
			customProperties.remove(key);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#removeListener(org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener)
	 * @since 0.8
	 */
	public void removeListener(IAcceleoTextGenerationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#removeProperties(java.io.File)
	 * @since 0.9
	 */
	public void removeProperties(File propertiesHolder) {
		loadedProperties.remove(propertiesHolder);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#reset()
	 * @since 0.9
	 */
	public void reset() {
		listeners.clear();
		loadedProperties.clear();
		customProperties.clear();
	}

	/**
	 * This will be used to create a clean environment for each generation.
	 * 
	 * @param generationRoot
	 *            Root of the generation for which this environment will be used.
	 * @param rootModule
	 *            Root module of said generation.
	 * @param strategy
	 *            The generation strategy will control the creation of files for this generation.
	 * @param monitor
	 *            Process monitor which will be used to display information to the user about the current
	 *            generation.
	 * @return The created environment.
	 */
	protected AbstractAcceleoEnvironmentFactory createEnvironmentFactory(File generationRoot,
			Module rootModule, IAcceleoGenerationStrategy strategy, Monitor monitor) {
		final List<IAcceleoTextGenerationListener> listenersCopy = new ArrayList<IAcceleoTextGenerationListener>(
				listeners);
		final List<Properties> propertiesCopy = new ArrayList<Properties>(loadedProperties.values());
		propertiesCopy.add(0, customProperties);
		return new AcceleoEnvironmentFactory(generationRoot, rootModule, listenersCopy, propertiesCopy,
				strategy, monitor);
	}

	/**
	 * This will be called internally by the engine to offer a chance of disposing anything that could have
	 * been loaded during the generation.
	 * 
	 * @param factory
	 *            {@link AbstractAcceleoEnvironmentFactory} that's been used for this generation.
	 * @nooverride This method is not intended to be re-implemented or extended by clients.
	 */
	protected void hookGenerationEnd(AbstractAcceleoEnvironmentFactory factory) {
		factory.hookGenerationEnd();
		fireGenerationEnd();
	}

	/**
	 * Notifies all registered listeners that the generation just ended.
	 */
	protected void fireGenerationEnd() {
		for (IAcceleoTextGenerationListener listener : listeners) {
			listener.generationEnd(new AcceleoTextGenerationEvent(null, null, null));
		}
	}

	/**
	 * This does the actual work of template evaluation. It will be called from the public API methods exposed
	 * by the engine.
	 * 
	 * @param template
	 *            The template which is to be evaluated.
	 * @param arguments
	 *            These will be passed as the template arguments.
	 */
	private void doEvaluate(Template template, List<? extends Object> arguments) {
		// Guard Evaluation
		boolean guardValue = true;
		if (template.getGuard() != null) {
			final OCL.Query guard = ocl.createQuery(template.getGuard());
			// Sets all needed variables for the guard evaluation
			for (int i = 0; i < template.getParameter().size(); i++) {
				Variable param = template.getParameter().get(i);
				Object value = arguments.get(i);
				if (param.getType().isInstance(value)) {
					guard.getEvaluationEnvironment().add(param.getName(), value);
				} else {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							"AcceleoEngine.ArgumentMismatch", template.getName())); //$NON-NLS-1$
				}
				// [255379] also sets "self" variable to match the very first parameter
				if (i == 0) {
					guard.getEvaluationEnvironment().add(SELF_VARIABLE_NAME, value);
					guard.getEvaluationEnvironment().add(SELF_CONTEXT_VARIABLE_NAME, value);
				}
			}
			guardValue = ((Boolean)guard.evaluate()).booleanValue();
			// reset variables
			for (Variable var : template.getParameter()) {
				guard.getEvaluationEnvironment().remove(var.getName());
			}
			if (template.getParameter().size() > 0) {
				guard.getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
				guard.getEvaluationEnvironment().remove(SELF_CONTEXT_VARIABLE_NAME);
			}
		}

		// If there were no guard or its condition is verified, evaluate the template now.
		if (guardValue) {
			final OCL.Query query = ocl.createQuery(template);
			// Sets all needed variables for the template evaluation
			for (int i = 0; i < template.getParameter().size(); i++) {
				Variable param = template.getParameter().get(i);
				Object value = arguments.get(i);
				if (param.getType().isInstance(value)) {
					query.getEvaluationEnvironment().add(param.getName(), value);
				} else {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							"AcceleoEngine.ArgumentMismatch", template.getName())); //$NON-NLS-1$
				}
				// [255379] also sets "self" variable to match the very first parameter
				if (i == 0) {
					query.getEvaluationEnvironment().add(SELF_VARIABLE_NAME, value);
					query.getEvaluationEnvironment().add(SELF_CONTEXT_VARIABLE_NAME, value);
				}
			}
			try {
				query.evaluate();
			} finally {
				// reset variables
				for (Variable var : template.getParameter()) {
					query.getEvaluationEnvironment().remove(var.getName());
				}
				if (template.getParameter().size() > 0) {
					query.getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
					query.getEvaluationEnvironment().remove(SELF_CONTEXT_VARIABLE_NAME);
				}
			}
		}
	}
}
