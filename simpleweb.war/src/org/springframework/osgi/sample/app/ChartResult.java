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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.sergas.chuac.logos.modelo.wireadminmanager.resultados.vo.ResultadoVO;

public class ChartResult {

	private Long diferencia = new Long(0);
	
	private Calendar fecha;
	
	private String formattedFecha = "";
	
	private Float valor;

	private String tipo;
	
	private SimpleDateFormat df = new SimpleDateFormat("H:mm:ss SS");
	
	public static ChartResult createFrom(ResultadoVO resultado){
		return new ChartResult ( new Long(0), resultado.getFecha(), resultado.getValorFloat(), null);
	}

	public ChartResult(Long diferencia, Calendar fecha, Float valor, String tipo){
		this.setFecha(fecha);
		this.setValor(valor);
		this.setDiferencia(diferencia);
		this.setTipo(tipo);
	}

	public ChartResult(Long diferencia, Float valor, String tipo) {
		this.setValor(valor);
		this.setDiferencia(diferencia);
		this.setTipo(tipo);
	}

	public void setDiferencia(Long diferencia) {
		this.diferencia = diferencia;
	}

	public Long getDiferencia() {
		return diferencia;
	}

	public void setFecha(Calendar fecha) {
		if(fecha != null){
			setFormattedFecha(df.format(fecha.getTime()));
		}

		this.fecha = fecha;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Float getValor() {
		return valor;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setFormattedFecha(String formattedFecha) {
		this.formattedFecha = formattedFecha;
	}

	public String getFormattedFecha() {
		return this.formattedFecha;
	}

}
