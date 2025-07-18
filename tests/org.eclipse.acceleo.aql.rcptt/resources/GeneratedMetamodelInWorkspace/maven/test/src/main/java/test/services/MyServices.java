package test.services;

import test.metamodel.my.Root;

public class MyServices {

	public String getMessage(Root root) {
		return "Hello " + root.getName();
	}

}
