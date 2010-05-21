<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

Total method and properties : <%eAllContents("Property").nSize().adapt('int') + eAllContents("Operation").nSize().adapt('int')%>

Boolean : <%getProperty("Test").adapt("boolean")%>

Boolean : <%getProperty("Test").adapt("boolean")%>

=End=

<%script type="Class" name="hasOneOperation" post="adapt("boolean")"%>
<%if (eAllContents("Property").nSize() == 1){%>
	true
<%}else{%>
	false
<%}%>
