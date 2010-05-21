<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

List size : <%name.nSize()%>

Nb char in classe name : <%name.length()%>

Nb attribute : <%attribute.nSize()%>


Nb operation : <%ownedOperation.nSize()%>

