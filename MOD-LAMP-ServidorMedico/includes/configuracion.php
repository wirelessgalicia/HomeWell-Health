<?php
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

// Configuraciones que dependen del servidor

if( __FILE__ == '/home/ender/proyectos/wireless/home_well_health/directorio_svn/includes/configuracion.php'){
	// Configuración local {{{

	// Controlamos la actividad del Profile
    define('PROFILE_DOCTRINE', 0);
    define('PROFILE_TIEMPOS_CARGA', 0);

	// Definimos el tema de los errores
    error_reporting(E_ALL);
    //error_reporting(0);

	// Ruta aplicación
    define('RUTA_APLICACION','/home/ender/proyectos/wireless/home_well_health/directorio_svn/');

	// Definimos los parametros de conexion a la BD
    define('DB_SERVER'	       , 'localhost'); 
    define('DB_SERVER_USERNAME', 'xxxxxxxxxxxx');
    define('DB_SERVER_PASSWORD', 'xxxxxxxxxxxx');
    define('DB_DATABASE'       , 'home_well_health');

	// Definimos los parametros necesarios para smarty
    define('RUTA_SMARTY', '/usr/local/lib/php/Smarty/Smarty.class.php');

	// Definimos los parametros necesarios para doctrine
    define('RUTA_DOCTRINE', '/home/ender/proyectos/doctrine_1.2.2/directorio_svn/lib/Doctrine.php');

    // }}}
}elseif( __FILE__ == '/home/wgxestion/aplicacion_home_well_health/includes/configuracion.php'){
	// Configuración AWS {{{

	// Controlamos la actividad del Profile
    define('PROFILE_DOCTRINE', 0);
    define('PROFILE_TIEMPOS_CARGA', 0);

	// Definimos el tema de los errores
    error_reporting(E_ALL ^ E_DEPRECATED);
    //error_reporting(0);
    //error_reporting(E_ALL);

	// Ruta aplicación
    define('RUTA_APLICACION','/home/wgxestion/aplicacion_home_well_health/');

	// Definimos los parametros de conexion a la BD
    define('DB_SERVER'	       , 'localhost'); 
    define('DB_SERVER_USERNAME', 'xxxxxxxxxxx');
    define('DB_SERVER_PASSWORD', 'xxxxxxxxxxx');
    define('DB_DATABASE'       , 'home_well_health');

	// Definimos los parametros necesarios para smarty
    define('RUTA_SMARTY', '/home/wgxestion/aplicacion_home_well_health/libs/smarty/Smarty.class.php');

	// Definimos los parametros necesarios para doctrine
    define('RUTA_DOCTRINE', '/home/wgxestion/aplicacion_home_well_health/libs/doctrine1/lib/Doctrine.php');

    // Definimos el PATH de nuestra instalación de PEAR
    ini_set('include_path', '/home/wgxestion/aplicacion_home_well_health/libs/pear/lib' . PATH_SEPARATOR . ini_get('include_path'));

    // }}}
}else{
    die('Configuración incorrecta.');
}

// Configuraciones generales

define('RUTA_MODELS',               RUTA_APLICACION . 'includes/models/'); 
define('RUTA_SCHEMA',               RUTA_APLICACION . 'includes/schema.yml'); 

define('RUTA_SMARTY_TEMPLATE_DIR',  RUTA_APLICACION . 'smarty/templates');
define('RUTA_SMARTY_COMPILE_DIR',   RUTA_APLICACION . 'smarty/templates_c');
define('RUTA_SMARTY_CACHE_DIR',     RUTA_APLICACION . 'smarty/cache');
define('RUTA_SMARTY_CONFIG_DIR',    RUTA_APLICACION . 'smarty/configs');

define('URL_SERVICIOS', 'http://homewellhealth.wirelessgalicia.com:8888/homewellhealth_1.0.0/osgi/');
