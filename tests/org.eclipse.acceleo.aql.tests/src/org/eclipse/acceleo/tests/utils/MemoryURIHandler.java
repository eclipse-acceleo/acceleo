/*******************************************************************************
 *  Copyright (c) 2017 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.tests.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIHandler;

/**
 * A memory {@link URIHandler}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MemoryURIHandler implements URIHandler {

	/**
	 * A not openable file.
	 */
	private static final String NOT_OPENABLE_TXT = "notOpenable.txt";

	/**
	 * A not writable file.
	 */
	private static final String NOT_WRITABLE_TXT = "notWritable.txt";

	/**
	 * A not closable file.
	 */
	private static final String NOT_CLOSABLE_TXT = "notClosable.txt";

	/**
	 * Resources.
	 */
	private final Map<URI, ByteArrayOutputStream> resources = new HashMap<URI, ByteArrayOutputStream>();

	/**
	 * Clears the memory.
	 */
	public void clear() {
		resources.clear();
	}

	@Override
	public boolean canHandle(URI uri) {
		return "acceleotests".equals(uri.scheme());
	}

	@Override
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		final InputStream res;

		final ByteArrayOutputStream outputStream = resources.get(uri);
		if (outputStream != null) {
			res = new ByteArrayInputStream(outputStream.toByteArray());
		} else if (NOT_OPENABLE_TXT.equals(uri.lastSegment()) || NOT_WRITABLE_TXT.equals(uri.lastSegment())
				|| NOT_CLOSABLE_TXT.equals(uri.lastSegment())) {
			res = new ByteArrayInputStream(new byte[] {});
		} else {
			throw new IOException("Resource " + uri + " doesn't exist in memory.");
		}

		return res;
	}

	@Override
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		final OutputStream res;

		if (NOT_OPENABLE_TXT.equals(uri.lastSegment())) {
			throw new IOException("Can't open OutputStream");
		} else if (NOT_WRITABLE_TXT.equals(uri.lastSegment())) {
			res = new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					throw new IOException("Can't write to OutputStream");
				}
			};
		} else if (NOT_CLOSABLE_TXT.equals(uri.lastSegment())) {
			res = new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					// nothing to do here
				}

				@Override
				public void close() throws IOException {
					throw new IOException("Can't close OutputStream");
				}
			};
		} else {
			res = new ByteArrayOutputStream();
			resources.put(uri, (ByteArrayOutputStream)res);
		}

		return res;
	}

	@Override
	public void delete(URI uri, Map<?, ?> options) throws IOException {
		resources.remove(uri);
	}

	@Override
	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		return Collections.emptyMap();
	}

	@Override
	public boolean exists(URI uri, Map<?, ?> options) {
		return resources.containsKey(uri);
	}

	@Override
	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		return Collections.emptyMap();
	}

	@Override
	public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
		// nothing to do here
	}

}
