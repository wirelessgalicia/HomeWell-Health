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
 
class PruebasTiposControl
{

    public static function popularSelect($opcion_defecto) {
        // {{{
        $q = Doctrine_Query::create()
            ->select('tip.id_tipo_prueba, tip.nombre')
            ->from('PruebasTipos tip')
            ->orderBy('tip.id_tipo_prueba asc');

        $arrTipos = $q->execute(array(), Doctrine::HYDRATE_NONE);

        $arrRespuesta = array();

        if(!empty($opcion_defecto)){
            $arrRespuesta[0] = $opcion_defecto;
        }

        foreach($arrTipos as $tipo){
            $arrRespuesta[$tipo[0]] = $tipo[1];
        }

        return $arrRespuesta;

        // }}}
    }

}
