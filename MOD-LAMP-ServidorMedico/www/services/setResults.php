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

/**
 * INCLUDES
 */

$GLTCN_pagina_privada = 0;
require_once('../../includes/aplicacion_comienzo.php');

/*
{"resultados":{
    "Saturacion":[100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0],"Peso":[100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0],"Rpm":[100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0],"Pulso":[100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0,100.0,101.0,102.0,103.0,104.0,105.0,106.0,107.0,108.0,109.0]},"idPrueba":"id_prueba_1"}
*/

header('Content-Type:application/json');

$datos = $HTTP_RAW_POST_DATA;
$arrDatos = json_decode($HTTP_RAW_POST_DATA, true);

if ($arrDatos === null) {
    echo json_encode(array('ok' => 0, 'mensaje' => 'formato json no válido'));
    exit;
}

if (! isset($arrDatos['idPrueba'])) {
    echo json_encode(array('ok' => 0, 'mensaje' => 'no se indica idPrueba'));
    exit;
}

if (!( is_numeric($arrDatos['idPrueba']) && $arrDatos['idPrueba'] > 0)) {
    echo json_encode(array('ok' => 0, 'mensaje' => 'el valor de idPrueba no es válido'));
    exit;
}

if (! isset($arrDatos['resultados'])) {
    echo json_encode(array('ok' => 0, 'mensaje' => 'no hay clave resultados'));
    exit;
}

if (count($arrDatos['resultados']) == 0) {
    echo json_encode(array('ok' => 0, 'mensaje' => 'ningun relsultado'));
    exit;
}

$idPruebaPaciente = $arrDatos['idPrueba'];

$pruebaPaciente = PruebasProgramadasControl::obtenerRegistroPorId($idPruebaPaciente);

if ($pruebaPaciente == false) {
    echo json_encode(array('ok' => 0, 'mensaje' => 'no existe el idPrueba enviado'));
    exit;
}

$pruebaPaciente->resultado = json_encode($arrDatos['resultados']);
$pruebaPaciente->estado = 'F';
$pruebaPaciente->save();


// Guardo todo el mensaje

$sql = 'INSERT registro SET fecha=now(), contenido=?';
$q = $conn->prepare($sql);
$q->execute(array($datos));

echo json_encode(array('ok' => 1));
exit;
