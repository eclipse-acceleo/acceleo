/*******************************************************************************
 *  Copyright (c) 2017, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.cdo.providers.configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.eclipse.emf.cdo.common.lob.CDOBlob;
import org.eclipse.emf.cdo.common.lob.CDOClob;
import org.eclipse.emf.cdo.eresource.CDOBinaryResource;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.eresource.CDOTextResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.internal.cdo.view.CDOURIHandler;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction;
import org.eclipse.emf.spi.cdo.InternalCDOView;

/**
 * A {@link CDOURIHandler} that support {@link CDOBinaryResource} CDO.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("restriction")
public class AqlCDOURIHandler extends CDOURIHandler {

    /**
     * The buffer size.
     */
    private static final int BUFFER_SIZE = 8 * 1024;

    /**
     * An {@link OutputStream} that buffer writes and feed them as the content of a {@link CDOResourceNode}.
     * 
     * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
     */
    private static final class CDONodeOutputStream extends OutputStream {

        /**
         * The {@link CDOResourceNode} to set contents to.
         */
        private final CDOResourceNode node;

        /**
         * The buffer.
         */
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        /**
         * Constructor.
         * 
         * @param node
         *            the {@link CDOResourceNode} to set contents to
         */
        private CDONodeOutputStream(CDOResourceNode node) {
            this.node = node;
        }

        @Override
        public void write(int b) throws IOException {
            buffer.write(b);
        }

        @Override
        public void flush() throws IOException {
            buffer.flush();
        }

        @Override
        public void close() throws IOException {
            if (node instanceof CDOBinaryResource) {
                ((CDOBinaryResource) node).setContents(new CDOBlob(new ByteArrayInputStream(buffer.toByteArray())));
            } else if (node instanceof CDOTextResource) {
                final CDOTextResource textResource = (CDOTextResource) node;
                final String encoding = getEncoding(textResource);
                textResource.setContents(new CDOClob(new StringReader(buffer.toString(encoding))));
            } else {
                throw new IllegalStateException("CDOResourceNode type not supported.");
            }
            buffer.close();
        }

    }

    /**
     * Constructor.
     * 
     * @param view
     *            the {@link InternalCDOView}
     */
    public AqlCDOURIHandler(InternalCDOView view) {
        super(view);
    }

    @Override
    public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
        final InputStream res;

        final InternalCDOView view = getView();
        final CDOResourceNode node = view.getResourceNode(uri.path());
        if (node instanceof CDOBinaryResource) {
            res = ((CDOBinaryResource) node).getContents().getContents();
        } else if (node instanceof CDOTextResource) {
            final CDOTextResource textResource = (CDOTextResource) node;
            final String encoding = getEncoding(textResource);
            final Reader contents = textResource.getContents().getContents();

            char[] charBuffer = new char[BUFFER_SIZE];
            StringBuilder builder = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = contents.read(charBuffer, 0, charBuffer.length)) != -1) {
                builder.append(charBuffer, 0, numCharsRead);
            }
            res = new ByteArrayInputStream(builder.toString().getBytes(encoding));

        } else {
            res = super.createInputStream(uri, options);
        }

        return res;
    }

    /**
     * Gets the encoding of the given {@link CDOTextResource} and fallback to UTF-8.
     * 
     * @param textResource
     *            the {@link CDOTextResource}
     * @return the encoding of the given {@link CDOTextResource} and fallback to UTF-8
     */
    private static String getEncoding(final CDOTextResource textResource) {
        final String encoding;
        if (textResource.getEncoding() != null) {
            encoding = textResource.getEncoding();
        } else {
            encoding = "UTF-8";
        }
        return encoding;
    }

    @Override
    public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
        final OutputStream res;

        final InternalCDOView view = getView();
        if (view instanceof InternalCDOTransaction) {
            final InternalCDOTransaction transaction = (InternalCDOTransaction) view;

            final CDOResourceNode node;
            // TODO non binary resource
            if (uri.path().lastIndexOf('/') == 0) {
                node = transaction.getOrCreateBinaryResource(uri.path().substring(1));
            } else {
                node = transaction.getOrCreateBinaryResource(uri.path());
            }
            res = new CDONodeOutputStream(node);
        } else {
            res = super.createOutputStream(uri, options);
        }

        return res;
    }

}
