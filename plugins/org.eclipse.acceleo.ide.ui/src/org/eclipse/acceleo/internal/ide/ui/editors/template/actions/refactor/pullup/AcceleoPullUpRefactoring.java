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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AcceleoRefactoringUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * This class will realize the pull up refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpRefactoring extends Refactoring {

	/**
	 * The module from which the templates will be pulled up.
	 */
	private Module module;

	/**
	 * The list of all the templates that will be pulled up.
	 */
	private List<Template> templates;

	/**
	 * The name of the refactoring.
	 */
	private String refactoringName;

	/**
	 * The name of the module which will contains the pulled up templates.
	 */
	private String fileName;

	/**
	 * The container which will received the pulled up templates.
	 */
	private IContainer container;

	/**
	 * The file of the module.
	 */
	private IFile file;

	/**
	 * Indicates if we pull up in a new file.
	 */
	private boolean pullUpInNewFile;

	/**
	 * The constructor.
	 */
	public AcceleoPullUpRefactoring() {
		super();
	}

	/**
	 * The constructor.
	 * 
	 * @param m
	 *            The module
	 * @param name
	 *            The name of the refactoring
	 * @param iFile
	 *            The file
	 */
	public AcceleoPullUpRefactoring(final Module m, final String name, final IFile iFile) {
		super();
		this.module = m;
		this.refactoringName = name;
		this.file = iFile;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#getName()
	 */
	@Override
	public String getName() {
		return this.refactoringName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (this.module != null) {
			EList<EObject> eContents = this.module.eContents();
			for (EObject eObject : eContents) {
				if (eObject instanceof Template && ((Template)eObject).getOverrides().size() == 0) {
					return RefactoringStatus.create(Status.OK_STATUS);
				}
			}
		}
		return RefactoringStatus.createFatalErrorStatus(AcceleoUIMessages
				.getString("AcceleoEditorPullUpRefactoring.NoTemplateToPullUp")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		RefactoringStatus result = RefactoringStatus.createFatalErrorStatus(AcceleoUIMessages
				.getString("AcceleoEditorPullUpRefactoring.InvalidRefactoringParameters")); //$NON-NLS-1$
		if (this.module != null && this.templates.size() > 0 && this.fileName != null
				&& this.container != null) {
			if (this.container.getProject().isAccessible()
					&& this.container.getProject().hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
				result = RefactoringStatus.create(Status.OK_STATUS);
			}
		}
		IResource resource = this.container.findMember(this.fileName);
		if (resource instanceof IFile && resource.exists()) {
			IFile moduleFile = (IFile)resource;
			Module moduleFromFile = AcceleoRefactoringUtils.getModuleFromFile(moduleFile);
			for (Template template : this.templates) {
				EList<EObject> contents = moduleFromFile.eContents();
				for (EObject eObject : contents) {
					if (eObject instanceof Template
							&& VisibilityKind.PUBLIC.getName().equals(template.getVisibility().getName())) {
						Template temp = (Template)eObject;
						if (this.templateEquals(template, temp)) {
							result = RefactoringStatus
									.createFatalErrorStatus(AcceleoUIMessages
											.getString("AcceleoEditorPullUpRefactoring.OutputModuleHasSimilarTemplates")); //$NON-NLS-1$
						}
					}
				}
			}
			moduleFromFile.eResource().unload();
		}

		return result;
	}

	/**
	 * Indicates if two templates have the same name, and the same parameters.
	 * 
	 * @param template
	 *            The original template
	 * @param temp
	 *            The template of the module where we will pull the original template up
	 * @return true if the two templates have the same name and the same parameters
	 */
	private boolean templateEquals(Template template, Template temp) {
		boolean result = false;
		if (template.getName().equals(temp.getName())) {
			EList<Variable> parameterList1 = template.getParameter();
			EList<Variable> parameterList2 = temp.getParameter();
			if (parameterList1.size() == parameterList2.size()) {
				for (int i = 0; i < parameterList1.size(); i++) {
					Variable variable1 = parameterList1.get(i);
					Variable variable2 = parameterList2.get(i);
					if (variable1.eClass().getName().equals(variable2.eClass().getName())) {
						result = true;
					}
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Refactoring#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		CompositeChange result = new CompositeChange(this.refactoringName);

		// step 0: does the new module exists ?
		IResource resource = this.container.findMember(this.fileName);
		if (resource == null) {
			this.pullUpInNewFile = true;
		}

		// step 1: pull up
		IFile mtlFile = null;
		if (!pullUpInNewFile && resource instanceof IFile
				&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)resource).getFileExtension())) {
			mtlFile = (IFile)resource;
		} else {
			mtlFile = this.container.getFile(new Path(this.fileName));
		}

		// step 2 v1: create the new module
		if (!mtlFile.exists()) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes()); //$NON-NLS-1$
			mtlFile.create(inputStream, true, pm);
			TextFileChange newModuleChange = this.createChangeInNewModule(mtlFile);
			result.add(newModuleChange);
		} else {
			TextFileChange pulledUpTemplateChange = this.createChangeByAddingTemplates(mtlFile);
			result.add(pulledUpTemplateChange);
		}

		// step 3: add template "overrides newTemplate"
		for (Template template : this.templates) {
			TextFileChange changeOverrides = this.createChangeOverridesTemplate(template);
			result.add(changeOverrides);
		}

		Module moduleFromFile = AcceleoRefactoringUtils.getModuleFromFile(mtlFile);
		boolean alreadyExtends = false;
		List<Module> extendslist = this.module.getExtends();
		for (Module extendedModule : extendslist) {
			if (extendedModule.getNsURI().equals(moduleFromFile.getNsURI())) {
				alreadyExtends = true;
				break;
			}
		}

		if (!alreadyExtends) {
			// step 4: add oldModule "extends newModule"
			TextFileChange changeExtendsModule = this.createChangeExtendsModule(mtlFile);
			result.add(changeExtendsModule);
		}
		moduleFromFile.eResource().unload();
		this.module.eResource().unload();

		return result;
	}

	/**
	 * Create the change of the oldTemplate "overrides newTemplate".
	 * 
	 * @param template
	 *            The old template
	 * @return The text file change
	 */
	private TextFileChange createChangeOverridesTemplate(Template template) {
		TextFileChange tfc = new TextFileChange(this.refactoringName, this.file);
		MultiTextEdit edit = new MultiTextEdit();
		tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
		tfc.setEdit(edit);

		StringBuffer content = FileContent.getFileContent(this.file.getLocation().toFile());

		int offset = content.indexOf(IAcceleoConstants.DEFAULT_END, template.getStartPosition());
		int length = 0;
		edit.addChild(new ReplaceEdit(offset, length, ' ' + IAcceleoConstants.OVERRIDES + ' '
				+ template.getName()));

		return tfc;
	}

	/**
	 * Create the change of the oldModule extends the newModule.
	 * 
	 * @param mtlFile
	 *            The file of the new module
	 * @return The text file change
	 */
	private TextFileChange createChangeExtendsModule(IFile mtlFile) {
		int startHeaderPosition = this.module.getStartHeaderPosition();
		StringBuffer fileContent = FileContent.getFileContent(this.file.getLocation().toFile());
		int endHeaderPosition = fileContent.indexOf(IAcceleoConstants.DEFAULT_END_BODY_CHAR
				+ IAcceleoConstants.DEFAULT_END, startHeaderPosition);
		int endInputPosition = fileContent.indexOf(IAcceleoConstants.PARENTHESIS_END, startHeaderPosition);

		TextFileChange tfc = new TextFileChange(this.refactoringName, this.file);
		MultiTextEdit edit = new MultiTextEdit();
		tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
		tfc.setEdit(edit);

		if (this.module.getExtends().size() == 0 && endInputPosition < endHeaderPosition) {
			int length = 0;
			edit.addChild(new ReplaceEdit(endInputPosition + 1, length, ' '
					+ IAcceleoConstants.EXTENDS
					+ ' '
					+ this.fileName.substring(0, this.fileName.length()
							- IAcceleoConstants.EMTL_FILE_EXTENSION.length())));
		} else {
			int length = 0;
			edit.addChild(new ReplaceEdit(endHeaderPosition, length, IAcceleoConstants.COMMA_SEPARATOR
					+ ' '
					+ this.fileName.substring(0, this.fileName.length()
							- IAcceleoConstants.EMTL_FILE_EXTENSION.length())));
		}

		return tfc;
	}

	/**
	 * Create the change of the pulled up templates.
	 * 
	 * @param mtlFile
	 *            The file where the template are pulled to
	 * @return The text file change
	 */
	private TextFileChange createChangeByAddingTemplates(IFile mtlFile) {
		TextFileChange tfc = new TextFileChange(this.refactoringName, mtlFile);
		MultiTextEdit edit = new MultiTextEdit();
		tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
		tfc.setEdit(edit);

		StringBuffer outputFileContent = FileContent.getFileContent(mtlFile.getLocation().toFile());
		StringBuffer inputFileContent = FileContent.getFileContent(this.file.getLocation().toFile());

		int offset = outputFileContent.length();
		int length = 0;

		StringBuffer templatesText = new StringBuffer();
		int start = 0;
		int end = 0;
		for (Template template : this.templates) {
			start = template.getStartPosition();
			end = template.getEndPosition();

			templatesText.append(inputFileContent.toString().substring(start, end) + '\n');
		}

		edit.addChild(new ReplaceEdit(offset, length, templatesText.toString()));

		return tfc;
	}

	/**
	 * Creates the new module and pull up the templates.
	 * 
	 * @param mtlFile
	 *            The file where we should write the new module
	 * @return The text file change
	 */
	private TextFileChange createChangeInNewModule(IFile mtlFile) {
		TextFileChange tfc = new TextFileChange(this.refactoringName, mtlFile);
		MultiTextEdit edit = new MultiTextEdit();
		tfc.setTextType(IAcceleoConstants.MTL_FILE_EXTENSION);
		tfc.setEdit(edit);

		int offset = 0;
		int length = FileContent.getFileContent(mtlFile.getLocation().toFile()).length();

		StringBuffer moduleModels = new StringBuffer();
		for (int i = 0; i < this.module.getInput().size(); i++) {
			if (i < this.module.getInput().size() && this.module.getInput().size() > 1) {
				moduleModels.append(IAcceleoConstants.LITERAL_BEGIN
						+ this.module.getInput().get(i).getTakesTypesFrom().get(0).getNsURI()
						+ IAcceleoConstants.LITERAL_END + IAcceleoConstants.COMMA_SEPARATOR);
			} else {
				moduleModels.append(IAcceleoConstants.LITERAL_BEGIN
						+ this.module.getInput().get(i).getTakesTypesFrom().get(0).getNsURI()
						+ IAcceleoConstants.LITERAL_END);
			}

		}

		StringBuffer newModuleContent = new StringBuffer();
		StringBuffer fileContent = FileContent.getFileContent(this.file.getLocation().toFile());
		newModuleContent.append(fileContent.substring(0, this.module.getStartHeaderPosition()));
		newModuleContent.append(IAcceleoConstants.DEFAULT_BEGIN + IAcceleoConstants.MODULE + ' '
				+ this.fileName + IAcceleoConstants.PARENTHESIS_BEGIN + moduleModels.toString()
				+ IAcceleoConstants.PARENTHESIS_END + IAcceleoConstants.DEFAULT_END_BODY_CHAR
				+ IAcceleoConstants.DEFAULT_END);
		newModuleContent.append('\n');

		StringBuffer templatesText = new StringBuffer();
		int start = 0;
		int end = 0;
		for (Template template : this.templates) {
			start = template.getStartPosition();
			end = template.getEndPosition();
			templatesText.append(fileContent.toString().substring(start, end) + '\n');
		}

		edit.addChild(new ReplaceEdit(offset, length, newModuleContent.toString() + templatesText.toString()));

		return tfc;
	}

	/**
	 * Returns the module.
	 * 
	 * @return The module
	 */
	public Module getModule() {
		return this.module;
	}

	/**
	 * Sets the list of templates to pull up.
	 * 
	 * @param selectedTemplate
	 *            The templates to pull up
	 */
	public void setTemplates(List<Template> selectedTemplate) {
		this.templates = selectedTemplate;
	}

	/**
	 * Sets the destination folder of the pulled up templates.
	 * 
	 * @param element
	 *            The destination folder
	 */
	public void setContainer(IContainer element) {
		this.container = element;
	}

	/**
	 * Sets the name of the file.
	 * 
	 * @param name
	 *            The name of the file
	 */
	public void setFileName(String name) {
		this.fileName = name;
	}

	/**
	 * Returns the container.
	 * 
	 * @return The container
	 */
	public IContainer getContainer() {
		return this.container;
	}

	/**
	 * Returns the file name of the module which will contain the pulled up templates.
	 * 
	 * @return The file name of the module which will contain the pulled up templates.
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Initialize with the given argument.
	 * 
	 * @param fArguments
	 *            The arguments
	 * @return The refactoring status
	 */
	public RefactoringStatus initialize(Map<String, String> fArguments) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns true if we pull up in a new file.
	 * 
	 * @return True if we pull up in a new file.
	 */
	public boolean isPullUpInNewFile() {
		return this.pullUpInNewFile;
	}

}
