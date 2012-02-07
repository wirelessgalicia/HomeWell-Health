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
<link rel="stylesheet" type="text/css" href="styles/style.css" media="screen" />
<script  type="text/javascript" src="js/jquery-1.3.1.min.js"></script> 
<script  type="text/javascript" src="js/scripts.js"></script>
<script  type="text/javascript" src="js/coda-slider.1.1.1.js"></script>
<script  type="text/javascript" src="js/jquery-easing-compatibility.1.2.pack.js"></script>
<script  type="text/javascript" src="js/jquery-easing.1.2.pack.js"></script>
</head>
<body>
<c:if test="${param['language'] != null}">
	<fmt:setLocale value="${param['language']}" scope="session" />
</c:if>
<fmt:setBundle basename="messages"/>
<fmt:message key="tab1" var="tab1" />
<fmt:message key="tab2" var="tab2" />
<fmt:message key="tab3" var="tab3" />	
<fmt:message key="img_menu" var="img_menu" />
<div id="main_container">
	<div id="header">
        <div id="menu" class="${img_menu}">
            <ul>                                              
                <li><a class="current" href="index.jsp" title=""><fmt:message key="index.inicio" /></a></li>
                <li class="divider"></li>
                <li><a href="osgi/pruebas.htm" title=""><fmt:message key="index.probas" /></a></li>
                <li class="divider"></li>
                <li><a href="osgi/historial.htm" title=""><fmt:message key="index.historial" /></a></li>
                <li class="divider"></li>
                <li><a href="osgi/control.htm" title=""><fmt:message key="index.control" /></a></li>
                <li class="divider"></li>
                <li><a href="http://www.sergas.es/MostrarContidos_N1_T02.aspx?IdPaxina=9&uri=https://extranet.sergas.es/cita/inicioCI.asp?idioma=ga&hifr=800&title=Cita%20atenci%F3n%20primaria"><fmt:message key="index.consulta" /></a></li>
            </ul>
        </div>
        <div >
        	<a class=idioma href="index.jsp?language=en_GB">
				<img src='images/flat_en.png'>
				<fmt:message key="idioma.en" />
			</a>
			<a class=idioma href="index.jsp?language=fr_FR">
				<img src='images/flat_gl.png'>
				<fmt:message key="idioma.gl" />
			</a>
			<a class=idioma href="index.jsp?language=es_ES">
				<img src='images/flat_es.png'>
				<fmt:message key="idioma.es" />
			</a>
        </div>
    </div>
	
    <div id="slider_content">

        <div id="slider">
           <div class="slider-wrap">
            <div id="sliderc" class="csw">
                <div class="panelContainer">
                    <div class="panel">
                        <div id="${tab1}" class="tab">
                        	<p class="tab_content"><fmt:message key="index.accesoproxactividad" /></p> 
                            <a href="osgi/pruebas.htm" class="read_more">
								<div class="enlace_tab"><fmt:message key="entrar" /></div>
                            </a>
                        </div>
                        <div id="${tab2}" class="tab">
                            <p class="tab_content"><fmt:message key="index.consultehistorico" /></p>
                            <a href="osgi/historial.htm" class="read_more">
								<div class="enlace_tab"><fmt:message key="entrar" /></div>
                            </a>
                        </div>                        
                        <div id="${tab3}" class="tab">
                            <p class="tab_content"><fmt:message key="index.solicitacita"/></p>
                            <a href="http://www.sergas.es/MostrarContidos_N1_T02.aspx?IdPaxina=9&uri=https://extranet.sergas.es/cita/inicioCI.asp?idioma=ga&hifr=800&title=Cita%20atenci%F3n%20primaria" class="read_more">
								<div class="enlace_tab"><fmt:message key="entrar" /></div>
							</a>
                        </div>                                
                    </div>        
                </div>
            </div>
        </div>
        </div>

		</div>
        <div class="clear"></div>
	</div> <!--end of slider content-->
    
    <div class="main_content_index">
    	<div class="left_content">
                <div class="title"><fmt:message key="index.benvido" /></div>
                <p><fmt:message key="index.titulo" />
				<br /><br />
				<fmt:message key="index.explicativo" />
                </p> 
        </div> <!--end of left content-->

    	<div class="right_content">
            
            <div class="title"><fmt:message key="index.tempoesaude" /></div>     
            <div class="project_box">  
				<div class="banner">
                		<img src="images/reloj.gif" alt="" title="" class="news_icon" />
                		<p class="banner_content">
                			<fmt:message key="index.explicativo2" />
                		</p>
            	</div>
            </div>
        </div> <!--end of right content-->
    
    <div class="clear"></div>
    </div>
    
    <div class="footer">
        <div class="right_footer">
			Copyright 2011. Web HomeWell-Health  
			<a href="index.jsp"><fmt:message key="index.inicio" /></a>  
			<a href="osgi/pruebas.htm"><fmt:message key="index.probas" /></a>
			<a href="osgi/historial.htm"><fmt:message key="index.historial" /></a>
			<a href="osgi/control.htm"><fmt:message key="index.control" /></a>
			<a href="http://www.sergas.es/MostrarContidos_N1_T02.aspx?IdPaxina=9&uri=https://extranet.sergas.es/cita/inicioCI.asp?idioma=ga&hifr=800&title=Cita%20atenci%F3n%20primaria">Consulta</a>
			<img src='images/logo_homewell-health.png' alt='Xunta de Galicia - Presidencia - Secretaria Xeral de Modernización e Innovación Tecnolóxica'>
        </div>
    </div>
    
    


</div>
</body>
</html>
