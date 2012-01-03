/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.generation;

import java.util.List;

import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.Monitor;

/**
 * This interface is only temporary, it serves as a temporary workaroun in order not to break the
 * {@link IAcceleoEngine} interface.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public interface IAcceleoEngine2 extends IAcceleoEngine {
	/**
	 * Evaluates the given Acceleo query with the given arguments.
	 * 
	 * @param query
	 *            The Acceleo query which is to be evaluated.
	 * @param arguments
	 *            List of the template's arguments.
	 * @param strategy
	 *            The generation strategy that's to be used by this engine.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return The result of this query's evaluation.
	 */
	Object evaluate(Query query, List<? extends Object> arguments, IAcceleoGenerationStrategy strategy,
			Monitor monitor);

	/**
	 * Evaluates the given Acceleo template with the given arguments.
	 * 
	 * @param template
	 *            The Acceleo template which is to be evaluated.
	 * @param arguments
	 *            List of the template's arguments.
	 * @param strategy
	 *            The generation strategy that's to be used by this engine.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation. Can be <code>null</code>.
	 * @return The result of this query's evaluation.
	 */
	Object evaluate(Template template, List<? extends Object> arguments, IAcceleoGenerationStrategy strategy,
			Monitor monitor);
}
