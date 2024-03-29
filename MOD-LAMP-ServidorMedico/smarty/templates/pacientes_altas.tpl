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

{* ../../www/pacientes_altas.php *}

<h2 class="titulo_seccion">
    Alta paciente
</h2>

{assign var='form' value=$arrAlta}
<form {$form.attributes} style="display:inline;">
{$form.elements_name._qf__alta_registro.html}

            {* nombre - apellidos {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Nombre:
                    </th>

                    <td>
                        {$form.elements_name.nombre.html}

                        {if $form.elements_name.nombre.error}
                        <div class="error_formulario">
                            {$form.elements_name.nombre.error}
                        </div>
                        {/if}
                    </td>

                    <th valign="top">
                        Apellidos:
                    </th>

                    <td>
                        {$form.elements_name.apellidos.html}

                        {if $form.elements_name.apellidos.error}
                        <div class="error_formulario">
                            {$form.elements_name.apellidos.error}
                        </div>
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
                        {$form.elements_name.fecha_nacimiento.html}

                        {if $form.elements_name.fecha_nacimiento.error}
                        <div class="error_formulario">
                            {$form.elements_name.fecha_nacimiento.error}
                        </div>
                        {/if}
                    </td>

                    <th valign="top">
                        Sexo:
                    </th>

                    <td>
                        {$form.elements_name.sexo.html}

                        {if $form.elements_name.sexo.error}
                        <div class="error_formulario">
                            {$form.elements_name.sexo.error}
                        </div>
                        {/if}
                    </td>

                    <th valign="top">
                        TIS:
                    </th>

                    <td>
                        {$form.elements_name.no_tis.html}

                        {if $form.elements_name.no_tis.error}
                        <div class="error_formulario">
                            {$form.elements_name.no_tis.error}
                        </div>
                        {/if}
                    </td>
                </tr>
            </table>
            {* }}} *}
            <br>
            {* domicilio {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Domicilio:
                    </th>

                    <td>
                        {$form.elements_name.domicilio.html}

                        {if $form.elements_name.domicilio.error}
                        <div class="error_formulario">
                            {$form.elements_name.domicilio.error}
                        </div>
                        {/if}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {* telefono1 - telefono2 {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Teléfono 1:
                    </th>

                    <td>
                        {$form.elements_name.telefono1.html}

                        {if $form.elements_name.telefono1.error}
                        <div class="error_formulario">
                            {$form.elements_name.telefono1.error}
                        </div>
                        {/if}
                    </td>

                    <th valign="top">
                        Teléfono 2:
                    </th>

                    <td>
                        {$form.elements_name.telefono2.html}

                        {if $form.elements_name.telefono2.error}
                        <div class="error_formulario">
                            {$form.elements_name.telefono2.error}
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
        <a href="pacientes.php">Cancelar Alta</a>
    </span>
</div>

</form>
