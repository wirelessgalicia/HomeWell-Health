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

   function reprogramacionCorrecta(){
	   	var url = '../index.jsp';
	   	var url_image = '../images/Ok.png';
    	//var message = 'A proba reprogramouse correctamente';
    	showMensaje(message_reprogramar_ok,url, url_image);
   }

   function reprogramacionIncorrecta(){
	   var url = 'control.htm';
	   var url_image = '../images/warning.png';
	   //var message ='Error: A proba non se pudo reprogramar';
	   showMensaje(message_reprogramar_error,url, url_image);
   }
    
   function reprogramar(id) {   
       $.ajax({
           url: "inicializar_prueba?id="+id,
           method: 'GET',
           dataType: 'json',
           success: reprogramacionCorrecta,
           error: reprogramacionIncorrecta
    	});
    }
    
    /**
     * ---------------------------------------
     * -------------- Mensajes ----------------
     * ---------------------------------------
     */
    
    function showMensaje(message,urlDestino, image){
    	$.blockUI({ 
    		message: '<h1><img src="'+image+'" alt="ok" /> '+message+'</h1>',
    		fadeIn: 1000, 
            fadeOut: 1000, 
            showOverlay: false, 
            css: { 
            	width: '650px',
                border: 'none',
                backgroundColor: '#000', 
                '-webkit-border-radius': '15px', 
                '-moz-border-radius': '15px',
                color: '#fff',
                top:    '20%',
                left:   '15%'
            } 
    	}); 
    	
    	setTimeout(function() { 
            $.unblockUI({ 
                onUnblock: function(){
                	window.location.href=urlDestino;
                } 
            }); 
        }, 4000); 

    }

    
    /* 
     * Los botones de cada prueba
    */
    $("#btprueba1").click( function() {
    	//Medición del peso (real)
    	reprogramar('4889e0d745694a7600b7ec5d98prue90');
	});
    
    $("#btprueba2").click( function() {
    	// sim pulsioximetro y sim cicloergometro
    	//reprogramar('2289e0d745694a7600b7ec5d9849bc70');
    	reprogramar('4889e0d745694a7600b7ec5d98prue55');
	});
    
    $("#btprueba3").click( function() {
    	//Medición del peso con pulsioximetro con simulador
    	reprogramar('26146cbf7f00010101d742a1dceb4d1e');
    	//reprogramar('445a40897f00010101e36ec60b1363f9');
    	//reprogramar('16bc546c45694a760051207c68843f29');
	});

    
  });
