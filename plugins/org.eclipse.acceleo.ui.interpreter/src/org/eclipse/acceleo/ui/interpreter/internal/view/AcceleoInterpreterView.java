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
package org.eclipse.acceleo.ui.interpreter.internal.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

/**
 * The Actual "Interpreter" view that will be displayed in the Eclipse workbench.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoInterpreterView extends ViewPart {
	/**
	 * Keeps a reference towards the text area in which we'll display the expression that will have to be
	 * evaluated.
	 */
	protected AcceleoSourceViewer expressionViewer;

	/** Context activation token. This will be needed to deactivate it. */
	private IContextActivation activation;

	/** Content assist activation token. This will be needed to deactivate our handler. */
	private IHandlerActivation contentAssistActionActivation;

	/** Keeps a reference towards the text area in which we'll display the result of the evaluation. */
	private TextViewer resultViewer;

	/** This will hold the current selection of EObjects. */
	private List<EObject> selectedEObjects;

	/** This will be used to listen to EObject selection within the workbench. */
	private ISelectionListener eobjectSelectionListener;

	/** If any, this will hold the current selection's root EObject. */
	private EObject selectionRoot;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite rootContainer = new Composite(parent, SWT.NULL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
		gridData.verticalIndent = 1;
		rootContainer.setLayoutData(gridData);
		GridLayout rootContainerLayout = new GridLayout();
		rootContainer.setLayout(rootContainerLayout);
		Composite composite = new SashForm(rootContainer, SWT.VERTICAL);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalIndent = 1;
		composite.setLayoutData(gridData);
		expressionViewer = new AcceleoSourceViewer(this, composite, null, SWT.V_SCROLL | SWT.H_SCROLL);
		resultViewer = new TextViewer(composite, SWT.READ_ONLY);

		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
		activation = contextService.activateContext("org.eclipse.acceleo.ui.interpreter.interpreterview"); //$NON-NLS-1$

		configureSourceViewer(expressionViewer);
	}

	/**
	 * Takes care of setting up our source viewer for Acceleo (content assist, syntax highlighting, ...).
	 * 
	 * @param viewer
	 *            The source viewer we are to configure.
	 */
	private void configureSourceViewer(final AcceleoSourceViewer viewer) {
		Document document = new Document();
		viewer.setDocument(document);

		// Creates the source content
		viewer.initializeContent();

		// Setup syntax highlighting and partitioning
		IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoPartitionScanner(),
				AcceleoPartitionScanner.LEGAL_CONTENT_TYPES);
		document.setDocumentPartitioner(partitioner);
		partitioner.connect(document);

		// Setup source content updating
		document.addPositionUpdater(new IPositionUpdater() {
			public void update(DocumentEvent event) {
				viewer.handlePositionUpdate(event.getOffset(), event.getOffset() + event.getLength(),
						event.getText());
			}
		});

		viewer.configure(new AcceleoInterpreterConfiguration(AcceleoUIActivator.getDefault()
				.getPreferenceStore()));
		activateContentAssistAction(viewer);
	}

	/**
	 * This will display the given String in the result viewer.
	 * 
	 * @param result
	 *            The String that is to be displayed.
	 */
	public void displayResult(String result) {
		resultViewer.getTextWidget().setText(result);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
		contextService.deactivateContext(activation);

		IHandlerService handlerService = (IHandlerService)getSite().getService(IHandlerService.class);
		handlerService.deactivateHandler(contentAssistActionActivation);

		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null && workbenchWindow.getActivePage() != null
				&& eobjectSelectionListener != null) {
			workbenchWindow.getActivePage().removeSelectionListener(eobjectSelectionListener);
		}

		clearSelection();

		super.dispose();
	}

	/**
	 * Returns the currently selected expression if any, or the ModuleElement in which the cursor is located
	 * otherwise.
	 * 
	 * @return The currently selected expression or the template/Query at the selected offset.
	 */
	public OCLExpression getExpression() {
		ASTNode selectedNode;
		ISelection selection = expressionViewer.getSelection();
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection)selection;
			expressionViewer.updateCST();

			int startOffset = textSelection.getOffset() + expressionViewer.getGap();
			int endOffset = startOffset + textSelection.getLength();
			selectedNode = expressionViewer.getContent().getResolvedASTNode(startOffset, endOffset);

			if (textSelection.getLength() == 0) {
				while (selectedNode != null && !(selectedNode instanceof ModuleElement)) {
					selectedNode = (ASTNode)selectedNode.eContainer();
				}
			}
		} else {
			selectedNode = expressionViewer.getContent().getAST().getOwnedModuleElement().get(0);
		}
		if (selectedNode instanceof OCLExpression) {
			return (OCLExpression)selectedNode;
		}
		return null;
	}

	/**
	 * Returns the EObjects on which the expression should be compiled and run.
	 * 
	 * @return The current target EObjects.
	 */
	public List<EObject> getTargetObjects() {
		return selectedEObjects;
	}

	/**
	 * Returns the root of the first selected element.
	 * 
	 * @return Root of the first selected element.
	 */
	public EObject getTargetRoot() {
		return selectionRoot;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
	 */
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		registerWorkbenchPageSelectionListener(site.getPage());
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
	 * Creates a new list for the selection if needed, and adds the given object to it.
	 * 
	 * @param object
	 *            The element that is to be added to the current selection.
	 */
	protected void addToSelection(EObject object) {
		if (selectedEObjects == null) {
			// Assumes the "usual" selection is always 1 element long
			selectedEObjects = new ArrayList<EObject>(1);
			selectionRoot = EcoreUtil.getRootContainer(object);
		}
		selectedEObjects.add(object);
		expressionViewer.handleSelectionUpdate();
	}

	/**
	 * If we currently have EObjects selected, this will clear the whole list.
	 */
	protected void clearSelection() {
		if (selectedEObjects != null) {
			selectedEObjects.clear();
			selectedEObjects = null;
			selectionRoot = null;
		}
	}

	/**
	 * Create a custom action handler for the content assistant : the default one would not be activated on
	 * ctrl+space.
	 * 
	 * @param viewer
	 *            The source viewer we are to configure.
	 */
	private void activateContentAssistAction(final AcceleoSourceViewer viewer) {
		final int actionID = ISourceViewer.CONTENTASSIST_PROPOSALS;
		final String actionDefinitionID = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;

		IAction action = new Action() {
			@Override
			public void run() {
				viewer.doOperation(actionID);
			}
		};
		IHandler handler = new ActionHandler(action);

		IHandlerService service = (IHandlerService)getSite().getService(IHandlerService.class);
		contentAssistActionActivation = service.activateHandler(actionDefinitionID, handler);
	}

	/**
	 * Creates an {@link EObjectSelectionListener} and registers it against the given workbench page.
	 * 
	 * @param page
	 *            The workbench page on which we need to listen to selection events.
	 */
	private void registerWorkbenchPageSelectionListener(IWorkbenchPage page) {
		if (eobjectSelectionListener == null) {
			eobjectSelectionListener = new EObjectSelectionListener();
		}
		page.addSelectionListener(eobjectSelectionListener);
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
	 * This implementation of an Acceleo configuration will create its completion processor with our source
	 * viewer.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class AcceleoInterpreterConfiguration extends AcceleoConfiguration {
		/**
		 * Simply delegates to the super constructor.
		 * 
		 * @param preferenceStore
		 *            The preference store, can be read-only.
		 */
		public AcceleoInterpreterConfiguration(IPreferenceStore preferenceStore) {
			super(preferenceStore);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration#createContentAssistProcessor(org.eclipse.jface.text.source.ISourceViewer)
		 */
		@Override
		public IContentAssistProcessor createContentAssistProcessor(ISourceViewer sourceViewer) {
			if (sourceViewer instanceof AcceleoSourceViewer) {
				return new AcceleoInterpreterCompletionProcessor(
						((AcceleoSourceViewer)sourceViewer).getContent());
			}
			return super.createContentAssistProcessor(sourceViewer);
		}
	}

	/**
	 * This implementation of a completion processor will allow us to maintain a gap between the text and the
	 * CST.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class AcceleoInterpreterCompletionProcessor extends AcceleoCompletionProcessor {
		/**
		 * Delegates to the super constructor.
		 * 
		 * @param content
		 *            The Acceleo content for which completion should be displayed.
		 */
		public AcceleoInterpreterCompletionProcessor(AcceleoSourceContent content) {
			super(content);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#setCompletionOffset(int)
		 */
		@Override
		protected void setCompletionOffset(int newOffset) {
			if (textViewer == null || !(content instanceof AcceleoInterpreterSourceContent)) {
				super.setCompletionOffset(newOffset);
			} else {
				super.setCompletionOffset(newOffset + ((AcceleoInterpreterSourceContent)content).getGap());
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#setText(org.eclipse.jface.text.ITextViewer)
		 */
		@Override
		protected void setText(ITextViewer viewer) {
			if (viewer == null || !(viewer instanceof AcceleoSourceViewer)) {
				super.setText(viewer);
			} else {
				text = ((AcceleoSourceViewer)viewer).getFullExpression();
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#createCompletionProposal(java.lang.String,
		 *      int, int, int, org.eclipse.swt.graphics.Image, java.lang.String,
		 *      org.eclipse.jface.text.contentassist.IContextInformation, java.lang.String)
		 */
		/*
		 * Deactivating checkstyle : we're overriding a superclass method with more than 7 parameters.
		 * CHECKSTYLE:OFF
		 */
		@Override
		protected ICompletionProposal createCompletionProposal(String replacementString,
				int replacementOffset, int replacementLength, int cursorPosition, Image image,
				String displayString, IContextInformation contextInformation, String additionalProposalInfo) {
			// CHECKSTYLE:ON
			int actualOffset = replacementOffset;
			if (content instanceof AcceleoInterpreterSourceContent) {
				actualOffset -= ((AcceleoInterpreterSourceContent)content).getGap();
			}
			return super.createCompletionProposal(replacementString, actualOffset, replacementLength,
					cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#createTemplateProposal(java.lang.String,
		 *      int, int, int, org.eclipse.swt.graphics.Image, java.lang.String,
		 *      org.eclipse.jface.text.contentassist.IContextInformation, java.lang.String)
		 */
		/*
		 * Deactivating checkstyle : we're overriding a superclass method with more than 7 parameters.
		 * CHECKSTYLE:OFF
		 */
		@Override
		protected ICompletionProposal createTemplateProposal(String replacementString, int replacementOffset,
				int replacementLength, int cursorPosition, Image image, String displayString,
				IContextInformation contextInformation, String additionalProposalInfo) {
			// CHECKSTYLE:ON
			if (textViewer != null && textViewer.getDocument() != null
					&& content instanceof AcceleoInterpreterSourceContent) {
				int actualOffset = replacementOffset - ((AcceleoInterpreterSourceContent)content).getGap();
				return super.createTemplateProposal(replacementString, actualOffset, replacementLength,
						cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
			}
			return super.createTemplateProposal(replacementString, replacementOffset, replacementLength,
					cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
		}
	}
}
