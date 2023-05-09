/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.doc.internal;

import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.body;
import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.head;
import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.header;
import static org.eclipse.acceleo.query.doc.internal.AQLHelpContentUtils.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.services.PropertiesServices;
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
	 * Replacement marker.
	 */
	private static final String TRIGGER_TO_APPEND_STD_DOC = "<p>These sections are listing all the services of the standard library of AQL.</p>";

	/**
	 * The name of the charset to use to write the documentation.
	 */
	private static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	/**
	 * The list of the service providers to consider for the standard documentation.
	 */
	private static final Class<?>[] STANDARD_SERVICE_PROVIDERS = new Class<?>[] {AnyServices.class,
			BooleanServices.class, CollectionServices.class, ComparableServices.class, EObjectServices.class,
			NumberServices.class, PropertiesServices.class, ResourceServices.class, StringServices.class,
			XPathServices.class, };

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

		System.out.println("Prepare the generation of the documentation for " + pluginFolder
				.getAbsolutePath());

		File inputFolder = new File(pluginFolder, "input"); //$NON-NLS-1$
		File documentationFolder = new File(pluginFolder, "pages"); //$NON-NLS-1$
		File indexHtmlFile = new File(documentationFolder, "index.html"); //$NON-NLS-1$

		// toc.xml
		StringBuffer buffer = AQLHelpContentUtils.computeToc(STANDARD_SERVICE_PROVIDERS);
		try {
			File tocFile = new File(pluginFolder, "toc.xml");
			System.out.println("Writing the content of toc.xml in " + tocFile.getAbsolutePath());
			write(buffer.toString(), tocFile, Charset.forName(UTF8));
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		// Services
		StringBuffer aggregated = new StringBuffer();
		for (Class<?> serviceProviderClass : STANDARD_SERVICE_PROVIDERS) {
			if (serviceProviderClass.isAnnotationPresent(ServiceProvider.class)) {
				try {
					List<StringBuffer> sections = AQLHelpContentUtils.computeServiceSections(
							serviceProviderClass);
					StringBuffer stringBuffer = html(head(), body(header(false), sections));

					File file = new File(documentationFolder, AQLHelpContentUtils.AQL_HREF_PREFIX
							+ serviceProviderClass.getSimpleName().toLowerCase() + ".html");
					System.out.println("Writing content of " + file.getAbsolutePath());
					write(stringBuffer.toString(), file, Charset.forName(UTF8));

					/*
					 * generating a documentation aggregating all the services at once.
					 */
					List<StringBuffer> sectionsForAggregatedServices = AQLHelpContentUtils
							.computeServiceSections(serviceProviderClass, 3,
									AQLHelpContentUtils.METHOD_SIGNATURE_GENERATOR_2016);
					for (StringBuffer b : sectionsForAggregatedServices) {
						aggregated.append(b);
					}

				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}

		// index.html
		try {
			List<StringBuffer> sections = AQLHelpContentUtils.computeAQLOverviewSections();

			String inputHtmlContent = read(new File(inputFolder, "index.html"), Charset.forName(UTF8));
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
			String out = stringBuffer.toString().replace(TRIGGER_TO_APPEND_STD_DOC, TRIGGER_TO_APPEND_STD_DOC
					+ "\n" + aggregated);

			System.out.println("Writing content of " + indexHtmlFile.getAbsolutePath());
			write(out, indexHtmlFile, Charset.forName(UTF8));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private static void write(String content, File file, Charset charset) throws IOException {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), charset)) {
			writer.write(content);
		}
	}

	private static String read(File file, Charset charset) throws IOException {
		return new String(Files.readAllBytes(file.toPath()), charset);
	}
}
