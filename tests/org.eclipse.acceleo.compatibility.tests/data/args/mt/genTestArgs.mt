<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

<%argScript1("arg0", "arg1")%>

<%argScript2("arg0", "arg1")%>

<%argScript3("arg0", "arg1", "arg2")%>

<%argScript4("arg0", "arg1")%>

=End=

<%script type="Class" name="argScript1"%>
arg 0: <%args(0)%> / arg 1: <%args(1)%>

<%script type="Class" name="argScript2"%>
arg 1: <%args(1)%> / arg 0: <%args(0)%>

<%script type="Class" name="argScript3"%>
arg 0: <%args(0)%> / arg 1: <%args(1)%> / arg 2: <%args(2)%>

<%script type="Class" name="argScript4"%>
first arg : <%args(0)%>  second arg : <%args(one)%>

<%script type="Class" name="one"%>
1
