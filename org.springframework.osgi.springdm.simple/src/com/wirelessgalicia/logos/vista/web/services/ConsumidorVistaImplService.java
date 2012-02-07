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
package com.wirelessgalicia.logos.vista.web.services;

import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.wireadmin.Consumer;
import org.osgi.service.wireadmin.WireConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import com.wirelessgalicia.logos.vista.web.almacen.IAlmacenEventos;
import com.wirelessgalicia.logos.vista.web.almacen.IAlmacenResultados;
import com.wirelessgalicia.logos.vista.web.consumers.ConsumidorEventosVistaService;
import com.wirelessgalicia.logos.vista.web.consumers.ConsumidorResultadosVistaService;

import es.sergas.chuac.logos.modelo.gestorpruebas.vo.ConsumidorVistaVO;
import es.sergas.chuac.logos.modelo.wireadminmanager.services.ConsumidorVistaService;

import es.sergas.chuac.logos.servicios.wireadminmanager.vista.eventos.WireAdminManagerVistaEventosService;
import es.sergas.chuac.logos.servicios.wireadminmanager.vista.resultados.WireAdminManagerVistaResultadosService;

/**
 * 
 * @author frodcal
 *
 */

public class ConsumidorVistaImplService implements ConsumidorVistaService, BundleContextAware  {
	
	private Logger LOGGER = LoggerFactory.getLogger(ConsumidorVistaImplService.class.getName());
	
	private ConsumidorVistaVO consumidorResultadosVO;
	private ConsumidorVistaVO consumidorEventosVO;
	

	/**
	 * Productor de pruebas
	 */
	private ConsumidorResultadosVistaService consumidorResultadosService;
	private ConsumidorEventosVistaService consumidorEventosService;

	private BundleContext bundleContext;

	private WireAdminManagerVistaResultadosService wireAdminVistaResultadosService;
	private WireAdminManagerVistaEventosService wireAdminVistaEventosService;
	
	
	/**
	 * @param wireAdminVistaService the wireAdminVista to set
	 */
	public void setWireAdminVistaEventosService(WireAdminManagerVistaEventosService wireAdminVistaEventosService) {
		this.wireAdminVistaEventosService = wireAdminVistaEventosService;
	}

	public void setWireAdminVistaResultadosService(WireAdminManagerVistaResultadosService wireAdminVistaResultadosService){
		this.wireAdminVistaResultadosService = wireAdminVistaResultadosService;
	}
	
	@Override
	public void setBundleContext(BundleContext bc) {
		bundleContext = bc;
	}

	public ConsumidorVistaVO getConsumidorResultadosVO() {
		return  consumidorResultadosVO;
	}
	
	public void setConsumidorResultadosVO(ConsumidorVistaVO consumidorResultadosVO) {
		this.consumidorResultadosVO = consumidorResultadosVO;
	}

	public ConsumidorVistaVO getConsumidorEventosVO() {
		return  consumidorEventosVO;
	}

	public void setConsumidorEventosVO(ConsumidorVistaVO consumidorEventosVO) {
		this.consumidorEventosVO = consumidorEventosVO;
	}

	public void setConsumidorResultadosService(ConsumidorResultadosVistaService consumidorResultados) {
		this.consumidorResultadosService = consumidorResultados;
	}

	public void setConsumidorEventosService(ConsumidorEventosVistaService consumidorEventos) {
		this.consumidorEventosService = consumidorEventos;
	}

	/**
	 * Arranca el servicio del productor de pruebas
	 */
	@Override
	public void start() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("Arrancando Servicio del consumidor para la vista");
		if (consumidorResultadosVO!= null){
			if (LOGGER.isTraceEnabled()){
				LOGGER.trace("CONSUMIDOR:"+ consumidorResultadosVO);
			}
			registrarConsumidorResultados();
			wireAdminVistaResultadosService.conectarConsumidorConProductores(consumidorResultadosVO);

			registrarConsumidorEventos();
			wireAdminVistaEventosService.conectarConsumidorConProductores(consumidorEventosVO);
		}else
			LOGGER.info("El consumidor para la vista no esta configurado");

	}
	
	@Override
	public void stop() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("Parando del consumidor para la vista...");
		desconectarConsumidorResultados();
		desconectarConsumidorEventos();
	}
	
	

	/**
	 * Crea el consumidor de pruebas
	 */
	@Override
	public ServiceRegistration registrarConsumidorResultados() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("registrarConsumidorResultados("+consumidorResultadosVO+")");
		this.consumidorResultadosService=new ConsumidorResultadosVistaService( bundleContext, consumidorResultadosVO);
		Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put(
				WireConstants.WIREADMIN_CONSUMER_FLAVORS,
				consumidorResultadosVO.getSabores());
		props.put(org.osgi.framework.Constants.SERVICE_PID, consumidorResultadosVO.getIdServicio());
		props.put( org.osgi.framework.Constants.SERVICE_DESCRIPTION, consumidorResultadosVO.getDescServicio());
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Consumidor que se va a registrar:"+consumidorResultadosVO+" flavors: "+consumidorResultadosVO.getSabores());
		ServiceRegistration serviceReg = bundleContext.registerService(Consumer.class.getName(), 
				consumidorResultadosService, props);
		return serviceReg;
	}



	/**
	 * Crea el consumidor de pruebas
	 */
	@Override
	public ServiceRegistration registrarConsumidorEventos() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("registrarConsumidorEventos("+consumidorEventosVO+")");
		this.consumidorEventosService=new ConsumidorEventosVistaService( bundleContext, consumidorEventosVO);
		Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put(
				WireConstants.WIREADMIN_CONSUMER_FLAVORS,
				consumidorEventosVO.getSabores());
		props.put(org.osgi.framework.Constants.SERVICE_PID, consumidorEventosVO.getIdServicio());
		props.put( org.osgi.framework.Constants.SERVICE_DESCRIPTION, consumidorEventosVO.getDescServicio());
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Consumidor que se va a registrar:"+consumidorEventosVO+" flavors: "+consumidorEventosVO.getSabores());
		ServiceRegistration serviceReg = bundleContext.registerService(Consumer.class.getName(), 
				consumidorEventosService, props);
		return serviceReg;
	}

		
	/**
	 * Desconecta el consumidor 
	 */
	@Override
	public void desconectarConsumidorResultados() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("desconectarConsumidorResultados()");
		wireAdminVistaResultadosService.desconectarConsumidor(consumidorResultadosVO);
		
	}
	/**
	 * Desconecta el consumidor 
	 */
	@Override
	public void desconectarConsumidorEventos() {
		if (LOGGER.isTraceEnabled())
			LOGGER.trace("desconectarConsumidorEventos()");
		wireAdminVistaResultadosService.desconectarConsumidor(consumidorEventosVO);
		
	}

}