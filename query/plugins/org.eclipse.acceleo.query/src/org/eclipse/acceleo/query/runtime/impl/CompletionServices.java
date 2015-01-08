/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * * Implementation of the elementary language completion services like retrieving variable names and service
 * names.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CompletionServices extends ValidationServices {

	/**
	 * Creates a new service instance given a {@link IQueryEnvironment} and logging flag.
	 * 
	 * @param queryEnv
	 *            the {@link IQueryEnvironment} to use
	 * @param doLog
	 *            when <code>true</code> the resulting instance will log error and warning messages.
	 */
	public CompletionServices(IQueryEnvironment queryEnv, boolean doLog) {
		super(queryEnv, doLog);
	}

	/**
	 * Gets the {@link List} of {@link VariableCompletionProposal} for variables.
	 * 
	 * @param variableNames
	 *            the variables names.
	 * @return the {@link List} of {@link VariableCompletionProposal} for variables
	 */
	public List<VariableCompletionProposal> getVariableProposals(List<String> variableNames) {
		final List<VariableCompletionProposal> result = new ArrayList<VariableCompletionProposal>();

		for (String varName : variableNames) {
			result.add(new VariableCompletionProposal(varName));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link ServiceCompletionProposal} for {@link IService}.
	 * 
	 * @param receiverTypes
	 *            the receiver types.
	 * @param keepOperators
	 *            tells if we should keep operator services
	 * @return the {@link List} of {@link ServiceCompletionProposal} for {@link IService}
	 */
	public List<ServiceCompletionProposal> getServiceProposals(Set<IType> receiverTypes, boolean keepOperators) {
		final List<ServiceCompletionProposal> result = new ArrayList<ServiceCompletionProposal>();

		final Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		for (IType type : receiverTypes) {
			classes.add(getClass(type));
		}

		for (IService service : lookupEngine.getServices(classes)) {
			if (keepOperators
					|| !AstBuilderListener.OPERATOR_SERVICE_NAMES.contains(service.getServiceMethod()
							.getName())) {
				result.add(new ServiceCompletionProposal(service));
			}
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EOperationCompletionProposal} for {@link EOperation}.
	 * 
	 * @param receiverTypes
	 *            the receiver types.
	 * @return the {@link List} of {@link EOperationCompletionProposal} for {@link EOperation}
	 */
	public List<EOperationCompletionProposal> getEOperationProposals(Set<IType> receiverTypes) {
		final List<EOperationCompletionProposal> result = new ArrayList<EOperationCompletionProposal>();

		final Set<EClass> eClasses = new LinkedHashSet<EClass>();
		for (IType type : receiverTypes) {
			if (type.getType() instanceof EClass) {
				eClasses.add((EClass)type.getType());
			}
		}
		for (EOperation eOperation : ePackageProvider.getEOperations(eClasses)) {
			result.add(new EOperationCompletionProposal(eOperation));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EFeatureCompletionProposal} for {@link EStructuralFeature}.
	 * 
	 * @param receiverTypes
	 *            the receiver types.
	 * @return the {@link List} of {@link EFeatureCompletionProposal} for {@link EStructuralFeature}
	 */
	public List<EFeatureCompletionProposal> getEStructuralFeatureProposals(Set<IType> receiverTypes) {
		final List<EFeatureCompletionProposal> result = new ArrayList<EFeatureCompletionProposal>();
		final Set<EClass> eClasses = new LinkedHashSet<EClass>();

		for (IType iType : receiverTypes) {
			if (iType.getType() instanceof EClass) {
				eClasses.add((EClass)iType.getType());
			}
		}

		for (EStructuralFeature feature : ePackageProvider.getEStructuralFeatures(eClasses)) {
			result.add(new EFeatureCompletionProposal(feature));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EClassifierCompletionProposal} for {@link EClassifier}.
	 * 
	 * @return the {@link List} of {@link EClassifierCompletionProposal} for {@link EClassifier}
	 */
	public List<EClassifierCompletionProposal> getEClassifierProposals() {
		final List<EClassifierCompletionProposal> result = new ArrayList<EClassifierCompletionProposal>();

		for (EClassifier eClassifier : ePackageProvider.getEClassifiers()) {
			result.add(new EClassifierCompletionProposal(eClassifier));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EClassifierCompletionProposal} for {@link EClassifier}.
	 * 
	 * @param nsPrefix
	 *            the {@link EPackage#getNsPrefix() name space prefix}
	 * @return the {@link List} of {@link EClassifierCompletionProposal} for {@link EClassifier}
	 */
	public List<EClassifierCompletionProposal> getEClassifierProposals(String nsPrefix) {
		final List<EClassifierCompletionProposal> result = new ArrayList<EClassifierCompletionProposal>();

		final EPackage ePkg = ePackageProvider.getEPackage(nsPrefix);
		if (ePkg != null) {
			for (EClassifier eClassifier : ePkg.getEClassifiers()) {
				result.add(new EClassifierCompletionProposal(eClassifier));
			}
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}.
	 * 
	 * @return the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}
	 */
	public List<EEnumLiteralCompletionProposal> getEEnumLiteralProposals() {
		final List<EEnumLiteralCompletionProposal> result = new ArrayList<EEnumLiteralCompletionProposal>();

		for (EEnumLiteral literal : ePackageProvider.getEEnumLiterals()) {
			result.add(new EEnumLiteralCompletionProposal(literal));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}.
	 * 
	 * @param nsPrefix
	 *            the {@link EPackage#getNsPrefix() name space prefix}
	 * @return the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}
	 */
	public List<EEnumLiteralCompletionProposal> getEEnumLiteralProposals(String nsPrefix) {
		final List<EEnumLiteralCompletionProposal> result = new ArrayList<EEnumLiteralCompletionProposal>();

		final EPackage ePkg = ePackageProvider.getEPackage(nsPrefix);
		if (ePkg != null) {
			for (EClassifier eClassifier : ePkg.getEClassifiers()) {
				if (eClassifier instanceof EEnum) {
					for (EEnumLiteral literal : ((EEnum)eClassifier).getELiterals()) {
						result.add(new EEnumLiteralCompletionProposal(literal));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}.
	 * 
	 * @param nsPrefix
	 *            the {@link EPackage#getNsPrefix() name space prefix}
	 * @param eEnumName
	 *            the {@link EEnum#getName() eenum name}
	 * @return the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}
	 */
	public List<EEnumLiteralCompletionProposal> getEEnumLiteralProposals(String nsPrefix, String eEnumName) {
		final List<EEnumLiteralCompletionProposal> result = new ArrayList<EEnumLiteralCompletionProposal>();

		final EPackage ePkg = ePackageProvider.getEPackage(nsPrefix);
		if (ePkg != null) {
			EClassifier eClassifier = ePkg.getEClassifier(eEnumName);
			if (eClassifier instanceof EEnum) {
				for (EEnumLiteral literal : ((EEnum)eClassifier).getELiterals()) {
					result.add(new EEnumLiteralCompletionProposal(literal));
				}
			}
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link VariableDeclarationCompletionProposal} for the given {@link Set} of
	 * {@link IType} the {@link org.eclipse.acceleo.query.ast.VariableDeclaration VariableDeclaration} can
	 * have.
	 * 
	 * @param possibleTypes
	 *            the {@link Set} of {@link IType} the
	 *            {@link org.eclipse.acceleo.query.ast.VariableDeclaration VariableDeclaration} can have
	 * @return the {@link List} of {@link VariableDeclarationCompletionProposal} for the given {@link Set} of
	 *         {@link IType} the {@link org.eclipse.acceleo.query.ast.VariableDeclaration VariableDeclaration}
	 *         can have
	 */
	public List<VariableDeclarationCompletionProposal> getVariableDeclarationProposals(
			Set<IType> possibleTypes) {
		final List<VariableDeclarationCompletionProposal> result = new ArrayList<VariableDeclarationCompletionProposal>();

		for (IType type : possibleTypes) {
			result.add(new VariableDeclarationCompletionProposal(type));
		}

		return result;
	}

}
