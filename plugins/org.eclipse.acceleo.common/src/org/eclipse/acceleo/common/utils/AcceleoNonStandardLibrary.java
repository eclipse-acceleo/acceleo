/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.internal.OCLStandardLibraryImpl;

/**
 * This will define additional convenience operations on OCL types. Those were not defined in the
 * specification.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoNonStandardLibrary {
	/**
	 * Name of the &quot;lastIndexOf&quot; non-standard operation accessible on collections.
	 * <p>
	 * <b>lastIndexOf( T element ) : Integer</b><br/>
	 * returns the index of the last occurence of <code>element</code> in <code>self</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_COLLECTION_LASTINDEXOF = "lastIndexOf"; //$NON-NLS-1$

	/**
	 * Name of the &quot;reverse&quot; non-standard operation accessible on collections. Note that depending
	 * on the source collection, the returned collection will be of different types.
	 * <table>
	 * <tr>
	 * <td>Source Type</td>
	 * <td>Return Type</td>
	 * </tr>
	 * <tr>
	 * <td>OrderedSet(T)</td>
	 * <td>OrderedSet(T)</td>
	 * </tr>
	 * <tr>
	 * <td>Sequence(T)</td>
	 * <td>Sequence(T)</td>
	 * </tr>
	 * </table>
	 * <p>
	 * <b>reverse( ) : Collection</b><br/>
	 * Returns all elements from the source collection in reverse order.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_COLLECTION_REVERSE = "reverse"; //$NON-NLS-1$

	/**
	 * Name of the &quot;select&quot; non-standard operation accessible on collections. Note that depending on
	 * the source collection, the returned collection will be of different types.
	 * <p>
	 * <b>filter( type ) : Collection</b><br/>
	 * Filters out of the collection all elements that are not instance of the given type. The returned
	 * collection is typed according to <em>type</em>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_COLLECTION_FILTER = "filter"; //$NON-NLS-1$

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
	 * <b>sep( String separator ) : Collection</b><br/>
	 * Returns all elements from the source collection separated by an element composed of the
	 * <code>separator</code> String.
	 * </p>
	 * <p>
	 * <b>sep( String prefix, String separator String suffix ) : Collection</b><br/>
	 * Returns all elements from the source collection starting by the <code>prefix</code> String separated by
	 * an element composed of the <code>separator</code> String and ending with the <code>suffix</code>
	 * String.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_COLLECTION_SEP = "sep"; //$NON-NLS-1$

	/**
	 * Name of the &quot;addAll&quot; non standard operation accessible on collections. The returned type of
	 * the operation will be the same type as the type of collection on which the operation is called.
	 * <p>
	 * <b>addAll(Collection): Collection</b><br />
	 * Returns a collection containing all the elements of the collection on which the operation is called and
	 * the collection passed as a parameter.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_ADD_ALL = "addAll"; //$NON-NLS-1$

	/**
	 * Name of the &quot;removeAll&quot; non standard operation accessible on collections. The returned type
	 * of the operation will be the same type as the type of collection on which the operation is called.
	 * <p>
	 * <b>removeAll(Collection): Collection</b><br />
	 * Returns a collection containing the elements of the collection on which the operation is called minus
	 * the elements of the collection passed as a parameter.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_REMOVE_ALL = "removeAll"; //$NON-NLS-1$

	/**
	 * Name of the &quot;drop&quot; non standard operation accessible on OrderedSet and Sequence. The returned
	 * type of the operation will be the same type as the type of collection on which the operation is called
	 * (ie OrderedSet or Sequence).
	 * <p>
	 * <b>drop(Integer): OrderedSet</b><br />
	 * Returns an OrderedSet containing the elements of the OrderedSet on which the operation is called minus
	 * the <code>n</code> first elements.
	 * </p>
	 * <p>
	 * <b>drop(Integer): Sequence</b><br />
	 * Returns an Sequence containing the elements of the OrderedSet on which the operation is called minus
	 * the <code>n</code> first elements.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_DROP = "drop"; //$NON-NLS-1$

	/**
	 * Name of the &quot;dropRight&quot; non standard operation accessible on OrderedSet and Sequence. The
	 * returned type of the operation will be the same type as the type of collection on which the operation
	 * is called (ie OrderedSet or Sequence).
	 * <p>
	 * <b>drop(Integer): OrderedSet</b><br />
	 * Returns an OrderedSet containing the elements of the OrderedSet on which the operation is called minus
	 * the <code>n</code> last elements.
	 * </p>
	 * <p>
	 * <b>drop(Integer): Sequence</b><br />
	 * Returns an Sequence containing the elements of the OrderedSet on which the operation is called minus
	 * the <code>n</code> last elements.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_DROP_RIGHT = "dropRight"; //$NON-NLS-1$

	/**
	 * Name of the &quot;startsWith&quot; non standard operation accessible on OrderedSet and Sequence. The
	 * returned type of the operation will be a boolean only if the collection on which the operation is
	 * applied starts with the collection given as argument.
	 * <p>
	 * <b>startsWith(OrderedSet): Boolean</b><br />
	 * Returns true if the collection (Sequence or OrderedSet) starts with the given OrderedSet.
	 * </p>
	 * <b>startsWith(Sequence): Boolean</b><br />
	 * Returns true if the collection (Sequence or OrderedSet) starts with the given Sequence. </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_STARTS_WITH = "startsWith"; //$NON-NLS-1$

	/**
	 * Name of the &quot;endsWith&quot; non standard operation accessible on OrderedSet and Sequence. The
	 * returned type of the operation will be a boolean only if the collection on which the operation is
	 * applied ends with the collection given as argument.
	 * <p>
	 * <b>endsWith(OrderedSet): Boolean</b><br />
	 * Returns true if the collection (Sequence or OrderedSet) ends with the given OrderedSet.
	 * </p>
	 * <b>endsWith(Sequence): Boolean</b><br />
	 * Returns true if the collection (Sequence or OrderedSet) ends with the given Sequence. </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_ENDS_WITH = "endsWith"; //$NON-NLS-1$

	/**
	 * Name of the &quot;indexOfSlice&quot; non standard operation accessible on OrderedSet and Sequence. The
	 * returned type of the operation will be an integer indicating the index of the sub-collection in the
	 * original collection.
	 * <p>
	 * <b>indexOfSlice(OrderedSet): Integer</b><br />
	 * Returns the index of the sub-collection in the collection on which is called the operation. -1 if the
	 * sub-collection does not appear in the original collection.
	 * </p>
	 * <p>
	 * <b>indexOfSlice(Sequence): Integer</b><br />
	 * Returns the index of the sub-collection in the collection on which is called the operation. -1 if the
	 * sub-collection does not appear in the original collection.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_INDEX_OF_SLICE = "indexOfSlice"; //$NON-NLS-1$

	/**
	 * Name of the &quot;indexOfSlice&quot; non standard operation accessible on OrderedSet and Sequence. The
	 * returned type of the operation will be an integer indicating the last index of the sub-collection in
	 * the original collection.
	 * <p>
	 * <b>lastIndexOfSlice(OrderedSet): Integer</b><br />
	 * Returns the last index of the sub-collection in the collection on which is called the operation. -1 if
	 * the sub-collection does not appear in the original collection.
	 * </p>
	 * <p>
	 * <b>lastIndexOfSlice(Sequence): Integer</b><br />
	 * Returns the last index of the sub-collection in the collection on which is called the operation. -1 if
	 * the sub-collection does not appear in the original collection.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_COLLECTION_LAST_INDEX_OF_SLICE = "lastIndexOfSlice"; //$NON-NLS-1$

	/**
	 * Name of the &quot;ancestors&quot; non-standard operation accessible on all objects. This operation
	 * comes in two flavors :
	 * <p>
	 * <b>ancestors( ) : Sequence</b><br/>
	 * Returns a Sequence containing the full set of the receiver's ancestors.
	 * </p>
	 * <p>
	 * <b>ancestors( OclType ) : Sequence</b><br/>
	 * Returns the elements of the given type from the set of the receiver's ancestors as a Sequence.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_ANCESTORS = "ancestors"; //$NON-NLS-1$

	/**
	 * Name of the &quot;siblings&quot; non-standard operation accessible on all objects. This operation comes
	 * in two flavors :
	 * <p>
	 * <b>siblings( ) : Sequence</b><br/>
	 * Returns a Sequence containing the full set of the receiver's siblings.
	 * </p>
	 * <p>
	 * <b>siblings( OclType ) : Sequence</b><br/>
	 * Returns the elements of the given type from the set of the receiver's siblings as a Sequence.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_SIBLINGS = "siblings"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eAllContents&quot; non-standard operation accessible on all objects. This operation
	 * comes in two flavors :
	 * <p>
	 * <b>eAllContents( ) : Sequence</b><br/>
	 * Returns the whole content tree of the receiver as a Sequence.
	 * </p>
	 * <p>
	 * <b>eAllContents( OclType ) : Sequence</b><br/>
	 * Returns the elements of the given type from the whole content tree of the receiver as a Sequence.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_EALLCONTENTS = "eAllContents"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eContainer&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>eContainer( OclType ) : Sequence</b><br/>
	 * Returns the first container of the receiver that if of the given type.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_ECONTAINER = "eContainer"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eContents&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>eContents( OclType ) : Sequence</b><br/>
	 * Returns the elements of the given type that are direct children of <em>self</em>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_ECONTENTS = "eContents"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eGet&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>eGet( String ) : EJavaObject</b><br/>
	 * This will fetch the value of the feature named <em>featureName</em> on the current Object. Return type
	 * can as well be a collection as a single value.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_EGET = "eGet"; //$NON-NLS-1$

	/**
	 * Name of the &quot;eInverse&quot; non-standard operation accessible on all objects. This operation comes
	 * in two flavors :
	 * <p>
	 * <b>eInverse( ) : Sequence(T)</b><br/>
	 * Returns the inverse references of the receiver.
	 * </p>
	 * <p>
	 * <b>eInverse( OclType ) : Sequence(T)</b><br/>
	 * Returns the elements of the given type from the set of the inverse references of the receiver.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_EINVERSE = "eInverse"; //$NON-NLS-1$

	/**
	 * Name of the &quot;followingSiblings&quot; non-standard operation accessible on all objects. This
	 * operation comes in two flavors :
	 * <p>
	 * <b>followingSiblings( ) : Sequence</b><br/>
	 * Returns the list of all elements at the same level as <em>self</em> which follow <em>self</em>, without
	 * reordering them.
	 * </p>
	 * <p>
	 * <b>followingSiblings( OclType ) : Sequence</b><br/>
	 * Returns the list of all elements of the given type located at the same level as <em>self</em> which
	 * follow <em>self</em>, without reordering them.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_FOLLOWINGSIBLINGS = "followingSiblings"; //$NON-NLS-1$

	/**
	 * Name of the &quot;precedingSiblings&quot; non-standard operation accessible on all objects. This
	 * operation comes in two flavors :
	 * <p>
	 * <b>precedingSiblings( ) : Sequence</b><br/>
	 * Returns the list of all elements at the same level as <em>self</em> which precede <em>self</em>,
	 * without reordering them.
	 * </p>
	 * <p>
	 * <b>precedingSiblings( OclType ) : Sequence</b><br/>
	 * Returns the list of all elements of the given type located at the same level as <em>self</em> which
	 * precede <em>self</em>, without reordering them.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_EOBJECT_PRECEDINGSIBLINGS = "precedingSiblings"; //$NON-NLS-1$

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
	 * @since 3.0
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
	 * @since 3.0
	 */
	public static final String OPERATION_OCLANY_GETPROPERTY = "getProperty"; //$NON-NLS-1$

	/**
	 * Name of the &quot;+&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>+( String other ) : String</b><br/>
	 * Returns the concatenation of <em>self</em> with <em>other</em>. Note that <em>self</em> will be
	 * implicitely cast to String via its <em>toString()</em> method beforehand.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_OCLANY_PLUS = "+"; //$NON-NLS-1$

	/**
	 * Name of the &quot;toString&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>toString( ) : String</b><br/>
	 * Returns the String representation of the receiver.
	 * </p>
	 */
	public static final String OPERATION_OCLANY_TOSTRING = "toString"; //$NON-NLS-1$

	/**
	 * Name of the &quot;lineSeparator&quot; non-standard operation accessible on all objects.
	 * <p>
	 * <b>lineSeparator(): String</b><br />
	 * Returns the currently used line separator by Eclipse. (by default, the standard line separator of the
	 * platform)
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_OCLANY_LINE_SEPARATOR = "lineSeparator"; //$NON-NLS-1$

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
	 * Name of the &quot;equalsIgnoreCase&quot; non-standard String operation.
	 * <p>
	 * <b>equalsIgnoreCase( String other ) : Boolean</b><br/>
	 * Returns <code>true</code> if self is equal to <code>other</code>, without considering casing in the
	 * comparison; <code>false</code> otherwise.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_STRING_EQUALSIGNORECASE = "equalsIgnoreCase"; //$NON-NLS-1$

	/**
	 * Name of the &quot;lastIndex&quot; non-standard String operation.
	 * <p>
	 * <b>lastIndex( String substring ) : Integer</b><br/>
	 * Returns the index of the last occurence of <code>substring</code> in self, <code>-1</code> if self
	 * doesn't contain this particular substring.
	 * </p>
	 * <b>lastIndex( String substring, Integer index ) : Integer</b><br/>
	 * Returns the index of the first occurence of <code>substring</code> in self starting from
	 * <code>index</code> but backward, <code>-1</code> if self doesn't contain this particular substring.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_STRING_LASTINDEX = "lastIndex"; //$NON-NLS-1$

	/**
	 * Name of the &quot;matches&quot; non-standard String operation.
	 * <p>
	 * <b>matches( String regex ) : Integer</b><br/>
	 * Returns <code>true</code> if <code>self</code> matches the given <code>regex</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_STRING_MATCHES = "matches"; //$NON-NLS-1$

	/**
	 * Name of the &quot;+&quot; non-standard String operation.
	 * <p>
	 * <b>+( OclAny other ) : String</b><br/>
	 * Returns the concatenation of <em>self</em> with <em>other</em>. Note that <em>other</em> will be
	 * implicitely cast to String via its <em>toString()</em> method beforehand.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_STRING_PLUS = "+"; //$NON-NLS-1$

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
	 * Name of the &quot;substring&quot; non-standard String operation.
	 * <p>
	 * <b>substring( Integer startIndex ) : String</b><br/>
	 * Returns the substring composed of all characters of <code>self</code> starting at index
	 * <code>startIndex</code>.
	 * </p>
	 * 
	 * @since 3.0
	 */
	public static final String OPERATION_STRING_SUBSTRING = "substring"; //$NON-NLS-1$

	/**
	 * Name of the &quot;tokenize&quot; non-standard String operation.
	 * <p>
	 * <b>tokenize( String delim ) : Sequence</b><br/>
	 * Returns a sequence containing all parts of self split around delimiters defined by the characters in
	 * String delim.
	 * </p>
	 * <p>
	 * <b>tokenize(): Sequence</b> Returns a sequence containing all parts of self split around whitespaces.
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

	/**
	 * Name of the &quot;index&quot; non-standard String operation.
	 * <p>
	 * <b>index(String, Integer) : Integer</b><br/>
	 * Returns the index of the subString starting from the given index.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_STRING_INDEX = "index"; //$NON-NLS-1$

	/**
	 * Name of the $quot;tokenizeLine$quot; non standard String operation.
	 * <p>
	 * <b>tokenizeLine(): Sequence(String)</b><br />
	 * Returns a sequence of String containing all parts of self split around line delimiters.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_STRING_TOKENIZE_LINE = "tokenizeLine"; //$NON-NLS-1$

	/**
	 * Name of &quot;prefix&quot; non standard String operation.
	 * <p>
	 * <b>prefix(String): String</b><br />
	 * Returns the self String prefixed by the String given as argument.
	 * </p>
	 * 
	 * @since 3.3
	 */
	public static final String OPERATION_STRING_PREFIX = "prefix"; //$NON-NLS-1$

	/** Name of the primitive type "String" as defined in the OCL standard library. */
	public static final String PRIMITIVE_STRING_NAME = "String"; //$NON-NLS-1$

	/**
	 * Name of the type "Collection" used for common EOperations on OCL collection.
	 * 
	 * @since 3.0
	 */
	public static final String TYPE_COLLECTION_NAME = "Collection(T)"; //$NON-NLS-1$

	/**
	 * Name of the type "EObject" used for common EOperations on EObjects.
	 * 
	 * @since 3.0
	 */
	public static final String TYPE_EOBJECT_NAME = "EObject"; //$NON-NLS-1$

	/** Name of the type "OclAny" used for common EOperations for all EObjects. */
	public static final String TYPE_OCLANY_NAME = "OclAny"; //$NON-NLS-1$

	/**
	 * Name of the type "OrderedSet" used for common EOperations on OCL OrderedSet.
	 * 
	 * @since 3.0
	 */
	public static final String TYPE_ORDEREDSET_NAME = "OrderedSet(T)"; //$NON-NLS-1$

	/**
	 * Name of the type "Sequence" used for common EOperations on OCL Sequence.
	 * 
	 * @since 3.0
	 */
	public static final String TYPE_SEQUENCE_NAME = "Sequence(T)"; //$NON-NLS-1$

	/** This is the ecore package that will contain the Acceleo non-standard Library classifiers. */
	private static EPackage nonStdLibPackage;

	/** NS URI of the mtlnonstdlib.ecore which defines the Acceleo non-standard operation library. */
	private static final String NS_URI = "http://www.eclipse.org/acceleo/mtl/3.0/mtlnonstdlib.ecore"; //$NON-NLS-1$

	/** EClass for the Acceleo non-standard library's "Collection" type. */
	private static EClass collectionType;

	/** EClass for the Acceleo non-standard library's "EObject" type. */
	private static EClass eObjectType;

	/** EClass for the Acceleo non-standard library's "OclAny" type. */
	private static EClass oclAnyType;

	/** EClass for the Acceleo non-standard library's "OrderedSet" type. */
	private static EClass orderedSetType;

	/** EClass for the Acceleo non-standard library's "Sequence" type. */
	private static EClass sequenceType;

	/** EClass for the Acceleo non-standard library's "String" type. */
	private static EClass stringType;

	/**
	 * Initializes the non-standard library package along with its content.
	 */
	public AcceleoNonStandardLibrary() {
		final ResourceSet resourceSet = new ResourceSetImpl();
		/*
		 * Crude workaround : We try not to reload the std lib for no reason, but that means the OCL standard
		 * lib used for our references must be the sole instance used by OCL. FIXME : For now, use internals
		 * ... try and find a way to use it without restricted access.
		 */
		resourceSet.getResources().add(OCLStandardLibraryImpl.INSTANCE.getString().eResource());

		try {
			if (nonStdLibPackage == null) {
				nonStdLibPackage = (EPackage)ModelUtils.load(URI.createURI(NS_URI), resourceSet);
				collectionType = (EClass)nonStdLibPackage.getEClassifier(TYPE_COLLECTION_NAME);
				eObjectType = (EClass)nonStdLibPackage.getEClassifier(TYPE_EOBJECT_NAME);
				oclAnyType = (EClass)nonStdLibPackage.getEClassifier(TYPE_OCLANY_NAME);
				orderedSetType = (EClass)nonStdLibPackage.getEClassifier(TYPE_ORDEREDSET_NAME);
				sequenceType = (EClass)nonStdLibPackage.getEClassifier(TYPE_SEQUENCE_NAME);
				stringType = (EClass)nonStdLibPackage.getEClassifier(PRIMITIVE_STRING_NAME);
			}
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
			result.addAll(EcoreUtil.copyAll(stringType.getEOperations()));
		} else if (TYPE_OCLANY_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(oclAnyType.getEOperations()));
		} else if (TYPE_EOBJECT_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(eObjectType.getEOperations()));
		} else if (TYPE_COLLECTION_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(collectionType.getEOperations()));
		} else if (TYPE_ORDEREDSET_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(orderedSetType.getEOperations()));
		} else if (TYPE_SEQUENCE_NAME.equals(classifierName)) {
			result.addAll(EcoreUtil.copyAll(sequenceType.getEOperations()));
		}
		return result;
	}
}
