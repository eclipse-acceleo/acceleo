/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.standalone.tests.compiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.internal.parser.compiler.AcceleoParser;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoParserUtils;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProject;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProjectClasspathEntry;
import org.eclipse.acceleo.internal.parser.compiler.IParserListener;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class AcceleoParserTests {

	@Test
	public void testBuildFromMainFirstProject() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.first");
		File output = new File(projectRoot, "bin");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src");
		File outputDirectory = new File(projectRoot, "bin");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		File main = new File(projectRoot, "src/org/eclipse/acceleo/project/first/main/main.mtl");
		File genClass = new File(projectRoot, "src/org/eclipse/acceleo/project/first/file/genClass.mtl");
		File genInterface = new File(projectRoot,
				"src/org/eclipse/acceleo/project/first/file/genInterface.mtl");
		File genClassifier = new File(projectRoot,
				"src/org/eclipse/acceleo/project/first/file/genClassifier.mtl");
		File services = new File(projectRoot, "src/org/eclipse/acceleo/project/first/common/services.mtl");

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildFile(main, new BasicMonitor());

		File builtMain = new File(projectRoot, "bin/org/eclipse/acceleo/project/first/main/main.emtl");
		assertTrue(builtMain.exists());

		File builtGenInterface = new File(projectRoot,
				"bin/org/eclipse/acceleo/project/first/file/genInterface.emtl");
		assertTrue(builtGenInterface.exists());

		File builtGenClassifier = new File(projectRoot,
				"bin/org/eclipse/acceleo/project/first/file/genClassifier.emtl");
		assertTrue(builtGenClassifier.exists());

		File builtGenClass = new File(projectRoot, "bin/org/eclipse/acceleo/project/first/file/genClass.emtl");
		assertTrue(builtGenClass.exists());

		File builtServices = new File(projectRoot,
				"bin/org/eclipse/acceleo/project/first/common/services.emtl");
		assertTrue(builtServices.exists());

		assertThat(parser.getProblems(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(main).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClass).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClassifier).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genInterface).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(services).toString(), is(Collections.emptyList().toString()));
	}

	@Test
	public void testBuildFromMainSecondProject() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.second");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		File main = new File(projectRoot, "src/org/eclipse/acceleo/project/second/main/main.mtl");
		File genClass = new File(projectRoot, "src/org/eclipse/acceleo/project/second/file/genClass.mtl");
		File genInterface = new File(projectRoot,
				"src/org/eclipse/acceleo/project/second/file/genInterface.mtl");
		File genClassifier = new File(projectRoot,
				"src/org/eclipse/acceleo/project/second/file/genClassifier.mtl");
		File services = new File(projectRoot, "src/org/eclipse/acceleo/project/second/common/services.mtl");

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildFile(main, new BasicMonitor());

		File builtMain = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/second/main/main.emtl");
		assertTrue(builtMain.exists());

		File builtGenInterface = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/second/file/genInterface.emtl");
		assertTrue(builtGenInterface.exists());

		File builtGenClassifier = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/second/file/genClassifier.emtl");
		assertTrue(builtGenClassifier.exists());

		File builtGenClass = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/second/file/genClass.emtl");
		assertTrue(builtGenClass.exists());

		File builtServices = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/second/common/services.emtl");
		assertTrue(builtServices.exists());

		assertThat(parser.getProblems(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(main).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClass).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClassifier).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genInterface).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(services).toString(), is(Collections.emptyList().toString()));
	}

	@Test
	public void testBuildFromMainThirdProject() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.third");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		File main = new File(projectRoot, "src/main/java/org/eclipse/acceleo/project/third/main/main.mtl");
		File genClass = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/third/file/genClass.mtl");
		File genInterface = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/third/file/genInterface.mtl");
		File genClassifier = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/third/file/genClassifier.mtl");
		File services = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/third/common/services.mtl");

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildFile(main, new BasicMonitor());

		File builtMain = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/third/main/main.emtl");
		assertTrue(builtMain.exists());

		File builtGenInterface = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/third/file/genInterface.emtl");
		assertTrue(builtGenInterface.exists());

		File builtGenClassifier = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/third/file/genClassifier.emtl");
		assertTrue(builtGenClassifier.exists());

		File builtGenClass = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/third/file/genClass.emtl");
		assertTrue(builtGenClass.exists());

		File builtServices = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/third/common/services.emtl");
		assertTrue(builtServices.exists());

		assertThat(parser.getProblems(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(main).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClass).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClassifier).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genInterface).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(services).toString(), is(Collections.emptyList().toString()));
	}

	@Test
	public void testBuildFromMainFourthProject() {
		String curDir = System.getProperty("user.dir");
		File gitRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fourth");

		File javaProjectRoot = new File(gitRoot, "plugin/org.eclipse.acceleo.project.fourth.java");
		File output = new File(javaProjectRoot, "bin");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(javaProjectRoot, "src/");
		File outputDirectory = new File(javaProjectRoot, "bin");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(javaProjectRoot, entries);

		File main = new File(javaProjectRoot, "src/org/eclipse/acceleo/project/fourth/java/main/main.mtl");
		File genClass = new File(javaProjectRoot,
				"src/org/eclipse/acceleo/project/fourth/java/file/genClass.mtl");
		File genInterface = new File(javaProjectRoot,
				"src/org/eclipse/acceleo/project/fourth/java/file/genInterface.mtl");
		File genClassifier = new File(javaProjectRoot,
				"src/org/eclipse/acceleo/project/fourth/java/file/genClassifier.mtl");
		File services = new File(javaProjectRoot,
				"src/org/eclipse/acceleo/project/fourth/java/common/services.mtl");

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildFile(main, new BasicMonitor());

		File builtMain = new File(javaProjectRoot,
				"bin/org/eclipse/acceleo/project/fourth/java/main/main.emtl");
		assertTrue(builtMain.exists());

		File builtGenInterface = new File(javaProjectRoot,
				"bin/org/eclipse/acceleo/project/fourth/java/file/genInterface.emtl");
		assertTrue(builtGenInterface.exists());

		File builtGenClassifier = new File(javaProjectRoot,
				"bin/org/eclipse/acceleo/project/fourth/java/file/genClassifier.emtl");
		assertTrue(builtGenClassifier.exists());

		File builtGenClass = new File(javaProjectRoot,
				"bin/org/eclipse/acceleo/project/fourth/java/file/genClass.emtl");
		assertTrue(builtGenClass.exists());

		File builtServices = new File(javaProjectRoot,
				"bin/org/eclipse/acceleo/project/fourth/java/common/services.emtl");
		assertTrue(builtServices.exists());

		assertThat(parser.getProblems(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(main).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClass).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClassifier).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genInterface).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(services).toString(), is(Collections.emptyList().toString()));

		File mavenProjectRoot = new File(gitRoot, "plugin/org.eclipse.acceleo.project.fourth.maven");
		output = new File(mavenProjectRoot, "target/classes");
		listFiles = output.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		inputDirectory = new File(mavenProjectRoot, "src/main/java");
		outputDirectory = new File(mavenProjectRoot, "target/classes");
		entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		project = new AcceleoProject(mavenProjectRoot, entries);

		main = new File(mavenProjectRoot,
				"src/main/java/org/eclipse/acceleo/project/fourth/maven/main/main.mtl");
		genClass = new File(mavenProjectRoot,
				"src/main/java/org/eclipse/acceleo/project/fourth/maven/file/genClass.mtl");
		genInterface = new File(mavenProjectRoot,
				"src/main/java/org/eclipse/acceleo/project/fourth/maven/file/genInterface.mtl");
		genClassifier = new File(mavenProjectRoot,
				"src/main/java/org/eclipse/acceleo/project/fourth/maven/file/genClassifier.mtl");
		services = new File(mavenProjectRoot,
				"src/main/java/org/eclipse/acceleo/project/fourth/maven/common/services.mtl");

		parser = new AcceleoParser(project, false, true);
		parser.buildFile(main, new BasicMonitor());

		builtMain = new File(mavenProjectRoot,
				"target/classes/org/eclipse/acceleo/project/fourth/maven/main/main.emtl");
		assertTrue(builtMain.exists());

		builtGenInterface = new File(mavenProjectRoot,
				"target/classes/org/eclipse/acceleo/project/fourth/maven/file/genInterface.emtl");
		assertTrue(builtGenInterface.exists());

		builtGenClassifier = new File(mavenProjectRoot,
				"target/classes/org/eclipse/acceleo/project/fourth/maven/file/genClassifier.emtl");
		assertTrue(builtGenClassifier.exists());

		builtGenClass = new File(mavenProjectRoot,
				"target/classes/org/eclipse/acceleo/project/fourth/maven/file/genClass.emtl");
		assertTrue(builtGenClass.exists());

		builtServices = new File(mavenProjectRoot,
				"target/classes/org/eclipse/acceleo/project/fourth/maven/common/services.emtl");
		assertTrue(builtServices.exists());

		assertThat(parser.getProblems(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(main).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(main).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClass).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClass).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClassifier).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClassifier).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genInterface).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genInterface).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertThat(parser.getProblems(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(services).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(services).toString(), is(Collections.emptyList().toString()));
	}

	@Test
	public void testBuildFromMainFifthProject() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		File main = new File(projectRoot, "src/main/java/org/eclipse/acceleo/project/fifth/main/workflow.mtl");

		ParserListener parserListener = new ParserListener();
		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildFile(main, new BasicMonitor());

		File attributes = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/attributes.emtl");
		File behavior = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/behavior.emtl");
		File common = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/common.emtl");
		File declaration = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/declaration.emtl");
		File imports = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/imports.emtl");
		File operations = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/operations.emtl");
		File properties = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/properties.emtl");
		File type = new File(projectRoot, "target/classes/org/eclipse/acceleo/project/fifth/common/type.emtl");

		File classFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/classFile.emtl");
		File commonFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/commonFile.emtl");
		File enumerationFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/enumerationFile.emtl");
		File interfaceFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/interfaceFile.emtl");

		File workflow = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/main/workflow.emtl");

		File request = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/request/request.emtl");

		File commonServices = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/services/commonServices.emtl");

		File validator = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/validator/validator.emtl");

		assertThat(project.getAllCompiledAcceleoModules(), hasItems(attributes, behavior, common,
				declaration, imports, operations, properties, type, classFile, commonFile, enumerationFile,
				interfaceFile, workflow, request, commonServices, validator));

		File attributesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/attributes.mtl");
		File behaviorMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/behavior.mtl");
		File commonMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/common.mtl");
		File declarationMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/declaration.mtl");
		File importsMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/imports.mtl");
		File operationsMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/operations.mtl");
		File propertiesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/properties.mtl");
		File typeMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/type.mtl");

		File classFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/classFile.mtl");
		File commonFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/commonFile.mtl");
		File enumerationFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/enumerationFile.mtl");
		File interfaceFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/interfaceFile.mtl");

		File workflowMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/main/workflow.mtl");

		File requestMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/request/request.mtl");

		File commonServicesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/services/commonServices.mtl");

		File validatorMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/validator/validator.mtl");

		List<File> filesBuilt = parserListener.getFilesBuilt();
		String filesBuiltStr = "";
		Iterator<File> iterator = filesBuilt.iterator();
		while (iterator.hasNext()) {
			File file = iterator.next();
			filesBuiltStr += file.getName();
			if (iterator.hasNext()) {
				filesBuiltStr += ", ";
			}
		}

		List<File> expectedFilesBuilt = Lists.newArrayList(workflowMTL, classFileMTL, attributesMTL,
				commonMTL, commonServicesMTL, declarationMTL, commonMTL, attributesMTL, declarationMTL,
				typeMTL, propertiesMTL, importsMTL, typeMTL, attributesMTL, declarationMTL, requestMTL,
				importsMTL, classFileMTL, attributesMTL, declarationMTL, behaviorMTL, operationsMTL,
				classFileMTL, attributesMTL, interfaceFileMTL, commonFileMTL, classFileMTL, workflowMTL,
				enumerationFileMTL, interfaceFileMTL, validatorMTL);

		assertThat("Expected: " + filesBuiltStr, filesBuilt, equalTo(expectedFilesBuilt));

		List<File> filesSaved = parserListener.getFileSaved();
		String filesSavedStr = "";
		iterator = filesSaved.iterator();
		while (iterator.hasNext()) {
			File file = iterator.next();
			filesSavedStr += file.getName();
			if (iterator.hasNext()) {
				filesSavedStr += ", ";
			}
		}

		List<File> expectedFilesSaved = Lists.newArrayList(commonServicesMTL, commonMTL, propertiesMTL,
				typeMTL, requestMTL, importsMTL, declarationMTL, behaviorMTL, operationsMTL, attributesMTL,
				commonFileMTL, classFileMTL, enumerationFileMTL, interfaceFileMTL, workflowMTL, validatorMTL);
		assertThat("Expected: " + filesSavedStr, filesSaved, equalTo(expectedFilesSaved));

		for (File file : filesSaved) {
			assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(), is(Collections
					.emptyList().toString()));
		}
	}

	@Test
	public void testBuildAllFifthProject() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		ParserListener parserListener = new ParserListener();
		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildAll(new BasicMonitor());

		File attributes = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/attributes.emtl");
		File behavior = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/behavior.emtl");
		File common = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/common.emtl");
		File declaration = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/declaration.emtl");
		File imports = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/imports.emtl");
		File operations = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/operations.emtl");
		File properties = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/common/properties.emtl");
		File type = new File(projectRoot, "target/classes/org/eclipse/acceleo/project/fifth/common/type.emtl");

		File classFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/classFile.emtl");
		File commonFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/commonFile.emtl");
		File enumerationFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/enumerationFile.emtl");
		File interfaceFile = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/files/interfaceFile.emtl");

		File workflow = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/main/workflow.emtl");

		File request = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/request/request.emtl");

		File commonServices = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/services/commonServices.emtl");
		File logger = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/services/logger.emtl");

		File validator = new File(projectRoot,
				"target/classes/org/eclipse/acceleo/project/fifth/validator/validator.emtl");

		assertThat(project.getAllCompiledAcceleoModules(), hasItems(attributes, behavior, common,
				declaration, imports, operations, properties, type, classFile, commonFile, enumerationFile,
				interfaceFile, workflow, request, commonServices, logger, validator));

		File attributesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/attributes.mtl");
		File behaviorMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/behavior.mtl");
		File commonMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/common.mtl");
		File declarationMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/declaration.mtl");
		File importsMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/imports.mtl");
		File operationsMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/operations.mtl");
		File propertiesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/properties.mtl");
		File typeMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/type.mtl");

		File classFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/classFile.mtl");
		File commonFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/commonFile.mtl");
		File enumerationFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/enumerationFile.mtl");
		File interfaceFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/interfaceFile.mtl");

		File workflowMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/main/workflow.mtl");

		File requestMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/request/request.mtl");

		File commonServicesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/services/commonServices.mtl");
		File loggerMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/services/logger.mtl");

		File validatorMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/validator/validator.mtl");

		List<File> filesBuilt = parserListener.getFilesBuilt();
		String filesBuiltStr = "";
		Iterator<File> iterator = filesBuilt.iterator();
		while (iterator.hasNext()) {
			File file = iterator.next();
			filesBuiltStr += file.getName();
			if (iterator.hasNext()) {
				filesBuiltStr += ", ";
			}
		}

		List<File> expectedFilesBuilt = Lists.newArrayList(attributesMTL, commonMTL, commonServicesMTL,
				declarationMTL, commonMTL, attributesMTL, declarationMTL, typeMTL, propertiesMTL, importsMTL,
				typeMTL, attributesMTL, declarationMTL, requestMTL, importsMTL, classFileMTL, attributesMTL,
				declarationMTL, behaviorMTL, operationsMTL, classFileMTL, attributesMTL, interfaceFileMTL,
				commonFileMTL, classFileMTL, workflowMTL, enumerationFileMTL, interfaceFileMTL, validatorMTL,
				loggerMTL);

		assertThat("Expected: " + filesBuiltStr, filesBuilt, equalTo(expectedFilesBuilt));

		List<File> filesSaved = parserListener.getFileSaved();
		String filesSavedStr = "";
		iterator = filesSaved.iterator();
		while (iterator.hasNext()) {
			File file = iterator.next();
			filesSavedStr += file.getName();
			if (iterator.hasNext()) {
				filesSavedStr += ", ";
			}
		}

		List<File> expectedFilesSaved = Lists.newArrayList(commonServicesMTL, commonMTL, propertiesMTL,
				typeMTL, requestMTL, importsMTL, declarationMTL, behaviorMTL, operationsMTL, attributesMTL,
				commonFileMTL, classFileMTL, enumerationFileMTL, interfaceFileMTL, workflowMTL, validatorMTL,
				loggerMTL);
		assertThat("Expected: " + filesSavedStr, filesSaved, equalTo(expectedFilesSaved));

		for (File file : filesSaved) {
			assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(), is(Collections
					.emptyList().toString()));
		}
	}

	@Test
	public void testBuildFromMainFifthProjectAlreadyBuilt() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildAll(new BasicMonitor());

		// Now let's rebuild the main module
		File main = new File(projectRoot, "src/main/java/org/eclipse/acceleo/project/fifth/main/workflow.mtl");

		ParserListener parserListener = new ParserListener();
		parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildFile(main, new BasicMonitor());

		List<File> filesBuilt = parserListener.getFilesBuilt();
		List<File> expectedFilesBuilt = Lists.newArrayList(main);
		assertThat(filesBuilt, equalTo(expectedFilesBuilt));

		List<File> fileSaved = parserListener.getFileSaved();
		List<File> expectedFilesSaved = Lists.newArrayList(main);
		assertThat(fileSaved, equalTo(expectedFilesSaved));

		for (File file : fileSaved) {
			assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(), is(Collections
					.emptyList().toString()));
		}
	}

	@Test
	public void testBuildFromMainFifthProjectAlreadyBuiltWithPropagation() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildAll(new BasicMonitor());

		// Now let's delete the main module and rebuild it
		File main = new File(projectRoot, "src/main/java/org/eclipse/acceleo/project/fifth/main/workflow.mtl");
		File outputFile = project.getOutputFile(main);
		outputFile.delete();

		ParserListener parserListener = new ParserListener();
		parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildFile(main, new BasicMonitor());

		List<File> filesBuilt = parserListener.getFilesBuilt();
		List<File> expectedFilesBuilt = Lists.newArrayList(main);
		assertThat(filesBuilt, equalTo(expectedFilesBuilt));

		List<File> fileSaved = parserListener.getFileSaved();
		List<File> expectedFilesSaved = Lists.newArrayList(main);
		assertThat(fileSaved, equalTo(expectedFilesSaved));

		for (File file : fileSaved) {
			assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(), is(Collections
					.emptyList().toString()));
		}
	}

	@Test
	public void testBuildFromCommonFifthProjectAlreadyBuilt() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildAll(new BasicMonitor());

		// Now let's rebuild common
		File commonMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/common.mtl");

		ParserListener parserListener = new ParserListener();
		parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildFile(commonMTL, new BasicMonitor());

		List<File> filesBuilt = parserListener.getFilesBuilt();
		List<File> expectedFilesBuilt = Lists.newArrayList(commonMTL);
		assertThat(filesBuilt, equalTo(expectedFilesBuilt));

		List<File> fileSaved = parserListener.getFileSaved();
		List<File> expectedFilesSaved = Lists.newArrayList(commonMTL);
		assertThat(fileSaved, equalTo(expectedFilesSaved));

		for (File file : fileSaved) {
			assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(), is(Collections
					.emptyList().toString()));
		}
	}

	@Test
	public void testBuildFromCommonFifthProjectAlreadyBuiltWithPropagation() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildAll(new BasicMonitor());

		// Now let's rebuild common
		File attributesMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/attributes.mtl");
		File behaviorMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/behavior.mtl");
		File commonMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/common.mtl");
		File declarationMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/declaration.mtl");
		File importsMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/imports.mtl");
		File operationsMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/operations.mtl");
		File typeMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/common/type.mtl");

		File classFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/classFile.mtl");
		File commonFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/commonFile.mtl");
		File enumerationFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/enumerationFile.mtl");
		File interfaceFileMTL = new File(projectRoot,
				"src/main/java/org/eclipse/acceleo/project/fifth/files/interfaceFile.mtl");

		File outputFile = project.getOutputFile(commonMTL);
		outputFile.delete();

		ParserListener parserListener = new ParserListener();
		parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildFile(commonMTL, new BasicMonitor());

		List<File> filesBuilt = parserListener.getFilesBuilt();
		List<File> expectedFilesBuilt = Lists.newArrayList(commonMTL, attributesMTL, behaviorMTL,
				declarationMTL, importsMTL, operationsMTL, typeMTL, classFileMTL, commonFileMTL,
				enumerationFileMTL, interfaceFileMTL);
		assertThat(filesBuilt, equalTo(expectedFilesBuilt));

		List<File> fileSaved = parserListener.getFileSaved();
		List<File> expectedFilesSaved = Lists.newArrayList(commonMTL, attributesMTL, behaviorMTL,
				declarationMTL, importsMTL, operationsMTL, typeMTL, classFileMTL, commonFileMTL,
				enumerationFileMTL, interfaceFileMTL);
		assertThat(fileSaved, equalTo(expectedFilesSaved));

		for (File file : fileSaved) {
			assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
					is(Collections.emptyList().toString()));
			assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(), is(Collections
					.emptyList().toString()));
		}
	}

	@Test
	public void testBuildOneByOneFifthProjectAlreadyBuiltWithPropagation() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.fifth");
		File output = new File(projectRoot, "target/classes");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src/main/java");
		File outputDirectory = new File(projectRoot, "target/classes");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.buildAll(new BasicMonitor());

		Set<File> allAcceleoModules = project.getAllAcceleoModules();
		for (File module : allAcceleoModules) {
			File outputFile = project.getOutputFile(module);
			outputFile.delete();

			ParserListener parserListener = new ParserListener();
			parser = new AcceleoParser(project, false, true);
			parser.addListeners(parserListener);
			parser.buildFile(module, new BasicMonitor());

			List<File> filesBuilt = parserListener.getFilesBuilt();
			List<File> expectedFilesBuilt = new ArrayList<File>();
			expectedFilesBuilt.add(module);
			expectedFilesBuilt.addAll(Lists.newArrayList(project.getFilesDependingOn(module)));

			assertThat(filesBuilt, equalTo(expectedFilesBuilt));
			assertThat(filesBuilt.size(), equalTo(expectedFilesBuilt.size()));

			List<File> fileSaved = parserListener.getFileSaved();
			List<File> expectedFilesSaved = expectedFilesBuilt;
			assertThat(fileSaved, equalTo(expectedFilesSaved));

			for (File file : fileSaved) {
				assertThat("Problems for file: " + file.getName(), parser.getProblems(file).toString(),
						is(Collections.emptyList().toString()));
				assertThat("Warnings for file: " + file.getName(), parser.getWarnings(file).toString(),
						is(Collections.emptyList().toString()));
				assertThat("Infos for file: " + file.getName(), parser.getInfos(file).toString(),
						is(Collections.emptyList().toString()));
			}
		}
	}

	@Test
	public void testBuildFromMainSixthProject() {
		String curDir = System.getProperty("user.dir");
		File projectSevenRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.seventh");
		File output = new File(projectSevenRoot, "bin");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectSevenRoot, "src");
		File outputDirectory = new File(projectSevenRoot, "bin");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject projectSeven = new AcceleoProject(projectSevenRoot, entries);

		File commonSeven = new File(projectSevenRoot,
				"src/org/eclipse/acceleo/project/seventh/common/common.mtl");
		File requestSeven = new File(projectSevenRoot,
				"src/org/eclipse/acceleo/project/seventh/common/request.mtl");
		File servicesSeven = new File(projectSevenRoot,
				"src/org/eclipse/acceleo/project/seventh/common/services.mtl");
		File genTypeSeven = new File(projectSevenRoot,
				"src/org/eclipse/acceleo/project/seventh/file/genType.mtl");
		File genTraitSeven = new File(projectSevenRoot,
				"src/org/eclipse/acceleo/project/seventh/file/genTrait.mtl");

		File projectSixRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.sixth");
		output = new File(projectSixRoot, "target/modules");
		listFiles = output.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		inputDirectory = new File(projectSixRoot, "src/main/acceleo");
		outputDirectory = new File(projectSixRoot, "target/modules");
		entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject projectSix = new AcceleoProject(projectSixRoot, entries);

		projectSeven.addProjectDependencies(Sets.newHashSet(projectSix));

		File mainSix = new File(projectSixRoot,
				"src/main/acceleo/org/eclipse/acceleo/project/sixth/main/main.mtl");
		File genClassSix = new File(projectSixRoot,
				"src/main/acceleo/org/eclipse/acceleo/project/sixth/file/genClass.mtl");
		File genInterfaceSix = new File(projectSixRoot,
				"src/main/acceleo/org/eclipse/acceleo/project/sixth/file/genInterface.mtl");
		File genClassifierSix = new File(projectSixRoot,
				"src/main/acceleo/org/eclipse/acceleo/project/sixth/file/genClassifier.mtl");
		File servicesSix = new File(projectSixRoot,
				"src/main/acceleo/org/eclipse/acceleo/project/sixth/common/services.mtl");

		AcceleoParser parser = new AcceleoParser(projectSix, false, true);
		ParserListener parserListener = new ParserListener();
		parser.addListeners(parserListener);
		parser.buildFile(mainSix, new BasicMonitor());

		assertTrue(projectSix.getOutputFile(mainSix).exists());
		assertTrue(projectSix.getOutputFile(genClassSix).exists());
		assertTrue(projectSix.getOutputFile(genInterfaceSix).exists());
		assertTrue(projectSix.getOutputFile(genClassifierSix).exists());
		assertTrue(projectSix.getOutputFile(servicesSix).exists());

		assertTrue(projectSeven.getOutputFile(commonSeven).exists());
		assertTrue(projectSeven.getOutputFile(requestSeven).exists());
		assertTrue(projectSeven.getOutputFile(servicesSeven).exists());
		assertTrue(projectSeven.getOutputFile(genTypeSeven).exists());
		assertTrue(projectSeven.getOutputFile(genTraitSeven).exists());

		List<File> filesBuilt = parserListener.getFilesBuilt();
		List<File> expectedFilesBuilt = Lists.newArrayList(mainSix, genClassSix, servicesSix, servicesSeven,
				genClassifierSix, genInterfaceSix, mainSix, genClassSix, genTypeSeven, genTraitSeven,
				requestSeven, commonSeven, genTraitSeven, genTypeSeven, genTraitSeven);
		assertThat(filesBuilt, equalTo(expectedFilesBuilt));

		List<File> filesSaved = parserListener.getFileSaved();
		List<File> expectedFilesSaved = Lists.newArrayList(servicesSix, servicesSeven, genClassifierSix,
				genInterfaceSix, genClassSix, mainSix, commonSeven, requestSeven, genTraitSeven,
				genTypeSeven, genTraitSeven);
		assertThat(filesSaved, equalTo(expectedFilesSaved));
	}

	@Test
	public void testBuildFromMainSixthAfterFullBuildProject() {
		String curDir = System.getProperty("user.dir");
		File projectSevenRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.seventh");
		File output = new File(projectSevenRoot, "bin");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectSevenRoot, "src");
		File outputDirectory = new File(projectSevenRoot, "bin");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject projectSeven = new AcceleoProject(projectSevenRoot, entries);

		File projectSixRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.sixth");
		inputDirectory = new File(projectSixRoot, "src/main/acceleo");
		outputDirectory = new File(projectSixRoot, "target/modules");
		entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject projectSix = new AcceleoProject(projectSixRoot, entries);

		projectSeven.addProjectDependencies(Sets.newHashSet(projectSix));

		AcceleoParser parser = new AcceleoParser(projectSeven, false, true);
		ParserListener parserListener = new ParserListener();
		parser.addListeners(parserListener);
		parser.buildAll(new BasicMonitor());

		File genClassifierSix = new File(projectSixRoot,
				"target/modules/org/eclipse/acceleo/project/sixth/file/genClassifier.emtl");
		genClassifierSix.delete();

		File genTraitSeven = new File(projectSevenRoot,
				"src/org/eclipse/acceleo/project/seventh/file/genTrait.mtl");
		parser.buildFile(genTraitSeven, new BasicMonitor());

		assertThat(parser.getProblems(genTraitSeven).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genTraitSeven).toString(), is(Collections.emptyList().toString()));
		assertThat(
				parser.getInfos(genTraitSeven).toString(),
				is("[AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'., AcceleoParser.Info.TemplateOverrideTemplate 'genName' overrides template 'genName'.]"));

		assertTrue(genClassifierSix.exists());
		assertThat(parser.getProblems(genClassifierSix).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(genClassifierSix).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(genClassifierSix).toString(), is(Collections.emptyList().toString()));
	}

	@Test
	public void testBuildAllEigthProject() {
		String curDir = System.getProperty("user.dir");
		File projectRoot = new File(curDir, "data/workspace/org.eclipse.acceleo.project.eigth");
		File output = new File(projectRoot, "bin");
		File[] listFiles = output.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					AcceleoParserUtils.removeDirectory(file);
				} else {
					file.delete();
				}
			}
		}

		File inputDirectory = new File(projectRoot, "src");
		File outputDirectory = new File(projectRoot, "bin");
		AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(inputDirectory, outputDirectory);
		Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();
		entries.add(entry);
		AcceleoProject project = new AcceleoProject(projectRoot, entries);

		ParserListener parserListener = new ParserListener();
		AcceleoParser parser = new AcceleoParser(project, false, true);
		parser.addListeners(parserListener);
		parser.buildAll(new BasicMonitor());

		File first = new File(projectRoot, "src/org/eclipse/acceleo/project/eigth/files/first.mtl");
		File second = new File(projectRoot, "src/org/eclipse/acceleo/project/eigth/files/second.mtl");
		File third = new File(projectRoot, "src/org/eclipse/acceleo/project/eigth/files/third.mtl");

		assertThat(
				parser.getProblems(first).toString(),
				is("[A circular dependency exists between org::eclipse::acceleo::project::eigth::files::first and org::eclipse::acceleo::project::eigth::files::second thus the module org::eclipse::acceleo::project::eigth::files::first will fail to compile correctly., Module org::eclipse::acceleo::project::eigth::files::second not found.]"));
		assertThat(parser.getWarnings(first).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(first).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(second).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(second).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(second).toString(), is(Collections.emptyList().toString()));

		assertThat(parser.getProblems(third).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getWarnings(third).toString(), is(Collections.emptyList().toString()));
		assertThat(parser.getInfos(third).toString(), is(Collections.emptyList().toString()));
	}

	public class ParserListener implements IParserListener {

		private List<File> filesBuilt = new ArrayList<File>();

		private List<File> fileSaved = new ArrayList<File>();

		public void startBuild(File file) {
			this.filesBuilt.add(file);
		}

		public void loadDependency(File file) {
		}

		public void loadDependency(URI uri) {
		}

		public void fileSaved(File file) {
			this.fileSaved.add(file);
		}

		public void endBuild(File file) {
		}

		public List<File> getFileSaved() {
			return fileSaved;
		}

		public List<File> getFilesBuilt() {
			return filesBuilt;
		}
	}
}
