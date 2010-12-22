package org.eclipse.acceleo.benchmark.ecore2unittests.xpand.services;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

public class MetamodelUtil {
	public EClass getEcoreClass(GenClass clazz) {
		return clazz.getEcoreClass();
	}
	
	public EEnum getEcoreEnum(GenEnum enumeration) {
		return enumeration.getEcoreEnum();
	}
	
	public EPackage getEcorePackage(GenPackage packaje) {
		return packaje.getEcorePackage();
	}
}
