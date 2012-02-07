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

public enum DemoPrueba {

	Peso("4889e0d745694a7600b7ec5d98prue90"), PulsoCiclo("4889e0d745694a7600b7ec5d98prue55"),PesoPulso("26146cbf7f00010101d742a1dceb4d1e");
	
	private String idPrueba;
	
	DemoPrueba(String idPrueba){
		this.setIdPrueba(idPrueba);
	}

	public void setIdPrueba(String idPrueba) {
		this.idPrueba = idPrueba;
	}

	public String getIdPrueba() {
		return idPrueba;
	}
}
