/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarker;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchQuery;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.AcceleoOutlinePage;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.QuickOutlineControl;
import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.QuickOutlineInformationProvider;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * The Acceleo template editor (Acceleo editor).
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoEditor extends TextEditor implements IResourceChangeListener {

	/**
	 * The Acceleo editor ID.
	 */
	public static final String ACCELEO_EDITOR_ID = "org.eclipse.acceleo.ide.ui.editors.template.AcceleoEditor"; //$NON-NLS-1$

	/**
	 * Preference key for matching brackets.
	 */
	private static final String MATCHING_BRACKETS = PreferenceConstants.EDITOR_MATCHING_BRACKETS;

	/**
	 * Preference key for matching brackets color.
	 */
	private static final String MATCHING_BRACKETS_COLOR = PreferenceConstants.EDITOR_MATCHING_BRACKETS_COLOR;

	/**
	 * The source content (The semantic content for this editor). It is used to create a CST model and is able
	 * to do an incremental parsing of the text.
	 */
	private AcceleoSourceContent content;

	/**
	 * Color manager for the syntax highlighting of this editor.
	 */
	private ColorManager colorManager;

	/**
	 * Content outline page.
	 */
	private AcceleoOutlinePage contentOutlinePage;

	/**
	 * A listener which is notified when the outline's selection changes.
	 */
	private ISelectionChangedListener selectionChangedListener;

	/**
	 * The editor's blocks matcher.
	 */
	private AcceleoPairMatcher blockMatcher;

	/** Allows us to enable folding support on this editor. */
	private ProjectionSupport projectionSupport;

	/** This will allow us to update the folding structure of the document. */
	private ProjectionAnnotationModel annotationModel;

	/**
	 * Keeps a reference to the object last updated in the outline through a double-click on the editor. This
	 * allows us to ignore the feedback &quot;updateSelection&quot; event from the outline. It identifies an
	 * EObject without keeping the real reference on this EObject.
	 */
	private String updatingOutlineURI = ""; //$NON-NLS-1$

	/**
	 * Keeps a reference to the last selected AST node in the editor through a simple-click on the editor. It
	 * identifies an EObject without keeping the real reference on this EObject.
	 */
	private String offsetASTNodeURI = ""; //$NON-NLS-1$

	/**
	 * The decorate job to highlight all the occurrences of the current selection in the editor.
	 */
	private AcceleoOccurrencesFinderJob occurrencesFinderJob;

	/**
	 * Listener which is notified when the post selection changes on the page. It is used to highlight in the
	 * editor the occurrences of the current selected region.
	 */
	private ISelectionListener findOccurrencesPostSelectionListener;

	/**
	 * Constructor.
	 */
	public AcceleoEditor() {
		super();
		content = new AcceleoSourceContent();
		colorManager = new ColorManager();
		blockMatcher = new AcceleoPairMatcher();
	}

	/**
	 * Gets the source content. It stores the CST model that represents the semantic content of the text.
	 * 
	 * @return the source content
	 */
	public AcceleoSourceContent getContent() {
		return content;
	}

	/**
	 * Gets the color manager. It is often used for syntax highlighting.
	 * 
	 * @return the color manager
	 */
	public ColorManager getColorManager() {
		return colorManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#doSetInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		setSourceViewerConfiguration(createSourceViewerConfiguration());
		setDocumentProvider(createDocumentProvider());
		super.doSetInput(input);
		IDocumentProvider provider = getDocumentProvider();
		if (provider != null) {
			registerAccessibleEcoreFiles();
			IFile file = getFile();
			IDocument document = provider.getDocument(getEditorInput());
			initializeContent(document, file);
		}
	}

	/**
	 * Register the accessible workspace ecore files.
	 * 
	 * @throws CoreException
	 *             when an issue occurs
	 */
	private void registerAccessibleEcoreFiles() throws CoreException {
		IFile file = getFile();
		if (file != null) {
			List<IFile> ecoreFiles = new ArrayList<IFile>();
			AcceleoProject acceleoProject = new AcceleoProject(file.getProject());
			for (IProject project : acceleoProject.getRecursivelyAccessibleProjects()) {
				if (project.isAccessible()) {
					members(ecoreFiles, project, "ecore"); //$NON-NLS-1$
				}
			}
			for (IFile ecoreFile : ecoreFiles) {
				ModelUtils.registerEcorePackages(ecoreFile.getFullPath().toString());
			}
		}
	}

	/**
	 * Returns a list of existing member files (that validate the file extension) in this resource.
	 * 
	 * @param filesOutput
	 *            an output parameter to get all the files
	 * @param container
	 *            is the container to browse
	 * @param extension
	 *            is the extension
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void members(List<IFile> filesOutput, IContainer container, String extension)
			throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile && extension.equals(((IFile)resource).getFileExtension())) {
						filesOutput.add((IFile)resource);
					} else if (resource instanceof IContainer) {
						members(filesOutput, (IContainer)resource, extension);
					}
				}
			}
		}
	}

	/**
	 * Initializes the semantic content of the file, by creating the initial version of the CST model.
	 * 
	 * @param document
	 *            is the document
	 * @param file
	 *            is the file, it can be null if the file isn't in the workspace
	 */
	private void initializeContent(IDocument document, IFile file) {
		if (document != null) {
			try {
				if (file == null || file.getProject().hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
					content.init(new StringBuffer(document.get()), file);
					content.createCST();
				} else {
					MessageDialog.openError(getSite().getShell(), AcceleoUIMessages
							.getString("AcceleoEditor.MissingNatureTitle"), //$NON-NLS-1$
							AcceleoUIMessages.getString("AcceleoEditor.MissingNatureDescription")); //$NON-NLS-1$
					content.init(new StringBuffer(document.get()), file);
					content.createCST();
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#initializeKeyBindingScopes()
	 */
	@Override
	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(new String[] {"org.eclipse.acceleo.ide.ui.editors.template.editor", }); //$NON-NLS-1$
	}

	/**
	 * Creates the source viewer configuration.
	 * 
	 * @return the source viewer configuration
	 */
	protected SourceViewerConfiguration createSourceViewerConfiguration() {
		return new AcceleoConfiguration(this, getPreferenceStore());
	}

	/**
	 * Creates the document provider.
	 * 
	 * @return the document provider
	 */
	protected IDocumentProvider createDocumentProvider() {
		return new AcceleoDocumentProvider(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#dispose()
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		if (selectionChangedListener != null) {
			getContentOutlinePage().removeSelectionChangedListener(selectionChangedListener);
			selectionChangedListener = null;
		}
		if (findOccurrencesPostSelectionListener != null) {
			this.getSite().getPage().removePostSelectionListener(findOccurrencesPostSelectionListener);
			findOccurrencesPostSelectionListener = null;
		}
		if (content != null && content.getFile() != null) {
			try {
				IMarker[] markers = content.getFile().findMarkers(AcceleoMarker.PROBLEM_MARKER, false,
						IResource.DEPTH_INFINITE);
				for (IMarker marker : markers) {
					if (marker.getAttribute(IMarker.TRANSIENT, false)) {
						marker.delete();
					}
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		if (content != null) {
			content.cancelTasks();
		}
		super.dispose();
		colorManager.dispose();
		/*
		 * Dispose the block matcher
		 */
		if (blockMatcher != null) {
			blockMatcher.dispose();
			blockMatcher = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#getAdapter(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
		if (type.equals(IContentOutlinePage.class)) {
			return getContentOutlinePage();
		}
		return super.getAdapter(type);
	}

	/**
	 * Returns the template content outline page. Creates the listener which is notified when the outline's
	 * selection changes.
	 * 
	 * @return the template content outline page
	 */
	protected AcceleoOutlinePage getContentOutlinePage() {
		if (contentOutlinePage == null) {
			contentOutlinePage = createContentOutlinePage();
			selectionChangedListener = createSelectionChangeListener();
			contentOutlinePage.addSelectionChangedListener(selectionChangedListener);
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		}
		return contentOutlinePage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor() != this) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE && getFile() != null
					&& deltaMembers(event.getDelta()).contains(getFile())) {
				try {
					init(getEditorSite(), getEditorInput());
				} catch (PartInitException e) {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
				}
			}
		}
	}

	/**
	 * Gets all the modified files in the resource delta.
	 * 
	 * @param delta
	 *            the resource delta represents changes in the state of a resource tree
	 * @return all the modified files
	 */
	private List<IFile> deltaMembers(IResourceDelta delta) {
		List<IFile> files = new ArrayList<IFile>();
		IResource resource = delta.getResource();
		if (resource instanceof IFile) {
			if (delta.getKind() == IResourceDelta.CHANGED) {
				files.add((IFile)resource);
			}
		}
		IResourceDelta[] children = delta.getAffectedChildren();
		for (int i = 0; i < children.length; i++) {
			files.addAll(deltaMembers(children[i]));
		}
		return files;
	}

	/**
	 * Creates the content outline page.
	 * 
	 * @return the content outline page
	 */
	protected AcceleoOutlinePage createContentOutlinePage() {
		return new AcceleoOutlinePage(this);
	}

	/**
	 * Creates a listener which is notified when the outline's selection changes.
	 * 
	 * @return the listener which is notified when the outline's selection changes
	 */
	protected ISelectionChangedListener createSelectionChangeListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				selectionChangedDetected(event);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#configureSourceViewerDecorationSupport(org.eclipse.ui.texteditor.SourceViewerDecorationSupport)
	 */
	@Override
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		/*
		 * Set the block matcher
		 */
		support.setCharacterPairMatcher(blockMatcher);
		support.setMatchingCharacterPainterPreferenceKeys(MATCHING_BRACKETS, MATCHING_BRACKETS_COLOR);
		// TODO it was JavaPlugin.getDefault().getPreferenceStore()
		IPreferenceStore pref = AcceleoUIActivator.getDefault().getPreferenceStore();
		IPreferenceStore[] stores = {getPreferenceStore(), pref, };
		setPreferenceStore(new ChainedPreferenceStore(stores));
		support.install(getPreferenceStore());
		super.configureSourceViewerDecorationSupport(support);
	}

	/**
	 * Methods which is notified when the outline's selection changes.
	 * 
	 * @param event
	 *            is the selection changed event
	 */
	protected void selectionChangedDetected(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		Object selectedElement = ((IStructuredSelection)selection).getFirstElement();
		String selectedElementURI = getFragmentID(selectedElement);
		if (selectedElementURI.equals(updatingOutlineURI)) {
			// Simply ignore the event
			updatingOutlineURI = ""; //$NON-NLS-1$
		} else if (selectedElement instanceof CSTNode) {
			int b = ((CSTNode)selectedElement).getStartPosition();
			int e = ((CSTNode)selectedElement).getEndPosition();
			if (b > -1 && e > -1) {
				selectRange(b, e);
			}
		}
	}

	/**
	 * Returns the EMF fragment URI of the given object. The result cannot be null. The default value is an
	 * empty string. It is useful in this class to identify an EObject without keeping the real reference on
	 * this EObject.
	 * 
	 * @param object
	 *            is the object
	 * @return the EMF fragment URI, the default value is an empty string
	 */
	private String getFragmentID(Object object) {
		String fragmentURI = null;
		if (object instanceof EObject) {
			URI uri = EcoreUtil.getURI((EObject)object);
			if (uri != null) {
				fragmentURI = uri.toString();
			}
		}
		if (fragmentURI == null) {
			fragmentURI = String.valueOf(object);
		}
		return fragmentURI;
	}

	/**
	 * Updates the outline selection by using the current offset in the text. It browses the CST model to find
	 * the element that is defined at the given offset.
	 * 
	 * @param posBegin
	 *            is the beginning index of the selected text
	 * @param posEnd
	 *            is the ending index of the selected text
	 */
	public void updateSelection(int posBegin, int posEnd) {
		int e;
		if (posEnd < posBegin) {
			e = posBegin;
		} else {
			e = posEnd;
		}
		if (getContentOutlinePage() != null && posBegin > -1 && e > -1) {
			// EObject
			AcceleoSourceContent source = getContent();
			if (source != null) {
				EObject object = source.getCSTNode(posBegin, e);
				if (object != null) {
					updatingOutlineURI = getFragmentID(object);
					getContentOutlinePage().setSelection(new StructuredSelection(object));
				}
			}
		}
	}

	/**
	 * This will create the quick outline presenter and install it on this editor.
	 * 
	 * @return The quick outline presenter.
	 */
	public IInformationPresenter getQuickOutlinePresenter() {
		InformationPresenter informationPresenter = new InformationPresenter(
				new IInformationControlCreator() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(org.eclipse.swt.widgets.Shell)
					 */
					public IInformationControl createInformationControl(Shell parent) {
						return new QuickOutlineControl(parent, SWT.RESIZE, AcceleoEditor.this);
					}
				});
		informationPresenter.install(getSourceViewer());
		IInformationProvider provider = new QuickOutlineInformationProvider(this);
		informationPresenter.setInformationProvider(provider, IDocument.DEFAULT_CONTENT_TYPE);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_BLOCK);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_COMMENT);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_FOR);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_IF);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_LET);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_MACRO);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_PROTECTED_AREA);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_QUERY);
		informationPresenter.setInformationProvider(provider, AcceleoPartitionScanner.ACCELEO_TEMPLATE);
		final int minimalWidth = 50;
		final int minimalHeight = 30;
		informationPresenter.setSizeConstraints(minimalWidth, minimalHeight, true, false);
		informationPresenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
		return informationPresenter;
	}

	/**
	 * Sets the highlighted range of this text editor to the specified region.
	 * 
	 * @param begin
	 *            is the beginning index
	 * @param end
	 *            is the ending index
	 */
	public void selectRange(int begin, int end) {
		if (begin > -1 && end >= begin) {
			ISourceViewer viewer = getSourceViewer();
			StyledText widget = viewer.getTextWidget();
			widget.setRedraw(false);
			setHighlightRange(begin, end - begin, true);
			selectAndReveal(begin, end - begin);
			widget.setRedraw(true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		content.doSave();
		super.doSave(progressMonitor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		content.doSave();
		super.doSaveAs();
	}

	/**
	 * Get the input file for this editor.
	 * 
	 * @return the editor input file, it can be null if the template isn't in the workspace
	 */
	public IFile getFile() {
		return (IFile)getEditorInput().getAdapter(IFile.class);
	}

	/**
	 * Updates the folding structure of the template. This will be called from the Acceleo template reconciler
	 * in order to allow the folding of blocks to the user.
	 * 
	 * @param addedAnnotations
	 *            These annotations have been added since the last reconciling operation.
	 * @param deletedAnnotations
	 *            This list represents the annotations that were deleted since we last reconciled.
	 * @param modifiedAnnotations
	 *            These annotations have seen their positions updated.
	 */
	public void updateFoldingStructure(Map<Annotation, Position> addedAnnotations,
			List<Annotation> deletedAnnotations, Map<Annotation, Position> modifiedAnnotations) {
		Annotation[] deleted = new Annotation[deletedAnnotations.size() + modifiedAnnotations.size()];
		for (int i = 0; i < deletedAnnotations.size(); i++) {
			deleted[i] = deletedAnnotations.get(i);
		}
		/*
		 * bug [273034] : merge "modified" annotations with deleted and added so as to update the whole
		 * folding structure in one go.
		 */
		final Iterator<Annotation> modifiedIterator = modifiedAnnotations.keySet().iterator();
		for (int i = deletedAnnotations.size(); i < deleted.length; i++) {
			deleted[i] = modifiedIterator.next();
		}
		addedAnnotations.putAll(modifiedAnnotations);
		if (annotationModel != null) {
			annotationModel.modifyAnnotations(deleted, addedAnnotations, null);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		ProjectionViewer viewer = (ProjectionViewer)getSourceViewer();

		projectionSupport = new ProjectionSupport(viewer, getAnnotationAccess(), getSharedColors());
		projectionSupport.install();

		// turn projection mode on
		viewer.doOperation(ProjectionViewer.TOGGLE);
		if (findOccurrencesPostSelectionListener == null) {
			findOccurrencesPostSelectionListener = new ISelectionListener() {
				public void selectionChanged(IWorkbenchPart part, ISelection selection) {
					findOccurrences();
				}
			};
			this.getSite().getPage().addPostSelectionListener(findOccurrencesPostSelectionListener);
		}

		annotationModel = viewer.getProjectionAnnotationModel();
	}

	/**
	 * Find all the occurrences of the selected element and highlight them.
	 */
	@SuppressWarnings("unchecked")
	private void findOccurrences() {
		if (occurrencesFinderJob != null) {
			occurrencesFinderJob.cancel();
		}
		final EObject selectedElement = this.findDeclaration();
		String selectedElementURI = getFragmentID(selectedElement);
		if (!selectedElementURI.equals(offsetASTNodeURI)) {
			offsetASTNodeURI = selectedElementURI;
			final IAnnotationModel model = this.getDocumentProvider().getAnnotationModel(
					this.getEditorInput());
			if (model != null) {
				Iterator<Annotation> annotations = model.getAnnotationIterator();
				while (annotations.hasNext()) {
					Annotation annotation = (Annotation)annotations.next();
					if (AcceleoOccurrencesFinderJob.FIND_OCCURENCES_ANNOTATION_TYPE.equals(annotation
							.getType())) {
						model.removeAnnotation(annotation);
					}
				}
			}
			if (selectedElement != null) {
				final ReferencesSearchQuery searchQuery = new ReferencesSearchQuery(this, selectedElement);
				occurrencesFinderJob = new AcceleoOccurrencesFinderJob(this, AcceleoUIMessages
						.getString("AcceleoEditor.HighligthAllOccurrencesJob"), searchQuery); //$NON-NLS-1$
				occurrencesFinderJob.setSystem(true);
				occurrencesFinderJob.setPriority(Job.DECORATE);
				occurrencesFinderJob.schedule();
			}
		}
	}

	/**
	 * Find the declaration of the text selected in the given editor.
	 * 
	 * @return the EObject corresponding to the declaration of the selected element
	 */
	private EObject findDeclaration() {
		EObject res = null;
		int offset;
		final ISelection selection = this.getSelectionProvider().getSelection();
		if (selection instanceof TextSelection) {
			offset = ((TextSelection)selection).getOffset();
		} else {
			offset = -1;
		}
		final ASTNode astNode = this.getContent().getASTNode(offset, offset);
		if (astNode != null) {
			res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
		}
		if (res == null) {
			final CSTNode cstNode = this.getContent().getCSTNode(offset, offset);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(this, astNode, cstNode);
			}
		}
		return res;
	}

	/**
	 * Compute the whole line of the current offset.
	 * 
	 * @param document
	 *            the current document
	 * @param offset
	 *            the current offset
	 * @return the line containing the offset, ended with the offset
	 */
	public String getCurrentLine(final IDocument document, final int offset) {
		String result = null;
		try {
			if (offset >= 0) {
				final int lineNumber = document.getLineOfOffset(offset);
				final int lineOffset = document.getLineOffset(lineNumber);
				result = document.get(lineOffset, offset - lineOffset);
			}
			return result;
		} catch (BadLocationException ble) {
			result = null;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#createSourceViewer(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.jface.text.source.IVerticalRuler, int)
	 */
	@Override
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		ISourceViewer viewer = new ProjectionViewer(parent, ruler, getOverviewRuler(),
				isOverviewRulerVisible(), styles);

		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);

		return viewer;
	}

}
