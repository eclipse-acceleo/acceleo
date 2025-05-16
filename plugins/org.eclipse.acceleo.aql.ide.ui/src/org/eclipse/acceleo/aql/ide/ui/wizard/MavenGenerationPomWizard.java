/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.wizard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.GenerationCompareEditorInput;
import org.eclipse.acceleo.aql.ide.ui.module.main.MavenGenerationPomGenerator;
import org.eclipse.acceleo.aql.ide.ui.module.main.StandaloneGenerator;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The maven generation pom wizard.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MavenGenerationPomWizard extends Wizard implements INewWizard {

	/**
	 * The ok status message.
	 */
	private static final String OK_MESSAGE = "Maven generation POM %s created.";

	/**
	 * The {@link Job} writing the {@link Module} file.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private class FinishJob extends Job {

		/**
		 * The {@link ModuleConfiguration}.
		 */
		private GenerationPomConfiguration pomConfiguration;

		private final Map<URI, String> preview = new HashMap<>();

		public FinishJob(GenerationPomConfiguration pomConfiguration) {
			super("Creating Maven generation POM file: " + pomConfiguration.getPomFolderURI());
			this.pomConfiguration = pomConfiguration;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus res = new Status(IStatus.OK, AcceleoUIPlugin.PLUGIN_ID, String.format(OK_MESSAGE,
					pomConfiguration.getPomFolderURI()));

			final int amountOfWork = 2;

			final SubMonitor subMonitor = SubMonitor.convert(monitor, amountOfWork);
			Monitor childMonitor = BasicMonitor.toMonitor(subMonitor.split(1));
			try {
				final StandaloneGenerator standaloneGenerator = new StandaloneGenerator(moduleFile);
				standaloneGenerator.generate(childMonitor);
				preview.putAll(standaloneGenerator.getPreview());
			} finally {
				childMonitor.done();
			}
			if (!monitor.isCanceled()) {
				childMonitor = BasicMonitor.toMonitor(subMonitor.split(1));
				try {
					final MavenGenerationPomGenerator generationPomGenerator = new MavenGenerationPomGenerator(
							moduleFile, configuration);
					generationPomGenerator.generate(childMonitor);
					preview.putAll(generationPomGenerator.getPreview());
				} finally {
					childMonitor.done();
				}
			}

			return res;
		}

		/**
		 * Gets the preview.
		 * 
		 * @return the preview
		 */
		public Map<URI, String> getPreview() {
			return preview;
		}

	}

	/**
	 * the module {@link IFile}.
	 */
	private IFile moduleFile;

	/**
	 * The {@link GenerationPomConfiguration}.
	 */
	private GenerationPomConfiguration configuration;

	/**
	 * The {@link GenerationPomConfigurationPage}.
	 */
	private GenerationPomConfigurationPage modulePage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof IFile) {
			moduleFile = (IFile)selection.getFirstElement();
			configuration = initializeGenerationPomConfiguration(moduleFile);
		}
	}

	/**
	 * Initializes the {@link GenerationPomConfiguration} with the given module {@link IFile}.
	 * 
	 * @param moduleFile
	 *            the module {@link IFile}
	 * @return the initialized {@link GenerationPomConfiguration}
	 */
	private GenerationPomConfiguration initializeGenerationPomConfiguration(IFile moduleFile) {
		final GenerationPomConfiguration res = new GenerationPomConfiguration();

		final IFile pomFile = moduleFile.getProject().getFile("pom.xml");
		if (pomFile.isAccessible()) {
			try {
				final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				final DocumentBuilder db = dbf.newDocumentBuilder();
				final Document pom = db.parse(pomFile.getLocation().toFile());
				final NodeList projectList = pom.getElementsByTagName("project");
				if (projectList != null && projectList.getLength() > 0) {
					final Element project = (Element)projectList.item(0);
					// groupId
					final NodeList groupIdList = project.getElementsByTagName("groupId");
					if (groupIdList != null && groupIdList.getLength() > 0) {
						final Element groupId = (Element)groupIdList.item(0);
						res.setGeneratorGroupId(groupId.getTextContent());
						res.setGenerationGroupId(groupId.getTextContent());
					}
					// groupId
					final NodeList artifactIdList = project.getElementsByTagName("artifactId");
					if (artifactIdList != null && artifactIdList.getLength() > 0) {
						final Element artifactId = (Element)artifactIdList.item(0);
						res.setGeneratorArtifactId(artifactId.getTextContent());
						res.setGenerationArtifactId(artifactId.getTextContent() + "-generation");
					}
					// version
					final NodeList versionList = project.getElementsByTagName("version");
					if (versionList != null && versionList.getLength() > 0) {
						final Element version = (Element)versionList.item(0);
						res.setGeneratorVersion(version.getTextContent());
						res.setGenerationVersion(version.getTextContent());
					}
				}
			} catch (IOException | SAXException | ParserConfigurationException e) {
				AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, getClass(),
						"Couldn't initialize Maven generation POM configuration from " + moduleFile
								.getFullPath(), e));
			}
		}

		return res;
	}

	@Override
	public void addPages() {
		modulePage = new GenerationPomConfigurationPage(configuration);
		addPage(modulePage);
	}

	@Override
	public boolean performFinish() {
		final FinishJob job = new FinishJob(configuration);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();

		try {
			job.join();

			final GenerationCompareEditorInput compareEditorInput = new GenerationCompareEditorInput(
					new CompareConfiguration(), job.getPreview(), ResourcesPlugin.getWorkspace().getRoot());
			if (compareEditorInput.hasDifferences()) {
				CompareUI.openCompareDialog(compareEditorInput);
			}
		} catch (InterruptedException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"Couldn't generate.", e));
		}

		return true;
	}

	/**
	 * Gets the {@link GenerationPomConfiguration}.
	 * 
	 * @return the {@link GenerationPomConfiguration}
	 */
	public GenerationPomConfiguration getConfiguration() {
		return configuration;
	}

}
