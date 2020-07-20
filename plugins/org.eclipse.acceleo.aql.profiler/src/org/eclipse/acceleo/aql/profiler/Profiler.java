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
package org.eclipse.acceleo.aql.profiler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * The default template profiler.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.0
 */
public class Profiler {

	/**
	 * The EObject representing time that is not profiled.
	 */
	public static final EObject INTERNAL = ProfilerPackage.eINSTANCE.getEClassifier("Internal"); //$NON-NLS-1$

	/**
	 * Context class for tree construction while profiling.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class Context {
		/**
		 * Cache Object -> Context.
		 */
		private final Map<EObject, Context> childrenCache = new HashMap<EObject, Context>();

		/**
		 * Parent context.
		 */
		private final Context parent;

		/**
		 * Current call entry.
		 */
		private final LoopProfileEntry currentEntry;

		/**
		 * Constructor.
		 * 
		 * @param parent
		 *            parent context if any
		 * @param entry
		 *            the current loop entry
		 */
		public Context(Context parent, LoopProfileEntry entry) {
			this.parent = parent;
			currentEntry = entry;
		}

		/**
		 * Getter for the Profiling entry that match the given object.
		 * 
		 * @param monitored
		 *            the object to monitor
		 * @return the Profiling entry
		 */
		public Context getChildContext(EObject monitored) {
			Context childContext = childrenCache.get(monitored);
			if (childContext == null) {
				final LoopProfileEntry entry = ProfilerFactory.eINSTANCE.createLoopProfileEntry();
				entry.setCreateTime(System.currentTimeMillis());
				entry.setMonitored(monitored);
				// TODO set positions
				// entry.setTextBegin(begin);
				// entry.setTextEnd(end);
				childContext = new Context(this, entry);
				childrenCache.put(monitored, childContext);
			}
			return childContext;
		}

		/**
		 * Getter for the current entry.
		 * 
		 * @return the current entry
		 */
		public LoopProfileEntry getcurrentEntry() {
			return currentEntry;
		}

		/**
		 * Getter for the parent of this context.
		 * 
		 * @return the parent context
		 */
		public Context getParent() {
			return parent;
		}
	}

	/**
	 * The profiling currentContext stack.
	 */
	private Context currentContext;

	/**
	 * Profiling resource container.
	 */
	private ProfileResource resource;

	/**
	 * The current loop element to use.
	 */
	private ProfileEntry currentLoopEntry;

	/**
	 * Set the given element as a loop element of the current monitored element.
	 * 
	 * @param loopElement
	 *            the current loop element to consider.
	 */
	public void loop(EObject loopElement) {
		final LoopProfileEntry entry = currentContext.getcurrentEntry();

		stopCurrentLoopEntry();
		startCurrentLoopEntry(loopElement);
		entry.getLoopElements().add(currentLoopEntry);
	}

	/**
	 * Stop the current loop element profiling.
	 */
	private void stopCurrentLoopEntry() {
		if (currentLoopEntry != null) {
			currentLoopEntry.stop();
			currentLoopEntry = null;
		}
	}

	/**
	 * Start the profiling for the new loop element.
	 * 
	 * @param loopElement
	 *            the new loop element to profile
	 */
	private void startCurrentLoopEntry(EObject loopElement) {
		currentLoopEntry = ProfilerFactory.eINSTANCE.createProfileEntry();
		currentLoopEntry.setCreateTime(System.currentTimeMillis());
		currentLoopEntry.setMonitored(loopElement);
		currentLoopEntry.start();
	}

	/**
	 * Start monitoring for the given object.
	 * 
	 * @param monitored
	 *            the object to monitor
	 */
	public void start(EObject monitored) {
		final Context nextContext;
		if (currentContext != null) {
			nextContext = currentContext.getChildContext(monitored);
			nextContext.getcurrentEntry().start();
			currentContext.getcurrentEntry().getCallees().add(nextContext.getcurrentEntry());
		} else {
			final LoopProfileEntry entry = ProfilerFactory.eINSTANCE.createLoopProfileEntry();
			entry.setCreateTime(System.currentTimeMillis());
			entry.setMonitored(monitored);
			// TODO set positions
			// entry.setTextBegin(begin);
			// entry.setTextEnd(end);
			if (resource == null) {
				resource = ProfilerFactory.eINSTANCE.createProfileResource();
			}
			resource.getEntries().add(entry);
			entry.start();
			nextContext = new Context(null, entry);
		}
		currentContext = nextContext;
	}

	/**
	 * Reset statistics for the profiler.
	 */
	public void reset() {
		currentContext = null;
		resource = null;
	}

	/**
	 * Stop monitoring for the current monitored object.
	 */
	public void stop() {
		currentContext.getcurrentEntry().stop();
		stopCurrentLoopEntry();
		currentContext = currentContext.getParent();
	}

	/**
	 * Save profiling results to the given URI.
	 * 
	 * @param modelURI
	 *            the URI where to save results.
	 * @throws IOException
	 *             if model serialisation fail
	 */
	public void save(String modelURI) throws IOException {
		addDefaultNodes();
		computePercentage();
		if (resource != null) {
			save(resource, modelURI);
		}
	}

	/**
	 * Compute percentage for the current profile tree.
	 */
	private void computePercentage() {
		if (resource != null) {
			for (ProfileEntry root : resource.getEntries()) {
				final long baseTime = root.getDuration();
				root.setPercentage(100.0);
				final Iterator<EObject> itContent = root.eAllContents();
				while (itContent.hasNext()) {
					final ProfileEntry node = (ProfileEntry)itContent.next();
					if (node.getDuration() == baseTime) {
						// prevents NaN when baseTime == 0
						node.setPercentage(100);
					} else {
						node.setPercentage(node.getDuration() * 100.0 / baseTime);
					}
				}
			}
		}
	}

	/**
	 * Add default entries to reach 100%.
	 */
	protected void addDefaultNodes() {
		if (resource != null) {
			for (ProfileEntry root : resource.getEntries()) {
				addDefaultNodes(root);
			}
		}
	}

	/**
	 * Add default entries to reach 100%.
	 * 
	 * @param entry
	 *            the root node
	 */
	private void addDefaultNodes(ProfileEntry entry) {
		long childrenDuration = 0;

		for (ProfileEntry child : entry.getCallees()) {
			childrenDuration += child.getDuration();
			addDefaultNodes(child);
		}
		if (entry.getCallees().size() > 0 && entry.getDuration() - childrenDuration > 0) {
			ProfileEntry def = ProfilerFactory.eINSTANCE.createProfileEntry();
			def.setCreateTime(System.currentTimeMillis());
			def.setDuration(entry.getDuration() - childrenDuration);
			def.setMonitored(INTERNAL);
			entry.getCallees().add(def);
		}
	}

	/**
	 * Serialize the current profiling data.
	 * 
	 * @param result
	 *            root element for profiling data
	 * @param modelURI
	 *            URI where to save data
	 * @throws IOException
	 *             if save fail
	 */
	private void save(EObject result, String modelURI) throws IOException {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		final Resource newModelResource = resourceSet.createResource(URI.createFileURI(URI.decode(modelURI)));
		newModelResource.getContents().add(result);
		final Map<?, ?> options = new HashMap<Object, Object>();
		newModelResource.save(options);
		newModelResource.unload();
	}

	/**
	 * Returns the profile resource.
	 * 
	 * @return The profile resource.
	 * @since 4.0
	 */
	public ProfileResource getProfileResource() {
		return resource;
	}
}
