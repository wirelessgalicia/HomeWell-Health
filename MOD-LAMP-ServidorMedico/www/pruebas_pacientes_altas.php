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

// ../smarty/templates/pruebas_pacientes_altas.tpl

/**
 * INCLUDES
 */

require_once('../includes/aplicacion_comienzo.php');

/**
 * FUNCIONES
 */

function comprobarFormulario($valores) {
    // {{{

    $errores = array();

    if (
        isset($valores['repetir'])
        && $valores['repetir'] == 1
    ) {

        if (!(
            isset($valores['numero_repeticiones'])
            && is_numeric($valores['numero_repeticiones'])
            && $valores['numero_repeticiones'] > 0
        )) {
            $errores['numero_repeticiones'] = 'Tiene que indicar un número de repeticiones mayores que cero';
        }

        if (!(
            isset($valores['incremento_dias'])
            && is_numeric($valores['incremento_dias'])
            && $valores['incremento_dias'] > 0
        )) {
            $errores['incremento_dias'] = 'Tiene que indicar un incremeto de días mayor que cero';
        }
    }

    if (count($errores) == 0) {
        return true;
    } else {
        return $errores;
    }

    // }}}
}

/**
 * ENTRADA
 */

$idPaciente = FuncionesGenerales::leerParametroNumerico('paciente',0);

if ( ! $idPaciente > 0) {
    FuncionesGenerales::generarError('idPaciente incorrecto', __LINE__, __FILE__);
}

/**
 * MAIN
 */

$paciente = PacientesControl::obtenerRegistroPorId($idPaciente);
$smarty->assign('paciente', $paciente);

// Nos aseguramos que el paciente pertenece al medico
if ($paciente->id_medico != $usuario->id_medico) {
    header('Location: pacientes.php');
}

// Formulario Alta {{{

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


$formAlta =  new HTML_QuickForm( 
    'alta_registro', 
    'post', 
    basename(__FILE__) . '?paciente=' . $paciente->id,
    null,
    null,
    true
);

$formAlta->addElement('select', 'id_tipo_prueba', null, PruebasTiposControl::popularSelect('Seleccione un tipo'), 'id="id_tipo"');
$formAlta->addElement('date', 'fecha', null, $opciones_fecha);
$formAlta->addElement('checkbox', 'repetir');
$formAlta->addElement('text', 'numero_repeticiones', null, 'style="width:4em;"');
$formAlta->addElement('text', 'incremento_dias', null, 'style="width:4em;"');

$formAlta->addElement('submit', 'enviar' , 'Grabar prueba');

// Reglas del formulario

$formAlta->addRule('id_tipo_prueba','Tiene que indica un tipo de prueba','required');
$formAlta->addRule('id_tipo_prueba','Tiene que indica un tipo de prueba','nonzero');
$formAlta->addFormRule('comprobarFormulario');

// Valores por defecto

$arrValoresDefecto = array();
$arrValoresDefecto['fecha'] = $dia_actual;

$formAlta->setDefaults($arrValoresDefecto);

// Filtros del formulario

$formAlta->applyFilter('__ALL__','trim');

if ($formAlta->validate()) {

    $arrValoresForm=$formAlta->getSubmitValues();

    if (
            isset($arrValoresForm['fecha']['Y'])
        && !empty($arrValoresForm['fecha']['Y'])
        &&  isset($arrValoresForm['fecha']['M'])
        && !empty($arrValoresForm['fecha']['M'])
        &&  isset($arrValoresForm['fecha']['d'])
        && !empty($arrValoresForm['fecha']['d'])
        &&  isset($arrValoresForm['fecha']['H'])
        && !empty($arrValoresForm['fecha']['H'])
        &&  isset($arrValoresForm['fecha']['i'])
        && !empty($arrValoresForm['fecha']['i'])
    ){
        $strFecha =
              $arrValoresForm['fecha']['Y']
            . '-'
            . $arrValoresForm['fecha']['M'] 
            . '-'
            . $arrValoresForm['fecha']['d'] 
            . ' '
            . $arrValoresForm['fecha']['H'] 
            . ':'
            . $arrValoresForm['fecha']['i'] 
        ;
    }else{
        $strFecha = null;
    }

    $pruebaProgramada = new PruebasProgramadas();
    $pruebaProgramada->id_paciente = $paciente->id;
    $pruebaProgramada->id_tipo_prueba = $arrValoresForm['id_tipo_prueba'];
    $pruebaProgramada->fecha = $strFecha;
    $pruebaProgramada->estado = 'P';
    $pruebaProgramada->save();

    $pruebaProgramada->enviarRegistro();

    // Vemos si hemos activado la repetición de pruebas

    if (isset($arrValoresForm['repetir']) && $arrValoresForm['repetir'] == 1) {
        for ($i = 1; $i <= $arrValoresForm['numero_repeticiones']; $i++) {
            $pruebaProgramada = new PruebasProgramadas();
            $pruebaProgramada->id_paciente = $paciente->id;
            $pruebaProgramada->id_tipo_prueba = $arrValoresForm['id_tipo_prueba'];

            $fechaUnix = mktime(
                $arrValoresForm['fecha']['H'],
                $arrValoresForm['fecha']['i'],
                0,
                $arrValoresForm['fecha']['M'],
                $arrValoresForm['fecha']['d'] + ($i * $arrValoresForm['incremento_dias']),
                $arrValoresForm['fecha']['Y']
            );

            $pruebaProgramada->fecha = date('Y-m-d H:i', $fechaUnix);
            $pruebaProgramada->estado = 'P';
            $pruebaProgramada->save();
            $pruebaProgramada->enviarRegistro();
            unset($pruebaProgramada);
        }
    }

    header('Location: pacientes_ver.php?id=' . $paciente->id);
}

$arrAlta= FuncionesGenerales::formularioAArray($formAlta);
$smarty->assign('arrAlta', $arrAlta);

// }}}

/**
 * SALIDA
 */

$smarty->assign('principal', 'pruebas_pacientes_altas.tpl');
$smarty->display('layout.tpl');