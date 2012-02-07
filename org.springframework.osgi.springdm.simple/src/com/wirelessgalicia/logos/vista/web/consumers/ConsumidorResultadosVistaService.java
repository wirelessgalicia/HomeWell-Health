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
package com.wirelessgalicia.logos.vista.web.consumers;


import org.osgi.framework.BundleContext;
import org.osgi.service.wireadmin.Consumer;
import org.osgi.service.wireadmin.Wire;
import org.osgi.service.wireadmin.WireConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wirelessgalicia.logos.vista.web.almacen.AlmacenResultados;

import es.sergas.chuac.logos.modelo.gestorpruebas.vo.ConsumidorVistaVO;
import es.sergas.chuac.logos.modelo.tramaprmsalida.vo.TramaProductorEnEjecucionVO;


/**
 * Crea el consumidor implementando la interfaz {@link Consumer} y {@link Runnable}
 * @author Susana Montes Pedreira <smontes@wirelessgalicia.com>
 *
 */
public class ConsumidorResultadosVistaService implements Consumer, Runnable {
	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ConsumidorResultadosVistaService.class.getName());


	/**
	 * Lista de conexiones a los productores que est&aacute;n conectados a este consumidor 
	 */
	private Wire wires[];


	/**
	 * Contexto del m&oacute;dulo
	 */
	private BundleContext context;

	/**
	 * Datos de configuraci&oacute;n del consumidor 
	 */
	private ConsumidorVistaVO consumidor;



	/**
	 * Constructor del consumidor. Arranca el hilo
	 * @param context el contexto del m&oacute;dulo
	 * @param consumidor los datos de configuraci&oacute;n del consumidor 
	 */
	public ConsumidorResultadosVistaService(BundleContext context, ConsumidorVistaVO consumidor) {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Creando consumidor de resultados para la vista");
		this.context = context;
		this.consumidor = consumidor;
		new Thread(this).start();
	}

	/**
	 * Arranca el consumidor {@link Consumer}, es decir, lo registra como servicio
	 */
	public synchronized void run() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("run()");		
	}

	/**
	 * Finaliza el consumidor
	 */
	public void end() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("end");
	}

	/**
	 * Este m&eacute;todo lo utilizan los productores autom&aacute;ticamente para comunicar al 
	 * consumidor los resultados le&iacute;dos
	 */
	public void updated(Wire wire, Object value) {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("\t\t\t CONSUMIDOR VISTA update() " );
			Class<?> clazzes[] = wire.getFlavors();
			try{
				for (Class<?> clazz:clazzes){
					if (clazz.isAssignableFrom(TramaProductorEnEjecucionVO.class) && clazz.isAssignableFrom(value.getClass())){
						TramaProductorEnEjecucionVO trama = (TramaProductorEnEjecucionVO)value;
						if (LOGGER.isDebugEnabled())
							LOGGER.debug("\t\tCONSUMIDOR VISTA (Trama):" +
									wire.getProperties().get(WireConstants.WIREADMIN_PRODUCER_PID) + 
									" = " + trama);
						
						AlmacenResultados.getInstance().addTrama(trama);
						
					}else if (clazz.isAssignableFrom(String.class) && clazz.isAssignableFrom(value.getClass())){
						if (LOGGER.isDebugEnabled())
							LOGGER.debug("\tCONSUMIDOR VISTA " +
									wire.getProperties().get(WireConstants.WIREADMIN_PRODUCER_PID) + 
									" = " + value);
					}

				}
			}catch(ClassCastException e){
				LOGGER.error(e.getMessage());				
			}catch(Exception e){
				e.printStackTrace();
			}


	}

	/**
	 * Este m&eacute;todo se ejecuta cada vez que se detecta un nuevo productor
	 * conectado al consumidor
	 */
	public void producersConnected(Wire[] wires) {
		this.wires = wires;
		if (wires == null) {
			if (LOGGER.isInfoEnabled())
				LOGGER.info("\t\tCONSUMIDOR VISTA: No hay productores conectados al consumidor");
		} else {
			if (LOGGER.isInfoEnabled())
				LOGGER.info("\t\tCONSUMIDOR VISTA: " + wires.length + " productores conectados al consumidor");
			if (LOGGER.isDebugEnabled())
				for (Wire w: wires){
					LOGGER.debug("CONSUMIDOR VISTA: Wire con Productor "+w.getProperties().get(WireConstants.WIREADMIN_PRODUCER_PID) );
				}

		}

	}
}