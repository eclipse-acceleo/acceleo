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

import org.eclipse.acceleo.query.ast.CallType;
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
import org.eclipse.acceleo.query.validation.type.ICollectionType;
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
	 */
	public CompletionServices(IQueryEnvironment queryEnv) {
		super(queryEnv);
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
	 * @param callType
	 *            Indicate the type of call used, can be <code>null</code>
	 * @return the {@link List} of {@link ServiceCompletionProposal} for {@link IService}
	 */
	public List<ServiceCompletionProposal> getServiceProposals(Set<IType> receiverTypes, CallType callType) {
		final List<ServiceCompletionProposal> result = new ArrayList<ServiceCompletionProposal>();

		final Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

		if (CallType.CALLORAPPLY.equals(callType)) {
			for (IType type : receiverTypes) {
				if (type instanceof ICollectionType) {
					// Implicit collect
					ICollectionType collectionType = (ICollectionType)type;
					IType cType = collectionType.getCollectionType();
					classes.add(getClass(cType));
				} else {
					classes.add(getClass(type));
				}
			}
		} else if (CallType.COLLECTIONCALL.equals(callType) || callType == null) {
			for (IType type : receiverTypes) {
				classes.add(getClass(type));
			}
		}

		for (IService service : queryEnvironment.getLookupEngine().getServices(classes)) {
			if (callType == null
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
		for (EOperation eOperation : queryEnvironment.getEPackageProvider().getEOperations(eClasses)) {
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
			} else if (iType instanceof ICollectionType) {
				// Implicit collect
				ICollectionType collectionType = (ICollectionType)iType;
				IType type = collectionType.getCollectionType();
				if (type.getType() instanceof EClass) {
					eClasses.add((EClass)type.getType());
				}
			}
		}

		for (EStructuralFeature feature : queryEnvironment.getEPackageProvider().getEStructuralFeatures(
				eClasses)) {
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

		for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getEClassifiers()) {
			result.add(new EClassifierCompletionProposal(eClassifier));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EClassifierCompletionProposal} for {@link EClassifier}.
	 * 
	 * @param name
	 *            the {@link EPackage#getName() name space name}
	 * @return the {@link List} of {@link EClassifierCompletionProposal} for {@link EClassifier}
	 */
	public List<EClassifierCompletionProposal> getEClassifierProposals(String name) {
		final List<EClassifierCompletionProposal> result = new ArrayList<EClassifierCompletionProposal>();

		final EPackage ePkg = queryEnvironment.getEPackageProvider().getEPackage(name);
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

		for (EEnumLiteral literal : queryEnvironment.getEPackageProvider().getEEnumLiterals()) {
			result.add(new EEnumLiteralCompletionProposal(literal));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}.
	 * 
	 * @param name
	 *            the {@link EPackage#getName() name space name}
	 * @return the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}
	 */
	public List<EEnumLiteralCompletionProposal> getEEnumLiteralProposals(String name) {
		final List<EEnumLiteralCompletionProposal> result = new ArrayList<EEnumLiteralCompletionProposal>();

		final EPackage ePkg = queryEnvironment.getEPackageProvider().getEPackage(name);
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
	 * @param name
	 *            the {@link EPackage#getName() name space name}
	 * @param eEnumName
	 *            the {@link EEnum#getName() eenum name}
	 * @return the {@link List} of {@link EEnumLiteralCompletionProposal} for {@link EEnumLiteral}
	 */
	public List<EEnumLiteralCompletionProposal> getEEnumLiteralProposals(String name, String eEnumName) {
		final List<EEnumLiteralCompletionProposal> result = new ArrayList<EEnumLiteralCompletionProposal>();

		final EPackage ePkg = queryEnvironment.getEPackageProvider().getEPackage(name);
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
