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

require_once('configuracion.php');

require_once(RUTA_DOCTRINE);
spl_autoload_register(array('Doctrine', 'autoload'));
spl_autoload_register(array('Doctrine', 'modelsAutoload'));


require_once('HTML/QuickForm.php');

// Doctrine {{{

$manager = Doctrine_Manager::getInstance();
$manager->setAttribute(Doctrine::ATTR_MODEL_LOADING, Doctrine::MODEL_LOADING_CONSERVATIVE);
$manager->setAttribute(Doctrine::ATTR_AUTOLOAD_TABLE_CLASSES, true);
$manager->setAttribute(Doctrine::ATTR_AUTO_ACCESSOR_OVERRIDE, true);
$manager->setAttribute(Doctrine::ATTR_USE_DQL_CALLBACKS, true);
// Conexión mysql {{{
$cadena_conexion = 
    "mysql://" .
    DB_SERVER_USERNAME . 
    ":" . 
    DB_SERVER_PASSWORD . 
    "@" . 
    DB_SERVER . 
    "/" . 
    DB_DATABASE; 

$conn = Doctrine_Manager::connection($cadena_conexion, 'mysql');
$conn->setCharset('utf8');

// }}}

$manager->setCurrentConnection('mysql');

Doctrine::loadModels(RUTA_MODELS);

// }}}

// Sesión {{{
$sesion = new Session();
// }}}

// Smarty {{{
require_once(RUTA_SMARTY);
$smarty = new Smarty();

$smarty->template_dir = RUTA_SMARTY_TEMPLATE_DIR;
$smarty->compile_dir = RUTA_SMARTY_COMPILE_DIR;
$smarty->cache_dir = RUTA_SMARTY_CACHE_DIR;
$smarty->config_dir = RUTA_SMARTY_CONFIG_DIR;

// }}}

// Control Entrada y $GLTCN {{{ 

// Recuperamos el usuario

$GLTCN['id_usuario'] = $sesion->get('id_usuario');

// Por defecto este sitio tendrá todas las páginas bajo el control
// de acceso. A no se que explicitamente indiquemos un $GLTCN_pagina_privada==0
// antes del include aplicacion_comienzo.php.

if(
    isset($GLTCN_pagina_privada) 
    && $GLTCN_pagina_privada == 0
){

    $GLTCN_pagina_privada = 0;

}else{
    
    $GLTCN_pagina_privada=1;
}

// Comprobamos que si es una página privada tenemos un usuario autentificado

if (
    $GLTCN_pagina_privada == 1
    && !($GLTCN['id_usuario'] > 0)
){

    // Intento de entrada sin haberse autentificado
    header('Location: login.php');
    exit;

} 

if ($GLTCN['id_usuario'] > 0) {

    $usuario = UsuariosControl::obtenerRegistroPorId($GLTCN['id_usuario']);

    if ($usuario == false) {
        FuncionesGenerales::generarError('Usuario no válido', __LINE__, __FILE__);
    }

    $smarty->assign('usuario', $usuario);
}

// Pasamos todos los datos a la plantilla
$smarty->assign('GLTCN', $GLTCN);

// }}}
