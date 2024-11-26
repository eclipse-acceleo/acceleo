/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;

/**
 * * Implementation of the elementary language completion services like retrieving variable names and service
 * names.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CompletionServices extends ValidationServices {

	/**
	 * Creates a new service instance given a {@link IReadOnlyQueryEnvironment} and logging flag.
	 * 
	 * @param queryEnv
	 *            the {@link IReadOnlyQueryEnvironment} to use
	 */
	public CompletionServices(IReadOnlyQueryEnvironment queryEnv) {
		super(queryEnv);
	}

	/**
	 * Gets the {@link List} of {@link VariableCompletionProposal} for variables.
	 * 
	 * @param variableTypes
	 *            the {@link Map} of variable names to their {@link Set} of {@link IType}
	 * @return the {@link List} of {@link VariableCompletionProposal} for variables
	 */
	public List<VariableCompletionProposal> getVariableProposals(Map<String, Set<IType>> variableTypes) {
		final List<VariableCompletionProposal> result = new ArrayList<VariableCompletionProposal>();

		for (Entry<String, Set<IType>> entry : variableTypes.entrySet()) {
			result.add(new VariableCompletionProposal(entry.getKey(), entry.getValue()));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for {@link IService}.
	 * 
	 * @param receiverTypes
	 *            the receiver {@link IType types}.
	 * @param callType
	 *            Indicate the type of call used, can be <code>null</code>
	 * @return the {@link List} of {@link ICompletionProposal} for {@link IService}
	 */
	public List<ICompletionProposal> getServiceProposals(Set<IType> receiverTypes, CallType callType) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		final Set<IType> types;

		if (CallType.CALLORAPPLY.equals(callType)) {
			types = new LinkedHashSet<IType>();
			for (IType type : receiverTypes) {
				if (type instanceof ICollectionType) {
					// Implicit collect
					final ICollectionType collectionType = (ICollectionType)type;
					final IType cType = collectionType.getCollectionType();
					types.add(cType);
				} else {
					types.add(type);
				}
			}
		} else if (CallType.COLLECTIONCALL.equals(callType) || callType == null) {
			types = receiverTypes;
		} else {
			types = Collections.emptySet();
		}

		for (IService<?> service : queryEnvironment.getLookupEngine().getServices(types)) {
			if (callType == null || !AstBuilderListener.OPERATOR_SERVICE_NAMES.contains(service.getName())) {
				result.addAll(service.getProposals(queryEnvironment, receiverTypes));
			}
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

		final Collection<EPackage> ePkgs = queryEnvironment.getEPackageProvider().getEPackage(name);
		if (!ePkgs.isEmpty()) {
			for (EPackage ePkg : ePkgs) {
				for (EClassifier eClassifier : ePkg.getEClassifiers()) {
					result.add(new EClassifierCompletionProposal(eClassifier));
				}
			}
		} else {
			result.addAll(getEClassifierProposals());
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

		final Collection<EPackage> ePkgs = queryEnvironment.getEPackageProvider().getEPackage(name);
		if (!ePkgs.isEmpty()) {
			for (EPackage ePkg : ePkgs) {
				for (EClassifier eClassifier : ePkg.getEClassifiers()) {
					if (eClassifier instanceof EEnum) {
						for (EEnumLiteral literal : ((EEnum)eClassifier).getELiterals()) {
							result.add(new EEnumLiteralCompletionProposal(literal));
						}
					}
				}
			}
		} else {
			result.addAll(getEEnumLiteralProposals());
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

		final Collection<EPackage> ePkgs = queryEnvironment.getEPackageProvider().getEPackage(name);
		if (!ePkgs.isEmpty()) {
			for (EPackage ePkg : ePkgs) {
				EClassifier eClassifier = ePkg.getEClassifier(eEnumName);
				if (eClassifier != null) {
					if (eClassifier instanceof EEnum) {
						for (EEnumLiteral literal : ((EEnum)eClassifier).getELiterals()) {
							result.add(new EEnumLiteralCompletionProposal(literal));
						}
					}
				} else {
					result.addAll(getEEnumLiteralProposals(eEnumName));
				}
			}
		} else {
			result.addAll(getEEnumLiteralProposals());
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
