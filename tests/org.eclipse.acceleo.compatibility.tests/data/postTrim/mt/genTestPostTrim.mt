<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

<%testSimpleScript1%>

<%testParameterScript1("Param1", "Param2")%>

<%testSimpleScript2%>

<%testParameterScript2("Param1", "Param2")%>
=End=

<%script type="Class" name="testSimpleScript1" post="trim"%>
Text of testSimpleScript



<%script type="Class" name="testParameterScript1" post="trim"%>
Text of testParameterScript with parameter 1 : <%args(0)%> and parameter 2 : <%args(1)%>


			


<%script type="Class" name="testSimpleScript2" post="trim()"%>
Text of testSimpleScript

			
	


<%script type="Class" name="testParameterScript2" post="trim()"%>
Text of testParameterScript with parameter 1 : <%args(0)%> and parameter 2 : <%args(1)%>