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
package org.eclipse.acceleo.parser.tests.ast;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Bundle;

/**
 * The common superclass of all the AST tests.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public abstract class AbstractASTParserTests {

	protected static IProject project;

	protected static Bundle bundle;

	/**
	 * Default URI of the EMTL file if it doesn't exist.
	 */
	private static final String DEFAULT_EMTL_URI = "http://acceleo.eclipse.org/default.emtl"; //$NON-NLS-1$

	protected static final ResourceSet oResourceSet = new ResourceSetImpl();

	private static final List<URI> resourceSetURIs = new ArrayList<URI>();

	/**
	 * The Acceleo project.
	 */
	private AcceleoProject acceleoProject;

	/**
	 * The MTL file that we are parsing.
	 */
	private IFile mtlFile;

	/**
	 * The Acceleo source buffer used for the parsing.
	 */
	protected AcceleoSourceBuffer sourceBuffer;

	/**
	 * Utility method used to create an acceleo project with the given name.
	 * 
	 * @param name
	 *            The name of the project
	 * @return The project created
	 */
	protected static IProject createAcceleoProject(String name) {
		NullProgressMonitor monitor = new NullProgressMonitor();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject proj = root.getProject(name);
		try {
			proj.create(monitor);
			proj.open(monitor);
		} catch (CoreException e) {
			fail("Project creation error"); //$NON-NLS-1$
		}

		new AcceleoProject(proj);

		String eol = System.getProperty("line.separator"); //$NON-NLS-1$

		// creates the manifest.MF
		StringBuffer buffer = new StringBuffer("Manifest-Version: 1.0" + eol + "Bundle-ManifestVersion: 2" //$NON-NLS-1$ //$NON-NLS-2$
				+ eol + "Bundle-Name: Acceleo Sample Module Runtime Plug-in" + eol //$NON-NLS-1$
				+ "Bundle-SymbolicName: " + proj.getName() + eol //$NON-NLS-1$
				+ "Bundle-Version: 1.0.0.qualifier" + eol //$NON-NLS-1$
				+ "Bundle-Vendor: Eclipse Modeling Project" + eol //$NON-NLS-1$
				+ "Require-Bundle: org.eclipse.core.runtime, " + eol + " org.eclipse.emf.ecore," + eol //$NON-NLS-1$ //$NON-NLS-2$
				+ " org.eclipse.emf.ecore," + eol + " org.eclipse.emf.ecore.xmi," + eol + " org.eclipse.ocl," //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ eol + " org.eclipse.ocl.ecore," + eol + " org.eclipse.acceleo.model," + eol //$NON-NLS-1$ //$NON-NLS-2$
				+ " org.eclipse.acceleo.engine" + eol + "Bundle-RequiredExecutionEnvironment: J2SE-1.5" + eol //$NON-NLS-1$ //$NON-NLS-2$
				+ "Bundle-ActivationPolicy: lazy" + eol + "Eclipse-LazyStart: true" + eol //$NON-NLS-1$ //$NON-NLS-2$
				+ "Export-Package: " + proj.getName() + ".files"); //$NON-NLS-1$ //$NON-NLS-2$
		createFile(buffer.toString(), new Path("/META-INF"), proj, "MANIFEST.MF"); //$NON-NLS-1$ //$NON-NLS-2$

		try {
			boolean hasNature = proj.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
			if (!hasNature) {
				IProjectDescription description = proj.getDescription();
				String[] natureIds = description.getNatureIds();
				String[] newNaturesIds = new String[natureIds.length + 3];
				System.arraycopy(natureIds, 0, newNaturesIds, 0, natureIds.length);
				newNaturesIds[natureIds.length] = IAcceleoConstants.ACCELEO_NATURE_ID;
				newNaturesIds[natureIds.length + 1] = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$
				newNaturesIds[natureIds.length + 2] = "org.eclipse.jdt.core.javanature"; //$NON-NLS-1$
				description.setNatureIds(newNaturesIds);
				proj.setDescription(description, IResource.FORCE, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			fail();
		}

		// creates the .classpath
		buffer = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" //$NON-NLS-1$
						+ eol
						+ "<classpath>" //$NON-NLS-1$
						+ eol
						+ "<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/J2SE-1.5\"/>" //$NON-NLS-1$
						+ eol
						+ "<classpathentry kind=\"con\" path=\"org.eclipse.pde.core.requiredPlugins\"/>" //$NON-NLS-1$
						+ eol + "<classpathentry kind=\"src\" path=\"src\"/>" + eol //$NON-NLS-1$
						+ "<classpathentry kind=\"output\" path=\"bin\"/>" + eol + "</classpath>"); //$NON-NLS-1$ //$NON-NLS-2$
		createFile(buffer.toString(), new Path("/"), proj, ".classpath"); //$NON-NLS-1$//$NON-NLS-2$

		// creates the settings
		buffer = new StringBuffer("eclipse.preferences.version=1" + eol //$NON-NLS-1$
				+ "org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.5" + eol //$NON-NLS-1$
				+ "org.eclipse.jdt.core.compiler.compliance=1.5" + eol //$NON-NLS-1$
				+ "org.eclipse.jdt.core.compiler.problem.assertIdentifier=error" + eol //$NON-NLS-1$
				+ "org.eclipse.jdt.core.compiler.problem.enumIdentifier=error" + eol //$NON-NLS-1$
				+ "org.eclipse.jdt.core.compiler.source=1.5"); //$NON-NLS-1$
		createFile(buffer.toString(), new Path("/.settings"), proj, "org.eclipse.jdt.core.prefs"); //$NON-NLS-1$ //$NON-NLS-2$

		return proj;
	}

	/**
	 * Utility method used to create a module file.
	 * 
	 * @param buffer
	 *            The source buffer of the content of the file
	 * @param path
	 *            the path of the file to create
	 * @param proj
	 *            The project in which the file will be created
	 * @param name
	 *            The name of the file to create
	 * @return The created file
	 */
	protected static IFile createFile(String content, IPath path, IProject proj, String name) {
		String[] segments = path.segments();

		IContainer currentContainer = proj;
		for (String string : segments) {
			IFolder folder = currentContainer.getFolder(new Path(string));
			if (!folder.exists()) {
				try {
					folder.create(true, true, new NullProgressMonitor());
				} catch (CoreException e) {
					fail(e.getMessage());
					return null;
				}
			}
			currentContainer = folder;
		}

		IFile file = currentContainer.getFile(new Path(name));
		if (!file.exists()) {
			try {
				ByteArrayInputStream source = new ByteArrayInputStream(content.getBytes("UTF8")); //$NON-NLS-1$
				file.create(source, true, new NullProgressMonitor());
			} catch (UnsupportedEncodingException e) {
				fail(e.getMessage());
				return null;
			} catch (CoreException e) {
				fail(e.getMessage());
				return null;
			}
		}
		return file;
	}

	protected void parseAndLoadModule(IFile file) {
		AcceleoFile acceleoFile = new AcceleoFile(file.getLocation().toFile(), AcceleoFile
				.relativePathToFullModuleName(file.getProjectRelativePath().toString()));
		AcceleoSourceBuffer aSourceBuffer = new AcceleoSourceBuffer(acceleoFile);
		aSourceBuffer.createCST();

		AcceleoProject anAcceleoProject = new AcceleoProject(file.getProject());
		IFile aMtlFile = file;

		URI fileURI = null;
		IPath outputPath = anAcceleoProject.getOutputFilePath(aMtlFile);
		if (outputPath != null) {
			fileURI = URI.createPlatformResourceURI(outputPath.toString(), false);
		} else {
			fileURI = URI.createPlatformResourceURI(aMtlFile.getFullPath().removeFileExtension()
					.addFileExtension(IAcceleoConstants.EMTL_FILE_EXTENSION).toString(), false);
		}

		Resource resource = ModelUtils.createResource(fileURI, oResourceSet);
		System.out.println(resource.getURI());
		resourceSetURIs.add(fileURI);
	}

	protected void checkCSTParsing(IFile file, int infosCount, int warningsCount, int problemsCount) {
		AcceleoFile acceleoFile = new AcceleoFile(file.getLocation().toFile(), AcceleoFile
				.relativePathToFullModuleName(file.getProjectRelativePath().toString()));
		this.sourceBuffer = new AcceleoSourceBuffer(acceleoFile);
		this.sourceBuffer.getProblems().clear();
		this.sourceBuffer.getWarnings().clear();
		this.sourceBuffer.getInfos().clear();

		this.sourceBuffer.createCST();

		this.checkInfos(sourceBuffer, infosCount);
		this.checkWarnings(sourceBuffer, warningsCount);
		this.checkProblems(sourceBuffer, problemsCount);

		this.acceleoProject = new AcceleoProject(file.getProject());
		this.mtlFile = file;
	}

	protected void checkCST2ASTConvertion(int infosCount, int warningsCount, int problemsCount) {
		if (this.sourceBuffer != null) {
			URI fileURI;
			if (this.mtlFile != null) {
				IPath outputPath = acceleoProject.getOutputFilePath(this.mtlFile);
				if (outputPath != null) {
					fileURI = URI.createPlatformResourceURI(outputPath.toString(), false);
				} else {
					fileURI = URI.createPlatformResourceURI(this.mtlFile.getFullPath().removeFileExtension()
							.addFileExtension(IAcceleoConstants.EMTL_FILE_EXTENSION).toString(), false);
				}
			} else {
				fileURI = URI.createURI(DEFAULT_EMTL_URI);
			}

			Resource oResource = ModelUtils.createResource(fileURI, oResourceSet);
			resourceSetURIs.add(fileURI);
			// We don't have to create the CST : source.createCST();
			this.sourceBuffer.createAST(oResource);

			this.checkProblems(sourceBuffer, problemsCount);
			this.checkWarnings(sourceBuffer, warningsCount);
			this.checkInfos(sourceBuffer, infosCount);
		}
	}

	protected void checkASTResolution(int infosCount, int warningsCount, int problemsCount) {
		if (this.sourceBuffer != null) {

			this.sourceBuffer.resolveAST();

			this.checkProblems(sourceBuffer, problemsCount);
			this.checkWarnings(sourceBuffer, warningsCount);
			this.checkInfos(sourceBuffer, infosCount);
		} else {
			fail("The source buffer is null"); //$NON-NLS-1$
		}
	}

	protected void checkASTDocumentationResolution(int infosCount, int warningsCount, int problemsCount) {
		if (this.sourceBuffer != null) {
			this.sourceBuffer.resolveASTDocumentation();
			this.checkProblems(sourceBuffer, problemsCount);
			this.checkWarnings(sourceBuffer, warningsCount);
			this.checkInfos(sourceBuffer, infosCount);
		} else {
			fail("The source buffer is null"); //$NON-NLS-1$
		}
	}

	/**
	 * Checks the number of problems in the source buffer.
	 * 
	 * @param source
	 *            The source buffer
	 * @param problemsCount
	 *            The number of problems wanted
	 */
	protected void checkProblems(AcceleoSourceBuffer source, int problemsCount) {
		if (source.getProblems().getList().size() != problemsCount) {
			StringBuffer message = new StringBuffer();
			List<AcceleoParserProblem> list = source.getProblems().getList();
			for (AcceleoParserProblem acceleoParserProblem : list) {
				message.append(acceleoParserProblem.getMessage() + '\n');
			}
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getList().size() + " error : " + message.toString()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	/**
	 * Checks the number of warnings in the source buffer.
	 * 
	 * @param source
	 *            The source buffer
	 * @param problemsCount
	 *            The number of warnings wanted
	 */
	protected void checkWarnings(AcceleoSourceBuffer source, int warningsCount) {
		if (source.getWarnings().getList().size() != warningsCount) {
			StringBuffer message = new StringBuffer();
			List<AcceleoParserWarning> list = source.getWarnings().getList();
			for (AcceleoParserWarning acceleoParserWarning : list) {
				message.append(acceleoParserWarning.getMessage() + '\n');
			}
			fail("You must have " + warningsCount + " syntax warnings : " + source.getWarnings().getList().size() + " warning : " + message.toString()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	/**
	 * Checks the number of infos in the source buffer.
	 * 
	 * @param source
	 *            The source buffer
	 * @param problemsCount
	 *            The number of infos wanted
	 */
	protected void checkInfos(AcceleoSourceBuffer source, int infosCount) {
		if (source.getInfos().getList().size() != infosCount) {
			StringBuffer message = new StringBuffer();
			List<AcceleoParserInfo> list = source.getInfos().getList();
			for (AcceleoParserInfo acceleoParserInfo : list) {
				message.append(acceleoParserInfo.getMessage() + '\n');
			}
			fail("You must have " + infosCount + " syntax infos : " + source.getInfos().getList().size() + " info : " + message.toString()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	protected String getContentFromPath(String pathName) {
		try {
			URL url = FileLocator.resolve(bundle.getEntry(pathName));
			return readFileFromURL(url);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return null;
	}

	private String readFileFromURL(URL url) {
		String result = ""; //$NON-NLS-1$

		InputStream openStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			openStream = url.openStream();
		} catch (IOException e) {
			// do nothing, openStream is null
		}

		if (openStream != null) {
			inputStreamReader = new InputStreamReader(openStream);
			bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder stringBuilder = new StringBuilder();

			String line = null;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						openStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			result = stringBuilder.toString();
		}

		return result;
	}
}
