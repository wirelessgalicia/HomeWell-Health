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

{include file="cabecera.tpl"}

<img src="imagenes/superior.png" style="margin-left:auto;margin-right:auto;display:block;margin-top:20px;">

<table id="layout" cellpadding="0" cellspacing="0"> 
    <tr>
        <td valign="top" id="layout-principal">
            <div class="container">

                <div style="text-align:right;color:#8d6239;font-weight:normal;">
                    Usuario:
                    <span style="font-weight:bold;">
                        {$usuario.usuario}
                    </span>
                    -
                    <a href="logout.php" class="boton" style="color:red;">(Salir)</a>
                </div>

                <div class="principal" style="margin-left:10px;">
                    {include file=$principal}
                </div>

                <div style="clear:both;font-size:1px;">&nbsp;</div>
            </div>

            <div id="pie">
               <div style="text-align:right;">
                    © 2012 Web HomeWell-Health. Todos los derechos reservados
                </div>
            </div>

        </td>
    </tr>
</table>

</body>
</html>
