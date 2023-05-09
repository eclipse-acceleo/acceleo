/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.tests.acceleo3;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

public class Synchronize {

	private static final File A3_ROOT_FOLDER = new File("src/resources");

	private static final File A3_BIN_ROOT_FOLDER = new File("bin/resources");

	private static final File A4_ROOT_FOLDER = new File(new File(new File("").getAbsolutePath()).getParentFile(),
			"org.eclipse.acceleo.aql.migration.tests/resources");

	public static void main(String[] args) throws IOException {
		Files.walk(A3_ROOT_FOLDER.toPath())
				.filter(t -> Files.isDirectory(t) && new File(t.toFile(), t.getFileName() + ".mtl").exists())
				.map(Path::toFile).forEach(testFolder -> {
					generate(testFolder);
					try {
						synchronize(testFolder);
					} catch (IOException e) {
						System.err.println("Synchronization issue");
						e.printStackTrace();
					}
				});
	}

	protected static void generate(File testFolder) {
		Class<?> generatorClass = getGeneratorClass(testFolder);
		if (generatorClass != null) {
			String modelName = generatorClass.getSimpleName().substring(0, 1).toLowerCase()
					+ generatorClass.getSimpleName().substring(1);
			String modelPath = new File(testFolder, modelName + ".xmi").getAbsolutePath();
			String generatedPath = new File(testFolder, "generated").getAbsolutePath();
			removeDirectory(new File(generatedPath));
			try {
				Method meth = generatorClass.getMethod("main", String[].class);
				meth.invoke(null, (Object) new String[] { modelPath, generatedPath });
			} catch (Exception e) {
				System.err.println("Error when generating " + testFolder.getName());
				e.printStackTrace();
			}
		}
	}

	/*
	 * Prepare test: fetch model, .emtl & generated code versions in Acceleo 3
	 */
	protected static void synchronize(File a3TestFolder) throws IOException {
		String testName = a3TestFolder.getName();
		File a4TestFolder = getA4TestFolder(a3TestFolder);
		if (!a4TestFolder.exists()) {
			a4TestFolder.mkdirs();
		}

		// .mtl
		String a3mtlFileName = testName + ".mtl";
		String a4mtlFileName = testName + "-origin.mtl";
		File a3MTLFile = new File(a3TestFolder, a3mtlFileName);
		File a4MTLFile = new File(a4TestFolder, a4mtlFileName);
		a4MTLFile.delete();
		Files.copy(a3MTLFile.toPath(), a4MTLFile.toPath());

		// .emtl
		String emtlFileName = testName + ".emtl";
		File a3EMTLFile = getEMTLFile(a3TestFolder);
		File a4EMTLFile = new File(a4TestFolder, emtlFileName);
		if (a3EMTLFile.exists()) {
			a4EMTLFile.delete();
			Files.copy(a3EMTLFile.toPath(), a4EMTLFile.toPath());
		} else {
			System.err.println("" + a3EMTLFile + " not found.");
		}

		// model
		String modelFileName = testName + ".xmi";
		File a3XMIFile = new File(a3TestFolder, modelFileName);
		File a4XMIFile = new File(a4TestFolder, modelFileName);
		if (a3XMIFile.exists()) {
			a4XMIFile.delete();
			Files.copy(a3XMIFile.toPath(), a4XMIFile.toPath());
		}

		// generated code
		File a3Generated = new File(a3TestFolder, "generated");
		File a4Generated = new File(a4TestFolder, "generated");
		File a4GeneratedOrigin = new File(a4TestFolder, "generated-origin");
		if (a3Generated.exists()) {
			if (!a4Generated.exists()) {
				a4Generated.mkdir();
			}
			removeDirectory(a4GeneratedOrigin);
			a4GeneratedOrigin.mkdir();
			for (File a3GeneratedFile : a3Generated.listFiles()) {
				final File expectedFile = new File(a4Generated, a3GeneratedFile.getName() + "-expected.txt");
				final File expectedOriginFile = new File(a4GeneratedOrigin,
						a3GeneratedFile.getName() + "-expected.txt");
				if (!expectedFile.exists()) {
					Files.copy(a3GeneratedFile.toPath(), expectedFile.toPath());
				}
				Files.copy(a3GeneratedFile.toPath(), expectedOriginFile.toPath());
			}
		}
	}

	private static File getA4TestFolder(File a3TestFolder) {
		return new File(A4_ROOT_FOLDER, getSimpleTestPath(a3TestFolder));
	}

	private static File getEMTLFile(File a3TestFolder) {
		return new File(new File(A3_BIN_ROOT_FOLDER, getSimpleTestPath(a3TestFolder)),
				a3TestFolder.getName() + ".emtl");
	}

	private static Class<?> getGeneratorClass(File testFolder) {
		Class<?> generatorClass = null;
		String className = testFolder.getName().substring(0, 1).toUpperCase() + testFolder.getName().substring(1);
		String classQualifiedName = "resources." + getSimpleTestPath(testFolder).replaceAll("\\" + File.separator, ".")
				+ '.' + className;
		try {
			generatorClass = Class.forName(classQualifiedName);
		} catch (ClassNotFoundException e) {
			// nothing to execute here
			System.err.println("Not found: class " + classQualifiedName);
		}
		return generatorClass;
	}

	private static String getSimpleTestPath(File testFolder) {
		return A3_ROOT_FOLDER.toPath().relativize(testFolder.toPath()).toString();
	}

	private static void removeDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (File aFile : files) {
					removeDirectory(aFile);
				}
			}
			dir.delete();
		} else {
			dir.delete();
		}
	}

}
