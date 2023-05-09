/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.ui.test.view;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ide.ui.ProposalLabelProvider;
import org.eclipse.acceleo.query.ide.ui.viewer.AQLConfiguration;
import org.eclipse.acceleo.query.ide.ui.viewer.ColorManager;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * Test view for {@link AQLConfiguration}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLTestView extends ViewPart {

	/**
	 * The 12 constant.
	 */
	private static final int TWELVE = 12;

	/**
	 * The {@link ColorManager}.
	 */
	private final ColorManager colorManager = new ColorManager();

	/**
	 * The {@link ILabelProvider}.
	 */
	private final ILabelProvider labelProvider = new ProposalLabelProvider();

	/**
	 * The {@link SourceViewer}.
	 */
	private SourceViewer sourceViewer;

	/**
	 * Content assist {@link IHandlerActivation}.
	 */
	private IHandlerActivation contentAssistHandlerActivation;

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		final GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);

		sourceViewer = new SourceViewer(composite, new VerticalRuler(TWELVE), SWT.BORDER);
		sourceViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		final IQueryEnvironment env = Query.newEnvironmentWithDefaultServices(null);
		final Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(env, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("self", selfTypes);
		final AQLConfiguration configuration = new AQLConfiguration(colorManager, labelProvider, env,
				variableTypes);
		sourceViewer.configure(configuration);

		sourceViewer.setInput(new Document(""));

		setUpContentAssist(configuration.getContentAssistant(sourceViewer));
	}

	/**
	 * Sets up the content assist action for the given source viewer.
	 * 
	 * @param assistant
	 *            The viewer {@link IContentAssistant}
	 */
	protected final void setUpContentAssist(final IContentAssistant assistant) {
		IHandlerService service = (IHandlerService)getSite().getService(IHandlerService.class);
		if (contentAssistHandlerActivation != null) {
			service.deactivateHandler(contentAssistHandlerActivation);
		}

		IAction contentAssistAction = new Action() {
			@Override
			public void run() {
				assistant.showPossibleCompletions();
			}
		};
		IHandler contentAssistHandler = new ActionHandler(contentAssistAction);

		contentAssistHandlerActivation = service.activateHandler(
				ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS, contentAssistHandler);
	}

	@Override
	public void dispose() {
		super.dispose();
		colorManager.dispose();
		labelProvider.dispose();
		IHandlerService service = (IHandlerService)getSite().getService(IHandlerService.class);
		service.deactivateHandler(contentAssistHandlerActivation);
	}

	@Override
	public void setFocus() {
		if (sourceViewer != null) {
			sourceViewer.getControl().setFocus();
		}
	}

}
