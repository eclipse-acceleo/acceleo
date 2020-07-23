package org.eclipse.acceleo.aql.migration.tests.acceleo3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GenerateServicesDoc {

	private static final String UNSUPPORTED_TITLE = "Unsupported in A4:\n==================\n";
	private static final String DIFFERENCES_TITLE = "Differences when evaluated in A4:\n=================================\n";
	private static final String TODO_TITLE = "Tests to write:\n================\n";

	private static final File TODO_FILE = new File("status/TODO");
	private static final File TODO_LOT1_FILE = new File("status/TODO_lot1");
	private static final File SUPPORTED_FILE = new File("status/PASS");
	private static final File IRRELEVANT_FILE = new File("status/IRRELEVANT");
	private static final File A3_ROOT_FOLDER = new File("src/resources/services");

	public static void main(String[] args) throws IOException {
		write(SUPPORTED_FILE, parse(mtlFile -> getTests(mtlFile)));
		write(IRRELEVANT_FILE, parse(mtlFile -> getComments(mtlFile, "IRRELEVANT-", false)));
		writeTODOFile(TODO_FILE, false);
		writeTODOFile(TODO_LOT1_FILE, true);
	}

	private static void writeTODOFile(File file, boolean lot1Only) throws IOException {
		write(file, TODO_TITLE, parse(mtlFile -> getComments(mtlFile, "TODO", lot1Only)), "\n", DIFFERENCES_TITLE,
				parse(mtlFile -> getComments(mtlFile, "A4-DIFFERS", lot1Only)), "\n", UNSUPPORTED_TITLE,
				parse(mtlFile -> getComments(mtlFile, "A4-UNSUPPORTED", lot1Only)));
	}

	private static void write(File file, String... content) throws IOException {
		String all = "";
		for (String string : content) {
			all += string;
		}
		if (!all.isEmpty()) {
			Files.write(file.toPath(), all.getBytes());
		} else {
			file.delete();
		}
	}

	private static String parse(Function<Path, List<String>> linesGetter) throws IOException {
		StringBuilder builder = new StringBuilder();
		Files.walk(A3_ROOT_FOLDER.toPath()).filter(t -> t.getFileName().toString().endsWith(".mtl"))
				.forEach(mtlFile -> {
					List<String> tests = linesGetter.apply(mtlFile);
					if (!tests.isEmpty()) {
						builder.append(mtlFile.getParent().getFileName().toString() + '\n');
						for (String test : tests) {
							builder.append('\t' + test + '\n');
						}
					}
				});
		return builder.toString();
	}

	private static List<String> getTests(Path mtlFile) {
		List<String> res = new ArrayList<>();
		try {
			for (String line : Files.readAllLines(mtlFile)) {
				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("@Test")) {
					res.add(trimmedLine.substring(line.indexOf('[') + 1, line.length() - 2));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return res;
	}

	private static List<String> getComments(Path mtlFile, String commentPrefix, boolean lot1Only) {
		List<String> res = new ArrayList<>();
		try {
			for (String line : Files.readAllLines(mtlFile)) {
				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("[comment]" + commentPrefix)) {
					trimmedLine = trimmedLine.replaceAll("\\[comment\\]" + commentPrefix, "");
					trimmedLine = trimmedLine.replaceAll("\\[/comment\\]", "");
					if (!lot1Only) {
						res.add(trimmedLine);
					} else if (trimmedLine.contains("(LOT1)")) {
						res.add(trimmedLine.replaceAll("\\(LOT1\\)\\S*", ""));
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Collections.sort(res);
		return res;
	}
}
