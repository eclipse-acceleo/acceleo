/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.utils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jdt.internal.debug.ui.LocalFileStorageEditorInput;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

/**
 * Provides utility methods used by both the "F3" and "CTRL+click" open declaration actions.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class OpenDeclarationUtils {
	/**
	 * Utility classes don't need to be instantiated.
	 */
	private OpenDeclarationUtils() {
		// hides constructor
	}

	/**
	 * Creates a region for the given EObject.
	 * 
	 * @param eObj
	 *            is the module element
	 * @return a region in the text
	 */
	public static IRegion createRegion(EObject eObj) {
		IRegion res = null;

		if (eObj instanceof ModuleElement) {
			ModuleElement eModuleElement = (ModuleElement)eObj;
			if (eModuleElement.eResource() != null) {
				int b = eModuleElement.getStartPosition();
				if (b > -1) {
					int e = eModuleElement.getEndPosition();
					res = new Region(b, e - b);
				}
			}
		} else if (eObj instanceof Variable) {
			Variable eVariable = (Variable)eObj;
			if (eVariable.eResource() != null) {
				int b = eVariable.getStartPosition();
				if (b > -1) {
					res = new Region(b, eVariable.getEndPosition() - b);
				}
			}
		}
		return res;
	}

	/**
	 * Opens the definition of the given ASTNode.
	 * 
	 * @param astNode
	 *            is the node which contains a reference to another ASTNode
	 * @return the EObject corresponding to the declaration
	 */
	public static EObject findDeclarationFromAST(ASTNode astNode) {
		EObject res = null;
		if (astNode instanceof TemplateInvocation) {
			Template eTemplate = ((TemplateInvocation)astNode).getDefinition();
			res = eTemplate;
		} else if (astNode instanceof QueryInvocation) {
			Query eQuery = ((QueryInvocation)astNode).getDefinition();
			res = eQuery;
		} else if (astNode instanceof MacroInvocation) {
			Macro eMacro = ((MacroInvocation)astNode).getDefinition();
			res = eMacro;
		} else if (astNode instanceof VariableExp) {
			Variable eVariable = (Variable)((VariableExp)astNode).getReferredVariable();
			res = eVariable;
		} else if (astNode instanceof OperationCallExp) {
			EOperation eOperation = ((OperationCallExp)astNode).getReferredOperation();
			res = eOperation;
		} else if (astNode instanceof PropertyCallExp) {
			EStructuralFeature eProperty = ((PropertyCallExp)astNode).getReferredProperty();
			res = eProperty;
		} else if (astNode instanceof Variable) {
			EClassifier eClassifier = ((Variable)astNode).getType();
			res = eClassifier;
		}
		return res;
	}

	/**
	 * Opens the definition of the given CSTNode.
	 * 
	 * @param editor
	 *            is the current editor that contains the given CSTNode
	 * @param astNode
	 *            is the current ASTNode
	 * @param cstNode
	 *            is the current CSTNode
	 * @return the EObject corresponding to the declaration
	 */
	public static EObject findDeclarationFromCST(AcceleoEditor editor, ASTNode astNode, CSTNode cstNode) {
		EObject res = null;
		if (cstNode instanceof TypedModel) {
			List<EPackage> ePackages = ((TypedModel)cstNode).getTakesTypesFrom();
			if (ePackages.size() > 0) {
				EPackage ePackage = ePackages.get(0);
				res = ePackage;
			}
		} else if (cstNode instanceof ModuleImportsValue) {
			String importName = ((ModuleImportsValue)cstNode).getName();
			if (importName != null && editor.getContent().getAST() != null) {
				Iterator<Module> eModules = editor.getContent().getAST().getImports().iterator();
				while (res == null && eModules.hasNext()) {
					Module eModule = eModules.next();
					if (importName.equalsIgnoreCase(eModule.getName())) {
						res = eModule;
					}
				}
			}
		} else if (cstNode instanceof ModuleExtendsValue) {
			String extendsName = ((ModuleExtendsValue)cstNode).getName();
			if (extendsName != null && editor.getContent().getAST() != null) {
				Iterator<Module> eModules = editor.getContent().getAST().getExtends().iterator();
				while (res == null && eModules.hasNext()) {
					Module eModule = eModules.next();
					if (extendsName.equalsIgnoreCase(eModule.getName())) {
						res = eModule;
					}
				}
			}
		} else if (cstNode instanceof TemplateOverridesValue) {
			String overridesName = ((TemplateOverridesValue)cstNode).getName();
			if (overridesName != null && astNode instanceof Template) {
				Iterator<Template> eOverrides = ((Template)astNode).getOverrides().iterator();
				while (res == null && eOverrides.hasNext()) {
					Template eOverride = eOverrides.next();
					if (overridesName.equalsIgnoreCase(eOverride.getName())) {
						res = eOverride;
					}
				}
			}
		}
		return res;
	}

	/**
	 * Opens the referenced definition on the given page. It can be on the active editor, or not. The
	 * highlighted range of the active editor is modified if the file URI of the referenced object is the
	 * active editor itself. Another editor can be opened if the referenced file is another file.
	 * 
	 * @param page
	 *            is the current page
	 * @param fileURI
	 *            is the file to open on the page
	 * @param region
	 *            is the textual region to select (Text editor)
	 * @param eObject
	 *            is the EObject to select (Ecore editor)
	 */
	public static void showEObject(IWorkbenchPage page, URI fileURI, IRegion region, EObject eObject) {
		IWorkbench workbench = page.getWorkbenchWindow().getWorkbench();
		if (fileURI != null && eObject != null) {
			URI newFileURI = formatURI(fileURI);
			if (newFileURI != null) {
				Object fileObject = getIFileXorIOFile(newFileURI);
				if (fileObject instanceof IFile) {
					newFileURI = URI.createPlatformResourceURI(((IFile)fileObject).getFullPath().toString(),
							false);
				}
				IEditorDescriptor editorDescriptor;
				String lastSegment = newFileURI.lastSegment();
				if (lastSegment.endsWith(IAcceleoConstants.EMTL_FILE_EXTENSION)
						|| lastSegment.endsWith(".ecore") || lastSegment.endsWith(".xmi") || lastSegment.endsWith(".uml")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					editorDescriptor = getXMIEditor();
				} else {
					editorDescriptor = workbench.getEditorRegistry().getDefaultEditor(lastSegment);
				}
				if (editorDescriptor != null
						&& (!(eObject instanceof ASTNode || eObject instanceof Module) || fileURI
								.isPlatformPlugin())) {
					try {
						IEditorPart newEditor = page.openEditor(new URIEditorInput(newFileURI),
								editorDescriptor.getId());
						selectAndReveal(newEditor, region, eObject);
					} catch (PartInitException e) {
						// Do nothing
					}
				}
			}
		}
		if (fileURI != null && (eObject instanceof ASTNode || eObject instanceof Module)) {
			Object fileObject = getIFileXorIOFile(fileURI);
			IFile file = null;
			File absoluteFile = null;
			if (fileObject instanceof IFile) {
				file = (IFile)fileObject;
			} else if (fileObject instanceof File) {
				absoluteFile = (File)fileObject;
			}
			if (file == null && absoluteFile != null) {
				IEditorInput input = new URIEditorInput(fileURI);
				String editorID;
				IEditorDescriptor editor = getXMIEditor();
				if (editor != null) {
					editorID = editor.getId();
				} else {
					editorID = AcceleoEditor.ACCELEO_EDITOR_ID;
				}
				File[] siblings = absoluteFile.getParentFile().listFiles();
				for (int i = 0; i < siblings.length; i++) {
					if (siblings[i].getName().equals(
							new Path(absoluteFile.getName()).removeFileExtension().addFileExtension(
									IAcceleoConstants.MTL_FILE_EXTENSION).lastSegment())) {
						input = new LocalFileStorageEditorInput(new LocalFileStorage(siblings[i]));
						editorID = AcceleoEditor.ACCELEO_EDITOR_ID;
						break;
					}
				}
				try {
					IEditorPart newEditor = IDE.openEditor(page, input, editorID);
					selectAndReveal(newEditor, region, eObject);
				} catch (PartInitException e) {
					// Do nothing
				}
			} else if (file != null) {
				try {
					IPath filePath = file.getFullPath();
					if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(filePath.getFileExtension())) {
						filePath = new AcceleoProject(file.getProject()).getInputFilePath(filePath);
						file = ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
					}
					IEditorDescriptor editorDescriptor = workbench.getEditorRegistry().getDefaultEditor(
							file.getName());
					if (editorDescriptor != null) {
						IEditorPart newEditor = IDE.openEditor(page, file, editorDescriptor.getId());
						selectAndReveal(newEditor, region, eObject);
					}
				} catch (PartInitException e) {
					// Do nothing
				}
			}
		}
	}

	/**
	 * Returns the workspace file (IFile). If it doesn't exist, we try to find the java.io.File.
	 * 
	 * @param fileURI
	 *            is the platform URI or the file URI...
	 * @return the IFile, or the java.io.File, or null if it doesn't exist
	 */
	public static Object getIFileXorIOFile(URI fileURI) {
		IFile workspaceFile = null;
		File absoluteFile = null;
		String platformString = fileURI.toPlatformString(true);
		IPath platformPath;
		if (platformString != null) {
			platformPath = new Path(platformString);
		} else {
			platformPath = null;
		}
		if (platformPath.segmentCount() > 1) {
			if (ResourcesPlugin.getWorkspace().getRoot().exists(platformPath)) {
				workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(platformPath);
			} else {
				String pluginName = platformPath.segment(0);
				String bundleLocation;
				Bundle bundle = Platform.getBundle(pluginName);
				if (bundle != null) {
					try {
						bundleLocation = bundle.getLocation();
					} catch (SecurityException e) {
						bundleLocation = null;
					}
				} else {
					bundleLocation = null;
				}
				String prefix = "reference:file:"; //$NON-NLS-1$
				if (bundleLocation != null && bundleLocation.startsWith(prefix)) {
					absoluteFile = new Path(bundleLocation.substring(prefix.length())).removeLastSegments(1)
							.append(platformPath).toFile();
				} else if (bundleLocation != null) {
					absoluteFile = new Path(bundleLocation).removeLastSegments(1).append(platformPath)
							.toFile();
				}
			}
		}
		String absolutePath = fileURI.toFileString();
		if (absolutePath != null) {
			absoluteFile = new File(absolutePath);
		}
		if (workspaceFile == null && fileURI.isFile() && absoluteFile != null) {
			IFile tmpFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(absolutePath));
			if (tmpFile != null && tmpFile.exists()) {
				workspaceFile = tmpFile;
			}
		}
		if (absoluteFile != null && !absoluteFile.exists()) {
			absoluteFile = null;
		}
		if (workspaceFile != null) {
			return workspaceFile;
		} else {
			return absoluteFile;
		}
	}

	/**
	 * Finds an ecore file in the given OSGI bundle.
	 * 
	 * @param bundle
	 *            is an OSGI bundle
	 * @param ecoreName
	 *            is the ecore file name to search
	 * @return the URI of the ecore file, or null if it doesn't exist
	 */
	@SuppressWarnings("unchecked")
	private static URI findEcore(Bundle bundle, String ecoreName) {
		Enumeration<URL> entries = bundle.findEntries("/", ecoreName, true); //$NON-NLS-1$
		if (entries != null) {
			while (entries.hasMoreElements()) {
				URL entry = entries.nextElement();
				if (entry != null) {
					IPath path = new Path(bundle.getSymbolicName()).append(entry.getPath());
					return URI.createPlatformPluginURI(path.toString(), false);
				}
			}
		}
		return null;
	}

	/**
	 * Changes the file URI to be readable in a plug-in context. If an EMF URI starts with 'http', We would
	 * like to transform this EMF URI to an ecore platform URI.
	 * 
	 * @param fileURI
	 *            is the URI
	 * @return platform URI
	 */
	private static URI formatURI(URI fileURI) {
		if (fileURI.toString().startsWith("http")) { //$NON-NLS-1$
			URI result = null;
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry
					.getExtensionPoint("org.eclipse.emf.ecore.generated_package"); //$NON-NLS-1$
			if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
				IExtension[] extensions = extensionPoint.getExtensions();
				for (int i = 0; result == null && i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] members = extension.getConfigurationElements();
					for (int j = 0; result == null && j < members.length; j++) {
						IConfigurationElement member = members[j];
						String mURI = member.getAttribute("uri"); //$NON-NLS-1$
						String genModelPath = member.getAttribute("genModel"); //$NON-NLS-1$
						String bundleID = member.getNamespaceIdentifier();
						if (mURI != null && mURI.equals(fileURI.toString())
								&& Platform.getBundle(bundleID) != null && genModelPath != null) {
							String ecoreName = new Path(genModelPath).removeFileExtension().addFileExtension(
									"ecore").lastSegment(); //$NON-NLS-1$
							result = findEcore(Platform.getBundle(bundleID), ecoreName);
						}
					}
				}
			}
			extensionPoint = registry.getExtensionPoint("org.eclipse.emf.ecore.uri_mapping"); //$NON-NLS-1$
			if (result == null && extensionPoint != null && extensionPoint.getExtensions().length > 0) {
				IExtension[] extensions = extensionPoint.getExtensions();
				for (int i = 0; result == null && i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] members = extension.getConfigurationElements();
					for (int j = 0; result == null && j < members.length; j++) {
						IConfigurationElement member = members[j];
						String sourceURI = member.getAttribute("source"); //$NON-NLS-1$
						String targetURI = member.getAttribute("target"); //$NON-NLS-1$
						if (sourceURI != null && sourceURI.equals(fileURI.toString()) && targetURI != null) {
							result = URI.createURI(targetURI, false);
						}
					}
				}
			}
			return result;
		}
		return fileURI;
	}

	/**
	 * Gets the best XMI editor.
	 * 
	 * @return the descriptor of the XMI editor
	 */
	private static IEditorDescriptor getXMIEditor() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IEditorDescriptor editorDescriptor = workbench.getEditorRegistry().findEditor(
				"org.eclipse.emf.ecore.presentation.EcoreEditorID"); //$NON-NLS-1$
		if (editorDescriptor == null) {
			editorDescriptor = workbench.getEditorRegistry().getDefaultEditor("Ecore.ecore"); //$NON-NLS-1$
			if (editorDescriptor == null) {
				editorDescriptor = workbench.getEditorRegistry().getDefaultEditor("Ecore.xmi"); //$NON-NLS-1$
			}
		}
		return editorDescriptor;
	}

	/**
	 * Creates a selection in the given editor. A selection can be created for a text editor by defining a
	 * region. A selection can be created for an ecore editor by defining an EObject.
	 * 
	 * @param newEditor
	 *            is an editor
	 * @param aRegion
	 *            is the textual region to select (Text editor), can be null
	 * @param eObject
	 *            is the EObject to select (Ecore editor), can be null
	 */
	private static void selectAndReveal(IEditorPart newEditor, IRegion aRegion, EObject eObject) {
		IRegion region = aRegion;
		if (newEditor instanceof AcceleoEditor) {
			AcceleoEditor acceleoEditor = (AcceleoEditor)newEditor;
			if (region != null && eObject instanceof ModuleElement) {
				int e = acceleoEditor.getContent().getText().indexOf(IAcceleoConstants.DEFAULT_END,
						region.getOffset());
				if (e > -1) {
					region = new Region(region.getOffset(), e + IAcceleoConstants.DEFAULT_END.length()
							- region.getOffset());
				}
				acceleoEditor.selectAndReveal(region.getOffset(), region.getLength());
			} else if (region != null) {
				acceleoEditor.selectAndReveal(region.getOffset(), region.getLength());
			} else {
				EObject newEObject = null;
				Module eModule = acceleoEditor.getContent().getAST();
				if (eModule != null && eModule.eResource() != null && eObject != null
						&& eObject.eResource() != null) {
					String eObjectFragmentURI = eObject.eResource().getURIFragment(eObject);
					newEObject = eModule.eResource().getEObject(eObjectFragmentURI);
				}
				if (newEObject instanceof TemplateExpression
						&& ((TemplateExpression)newEObject).getStartPosition() > -1) {
					int b = ((TemplateExpression)newEObject).getStartPosition();
					int e = acceleoEditor.getContent().getText().indexOf(IAcceleoConstants.DEFAULT_END, b) + 1;
					acceleoEditor.selectAndReveal(b, e - b);
				}
			}
		} else if (newEditor instanceof EcoreEditor && eObject.eResource() != null) {
			EcoreEditor ecoreEditor = (EcoreEditor)newEditor;
			String eObjectFragmentURI = eObject.eResource().getURIFragment(eObject);
			if (ecoreEditor.getEditingDomain() != null
					&& ecoreEditor.getEditingDomain().getResourceSet() != null
					&& ecoreEditor.getEditingDomain().getResourceSet().getResources().size() > 0
					&& eObjectFragmentURI != null) {
				EObject newObject = ecoreEditor.getEditingDomain().getResourceSet().getResources().get(0)
						.getEObject(eObjectFragmentURI);
				ecoreEditor.setSelectionToViewer(Collections.singleton(newObject));
			}
		}
	}
}
