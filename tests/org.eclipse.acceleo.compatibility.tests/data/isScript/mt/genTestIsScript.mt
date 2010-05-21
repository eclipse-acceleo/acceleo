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

<%script type="Type" name="isJavaPrimitiveTypeSimple"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>true<%}else{%>false<%}%>

<%script type="Type" name="checkJavaPrimitiveTypeSimple"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>true<%}else{%>false<%}%>

<%script type="Type" name="isJavaPrimitiveTypeUnElseIf"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>true<%}else if(name == "Integer1" || name =="Float1" || name == "Boolean1"){%>false<%}else{%>false<%}%>

<%script type="Type" name="isJavaPrimitiveTypeTroisElseIF"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>true<%}else if(name == "Integer1" || name =="Float1" || name == "Boolean1"){%>false<%}else if(name == "Integer2" || name =="Float2" || name == "Boolean2"){%>false<%}else if(name == "Integer3" || name =="Float3" || name == "Boolean3"){%>false<%}else{%>false<%}%>

<%script type="Type" name="isJavaPrimitiveTypeSimpleMultiligne"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>
true<%}else{%>
false<%}%>

<%script type="Type" name="isJavaPrimitiveTypeUnElseIfMultiligne"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>
true<%}else if(name == "Integer1" || name =="Float1" || name == "Boolean1"){%>
false<%}else{%>
false<%}%>

<%script type="Type" name="isJavaPrimitiveTypeTroisElseIFMultiligne"%>
<%if (name == "Integer" || name =="Float" || name == "Boolean"){%>
true<%}else if(name == "Integer1" || name =="Float1" || name == "Boolean1"){%>
false<%}else if(name == "Integer2" || name =="Float2" || name == "Boolean2"){%>
false<%}else if(name == "Integer3" || name =="Float3" || name == "Boolean3"){%>
false<%}else{%>
false<%}%>
