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
import java.io.InputStream;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.dialog.AbstractResourceSelectionDialog;
import org.eclipse.acceleo.aql.ide.ui.dialog.FolderSelectionDialog;
import org.eclipse.acceleo.aql.ide.ui.message.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Generation pom configuration page.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class GenerationPomConfigurationPage extends WizardPage {

	/**
	 * The Workspace button text.
	 */
	protected static final String WORKSPACE = "Workspace...";

	/**
	 * The Workspace button text.
	 */
	protected static final String FILE_SYSTEM = "File system...";

	/**
	 * The {@link GenerationPomConfiguration}.
	 */
	private final GenerationPomConfiguration pomConfiguration;

	/**
	 * The generaiton pom destination folder {@link Text}.
	 */
	private Text generationPomDestinationText;

	/**
	 * The generator group ID {@link Text}.
	 */
	private Text generatorGroupIdText;

	/**
	 * The generator artifact ID {@link Text}.
	 */
	private Text generatorArtifactIdText;

	/**
	 * The generator version {@link Text}.
	 */
	private Text generatorVersionText;

	/**
	 * The model {@link Text}.
	 */
	private Text generationInputModelText;

	/**
	 * The generation group ID {@link Text}.
	 */
	private Text generationTargetFolderText;

	/**
	 * The generation group ID {@link Text}.
	 */
	private Text generationGroupIdText;

	/**
	 * The generation artifact ID {@link Text}.
	 */
	private Text generationArtifactIdText;

	/**
	 * The generation version {@link Text}.
	 */
	private Text generationVersionText;

	/**
	 * Tells if the target pom exists.
	 */
	private boolean targetPomExists;

	/**
	 * Constructor.
	 * 
	 * @param initialContainer
	 *            the initial container
	 */
	public GenerationPomConfigurationPage(GenerationPomConfiguration pomConfiguration) {
		super("Generation Pom File");
		this.pomConfiguration = pomConfiguration;
	}

	/**
	 * Gets the edited {@link ModuleConfiguration}.
	 * 
	 * @return the {@link ModuleConfiguration}
	 */
	public GenerationPomConfiguration getPomConfiguration() {
		return pomConfiguration;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite control = new Composite(parent, parent.getStyle());
		control.setLayout(new GridLayout(1, false));
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		generationPomDestinationText = createPomDestinationComposite(control);
		generationInputModelText = createModelComposite(control);
		generationTargetFolderText = createGenerationTargetFolderComposite(control);
		createGenerationComposite(control);
		createGeneratorComposite(control);

		setControl(control);
		reloadTargetPom(pomConfiguration);
		checkErrors();
	}

	/**
	 * Creates the model {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createModelComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Generation model file:");
		final Text res = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGenerationModelPath() != null) {
			res.setText(pomConfiguration.getGenerationModelPath());
		}
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGenerationModelPath(res.getText());
				checkErrors();
			}
		});
		Button workspaceButton = createPushButton(group, WORKSPACE, null);
		workspaceButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleWorkspaceModelButton();
			}
		});
		Button fileSystemButton = createPushButton(group, FILE_SYSTEM, null);
		fileSystemButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleFileSystemModelButton();
			}
		});
		return res;
	}

	/**
	 * Creates the generation target folder {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createGenerationTargetFolderComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Generation target folder:");
		final Text res = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGenerationTargetPath() != null) {
			res.setText(pomConfiguration.getGenerationTargetPath());
		}
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGenerationTargetPath(res.getText());
				checkErrors();
			}
		});
		Button workspaceButton = createPushButton(group, WORKSPACE, null);
		workspaceButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleWorkspaceGenerationTargetFolderButton();
			}
		});
		Button fileSystemButton = createPushButton(group, FILE_SYSTEM, null);
		fileSystemButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleFileSystemGenerationTargetFolderButton();
			}
		});
		return res;
	}

	/**
	 * Creates the destination {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 * @return the created {@link Text}
	 */
	private Text createPomDestinationComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Pom Destination folder:");
		final Text res = new Text(group, SWT.BORDER);
		if (pomConfiguration.getPomFolder() != null) {
			res.setText(pomConfiguration.getPomFolder());
		}
		res.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		res.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setPomFolder(res.getText());
				reloadTargetPom(pomConfiguration);
				checkErrors();
			}
		});
		Button workspaceButton = createPushButton(group, WORKSPACE, null);
		workspaceButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleWorkspaceDestinationButton();
			}
		});
		Button fileSystemButton = createPushButton(group, FILE_SYSTEM, null);
		fileSystemButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				handleFileSystemDestinationButton();
			}
		});
		return res;
	}

	/**
	 * Creates the generation {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 */
	private void createGenerationComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Generation:");
		// groupId
		final Label groupIdLabel = new Label(group, SWT.NONE);
		groupIdLabel.setText("groupId:");
		generationGroupIdText = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGenerationGroupId() != null) {
			generationGroupIdText.setText(pomConfiguration.getGenerationGroupId());
		}
		generationGroupIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generationGroupIdText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGenerationGroupId(generationGroupIdText.getText());
				checkErrors();
			}
		});
		// artifactId
		final Label artifactIdLabel = new Label(group, SWT.NONE);
		artifactIdLabel.setText("artifactId:");
		generationArtifactIdText = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGenerationArtifactId() != null) {
			generationArtifactIdText.setText(pomConfiguration.getGenerationArtifactId());
		}
		generationArtifactIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generationArtifactIdText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGenerationArtifactId(generationArtifactIdText.getText());
				checkErrors();
			}
		});
		// version
		final Label versionLabel = new Label(group, SWT.NONE);
		versionLabel.setText("version:");
		generationVersionText = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGenerationVersion() != null) {
			generationVersionText.setText(pomConfiguration.getGenerationVersion());
		}
		generationVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generationVersionText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGenerationVersion(generationVersionText.getText());
				checkErrors();
			}
		});
	}

	/**
	 * Creates the generator {@link Composite}.
	 * 
	 * @param parent
	 *            the parent
	 */
	private void createGeneratorComposite(final Composite parent) {
		final Group group = new Group(parent, parent.getStyle());
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		group.setText("Generator:");
		// groupId
		final Label groupIdLabel = new Label(group, SWT.NONE);
		groupIdLabel.setText("groupId:");
		generatorGroupIdText = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGeneratorGroupId() != null) {
			generatorGroupIdText.setText(pomConfiguration.getGeneratorGroupId());
		}
		generatorGroupIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generatorGroupIdText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGeneratorGroupId(generatorGroupIdText.getText());
				checkErrors();
			}
		});
		// artifactId
		final Label artifactIdLabel = new Label(group, SWT.NONE);
		artifactIdLabel.setText("artifactId:");
		generatorArtifactIdText = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGeneratorArtifactId() != null) {
			generatorArtifactIdText.setText(pomConfiguration.getGeneratorArtifactId());
		}
		generatorArtifactIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generatorArtifactIdText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGeneratorArtifactId(generatorArtifactIdText.getText());
				checkErrors();
			}
		});
		// version
		final Label versionLabel = new Label(group, SWT.NONE);
		versionLabel.setText("version:");
		generatorVersionText = new Text(group, SWT.BORDER);
		if (pomConfiguration.getGeneratorVersion() != null) {
			generatorVersionText.setText(pomConfiguration.getGeneratorVersion());
		}
		generatorVersionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generatorVersionText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				pomConfiguration.setGeneratorVersion(generatorVersionText.getText());
				checkErrors();
			}
		});
	}

	private void handleWorkspaceModelButton() {
		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(getShell(), false,
				ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		dialog.setTitle("Select the model file");
		String path = generationInputModelText.getText();
		if (path != null && path.length() > 0 && new Path(path).lastSegment().length() > 0) {
			dialog.setInitialPattern(new Path(path).lastSegment());
		} else {
			String initial = "*.xmi"; //$NON-NLS-1$
			dialog.setInitialPattern(initial);
		}
		dialog.open();
		if (dialog.getResult() != null && dialog.getResult().length > 0 && dialog
				.getResult()[0] instanceof IFile) {
			generationInputModelText.setText(((IFile)dialog.getResult()[0]).getFullPath().toString());
			checkErrors();
		}
	}

	private void handleFileSystemModelButton() {
		final FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
		dialog.setText("Select the model file");
		final String selected = dialog.open();
		if (selected != null) {
			generationInputModelText.setText(URI.createFileURI(selected).toString());
			checkErrors();
		}
	}

	private void handleWorkspaceDestinationButton() {
		final AbstractResourceSelectionDialog dialog;
		if (pomConfiguration.getPomFolder() != null) {
			final IResource workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(
					pomConfiguration.getPomFolder()));
			if (workspaceResource != null) {
				dialog = new FolderSelectionDialog(getShell(), "Select the destination folder",
						pomConfiguration.getPomFolder());
			} else {
				dialog = new FolderSelectionDialog(getShell(), "Select the destination folder", null);
			}
		} else {
			dialog = new FolderSelectionDialog(getShell(), "Select the destination folder", null);
		}

		final int dialogResult = dialog.open();
		if ((dialogResult == IDialogConstants.OK_ID) && dialog.getFileName() != null && !dialog.getFileName()
				.isEmpty()) {
			generationPomDestinationText.setText(dialog.getFileName());
			checkErrors();
			reloadTargetPom(pomConfiguration);
		}
	}

	private void handleFileSystemDestinationButton() {
		final DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
		dialog.setText("Select the generation pom destination folder");
		final String selected = dialog.open();
		if (selected != null) {
			if (selected.endsWith("/")) {
				generationPomDestinationText.setText(URI.createFileURI(selected).toString());
			} else {
				generationPomDestinationText.setText(URI.createFileURI(selected).toString() + "/");
			}
			checkErrors();
			reloadTargetPom(pomConfiguration);
		}
	}

	private void handleWorkspaceGenerationTargetFolderButton() {
		final AbstractResourceSelectionDialog dialog;
		if (pomConfiguration.getGenerationTargetPath() != null) {
			final IResource workspaceResource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(
					pomConfiguration.getGenerationTargetPath()));
			if (workspaceResource != null) {
				dialog = new FolderSelectionDialog(getShell(), "Select the destination folder",
						pomConfiguration.getGenerationTargetPath());
			} else {
				dialog = new FolderSelectionDialog(getShell(), "Select the destination folder", null);
			}
		} else {
			dialog = new FolderSelectionDialog(getShell(), "Select the destination folder", null);
		}

		final int dialogResult = dialog.open();
		if ((dialogResult == IDialogConstants.OK_ID) && dialog.getFileName() != null && !dialog.getFileName()
				.isEmpty()) {
			generationTargetFolderText.setText(dialog.getFileName());
			checkErrors();
		}
	}

	private void handleFileSystemGenerationTargetFolderButton() {
		final DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
		dialog.setText("Select the generation destination folder");
		final String selected = dialog.open();
		if (selected != null) {
			if (selected.endsWith("/")) {
				generationTargetFolderText.setText(URI.createFileURI(selected).toString());
			} else {
				generationTargetFolderText.setText(URI.createFileURI(selected).toString() + "/");
			}
			checkErrors();
		}
	}

	/**
	 * Checks errors.
	 */
	private void checkErrors() {
		final IStatus status;

		if (pomConfiguration.getPomFolder() == null || pomConfiguration.getPomFolder().isEmpty()) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidPomTargetFolder"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (pomConfiguration.getGenerationModelPath() == null || pomConfiguration
				.getGenerationModelPath().isEmpty()) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGenerationModel"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (pomConfiguration.getGenerationTargetPath() == null || pomConfiguration
				.getGenerationTargetPath().isEmpty()) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGenerationFolder"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (!targetPomExists && (pomConfiguration.getGenerationGroupId() == null || pomConfiguration
				.getGenerationGroupId().isEmpty())) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGenerationGroupID"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (!targetPomExists && (pomConfiguration.getGenerationArtifactId() == null || pomConfiguration
				.getGenerationArtifactId().isEmpty())) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGenerationArtifactID"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (!targetPomExists && (pomConfiguration.getGenerationVersion() == null || pomConfiguration
				.getGenerationVersion().isEmpty())) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGenerationVersion"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (!targetPomExists && (pomConfiguration.getGeneratorGroupId() == null || pomConfiguration
				.getGeneratorGroupId().isEmpty())) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGeneratorGroupID"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (pomConfiguration.getGeneratorArtifactId() == null || pomConfiguration
				.getGeneratorArtifactId().isEmpty()) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGeneratorArtifactID"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else if (pomConfiguration.getGeneratorVersion() == null || pomConfiguration.getGeneratorVersion()
				.isEmpty()) {
			String message = AcceleoUIMessages.getString(
					"GenerationPomConfigurationPage.InvalidGeneratorVersion"); //$NON-NLS-1$
			status = new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID, message);
		} else {
			status = new Status(IStatus.OK, AcceleoUIPlugin.PLUGIN_ID, null);
		}

		applyToStatusLine(status);
	}

	/**
	 * Applies the status to the status line of a dialog page.
	 * 
	 * @param status
	 *            the status to apply
	 */
	public void applyToStatusLine(IStatus status) {
		String message = status.getMessage();
		if (message != null && message.length() == 0) {
			message = null;
		}
		switch (status.getSeverity()) {
			case IStatus.OK:
				setMessage(message, IMessageProvider.NONE);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.WARNING:
				setMessage(message, IMessageProvider.WARNING);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			case IStatus.INFO:
				setMessage(message, IMessageProvider.INFORMATION);
				setErrorMessage(null);
				setPageComplete(true);
				break;
			default:
				setMessage(null);
				setErrorMessage(message);
				setPageComplete(false);
				break;
		}
	}

	/**
	 * Creates and returns a new push button with the given label and/or image.
	 *
	 * @param parent
	 *            parent control
	 * @param label
	 *            button label or <code>null</code>
	 * @param image
	 *            image of <code>null</code>
	 * @return a new push button
	 */
	protected Button createPushButton(Composite parent, String label, Image image) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (image != null) {
			button.setImage(image);
		}
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);
		if (gd instanceof GridData) {
			PixelConverter converter = new PixelConverter(button);
			int widthHint = converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
			((GridData)gd).widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					true).x);
			((GridData)gd).horizontalAlignment = GridData.FILL;
		}

		return button;
	}

	private void reloadTargetPom(GenerationPomConfiguration pomConfiguration) {
		final URI pomTargetFolderURI = pomConfiguration.getPomFolderURI();
		if (pomTargetFolderURI != null) {
			final URI pomURI = URI.createURI("pom.xml").resolve(pomTargetFolderURI);
			if (URIConverter.INSTANCE.exists(pomURI, Collections.emptyMap())) {
				try (InputStream input = URIConverter.INSTANCE.createInputStream(pomURI)) {
					final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					final DocumentBuilder db = dbf.newDocumentBuilder();
					final Document pom = db.parse(input);
					final NodeList projectList = pom.getElementsByTagName("project");
					if (projectList != null && projectList.getLength() > 0) {
						final Element project = (Element)projectList.item(0);
						// groupId
						final NodeList groupIdList = project.getElementsByTagName("groupId");
						if (groupIdList != null && groupIdList.getLength() > 0) {
							final Element groupId = (Element)groupIdList.item(0);
							pomConfiguration.setGenerationGroupId(groupId.getTextContent());
							generationGroupIdText.setText(groupId.getTextContent());
						}
						// groupId
						final NodeList artifactIdList = project.getElementsByTagName("artifactId");
						if (artifactIdList != null && artifactIdList.getLength() > 0) {
							final Element artifactId = (Element)artifactIdList.item(0);
							pomConfiguration.setGenerationArtifactId(artifactId.getTextContent());
							generationArtifactIdText.setText(artifactId.getTextContent());
						}
						// version
						final NodeList versionList = project.getElementsByTagName("version");
						if (versionList != null && versionList.getLength() > 0) {
							final Element version = (Element)versionList.item(0);
							pomConfiguration.setGenerationVersion(version.getTextContent());
							generationVersionText.setText(version.getTextContent());
						}
					}
				} catch (IOException | SAXException | ParserConfigurationException e) {
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, getClass(),
							"Couldn't initialize generation pom configuration from " + pomURI, e));
				}
				checkErrors();
				targetPomExists = true;
			} else {
				targetPomExists = false;
			}
		} else {
			targetPomExists = false;
		}
		generationGroupIdText.setEnabled(!targetPomExists);
		generationArtifactIdText.setEnabled(!targetPomExists);
		generationVersionText.setEnabled(!targetPomExists);
	}

}
