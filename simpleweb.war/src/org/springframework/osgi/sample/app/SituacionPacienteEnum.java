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
package org.springframework.osgi.sample.app;

public enum SituacionPacienteEnum {

	 Move("En movemento"), Wait("En repouso"), None ("Indiferente");

	 private String desc;
	 
	 SituacionPacienteEnum(String value){
		 desc = value;
	 }
	 
	 public String getDescription() {
		 return desc;
	 }
}
