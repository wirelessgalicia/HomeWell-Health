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

import java.util.ArrayList;
import java.util.List;

public enum Views {

	 Pulsioximetro("pulso","ejecucionFasePulsioximetro"), 
	 Bascula("peso","ejecucionFaseBascula"),
	 Cicloergometro("ciclo","ejecucionFaseCicloergometro"),
	 PulsoCiclo("pulso_ciclo","ejecucionFasePulsoCiclo");

	 private String type;

	 private String view;
	 
	 Views(String type, String view){
		 this.type = type;
		 this.view = view;
	 }
	 
	 public String getType() {
		 return type;
	 }

	 public String getView() {
		 return view;
	 }
	 
	 public boolean isEquals(String type){
		 return this.type.equals(type);
	 }

	 public static String getViewById(String type){
		 if(Pulsioximetro.isEquals(type)){
			 return Pulsioximetro.getView();
		 }
		 if(Bascula.isEquals(type)){
			 return Bascula.getView();
		 }
		 if(Cicloergometro.isEquals(type)){
			 return Cicloergometro.getView();
		 }
		 if(PulsoCiclo.isEquals(type)){
			 return PulsoCiclo.getView();
		 }
		 return "";
	 }

}
