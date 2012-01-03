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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoBuilderUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AcceleoRefactoringUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.ChangeDescriptor;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringChangeDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class is the central class in the Rename Module refactoring process.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameModuleRefactoring extends Refactoring {

	/**
	 * Module.
	 */
	private static final String MODULE = "module"; //$NON-NLS-1$

	/**
	 * New name.
	 */
	private static final String NEWNAME = "newName"; //$NON-NLS-1$

	/**
	 * The current module.
	 */
	protected Module fModule;

	/**
	 * The current project.
	 */
	protected IProject fProject;

	/**
	 * The name of the new module.
	 */
	protected String fNewModuleName;

	/**
	 * The files that will be impacted by the changes and the changes.
	 */
	private Map<IFile, TextFileChange> fChanges;

	/**
	 * The refactoring process that will be used to rename the java file.
	 */
	private Refactoring javaCompUnitRenameRefactoring;

	/**
	 * The java refactoring contribution.
	 */
	private RefactoringContribution javaCompUnitRefactoringContribution;

	/**
	 * The java refactoring descriptor.
	 */
	private RenameJavaElementDescriptor javaCompUnitRefactoringDescriptor;

	/**
	 * The file.
	 */
	private IFile file;

	/**
	 * Name of our refactoring.
	 */
	private final String title = AcceleoUIMessages
			.getString("AcceleoEditorRenameModuleRefactoring.RenameModuleTitle"); //$NON-NLS-1$

	/**
	 * Indicates if we should rename the module file.
	 */
	private final boolean renameModule;

	/**
	 * Indicates if we should rename the java file.
	 */
	private boolean renameJavaFile;

	/**
	 * The constructor.
	 */
	public AcceleoRenameModuleRefactoring() {
		super();
		this.renameJavaFile = true;
		this.renameModule = true;
	}

	/**
	 * The constructor.
	 * 
	 * @param renameModuleFile
	 *            Indicates whether or not we will rename the file in which the module is declared.
	 */
	public AcceleoRenameModuleRefactoring(final boolean renameModuleFile) {
		super();
		this.renameJavaFile = true;
		this.renameModule = renameModuleFile;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		try {
			monitor.beginTask(AcceleoUIMessages
					.getString("AcceleoEditorRenameRefactoring.CheckingPreconditions"), 1); //$NON-NLS-1$

			fChanges = new LinkedHashMap<IFile, TextFileChange>();
			this.putChangesFromWorkspace(monitor);

			if (this.renameJavaFile) {
				this.javaCompUnitRefactoringDescriptor.setNewName(this.fNewModuleName.substring(0, 1)
						.toUpperCase()
						+ this.fNewModuleName.substring(1));
				this.javaCompUnitRenameRefactoring = this.javaCompUnitRefactoringDescriptor
						.createRefactoring(status);
				status.merge(this.javaCompUnitRenameRefactoring.checkInitialConditions(monitor));
				status.merge(this.javaCompUnitRenameRefactoring.checkFinalConditions(monitor));
			}
		} finally {
			monitor.done();
		}
		return status;
	}

	/**
	 * Find the changes and put them in the fChanges map.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 */
	private void putChangesFromWorkspace(final IProgressMonitor monitor) {
		// find the sequences from our module
		AcceleoProject acceleoProject = new AcceleoProject(this.fProject);
		List<Sequence> sequencesToFind = AcceleoBuilderUtils.getImportSequencesToSearch(acceleoProject,
				this.file);

		// we take all the mtl files in all accessible project in the workspace
		for (IFile mtlFile : this.getMTLFilesFromWorkspace()) {
			// we create changes for all these mtl files
			this.createChangesForFileWithDependencies(mtlFile, sequencesToFind);
		}

		final StringBuffer fileModuleContent = FileContent.getFileContent(this.file.getLocation().toFile());

		// rename [module this.fModule.geTName()] in the file "this.file"
		this.createChangesForModuleDefinition(fileModuleContent);
	}

	/**
	 * Returns the list of all MTL files in the workspace.
	 * 
	 * @return The list of all MTL files in the workspace.
	 */
	private List<IFile> getMTLFilesFromWorkspace() {
		// find all the projects in the workspace
		List<IFile> fileList = new ArrayList<IFile>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {

			// we skip non accessible projects
			if (!project.isAccessible()) {
				continue;
			}

			try {
				// we find all the mtl files in the current project.
				AcceleoBuilderUtils.members(fileList, project, IAcceleoConstants.MTL_FILE_EXTENSION,
						AcceleoRefactoringUtils.getOutputFolder(project));
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}

		return fileList;
	}

	/**
	 * Create a change for all the MTL files that import our module and therefore have a dependency with it.
	 * 
	 * @param mtlFile
	 *            The file for which we will create a change.
	 * @param sequencesToFind
	 *            The list of sequences to find in that file.
	 */
	private void createChangesForFileWithDependencies(final IFile mtlFile,
			final List<Sequence> sequencesToFind) {
		File ioFile = mtlFile.getLocation().toFile();

		// we find the list of all files that import our module
		final StringBuffer mtlContent = FileContent.getFileContent(ioFile);

		for (Sequence sequence : sequencesToFind) {
			Region region = sequence.search(mtlContent);

			// if we find the region that uses our module, we change it.
			if (region.b() > -1) {
				TextFileChange tfc = null;
				MultiTextEdit edit = null;

				if (this.fChanges.containsKey(mtlFile)
						&& this.fChanges.get(mtlFile).getEdit() instanceof MultiTextEdit) {
					tfc = this.fChanges.get(mtlFile);
					edit = (MultiTextEdit)this.fChanges.get(mtlFile).getEdit();
				} else {
					tfc = new TextFileChange(this.title, mtlFile);
					edit = new MultiTextEdit();
					tfc.setEdit(edit);
					tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
				}

				final int offset = region.e() - this.fModule.getName().length();
				// final int offset = mtlContent.indexOf(this.fModule.getName(), region.b());
				edit.addChild(new ReplaceEdit(offset, this.fModule.getName().length(), this.fNewModuleName));

				this.fChanges.put(mtlFile, tfc);

			}
		}
	}

	/**
	 * Creates changes for the definition of the module.
	 * 
	 * @param fileModuleContent
	 *            The content of the file.
	 */
	private void createChangesForModuleDefinition(final StringBuffer fileModuleContent) {
		if (this.fModule != null) {
			final Sequence sequence = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.MODULE,
					this.fModule.getName());
			Region region = sequence.search(fileModuleContent);
			if (region.b() > -1) {
				TextFileChange tfc = null;
				MultiTextEdit edit = null;

				if (this.fChanges.containsKey(this.file)
						&& this.fChanges.get(this.file).getEdit() instanceof MultiTextEdit) {
					tfc = this.fChanges.get(this.file);
					edit = (MultiTextEdit)this.fChanges.get(this.file).getEdit();
				} else {
					tfc = new TextFileChange(this.title, this.file);
					edit = new MultiTextEdit();
					tfc.setEdit(edit);
					tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
				}

				final int offset = region.e() - this.fModule.getName().length();
				// final int offset = fileModuleContent.indexOf(this.fModule.getName(), publicRegion.b());
				edit.addChild(new ReplaceEdit(offset, this.fModule.getName().length(), this.fNewModuleName));

				this.fChanges.put(this.file, tfc);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {

		RefactoringStatus status = new RefactoringStatus();

		ICompilationUnit element = AcceleoRefactoringUtils.getJavaCompilationUnitFromModuleFile(
				this.fProject, this.file);
		if (element == null || !element.exists()) {
			this.renameJavaFile = false;
		}

		if (this.fModule == null) {
			status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
					.getString("AcceleoEditorRenameModuleRefactoring.NoModuleSpecified"))); //$NON-NLS-1$
		} else if (this.renameJavaFile) {
			assert element != null;
			// ID of the rename compilation unit refactoring process of the JDT
			this.javaCompUnitRefactoringContribution = RefactoringCore
					.getRefactoringContribution(IJavaRefactorings.RENAME_TYPE);
			RefactoringDescriptor descriptor = this.javaCompUnitRefactoringContribution.createDescriptor();
			if (descriptor instanceof RenameJavaElementDescriptor) {
				this.javaCompUnitRefactoringDescriptor = (RenameJavaElementDescriptor)descriptor;
				this.javaCompUnitRefactoringDescriptor.setDescription(AcceleoUIMessages
						.getString("AcceleoEditorRenameModuleRefactoring.JavaRenameDescription")); //$NON-NLS-1$

				this.javaCompUnitRefactoringDescriptor.setJavaElement(element.getType(this.fModule.getName()
						.substring(0, 1).toUpperCase()
						+ this.fModule.getName().substring(1)));
				this.javaCompUnitRefactoringDescriptor.setUpdateReferences(true);
			}
		}

		return status;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(final IProgressMonitor monitor) throws CoreException,
			OperationCanceledException {
		try {
			monitor.beginTask(
					AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.CreatingChanges"), 1); //$NON-NLS-1$
			final Collection<TextFileChange> changes = fChanges.values();

			// We create a composite change
			final CompositeChange change = new CompositeChange(getName()) {
				@Override
				public ChangeDescriptor getDescriptor() {
					Map<String, String> arguments = new HashMap<String, String>();
					final String project = fProject.getName();
					if (fModule != null) {
						final String description = AcceleoUIMessages
								.getString("AcceleoEditorRenameModuleRefactoring.RenamingModule") //$NON-NLS-1$
								+ " " //$NON-NLS-1$
								+ fModule.getName();
						final String comment = AcceleoUIMessages.getString(
								"AcceleoEditorRenameModuleRefactoring.RenamingModuleWithNewName", //$NON-NLS-1$
								fModule.getName(), fNewModuleName);
						arguments.put(MODULE, fModule.getName());
						arguments.put(NEWNAME, fNewModuleName);
						return new RefactoringChangeDescriptor(new AcceleoRenameVariableDescriptor(project,
								description, comment, arguments));
					}
					return new RefactoringChangeDescriptor(new AcceleoRenameVariableDescriptor(project,
							"", "", //$NON-NLS-1$ //$NON-NLS-2$
							arguments));
				}
			};

			if (this.renameJavaFile) {
				// We add the rename java file change
				change.add(this.javaCompUnitRenameRefactoring.createChange(monitor));
			}

			if (this.renameModule) {
				final CompositeChange moduleFileChange = new CompositeChange(getName());

				for (TextFileChange textFileChange : changes) {
					if (textFileChange.getFile().equals(this.file)) {
						moduleFileChange.add(textFileChange);
					} else {
						change.add(textFileChange);
					}
				}

				final RenameResourceChange changeRenameModule = new RenameResourceChange(this.file
						.getFullPath(), this.fNewModuleName + "." + IAcceleoConstants.MTL_FILE_EXTENSION); //$NON-NLS-1$
				moduleFileChange.add(changeRenameModule);

				change.add(moduleFileChange);
			} else {
				for (TextFileChange textFileChange : changes) {
					change.add(textFileChange);
				}
			}

			return change;
		} finally {
			monitor.done();
		}
	}

	/**
	 * Sets the file.
	 * 
	 * @param f
	 *            The file.
	 */
	public void setFile(final IFile f) {
		this.file = f;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#getName()
	 */
	@Override
	public String getName() {
		return this.title;
	}

	/**
	 * Sets the current module.
	 * 
	 * @param currentModule
	 *            The current module.
	 */
	public void setModule(final Module currentModule) {
		this.fModule = currentModule;
	}

	/**
	 * Returns the module.
	 * 
	 * @return The module.
	 */
	public Module getModule() {
		return this.fModule;
	}

	/**
	 * Sets the new name of the module.
	 * 
	 * @param text
	 *            The new name of module.
	 * @return The refactoring status.
	 */
	public RefactoringStatus setNewModuleName(final String text) {
		this.fNewModuleName = text;
		final RefactoringStatus status = checkModuleName(this.fNewModuleName);
		status.merge(checkOverLoading());
		return status;
	}

	/**
	 * Check that the new name does not create any conflict with an existing module.
	 * 
	 * @return The refactoring status.
	 */
	private RefactoringStatus checkOverLoading() {
		RefactoringStatus status = new RefactoringStatus();

		List<IFile> fileList = new ArrayList<IFile>();
		try {
			AcceleoBuilderUtils.members(fileList, this.fProject, IAcceleoConstants.MTL_FILE_EXTENSION,
					AcceleoRefactoringUtils.getOutputFolder(this.fProject));

			// we are looking for all mtl files in the same package as the module file
			// that have the name of the fNemModuleName + "." + mtl
			for (IFile iFile : fileList) {
				if (iFile.getParent() != null && iFile.getParent().equals(this.file.getParent())) {
					if (iFile.getName().equals(
							this.fNewModuleName + "." + IAcceleoConstants.MTL_FILE_EXTENSION)) { //$NON-NLS-1$
						status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
								.getString("AcceleoEditorRenameModuleRefactoring.ModuleOverloadingError"))); //$NON-NLS-1$
						break;
					}
				}
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}

		return status;
	}

	/**
	 * Checks the name of the module.
	 * 
	 * @param name
	 *            The name of the module.
	 * @return The refactoring status.
	 */
	private RefactoringStatus checkModuleName(final String name) {
		RefactoringStatus status = new RefactoringStatus();
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isJavaIdentifierPart(name.charAt(i))) {
				status.merge(RefactoringStatus.createErrorStatus(AcceleoUIMessages
						.getString("AcceleoEditorRenameModuleRefactoring.InvalidModuleName"))); //$NON-NLS-1$
				break;
			}
		}
		return status;
	}

	/**
	 * Initialize.
	 * 
	 * @param arguments
	 *            The argument of the initialization.
	 * @return The refactoring status.
	 */
	public RefactoringStatus initialize(final Map<String, String> arguments) {
		final RefactoringStatus status = new RefactoringStatus();
		String value = arguments.get(MODULE);
		if (value != null) {
			// I'm not sure I need to do something here, so let's do nothing instead :)
		}
		value = arguments.get(NEWNAME);
		if (value != null) {
			this.setNewModuleName(value);
		}
		return status;
	}

	/**
	 * Sets the project.
	 * 
	 * @param project
	 *            The current project of the module.
	 */
	public void setProject(final IProject project) {
		this.fProject = project;
	}
}
