<%
metamodel http://www.eclipse.org/emf/2002/Ecore
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.commonEcore
%>

<%script type="EStructuralFeature" name="getterPrefix"%>
<%if (eType.name == "EBoolean") {%>is<%}else{%>get<%}%>

<%script type="EClass" name="valueGetter"%>
<%if (args(0).eType.isPrimitive()) {%><%current.primitiveValueGetter(args(0))%><%}else{%><%name.toL1Case()%>.get<%args(0).name.toU1Case()%>()<%}%>

<%script type="EClass" name="primitiveValueGetter"%>
<%if (args(0).eType.instanceClassName == "int") {%>((Integer)<%name.toL1Case()%>.<%args(0).getterPrefix()%><%args(0).name.toU1Case()%>()).intValue()<%}else{%><%if (args(0).eType.instanceClassName == "char") {%>((Character)<%name.toL1Case()%>.<%args(0).getterPrefix()%><%args(0).name.toU1Case()%>()).charValue()<%}else{%>((<%args(0).eType.instanceClassName.toU1Case()%>)<%name.toL1Case()%>.<%args(0).getterPrefix()%><%args(0).name.toU1Case()%>()).<%args(0).eType.instanceClassName%>Value()<%}%><%}%>

<%script type="EStructuralFeature" name="defaultValueGetter"%>
<%if (eType.isPrimitive()) {%><%primitiveDefaultValueGetter()%><%}else{%>feature.getDefaultValue()<%}%>

<%script type="EStructuralFeature" name="primitiveDefaultValueGetter"%>
<%if (eType.instanceClassName == "int") {%>((Integer)feature.getDefaultValue()).intValue()<%}else{%><%if (eType.instanceClassName == "char") {%>((Character)feature.getDefaultValue()).charValue()<%}else{%>((<%eType.instanceClassName.toU1Case()%>)feature.getDefaultValue()).<%eType.instanceClassName%>Value()<%}%><%}%>

<%script type="EClass" name="reflectiveGetter"%>
<%if (args(0).eType.isPrimitive()) {%><%primitiveReflectiveGetter(args(0))%><%}else{%><%name.toL1Case()%>.eGet(feature)<%}%>

<%script type="EClass" name="reflectiveBasicGetter"%>
<%if (args(0).eType.isPrimitive()) {%><%primitiveReflectiveBasicGetter(args(0))%><%}else{%><%name.toL1Case()%>.eGet(feature, false)<%}%>

<%script type="EClass" name="primitiveReflectiveGetter"%>
<%if (args(0).eType.instanceClassName == "int") {%>((Integer)<%name.toL1Case()%>.eGet(feature)).intValue()<%}else{%><%if (args(0).eType.instanceClassName == "char") {%>((Character)<%name.toL1Case()%>.eGet(feature)).charValue()<%}else{%>((<%args(0).eType.instanceClassName.toU1Case()%>)<%name.toL1Case()%>.eGet(feature)).<%args(0).eType.instanceClassName%>Value()<%}%><%}%>

<%script type="EClass" name="primitiveReflectiveBasicGetter"%>
<%if (args(0).eType.instanceClassName == "int") {%>((Integer)<%name.toL1Case()%>.eGet(feature, false)).intValue()<%}else{%><%if (args(0).eType.instanceClassName == "char") {%>((Character)<%name.toL1Case()%>.eGet(feature, false)).charValue()<%}else{%>((<%args(0).eType.instanceClassName.toUpperFirst()%>)<%name.toL1Case()%>.eGet(feature, false)).<%args(0).eType.instanceClassName%>Value()<%}%><%}%>
