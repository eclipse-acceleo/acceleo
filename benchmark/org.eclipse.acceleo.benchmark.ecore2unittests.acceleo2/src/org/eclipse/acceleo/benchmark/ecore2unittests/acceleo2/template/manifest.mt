<%
metamodel http://www.eclipse.org/emf/2002/GenModel
%>

<%script type="GenModel" name="manifest" file="META-INF/MANIFEST.MF"%>
Manifest-Version: 1.0
Bundle-ManifestVersion: 2
Bundle-Name: <%modelName%> Tests
Bundle-SymbolicName: <%modelPluginID%>.test
Bundle-Version: 1.0.0
Bundle-RequiredExecutionEnvironment: J2SE-1.5
Require-Bundle: <%modelPluginID%>,
 org.junit;bundle-version="3.8.2",
 org.eclipse.emf.common;bundle-version="2.3.2",
 org.eclipse.emf.ecore;bundle-version="2.3.2"
