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

{* ../../www/login.php *}

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>:: Home Well Health ::</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <link href="styles.css" rel="stylesheet" type="text/css" />
{* Javascript {{{ *}
{literal}
<script language="Javascript">

onload=function() { 
    document.forms[0].usuario.focus();
}
</script>
{/literal}
{* Fin Javascript }}} *}
</head>
<body>

<img src="imagenes/superior.png" style="margin-left:auto;margin-right:auto;display:block;margin-top:20px;">

<table id="layout" cellpadding="0" cellspacing="0"> 
    <tr>
        <td valign="top" id="layout-principal">
            <div class="containerLogin">

            {assign var='form' value=$formulario} 

                {if 
                    $form.elements_name.usuario.error
                    || $form.elements_name.clave.error
                }
                <div class="aviso_login">
                    <img width="16" height="14" alt="caution" src="imagenes/icono_error.gif"/>
                    &nbsp; &nbsp;
                    {$form.elements_name.usuario.error}
                    {$form.elements_name.clave.error}
                </div>
                {/if}

                <h1 class='login'>Identificación usuarios</h1>

                <div class="caja_login">
                    <form {$form.attributes}>
                    {$form.elements_name._qf__login.html}


                    <label for="username">Usuario:</label>
                    <br>
                    {$form.elements_name.usuario.html}

                    <br>
                    <br>

                    <label for="password">Clave:</label>
                    <br>
                    {$form.elements_name.clave.html}

                    <br>
                    <br>

                    AVISO: Para el correcto funcionamiento de la aplicación, el navegador tiene que tener activo las cookies y JavaScript.<br/>

                    <br>

                    <input type="submit" value="Entrar" class="boton_login"/>

                    </form>
                </div>
            </div>

            <div id="pie">
               <div style="text-align:center;">
                    © 2012 Web HomeWell-Health. Todos los derechos reservados
                </div>
            </div>

        </td>
    </tr>
</table>

</body>
</html>
