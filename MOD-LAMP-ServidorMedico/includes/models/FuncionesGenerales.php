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

class FuncionesGenerales {


    public static function leerParametroNumerico($nombre_parametro,$valor_defecto){
    //{{{

        //Empezamos mirando si hay un get.

        if (isset($_GET[$nombre_parametro]) and is_numeric($_GET[$nombre_parametro])){
            return $_GET[$nombre_parametro];
        }

        if (isset($_POST[$nombre_parametro]) and is_numeric($_POST[$nombre_parametro])){
            return $_POST[$nombre_parametro];
        }

        return $valor_defecto;
    //}}}
    }

    public static function leer_parametro_texto($nombre_parametro,$valor_defecto){
    //{{{

        //Empezamos mirando si hay un get.

        if (isset($_GET[$nombre_parametro])){
            return $_GET[$nombre_parametro];
        }

        if (isset($_POST[$nombre_parametro])){
            return $_POST[$nombre_parametro];
        }

        return $valor_defecto;
    //}}}
    }

    public static function resize($filename, $dest, $width, $height, $pictype = "") {
        // {{{
      $format = strtolower(substr(strrchr($filename,"."),1));
      switch($format)
      {
       case 'gif' :
       $type ="gif";
       $img = imagecreatefromgif($filename);
       break;
       case 'png' :
       $type ="png";
       $img = imagecreatefrompng($filename);
       break;
       case 'jpg' :
       $type ="jpg";
       $img = imagecreatefromjpeg($filename);
       break;
       case 'jpeg' :
       $type ="jpg";
       $img = imagecreatefromjpeg($filename);
       break;
       default :
       die ("ERROR; UNSUPPORTED IMAGE TYPE");
       break;
      }

      list($org_width, $org_height) = getimagesize($filename);
      $xoffset = 0;
      $yoffset = 0;
      if ($pictype == "thumb") // To minimize destortion
      {
       if ($org_width / $width > $org_height/ $height)
       {
         $xtmp = $org_width;
         $xratio = 1-((($org_width/$org_height)-($width/$height))/2);
         $org_width = $org_width * $xratio;
         $xoffset = ($xtmp - $org_width)/2;
       }
       elseif ($org_height/ $height > $org_width / $width)
       {
         $ytmp = $org_height;
         $yratio = 1-((($width/$height)-($org_width/$org_height))/2);
         $org_height = $org_height * $yratio;
         $yoffset = ($ytmp - $org_height)/2;
       }
      //Added this else part -------------
      } else {   
         $xtmp = $org_width/$width;
         $new_width = $width;
         $new_height = $org_height/$xtmp;
         if ($new_height > $height){
           $ytmp = $org_height/$height;
           $new_height = $height;
           $new_width = $org_width/$ytmp;
         }

        // No queremos que la imagen se estire, o sea, si el nuevo tamaña es mayor que el original, no estiramos.
        // Lo dejamos en ese máximo tamaño...
        

         if ($new_width<$org_width && $new_height<$org_height) {
             $width = round($new_width);
             $height = round($new_height);
         }else{
             $width = $org_width;
             $height = $org_height;
         }
      }
     

      $img_n=imagecreatetruecolor ($width, $height);
      imagecopyresampled($img_n, $img, 0, 0, $xoffset, $yoffset, $width, $height, $org_width, $org_height);

      if($type=="gif")
      {
       imagegif($img_n, $dest);
      }
      elseif($type=="jpg")
      {
       imagejpeg($img_n, $dest);
      }
      elseif($type=="png")
      {
       imagepng($img_n, $dest);
      }
      elseif($type=="bmp")
      {
       imagewbmp($img_n, $dest);
      }
      Return true;
      // }}}
    }

    public static function mostrarArray ($array){
    //{{{
        print "<div style='margin:20px;padding:15px;border:1px solid red;background-color:#eaeaea;font-weight:bold;color:#000;'>";
        print "<pre>";
        print_r($array);
        print "</pre>";
        print "</div>";

    //}}}
    }

    public static function generarError ($descripcion,$linea=null,$archivo=null){
    //{{{
        print "<div style='margin:20px;padding:15px;border:1px solid red;background-color:#eaeaea;font-weight:bold;color:#000;'>";
            print "<pre>";
                print "Descripción: ";
                print $descripcion;

                if ($linea){
                    print "<br>";
                    print "Linea: ";
                    print $linea;
                }

                if ($archivo){
                    print "<br>";
                    print "Archivo: ";
                    print $archivo;
                }

            print "</pre>";
        print "</div>";
        exit;

    //}}}
    }

