/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.acceleo.aql.profiler;

import java.util.Iterator;

import org.eclipse.acceleo.aql.profiler.internal.FlatProfiler;
import org.eclipse.acceleo.aql.profiler.internal.TreeProfiler;
import org.eclipse.emf.ecore.EObject;

/**
 * Utility class for profiler.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ProfilerUtils {

	/**
	 * Profile model extension.
	 */
	public static final String PROFILE_EXTENSION = "mtlp"; //$NON-NLS-1$

	/**
	 * {@link IProfiler} kinds.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static enum Representation {
		/**
		 * The tree profiler. It creates a tree representing the profiled process.
		 */
		TREE("Tree profiling", "It creates a tree representing the profiled process"),
		/**
		 * The flat profiler. It creates a flat representation of the profiled process.
		 */
		FLAT("Flat profiling", "It creates a flat representation of the profiled process");

		/**
		 * The human name of the profiling representation.
		 */
		private final String name;

		/**
		 * The human hint of the profiling representation.
		 */
		private final String hint;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            the human name of the profiling representation
		 * @param hint
		 *            the human hint of the profiling representation
		 */
		Representation(String name, String hint) {
			this.name = name;
			this.hint = hint;
		}

		/**
		 * Gets the name of the profiling representation.
		 * 
		 * @return the name of the profiling representation
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the hint of the profiling representation.
		 * 
		 * @return the hint of the profiling representation
		 */
		public String getHint() {
			return hint;
		}

	};

	/**
	 * Constructor.
	 */
	private ProfilerUtils() {
		// utility class
	}

	/**
	 * Computes percentages for the given {@link ProfileResource}.
	 * 
	 * @param resource
	 *            the {@link ProfileResource} to compute
	 */
	public static void computePercentage(ProfileResource resource) {
		ProfileEntry root = resource.getEntry();
		if (root != null) {
			final long baseTime = root.getDuration();
			root.setPercentage(100.0F);
			final Iterator<EObject> itContent = root.eAllContents();
			while (itContent.hasNext()) {
				final ProfileEntry node = (ProfileEntry)itContent.next();
				node.setPercentage(node.getDuration() * 100.0F / baseTime);
			}
		}
	}

	/**
	 * Gets a fresh new {@link IProfiler}.
	 * 
	 * @param representation
	 *            the {@link Representation} to use for profiling.
	 * @param childFactory
	 *            the factory to build profiler model.
	 * @return a fresh new {@link IProfiler}
	 */
	public static IProfiler getProfiler(Representation representation, ProfilerFactory profilerFactory) {
		IProfiler res = null;

		switch (representation) {
			case TREE:
				res = new TreeProfiler(profilerFactory);
				break;

			case FLAT:
				res = new FlatProfiler(profilerFactory);
				break;

			default:
				throw new IllegalArgumentException();
		}

		return res;
	}
}
