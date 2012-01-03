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
package org.eclipse.acceleo.internal.ide.ui.views.proposals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionTemplateProposal;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.compare.Splitter;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.ocl.ecore.EcorePackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * A view to update the settings of the current 'pattern' completion proposal. It displays the proposal
 * Patterns (proposals extension point) and the Types available in the current template, so it listens when an
 * Acceleo Editor bring to top.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ProposalsBrowser extends ViewPart implements IEditingDomainProvider {

	/**
	 * The identifier of the view.
	 */
	public static final String PROPOSALS_BROWSER_VIEW_ID = "org.eclipse.acceleo.ide.ui.views.proposals.ProposalsBrowser"; //$NON-NLS-1$

	/**
	 * The editing domain.
	 */
	private AdapterFactoryEditingDomain editingDomain;

	/**
	 * The adapter factory.
	 */
	private ComposedAdapterFactory adapterFactory;

	/**
	 * The Types part.
	 */
	private CheckboxTreeViewer typesViewer;

	/**
	 * The Patterns part.
	 */
	private CheckboxTreeViewer patternsViewer;

	/**
	 * Interface for listening to workbench part lifecycle events. It is used to listen when an Acceleo Editor
	 * brought to top.
	 */
	private IPartListener partListener;

	/**
	 * Gets the sub types of an EClass.
	 */
	private Map<EClass, List<EClass>> mapSubTypes = new HashMap<EClass, List<EClass>>();

	/**
	 * Gets all the handlers created for an EClass.
	 */
	private Map<EClass, List<EClassHandler>> mapEClassHandlers = new HashMap<EClass, List<EClassHandler>>();

	/**
	 * The beginning time of the current task, to check if it is a long time operation.
	 */
	private long beginningTime;

	/**
	 * Constructor.
	 */
	public ProposalsBrowser() {
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new EcoreItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		adapterFactory = new ComposedAdapterFactory(factories);
		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack,
				new HashMap<Resource, Boolean>());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite rootContainer = new Composite(parent, SWT.NULL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalIndent = 1;
		rootContainer.setLayoutData(gridData);
		GridLayout rootContainerLayout = new GridLayout();
		rootContainer.setLayout(rootContainerLayout);
		Composite composite = new Splitter(rootContainer, SWT.VERTICAL);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.numColumns = 1;
		composite.setLayout(layout);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalIndent = 1;
		composite.setLayoutData(gridData);
		createPatternsViewer(composite);
		createTypesViewer(composite);
		if (getSite() != null && getSite().getPage() != null && partListener == null) {
			partListener = createPartListener();
			getSite().getPage().addPartListener(partListener);

		}
	}

	/**
	 * It creates the object that will listen when an Acceleo Editor bring to top.
	 * 
	 * @return the listener
	 */
	private IPartListener createPartListener() {
		return new IPartListener() {
			public void partOpened(IWorkbenchPart part) {
				if (part instanceof AcceleoEditor) {
					AcceleoEditor editor = (AcceleoEditor)part;
					updateViewTypes(editor);
				}
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partClosed(IWorkbenchPart part) {
			}

			public void partActivated(IWorkbenchPart part) {
			}

			public void partBroughtToTop(IWorkbenchPart part) {
				if (part instanceof AcceleoEditor) {
					AcceleoEditor editor = (AcceleoEditor)part;
					updateViewTypes(editor);
				}
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (partListener != null && getSite() != null && getSite().getPage() != null) {
			getSite().getPage().removePartListener(partListener);
			partListener = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	/**
	 * Creates the widget for the Types part in the given composite.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createTypesViewer(Composite parent) {
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 100;
		typesViewer = new CheckboxTreeViewer(parent, SWT.BORDER);
		typesViewer.getTree().setLayoutData(data);
		typesViewer.getTree().setFont(parent.getFont());
		typesViewer.setContentProvider(new ProposalsBrowserTypesProvider(this, adapterFactory));
		typesViewer.setLabelProvider(new ProposalsBrowserTypesLabelProvider(adapterFactory));

		typesViewer.addCheckStateListener(new ICheckStateListener() {
			private boolean active;

			public void checkStateChanged(CheckStateChangedEvent event) {
				if (!active) {
					active = true;
					beginningTime = System.currentTimeMillis();
					try {
						if (event.getElement() instanceof EClassHandler) {
							EClassHandler eClassHandler = (EClassHandler)event.getElement();
							checkStateType(eClassHandler, event.getChecked());
						}
					} finally {
						active = false;
						beginningTime = 0;
					}
				}
			}
		});

		IEditorPart part = getSite().getPage().getActiveEditor();
		if (part instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)part;
			updateViewTypes(editor);
		} else {
			typesViewer.setInput(null);
		}
	}

	/**
	 * Sets the checked state of the handlers of the given EClass and its sub types. It checks if it is a long
	 * time operation.
	 * 
	 * @param eClassHandler
	 *            is the element to check
	 * @param state
	 *            is the checked state
	 */
	private synchronized void checkStateType(EClassHandler eClassHandler, boolean state) {
		Object[] checkedElements = typesViewer.getCheckedElements();
		try {
			List<EClassHandler> eClassAllHandlers = mapEClassHandlers.get(eClassHandler.getEClass());
			if (eClassAllHandlers != null) {
				for (EClassHandler eClassAllHandler : eClassAllHandlers) {
					typesViewer.expandToLevel(eClassAllHandler, 1);
					typesViewer.setChecked(eClassAllHandler, state);
					checkStateSubTypes(eClassAllHandler, state);
				}
			}
		} catch (CancellationException e) {
			typesViewer.setCheckedElements(checkedElements);
			if (state) {
				typesViewer.setChecked(eClassHandler, !state);
			}
		}
	}

	/**
	 * Sets the checked state of the sub types of the given EClass. It checks if it is a long time operation.
	 * 
	 * @param eClassHandler
	 *            is the element to browse
	 * @param state
	 *            is the checked state of the sub types of the given EClass
	 */
	private void checkStateSubTypes(EClassHandler eClassHandler, boolean state) {
		final long longTimeOperation = 5000;
		List<EClass> subTypes = mapSubTypes.get(eClassHandler.getEClass());
		if (subTypes == null || subTypes.size() == 0) {
			return;
		}
		typesViewer.expandToLevel(eClassHandler, 1);
		for (EClass eSubType : subTypes) {
			List<EClassHandler> eSubTypeHandlers = mapEClassHandlers.get(eSubType);
			if (eSubTypeHandlers != null) {
				for (EClassHandler eSubTypeHandler : eSubTypeHandlers) {
					if (beginningTime != -1 && System.currentTimeMillis() - beginningTime > longTimeOperation) {
						if (MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getShell(), AcceleoUIMessages
								.getString("ProposalsBrowser.LongTimeOperation"), AcceleoUIMessages //$NON-NLS-1$
								.getString("ProposalsBrowser.LongTimeOperationCancel"))) { //$NON-NLS-1$ 
							throw new CancellationException();
						}
						beginningTime = -1;
					}
					typesViewer.setChecked(eSubTypeHandler, state);
					checkStateSubTypes(eSubTypeHandler, state);
				}
			}
		}
	}

	/**
	 * Updates the types in the view by getting the metamodel URIs of the current template. The current
	 * template is in the given editor.
	 * 
	 * @param editor
	 *            is the editor that contains the current template to display in the Types view
	 */
	private synchronized void updateViewTypes(AcceleoEditor editor) {
		List<EPackage> ePackages = new ArrayList<EPackage>();
		org.eclipse.acceleo.parser.cst.Module cst = editor.getContent().getCST();
		if (cst != null) {
			for (TypedModel typedModel : cst.getInput()) {
				for (EPackage ePackage : typedModel.getTakesTypesFrom()) {
					if (!ePackages.contains(ePackage)) {
						ePackages.add(ePackage);
					}
				}
			}
		}
		mapSubTypes.clear();
		mapEClassHandlers.clear();
		computeSubTypes(mapSubTypes, ePackages);
		List<EClassHandler> input = new ArrayList<EClassHandler>();
		for (EClass eClass : mapSubTypes.keySet()) {
			if (eClass.getESuperTypes().size() == 0) {
				EClassHandler handler = new EClassHandler(eClass);
				input.add(handler);
				List<EClassHandler> handlers = mapEClassHandlers.get(eClass);
				if (handlers == null) {
					handlers = new ArrayList<EClassHandler>();
					mapEClassHandlers.put(eClass, handlers);
				}
				handlers.add(handler);
			}
		}
		typesViewer.setInput(input);
	}

	/**
	 * Creates the sub types handlers for the given EClass handler. The sub types items are displayed in the
	 * Types view as the children of the given EClass handler.
	 * 
	 * @param eClassHandler
	 *            is the super type EClass handler
	 * @return the sub types handlers
	 */
	protected EClassHandler[] createSubTypeHandlers(EClassHandler eClassHandler) {
		List<EClassHandler> result = new ArrayList<EClassHandler>();
		List<EClass> subTypes = mapSubTypes.get(eClassHandler.getEClass());
		if (subTypes != null) {
			for (EClass subType : subTypes) {
				EClassHandler handler = new EClassHandler(subType);
				result.add(handler);
				List<EClassHandler> handlers = mapEClassHandlers.get(subType);
				if (handlers == null) {
					handlers = new ArrayList<EClassHandler>();
					mapEClassHandlers.put(subType, handlers);
				}
				handlers.add(handler);
			}
		}
		return result.toArray(new EClassHandler[result.size()]);
	}

	/**
	 * Indicates if there is at least one sub type to display in the children list of the given EClass
	 * handler.
	 * 
	 * @param eClassHandler
	 *            is the super type EClass handler
	 * @return true if there is at least one sub type to display
	 */
	protected boolean hasSubType(EClassHandler eClassHandler) {
		List<EClass> subTypes = mapSubTypes.get(eClassHandler.getEClass());
		return subTypes != null && subTypes.size() > 0;
	}

	/**
	 * Creates the sub types map for all the classifiers of the given packages and their sub packages.
	 * 
	 * @param subTypesMap
	 *            is the map to create, it's an input/output parameter
	 * @param ePackages
	 *            are the packages to browse
	 */
	private void computeSubTypes(Map<EClass, List<EClass>> subTypesMap, List<EPackage> ePackages) {
		for (EPackage ePackage : ePackages) {
			for (EClassifier eChildClassifier : ePackage.getEClassifiers()) {
				if (eChildClassifier instanceof EClass) {
					EClass eClass = (EClass)eChildClassifier;
					computeSubTypesOfAllSuperTypes(subTypesMap, eClass);
					if (!subTypesMap.containsKey(eClass)) {
						subTypesMap.put(eClass, new ArrayList<EClass>());
					}
				}
			}
			computeSubTypes(subTypesMap, ePackage.getESubpackages());
		}

	}

	/**
	 * Adds information in the sub types map for all the super types of the given type.
	 * 
	 * @param subTypesMap
	 *            is the map to create, it's an input/output parameter
	 * @param eClass
	 *            is the type
	 */
	private void computeSubTypesOfAllSuperTypes(Map<EClass, List<EClass>> subTypesMap, EClass eClass) {
		for (EClass eSuperType : eClass.getESuperTypes()) {
			List<EClass> subTypes = subTypesMap.get(eSuperType);
			if (subTypes == null) {
				subTypes = new ArrayList<EClass>();
				subTypesMap.put(eSuperType, subTypes);
			}
			if (!subTypes.contains(eClass)) {
				subTypes.add(eClass);
			}
			computeSubTypesOfAllSuperTypes(subTypesMap, eSuperType);
		}
	}

	/**
	 * Creates the widget for the Patterns part in the given composite.
	 * 
	 * @param parent
	 *            is the parent composite
	 */
	private void createPatternsViewer(Composite parent) {
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		final int heightHint = 50;
		data.heightHint = heightHint;
		patternsViewer = new CheckboxTreeViewer(parent, SWT.BORDER);
		patternsViewer.getTree().setLayoutData(data);
		patternsViewer.getTree().setFont(parent.getFont());
		patternsViewer.setContentProvider(new ProposalsBrowserPatternsProvider(adapterFactory));
		patternsViewer.setLabelProvider(new ProposalsBrowserPatternsLabelProvider(adapterFactory));
		patternsViewer.setInput(AcceleoPatternProposalsUtils.getPatternProposals());
	}

	/**
	 * Gets the selected template completion proposals. It means a replacement string with dynamic variables
	 * 
	 * @param document
	 *            is the document
	 * @param text
	 *            is the text of the document
	 * @param offset
	 *            is the current offset
	 * @param cstNode
	 *            is the current CST node
	 * @return the list of selected proposals, it can be empty
	 */
	public List<ICompletionProposal> getPatternCompletionProposals(IDocument document, String text,
			int offset, CSTNode cstNode) {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		Object[] patternCheckedElements = patternsViewer.getCheckedElements();
		for (int i = 0; i < patternCheckedElements.length; i++) {
			Object patternCheckedElement = patternCheckedElements[i];
			if (patternCheckedElement instanceof IAcceleoPatternProposal
					&& ((IAcceleoPatternProposal)patternCheckedElement).isEnabled(text, offset, cstNode)) {
				IAcceleoPatternProposal pattern = (IAcceleoPatternProposal)patternCheckedElement;
				int index = offset;
				while (index > 0 && Character.isJavaIdentifierPart(text.charAt(index - 1))) {
					index--;
				}
				if (index > 0 && (text.charAt(index - 1) == '[' || text.charAt(index - 1) == ']')) {
					index--;
				}
				String start = text.substring(index, offset);
				StringBuffer tabBuffer = new StringBuffer();
				while (index > 0 && Character.isWhitespace(text.charAt(index - 1))
						&& text.charAt(index - 1) != '\n') {
					tabBuffer.insert(0, text.charAt(index - 1));
					index--;
				}
				String indentTab = tabBuffer.toString();
				List<EClass> types = new ArrayList<EClass>();
				Object[] typeCheckedElements = typesViewer.getCheckedElements();
				for (int j = 0; j < typeCheckedElements.length; j++) {
					Object typeCheckedElement = typeCheckedElements[j];
					if (typeCheckedElement instanceof EClassHandler) {
						EClass eClass = ((EClassHandler)typeCheckedElement).getEClass();
						if (!types.contains(eClass)) {
							types.add(eClass);
						}
					}
				}
				if (types.size() == 0) {
					types.add(EcorePackage.eINSTANCE.getInvalidType());
				}
				String newTemplateProposal = pattern.createTemplateProposal(types, indentTab);
				if (newTemplateProposal != null && newTemplateProposal.length() > 0) {
					if (newTemplateProposal.startsWith(start)
							|| (newTemplateProposal.startsWith(IAcceleoConstants.DEFAULT_BEGIN) && newTemplateProposal
									.substring(IAcceleoConstants.DEFAULT_BEGIN.length()).startsWith(start))) {
						Template template = new Template(pattern.getDescription(), pattern.getDescription(),
								AcceleoPartitionScanner.ACCELEO_BLOCK, newTemplateProposal, true);
						TemplateContextType type = new TemplateContextType(
								AcceleoPartitionScanner.ACCELEO_BLOCK, AcceleoPartitionScanner.ACCELEO_BLOCK);
						TemplateContext context = new DocumentTemplateContext(type, document, offset
								- start.length(), start.length());
						Region region = new Region(offset - start.length(), start.length());
						AcceleoCompletionTemplateProposal proposal = new AcceleoCompletionTemplateProposal(
								template, context, region, AcceleoUIActivator.getDefault().getImage(
										"icons/template-editor/completion/ProposalsBrowser.gif")); //$NON-NLS-1$
						proposals.add(proposal);
					}
				}
			}
		}
		return proposals;
	}

}
