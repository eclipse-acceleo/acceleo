package org.eclipse.acceleo.aql.migration.tests.acceleo3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FormatTests {

	private static final File A3_ROOT_FOLDER = new File("src/resources/services");

	public static void main(String[] args) throws IOException {
		Files.walk(A3_ROOT_FOLDER.toPath()).filter(t -> t.getFileName().toString().endsWith(".mtl"))
				.forEach(mtlFile -> {
					try {
						formatTests(mtlFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}

	private static void formatTests(Path mtlFile) throws IOException {
		StringBuilder res = new StringBuilder();
		for (String line : Files.readAllLines(mtlFile)) {
			if (line.startsWith("@Test")) {
				line = formatLine(line);
			} else if (line.startsWith("[comment") && line.contains("@Test")) {
				line = formatCommentLine(line);
			}
			res.append(line + '\n');
		}
		Files.write(mtlFile, res.toString().getBytes());
	}

	private static String formatLine(String line) {
		String expr = line.substring(line.indexOf('[') + 1, line.length() - 2);
		if (expr.contains("[")) {
			return line;
		}
		return "@Test " + expr + " => [" + expr + "/]";
	}

	private static String formatCommentLine(String line) {
		int testStart = line.indexOf("@Test");
		int exprStart = line.indexOf("[", testStart);
		return line.substring(0,testStart) + line.substring(exprStart);
	}
}
