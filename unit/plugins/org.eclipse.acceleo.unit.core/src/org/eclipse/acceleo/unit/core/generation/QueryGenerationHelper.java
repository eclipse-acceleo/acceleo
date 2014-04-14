/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.generation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.generation.IAcceleoEngine2;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.ocl.ecore.Variable;

/**
 * The acceleo query generation helper class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class QueryGenerationHelper extends AbstractGenerationHelper {

	/**
	 * The generated content.
	 */
	private Object generatedContent;

	/**
	 * The constructor.
	 * 
	 * @param modulePath
	 *            the module path.
	 * @param index
	 *            the signature index.
	 * @param queryQualifiedName
	 *            the query qualified name.
	 */
	public QueryGenerationHelper(String modulePath, int index, String queryQualifiedName) {
		super(modulePath, index, queryQualifiedName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.unit.core.generation.AbstractGenerationHelper#generate()
	 */
	@Override
	public void generate() {
		if (modelElement == null) {
			throw new IllegalArgumentException("Model element is null"); //$NON-NLS-1$
		}

		this.module = this.loadModule();
		if (this.module == null) {
			throw new IllegalArgumentException("Module is null"); //$NON-NLS-1$
		}

		BasicMonitor monitor = new BasicMonitor();
		List<Object> arguments = new ArrayList<Object>();
		arguments.add(modelElement);

		IAcceleoEngine2 engine = new AcceleoEngine();
		List<Query> queries = getQueries();
		if (queries.size() > 0) {
			Query query = queries.get(index);
			if (query != null) {
				Variable variable = query.getParameter().get(0);
				if (variable.getType().isInstance(modelElement)) {
					Object evaluationResult = engine.evaluate(query, arguments, new PreviewStrategy(),
							monitor);
					generatedContent = evaluationResult;
				} else {
					throw new IllegalArgumentException(
							"The query argument " + variable.getType() + " does not match with " + modelElement.getClass()); //$NON-NLS-1$ //$NON-NLS-2$
				}
			} else {
				throw new IllegalArgumentException("The query : " + getQualifiedName() //$NON-NLS-1$
						+ " does not exist or the index : " + index + " is not valid."); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} else {
			throw new IllegalArgumentException("No query could be found"); //$NON-NLS-1$
		}
	}

	/**
	 * Queries getter.
	 * 
	 * @return query list.
	 */
	public List<Query> getQueries() {
		List<Query> queries = new ArrayList<Query>();
		if (module != null) {
			List<ModuleElement> moduleElements = module.getOwnedModuleElement();
			for (ModuleElement element : moduleElements) {
				if (element instanceof Query && getQualifiedName().equals(((Query)element).getName())) {
					queries.add((Query)element);
				}
			}
		}
		return queries;
	}

	/**
	 * Generated content getter.
	 * 
	 * @return the generated content.
	 */
	public Object getGeneratedObject() {
		return generatedContent;
	}

}
