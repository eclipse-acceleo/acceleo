/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.standalone;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.eclipse.acceleo.aql.migration.MigrationException;
import org.eclipse.acceleo.aql.migration.ModuleMigrator;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;

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
	private static final String MTL_FILE_EXTENSION = ".mtl";

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
	 * Creates a launcher using the given source & target paths.
	 * 
	 * @param sourceFolderPath
	 *            the source path
	 * @param targetFolderPath
	 *            the target path
	 */
	public StandaloneMigrator(Path sourceFolderPath, Path targetFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
		this.targetFolderPath = targetFolderPath;
		binFolderPath = sourceFolderPath.getParent().resolve(BIN_FOLDER_NAME);
	}

	/**
	 * Migrates Acceleo 3 content to Acceleo 4.
	 * 
	 * @throws IOException
	 */
	public void migrateAll() throws IOException {
		Iterator<Path> iterator = Files.walk(sourceFolderPath).filter(p -> p.getFileName().toString()
				.endsWith(MTL_FILE_EXTENSION)).iterator();
		while (iterator.hasNext()) {
			Path mtlFile = (Path)iterator.next();
			migrate(mtlFile);
		}
	}

	/**
	 * Migrates a single mtl file.
	 * 
	 * @param mtlFile
	 *            the mtl file
	 * @throws IOException
	 */
	public void migrate(Path mtlFile) throws IOException {
		String relativePath = sourceFolderPath.relativize(mtlFile).toString();
		Path targetMtlFile = targetFolderPath.resolve(relativePath);
		File emtlFile = binFolderPath.resolve(relativePath.replaceAll(MTL_FILE_EXTENSION,
				EMTL_FILE_EXTENSION)).toFile();
		if (emtlFile.exists()) {
			try {
				// migrate AST
				org.eclipse.acceleo.Module module = new ModuleMigrator(new StandaloneModuleResolver(
						binFolderPath)).migrate(emtlFile, mtlFile.toFile());

				// serialize
				String a4Content = new AcceleoAstSerializer().serialize(module);

				// write result
				Files.deleteIfExists(targetMtlFile);
				Files.createDirectories(targetMtlFile.getParent());
				targetMtlFile.toFile().createNewFile();
				Files.write(targetMtlFile, a4Content.getBytes());
				System.out.println("Migrated " + mtlFile);
			} catch (MigrationException e) {
				System.err.println("Error migrating " + mtlFile + ": " + e.getMessage());
			}
		} else {
			System.err.println("EMTL file not found: " + emtlFile);
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
			new StandaloneMigrator(sourceFolderPath, targetFolderPath).migrateAll();
		}
	}

}