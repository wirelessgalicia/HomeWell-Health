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
	<link rel="stylesheet" type="text/css" href="../js/css/redmond/jquery-ui-1.8.16.custom.css" />
	<link rel="stylesheet" type="text/css" href="../js/css/ui.jqgrid.css" />
	<link rel="stylesheet" href="../js/css/jScrollbar.jquery.css" type="text/css" />

    <script language="javascript" type="application/javascript" src="../js/jquery.js"></script>
    <script language="javascript" type="application/javascript" src="../js/jquery-1.6.2.min.js"></script>
  
    <script language="javascript" type="application/javascript" src="../js/jquery.flot.js"></script>

    <script language="javascript" type="application/javascript" src="../js/jquery-ui-1.8.16.custom.min.js"></script>
    <script language="javascript" type="application/javascript" src="../js/i18n/grid.locale-en.js"></script>
    <script language="javascript" type="application/javascript" src="../js/jquery.jqGrid.min.js"></script>

    <script type="text/javascript" src="../js/jquery-ui.js"></script>        
	<script type="text/javascript" src="../js/jquery-mousewheel.js"></script>
	<script type="text/javascript" src="../js/jScrollbar.jquery.js"></script>
	
	<script language="javascript" type="application/javascript" src="../js/myjs/ejecucionPrueba.js"></script>

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
    	<div class="wide_content">			
                <div class="title"><fmt:message key="ejecPruebajsp.pendentes" />Probas pendentes de realizar</div>
					<div id="result_container">			
						<p>
						<fmt:message key="ejecPruebajsp.iniciouse" />
						<br /><br />
						</p> 
						<div id="alarmas_container" class="alarmas_container_hidden">
							<div class="title"><fmt:message key="ejecFasejsp.eventos" /></div>
							<p>
							<div id="scroll_container">
							<ul id="scroller" class="ul_event">
									<div class="jScrollbar5">
										<div class="jScrollbar_mask">							
									</div>
									<div class="jScrollbar_draggable">
										<a href="#" class="draggable"></a>
									</div>
									<div class="clr"></div>
								</div>
							</ul>
							</div>
							</p>
					   </div>
					   <div>
						<a id="btView" class="read_more" href="#">
							<div class="enlace_tab"><fmt:message key="ejecFasejsp.inciar" /></div>
                        </a>
                        <a id="btStop" class="read_more" href="#">
							<div class="enlace_tab">parar</div>
                        </a>
                        </div>
						<div class="col1">
							<div id="placeholder" style="width:600px;height:300px;"></div>
							<p>
								<fmt:message key="ejecFasejsp.intervalo.actualizacion" /> :
								<input id="updateInterval" type="text" value="" style="text-align: right; width:5em"> 
								<fmt:message key="milisegundos" />
							</p>
						</div>
				
						<div class="col2">
							<table id="t_pulso"></table> 
							<div id="pagerPul10"></div>
						</div>
    			
						<div class="col3">
							<p>
								<div id="chartSat" style="width:600px;height:300px;"></div>
							</p>
							</div>
							<div class="col4">
								<table id="t_sat"></table> 
								<div id="pagerSat10"></div>
							</div>
                </div><!--end of result content-->
        </div> <!--end of wide content-->

    
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

