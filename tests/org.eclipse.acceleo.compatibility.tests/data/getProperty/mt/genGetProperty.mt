<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

<%getProperty("find")%>

<%getProperty(name + "find")%>

<%getProperty("propertyName","find")%>

<%getProperty("propertyName",name + "find")%>

=End=

<%script type="Operation" name="hasStereotype"%>
<%if (args(0) == "true"){%><%true%><%}else{%><%false%><%}%>
