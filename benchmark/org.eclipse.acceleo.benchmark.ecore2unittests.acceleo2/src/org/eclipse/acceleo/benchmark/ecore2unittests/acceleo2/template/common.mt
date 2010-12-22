<%
metamodel http://www.eclipse.org/emf/2002/GenModel
%>

<%script type="GenPackage" name="testPackage"%>
<%basePackage%>.tests

<%script type="GenClassifier" name="testPackage"%>
<%genPackage.testPackage%>

<%script type="GenPackage" name="modelPackage"%>
<%basePackage%>.<%prefix.toLowerCase()%><%if (interfacePackageSuffix != "") {%>.<%interfacePackageSuffix.toLowerCase()%><%}%>

<%script type="GenClassifier" name="modelPackage"%>
<%genPackage.modelPackage%>

<%script type="GenPackage" name="implementationPackage"%>
<%basePackage%>.<%prefix.toLowerCase()%><%if (classPackageSuffix != "") {%>.<%classPackageSuffix.toLowerCase()%><%}%>

<%script type="GenClassifier" name="implementationPackage"%>
<%genPackage.implementationPackage()%>

<%script type="GenPackage" name="modelAdapterFactoryClassName"%>
<%prefix%>AdapterFactory

<%script type="GenPackage" name="modelFactoryClassName"%>
<%prefix%>Factory

<%script type="GenPackage" name="modelSwitchClassName"%>
<%prefix%>Switch

<%script type="GenPackage" name="qualifiedModelFactoryClassName"%>
<%modelPackage%>.<%prefix%>Factory

<%script type="GenPackage" name="modelPackageClassName"%>
<%prefix%>Package

<%script type="GenPackage" name="qualifiedModelPackageClassName"%>
<%modelPackage%>.<%prefix%>Package
