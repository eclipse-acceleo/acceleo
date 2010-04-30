<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=


<%for (attribute.filter("Property")){%>
<%name%>
<%}%>

<%feature.filter("Class").name%>

=End=


<%script type="Package" name="getClass"%>
<%eAllContents().filter("Class").name%>