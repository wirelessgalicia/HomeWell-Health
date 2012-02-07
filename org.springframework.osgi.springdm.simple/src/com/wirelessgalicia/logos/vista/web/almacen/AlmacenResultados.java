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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.sergas.chuac.logos.modelo.tramaprmsalida.vo.TramaProductorEnEjecucionVO;
import java.util.LinkedList;
import java.util.Queue;


public class AlmacenResultados implements IAlmacenResultados {

		private static final Logger LOGGER = LoggerFactory.getLogger(AlmacenResultados.class.getName());

		/**
		 * Instancia del almac&eacute;n de resultados
		 */
		private static final AlmacenResultados instancia = new AlmacenResultados();

		/**
		 * El gestor de pruebas se encargará de ir a&ntilde;adiendo tramas a la cola y de manera que se iran
		 * enviando cada cierto tiempo a los consumidores de la vista
		 */
		private static final Queue<TramaProductorEnEjecucionVO> colaTramas = new LinkedList<TramaProductorEnEjecucionVO>();

		/**
		 * Devuelve la instancia &uacute;nica del almac&eacute;n
		 * @return la instancia &uacute;nica del almac&eacute;n
		 */
		public static AlmacenResultados getInstance(){
			return instancia;
		}

		/**
		 * An&ntilde;ade una trama a la cola de resultados
		 * @param trama trama que se quiere a&ntilde;adir a la cola de resultados
		 * @return {@code true} si se ha a&ntilde;adido correctamente
		 */
		@Override
		public synchronized boolean addTrama(TramaProductorEnEjecucionVO trama){
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("ALMACÉN resultados vista: add Evento vista "+trama);
				boolean result = colaTramas.offer(trama);
				return result;
					
		}

		/**
		 * Devuelve la primera trama de la cola y la elimina de la cola
		 * @return la primera trama de la cola 
		 */
		@Override
		public synchronized TramaProductorEnEjecucionVO getFirstTrama(){
			if (LOGGER.isTraceEnabled())
				LOGGER.trace("ALMACÉN resultados vista: get Evento vista ");
			if (colaTramas.size()==0) 
				return null;
			return colaTramas.peek();
		}
		
		@Override
		public synchronized Queue<TramaProductorEnEjecucionVO> getTramas(){
			if (LOGGER.isTraceEnabled())
				LOGGER.trace("ALMACÉN resultados vista: get All Results ");
			if (colaTramas.size()==0) 
				return null;
			return colaTramas;
		}

		/**
		 * Indica si quedan tramas en la cola
		 * @return indica si quedan tramas en la cola
		 */
		@Override
		public boolean hayTramas(){
			return colaTramas.size()!=0;
		}

		@Override
		public int size(){
			return colaTramas.size();
		}

		@Override
		public void clear(){
			colaTramas.clear();
		}
	}
