/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Jerome Benois - eInverse initial implementation
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

/**
 * This will define additional convenience operations on OCL types. Those were not defined in the
 * specification.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoNonStandardLibrary {
	/**
	 * Name of the &quot;sep&quot; non-standard operation accessible on collections. Note that depending on
	 * the source collection, the returned collection will be of different types.
	 * <table>
	 * <tr>
	 * <td>Source Type</td>
	 * <td>Return Type</td>
	 * </tr>
	 * <tr>
	 * <td>Set(T)</td>
	 * <td>Bag(OclAny)</td>
	 * </tr>
	 * <tr>
	 * <td>OrderedSet(T)</td>
	 * <td>Sequence(OclAny)</td>
	 * </tr>
	 * <tr>
	 * <td>Sequence(T)</td>
	 * <td>Sequence(OclAny)</td>
	 * </tr>
	 * <tr>
	 * <td>Bag(T)</td>
	 * <td>Bag(OclAny)</td>
	 * </tr>
	 * </table>
	 * <p>
	 * <b>sep( String ) : Collection</b><br/>
	 * Returns all elements from the source collection separated by an element composed of the
	 * <code>separator</code> String.
	 * </p>
	 * 
	 * @since 0.9
	 */
	public static final String OPERATION_COLLECTION_SEP = "sep"; //$NON-NLS-1$

	/**
	 * Name of the &quot;ancestors&quot; non-standard operation accessible on all objects. This operation
	 * comes in two flavors :
	 * <p>
	 * <b>ancestors( ) : Sequence</b><br/>
	 * Returns a Sequence containing the full set of the receiver's ancestors.
	 * </p>
	 * <p>
	 * <b>ancestors( OclAny ) : Sequence</b><br/>
	 * Returns the elements of the given type from the set of the receiver's ancestors as a Sequence.
	 * </p>
	 */
	public static final String OPERATION_OCLANY_ANCESTORS = "ancestors"; //$NON-NLS-1$

	/**
	 * Name of the &quot;siblings&quot; non-standard operation accessible on all objects. This operation comes
	 * in two flavors :
	 * <p>
	 * <b>siblings( ) : Sequence</b><br/>
	 * Returns a Sequence containing the full set of the receiver's siblings.
	 * </p>
	 * <p>
	 * <b>siblings( OclAny ) : Sequence</b><br/>
	 * Returns the elements of the given type from the set of the receiver's siblings as a Sequence.
	 * </p>
	 */
	public static final String OPERATION_OCLANY_SIBLINGS = "siblings"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eAllContents&quot; non-standard operation accessible on all objects. This operation
	 * comes in two flavors :
	 * <p>
	 * <b>eAllContents( ) : Sequence</b><br/>
	 * Returns the whole content tree of the receiver as a Sequence.
	 * </p>
	 * <p>
	 * <b>eAllContents( OclAny ) : Sequence</b><br/>
	 * Returns the elements of the given type from the whole content tree of the receiver as a Sequence.
	 * </p>
	 */
	public static final String OPERATION_OCLANY_EALLCONTENTS = "eAllContents"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eGet&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>eGet( String ) : EJavaObject</b><br/>
	 * This will fetch the value of the feature named <em>featureName</em> on the current Object. Return type
	 * can as well be a collection as a single value.
	 * </p>
	 * 
	 * @since 0.9
	 */
	public static final String OPERATION_OCLANY_EGET = "eGet"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eInverse&quot; non-standard operation accessible on all objects. This operation comes
	 * in two flavors :
	 * <p>
	 * <b>eInverse( ) : Sequence(T)</b><br/>
	 * Returns the inverse references of the receiver.
	 * </p>
	 * <p>
	 * <b>eInverse( OclAny ) : Sequence(T)</b><br/>
	 * Returns the elements of the given type from the set of the inverse references of the receiver.
	 * </p>
	 */
	public static final String OPERATION_OCLANY_EINVERSE = "eInverse"; //$NON-NLS-1$

	/**
	 * Name of the &quot;invoke&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>invoke( String class, String method, Sequence(OclAny) arguments ) : OclAny</b><br/>
	 * Invokes the method <code>method</code> of class <code>class</code> with the given arguments. This will
	 * return OclInvalid if the method cannot be called.
	 * </p>
	 * 
	 * @since 0.8
	 */
	public static final String OPERATION_OCLANY_INVOKE = "invoke"; //$NON-NLS-1$

	/**
	 * Name of the &quot;current&quot; non-standard operation accessible on all objects. This operation comes
	 * in two flavors :
	 * <p>
	 * <b>current( Integer ) : OclAny</b><br/>
	 * Returns the value of the context <code>index</code> ranks above the current context.
	 * </p>
	 * <p>
	 * <b>current( OclType ) : OclAny</b><br/>
	 * Returns the value of the first context at or above the current context of type <code>filter</code>.
	 * </p>
	 * 
	 * @since 0.9
	 */
	public static final String OPERATION_OCLANY_CURRENT = "current"; //$NON-NLS-1$

	/**
	 * Name of the &quot;getProperty&quot; non-standard operation accessible on all objects. This operation
	 * comes in four flavors :
	 * <p>
	 * <b>getProperty( String ) : String</b><br/>
	 * Returns the value of the property corresponding to the given key. <b>Note</b> that parameterized
	 * properties will be returned as is with this.
	 * </p>
	 * <p>
	 * <b>getProperty( String, Sequence ) : String</b><br/>
	 * Returns the value of the property corresponding to the given key, with parameters substituted with the
	 * given values.
	 * </p>
	 * <p>
	 * <b>getProperty( String, String ) : String</b><br/>
	 * Returns the value of the property corresponding to the given key from a properties file corresponding
	 * to the given name. <b>Note</b> that parameterized properties will be returned as is with this.
	 * </p>
	 * <p>
	 * <b>getProperty( String, String, Sequence ) : String</b><br/>
	 * Returns the value of the property corresponding to the given key from a properties file corresponding
	 * to the given name, with parameters substituted with the given values.
	 * </p>
	 * 
	 * @since 0.9
	 */
	public static final String OPERATION_OCLANY_GETPROPERTY = "getProperty"; //$NON-NLS-1$

	/**
	 * Name of the &quot;toString&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>toString( ) : String</b><br/>
	 * Returns the String representation of the receiver.
	 * </p>
	 */
	public static final String OPERATION_OCLANY_TOSTRING = "toString"; //$NON-NLS-1$

	/**
	 * Name of the &quot;contains&quot; non-standard String operation.
	 * <p>
	 * <b>contains( String substring ) : Boolean</b><br/>
	 * Returns <code>true</code> if self contains the substring <code>substring</code>, <code>false</code>
	 * otherwise.
	 * </p>
	 * 
	 * @since 0.8
	 */
	public static final String OPERATION_STRING_CONTAINS = "contains"; //$NON-NLS-1$

	/**
	 * Name of the &quot;endsWith&quot; non-standard String operation.
	 * <p>
	 * <b>endsWith( String substring ) : Boolean</b><br/>
	 * Returns <code>true</code> if self ends with the substring <code>substring</code>, <code>false</code>
	 * otherwise.
	 * </p>
	 */
	public static final String OPERATION_STRING_ENDSWITH = "endsWith"; //$NON-NLS-1$

	/**
	 * Name of the &quot;replace&quot; non-standard String operation.
	 * <p>
	 * <b>replace( String substring, String replacement ) : String</b><br/>
	 * Substitutes the first occurence of substring <code>substring</code> in self by substring
	 * <code>replacement</code> and returns the resulting string. If there is no occurrence of the substring,
	 * The original string is returned. <code>substring</code> and <code>replacement</code> are treated as
	 * regular expressions.
	 * </p>
	 */
	public static final String OPERATION_STRING_REPLACE = "replace"; //$NON-NLS-1$

	/**
	 * Name of the &quot;replaceAll&quot; non-standard String operation.
	 * <p>
	 * <b>replaceAll( String substring, String replacement ) : String</b><br/>
	 * Substitutes all substrings <code>substring</code> in self by substring <code>replacement</code> and
	 * returns the resulting string. If there is no occurrence of the substring, The original string is
	 * returned. <code>substring</code> and <code>replacement</code> are treated as regular expressions.
	 * </p>
	 */
	public static final String OPERATION_STRING_REPLACEALL = "replaceAll"; //$NON-NLS-1$

	/**
	 * Name of the &quot;startsWith&quot; non-standard String operation.
	 * <p>
	 * <b>startsWith( String substring ) : Boolean</b><br/>
	 * Returns <code>true</code> if self starts with the substring <code>substring</code>, <code>false</code>
	 * otherwise.
	 * </p>
	 */
	public static final String OPERATION_STRING_STARTSWITH = "startsWith"; //$NON-NLS-1$

	/**
	 * Name of the &quot;substituteAll&quot; non-standard String operation.
	 * <p>
	 * <b>substituteAll( String substring, String replacement ) : String</b><br/>
	 * Substitutes all substrings <code>substring</code> in self by substring <code>replacement</code> and
	 * returns the resulting string. If there is no occurrence of the substring, The original string is
	 * returned. <code>substring</code> and <code>replacement</code> are not treated as regular expressions.
	 * </p>
	 */
	public static final String OPERATION_STRING_SUBSTITUTEALL = "substituteAll"; //$NON-NLS-1$

	/**
	 * Name of the &quot;tokenize&quot; non-standard String operation.
	 * <p>
	 * <b>tokenize( String delim ) : Sequence</b><br/>
	 * Returns a sequence containing all parts of self split around delimiters defined by the characters in
	 * String delim.
	 * </p>
	 */
	public static final String OPERATION_STRING_TOKENIZE = "tokenize"; //$NON-NLS-1$

	/**
	 * Name of the &quot;trim&quot; non-standard String operation.
	 * <p>
	 * <b>trim( ) : String</b><br/>
	 * Removes all leading and trailing spaces of self.
	 * </p>
	 */
	public static final String OPERATION_STRING_TRIM = "trim"; //$NON-NLS-1$

	/** Name of the primitive type "String" as defined in the OCL standard library. */
	public static final String PRIMITIVE_STRING_NAME = "String"; //$NON-NLS-1$

	/**
	 * Name of the type "Collection" used for common EOperations on OCL collection.
	 * 
	 * @since 0.9
	 */
	public static final String TYPE_COLLECTION_NAME = "Collection(T)"; //$NON-NLS-1$

	/** Name of the type "OclAny" used for common EOperations for all EObjects. */
	public static final String TYPE_OCLANY_NAME = "OclAny"; //$NON-NLS-1$

	/** This is the ecore package that will contain the Acceleo non-standard Library classifiers. */
	private static EPackage nonStdLibPackage;

	/** NS URI of the mtlnonstdlib.ecore which defines the Acceleo non-standard operation library. */
	private static final String NS_URI = "http://www.eclipse.org/acceleo/mtl/0.8.0/mtlnonstdlib.ecore"; //$NON-NLS-1$

	/** EClass for the Acceleo non-standard library's "Collection" type. */
	private static EClass collectionType;

	/** EClass for the Acceleo non-standard library's "OclAny" type. */
	private static EClass oclAnyType;

	/** EClass for the Acceleo non-standard library's "String" type. */
	private static EClass stringType;

	/**
	 * Initializes the non-standard library package along with its content.
	 */
	public AcceleoNonStandardLibrary() {
		final ResourceSet resourceSet = new ResourceSetImpl();

		try {
			nonStdLibPackage = (EPackage)ModelUtils.load(URI.createURI(NS_URI), resourceSet);
			collectionType = (EClass)nonStdLibPackage.getEClassifier(TYPE_COLLECTION_NAME);
			oclAnyType = (EClass)nonStdLibPackage.getEClassifier(TYPE_OCLANY_NAME);
			stringType = (EClass)nonStdLibPackage.getEClassifier(PRIMITIVE_STRING_NAME);
		} catch (IOException e) {
			AcceleoCommonPlugin.log(
					AcceleoCommonMessages.getString("AcceleoNonStandardLibrary.LoadFailure"), false); //$NON-NLS-1$
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
	public synchronized EList<EOperation> getExistingOperations(String classifierName) {
		EList<EOperation> result = new BasicEList<EOperation>();

		if (PRIMITIVE_STRING_NAME.equals(classifierName)) {
			result.addAll(stringType.getEOperations());
		} else if (TYPE_OCLANY_NAME.equals(classifierName)) {
			result.addAll(oclAnyType.getEOperations());
		} else if (TYPE_COLLECTION_NAME.equals(classifierName)) {
			result.addAll(collectionType.getEOperations());
		}

		return result;
	}
}
