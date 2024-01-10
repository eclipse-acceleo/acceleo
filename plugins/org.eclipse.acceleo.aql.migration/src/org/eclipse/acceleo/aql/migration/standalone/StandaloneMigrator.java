/*******************************************************************************
 * Copyright (c) 2017, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.standalone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

import org.eclipse.acceleo.aql.migration.MigrationException;
import org.eclipse.acceleo.aql.migration.ModuleMigrator;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.aql.parser.AcceleoParser;

/**
 * A standalone launcher.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class StandaloneMigrator {

	/**
	 * The folder where emtl files are.
	 */
	private static final String BIN_FOLDER_NAME = "bin";

	/**
	 * The mtl file extension.
	 */
	private static final String MTL_FILE_EXTENSION = "." + AcceleoParser.MODULE_FILE_EXTENSION;

	/**
	 * The emtl file extension.
	 */
	private static final String EMTL_FILE_EXTENSION = ".emtl";

	/**
	 * The command line usage.
	 */
	private static final String USAGE = "arguments: <acceleo3_source_folder> <acceleo4_target>. To keep module documentation, put the acceleo3 mtl file aside the .emtl file";

	/**
	 * The Acceleo 3 source path.
	 */
	private Path sourceFolderPath;

	/**
	 * The Acceleo 4 target path.
	 */
	private Path targetFolderPath;

	/**
	 * The Acceleo 3 bin path.
	 */
	private Path binFolderPath;

	/**
	 * The log {@link Writer}.
	 */
	private final Writer logWriter;

	/**
	 * Creates a launcher using the given source & target paths.
	 * 
	 * @param logWriter
	 *            the log {@link Writer}
	 * @param sourceFolderPath
	 *            the source path
	 * @param binFolderPath
	 *            the binary folder path
	 * @param targetFolderPath
	 *            the target path
	 */
	public StandaloneMigrator(Writer logWriter, Path sourceFolderPath, Path binFolderPath,
			Path targetFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
		this.targetFolderPath = targetFolderPath;
		this.logWriter = logWriter;
		if (binFolderPath == null) {
			this.binFolderPath = sourceFolderPath.getParent().resolve(BIN_FOLDER_NAME);
		} else {
			this.binFolderPath = binFolderPath;
		}
	}

	/**
	 * Migrates Acceleo 3 content to Acceleo 4.
	 * 
	 * @throws IOException
	 */
	public void migrateAll() throws IOException {
		Iterator<Path> iterator = Files.walk(sourceFolderPath).filter(p -> !p.getFileName().toString()
				.endsWith(MTL_FILE_EXTENSION)).iterator();
		while (iterator.hasNext()) {
			final Path javaPath = iterator.next();
			final Path relativeJavaPath = sourceFolderPath.relativize(javaPath.toAbsolutePath());
			final Path javaTargetPath = targetFolderPath.resolve(relativeJavaPath);

			final File javaFile = javaPath.toFile();
			final File javaTargetFile = javaTargetPath.toFile();
			if (javaFile.exists() && !javaFile.isDirectory()) {
				javaTargetFile.getParentFile().mkdirs();
				Files.copy(javaFile.toPath(), javaTargetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Copied " + javaFile.getAbsolutePath());
				logWriter.write("Copied " + javaFile.getAbsolutePath() + "\n");
			}
		}

		iterator = Files.walk(sourceFolderPath).filter(p -> p.getFileName().toString().endsWith(
				MTL_FILE_EXTENSION)).iterator();
		while (iterator.hasNext()) {
			Path mtlFile = (Path)iterator.next();
			migrate(mtlFile);
		}
		logWriter.flush();
	}

	/**
	 * Migrates a single mtl file.
	 * 
	 * @param mtlFile
	 *            the mtl file
	 * @param logWriter
	 *            the log {@link Writer}
	 * @throws IOException
	 */
	public void migrate(Path mtlFile) throws IOException {
		String relativePath = sourceFolderPath.relativize(mtlFile).toString();
		File emtlFile = binFolderPath.resolve(relativePath.replaceAll(MTL_FILE_EXTENSION,
				EMTL_FILE_EXTENSION)).toFile();
		if (emtlFile.exists()) {
			try {
				// migrate AST
				org.eclipse.acceleo.Module module = new ModuleMigrator(new StandaloneModuleResolver(
						binFolderPath), targetFolderPath).migrate(emtlFile, mtlFile.toFile());

				// serialize
				String a4Content = new AcceleoAstSerializer(System.lineSeparator()).serialize(module);

				// write result
				Path targetMtlFile = targetFolderPath.resolve(relativePath).getParent().resolve(module
						.getName() + "." + AcceleoParser.MODULE_FILE_EXTENSION);
				Files.deleteIfExists(targetMtlFile);
				Files.createDirectories(targetMtlFile.getParent());
				targetMtlFile.toFile().createNewFile();
				Files.write(targetMtlFile, a4Content.getBytes());
				System.out.println("Migrated " + mtlFile);
				logWriter.write("Migrated " + mtlFile + "\n");
			} catch (MigrationException e) {
				System.err.println("Error migrating " + mtlFile + ": " + e.getMessage());
				logWriter.write("Error migrating " + mtlFile + ": " + e.getMessage() + "\n");
			}
		} else {
			System.err.println("Error EMTL file not found: " + emtlFile);
			logWriter.write("Error EMTL file not found: " + emtlFile + "\n");
		}
	}

	/**
	 * Converts a folder of acceleo3 mtl files to its acceleo4 equivalent.
	 * 
	 * @param args
	 *            the source & target folders.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println(USAGE);
		} else {
			Path sourceFolderPath = Paths.get(args[0]);
			Path targetFolderPath = Paths.get(args[1]);

			final File logFile = targetFolderPath.resolve("Acceleo4migation.log").toFile();
			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			try (FileWriter logWriter = new FileWriter(logFile)) {
				new StandaloneMigrator(logWriter, sourceFolderPath, null, targetFolderPath).migrateAll();
			}
		}
	}

}
