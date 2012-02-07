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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.tree.DefaultMutableTreeNode;

import com.wirelessgalicia.logos.vista.web.almacen.IAlmacenEventos;
import com.wirelessgalicia.logos.vista.web.almacen.IAlmacenResultados;
import es.sergas.chuac.logos.modelo.evento.EventoRealizacionPrueba;
import es.sergas.chuac.logos.modelo.prmsalida.vo.PrmSalidaVO;
import es.sergas.chuac.logos.modelo.prueba.vo.PruebaVO;
import es.sergas.chuac.logos.modelo.tramaprmsalida.vo.TramaPrmSalidaEnEjecucionVO;
import es.sergas.chuac.logos.modelo.tramaprmsalida.vo.TramaProductorEnEjecucionVO;
import es.sergas.chuac.logos.modelo.wireadminmanager.resultados.vo.ResultadoVO;
import es.sergas.chuac.logos.modelo.wireadminmanager.services.ConsumidorVistaService;
import es.sergas.chuac.logos.servicios.gestorpruebas.GestorPruebasService;
import es.sergas.chuac.logos.util.exception.InternalErrorException;
import es.sergas.chuac.logos.configuracionpruebas.fachadaprincipal.IFachadaPrincipal;

/**
 * @author Susana Montes Pedreira <smontes@wirelessgalicia.com
 */

public class ServiceViewOSGiFacade {

	private ConsumidorVistaService vistaService;

	private	IAlmacenEventos almacenEventos;

	private IAlmacenResultados almacenResultados;
	
	private GestorPruebasService gestorPruebas;

	private IFachadaPrincipal principal;

	private Integer numResultadosRecuperados = 0;

	private Map<PrmSalidaVO,Calendar> ultimoResultados = new HashMap<PrmSalidaVO,Calendar>();
	
	private Calendar ultimoResultadoSat = null;

	private Calendar ultimoResultadoPulso = null;

	private Calendar ultimoResultadoRpm = null; 

	private final SimpleDateFormat sdf = new SimpleDateFormat(
	"dd-MM-yyyy HH:mm:ss");

	public void setVistaService(ConsumidorVistaService vistaService) {
		this.vistaService = vistaService;
	}

	public ConsumidorVistaService getVistaService() {
		return vistaService;
	}

	public void setAlmacenResultados(IAlmacenResultados almacenResultados) {
		this.almacenResultados = almacenResultados;
	}

	public IAlmacenResultados getAlmacenResultados() {
		return almacenResultados;
	}

	public void setAlmacenEventos(IAlmacenEventos almacenEventos) {
		this.almacenEventos = almacenEventos;
	}

	public IAlmacenEventos getAlmacenEventos() {
		return almacenEventos;
	}

	public void setGestorPruebas(GestorPruebasService gestorPruebas) {
		this.gestorPruebas = gestorPruebas;
	}

