<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import services.StringServices
%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

Package name : <%getPackage%>

Path : <%toPath(getPackage)%>

=End=

<%script type="Class" name="getPackage"%>
fr.obeo.test

