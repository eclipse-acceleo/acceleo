/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is an implementation of {@link EPackage.Descriptor} which will load EPackages only on demand. It
 * provides a set of static method to create new instances in an efficient way, not loading the actual model
 * and not build it but directly parsing the XML to retrieve the necessary information. When the Ecore model
 * is getting loaded (on demand then) the given {@link ResourceSet} will be populated with the model.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 * @since 3.4
 */
public class LazyEPackageDescriptor implements EPackage.Descriptor {
	/**
	 * The name attribute of the EPackage.
	 */
	private String name;

	/**
	 * The nsURI of the EPackage.
	 */
	private String nsURI;

	/**
	 * The nsPrefix attribute of the EPackage.
	 */
	private String nsPrefix;

	/**
	 * The normalized URI of the Resource providing the EPackage.
	 */
	private URI resourceURI;

	/**
	 * ESubPackages descriptors.
	 */
	private List<LazyEPackageDescriptor> subPackages = Lists.newArrayList();

	/**
	 * The root EPackage corresponding to the URI. Present only when loaded on demand.
	 */
	private Optional<EPackage> alreadyLoaded = Optional.absent();

	/**
	 * The uri converter to use to normalize the URIs and to resolve their input streams.
	 */
	private URIConverter converter;

	/**
	 * The registry to populate when the Ecore model is getting loaded.
	 */
	private EPackage.Registry registryToPopulate;

	/**
	 * A resourceSet to populate when the Ecore model is getting loaded.
	 */
	private Optional<ResourceSet> setToPopulate;

	/**
	 * Create a descriptor from raw data.
	 * 
	 * @param nsURI
	 *            the nsURI of the EPackage.
	 * @param nsPrefix
	 *            the nsPrefix of the EPackage
	 * @param name
	 *            the name of the EPackage.
	 * @param resourceURI
	 *            the uri where we will find the actual data corresponding to the EPackage.
	 * @param set
	 *            the resourceset to populate if the EPackage is actually loaded.
	 * @param registryToPopulate
	 *            the registry to populate if the EPackage is actually loaded.
	 */
	LazyEPackageDescriptor(String nsURI, String nsPrefix, String name, URI resourceURI, ResourceSet set,
			EPackage.Registry registryToPopulate) {
		super();
		this.nsURI = nsURI;
		this.name = name;
		this.nsPrefix = nsPrefix;
		this.converter = set.getURIConverter();
		this.resourceURI = converter.normalize(resourceURI);
		this.setToPopulate = Optional.of(set);
		this.registryToPopulate = registryToPopulate;

	}

	/**
	 * Create a new descriptor for an existing instance of {@link EPackage}.
	 * 
	 * @param instance
	 *            the instance to use to create the descriptor.
	 * @param converter
	 *            the {@link URIConverter} to use to normalize URIs or get input streams.
	 * @param registryToPopulate
	 *            the registry to populate.
	 */
	LazyEPackageDescriptor(EPackage instance, URIConverter converter, EPackage.Registry registryToPopulate) {
		this.nsURI = instance.getNsURI();
		this.nsPrefix = instance.getNsPrefix();
		this.name = instance.getName();
		this.resourceURI = converter.normalize(instance.eResource().getURI());
		this.alreadyLoaded = Optional.of(instance);
		this.converter = converter;
		this.registryToPopulate = registryToPopulate;
	}

	/**
	 * return the EPackage name.
	 * 
	 * @return the EPackage name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * return the EPackage nsURI.
	 * 
	 * @return the EPackage nsURI.
	 */
	public String getNsURI() {
		return nsURI;
	}

	/**
	 * return the EPackage NsPrefix.
	 * 
	 * @return the EPackage NsPrefix.
	 */
	public String getNsPrefix() {
		return nsPrefix;
	}

	/**
	 * Add a new ESubPackage descriptor to the current instance.
	 * 
	 * @param child
	 *            the child descriptor to add.
	 */
	public void addESubpackage(LazyEPackageDescriptor child) {
		this.subPackages.add(child);
	}

	/**
	 * return the normalized resource corresponding to the descriptor. Please note that several descriptors
	 * can return the same URI.
	 * 
	 * @return the normalized resource corresponding to the descriptor.
	 */
	public URI getResourceURI() {
		return resourceURI;
	}

	/**
	 * return the list of child descriptors.
	 * 
	 * @return the list of child descriptors.
	 */
	public List<LazyEPackageDescriptor> getESubpackages() {
		return subPackages;
	}

