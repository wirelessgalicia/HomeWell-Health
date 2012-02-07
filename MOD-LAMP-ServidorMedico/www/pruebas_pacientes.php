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

// ../smarty/templates/pruebas_pacientes.tpl

/**
 * INCLUDES
 */

require_once('../includes/aplicacion_comienzo.php');

/**
 * ENTRADA
 */

$idPaciente = FuncionesGenerales::leerParametroNumerico('id_paciente',0);

if ( ! $idPaciente > 0) {
    FuncionesGenerales::generarError('idPaciente incorrecto', __LINE__, __FILE__);
}

$idTipo = FuncionesGenerales::leerParametroNumerico('id_tipo',0);

if ( ! $idTipo > 0) {
    FuncionesGenerales::generarError('idTipo incorrecto', __LINE__, __FILE__);
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

// Listado de pruebas {{{

$listado = PruebasProgramadasControl::historial($idPaciente, $idTipo, array(), 15);
$smarty->assign('listado', $listado);

// }}}

$smarty->assign('principal', 'pruebas_pacientes.tpl');
$smarty->display('layout.tpl');
