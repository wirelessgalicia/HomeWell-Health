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

{* ../../www/pruebas_pacientes_altas.php *}

<h2 class="titulo_seccion">
    Programar nueva prueba al paciente:
    <span class="resaltar">
        {$paciente.nombre}
        {$paciente.apellidos}
    </span>
</h2>

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

{assign var='form' value=$arrAlta}
<form {$form.attributes} style="display:inline;">
{$form.elements_name._qf__alta_registro.html}

            {* id_tipo_prueba {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Prueba:
                    </th>

                    <td>
                        {$form.elements_name.id_tipo_prueba.html}

                        {if $form.elements_name.id_tipo_prueba.error}
                        <div class="error_formulario">
                            {$form.elements_name.id_tipo_prueba.error}
                        </div>
                        {/if}
                    </td>

                </tr>
            </table>
            {* }}} *}
            {* fecha {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Fecha:
                    </th>

                    <td>
                        {$form.elements_name.fecha.html}

                        &nbsp; &nbsp;

                        {$form.elements_name.repetir.html}
                        Repetir
                        {$form.elements_name.numero_repeticiones.html}
                        veces cada
                        {$form.elements_name.incremento_dias.html}
                        días

                        {if $form.elements_name.fecha.error}
                        <div class="error_formulario">
                            {$form.elements_name.fecha.error}
                        </div>
                        {/if}

                        {if $form.elements_name.numero_repeticiones.error}
                        <div class="error_formulario">
                            {$form.elements_name.numero_repeticiones.error}
                        </div>
                        {/if}

                        {if $form.elements_name.incremento_dias.error}
                        <div class="error_formulario">
                            {$form.elements_name.incremento_dias.error}
                        </div>
                        {/if}
                    </td>
                </tr>
            </table>
            {* }}} *}

<div class="botones_final">
    <span class="btn btn-medium-green">
        {$form.elements_name.enviar.html}
    </span>
    <span class="btn btn-medium-white">
        <a href="pacientes_ver.php?id={$paciente.id}">Cancelar alta de prueba</a>
    </span>
</div>

</form>
