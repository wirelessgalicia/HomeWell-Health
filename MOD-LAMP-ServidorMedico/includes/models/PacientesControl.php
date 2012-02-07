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

class PacientesControl
{

    public static function listar($idMedico, $filtrado, $paginacion = 0)
    {
        // {{{

        $resultado = array(
            'orden' => null,
            'coleccion' => null,
            'paginador' => null,
            'enlacesPaginador' => null,
        );

        $q = Doctrine_Query::create()
            ->from('Pacientes pac')
            ->where('pac.id_medico = ?', $idMedico)
            ;

        // Gestión Orden {{{

        $orden = new SortColumn(basename(__FILE__)); 

        $orden->add('apellidos', 'Apellidos', 'pac.apellidos', 1);
        $orden->add('nombre', 'Nombre', 'pac.nombre');
        $orden->add('nacimiento', 'Nacimiento', 'pac.fecha_nacimiento');
        $orden->add('sexo', 'Sexo', 'pac.sexo');
        $orden->add('estado', 'Estado', 'pac.estado');

        $orden_sql = $orden->sql();
        if (!empty($orden_sql)){
            $q->orderBy($orden_sql);
        }

        $resultado['orden'] = $orden;

        // }}}

        // Filtrado {{{
        // Condicion nombre, Tipo cadena {{{
        if (    
            isset($filtrado['nombre']) 
            && !empty($filtrado['nombre']) 
        ){
            $q->addWhere('pac.nombre like ?', '%' . $filtrado['nombre'] . '%');
        }
        // }}}
        // Condicion apellidos, Tipo cadena {{{
        if (    
            isset($filtrado['apellidos']) 
            && !empty($filtrado['apellidos']) 
        ){
            $q->addWhere('pac.apellidos like ?', '%' . $filtrado['apellidos'] . '%');
        }
        // }}}
        // Condicion no_tis, Tipo cadena {{{
        if (    
            isset($filtrado['no_tis']) 
            && !empty($filtrado['no_tis']) 
        ){
            $q->addWhere('pac.no_tis = ?', $filtrado['no_tis']);
        }
        // }}}
        // Condicion domicilio, Tipo cadena {{{
        if (    
            isset($filtrado['domicilio']) 
            && !empty($filtrado['domicilio']) 
        ){
            $q->addWhere('pac.domicilio like ?', '%' . $filtrado['domicilio'] . '%');
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

    public static function obtenerRegistroPorId($idPaciente)
    {
        // {{{

        return Doctrine::getTable('Pacientes')->find($idPaciente);

        // }}}
    }


}
