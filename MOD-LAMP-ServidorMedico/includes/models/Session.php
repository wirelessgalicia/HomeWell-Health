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

class Session {

    function Session () {
        session_start();
    }

    function set ($name,$value) {
        $_SESSION['sesion_' . $name]=$value;
    }

    function get ($name) {
        if ( isset ( $_SESSION['sesion_' . $name] ) )
            return $_SESSION['sesion_' . $name];
        else
            return false;
    }

    function del ($name) {
        if ( isset ( $_SESSION['sesion_' . $name] ) ) {
            unset ( $_SESSION['sesion_' . $name] );
            return true;
        } else {
            return false;
        }
    }

    function destroy () {
        $_SESSION = array();
        session_destroy();
    }
}
