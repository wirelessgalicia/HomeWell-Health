<!--
  This file is part of HomeWell-Health
 
  Copyright (C) 2011-2012 WirelessGalicia S.L.
 
  The contents of this file are subject to the Mozilla Public License
  Version 1.1 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.mozilla.org/MPL/
  
  Software distributed under the License is distributed on an "AS IS"
  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
  License for the specific language governing rights and limitations
  under the License.
-->

{* ../../www/pacientes.php *}

{* Javascript {{{ *}
{literal}
<script language="Javascript">

</script>
{/literal}
{* Fin Javascript }}} *}
<table style="width:100%">
    <tr>
        <td>
            <h2 class="titulo_seccion">
                Listado de pacientes
            </h2>
        </td>
        <td>
            <span class="btn btn-medium-green" style="float:right;">
                <a href="pacientes_altas.php">
                    Alta nuevo paciente
                </a>
            </span>
        </td>
    </tr>
</table>

{* Formulario de búsqueda {{{ *}
{assign var='form' value=$formFiltrado}

    <div class="destacado">
        <form {$form.attributes} style="padding:0;margin:0;">
        {$form.elements_name._qf__filtrar_registros.html}
        {* nombre {{{ *}
            <table width="99%" class="formulario">
                <tr>
                    <td align="left" style="line-height:25px;">
                        Nombre:
                        {$form.elements_name.nombre.html}
                        &nbsp;&nbsp;
                        Apellidos:
                        {$form.elements_name.apellidos.html}
                        &nbsp;&nbsp;
                        TIS:
                        {$form.elements_name.no_tis.html}
                        &nbsp;&nbsp;
                        Domicilio:
                        {$form.elements_name.domicilio.html}

                        &nbsp;&nbsp;
                        {$form.elements_name.enviar.html}
                    </td>
                </tr>
            </table>
        {*}}}*}
        </form>
    </div>

{* }}} *}

{* Listado de registros {{{ *}

<div class="paginador">
{* Paginador {{{*}

    <b>{$listado.paginador->getNumResults()}</b> pacientes  

    {if $listado.paginador->haveToPaginate()}
        en {$listado.paginador->getLastPage()}
        páginas
        &nbsp; &nbsp; &nbsp;
        {$listado.enlacesPaginador}
    {/if}

{* }}} *}
</div>

<br>

<table width="100%" class="resultados" cellspacing="0" cellpadding="0">
{* Tabla Resultados {{{*}
    <tr>

        <th style="width:180px;">
            {$listado.orden->table('nombre')}
        </th>
        <th>
            {$listado.orden->table('apellidos')}
        </th>
        <th style="width:150px;text-align:center;">
            {$listado.orden->table('nacimiento')}
        </th>
        <th style="width:80px;text-align:center;">
            {$listado.orden->table('sexo')}
        </th>
        <th style="width:80px;text-align:center;">
            TIS
        </th>
        <th style="width:80px;text-align:center;">
            {$listado.orden->table('estado')}
        </th>
        <th width="50" style="text-align:center;">
            &nbsp;
        </th>
    </tr>

    {if $listado.paginador->getNumResults() > 0}
    {foreach item=registro from=$listado.coleccion}
    <tr class="{cycle values="fila_par,fila_impar"}">

        <td>
            <a href="pacientes_ver.php?id={$registro.id}" class="boton" style="font-weight:bold;">
                {$registro.nombre}
            </a>
        </td>
        <td>
            <a href="pacientes_ver.php?id={$registro.id}" class="boton" style="font-weight:bold;">
                {$registro.apellidos}
            </a>
        </td>
        <td style="text-align:center;">
            <b>({$registro->calcularEdad()} años)</b>
            {$registro.fecha_nacimiento|date_format:"%d/%m/%Y"}

        </td>
        <td style="text-align:center;">
            {$registro.sexo}
        </td>
        <td style="text-align:center;">
            {$registro.no_tis}
        </td>
        <td style="text-align:center;">
            {if $registro.estado == 'A'}
                <b>Activo</b>
            {elseif $registro.estado == 'P'}
                Proceso alta
            {else}
                Inactivo
            {/if}
        </td>
        <td style="padding-left:15px;">
            <span class="btn btn-medium-white">
                <a href="pacientes_ver.php?id={$registro.id}">
                    Ver
                </a>
            </span>
        </td>
    </tr>
    {/foreach}
    {else}
    <tr>
        <td colspan="7" style="text-align:center;padding:15px;">
            No se ha encontrado ningún registro.
        </td>
    </tr>
    {/if}
{*}}}*}
</table>

<br>

<div class="paginador">
{* Paginador {{{*}
    <b>{$listado.paginador->getNumResults()}</b> pacientes  

    {if $listado.paginador->haveToPaginate()}
        en {$listado.paginador->getLastPage()}
        páginas
        &nbsp; &nbsp; &nbsp;
        {$listado.enlacesPaginador}
    {/if}
{* }}} *}
</div>

{* }}} *}