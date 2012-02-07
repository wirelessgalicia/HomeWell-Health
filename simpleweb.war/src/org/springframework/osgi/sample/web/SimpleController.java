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


import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import org.springframework.osgi.sample.app.ChartResult;
import org.springframework.osgi.sample.app.DateFormat;
import org.springframework.osgi.sample.app.HistorialResult;
import org.springframework.osgi.sample.app.ListIdsPruebas;
import org.springframework.osgi.sample.app.ListPruebaProgramadas;
import org.springframework.osgi.sample.app.PruebaProgramada;
import org.springframework.osgi.sample.app.PruebaVistaResult;
import org.springframework.osgi.sample.app.ResultSendHistorial;
import org.springframework.osgi.sample.app.ResultTable;
import org.springframework.osgi.sample.app.Row;
import org.springframework.osgi.sample.app.ServiceViewOSGiFacade;
import org.springframework.osgi.sample.app.SituacionPacienteEnum;
import org.springframework.osgi.sample.app.TipoTrama;
import org.springframework.osgi.sample.app.TiposDispositivos;
import org.springframework.osgi.sample.app.Views;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;
import es.sergas.chuac.logos.modelo.evento.EventoRealizacionPrueba;
import es.sergas.chuac.logos.modelo.evento.EventoRealizacionPrueba.TipoEvento;
import es.sergas.chuac.logos.modelo.prmsalida.vo.PrmSalidaVO;
import es.sergas.chuac.logos.util.exception.InternalErrorException;


@Controller
public class SimpleController {
	
	private ServiceViewOSGiFacade serviceViewOSGiFacade;

	private IModelPruebas modelPruebas;

	private HistorialResult historialResult = new HistorialResult();

	private int j=0;

	public SimpleController(ServiceViewOSGiFacade stringReverserOSGiFacade,
			IModelPruebas modelPruebas) {
		this.serviceViewOSGiFacade = stringReverserOSGiFacade;
		this.modelPruebas = modelPruebas;
	}

	/**
	 * 
	 * El método se asocia a la url /processPrueba y cualquier petición HTTP a
	 * esta url hará que se ejecute el método correspondiente.
	 */
	@RequestMapping(value = "/processPrueba", method = RequestMethod.GET)
	@ResponseBody
	
	public int processPrueba() {
		String idPrueba = modelPruebas.getPrueba().getId();
		try {
			
			historialResult.getResultados().clear();
			historialResult.setIdPrueba(idPrueba);
			
			serviceViewOSGiFacade.runPrueba(idPrueba);
		} catch (InternalErrorException e) {
			return 0;
		}
		return 1;
	}

	@RequestMapping(value = "/pruebas.htm")
	public ModelAndView goPruebasPendientes(HttpServletRequest request) {
		modelPruebas.init();

		ModelAndView mv = new ModelAndView();
		if (modelPruebas.getPrueba() == null) {
			mv.setViewName("sinpruebas");
		} else {
			mv.addObject("descripcion", modelPruebas.getTipoPrueba()
					.getDescripcion());

			Calendar date = modelPruebas.getPrueba().getFechaPrgInicio();
			mv.addObject("hora", DateFormat.getHora(date));
			mv.addObject("dia", DateFormat.getDate(date));

			String tipoPrueba = modelPruebas.getTipoPruebaClass();
			mv.addObject("class_link", tipoPrueba);
			mv.setViewName("pruebasiguiente");
		}
		return mv;
	}

	@RequestMapping("/prueba.htm")
	public ModelAndView goPrueba() {
		ModelAndView mv = new ModelAndView();
		if (modelPruebas.getPrueba() == null) {
			mv.setViewName("sinpruebas");
		} else {
			mv.addObject("tituloPrueba", modelPruebas.getPrueba().getId());
			mv.setViewName("prueba");
		}
		return mv;
	}

	@RequestMapping(value = "/ejecucionPruebas.htm")
	public ModelAndView goEjecutarPrueba(HttpServletRequest request) {
		return new ModelAndView("ejecucionPrueba");
	}

