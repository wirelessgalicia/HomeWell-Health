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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorialResult {

	private String idPrueba;
	
	private Map<TipoTrama,List<Float>> resultados = new HashMap<TipoTrama,List<Float>>();
	
//	public HistorialResult(T idPrueba, Map<TipoTrama,List<Float>> resultados){
	public HistorialResult(String idPrueba, Map<TipoTrama,List<Float>> resultados){
		this.setIdPrueba(idPrueba);
	//public HistorialResult(Map<TipoTrama,List<Float>> resultados){
		this.setResultados(resultados);
	}

	public HistorialResult(){
		
	}

//	public void setIdPrueba(T idPrueba) {
//		this.idPrueba = idPrueba;
//	}
//
//	public T getIdPrueba() {
//		return idPrueba;
//	}

	public void setIdPrueba(String idPrueba) {
		this.idPrueba = idPrueba;
	}

	public String getIdPrueba() {
		return idPrueba;
	}

	public void setResultados(Map<TipoTrama,List<Float>> resultados) {
		this.resultados = resultados;
	}

	public Map<TipoTrama,List<Float>> getResultados() {
		return resultados;
	}

	
}
