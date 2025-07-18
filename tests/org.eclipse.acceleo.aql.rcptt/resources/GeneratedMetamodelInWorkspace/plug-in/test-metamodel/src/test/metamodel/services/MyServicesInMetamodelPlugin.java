package test.metamodel.services;

import test.metamodel.my.Root;

public class MyServicesInMetamodelPlugin {

	public String getMessageInMetamodelPlugin(Root root) {
		return "Hello " + root.getName();
	}

}
