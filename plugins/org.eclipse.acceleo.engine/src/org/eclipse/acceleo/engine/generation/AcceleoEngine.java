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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.ecore.AnyType;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.Variable;

/**
 * This class can be used to launch the generation of an Acceleo template.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @noextend This class is not intended to be subclassed by clients.
 * @since 3.0
 */
public class AcceleoEngine implements IAcceleoEngine2 {
	/** Externalized name of the "self" context variable to avoid too many distinct uses. */
	private static final String SELF_CONTEXT_VARIABLE_NAME = "context$0"; //$NON-NLS-1$

	/** Externalized name of the "self" OCL variable to avoid too many distinct uses. */
	private static final String SELF_VARIABLE_NAME = "self"; //$NON-NLS-1$

	/** The key of the externalized message for argument mismatch. */
	private static final String ARGUMENT_MISMATCH_KEY = "AcceleoEngine.ArgumentMismatch"; //$NON-NLS-1$

	/**
	 * This will hold the list of all listeners registered for notification on text generation from this
	 * engine.
	 */
	protected final List<IAcceleoTextGenerationListener> listeners = new ArrayList<IAcceleoTextGenerationListener>();

	/** This will hold a reference to the properties manager for a given generation. */
	protected AcceleoPropertiesLookup propertiesLookup = new AcceleoPropertiesLookup();

