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
 * Pacientes
 * 
 * This class has been auto-generated by the Doctrine ORM Framework
 * 
 * @package    ##PACKAGE##
 * @subpackage ##SUBPACKAGE##
 * @author     ##NAME## <##EMAIL##>
 * @version    SVN: $Id: Builder.php 7490 2010-03-29 19:53:27Z jwage $
 */
class Pacientes extends BasePacientes
{

    public function calcularEdad()
    {
        // {{{

        $fecha_nacimiento = strtotime($this->fecha_nacimiento);

        $ano_nacimiento = date('Y', $fecha_nacimiento);
        $ano_actual = date('Y');

        $edad = $ano_actual - $ano_nacimiento;

        if(
            mktime(
                0,
                0,
                0,
                date('n', $fecha_nacimiento),
                date('j', $fecha_nacimiento),
                2000
            )

            >

            mktime(
                0,
                0,
                0,
                date('n'),
                date('j'),
                2000
            )
        ){
            $edad--;
        }

        return $edad;

        // }}}
    }

    public function modificar($cambios)
    {
        // {{{

        if (
                isset($cambios['fecha_nacimiento']['Y'])
            && !empty($cambios['fecha_nacimiento']['Y'])
            &&  isset($cambios['fecha_nacimiento']['M'])
            && !empty($cambios['fecha_nacimiento']['M'])
            &&  isset($cambios['fecha_nacimiento']['d'])
            && !empty($cambios['fecha_nacimiento']['d'])
        ){
            $strFechaNacimiento =
                  $cambios['fecha_nacimiento']['Y']
                . '-'
                . $cambios['fecha_nacimiento']['M'] 
                . '-'
                . $cambios['fecha_nacimiento']['d'] 
            ;
        }else{
            $strFechaNacimiento = null;
        }

        $this->nombre= $cambios['nombre'];
        $this->apellidos= $cambios['apellidos'];
        $this->fecha_nacimiento= $strFechaNacimiento;
        $this->sexo= $cambios['sexo'];
        $this->no_tis= $cambios['no_tis'];
        $this->dni= $cambios['dni'];
        $this->domicilio= $cambios['domicilio'];
        $this->telefono1= $cambios['telefono1'];
        $this->telefono2= $cambios['telefono2'];

        $this->save();

        // }}}
    }

    public function listarPruebas($filtrado, $paginacion = 0)
    {
        // {{{

        $resultado = array(
            'orden' => null,
            'coleccion' => null,
            'paginador' => null,
            'enlacesPaginador' => null,
        );

        $q = Doctrine_Query::create()
            ->from('PruebasProgramadas pp')
            ->where('pp.id_paciente = ?', $this->id)
            ;

        // Gestión Orden {{{

        $orden = new SortColumn(basename(__FILE__)); 

        $orden->add('fecha', 'Fecha', 'pp.fecha', 1);
        $orden->add('tipo', 'Tipo', 'pp.id_tipo_prueba');
        $orden->add('estado', 'Estado', 'pp.estado');

        $orden_sql = $orden->sql();
        if (!empty($orden_sql)){
            $q->orderBy($orden_sql);
        }

        $resultado['orden'] = $orden;

        // }}}

        // Filtrado {{{
        // Condicion id_tipo_prueba, Tipo numerico {{{
        if (    
            isset($filtrado['id_tipo_prueba']) 
            && is_numeric($filtrado['id_tipo_prueba']) 
            && $filtrado['id_tipo_prueba'] > 0 
        ){
            $q->addWhere('pp.id_tipo_prueba = ?', $filtrado['id_tipo_prueba']);
        }
        // }}}
        // }}}

        // Paginacion {{{

        if ($paginacion > 0) {
            // Paginamos {{{
            $resultado = array_merge($resultado, Paginacion::paginar($q, $paginacion));
            // }}}
        } else {
            // No paginamos {{{
            $resultado['coleccion'] = $q->execute();
            // }}}
        }

        // }}}

        return $resultado;

        // }}}
    }

}
