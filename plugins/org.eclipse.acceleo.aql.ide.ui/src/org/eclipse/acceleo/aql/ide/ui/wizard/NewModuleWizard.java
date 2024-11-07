/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

/**
 * The new {@link Module} wizard.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class NewModuleWizard extends Wizard implements INewWizard {

	/**
	 * The ok status message.
	 */
	private static final String OK_MESSAGE = "Acceleo 4 module %s created.";

	/**
	 * The new line {@link String}.
	 */
	private static final String NEW_LINE = System.lineSeparator();

	/**
	 * The {@link Job} writing the {@link Module} file.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private class FinishJob extends Job {

		/**
		 * The {@link ModuleConfiguration}.
		 */
		private ModuleConfiguration moduleConfiguration;

		public FinishJob(ModuleConfiguration moduleConfiguration) {
			super("Creating Acceleo 4 module: " + moduleConfiguration.getModuleFile().getFullPath());
			this.moduleConfiguration = moduleConfiguration;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			final IFile moduleFile = moduleConfiguration.getModuleFile();
			IStatus res = new Status(IStatus.OK, AcceleoUIPlugin.PLUGIN_ID, String.format(OK_MESSAGE, moduleFile
					.getFullPath()));

			final org.eclipse.acceleo.Module module = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createModule();
			module.setName(moduleConfiguration.getModuleName());
			final ModuleDocumentation moduleDocumentation = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createModuleDocumentation();
			module.setDocumentation(moduleDocumentation);
			moduleDocumentation.setDocumentedElement(module);
			final CommentBody moduleDocumentationBody = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createCommentBody();
			moduleDocumentationBody.setValue(NEW_LINE + " * The documentation of the module " + module
					.getName() + "." + NEW_LINE);
			final String username = System.getProperty("user.name");
			if (username != null && !"".equals(username)) {
				moduleDocumentationBody.setValue(moduleDocumentationBody.getValue() + " * "
						+ AcceleoParser.AUTHOR_TAG + username + NEW_LINE);
			}
			moduleDocumentationBody.setValue(moduleDocumentationBody.getValue() + " * "
					+ AcceleoParser.VERSION_TAG + "1.0.0" + NEW_LINE);
			moduleDocumentationBody.setValue(moduleDocumentationBody.getValue() + " * "
					+ AcceleoParser.SINCE_TAG + "1.0.0" + NEW_LINE);
			moduleDocumentationBody.setValue(moduleDocumentationBody.getValue() + " *");

			moduleDocumentation.setBody(moduleDocumentationBody);
			module.setDocumentation(moduleDocumentation);
			for (String nsURI : moduleConfiguration.getNsURIs()) {
				final Metamodel metamodel = AcceleoPackage.eINSTANCE.getAcceleoFactory().createMetamodel();
				final EPackage ePkg = EPackage.Registry.INSTANCE.getEPackage(nsURI);
				metamodel.setReferencedPackage(ePkg);
				module.getMetamodels().add(metamodel);
			}

			if (moduleConfiguration.getModuleElementEClass() == AcceleoPackage.eINSTANCE.getTemplate()) {
				final Template template = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTemplate();
				template.setName(moduleConfiguration.getModuleElementName());
				template.setVisibility(VisibilityKind.PUBLIC);
				template.getParameters().add(createVariable(moduleConfiguration
						.getModuleElementParameterType()));
				if (moduleConfiguration.isGenerateDocumentation()) {
					template.setDocumentation(createModuleElementDocumentation(template));
					if (moduleConfiguration.isMainTemplate()) {
						final CommentBody body = template.getDocumentation().getBody();
						body.setValue(body.getValue() + " " + AcceleoParser.MAIN_TAG + NEW_LINE + " *");
					}
				} else if (moduleConfiguration.isMainTemplate()) {
					final Comment mainComment = AcceleoPackage.eINSTANCE.getAcceleoFactory().createComment();
					final CommentBody mainCommentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory()
							.createCommentBody();
					mainCommentBody.setValue(AcceleoParser.MAIN_TAG);
					mainComment.setBody(mainCommentBody);
					module.getModuleElements().add(mainComment);
				}
				template.setBody(createTemplateBody(moduleConfiguration));
				module.getModuleElements().add(template);
			} else if (moduleConfiguration.getModuleElementEClass() == AcceleoPackage.eINSTANCE.getQuery()) {
				final Query query = AcceleoPackage.eINSTANCE.getAcceleoFactory().createQuery();
				query.setName(moduleConfiguration.getModuleElementName());
				query.setVisibility(VisibilityKind.PUBLIC);
				query.getParameters().add(createVariable(moduleConfiguration
						.getModuleElementParameterType()));

				final QueryBuilderEngine queryBuilder = new QueryBuilderEngine();
				final AstResult astResultReturnType = queryBuilder.build(moduleConfiguration
						.getModuleElementParameterType());
				query.setType(astResultReturnType);
				query.setTypeAql(astResultReturnType.getAst());

				query.setBody(createExpression(query.getParameters().get(0).getName()));

				if (moduleConfiguration.isGenerateDocumentation()) {
					query.setDocumentation(createModuleElementDocumentation(query));
				}
				module.getModuleElements().add(query);
			}

			final AcceleoAstSerializer serializer = new AcceleoAstSerializer(NEW_LINE);
			try {
				module.setEncoding(moduleFile.getCharset());
				final String moduleString = serializer.serialize(module);
				final ByteArrayInputStream moduleInputStream = new ByteArrayInputStream(moduleString.getBytes(
						moduleFile.getCharset()));
				moduleFile.create(moduleInputStream, true, monitor);
			} catch (CoreException e) {
				res = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, "Can't write " + moduleFile
						.getFullPath(), e);
			} catch (UnsupportedEncodingException e) {
				res = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, "Can't write " + moduleFile
						.getFullPath(), e);
			}

			return res;
		}

		/**
		 * Creates the {@link Block} template body from the given {@link ModuleConfiguration}.
		 * 
		 * @param configuration
		 *            the {@link ModuleConfiguration}
		 * @return the created the {@link Block} template body from the given {@link ModuleConfiguration}
		 */
		private Block createTemplateBody(ModuleConfiguration configuration) {
			final Block res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();

			final String initialContent;
			if (configuration.isIsInitialized()) {
				IFile initialContentFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
						configuration.getInitializationPath()));
				String fileContent;
				try {
					fileContent = AcceleoUtil.getContent(initialContentFile.getContents(), initialContentFile
							.getCharset());
				} catch (IOException e) {
					fileContent = "";//$NON-NLS-1$
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID,
							"unable to load intial file content.", e));
					e.printStackTrace();
				} catch (CoreException e) {
					fileContent = "";//$NON-NLS-1$
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID,
							"unable to load intial file content.", e));
				}
				initialContent = fileContent;
			} else {
				initialContent = "";//$NON-NLS-1$
			}

			res.setInlined(false);
			final TextStatement text = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTextStatement();
			if (configuration.isGenerateFile()) {
				text.setValue(NEW_LINE);
			} else {
				final String newText = StringServices.NEW_LINE_PATTERN.matcher(initialContent).replaceAll(
						NEW_LINE + "  ") + NEW_LINE;
				text.setValue(newText);
			}
			if (configuration.isGenerateFile()) {
				final FileStatement fileStatement = AcceleoPackage.eINSTANCE.getAcceleoFactory()
						.createFileStatement();
				fileStatement.setUrl(createExpression("'file.txt'"));
				fileStatement.setMode(OpenModeKind.OVERWRITE);
				fileStatement.setCharset(createExpression("'UTF-8'"));
				final Block fileBlock = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();
				fileBlock.setInlined(false);
				final TextStatement fileText = AcceleoPackage.eINSTANCE.getAcceleoFactory()
						.createTextStatement();
				fileText.setValue(getTextStatementValue(initialContent));
				fileBlock.getStatements().add(fileText);
				fileStatement.setBody(fileBlock);
				res.getStatements().add(fileStatement);
			}
			res.getStatements().add(text);

			return res;
		}

		/**
		 * Gets the {@link TextStatement} value for the given initial content.
		 * 
		 * @param initialContent
		 *            the initial content
		 * @return the {@link TextStatement} value for the given initial content
		 */
		private String getTextStatementValue(final String initialContent) {
			String res;

			final String emptyLineReplacement = UUID.randomUUID().toString() + UUID.randomUUID().toString()
					+ UUID.randomUUID().toString();

			res = StringServices.EMPTY_LINE_PATTERN.matcher(initialContent).replaceAll(emptyLineReplacement);
			res = StringServices.NEW_LINE_PATTERN.matcher(res).replaceAll(NEW_LINE + "    ");
			res = res.replace(emptyLineReplacement, NEW_LINE + NEW_LINE + "    ");

			res = res.replace("[", "['['/]");

			return res + NEW_LINE + "  ";
		}

		/**
		 * Creates the {@link ModuleElement}'s {@link Documentation}.
		 * 
		 * @param moduleElement
		 *            the {@link ModuleElement}
		 * @return created the {@link ModuleElement}'s {@link Documentation}
		 */
		private Documentation createModuleElementDocumentation(
				org.eclipse.acceleo.ModuleElement moduleElement) {
			final ModuleElementDocumentation res = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createModuleElementDocumentation();

			final CommentBody documentationBody = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createCommentBody();
			res.setBody(documentationBody);
			if (moduleElement instanceof Template) {
				final Template template = (Template)moduleElement;
				documentationBody.setValue(NEW_LINE + " * The documentation of the template " + template
						.getName() + "." + NEW_LINE + " *");
				documentationBody.setValue(documentationBody.getValue() + " @param " + template
						.getParameters().get(0).getName() + NEW_LINE + " *");
				res.setDocumentedElement(template);
			} else if (moduleElement instanceof Query) {
				final Query query = (Query)moduleElement;
				documentationBody.setValue(NEW_LINE + " * The documentation of the query " + query.getName()
						+ "." + NEW_LINE + " *");
				documentationBody.setValue(documentationBody.getValue() + " @param " + query.getParameters()
						.get(0).getName() + NEW_LINE + " *");
				res.setDocumentedElement(query);
			}

			return res;
		}

		/**
		 * Creates an {@link Expression} for the given expression {@link String}.
		 * 
		 * @param expression
		 *            the expression {@link String}
		 * @return the created {@link Expression} for the given expression {@link String}
		 */
		private Expression createExpression(String expression) {
			final Expression res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpression();

			final QueryBuilderEngine queryBuilder = new QueryBuilderEngine();
			final AstResult astResult = queryBuilder.build(expression);

			res.setAst(astResult);
			res.setAql(astResult.getAst());

			return res;
		}

		/**
		 * Creates a {@link Variable} with the given parameter type.
		 * 
		 * @param moduleElementParameterType
		 *            the parameter type
		 * @return the created {@link Variable} with the given parameter type
		 */
		private Variable createVariable(String moduleElementParameterType) {
			final Variable res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createVariable();

			final QueryBuilderEngine queryBuilder = new QueryBuilderEngine();
			final AstResult astResult = queryBuilder.build(moduleElementParameterType);

			res.setName("parameter");
			res.setType(astResult);
			res.setTypeAql(astResult.getAst());

			return res;
		}

	}

	/**
	 * The destination {@link IContainer}.
	 */
	private String container;

	/**
	 * The {@link ModulePage}.
	 */
	private ModulePage modulePage;

	/**
	 * The active {@link IWorkbenchPage}.
	 */
	private IWorkbenchPage activePage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		IContainer firstContainer = null;
		if (selection != null && !selection.isEmpty()) {
			IStructuredSelection aSelection = selection;
			if (aSelection.size() > 0) {
				Object element = aSelection.getFirstElement();
				if (element instanceof IAdaptable) {
					element = ((IAdaptable)element).getAdapter(IResource.class);
				}
				if (element instanceof IContainer) {
					firstContainer = (IContainer)element;
				} else if (element instanceof IResource) {
					firstContainer = ((IResource)element).getParent();
				}
			}
		}

		if (firstContainer != null) {
			container = firstContainer.getFullPath().toString();
		} else {
			container = "";
		}
		activePage = workbench.getActiveWorkbenchWindow().getActivePage();
	}

	@Override
	public void addPages() {
		modulePage = new ModulePage(container);
		addPage(modulePage);
	}

	@Override
	public boolean performFinish() {
		final FinishJob job = new FinishJob(modulePage.getModuleConfiguration());
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		try {
			job.join();

			// Open the module created
			final IFile moduleFile = modulePage.getModuleConfiguration().getModuleFile();
			if (moduleFile != null && moduleFile.isAccessible()) {
				try {
					IDE.openEditor(activePage, moduleFile);
				} catch (PartInitException e) {
					// nothing to do here
				}
			}
		} catch (InterruptedException e) {
			// nothing to do here
		}

		return true;
	}

}
