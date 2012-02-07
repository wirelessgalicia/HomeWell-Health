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

    function showMessage(){
    	var url = '../index.jsp';
    	var message = message_sinpruebas;

    	$.blockUI({ 
    		message: '<h1>'+message+'</h1>',
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
                left:   '20%'
            } 
    	}); 
    	
    	setTimeout(function() { 
            $.unblockUI({ 
                onUnblock: function(){
                	window.location.href=url;
                } 
            }); 
        }, 8000); 
    }
    //----------------------------------------------------------------------
    
    showMessage();
});