    public static function cadena_now (){
    //{{{

        // Calculo el unix_stamp
        $arrFechaActual=getdate();
        $fecha_actual=
            $arrFechaActual['year']
            . '-'
            . $arrFechaActual['mon']
            . '-'
            . $arrFechaActual['mday']
            ;

        return $fecha_actual;

    //}}}
    }

    public static function cadena_now_completa (){
    //{{{

        // Calculo el unix_stamp
        $arrFechaActual=getdate();
        $fecha_actual=
            $arrFechaActual['year']
            . '-'
            . $arrFechaActual['mon']
            . '-'
            . $arrFechaActual['mday']
            . ' '
            . $arrFechaActual['hours']
            . ':'
            . $arrFechaActual['minutes']
            . ':'
            . $arrFechaActual['seconds']
            ;

        return $fecha_actual;

    //}}}
    }

    public static function crear_calendario(
        $tipo_presentacion,
        $dia,
        $mes,
        $ano
        ){
    //{{{

        /**
         * Funcion que crea una estructura de datos con todos
         * los datos de la agenda, recibe los siguientes pa-
         * rámetros
         * 
         *  - $tipo_presentación: 
         *      0 -> 1 mes (defecto)
         *      1 -> 1 semana 
         * 
         *  - $dia,$mes y $ano: cualquier fecha que esté dentro del tipo
         *    de presentación (mes o semana).
         * 
         * La función devuelve un array con toda la información
         */


        // Algunos array de cambio

            $arr_correccion_nombre_mes=array(
                1=>'Enero',
                2=>'Febrero',
                3=>'Marzo',
                4=>'Abril',
                5=>'Mayo',
                6=>'Junio',
                7=>'Julio',
                8=>'Agosto',
                9=>'Septiembre',
                10=>'Octubre',
                11=>'Noviembre',
                12=>'Diciembre'
                );

            $arr_correccion_dia_semana=array(
                0=>'Domingo',
                1=>'Lunes',
                2=>'Martes',
                3=>'Miércoles',
                4=>'Jueves',
                5=>'Viernes',
                6=>'Sábado'
                );

        // Comprobamos los parámetros de entrada

        if ($tipo_presentacion!=1) $tipo_presentacion=0;

        if (!(
            is_numeric($dia)
            && is_numeric($mes)
            && is_numeric($ano)
            && checkdate($mes,$dia,$ano)
            )){

            die ('Fecha no válida al llamar a la función crear_calendario');
            
        }

        // Calculo la fecha solicitada
        $fecha_ts=mktime(
                0,
                0,
                0,
                $mes,
                $dia,
                $ano
                );

        $arrFecha=getdate($fecha_ts);
        $arrFecha['weekday']=$arr_correccion_dia_semana[$arrFecha['wday']];
        $arrFecha['month']=$arr_correccion_nombre_mes[$arrFecha['mon']];

        $fecha_mes_anterior_ts=mktime(
                0,
                0,
                0,
                $mes-1,
                1,
                $ano
                );

        $fecha_mes_siguiente_ts=mktime(
                0,
                0,
                0,
                $mes+1,
                1,
                $ano
                );

        // Calculo del mes siguiente y anterior a este

        // Calculo la fecha actual (a las 0:00)
        $arrHoy=getdate();
        $hoy_ts=mktime(
                0,
                0,
                0,
                $arrHoy['mon'],
                $arrHoy['mday'],
                $arrHoy['year']
                );

        // A partir de la fecha y del tipo de presentación, calculamos
        // el primer y último dia de nuestro calendario.

        if ($tipo_presentacion==0){
        
            // Presentación tipo mes, tenemos que buscar el primer día del
            // mes y si no fuera lunes, buscar el ultimo lunes del mes anterior

            $primer_dia_mes_ts=mktime(
                0,
                0,
                0,
                $mes,
                1,
                $ano
                );

            $primer_dia_mes_arr=getdate($primer_dia_mes_ts);

            // Es lunes?
            $arr_correccion=array(-5,1,0,-1,-2,-3,-4,-5);

            $primer_dia_calendario_ts=mktime(
                0,
                0,
                0,
                $mes,
                $arr_correccion[$primer_dia_mes_arr['wday']],
                $ano
                );

            $primer_dia_periodo_ts=$primer_dia_calendario_ts;
            $primer_dia_activo_periodo_ts=$primer_dia_mes_ts;

            // Buscamos el último día del mes y si no fuera domingo
            // buscar el siguiente domingo del mes siguiente

            $ultimo_dia_mes_ts=mktime(
                0,
                0,
                0,
                $mes+1,
                0,
                $ano
                );

            $ultimo_dia_mes_arr=getdate($ultimo_dia_mes_ts);

            // Es domingo?
            $arr_correccion=array(0,6,5,4,3,2,1);

            $ultimo_dia_calendario_ts=mktime(
                0,
                0,
                0,
                $mes+1,
                $arr_correccion[$ultimo_dia_mes_arr['wday']],
                $ano
                );

            $ultimo_dia_periodo_ts=$ultimo_dia_calendario_ts;
            $ultimo_dia_activo_periodo_ts=$ultimo_dia_mes_ts;

        }elseif ($tipo_presentacion==1) {

            // Presentación tipo semana, tenemos que buscar el primer día de
            // la semana

            $fecha_seleccionada_ts=mktime(
                0,
                0,
                0,
                $mes,
                $dia,
                $ano
                );

            $fecha_seleccionada_arr=getdate($fecha_seleccionada_ts);

            // Es lunes?
            $arr_correccion=array(6,0,1,2,3,4,1);

            $primer_dia_semana_ts=mktime(
                0,
                0,
                0,
                $mes,
                $dia - $arr_correccion[$fecha_seleccionada_arr['wday']],
                $ano
                );

            $primer_dia_periodo_ts=$primer_dia_semana_ts;
            $primer_dia_activo_periodo_ts=$primer_dia_semana_ts;

            // Buscamos el último día de la semana

            $arr_correccion=array(0,6,5,4,3,2,1);
            $ultimo_dia_semana_ts=mktime(
                0,
                0,
                0,
                $mes,
                $dia + $arr_correccion[$fecha_seleccionada_arr['wday']],
                $ano
                );

            $ultimo_dia_periodo_ts=$ultimo_dia_semana_ts;
            $ultimo_dia_activo_periodo_ts=$ultimo_dia_semana_ts;
        
        }

        // Iteramos entre las fechas y creamos el resto

        $arrDiasCalendario=array();
        $arrBusquedaCalendario=array();

        $dia_ts=$primer_dia_periodo_ts;
        $indice=0;

        while ($dia_ts<=$ultimo_dia_periodo_ts){

            $arrDia=getdate($dia_ts);


            // Vemos la clase del dia (inactivos, normales, actual, fin_semana)

            if ($dia_ts==$hoy_ts){
                $clase='actual';
            }elseif ($dia_ts<$primer_dia_activo_periodo_ts || $dia_ts>$ultimo_dia_activo_periodo_ts){
                $clase='inactivo';
            }elseif ( $arrDia['wday']==0 || $arrDia['wday']==6){
                $clase='fin_semana';
            }else{
                $clase='normal';
            }

            // Controlamos el salto de linea
            if ($arrDia['wday']==1){
                $empieza_fila=1;
                $termina_fila=0;
            } elseif ($arrDia['wday']==0){
                $termina_fila=1;
                $empieza_fila=0;
            }else{
                $empieza_fila=0;
                $termina_fila=0;
            }

            // Ponemos en español el día de la semana

            $arrDia['weekday']=$arr_correccion_dia_semana[$arrDia['wday']];

            // Ponemos en español el nombre del mes

            $arrDia['month']=$arr_correccion_nombre_mes[$arrDia['mon']];
            
            // Metemos todos los datos en el array
            $arrDiasCalendario[$indice]=array(
                'clase'=>$clase,
                'empieza_fila'=>$empieza_fila,
                'termina_fila'=>$termina_fila,
                'fecha'=>$arrDia,
                );

            // Creamos otro array para facilitar las futuras
            // busquedas que nos popularan el calendario con citas
            $arrBusquedaCalendario[$arrDia[0]]=$indice;

            // Aumentamos un dia, lo hacemos de una forma correcta para no tener problemas con
            // los cambios de hora que ocurren dos veces al año

            $dia_ts=mktime(
                0,
                0,
                0,
                $arrDia['mon'],
                $arrDia['mday']+1,
                $arrDia['year']
                );
            $indice++;
        }

        // Construimos un array con los datos del calendario

        $arrCalendario=array(
            'tipo_calendario' => $tipo_presentacion,
            'fecha' => $arrFecha,
            'fecha_mes_anterior' => getdate($fecha_mes_anterior_ts),
            'fecha_mes_siguiente' => getdate($fecha_mes_siguiente_ts),
            'fecha_actual' => $arrHoy,
            //'primer_dia_periodo_ts'=>$primer_dia_periodo_ts,
            //'primer_dia_activo_periodo_ts'=>$primer_dia_activo_periodo_ts,
            //'ultimo_dia_periodo_ts'=>$ultimo_dia_periodo_ts,
            //'ultimo_dia_activo_periodo_ts'=>$ultimo_dia_activo_periodo_ts,
            //'primer_dia_periodo'=>getdate($primer_dia_periodo_ts),
            //'primer_dia_activo_periodo'=>getdate($primer_dia_activo_periodo_ts),
            //'ultimo_dia_periodo'=>getdate($ultimo_dia_periodo_ts),
            //'ultimo_dia_activo_periodo'=>getdate($ultimo_dia_activo_periodo_ts),
            'calendario' => $arrDiasCalendario,
            'calendario_busqueda' => $arrBusquedaCalendario,
            );

        return $arrCalendario;

    //}}}
    }

