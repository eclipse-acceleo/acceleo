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
package org.eclipse.acceleo.internal.ide.ui.builders.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarkerUtils;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

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
						AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.PROBLEM_MARKER_ID,
								fileAcceleo, 0, 0, 0, AcceleoUIMessages.getString(
										"CreateRunnableAcceleoOperation.MissingExport", packageName)); //$NON-NLS-1$
					}
					URI moduleURI = URI.createPlatformResourceURI(acceleoProject.getOutputFilePath(
							fileAcceleo).toString(), true);
					ResourceSet resourceSet = new AcceleoResourceSetImpl();
					resourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
					try {
						registerPackages(resourceSet);
						List<AcceleoPackage> packages = new ArrayList<AcceleoPackage>();
						EObject module = ModelUtils.load(moduleURI, resourceSet);
						if (module instanceof Module) {
							Iterator<TypedModel> typedModelIt = ((Module)module).getInput().iterator();
							while (typedModelIt.hasNext()) {
								Iterator<EPackage> packagesIt = typedModelIt.next().getTakesTypesFrom()
										.iterator();
								while (packagesIt.hasNext()) {
									EPackage ePackage = packagesIt.next();
									String mClass = getMetamodelPackageClass(ePackage);
									if (mClass != null && ePackage.eResource() != null) {
										AcceleoPackage acceleoPackage = AcceleowizardmodelFactory.eINSTANCE
												.createAcceleoPackage();
										acceleoPackage.setClass(mClass);
										acceleoPackage.setPath(ePackage.eResource().getURI().toString());
										packages.add(acceleoPackage);
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
							AcceleoMainClass acceleoMainClass = AcceleowizardmodelFactory.eINSTANCE
									.createAcceleoMainClass();
							acceleoMainClass.setBasePackage(packageName);
							acceleoMainClass.setClassShortName(classShortName);

							IPath moduleFilePath = computeRelativeModuleFilePath(fileAcceleo);

							String moduleFileShortName = moduleFilePath.removeFileExtension().toString();
							acceleoMainClass.setModuleFileShortName(moduleFileShortName);
							acceleoMainClass.setProjectName(fileAcceleo.getProject().getName());
							EList<AcceleoPackage> packagesList = acceleoMainClass.getPackages();
							packagesList.addAll(packages);
							List<String> classPath = acceleoMainClass.getResolvedClassPath();
							classPath.addAll(resolvedClasspath);
							List<String> templateNames = acceleoMainClass.getTemplateNames();
							templateNames.addAll(mainTemplateNames);

							AcceleoUIGenerator.getDefault().generateJavaClass(acceleoMainClass,
									fileAcceleo.getParent());

							IFolder antFolder = fileAcceleo.getProject().getFolder("tasks"); //$NON-NLS-1$
							IFile antFile = antFolder.getFile(acceleoMainClass.getModuleFileShortName()
									+ ".xml"); //$NON-NLS-1$
							if (antFolder.exists() && !antFile.exists()) {
								IPath workspacePathRelativeToFile = computeWorkspacePath();
								IPath eclipsePathRelativeToFile = computeEclipsePath();

								moduleFileShortName = moduleFilePath.removeFileExtension().lastSegment()
										.toString();
								acceleoMainClass.setModuleFileShortName(moduleFileShortName);
								AcceleoUIGenerator.getDefault().generateAntFiles(
										acceleoMainClass,
										AcceleoProject.makeRelativeTo(eclipsePathRelativeToFile,
												antFolder.getLocation()).toString(),
										AcceleoProject.makeRelativeTo(workspacePathRelativeToFile,
												antFolder.getLocation()).toString(), antFolder);
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
	 * Computes the file path of the module relative to the source folder.
	 * 
	 * @param file
	 *            The module
	 * @return The file path of the module relative to the source folder.
	 * @since 3.1
	 */
	private IPath computeRelativeModuleFilePath(IFile file) {
		IPath fullPath = file.getFullPath();

		IPath moduleFilePath = fullPath;

		AcceleoProject project = new AcceleoProject(file.getProject());
		List<IPath> sourceFolders = project.getSourceFolders();
		for (IPath sourceFolderPath : sourceFolders) {
			if (sourceFolderPath.isPrefixOf(fullPath)) {
				moduleFilePath = AcceleoProject.makeRelativeTo(fullPath, sourceFolderPath);
			}
		}

		if (moduleFilePath.equals(file.getFullPath())) {
			moduleFilePath = new Path(moduleFilePath.lastSegment());
		} else {
			moduleFilePath = new Path("").addTrailingSeparator().append(moduleFilePath); //$NON-NLS-1$
		}

		return moduleFilePath;
	}

	/**
	 * Returns the workspace path.
	 * 
	 * @return The workspace path.
	 * @since 3.1
	 */
	public static IPath computeWorkspacePath() {
		return new Path(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
	}

	/**
	 * Returns the Eclipse path.
	 * 
	 * @return The Eclipse path.
	 * @since 3.1
	 */
	public static IPath computeEclipsePath() {
		URL fileURL = Platform.getInstallLocation().getURL();
		try {
			String filepath = FileLocator.toFileURL(fileURL).getFile();
			File file = new File(filepath);
			return new Path(file.getAbsolutePath().toString());
		} catch (IOException e) {
			AcceleoUIActivator.log(e, true);
		}
		return null;
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
		if (!EPackage.class.getName().equals(metamodel.getClass().getName())
				&& !EPackageImpl.class.getName().equals(metamodel.getClass().getName())) {
			String name = metamodel.getClass().getName();
			if (metamodel.getClass().getInterfaces().length > 1) {
				for (Class<?> clazz : metamodel.getClass().getInterfaces()) {
					if (metamodel.getClass().getSimpleName().equals(clazz.getSimpleName() + "Impl")) { //$NON-NLS-1$
						name = clazz.getName();
						break;
					}
				}
			} else if (metamodel.getClass().getInterfaces().length == 1
					&& metamodel.getClass().getSimpleName().startsWith(
							metamodel.getClass().getInterfaces()[0].getSimpleName())) {
				name = metamodel.getClass().getInterfaces()[0].getName();
			}
			return name;
		}
		return null;
	}

	/**
	 * Gets all the templates that contain the main annotation (@main).
	 * 
	 * @param mainTemplateNames
	 *            are the templates that contain the main annotation (output parameter)
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
}
