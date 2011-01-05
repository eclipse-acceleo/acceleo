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
package org.eclipse.acceleo.internal.compatibility.mtl.gen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Entry point of the 'Mt2mtl' generation module.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @generated
 */
public class Mt2mtl extends AbstractAcceleoGenerator {

	/**
	 * The name of the module.
	 * 
	 * @generated
	 */
	public static final String MODULE_FILE_NAME = "mt2mtl";

	/**
	 * The name of the templates that are to be generated.
	 * 
	 * @generated
	 */
	public static final String[] TEMPLATE_NAMES = {"convertToModule", };

	/**
	 * Allows the public constructor to be used. Note that a generator created this way cannot be used to
	 * launch generations before one of {@link #initialize(EObject, File, List)} or
	 * {@link #initialize(URI, File, List)} is called.
	 * <p>
	 * The main reason for this constructor is to allow clients of this generation to call it from another
	 * Java file, as it allows for the retrieval of {@link #getProperties()} and
	 * {@link #getGenerationListeners()}.
	 * </p>
	 * 
	 * @generated
	 */
	public Mt2mtl() {
		// Empty implementation
	}

	/**
	 * Constructor.
	 * 
	 * @param modelURI
	 *            is the URI of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public Mt2mtl(URI modelURI, File targetFolder, List<? extends Object> arguments) throws IOException {
		initialize(modelURI, targetFolder, arguments);
	}

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            is the root element of the model.
	 * @param targetFolder
	 *            is the output folder
	 * @param arguments
	 *            are the other arguments
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	public Mt2mtl(EObject model, File targetFolder, List<? extends Object> arguments) throws IOException {
		initialize(model, targetFolder, arguments);
	}

	/**
	 * Updates the registry used for looking up a package based namespace, in the resource set.
	 * 
	 * @param resourceSet
	 *            is the resource set
	 * @generated
	 */
	@Override
	public void registerPackages(ResourceSet resourceSet) {
		super.registerPackages(resourceSet);
		resourceSet.getPackageRegistry().put(
				org.eclipse.acceleo.compatibility.model.mt.MtPackage.eINSTANCE.getNsURI(),
				org.eclipse.acceleo.compatibility.model.mt.MtPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				org.eclipse.acceleo.compatibility.model.mt.core.CorePackage.eINSTANCE.getNsURI(),
				org.eclipse.acceleo.compatibility.model.mt.core.CorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage.eINSTANCE
						.getNsURI(),
				org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage.eINSTANCE.getNsURI(),
				org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage.eINSTANCE);
		// TODO If you need additional package registrations, do them here. The following line is an example
		// for UML.
		// resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
	}

	/**
	 * Updates the registry used for looking up resources factory in the given resource set.
	 * 
	 * @param resourceSet
	 *            The resource set that is to be updated.
	 * @generated
	 */
	@Override
	public void registerResourceFactories(ResourceSet resourceSet) {
		super.registerResourceFactories(resourceSet);
		// TODO If you need additional resource factories registrations, do them here. The following line is
		// an example for UML.
		// resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION,
		// UMLResource.Factory.INSTANCE);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            are the arguments
	 * @generated
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				System.out.println("Arguments not valid : {model, folder}.");
			} else {
				URI modelURI = URI.createFileURI(args[0]);
				File folder = new File(args[1]);
				List<String> arguments = new ArrayList<String>();
				for (int i = 2; i < args.length; i++) {
					arguments.add(args[i]);
				}
				Mt2mtl generator = new Mt2mtl(modelURI, folder, arguments);
				generator.doGenerate(new BasicMonitor());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launches the generation.
	 * 
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 * @generated
	 */
	@Override
	public void doGenerate(Monitor monitor) throws IOException {
		/*
		 * TODO if you wish to change the generation as a whole, override this. The default behavior should be
		 * sufficient in most cases.
		 */
		super.doGenerate(monitor);
	}

	/**
	 * If this generator needs to listen to text generation events, listeners can be returned from here.
	 * 
	 * @return List of listeners that are to be notified when text is generated through this launch.
	 * @generated
	 */
	@Override
	public List<IAcceleoTextGenerationListener> getGenerationListeners() {
		List<IAcceleoTextGenerationListener> listeners = super.getGenerationListeners();
		// TODO if you need to listen to generation event, add listeners to the list here
		return listeners;
	}

	/**
	 * If you need to change the way files are generated, this is your entry point.
	 * <p>
	 * The default is {@link org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy}; it generates
	 * files on the fly. If you only need to preview the results, return a new
	 * {@link org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy}. Both of these aren't aware of
	 * the running Eclipse and can be used standalone.
	 * </p>
	 * <p>
	 * If you need the file generation to be aware of the workspace (A typical example is when you wanna
	 * override files that are under clear case or any other VCS that could forbid the overriding), then
	 * return a new {@link org.eclipse.acceleo.engine.generation.strategy.WorkspaceAwareStrategy}.
	 * <b>Note</b>, however, that this <b>cannot</b> be used standalone.
	 * </p>
	 * <p>
	 * All three of these default strategies support merging through JMerge.
	 * </p>
	 * 
	 * @generated
	 */
	@Override
	public IAcceleoGenerationStrategy getGenerationStrategy() {
		return super.getGenerationStrategy();
	}

	/**
	 * This will be called in order to find and load the module that will be launched through this launcher.
	 * We expect this name not to contain file extension, and the module to be located beside the launcher.
	 * 
	 * @return The name of the module that is to be launched.
	 * @generated
	 */
	@Override
	public String getModuleName() {
		return MODULE_FILE_NAME;
	}

	/**
	 * If the module(s) called by this launcher require properties files, return their qualified path from
	 * here.Take note that the first added properties files will take precedence over subsequent ones if they
	 * contain conflicting keys.
	 * <p>
	 * Properties need to be in source folders, the path that we expect to get as a result of this call are of
	 * the form &lt;package>.&lt;properties file name without extension>. For example, if you have a file
	 * named "messages.properties" in package "org.eclipse.acceleo.sample", the path that needs be returned by
	 * a call to {@link #getProperties()} is "org.eclipse.acceleo.sample.messages".
	 * </p>
	 * 
	 * @return The list of properties file we need to add to the generation context.
	 * @see java.util.ResourceBundle#getBundle(String)
	 * @generated
	 */
	@Override
	public List<String> getProperties() {
		List<String> propertiesFiles = super.getProperties();
		/*
		 * TODO if your generation module requires access to properties files, add their qualified path to the
		 * list here. Properties files are expected to be in source folders, and the path here to be the
		 * qualified path as if referring to a Java class. For example, if you have a file named
		 * "messages.properties" in package "org.eclipse.acceleo.sample", the path that needs be added to this
		 * list is "org.eclipse.acceleo.sample.messages".
		 */
		return propertiesFiles;
	}

	/**
	 * This will be used to get the list of templates that are to be launched by this launcher.
	 * 
	 * @return The list of templates to call on the module {@link #getModuleName()}.
	 * @generated
	 */
	@Override
	public String[] getTemplateNames() {
		return TEMPLATE_NAMES;
	}

}
