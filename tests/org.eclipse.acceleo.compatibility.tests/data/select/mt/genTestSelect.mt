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

<%for (attribute.select("name.startsWith('a')")){%>
<%name%>
<%}%>

<%attribute[name == "aName"].sep(",")%>

<%attribute.select("name == 'aName'").sep(",")%>


<%if (attribute.select("upper == -1 || upper > 1").nSize() > 0){%>
import java.util.Collection;
import java.util.HashSet;
<%}%>

<%for (ownedOperation.select("!hasStereotype(getProperty('find'))")){%>	
	<%name%>
<%}%>

<%for (ownedOperation[!hasStereotype(getProperty("find"))]){%>	
	<%name%>
<%}%>

=End=

<%script type="Operation" name="hasStereotype"%>
<%if (args(0) == "true"){%><%true%><%}else{%><%false%><%}%>

<%script type="Class" name="mySelectScript"%>
MySelectScript Text
