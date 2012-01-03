/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferenceEntry;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchQuery;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchResult;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.ecore.CollectionItem;
import org.eclipse.ocl.ecore.CollectionLiteralExp;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.TypeExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.expressions.CollectionLiteralPart;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.search.ui.text.Match;
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
		} else if (astNode instanceof TypeExp) {
			EClassifier eClassifier = ((TypeExp)astNode).getReferredType();
			res = eClassifier;
		} else if (astNode instanceof IteratorExp) {
			if ((((IteratorExp)astNode).getBody().getStartPosition() == -1)
					&& (((IteratorExp)astNode).getBody().getEndPosition() == -1)) {
				res = findDeclarationFromAST(((IteratorExp)astNode).getBody());
			} else {
				res = astNode;
			}
		} else if (astNode instanceof Template) {
			Template template = (Template)astNode;
			res = template;
		} else if (astNode instanceof Query) {
			Query query = (Query)astNode;
			res = query;
		}
		return res;
	}

	/**
	 * Returns the correct EOperation from the given <em>environment</em>'s standard library instance
	 * corresponding to the given iterator expression.
	 * <p>
	 * At the time of writing, iterators consist of :
	 * <ul>
	 * <li>exists</li>
	 * <li>forAll</li>
	 * <li>select</li>
	 * <li>reject</li>
	 * <li>collect</li>
	 * <li>collectNested</li>
	 * <li>one</li>
	 * <li>any</li>
	 * <li>sortedBy</li>
	 * <li>isUnique</li>
	 * <li>closure</li>
	 * </ul>
	 * </p>
	 * 
	 * @param environment
	 *            Environment which standard library is to be referenced.
	 * @param iteratorExp
	 *            Iterator forw which an EOperation is to be retrieved.
	 * @return The EOperation <em>iteratorExp</em> references.
	 */
	public static EObject findIteratorEOperation(
			Environment<?, EClassifier, EOperation, EStructuralFeature, ?, ?, ?, ?, ?, ?, ?, ?> environment,
			IteratorExp iteratorExp) {
		/*
		 * There is no way to access the "*_Class" classifier from the ocl standard library package that
		 * contains all EOperations, thus this code.
		 */
		final EPackage stdLibPackage = (EPackage)environment.getOCLStandardLibrary().getCollection()
				.eContainer();
		final EClassifier sourceType = iteratorExp.getSource().getType();
		final String operationHolderName = sourceType.getName().substring(0,
				sourceType.getName().indexOf('(')).concat("(T)_Class"); //$NON-NLS-1$
		final EClassifier operationHolder = stdLibPackage.getEClassifier(operationHolderName);
		final String operationName = iteratorExp.getName();

		EOperation referredOperation = null;
		// Thread safe copy
		for (EObject child : new ArrayList<EObject>(operationHolder.eContents())) {
			if (child instanceof EOperation && operationName.equals(((EOperation)child).getName())) {
				referredOperation = (EOperation)child;
				break;
			}
		}
		return referredOperation;
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
					if (importName.equalsIgnoreCase(eModule.getNsURI())
							|| importName.equalsIgnoreCase(eModule.getName())) {
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
					if (extendsName.equalsIgnoreCase(eModule.getNsURI())
							|| extendsName.equalsIgnoreCase(eModule.getName())) {
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
			if (newFileURI != null && (!(eObject instanceof ASTNode || eObject instanceof Module))) {
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
				if (editorDescriptor != null) {
					try {
						IEditorPart newEditor = page.openEditor(new URIEditorInput(newFileURI),
								editorDescriptor.getId());
						selectAndReveal(newEditor, region, eObject);
					} catch (PartInitException e) {
						// Do nothing
						AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
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
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
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
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
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
		if (platformPath != null && platformPath.segmentCount() > 1) {
			if (ResourcesPlugin.getWorkspace().getRoot().exists(platformPath)) {
				workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(platformPath);
			} else {
				String pluginName = platformPath.segment(0);
				Bundle bundle = Platform.getBundle(pluginName);
				if (bundle != null) {
					// force to extract the MTL file of the jar archive
					getAbsoluteFile(bundle, platformPath.removeFirstSegments(1).removeFileExtension()
							.addFileExtension(IAcceleoConstants.MTL_FILE_EXTENSION).toString());
					absoluteFile = getAbsoluteFile(bundle, platformPath.removeFirstSegments(1).toString());
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
		}
		return absoluteFile;
	}

	/**
	 * Gets the absolute file path of the given plug-in entry.
	 * 
	 * @param bundle
	 *            is the plug-in
	 * @param entryPath
	 *            is the entry path in the plug-in
	 * @return the absolute file path
	 */
	private static File getAbsoluteFile(Bundle bundle, String entryPath) {
		URL entry = bundle.getEntry(entryPath);
		File absoluteFile;
		if (entry != null) {
			try {
				entry = FileLocator.toFileURL(entry);
				if (entry != null) {
					absoluteFile = new Path(entry.getPath()).toFile();
				} else {
					absoluteFile = null;
				}
			} catch (IOException e1) {
				AcceleoUIActivator.log(e1, true);
				absoluteFile = null;
			}
		} else {
			absoluteFile = null;
		}
		return absoluteFile;
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
		URI result = null;
		String fileURIString = fileURI.toString();
		String ecorePath = AcceleoPackageRegistry.INSTANCE.getRegisteredEcorePackagePath(fileURIString);
		if (ecorePath != null) {
			result = URI.createPlatformResourceURI(ecorePath, false);
		}
		// FIXME stop reparsing extension points!!!!!!!!!!!!!!
		if (result == null && fileURIString.startsWith("http")) { //$NON-NLS-1$
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry
					.getExtensionPoint("org.eclipse.emf.ecore.generated_package"); //$NON-NLS-1$
			if (extensionPoint != null && extensionPoint.isValid()
					&& extensionPoint.getExtensions().length > 0) {
				IExtension[] extensions = extensionPoint.getExtensions();
				for (int i = 0; result == null && i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] members = extension.getConfigurationElements();
					for (int j = 0; result == null && j < members.length; j++) {
						IConfigurationElement member = members[j];
						String mURI = member.getAttribute("uri"); //$NON-NLS-1$
						String genModelPath = member.getAttribute("genModel"); //$NON-NLS-1$
						String bundleID = member.getNamespaceIdentifier();
						if (mURI != null && mURI.equals(fileURIString)
								&& Platform.getBundle(bundleID) != null && genModelPath != null) {
							String ecoreName = new Path(genModelPath).removeFileExtension().addFileExtension(
									"ecore").lastSegment(); //$NON-NLS-1$
							result = findEcore(Platform.getBundle(bundleID), ecoreName);
						}
					}
				}
			}
			if (result == null) {
				final Iterator<Map.Entry<URI, URI>> iterator = URIConverter.URI_MAP.entrySet().iterator();
				while (iterator.hasNext() && result == null) {
					Map.Entry<URI, URI> uriMapEntry = iterator.next();
					if (uriMapEntry.getKey().toString().equals(fileURI.toString())) {
						result = uriMapEntry.getValue();
					}
				}
			}
		}
		if (result != null) {
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
		if (newEditor instanceof AcceleoEditor) {
			selectAndReveal((AcceleoEditor)newEditor, aRegion, eObject);
		} else if (newEditor instanceof IEditingDomainProvider && eObject.eResource() != null) {
			IEditingDomainProvider editor = (IEditingDomainProvider)newEditor;
			String eObjectFragmentURI = eObject.eResource().getURIFragment(eObject);
			if (editor.getEditingDomain() != null && editor.getEditingDomain().getResourceSet() != null
					&& editor.getEditingDomain().getResourceSet().getResources().size() > 0
					&& eObjectFragmentURI != null) {
				EObject newObject = editor.getEditingDomain().getResourceSet().getResources().get(0)
						.getEObject(eObjectFragmentURI);
				if (editor instanceof IViewerProvider) {
					setSelectionToViewer(newObject, ((IViewerProvider)editor).getViewer());
				}
			}
		}
	}

	/**
	 * Creates a selection in the given Acceleo editor. A selection can be created for a text editor by
	 * defining a region.
	 * 
	 * @param acceleoEditor
	 *            is an editor
	 * @param aRegion
	 *            is the textual region to select (Text editor), can be null
	 * @param eObject
	 *            is the EObject to select (Ecore editor), can be null
	 */
	private static void selectAndReveal(AcceleoEditor acceleoEditor, IRegion aRegion, EObject eObject) {
		IRegion region = aRegion;
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
			if (eModule != null && eModule.eResource() != null && eObject != null) {
				String eObjectFragmentURI = null;
				if (eObject.eResource() != null) {
					eObjectFragmentURI = eObject.eResource().getURIFragment(eObject);
				} else if (eObject.eIsProxy()) {
					eObjectFragmentURI = ((InternalEObject)eObject).eProxyURI().fragment();
				}
				try {
					newEObject = eModule.eResource().getEObject(eObjectFragmentURI);
				} catch (IllegalArgumentException e) {
					AcceleoUIActivator.log(e, true);
					newEObject = eObject;
				}
				// Remark : We must be sure to get the positions of the new EObject
			}
			int defaultBegin;
			int defaultEnd;
			if (eObject instanceof ASTNode) {
				defaultBegin = ((ASTNode)eObject).getStartPosition();
				defaultEnd = ((ASTNode)eObject).getEndPosition();
			} else {
				defaultBegin = -1;
				defaultEnd = -1;
			}
			if (newEObject instanceof ASTNode) {
				selectAndRevealASTNode(acceleoEditor, (ASTNode)newEObject, defaultBegin, defaultEnd);
			}
		}
	}

	/**
	 * Select and reveal the given ASTNode in the given editor.
	 * 
	 * @param acceleoEditor
	 *            is the editor
	 * @param astNode
	 *            is the AST node to select
	 * @param defaultBegin
	 *            is the default beginning index if the given AST node start position is -1
	 * @param defaultEnd
	 *            is the default ending index if the given AST node end position is -1
	 */
	private static void selectAndRevealASTNode(AcceleoEditor acceleoEditor, ASTNode astNode,
			int defaultBegin, int defaultEnd) {
		int b;
		int e;
		if (astNode != null) {
			b = astNode.getStartPosition();
			e = astNode.getEndPosition();
		} else {
			b = -1;
			e = -1;
		}
		if (b == -1) {
			b = defaultBegin;
		}
		if (e == -1) {
			e = defaultEnd;
		}
		if (b > -1) {
			if ((b + IAcceleoConstants.DEFAULT_BEGIN.length() < acceleoEditor.getContent().getText().length())
					&& acceleoEditor.getContent().getText().substring(b,
							b + IAcceleoConstants.DEFAULT_BEGIN.length()).equals(
							IAcceleoConstants.DEFAULT_BEGIN)) {
				e = acceleoEditor.getContent().getText().indexOf(IAcceleoConstants.DEFAULT_END, b)
						+ IAcceleoConstants.DEFAULT_END.length();
			}
			if (e < b) {
				e = b;
			}
			acceleoEditor.selectAndReveal(b, e - b);
		}
	}

	/**
	 * Select the given EObject in the viewer.
	 * 
	 * @param selectedEObject
	 *            is the object to select
	 * @param viewer
	 *            is the viewer that contains some EMF objects
	 */
	private static void setSelectionToViewer(final EObject selectedEObject, final Viewer viewer) {
		if (selectedEObject != null && viewer != null) {
			Runnable runnable = new Runnable() {
				public void run() {
					viewer.setSelection(new StructuredSelection(selectedEObject), true);
				}
			};
			viewer.getControl().getDisplay().asyncExec(runnable);
		}
	}

	/**
	 * The region of the found word.
	 * 
	 * @param document
	 *            the current document
	 * @param offset
	 *            the given offset
	 * @return the region of the word at the given offset
	 */
	public static IRegion findIdentifierRegion(final IDocument document, final int offset) {
		final int startValue = -2;
		int start = startValue;
		int end = -1;

		int max = document.getLength();
		try {
			int pos = offset;
			char c;
			while (pos >= 0 && pos < max) {
				c = document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				--pos;
			}
			start = pos;
			pos = offset;
			int length = document.getLength();

			while (pos < length) {
				c = document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				++pos;
			}
			end = pos;
		} catch (BadLocationException x) {
			// do nothing
			AcceleoUIActivator.log(x, true);
		}
		if (start >= -1 && end > -1) {
			IRegion region = null;
			if (start == offset && end == offset) {
				region = new Region(offset, 0);
			} else if (start == offset) {
				region = new Region(start, end - start);
			} else {
				region = new Region(start + 1, end - start - 1);
			}
			return region;
		}
		return null;

	}

	/**
	 * Find the element selected in the given editor.
	 * 
	 * @param editor
	 *            The editor.
	 * @param withImportsExtends
	 *            Indicates if we should look in the imports and extends dependencies of the module in the
	 *            editor
	 * @return The EObject corresponding to the selected element in the editor.
	 */
	public static EObject findDeclaration(final AcceleoEditor editor, boolean withImportsExtends) {
		EObject res = null;
		int offset;
		final ISelection selection = editor.getSelectionProvider().getSelection();
		if (selection instanceof TextSelection) {
			offset = ((TextSelection)selection).getOffset();
		} else {
			offset = -1;
		}

		ASTNode astNode = null;
		if (withImportsExtends) {
			astNode = editor.getContent().getASTNode(offset, offset);
		} else {
			astNode = editor.getContent().getASTNodeWithoutImportsExtends(offset, offset);
		}

		if (astNode != null) {
			res = OpenDeclarationUtils.computeEObject(astNode, offset);
		}
		if (res == null) {
			final CSTNode cstNode = editor.getContent().getCSTNode(offset, offset);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(editor, astNode, cstNode);
			}
		}

		// If there is no result, we will try to see if what we are looking for is not the module header.
		if (res == null && editor.getContent().isInModuleHeader(offset, offset, true)) {
			res = editor.getContent().getAST();
		} else if (res == null && editor.getContent().isInModuleHeader(offset, offset, false)) {
			res = editor.getContent().getCST();
		}

		return res;
	}

	/**
	 * Finds the EObject matching the given ASTNode at the given offset.
	 * 
	 * @param astNode
	 *            The ASTNode
	 * @param offset
	 *            The offset
	 * @return The EObject matching the given ASTNode at the given offset
	 */
	private static EObject computeEObject(ASTNode astNode, int offset) {
		EObject res = null;
		// If the user has selected the name of the variable
		if (astNode instanceof Variable
				&& ((Variable)astNode).getName() != null
				&& (offset < (((Variable)astNode).getStartPosition() + ((Variable)astNode).getName().length()))) {
			res = astNode;
		} else if (astNode instanceof CollectionLiteralExp) {
			// If the user has selected something in a collection
			CollectionLiteralExp collectionLiteralExp = (CollectionLiteralExp)astNode;
			List<CollectionLiteralPart<EClassifier>> part = collectionLiteralExp.getPart();
			for (CollectionLiteralPart<EClassifier> collectionLiteralPart : part) {
				if (collectionLiteralPart instanceof CollectionItem) {
					CollectionItem item = (CollectionItem)collectionLiteralPart;
					OCLExpression<EClassifier> expression = item.getItem();
					if (expression.getStartPosition() <= offset && expression.getEndPosition() >= offset) {
						res = expression;
					}
				}
			}
		} else {
			// If the user has selected anything else...
			res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
		}
		return res;
	}

	/**
	 * Find the element selected in the given editor by resolving the AST on the selected Area.
	 * 
	 * @param editor
	 *            The editor.
	 * @return The EObject corresponding to the selected element in the editor.
	 */
	public static EObject findResolvedDeclaration(final AcceleoEditor editor) {
		EObject res = null;
		int offset;
		final ISelection selection = editor.getSelectionProvider().getSelection();
		if (selection instanceof TextSelection) {
			offset = ((TextSelection)selection).getOffset();
		} else {
			offset = -1;
		}
		final ASTNode astNode = editor.getContent().getResolvedASTNode(offset, offset);
		if (astNode != null) {
			// If the user has selected the name of the variable
			if (astNode instanceof Variable
					&& ((Variable)astNode).getName() != null
					&& (offset < (((Variable)astNode).getStartPosition() + ((Variable)astNode).getName()
							.length()))) {
				res = astNode;
			} else {
				// If the user has selected the type of the variable or anything else...
				res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
			}
		}
		if (res == null) {
			final CSTNode cstNode = editor.getContent().getCSTNode(offset, offset);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(editor, astNode, cstNode);
			}
		}

		// If there is no result, we will try to see if what we are looking for is not the module header.
		if (res == null && editor.getContent().isInModuleHeader(offset, offset, true)) {
			res = editor.getContent().getAST();
		} else if (res == null && editor.getContent().isInModuleHeader(offset, offset, false)) {
			res = editor.getContent().getCST();
		}

		return res;
	}

	/**
	 * Find all the occurrences of the element in the editor.
	 * 
	 * @param editor
	 *            The editor.
	 * @param object
	 *            The element.
	 * @return The list of all the matches.
	 */
	public static List<Match> findOccurrences(final AcceleoEditor editor, final EObject object) {
		final List<Match> list = new ArrayList<Match>();

		final ReferencesSearchQuery searchQuery = new ReferencesSearchQuery(editor, object);
		searchQuery.run(new NullProgressMonitor());

		final ReferencesSearchResult result = (ReferencesSearchResult)searchQuery.getSearchResult();
		final Object[] array = result.getElements();

		for (int i = 0; i < array.length; i++) {
			if (((ReferenceEntry)array[i]).getRegion() != null) {
				list.add(new Match(array[i], ((ReferenceEntry)array[i]).getRegion().getOffset(),
						((ReferenceEntry)array[i]).getRegion().getLength()));
			}
		}

		return list;
	}

	/**
	 * Select the matches from a list of matches that are in the same file as the given file and that have a
	 * valid region.
	 * 
	 * @param matches
	 *            The list of matches to filter.
	 * @param file
	 *            The given file.
	 * @return The new list of matches.
	 */
	public static List<Match> getMatchesFromTheFile(final List<Match> matches, final IFile file) {
		final List<Match> list = new ArrayList<Match>();

		for (Iterator<Match> iterator = matches.iterator(); iterator.hasNext();) {
			Match match = iterator.next();
			if (((ReferenceEntry)match.getElement()).getTemplateFile().equals(file)) {
				list.add(match);
			}
		}

		return list;
	}

	/**
	 * Check if two templates have the same name and the same position.
	 * 
	 * @param t1
	 *            The first template.
	 * @param t2
	 *            The second template.
	 * @return True if the two templates are equals following our criteria.
	 */
	public static boolean checkTemplateEqual(final Template t1, final Template t2) {
		boolean result;
		if (t1 != null && t2 != null) {
			result = t1.getName().equals(t2.getName());
			result = result && (t1.getStartPosition() == t2.getStartPosition());
			result = result && (t1.getEndPosition() == t2.getEndPosition());
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Returns the template containing the element or null if there isn't one.
	 * 
	 * @param object
	 *            The object.
	 * @return The template containing the element.
	 */
	public static Template findContainingTemplateOf(final EObject object) {
		EObject temp = object;
		while (temp != null && !(temp instanceof Template)) {
			temp = temp.eContainer();
		}

		return (Template)temp;
	}
}
