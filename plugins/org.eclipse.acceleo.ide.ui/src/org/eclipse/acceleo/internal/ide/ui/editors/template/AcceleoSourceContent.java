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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarkerUtils;
import org.eclipse.acceleo.internal.parser.ast.ocl.OCLParser;
import org.eclipse.acceleo.internal.parser.cst.CSTParser;
import org.eclipse.acceleo.parser.AcceleoParserInfos;
import org.eclipse.acceleo.parser.AcceleoParserMessage;
import org.eclipse.acceleo.parser.AcceleoParserMessages;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.acceleo.parser.AcceleoParserWarnings;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.Documentation;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateExpression;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.ecore.CollectionItem;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.ocl.helper.ChoiceKind;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * The source content (i.e the semantic content for the template editor). It can create a CST model and it is
 * able to do an incremental parsing of the text. You have to initialize the content with the method
 * <code>init</code>.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoSourceContent {

	/**
	 * Default URI of the EMTL file if it doesn't exist.
	 */
	private static final String DEFAULT_EMTL_URI = "http://acceleo.eclipse.org/default.emtl"; //$NON-NLS-1$

	/**
	 * The separator of marker position.
	 */
	private static final String MARKER_POSITION_SEPARATOR = ","; //$NON-NLS-1$

	/**
	 * There can be several syntax help requested. It count the requests.
	 */
	protected int syntaxHelpCount;

	/**
	 * The current resource set used to compute the syntax help information. It contains a copy of the current
	 * AST and its dependencies.
	 */
	protected ResourceSet syntaxHelpResourceSet;

	/**
	 * The Acceleo file. It can be null if the file hasn't been specified.
	 */
	protected IFile file;

	/**
	 * The source.
	 */
	protected AcceleoSourceBufferWithASTJob source;

	/**
	 * The Acceleo project which contains the Acceleo file. It can be null if the file hasn't been specified.
	 */
	private AcceleoProject acceleoProject;

	/**
	 * The parser used to create a CST model from the document.
	 */
	private CSTParser cstParser;

	/**
	 * The job instance to unload the syntax help information.
	 */
	private SyntaxHelpJob syntaxHelpUnloadJob = new SyntaxHelpJob();

	/**
	 * You can use the method 'getAccessibleOutputFiles' to get all the accessible output files (EMTL) of the
	 * current project (It means the files of the current project and the files of the required plugins). This
	 * is the cache of this method. You can clear the cache by using the method
	 * 'resetAccessibleOutputFilesCache'.
	 */
	private List<URI> accessibleOutputFiles;

	/**
	 * The job class to unload the syntax help information.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	protected class SyntaxHelpJob {

		/**
		 * The job.
		 */
		Job unloadJob;

		/**
		 * Executes this job.
		 */
		public void run() {
			if (unloadJob != null) {
				unloadJob.cancel();
			}
			unloadJob = new Job("Acceleo") { //$NON-NLS-1$

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					ResourceSet resourceSet = syntaxHelpResourceSet;
					if (syntaxHelpCount == 0) {
						syntaxHelpResourceSet = null;
						if (resourceSet != null) {
							Iterator<Resource> resources = resourceSet.getResources().iterator();
							while (resources.hasNext()) {
								resources.next().unload();
							}
							resourceSet.getResources().clear();
						}
					}
					return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
				}
			};
			unloadJob.setPriority(Job.DECORATE);
			unloadJob.setSystem(true);
			final int delay = 4000;
			unloadJob.schedule(delay);
		}
	}

	/**
	 * Constructor.
	 */
	public AcceleoSourceContent() {
	}

	/**
	 * Creates a specific Acceleo source buffer which is able to refresh the AST with a low priority job.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	protected class AcceleoSourceBufferWithASTJob extends AcceleoSourceBuffer {

		/**
		 * The job to compute the AST.
		 */
		private Job createASTJob;

		/**
		 * Constructor.
		 * 
		 * @param buffer
		 *            is the buffer to parse, the file property will be null
		 */
		public AcceleoSourceBufferWithASTJob(StringBuffer buffer) {
			super(buffer);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.parser.AcceleoSourceBuffer#refreshAST()
		 */
		@Override
		public void refreshAST() {
			if (createASTJob != null) {
				createASTJob.cancel();
			}
			createASTJob = createASTJob();
			createASTJob.setPriority(Job.DECORATE);
			createASTJob.setSystem(true);
			final int schedule = 1500;
			createASTJob.schedule(schedule);
		}

		/**
		 * A method to cancel the current job.
		 */
		public void cancel() {
			if (createASTJob != null) {
				createASTJob.cancel();
			}
		}

		/**
		 * Creates the job that is able to compute the AST. The AST links will be resolved in the specified
		 * region.
		 * 
		 * @return the job
		 */
		private Job createASTJob() {
			return new Job("Acceleo") { //$NON-NLS-1$
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					ast = null;
					runCreateAST();
					if (astCreator != null) {
						astCreator.canceling(false);
					}
					return new Status(IStatus.OK, AcceleoUIActivator.PLUGIN_ID, "OK"); //$NON-NLS-1$
				}

				@Override
				protected void canceling() {
					super.canceling();
					// could be canceled before astCreator is created
					if (astCreator != null) {
						astCreator.canceling(true);
					}
				}
			};
		}

		/**
		 * It computes the AST. The AST links will be resolved in the specified region.
		 */
		protected void runCreateAST() {
			IFile fileMTL = AcceleoSourceContent.this.file;
			if (fileMTL != null && fileMTL.exists()) {
				Map<String, IMarker> position2problemMarkers = new HashMap<String, IMarker>();
				position2problemMarkers = initMarkers(position2problemMarkers, fileMTL,
						AcceleoMarkerUtils.PROBLEM_MARKER_ID);

				Map<String, IMarker> position2warningMarkers = new HashMap<String, IMarker>();
				position2warningMarkers = initMarkers(position2warningMarkers, fileMTL,
						AcceleoMarkerUtils.WARNING_MARKER_ID);

				Map<String, IMarker> position2infoMarkers = new HashMap<String, IMarker>();
				position2infoMarkers = initMarkers(position2infoMarkers, fileMTL,
						AcceleoMarkerUtils.INFO_MARKER_ID);

				AcceleoSourceContent.this.createAST();
				org.eclipse.acceleo.model.mtl.Module vAST = this.getAST();
				if (vAST != null) {
					List<URI> dependenciesURIs = getAccessibleOutputFiles();
					loadImportsDependencies(vAST, dependenciesURIs);
					loadExtendsDependencies(vAST, dependenciesURIs);
					source.resolveAST();
					source.resolveASTDocumentation();
				}

				AcceleoParserProblems problems = source.getProblems();
				manageMarker(problems, position2problemMarkers, fileMTL);
				source.getProblems().clear();

				AcceleoParserWarnings warnings = source.getWarnings();
				manageMarker(warnings, position2warningMarkers, fileMTL);
				source.getWarnings().clear();

				AcceleoParserInfos infos = source.getInfos();
				manageMarker(infos, position2infoMarkers, fileMTL);
				source.getInfos().clear();
			}
		}
	}

	/**
	 * Cancel the current background tasks.
	 */
	public void cancelTasks() {
		if (source != null) {
			source.cancel();
			source.getProblems().clear();
			source.getWarnings().clear();
			source.getInfos().clear();
		}
	}

	/**
	 * Gets the current CST model. It creates the CST if <code>createCST</code> hasn't been called.
	 * 
	 * @return the current CST model, or null if the source is null
	 */
	public org.eclipse.acceleo.parser.cst.Module getCST() {
		if (source == null) {
			return null;
		}
		if (source.getCST() == null) {
			createCST();
		}
		return source.getCST();
	}

	/**
	 * Gets the current AST model. It creates the AST if <code>createAST</code> hasn't been called. The AST
	 * that will be returned, will not be resolved unless if it has been resolved earlier.
	 * 
	 * @return the current AST model
	 */
	public org.eclipse.acceleo.model.mtl.Module getAST() {
		if (source == null) {
			return null;
		}
		if (source.getAST() == null) {
			createAST();
		}
		return source.getAST();
	}

	/**
	 * Gets the Acceleo file. It can be null if the file hasn't been specified.
	 * 
	 * @return the Acceleo file, or null
	 */
	public IFile getFile() {
		return file;
	}

	/**
	 * It initializes the template content with the given text. The file and the project won't be specified.
	 * 
	 * @param text
	 *            is the content of the document
	 */
	public void init(StringBuffer text) {
		init(text, null);
	}

	/**
	 * It initializes the template content with the given text and the corresponding file.
	 * 
	 * @param text
	 *            is the content of the document
	 * @param aFile
	 *            is a file, can be null
	 */
	public void init(StringBuffer text, IFile aFile) {
		file = aFile;
		if (file != null) {
			acceleoProject = new AcceleoProject(file.getProject());
		} else {
			acceleoProject = null;
		}
		source = new AcceleoSourceBufferWithASTJob(text);
		cstParser = new CSTParser(source);
	}

	/**
	 * Creates a CST model. You can get it with the method <code>getCST</code>.
	 */
	public void createCST() {
		source.getProblems().clear();
		source.getWarnings().clear();
		source.getInfos().clear();
		source.createCST();
	}

	/**
	 * Computes a delta version of the CST model, and returns the modified object. This object will be
	 * refreshed in the outline view.
	 * 
	 * @param posBegin
	 *            the beginning index, inclusive
	 * @param posEnd
	 *            the ending index, exclusive
	 * @param newText
	 *            the string that will replace previous contents
	 * @return the modified object
	 */
	public CSTNode updateCST(int posBegin, int posEnd, String newText) {
		source.getProblems().clear();
		source.getWarnings().clear();
		source.getInfos().clear();
		source.getBuffer().replace(posBegin, posEnd, newText);
		CSTNode current = getCSTNode(posBegin, posEnd);
		if (current instanceof TextExpression && newText.indexOf(IAcceleoConstants.DEFAULT_BEGIN) > -1) {
			current = (CSTNode)current.eContainer();
		}
		if (current != null && posBegin == posEnd && posBegin == current.getStartPosition()) {
			if (!(current instanceof TextExpression || current instanceof Variable || current instanceof ModelExpression)) {
				current = (CSTNode)current.eContainer();
			}
		}
		while (current != null) {
			EObject modified = null;
			if (current instanceof ModuleElement
					&& (((ModuleElement)current).eContainingFeature() == CstPackage.eINSTANCE
							.getModule_OwnedModuleElement())) {
				ModuleElement oldModuleElement = (ModuleElement)current;
				int newPosEnd = posBegin + newText.length();
				int shift = newPosEnd - posEnd;
				shiftPositionsAfter(posBegin, shift);
				modified = replaceModuleElement(oldModuleElement);
				if (modified == null) {
					shiftPositionsAfter(posBegin + shift, -shift);
				}
			} else if (current instanceof ModuleElement
					&& (((ModuleElement)current).eContainingFeature() == CstPackage.eINSTANCE
							.getModule_Documentation())) {
				Documentation oldDocumentation = (Documentation)current;
				int newPosEnd = posBegin + newText.length();
				int shift = newPosEnd - posEnd;
				shiftPositionsAfter(posBegin, shift);
				modified = replaceModuleDocumentation(oldDocumentation);
				if (modified == null) {
					shiftPositionsAfter(posBegin + shift, -shift);
				}
			} else if (current instanceof ModelExpression) {
				ModelExpression oldExpression = (ModelExpression)current;
				int newPosEnd = posBegin + newText.length();
				int shift = newPosEnd - posEnd;
				shiftPositionsAfter(posBegin, shift);
				modified = replaceModelExpression(oldExpression);
				if (modified == null) {
					shiftPositionsAfter(posBegin + shift, -shift);
				}
			} else if (current instanceof TemplateExpression) {
				TemplateExpression oldExpression = (TemplateExpression)current;
				int newPosEnd = posBegin + newText.length();
				int shift = newPosEnd - posEnd;
				shiftPositionsAfter(posBegin, shift);
				modified = replaceTemplateExpression(oldExpression);
				if (modified == null) {
					shiftPositionsAfter(posBegin + shift, -shift);
				}
			} else if (current instanceof Variable) {
				Variable oldVariable = (Variable)current;
				int newPosEnd = posBegin + newText.length();
				int shift = newPosEnd - posEnd;
				shiftPositionsAfter(posBegin, shift);
				modified = replaceVariable(oldVariable);
				if (modified == null) {
					shiftPositionsAfter(posBegin + shift, -shift);
				}
			}
			if (modified instanceof CSTNode) {
				source.refreshAST();
				return (CSTNode)modified;
			}
			current = (CSTNode)current.eContainer();
		}
		doSave();
		return source.getCST();
	}

	/**
	 * Performs a save of the editor content.
	 */
	public void doSave() {
		if (source != null) {
			source.cancel();
			source.getProblems().clear();
			source.getWarnings().clear();
			source.getInfos().clear();
			source.createCST();
		}
	}

	/**
	 * Replaces the documentation of the module.
	 * 
	 * @param oldDocumentation
	 *            The old documentation.
	 * @return The new documentation
	 */
	private EObject replaceModuleDocumentation(Documentation oldDocumentation) {
		Module tempModule = CstFactory.eINSTANCE.createModule();
		if (getCST() != null) {
			tempModule.getInput().addAll(EcoreUtil.copyAll(getCST().getInput()));
		}
		cstParser.parseModuleDocumentation(0, tempModule);
		if (tempModule.getDocumentation() != null) {
			try {
				Documentation newDocumentation = tempModule.getDocumentation();
				EcoreUtil.replace(oldDocumentation, newDocumentation);
				return newDocumentation;
			} catch (ClassCastException e) {
				AcceleoUIActivator.log(e, true);
				// continue
			} catch (ArrayStoreException e) {
				AcceleoUIActivator.log(e, true);
				// continue
			}
		}
		return null;
	}

	/**
	 * Creates a new version of a module element in the CST model.
	 * 
	 * @param oldModuleElement
	 *            is the old module element to replace
	 * @return the new module element
	 */
	private EObject replaceModuleElement(ModuleElement oldModuleElement) {
		Module tempModule = CstFactory.eINSTANCE.createModule();
		if (getCST() != null) {
			tempModule.getInput().addAll(EcoreUtil.copyAll(getCST().getInput()));
		}
		cstParser.parseModuleBody(oldModuleElement.getStartPosition(), oldModuleElement.getEndPosition(),
				tempModule);
		if (tempModule.getOwnedModuleElement().size() > 0) {
			try {
				ModuleElement newModuleElement = tempModule.getOwnedModuleElement().get(0);
				EcoreUtil.replace(oldModuleElement, newModuleElement);
				return newModuleElement;
			} catch (ClassCastException e) {
				// continue
			} catch (ArrayStoreException e) {
				// continue
			}
		}
		return null;
	}

	/**
	 * Creates a new version of a model expression in the CST model.
	 * 
	 * @param oldExpression
	 *            is the old expression to replace
	 * @return the new model expression
	 */
	private ModelExpression replaceModelExpression(ModelExpression oldExpression) {
		oldExpression.setBefore(null);
		oldExpression.setEach(null);
		oldExpression.setAfter(null);
		cstParser.getPBlock().parseExpressionHeader(oldExpression.getStartPosition(),
				oldExpression.getEndPosition(), oldExpression);
		return oldExpression;
	}

	/**
	 * Creates a new version of a template expression in the CST model.
	 * 
	 * @param oldExpression
	 *            is the old expression to replace
	 * @return the new template expression
	 */
	private EObject replaceTemplateExpression(TemplateExpression oldExpression) {
		Module tempModule = CstFactory.eINSTANCE.createModule();
		if (getCST() != null) {
			tempModule.getInput().addAll(EcoreUtil.copyAll(getCST().getInput()));
		}
		Template tempBlock = CstFactory.eINSTANCE.createTemplate();
		tempModule.getOwnedModuleElement().add(tempBlock);
		cstParser.getPBlock().parse(oldExpression.getStartPosition(), oldExpression.getEndPosition(),
				tempBlock);
		if (tempBlock.getBody().size() > 0) {
			try {
				TemplateExpression newExpression = tempBlock.getBody().get(0);
				EcoreUtil.replace(oldExpression, newExpression);
				return newExpression;
			} catch (ClassCastException e) {
				// continue
			} catch (ArrayStoreException e) {
				// continue
			}
		}
		return null;
	}

	/**
	 * Creates a new version of a variable in the CST model.
	 * 
	 * @param oldVariable
	 *            is the old variable to replace
	 * @return the new variable
	 */
	private EObject replaceVariable(Variable oldVariable) {
		Module tempModule = CstFactory.eINSTANCE.createModule();
		if (getCST() != null) {
			tempModule.getInput().addAll(EcoreUtil.copyAll(getCST().getInput()));
		}
		Variable newVariable = cstParser.createVariable(oldVariable.getStartPosition(), oldVariable
				.getEndPosition());
		if (newVariable != null) {
			try {
				EcoreUtil.replace(oldVariable, newVariable);
				return newVariable;
			} catch (ClassCastException e) {
				// continue
			} catch (ArrayStoreException e) {
				// continue
			}
		}
		return null;
	}

	/**
	 * All the positions after the index are shifted. It browses the current CST and modifies the positions
	 * when it is necessary (only if pos > index).
	 * 
	 * @param index
	 *            is the beginning index for all the modifications
	 * @param shift
	 *            is the value to be added for each position
	 */
	private void shiftPositionsAfter(int index, int shift) {
		if (getCST() != null) {
			Iterator<EObject> eAllContents = getCST().eAllContents();
			while (eAllContents.hasNext()) {
				EObject eObject = eAllContents.next();
				if (eObject instanceof CSTNode) {
					CSTNode cstNode = (CSTNode)eObject;
					shiftPositionsAfter(cstNode, index, shift);
				}
			}
		}
	}

	/**
	 * The positions of the given CST candidate are shifted. It modifies the positions when it is necessary
	 * (only if pos > index).
	 * 
	 * @param candidate
	 *            is the current candidate to modify
	 * @param index
	 *            is the beginning index for all the modifications
	 * @param shift
	 *            is the value to be added for each position
	 */
	private void shiftPositionsAfter(CSTNode candidate, int index, int shift) {
		if (candidate instanceof TextExpression || candidate instanceof Variable
				|| candidate instanceof ModelExpression) {
			if (candidate.getStartPosition() > index) {
				assert candidate.getStartPosition() + shift > -1;
				candidate.setStartPosition(candidate.getStartPosition() + shift);
			}
			if (candidate.getEndPosition() >= index) {
				assert candidate.getEndPosition() + shift > -1;
				candidate.setEndPosition(candidate.getEndPosition() + shift);
			}
		} else {
			if (candidate.getStartPosition() >= index) {
				assert candidate.getStartPosition() + shift > -1;
				candidate.setStartPosition(candidate.getStartPosition() + shift);
			}
			if (candidate.getEndPosition() > index) {
				assert candidate.getEndPosition() + shift > -1;
				candidate.setEndPosition(candidate.getEndPosition() + shift);
			}
		}
	}

	/**
	 * Gets the nearest CST node at the given position.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest CST node
	 */
	public CSTNode getCSTNode(int posBegin, int posEnd) {
		if (getCST() != null) {
			CSTNode candidate = getCST();
			CSTNode childrenCandidate = getChildrenCandidate(candidate, posBegin, posEnd);
			while (childrenCandidate != null) {
				candidate = childrenCandidate;
				childrenCandidate = getChildrenCandidate(candidate, posBegin, posEnd);
			}
			return candidate;
		}
		return null;
	}

	/**
	 * Gets the nearest CST child at the given position. It browses the children of the given candidate and
	 * returns the nearest children if it exists.
	 * 
	 * @param candidate
	 *            is the current candidate to browse
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest CST node in the children of the current candidate
	 */
	private CSTNode getChildrenCandidate(CSTNode candidate, int posBegin, int posEnd) {
		Iterator<EObject> itContents = candidate.eContents().iterator();
		while (itContents.hasNext()) {
			CSTNode eContent = (CSTNode)itContents.next();
			int startPosition = eContent.getStartPosition();
			int endPosition = eContent.getEndPosition();
			assert startPosition > -1 && endPosition > -1;
			if (startPosition <= posBegin && endPosition >= posEnd) {
				return eContent;
			}
		}
		return null;
	}

	/**
	 * Gets the CST parent of the given type. It browses the ancestors of the given node and returns the
	 * nearest valid parent if it exists.
	 * 
	 * @param node
	 *            is the current node
	 * @param c
	 *            is the class which represents the type of the parent to search
	 * @return the CST parent, or null if it doesn't exist
	 */
	@SuppressWarnings("rawtypes")
	public CSTNode getCSTParent(CSTNode node, Class c) {
		CSTNode eContainer = (CSTNode)node.eContainer();
		while (eContainer != null) {
			if (c.isInstance(eContainer)) {
				return eContainer;
			}
			eContainer = (CSTNode)eContainer.eContainer();
		}
		return null;
	}

	/**
	 * Gets the AST parent of the given type. It browses the ancestors of the given node and returns the
	 * nearest valid parent if it exists.
	 * 
	 * @param node
	 *            is the current node
	 * @param c
	 *            is the class which represents the type of the parent to search
	 * @return the AST parent, or null if it doesn't exist
	 */
	@SuppressWarnings("rawtypes")
	public ASTNode getASTParent(ASTNode node, Class c) {
		ASTNode eContainer = (ASTNode)node.eContainer();
		while (eContainer != null) {
			if (c.isInstance(eContainer)) {
				return eContainer;
			}
			eContainer = (ASTNode)eContainer.eContainer();
		}
		return null;
	}

	/**
	 * Returns the environment instance that was used under the covers by the ocl parser. It returns null when
	 * we don't know the OCL environment. Be careful when you manipulate this OCL environment because the
	 * content can be modified at any time by the background jobs of this class. You musn't modify the
	 * environment and you must have a thread safe implementation.
	 * 
	 * @return The environment instance that was used under the covers by the ocl parser, can be null
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public Environment<?, EClassifier, EOperation, EStructuralFeature, ?, ?, ?, ?, ?, ?, ?, ?> getOCLEnvironment() {
		return source.getOCLEnvironment();
	}

	/**
	 * Obtains syntax completion choices for the specified fragment of an OCL expression.
	 * 
	 * @param text
	 *            a partial OCL expression for which to seek choices that could be appended to it
	 * @param offset
	 *            is the current offset in the text
	 * @return a list of {@link Choice}s, possibly empty. The ordering of the list may or may not indicate
	 *         relative relevance or frequency of a choice
	 */
	@SuppressWarnings("cast")
	public synchronized Collection<Choice> getSyntaxHelp(String text, int offset) {
		syntaxHelpCount++;
		try {
			if (getCST() != null) {
				org.eclipse.acceleo.model.mtl.Module vAST = source.getAST();
				OCLParser oclParser;
				if (vAST == null) {
					createAST();
					vAST = source.getAST();
				}
				if (vAST != null) {
					URI uri = vAST.eResource().getURI();
					vAST = (org.eclipse.acceleo.model.mtl.Module)EcoreUtil.copy(vAST);
					if (syntaxHelpResourceSet == null) {
						syntaxHelpResourceSet = new ResourceSetImpl();
					}
					Resource resource = syntaxHelpResourceSet.getResource(uri, false);
					if (resource == null) {
						resource = syntaxHelpResourceSet.createResource(uri);
					}
					resource.getContents().add(vAST);
					oclParser = new OCLParser(resource);
					if (!getCST().getImports().isEmpty() || !getCST().getExtends().isEmpty()) {
						List<URI> dependenciesURIs = getAccessibleOutputFiles();
						loadImportsDependencies(vAST, dependenciesURIs);
						loadExtendsDependencies(vAST, dependenciesURIs);
					}
					oclParser.addRecursivelyMetamodelsToScope(vAST);
					boolean isAfterDot = isAfterDot(text);
					oclParser.addRecursivelyBehavioralFeaturesToScope(vAST, true, !isAfterDot,
							getCurrentQualifiedName(text));
					int specificOffset = getSpecificOffset(offset);
					EClassifier eContext = oclParser.addRecursivelyVariablesToScopeAndGetContextClassifierAt(
							vAST, specificOffset);
					if (isPostOffset(offset)) {
						eContext = oclParser.getStringType();
					}
					if (eContext != null) {
						oclParser.pushContext(eContext);
					}
					try {
						Collection<Choice> order = order(oclParser.getSyntaxHelp(text), oclParser);
						oclParser.dispose();
						return order;
					} finally {
						if (eContext != null) {
							oclParser.popContext();
						}
						syntaxHelpUnloadJob.run();
						oclParser.dispose();
					}
				}
			}
			return new ArrayList<Choice>();
		} finally {
			syntaxHelpCount--;
		}
	}

	/**
	 * Order a list of OCL completion proposals.
	 * 
	 * @param choices
	 *            is the list to order
	 * @param oclParser
	 *            is the OCL parser used to get the given OCL choices
	 * @return an ordered list
	 */
	private Collection<Choice> order(List<Choice> choices, OCLParser oclParser) {
		final List<Choice> orderedChoices = new ArrayList<Choice>(choices.size());
		Iterator<Choice> choicesIt = choices.iterator();
		while (choicesIt.hasNext()) {
			Choice choice = choicesIt.next();
			if (choice.getElement() instanceof EOperation) {
				EOperation eOperation = (EOperation)choice.getElement();
				org.eclipse.acceleo.model.mtl.ModuleElement eModuleElement = oclParser
						.getModuleElement(eOperation);
				if (eModuleElement != null) {
					AcceleoCompletionChoice acceleoChoice = new AcceleoCompletionChoice(choice,
							eModuleElement);
					choice = acceleoChoice;
				}
			}
			orderedChoices.add(choice);
		}
		Collections.sort(orderedChoices, new ChoiceComparator());
		return orderedChoices;
	}

	/**
	 * Indicates if the beginning text of the completion is after a dot character.
	 * 
	 * @param text
	 *            is the beginning text
	 * @return true if the beginning text of the completion is after a dot character
	 */
	private boolean isAfterDot(String text) {
		boolean isAfterDot = false;
		for (int i = text.length() - 1; !isAfterDot && i >= 0; i--) {
			char c = text.charAt(i);
			if (c == '.') {
				isAfterDot = true;
			} else if (!Character.isJavaIdentifierPart(c)) {
				break;
			}
		}
		return isAfterDot;
	}

	/**
	 * Gets the current name to filter during the completion, after a dot character...
	 * 
	 * @param text
	 *            is the beginning text
	 * @return the current name to filter
	 */
	private String getCurrentQualifiedName(String text) {
		StringBuffer result = new StringBuffer();
		for (int i = text.length() - 1; i >= 0; i--) {
			char c = text.charAt(i);
			if (Character.isJavaIdentifierPart(c)) {
				result.insert(0, c);
			} else {
				break;
			}
		}
		return result.toString();
	}

	/**
	 * Gets a specific offset to ignore some variables in the current context.
	 * <p>
	 * The variable <b>classes</b> can't be used in the following example :
	 * </p>
	 * classes : Set(Class) = <b>classes</b>.name
	 * 
	 * @param offset
	 *            is the current offset
	 * @return the specific offset, the given offset is returned by default
	 */
	private int getSpecificOffset(int offset) {
		CSTNode cstNode = getCSTNode(offset, offset);
		int result = offset;
		if (cstNode != null) {
			EObject eContainer = cstNode.eContainer();
			EObject eContainingFeature = cstNode.eContainingFeature();
			if (cstNode instanceof ModelExpression && eContainer instanceof ForBlock) {
				if (eContainingFeature == CstPackage.eINSTANCE.getForBlock_IterSet()
						|| eContainingFeature == CstPackage.eINSTANCE.getForBlock_Before()
						|| eContainingFeature == CstPackage.eINSTANCE.getForBlock_Each()
						|| eContainingFeature == CstPackage.eINSTANCE.getForBlock_After()) {
					result = ((ForBlock)eContainer).getStartPosition();
				}
			} else if (cstNode instanceof TemplateOverridesValue && eContainer instanceof Template) {
				result = ((Template)eContainer).getStartPosition();
			} else if (cstNode instanceof Variable) {
				result = cstNode.getStartPosition();
			}
		}
		return result;
	}

	/**
	 * Indicates if the given offset is in a postcondition expression.
	 * 
	 * @param offset
	 *            is the current offset
	 * @return true if the offset is in a postcondition text region
	 */
	private boolean isPostOffset(int offset) {
		CSTNode cstNode = getCSTNode(offset, offset);
		if (cstNode != null) {
			EObject eContainer = cstNode.eContainer();
			EObject eContainingFeature = cstNode.eContainingFeature();
			if (cstNode instanceof ModelExpression && eContainer instanceof Template) {
				if (eContainingFeature == CstPackage.eINSTANCE.getTemplate_Post()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Loads the "imports" dependencies in the current AST, using the CST information.
	 * 
	 * @param vAST
	 *            is the current AST model
	 * @param dependenciesURIs
	 *            are the URIs of the "imports" dependencies to load
	 */
	protected void loadImportsDependencies(org.eclipse.acceleo.model.mtl.Module vAST,
			List<URI> dependenciesURIs) {
		for (Iterator<URI> itDependenciesURIs = dependenciesURIs.iterator(); itDependenciesURIs.hasNext();) {
			URI oURI = itDependenciesURIs.next();
			String oName = new Path(oURI.lastSegment()).removeFileExtension().lastSegment();
			Iterator<ModuleImportsValue> values = getCST().getImports().iterator();
			while (values.hasNext()) {
				ModuleImportsValue moduleImportsValue = values.next();
				if (moduleImportsValue != null
						&& moduleImportsValue.getName() != null
						&& (IAcceleoConstants.NAMESPACE_SEPARATOR + moduleImportsValue.getName())
								.endsWith(IAcceleoConstants.NAMESPACE_SEPARATOR + oName)) {
					org.eclipse.acceleo.model.mtl.Module otherModule = getModule(vAST.eResource()
							.getResourceSet(), oURI);
					if (otherModule != null
							&& (moduleImportsValue.getName().equals(otherModule.getNsURI()) || moduleImportsValue
									.getName().equals(otherModule.getName()))
							&& !vAST.getImports().contains(otherModule)) {
						vAST.getImports().add(otherModule);
					}
					break;
				}
			}

		}
	}

	/**
	 * Loads the "extends" dependencies in the current AST, using the CST information.
	 * 
	 * @param vAST
	 *            is the current AST model
	 * @param dependenciesURIs
	 *            are the URIs of the "extends" dependencies to load
	 */
	protected void loadExtendsDependencies(org.eclipse.acceleo.model.mtl.Module vAST,
			List<URI> dependenciesURIs) {
		for (Iterator<URI> itDependenciesURIs = dependenciesURIs.iterator(); itDependenciesURIs.hasNext();) {
			URI oURI = itDependenciesURIs.next();
			String oName = new Path(oURI.lastSegment()).removeFileExtension().lastSegment();
			Iterator<ModuleExtendsValue> values = getCST().getExtends().iterator();
			while (values.hasNext()) {
				ModuleExtendsValue moduleExtendsValue = values.next();
				if (moduleExtendsValue != null
						&& moduleExtendsValue.getName() != null
						&& (IAcceleoConstants.NAMESPACE_SEPARATOR + moduleExtendsValue.getName())
								.endsWith(IAcceleoConstants.NAMESPACE_SEPARATOR + oName)) {
					org.eclipse.acceleo.model.mtl.Module otherModule = getModule(vAST.eResource()
							.getResourceSet(), oURI);
					if (otherModule != null
							&& (moduleExtendsValue.getName().equals(otherModule.getNsURI()) || moduleExtendsValue
									.getName().equals(otherModule.getName()))
							&& !vAST.getExtends().contains(otherModule)) {
						vAST.getExtends().add(otherModule);
					}
					break;
				}
			}
		}
	}

	/**
	 * Initialize the handling of markers.
	 * 
	 * @param position2marker
	 *            The map of position and markers
	 * @param fileMTL
	 *            The MTL file
	 * @param markerID
	 *            The ID of the marker
	 * @return The initialized map of markers
	 */
	protected Map<String, IMarker> initMarkers(final Map<String, IMarker> position2marker,
			final IFile fileMTL, final String markerID) {
		try {
			IMarker[] markers = fileMTL.findMarkers(markerID, false, IResource.DEPTH_INFINITE);

			if (AcceleoMarkerUtils.INFO_MARKER_ID.equals(markerID)) {
				IMarker[] taskMarkers = fileMTL.findMarkers(AcceleoMarkerUtils.TASK_MARKER_ID, false,
						IResource.DEPTH_INFINITE);
				if (taskMarkers.length > 0) {
					IMarker[] markersArray = new IMarker[markers.length + taskMarkers.length];
					System.arraycopy(markers, 0, markersArray, 0, markers.length);
					System.arraycopy(taskMarkers, 0, markersArray, markers.length, taskMarkers.length);
				}
			}

			for (IMarker marker : markers) {
				String key = MarkerUtilities.getCharStart(marker) + MARKER_POSITION_SEPARATOR
						+ MarkerUtilities.getCharEnd(marker);
				if (position2marker.containsKey(key)) {
					marker.delete();
				} else {
					position2marker.put(key, marker);
				}
			}
		} catch (CoreException ex) {
			AcceleoUIActivator.getDefault().getLog().log(ex.getStatus());
		}
		return position2marker;
	}

	/**
	 * Manages the Acceleo Parser Datas that appeared during the parsing.
	 * 
	 * @param acceleoParserDatas
	 *            The datas
	 * @param position2marker
	 *            The map of the position and the markers
	 * @param fileMTL
	 *            The MTL file on which the marker should appeared
	 */
	protected void manageMarker(final AcceleoParserMessages acceleoParserDatas,
			final Map<String, IMarker> position2marker, final IFile fileMTL) {
		if (acceleoParserDatas == null) {
			return;
		}

		List<? extends AcceleoParserMessage> messageList = null;
		String markerId = null;

		if (acceleoParserDatas instanceof AcceleoParserProblems) {
			AcceleoParserProblems acceleoParserProblems = (AcceleoParserProblems)acceleoParserDatas;
			messageList = acceleoParserProblems.getList();
			markerId = AcceleoMarkerUtils.PROBLEM_MARKER_ID;
		} else if (acceleoParserDatas instanceof AcceleoParserWarnings) {
			AcceleoParserWarnings acceleoParserWarnings = (AcceleoParserWarnings)acceleoParserDatas;
			messageList = acceleoParserWarnings.getList();
			markerId = AcceleoMarkerUtils.WARNING_MARKER_ID;
		} else if (acceleoParserDatas instanceof AcceleoParserInfos) {
			AcceleoParserInfos acceleoParserInfos = (AcceleoParserInfos)acceleoParserDatas;
			messageList = acceleoParserInfos.getList();
			markerId = AcceleoMarkerUtils.INFO_MARKER_ID;
		} else {
			return;
		}

		for (AcceleoParserMessage message : messageList) {
			try {
				String key = message.getPosBegin() + MARKER_POSITION_SEPARATOR + message.getPosEnd();
				if (!position2marker.containsKey(key)) {
					AcceleoMarkerUtils.createMarkerOnFile(markerId, fileMTL, message.getLine(), message
							.getPosBegin(), message.getPosEnd(), message.getMessage());
				} else {
					position2marker.remove(key);
				}
			} catch (CoreException ex) {
				AcceleoUIActivator.getDefault().getLog().log(ex.getStatus());
			}
		}

		for (IMarker markerToDelete : position2marker.values()) {
			try {
				markerToDelete.delete();
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
	}

	/**
	 * Returns the first root element of the resource resolved by the URI. The first root element must be of
	 * type 'Module'.
	 * 
	 * @param resourceSet
	 *            is the resource set
	 * @param uri
	 *            the URI to resolve.
	 * @return the first root element of the resource resolved by the URI, or null if there isn't one.
	 */
	private org.eclipse.acceleo.model.mtl.Module getModule(ResourceSet resourceSet, URI uri) {
		try {
			if (resourceSet != null) {
				EObject root = ModelUtils.load(uri, resourceSet);
				if (root instanceof org.eclipse.acceleo.model.mtl.Module) {
					return (org.eclipse.acceleo.model.mtl.Module)root;
				}
			}
		} catch (IOException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		} catch (WrappedException e) {
			if (e.getMessage().endsWith(".emtl' does not exist.")) { //$NON-NLS-1$
				/*
				 * Discard exception. This is seldom thrown by the editor when repeatedly copy/pasting chunks
				 * of code.
				 */
			} else {
				throw e;
			}
		}
		return null;
	}

	/**
	 * Gets the meta-model types.
	 * 
	 * @return the meta-model objects, or an empty list
	 */
	@SuppressWarnings("cast")
	public List<EClassifier> getTypes() {
		if (getCST() != null) {
			org.eclipse.acceleo.model.mtl.Module vAST = source.getAST();
			OCLParser oclParser;
			if (vAST == null) {
				createAST();
				vAST = source.getAST();
			}
			if (vAST != null) {
				URI uri = vAST.eResource().getURI();
				vAST = (org.eclipse.acceleo.model.mtl.Module)EcoreUtil.copy(vAST);
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = ModelUtils.createResource(uri, resourceSet);
				resource.getContents().add(vAST);
				oclParser = new OCLParser(resource);
				List<URI> dependenciesURIs = getAccessibleOutputFiles();
				loadImportsDependencies(vAST, dependenciesURIs);
				loadExtendsDependencies(vAST, dependenciesURIs);
				oclParser.addRecursivelyMetamodelsToScope(vAST);
				List<EClassifier> result = oclParser.getTypes();
				Iterator<Resource> resources = resourceSet.getResources().iterator();
				while (resources.hasNext()) {
					resources.next().unload();
				}
				oclParser.dispose();
				return result;
			}
		}
		return new ArrayList<EClassifier>();
	}

	/**
	 * Gets all the accessible output files (EMTL) of this project. It means the files of the project and the
	 * files of the required plugins.
	 * 
	 * @return the URIs of the output files, there are 'plugin' URIs and 'workspace' URIs...
	 */
	public List<URI> getAccessibleOutputFiles() {
		if (accessibleOutputFiles == null) {
			if (acceleoProject != null) {
				accessibleOutputFiles = acceleoProject.getAccessibleOutputFiles();
			}
		}
		if (accessibleOutputFiles != null) {
			return new ArrayList<URI>(accessibleOutputFiles);
		}
		return new ArrayList<URI>();
	}

	/**
	 * You can use the method 'getAccessibleOutputFiles' to get all the accessible output files (EMTL) of the
	 * current project (It means the files of the current project and the files of the required plugins).
	 * There is a cache on 'getAccessibleOutputFiles'. This is a way to clear this cache.
	 */
	protected void resetAccessibleOutputFilesCache() {
		accessibleOutputFiles = null;
	}

	/**
	 * Returns the text of the source.
	 * 
	 * @return the text of the source
	 */
	public String getText() {
		return source.getBuffer().toString();
	}

	/**
	 * Creates an AST model. You can get it with the method <code>getAST</code>. The resolution step isn't
	 * executed.
	 */
	protected void createAST() {
		URI fileURI;
		if (file != null) {
			IPath outputPath = acceleoProject.getOutputFilePath(file);
			if (outputPath != null) {
				fileURI = URI.createPlatformResourceURI(outputPath.toString(), false);
			} else {
				fileURI = URI.createPlatformResourceURI(file.getFullPath().removeFileExtension()
						.addFileExtension(IAcceleoConstants.EMTL_FILE_EXTENSION).toString(), false);
			}
		} else {
			fileURI = URI.createURI(DEFAULT_EMTL_URI);
		}
		ResourceSet oResourceSet = new ResourceSetImpl();
		Resource oResource = ModelUtils.createResource(fileURI, oResourceSet);
		List<URI> resourceSetURIs = new ArrayList<URI>();
		resourceSetURIs.add(fileURI);
		// We don't have to create the CST : source.createCST();
		source.createAST(oResource);
	}

	/**
	 * Gets the nearest AST node at the given position. The AST will be resolved at that position.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest AST node
	 */
	public ASTNode getResolvedASTNode(int posBegin, int posEnd) {
		if (posBegin > -1) {
			org.eclipse.acceleo.model.mtl.Module vAST = getAST();
			if (vAST != null) {
				List<URI> dependenciesURIs = getAccessibleOutputFiles();
				loadImportsDependencies(vAST, dependenciesURIs);
				loadExtendsDependencies(vAST, dependenciesURIs);
				source.resolveAST(posBegin, posEnd);
				ASTNode candidate = null;
				ASTNode childrenCandidate = getChildrenCandidate(vAST, posBegin, posEnd);
				while (childrenCandidate != null) {
					candidate = childrenCandidate;
					childrenCandidate = getChildrenCandidate(candidate, posBegin, posEnd);
				}
				return candidate;
			}
		}
		return null;
	}

	/**
	 * Evaluates if the module is a valid candidate. The module is considered as a valid candidate if the
	 * element we are looking for has its begin and end position within the module header.
	 * 
	 * @param posBegin
	 *            The begin position
	 * @param posEnd
	 *            The end position
	 * @param inAST
	 *            Indicates if we search in the AST (true) or the CST (false).
	 * @return true if the module is a valid candidate
	 */
	public boolean isInModuleHeader(int posBegin, int posEnd, boolean inAST) {
		int beginHeaderModule = -1;
		if (inAST) {
			if (this.getAST() == null) {
				this.createAST();
			}
			beginHeaderModule = this.getAST().getStartHeaderPosition();
		} else {
			beginHeaderModule = this.getCST().getStartPosition();
		}
		int endHeaderModule = this.source.getBuffer().indexOf(
				IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.DEFAULT_END, beginHeaderModule);

		if (posBegin >= beginHeaderModule && posBegin <= endHeaderModule && posEnd >= beginHeaderModule
				&& posEnd <= endHeaderModule) {
			if (posBegin >= posEnd) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the nearest AST node at the given position.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest AST node
	 */
	public ASTNode getASTNode(int posBegin, int posEnd) {
		if (posBegin > -1) {
			org.eclipse.acceleo.model.mtl.Module vAST = getAST();
			if (vAST != null) {
				List<URI> dependenciesURIs = getAccessibleOutputFiles();
				loadImportsDependencies(vAST, dependenciesURIs);
				loadExtendsDependencies(vAST, dependenciesURIs);
				ASTNode candidate = null;
				ASTNode childrenCandidate = getChildrenCandidate(vAST, posBegin, posEnd);
				while (childrenCandidate != null) {
					candidate = childrenCandidate;
					childrenCandidate = getChildrenCandidate(candidate, posBegin, posEnd);
				}
				return candidate;
			}
		}
		return null;
	}

	/**
	 * Gets the nearest AST node at the given position without resolving the imports and the extends of the
	 * AST.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest AST node
	 */
	public ASTNode getASTNodeWithoutImportsExtends(int posBegin, int posEnd) {
		if (posBegin > -1) {
			org.eclipse.acceleo.model.mtl.Module vAST = getAST();
			if (vAST != null) {
				ASTNode candidate = null;
				ASTNode childrenCandidate = getChildrenCandidate(vAST, posBegin, posEnd);
				while (childrenCandidate != null) {
					candidate = childrenCandidate;
					childrenCandidate = getChildrenCandidate(candidate, posBegin, posEnd);
				}
				return candidate;
			}
		}
		return null;
	}

	/**
	 * Gets the nearest AST child at the given position. It browses the children of the given candidate and
	 * returns the nearest children if it exists.
	 * 
	 * @param candidate
	 *            is the current candidate to browse
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the nearest AST node in the children of the current candidate
	 */
	private ASTNode getChildrenCandidate(EObject candidate, int posBegin, int posEnd) {
		Iterator<EObject> itContents = candidate.eContents().iterator();
		while (itContents.hasNext()) {
			ASTNode astNode = null;
			EObject eContent = itContents.next();
			if (eContent instanceof ASTNode) {
				astNode = (ASTNode)eContent;
			} else if (eContent instanceof CollectionItem) {
				astNode = ((CollectionItem)eContent).getItem();
			}
			if (astNode != null) {
				int startPosition = astNode.getStartPosition();
				int endPosition = astNode.getEndPosition();
				if (startPosition > -1 && endPosition > -1) {
					if (startPosition <= posBegin && endPosition >= posEnd) {
						return astNode;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Completion choices will be ordered with this comparator.
	 * <p>
	 * In short, choices will be divided in four groups, in the following order :
	 * <ol>
	 * <li>Variables</li>
	 * <li>Properties</li>
	 * <li>Templates</li>
	 * <li>Queries</li>
	 * <li>Operations</li>
	 * <li>&quot;Everything else&quot;</li>
	 * </ol>
	 * Within each of these groups, choices will be alphabetically ordered.
	 * </p>
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected class ChoiceComparator implements Comparator<Choice> {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Choice arg0, Choice arg1) {
			int value;
			if (arg0.getKind() == ChoiceKind.VARIABLE) {
				if (arg1.getKind() == ChoiceKind.VARIABLE) {
					value = arg0.getName().compareTo(arg1.getName());
				} else {
					// Variable come before anything else
					value = -1;
				}
			} else if (arg0.getKind() == ChoiceKind.PROPERTY) {
				if (arg1.getKind() == ChoiceKind.VARIABLE) {
					// Properties come after variables
					value = 1;
				} else if (arg1.getKind() == ChoiceKind.PROPERTY) {
					value = arg0.getName().compareTo(arg1.getName());
				} else {
					// Properties come right after variables
					value = -1;
				}
			} else if (arg0.getKind() == ChoiceKind.OPERATION) {
				if (arg1.getKind() == ChoiceKind.VARIABLE) {
					// Operations come after variables ...
					value = 1;
				} else if (arg1.getKind() == ChoiceKind.PROPERTY) {
					// ... and after properties
					value = 1;
				} else if (arg1.getKind() == ChoiceKind.OPERATION) {
					value = compareOperations(arg0, arg1);
				} else {
					// Operations come right after properties
					value = -1;
				}
			} else {
				if (arg1.getKind() == ChoiceKind.VARIABLE) {
					value = 1;
				} else if (arg1.getKind() == ChoiceKind.PROPERTY) {
					value = 1;
				} else if (arg1.getKind() == ChoiceKind.OPERATION) {
					value = 1;
				} else {
					value = arg0.getName().compareTo(arg1.getName());
				}
			}
			return value;
		}
	}

	/**
	 * This will handle the comparison of two 'operation' kind choices as we need special handling for
	 * template and query comparison.
	 * 
	 * @param arg0
	 *            The first of the two choices to compare.
	 * @param arg1
	 *            The second of the two choices to compare.
	 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to,
	 *         or greater than the second.
	 */
	protected int compareOperations(Choice arg0, Choice arg1) {
		int value;
		// Templates and queries require special handling
		boolean arg0IsTemplate = arg0 instanceof AcceleoCompletionChoice
				&& ((AcceleoCompletionChoice)arg0).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Template;
		boolean arg1IsTemplate = arg1 instanceof AcceleoCompletionChoice
				&& ((AcceleoCompletionChoice)arg1).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Template;
		boolean arg0IsQuery = arg0 instanceof AcceleoCompletionChoice
				&& ((AcceleoCompletionChoice)arg0).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Query;
		boolean arg1IsQuery = arg1 instanceof AcceleoCompletionChoice
				&& ((AcceleoCompletionChoice)arg1).getAcceleoElement() instanceof org.eclipse.acceleo.model.mtl.Query;

		if (arg0IsTemplate) {
			if (arg1IsTemplate) {
				value = arg0.getName().compareTo(arg1.getName());
			} else {
				// Templates come before queries and regular operations
				value = -1;
			}
		} else if (arg0IsQuery) {
			if (arg1IsTemplate) {
				// Queries come after templates
				value = 1;
			} else if (arg1IsQuery) {
				value = arg0.getName().compareTo(arg1.getName());
			} else {
				// Queries come before regular operations
				value = -1;
			}
		} else {
			if (arg1IsTemplate) {
				// Regular operations come after everything
				value = 1;
			} else if (arg1IsQuery) {
				// Regular operations come after everything
				value = 1;
			} else {
				value = arg0.getName().compareTo(arg1.getName());
			}
		}

		return value;
	}
}
