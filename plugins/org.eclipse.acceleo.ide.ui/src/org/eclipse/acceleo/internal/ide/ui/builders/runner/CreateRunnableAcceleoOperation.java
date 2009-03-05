/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.builders.runner;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarker;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * The operation that compiles the FileBlock elements of the module in a background task. It creates a java
 * file and an ANT task for each template.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CreateRunnableAcceleoOperation implements IWorkspaceRunnable {

	/**
	 * The Acceleo project which contains the templates to be considered.
	 */
	private AcceleoProject acceleoProject;

	/**
	 * A list of templates to be considered.
	 */
	private List<IFile> files;

	/**
	 * Constructor.
	 * 
	 * @param acceleoProject
	 *            project which contains the templates
	 * @param files
	 *            are the templates to be considered
	 */
	public CreateRunnableAcceleoOperation(AcceleoProject acceleoProject, List<IFile> files) {
		super();
		this.acceleoProject = acceleoProject;
		this.files = files;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			Iterator<IFile> filesIt = files.iterator();
			if (filesIt.hasNext()) {
				List<String> resolvedClasspath = new ArrayList<String>();
				Iterator<IPath> entries = acceleoProject.getResolvedClasspath().iterator();
				IPath eclipseWorkspace = ResourcesPlugin.getWorkspace().getRoot().getLocation();
				IPath eclipseHome = new Path(Platform.getInstallLocation().getURL().getPath());
				while (entries.hasNext()) {
					IPath path = entries.next();
					if (eclipseWorkspace.isPrefixOf(path)) {
						resolvedClasspath.add("${ECLIPSE_WORKSPACE}/" //$NON-NLS-1$
								+ path.toString().substring(eclipseWorkspace.toString().length()));
					} else if (eclipseHome.isPrefixOf(path)) {
						resolvedClasspath.add("${ECLIPSE_HOME}/" //$NON-NLS-1$
								+ path.toString().substring(eclipseHome.toString().length()));
					}
				}
				while (filesIt.hasNext()) {
					IFile fileAcceleo = filesIt.next();
					String packageName = acceleoProject.getPackageName(fileAcceleo);
					IFile manifest = fileAcceleo.getProject().getFile("/META-INF/MANIFEST.MF"); //$NON-NLS-1$
					if (manifest.exists()
							&& FileContent.getFileContent(manifest.getLocation().toFile()).indexOf(
									packageName) == -1) {
						reportError(fileAcceleo, 0, 0, 0, AcceleoUIMessages.getString(
								"CreateRunnableAcceleoOperation.MissingExport", packageName)); //$NON-NLS-1$
					}
					URI moduleURI = URI.createPlatformResourceURI(acceleoProject.getOutputFilePath(
							fileAcceleo).toString(), true);
					ResourceSet resourceSet = new ResourceSetImpl();
					try {
						registerPackages(resourceSet);
						List<String> packages = new ArrayList<String>();
						EObject module = ModelUtils.load(moduleURI, resourceSet);
						if (module instanceof Module) {
							Iterator<TypedModel> typedModelIt = ((Module)module).getInput().iterator();
							while (typedModelIt.hasNext()) {
								Iterator<EPackage> packagesIt = typedModelIt.next().getTakesTypesFrom()
										.iterator();
								while (packagesIt.hasNext()) {
									EPackage ePackage = packagesIt.next();
									String mClass = getMetamodelPackageClass(ePackage);
									if (mClass != null && !packages.contains(mClass)) {
										packages.add(mClass);
									}
								}
							}
						}
						String classShortName = new Path(Character.toUpperCase(fileAcceleo.getName()
								.charAt(0))
								+ fileAcceleo.getName().substring(1)).removeFileExtension().lastSegment();
						List<String> mainTemplateNames = new ArrayList<String>();
						computesMainTemplateNames(mainTemplateNames, module);
						if (mainTemplateNames.size() > 0) {
							CreateRunnableAcceleoContent arg = new CreateRunnableAcceleoContent(fileAcceleo
									.getProject().getName(), packageName, classShortName, new Path(
									fileAcceleo.getName()).removeFileExtension().lastSegment(),
									mainTemplateNames, packages, resolvedClasspath);
							CreateRunnableJavaWriter javaWriter = new CreateRunnableJavaWriter();
							String javaText = javaWriter.generate(arg);
							IFile javaFile = fileAcceleo.getParent().getFile(
									new Path(classShortName).addFileExtension("java")); //$NON-NLS-1$
							createFile(javaFile, javaText, monitor);
							IFolder antFolder = fileAcceleo.getProject().getFolder("tasks"); //$NON-NLS-1$
							if (antFolder.exists()) {
								CreateRunnableAntWriter antWriter = new CreateRunnableAntWriter();
								String antText = antWriter.generate(arg);
								IFile antFile = antFolder.getFile(new Path(fileAcceleo.getName())
										.removeFileExtension().addFileExtension("xml")); //$NON-NLS-1$
								createFile(antFile, antText, monitor);
								CreateRunnableAntCallWriter antCallWriter = new CreateRunnableAntCallWriter();
								String antCallText = antCallWriter.generate(arg);
								IFile antCallFile = antFolder.getFile(new Path(fileAcceleo.getName())
										.removeFileExtension().addFileExtension("readme")); //$NON-NLS-1$
								createFile(antCallFile, antCallText, monitor);
							}
							// else : We don't want to create the ANT folder if it doesn't exist
						}
					} finally {
						Iterator<Resource> resources = resourceSet.getResources().iterator();
						while (resources.hasNext()) {
							resources.next().unload();
						}
					}
				}
			}
		} catch (IOException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Updates the registry used for looking up a package based namespace, in the resource set.
	 * 
	 * @param resourceSet
	 *            is the resource set
	 */
	private void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
	}

	/**
	 * Get the registered package class name to use for the classes of the given metamodel.
	 * 
	 * @param metamodel
	 *            is a metamodel
	 * @return the registered package class name
	 */
	private String getMetamodelPackageClass(EPackage metamodel) {
		// FIXME can't we simply return metamodel.getClass().getName()
		// EPackage.Registry.INSTANCE.getEPackage(metamodel.getNsURI())
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint("org.eclipse.emf.ecore.generated_package"); //$NON-NLS-1$
		if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IExtension extension = extensions[i];
				IConfigurationElement[] members = extension.getConfigurationElements();
				for (int j = 0; j < members.length; j++) {
					IConfigurationElement member = members[j];
					String mURI = member.getAttribute("uri"); //$NON-NLS-1$
					if (mURI != null && mURI.equals(metamodel.getNsURI())) {
						String mClass = member.getAttribute("class"); //$NON-NLS-1$
						if (mClass != null) {
							return mClass;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets all the templates that contain the main tag (@main).
	 * 
	 * @param mainTemplateNames
	 *            are the templates that contain the main tag (output parameter)
	 * @param eObject
	 *            is the object to browse
	 */
	private void computesMainTemplateNames(List<String> mainTemplateNames, EObject eObject) {
		if (eObject instanceof Template) {
			Template eTemplate = (Template)eObject;
			if (eTemplate.isMain() && !mainTemplateNames.contains(eTemplate.getName())) {
				mainTemplateNames.add(eTemplate.getName());
			}
		} else if (eObject != null) {
			Iterator<EObject> eContentsIt = eObject.eContents().iterator();
			while (eContentsIt.hasNext()) {
				EObject eContent = eContentsIt.next();
				computesMainTemplateNames(mainTemplateNames, eContent);
			}
		}
	}

	/**
	 * Creates the given file and its content.
	 * 
	 * @param newFile
	 *            is the file to create
	 * @param content
	 *            is the content of the new file
	 * @param monitor
	 *            is the monitor
	 * @throws UnsupportedEncodingException
	 *             when an encoding problem has been detected
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void createFile(IFile newFile, String content, IProgressMonitor monitor)
			throws UnsupportedEncodingException, CoreException {
		IFile file = newFile;
		String text = content;
		if (file.exists() && "java".equals(file.getFileExtension())) { //$NON-NLS-1$
			String jmergeFile = URI.createPlatformPluginURI(
					"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
			JControlModel model = new JControlModel();
			ASTFacadeHelper astFacadeHelper = new ASTFacadeHelper();
			model.initialize(astFacadeHelper, jmergeFile);
			if (model.canMerge()) {
				JMerger jMerger = new JMerger(model);
				jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForContents(text));
				try {
					jMerger.setTargetCompilationUnit(jMerger
							.createCompilationUnitForInputStream(new FileInputStream(file.getLocation()
									.toFile()))); // target=last generated code
					jMerger.merge();
					text = jMerger.getTargetCompilationUnit().getContents();
				} catch (FileNotFoundException e) {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
				}
			} else {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, AcceleoUIMessages
								.getString("CreateRunnableAcceleoOperation.MergerFailure"), null)); //$NON-NLS-1$
			}
		}
		if (!file.exists() && file.getParent().exists()) {
			IResource[] members = file.getParent().members(IResource.FILE);
			for (int i = 0; i < members.length; i++) {
				if (members[i] instanceof IFile
						&& file.getName().toLowerCase().equals(members[i].getName().toLowerCase())) {
					file = (IFile)members[i];
					break;
				}
			}
		}
		if (!file.exists()
				|| !text.equals(FileContent.getFileContent(file.getLocation().toFile()).toString())) {
			ByteArrayInputStream javaStream = new ByteArrayInputStream(text.getBytes("UTF8")); //$NON-NLS-1$
			if (!file.exists()) {
				file.create(javaStream, true, monitor);
			} else {
				file.setContents(javaStream, true, false, monitor);
			}
		}
	}

	/**
	 * Creates an error marker on the given file.
	 * 
	 * @param file
	 *            is the file that contains a syntax error
	 * @param line
	 *            is the line of the problem
	 * @param posBegin
	 *            is the beginning position of the problem
	 * @param posEnd
	 *            is the ending position of the problem
	 * @param message
	 *            is the message of the problem, it is the message displayed when you're hover the marker
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void reportError(IFile file, int line, int posBegin, int posEnd, String message)
			throws CoreException {
		IMarker m = file.createMarker(AcceleoMarker.PROBLEM_MARKER);
		m.setAttribute(IMarker.LINE_NUMBER, line);
		m.setAttribute(IMarker.CHAR_START, posBegin);
		m.setAttribute(IMarker.CHAR_END, posEnd);
		m.setAttribute(IMarker.MESSAGE, message);
		m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
	}

}
