<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

getRootContainer : <%getRootContainer().eContents().nSize()%>

precedingSibling : <%precedingSibling().nSize()%>

followingSibling : <%followingSibling().nSize()%>

eAllContents : <%eAllContents().nSize()%>

eAllContents("type") + nLast + nFirst : <%eAllContents("Operation").nLast().name%> / <%eAllContents("Operation").nFirst().name%>

eContainer : <%eContainer().nSize()%>

eContainer("Type") : <%eContainer("Package").name%>

ancestor : <%ancestor().nSize()%>




