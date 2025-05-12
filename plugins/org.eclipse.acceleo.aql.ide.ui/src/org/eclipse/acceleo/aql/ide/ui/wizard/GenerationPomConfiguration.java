/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.wizard;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

/**
 * The generation pom configuration.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class GenerationPomConfiguration {

	/**
	 * the generation pom target folder.
	 */
	private String pomFolder;

	/**
	 * The generator group ID.
	 */
	private String generatorGroupId;

	/**
	 * The generator artifact ID.
	 */
	private String generatorArtifactId;

	/**
	 * The generator version.
	 */
	private String generatorVersion;

	/**
	 * The generation group ID.
	 */
	private String generationGroupId;

	/**
	 * The generation Artifact ID.
	 */
	private String generationArtifactId;

	/**
	 * The generation version.
	 */
	private String generationVersion;

	/**
	 * The generation model path used as input.
	 */
	private String generationModelPath;

	/**
	 * The generation target path used as input.
	 */
	private String generationTargetPath;

	/**
	 * Gets the generation pom folder.
	 * 
	 * @return the generation folder
	 */
	public String getPomFolder() {
		return pomFolder;
	}

	public URI getPomFolderURI() {
		return getPathURI(getPomFolder());
	}

	/**
	 * Sets the generation pom target folder.
	 * 
	 * @param pomFolder
	 *            the generation pom target folder
	 */
	public void setPomFolder(String pomFolder) {
		this.pomFolder = pomFolder;
	}

	/**
	 * Gets the generator group ID.
	 * 
	 * @return the generator group ID
	 */
	public String getGeneratorGroupId() {
		return generatorGroupId;
	}

	/**
	 * Sets the generator group ID.
	 * 
	 * @param generatorGroupId
	 *            the generator group ID
	 */
	public void setGeneratorGroupId(String generatorGroupId) {
		this.generatorGroupId = generatorGroupId;
	}

	/**
	 * Gets the generator artifact ID.
	 * 
	 * @return the generator artifact ID
	 */
	public String getGeneratorArtifactId() {
		return generatorArtifactId;
	}

	/**
	 * Sets the generator artifact ID.
	 * 
	 * @param generatorArtifactId
	 *            the generator artifact ID
	 */
	public void setGeneratorArtifactId(String generatorArtifactId) {
		this.generatorArtifactId = generatorArtifactId;
	}

	/**
	 * Gets the generator version.
	 * 
	 * @return the generator version
	 */
	public String getGeneratorVersion() {
		return generatorVersion;
	}

	/**
	 * Sets the generator version
	 * 
	 * @param generatorVersion
	 *            the generator version
	 */
	public void setGeneratorVersion(String generatorVersion) {
		this.generatorVersion = generatorVersion;
	}

	/**
	 * Gets the generation group ID
	 * 
	 * @return the generation group ID
	 */
	public String getGenerationGroupId() {
		return generationGroupId;
	}

	/**
	 * Sets the generation group ID.
	 * 
	 * @param generationGroupId
	 *            the generation group ID
	 */
	public void setGenerationGroupId(String generationGroupId) {
		this.generationGroupId = generationGroupId;
	}

	/**
	 * Gets the generation artifact ID
	 * 
	 * @return the generation artifact ID
	 */
	public String getGenerationArtifactId() {
		return generationArtifactId;
	}

	/**
	 * Sets the generation artifact ID.
	 * 
	 * @param generationArtifactId
	 *            the generation artifact ID
	 */
	public void setGenerationArtifactId(String generationArtifactId) {
		this.generationArtifactId = generationArtifactId;
	}

	/**
	 * Gets the generation version.
	 * 
	 * @return the generation version
	 */
	public String getGenerationVersion() {
		return generationVersion;
	}

	/**
	 * Sets the generation version.
	 * 
	 * @param generationVersion
	 *            the generation version
	 */
	public void setGenerationVersion(String generationVersion) {
		this.generationVersion = generationVersion;
	}

	/**
	 * Gets the generation model path used as input.
	 * 
	 * @return the generation model path used as input
	 */
	public String getGenerationModelPath() {
		return generationModelPath;
	}

	public URI getGenerationModelPathURI() {
		return getPathURI(getGenerationModelPath());
	}

	/**
	 * Sets the generation model path used as input.
	 * 
	 * @param modelPath
	 *            the generation model path used as input
	 */
	public void setGenerationModelPath(String modelPath) {
		this.generationModelPath = modelPath;
	}

	/**
	 * Gets the generation target path.
	 * 
	 * @return the generation target path
	 */
	public String getGenerationTargetPath() {
		return generationTargetPath;
	}

	public URI getGenerationTargetPathURI() {
		return getPathURI(getGenerationTargetPath());
	}

	private URI getPathURI(String path) {
		final URI res;

		if (path != null && !path.isBlank()) {
			if (path.startsWith("file:")) {
				res = URI.createURI(path);
			} else {
				final Path destinationPath = new Path(path);
				final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IResource destination = root.findMember(destinationPath);
				if (destination == null) {
					try {
						destination = root.getFolder(destinationPath);
						if (destination == null) {
							destination = root.getFile(destinationPath);
						}
					} catch (IllegalArgumentException e) {
						// nothing to do here
					}
				}
				if (destination != null) {
					res = URI.createURI(destination.getLocation().toFile().getAbsoluteFile().toURI()
							.toString());
				} else {
					res = null;
				}
			}
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Sets the generation target path.
	 * 
	 * @param generationTargetPath
	 *            the generation target path
	 */
	public void setGenerationTargetPath(String generationTargetPath) {
		this.generationTargetPath = generationTargetPath;
	}

}
