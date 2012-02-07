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

// ../smarty/templates/login.tpl

/**
 * INCLUDES
 */

$GLTCN_pagina_privada = 0;
require_once('../includes/aplicacion_comienzo.php');

/**
 * FUNCIONES
 */


function comprobarLogin($campos){
    // {{{

    if ( isset($campos['usuario']) && isset($campos['clave'])){

        if (UsuariosControl::comprobarUsuario($campos['usuario'], $campos['clave']) == 1){
            return true;
        }else{
            return array('clave'=>'Los datos introducidos son incorrectos');
        }

    }else{
       return array('clave'=>'Los datos introducidos son incorrectos');
    }

// }}}
}

/**
 * MAIN
 */

// Formulario de login {{{

$form = new HTML_QuickForm( 
    'login', 
    'post', 
    'login.php',
    null,
    null,
    true
);

$form->addElement('text', 'usuario', 'Usuario:', 'maxlength="200" size="30"'); 
$form->addElement('password', 'clave', 'Clave:', 'maxlength="200" size="30"'); 
$form->addElement('submit', 'enviar' , 'Entrar');

$form->addRule('usuario','Tiene que indicar un usuario','required');
$form->addRule('clave','Tiene que indicar una clave','required');

$form->addFormRule('comprobarLogin');

$form->applyFilter('__ALL__','trim');

if ($form->validate()) {

    $arrValoresForm = $form->getSubmitValues();

    $usuario = UsuariosControl::buscarPorUsuario($arrValoresForm['usuario']);

    if ($usuario == false){
        FuncionesGenerales::generarError('Usuario no válido', __LINE__, __FILE__);
    }

    $sesion->set('id_usuario',$usuario->id);

    header('Location: index.php');
    exit;
    
}

$array_formulario= FuncionesGenerales::formularioAArray($form);

// }}}
 
/**
 * SALIDA
 */

$smarty->assign('formulario', $array_formulario);
$smarty->display('login.tpl');
