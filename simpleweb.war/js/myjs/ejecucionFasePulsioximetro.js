/*
 * This file is part of HomeWell-Health
 *
 * Copyright (C) 2011-2012 WirelessGalicia S.L.
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 */
$(function () {
    // we use an inline data source in the example, usually data would
    // be fetched from a server
    var dataPlot = [];
    var totalPoints = 1200;
    var stop = 0;
    var viewData = [];
    var alarmas = [];
    var pulso = [];
    var sat = [];
   

    /*---------------------------------------
     * ---------- Opciones Grafica ----------
     * --------------------------------------
     */
    var options = {
        series: { shadowSize: 0 }, // drawing is faster without shadows
        lines: { show: true },
        //points: { show: true },
        label: "y = pulos",
        label: "x = tiempo (segundos)",
        yaxis: { min: 50, max: 150 },
        //xaxis: { tickDecimals: 0, tickSize: 1 }
        xaxis: { min: 0, max: 60000 }
    };
    
    /*---------------------------------------
     * ---------- Grafica Pulso -------------
     * --------------------------------------
     */
    var plotPulso = $.plot($("#placeholder"), [ {label: 'pulso', data: dataPlot} ], options);


    /*------------------------------------------
     * ---------- Grafica Saturacion -----------
     * -----------------------------------------
     */
    var plotSat = $.plot($("#chartSat"), [ {label: 'saturación', data:dataPlot} ], options);

    
    function onAllDataReceived(view) {
    	 viewData = view;
    	 var t_pulso = [];
 	 	 var t_sat = [];
 	 	 if(viewData != null && viewData.resultados != null){
 	 		if(viewData.resultados.Pulso != null){
 	 			t_pulso = viewData.resultados.Pulso;
 	 		}
 	 		if(viewData.resultados.Saturacion != null){
 	 			t_sat = viewData.resultados.Saturacion;
 	 		}
 	 	 }

    	 var pos = 0
    	 for (var i = 0; i < t_pulso.length; ++i){
    		 pos = pos + (t_pulso[i].diferencia*50);
    		 pulso.push([pos,t_pulso[i].valor]);
    	 }
		 
    	 pos = 0
    	 for (var i = 0; i < t_sat.length; ++i){
    		 pos = pos + (t_sat[i].diferencia*50);
    		 sat.push([pos,t_sat[i].valor]);
    	 }
    }

    function onNewDataReceived(view) {
    	viewData = view;
    	var t_pulso = [];
	 	var t_sat = [];
	 	if(viewData != null && viewData.resultados != null){
	 		if(viewData.resultados.Pulso != null){
	 			t_pulso = viewData.resultados.Pulso;
	 		}
	 		if(viewData.resultados.Saturacion != null){
	 			t_sat = viewData.resultados.Saturacion;
	 		}
	 	}

 		 //une todos los puntos del pulso con los nuevos datos
   	 	if(t_pulso.length >= totalPoints){
   	 		t_pulso = t_pulso.slice(-totalPoints);
   	 	}
   	 	
   	 	if(pulso == null){
   	 		pulso = [];
   	 	}else{
   	 		var ptoAnteriores = totalPoints - t_pulso.length;
   	 		pulso = pulso.slice(-ptoAnteriores);
   	 	}

   	 	var pos = 0;
   	 	var pos_primero = 0;
   	 	if( pulso.length > 0 ){
   	 		pos_primero = pulso[0][0];
   	 	}
   	 	for(var i= 0 ; i < pulso.length ; i++){
   	 		pos = pulso[i][0] - pos_primero;
   	 		pulso[i][0] = pos;
   	 	}
   	 	for (var i = 0; i < t_pulso.length; ++i){
    		 pos = pos + (t_pulso[i].diferencia*50);
    		 pulso.push([pos,t_pulso[i].valor]);
   	 	}
   	 	

    	 //une todos los puntos de la saturacion con los nuevos datos
    	 if(t_sat.length >= totalPoints){
    		 t_sat = t_sat.slice(-totalPoints);
    	 }
    	 if(sat == null){
    	 		sat = [];
    	 }else{
    		 ptoAnteriores = totalPoints - t_sat.length;
    		 sat = sat.slice(-ptoAnteriores);
    	 }
    	 
 		 pos = 0;
 		 pos_primero = 0;
 		 if(sat.length > 0 ){
 			 pos_primero = sat[0][0];
 		 }
		 for(var i= 0 ; i < sat.length ; i++){
			 pos = sat[i][0] - pos_primero;
			 sat[i][0] = pos;
		 }
     	 for (var i = 0; i < t_sat.length; ++i){
     		 pos = pos + (t_sat[i].diferencia*50);
     		 sat.push([pos,t_sat[i].valor]);
     	 }
   }
 
   function getAllData() {   
       var res = [];
       pulso = [];
       sat = [];

       $.ajax({
           url: "allViewResults",
           method: 'GET',
           dataType: 'json',
           success: onAllDataReceived
    	});
       
       res.push(pulso);
       res.push(sat);
       return res;
    }

    function getNewData() {
    	var res = [];
        $.ajax({
            url: "newViewResults",
            method: 'GET',
            dataType: 'json',
            success: onNewDataReceived
     	});
        
        res.push(pulso);
        res.push(sat);
        return res;
     }

    // setup control widget
    var updateInterval = 50;
//    $("#updateInterval").val(updateInterval).change(function () {
//        var v = $(this).val();
//        if (v && !isNaN(+v)) {
//            updateInterval = +v;
//            if (updateInterval < 1)
//                updateInterval = 1;
//            if (updateInterval > 50)
//                updateInterval = 50;
//            $(this).val("" + updateInterval);
//        }
//    });

    
    function update() {
        // Actualizar con nuevos datos
        updateView(getNewData());
    }
    
    function updateView(resultado){
    	plotPulso.setData([ resultado[0] ]);
        plotPulso.draw();
        
        //actualizar sat
        plotSat.setData([ resultado[1] ]);
        plotSat.draw();
        
        //actualiza tabla pulso
        updateTablas();
        
        //actualizar alarmas
        if(viewData != null && viewData.alarmas != null){
        	alarmas = viewData.alarmas;
    	}
        if(alarmas != null){
        	for(var i = 0; i < alarmas.length ; ++i){
   		 		showAlarmas(alarmas[i]);
   	 		}
        	alarmas = [];
        }

        if(stop == 0){
        	setTimeout(update, updateInterval);
        }
    }
    
    function updateTablas(){
    	var t_pulso = [];
	 	var t_sat = [];
	 	if(viewData != null && viewData.resultados != null){
	 		if(viewData.resultados.Pulso != null){
	 			t_pulso = viewData.resultados.Pulso;
	 		}
	 		if(viewData.resultados.Saturacion != null){
	 			t_sat = viewData.resultados.Saturacion;
	 		}
	 	}

    	$("#t_pulso").addRowData("formattedFecha", t_pulso, "first");
    	$("#t_pulso").trigger('reloadGrid');
    	$("#t_sat").addRowData("formattedFecha", t_sat, "first");
    	$("#t_sat").trigger('reloadGrid');
    }
    
    function init(){
    	// Inicializar con todos los datos
    	stop = 0;
    	processPrueba();
    	updateView(getAllData());
    }

    /**
     * --------------------------------------
     * ------------ Tabla Pulso -------------
     * --------------------------------------
     */
    var dataPulso = [];
    
    $("#t_pulso").jqGrid({
    	data: dataPulso, 
    	datatype: "local",
    	colNames:['Hora','Pulso'], 
    	colModel:[ {name:'formattedFecha', width:100},  
    	           {name:'valor', width:100}
    	         ], 
    	rowNum:10, 
    	rowList:[10,20,30], 
    	pager: '#pagerPul10',
    	sortname: 'formattedFecha',
    	viewrecords: true, 
    	sortorder: "desc", 
    	multiselect: false, 
    	caption: "Tabla Pulso"
    }); 

    $("#t_pulso").jqGrid('navGrid','#pagerPul10',{
    	add:false,
    	edit:false,
    	del:false
    }); 

    
    /**
     * --------------------------------------
     * ------------ Tabla Sat -------------
     * --------------------------------------
     */
    var dataSat = [];

    $("#t_sat").jqGrid({ 
    	data: dataSat, 
    	datatype: "local",
    	colNames:['Hora','Saturacion'], 
    	colModel:[ {name:'formattedFecha', width:100},  
    	           {name:'valor', width:100}
    	         ],
    	rowNum:10, 
    	rowList:[10,20,30], 
    	pager: '#pagerSat10',
    	sortname: 'formattedFecha',
    	viewrecords: true, 
    	sortorder: "desc",
    	multiselect: false, 
    	caption: "Tabla Saturacion"
    }); 

    $("#t_sat").jqGrid('navGrid','#pagerSat10',{
    	add:false,
    	edit:false,
    	del:false
    }); 

    // --------------------Funciones para enviar los resultados al servidor principal ------------------
    function showHistorialResult(image, message){
 	   $.blockUI({ 
 	   		message: '<h1><img src="'+image+'" alt="ok" /> '+message+'</h1>',
 	   		fadeIn: 1000, 
 	           fadeOut: 1000, 
 	           showOverlay: false, 
 	           css: { 
 	           	width: '550px',
 	               border: 'none',
 	               backgroundColor: '#000', 
 	               '-webkit-border-radius': '10px', 
 	               '-moz-border-radius': '10px',
 	               color: '#fff',
 	               top:    '20%',
 	               left:   '15%'
 	           } 
 	   	});
 	   
 	  setTimeout(function() { 
          $.unblockUI({ 
              onUnblock: function(){
            	  window.location.href = '../index.jsp';
              } 
          }); 
      }, 20000);
    }
    
    function resultSendHistorialFailed(){
 	   var messageResultHistorial = send_historial_conexion;
 	   var url_image = '../images/warning.png';
 	   showHistorialResult(url_image, messageResultHistorial);
    }
    
    function resultSendHistorialOk(result){
    	if(result.ok < 2){
    		if(result == 0){
    			var messageResultHistorial = send_historial_error;
    			var url_image = '../images/warning.png';
    			showHistorialResult(url_image, result.mensaje);
    		}else{
    			var messageResultHistorial = send_historial_ok;
    			var url_image = '../images/Ok.png';
    			showHistorialResult(url_image, messageResultHistorial);
    		}
    	}else{
    		window.location.href = '../index.jsp';
    	}
    }
    
    function sendHistorial() {   
        $.ajax({
     	    url: "send_json",
            method: 'GET',
            dataType: 'json',
            success: resultSendHistorialOk,
            error: resultSendHistorialFailed
     	});
     }

    /**
     * ---------------------------------------
     * -------------- Alarmas ----------------
     * ---------------------------------------
     */
    
    function showAlarmas(data){
    	stop = 1;
    	var salir = 1;
    	var message = cancelada;
    	var url_image = '../images/Ok.png';

    	if(data == "PACIENTE_DEBE_PARAR_PRUEBA"){
    		message = atencion;
    		url_image = '../images/warning.png';
    		salir = 0;
    	}
    	if(data == "FASE_FINALIZADA"){
    		message = rematoufase;
    		salir = 1;
    	}
    	if(data == "PRUEBA_FINALIZADA"){
    		message = rematouproba;
    		salir = 0;
    	}
    	showBoxInfo(url_image, message, salir); 
    }
    
    
    function showBoxInfo(url_image, message,salir){
    	$.blockUI({ 
    		message: '<h1><img src="'+url_image+'" alt="ok" /> '+message+'</h1>',
    		fadeIn: 1000, 
            fadeOut: 1000, 
            showOverlay: false, 
            css: { 
            	width: '550px',
                border: 'none',
                backgroundColor: '#000', 
                '-webkit-border-radius': '10px', 
                '-moz-border-radius': '10px',
                color: '#fff',
                top:    '20%',
                left:   '15%'
            } 
    	}); 
    	
    	setTimeout(function() { 
            $.unblockUI({ 
                onUnblock: function(){
                	if(salir == 1){
                		window.location.href = '../index.jsp';
                	}else{
                		sendHistorial();
                	}
                } 
            }); 
        }, 20000);
    }
    
    
    /**
     * ---------------------------------------
     * -------------- Alarmas ----------------
     * ---------------------------------------
     */
    
//    function showAlarmas(data){
//    	stop = 1;
//    	var url = '../index.jsp';
//    	var message = cancelada;
//    	var url_image = '../images/Ok.png';
//
//    	if(data == "PACIENTE_DEBE_PARAR_PRUEBA"){
//    		message = atencion;
//    		url_image = '../images/warning.png';
//    		sendHistorial();
//    	}
//    	if(data == "FASE_FINALIZADA"){
//    		message = rematoufase;
//    		url='ejecucionFase.htm';
//    	}
//    	if(data == "PRUEBA_FINALIZADA"){
//    		message = rematouproba;
//    		sendHistorial();
//    	}
//    	
//    	
//    	$.blockUI({ 
//    		message: '<h1><img src="'+url_image+'" alt="ok" /> '+message+'</h1>',
//    		fadeIn: 1000, 
//            fadeOut: 1000, 
//            showOverlay: false, 
//            css: { 
//            	width: '550px',
//                border: 'none',
//                backgroundColor: '#000', 
//                '-webkit-border-radius': '10px', 
//                '-moz-border-radius': '10px',
//                color: '#fff',
//                top:    '20%',
//                left:   '15%'
//            } 
//    	}); 
//    	
//    	setTimeout(function() { 
//            $.unblockUI({ 
//                onUnblock: function(){
//                	window.location.href=url;
//                } 
//            }); 
//        }, 14000); 
//
//    }

    
    function runPrueba(){
    	$.ajax({
            url: "processPrueba",
            method: 'GET'
     	});
    }
    
    function onFaseReceived(numFaseActual) {
    	if(numFaseActual == 1){
    		runPrueba();
    	}
    }

   
   function processPrueba() {   
      $.ajax({
          url: "faseActual",
          method: 'GET',
          dataType: 'json',
          success: onFaseReceived
   	  });
   }


 //-------------------- FUNCIONES DE PARADA ------------------------------
   $("#btStop").click( function() { 
   		stop = 1;
   		showStopMessage();
	});

   function showStopMessage(){
   	var url = '../index.jsp';
	   	var url_image = '../images/Ok.png';
   	var message = probadetida;

   	$.blockUI({ 
   		message: '<h1><img src="'+url_image+'" alt="ok" /> '+message+'</h1>',
   		fadeIn: 1000, 
           fadeOut: 1000, 
           showOverlay: false, 
           css: { 
           	width: '550px',
               border: 'none',
               backgroundColor: '#000', 
               '-webkit-border-radius': '10px', 
               '-moz-border-radius': '10px',
               color: '#fff',
               top:    '20px',
               left:   '15px'
           } 
   	}); 
   	
   	setTimeout(function() { 
           $.unblockUI({ 
               onUnblock: function(){
               	window.location.href=url;
               } 
           }); 
       }, 4000); 
   }

    
    //----------------------------------------------------------------------
    
    
    init();
});
