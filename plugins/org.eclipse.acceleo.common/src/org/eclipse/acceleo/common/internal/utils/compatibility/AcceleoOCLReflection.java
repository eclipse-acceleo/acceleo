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
package org.eclipse.acceleo.common.internal.utils.compatibility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.types.OCLStandardLibrary;

/**
 * This class will allow us to remain compatible with OCL throughout standard library breakages.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoOCLReflection {
	/** Whole set of reserved keywords for the current OCL version. */
	private static Set<String> reservedKeywords;

	/**
	 * Once we've found this, there is no need to look it up again. The OCL Version won't change in one given
	 * lifespan of this Class.
	 */
	private static Method oclInvalidMethod;

	/** Parent environment of this reflection. */
	private EcoreEnvironment environment;

	/** Singleton instance of the OCLInvalid type. */
	private Object invalid;

	/** Classifier representing the OCL invalid type. */
	private EClassifier oclInvalid;

	/**
	 * Prepares the library reflection using the given parent environment.
	 * 
	 * @param env
	 *            Parent environment of this reflection.
	 */
	public AcceleoOCLReflection(EcoreEnvironment env) {
		environment = env;
	}

	/**
	 * This will return the singleton instance of the "OCLInvalid" type.
	 * 
	 * @return The singleton instance of the "OCLInvalid" type.
	 */
	public Object getInvalid() {
		if (invalid == null) {
			final OCLStandardLibrary<EClassifier> stdLib = environment.getOCLStandardLibrary();
			String methodName = "getOclInvalid"; //$NON-NLS-1$
			if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.HELIOS) {
				methodName = "getInvalid"; //$NON-NLS-1$
			}
			if (oclInvalidMethod == null) {
				try {
					oclInvalidMethod = stdLib.getClass().getMethod(methodName);
				} catch (NoSuchMethodException e) {
					AcceleoCommonPlugin.log(e, true);
				}
			}
			try {
				invalid = oclInvalidMethod.invoke(stdLib);
			} catch (InvocationTargetException e) {
				// cannot happen
				AcceleoCommonPlugin.log(e, true);
			} catch (IllegalAccessException e) {
				// We know this method is public
				AcceleoCommonPlugin.log(e, true);
			}
		}
		return invalid;
	}

	/**
	 * This will return the classifier representing the invalid type.
	 * 
	 * @return Classifier representing the invalid type.
	 */
	public EClassifier getOCLInvalid() {
		if (oclInvalid == null) {
			final OCLStandardLibrary<EClassifier> stdLib = environment.getOCLStandardLibrary();
			String methodName = "getInvalid"; //$NON-NLS-1$
			if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.HELIOS) {
				methodName = "getOclInvalid"; //$NON-NLS-1$
			}
			try {
				final Method method = stdLib.getClass().getMethod(methodName);
				oclInvalid = (EClassifier)method.invoke(stdLib);
			} catch (NoSuchMethodException e) {
				// Shouldn't happen
				AcceleoCommonPlugin.log(e, true);
			} catch (InvocationTargetException e) {
				// cannot happen
				AcceleoCommonPlugin.log(e, true);
			} catch (IllegalAccessException e) {
				// We know this method is public
				AcceleoCommonPlugin.log(e, true);
			}
		}
		return oclInvalid;
	}

	/**
	 * This will return the list of reserved keyword for this specific version of OCL.
	 * 
	 * @return The list of reserved keyword for this specific version of OCL.
	 */
	public static Set<String> getReservedKeywords() {
		if (reservedKeywords != null) {
			return reservedKeywords;
		}

		reservedKeywords = new CompactHashSet<String>();
		reservedKeywords.add("and"); //$NON-NLS-1$
		reservedKeywords.add("context"); //$NON-NLS-1$
		reservedKeywords.add("def"); //$NON-NLS-1$
		reservedKeywords.add("else"); //$NON-NLS-1$
		reservedKeywords.add("endif"); //$NON-NLS-1$
		reservedKeywords.add("endpackage"); //$NON-NLS-1$
		reservedKeywords.add("if"); //$NON-NLS-1$
		reservedKeywords.add("implies"); //$NON-NLS-1$
		reservedKeywords.add("in"); //$NON-NLS-1$
		reservedKeywords.add("inv"); //$NON-NLS-1$
		reservedKeywords.add("let"); //$NON-NLS-1$
		reservedKeywords.add("not"); //$NON-NLS-1$
		reservedKeywords.add("or"); //$NON-NLS-1$
		reservedKeywords.add("package"); //$NON-NLS-1$
		reservedKeywords.add("post"); //$NON-NLS-1$
		reservedKeywords.add("pre"); //$NON-NLS-1$
		reservedKeywords.add("then"); //$NON-NLS-1$
		reservedKeywords.add("xor"); //$NON-NLS-1$

		if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.HELIOS) {
			reservedKeywords.add("body"); //$NON-NLS-1$
			reservedKeywords.add("derive"); //$NON-NLS-1$
			reservedKeywords.add("init"); //$NON-NLS-1$
			reservedKeywords.add("static"); //$NON-NLS-1$
		} else {
			reservedKeywords.add("attr"); //$NON-NLS-1$
			reservedKeywords.add("oper"); //$NON-NLS-1$
		}

		return reservedKeywords;
	}
}
