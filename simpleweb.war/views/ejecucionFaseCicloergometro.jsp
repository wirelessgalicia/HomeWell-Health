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
<fmt:message key="ejecfasebasculajs.cancelada" var="cancelada" />
<fmt:message key="ejecfasebasculajs.atencion" var="atencion" />
<fmt:message key="ejecfasebasculajs.rematoufase" var="rematoufase" />
<fmt:message key="ejecfasebasculajs.rematouproba" var="rematouproba" />
<fmt:message key="ejecfasebasculajs.probadetida" var="probadetida" />
<fmt:message key="send.historial.error" var="send_historial_error" />
<fmt:message key="send.historial.conexion" var="send_historial_conexion" />
<fmt:message key="send.historial.ok" var="send_historial_ok" />
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
	<script  type="text/javascript" src="../js/jquery.blockUI.js"></script>
	<script language="javascript" type="application/javascript" src="../js/myjs/ejecucionFaseCicloergometro.js"></script>

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
                <div class="title"><fmt:message key="ejecFasejsp.fase" /> ${orden} : ${descripcion} </div>
					<div id="result_container">			
						<div class="col1">			
							<p>
								<fmt:message key="ejecPruebajsp.iniciouse" />
								<br /><br />
							</p>
							<p>
								<fmt:message key="ejecFasejsp.duracion" /> : ${duracion}
								<br /><br />
								<fmt:message key="ejecFasejsp.situacion" /> : ${sitpac}
							</p> 
						</div>	
						<div class="col1">		
					    	<div class="colLarge">
					    		<a id="btStop" class="colRight" href="#" >
                            		<div class="enlace_parar"><fmt:message key="ejecFasejsp.parar" /></div>
								</a>
                        	</div>
                        </div>
						<div class="col1">
							<div class="font_grafica">
								<div id="chartRpm" style="width:600px;height:300px;"></div>
							</div>
							<!-- <p>
								Tiempo entre actualizaciones: 
								<input id="updateInterval" type="text" value="" style="text-align: right; width:5em"> 
								milliseconds
							</p>
							 -->
						</div>
				
						<div class="col2">
							<table id="t_rpm"></table> 
							<div id="pagerRpm10"></div>
							<div class="legend_x"><fmt:message key="milisegundos" /></div>
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
<SCRIPT LANGUAGE="JavaScript">
var cancelada = "${cancelada}"
var atencion = "${atencion}"
var rematoufase = "${rematoufase}"
var rematouproba = "${rematouproba}"
var probadetida = "${probadetida}"
var send_historial_error = "${send_historial_error}"
var send_historial_conexion = "${send_historial_conexion}"
var send_historial_ok = "${send_historial_ok}"
</SCRIPT>
</html>

