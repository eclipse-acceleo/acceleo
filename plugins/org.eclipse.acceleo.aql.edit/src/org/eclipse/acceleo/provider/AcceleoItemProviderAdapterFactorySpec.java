/**
 * Copyright (c) 2008, 2020 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.provider;

import org.eclipse.emf.common.notify.Adapter;

public class AcceleoItemProviderAdapterFactorySpec extends AcceleoItemProviderAdapterFactory {

	/**
	 * Constructor.
	 */
	public AcceleoItemProviderAdapterFactorySpec() {
		super();
	}

	@Override
	public Adapter createFileStatementAdapter() {
		if (fileStatementItemProvider == null) {
			fileStatementItemProvider = new FileStatementItemProviderSpec(this);
		}

		return fileStatementItemProvider;
	}

	@Override
	public Adapter createForStatementAdapter() {
		if (forStatementItemProvider == null) {
			forStatementItemProvider = new ForStatementItemProviderSpec(this);
		}

		return forStatementItemProvider;
	}

	@Override
	public Adapter createIfStatementAdapter() {
		if (ifStatementItemProvider == null) {
			ifStatementItemProvider = new IfStatementItemProviderSpec(this);
		}

		return ifStatementItemProvider;
	}

	@Override
	public Adapter createLetStatementAdapter() {
		if (letStatementItemProvider == null) {
			letStatementItemProvider = new LetStatementItemProviderSpec(this);
		}

		return letStatementItemProvider;
	}

	@Override
	public Adapter createTemplateAdapter() {
		if (templateItemProvider == null) {
			templateItemProvider = new TemplateItemProviderSpec(this);
		}
		return templateItemProvider;
	}

	@Override
	public Adapter createExpressionAdapter() {
		if (expressionItemProvider == null) {
			expressionItemProvider = new ExpressionItemProviderSpec(this);
		}
		return expressionItemProvider;
	}

	@Override
	public Adapter createExpressionStatementAdapter() {
		if (expressionStatementItemProvider == null) {
			expressionStatementItemProvider = new ExpressionStatementItemProviderSpec(this);
		}
		return expressionStatementItemProvider;
	}
}
