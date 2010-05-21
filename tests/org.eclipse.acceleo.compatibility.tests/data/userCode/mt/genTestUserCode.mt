<%
metamodel http://www.eclipse.org/uml2/2.0.0/UML

%> 
<%--Test Simple de script .mt Acceleo  --%>

<%script type="Class" name="generate" file="test.txt"%>
== Sample of text ==

=Start=

<%startUserCode%> UCText1
<%scriptText%>
<%endUserCode%>


<%startUserCode%> UCText2 UCText2
<%scriptText%>
<%endUserCode%>

<%startUserCode%> UCText3 UCText3 UCText3
<%scriptText%>
<%endUserCode%>

<%startUserCode%> <%ucScript1%>
<%scriptText%>
<%endUserCode%>

<%startUserCode%> UCText1  <%ucScript1%>
<%scriptText%>
<%endUserCode%>

<%startUserCode%> <%ucScript1%> <%ucScript1%>
<%scriptText%>
<%endUserCode%>

<%startUserCode%> <%ucScript1%> <%ucScript2%>
<%scriptText%>
<%endUserCode%>

//<%startUserCode%> Comment user code
//<%endUserCode%> Comment user code

// <%startUserCode%> TextStartUC <%name%> TextEndUC
 
// <%endUserCode%> TextStartUC <%name%> TextEndUC

=End=

<%script type="Class" name="ucScript1"%>
UCScript1

<%script type="Class" name="ucScript2"%>
UCScript2

<%script type="Class" name="scriptText"%>
blaBlaBlaBlaBlaBlaBla
BlaBlaBlaBlaBlaBlaBlaBlaBla
...
