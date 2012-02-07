<!--
  This file is part of HomeWell-Health
 
  Copyright (C) 2011-2012 WirelessGalicia S.L.
 
  The contents of this file are subject to the Mozilla Public License
  Version 1.1 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.mozilla.org/MPL/
  
  Software distributed under the License is distributed on an "AS IS"
  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  License for the specific language governing rights and limitations
  under the License.
-->
<%@page pageEncoding="iso-8859-1" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>HomeWell-Health Web Demo</title>
<link rel="stylesheet" type="text/css" href="../styles/style.css" media="screen" />
<script  type="text/javascript" src="../js/jquery-1.3.1.min.js"></script> 
<script  type="text/javascript" src="../js/scripts.js"></script>
<script  type="text/javascript" src="../js/coda-slider.1.1.1.js"></script>
<script  type="text/javascript" src="../js/jquery-easing-compatibility.1.2.pack.js"></script>
<script  type="text/javascript" src="../js/jquery-easing.1.2.pack.js"></script>
</head>
<body>
<fmt:message key="img_menu" var="img_menu" />
<div id="main_container">
	<div id="header">
        <div id="menu" class="${img_menu}">
            <ul>                                              
                <li><a class="current" href="../index.jsp" title=""><fmt:message key="index.inicio" /></a></li>
                <li class="divider"></li>
                <li><a href="pruebas.htm" title=""><fmt:message key="index.probas" /></a></li>
                <li class="divider"></li>
                <li><a href="historial.htm" title=""><fmt:message key="index.historial" /></a></li>
                <li class="divider"></li>
                <li><a href="control.htm" title=""><fmt:message key="index.control" /></a></li>
                <li class="divider"></li>
                <li><a href="http://www.sergas.es/MostrarContidos_N1_T02.aspx?IdPaxina=9&uri=https://extranet.sergas.es/cita/inicioCI.asp?idioma=ga&hifr=800&title=Cita%20atenci%F3n%20primaria"><fmt:message key="index.consulta" /></a></li>
            </ul>
        </div>
    </div>
     <div class="main_content">
        <div class="title_siguiente_prueba"><fmt:message key="pruebasiguientejsp.seguinte" /></div>
     	<div id="result_container">
    		<div class="col1">
				<a id="link_iniciar" href="ejecucionFase.htm" class="button">
					<span class='${class_link}'}><fmt:message key="pruebasiguientejsp.iniciar" /></span>
				</a>
        	</div> <!--end of left content-->

    		<div id="des_prueba" class="col_desc">
    			<div class="col1">
            		<div class="project_title"><fmt:message key="pruebasiguientejsp.descripcion" /></div>
            	</div>	  
            	<div class="col1">
            		<p align="left">${descripcion}</p>
            	</div>
                <br></br>
            </div>
            <div id="des_prueba" class="col_desc">
                <div class="col1">
                	<div class="project_title"><fmt:message key="pruebasiguientejsp.datainicio" /></div>
                </div>  
            	<div class="col1">
            		<p>${dia}</p>
            	</div>
                <br></br>
           </div>
           <div id="des_prueba" class="col_desc">
                <div class="col1">
                	<div class="project_title"><fmt:message key="pruebasiguientejsp.horainicio" /></div>
                </div>	  
            	<div class="col1">
            		<p>${hora}</p>
            	</div>                
           </div>
        	
    		<div class="clear"></div>
    	</div>
    </div>
    <div class="main_content">
    	<div class="left_content">
              
                
        </div> <!--end of left content-->

    	<div class="right_content">
                      
        </div> <!--end of right content-->
    
    <div class="clear"></div>
    </div>
    
    <div class="footer">
    	<div class="right_footer">
			Copyright 2011. Web HomeWell-Health  
			<a href="../index.jsp"><fmt:message key="index.inicio" /></a>  
			<a href="pruebas.htm"><fmt:message key="index.probas" /></a>
			<a href="historial.htm"><fmt:message key="index.historial" /></a>
			<a href="control.htm"><fmt:message key="index.control" /></a>
			<a href="http://www.sergas.es/MostrarContidos_N1_T02.aspx?IdPaxina=9&uri=https://extranet.sergas.es/cita/inicioCI.asp?idioma=ga&hifr=800&title=Cita%20atenci%F3n%20primaria"><fmt:message key="index.consulta" /></a>
			<img src='../images/logo_homewell-health.png' alt='Xunta de Galicia - Presidencia - Secretaria Xeral de Modernización e Innovación Tecnolóxica'>
        </div>
    </div>
    
    


</div>
</body>
</html>