	/**
	 * Return the EPackage instance described by this descriptor. The EPackage instance might be loaded from
	 * its file and constructed during this call. Once constructed and loaded, it is always the same instance
	 * which is returned by a given descriptor instance.
	 * 
	 * @return the EPackage instance described by this descriptor.
	 */
	public EPackage getEPackage() {
		if (alreadyLoaded.isPresent()) {
			return alreadyLoaded.get();
		}
		EPackage result = null;

		if (setToPopulate.isPresent()) {
			Resource res = setToPopulate.get().getResource(getResourceURI(), true);
			EcoreUtil.resolveAll(res);
			if (res.getContents().size() > 0 && res.getContents().get(0) instanceof EPackage) {
				EPackage ePackage = (EPackage)res.getContents().get(0);
				alreadyLoaded = Optional.fromNullable(ePackage);
				for (Resource resource : setToPopulate.get().getResources()) {
					TreeIterator<EObject> allContents = resource.getAllContents();
					while (allContents.hasNext()) {
						EObject next = allContents.next();
						if (next instanceof EPackage) {
							registerEcorePackageHierarchy((EPackage)next, this.registryToPopulate);
						}
					}
				}
				result = ePackage;
			}
		}
		return result;
	}

	/**
	 * Register the given EPackage and its descendants.
	 * 
	 * @param ePackage
	 *            is the root package to register
	 * @param registry
	 *            the registry to register the package in .
	 */
	private void registerEcorePackageHierarchy(EPackage ePackage, EPackage.Registry registry) {
		if (ePackage.getNsURI() != null) {
			// The MTL ecore file mustn't be dynamic!!!
			// TODO JMU we should use an extension point for the dynamic ecore files we would like to exclude
			if (!"mtl".equals(ePackage.getNsPrefix()) && !"mtlnonstdlib".equals(ePackage.getNsPrefix()) //$NON-NLS-1$ //$NON-NLS-2$
					&& !"mtlstdlib".equals(ePackage.getNsPrefix()) && !"oclstdlib".equals(ePackage.getNsPrefix())) { //$NON-NLS-1$ //$NON-NLS-2$
				registry.put(ePackage.getNsURI(), ePackage);
			}
		}

		for (EPackage subPackage : ePackage.getESubpackages()) {
			registerEcorePackageHierarchy(subPackage, registry);
		}
	}

	public EFactory getEFactory() {
		return getEPackage().getEFactoryInstance();
	}

	/**
	 * Create a new descriptor from an in memory instance of {@link EPackage}.
	 * 
	 * @param loadedEPackage
	 *            the in memorry instance to use.
	 * @param registry
	 *            the registry to populate.
	 * @return a new descriptor representing the instance.
	 */
	public static LazyEPackageDescriptor create(EPackage loadedEPackage, EPackage.Registry registry) {
		URIConverter converter = null;
		if (loadedEPackage.eResource() != null && loadedEPackage.eResource().getResourceSet() != null) {
			converter = loadedEPackage.eResource().getResourceSet().getURIConverter();
		}
		if (converter == null) {
			converter = new ExtensibleURIConverterImpl();
		}
		LazyEPackageDescriptor current = new LazyEPackageDescriptor(loadedEPackage, converter, registry);
		for (EPackage child : loadedEPackage.getESubpackages()) {
			current.addESubpackage(create(child, registry));
		}
		return current;
	}

