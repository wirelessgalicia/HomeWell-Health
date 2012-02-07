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

// ../smarty/templates/pacientes.tpl

/**
 * INCLUDES
 */

require_once('../includes/aplicacion_comienzo.php');

/**
 * MAIN
 */

// Formulario Filtrado {{{

$formFiltrado = new HTML_QuickForm( 
    'filtrar_registros', 
    'get', 
    basename(__FILE__),
    null,
    null,
    true
);

$formFiltrado->addElement('text', 'nombre', null, 'maxwidth="255" size="10"');
$formFiltrado->addElement('text', 'apellidos', null, 'maxwidth="255" size="20"');
$formFiltrado->addElement('text', 'no_tis', null, 'maxwidth="255" size="10"');
$formFiltrado->addElement('text', 'domicilio', null, 'maxwidth="255" size="20"');

$formFiltrado->addElement('submit', 'enviar' , 'Filtrar');

// Filtros del formulario:

$formFiltrado->applyFilter('__ALL__','trim');

$arrFormFiltrado= FuncionesGenerales::formularioAArray($formFiltrado);
$smarty->assign('formFiltrado', $arrFormFiltrado);

// }}}
// Listado de pacientes {{{

$listado = PacientesControl::listar($usuario->id_medico, $formFiltrado->getSubmitValues(), 15);
$smarty->assign('listado', $listado);

// }}}

$smarty->assign('principal', 'pacientes.tpl');
$smarty->display('layout.tpl');
