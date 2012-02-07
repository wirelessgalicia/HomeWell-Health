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

// ../smarty/templates/pruebas_pacientes_ver.tpl

/**
 * INCLUDES
 */

require_once('../includes/aplicacion_comienzo.php');

/**
 * ENTRADA
 */

$idPruebaPaciente = FuncionesGenerales::leerParametroNumerico('id',0);

if ( ! $idPruebaPaciente > 0) {
    FuncionesGenerales::generarError('idPruebaPaciente incorrecto', __LINE__, __FILE__);
}

/**
 * MAIN
 */

$pruebaPaciente = PruebasProgramadasControl::obtenerRegistroPorId($idPruebaPaciente);
$smarty->assign('pruebaPaciente', $pruebaPaciente);

if ($pruebaPaciente == false) {
    header('Location: pacientes.php');
}

$paciente = $pruebaPaciente->Pacientes;
$smarty->assign('paciente', $paciente);

// Nos aseguramos que el paciente pertenece al medico
if ($paciente->id_medico != $usuario->id_medico) {
    header('Location: pacientes.php');
}

// Formulario Modificacion {{{

$opciones_fecha = array(
    'language'  => 'es',
    'format'    => 'dMY H:i',
    'minYear'   => FuncionesGenerales::anoActual()-5,
    'maxYear'   => FuncionesGenerales::anoActual()+5,
    'addEmptyOption' => false
);

$dia_actual = array(
    'd' => date('d'),        
    'M' => date('n'),
    'Y' => date('Y'),
    'H' => date('H'),
    'i' => date('i'),
);

$formModificacion =  new HTML_QuickForm( 
    'modificar_registro', 
    'post', 
    basename(__FILE__) . '?id=' . $paciente->id .'#formulario',
    null,
    null,
    true
);

$formModificacion->addElement('select', 'id_tipo_prueba', null, PruebasTiposControl::popularSelect('Seleccione un tipo'), 'id="id_tipo"');
$formModificacion->addElement('date', 'fecha', null, $opciones_fecha);
$formModificacion->addElement('select', 'estado', null, array('P' => 'Programadas', 'C' => 'Cancelada'));

$formModificacion->addElement('submit', 'enviar' , 'Grabar datos');

// Reglas del formulario

$formModificacion->addRule('id_tipo_prueba','Tiene que indica un tipo de prueba','required');
$formModificacion->addRule('id_tipo_prueba','Tiene que indica un tipo de prueba','nonzero');

// Valores por defecto

$arrValoresDefecto = array();
$arrValoresDefecto['id_tipo_prueba'] = $pruebaPaciente->id_tipo_prueba;
$arrValoresDefecto['fecha'] = $pruebaPaciente->fecha;

$formModificacion->setDefaults($arrValoresDefecto);

// Filtros del formulario

$formModificacion->applyFilter('__ALL__','trim');

if ($formModificacion->validate()) {

    $arrValoresForm = $formModificacion->getSubmitValues();
    $pruebaPaciente->modificar($arrValoresForm);
    $pruebaPaciente->enviarRegistro();
    header('Location: pacientes_ver.php?id=' . $paciente->id .'#ficha');
}

$arrModificacion= FuncionesGenerales::formularioAArray($formModificacion);
$smarty->assign('arrModificacion', $arrModificacion);

// }}}

/**
 * SALIDA
 */

$smarty->assign('principal', 'pruebas_pacientes_ver.tpl');
$smarty->display('layout.tpl');
