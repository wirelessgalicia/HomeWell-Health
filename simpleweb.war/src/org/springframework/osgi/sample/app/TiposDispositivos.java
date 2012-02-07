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

import java.util.HashMap;
import java.util.Map;

public class TiposDispositivos {

	private static TiposDispositivos dispositivos;
	
	private static Map<String,String> mapTypes = new HashMap<String,String>();
	
	public static TiposDispositivos getInstance(){
		if(dispositivos == null){
			initMapTypes();
			return new TiposDispositivos();
		}
		return dispositivos;
	}

	private static void initMapTypes(){
		mapTypes.put("4c5ae1e545694a76015c07d83bf6657a", "ciclo");
		mapTypes.put("4c541c3245694a76001486625cee939c" ,"ciclo"); 
		mapTypes.put("4c541c3245694a76001486625cee939d" ,"pulso");
		mapTypes.put("f85fa51e45694a76013f210fbb70a712" ,"pulso");
		mapTypes.put("f85fa51e45694a76013f210fbb70a769","tension");
		mapTypes.put("4889e07a45694a7600b7ec5ded8995fb" ,"peso");
		mapTypes.put("4c541c3245694a76001486625ceesiba" ,"peso");
		
	}
	
	public String getTipo(String key){
		return mapTypes.get(key);
	}
}
