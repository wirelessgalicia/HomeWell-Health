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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.osgi.sample.app.DemoPrueba;
import org.springframework.osgi.sample.app.Row;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import es.sergas.chuac.logos.modelo.prmsalida.vo.PrmSalidaVO;
import es.sergas.chuac.logos.modelo.prueba.dao.SQLPruebaDAO;
import es.sergas.chuac.logos.modelo.tipoprueba.dao.SQLTipoPruebaDAO;
import es.sergas.chuac.logos.modelo.prueba.vo.PruebaVO;
import es.sergas.chuac.logos.modelo.tipoprueba.vo.TipoPruebaVO;
import es.sergas.chuac.logos.modelo.fase.vo.FaseVO;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.BeanDefinition;
import es.sergas.chuac.logos.modelo.fase.dao.SQLFaseDAO;
import es.sergas.chuac.logos.modelo.tipofase.dao.SQLTipoFaseDAO;
import es.sergas.chuac.logos.modelo.prmsalida.dao.SQLPrmSalidaDAO;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import es.sergas.chuac.logos.modelo.tipofase.vo.TipoFaseVO;

import es.sergas.chuac.logos.util.exception.InstanceNotFoundException;
import es.sergas.chuac.logos.util.exception.InternalErrorException;

public class ModelPruebas implements IModelPruebas{

	private String idProtocolo = "4889e0d745694a7600b7ec5d9849bc33";
	/*
	 * Daos
	 */
	private SQLPruebaDAO pruebaDAO;
	
	private SQLTipoPruebaDAO tipoPruebaDAO;
	
	private SQLFaseDAO faseDAO;
	
	private SQLPrmSalidaDAO prmSalidaDAO;

	private SQLTipoFaseDAO tipoFaseDAO;

	/*
	 * Properties
	 */
	private List<PruebaVO> pruebas = new ArrayList<PruebaVO>();
	
	private PruebaVO prueba;

	private TipoPruebaVO tipoPrueba;

	private Map<FaseVO,TipoFaseVO> mapFases = new HashMap<FaseVO,TipoFaseVO>();

	private Map<FaseVO,List<PrmSalidaVO>> mapParametros = new HashMap<FaseVO,List<PrmSalidaVO>>();
	
	
	/*
	 * flags de control
	 */
	private FaseVO faseActual;
	
	private int numFaseActual = 0;

	
	public ModelPruebas(SQLPruebaDAO pruebaDAO, SQLTipoPruebaDAO tipoPruebaDAO,
			SQLFaseDAO faseDAO, SQLTipoFaseDAO tipoFaseDAO, SQLPrmSalidaDAO prmSalidaDAO) {
		this.pruebaDAO = pruebaDAO;
		this.tipoPruebaDAO = tipoPruebaDAO;
		this.faseDAO = faseDAO;
		this.tipoFaseDAO = tipoFaseDAO;
		this.prmSalidaDAO = prmSalidaDAO;
	}

	/*
	 * Methods to retrieve data
	 */
	public void init(){
		setPrueba(initPrueba());
		setTipoPrueba(initTipoPrueba());
		setMapFases(initFases());
		initParametros();
		numFaseActual = 0;
	}

	@Override
	public void updateFase(){
		System.out.println("Fase anterior: "+numFaseActual+" updating..");
		numFaseActual++;
		this.faseActual = null;
		
		System.out.println("Buscando Fase : "+numFaseActual);
		for(Entry<FaseVO,TipoFaseVO> entry : this.mapFases.entrySet()){
			System.out.println("orden :"+ entry.getValue().getOrden());
			if(entry.getValue().getOrden() == numFaseActual){
				this.faseActual = entry.getKey();
			}
		}
	}

	public int lastFase(){
		int last = 0;
		for(Entry<FaseVO,TipoFaseVO> entry : this.mapFases.entrySet()){
			if(entry.getValue().getOrden() > last){
				last = entry.getValue().getOrden();
			}
		}
		System.out.println("last fase :"+ last);
		return last;
	}

