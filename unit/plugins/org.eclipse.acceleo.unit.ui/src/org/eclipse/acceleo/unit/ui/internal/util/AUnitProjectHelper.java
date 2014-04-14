/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui.internal.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.parser.cst.CSTParser;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.eclipse.jdt.launching.environments.IExecutionEnvironmentsManager;
import org.eclipse.text.edits.TextEdit;

/**
 * The AUnitCreateProject class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class AUnitProjectHelper {

	/**
	 * The acceleo target project.
	 */
	private IProject acceleoTarget;

	/**
	 * The new project name.
	 */
	private String newProjectName;

	/**
	 * The new project path.
	 */
	private IPath newProjectPath;

	/**
	 * The progress monitor.
	 */
	private IProgressMonitor monitor;

	/**
	 * The new project.
	 */
	private IProject project;

	/**
	 * The java project helper.
	 */
	private IJavaProject javaProject;

	/**
	 * The constructor.
	 * 
	 * @param acceleoTarget
	 *            The acceleo target project.
	 * @param newProjectName
	 *            The new project name.
	 * @param newProjectPath
	 *            The new project path.
	 * @param monitor
	 *            The progress monitor.
	 */
	public AUnitProjectHelper(IProject acceleoTarget, String newProjectName, IPath newProjectPath,
			IProgressMonitor monitor) {
		this.setAcceleoTarget(acceleoTarget);
		this.setNewProjectName(newProjectName);
		this.newProjectPath = newProjectPath;
		this.monitor = monitor;
	}

	/**
	 * Create the project.
	 */
	public void create() {
		try {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(newProjectName);

			if (!project.exists()) {
				IProjectDescription desc = project.getWorkspace().newProjectDescription(newProjectName);

				desc.setNatureIds(new String[] {JavaCore.NATURE_ID, "org.eclipse.pde.PluginNature" }); //$NON-NLS-1$

				if (ResourcesPlugin.getWorkspace().getRoot().getLocation().equals(newProjectPath)) {
					newProjectPath = null;
				}
				desc.setLocation(newProjectPath);
				project.create(desc, monitor);
				project.open(monitor);

				convertToAUnitProject();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converts the empty project in a acceleo unit project.
	 * 
	 * @throws CoreException
	 *             if sub function throw this exception.
	 */
	private void convertToAUnitProject() throws CoreException {

		IFolder src = project.getFolder("src"); //$NON-NLS-1$
		src.create(true, true, monitor);

		IFolder meta = project.getFolder("META-INF"); //$NON-NLS-1$
		meta.create(true, true, monitor);

		IFile metaFile = meta.getFile("MANIFEST.MF"); //$NON-NLS-1$

		String fileContent = AUnitMetaInfHelper.generateMetaInf(newProjectName);

		InputStream source = new ByteArrayInputStream(fileContent.getBytes());
		metaFile.create(source, true, monitor);

		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] {JavaCore.NATURE_ID, "org.eclipse.pde.PluginNature" }); //$NON-NLS-1$

		project.setDescription(description, monitor);

		javaProject = JavaCore.create(project);

		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		IExecutionEnvironmentsManager executionEnvironmentsManager = JavaRuntime
				.getExecutionEnvironmentsManager();
		IExecutionEnvironment[] executionEnvironments = executionEnvironmentsManager
				.getExecutionEnvironments();
		for (IExecutionEnvironment iExecutionEnvironment : executionEnvironments) {
			if ("J2SE-1.5".equals(iExecutionEnvironment.getId())) { //$NON-NLS-1$
				entries.add(JavaCore
						.newContainerEntry(JavaRuntime.newJREContainerPath(iExecutionEnvironment)));
				break;
			}
		}
		entries.add(JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"))); //$NON-NLS-1$

		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);

		IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
		System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);

		IFolder target = project.getFolder("target"); //$NON-NLS-1$
		target.create(true, true, monitor);

		IFolder classes = target.getFolder("classes"); //$NON-NLS-1$
		classes.create(true, true, monitor);

		javaProject.setOutputLocation(classes.getFullPath(), monitor);

		IPackageFragmentRoot packageRoot = javaProject.getPackageFragmentRoot(src);
		newEntries[oldEntries.length] = JavaCore.newSourceEntry(packageRoot.getPath(), new Path[] {},
				new Path[] {}, classes.getFullPath());

		javaProject.setRawClasspath(newEntries, null);
		buildTestCases();
	}

	/**
	 * Builds test cases with target project module files.
	 * 
	 * @throws CoreException
	 *             if one or several java files have syntax error during the auto ident.
	 */
	private void buildTestCases() throws CoreException {

		IPackageFragmentRoot packageFragmentRoot = javaProject.getPackageFragmentRoot(project
				.getFolder("src")); //$NON-NLS-1$
		// new line
		final String nL = AUnitUtils.getLineSeparator();

		AcceleoProject acceleoProject = new AcceleoProject(acceleoTarget);
		List<IFile> templateFiles = acceleoProject.getInputFiles();

		for (IFile templateFile : templateFiles) {

			String templatePath = acceleoProject.getPackageName(templateFile);
			String packageName = templatePath.replace('/', '.');
			IPackageFragment pack = packageFragmentRoot.createPackageFragment(packageName, false, null); // $NON-NLS-1$

			// create java file
			String newFileIdent = templateFile.getName();
			StringTokenizer st = new StringTokenizer(newFileIdent, "."); //$NON-NLS-1$
			newFileIdent = st.nextToken() + "Test"; //$NON-NLS-1$

			String emtlPath = acceleoProject.getOutputFilePath(templateFile).toString();
			String className = AUnitUtils.convert2ClassIdent(newFileIdent);
			AcceleoSourceBuffer source = new AcceleoSourceBuffer(templateFile.getLocation().toFile());
			CSTParser cstParser = new CSTParser(source);
			Module module = cstParser.parse();

			String fileContent = AUnitTestCaseHelper.generateClassTestCase(className, pack, emtlPath, module);

			ICompilationUnit cu = pack.createCompilationUnit(AUnitUtils.convert2ClassIdent(newFileIdent)
					+ ".java", fileContent, false, null); //$NON-NLS-1$

			// auto indent the java file.
			CodeFormatter formatter = ToolFactory.createCodeFormatter(null);
			ISourceRange range = cu.getSourceRange();
			TextEdit indentEdit = formatter.format(CodeFormatter.K_COMPILATION_UNIT, cu.getSource(), range
					.getOffset(), range.getLength(), 0, nL);
			cu.applyTextEdit(indentEdit, monitor);
			cu.save(monitor, true);
		}
	}

	/**
	 * The acceleoTarget getter.
	 * 
	 * @return the acceleoTarget
	 */
	public IProject getAcceleoTarget() {
		return acceleoTarget;
	}

	/**
	 * The acceleoTarget setter.
	 * 
	 * @param acceleoTarget
	 *            the acceleoTarget to set
	 */
	public void setAcceleoTarget(IProject acceleoTarget) {
		this.acceleoTarget = acceleoTarget;
	}

	/**
	 * The newProjectName getter.
	 * 
	 * @return the newProjectName
	 */
	public String getNewProjectName() {
		return newProjectName;
	}

	/**
	 * The newProjectName setter.
	 * 
	 * @param newProjectName
	 *            the newProjectName to set
	 */
	public void setNewProjectName(String newProjectName) {
		this.newProjectName = newProjectName;
	}

}
