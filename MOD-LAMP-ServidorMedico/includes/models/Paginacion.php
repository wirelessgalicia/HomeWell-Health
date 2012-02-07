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

class Paginacion
{

    public static function paginar($q, $tamanoPagina = 15)
    {
        // {{{

        $pager = new Doctrine_Pager(
            $q,
            1, // Current page of request
            $tamanoPagina 
        );

        if(
            isset($_GET['page'])
            && is_numeric($_GET['page'])
            && $_GET['page'] > 0
        ){
            $pager->setPage($_GET['page']);
        }

        $arrConsulta = $_GET;
        unset($arrConsulta['page']);
        if (count($arrConsulta) > 0){
            $strConsulta = '&' . http_build_query($arrConsulta);
        }else{
            $strConsulta = '';
        }

        $pagerLayout = new PagerLayoutWithArrows(
            $pager,
            new Doctrine_Pager_Range_Sliding( array( 'chunk' => 5), $pager),
            FuncionesGenerales::getControlador() . '?page={%page_number}' . $strConsulta
        );

        $pagerLayout->setTemplate('<a href="{%url}">{%page}</a>&nbsp;');
        $pagerLayout->setSelectedTemplate('{%page}&nbsp;');

        $arrRegistros = $pagerLayout->execute(
            array(), 
            Doctrine::HYDRATE_RECORD
        );


        $link_paginador = $pagerLayout->display( 
            array(), 
            true
        );

        $resultado['coleccion'] = $arrRegistros;
        $resultado['paginador'] = $pager;
        $resultado['enlacesPaginador'] = $link_paginador;

        return $resultado;

        // }}}
    }

}