	@Override
	public void update(PruebaVO prueba){
		setPrueba(prueba);
		if(prueba != null){
			System.out.println("prueba "+prueba.getId());
			System.out.println("fases"+prueba.getFases().size());
		}
		setTipoPrueba(initTipoPrueba());
		setMapFases(initFases());
	}

	public PruebaVO initPrueba(){
		// se obtiene la próxima prueba a realizar
		Calendar today = Calendar.getInstance();// .getTime();
		System.out.println("today "+today);
		try {
			return pruebaDAO.findSiguientePruebaPendienteByProtocoloByFecha(
					idProtocolo, today);
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public TipoPruebaVO initTipoPrueba(){
		if(prueba!=null){
			try {
				return (TipoPruebaVO) tipoPruebaDAO
						.findTipoPruebaById(prueba.getIdTipoPruebaS());
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InternalErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public Map<FaseVO,TipoFaseVO> initFases(){
		mapFases = new HashMap<FaseVO,TipoFaseVO>();
		for(FaseVO fase : getFasesByPrueba()){
			TipoFaseVO tipoFase = getTipoFaseById(fase.getIdTipoFaseS());
			mapFases.put(fase,tipoFase);
		}
		return mapFases;
	}
	
	@Override
	public boolean haySiguienteFase(){
		int faseSig = numFaseActual;
		faseSig++;
		FaseVO faseSigVO = null;
		for(Entry<FaseVO,TipoFaseVO> entry : this.mapFases.entrySet()){
			if(entry.getValue().getOrden() == faseSig){
				faseSigVO = entry.getKey();
			}
		}
		return faseSigVO == null ? false : true;
	}

	public void initParametros() {
		this.mapParametros = new HashMap<FaseVO, List<PrmSalidaVO>>();
		for (Entry<FaseVO,TipoFaseVO> entry : this.getMapFases().entrySet()) {
			List<PrmSalidaVO> parametros;
			try {
				parametros = prmSalidaDAO.findAllPrmSalidaByFase(entry
						.getKey().getId());
				mapParametros.put(entry.getKey(), parametros);
			} catch (InternalErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<PruebaVO> getPruebasPendientes(){
		pruebas = new ArrayList<PruebaVO>();
		try {
			pruebas =  (List<PruebaVO>) pruebaDAO.findPruebasProgramadas();
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return pruebas;
	}

	public List<FaseVO> getFasesByPrueba(){
		if (prueba != null) {
			try {
				return (List<FaseVO>) this.faseDAO.findAllFasesByPrueba(prueba
						.getId());
			} catch (InternalErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ArrayList<FaseVO>();
	}

	public TipoFaseVO getTipoFaseById(String id){
		if (id != null) {
			try {
				return (TipoFaseVO)tipoFaseDAO.findTipoFaseById(id);
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InternalErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public PruebaVO getDetallesPrueba(String idPrueba){
		try {
			return ((PruebaVO) pruebaDAO.findPruebaById(idPrueba));
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//method to parse in format json
	@Override
	public List<Row> getDetallesPruebaRows(String id){
		List<Row> rows = new ArrayList<Row>();
		PruebaVO prueba = getDetallesPrueba(id);
		if( prueba != null){
			//update prueba
			update(prueba);
			
			List<String> list = new ArrayList();
			list.add(prueba.getId());
			list.add(prueba.getIdTipoPruebaS());
			rows.add(new Row(prueba.getId(), list));
		}
		return rows;
	}
	
	public List<Row> getPruebasRows(List<PruebaVO> pruebas){
		List<Row> rows = new ArrayList<Row>();
		for(PruebaVO prueba : pruebas){
			List<String> list = new ArrayList();
			list.add(prueba.getId());
			list.add(prueba.getEstado());
			rows.add(new Row(prueba.getId(), list));
		}
		return rows;
	}
	/*
	 * Setters & Getters
	 */
	public void setPrueba(PruebaVO prueba) {
		this.prueba = prueba;
	}

	@Override
	public PruebaVO getPrueba() {
		return prueba;
	}

	public void setTipoPrueba(TipoPruebaVO tipoPrueba) {
		this.tipoPrueba = tipoPrueba;
	}

	@Override
	public TipoPruebaVO getTipoPrueba() {
		return this.tipoPrueba;
	}
	
	@Override
	public String getTipoPruebaClass() {
		String id = this.tipoPrueba.getId();
		String desc = this.tipoPrueba.getDescripcion().toLowerCase();
		if(id.equals("4889e0d745694a7600b7ec5d9849bc10") ||
			id.equals("4889e0d745694a7600b7ec5d9849bc30") ||
			id.equals("4889e0d745694a7600b7ec5d9849bc55")||
			id.equals("170731e545694a7601e8b9575c966297")||
			id.equals("3a3a70a345694a7601ba92bbe976bbd9")){
				return "pulso_ciclo";
		}
		 
		if(id.equals("4889e0d745694a7600b7ec5d9849bc32") ||
				id.equals("4889e0d745694a7600b7ec5d9849bc70") ||
				id.equals("4889e0d745694a7600b7ec5d9849bc50") ||
				id.equals("4889e0d745694a7600b7ec5d9849bc85") ||
				id.equals("4889e0d745694a7600b7ec5d9849bc80")){
			return "pulso";
		} 
		if(id.equals("4889e0d745694a7600b7ec5d9849bc40") ||
				id.equals("4889e0d745694a7600b7ec5d9849bc45")){
			return "ciclo";
		}
		if(id.equals("4889e0d745694a7600b7ec5d9849bc12")){
			return "tension";
		}
		if(id.equals("4889e0d745694a7600b7ec5d9849bc90") ||
				id.equals("3a451bb845694a7600ae6f4c928c02b6") ||
				id.equals("3a0d12e945694a7601ba92bb49684123")){
			return "peso"; 
		}
		if(id.equals("3a424a0a45694a7600ae6f4cd2e047b1") ||
				id.equals("445a07ac7f00010101e36ec6184c9f1e") ||
				id.equals("3a3a70a345694a7601ba92bbe976bbd9")){
			return "bascula_pulso_ciclo"; 
		}
		if(id.equals("2613d7a47f00010101d742a1473fdd64") ||
				id.equals("16bc2c6245694a760051207c9e98b2c0")){
			return "bascula_pulso";
		}
		return "";
	}
	
	public void setMapFases(Map<FaseVO,TipoFaseVO> mapFases) {
		this.mapFases = mapFases;
	}

	@Override
	public Map<FaseVO,TipoFaseVO> getMapFases() {
		return mapFases;
	}

	public void setFaseActual(FaseVO faseActual) {
		this.faseActual = faseActual;
	}

	@Override
	public FaseVO getFaseActual() {
		return faseActual;
	}

	@Override
	public TipoFaseVO getTipoFaseActual(){
		return this.mapFases.get(faseActual);
	}

	public List<PrmSalidaVO> getParametrosActuales(){
		return this.mapParametros.get(faseActual);
	}

	public void setNumFaseActual(int numFaseActual) {
		this.numFaseActual = numFaseActual;
	}

	@Override
	public int getNumFaseActual() {
		return numFaseActual;
	}

	public void setMapParametros(Map<FaseVO,List<PrmSalidaVO>> mapParametros) {
		this.mapParametros = mapParametros;
	}

	public Map<FaseVO,List<PrmSalidaVO>> getMapParametros() {
		return mapParametros;
	}

	@Override
	public boolean isDemoPrueba(String idPrueba) {
		if(idPrueba.equals(DemoPrueba.Peso.getIdPrueba()) || 
				idPrueba.equals(DemoPrueba.PulsoCiclo.getIdPrueba()) ||
				idPrueba.equals(DemoPrueba.PesoPulso.getIdPrueba())){
			return true;
		}
		return false;
	}

}