    public static function construir_array_dias($tsFechaInicio, $tsFechaFinal) {
    //{{{

        // La función recibe dos fechas y devuelve un array cuyas claves son los timestamp de 
        // cada día entre las dos fechas

        $arrDias = array();

        if(!(
            is_numeric($tsFechaInicio)
            && $tsFechaInicio > 0
            && is_numeric($tsFechaFinal)
            && $tsFechaFinal > 0
            && $tsFechaInicio < $tsFechaFinal
        )){
            return $arrDias;
        }

        $tsFechaBarrido = $tsFechaInicio;

        do{

            $arrDias[$tsFechaBarrido] = array();

            $tsFechaBarrido = mktime(
                0, 
                0, 
                0, 
                date('m', $tsFechaBarrido),   
                date('d', $tsFechaBarrido) + 1,
                date('Y', $tsFechaBarrido)
            );

        } while ($tsFechaBarrido <= $tsFechaFinal);

        return $arrDias;

    //}}}
    }

    public static function formularioAArray(&$formulario) {
    // {{{
        // Funcion para volcar un formulario a un array
        // antes de pasarselo a la plantilla.

        $array=$formulario->toArray();
        
        array_walk( $array['elements'], 'FuncionesGenerales::create_elements_name', &$array);

        return $array;

    //}}}
    }

