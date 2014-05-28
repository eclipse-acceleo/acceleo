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
package org.eclipse.acceleo.common.internal.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;

/**
 * This registry will act as an extension of the global package registry : dynamic models will be registered
 * and added here, yet they'll never be seen by other plugins.
 * <p>
 * We need to be able to react to changes made to these models : if the user removes or adds a new class, he
 * expects to be able to use this new concept in his generators. Likewise, if the workspace model is deleted,
 * we need to be able to restore the package registry so that the metamodel as installed in the plugins can be
 * used anew without relaunching a new eclipse instance.
 * </p>
 * <p>
 * This registry <b>must be separate from the global registry</b>! We cannot risk adding a model in the global
 * registry and potentially breaking any model the user edits!
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public final class AcceleoPackageRegistry extends HashMap<String, Object> implements EPackage.Registry {

	/** Singleton instance of our dynamic registry. */
	public static final AcceleoPackageRegistry INSTANCE = new AcceleoPackageRegistry();

	/** Generated SUID. */
	private static final long serialVersionUID = 5976916017848022583L;

	/** This is the registry we'll delegate calls to. */
	private EPackage.Registry delegate = EPackage.Registry.INSTANCE;

	/**
	 * For dynamic ecore files only. To get the ecore file path of the registered nsURI. Dynamic packages are
	 * registered in the EMF Registry by using the {@link #registerEcorePackages(String)} method. The map key
	 * is the dynamic nsURI of an EPackage and the value is the ecore file path used to register this nsURI.
	 */
	private Map<String, String> dynamicEcorePackagePaths = new HashMap<String, String>();

	/**
	 * This is a singleton. Access the sole instance through {@link #INSTANCE}.
	 */
	private AcceleoPackageRegistry() {
		// Hides default constructor.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		delegate.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(key) || delegate.containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		return super.containsValue(value) || delegate.containsValue(value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#entrySet()
	 */
	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		Set<Map.Entry<String, Object>> dynamicEntries = super.entrySet();
		Set<Map.Entry<String, Object>> globalEntries = delegate.entrySet();
		return new AcceleoMultipleSet<Map.Entry<String, Object>>(dynamicEntries, globalEntries);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		Object result = super.get(key);
		if (result == null) {
			result = delegate.get(key);
		}
		return result;
	}

	/**
	 * This will be used by our workspace listener so that it can react to changes on the dynamic ecore
	 * models.
	 * 
	 * @return The map of dynamic ecore models.
	 */
	public Map<String, String> getDynamicEcorePackagePaths() {
		return dynamicEcorePackagePaths;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry#getEFactory(java.lang.String)
	 */
	public EFactory getEFactory(String nsURI) {
		if (containsKey(nsURI)) {
			Object ePackage = get(nsURI);
			if (ePackage instanceof EPackage) {
				EPackage result = (EPackage)ePackage;
				return result.getEFactoryInstance();
			}
		}
		return delegate.getEFactory(nsURI);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry#getEPackage(java.lang.String)
	 */
	public EPackage getEPackage(String nsURI) {
		EPackage found = null;
		if (containsKey(nsURI)) {
			Object ePackage = get(nsURI);
			if (ePackage instanceof EPackage) {
				found = (EPackage)ePackage;
			} else if (ePackage instanceof EPackage.Descriptor) {
				found = ((EPackage.Descriptor)ePackage).getEPackage();
			}
		}
		if (found == null) {
			found = delegate.getEPackage(nsURI);
			if (found == null && nsURI != null && !nsURI.startsWith(IAcceleoConstants.LITERAL_BEGIN)) {
				found = searchInRegisteredEPackageInstances(nsURI);
			}
		}
		return found;
	}

	/**
	 * Search the nsURI by browsing through the registerd EPackage instances and looking up their nsURI
	 * attribute. This case might arise when for instance, the plugin.xml registration of the nsURI does not
	 * match the EPackage nsURI.
	 * 
	 * @param nsURI
	 *            the nsURI we are looking for.
	 * @return a matching {@link EPackage} if found, null otherwise.
	 */
	private EPackage searchInRegisteredEPackageInstances(String nsURI) {
		Collection<Object> values = this.values();
		for (Object object : values) {
			if (object instanceof EPackage && ((EPackage)object).eResource() != null) {
				EPackage ePackage = (EPackage)object;
				Resource eResource = ePackage.eResource();
				URI uri = eResource.getURI();
				if (uri != null && nsURI.equals(uri.toString())) {
					return ePackage;
				}
			}
		}
		return null;
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
	 */
	public String getRegisteredEcorePackagePath(String nsURI) {
		return dynamicEcorePackagePaths.get(nsURI);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return super.isEmpty() && delegate.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#keySet()
	 */
	@Override
	public Set<String> keySet() {
		Set<String> dynamicKeys = super.keySet();
		Set<String> globalKeys = delegate.keySet();
		return new AcceleoMultipleSet<String>(dynamicKeys, globalKeys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		if (dynamicEcorePackagePaths.containsKey(key)) {
			return super.put(key, value);
		}
		return delegate.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for (Map.Entry<? extends String, ? extends Object> entry : m.entrySet()) {
			// delegation will be taken care of in put(String, Object)
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Register the given ecore file in the EMF Package Registry. It loads the ecore file and browses the
	 * elements, it means the root EPackage and its descendants.
	 * 
	 * @param pathName
	 *            is the path of the ecore file to register
	 * @param resourceSet
	 *            The resource set.
	 * @return the NsURI of the ecore root package, or the given path name if it isn't possible to find the
	 *         corresponding NsURI
	 */
	public List<String> registerEcorePackages(String pathName, ResourceSet resourceSet) {
		List<String> res = new ArrayList<String>();
		List<LazyEPackageDescriptor> descriptors = new ArrayList<LazyEPackageDescriptor>();
		URIConverter converter = resourceSet.getURIConverter();
		if (converter == null) {
			converter = new ExtensibleURIConverterImpl();
		}
		URI metaURI = null;
		// Specifically get rid of Ecore.ecore so that we never dynamically register it
		if (pathName != null && pathName.endsWith(".ecore") && !pathName.startsWith("http://") //$NON-NLS-1$ //$NON-NLS-2$
				&& !pathName.endsWith("Ecore.ecore")) { //$NON-NLS-1$
			// Try and load the ecore file with its URI as-is or fall back to platform resource URI.
			metaURI = URI.createURI(URI.decode(pathName));
			if (!metaURI.isPlatform()) {
				metaURI = URI.createPlatformResourceURI(pathName, true);
			}
			/*
			 * If the resourceset already have the EPackage loaded as a model, lets reload it.
			 */
			List<Resource> resources = resourceSet.getResources();
			for (Resource resource : resources) {
				if (resource.getURI() != null && resource.getURI().equals(metaURI)) {
					resource.unload();
					try {
						resource.load(new HashMap<String, String>());
					} catch (IOException e) {
						AcceleoCommonPlugin.log(e, false);
					}
				}
			}
			descriptors.addAll(LazyEPackageDescriptor.create(metaURI, resourceSet, this));

			// If that failed, try and load the ecore file with a platform:/resource URI
			if (descriptors.size() == 0) {
				metaURI = URI.createPlatformResourceURI(pathName, false);
				descriptors.addAll(LazyEPackageDescriptor.create(metaURI, resourceSet, this));
			}

			// If all failed, try and load the model with a platform:/plugin URI
			if (descriptors.size() == 0) {
				metaURI = URI.createPlatformPluginURI(pathName, false);
				descriptors.addAll(LazyEPackageDescriptor.create(metaURI, resourceSet, this));
			}
		}

		final List<LazyEPackageDescriptor> toRemove = new ArrayList<LazyEPackageDescriptor>();
		for (LazyEPackageDescriptor descriptor : descriptors) {
			if ("".equals(descriptor.getNsURI())) { //$NON-NLS-1$
				toRemove.add(descriptor);
			}
		}
		descriptors.removeAll(toRemove);

		if (descriptors.size() != 0) {
			/*
			 * If there is already a LazyEPackageDescriptor for this uri, lets start by removing it.
			 */
			registerOrReplaceInRegistry(this, metaURI, descriptors);
			for (LazyEPackageDescriptor descriptor : descriptors) {
				res.add(descriptor.getNsURI());
			}
			return res;
		}
		res.add(pathName);
		return res;
	}

	/**
	 * Register the given descriptor in the registry but first de-registering any instance which might have
	 * been previously registered with an equivalent resource URI. This is done to support cases where for
	 * instance, the end user changed the nsURI of an {@link EPackage} in its workspace. We don't want the old
	 * nsURI to stay around.
	 * 
	 * @param registry
	 *            registry to update.
	 * @param resourceURI
	 *            the resource URI
	 * @param descriptors
	 *            descriptors to register.
	 */
	private void registerOrReplaceInRegistry(Registry registry, URI resourceURI,
			List<LazyEPackageDescriptor> descriptors) {

		List<String> toRemove = Lists.newArrayList();
		Set<LazyEPackageDescriptor> toUnload = Sets.newLinkedHashSet();
		for (Map.Entry<String, Object> entry : registry.entrySet()) {
			/*
			 * I only take care of my own instances to avoid unpredictable side effects on other tools.
			 */
			if (entry.getValue() instanceof LazyEPackageDescriptor) {
				LazyEPackageDescriptor registered = (LazyEPackageDescriptor)entry.getValue();
				if (registered.getResourceURI().equals(resourceURI)) {
					toRemove.add(entry.getKey());
				}
				toUnload.add(registered);
			}
		}
		for (String nsURI : toRemove) {
			registry.remove(nsURI);
		}
		for (LazyEPackageDescriptor descriptor : descriptors) {
			registerDescriptorsHierarchy(registry, descriptor);
		}

	}

	/**
	 * Register the given descriptor and its sub descriptors in the given registry.
	 * 
	 * @param registry
	 *            registry to update.
	 * @param descriptor
	 *            the descriptor to register.
	 */
	private void registerDescriptorsHierarchy(Registry registry, LazyEPackageDescriptor descriptor) {
		if (descriptor.getNsURI() != null) {
			// The MTL ecore file mustn't be dynamic!!!
			// TODO JMU we should use an extension point for the dynamic ecore files we would like to exclude
			if (!"mtl".equals(descriptor.getNsPrefix()) && !"mtlnonstdlib".equals(descriptor.getNsPrefix()) //$NON-NLS-1$ //$NON-NLS-2$
					&& !"mtlstdlib".equals(descriptor.getNsPrefix()) && !"oclstdlib".equals(descriptor.getNsPrefix())) { //$NON-NLS-1$ //$NON-NLS-2$
				dynamicEcorePackagePaths.put(descriptor.getNsURI(), descriptor.getResourceURI().toString());

				registry.put(descriptor.getNsURI(), descriptor);
			}
		}

		for (LazyEPackageDescriptor child : descriptor.getESubpackages()) {
			registerDescriptorsHierarchy(registry, child);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	@Override
	public Object remove(Object key) {
		boolean hasBeenRemoved = false;
		if (dynamicEcorePackagePaths.containsKey(key) && EMFPlugin.IS_ECLIPSE_RUNNING) {
			List<Resource> resources = AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET
					.getResources();
			Iterator<Resource> iterator = resources.iterator();
			while (iterator.hasNext()) {
				Resource resource = iterator.next();
				if (key instanceof String && key.equals(resource.getURI().toString())) {
					iterator.remove();
					super.remove(key);
					hasBeenRemoved = true;
				} else {
					String value = dynamicEcorePackagePaths.get(key);
					if (value.equals(resource.getURI().toString())) {
						iterator.remove();
						super.remove(key);
						hasBeenRemoved = true;
					}
				}
			}
		}
		Object value = get(key);
		if (value instanceof LazyEPackageDescriptor) {
			super.remove(key);
			hasBeenRemoved = true;
		}
		if (!hasBeenRemoved) {
			// if we found it in this package registry, it means that it was in the workspace.
			// no need to delete another version in the plugin.
			return delegate.remove(key);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#size()
	 */
	@Override
	public int size() {
		return super.size() + delegate.size();
	}

	/**
	 * Removes the given ecore file from the EMF Package Registry.
	 * 
	 * @param pathName
	 *            is the path of the ecore file to remove
	 */
	public void unregisterEcorePackages(String pathName) {
		Iterator<Map.Entry<String, String>> entryIterator = dynamicEcorePackagePaths.entrySet().iterator();
		while (entryIterator.hasNext()) {
			Map.Entry<String, String> dynamicEcore = entryIterator.next();
			if (dynamicEcore.getValue().endsWith(pathName)) {
				remove(dynamicEcore.getKey());
				entryIterator.remove();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.HashMap#values()
	 */
	@Override
	public Collection<Object> values() {
		Collection<Object> dynamicValues = super.values();
		Collection<Object> globalValues = delegate.values();
		return new AcceleoMultipleCollection<Object>(dynamicValues, globalValues);
	}

	/**
	 * Utility operation to register Ecore packages for dynamic metamodel already loaded by EMF.
	 * 
	 * @param loadedEPackage
	 *            The EPackage to register.
	 */
	public void registerEcorePackage(EPackage loadedEPackage) {
		this.registerDescriptorsHierarchy(this, LazyEPackageDescriptor.create(loadedEPackage, this));
	}

	/**
	 * This custom implementation of a Set will simply allow us to "wrap" around two sets so that iterating
	 * over one switches to the second once all entries have been browsed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class AcceleoMultipleSet<K> extends AbstractSet<K> {
		/** First of the two sets to iterate over. */
		private final Set<K> firstSet;

		/** Second of the two sets to iterate over. */
		private final Set<K> secondSet;

		/**
		 * This default constructor initializes the two sets this instance will wrap.
		 * 
		 * @param set1
		 *            First of the two sets to iterate over.
		 * @param set2
		 *            Second of the two sets to iterate over.
		 */
		public AcceleoMultipleSet(Set<K> set1, Set<K> set2) {
			firstSet = set1;
			secondSet = set2;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.AbstractCollection#iterator()
		 */
		@Override
		public Iterator<K> iterator() {
			return new AcceleoMultipleIterator<K>(firstSet.iterator(), secondSet.iterator());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.AbstractCollection#size()
		 */
		@Override
		public int size() {
			return firstSet.size() + secondSet.size();
		}
	}

	/**
	 * This custom implementation of a Collection will simply allow us to "wrap" around two Collections so
	 * that iterating over one switches to the second once all entries have been browsed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class AcceleoMultipleCollection<V> extends AbstractCollection<V> {
		/** First of the two collections to iterate over. */
		private final Collection<V> firstCollection;

		/** Second of the two collections to iterate over. */
		private final Collection<V> secondCollection;

		/**
		 * This default constructor initializes the two collections this instance will wrap.
		 * 
		 * @param collection1
		 *            First of the two collections to iterate over.
		 * @param collection2
		 *            Second of the two collections to iterate over.
		 */
		public AcceleoMultipleCollection(Collection<V> collection1, Collection<V> collection2) {
			firstCollection = collection1;
			secondCollection = collection2;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.AbstractCollection#iterator()
		 */
		@Override
		public Iterator<V> iterator() {
			return new AcceleoMultipleIterator<V>(firstCollection.iterator(), secondCollection.iterator());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.AbstractCollection#size()
		 */
		@Override
		public int size() {
			return firstCollection.size() + secondCollection.size();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.AbstractCollection#contains(java.lang.Object)
		 */
		@Override
		public boolean contains(Object o) {
			return firstCollection.contains(o) || secondCollection.contains(o);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.AbstractCollection#clear()
		 */
		@Override
		public void clear() {
			firstCollection.clear();
			secondCollection.clear();
		}
	}

	/**
	 * This custom implementation of an Iterator will simply allow us to "wrap" around two Iterators so that
	 * iterating over one switches to the second once all entries have been browsed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class AcceleoMultipleIterator<E> implements Iterator<E> {
		/** First of the two iterators to iterate over. */
		private final Iterator<E> firstIterator;

		/** Second of the two iterators to iterate over. */
		private final Iterator<E> secondIterator;

		/** Keeps track of the current iterator. */
		private Iterator<E> current;

		/**
		 * This default constructor initializes the two iterators this instance will wrap.
		 * 
		 * @param iterator1
		 *            First of the two iterators to iterate over.
		 * @param iterator2
		 *            Second of the two iterators to iterate over.
		 */
		public AcceleoMultipleIterator(Iterator<E> iterator1, Iterator<E> iterator2) {
			firstIterator = iterator1;
			secondIterator = iterator2;
			current = firstIterator;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return firstIterator.hasNext() || secondIterator.hasNext();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			if (firstIterator.hasNext()) {
				return firstIterator.next();
			}
			current = secondIterator;
			return secondIterator.next();

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			current.remove();
		}
	}

}
