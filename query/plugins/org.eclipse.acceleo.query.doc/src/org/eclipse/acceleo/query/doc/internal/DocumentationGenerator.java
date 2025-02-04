/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.doc.internal;

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
import org.eclipse.acceleo.query.services.PromptServices;
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
	 * Textile service section start tag.
	 */
	private static final String SERVICES_START_ASCIIDOC_TAG = "// SERVICES START"; //$NON-NLS-1$

	/**
	 * Textile service section end tag.
	 */
	private static final String SERVICES_END_ASCIIDOC_TAG = "// SERVICES END"; //$NON-NLS-1$

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
			XPathServices.class, PromptServices.class, };

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

		File pagesFolder = new File(pluginFolder, "pages"); //$NON-NLS-1$
		File indexAsciidocFile = new File(pagesFolder, "index.adoc"); //$NON-NLS-1$

		// Services
		StringBuffer aggregated = new StringBuffer();
		for (Class<?> serviceProviderClass : STANDARD_SERVICE_PROVIDERS) {
			if (serviceProviderClass.isAnnotationPresent(ServiceProvider.class)) {
				/*
				 * generating a documentation aggregating all the services at once.
				 */
				List<StringBuffer> sectionsForAggregatedServices = AQLHelpContentUtils.computeServiceSections(
						serviceProviderClass, AQLHelpContentUtils.METHOD_SIGNATURE_GENERATOR_2016);
				for (StringBuffer b : sectionsForAggregatedServices) {
					aggregated.append(b);
				}
			}
		}

		// index.adoc
		try {
			String inputAsciidocContent = read(indexAsciidocFile, Charset.forName(UTF8));

			StringBuffer buffer = new StringBuffer(inputAsciidocContent.length());

			int serviceSectionStartIndex = inputAsciidocContent.indexOf(SERVICES_START_ASCIIDOC_TAG)
					+ SERVICES_START_ASCIIDOC_TAG.length();
			int serviceSectionEndIndex = inputAsciidocContent.indexOf(SERVICES_END_ASCIIDOC_TAG);
			buffer.append(inputAsciidocContent.substring(0, serviceSectionStartIndex));
			buffer.append(AQLHelpContentUtils.LS);
			buffer.append(AQLHelpContentUtils.LS);
			buffer.append(aggregated);
			buffer.append(inputAsciidocContent.substring(serviceSectionEndIndex, inputAsciidocContent
					.length()));
			String out = buffer.toString();

			System.out.println("Writing content of " + indexAsciidocFile.getAbsolutePath());
			write(out, indexAsciidocFile, Charset.forName(UTF8));
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
