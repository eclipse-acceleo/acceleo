/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.ide.ui.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.acceleo.aql.migration.ide.ui.AcceleoMigrationPlugin;
import org.eclipse.acceleo.aql.migration.standalone.StandaloneMigrator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Migrate selected projects to Acceleo 4.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MigrateToAcceleo4Handler extends AbstractHandler {

	/**
	 * The migration failed message.
	 */
	private static final String MIGRATION_FAILED = "Migration failed";

	/**
	 * Acceleo 3 nature.
	 */
	private static final String ACCELEO3_NATURE = "org.eclipse.acceleo.ide.ui.acceleoNature";

	/**
	 * Acceleo 3 builder.
	 */
	private static final String ACCELEO3_BUILDER = "org.eclipse.acceleo.ide.ui.acceleoBuilder";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Shell shell = HandlerUtil.getActiveShell(event);
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			final DirectoryDialog dialog = new DirectoryDialog(shell);
			dialog.setMessage("Select target directory");
			dialog.setText("Select the target directory for migrated projects.");
			final String targetString = dialog.open();
			try {
				if (targetString != null) {
					final Path targetPath = new File(targetString).toPath();
					final File logFile = targetPath.resolve("Acceleo4migation.log").toFile();
					if (!logFile.exists()) {
						final File parentFolder = logFile.getParentFile();
						if (!parentFolder.exists()) {
							parentFolder.mkdirs();
						}
						logFile.createNewFile();
					}

					try (FileWriter logWriter = new FileWriter(logFile)) {
						Iterator<?> it = ((IStructuredSelection)selection).iterator();
						while (it.hasNext()) {
							final IJavaProject javaProject;
							final Object selected = it.next();
							if (selected instanceof IJavaProject) {
								javaProject = (IJavaProject)selected;
							} else if (selected instanceof IProject) {
								javaProject = JavaCore.create((IProject)selected);
							} else {
								javaProject = null; // not possible
							}
							final IProject project = javaProject.getProject();
							final Path projectPath = project.getLocation().toFile().toPath();

							final List<Path> ignoreFolders = new ArrayList<>();
							try {
								final Path binaryPath = new File(projectPath.getParent().toString()
										+ javaProject.getOutputLocation().toString()).toPath();
								ignoreFolders.add(binaryPath);
								for (IClasspathEntry entry : javaProject.getRawClasspath()) {
									if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
										final Path sourcePath = new File(projectPath.getParent().toString()
												+ entry.getPath().toString()).toPath();
										ignoreFolders.add(sourcePath);
										final Path relativeSourcePath = projectPath.getParent().relativize(
												sourcePath);
										final Path targetSourcePath = targetPath.resolve(relativeSourcePath);
										final StandaloneMigrator migrator = new StandaloneMigrator(logWriter,
												sourcePath, binaryPath, targetSourcePath);
										migrator.migrateAll();
									}
								}
							} catch (JavaModelException | IOException e) {
								AcceleoMigrationPlugin.getPlugin().log(new Status(IStatus.ERROR,
										AcceleoMigrationPlugin.ID, MIGRATION_FAILED, e));
							}

							final Path targetProjectPath = targetPath.resolve(projectPath.getParent()
									.relativize(projectPath));
							try {
								copyPath(projectPath, targetProjectPath, ignoreFolders);
							} catch (IOException e) {
								AcceleoMigrationPlugin.getPlugin().log(new Status(IStatus.ERROR,
										AcceleoMigrationPlugin.ID, MIGRATION_FAILED, e));
								e.printStackTrace();
							}

							removeAcceleo3BuilderAndNature(targetProjectPath);
						}
					}
				}
			} catch (IOException e) {
				AcceleoMigrationPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoMigrationPlugin.ID,
						MIGRATION_FAILED, e));
			}
		}

		return null;
	}

	/**
	 * Removes the Acceleo 3 builder and nature from the target project.
	 * 
	 * @param targetProjectPath
	 *            the target project {@link Path}
	 */
	private void removeAcceleo3BuilderAndNature(final Path targetProjectPath) {
		final Path projectDescriptionPath = targetProjectPath.resolve(".project");
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder db = dbf.newDocumentBuilder();
			final Document xml = db.parse(projectDescriptionPath.toFile());
			final NodeList childNodes = xml.getChildNodes().item(0).getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				final Node node = childNodes.item(i);
				if ("buildSpec".equals(node.getNodeName())) {
					removeAcceleo3Builder(node);
				}
				if ("natures".equals(node.getNodeName())) {
					removeAcceleo3Nature(node);
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xml);
			StreamResult streamResult = new StreamResult(projectDescriptionPath.toFile());
			transformer.transform(source, streamResult);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			AcceleoMigrationPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoMigrationPlugin.ID,
					MIGRATION_FAILED, e));
		}
	}

	/**
	 * Removes the Acceleo 3 nature.
	 * 
	 * @param node
	 *            the natures {@link Node}
	 */
	private void removeAcceleo3Nature(final Node node) {
		final NodeList naturesChildren = node.getChildNodes();
		boolean found = false;
		Node natureNode = null;
		for (int j = 0; j < naturesChildren.getLength(); j++) {
			natureNode = naturesChildren.item(j);
			if ("nature".equals(natureNode.getNodeName()) && ACCELEO3_NATURE.equals(natureNode.getFirstChild()
					.getNodeValue())) {
				found = true;
				break;
			}
		}
		if (found) {
			node.removeChild(natureNode);
		}
	}

	/**
	 * Removes the Acceleo 3 builder.
	 * 
	 * @param node
	 *            the buildSpec {@link Node}
	 */
	private void removeAcceleo3Builder(final Node node) {
		final NodeList buildChildren = node.getChildNodes();
		boolean found = false;
		for (int j = 0; j < buildChildren.getLength(); j++) {
			final Node buildNode = buildChildren.item(j);
			final NodeList buildNodeChildren = buildNode.getChildNodes();
			for (int k = 0; k < buildNodeChildren.getLength(); k++) {
				final Node buildNodeChildNode = buildNodeChildren.item(k);
				if ("name".equals(buildNodeChildNode.getNodeName()) && ACCELEO3_BUILDER.equals(
						buildNodeChildNode.getFirstChild().getNodeValue())) {
					found = true;
					break;
				}
			}
			if (found) {
				node.removeChild(buildNode);
				break;
			}
		}
	}

	/**
	 * Copies the source {@link Path} to the target {@link Path} ignoring given folders.
	 * 
	 * @param source
	 *            the source {@link Path}
	 * @param target
	 *            the target {@link Path}
	 * @param ignoreFolders
	 *            the {@link List} of folders to ignore
	 * @throws IOException
	 *             if the copy fails
	 */
	private void copyPath(Path source, Path target, List<Path> ignoreFolders) throws IOException {
		final File sourceFile = source.toFile();
		for (File child : sourceFile.listFiles()) {
			final Path childPath = child.toPath().toAbsolutePath();
			final Path childTarget = target.toAbsolutePath().resolve(source.toAbsolutePath().relativize(
					childPath));
			if (child.isDirectory() && !ignoreFolders.contains(childPath)) {
				final File targetFolder = childTarget.toFile();
				if (!targetFolder.exists()) {
					targetFolder.mkdirs();
				}
				copyPath(childPath, childTarget, ignoreFolders);
			} else if (!childTarget.toFile().exists()) {
				Files.copy(childPath, childTarget);
			}
		}
	}

}
