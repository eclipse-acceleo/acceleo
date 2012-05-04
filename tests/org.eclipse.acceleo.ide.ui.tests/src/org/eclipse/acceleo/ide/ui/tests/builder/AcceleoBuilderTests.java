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
package org.eclipse.acceleo.ide.ui.tests.builder;

import com.google.common.collect.Sets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoProjectUtils;
import org.eclipse.acceleo.internal.ide.ui.wizards.project.AcceleoProjectWizard;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProject;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProjectClasspathEntry;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AcceleoBuilderTests {

	@BeforeClass
	public static void setUp() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().getActivePart();

				if (activePart instanceof IViewPart && activePart.getTitle().equals("Welcome")) { //$NON-NLS-1$
					IViewPart view = (IViewPart)activePart;
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
				}
			}
		});
	}

	@AfterClass
	public static void tearDown() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iProject : projects) {
			try {
				iProject.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testBuildWithClosedProjectInWorkspace() {
		IProject closedProject = ResourcesPlugin.getWorkspace().getRoot().getProject("closedproject"); //$NON-NLS-1$
		try {
			IProgressMonitor monitor = new NullProgressMonitor();
			closedProject.create(monitor);
			closedProject.close(monitor);

			org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE
					.createAcceleoProject();
			acceleoProject.setName("acceleoproject"); //$NON-NLS-1$
			acceleoProject.setGeneratorName("generator"); //$NON-NLS-1$
			acceleoProject.setJre("J2SE-1.5"); //$NON-NLS-1$

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("acceleoproject"); //$NON-NLS-1$
			project.create(monitor);
			project.open(monitor);

			AcceleoProjectUtils.generateFiles(acceleoProject, Collections.<AcceleoModule> emptyList(),
					project, false, monitor);

			AcceleoClosedProjectLogListener listener = new AcceleoClosedProjectLogListener();

			AcceleoUIActivator.getDefault().getLog().addLogListener(listener);
			project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);

			if (listener.message != null) {
				fail(listener.message);
			}

			closedProject.delete(true, new NullProgressMonitor());
			project.delete(true, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	public class AcceleoClosedProjectLogListener implements ILogListener {

		public String message = null;

		public void logging(IStatus status, String plugin) {
			if (status.getMessage().contains("Resource '/closedproject' is not open.")) { //$NON-NLS-1$
				message = status.getMessage();
			}
		}
	}

	@Test
	public void testBuildWithMetamodelInPlugin() {
		// Creates the Acceleo project
		String projectName = "org.eclipse.acceleo.ide.ui.tests.builder.metamodelinplugin"; //$NON-NLS-1$
		String selectedJVM = "J2SE-1.5"; //$NON-NLS-1$
		List<AcceleoModule> allModules = new ArrayList<AcceleoModule>();

		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName("commonModule"); //$NON-NLS-1$
		acceleoModule.setProjectName(projectName);
		acceleoModule.setParentFolder(projectName
				+ "/src/org/eclipse/acceleo/ide/ui/tests/builder/metamodelinplugin/common"); //$NON-NLS-1$
		acceleoModule.setIsInitialized(false);
		acceleoModule.setGenerateDocumentation(true);
		acceleoModule.getMetamodelURIs().add("http://www.eclipse.org/emf/2002/Ecore"); //$NON-NLS-1$

		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName("genCommon"); //$NON-NLS-1$
		acceleoModuleElement.setGenerateFile(false);
		acceleoModuleElement.setIsMain(false);
		acceleoModuleElement.setKind(ModuleElementKind.TEMPLATE);
		acceleoModuleElement.setParameterType("EClass"); //$NON-NLS-1$
		acceleoModule.setModuleElement(acceleoModuleElement);

		allModules.add(acceleoModule);

		boolean shouldGenerateModules = true;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IProgressMonitor monitor = new NullProgressMonitor();
		try {
			project.create(monitor);
			project.open(monitor);
			AcceleoProjectWizard.convert(project, selectedJVM, allModules, shouldGenerateModules, monitor);

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Could not sleep", e); //$NON-NLS-1$
			}

			// Check the existence of the output files
			File src = project.getFolder("src").getLocation().toFile(); //$NON-NLS-1$
			File bin = project.getFolder("bin").getLocation().toFile(); //$NON-NLS-1$
			AcceleoProject acceleoProject = new AcceleoProject(project.getLocation().toFile(), Sets
					.newHashSet(new AcceleoProjectClasspathEntry(src, bin)));
			Set<File> compiledAcceleoModules = acceleoProject.getAllCompiledAcceleoModules();
			assertEquals(1, compiledAcceleoModules.size());
			assertEquals("commonModule.emtl", compiledAcceleoModules.iterator().next().getName()); //$NON-NLS-1$

			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			for (IMarker iMarker : markers) {
				Object attribute = iMarker.getAttribute(IMarker.SEVERITY);
				if (attribute instanceof Integer && ((Integer)attribute).intValue() == IMarker.SEVERITY_ERROR) {
					fail(iMarker.getAttribute(IMarker.MESSAGE).toString());
				}
			}

			project.delete(true, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testErrorBuildWithMetamodelInPlugin() {
		// Creates the Acceleo project
		String projectName = "org.eclipse.acceleo.ide.ui.tests.builder.metamodelinplugin"; //$NON-NLS-1$
		String selectedJVM = "J2SE-1.5"; //$NON-NLS-1$
		List<AcceleoModule> allModules = new ArrayList<AcceleoModule>();

		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName("commonModule"); //$NON-NLS-1$
		acceleoModule.setProjectName(projectName);
		acceleoModule.setParentFolder(projectName
				+ "/src/org/eclipse/acceleo/ide/ui/tests/builder/metamodelinplugin/common"); //$NON-NLS-1$
		acceleoModule.setIsInitialized(false);
		acceleoModule.setGenerateDocumentation(true);
		acceleoModule.getMetamodelURIs().add("http://www.eclipse.org/emf/2002/Ecore"); //$NON-NLS-1$

		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName("genCommon"); //$NON-NLS-1$
		acceleoModuleElement.setGenerateFile(false);
		acceleoModuleElement.setIsMain(false);
		acceleoModuleElement.setKind(ModuleElementKind.TEMPLATE);
		acceleoModuleElement.setParameterType("MyClass"); //$NON-NLS-1$
		acceleoModule.setModuleElement(acceleoModuleElement);

		allModules.add(acceleoModule);

		boolean shouldGenerateModules = true;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IProgressMonitor monitor = new NullProgressMonitor();
		try {
			project.create(monitor);
			project.open(monitor);
			AcceleoProjectWizard.convert(project, selectedJVM, allModules, shouldGenerateModules, monitor);

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Could not sleep", e); //$NON-NLS-1$
			}

			// Check the existence of the output files
			File src = project.getFolder("src").getLocation().toFile(); //$NON-NLS-1$
			File bin = project.getFolder("bin").getLocation().toFile(); //$NON-NLS-1$
			AcceleoProject acceleoProject = new AcceleoProject(project.getLocation().toFile(), Sets
					.newHashSet(new AcceleoProjectClasspathEntry(src, bin)));
			Set<File> compiledAcceleoModules = acceleoProject.getAllCompiledAcceleoModules();
			assertEquals(1, compiledAcceleoModules.size());
			assertEquals("commonModule.emtl", compiledAcceleoModules.iterator().next().getName()); //$NON-NLS-1$

			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			boolean foundError = false;
			for (IMarker iMarker : markers) {
				Object attribute = iMarker.getAttribute(IMarker.SEVERITY);
				if (attribute instanceof Integer && ((Integer)attribute).intValue() == IMarker.SEVERITY_ERROR) {
					foundError = true;
				}
			}

			if (!foundError) {
				fail("There should be at least one error."); //$NON-NLS-1$
			}

			project.delete(true, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testBuildWithMetamodelInWorkspace() {
		// Creates the project containing the meta-model
		String metamodelProjectName = "metamodel"; //$NON-NLS-1$
		IProject metamodelProject = ResourcesPlugin.getWorkspace().getRoot().getProject(metamodelProjectName);
		String nsURI = "http://www.eclipse.org/acceleo/builder/tests"; //$NON-NLS-1$

		IProgressMonitor monitor = new NullProgressMonitor();
		try {
			metamodelProject.create(monitor);
			metamodelProject.open(monitor);
			IFolder modelFolder = metamodelProject.getFolder("model"); //$NON-NLS-1$
			modelFolder.create(true, false, monitor);
			IFile modelFile = modelFolder.getFile("model.ecore"); //$NON-NLS-1$

			String ls = System.getProperty("line.separator"); //$NON-NLS-1$
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ls); //$NON-NLS-1$
			buffer.append("<ecore:EPackage xmi:version=\"2.0\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"target\"" + ls); //$NON-NLS-1$
			buffer.append("    nsURI=\"" + nsURI + "\" nsPrefix=\"target\">" + ls); //$NON-NLS-1$ //$NON-NLS-2$
			buffer.append("  <eClassifiers xsi:type=\"ecore:EClass\" name=\"ClasseA\">" + ls); //$NON-NLS-1$
			buffer.append("    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>" + ls); //$NON-NLS-1$
			buffer.append("  </eClassifiers>" + ls); //$NON-NLS-1$
			buffer.append("</ecore:EPackage>" + ls); //$NON-NLS-1$
			modelFile.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		// Creates the Acceleo project
		String projectName = "org.eclipse.acceleo.ide.ui.tests.builder.metamodelinworkspace"; //$NON-NLS-1$
		String selectedJVM = "J2SE-1.5"; //$NON-NLS-1$
		List<AcceleoModule> allModules = new ArrayList<AcceleoModule>();

		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName("commonModule"); //$NON-NLS-1$
		acceleoModule.setProjectName(projectName);
		acceleoModule.setParentFolder(projectName
				+ "/src/org/eclipse/acceleo/ide/ui/tests/builder/metamodelinworkspace/common"); //$NON-NLS-1$
		acceleoModule.setIsInitialized(false);
		acceleoModule.setGenerateDocumentation(true);
		acceleoModule.getMetamodelURIs().add(nsURI);

		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName("genCommon"); //$NON-NLS-1$
		acceleoModuleElement.setGenerateFile(false);
		acceleoModuleElement.setIsMain(false);
		acceleoModuleElement.setKind(ModuleElementKind.TEMPLATE);
		acceleoModuleElement.setParameterType("ClasseA"); //$NON-NLS-1$
		acceleoModule.setModuleElement(acceleoModuleElement);

		allModules.add(acceleoModule);

		boolean shouldGenerateModules = true;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			project.create(monitor);
			project.open(monitor);
			AcceleoProjectWizard.convert(project, selectedJVM, allModules, shouldGenerateModules, monitor);

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Could not sleep", e); //$NON-NLS-1$
			}

			// Check the existence of the output files
			File src = project.getFolder("src").getLocation().toFile(); //$NON-NLS-1$
			File bin = project.getFolder("bin").getLocation().toFile(); //$NON-NLS-1$
			AcceleoProject acceleoProject = new AcceleoProject(project.getLocation().toFile(), Sets
					.newHashSet(new AcceleoProjectClasspathEntry(src, bin)));
			Set<File> compiledAcceleoModules = acceleoProject.getAllCompiledAcceleoModules();
			assertEquals(1, compiledAcceleoModules.size());
			assertEquals("commonModule.emtl", compiledAcceleoModules.iterator().next().getName()); //$NON-NLS-1$

			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			for (IMarker iMarker : markers) {
				Object attribute = iMarker.getAttribute(IMarker.SEVERITY);
				if (attribute instanceof Integer && ((Integer)attribute).intValue() == IMarker.SEVERITY_ERROR) {
					fail(iMarker.getAttribute(IMarker.MESSAGE).toString());
				}
			}

			project.delete(true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		try {
			metamodelProject.delete(true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBuildWithMetamodelInWorkspaceDependingOnMetamodelInPlugin() {
		// Creates the project containing the meta-model
		String metamodelProjectName = "metamodel"; //$NON-NLS-1$
		IProject metamodelProject = ResourcesPlugin.getWorkspace().getRoot().getProject(metamodelProjectName);
		String nsURI = "http://www.eclipse.org/acceleo/builder/tests"; //$NON-NLS-1$

		IProgressMonitor monitor = new NullProgressMonitor();
		try {
			metamodelProject.create(monitor);
			metamodelProject.open(monitor);
			IFolder modelFolder = metamodelProject.getFolder("model"); //$NON-NLS-1$
			modelFolder.create(true, false, monitor);
			IFile modelFile = modelFolder.getFile("model.ecore"); //$NON-NLS-1$

			String ls = System.getProperty("line.separator"); //$NON-NLS-1$
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ls); //$NON-NLS-1$
			buffer.append("<ecore:EPackage xmi:version=\"2.0\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"target\"" + ls); //$NON-NLS-1$
			buffer.append("    nsURI=\"" + nsURI + "\" nsPrefix=\"target\">" + ls); //$NON-NLS-1$ //$NON-NLS-2$
			buffer.append("  <eClassifiers xsi:type=\"ecore:EClass\" name=\"ClasseA\" eSuperTypes=\"platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore#//EClass\">" + ls); //$NON-NLS-1$
			buffer.append("    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>" + ls); //$NON-NLS-1$
			buffer.append("  </eClassifiers>" + ls); //$NON-NLS-1$
			buffer.append("</ecore:EPackage>" + ls); //$NON-NLS-1$
			modelFile.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		// Creates the Acceleo project
		String projectName = "org.eclipse.acceleo.ide.ui.tests.builder.metamodelinworkspacedependingonmetamodelinplugin"; //$NON-NLS-1$
		String selectedJVM = "J2SE-1.5"; //$NON-NLS-1$
		List<AcceleoModule> allModules = new ArrayList<AcceleoModule>();

		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName("commonModule"); //$NON-NLS-1$
		acceleoModule.setProjectName(projectName);
		acceleoModule
				.setParentFolder(projectName
						+ "/src/org/eclipse/acceleo/ide/ui/tests/builder/metamodelinworkspacedependingonmetamodelinplugin/common"); //$NON-NLS-1$
		acceleoModule.setIsInitialized(false);
		acceleoModule.setGenerateDocumentation(true);
		acceleoModule.getMetamodelURIs().add("http://www.eclipse.org/emf/2002/Ecore"); //$NON-NLS-1$
		acceleoModule.getMetamodelURIs().add(nsURI);

		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName("genCommon"); //$NON-NLS-1$
		acceleoModuleElement.setGenerateFile(false);
		acceleoModuleElement.setIsMain(false);
		acceleoModuleElement.setKind(ModuleElementKind.TEMPLATE);
		acceleoModuleElement.setParameterType("ClasseA"); //$NON-NLS-1$
		acceleoModule.setModuleElement(acceleoModuleElement);

		allModules.add(acceleoModule);

		boolean shouldGenerateModules = true;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			project.create(monitor);
			project.open(monitor);
			AcceleoProjectWizard.convert(project, selectedJVM, allModules, shouldGenerateModules, monitor);

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Could not sleep", e); //$NON-NLS-1$
			}

			// Check the existence of the output files
			File src = project.getFolder("src").getLocation().toFile(); //$NON-NLS-1$
			File bin = project.getFolder("bin").getLocation().toFile(); //$NON-NLS-1$
			AcceleoProject acceleoProject = new AcceleoProject(project.getLocation().toFile(), Sets
					.newHashSet(new AcceleoProjectClasspathEntry(src, bin)));
			Set<File> compiledAcceleoModules = acceleoProject.getAllCompiledAcceleoModules();
			assertEquals(1, compiledAcceleoModules.size());
			assertEquals("commonModule.emtl", compiledAcceleoModules.iterator().next().getName()); //$NON-NLS-1$

			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			for (IMarker iMarker : markers) {
				Object attribute = iMarker.getAttribute(IMarker.SEVERITY);
				if (attribute instanceof Integer && ((Integer)attribute).intValue() == IMarker.SEVERITY_ERROR) {
					fail(iMarker.getAttribute(IMarker.MESSAGE).toString());
				}
			}

			project.delete(true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		try {
			metamodelProject.delete(true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBuildWithMetamodelInWorkspaceDependingOnMetamodelInWorkspace() {
		// Creates the project containing the meta-model
		String metamodelProjectName = "metamodel"; //$NON-NLS-1$
		IProject metamodelProject = ResourcesPlugin.getWorkspace().getRoot().getProject(metamodelProjectName);
		String nsURI = "http://www.eclipse.org/acceleo/builder/tests"; //$NON-NLS-1$
		String nsURI2 = "http://www.eclipse.org/acceleo/builder/tests2"; //$NON-NLS-1$

		IProgressMonitor monitor = new NullProgressMonitor();
		try {
			metamodelProject.create(monitor);
			metamodelProject.open(monitor);
			IFolder modelFolder = metamodelProject.getFolder("model"); //$NON-NLS-1$
			modelFolder.create(true, false, monitor);
			IFile modelFile = modelFolder.getFile("model.ecore"); //$NON-NLS-1$

			String ls = System.getProperty("line.separator"); //$NON-NLS-1$
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ls); //$NON-NLS-1$
			buffer.append("<ecore:EPackage xmi:version=\"2.0\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"target\"" + ls); //$NON-NLS-1$
			buffer.append("    nsURI=\"" + nsURI + "\" nsPrefix=\"target\">" + ls); //$NON-NLS-1$ //$NON-NLS-2$
			buffer.append("  <eClassifiers xsi:type=\"ecore:EClass\" name=\"ClasseA\">" + ls); //$NON-NLS-1$
			buffer.append("    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"name\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>" + ls); //$NON-NLS-1$
			buffer.append("  </eClassifiers>" + ls); //$NON-NLS-1$
			buffer.append("</ecore:EPackage>" + ls); //$NON-NLS-1$
			modelFile.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);

			IFile modelFile2 = modelFolder.getFile("model2.ecore"); //$NON-NLS-1$

			buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ls); //$NON-NLS-1$
			buffer.append("<ecore:EPackage xmi:version=\"2.0\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + ls); //$NON-NLS-1$
			buffer.append("    xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" name=\"target\"" + ls); //$NON-NLS-1$
			buffer.append("    nsURI=\"" + nsURI2 + "\" nsPrefix=\"target\">" + ls); //$NON-NLS-1$ //$NON-NLS-2$
			buffer.append("  <eClassifiers xsi:type=\"ecore:EClass\" name=\"ClasseB\" eSuperTypes=\"model1.ecore#//ClassA\">" + ls); //$NON-NLS-1$
			buffer.append("    <eStructuralFeatures xsi:type=\"ecore:EAttribute\" name=\"othername\" eType=\"ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EString\"/>" + ls); //$NON-NLS-1$
			buffer.append("  </eClassifiers>" + ls); //$NON-NLS-1$
			buffer.append("</ecore:EPackage>" + ls); //$NON-NLS-1$
			modelFile2.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		// Creates the Acceleo project
		String projectName = "org.eclipse.acceleo.ide.ui.tests.builder.metamodelinworkspacedependingonmetamodelinworkspace"; //$NON-NLS-1$
		String selectedJVM = "J2SE-1.5"; //$NON-NLS-1$
		List<AcceleoModule> allModules = new ArrayList<AcceleoModule>();

		AcceleoModule acceleoModule = AcceleowizardmodelFactory.eINSTANCE.createAcceleoModule();
		acceleoModule.setName("commonModule"); //$NON-NLS-1$
		acceleoModule.setProjectName(projectName);
		acceleoModule
				.setParentFolder(projectName
						+ "/src/org/eclipse/acceleo/ide/ui/tests/builder/metamodelinworkspacedependingonmetamodelinworkspace/common"); //$NON-NLS-1$
		acceleoModule.setIsInitialized(false);
		acceleoModule.setGenerateDocumentation(true);
		acceleoModule.getMetamodelURIs().add(nsURI);
		acceleoModule.getMetamodelURIs().add(nsURI2);

		AcceleoModuleElement acceleoModuleElement = AcceleowizardmodelFactory.eINSTANCE
				.createAcceleoModuleElement();
		acceleoModuleElement.setName("genCommon"); //$NON-NLS-1$
		acceleoModuleElement.setGenerateFile(false);
		acceleoModuleElement.setIsMain(false);
		acceleoModuleElement.setKind(ModuleElementKind.TEMPLATE);
		acceleoModuleElement.setParameterType("ClasseB"); //$NON-NLS-1$
		acceleoModule.setModuleElement(acceleoModuleElement);

		allModules.add(acceleoModule);

		boolean shouldGenerateModules = true;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			project.create(monitor);
			project.open(monitor);
			AcceleoProjectWizard.convert(project, selectedJVM, allModules, shouldGenerateModules, monitor);

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				throw new RuntimeException("Could not sleep", e); //$NON-NLS-1$
			}

			// Check the existence of the output files
			File src = project.getFolder("src").getLocation().toFile(); //$NON-NLS-1$
			File bin = project.getFolder("bin").getLocation().toFile(); //$NON-NLS-1$
			AcceleoProject acceleoProject = new AcceleoProject(project.getLocation().toFile(), Sets
					.newHashSet(new AcceleoProjectClasspathEntry(src, bin)));
			Set<File> compiledAcceleoModules = acceleoProject.getAllCompiledAcceleoModules();
			assertEquals(1, compiledAcceleoModules.size());
			assertEquals("commonModule.emtl", compiledAcceleoModules.iterator().next().getName()); //$NON-NLS-1$

			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			for (IMarker iMarker : markers) {
				Object attribute = iMarker.getAttribute(IMarker.SEVERITY);
				if (attribute instanceof Integer && ((Integer)attribute).intValue() == IMarker.SEVERITY_ERROR) {
					fail(iMarker.getAttribute(IMarker.MESSAGE).toString());
				}
			}

			project.delete(true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		try {
			metamodelProject.delete(true, monitor);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
