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
package org.springframework.osgi.sample.web;

import java.util.List;
import java.util.Map;

import org.springframework.osgi.sample.app.Row;

import es.sergas.chuac.logos.modelo.fase.vo.FaseVO;
import es.sergas.chuac.logos.modelo.prmsalida.vo.PrmSalidaVO;
import es.sergas.chuac.logos.modelo.prueba.vo.PruebaVO;
import es.sergas.chuac.logos.modelo.tipofase.vo.TipoFaseVO;
import es.sergas.chuac.logos.modelo.tipoprueba.vo.TipoPruebaVO;

public interface IModelPruebas {

	void init();
	
	List<PruebaVO> getPruebasPendientes();
	
	List<Row> getPruebasRows(List<PruebaVO> pruebas);

	PruebaVO getDetallesPrueba(String idPrueba);

	List<Row> getDetallesPruebaRows(String id);

	PruebaVO getPrueba();

	TipoPruebaVO getTipoPrueba();

	FaseVO getFaseActual();

	int lastFase();
	
	Map<FaseVO,TipoFaseVO> getMapFases();

	TipoFaseVO getTipoFaseActual();

	List<PrmSalidaVO> getParametrosActuales();

	void updateFase();

	void update(PruebaVO prueba);

	String getTipoPruebaClass();

	int getNumFaseActual();

	boolean haySiguienteFase();

	boolean isDemoPrueba(String idPrueba);

}