	@RequestMapping(value = "/ejecucionFase.htm")
	public ModelAndView goEjecutarFase(HttpServletRequest request) {
		System.out.println("/ejecucionFase.htm  ------------------------------------------------------");
		ModelAndView mv = new ModelAndView();
		serviceViewOSGiFacade.clearAlmacenes();
		
		if (modelPruebas.getPrueba() == null) {
			System.out.println("prueba nula");
			mv.setViewName("sinpruebas");
		} else {
			modelPruebas.updateFase();
			if (modelPruebas.getFaseActual() == null) {
				System.out.println("fase nula");
				mv.setViewName("sinpruebas");
			} else {
				mv.addObject("orden", modelPruebas.getTipoFaseActual()
						.getOrden());
				mv.addObject("descripcion", modelPruebas.getTipoFaseActual()
						.getDescripcion());
				mv.addObject("duracion", modelPruebas.getTipoFaseActual()
						.getDuracion());

				int sitPac = Integer.parseInt(modelPruebas.getTipoFaseActual()
						.getSituacionPaciente());
				mv.addObject("sitpac",
						SituacionPacienteEnum.values()[sitPac].getDescription());

				List<PrmSalidaVO> parametros = modelPruebas
						.getParametrosActuales();
				List<String> tiposDispositivos = new ArrayList<String>();
				for (PrmSalidaVO parametro : parametros) {
					if (!tiposDispositivos.contains(parametro
							.getIdTipoDispositivo())) {
						tiposDispositivos.add(parametro.getIdTipoDispositivo());
					}
				}

				String nameView = "";
				String tipo = "";
				if (tiposDispositivos.size() > 0) {
					if (tiposDispositivos.size() > 1) {
						tipo = "pulso_ciclo";
					} else {
						tipo = TiposDispositivos.getInstance().getTipo(
								tiposDispositivos.get(0));
					}
				}
				nameView = Views.getViewById(tipo);
				mv.setViewName(nameView);
			}
		}
		return mv;
	}

