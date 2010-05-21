<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

import pck.lib.lib
import pck.genTestSelect

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

Launch script in same project : <%myScript%>

Launch script in other project : <%mySelectScript%>

=End=