	public GestorPruebasService getGestorPruebas() {
		return gestorPruebas;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(IFachadaPrincipal principal) {
		this.principal = principal;
	}


	public void clearAlmacenes(){
		almacenEventos.clear();
		almacenResultados.clear();
	}
	/**
	 * Inicia la prueba
	 * 
	 * @throws InternalErrorException
	 */
	
	public void runPrueba(String idPrueba) throws InternalErrorException{
		gestorPruebas.realizarPrueba(idPrueba);
	}

	public void inicializarPrueba(String idPrueba){
		//FachadaPrincipal inicializar prueba con demora
		int segundosDemora = 1800;
		try {
			principal.inicializarPruebaParaRealizarHoy(idPrueba, segundosDemora);
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getResults(){
		String result = "results is null";
		if(almacenResultados != null){
			result = "results INITIALIZED";
			if(almacenResultados.hayTramas()){
				TramaProductorEnEjecucionVO trama = almacenResultados.getFirstTrama();
				result = result +" --- "+trama.getIdTramaFase();
				result += " -- HAY RESULTADOS";
			}
		}
		return result;
	}
	
	public String getAllResults() {
		String result = "";
		if (almacenResultados != null) {
			if (almacenResultados.hayTramas()) {
				List<TramaProductorEnEjecucionVO> resultados = new ArrayList<TramaProductorEnEjecucionVO>(
						almacenResultados.getTramas());
				for (TramaProductorEnEjecucionVO trama : resultados) {
					result = result + " fase: " + trama.getIdTramaFase() + ", ";
					result += trama.getIdTipoDispositivo();
					for (TramaPrmSalidaEnEjecucionVO tramaPrm : trama
							.getTramasPrmsSalida()) {
						for (ResultadoVO resultado : tramaPrm.getResultados()) {
							result += "fecha: "
									+ resultado.getFecha().toString() + "  "
									+ resultado.getValorFloat();
						}
					}
				}
			}
		}
		return result;
	}
	
	public List<String> getAllEvents(){
		List<String> list = new ArrayList<String>();
		if(almacenEventos != null){
			while(almacenEventos.hayEventos()){
				EventoRealizacionPrueba event = almacenEventos.getEvento();
				
				String alarma = (event.getAlarma() == null) ? "" : event.getAlarma().getTexto();
				String tipo = (event.getTipo() == null) ? "" : event.getTipo().name();
				list.add(alarma+" -- "+tipo);
			}
		}
		return list;
	}
	
	public List<EventoRealizacionPrueba> getEventos(){
		List<EventoRealizacionPrueba> list = new ArrayList<EventoRealizacionPrueba>();
		if(almacenEventos != null){
			while(almacenEventos.hayEventos()){
				EventoRealizacionPrueba event = almacenEventos.getEvento();
				list.add(event);
			}
		}
		return list;
	}
	
	
	public Map<Boolean,List<String>> getAllEventsAndAlarmas(){
		Map<Boolean,List<String>> map = new HashMap<Boolean,List<String>>();
		List<String> listEvent = new ArrayList<String>();
		List<String> listAlarm = new ArrayList<String>();
		map.put(true, listEvent);
		map.put(false, listAlarm);
		
		if(almacenEventos != null){
			while(almacenEventos.hayEventos()){
				EventoRealizacionPrueba event = almacenEventos.getEvento();
				
				String alarma = (event.getAlarma() == null) ? "" : event.getAlarma().getTexto();
				String tipo = (event.getTipo() == null) ? "" : event.getTipo().name();
				
				if(event.isTipoAlarma()){
					map.get(false).add(alarma+" -- "+tipo);
				}else{
					map.get(true).add(alarma+" -- "+tipo);
				}
			}
		}
		return map;
	}
	
	public String getEvents(){
		String result = "events is null";
		if(almacenEventos != null){
			result = "events INITIALIZED";
			if(almacenEventos.hayEventos()){
				EventoRealizacionPrueba event = almacenEventos.getEvento();
				result += event.getTipo().name();
				result += " -- HAY EVENTOS";
			}
		}
		return result;
	}

	public String getEvent(){
		String result = null;
		if(almacenEventos != null){
			if(almacenEventos.hayEventos()){
				EventoRealizacionPrueba event = almacenEventos.getEvento();
				result += event.getAlarma().getTexto()+" -- "+event.getTipo().name();
			}
		}
		return result;
	}
	
	public Map<TipoTrama,List<ChartResult>> getPesoResult(){
		Map<TipoTrama,List<ChartResult>> mapChartResults = new HashMap<TipoTrama,List<ChartResult>>();
		
		if(almacenResultados != null && almacenResultados.hayTramas()){
			int lastIndex = almacenResultados.getTramas().size()-1;
			TramaProductorEnEjecucionVO trama = (TramaProductorEnEjecucionVO) (almacenResultados.getTramas()).toArray()[lastIndex];
		    if(trama.getTramasPrmsSalida().size() > 0){
		    	TramaPrmSalidaEnEjecucionVO lastSalida = (TramaPrmSalidaEnEjecucionVO) trama.getTramasPrmsSalida().toArray()[trama.getTramasPrmsSalida().size()-1];
		    	if(lastSalida.getResultados().size() > 0){
		    		ResultadoVO resultado = (ResultadoVO) lastSalida.getResultados().toArray()[lastSalida.getResultados().size()-1];
		    		TipoTrama tipo = TipoTrama.Peso;

		    		ChartResult result = ChartResult.createFrom(resultado);
					result.setTipo(tipo.toString());

					List<ChartResult> list = mapChartResults.get(tipo);
					if(list == null){
						list = new ArrayList<ChartResult>();
					}
					list.add(result);
					mapChartResults.put(tipo, list);
		    	}
		    }
		}
		return mapChartResults;
	}
	
	public Map<TipoTrama,List<ChartResult>> getAllChartResults(){
		this.numResultadosRecuperados = 0;
		this.ultimoResultadoPulso = null;
		this.ultimoResultadoSat = null;
		this.ultimoResultadoRpm = null;
		return getChartResults(numResultadosRecuperados);
	}

	public Map<TipoTrama,List<ChartResult>> getNewChartResults(){
		return getChartResults(numResultadosRecuperados);
	}

	private Map<TipoTrama,List<ChartResult>> getChartResults(Integer fromPosition){
		Map<TipoTrama,List<ChartResult>> mapChartResults = new HashMap<TipoTrama,List<ChartResult>>();
		
		if(almacenResultados != null && almacenResultados.hayTramas()){
			for (int i = this.numResultadosRecuperados; i < almacenResultados.getTramas().size(); i++){
				TramaProductorEnEjecucionVO trama = (TramaProductorEnEjecucionVO) (almacenResultados.getTramas()).toArray()[i];
				for (TramaPrmSalidaEnEjecucionVO tramaPrm: trama.getTramasPrmsSalida()){
					TipoTrama tipo = getTipo(tramaPrm);
					
					for(ResultadoVO resultado : tramaPrm.getResultados()){
						ChartResult result = ChartResult.createFrom(resultado);
						result.setTipo(tipo.toString());
						result.setDiferencia(getDiferencia(result.getFecha(),tipo));
						
						List<ChartResult> list = mapChartResults.get(tipo);
						if(mapChartResults.get(tipo) == null){
							list = new ArrayList<ChartResult>();
						}
						list.add(result);
						mapChartResults.put(tipo, list);
					}
				}
			}
			this.numResultadosRecuperados = almacenResultados.getTramas().size();
		}
		return mapChartResults;
	}

	private Long getDiferencia(Calendar second, TipoTrama tipo) {
		Calendar ultimoResultado;
		if(tipo.equals(TipoTrama.Pulso)){
			ultimoResultado = this.ultimoResultadoPulso;
			this.ultimoResultadoPulso = second;
		}else if(tipo.equals(TipoTrama.Saturacion)){
			ultimoResultado = this.ultimoResultadoSat;
			this.ultimoResultadoSat = second;
		}else{
			ultimoResultado = this.ultimoResultadoRpm;
			this.ultimoResultadoRpm = second;
		}

		if (ultimoResultado != null) {
			Long diferencia = second.getTimeInMillis()
					- ultimoResultado.getTimeInMillis();
			//System.out.println(" diferencia en milisegundos " + diferencia);
			
			if(diferencia > 0){
				diferencia = (Long)diferencia / 50;
			}
			return diferencia;
		}
		return (long) 0;
	}

	private TipoTrama getTipo(TramaPrmSalidaEnEjecucionVO trama){
		if(trama.getIdPrmSalida().contains("pulso")){
			return TipoTrama.Pulso;
		}
		if(trama.getIdPrmSalida().contains("sat")){
			return TipoTrama.Saturacion;
		}else{
			System.out.println("tipo trama rpm");
			return TipoTrama.Rpm;
		}
	}

	
	/**
	 * -----------------------------------------------------------------------------------------
	 * ------------    TENIENDO EN CUENTA LAS FASES Y LOS DISTINTOS PARAMETROS --------------
	 * ----------------------------------------------------------------------------------------
	 */
	
	public Map<PrmSalidaVO,List<ChartResult>> getAllChartResults(List<PrmSalidaVO> parametros){
		this.numResultadosRecuperados = 0;
		ultimoResultados = new HashMap<PrmSalidaVO,Calendar>();
		return getChartResults(numResultadosRecuperados,parametros);
	}

	public Map<PrmSalidaVO,List<ChartResult>> getNewChartResults(List<PrmSalidaVO> parametros){
		return getChartResults(numResultadosRecuperados,parametros);
	}

	private Map<PrmSalidaVO,List<ChartResult>> getChartResults(Integer fromPosition, List<PrmSalidaVO> parametros){
		Map<PrmSalidaVO,List<ChartResult>> mapChartResults = new HashMap<PrmSalidaVO,List<ChartResult>>();
		
		if(almacenResultados != null && almacenResultados.hayTramas()){
			for (int i = this.numResultadosRecuperados; i < almacenResultados.getTramas().size(); i++){
				TramaProductorEnEjecucionVO trama = (TramaProductorEnEjecucionVO) (almacenResultados.getTramas()).toArray()[i];
				for (TramaPrmSalidaEnEjecucionVO tramaPrm: trama.getTramasPrmsSalida()){
					
					/*Se discrimina de que tipo de parametro es la trama, sino es del
					 * conjunto de parametros de la fase se descarta 
					 */
					PrmSalidaVO idPrmSalida = getPrmSalidaFrom(parametros,tramaPrm.getIdPrmSalida());
					if(idPrmSalida != null){
						for(ResultadoVO resultado : tramaPrm.getResultados()){
							ChartResult result = ChartResult.createFrom(resultado);
							result.setTipo(idPrmSalida.getId());
						
							List<ChartResult> list = mapChartResults.get(idPrmSalida);
							if(list == null){
								list = new ArrayList<ChartResult>();
								mapChartResults.put(idPrmSalida, list);
							}else{
								result.setDiferencia(getDiferencia(result.getFecha(),idPrmSalida));
							}
							mapChartResults.get(idPrmSalida).add(result);
						}
					}
				}
			}
			this.numResultadosRecuperados = almacenResultados.getTramas().size();
		}
		return mapChartResults;
	}

	private Long getDiferencia(Calendar second, PrmSalidaVO salida) {
		Calendar ultimoResultado = this.ultimoResultados.get(salida);
		ultimoResultados.put(salida, second);
		if (ultimoResultado != null) {
			Long diferencia = second.getTimeInMillis()
					- ultimoResultado.getTimeInMillis();
			System.out.println(" diferencia en milisegundos " + diferencia);
			
			if(diferencia > 0){
				diferencia = (Long)diferencia / 50;
			}
			return diferencia;
		}
		return (long) 0;
	}

	private PrmSalidaVO getPrmSalidaFrom(List<PrmSalidaVO>list , String id){
		for(PrmSalidaVO salida : list){
			if(salida.getId().equalsIgnoreCase(id)){
				return salida;
			}
		}
		return null;
	}

	/*
	 * function to manage order from medical enviroment
	 */

	public boolean insertOrUpdatePrueba(ListPruebaProgramadas list_pruebas){
		//FachadaPrincipal actualizar prueba
		List<PruebaProgramada> pruebas = list_pruebas.getListpruebas();
		List<PruebaVO> pruebasvo = toPruebaVOList(pruebas);
		try {
			return principal.insertOrOpdatePrueba(pruebasvo);
		} catch (InternalErrorException e) {
			return false;
		}
	}

	private List<PruebaVO> toPruebaVOList(List<PruebaProgramada> pruebas) {
		List<PruebaVO> pruebasvo = new ArrayList<PruebaVO>();
		for(PruebaProgramada prueba : pruebas){
			Calendar fechaProgPrueba = Calendar.getInstance();
			fechaProgPrueba.setTime(prueba.getFecha());
			
			PruebaVO pruebaVO = new PruebaVO(prueba.getIdtipoprueba(), 
					fechaProgPrueba, fechaProgPrueba, 5, 
					getEstado(prueba));
			pruebaVO.setId(prueba.getIdprueba());
			pruebaVO.setIdSubrogado(prueba.getIdprueba());
			pruebasvo.add(pruebaVO);
		}
		return pruebasvo;
	}

	private String getEstado(PruebaProgramada prueba) {
		switch(prueba.getEstado()){
		case 'p': return PruebaVO.ESTADO_PROGRAMADA;
		case 'i': return PruebaVO.ESTADO_INICIADA;
		case 'f': return PruebaVO.ESTADO_FINALIZADA;
		case 'c': return PruebaVO.ESTADO_CANCELADA;
		}
		return PruebaVO.ESTADO_PROGRAMADA;
	}	

	public boolean deletePrueba(ListIdsPruebas list_pruebas){
		List<String> pruebas = list_pruebas.getIds();
		try {
			return principal.delete(pruebas);
		} catch (InternalErrorException e) {
			return false;
		}
	}
}