    public static function create_elements_name($value, $key,&$array) {
    // {{{
        // Funcion llamada por el array_walk para crear el nuevo array elements_name dentro
        // del array de elementos del formulario

        if ($value['type']=='radio'){
            $array['elements_name'][$value['name'] . $value['value']]=$array['elements'][$key];
        
        }else{
            $array['elements_name'][$value['name']]=$array['elements'][$key];
        }
    //}}}
    }

    public static function anoActual() {
    // {{{

        $fecha_actual=getdate();
        return $fecha_actual['year'];
    //}}}
    }

    public static function normalizarFecha($strFecha) {
    // {{{

        // Función para tratar el problema de que mssql genera fecha del tipo
        // Jul 20 1960 12:00:00:000AM  que no entiende strtotime
        // lo que haremos será convertir esa cadena en 
        // Jul 20 1960 12:00:00 AM

        return preg_replace('/:\d{3,}/', '', $strFecha);

    //}}}
    }

    public static function extensionUpload($nombre_upload) {
    // {{{

        if ( preg_match('/\.(\w+)$/', $nombre_upload, $matches) == 1){
            return '.' . $matches[1];
        }else{
            return '';
        }

    //}}}
    }

    public static function formatear_fecha_mes ($timestamp){
    //{{{

        $arrFecha=getdate($timestamp);

        $arrMeses = array(
            1 => 'enero',
            2 => 'febrero',
            3 => 'marzo',
            4 => 'abril',
            5 => 'mayo',
            6 => 'junio',
            7 => 'julio',
            8 => 'agosto',
            9 => 'septiembre',
            10 => 'octubre',
            11 => 'noviembre',
            12 => 'diciembre',
        );

        $arrDiasSemana = array(
            0 => 'domingo',
            1 => 'lunes',
            2 => 'martes',
            3 => 'miercoles',
            4 => 'jueves',
            5 => 'viernes',
            6 => 'sábado',
        );

        $strFecha = 
            $arrMeses[$arrFecha['mon']]
            . ' de '
            . $arrFecha['year']
            ;

        return $strFecha;

    //}}}
    }

