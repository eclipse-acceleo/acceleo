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
package org.eclipse.acceleo.model.mtl.provider;

import org.eclipse.acceleo.model.ocl.provider.OclItemProviderSpec;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.ocl.ecore.EcorePackage;

/**
 * Specializes the MtlItemProviderAdapterFactory implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MtlItemProviderAdapterFactorySpec extends MtlItemProviderAdapterFactory {
	/**
	 * Instance of the OCL EPackage.
	 */
	private static final EPackage OCL_EPACKAGE = EcorePackage.eINSTANCE;

	/**
	 * This keeps track of the one adapter used for all OCL objects instances in Acceleo.
	 * 
	 * @generated
	 */
	private Adapter oclSpecAdapter;

	/**
	 * Constructor.
	 */
	public MtlItemProviderAdapterFactorySpec() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createFileBlockAdapter()
	 */
	@Override
	public Adapter createFileBlockAdapter() {
		if (fileBlockItemProvider == null) {
			fileBlockItemProvider = new FileBlockItemProviderSpec(this);
		}

		return fileBlockItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createForBlockAdapter()
	 */
	@Override
	public Adapter createForBlockAdapter() {
		if (forBlockItemProvider == null) {
			forBlockItemProvider = new ForBlockItemProviderSpec(this);
		}

		return forBlockItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createIfBlockAdapter()
	 */
	@Override
	public Adapter createIfBlockAdapter() {
		if (ifBlockItemProvider == null) {
			ifBlockItemProvider = new IfBlockItemProviderSpec(this);
		}

		return ifBlockItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createLetBlockAdapter()
	 */
	@Override
	public Adapter createLetBlockAdapter() {
		if (letBlockItemProvider == null) {
			letBlockItemProvider = new LetBlockItemProviderSpec(this);
		}

		return letBlockItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createMacroInvocationAdapter()
	 */
	@Override
	public Adapter createMacroInvocationAdapter() {
		if (macroInvocationItemProvider == null) {
			macroInvocationItemProvider = new MacroInvocationItemProviderSpec(this);
		}

		return macroInvocationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createTemplateInvocationAdapter()
	 */
	@Override
	public Adapter createTemplateInvocationAdapter() {
		if (templateInvocationItemProvider == null) {
			templateInvocationItemProvider = new TemplateInvocationItemProviderSpec(this);
		}

		return templateInvocationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createQueryInvocationAdapter()
	 */
	@Override
	public Adapter createQueryInvocationAdapter() {
		if (queryInvocationItemProvider == null) {
			queryInvocationItemProvider = new QueryInvocationItemProviderSpec(this);
		}

		return queryInvocationItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createQueryAdapter()
	 */
	@Override
	public Adapter createQueryAdapter() {
		if (queryItemProvider == null) {
			queryItemProvider = new QueryItemProviderSpec(this);
		}
		return queryItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#createTemplateAdapter()
	 */
	@Override
	public Adapter createTemplateAdapter() {
		if (templateItemProvider == null) {
			templateItemProvider = new TemplateItemProviderSpec(this);
		}
		return templateItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.MtlItemProviderAdapterFactory#isFactoryForType(java.lang.Object)
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		boolean res = super.isFactoryForType(object);
		res = res || object == OCL_EPACKAGE;
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.util.MtlAdapterFactory#createAdapter(org.eclipse.emf.common.notify.Notifier)
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		Adapter res = super.createAdapter(target);
		if (res == null) {
			if (oclSpecAdapter == null) {
				oclSpecAdapter = new OclItemProviderSpec(this);
			}
			res = oclSpecAdapter;
		}
		return res;
	}
}
