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

{* ../../www/pruebas_pacientes.php *}

<h2 class="titulo_seccion" style="margin-bottom:0;">
    Historial de pruebas del paciente:
    <span class="resaltar">
        {$paciente.nombre}
        {$paciente.apellidos}
    </span>
</h2>

<div style="margin:0;padding-bottom:17px;">
    <span class="btn btn-medium-white" style="float:right;">
        <a href="pacientes_ver.php?id={$paciente.id}">Volver a la ficha del paciente</a>
    </span>
    <span class="btn btn-medium-white" style="float:right;margin-right:20px;">
        <a href="pacientes.php">Volver al listado de pacientes</a>
    </span>
</div>

<br>
<br>

            {* nombre - apellidos - estado {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Nombre:
                    </th>

                    <td>
                        {$paciente.nombre}
                    </td>

                    <th valign="top">
                        Apellidos:
                    </th>

                    <td>
                        {$paciente.apellidos}
                    </td>

                    <th valign="top">
                        Estado:
                    </th>

                    <td style="width:140px;">
                        {if $paciente.estado == 'A'}
                            <b>Activo</b>
                        {elseif $paciente.estado == 'P'}
                            Proceso alta
                        {else}
                            Inactivo
                        {/if}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {* fecha_nacimiento - sexo - no_tis {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Nacimiento:
                    </th>

                    <td>
                        <b>({$paciente->calcularEdad()} años)</b>
                        {$paciente.fecha_nacimiento|date_format:"%d/%m/%Y"}
                    </td>

                    <th valign="top">
                        Sexo:
                    </th>

                    <td>
                        {$paciente.sexo}
                    </td>

                    <th valign="top">
                        TIS:
                    </th>

                    <td>
                        {$paciente.no_tis}
                    </td>
                </tr>
            </table>
            {* }}} *}

<br>

{* Listado de registros {{{ *}

<div class="paginador">
{* Paginador {{{*}

    <b>{$listado.paginador->getNumResults()}</b> pruebas  

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

        <th style="width:120px;text-align:center;">
            {$listado.orden->table('fecha')}
        </th>
        <th>
            Resultados
        </th>
        <th width="50" style="text-align:center;">
            &nbsp;
        </th>
    </tr>

    {if $listado.paginador->getNumResults() > 0}
    {foreach item=registro from=$listado.coleccion}
    <tr class="{cycle values="fila_par,fila_impar"}">

        <td style="text-align:center;">
            <b>{$registro.fecha|date_format:"%d/%m/%Y"}</b>
            {$registro.fecha|date_format:"%H:%M"}
        </td>
        <td>
            {$registro->mostrarResultado()}
        </td>
        <td style="padding-left:15px;">
            <span class="btn btn-medium-white">
                <a href="pruebas_pacientes_ver.php?id={$registro.id}">
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
    <b>{$listado.paginador->getNumResults()}</b> pruebas  

    {if $listado.paginador->haveToPaginate()}
        en {$listado.paginador->getLastPage()}
        páginas
        &nbsp; &nbsp; &nbsp;
        {$listado.enlacesPaginador}
    {/if}
{* }}} *}
</div>

{* }}} *}

<div class="botones_final">
    <span class="btn btn-medium-white">
        <a href="pacientes_ver.php?id={$paciente.id}">Volver a la ficha del paciente</a>
    </span>
</div>
