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

import org.eclipse.acceleo.ui.interpreter.InterpreterPlugin;
import org.eclipse.acceleo.ui.interpreter.internal.IInterpreterConstants;
import org.eclipse.acceleo.ui.interpreter.internal.InterpreterImages;
import org.eclipse.acceleo.ui.interpreter.internal.InterpreterMessages;
import org.eclipse.acceleo.ui.interpreter.internal.language.DefaultLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterDescriptor;
import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterRegistry;
import org.eclipse.acceleo.ui.interpreter.internal.view.InterpreterFileStorage;
import org.eclipse.acceleo.ui.interpreter.internal.view.ResultDragListener;
import org.eclipse.acceleo.ui.interpreter.internal.view.StorageEditorInput;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableContentProvider;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableDropListener;
import org.eclipse.acceleo.ui.interpreter.internal.view.VariableLabelProvider;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.ClearExpressionViewerAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.ClearResultViewerAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.ClearVariableViewerAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.DeleteVariableOrValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.EvaluateAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.NewBooleanValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.NewEObjectValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.NewFloatValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.NewIntegerValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.NewStringValueAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.NewVariableAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.RenameVariableAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.ToggleLinkWithEditorAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.ToggleRealTimeAction;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.ToggleVariableVisibilityAction;
import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.language.IInterpreterSourceViewer;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.editors.text.EditorsUI;
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
public class InterpreterView extends ViewPart {
	/** Prefix of the messages corresponding to compilation problems. */
	protected static final String COMPILATION_MESSAGE_PREFIX = "compilation.message"; //$NON-NLS-1$

	/** Prefix of the messages corresponding to evaluation problems. */
	protected static final String EVALUATION_MESSAGE_PREFIX = "evaluation.message"; //$NON-NLS-1$

	/** ID of the interpreter view context. Must be kept in sync with the plugin.xml declaration. */
	private static final String INTERPRETER_VIEW_CONTEXT_ID = "org.eclipse.acceleo.ui.interpreter.interpreterview"; //$NON-NLS-1$

	/** Key for the currently selected language as stored in this view's memento. */
	private static final String MEMENTO_CURRENT_LANGUAGE_KEY = "org.eclipse.acceleo.ui.interpreter.memento.current.language"; //$NON-NLS-1$

	/** Key for the expression as stored in this view's memento. */
	private static final String MEMENTO_EXPRESSION_KEY = "org.eclipse.acceleo.ui.interpreter.memento.expression"; //$NON-NLS-1$

	/** Key for the "link with editor" state as stored in this view's memento. */
	private static final String MEMENTO_LINK_WITH_EDITOR_KEY = "org.eclipse.acceleo.ui.interpreter.memento.linkwitheditor"; //$NON-NLS-1$

	/** Key for the real-time compilation state as stored in this view's memento. */
	private static final String MEMENTO_REAL_TIME_KEY = "org.eclipse.acceleo.ui.interpreter.memento.realtime"; //$NON-NLS-1$

	/** Key for the hidden state of the variable viewer as stored in this view's memento. */
	private static final String MEMENTO_VARIABLES_HIDDEN_KEY = "org.eclipse.acceleo.ui.interpreter.memento.variables.hide"; //$NON-NLS-1$

	/**
	 * If we have a compilation result, this will contain it (note that some language are not compiled, thus
	 * an evaluation task can legally be created while this is <code>null</code>.
	 */
	protected CompilationResult compilationResult;

	/** Thread which purpose is to compile the expression and update the context with the result. */
	protected CompilationThread compilationThread;

	/** This executor service will be used in order to launch the evaluation tasks of this interpreter. */
	/* package */ExecutorService evaluationPool = Executors.newSingleThreadExecutor();

	/** This will hold the real-time evaluation thread. */
	/* package */RealTimeThread realTimeThread;

	/** Content assist activation token. This will be needed to deactivate our handler. */
	private IHandlerActivation activationTokenContentAssist;

	/** Redo activation token. This will be needed to deactivate our handler. */
	private IHandlerActivation activationTokenRedo;

	/** Undo action activation token. This will be needed to deactivate our handler. */
	private IHandlerActivation activationTokenUndo;

	/** This executor service will be used in order to launch the compilation tasks of this interpreter. */
	private ExecutorService compilationPool = Executors.newSingleThreadExecutor();

	/** Context activation token. This will be needed to deactivate it. */
	private IContextActivation contextActivationToken;

	/** Currently selected language descriptor. */
	private LanguageInterpreterDescriptor currentLanguage;

	/** Currently selected language interpreter. */
	private AbstractLanguageInterpreter currentLanguageInterpreter;

