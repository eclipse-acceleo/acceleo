/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.ui;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.TextCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * A {@link LabelProvider} for {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProposalLabelProvider extends LabelProvider {

	/**
	 * Icon for {@link ServiceCompletionProposal}.
	 */
	protected Image service;

	/**
	 * Icon for {@link TextCompletionProposal}.
	 */
	protected Image text;

	/**
	 * Icon for {@link VariableCompletionProposal}.
	 */
	protected Image variable;

	/**
	 * The EMF {@link ILabelProvider}.
	 */
	protected final ILabelProvider eLabelProvider;

	/**
	 * Constructor.
	 */
	public ProposalLabelProvider() {
		super();
		Display.getCurrent().syncExec(new Runnable() {

			public void run() {
				InputStream resourceAsStream = this.getClass().getResourceAsStream(
						"/icons/full/obj16/Service.gif");
				service = new Image(Display.getCurrent(), resourceAsStream);
				text = new Image(Display.getCurrent(), this.getClass().getResourceAsStream(
						"/icons/full/obj16/Text.gif"));
				variable = new Image(Display.getCurrent(), this.getClass().getResourceAsStream(
						"/icons/full/obj16/Variable.gif"));
			}
		});

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		eLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
	}

	@Override
	public void dispose() {
		service.dispose();
		service = null;
		text.dispose();
		text = null;
		variable.dispose();
		variable = null;
		eLabelProvider.dispose();
		super.dispose();
	}

	@Override
	public String getText(Object element) {
		final String result;

		if (element instanceof ServiceCompletionProposal) {
			final Method method = ((ServiceCompletionProposal)element).getObject().getServiceMethod();
			result = serviceSignature(method.getName(), method.getParameterTypes());
		} else if (element instanceof ICompletionProposal) {
			result = ((ICompletionProposal)element).getProposal();
		} else {
			result = super.getText(element);
		}

		return result;
	}

	@Override
	public Image getImage(Object element) {
		final Image result;
		if (element instanceof ServiceCompletionProposal) {
			result = service;
		} else if (element instanceof TextCompletionProposal) {
			result = text;
		} else if (element instanceof VariableCompletionProposal) {
			result = variable;
		} else if (element instanceof ICompletionProposal
				&& ((ICompletionProposal)element).getObject() instanceof EObject) {
			result = eLabelProvider.getImage(element);
		} else {
			result = super.getImage(element);
		}
		return result;
	}

	/**
	 * build up the specified service's signature for reporting.
	 * 
	 * @param serviceName
	 *            the name of the service.
	 * @param argumentTypes
	 *            the service's call argument types.
	 * @return the specified service's signature.
	 */
	protected String serviceSignature(String serviceName, Object[] argumentTypes) {
		StringBuilder builder = new StringBuilder();
		builder.append(serviceName).append('(');
		boolean first = true;
		for (Object argType : argumentTypes) {
			if (!first) {
				builder.append(", ");
			} else {
				first = false;
			}
			if (argType instanceof Class<?>) {
				builder.append(((Class<?>)argType).getCanonicalName());
			} else if (argType instanceof EClass) {
				builder.append("EClass=" + ((EClass)argType).getName());
			} else {
				// should not happen
				builder.append("Object=" + argType.toString());
			}
		}
		return builder.append(')').toString();
	}

}
