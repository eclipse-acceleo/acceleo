/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.doc.internal;

import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.body;
import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.head;
import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.header;
import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.html;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.services.ResourceServices;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.services.XPathServices;

/**
 * Utility class used to generate the Acceleo Query documentation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class DocumentationGenerator {
	/**
	 * The name of the charset to use to write the documentation.
	 */
	private static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	/**
	 * The list of the service providers to consider for the standard documentation.
	 */
	private static final Class<?>[] STANDARD_SERVICE_PROVIDERS = new Class<?>[] {AnyServices.class,
			BooleanServices.class, CollectionServices.class, ComparableServices.class, EObjectServices.class,
			NumberServices.class, ResourceServices.class, StringServices.class, XPathServices.class, };

	/**
	 * The constructor.
	 */
	private DocumentationGenerator() {
		// Prevent instantiation
	}

	/**
	 * Launches the generation of the documentation.
	 * 
	 * @param args
	 *            The arguments of the generation
	 */
	public static void main(String[] args) {
		File pluginFolder = new File(args[0]);

		System.out.println("Prepare the generation of the documentation for "
				+ pluginFolder.getAbsolutePath());

		File inputFolder = new File(pluginFolder, "input"); //$NON-NLS-1$
		File documentationFolder = new File(pluginFolder, "pages"); //$NON-NLS-1$
		File indexHtmlFile = new File(documentationFolder, "index.html"); //$NON-NLS-1$

		// toc.xml
		StringBuffer buffer = AQLHelpContentUtils.computeToc(STANDARD_SERVICE_PROVIDERS);
		try {
			File tocFile = new File(pluginFolder, "toc.xml");
			System.out.println("Writing the content of toc.xml in " + tocFile.getAbsolutePath());
			Files.write(buffer, tocFile, Charset.forName(UTF8));
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		// index.html
		try {
			List<StringBuffer> sections = AQLHelpContentUtils.computeAQLOverviewSections();

			String inputHtmlContent = Files.toString(new File(inputFolder, "index.html"), Charset
					.forName(UTF8));
			int indexOfBodyStart = inputHtmlContent.indexOf("<body>");
			if (indexOfBodyStart != -1 && indexOfBodyStart + 6 < inputHtmlContent.length()) {
				inputHtmlContent = inputHtmlContent.substring(indexOfBodyStart + 6);
			}
			int indexOfBodyEnd = inputHtmlContent.indexOf("</body>");
			if (indexOfBodyEnd != -1) {
				inputHtmlContent = inputHtmlContent.substring(0, indexOfBodyEnd);
			}
			sections.add(new StringBuffer(inputHtmlContent));

			StringBuffer stringBuffer = html(head(), body(header(true), sections));

			System.out.println("Writing content of " + indexHtmlFile.getAbsolutePath());
			Files.write(stringBuffer, indexHtmlFile, Charset.forName(UTF8));
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		// Services
		for (Class<?> serviceProviderClass : STANDARD_SERVICE_PROVIDERS) {
			if (serviceProviderClass.isAnnotationPresent(ServiceProvider.class)) {
				try {
					List<StringBuffer> sections = AQLHelpContentUtils
							.computeServiceSections(serviceProviderClass);
					StringBuffer stringBuffer = html(head(), body(header(false), sections));

					File file = new File(documentationFolder, AQLHelpContentUtils.AQL_HREF_PREFIX
							+ serviceProviderClass.getSimpleName().toLowerCase() + ".html");
					System.out.println("Writing content of " + file.getAbsolutePath());
					Files.write(stringBuffer, file, Charset.forName(UTF8));
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}
