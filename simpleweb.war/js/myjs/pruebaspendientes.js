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
$(document).ready(function() {

var container = $('#errorContainer');
container.hide();
	
$("#list10").jqGrid({ 
	url: 'http://localhost:8888/simpleweb/osgi/pruebas/pendientes', 
	datatype: "json", 
	colNames:['Inv No','Date'], 
	colModel:[ {name:'id',index:'id', width:200},  
	           {name:'name',index:'name', width:400}
	         ], 
	rowNum:10, 
	rowList:[10,20,30], 
	pager: '#pager10', 
	sortname: 'id', 
	viewrecords: true, 
	sortorder: "desc", 
	multiselect: false, 
	caption: "Pruebas pendientes", 
	onSelectRow: function(ids) { 
		if(ids == null) {
			alert("ids seleccionado nulos")
			ids=0; 
			if($("#list10_d").jqGrid('getGridParam','records') >0 ) { 
				$("#list10_d").jqGrid('setGridParam',{
					url:"http://localhost:8888/simpleweb/osgi/pruebas/detalles?id="+ids,
					page:1
				}); 
				$("#list10_d").jqGrid('setCaption',"Informaci&oacuten detallada: "+ids).trigger('reloadGrid'); 
			} 
		} else { 
			container.hide();
			$("#list10_d").jqGrid('setGridParam',{
				url:"http://localhost:8888/simpleweb/osgi/pruebas/detalles?id="+ids,
				page:1
			}); 
			$("#list10_d").jqGrid('setCaption',"Informaci&oacuten detallada: "+ids).trigger('reloadGrid'); 
		} 
	} 
}); 

$("#list10").jqGrid('navGrid','#pager10',{
	add:false,
	edit:false,
	del:false
}); 

$("#list10_d").jqGrid({ 
	height: 100,  
	datatype: "json", 
	colNames:['No','Item'], 
	colModel:[ {name:'num',index:'num', width:200}, 
	           {name:'item',index:'item', width:400}
	          ], 
    rowNum:5, 
    rowList:[5,10,20], 
    pager: '#pager10_d', 
    sortname: 'item', 
    viewrecords: true, 
    sortorder: "asc", 
    multiselect: false, 
    caption:"Información Detallada" 
}).navGrid('#pager10_d',{add:false,edit:false,del:false}); 


$("#link_pasos").click( function() { 
	var id; 
	id = $("#list10").jqGrid('getGridParam','selrow');
	if(id == null){
		showError();
	}else{
		window.location.href = 'http://localhost:8888/simpleweb/osgi/prueba.htm';
	}
});

$("#link_iniciar").click( function() { 
	var id; 
	id = $("#list10").jqGrid('getGridParam','selrow');
	if(id == null){
		showError();
	}else{
		window.location.href = 'http://localhost:8888/simpleweb/osgi/ejecucionPruebas.htm';
	}
});

function showError(){
	container.html("Debe seleccionar una prueba")
    container.show();
}

});
