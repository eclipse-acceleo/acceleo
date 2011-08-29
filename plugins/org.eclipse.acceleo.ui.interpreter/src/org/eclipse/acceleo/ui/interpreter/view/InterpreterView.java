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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterDescriptor;
import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterRegistry;
import org.eclipse.acceleo.ui.interpreter.internal.view.ToggleVariableVisibilityAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableContentProvider;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableDropListener;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableLabelProvider;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.CreateVariableAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.DeleteVariableOrValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.RenameVariableAction;
import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.language.IInterpreterSourceViewer;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * The Actual "Interpreter" view that will be displayed in the Eclipse workbench.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class InterpreterView extends ViewPart implements IMenuListener {
	/** Prefix of the messages corresponding to compilation problems. */
	protected static final String COMPILATION_MESSAGE_PREFIX = "compilation.message"; //$NON-NLS-1$

	/** Prefix of the messages corresponding to evaluation problems. */
	protected static final String EVALUATION_MESSAGE_PREFIX = "evaluation.message"; //$NON-NLS-1$

	/** ID of the interpreter view context. Must be kept in sync with the plugin.xml declaration. */
	private static final String INTERPRETER_VIEW_CONTEXT_ID = "org.eclipse.acceleo.ui.interpreter.interpreterview"; //$NON-NLS-1$

	/** This executor service will be used in order to launch the compilation tasks of this interpreter. */
	private ExecutorService compilationPool = Executors.newSingleThreadExecutor();

	/** This executor service will be used in order to launch the evaluation tasks of this interpreter. */
	ExecutorService evaluationPool = Executors.newSingleThreadExecutor();

	/** Context activation token. This will be needed to deactivate it. */
	private IContextActivation contextActivationToken;

	/** Content assist activation token. This will be needed to deactivate our handler. */
	private IHandlerActivation contentAssistActivationToken;

	/** Currently selected language descriptor. */
	private LanguageInterpreterDescriptor currentLanguage;

	/** Currently selected language interpreter. */
	private AbstractLanguageInterpreter currentLanguageInterpreter;

	/** The editing domain that will serve as this interpreter's context. */
	private EditingDomain editingDomain;

	/** This will be used to listen to EObject selection within the workbench. */
	private ISelectionListener eobjectSelectionListener;

	/**
	 * Keeps a reference to the "expression" section of the interpreter form. This will be used to re-create
	 * the result viewer when changing language.
	 */
	private Section expressionSection;

	/**
	 * This source viewer will be used in order to display the "expression" area in which the user can type
	 * the expression that is to be evaluated.
	 */
	private SourceViewer expressionViewer;

	/** Keeps a reference to the toolkit used to create our form. This will be used when switching languages. */
	private FormToolkit formToolkit;

	/** Form that will contain the interpreter itself (left part of the view). */
	private Form interpreterForm;

	/** Kept as an instance member, this will allow us to set unique identifiers to the status messages. */
	private int messageCount;

	/** Thread which purpose is to compile the expression and update the context with the result. */
	protected CompilationThread compilationThread;

	/** Thread which purpose is to evaluate the expression and update the view with the result. */
	private EvaluationThread evaluationThread;

	/**
	 * If we have a compilation result, this will contain it (note that some language are not compiled, thus
	 * an evaluation task can legally be created while this is <code>null</code>.
	 */
	protected CompilationResult compilationResult;

	/**
	 * Keeps a reference to the "result" section of the interpreter form. This will be used to re-create the
	 * result viewer when changing language.
	 */
	private Section resultSection;

	/** Viewer in which we'll display the result of the evaluations. */
	private Viewer resultViewer;

	/** This will hold the current selection of EObjects (in the workspace). */
	private List<EObject> selectedEObjects;

	/** Viewer in which we'll display the accessible variables. */
	private TreeViewer variableViewer;

	/**
	 * Asks for the compilation of the current expression.
	 * <p>
	 * This will take a snapshot of the current interpreter context and launch a new thread for the
	 * compilation. This thread can and will be cancelled whenever a new compilation is required.
	 * </p>
	 */
	public void compileExpression() {
		final InterpreterContext context = getInterpreterContext();
		final Callable<CompilationResult> compilationTask = getCurrentLanguageInterpreter()
				.getCompilationTask(context);

		// Cancel running evaluation task
		if (evaluationThread != null && !evaluationThread.isInterrupted()) {
			evaluationThread.interrupt();
		}
		// Cancel previous compilation task
		if (compilationThread != null && !compilationThread.isInterrupted()) {
			compilationThread.interrupt();
		}

		// Clear previous compilation messages
		getForm().getMessageManager().removeMessages(expressionSection);

		if (compilationTask != null) {
			Future<CompilationResult> compilationFuture = compilationPool.submit(compilationTask);
			compilationThread = new CompilationThread(compilationFuture);
			compilationThread.start();
		}
	}

	public void evaluate() {
		if (evaluationThread != null && !evaluationThread.isInterrupted()) {
			evaluationThread.interrupt();
		}

		// Clear previous evaluation messages
		getForm().getMessageManager().removeMessages(resultSection);

		evaluationThread = new EvaluationThread(getInterpreterContext());
		evaluationThread.start();
	}

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

		formToolkit = new FormToolkit(rootContainer.getDisplay());

		createInterpreterForm(formToolkit, rootContainer);
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

		IHandlerService handlerService = (IHandlerService)getSite().getService(IHandlerService.class);
		handlerService.deactivateHandler(contentAssistActivationToken);

		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null && workbenchWindow.getActivePage() != null
				&& eobjectSelectionListener != null) {
			workbenchWindow.getActivePage().removeSelectionListener(eobjectSelectionListener);
		}
		clearSelection();

		super.dispose();
	}

	/**
	 * Returns the currently selected language.
	 * 
	 * @return The currently selected language.
	 */
	public final AbstractLanguageInterpreter getCurrentLanguageInterpreter() {
		if (currentLanguageInterpreter == null) {
			currentLanguageInterpreter = getCurrentLanguageDescriptor().createLanguageInterpreter();
		}
		return currentLanguageInterpreter;
	}

	/**
	 * Returns the current interpreter context. This will mainly be used by the compilation tasks of the
	 * language interpreters.
	 * 
	 * @return The current interpreter context.
	 */
	@SuppressWarnings("unchecked")
	public InterpreterContext getInterpreterContext() {
		String fullExpression = expressionViewer.getTextWidget().getText();

		List<EObject> targetEObjects = selectedEObjects;
		if (targetEObjects == null) {
			targetEObjects = Collections.emptyList();
		}

		final List<Variable> variables;
		Object variableViewerInput = variableViewer.getInput();
		if (variableViewerInput instanceof List<?>) {
			variables = (List<Variable>)variableViewerInput;
		} else {
			variables = Collections.emptyList();
		}

		return new InterpreterContext(fullExpression, expressionViewer.getSelection(), targetEObjects,
				variables);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
	 */
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		// FIXME use the init(IMemento) instead and restore the view to the proper selected language

		IContextService contextService = (IContextService)site.getService(IContextService.class);
		contextActivationToken = contextService.activateContext(INTERPRETER_VIEW_CONTEXT_ID);

		eobjectSelectionListener = new EObjectSelectionListener();
		site.getPage().addSelectionListener(eobjectSelectionListener);

		AdapterFactory factory = createAdapterFactory();
		CommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(factory, commandStack);
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
	 * Adds a new message to the form.
	 * 
	 * @param messageKey
	 *            Key of the message. Will be used to find and remove it later on.
	 * @param message
	 *            Text of the message that is to be added to the form.
	 * @param messageType
	 *            Type of the message as defined in {@link IMessageProvider}.
	 */
	protected final void addMessage(String messageKey, String message, int messageType) {
		// Adds this message to the appropriate control
		// FIXME find a way to alter the way messages are shown on controls
		Control targetControl = null;
		if (messageKey.startsWith(COMPILATION_MESSAGE_PREFIX)) {
			targetControl = expressionSection;
		} else if (messageKey.startsWith(EVALUATION_MESSAGE_PREFIX)) {
			targetControl = resultSection;
		}

		if (targetControl != null) {
			getForm().getMessageManager().addMessage(messageKey, message, null, messageType, targetControl);
		} else {
			getForm().getMessageManager().addMessage(messageKey, message, null, messageType);
		}
	}

	/**
	 * This will be used by the {@link CompilationThread}s in order to parse the compilation or evaluation
	 * results' status and add the necessary problem message to the form.
	 * 
	 * @param status
	 *            Status which messages is to be added to the form.
	 * @param keyPrefix
	 *            Prefix of the message key.
	 */
	protected final void addStatusMessages(IStatus status, String keyPrefix) {
		if (status instanceof MultiStatus) {
			for (IStatus child : status.getChildren()) {
				addStatusMessages(child, keyPrefix);
			}
		} else {
			String messageKey = keyPrefix + "." + messageCount++; //$NON-NLS-1$

			addMessage(messageKey, status.getMessage(), convertStatusToMessageSeverity(status.getSeverity()));
		}
	}

	/**
	 * Creates a new list for the selection if needed, and adds the given object to it.
	 * 
	 * @param object
	 *            The element that is to be added to the current selection.
	 */
	protected void addToSelection(EObject object) {
		if (selectedEObjects == null) {
			// Assumes the "usual" selection is always 1 element long
			selectedEObjects = new ArrayList<EObject>(1);
		}
		selectedEObjects.add(object);
	}

	/**
	 * If we currently have EObjects selected, this will clear the whole list.
	 */
	protected void clearSelection() {
		if (selectedEObjects != null) {
			selectedEObjects.clear();
			selectedEObjects = null;
		}
	}

	/**
	 * Creates the adapter factory that will be used for our label and content providers.
	 * 
	 * @return The adapter factory that will be used for our label and content providers.
	 */
	protected AdapterFactory createAdapterFactory() {
		return new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
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
		expressionSection = toolkit.createSection(leftColumn, ExpandableComposite.TITLE_BAR);
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
		interpreterForm.getMessageManager().setDecorationPosition(SWT.LEFT | SWT.TOP);
		toolkit.decorateFormHeading(interpreterForm);

		populateLanguageMenu(interpreterForm.getMenuManager());

		interpreterForm.setText(getCurrentLanguageDescriptor().getLabel() + " Interpreter");

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
		resultSection = toolkit.createSection(leftColumn, ExpandableComposite.TITLE_BAR);
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

			AdapterFactory adapterFactory = createAdapterFactory();
			TreeViewer treeViewer = (TreeViewer)viewer;
			treeViewer.setContentProvider(new ResultContentProvider(adapterFactory));
			treeViewer.setLabelProvider(new ResultLabelProvider(adapterFactory));
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

		if (viewer instanceof IInterpreterSourceViewer) {
			final String actionDefinitionID = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
			final IInterpreterSourceViewer interpreterSourceViewer = ((IInterpreterSourceViewer)viewer);

			IAction action = new Action() {
				@Override
				public void run() {
					interpreterSourceViewer.showContentAssist(getInterpreterContext());
				}
			};
			IHandler handler = new ActionHandler(action);

			IHandlerService service = (IHandlerService)getSite().getService(IHandlerService.class);
			contentAssistActivationToken = service.activateHandler(actionDefinitionID, handler);
		}

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
		AdapterFactory adapterFactory = createAdapterFactory();
		viewer.setContentProvider(new VariableContentProvider(adapterFactory));
		viewer.setLabelProvider(new VariableLabelProvider(adapterFactory));

		setUpVariableDropSupport(viewer);
		createVariableMenu(viewer);
		setUpVariableActions(viewer);

		viewer.setInput(new ArrayList<Variable>());

		return viewer;
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
	 * Adds the "language selection" radio buttons to the interpreter's menu.
	 * 
	 * @param menuManager
	 *            The interpreter form's menu manager.
	 */
	protected final void populateLanguageMenu(IMenuManager menuManager) {
		for (LanguageInterpreterDescriptor descriptor : LanguageInterpreterRegistry
				.getRegisteredInterpreters()) {
			IAction action = new ChangeLanguageAction(descriptor);
			menuManager.add(action);
			if (getCurrentLanguageDescriptor() == null) {
				currentLanguage = descriptor;
				action.setChecked(true);
			}
		}
	}

	/**
	 * Switch this intepreter to the given language. This will also re-title and re-create the viewers of this
	 * view.
	 * 
	 * @param selectedLanguage
	 *            The language to which this interpreter should be switched.
	 */
	protected void selectLanguage(LanguageInterpreterDescriptor selectedLanguage) {
		if (currentLanguage == selectedLanguage) {
			return;
		}

		if (compilationThread != null && !compilationThread.isInterrupted()) {
			compilationThread.interrupt();
		}

		/*
		 * We need to remove all actions from the menu : it somehow freeze if we do not. The "trigger" for
		 * this menu freeze is when we remove all messages from the message manager.
		 */
		IContributionItem[] changeLanguageActions = getForm().getMenuManager().getItems();
		getForm().getMessageManager().removeAllMessages();

		currentLanguageInterpreter.dispose();
		currentLanguageInterpreter = null;

		currentLanguage = selectedLanguage;
		interpreterForm.setText(getCurrentLanguageDescriptor().getLabel() + " Interpreter");

		if (expressionSection != null) {
			Composite expressionSectionBody = (Composite)expressionSection.getClient();
			expressionViewer.getControl().dispose();

			expressionViewer = createSourceViewer(expressionSectionBody);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.heightHint = 80;
			expressionViewer.getControl().setLayoutData(gridData);

			formToolkit.paintBordersFor(expressionSectionBody);

			expressionSectionBody.layout();
		}

		if (resultSection != null) {
			Composite resultSectionBody = (Composite)resultSection.getClient();
			resultViewer.getControl().dispose();

			resultViewer = createResultViewer(resultSectionBody);
			GridData gridData = new GridData(GridData.FILL_BOTH);
			resultViewer.getControl().setLayoutData(gridData);

			formToolkit.paintBordersFor(resultSectionBody);

			resultSectionBody.layout();
		}

		// Re-fill the menu now
		for (IContributionItem action : changeLanguageActions) {
			getForm().getMenuManager().add(action);
		}
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
		final Action deleteAction = new DeleteVariableOrValueAction(viewer);

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
	 * Sets the result of the compilation to the given instance.
	 * 
	 * @param compilationResult
	 *            The current expression's compilation result.
	 */
	protected final void setCompilationResult(CompilationResult compilationResult) {
		this.compilationResult = compilationResult;
	}

	/**
	 * Update the result viewer.
	 * 
	 * @param result
	 *            The current expressions's evaluation result.
	 */
	protected final void setEvaluationResult(EvaluationResult result) {
		List<Object> input = new ArrayList<Object>();
		Object evaluationResult = result.getEvaluationResult();
		if (evaluationResult instanceof Collection<?>) {
			input.addAll((Collection<?>)evaluationResult);
		} else if (evaluationResult != null) {
			input.add(evaluationResult);
		}
		resultViewer.setInput(input);
	}

	/**
	 * Converts an IStatus severity to a IMessageProviderSeverity.
	 * 
	 * @param statusSeverity
	 *            The status severity to be converted.
	 * @return The corresponding IMessageProvider severity.
	 */
	private int convertStatusToMessageSeverity(int statusSeverity) {
		int severity = IMessageProvider.NONE;
		switch (statusSeverity) {
			case IStatus.INFO:
				severity = IMessageProvider.INFORMATION;
				break;
			case IStatus.WARNING:
				severity = IMessageProvider.WARNING;
				break;
			case IStatus.ERROR:
				severity = IMessageProvider.ERROR;
				break;
			default:
				severity = IMessageProvider.NONE;
		}
		return severity;
	}

	/**
	 * Returns the currently selected language's descriptor.
	 * 
	 * @return The currently selected language's descriptor.
	 */
	private LanguageInterpreterDescriptor getCurrentLanguageDescriptor() {
		return currentLanguage;
	}

	/**
	 * This class will be used for all of our "language changing" actions.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ChangeLanguageAction extends Action {
		/** Language to which this action will change the interpreter when executed. */
		private final LanguageInterpreterDescriptor language;

		/**
		 * Instantiates our action given the language to which it will change.
		 * 
		 * @param language
		 *            The language to which this action will change the interpreter.
		 */
		public ChangeLanguageAction(LanguageInterpreterDescriptor language) {
			super(language.getLabel(), IAction.AS_RADIO_BUTTON);
			this.language = language;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			selectLanguage(language);
		}
	}

	/**
	 * This will be installed on the workbench page on which this view is displayed in order to listen to
	 * selection events corresponding to EObjects.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class EObjectSelectionListener implements ISelectionListener {
		/**
		 * Increases visibility of the default constructor.
		 */
		public EObjectSelectionListener() {
			// Increases visibility
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
		 *      org.eclipse.jface.viewers.ISelection)
		 */
		@SuppressWarnings("unchecked")
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
				boolean cleared = false;
				final Iterator<Object> selectionIterator = ((IStructuredSelection)selection).iterator();
				while (selectionIterator.hasNext()) {
					final Object next = selectionIterator.next();
					if (next instanceof EObject) {
						// At least one of the selected objects is an EObject, clear current selection
						if (!cleared) {
							clearSelection();
							cleared = true;
						}
						addToSelection((EObject)next);
					}
				}
			}
		}
	}

	/**
	 * This implementation of a Thread will be used to wrap an evaluation task as returned by the
	 * LanguageInterpreter, then asynchronously update the form with all error messages (if any) that were
	 * raised by this compilation task. Afterwards, this Thread will update the result view of the interpreter
	 * form.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class EvaluationThread extends Thread {
		/** The evaluation thread which result we are to wait for. */
		private Future<EvaluationResult> evaluationTask;

		/** Context of the interpreter as it was when this Thread has been created. */
		private final InterpreterContext interpreterContext;

		/**
		 * Instantiates our Thread given the initial interpreter context.
		 * 
		 * @param interpreterContext
		 *            The initial interpreter context.
		 */
		public EvaluationThread(InterpreterContext interpreterContext) {
			super("InterpreterEvaluationThread"); //$NON-NLS-1$
			this.interpreterContext = interpreterContext;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Thread#interrupt()
		 */
		@Override
		public void interrupt() {
			super.interrupt();
			if (evaluationTask != null) {
				evaluationTask.cancel(true);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try {
				// Cannot do anything before the current compilation thread stops.
				if (compilationThread != null) {
					compilationThread.join();
				}

				Callable<EvaluationResult> evaluationCallable = getCurrentLanguageInterpreter()
						.getEvaluationTask(new EvaluationContext(interpreterContext, compilationResult));
				evaluationTask = evaluationPool.submit(evaluationCallable);

				final EvaluationResult result = evaluationTask.get();

				if (result != null && result.getProblems() != null) {
					Display.getDefault().asyncExec(new Runnable() {
						/**
						 * {@inheritDoc}
						 * 
						 * @see java.lang.Runnable#run()
						 */
						public void run() {
							addStatusMessages(result.getProblems(), EVALUATION_MESSAGE_PREFIX);
						}
					});
				}

				// whether there were problems or not, try and update the result viewer.
				Display.getDefault().asyncExec(new Runnable() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see java.lang.Runnable#run()
					 */
					public void run() {
						setEvaluationResult(result);
					}
				});
			} catch (InterruptedException e) {
				// Thread is expected to be cancelled if another is started
			} catch (ExecutionException e) {
				// FIXME log
			}
		}
	}

	/**
	 * This implementation of a Thread will be used to wrap a compilation task as returned by the
	 * LanguageInterpreter, then asynchronously update the form with all error messages (if any) that were
	 * raised by this compilation task. Afterwards, this Thread will update the interpreter context with the
	 * compilation result.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class CompilationThread extends Thread {
		/** The compilation thread which result we are to wait for. */
		private Future<CompilationResult> compilationTask;

		/**
		 * Instantiates a compilation thread given the compilation task of which we are to check the result.
		 * 
		 * @param compilationTask
		 *            Thread which result we are to wait for.
		 */
		public CompilationThread(Future<CompilationResult> compilationTask) {
			super("InterpreterCompilationThread"); //$NON-NLS-1$
			this.compilationTask = compilationTask;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Thread#interrupt()
		 */
		@Override
		public void interrupt() {
			super.interrupt();
			compilationTask.cancel(true);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try {
				final CompilationResult result = compilationTask.get();

				if (result != null && result.getProblems() != null) {
					Display.getDefault().asyncExec(new Runnable() {
						/**
						 * {@inheritDoc}
						 * 
						 * @see java.lang.Runnable#run()
						 */
						public void run() {
							addStatusMessages(result.getProblems(), COMPILATION_MESSAGE_PREFIX);
						}
					});
				}

				// Whether there were problems or not, update the context with this result.
				setCompilationResult(result);
			} catch (InterruptedException e) {
				// Thread is expected to be cancelled if another is started
			} catch (ExecutionException e) {
				// FIXME log
			}
		}
	}
}
