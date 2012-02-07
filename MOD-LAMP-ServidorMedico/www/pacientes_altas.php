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

// ../smarty/templates/pacientes_altas.tpl

/**
 * INCLUDES
 */

require_once('../includes/aplicacion_comienzo.php');

/**
 * MAIN
 */

// Formulario Alta {{{

$opciones_fecha = array(
    'language'  => 'es',
    'format'    => 'dMY',
    'minYear'   => 1900,
    'maxYear'   => FuncionesGenerales::anoActual()+1,
    'addEmptyOption' => true
);

$dia_actual = array(
    'd' => date('d'),        
    'M' => date('n'),
    'Y' => date('Y')
);


$formAlta =  new HTML_QuickForm( 
    'alta_registro', 
    'post', 
    basename(__FILE__),
    null,
    null,
    true
);

$formAlta->addElement('text', 'nombre', null, 'maxwidth="255" style="width:95%;"');
$formAlta->addElement('text', 'apellidos', null, 'maxwidth="255" style="width:95%;"');
$formAlta->addElement('date', 'fecha_nacimiento', null, $opciones_fecha);
$formAlta->addElement('select', 'sexo', null, array('M' => 'M', 'F' => 'F'));
$formAlta->addElement('text', 'no_tis', null, 'maxwidth="255" style="width:20em;"');
$formAlta->addElement('textarea', 'domicilio', null, 'style="width:95%;height:4em;"');
$formAlta->addElement('text', 'telefono1', null, 'maxwidth="255" style="width:20em;"');
$formAlta->addElement('text', 'telefono2', null, 'maxwidth="255" style="width:20em;"');

$formAlta->addElement('submit', 'enviar' , 'Grabar paciente');

// Valores por defecto

$arrValoresDefecto = array();
$arrValoresDefecto['fecha_nacimiento'] = $dia_actual;

$formAlta->setDefaults($arrValoresDefecto);

// Reglas del formulario

$formAlta->addRule('nombre','Tiene que indica un nombre','required');
$formAlta->addRule('apellidos','Tiene que indica los apellidos','required');

// Filtros del formulario

$formAlta->applyFilter('__ALL__','trim');

if ($formAlta->validate()) {

    $arrValoresForm=$formAlta->getSubmitValues();

    if (
            isset($arrValoresForm['fecha_nacimiento']['Y'])
        && !empty($arrValoresForm['fecha_nacimiento']['Y'])
        &&  isset($arrValoresForm['fecha_nacimiento']['M'])
        && !empty($arrValoresForm['fecha_nacimiento']['M'])
        &&  isset($arrValoresForm['fecha_nacimiento']['d'])
        && !empty($arrValoresForm['fecha_nacimiento']['d'])
    ){
        $strFechaNacimiento =
              $arrValoresForm['fecha_nacimiento']['Y']
            . '-'
            . $arrValoresForm['fecha_nacimiento']['M'] 
            . '-'
            . $arrValoresForm['fecha_nacimiento']['d'] 
        ;
    }else{
        $strFechaNacimiento = null;
    }

    $paciente = new Pacientes();
    $paciente->id_medico = $usuario->id_medico;
    $paciente->nombre = $arrValoresForm['nombre'];
    $paciente->apellidos = $arrValoresForm['apellidos'];
    $paciente->fecha_nacimiento = $strFechaNacimiento;
    $paciente->sexo = $arrValoresForm['sexo'];
    $paciente->no_tis = $arrValoresForm['no_tis'];
    $paciente->domicilio = $arrValoresForm['domicilio'];
    $paciente->telefono1 = $arrValoresForm['telefono1'];
    $paciente->telefono2 = $arrValoresForm['telefono2'];
    $paciente->save();

    Header('Location: pacientes.php');
}

$arrAlta= FuncionesGenerales::formularioAArray($formAlta);
$smarty->assign('arrAlta', $arrAlta);

// }}}

/**
 * SALIDA
 */

$smarty->assign('principal', 'pacientes_altas.tpl');
$smarty->display('layout.tpl');