    public static function formatearFecha($fecha){
    //{{{

        $arrFecha = getdate(strtotime($fecha));

        $arrMeses = array(
            1 => 'enero',
            2 => 'febrero',
            3 => 'marzo',
            4 => 'abril',
            5 => 'mayo',
            6 => 'junio',
            7 => 'julio',
            8 => 'agosto',
            9 => 'septiembre',
            10 => 'octubre',
            11 => 'noviembre',
            12 => 'diciembre',
        );

        $strFecha = 
            $arrFecha['mday']
            . '-'
            . $arrMeses[$arrFecha['mon']]
            . '-'
            . $arrFecha['year']
            ;

        return $strFecha;

    //}}}
    }

    public static function formatearFechaHora($fecha){
    //{{{

        $arrFecha = getdate(strtotime($fecha));

        $arrMeses = array(
            1 => 'enero',
            2 => 'febrero',
            3 => 'marzo',
            4 => 'abril',
            5 => 'mayo',
            6 => 'junio',
            7 => 'julio',
            8 => 'agosto',
            9 => 'septiembre',
            10 => 'octubre',
            11 => 'noviembre',
            12 => 'diciembre',
        );

        $strFecha = 
            $arrFecha['mday']
            . '-'
            . $arrMeses[$arrFecha['mon']]
            . '-'
            . $arrFecha['year']
            . ' '
            . $arrFecha['hours']
            . ':'
            . sprintf('%02s',$arrFecha['minutes'])
            ;

        return $strFecha;

    //}}}
    }

    public static function calcular_variantes_fechas($tsFecha) {
    //{{{

        $arrRespuesta = array(
            'tsLunesAnterior' => null,
            'arrLunesAnterior' => null,
            'tsDomingoSiguiente' => null,
            'arrDomingoSiguiente' => null,
            'tsComienzoMes' => null,
            'arrComienzoMes' => null,
            'tsFinalMes' => null,
            'arrFinalMes' => null,
            'tsHoy' => null,
            'arrHoy' => null,
            'tsAyer' => null,
            'arrAyer' => null,
            'tsManana' => null,
            'arrManana' => null,
        );

        $arrFecha= getdate($tsFecha);

        // Lunes Anterior a la fecha

        if ($arrFecha['wday'] == 0){
            // domingo
            $quitar_dias = 6;
        }else{
            $quitar_dias = $arrFecha['wday']-1;
        }

        $tsLunesAnterior = mktime(
            0,
            0,
            0,
            $arrFecha['mon'],
            $arrFecha['mday'] - $quitar_dias,
            $arrFecha['year']
        );

        $arrRespuesta['tsLunesAnterior'] = $tsLunesAnterior;
        $arrLunesAnterior = getdate($tsLunesAnterior);
        $arrRespuesta['arrLunesAnterior'] = $arrLunesAnterior;

        // Domingo siguiente a la fecha

        $tsDomingoSiguiente = mktime(
            0,
            0,
            0,
            $arrLunesAnterior['mon'],
            $arrLunesAnterior['mday'] + 6,
            $arrLunesAnterior['year']
        );

        $arrRespuesta['tsDomingoSiguiente'] = $tsDomingoSiguiente;
        $arrRespuesta['arrDomingoSiguiente'] = getdate($tsDomingoSiguiente);

        // Comienzo Mes

        $tsComienzoMes = mktime(
            0,
            0,
            0,
            $arrFecha['mon'],
            1,
            $arrFecha['year']
        );

        $arrRespuesta['tsComienzoMes'] = $tsComienzoMes;
        $arrRespuesta['arrComienzoMes'] = getdate($tsComienzoMes);

        // Final Mes

        $tsFinalMes = mktime(
            0,
            0,
            0,
            $arrFecha['mon']+1,
            0,
            $arrFecha['year']
        );

        $arrRespuesta['tsFinalMes'] = $tsFinalMes;
        $arrRespuesta['arrFinalMes'] = getdate($tsFinalMes);

        // Hoy

        $tsHoy = mktime(
            0,
            0,
            0,
            $arrFecha['mon'],
            $arrFecha['mday'],
            $arrFecha['year']
        );

        $arrRespuesta['tsHoy'] = $tsHoy;
        $arrRespuesta['arrHoy'] = getdate($tsHoy);

        // Ayer

        $tsAyer = mktime(
            0,
            0,
            0,
            $arrFecha['mon'],
            $arrFecha['mday']-1,
            $arrFecha['year']
        );

        $arrRespuesta['tsAyer'] = $tsAyer;
        $arrRespuesta['arrAyer'] = getdate($tsAyer);

        // Mañana

        $tsManana = mktime(
            0,
            0,
            0,
            $arrFecha['mon'],
            $arrFecha['mday']+1,
            $arrFecha['year']
        );

        $arrRespuesta['tsManana'] = $tsManana;
        $arrRespuesta['arrManana'] = getdate($tsManana);

        return $arrRespuesta;


    //}}}
    }

