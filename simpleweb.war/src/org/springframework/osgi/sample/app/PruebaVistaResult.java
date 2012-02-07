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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PruebaVistaResult<T,E,K> {

	private List<T> alarmas = new ArrayList<T>();

	private List<T> eventos = new ArrayList<T>();
	
	private Map<E,List<K>> resultados = new HashMap<E,List<K>>();	
	
	public PruebaVistaResult(){
		
	}

	public PruebaVistaResult(List<T> eventos,List<T> alarmas, Map<E,List<K>> resultados){
		this.setEventos(eventos);
		this.setResultados(resultados);
		this.setAlarmas(alarmas);
	}

	public void setEventos(List<T> eventos) {
		this.eventos = eventos;
	}

	public List<T> getEventos() {
		return eventos;
	}

	public void setResultados(Map<E,List<K>> resultados) {
		this.resultados = resultados;
	}

	public Map<E,List<K>> getResultados() {
		return resultados;
	}

	public void setAlarmas(List<T> alarmas) {
		this.alarmas = alarmas;
	}

	public List<T> getAlarmas() {
		return alarmas;
	}

}
