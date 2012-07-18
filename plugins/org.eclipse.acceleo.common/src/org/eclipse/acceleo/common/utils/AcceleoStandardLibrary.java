/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.utils;

import java.io.IOException;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.internal.OCLStandardLibraryImpl;

/**
 * This will define additional operations on OCL types as defined in the Acceleo specification.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoStandardLibrary {
	/**
	 * Name of the &quot;toString&quot; standard Integer operation.
	 * <p>
	 * <b>toString( ) : String</b><br/>
	 * Converts the integer self to a string.
	 * </p>
	 * <p>
	 * <b>Note</b> that this has been replaced by the
	 * {@link AcceleoNonStandardLibrary#OPERATION_OCLANY_TOSTRING}.
	 * </p>
	 */
	public static final String OPERATION_INTEGER_TOSTRING = "toString"; //$NON-NLS-1$

	/**
	 * Name of the &quot;toString&quot; standard Real operation.
	 * <p>
	 * <b>toString( ) : String</b><br/>
	 * Converts the real self to a string.
	 * </p>
	 * <p>
	 * <b>Note</b> that this has been replaced by the
	 * {@link AcceleoNonStandardLibrary#OPERATION_OCLANY_TOSTRING}.
	 * </p>
	 */
	public static final String OPERATION_REAL_TOSTRING = "toString"; //$NON-NLS-1$

	/**
	 * Name of the &quot;first&quot; standard String operation.
	 * <p>
	 * <b>first( Integer n ) : String</b><br/>
	 * Returns first n characters of self, or self if size of self is less than n.
	 * </p>
	 */
	public static final String OPERATION_STRING_FIRST = "first"; //$NON-NLS-1$

	/**
	 * Name of the &quot;index&quot; standard String operation.
	 * <p>
	 * <b>index( String r ) : Integer</b><br/>
	 * Returns the index of substring r in self, or -1 if r is not in self.
	 * </p>
	 */
	public static final String OPERATION_STRING_INDEX = "index"; //$NON-NLS-1$

	/**
	 * Name of the &quot;isAlpha&quot; standard String operation.
	 * <p>
	 * <b>isAlpha() : Boolean</b><br/>
	 * Returns <code>true</code> if self consists only of alphabetical characters, <code>false</code>
	 * otherwise.
	 * </p>
	 */
	public static final String OPERATION_STRING_ISALPHA = "isAlpha"; //$NON-NLS-1$

	/**
	 * Name of the &quot;isAlphanum&quot; standard String operation.
	 * <p>
	 * <b>isAlphanum() : Boolean</b><br/>
	 * Returns <code>true</code> if self consists only of alphanumeric characters, <code>false</code>
	 * otherwise.
	 * </p>
	 */
	public static final String OPERATION_STRING_ISALPHANUM = "isAlphanum"; //$NON-NLS-1$

	/**
	 * Name of the &quot;last&quot; standard String operation.
	 * <p>
	 * <b>last( Integer n ) : String</b><br/>
	 * Returns last n characters of self, or self if size of self is less than n.
	 * </p>
	 */
	public static final String OPERATION_STRING_LAST = "last"; //$NON-NLS-1$

	/**
	 * Name of the &quot;strcmp&quot; standard String operation.
	 * <p>
	 * <b>strcmp( String s1 ) : Integer</b><br/>
	 * Returns an integer less than zero, equal to zero, or greater than zero depending on whether s1 is
	 * lexicographically less than, equal to, or greater than self.
	 * </p>
	 */
	public static final String OPERATION_STRING_STRCMP = "strcmp"; //$NON-NLS-1$

	/**
	 * Name of the &quot;strstr&quot; standard String operation.
	 * <p>
	 * <b>strstr( String r ) : Boolean</b><br/>
	 * Searches for string r in self. Returns true if found, false otherwise.
	 * </p>
	 */
	public static final String OPERATION_STRING_STRSTR = "strstr"; //$NON-NLS-1$

	/**
	 * Name of the &quot;strtok&quot; standard String operation.
	 * <p>
	 * <b>strtok( String s1, Integer flag ) : String</b><br/>
	 * Breaks the string self into a sequence of tokens each of which is delimited by any character in string
	 * s1. The parameter flag should be 0 when strtok is called for the first time, 1 subsequently.
	 * </p>
	 */
	public static final String OPERATION_STRING_STRTOK = "strtok"; //$NON-NLS-1$

	/**
	 * Name of the &quot;substitute&quot; standard String operation.
	 * <p>
	 * <b>substitute( String r, String t ) : String</b><br/>
	 * Substitutes substring r in self by substring t and returns the resulting string. If there is no
	 * occurrence of the substring, it returns the original string.
	 * </p>
	 */
	public static final String OPERATION_STRING_SUBSTITUTE = "substitute"; //$NON-NLS-1$

	/**
	 * Name of the &quot;toLowerFirst&quot; standard String operation.
	 * <p>
	 * <b>toLowerFirst() : String</b><br/>
	 * Creates a copy of self with first character converted to lowercase and returns it.
	 * </p>
	 */
	public static final String OPERATION_STRING_TOLOWERFIRST = "toLowerFirst"; //$NON-NLS-1$

	/**
	 * Name of the &quot;toUpperFirst&quot; standard String operation.
	 * <p>
	 * <b>toUpperFirst() : String</b><br/>
	 * Creates a copy of self with first character converted to uppercase and returns it.
	 * </p>
	 */
	public static final String OPERATION_STRING_TOUPPERFIRST = "toUpperFirst"; //$NON-NLS-1$

	/** Name of the primitive type "Integer" as defined in the OCL standard library. */
	public static final String PRIMITIVE_INTEGER_NAME = "Integer"; //$NON-NLS-1$

	/** Name of the primitive type "Real" as defined in the OCL standard library. */
	public static final String PRIMITIVE_REAL_NAME = "Real"; //$NON-NLS-1$

	/** Name of the primitive type "String" as defined in the OCL standard library. */
	public static final String PRIMITIVE_STRING_NAME = "String"; //$NON-NLS-1$

	/** EClass for the Acceleo standard library's "Integer" type. */
	private static EClass integerType;

	/** NS URI of the mtlstdlib.ecore which defines the Acceleo standard operation library. */
	private static final String NS_URI = "http://www.eclipse.org/acceleo/mtl/3.0/mtlstdlib.ecore"; //$NON-NLS-1$

	/** EClass for the Acceleo standard library's "Real" type. */
	private static EClass realType;

	/** This is the ecore package that will contain the Acceleo Standard Library classifiers. */
	private static EPackage stdLibPackage;

	/** EClass for the Acceleo standard library's "String" type. */
	private static EClass stringType;

	/**
	 * Initializes the standard library package along with its content.
	 */
	public AcceleoStandardLibrary() {
		final ResourceSet resourceSet = new ResourceSetImpl();
		/*
		 * Crude workaround : We try not to reload the std lib for no reason, but that means the OCL standard
		 * lib used for our references must be the sole instance used by OCL. FIXME : For now, use internals
		 * ... try and find a way to use it without restricted access.
		 */
		resourceSet.getResources().add(OCLStandardLibraryImpl.INSTANCE.getString().eResource());

		try {
			if (stdLibPackage == null) {
				stdLibPackage = (EPackage)ModelUtils.load(URI.createURI(NS_URI), resourceSet);
				stringType = (EClass)stdLibPackage.getEClassifier(PRIMITIVE_STRING_NAME);
				integerType = (EClass)stdLibPackage.getEClassifier(PRIMITIVE_INTEGER_NAME);
				realType = (EClass)stdLibPackage.getEClassifier(PRIMITIVE_REAL_NAME);
			}
		} catch (IOException e) {
			AcceleoCommonPlugin.log(
					AcceleoCommonMessages.getString("AcceleoStandardLibrary.LoadFailure"), false); //$NON-NLS-1$
		}
	}

	/**
	 * Obtains the operations declared on the specified type.
	 * 
	 * @param type
	 *            The type which operation are sought.
	 * @return The operations it declares. <code>null</code> if none.
	 */
	public EList<EOperation> getExistingOperations(EClassifier type) {
		return getExistingOperations(type.getName());
	}

	/**
	 * Obtains the operations declared on the specified type.
	 * 
	 * @param classifierName
	 *            The name of the classifier which operation are sought.
	 * @return The operations it declares. <code>null</code> if none.
	 */
	public EList<EOperation> getExistingOperations(String classifierName) {
		EList<EOperation> result = new BasicEList<EOperation>();

		if (PRIMITIVE_STRING_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(stringType.getEOperations()));
		} else if (PRIMITIVE_INTEGER_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(integerType.getEOperations()));
		} else if (PRIMITIVE_REAL_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(realType.getEOperations()));
		}

		return result;
	}
}