	/**
	 * Create a new descriptor from an accessible URI.
	 * 
	 * @param metaURI
	 *            the URI of an Ecore file
	 * @param set
	 *            the {@link ResourceSet} to populate
	 * @param registry
	 *            the registry to populate.
	 * @return a new descriptor representing the instance.
	 */
	public static List<LazyEPackageDescriptor> create(URI metaURI, ResourceSet set, EPackage.Registry registry) {
		List<LazyEPackageDescriptor> result = new ArrayList<LazyEPackageDescriptor>();
		InputStream is = null;
		try {
			is = set.getURIConverter().createInputStream(metaURI, Collections.EMPTY_MAP);

			final SAXParserFactory factory = SAXParserFactory.newInstance();
			final InputSource input = new InputSource(is);
			final SAXParser saxParser = factory.newSAXParser();
			EcoreEPackageSAXHandler lazyEPackageDescriptorSAXHandler = new EcoreEPackageSAXHandler(set,
					metaURI, registry);
			saxParser.parse(input, lazyEPackageDescriptorSAXHandler);
			is.close();
			result = lazyEPackageDescriptorSAXHandler.getRootDescriptors();
			// CHECKSTYLE:OFF
		} catch (Throwable e) {
			/*
			 * Anything might happen here. File is wrongly named .ecore, parsing fails, IO fails. In any case,
			 * we don't want the whole process to fail, it just mean we are unable to create the descriptor
			 * and we should just return null.
			 */
			// CHECKSTYLE:ON
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// swallow and return null
				}
			}
		}

		return result;

	}

	/**
	 * A Sax handler which parses Ecore files and retrieve enough information to register it in an
	 * {@link EPackage.Registry}.
	 * 
	 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
	 */
	private static class EcoreEPackageSAXHandler extends DefaultHandler {
		/**
		 * The XML attribute "name".
		 */
		private static final String NAME_ATTR = "name"; //$NON-NLS-1$

		/**
		 * The XML attribute "nsPrefix".
		 */
		private static final String NS_PREFIX_ATTR = "nsPrefix"; //$NON-NLS-1$

		/**
		 * The XML attribute "nsURI".
		 */
		private static final String NS_URI_ATTR = "nsURI"; //$NON-NLS-1$

		/**
		 * The XML tag used to start a new subPackage.
		 */
		private static final String E_SUBPACKAGES = "eSubpackages"; //$NON-NLS-1$

		/**
		 * The XML-xsi type for an EPackage.
		 */
		private static final String ECORE_E_PACKAGE = "ecore:EPackage"; //$NON-NLS-1$

		/**
		 * The uri of the resource we are parsing.
		 */
		private URI resourceURI;

		/**
		 * The first descriptor created during the parsing, aka the root EPackage.
		 */
		private List<LazyEPackageDescriptor> rootDescriptor = new ArrayList<LazyEPackageDescriptor>();

		/**
		 * A stack keeping track of the created EPackages. The peek of the stack always is the EPackage we are
		 * currently parsing.
		 */
		private Stack<LazyEPackageDescriptor> currentDescriptor;

		/**
		 * The resourceSet to use when instanciating {@link LazyEPackageDescriptor}.
		 */
		private ResourceSet set;

		/**
		 * The registry to use when instanciating {@link LazyEPackageDescriptor}.
		 */
		private Registry registry;

		/**
		 * Create a new Sax Handler to parse Ecore files.
		 * 
		 * @param set
		 *            The resourceSet to use when instanciating {@link LazyEPackageDescriptor}.
		 * @param resourceURI
		 *            The uri of the resource we are parsing.
		 * @param registry
		 *            The registry to use when instanciating {@link LazyEPackageDescriptor}.
		 */
		public EcoreEPackageSAXHandler(ResourceSet set, URI resourceURI, Registry registry) {
			this.set = set;
			this.registry = registry;
			this.resourceURI = resourceURI;
			this.currentDescriptor = new Stack<LazyEPackageDescriptor>();
		}

		/**
		 * return descriptors for root EPackage. Might be null if there is nothing of interest in the file.
		 * 
		 * @return descriptors for root EPackage. Might be null if there is nothing of interest in the file.
		 */
		public List<LazyEPackageDescriptor> getRootDescriptors() {
			return rootDescriptor;
		}

		@Override
		public void startElement(String saxURI, String localName, String qName, Attributes attributes)
				throws SAXException {
			super.startElement(saxURI, localName, qName, attributes);
			if (ECORE_E_PACKAGE.equals(qName)) {
				String nsURI = attributes.getValue(NS_URI_ATTR);
				String nsPrefix = attributes.getValue(NS_PREFIX_ATTR);
				String name = attributes.getValue(NAME_ATTR);
				LazyEPackageDescriptor root = new LazyEPackageDescriptor(nsURI, nsPrefix, name,
						this.resourceURI, set, registry);
				if (currentDescriptor.size() == 0) {
					rootDescriptor.add(root);
					currentDescriptor.push(root);
				}
			} else if (E_SUBPACKAGES.equals(qName)) {
				String nsURI = attributes.getValue(NS_URI_ATTR);
				String nsPrefix = attributes.getValue(NS_PREFIX_ATTR);
				String name = attributes.getValue(NAME_ATTR);
				LazyEPackageDescriptor newOne = new LazyEPackageDescriptor(nsURI, nsPrefix, name,
						this.resourceURI, set, registry);
				currentDescriptor.peek().addESubpackage(newOne);
				currentDescriptor.push(newOne);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
			if (ECORE_E_PACKAGE.equals(qName)) {
				currentDescriptor.pop();
			} else if (E_SUBPACKAGES.equals(qName)) {
				currentDescriptor.pop();
			}
		}
	}

}
