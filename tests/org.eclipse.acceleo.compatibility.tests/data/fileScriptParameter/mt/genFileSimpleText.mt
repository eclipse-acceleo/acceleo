<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

<%testSimpleScript%>

<%testParameterScript("Param1", "Param2")%>

=End=

<%script type="Class" name="testSimpleScript"%>
Text of testSimpleScript

<%script type="Class" name="testParameterScript"%>
Text of testParameterScript with parameter 1 : <%args(0)%> and parameter 2 : <%args(1)%>