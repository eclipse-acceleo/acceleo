/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.view;

import java.util.ArrayList;

import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterDescriptor;
import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterRegistry;
import org.eclipse.acceleo.ui.interpreter.internal.view.ToggleVariableVisibilityAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableContentProvider;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableDropListener;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableLabelProvider;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.CreateVariableAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.DeleteVariableAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.RenameVariableAction;
import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

/**
 * The Actual "Interpreter" view that will be displayed in the Eclipse workbench.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class InterpreterView extends ViewPart implements IMenuListener {
	/** ID of the interpreter view context. Must be kept in sync with the plugin.xml declaration. */
	private static final String INTERPRETER_VIEW_CONTEXT_ID = "org.eclipse.acceleo.ui.interpreter.interpreterview"; //$NON-NLS-1$

	/** Context activation token. This will be needed to deactivate it. */
	private IContextActivation contextActivationToken;

	/** Currently selected language interpreter. */
	private AbstractLanguageInterpreter currentLanguageInterpreter;

	/**
	 * This source viewer will be used in order to display the "expression" area in which the user can type
	 * the expression that is to be evaluated.
	 */
	private SourceViewer expressionViewer;

	/** Form that will contain the interpreter itself (left part of the view). */
	private Form interpreterForm;

	/** Viewer in which we'll display the result of the evaluations. */
	private Viewer resultViewer;

	/** Viewer in which we'll display the accessible variables. */
	private TreeViewer variableViewer;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite rootContainer = new SashForm(parent, SWT.HORIZONTAL);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		rootContainer.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalIndent = 1;
		rootContainer.setLayoutData(gridData);

		FormToolkit toolkit = new FormToolkit(rootContainer.getDisplay());

		createInterpreterForm(toolkit, rootContainer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
	 */
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		IContextService contextService = (IContextService)site.getService(IContextService.class);
		contextActivationToken = contextService.activateContext(INTERPRETER_VIEW_CONTEXT_ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (expressionViewer != null) {
			expressionViewer.getControl().setFocus();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
		contextService.deactivateContext(contextActivationToken);

		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	public void menuAboutToShow(IMenuManager manager) {
		manager.add(new CreateVariableAction(variableViewer));
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * This will be called to create the "Expression" section (top part of the left column) of the
	 * "Interpreter" form.
	 * 
	 * @param toolkit
	 *            Toolkit that can be used to create form parts.
	 * @param leftColumn
	 *            Left column of the "Interpreter" form.
	 */
	protected void createExpressionSection(FormToolkit toolkit, Composite leftColumn) {
		Section expressionSection = toolkit.createSection(leftColumn, ExpandableComposite.TITLE_BAR);
		expressionSection.setText("Expression");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		expressionSection.setLayoutData(gridData);

		Composite expressionSectionBody = toolkit.createComposite(expressionSection);
		GridLayout expressionSectionLayout = new GridLayout();
		expressionSectionBody.setLayout(expressionSectionLayout);

		expressionViewer = createSourceViewer(expressionSectionBody);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 80;
		expressionViewer.getControl().setLayoutData(gridData);

		toolkit.paintBordersFor(expressionSectionBody);
		expressionSection.setClient(expressionSectionBody);
	}

	/**
	 * This will be called in order to create the "Interpreter" form.
	 * 
	 * @param toolkit
	 *            Toolkit that can be used to create the form.
	 * @param parent
	 *            Parent composite of the form.
	 */
	protected void createInterpreterForm(FormToolkit toolkit, Composite parent) {
		interpreterForm = toolkit.createForm(parent);
		toolkit.decorateFormHeading(interpreterForm);
		interpreterForm.setText(getCurrentLanguageName() + " Interpreter");

		Composite formBody = interpreterForm.getBody();
		GridLayout formLayout = new GridLayout(2, false);
		formBody.setLayout(formLayout);

		Composite leftColumn = toolkit.createComposite(formBody);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		leftColumn.setLayoutData(gridData);
		leftColumn.setLayout(new GridLayout());

		createExpressionSection(toolkit, leftColumn);
		createResultSection(toolkit, leftColumn);

		// The right column is invisible by default
		Composite rightColumn = toolkit.createComposite(formBody);
		rightColumn.setVisible(false);
		gridData = new GridData(SWT.FILL, SWT.FILL, false, true);
		gridData.widthHint = 300;
		gridData.exclude = true;
		rightColumn.setLayoutData(gridData);
		rightColumn.setLayout(new GridLayout());

		createVariableSection(toolkit, rightColumn);

		createToolBar(interpreterForm, rightColumn);
	}

	/**
	 * This will be called to create the "Evaluation Result" section (bottom part of the left column) of the
	 * "Interpreter" form.
	 * 
	 * @param toolkit
	 *            Toolkit that can be used to create form parts.
	 * @param leftColumn
	 *            Left column of the "Interpreter" form.
	 */
	protected void createResultSection(FormToolkit toolkit, Composite leftColumn) {
		Section resultSection = toolkit.createSection(leftColumn, ExpandableComposite.TITLE_BAR);
		resultSection.setText("Evaluation Result");
		GridData gridData = new GridData(GridData.FILL_BOTH);
		resultSection.setLayoutData(gridData);

		Composite resultSectionBody = toolkit.createComposite(resultSection);
		GridLayout resultLayout = new GridLayout();
		resultSectionBody.setLayout(resultLayout);

		resultViewer = createResultViewer(resultSectionBody);
		gridData = new GridData(GridData.FILL_BOTH);
		resultViewer.getControl().setLayoutData(gridData);

		toolkit.paintBordersFor(resultSectionBody);
		resultSection.setClient(resultSectionBody);
	}

	/**
	 * Creates the result viewer for the currently selected language.
	 * 
	 * @param parent
	 *            Parent Composite of the result viewer.
	 * @return The result viewer for the currently selected language.
	 */
	protected Viewer createResultViewer(Composite parent) {
		Viewer viewer = getCurrentLanguageInterpreter().createResultViewer(parent);
		if (viewer == null) {
			viewer = new TreeViewer(parent);
		}
		return viewer;
	}

	/**
	 * Creates the source viewer for the currently selected language.
	 * 
	 * @param parent
	 *            Parent Composite of the result viewer.
	 * @return The source viewer for the currently selected language.
	 */
	protected SourceViewer createSourceViewer(Composite parent) {
		SourceViewer viewer = getCurrentLanguageInterpreter().createSourceViewer(parent);
		if (viewer == null) {
			viewer = new SourceViewer(parent, null, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		}
		getCurrentLanguageInterpreter().configureSourceViewer(viewer);
		return viewer;
	}

	/**
	 * Creates the toolbar of our interpreter form.
	 * 
	 * @param form
	 *            The interpreter form.
	 * @param variableColumn
	 *            The right column of our form, containing the variable section.
	 */
	protected void createToolBar(Form form, Composite variableColumn) {
		form.getToolBarManager().add(new ToggleVariableVisibilityAction(form, variableColumn));
		form.getToolBarManager().update(true);
	}

	/**
	 * This will be called in order to create the "Variables" section (right column) of the "Interpreter"
	 * form.
	 * 
	 * @param toolkit
	 *            Toolkit that can be used to create form parts.
	 * @param rightColumn
	 *            Right column of the "Interpreter" form.
	 */
	protected void createVariableSection(FormToolkit toolkit, Composite rightColumn) {
		Section variableSection = toolkit.createSection(rightColumn, ExpandableComposite.TITLE_BAR);
		variableSection.setText("Variables");
		GridData gridData = new GridData(GridData.FILL_BOTH);
		variableSection.setLayoutData(gridData);

		Composite variableSectionBody = toolkit.createComposite(variableSection);
		variableSectionBody.setLayout(new FillLayout());

		variableViewer = createVariableViewer(toolkit, variableSectionBody);

		toolkit.paintBordersFor(variableSectionBody);
		variableSection.setClient(variableSectionBody);
	}

	/**
	 * This will be called in order to create the TreeViewer used to display variables.
	 * 
	 * @param toolkit
	 *            Toolkit that can be used to create form parts.
	 * @param sectionBody
	 *            Parent composite of the TreeViewer.
	 */
	protected TreeViewer createVariableViewer(FormToolkit toolkit, Composite sectionBody) {
		Tree variableTree = toolkit.createTree(sectionBody, SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);

		TreeViewer viewer = new TreeViewer(variableTree);
		viewer.setContentProvider(new VariableContentProvider());
		viewer.setLabelProvider(new VariableLabelProvider());

		setUpVariableDropSupport(viewer);
		createVariableMenu(viewer);
		setUpVariableActions(viewer);

		viewer.setInput(new ArrayList<Variable>());

		return viewer;
	}

	/**
	 * This will be called in order to set up the actions available on the given variable viewer.
	 * 
	 * @param viewer
	 *            The viewer on which to activate actions.
	 */
	protected void setUpVariableActions(final TreeViewer viewer) {
		Tree tree = viewer.getTree();
		final Action renameAction = new RenameVariableAction(viewer);
		final Action deleteAction = new DeleteVariableAction(viewer);

		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.DEL && event.stateMask == 0) {
					if (deleteAction.isEnabled()) {
						deleteAction.run();
					}
				} else if (event.keyCode == SWT.F2 && event.stateMask == 0) {
					if (renameAction.isEnabled()) {
						renameAction.run();
					}
				}
			}
		});
	}

	/**
	 * Sets up the drag and drop support for the variable viewer.
	 * 
	 * @param viewer
	 *            The variable viewer.
	 */
	protected void setUpVariableDropSupport(TreeViewer viewer) {
		int operations = DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_LINK | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] {LocalTransfer.getInstance(), };

		viewer.addDropSupport(operations, transfers, new VariableDropListener(viewer));
	}

	/**
	 * Creates the right click menu that will be displayed for the variable viewer.
	 * 
	 * @param viewer
	 *            The variable viewer.
	 */
	protected void createVariableMenu(TreeViewer viewer) {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
	}

	/**
	 * Returns the currently selected language.
	 * 
	 * @return The currently selected language.
	 */
	protected AbstractLanguageInterpreter getCurrentLanguageInterpreter() {
		if (currentLanguageInterpreter == null) {
			currentLanguageInterpreter = getCurrentLanguageDescriptor().createLanguageInterpreter();
		}
		return currentLanguageInterpreter;
	}

	/**
	 * Returns the currently selected language's name.
	 * 
	 * @return The currently selected language's name.
	 */
	protected String getCurrentLanguageName() {
		return getCurrentLanguageDescriptor().getLabel();
	}

	/**
	 * Returns the interpreter form.
	 * 
	 * @return The interpreter form.
	 */
	protected Form getForm() {
		return interpreterForm;
	}

	/**
	 * Returns the current source viewer.
	 * <p>
	 * Note that the source viewer can change and be disposed over time; don't keep references to it.
	 * </p>
	 * 
	 * @return The current source viewer.
	 */
	protected SourceViewer getSourceViewer() {
		return expressionViewer;
	}

	/**
	 * Returns the currently selected language's descriptor.
	 * 
	 * @return The currently selected language's descriptor.
	 */
	private LanguageInterpreterDescriptor getCurrentLanguageDescriptor() {
		// FIXME needs a visual dropdown list
		return LanguageInterpreterRegistry.getRegisteredInterpreters().get(0);
	}
}