    public static function mandarCorreo($destinatario, $asunto, $cuerpo, $arrArchivos, $arrNombresArchivos, $tipo = null) {
    //{{{

        require_once(RUTA_VENDORS . 'phpmailer/class.phpmailer.php');

        $mail             = new PHPMailer();

        // Según el tipo lo mandamos desde una cuenta u otra 
        if($tipo == 'oferta'){
            $servidor = "authsmtp.vifer.biz";
            $usuario = "smtp@vifer.biz";
            $clave = "25442544";
            $direccion = 'presupuestos@vifer.biz';
        }else{
            $servidor = "authsmtp.vifer.biz";
            $usuario = "smtp@vifer.biz";
            $clave = "25442544";
            $direccion = 'administracion@vifer.biz';
        }


        $mail->IsSMTP(); // telling the class to use SMTP
        $mail->Host       = $servidor; // SMTP server
        //$mail->SMTPDebug  = 2;                     // enables SMTP debug information (for testing)
                                                   // 1 = errors and messages
                                                   // 2 = messages only
        $mail->SMTPAuth   = true;                  // enable SMTP authentication
        $mail->Username   = $usuario; // SMTP account username
        $mail->Password   = $clave;        // SMTP account password

        $mail->SetFrom($direccion, 'Vifer');

        $mail->Subject    = utf8_decode($asunto);

        $mail->AltBody    = utf8_decode("Para poder visualizar correctamente este mensaje debe utilizar un gestor de correo compatible con HTML"); // optional, comment out and test

        $mail->MsgHTML(utf8_decode($cuerpo));

        $mail->AddAddress($destinatario);

        foreach($arrArchivos as $i=>$archivo){
            if(isset($arrNombresArchivos[$i])){
                $mail->AddAttachment($archivo, $arrNombresArchivos[$i]);
            }else{
                $mail->AddAttachment($archivo);
            }
        }

        if(!$mail->Send()) {
          return "Error en el envío: " . $mail->ErrorInfo;
        } else {
          return "Envío realizado correctamente";
        }

    //}}}
    }

    public static function formatearTextoPDF($texto){
        // {{{

        return utf8_decode($texto);

        // }}}
    }

    public static function formatearNumero($numero){
        // {{{

        return number_format($numero, 2, ',', '.');

        // }}}
    }

    public static function segundos_a_dia_hora_minutos($duracion_segundos){
    //{{{

        $cadena='';

        // Calculo los días
        $numero_dias=floor($duracion_segundos/(24*60*60));
        $duracion_segundos=$duracion_segundos%(24*60*60);
        if ($numero_dias>0){
            $cadena=$numero_dias . 'd';
        }

        // Calculo las horas
        $numero_horas=floor($duracion_segundos/(60*60));
        $duracion_segundos=$duracion_segundos%(60*60);
        $cadena.= ' ' . sprintf('%02s', $numero_horas) . 'h';

        // Calculo los minutos
        $numero_minutos=floor($duracion_segundos/(60));
        $duracion_segundos=$duracion_segundos%(60);
        $cadena.= ' ' . sprintf('%02s', $numero_minutos) . 'm';

        return $cadena;

    //}}}
    }

    public static function getControlador(){
    //{{{

        // Devuelve la pagina del controlador

        $controlador = array_pop(debug_backtrace());

        return basename($controlador['file']);

    //}}}
    }

    public function selectIdiomas($opcion_defecto) 
    {
        // {{{

        $arrRespuesta = array();

        if(!empty($opcion_defecto)){
            $arrRespuesta[0] = $opcion_defecto;
        }

        $arrRespuesta['en'] = 'Inglés';

        return $arrRespuesta;

        // }}}
    }

