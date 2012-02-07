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
package com.wirelessgalicia.logos.vista.web.almacen;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.sergas.chuac.logos.modelo.evento.EventoRealizacionPrueba;
import es.sergas.chuac.logos.modelo.tramaprmsalida.vo.TramaProductorEnEjecucionVO;

/**
 * Clase que gestiona la cola de eventos que se van a enviar a trav&eacute;s del productor
 * de eventos, para evitar problemas de concurrencia con el servicio gestor de pruebas. Es un singleton
 * @author frodcal
 *
 */
public class AlmacenEventos implements IAlmacenEventos {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlmacenEventos.class.getName());

	private static final AlmacenEventos instancia = new AlmacenEventos();
	
	/**
	 * El gestor de pruebas se encargará de ir anhadiendo tramas a la cola y de manera que se iran
	 * enviando cada cierto tiempo a los consumidores de la vista. 
	 */
	private static final Queue<EventoRealizacionPrueba> colaEventos = new LinkedList<EventoRealizacionPrueba>();

	/**
	 * Devuelve la instancia &uacute;nica
	 * @return devuelve la instancia &uacute;nica
	 */
	public static AlmacenEventos getInstance(){
		return instancia;
	}

	/**
	 * A&ntilde;ade un evento a la cola
	 * @param evento evento que se desea a&ntilde;adir
	 * @return devuelve {@code true} si se ha a&ntilde;adido sin ning&uacute;n problema
	 */
	@Override
	public synchronized boolean addEvento(EventoRealizacionPrueba evento){
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("ALMACÉN eventos vista: add Evento vista "+evento);
		boolean result = colaEventos.offer(evento);
		return result;
	}

	/**
	 * Obtiene el primer evento de la cola y lo elimina de la cola
	 * @return el primer evento de la cola 
	 */
	@Override
	public synchronized EventoRealizacionPrueba getEvento(){
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("ALMACÉN eventos vista: get Evento vista ");
		if (colaEventos.size()==0) 
			return null;
		return colaEventos.remove();
	}
	
	/**
	 * Indica si quedan eventos en la cola
	 * @return indica si quedan eventos en la cola
	 */
	@Override
	public boolean hayEventos(){
		return colaEventos.size()!=0;
	}

	@Override
	public int size(){
		return colaEventos.size();
	}

	@Override
	public void clear(){
		colaEventos.clear();
	}
}
