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

class SortColumn {

    private $arrConfig;
    private $strUrl;
    private $baseUrl;

    public function __construct() 
    {
        // {{{
        $this->arrConfig['default']['element'] = null;
        $this->arrConfig['default']['value'] = null;
        $this->arrConfig['default']['sql_order'] = null;
        $this->arrConfig['default']['label'] = null;

        $this->arrConfig['parameter']['element'] = null;
        $this->arrConfig['parameter']['value'] = null;
        $this->arrConfig['parameter']['sql_order'] = null;
        $this->arrConfig['parameter']['label'] = null;

        $this->arrConfig{'result'}['sql'] = null;
        $this->arrConfig{'result'}['element'] = null;
        $this->arrConfig{'result'}['value'] = null;
        $this->arrConfig{'result'}['label'] = null;

        $this->strUrl = null;
        $this->baseUrl = FuncionesGenerales::getControlador();

        // }}}
    }

    public function add($name, $label, $sql_order, $valor = 0) 
    {
        // {{{
        $this->arrConfig['elements'][$name]['label'] = $label;
        $this->arrConfig['elements'][$name]['sql_order'] = $sql_order;
        $this->arrConfig['elements'][$name]['value'] = 0;


        if ($valor == 1 || $valor == 2){
            $this->arrConfig['elements'][$name]['default'] = $valor;
            $this->arrConfig['default']['element'] = $name;
            $this->arrConfig['default']['value'] = $valor;
            $this->arrConfig['default']['sql_order'] = $sql_order;
            $this->arrConfig['default']['label'] = $label;
        }else{
            $this->arrConfig['elements'][$name]['default'] = 0;
        }
        // }}}
    }

    public function sql() 
    {
        // {{{
        if(!empty($this->arrConfig['result']['sql'])){
            return $this->arrConfig['result']['sql'];
        }else{
            // Consultamos los valores pasados por parámetro

            foreach($_GET as $parametro => $valor){
                if(
                    preg_match('/^ord_([a-z]+)$/', $parametro, $coincidencias) == 1
                    && ($valor == 1 || $valor == 2)
                    && isset($this->arrConfig['elements'][$coincidencias[1]])
                ){
                    $this->arrConfig['elements'][$coincidencias[1]]['value'] = $valor;

                    $this->arrConfig['parameter']['element'] = $coincidencias[1];
                    $this->arrConfig['parameter']['value'] = $valor;
                    $this->arrConfig['parameter']['sql_order'] = 
                        $this->arrConfig['elements'][$coincidencias[1]]['sql_order'];
                    $this->arrConfig['parameter']['label'] = 
                        $this->arrConfig['elements'][$coincidencias[1]]['label'];
                }
            }

            // Si tenemos algún valor pasado por parámetro lo pasamos

            $sql = null;
            $value = null;
            $element = null;
            $label = null;

            if(
                !empty($this->arrConfig['parameter']['element'])
                && $this->arrConfig['parameter']['value'] == 1
            ){

                $sql = $this->arrConfig['parameter']['sql_order'] . ' asc';
                $value = 1;
                $element = $this->arrConfig['parameter']['element'];
                $label = $this->arrConfig['parameter']['label'];

            }elseif(
                !empty($this->arrConfig['parameter']['element'])
                && $this->arrConfig['parameter']['value'] == 2
            ){

                $sql = $this->arrConfig['parameter']['sql_order'] . ' desc';
                $value = 2;
                $element = $this->arrConfig['parameter']['element'];
                $label = $this->arrConfig['parameter']['label'];

            }elseif(
                !empty($this->arrConfig['default']['element'])
                && $this->arrConfig['default']['value'] == 1
            ){
                $sql = $this->arrConfig['default']['sql_order'] . ' asc';
                $value = 1;
                $element = $this->arrConfig['default']['element'];
                $label = $this->arrConfig['default']['label'];

            }elseif(
                !empty($this->arrConfig['default']['element'])
                && $this->arrConfig['default']['value'] == 2
            ){
                $sql = $this->arrConfig['default']['sql_order'] . ' desc';
                $value = 2;
                $element = $this->arrConfig['default']['element'];
                $label = $this->arrConfig['default']['label'];
            }

            $this->arrConfig['result']['sql'] = $sql;
            $this->arrConfig['result']['value'] = $value;
            $this->arrConfig['result']['element'] = $element;
            $this->arrConfig['result']['label'] = $label;

            return $sql;
        }
        // }}}
    }

    private function parseUrl() 
    {
        // {{{

            // Recorremos el array y borramos los parametros de orden y paginacion

            $arrParametros = $_GET;

            foreach ($arrParametros as $nombre => $valor){

                if(
                    $nombre == 'page'
                ){
                    unset($arrParametros[$nombre]);

                }elseif(
                    preg_match('/^ord_[a-z]+$/', $nombre) == 1
                ){
                    unset($arrParametros[$nombre]);
                }

            }

            // Convertimos de nuevo el array de parametros a una cadena URL válida

            $this->strUrl =  http_build_query($arrParametros);

        // }}}
    }

    public function table($element) 
    {
        // {{{

        if(empty($this->strUrl)){
            $this->parseUrl();
        }

        $strOutput = null;

        if(
            $this->arrConfig['result']['element'] == $element
            && $this->arrConfig['result']['value'] == 1
        ){
            // Estamos en una columna actualemente ordenada
            $strOutput = 
                  '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=2'
                . '" class="columna_orden">'
                . $this->arrConfig['elements'][$element]['label']
                . '</a>'

                . '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=2'
                . '">'
                . '<img src="imagenes/asc_off.gif" style="border:none; padding-left:2px;">'
                . '</a>'

                . '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=1'
                . '">'
                . '<img src="imagenes/desc.gif" style="border:none; padding-left:2px;">'
                . '</a>'
                ;

        }elseif(
            $this->arrConfig['result']['element'] == $element
            && $this->arrConfig['result']['value'] == 2
        ){
            // Estamos en una columna actualemente ordenada
            $strOutput = 
                  '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=1'
                . '" class="columna_orden">'
                . $this->arrConfig['elements'][$element]['label']
                . '</a>'

                . '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=2'
                . '">'
                . '<img src="imagenes/asc.gif" style="border:none; padding-left:2px;">'
                . '</a>'

                . '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=1'
                . '">'
                . '<img src="imagenes/desc_off.gif" style="border:none; padding-left:2px;">'
                . '</a>'
                ;

        }elseif(
            isset($this->arrConfig['elements'][$element])
        ){

            // Columna con posible orden pero sin usar
            $strOutput = 
                  '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=1'
                . '" class="columna_orden">'
                . $this->arrConfig['elements'][$element]['label']
                . '</a>'

                . '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=2'
                . '">'
                . '<img src="imagenes/asc_off.gif" style="border:none; padding-left:2px;">'
                . '</a>'

                . '<a href="' 
                . $this->baseUrl
                . '?'
                . $this->strUrl
                . '&ord_' . $element . '=1'
                . '">'
                . '<img src="imagenes/desc_off.gif" style="border:none; padding-left:2px;">'
                . '</a>'
                ;
        }else{

            // Columna que no se puede ordenar
            $strOutput = null;
        }


        return $strOutput;


        // }}}
    }

    public function debug() 
    {
        // {{{
        mostrar_array($this->arrConfig);
        // }}}
    }

}