    public function selectIdiomasCompleto($opcion_defecto) 
    {
        // {{{

        $arrRespuesta = FuncionesGenerales::selectIdiomas($opcion_defecto);

        $cadena = '';

        foreach ($arrRespuesta as $abreviatura => $idioma) {
            $cadena .= "<option value='$abreviatura'>$idioma</option>";
        }

        return $cadena;

        // }}}
    }

    public function generarFecha($parametro, $valorDefecto = null) 
    {
        // {{{

        $modelo = '?(\d{2})/(\d{2})/(\d{4})$?';

        if (
            isset($_GET[$parametro])
            && preg_match($modelo, $_GET[$parametro], $coincidencias)
        ){
            $valor = 
                $coincidencias[3]
                . '-'
                . $coincidencias[2]
                . '-'
                . $coincidencias[1]
                ;
        } elseif (
            isset($_POST[$parametro])
            && preg_match($modelo, $_POST[$parametro], $coincidencias)
        ) {
            $valor = 
                $coincidencias[3]
                . '-'
                . $coincidencias[2]
                . '-'
                . $coincidencias[1]
                ;
        } else {
            if ($valorDefecto) {
                $valor = date('Y-m-d', strtotime($valorDefecto));
            } else {
                $valor = date('Y-m-d');
            }
        }

        return $valor;

        // }}}
    }
    
    public function generarFechaPlantilla($parametro, $valorDefecto) 
    {
        // {{{

        $modelo = '?(\d{2})/(\d{2})/(\d{4})$?';

        if (
            isset($_GET[$parametro])
            && preg_match($modelo, $_GET[$parametro], $coincidencias)
        ){
            $valor = 
                $coincidencias[3]
                . '-'
                . $coincidencias[2]
                . '-'
                . $coincidencias[1]
                ;
        } elseif (
            isset($_POST[$parametro])
            && preg_match($modelo, $_POST[$parametro], $coincidencias)
        ) {
            $valor = 
                $coincidencias[3]
                . '-'
                . $coincidencias[2]
                . '-'
                . $coincidencias[1]
                ;
        } elseif (!empty($valorDefecto)) {
            $valor = date('Y-m-d', strtotime($valorDefecto));
        } else {
            $valor = null;
        }

        return $valor;

        // }}}
    }

    public function tiemposCarga($tiempos) 
    {
        // {{{

        $resultado = array(
            'total' => 0,
            'desglose' => array(),
            'orden' => array()
        );

        if (isset($tiempos['comienzo'])) {
            list($microsegundos_inicial, $segundos_inicial) = explode(" ", $tiempos['comienzo']);
        } else {
            echo 'El array de tiempos tiene que tener un elemento llamado comienzo';
        }

        foreach ($tiempos as $tipo => $tiempo) {
            if ($tipo != 'comienzo') {
                list($microsegundos, $segundos) = explode(" ", $tiempo);
                $duracion = ($segundos - $segundos_inicial) + ($microsegundos - $microsegundos_inicial);
                $resultado['total'] += $duracion;

                $resultado['desglose'][] = array(
                    'tipo' => $tipo,
                    'duracion' => $duracion,
                    'porcentaje' => 0
                );

                $segundos_inicial = $segundos;
                $microsegundos_inicial = $microsegundos;
            }
        }

        // Totalizamos

        foreach ($resultado['desglose'] as $indice => &$desglose) {
            $desglose['porcentaje'] = $desglose['duracion'] / $resultado['total'] * 100;
            $resultado['orden'][round($desglose['porcentaje']*1000)] = $indice;
        }

        krsort($resultado['orden']);

        //FuncionesGenerales::mostrarArray($resultado);

        // Imprimimos el resultado

        echo '<div style="margin:10px;">';
        foreach($resultado['orden'] as $indice) {
            echo sprintf("%05.2f", $resultado['desglose'][$indice]['porcentaje']);
            echo '%';
            echo ' - ';
            echo sprintf("%.4f", $resultado['desglose'][$indice]['duracion']);
            echo ' - ';
            echo $resultado['desglose'][$indice]['tipo'];
            echo '<br>';
        }

        echo 'Total: ';
        echo sprintf("%.4f", $resultado['total']);
        echo '</div>';

        // }}}
    }

}
