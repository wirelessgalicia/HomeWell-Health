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

import java.util.Date;


public class PruebaProgramada {

	private String idprueba;

	private String idpaciente;

	private String idtipoprueba;

	private char estado;

	private Date fecha;
	
	public PruebaProgramada(){
		
	}

	public PruebaProgramada(String idprueba, String idtipoprueba,
			String idpaciente, char estado, Date fecha) {
		this.setIdprueba(idprueba);
		this.setIdpaciente(idpaciente);
		this.setIdtipoprueba(idtipoprueba);
		this.setEstado(estado);
		this.setFecha(fecha);
	}

	public void setIdprueba(String idprueba) {
		this.idprueba = idprueba;
	}

	public String getIdprueba() {
		return idprueba;
	}

	public void setIdpaciente(String idpaciente) {
		this.idpaciente = idpaciente;
	}

	public String getIdpaciente() {
		return idpaciente;
	}

	public void setIdtipoprueba(String idtipoprueba) {
		this.idtipoprueba = idtipoprueba;
	}

	public String getIdtipoprueba() {
		return idtipoprueba;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public char getEstado() {
		return estado;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

}