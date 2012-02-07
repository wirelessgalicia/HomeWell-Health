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
    var stop = 0;
    var viewData = [];
    var peso = -1;

    
    function onDataReceived(view) {
    	 viewData = view;
    }

    
   function getData() {   
       peso = -1;
       $.ajax({
           url: "valorPeso",
           method: 'GET',
           dataType: 'json',
           success: onDataReceived
    	});
       return peso;
    }

    
    // setup control widget
    var interval = 50;
    var timeToShow = 10000

    function showValue(alarma){
    	if((alarma == "FASE_FINALIZADA") || (alarma == "PRUEBA_FINALIZADA")){
    		
    		var t_peso = [];
    	 	var peso = -1;
    	 	if(viewData != null && viewData.resultados != null){
    	 		if(viewData.resultados.Peso != null){
    	 			t_peso = viewData.resultados.Peso;
    	 			if(t_peso.length > 0){
    	 	 	 		lastValue = t_peso[t_peso.length - 1].valor;
    	 	 	 		peso = lastValue;
    	 	 	 	}
    	 		}
    	 	}

    		if(peso > -1){
    			$('#txtPeso').val(peso);
    		}
    	}
    	setTimeout(function(){
    		showAlarmas(alarma);
    	},4000);
    }

    function updateView(){
        //actualizar alarmas
    	var alarmas = [];
        if(viewData != null && viewData.alarmas != null){
        	alarmas = viewData.alarmas;
    	}
        if(alarmas != null){
        	for(var i = 0; i < alarmas.length ; ++i){
        		if(stop == 0){
        			stop = 1;
        			showValue(alarmas[i]);
        		}
   	 		}
        }

        if(stop == 0){
        	setTimeout(function(){
        		if(stop == 0){
        			update();
        		}
        	}, interval);
        }
    }
    
    function update() {
        // Actualizar con nuevos datos
    	getData();
        updateView();
    }

    function init(){
    	// Inicializar con todos los datos
    	stop = 0;
    	viewData = [];
    	processPrueba();
    	getData()
    	updateView();
    }

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
    		if(result.ok == 0){
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
    	var salir = 0;
    	var url_salida = '../index.jsp';
    	var message = cancelada;
    	var url_image = '../images/Ok.png';

    	if(data == "PACIENTE_DEBE_PARAR_PRUEBA"){
    		message = atencion;
    		url_image = '../images/warning.png';
    		salir = 0;
    	}
    	if(data == "FASE_FINALIZADA"){
    		message = rematoufase;
    		url_salida = 'ejecucionFase.htm'
    		salir = 1;
    	}
    	if(data == "PRUEBA_FINALIZADA"){
    		message = rematouproba;
    		salir = 0;
    	}
    	showBoxInfo(url_image, message, salir, url_salida); 
    }
    
    
    function showBoxInfo(url_image, message,salir, url_salida){
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

    	var intervalo = 2000;
    	if(salir == 1){
    		intervalo = 2000;
    	}else{
    		intervalo = 20000;
    	}

    	setTimeout(function() { 
            $.unblockUI({ 
                onUnblock: function(){
                	if(salir == 1){
                		window.location.href = url_salida;
                	}else{
                		sendHistorial();
                	}
                } 
            }); 
        }, intervalo);
    }
    
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
    	setTimeout(function(){
    		showStopMessage();
    	},20000);
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
                top:    '20%',
                left:   '15%'
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
