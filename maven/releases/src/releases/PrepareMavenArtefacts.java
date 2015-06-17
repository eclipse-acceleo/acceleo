package releases;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Creates all the folder, copy the jars from an Eclipse installation and create the pom.xml for a release of Acceleo.
 * 
 * @author sbegaudeau
 */
public class PrepareMavenArtefacts {
	/**
	 * Use with the following parameter for example. The folder luna-sr2 with a file artefacts.data need to be created first.
	 * <ul>
	 * <li>"{ECLIPSE_LUNA_SR2_LOCATION}/eclipse/plugins"</li>
	 * <li>"{GIT_CLONE_LOCATION}/org.eclipse.acceleo/maven/releases/luna-sr2"</li>
	 * </ul>
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			String pluginsFolderPath = args[0];
			String destinationFolderPath = args[1];
			
			try {
				List<String> lines = Files.readAllLines(Paths.get(destinationFolderPath + "/artefacts.data"), StandardCharsets.UTF_8);
				
				lines.forEach(line -> {
					try {
						Files.list(Paths.get(pluginsFolderPath))
						     .filter(path -> path.getFileName().toString().startsWith(line + '_'))
						     .forEach(path -> {
						    	 try {
						    		 Path folder = Paths.get(destinationFolderPath).resolve(line);
						    		 if (!folder.toFile().exists()) {
						    			 folder = Files.createDirectory(folder);
						    		 }
						    		 
						    		 Path jarFilePath = folder.resolve(path.getFileName());
						    		 if (!jarFilePath.toFile().exists()) {
						    			 Files.copy(path, jarFilePath);
						    		 }
						    		 
						    		 Path pomXmlPath = folder.resolve("pom.xml");
						    		 if (!pomXmlPath.toFile().exists()) {
						    			 pomXmlPath = Files.createFile(pomXmlPath);
						    		 }
						    		 
						    		 String pomXmlContent = createPomXmlContent(path.getFileName().toString());
						    		 Files.write(pomXmlPath, pomXmlContent.getBytes());
								} catch (Exception e) {
									e.printStackTrace();
								}
						     });
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String createPomXmlContent(String fileName) {
		String name = fileName.toString().substring(0, fileName.length() - ".jar".length());
		StringTokenizer stringTokenizer = new StringTokenizer(name, "_");
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">" + System.lineSeparator());
		stringBuilder.append("  <modelVersion>4.0.0</modelVersion>" + System.lineSeparator());
		stringBuilder.append("" + System.lineSeparator());
		stringBuilder.append("  <groupId>org.eclipse.acceleo</groupId>" + System.lineSeparator());
		stringBuilder.append("  <artifactId>" + stringTokenizer.nextToken() + "</artifactId>" + System.lineSeparator());
		stringBuilder.append("  <version>" + stringTokenizer.nextToken() + "</version>" + System.lineSeparator());
		stringBuilder.append("</project>" + System.lineSeparator());
		return stringBuilder.toString();
	}
}
