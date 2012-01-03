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
package org.eclipse.acceleo.engine.internal.debug;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * The fragment URI of an AST node.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ASTFragment {
	/**
	 * Inner separator between the file short name and the EObject fragment URI.
	 */
	private static final String SEPARATOR = "|"; //$NON-NLS-1$

	/**
	 * The generic pattern used to specify that every object name is valid.
	 */
	private static final String ALL_NAMES_FILTER = "*"; //$NON-NLS-1$

	/** AST node this fragments wraps. */
	private final ASTNode astNode;

	/**
	 * Short name of the file, it means the name of the file without extension.
	 */
	private String fileShortName;

	/**
	 * The EObject fragment URI.
	 */
	private String fragmentURI;

	/**
	 * Indicates if the AST fragment is empty.
	 */
	private boolean empty;

	/**
	 * The EObject name filter. It is used to filter the objects which suspend the debug thread.
	 */
	private String eObjectNameFilter;

	/**
	 * Constructor.
	 * 
	 * @param astNode
	 *            is the AST node
	 */
	public ASTFragment(ASTNode astNode) {
		super();
		this.astNode = astNode;
		if (astNode == null) {
			this.fileShortName = ""; //$NON-NLS-1$
			this.fragmentURI = ""; //$NON-NLS-1$
			this.empty = true;
		} else if (astNode.eResource() != null) {
			this.fileShortName = astNode.eResource().getURI().trimFileExtension().lastSegment();
			this.fragmentURI = astNode.eResource().getURIFragment(astNode);
			this.empty = false;
		} else {
			this.fileShortName = ""; //$NON-NLS-1$
			this.fragmentURI = EcoreUtil.getURI(astNode).toString();
			this.empty = this.fragmentURI.length() == 0;
		}
		this.eObjectNameFilter = ""; //$NON-NLS-1$
	}

	/**
	 * Constructor.
	 * <p>
	 * a = new ASTFragment(eObject);
	 * </p>
	 * <p>
	 * b = new ASTFragment(a.toString());
	 * </p>
	 * <p>
	 * a.equals(b) is true
	 * </p>
	 * 
	 * @param string
	 *            is the string representation of an old AST node
	 */
	public ASTFragment(String string) {
		super();
		this.astNode = null;
		if (string == null) {
			this.fileShortName = ""; //$NON-NLS-1$
			this.fragmentURI = ""; //$NON-NLS-1$
			this.empty = true;
			this.eObjectNameFilter = ""; //$NON-NLS-1$
		} else {
			int i = string.indexOf(SEPARATOR);
			if (i > -1) {
				this.fileShortName = string.substring(0, i);
				int j = string.indexOf(SEPARATOR, i + SEPARATOR.length());
				if (j > -1) {
					this.eObjectNameFilter = string.substring(j + SEPARATOR.length());
				} else {
					j = string.length();
					this.eObjectNameFilter = ""; //$NON-NLS-1$
				}
				this.fragmentURI = string.substring(i + SEPARATOR.length(), j);
				this.empty = this.fileShortName.length() == 0 && this.fragmentURI.length() == 0;
			} else {
				this.fileShortName = ""; //$NON-NLS-1$
				this.fragmentURI = string;
				this.empty = this.fragmentURI.length() == 0;
				this.eObjectNameFilter = ""; //$NON-NLS-1$
			}
		}
	}

	/**
	 * Returns the AST node this fragments represents (can be null).
	 * 
	 * @return AST node this fragments represents.
	 */
	public ASTNode getASTNode() {
		return astNode;
	}

	/**
	 * Sets the EObject name filter.
	 * 
	 * @return the EObject name filter
	 */
	public String getEObjectNameFilter() {
		return eObjectNameFilter;
	}

	/**
	 * Gets the EObject name filter.
	 * 
	 * @param nameFilter
	 *            the EObject name filter
	 */
	public void setEObjectNameFilter(String nameFilter) {
		eObjectNameFilter = nameFilter;
	}

	/**
	 * Indicates if the AST fragment is empty.
	 * 
	 * @return true if the AST fragment is empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (fileShortName + fragmentURI).hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ASTFragment) {
			ASTFragment other = (ASTFragment)obj;
			boolean result;
			if (fileShortName.equals(other.fileShortName) && fragmentURI.equals(other.fragmentURI)) {
				if ("".equals(eObjectNameFilter) || "".equals(other.eObjectNameFilter) //$NON-NLS-1$ //$NON-NLS-2$
						|| ALL_NAMES_FILTER.equals(eObjectNameFilter)
						|| ALL_NAMES_FILTER.equals(other.eObjectNameFilter)) {
					result = true;
				} else if (eObjectNameFilter.startsWith(ALL_NAMES_FILTER)
						&& other.eObjectNameFilter.endsWith(eObjectNameFilter.substring(ALL_NAMES_FILTER
								.length()))) {
					result = true;
				} else if (other.eObjectNameFilter.startsWith(ALL_NAMES_FILTER)
						&& eObjectNameFilter.endsWith(other.eObjectNameFilter.substring(ALL_NAMES_FILTER
								.length()))) {
					result = true;
				} else if (eObjectNameFilter.endsWith(ALL_NAMES_FILTER)
						&& other.eObjectNameFilter.startsWith(eObjectNameFilter.substring(0,
								eObjectNameFilter.length() - ALL_NAMES_FILTER.length()))) {
					result = true;
				} else if (other.eObjectNameFilter.endsWith(ALL_NAMES_FILTER)
						&& eObjectNameFilter.startsWith(other.eObjectNameFilter.substring(0,
								other.eObjectNameFilter.length() - ALL_NAMES_FILTER.length()))) {
					result = true;
				} else {
					result = eObjectNameFilter.equals(other.eObjectNameFilter);
				}
			} else {
				result = false;
			}
			return result;
		}
		return super.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (empty) {
			return ""; //$NON-NLS-1$
		}
		return fileShortName + SEPARATOR + fragmentURI + SEPARATOR + eObjectNameFilter;
	}
}
