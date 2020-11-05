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
package org.eclipse.acceleo.aql.migration.converters.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.ecore.AnyType;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.PrimitiveType;
import org.eclipse.ocl.util.Bag;

/**
 * Type converter.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class TypeUtils {

	private TypeUtils() {
	}

	/**
	 * Tells if the given {@link EClassifier} is OclAny or contains an OclAny.
	 * 
	 * @param type
	 *            the {@link EClassifier}
	 * @return <code>true</code> if the given {@link EClassifier} is OclAny or contains an OclAny,
	 *         <code>false</code> otherwise
	 */
	public static boolean containsOclAny(EClassifier type) {
		boolean res;

		if (type instanceof AnyType) {
			res = true;
		} else if (type instanceof CollectionType) {
			res = containsOclAny(((CollectionType)type).getElementType());
		} else {
			res = false;
		}

		return res;
	}

	/**
	 * Creates a type literal from the input type.
	 * 
	 * @param inputType
	 *            the A3 type
	 * @return the A4 type literal
	 */
	public static TypeLiteral createTypeLiteral(EClassifier inputType) {
		TypeLiteral typeLiteral = null;
		if (inputType instanceof CollectionType) {
			typeLiteral = AstFactory.eINSTANCE.createCollectionTypeLiteral();
			Class<?> instanceClass = ((CollectionType)inputType).getInstanceClass();
			if (instanceClass.equals(LinkedHashSet.class)) {
				instanceClass = Set.class;
			} else if (instanceClass.equals(Collection.class)) {
				// TODO it might be better to have an actual Collection type here
				instanceClass = List.class;
			} else if (instanceClass.equals(Bag.class)) {
				instanceClass = List.class;
			}
			((CollectionTypeLiteral)typeLiteral).setValue(instanceClass);
			((CollectionTypeLiteral)typeLiteral).setElementType(createTypeLiteral(((CollectionType)inputType)
					.getElementType()));
		} else if (inputType instanceof PrimitiveType) {
			typeLiteral = AstFactory.eINSTANCE.createTypeLiteral();
			typeLiteral.setValue(inputType.getInstanceClass());
		} else if (inputType instanceof AnyType) {
			typeLiteral = AstFactory.eINSTANCE.createTypeLiteral();
			typeLiteral.setValue(EcorePackage.eINSTANCE.getEObject());
		} else {
			typeLiteral = AstFactory.eINSTANCE.createTypeLiteral();
			typeLiteral.setValue(inputType);
		}
		return typeLiteral;
	}
}
