<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

<%for (attribute[name.startsWith("a")]){%>
<%name%>
<%}%>

<%for (attribute[name == "aName"]){%>
<%name%>
<%}%>

<%for (attribute.select(name.startsWith("a"))){%>
<%name%>
<%}%>

<%attribute[name == "aName"].sep(",")%>

<%attribute.select(name == "aName").sep(",")%>


=End=
