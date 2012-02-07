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

{* ../../www/pruebas_pacientes_ver.php *}

<h2 class="titulo_seccion">
    Detalles de la prueba programada al paciente:

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

<div class="wrap">
    <ul class="tabs">
        <li><a href="#ficha" class="w3">Datos prueba</a></li>
        <li><a href="#formulario" class="w3">Modificar</a></li>
    </ul>

    <div class="panes">
        <div id="ficha">
            {* Ficha Registro {{{ *}
            {* tipo {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Tipo:
                    </th>

                    <td>
                        {$pruebaPaciente.PruebasTipos.nombre}
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
                        <b>{$pruebaPaciente.fecha|date_format:"%d/%m/%Y"}</b>
                        {$pruebaPaciente.fecha|date_format:"%H:%M"}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {* estado {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Estado:
                    </th>

                    <td>
                        {$pruebaPaciente->descripcionEstado()}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {if $pruebaPaciente->tienePrueba('Peso')}
            {* resultado {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Resultado:
                    </th>

                    <td>
                        {$pruebaPaciente->mostrarResultadoPeso()}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {/if}
            {if $usuario.usuario == 'admin'}
            {* debug {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        JSON:
                        (debuggeo)
                        <a href="envioPrueba.php?id={$pruebaPaciente.id}" target="_blank">Enviar</a>
                    </th>

                    <td>
                        {$pruebaPaciente->generarJSON()}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {* resultado {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Resultado:
                        (debuggeo)
                    </th>

                    <td>
                        {$pruebaPaciente->resultado}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {/if}
            {* Fin Ficha Registro }}} *}
        </div>

        <div id="formulario">
            {* Formulario Registro {{{ *}

            {assign var='form' value=$arrModificacion}
            <form {$form.attributes} style="display:inline;">
            {$form.elements_name._qf__modificar_registro.html}
            {* tipo {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Tipo:
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

                        {if $form.elements_name.fecha.error}
                        <div class="error_formulario">
                            {$form.elements_name.fecha.error}
                        </div>
                        {/if}
                    </td>
                </tr>
            </table>
            {* }}} *}
            {* estado {{{ *}
            <table class="ficha">
                <tr>
                    <th valign="top">
                        Estado:
                    </th>

                    <td>
                        {$form.elements_name.estado.html}

                        {if $form.elements_name.estado.error}
                        <div class="error_formulario">
                            {$form.elements_name.estado.error}
                        </div>
                        {/if}
                    </td>
                </tr>
            </table>
            {* }}} *}
        {* Botón Enviar {{{ *}
            <br>
            <table style="width:100%;">
                <tr>
                    <td>
                        <span class="btn btn-medium-green">
                            {$form.elements_name.enviar.html}
                        </span>
                    </td>
                    <td style="text-align:right;">
                        &nbsp;
                    </td>
                </tr>
            </table>
        {* Fin Botón Enviar *}

        </form>

        {* Fin Formulario Registro }}} *}
            </form>
            {* Fin Formulario Registro }}} *}
        </div>
    </div>
</div>


{if $pruebaPaciente->tienePrueba('Saturacion')}
<div id="saturacion" style="width: 100%; height: 400px; margin-top:20px; margin-bottom:20px;"></div>
{/if}

{if $pruebaPaciente->tienePrueba('Rpm')}
<div id="rpm" style="width: 100%; height: 400px; margin-top:20px; margin-bottom:20px;"></div>
{/if}

{if $pruebaPaciente->tienePrueba('Pulso')}
    <div id="pulso" style="width: 100%; height: 400px; margin-top:20px; margin-bottom:20px;"></div>
{/if}

<div class="botones_final">
    <span class="btn btn-medium-white">
        <a href="pacientes_ver.php?id={$paciente.id}">Volver a la ficha del paciente</a>
    </span>
</div>

{* Javascript {{{ *}
<script language="Javascript">
{literal}

var chartSaturacion;
var chartRPM;
var chartPulso;

$(document).ready(function() {

    $('ul.tabs').tabs('div.panes > div');

{/literal}
{if $pruebaPaciente->tienePrueba('Saturacion')}
{literal}
    // Saturación {{{
   chartSaturacion = new Highcharts.Chart({
      chart: {
         renderTo: 'saturacion',
         defaultSeriesType: 'line',
         marginRight: 25,
         marginBottom: 45
      },
      title: {
         text: 'Saturación',
         x: -20 //center
      },
      subtitle: {
         text: '',
         x: -20
      },

      xAxis: {
         labels: {step: 4},
         categories: 
         {/literal}{$pruebaPaciente->devolverEscala('Saturacion')}{literal}
      },

      yAxis: {
         title: {
            text: 'Saturación'
         },
         plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
         }]
      },
      tooltip: {
         formatter: function() {
                   return '<b>'+ this.series.name +'</b><br/>'+
               this.x +': '+ this.y;
         }
      },
      series: [{
         name: 'Saturación',
         data: 
         {/literal}{$pruebaPaciente->devolverValores('Saturacion')}{literal}
      }]
   });

   // }}}
{/literal}
{/if}

{if $pruebaPaciente->tienePrueba('Rpm')}
{literal}
    // Rpm {{{
   chartRPM = new Highcharts.Chart({
      chart: {
         renderTo: 'rpm',
         defaultSeriesType: 'line',
         marginRight: 25,
         marginBottom: 45
      },
      title: {
         text: 'RPM',
         x: -20 //center
      },
      subtitle: {
         text: '',
         x: -20
      },

      xAxis: {
         labels: {step: 4},
         categories: 
         {/literal}{$pruebaPaciente->devolverEscala('Rpm')}{literal}
      },

      yAxis: {
         title: {
            text: 'RPM'
         },
         plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
         }]
      },
      tooltip: {
         formatter: function() {
                   return '<b>'+ this.series.name +'</b><br/>'+
               this.x +': '+ this.y;
         }
      },
      series: [{
         name: 'RPM',
         data: 
         {/literal}{$pruebaPaciente->devolverValores('Rpm')}{literal}
      }]
   });

   // }}}
{/literal}
{/if}

{if $pruebaPaciente->tienePrueba('Pulso')}
{literal}
    // Pulso {{{
   chartPulso = new Highcharts.Chart({
      chart: {
         renderTo: 'pulso',
         defaultSeriesType: 'line',
         marginRight: 25,
         marginBottom: 45
      },
      title: {
         text: 'Pulso',
         x: -20 //center
      },
      subtitle: {
         text: '',
         x: -20
      },

      xAxis: {
         labels: {step: 4},
         categories: 
         {/literal}{$pruebaPaciente->devolverEscala('Pulso')}{literal}
      },

      yAxis: {
         title: {
            text: 'Pulso'
         },
         plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
         }]
      },
      tooltip: {
         formatter: function() {
                   return '<b>'+ this.series.name +'</b><br/>'+
               this.x +': '+ this.y;
         }
      },
      series: [{
         name: 'Pulso',
         data: 
         {/literal}{$pruebaPaciente->devolverValores('Pulso')}{literal}
      }]
   });

   // }}}
{/literal}
{/if}
   
{literal}
});

</script>
{/literal}
{* Fin Javascript }}} *}