	@RequestMapping("/historial.htm")
	public ModelAndView goHistorial() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("historial");
		return mv;
	}

	@RequestMapping("/consulta.htm")
	public ModelAndView goConsulta() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("consulta");
		return mv;
	}

	@RequestMapping("/control.htm")
	public ModelAndView goControl() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("control");
		return mv;
	}

	/**
	 * ************************ Servicios REST para obtener datos *************************
	 */

	@RequestMapping(value = "/inicializar_prueba", headers = "Accept=*/*", method = RequestMethod.GET)
	@ResponseBody
	public void inicializarPrueba(@RequestParam String id) {
		serviceViewOSGiFacade.inicializarPrueba(id);
	}

	@RequestMapping(value = "/faseActual", method = RequestMethod.GET)
	@ResponseBody
	public int getFaseActual() {
		return modelPruebas.getNumFaseActual();
	}
	
	@RequestMapping(value = "/pruebas/pendientes", headers = "Accept=*/*", method = RequestMethod.GET)
	@ResponseBody
	public ResultTable getPruebasPendientesBD() {
		List<Row> rows = modelPruebas.getPruebasRows(modelPruebas
				.getPruebasPendientes());
		return new ResultTable(1, 1, 2, rows);
	}

	@RequestMapping(value = "/pruebas/detalles", headers = "Accept=*/*", method = RequestMethod.GET)
	@ResponseBody
	public ResultTable getPruebasDetalles(@RequestParam String id) {
		List<Row> rows = modelPruebas.getDetallesPruebaRows(id);
		return new ResultTable(1, 1, 2, rows);
	}

	@RequestMapping(value = "/allchartresults", method = RequestMethod.GET)
	@ResponseBody
	public Map<TipoTrama, List<ChartResult>> getAllChartResults() {
		Map<TipoTrama, List<ChartResult>> results = serviceViewOSGiFacade.getAllChartResults();
		saveHistory(results);
		return results;
	}

	private void saveHistory(Map<TipoTrama, List<ChartResult>> results) {
		for( Entry<TipoTrama,List<ChartResult>> entryTipo : results.entrySet()){
			List<Float> listValores = new ArrayList<Float>();
			for(ChartResult chartResult : entryTipo.getValue()){
				listValores.add(chartResult.getValor());
			}

			if(entryTipo.getKey().equals(TipoTrama.Peso)){
				List<Float> listPeso = new ArrayList<Float>();
				listPeso.add(getFirstValue(listValores));
				this.historialResult.getResultados().put(entryTipo.getKey(),listPeso);
			}else{
				if(this.historialResult.getResultados().get(entryTipo.getKey()) != null){
					this.historialResult.getResultados().get(entryTipo.getKey()).addAll(listValores);
				}else{
					this.historialResult.getResultados().put(entryTipo.getKey(), listValores);
				}
			}
		}
	}

	@RequestMapping(value = "/newchartresults", method = RequestMethod.GET)
	@ResponseBody
	public Map<TipoTrama, List<ChartResult>> getNewChartResults() {
		Map<TipoTrama, List<ChartResult>> results = serviceViewOSGiFacade.getNewChartResults();
		saveHistory(results);
		return results;
	}

	@RequestMapping(value = "/pesoresult", method = RequestMethod.GET)
	@ResponseBody
	public Map<TipoTrama, List<ChartResult>> getPesoResult() {
		return serviceViewOSGiFacade.getPesoResult();
	}

	@RequestMapping(value = "/allViewResults", method = RequestMethod.GET)
	@ResponseBody
	public PruebaVistaResult getAllVistaResults() {
		List<EventoRealizacionPrueba> eventos = serviceViewOSGiFacade
				.getEventos();
		List<String> list_eventos = new ArrayList<String>();
		List<String> alarmaUsuario = getAlarmasDeUsuario(eventos);

		Map<TipoTrama, List<ChartResult>> resultados = new HashMap<TipoTrama, List<ChartResult>>();
		resultados = getAllChartResults();
		return new PruebaVistaResult<String, TipoTrama, ChartResult>(
				list_eventos, alarmaUsuario, resultados);
	}

	@RequestMapping(value = "/newViewResults", method = RequestMethod.GET)
	@ResponseBody
	public PruebaVistaResult getNewVistaResults() {

		Map<TipoTrama, List<ChartResult>> resultados = new HashMap<TipoTrama, List<ChartResult>>();
		resultados = getNewChartResults();

		List<EventoRealizacionPrueba> eventos = serviceViewOSGiFacade
				.getEventos();
		List<String> list_eventos = new ArrayList<String>();
		List<String> alarmaUsuario = getAlarmasDeUsuario(eventos);
		return new PruebaVistaResult<String, TipoTrama, ChartResult>(
				list_eventos, alarmaUsuario, resultados);
	}

	private List<String> getAlarmasDeUsuario(
			List<EventoRealizacionPrueba> eventos) {
		List<String> alarmaUsuario = new ArrayList<String>();
		for (EventoRealizacionPrueba evento : eventos) {
			if (evento.getTipo().equals(TipoEvento.PACIENTE_DEBE_PARAR_PRUEBA)) {
				alarmaUsuario.add("PACIENTE_DEBE_PARAR_PRUEBA");
			}
			if (evento.getTipo().equals(TipoEvento.FASE_FINALIZADA)) {
				if (modelPruebas.getNumFaseActual() == modelPruebas.lastFase()) {
					alarmaUsuario.add("PRUEBA_FINALIZADA");
				} else {
					alarmaUsuario.add("FASE_FINALIZADA");
				}
			}
		}
		return alarmaUsuario;
	}

	@RequestMapping(value = "/valorPeso", method = RequestMethod.GET)
	@ResponseBody
	public PruebaVistaResult getValorPeso() {

		List<EventoRealizacionPrueba> eventos = serviceViewOSGiFacade
				.getEventos();
		List<String> list_eventos = new ArrayList<String>();
		List<String> alarmaUsuario = getAlarmasDeUsuario(eventos);

		Map<TipoTrama, List<ChartResult>> resultados = new HashMap<TipoTrama, List<ChartResult>>();
		resultados = getPesoResult();
		
		
		List<ChartResult> results = resultados.get(TipoTrama.Peso);
		if (j < 55) {
			j++;
			PruebaVistaResult vistaResult = new PruebaVistaResult<String, TipoTrama, ChartResult>(
					list_eventos, alarmaUsuario, resultados);
			Map<TipoTrama,List<ChartResult>> resultadosPeso = (Map<TipoTrama,List<ChartResult>>) vistaResult.getResultados();
			return vistaResult;
		}else{
			j=0;
		}
		PruebaVistaResult vistaResult = getVistaResultsManual();
		Map<TipoTrama,List<ChartResult>> resultadosPeso = (Map<TipoTrama,List<ChartResult>>) vistaResult.getResultados();
		saveHistory(resultadosPeso);
		
		return vistaResult;
	}

	private Float getFirstValue(List<Float> list) {
		if((list != null) && (list.size() > 0)){
			return list.get(0);
		}
		return null;
	}

	public PruebaVistaResult getVistaResultsManual() {

		Map<TipoTrama, List<ChartResult>> resultados = new HashMap<TipoTrama, List<ChartResult>>();

		// Añade uno manualmente --- QUITAR ----------------------------
		ChartResult charResult1 = new ChartResult(new Long(1),
				Calendar.getInstance(), new Float(61.4), "peso");
		ChartResult charResult2 = new ChartResult(new Long(1),
				Calendar.getInstance(), new Float(53.4), "peso");
		List<ChartResult> list1 = new ArrayList<ChartResult>();

		list1.add(charResult1);
		list1.add(charResult2);
		resultados.put(TipoTrama.Peso, list1);

		List<String> list_eventos = new ArrayList<String>();
		List<String> alarmaUsuario = new ArrayList<String>();
		
		if (modelPruebas.haySiguienteFase()) {
			alarmaUsuario.add("FASE_FINALIZADA");
		} else {
			alarmaUsuario.add("PRUEBA_FINALIZADA");
		}
		
		return new PruebaVistaResult<String, TipoTrama, ChartResult>(
				list_eventos, alarmaUsuario, resultados);
	}

	/**
	 * ------------- TO-DO ----------------------------------------------------------
	 * ------------------------------------------------------------------------
	 * ------- TENIENDO EN CUENTA LAS FASES Y LOS DISTINTOS PARAMETROS --------------
	 * --------------------------------------------------------------------------------
	 */

	@RequestMapping(value = "/allViewResultsFase", method = RequestMethod.GET)
	@ResponseBody
	public PruebaVistaResult getAllVistaResultsFase() {

		// Recupera los valores segun el tipo de parametro (PrmSalidaVO)
		Map<PrmSalidaVO, List<ChartResult>> resultados = new HashMap<PrmSalidaVO, List<ChartResult>>();
		List<PrmSalidaVO> parametros = modelPruebas.getParametrosActuales();
		resultados = serviceViewOSGiFacade.getAllChartResults(parametros);

		List<EventoRealizacionPrueba> eventos = serviceViewOSGiFacade
				.getEventos();
		List<String> list_eventos = new ArrayList<String>();
		List<String> alarmaUsuario = new ArrayList<String>();
		for (EventoRealizacionPrueba evento : eventos) {
			if (evento.getTipo().equals(TipoEvento.FASE_FINALIZADA)) {
				alarmaUsuario.add(TipoEvento.FASE_FINALIZADA.toString()
						+ " -- A continuación comienza la siguiente fase.");
			}
		}

		return new PruebaVistaResult<String, PrmSalidaVO, ChartResult>(
				list_eventos, alarmaUsuario, resultados);
	}

	@RequestMapping(value = "/newViewResultsFase", method = RequestMethod.GET)
	@ResponseBody
	public PruebaVistaResult getNewVistaResultsFase() {

		// Recupera los valores segun el tipo de parametro (PrmSalidaVO)
		Map<PrmSalidaVO, List<ChartResult>> resultados = new HashMap<PrmSalidaVO, List<ChartResult>>();
		List<PrmSalidaVO> parametros = modelPruebas.getParametrosActuales();
		resultados = serviceViewOSGiFacade.getNewChartResults(parametros);

		// Recupera los eventos y muestra solo el de FASE_FINALIZADA
		List<EventoRealizacionPrueba> eventos = serviceViewOSGiFacade
				.getEventos();
		List<String> list_eventos = new ArrayList<String>();
		List<String> alarmaUsuario = new ArrayList<String>();
		for (EventoRealizacionPrueba evento : eventos) {
			if (evento.getTipo().equals(TipoEvento.FASE_FINALIZADA)) {
				alarmaUsuario.add("FASE_FINALIZADA");
			}
		}
		return new PruebaVistaResult<String, PrmSalidaVO, ChartResult>(
				list_eventos, alarmaUsuario, resultados);
	}

	
	/*
	 * Servicios web para manejar los datos enviados desde la gestion del médico 
	 */
	
	@RequestMapping(value = "/pruebasvo", method = RequestMethod.GET)
	@ResponseBody
	public ListPruebaProgramadas getPruebasVo() {
		List<PruebaProgramada> pruebas = new ArrayList<PruebaProgramada>();
		
		Calendar fechaProgrInicio = Calendar.getInstance();
		fechaProgrInicio.add(Calendar.SECOND, 1800);
		Date date = fechaProgrInicio.getTime();
		
		PruebaProgramada prueba1 = new PruebaProgramada("id_prueba_1",
				"4889e0d745694a7600b7ec5d9849bc90", 
				"4889e0d745694a7600b7ec5d9849bc79", 
				'p', date);
		pruebas.add(prueba1);
		
		ListPruebaProgramadas list = new ListPruebaProgramadas(pruebas);
		return list;
	}

	@RequestMapping(value = "/insertorupdate_pruebas",headers = "content-type=application/json", method = RequestMethod.POST)
	@ResponseBody
	public int insertOrUpdatePruebaVo(@RequestBody ListPruebaProgramadas pruebas) {
		boolean result = false;
		if(pruebas!= null && (pruebas.getListpruebas()!= null) && !(pruebas.getListpruebas().isEmpty())){
			for(PruebaProgramada prueba : pruebas.getListpruebas()){
				System.out.println("id prueba: "+prueba.getIdprueba()+"   estado: "+prueba.getEstado());
				System.out.println("id tipo prueba: "+prueba.getIdtipoprueba()+"   id paciente: "+prueba.getIdpaciente());
				System.out.println("fecha inicio:  "+prueba.getFecha());
				System.out.println("----------------------------------------------------------------------------");
			}
			result = serviceViewOSGiFacade.insertOrUpdatePrueba(pruebas);
		}
		return (result)? 1 : 0;
	}

	@RequestMapping(value = "/ids_pruebas", method = RequestMethod.GET)
	@ResponseBody
	public ListIdsPruebas getIdsPruebasVo() {
		List<String> ids_pruebas = new ArrayList<String>();
		ids_pruebas.add("id_1");
		ids_pruebas.add("id_2");
		ids_pruebas.add("id_3");
		
		ListIdsPruebas list = new ListIdsPruebas();
		list.setIds(ids_pruebas);
		return list;
	}

	@RequestMapping(value = "/delete_pruebas", headers = "content-type=application/json", method = RequestMethod.POST)
	@ResponseBody
	public int deletePruebasVo(@RequestBody ListIdsPruebas pruebas) {
		boolean result = false;
		if((pruebas!= null) && (pruebas.getIds() != null) && (!pruebas.getIds().isEmpty())){
			for(String id : pruebas.getIds()){
				System.out.println("id "+id);
			}
			result = serviceViewOSGiFacade.deletePrueba(pruebas);
		}
		return (result)? 1 : 0;
	}
	
	@RequestMapping(value = "/historial_result_format", method = RequestMethod.GET)
	@ResponseBody
	public HistorialResult getHistorialResult() {
		Map<TipoTrama,List<Float>> resultados = new HashMap<TipoTrama,List<Float>>();
		List<Float> valores = new ArrayList<Float>();
		
		for(TipoTrama tipoTrama : TipoTrama.values()){
			Float x = new Float(100);	
			for(int i=0; i<10;i++){
				valores.add(x+i);
			}
			resultados.put(tipoTrama, valores);
		}
		HistorialResult historialResult = new HistorialResult("id_prueba_1",resultados);
		return historialResult;
	}
	
	@RequestMapping(value = "/set_historial", headers = "content-type=application/json", method = RequestMethod.POST)
	@ResponseBody
	public int setHistorial(@RequestBody HistorialResult historial) {
		if((historial!= null)){
			System.out.println("id "+historial.getIdPrueba());
			Map<TipoTrama,List<Float>> mapResultados = historial.getResultados();
			for( Entry<TipoTrama,List<Float>> entry : mapResultados.entrySet()){
				System.out.println("tipo "+entry.getKey());
				for(Float valor : entry.getValue()){
					System.out.println("valor "+valor);
				}
			}
			return 1;
		}
		return 0;
	}

	@RequestMapping(value = "/send_json", method = RequestMethod.GET)
	@ResponseBody
	public ResultSendHistorial sendJson(){
		if(modelPruebas.isDemoPrueba(this.historialResult.getIdPrueba())){
			return new ResultSendHistorial(2,"Puede volver al menú principal");
		}else{
			System.out.println("enviando historial");

			HistorialResult historialResult = this.historialResult;

			RestTemplate restTemplate = new RestTemplate();
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			restTemplate.setRequestFactory(factory);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HistorialResult> entity = new HttpEntity<HistorialResult>(
					historialResult, headers);
			String url = new String(
					"http://174.129.197.0:8080/services/setResults.php");

			ResultSendHistorial resultSendHistorial = restTemplate
					.postForObject(url, entity, ResultSendHistorial.class);
			return resultSendHistorial;
		}
	}

}
