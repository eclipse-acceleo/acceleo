<%
metamodel http://www.eclipse.org/emf/2002/Ecore
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.services.CrossReferencerService
%>

<%script type="EClass" name="modelFactoryClassName"%>
<%eInverse("GenClass").nFirst().genPackage.modelFactoryClassName()%>

<%script type="EClass" name="qualifiedModelFactoryClassName"%>
<%if (ePackage.nsURI == "http://www.eclipse.org/emf/2002/Ecore") {%>org.eclipse.emf.ecore.EcoreFactory<%}else{%><%eInverse("GenClass").nFirst.genPackage.qualifiedModelFactoryClassName%><%}%>

<%script type="EClass" name="modelPackageClassName"%>
<%eInverse("GenClass").nFirst().genPackage.modelPackageClassName()%>

<%script type="EClass" name="qualifiedModelPackageClassName"%>
<%if (ePackage.nsURI == "http://www.eclipse.org/emf/2002/Ecore") {%>org.eclipse.emf.ecore.EcorePackage<%}else{%><%eInverse("GenClass").nFirst.genPackage.qualifiedModelPackageClassName%><%}%>

<%script type="EClassifier" name="qualifiedGeneratedName"%>
<%if (ePackage.nsURI == "http://www.eclipse.org/emf/2002/Ecore") {%>org.eclipse.emf.ecore.<%name%><%}else{%><%eInverse("GenClassifier").nFirst().modelPackage%>.<%name%><%}%>

<%script type="EClassifier" name="qualifiedGeneratedImplementation"%>
<%if (ePackage.nsURI == "http://www.eclipse.org/emf/2002/Ecore") {%>org.eclipse.emf.ecore.impl.<%name%>Impl<%}else{%><%eInverse("GenClassifier").nFirst().implementationPackage%>.<%name%>Impl<%}%>

<%script type="EClass" name="instantiatableSubClass"%>
<%if (!abstract) {%><%current%><%}else{%><%eInverse("EClass")[!abstract && eSuperTypes.nContains(current(1))].nFirst()%><%}%>

<%script type="EClassifier" name="isPrimitive"%>
<%name.matches("EBoolean|EByte|EChar|EDouble|EFloat|EInt|ELong|EShort")%>