	/**
	 * This will be set to true if one of the registered generation listener is interested in generation end
	 * notifications.
	 * 
	 * @since 3.0
	 */
	protected boolean notifyOnGenerationEnd;

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
		if (listener.listensToGenerationEnd()) {
			notifyOnGenerationEnd = true;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(java.io.File)
	 * @since 3.0
	 */
	public void addProperties(String propertiesHolder) throws MissingResourceException {
		propertiesLookup.addProperties(propertiesHolder);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(java.util.Map)
	 * @since 3.0
	 */
	public void addProperties(Map<String, String> customProps) {
		propertiesLookup.addProperties(customProps);
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
		propertiesLookup.addProperties(resourceBundle, fileName);
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
		checkEvaluation(template, arguments);

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
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine2#evaluate(org.eclipse.acceleo.model.mtl.Template,
	 *      java.util.List, org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy,
	 *      org.eclipse.emf.common.util.Monitor)
	 */
	public Object evaluate(Template template, List<? extends Object> arguments,
			IAcceleoGenerationStrategy strategy, Monitor monitor) {
		checkEvaluation(template, arguments);

		// We need to create an OCL instance for each generation since the environment factory is contextual
		AbstractAcceleoEnvironmentFactory factory = createEnvironmentFactory(null, (Module)template
				.eContainer(), strategy, monitor);
		ocl = OCL.newInstance(factory);

		try {
			return doEvaluate(template, arguments);
		} catch (AcceleoEvaluationCancelledException e) {
			// All necessary disposal should have been made
		} finally {
			hookGenerationEnd(factory);
			factory.dispose();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine2#evaluate(org.eclipse.acceleo.model.mtl.Query,
	 *      java.util.List, org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy,
	 *      org.eclipse.emf.common.util.Monitor)
	 */
	public Object evaluate(Query query, List<? extends Object> arguments,
			IAcceleoGenerationStrategy strategy, Monitor monitor) {
		checkEvaluation(query, arguments);

		// We need to create an OCL instance for each generation since the environment factory is contextual
		AbstractAcceleoEnvironmentFactory factory = createEnvironmentFactory(null,
				(Module)query.eContainer(), strategy, monitor);
		ocl = OCL.newInstance(factory);

		try {
			return doEvaluate(query, arguments);
		} catch (AcceleoEvaluationCancelledException e) {
			// All necessary disposal should have been made
		} finally {
			hookGenerationEnd(factory);
			factory.dispose();
		}

		return null;
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
	 * Checks that the given module element can be evaluated with the given arguments.
	 * 
	 * @param moduleElement
	 *            The module element we need to evaluate.
	 * @param arguments
	 *            The arguments with which to evaluate <code>moduleElement</code>.
	 * @since 3.2
	 */
	protected void checkEvaluation(ModuleElement moduleElement, List<? extends Object> arguments) {
		if (moduleElement == null || arguments == null) {
			throw new NullPointerException(AcceleoEngineMessages.getString("AcceleoEngine.NullArguments")); //$NON-NLS-1$
		}
		if (moduleElement.getVisibility() != VisibilityKind.PUBLIC) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalInvocation")); //$NON-NLS-1$
		}
		boolean illegalParams = moduleElement instanceof Template
				&& ((Template)moduleElement).getParameter().size() != arguments.size();
		illegalParams = illegalParams || moduleElement instanceof Query
				&& ((Query)moduleElement).getParameter().size() != arguments.size();
		if (illegalParams) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalArguments")); //$NON-NLS-1$
		}
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
		return new AcceleoEnvironmentFactory(generationRoot, rootModule, listenersCopy, propertiesLookup,
				strategy, monitor);
	}

	/**
	 * This will be called internally by the engine to offer a chance of disposing anything that could have
	 * been loaded during the generation.
	 * 
	 * @param factory
	 *            {@link AbstractAcceleoEnvironmentFactory} that's been used for this generation.
	 */
	protected void hookGenerationEnd(AbstractAcceleoEnvironmentFactory factory) {
		factory.hookGenerationEnd();
		fireGenerationEnd();
	}

	/**
	 * Notifies all registered listeners that the generation just ended.
	 */
	protected void fireGenerationEnd() {
		if (!notifyOnGenerationEnd) {
			return;
		}
		for (IAcceleoTextGenerationListener listener : listeners) {
			if (listener.listensToGenerationEnd()) {
				listener.generationEnd(new AcceleoTextGenerationEvent());
			}
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
	 * @return The result of the evaluation of the template with the given arguments.
	 */
	private Object doEvaluate(Template template, List<? extends Object> arguments) {
		// Guard Evaluation
		boolean guardValue = true;
		if (template.getGuard() != null) {
			final OCL.Query guard = ocl.createQuery(template.getGuard());
			// Sets all needed variables for the guard evaluation
			for (int i = 0; i < template.getParameter().size(); i++) {
				Variable param = template.getParameter().get(i);
				Object value = arguments.get(i);
				if (isInstance(param.getType(), value)) {
					guard.getEvaluationEnvironment().add(param.getName(), value);
				} else {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							ARGUMENT_MISMATCH_KEY, template.getName()));
				}
				// [255379] also sets "self" variable to match the very first parameter
				if (i == 0) {
					guard.getEvaluationEnvironment().add(SELF_VARIABLE_NAME, value);
					guard.getEvaluationEnvironment().add(SELF_CONTEXT_VARIABLE_NAME, value);
				}
			}
			// FIXME [362056]
			Object result = guard.evaluate();
			if (result instanceof Boolean) {
				guardValue = ((Boolean)result).booleanValue();
			}
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
				if (param.getType() instanceof AnyType || isInstance(param.getType(), value)) {
					query.getEvaluationEnvironment().add(param.getName(), value);
				} else {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							ARGUMENT_MISMATCH_KEY, template.getName()));
				}
				// [255379] also sets "self" variable to match the very first parameter
				if (i == 0) {
					query.getEvaluationEnvironment().add(SELF_VARIABLE_NAME, value);
					query.getEvaluationEnvironment().add(SELF_CONTEXT_VARIABLE_NAME, value);
				}
			}
			try {
				return query.evaluate();
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

		return null;
	}

	/**
	 * This does the actual work of query evaluation. It will be called from the public API methods exposed by
	 * the engine.
	 * 
	 * @param acceleoQuery
	 *            The query which is to be evaluated.
	 * @param arguments
	 *            These will be passed as the query arguments.
	 * @return The result of the evaluation of the query with the given arguments.
	 */
	private Object doEvaluate(Query acceleoQuery, List<? extends Object> arguments) {
		final OCL.Query query = ocl.createQuery(acceleoQuery.getExpression());

		// Sets all needed variables for the query evaluation
		for (int i = 0; i < acceleoQuery.getParameter().size(); i++) {
			Variable param = acceleoQuery.getParameter().get(i);
			Object value = arguments.get(i);
			if (value == null || isInstance(param.getType(), value)) {
				query.getEvaluationEnvironment().add(param.getName(), value);
			} else {
				throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(ARGUMENT_MISMATCH_KEY,
						acceleoQuery.getName()));
			}
			// [255379] also sets "self" variable to match the very first parameter
			if (i == 0) {
				query.getEvaluationEnvironment().add(SELF_VARIABLE_NAME, value);
				query.getEvaluationEnvironment().add(SELF_CONTEXT_VARIABLE_NAME, value);
			}
		}
		try {
			return query.evaluate();
		} finally {
			// reset variables
			for (Variable var : acceleoQuery.getParameter()) {
				query.getEvaluationEnvironment().remove(var.getName());
			}
			if (acceleoQuery.getParameter().size() > 0) {
				query.getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
				query.getEvaluationEnvironment().remove(SELF_CONTEXT_VARIABLE_NAME);
			}
		}
	}

	/**
	 * In the context of Acceleo, we assume that "classifier" is more often a "normal" classifier than a
	 * primitive. We'll delegate to classifier.isInstance if not.
	 * 
	 * @param classifier
	 *            The classifier we need <code>value</code> to be an instance of.
	 * @param value
	 *            The value which class is to be checked.
	 * @return <code>true</code> if value is an instance of the given classifier.
	 */
	private boolean isInstance(EClassifier classifier, Object value) {
		boolean isInstance = false;
		if (classifier.getInstanceClass() != null) {
			isInstance = classifier.getInstanceClass().isInstance(value);
		}
		if (!isInstance) {
			isInstance = classifier.isInstance(value);
		}
		return isInstance;
	}
}
