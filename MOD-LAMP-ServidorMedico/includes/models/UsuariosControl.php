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
 
class UsuariosControl
{

    public static function comprobarUsuario($usuario, $clave)
    {
        // {{{
        $q = Doctrine_Query::create()
            ->select('COUNT(id) AS numero_usuarios')
            ->from('Usuarios')
            ->where('usuario = ? and clave = ?', array($usuario, $clave));

        $resultado = $q->count();

        if ($resultado == 1){
            return 1;
        }else{
            return 0;
        }
        // }}}
    }

    public static function buscarPorUsuario($usuario)
    {
        // {{{
        return Doctrine::getTable('Usuarios')->findOneByUsuario($usuario);
        // }}}
    }

    public static function popularSelect($opcion_defecto)
    {
        // {{{

        global $GLTCN;

        $q = Doctrine_Query::create()
            ->select('id, alias')
            ->from('Usuarios')
            ->where('id_medico = ?', $GLTCN['id_medico'])
            ->orderBy('alias asc');

        $arrRegistros = $q->execute(array(), Doctrine::HYDRATE_NONE);

        $arrRespuesta = array();

        if(!empty($opcion_defecto)){
            $arrRespuesta[0] = $opcion_defecto;
        }

        foreach($arrRegistros as $registro){
            $arrRespuesta[$registro[0]] = $registro[1];
        }

        return $arrRespuesta;

        // }}}
    }

    public static function listar($filtrado, $paginacion = 0)
    {
        // {{{

        $resultado = array(
            'orden' => null,
            'coleccion' => null,
            'paginador' => null,
            'enlacesPaginador' => null,
        );

        $q = Doctrine_Query::create()
            ->from('Usuarios')
            ;

        // Gestión Orden {{{

        $orden = new SortColumn(basename(__FILE__)); 

        $orden->add('nombre', 'Nombre', 'per.nombre', 1);
        $orden->add('apellidos', 'Apellidos', 'per.apellidos');
        $orden->add('alias', 'Alias', 'per.alias');
        $orden->add('fecha', 'Antiguedad', 'per.fecha_entrada');

        $orden_sql = $orden->sql();
        if (!empty($orden_sql)){
            $q->orderBy($orden_sql . ', per.id asc');
        }

        $resultado['orden'] = $orden;

        // }}}

        // Filtrado {{{
        // Condicion texto, Tipo cadena {{{
        if (    
            isset($filtrado['texto']) 
            && !empty($filtrado['texto']) 
        ){
            $q->addWhere(
                '(per.nombre like ? or per.apellidos like ? or per.alias like ?)', 
                array(
                    '%' . $filtrado['texto'] . '%',
                    '%' . $filtrado['texto'] . '%',
                    '%' . $filtrado['texto'] . '%'
                )
            );
        }
        // }}}
        // Condicion vinculado, Tipo numerico {{{
        if (    
            isset($filtrado['vinculado']) 
            && $filtrado['vinculado'] == 1 
        ){
            $q->addWhere('(per.vinculado = 1)');
        } elseif (
            isset($filtrado['vinculado']) 
            && $filtrado['vinculado'] == 2 
        ) {
            $q->addWhere('(per.vinculado = 0)');
        }
        // }}}
        // Condicion id_grupo_investigacion, Tipo numerico {{{
        if (    
            isset($filtrado['id_grupo_investigacion']) 
            && is_numeric($filtrado['id_grupo_investigacion'])
        ){
            $q->addWhere('per.id_grupo_investigacion = ?', $filtrado['id_grupo_investigacion']);
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

    public static function obtenerRegistroPorId($idUsuario)
    {
        // {{{

        return Doctrine::getTable('Usuarios')->find($idUsuario);

        // }}}
    }

    public static function existeNombreUsuario($usuario)
    {
        // {{{

        $q = Doctrine_Query::create()
            ->select('count(*)')
            ->from('Usuarios')
            ->where('usuario = ?', $usuario)
            ;

        $resultado = $q->fetchOne();

        if($resultado['count'] > 0){
            return true;
        } else {
            return false;
        }

        // }}}
    }
}
