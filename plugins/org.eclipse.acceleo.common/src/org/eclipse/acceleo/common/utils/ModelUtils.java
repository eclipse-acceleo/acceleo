/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Utility class for model loading/saving and serialization.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class ModelUtils {

	/** Constant for the file encoding system property. */
	private static final String ENCODING_PROPERTY = "file.encoding"; //$NON-NLS-1$

	/**
	 * The key for the loading error message.
	 */
	private static final String LOADING_ERROR_KEY = "ModelUtils.LoadingError"; //$NON-NLS-1$

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private ModelUtils() {
		// prevents instantiation
	}

	/**
	 * Attaches the given {@link EObject} to a new resource created in a new {@link ResourceSet} with the
	 * given URI.
	 * 
	 * @param resourceURI
	 *            URI of the new resource to create.
	 * @param root
	 *            EObject to attach to a new resource.
	 * @return The resource <tt>root</tt> has been attached to.
	 */
	public static Resource attachResource(URI resourceURI, EObject root) {
		if (root == null || resourceURI == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("ModelUtils.NullRoot")); //$NON-NLS-1$
		}
		final Resource newResource = createResource(resourceURI);
		newResource.getContents().add(root);
		return newResource;
	}

	/**
	 * Attaches the given {@link EObject} to a new resource created in the given {@link ResourceSet} with the
	 * given URI.
	 * 
	 * @param resourceURI
	 *            URI of the new resource to create.
	 * @param resourceSet
	 *            ResourceSet in which to create the resource.
	 * @param root
	 *            EObject to attach to a new resource.
	 * @return The resource <tt>root</tt> has been attached to.
	 */
	public static Resource attachResource(URI resourceURI, ResourceSet resourceSet, EObject root) {
		if (root == null || resourceURI == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("ModelUtils.NullRoot")); //$NON-NLS-1$
		}
		final Resource newResource = createResource(resourceURI, resourceSet);
		newResource.getContents().add(root);
		return newResource;
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @return The {@link Resource} given the model extension it is intended for.
	 */
	public static Resource createResource(URI modelURI) {
		return createResource(modelURI, new ResourceSetImpl());
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 */
	public static Resource createResource(URI modelURI, ResourceSet resourceSet) {
		ensureResourceFactoryPresent(resourceSet);
		return resourceSet.createResource(modelURI, IAcceleoConstants.XMI_CONTENT_TYPE);
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 * @since 3.1
	 */
	public static Resource createBinaryResource(URI modelURI, ResourceSet resourceSet) {
		ensureResourceFactoryPresent(resourceSet);
		return resourceSet.createResource(modelURI, IAcceleoConstants.BINARY_CONTENT_TYPE);
	}

	/**
	 * Loads the models contained by the given directory in the given ResourceSet.
	 * <p>
	 * If <code>resourceSet</code> is <code>null</code>, all models will be loaded in a new resourceSet.
	 * </p>
	 * 
	 * @param directory
	 *            The directory from which to load the models.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in. If <code>null</code>, all models will be
	 *            loaded in a new resourceSet.
	 * @return The models contained by the given directory.
	 * @throws IOException
	 *             Thrown if an I/O operation has failed or been interrupted.
	 */
	public static List<EObject> getModelsFrom(File directory, ResourceSet resourceSet) throws IOException {
		return getModelsFrom(directory, null, resourceSet);
	}

	/**
	 * Loads the files with the given extension contained by the given directory as EObjects in the given
	 * ResourceSet.
	 * <p>
	 * If <code>resourceSet</code> is <code>null</code>, all models will be loaded in a new resourceSet.
	 * </p>
	 * <p>
	 * The argument <code>extension</code> is in fact the needed suffix for its name in order for a file to be
	 * loaded. If it is equal to &quot;rd&quot;, a file named &quot;model.aird&quot; will be loaded, but so
	 * would be a file named &quot;Shepherd&quot;.
	 * </p>
	 * <p>
	 * The empty String or <code>null</code> will result in all the files of the given directory to be loaded,
	 * and would then be equivalent to {@link #getModelsFrom(File)}.
	 * </p>
	 * 
	 * @param directory
	 *            The directory from which to load the models.
	 * @param extension
	 *            File extension of the files to load. If <code>null</code>, will consider all extensions.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in. If <code>null</code>, all models will be
	 *            loaded in a new resourceSet.
	 * @return The models contained by the given directory.
	 * @throws IOException
	 *             Thrown if an I/O operation has failed or been interrupted.
	 */
	public static List<EObject> getModelsFrom(File directory, String extension, ResourceSet resourceSet)
			throws IOException {
		final List<EObject> models = new ArrayList<EObject>();
		final String fileExtension;
		if (extension != null) {
			fileExtension = extension;
		} else {
			fileExtension = ""; //$NON-NLS-1$
		}
		final ResourceSet theResourceSet;
		if (resourceSet == null) {
			theResourceSet = new ResourceSetImpl();
		} else {
			theResourceSet = resourceSet;
		}
		if (directory.exists() && directory.isDirectory() && directory.listFiles() != null) {
			final File[] files = directory.listFiles();
			for (int i = 0; i < files.length; i++) {
				final File aFile = files[i];

				if (!aFile.isDirectory() && aFile.getName().matches("[^.].*?\\Q" + fileExtension + "\\E")) { //$NON-NLS-1$ //$NON-NLS-2$
					models.add(load(aFile, theResourceSet));
				}
			}
		}

		return models;
	}

	/**
	 * Loads a model from a {@link java.io.File File} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param file
	 *            {@link java.io.File File} containing the model to be loaded.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The model loaded from the file.
	 * @throws IOException
	 *             If the given file does not exist.
	 */
	public static EObject load(File file, ResourceSet resourceSet) throws IOException {
		return load(URI.createFileURI(file.getPath()), resourceSet);
	}

	/**
	 * Load a model from an {@link java.io.InputStream InputStream} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param stream
	 *            The inputstream to load from
	 * @param fileName
	 *            The original filename
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The loaded model
	 * @throws IOException
	 *             If the given file does not exist.
	 */
	public static EObject load(InputStream stream, String fileName, ResourceSet resourceSet)
			throws IOException {
		if (stream == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("ModelUtils.NullInputStream")); //$NON-NLS-1$
		}
		EObject result = null;

		final Resource modelResource = createResource(URI.createURI(fileName), resourceSet);
		modelResource.load(stream, Collections.emptyMap());
		if (modelResource.getContents().size() > 0) {
			result = modelResource.getContents().get(0);
		}

		List<Diagnostic> errors = modelResource.getErrors();
		for (Diagnostic diagnostic : errors) {
			if (diagnostic != null) {
				String errorMessage = AcceleoCommonMessages.getString(LOADING_ERROR_KEY, fileName);
				errorMessage += '\n' + diagnosticString(diagnostic);
				AcceleoCommonPlugin.log(errorMessage, false);
			}
		}

		return result;
	}

	/**
	 * Loads a model from the String representing the location of a model.
	 * <p>
	 * This can be called with paths of the form
	 * <ul>
	 * <li><code>/pluginID/path</code></li>
	 * <li><code>platform:/plugin/pluginID/path</code></li>
	 * <li><code>platform:/resource/pluginID/path</code></li>
	 * </ul>
	 * </p>
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param path
	 *            Location of the model.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The model loaded from the path.
	 * @throws IOException
	 *             If the path doesn't resolve to a reachable location.
	 */
	public static EObject load(String path, ResourceSet resourceSet) throws IOException {
		if (path == null || "".equals(path)) { //$NON-NLS-1$
			throw new IllegalArgumentException(AcceleoCommonMessages.getString("ModelUtils.NullPath")); //$NON-NLS-1$
		}
		final EObject result;
		// path is already defined with a platform scheme
		if (path.startsWith("platform")) { //$NON-NLS-1$
			result = load(URI.createURI(path), resourceSet);
		} else {
			EObject temp = null;
			try {
				// Will first try and load as if the model is in the plugins
				temp = load(URI.createPlatformPluginURI(path, true), resourceSet);
			} catch (IOException e) {
				// Model wasn't in the plugins, try and load it within the workspace
				try {
					temp = load(URI.createPlatformResourceURI(path, true), resourceSet);
				} catch (IOException ee) {
					// Silently discarded, will fail later on
				}
			}
			result = temp;
		}
		if (result == null) {
			throw new IOException(AcceleoCommonMessages.getString("ModelUtils.LoadFailure", path)); //$NON-NLS-1$
		}
		if (result.eResource() != null) {
			List<Diagnostic> errors = result.eResource().getErrors();
			for (Diagnostic diagnostic : errors) {
				if (diagnostic != null) {
					String errorMessage = AcceleoCommonMessages.getString(LOADING_ERROR_KEY, path);
					errorMessage += '\n' + diagnosticString(diagnostic);
					AcceleoCommonPlugin.log(errorMessage, false);
				}
			}
		}
		return result;
	}

	/**
	 * Loads a model from an {@link org.eclipse.emf.common.util.URI URI} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The model loaded from the URI.
	 * @throws IOException
	 *             If the given file does not exist.
	 */
	public static EObject load(URI modelURI, ResourceSet resourceSet) throws IOException {
		return load(modelURI, resourceSet, false);
	}

	/**
	 * Loads a model from an {@link org.eclipse.emf.common.util.URI URI} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @param resolve
	 *            If true the method will resolve the proxies in the loaded resource.
	 * @return The model loaded from the URI.
	 * @throws IOException
	 *             If the given file does not exist.
	 * @since 3.5
	 */
	public static EObject load(URI modelURI, ResourceSet resourceSet, boolean resolve) throws IOException {
		ensureResourceFactoryPresent(resourceSet);
		String fileExtension = modelURI.fileExtension();
		ensureDefaultResourceFactoryPresent(resourceSet, fileExtension);

		Resource modelResource;
		EObject result = null;
		if (resolve) {
			modelResource = resourceSet.getResource(modelURI, true);
			if (!modelResource.isLoaded()) {
				modelResource.load(resourceSet.getLoadOptions());
				EcoreUtil.resolveAll(modelResource);
			}
		} else {
			modelResource = resourceSet.getResource(modelURI, true);
		}

		if (modelResource.getContents().size() > 0) {
			result = modelResource.getContents().get(0);
		}
		List<Diagnostic> errors = modelResource.getErrors();
		for (Diagnostic diagnostic : errors) {
			if (diagnostic != null) {
				String errorMessage = AcceleoCommonMessages.getString(LOADING_ERROR_KEY, modelURI.toString());
				errorMessage += '\n' + diagnosticString(diagnostic);
				AcceleoCommonPlugin.log(errorMessage, false);
			}
		}
		return result;
	}

	/**
	 * Returns a string representation of the given diagnostic object.
	 * 
	 * @param diagnostic
	 *            The diagnostic
	 * @return a string representation of the given diagnostic object.
	 */
	private static String diagnosticString(Diagnostic diagnostic) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(AcceleoCommonMessages
				.getString("ModelUtils.DiagnosticMessage", diagnostic.getMessage())); //$NON-NLS-1$
		buffer.append('\n');
		buffer.append(AcceleoCommonMessages.getString("ModelUtils.DiagnosticLocation", diagnostic //$NON-NLS-1$
				.getLocation(), Integer.valueOf(diagnostic.getLine()), Integer
				.valueOf(diagnostic.getColumn())));
		return buffer.toString();
	}

	/**
	 * Saves a model as a file to the given path.
	 * 
	 * @param root
	 *            Root of the objects to be serialized in a file.
	 * @param path
	 *            File where the objects have to be saved.
	 * @throws IOException
	 *             Thrown if an I/O operation has failed or been interrupted during the saving process.
	 */
	public static void save(EObject root, String path) throws IOException {
		if (root == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("ModelUtils.NullSaveRoot")); //$NON-NLS-1$
		}
		final Resource newModelResource = createResource(URI.createFileURI(path));
		newModelResource.getContents().add(root);
		final Map<String, String> options = new HashMap<String, String>();
		options.put(XMLResource.OPTION_ENCODING, System.getProperty(ENCODING_PROPERTY));
		newModelResource.save(options);
	}

	/**
	 * Serializes the given EObjet as a String.
	 * 
	 * @param root
	 *            Root of the objects to be serialized.
	 * @return The given EObjet serialized as a String.
	 * @throws IOException
	 *             Thrown if an I/O operation has failed or been interrupted during the saving process.
	 */
	public static String serialize(EObject root) throws IOException {
		if (root == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("ModelUtils.NullSaveRoot")); //$NON-NLS-1$
		}
		// Copies the root to avoid modifying it
		final EObject copyRoot = EcoreUtil.copy(root);
		attachResource(URI.createFileURI("resource.xml"), copyRoot); //$NON-NLS-1$

		final StringWriter writer = new StringWriter();
		final Map<String, String> options = new HashMap<String, String>();
		options.put(XMLResource.OPTION_ENCODING, System.getProperty(ENCODING_PROPERTY));
		// Should not throw ClassCast since uri calls for an xml resource
		((XMLResource)copyRoot.eResource()).save(writer, options);
		final String result = writer.toString();
		writer.flush();
		return result;
	}

	/**
	 * Looks up the value in the EMF Registry. It catches an EMF WrappedException. It is very useful if the
	 * EMF registry is corrupted by other contributions.
	 * 
	 * @param nsURI
	 *            is the NsURI key to search
	 * @return the EPackage value
	 * @since 3.0
	 */
	public static EPackage getEPackage(String nsURI) {
		try {
			return AcceleoPackageRegistry.INSTANCE.getEPackage(nsURI);
		} catch (WrappedException e) {
			return null;
		}
	}

	/**
	 * Looks up the value in the EMF Registry without resolving LazyEPackageDescriptor. This avoid loading
	 * models if all the information the client need is an attribute of the EPackage instance. It catches an
	 * EMF WrappedException. It is very useful if the EMF registry is corrupted by other contributions.
	 * 
	 * @param nsURI
	 *            is the NsURI key to search
	 * @return an Object being either an {@link EPackage} a LazyEPackageDescriptor. Clients have to check for
	 *         these types.
	 * @since 3.4
	 */
	public static Object getEPackageOrDescriptor(String nsURI) {
		try {
			Object found = AcceleoPackageRegistry.INSTANCE.get(nsURI);
			if (found == null) {
				found = AcceleoPackageRegistry.INSTANCE.getEPackage(nsURI);
			}
			return found;
		} catch (WrappedException e) {
			return null;
		}
	}

	/**
	 * Register the given ecore file in the EMF Package Registry. It loads the ecore file and browses the
	 * elements, it means the root EPackage and its descendants.
	 * 
	 * @param pathName
	 *            is the path of the ecore file to register
	 * @return the NsURI of the ecore root package, or the given path name if it isn't possible to find the
	 *         corresponding NsURI
	 * @since 3.0
	 * @deprecated
	 */
	@Deprecated
	public static String registerEcorePackages(String pathName) {
		return AcceleoPackageRegistry.INSTANCE.registerEcorePackages(pathName,
				AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET).get(0);
	}

	/**
	 * To get the ecore file path of the registered nsURI. Dynamic packages are registered in the EMF EPackage
	 * Registry by using the 'registerEcorePackages' method. The result is not null when the EPackage has been
	 * registered in the EMF Registry with the 'registerEcorePackages' method.
	 * 
	 * @param nsURI
	 *            the NsURI of an EPackage
	 * @return the ecore file path that contains the given EPackage, or null if it hasn't been registered in
	 *         the EMF Registry with the 'registerEcorePackages' method
	 * @since 3.0
	 * @deprecated
	 */
	@Deprecated
	public static String getRegisteredEcorePackagePath(String nsURI) {
		return AcceleoPackageRegistry.INSTANCE.getRegisteredEcorePackagePath(nsURI);
	}

	/**
	 * This will make sure that a resource factory is registered in the ResourceSet for the given file
	 * extension.
	 * <p>
	 * We'll first search the resource set to check that a resource factory exists. If not, we'll search
	 * within the global factory registry for one. If found, we'll register this factory in the resource set;
	 * if not, we'll register a new XMI resource factory in the resource set. A call to
	 * resourceSet#createResource() will never fail in {@link NullPointerException} after a call to this
	 * method.
	 * </p>
	 * 
	 * @param resourceSet
	 *            The resource set in which to make sure a resource factory for extension
	 *            <code>extension</code> exists.
	 * @param extension
	 *            The file extension of the file to load
	 */
	private static void ensureDefaultResourceFactoryPresent(ResourceSet resourceSet, String extension) {
		if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(extension)) {
			// don't handle EMTL files here, it can create conflict with binary resources...
			return;
		}
		String fileExtension = extension;
		if (fileExtension == null || fileExtension.length() == 0) {
			fileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;
		}
		Object resourceFactory = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().get(
				fileExtension);
		if (resourceFactory == null) {
			resourceFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
					.get(fileExtension);
			if (resourceFactory != null) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
						resourceFactory);
			} else {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
						new XMIResourceFactoryImpl());
			}
		}
	}

	/**
	 * This will make sure that a resource factory is registered in the ResourceSet.
	 * <p>
	 * We'll first search the resource set to check that a resource factory exists. If not, we'll search
	 * within the global factory registry for one. If found, we'll register this factory in the resource set;
	 * if not, we'll register a new XMI resource factory in the resource set. A call to
	 * resourceSet#createResource() will never fail in {@link NullPointerException} after a call to this
	 * method.
	 * </p>
	 * 
	 * @param resourceSet
	 *            The resource set in which to make sure a resource factory for extension
	 *            <code>extension</code> exists.
	 */
	private static void ensureResourceFactoryPresent(ResourceSet resourceSet) {
		// Ensure the registration of the resource factories by content type.
		if (resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().get(
				IAcceleoConstants.XMI_CONTENT_TYPE) == null
				|| resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().get(
						IAcceleoConstants.BINARY_CONTENT_TYPE) == null) {
			Object binaryFactory = Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().get(
					IAcceleoConstants.BINARY_CONTENT_TYPE);
			if (binaryFactory == null) {
				Object binaryResourceFactory = Resource.Factory.Registry.INSTANCE
						.getContentTypeToFactoryMap().get(IAcceleoConstants.BINARY_CONTENT_TYPE);
				resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
						IAcceleoConstants.BINARY_CONTENT_TYPE, binaryResourceFactory);
			}
			Object xmiFactory = Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().get(
					IAcceleoConstants.XMI_CONTENT_TYPE);
			if (xmiFactory == null) {
				Object xmiResourceFactory = Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap()
						.get(IAcceleoConstants.XMI_CONTENT_TYPE);
				resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
						IAcceleoConstants.XMI_CONTENT_TYPE, xmiResourceFactory);
			}
		}

		final String xmi = "xmi"; //$NON-NLS-1$
		if (resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().get(xmi) == null) {
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(xmi,
					new XMIResourceFactoryImpl());
		}
	}
}