	/** We'll add this listener to the current page whenever the "link with editor" action is enabled. */
	private IPartListener2 editorPartListener = new InterpreterPartListener();

	/** This will be used to listen to EObject selection within the workbench. */
	private ISelectionListener eobjectSelectionListener;

	/** Thread which purpose is to evaluate the expression and update the view with the result. */
	private EvaluationThread evaluationThread;

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

	/**
	 * This boolean indicates whether we should send notifications to the language interpreter when a new
	 * editor is given focus.
	 */
	private boolean linkWithEditor;

	/** Keeps a reference to the "link with editor" action. */
	private IAction linkWithEditorAction;

	/** Kept as an instance member, this will allow us to set unique identifiers to the status messages. */
	private int messageCount;

	/** Memento from which to restore this view's state. */
	private IMemento partMemento;

	/** This indicates whether we should be compiling the expression in real-time. */
	private boolean realTime;

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
	 * Creates a tool bar for the given section.
	 * 
	 * @param section
	 *            The section for which we need a tool bar.
	 * @return The created tool bar.
	 */
	protected static final ToolBarManager createSectionToolBar(Section section) {
		final ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		final ToolBar toolBar = toolBarManager.createControl(section);

		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolBar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (!handCursor.isDisposed()) {
					handCursor.dispose();
				}
			}
		});

		section.setTextClient(toolBar);
		toolBar.setData(toolBarManager);
		toolBar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolBar.setData(null);
			}
		});

		return toolBarManager;
	}

	/**
	 * Returns the toolbar of the given section if any.
	 * 
	 * @param section
	 *            The section of which we need the toolbar.
	 * @return The toolbar of the given section if any.
	 */
	protected static final ToolBarManager getSectionToolBar(Section section) {
		Control textClient = section.getTextClient();
		if (textClient instanceof ToolBar) {
			ToolBar toolBar = (ToolBar)textClient;
			Object data = toolBar.getData();
			if (data instanceof ToolBarManager) {
				return (ToolBarManager)data;
			}
		}
		return null;
	}

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

		// The view's state has been restored on-the-fly. We can now discard the memento.
		partMemento = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		if (contextActivationToken != null) {
			IContextService contextService = (IContextService)getSite().getService(IContextService.class);
			contextService.deactivateContext(contextActivationToken);
		}

		IHandlerService handlerService = (IHandlerService)getSite().getService(IHandlerService.class);
		if (activationTokenContentAssist != null) {
			handlerService.deactivateHandler(activationTokenContentAssist);
		}
		if (activationTokenRedo != null) {
			handlerService.deactivateHandler(activationTokenRedo);
		}
		if (activationTokenUndo != null) {
			handlerService.deactivateHandler(activationTokenUndo);
		}

		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null && workbenchWindow.getActivePage() != null
				&& eobjectSelectionListener != null) {
			workbenchWindow.getActivePage().removeSelectionListener(eobjectSelectionListener);
		}
		clearSelection();

		super.dispose();
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
	 * Returns the currently selected language.
	 * 
	 * @return The currently selected language.
	 */
	public final AbstractLanguageInterpreter getCurrentLanguageInterpreter() {
		if (currentLanguageInterpreter == null) {
			if (getCurrentLanguageDescriptor() != null) {
				currentLanguageInterpreter = getCurrentLanguageDescriptor().createLanguageInterpreter();
			} else {
				currentLanguageInterpreter = new DefaultLanguageInterpreter();
			}
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

		ISelection selection = expressionViewer.getSelection();
		if (selection == null
				|| (selection instanceof ITextSelection && ((ITextSelection)selection).getLength() == 0)) {
			selection = new TextSelection(expressionViewer.getDocument(), 0, fullExpression.length());
		}

		return new InterpreterContext(fullExpression, selection, targetEObjects, variables);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		this.partMemento = memento;

		IContextService contextService = (IContextService)site.getService(IContextService.class);
		contextActivationToken = contextService.activateContext(INTERPRETER_VIEW_CONTEXT_ID);

		eobjectSelectionListener = new EObjectSelectionListener();
		site.getPage().addSelectionListener(eobjectSelectionListener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
	 */
	@Override
	public void saveState(IMemento memento) {
		if (partMemento != null) {
			// Part had not been restored, keep old state
			memento.putMemento(partMemento);
		} else {
			if (getCurrentLanguageDescriptor() != null) {
				memento.putString(MEMENTO_CURRENT_LANGUAGE_KEY, getCurrentLanguageDescriptor().getClassName());
			}
			memento.putString(MEMENTO_EXPRESSION_KEY, expressionViewer.getTextWidget().getText());
			memento.putBoolean(MEMENTO_LINK_WITH_EDITOR_KEY, Boolean.valueOf(linkWithEditor));
			memento.putBoolean(MEMENTO_REAL_TIME_KEY, Boolean.valueOf(realTime));
			memento.putBoolean(MEMENTO_VARIABLES_HIDDEN_KEY,
					Boolean.valueOf(variableViewer.getControl().isVisible()));
		}
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
	 * Enables (or disables) the language interpreter's link with the editor focus.
	 */
	public void toggleLinkWithEditor() {
		IWorkbenchPage page = getSite().getPage();
		if (linkWithEditorAction.isEnabled()) {
			linkWithEditor = !linkWithEditor;
			if (linkWithEditor && linkWithEditorAction.isEnabled()) {
				page.addPartListener(editorPartListener);

				IEditorPart activeEditor = page.getActiveEditor();
				if (activeEditor != null) {
					editorActivated(activeEditor);
				}
			} else {
				page.removePartListener(editorPartListener);
			}
		} else {
			page.removePartListener(editorPartListener);
		}
	}

	/**
	 * Enables (or disables) the real-time evaluation of expressions.
	 */
	public synchronized void toggleRealTime() {
		realTime = !realTime;
		if (realTime) {
			realTimeThread = new RealTimeThread();

			final String text = expressionViewer.getTextWidget().getText();
			if (text != null && text.length() > 0) {
				// Launch a compilation right from the get-go
				compileExpression();
				evaluate();
			}

			realTimeThread.start();
		} else {
			if (realTimeThread != null) {
				realTimeThread.interrupt();
				realTimeThread = null;
			}
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
	 * Creates the expression viewer menu listener. This listener is in charge of filling the menu's actions.
	 * 
	 * @param viewer
	 *            The expression viewer.
	 */
	protected IMenuListener createExpressionMenuListener(SourceViewer viewer) {
		return new ExpressionMenuListener(viewer);
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
		expressionSection.setText(InterpreterMessages.getString("interpreter.view.expression.section.name")); //$NON-NLS-1$

		Composite expressionSectionBody = toolkit.createComposite(expressionSection);
		GridLayout expressionSectionLayout = new GridLayout();
		expressionSectionBody.setLayout(expressionSectionLayout);

		expressionViewer = createExpressionViewer(expressionSectionBody);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		expressionViewer.getControl().setLayoutData(gridData);

		createSectionToolBar(expressionSection);
		populateExpressionSectionToolbar(expressionSection);

		toolkit.paintBordersFor(expressionSectionBody);
		expressionSection.setClient(expressionSectionBody);
	}

	/**
	 * Creates the source viewer for the currently selected language.
	 * 
	 * @param parent
	 *            Parent Composite of the result viewer.
	 * @return The source viewer for the currently selected language.
	 */
	protected SourceViewer createExpressionViewer(Composite parent) {
		SourceViewer viewer = getCurrentLanguageInterpreter().createSourceViewer(parent);
		if (viewer == null) {
			viewer = new SourceViewer(parent, null, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		}
		getCurrentLanguageInterpreter().configureSourceViewer(viewer);

		if (viewer instanceof IInterpreterSourceViewer) {
			setUpContentAssist((IInterpreterSourceViewer)viewer);
		}
		setUpRealTimeCompilation(viewer);
		setUpDefaultTextAction(viewer);

		createExpressionMenu(viewer);

		if (partMemento != null) {
			String expression = partMemento.getString(MEMENTO_EXPRESSION_KEY);
			if (expression != null) {
				viewer.getTextWidget().setText(expression);
			}
		}
		return viewer;
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

		/*
		 * This will be called at initialization only. When initializing the "languages" menu, we do select
		 * the accurate descriptor, but we do not set the language text and icon. There is a chance that no
		 * languages have been provided. Use a default text and image for these.
		 */
		String languageName = ""; //$NON-NLS-1$
		ImageDescriptor titleImageDescriptor = null;
		if (getCurrentLanguageDescriptor() != null) {
			languageName = getCurrentLanguageDescriptor().getLabel();
			titleImageDescriptor = getCurrentLanguageDescriptor().getIcon();
		}
		interpreterForm.setText(InterpreterMessages.getString("interpreter.view.title", //$NON-NLS-1$
				languageName));
		final Image titleImage;
		if (titleImageDescriptor != null) {
			titleImage = titleImageDescriptor.createImage();
		} else {
			titleImage = InterpreterImages.getImageDescriptor(
					IInterpreterConstants.INTERPRETER_VIEW_DEFAULT_ICON).createImage();
		}
		setTitleImage(titleImage);
		interpreterForm.setImage(titleImage);

		Composite formBody = interpreterForm.getBody();
		GridLayout formLayout = new GridLayout(2, false);
		formBody.setLayout(formLayout);

		SashForm leftColumn = new SashForm(formBody, SWT.VERTICAL);
		toolkit.adapt(leftColumn);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		leftColumn.setLayoutData(gridData);

		createExpressionSection(toolkit, leftColumn);
		createResultSection(toolkit, leftColumn);

		leftColumn.setWeights(new int[] {1, 2, });

		// The right column is invisible by default
		boolean variableVisible = false;
		if (partMemento != null) {
			Boolean state = partMemento.getBoolean(MEMENTO_VARIABLES_HIDDEN_KEY);
			variableVisible = state != null && state.booleanValue();
		}
		Composite rightColumn = toolkit.createComposite(formBody);
		rightColumn.setVisible(variableVisible);
		gridData = new GridData(SWT.FILL, SWT.FILL, false, true);
		gridData.widthHint = 300;
		gridData.exclude = !variableVisible;
		rightColumn.setLayoutData(gridData);
		rightColumn.setLayout(new FillLayout());

		createVariableSection(toolkit, rightColumn);

		createToolBar(interpreterForm, rightColumn);
	}

	/**
	 * Creates the result viewer menu listener. This listener is in charge of filling the menu's actions.
	 * 
	 * @param viewer
	 *            The result viewer.
	 */
	protected IMenuListener createResultMenuListener(Viewer viewer) {
		return new ResultMenuListener(viewer);
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
		resultSection.setText(InterpreterMessages.getString("interpreter.view.result.section.name")); //$NON-NLS-1$

		Composite resultSectionBody = toolkit.createComposite(resultSection);
		GridLayout resultLayout = new GridLayout();
		resultSectionBody.setLayout(resultLayout);

		resultViewer = createResultViewer(resultSectionBody);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		resultViewer.getControl().setLayoutData(gridData);

		createSectionToolBar(resultSection);
		populateResultSectionToolbar(resultSection);

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
			viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			ColumnViewerToolTipSupport.enableFor((TreeViewer)viewer);

			AdapterFactory adapterFactory = createAdapterFactory();
			TreeViewer treeViewer = (TreeViewer)viewer;
			treeViewer.setContentProvider(new ResultContentProvider(adapterFactory));
			treeViewer.setLabelProvider(new ResultLabelProvider(adapterFactory));
		}

		if (viewer instanceof TreeViewer) {
			setUpResultDragSupport((TreeViewer)viewer);

			((TreeViewer)viewer).addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					if (event.getSelection().isEmpty()
							|| !(event.getSelection() instanceof IStructuredSelection)) {
						return;
					}
					final Object target = ((IStructuredSelection)event.getSelection()).getFirstElement();
					if (target instanceof InterpreterFile) {
						final IWorkbench workbench = PlatformUI.getWorkbench();
						if (workbench.getActiveWorkbenchWindow() == null
								|| workbench.getActiveWorkbenchWindow().getActivePage() == null) {
							return;
						}
						final IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
						final IStorage storage = new InterpreterFileStorage((InterpreterFile)target);
						final IEditorDescriptor editor = workbench.getEditorRegistry().getDefaultEditor(
								((InterpreterFile)target).getFileName());
						final IEditorInput input = new StorageEditorInput(storage);

						try {
							final String editorID;
							if (editor == null) {
								editorID = EditorsUI.DEFAULT_TEXT_EDITOR_ID;
							} else {
								editorID = editor.getId();
							}
							page.openEditor(input, editorID);
						} catch (PartInitException e) {
							// swallow : we just won't open editors
						}
					}
				}
			});
		}

		createResultMenu(viewer);

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
		IAction realTimeAction = new ToggleRealTimeAction(this);
		if (partMemento != null) {
			Boolean isRealTime = partMemento.getBoolean(MEMENTO_REAL_TIME_KEY);
			if (isRealTime != null && isRealTime.booleanValue()) {
				toggleRealTime();
				realTimeAction.setChecked(realTime);
			}
		} else {
			// Real-time is active by default
			toggleRealTime();
			realTimeAction.setChecked(realTime);
		}

		linkWithEditorAction = new ToggleLinkWithEditorAction(this);
		linkWithEditorAction.setEnabled(getCurrentLanguageInterpreter().acceptLinkWithEditor());
		if (partMemento != null) {
			Boolean isLinkEnabled = partMemento.getBoolean(MEMENTO_LINK_WITH_EDITOR_KEY);
			if (isLinkEnabled != null && isLinkEnabled.booleanValue()) {
				toggleLinkWithEditor();
				linkWithEditorAction.setChecked(linkWithEditor);
			}
		}

		IAction variableVisibilityAction = new ToggleVariableVisibilityAction(form, variableColumn);
		variableVisibilityAction.setChecked(variableColumn.getLayoutData() instanceof GridData
				&& !((GridData)variableColumn.getLayoutData()).exclude);

		IToolBarManager toolBarManager = form.getToolBarManager();
		toolBarManager.add(linkWithEditorAction);
		toolBarManager.add(realTimeAction);
		toolBarManager.add(variableVisibilityAction);

		getCurrentLanguageInterpreter().addToolBarActions(toolBarManager);

		toolBarManager.update(true);
	}

	/**
	 * Creates the variable viewer menu listener. This listener is in charge of filling the menu's actions.
	 * 
	 * @param viewer
	 *            The variable viewer.
	 */
	protected IMenuListener createVariableMenuListener(TreeViewer viewer) {
		return new VariableMenuListener(viewer);
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
		variableSection.setText(InterpreterMessages.getString("interpreter.view.variable.section.name")); //$NON-NLS-1$

		Composite variableSectionBody = toolkit.createComposite(variableSection);
		variableSectionBody.setLayout(new FillLayout());

		variableViewer = createVariableViewer(toolkit, variableSectionBody);

		ToolBarManager toolBarManager = createSectionToolBar(variableSection);
		toolBarManager.add(new ClearVariableViewerAction(variableViewer));
		toolBarManager.update(true);

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
		Tree variableTree = toolkit.createTree(sectionBody, SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);

		TreeViewer viewer = new TreeViewer(variableTree);
		ColumnViewerToolTipSupport.enableFor(viewer);
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
	 * This will be called whenever an editor has been activated, the input of the active editor has changed,
	 * or the last editor has been closed.
	 * 
	 * @param editorPart
	 *            The editor that has been activated. May be <code>null</code>, in which case the last editor
	 *            has been closed.
	 */
	protected void editorActivated(IEditorPart editorPart) {
		getCurrentLanguageInterpreter().linkWithEditor(editorPart);
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
	 * Populates the {@link #expressionSection}'s toolbar.
	 * 
	 * @param section
	 *            the expresssion section.
	 */
	protected void populateExpressionSectionToolbar(Section section) {
		ToolBarManager toolBarManager = getSectionToolBar(section);
		if (toolBarManager != null) {
			toolBarManager.removeAll();
			toolBarManager.add(new EvaluateAction(this));
			toolBarManager.add(new ClearExpressionViewerAction(getSourceViewer()));
			toolBarManager.update(true);
		}
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
				if (partMemento == null || partMemento.getString(MEMENTO_CURRENT_LANGUAGE_KEY) == null) {
					currentLanguage = descriptor;
					action.setChecked(true);
				} else if (partMemento.getString(MEMENTO_CURRENT_LANGUAGE_KEY).equals(
						descriptor.getClassName())) {
					currentLanguage = descriptor;
					action.setChecked(true);
				}
			}
		}
	}

	/**
	 * Populates the {@link #resultSection}'s toolbar.
	 * 
	 * @param section
	 *            the result section.
	 */
	protected void populateResultSectionToolbar(Section section) {
		ToolBarManager toolBarManager = getSectionToolBar(section);
		if (toolBarManager != null) {
			toolBarManager.removeAll();
			toolBarManager.add(new ClearResultViewerAction(resultViewer));
			toolBarManager.update(true);
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

		getCurrentLanguageInterpreter().dispose();
		currentLanguageInterpreter = null;

		LanguageInterpreterDescriptor previousLanguage = getCurrentLanguageDescriptor();
		currentLanguage = selectedLanguage;

		// Change interpreter title
		interpreterForm.setText(InterpreterMessages.getString("interpreter.view.title", //$NON-NLS-1$
				getCurrentLanguageDescriptor().getLabel()));

		// Change view's icon
		Image previousImage = null;
		if (previousLanguage != null && previousLanguage.getIcon() != null
				|| getCurrentLanguageDescriptor().getIcon() != null) {
			previousImage = getTitleImage();
		}
		if (getCurrentLanguageDescriptor().getIcon() != null) {
			setTitleImage(getCurrentLanguageDescriptor().getIcon().createImage());
		} else if (previousLanguage != null && previousLanguage.getIcon() != null) {
			setTitleImage(InterpreterImages.getImageDescriptor(
					IInterpreterConstants.INTERPRETER_VIEW_DEFAULT_ICON).createImage());
		}
		if (previousImage != null) {
			previousImage.dispose();
		}
		interpreterForm.setImage(getTitleImage());

		if (expressionSection != null) {
			Composite expressionSectionBody = (Composite)expressionSection.getClient();
			expressionViewer.getControl().dispose();

			expressionViewer = createExpressionViewer(expressionSectionBody);
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
		// re-fill the sections' toolbars
		populateExpressionSectionToolbar(expressionSection);
		populateResultSectionToolbar(resultSection);
		// Change the state of the link with editor action
		boolean linkEnabledForLanguage = getCurrentLanguageInterpreter().acceptLinkWithEditor();
		linkWithEditorAction.setEnabled(linkEnabledForLanguage);
		if (linkEnabledForLanguage && linkWithEditor) {
			// Force the activation of the link
			linkWithEditor = false;
		}
		toggleLinkWithEditor();
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
			for (Object child : (Collection<?>)evaluationResult) {
				if (child != null) {
					input.add(child);
				}
			}
		} else if (evaluationResult != null) {
			input.add(evaluationResult);
		}
		resultViewer.setInput(input);
	}

	/**
	 * Sets up the content assist action for the given source viewer.
	 * 
	 * @param viewer
	 *            The viewer we need content assist on.
	 */
	protected final void setUpContentAssist(final IInterpreterSourceViewer viewer) {
		IAction contentAssistAction = new Action() {
			@Override
			public void run() {
				viewer.showContentAssist(getInterpreterContext());
			}
		};
		IHandler contentAssistHandler = new ActionHandler(contentAssistAction);

		IHandlerService service = (IHandlerService)getSite().getService(IHandlerService.class);
		activationTokenContentAssist = service.activateHandler(
				ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS, contentAssistHandler);
	}

	/**
	 * Sets up the real-time compilation action on the given viewer. The real-time thread specifically needs
	 * to be told whenever the expression is dirty as well as when the timer should be reset (in order not to
	 * compile when the user is entering the expression).
	 * 
	 * @param viewer
	 *            The viewer for which to set up the real-time compilation thread.
	 */
	protected void setUpRealTimeCompilation(SourceViewer viewer) {
		viewer.addTextListener(new ITextListener() {
			public void textChanged(TextEvent event) {
				if (realTimeThread != null) {
					realTimeThread.reset();
					realTimeThread.setDirty();
				}
			}
		});
	}

	/**
	 * Sets up the drag and drop support for the result viewer.
	 * 
	 * @param viewer
	 *            The result viewer.
	 */
	protected void setUpResultDragSupport(TreeViewer viewer) {
		int operations = DND.DROP_COPY | DND.DROP_LINK | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] {LocalTransfer.getInstance(), };

		viewer.addDragSupport(operations, transfers, new ResultDragListener(viewer));
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
	 * Creates the right-click menu that will be displayed for the expression viewer.
	 * 
	 * @param viewer
	 *            The expression viewer.
	 */
	private void createExpressionMenu(SourceViewer viewer) {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(createExpressionMenuListener(viewer));
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	/**
	 * Creates the right-click menu that will be displayed for the result viewer.
	 * 
	 * @param viewer
	 *            The result viewer.
	 */
	private void createResultMenu(Viewer viewer) {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(createResultMenuListener(viewer));
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	/**
	 * Creates the right-click menu that will be displayed for the variable viewer.
	 * 
	 * @param viewer
	 *            The variable viewer.
	 */
	private void createVariableMenu(TreeViewer viewer) {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(createVariableMenuListener(viewer));
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
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
	 * Sets up the default text actions (undo, redo) on the given source viewer.
	 * 
	 * @param viewer
	 *            The viewer on which to activate default text actions.
	 */
	private final void setUpDefaultTextAction(final SourceViewer viewer) {
		IAction redoAction = new Action() {
			@Override
			public void run() {
				viewer.doOperation(ITextOperationTarget.REDO);
			}
		};
		IHandler redoHandler = new ActionHandler(redoAction);

		IAction undoAction = new Action() {
			@Override
			public void run() {
				viewer.doOperation(ITextOperationTarget.UNDO);
			}
		};
		IHandler undoHandler = new ActionHandler(undoAction);

		IHandlerService service = (IHandlerService)getSite().getService(IHandlerService.class);
		activationTokenRedo = service.activateHandler(IWorkbenchCommandConstants.EDIT_REDO, redoHandler);
		activationTokenUndo = service.activateHandler(IWorkbenchCommandConstants.EDIT_UNDO, undoHandler);
	}

	/**
	 * This class will be used in order to populate the right-click menu of the Expression viewer.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected class ExpressionMenuListener implements IMenuListener {
		/** The viewer on which this menu listener operates. */
		private final SourceViewer sourceViewer;

		/**
		 * Creates this menu listener given the viewer on which it operates.
		 * 
		 * @param sourceViewer
		 *            The viewer on which this menu listener operates.
		 */
		public ExpressionMenuListener(SourceViewer sourceViewer) {
			this.sourceViewer = sourceViewer;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
		 */
		public void menuAboutToShow(IMenuManager manager) {
			manager.add(new EvaluateAction(InterpreterView.this));
			manager.add(new ClearExpressionViewerAction(sourceViewer));
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}
	}

	/**
	 * This listener will be used when the "link with editor" action is enabled in order to notify the current
	 * language interpreter that an editor has been given focus or changed input.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected class InterpreterPartListener implements IPartListener2 {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partActivated(IWorkbenchPartReference partRef) {
			if (partRef instanceof IEditorReference) {
				editorActivated(((IEditorReference)partRef).getEditor(true));
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			// No need to react to this event
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partClosed(IWorkbenchPartReference partRef) {
			if (partRef instanceof IEditorReference && partRef.getPage() != null
					&& partRef.getPage().getEditorReferences().length == 0) {
				editorActivated(null);
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partDeactivated(IWorkbenchPartReference partRef) {
			// No need to react to this event
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partHidden(IWorkbenchPartReference partRef) {
			// No need to react to this event
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partInputChanged(IWorkbenchPartReference partRef) {
			if (partRef instanceof IEditorReference && partRef.getPage() != null
					&& partRef.getPage().getActivePartReference() == partRef) {
				editorActivated(((IEditorReference)partRef).getEditor(true));
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partOpened(IWorkbenchPartReference partRef) {
			// No need to react to this event
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.IWorkbenchPartReference)
		 */
		public void partVisible(IWorkbenchPartReference partRef) {
			// No need to react to this event
		}
	}

	/**
	 * This class will be used in order to populate the right-click menu of the result viewer.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected class ResultMenuListener implements IMenuListener {
		/** The viewer on which this menu listener operates. */
		private Viewer resultViewer;

		/**
		 * Creates this menu listener given the viewer on which it operates.
		 * 
		 * @param resultViewer
		 *            The viewer on which this menu listener operates.
		 */
		public ResultMenuListener(Viewer resultViewer) {
			this.resultViewer = resultViewer;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
		 */
		public void menuAboutToShow(IMenuManager manager) {
			manager.add(new ClearResultViewerAction(resultViewer));
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}
	}

	/**
	 * This class will be used in order to populate the right-click menu of the Variable viewer.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected class VariableMenuListener implements IMenuListener {
		/** The viewer on which this menu listener operates. */
		private TreeViewer variableViewer;

		/**
		 * Creates this menu listener given the viewer on which it operates.
		 * 
		 * @param variableViewer
		 *            The viewer on which this menu listener operates.
		 */
		public VariableMenuListener(TreeViewer variableViewer) {
			this.variableViewer = variableViewer;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
		 */
		public void menuAboutToShow(IMenuManager manager) {
			manager.add(new NewVariableAction(variableViewer));
			final Variable variable = getCurrentVariable();
			if (variable != null) {
				final IMenuManager submenu = new MenuManager(
						InterpreterMessages.getString("interpreter.action.newvalue.submenu.name")); //$NON-NLS-1$
				submenu.add(new NewEObjectValueAction(variableViewer, variable));
				submenu.add(new NewStringValueAction(variableViewer, variable));
				submenu.add(new NewIntegerValueAction(variableViewer, variable));
				submenu.add(new NewFloatValueAction(variableViewer, variable));
				submenu.add(new NewBooleanValueAction(variableViewer, variable));
				manager.add(submenu);
			}
			manager.add(new ClearVariableViewerAction(variableViewer));
			manager.add(new Separator());
			manager.add(new DeleteVariableOrValueAction(variableViewer));
			manager.add(new RenameVariableAction(variableViewer));
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}

		/**
		 * Returns the first of the currently selected Variable(s).
		 * 
		 * @return The first of the currently selected Variable(s)
		 */
		private Variable getCurrentVariable() {
			if (variableViewer == null || variableViewer.getTree() == null
					|| variableViewer.getTree().isDisposed()) {
				return null;
			}
			Tree tree = variableViewer.getTree();

			TreeItem[] selectedItems = tree.getSelection();
			Variable selectedVariable = null;
			if (selectedItems != null && selectedItems.length > 0) {
				for (int i = 0; i < selectedItems.length && selectedVariable == null; i++) {
					TreeItem item = selectedItems[i];
					if (item.getData() instanceof Variable) {
						selectedVariable = (Variable)item.getData();
					}
				}
				for (int i = 0; i < selectedItems.length && selectedVariable == null; i++) {
					TreeItem item = selectedItems[i].getParentItem();
					while (item != null && selectedVariable == null) {
						if (item.getData() instanceof Variable) {
							selectedVariable = (Variable)item.getData();
						}
						item = item.getParentItem();
					}
				}
			}
			return selectedVariable;
		}
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
				String message = e.getMessage();
				if (e.getCause() != null) {
					message = e.getCause().getMessage();
				}
				final IStatus status = new Status(IStatus.ERROR, InterpreterPlugin.PLUGIN_ID, message);
				final CompilationResult result = new CompilationResult(status);
				setCompilationResult(result);
			}
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
					final EObject nextEObject;
					if (next instanceof EObject) {
						nextEObject = (EObject)next;
					} else if (next instanceof IAdaptable) {
						nextEObject = (EObject)((IAdaptable)next).getAdapter(EObject.class);
					} else {
						nextEObject = (EObject)Platform.getAdapterManager().getAdapter(next, EObject.class);
					}
					if (nextEObject != null) {
						// At least one of the selected objects is an EObject, clear current selection
						if (!cleared) {
							clearSelection();
							cleared = true;
						}
						addToSelection(nextEObject);
					}
				}
				// If the selection changed somehow, relaunch the real-time evaluation
				if (cleared && realTimeThread != null) {
					realTimeThread.setDirty();
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
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						getForm().setBusy(true);
					}
				});
				// Cannot do anything before the current compilation thread stops.
				if (compilationThread != null) {
					compilationThread.join();
				}

				Callable<EvaluationResult> evaluationCallable = getCurrentLanguageInterpreter()
						.getEvaluationTask(new EvaluationContext(interpreterContext, compilationResult));
				evaluationTask = evaluationPool.submit(evaluationCallable);

				final EvaluationResult result = evaluationTask.get();

				if (result != null) {
					Display.getDefault().asyncExec(new Runnable() {
						/**
						 * {@inheritDoc}
						 * 
						 * @see java.lang.Runnable#run()
						 */
						public void run() {
							if (result.getProblems() != null) {
								addStatusMessages(result.getProblems(), EVALUATION_MESSAGE_PREFIX);
							}
							// whether there were problems or not, try and update the result viewer.
							setEvaluationResult(result);
						}
					});
				}
			} catch (InterruptedException e) {
				// Thread is expected to be cancelled if another is started
			} catch (ExecutionException e) {
				String message = e.getMessage();
				if (e.getCause() != null) {
					message = e.getCause().getMessage();
				}
				final IStatus status = new Status(IStatus.ERROR, InterpreterPlugin.PLUGIN_ID, message);
				final EvaluationResult result = new EvaluationResult(status);

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
			} finally {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						getForm().setBusy(false);
					}
				});
			}
		}
	}

	/**
	 * This daemon thread will be launched whenever the "real-time" toggle is activated, and will only be
	 * stopped when the view is disposed or the "real-time" toggle is disabled.
	 * <p>
	 * This Thread will be constantly reset on modifications of the expression viewer, and will only really
	 * start its work if the expression is left untouched for a given count of seconds.
	 * </p>
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class RealTimeThread extends Thread {
		/** Time to wait before launching the evaluation (0.5 second by default). */
		private static final int DELAY = 500;

		/** This will be set to <code>true</code> whenever we need to recompile the expression. */
		private boolean dirty = false;

		/** The lock we'll acquire for this thread's work. */
		private final Object lock = new Object();

		/** This will be set to <code>true</code> whenever we should reset this thread's timer. */
		private boolean reset;

		/**
		 * Instantiates the real-time evaluation thread.
		 */
		public RealTimeThread() {
			super("InterpreterRealTimeThread"); //$NON-NLS-1$
			setPriority(Thread.MIN_PRIORITY);
			setDaemon(true);
		}

		/**
		 * Resets this thread's timer.
		 */
		public void reset() {
			synchronized(this) {
				reset = true;
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (!Thread.interrupted()) {
				synchronized(lock) {
					try {
						lock.wait(DELAY);
					} catch (InterruptedException e) {
						// This is expected
					}
				}

				synchronized(this) {
					if (reset) {
						reset = false;
						// If a reset has been asked for, stop this iteration
						continue;
					}
					if (dirty) {
						dirty = false;
					} else {
						// The expression does not need to be recompiled
						continue;
					}
				}

				Display.getDefault().asyncExec(new Runnable() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see java.lang.Runnable#run()
					 */
					public void run() {
						compileExpression();
						evaluate();
					}
				});
			}
		}

		/**
		 * Sets the "dirty" state of this thread to indicate the expression needs to be recompiled.
		 */
		public void setDirty() {
			synchronized(this) {
				dirty = true;
			}
		}
	}
}
