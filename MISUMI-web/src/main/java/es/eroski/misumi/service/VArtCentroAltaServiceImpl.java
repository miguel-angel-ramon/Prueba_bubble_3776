package es.eroski.misumi.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CcRefCentroDao;
import es.eroski.misumi.dao.iface.QueHacerRefDao;
import es.eroski.misumi.dao.iface.VArtCentroAltaDao;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VArtCentroAltaService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service(value = "VArtCentroAltaService")
public class VArtCentroAltaServiceImpl implements VArtCentroAltaService {
	//private static Logger logger = LoggerFactory.getLogger(VArtCentroAltaServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VArtCentroAltaServiceImpl.class);
	
    @Autowired
	private VArtCentroAltaDao vArtCentroAltaDao;
    @Autowired
	private CcRefCentroDao ccRefCentroDao;
    @Autowired
	private QueHacerRefDao queHacerRefDao;
    
	@Override
	 public List<VArtCentroAlta> findAll(VArtCentroAlta vArtCentroAlta, Pagination pagination) throws Exception {
		
		List<QueHacerRef> listaQueHacerRef = new ArrayList<QueHacerRef>();
		List<VArtCentroAlta> lista = new ArrayList<VArtCentroAlta>();
		List<VArtCentroAlta> listaN2 = new ArrayList<VArtCentroAlta>();
		
		
		if ((vArtCentroAlta.getGrupo1() != null) && !(vArtCentroAlta.getGrupo1().equals(""))) { //Es un busqueda por estructura
		
			if (Constantes.AREA_TEXTIL.equals(vArtCentroAlta.getGrupo1().toString())){ //PET.49318. Si es una estructura de textil, se obtendran los datos de 
																					   //la vista V_ART_CENTRO_ALTA_TEX_N1 (Solo los de primer nivel)
				lista = this.vArtCentroAltaDao.findAllTextilN1(vArtCentroAlta, pagination);
			
			} else { //PET.49318. Si NO es una estructura de textil, se obtendran los datos de la vista V_ART_CENTRO_ALTA
				
				lista = this.vArtCentroAltaDao.findAll(vArtCentroAlta, pagination);
				
			}
		
		} else { //Es una busqueda por referencia
			
			//Consultamos V_ART_CENTRO_ALTA para saber si la referencia consultada es de textil o no
			lista = this.vArtCentroAltaDao.findAll(vArtCentroAlta, pagination);
			
			if ((lista != null) && (lista.size() > 0)) {
				if (Constantes.AREA_TEXTIL.equals(lista.get(0).getGrupo1().toString())) { 
					
					//Si es de textil, consultaremos V_ART_CENTRO_ALTA_TEX_N2, si existe, sabemos que corresponde a una 
					//referencia hija (nivel 2) y obtendremos su referencia padre. Si no existe, sabemos que es una referencia de nivel 1
					listaN2 = this.vArtCentroAltaDao.isReferenciaTextilN2(vArtCentroAlta);
					
					if ((listaN2 !=null) && (listaN2.size() > 0)) { 
						//Si la lista no es vacia,es una referencia hija, obtenemos la referencia 
						//lote para consultar con esta ultima la vista V_ART_CENTRO_ALTA_TEX_N1
						vArtCentroAlta.setCodArticulo(listaN2.get(0).getCodArticuloLote());	
					}
					
					//Obtenemos de V_ART_CENTRO_ALTA_TEX_N1 los datos para la referecia lote
					lista = this.vArtCentroAltaDao.findAllTextilN1(vArtCentroAlta, pagination);
					
				} 
			}
			
		}
		
		//Recorrido de la lista para obtener el campo de CC del PLSQL PK_APR_MISUMI.P_APR_OBT_CC_REF_CENTRO
		if (lista != null) {
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				VArtCentroAlta vArtCentroAltaActual = (VArtCentroAlta) iterator.next();
				//Preparar parámetros para búsqueda de cc
				CcRefCentro ccRefCentro = new CcRefCentro(vArtCentroAltaActual.getCodArticulo(), 
														  vArtCentroAltaActual.getCentro().getCodCentro(), 
														  new Date());
				
				
				vArtCentroAltaActual.setCc(ccRefCentroDao.consultaCc(ccRefCentro));
				vArtCentroAltaActual.setId(vArtCentroAltaActual.getCodArticulo().toString()); //En el caso de textil se utilizará para enlazar con las referencias hijas de un lote
				
			/*COMENTAMOS LA LLAMADA AL PROCEDIMIENTODE TEXTIL	
				//Montaje de lista de consulta de textil si es una referencia del área comercial textil
				if (Constantes.AREA_TEXTIL.equals(vArtCentroAltaActual.getGrupo1().toString())){
					QueHacerRef datosTextilActual = new QueHacerRef();
					datosTextilActual.setCodArtFormlog(vArtCentroAltaActual.getCodArticulo());
					datosTextilActual.setCodLoc(vArtCentroAltaActual.getCentro().getCodCentro());
	
					datosTextilActual = queHacerRefDao.obtenerAccionRef(datosTextilActual);
	
					if (datosTextilActual.getDescrTemporadaAbr() != null){
						if ("GP-OI".equals(datosTextilActual.getDescrTemporadaAbr()) || "GP-PV".equals(datosTextilActual.getDescrTemporadaAbr())){
							vArtCentroAltaActual.setTemporada("GP");
						}else{
							vArtCentroAltaActual.setTemporada(datosTextilActual.getDescrTemporadaAbr());
						}
					}
					if (datosTextilActual.getAnioColeccion() != null){
						if (datosTextilActual.getDescrTemporadaAbr() != null && "OI".equals(datosTextilActual.getDescrTemporadaAbr()) || "PV".equals(datosTextilActual.getDescrTemporadaAbr())){
							vArtCentroAltaActual.setAnioColeccion(datosTextilActual.getAnioColeccion());
						}else{
							vArtCentroAltaActual.setAnioColeccion("9999");
						}
					}
					vArtCentroAltaActual.setTalla(datosTextilActual.getTalla());
					vArtCentroAltaActual.setColor(datosTextilActual.getColor());
					vArtCentroAltaActual.setLote(datosTextilActual.getFlgLote());
					vArtCentroAltaActual.setModeloProveedor(datosTextilActual.getRefProveedor());
					vArtCentroAltaActual.setModelo(datosTextilActual.getConverArt());
				}
			 */
				
			}
		}
		return lista;
				
	}
	
	@Override
	public Long findAllCont(VArtCentroAlta vArtCentroAlta) throws Exception{
		return this.vArtCentroAltaDao.findAllCont(vArtCentroAlta);
	}
	@Override
	public List<GenericExcelVO> findAllExcel(VArtCentroAlta vArtCentroAlta,String[] columnModel)
			throws Exception{
		List<GenericExcelVO> lista = this.vArtCentroAltaDao.findAllExcel(vArtCentroAlta,columnModel);
		if (lista != null && !lista.isEmpty()){
			
			//Obtención de posición de campo cod_articulo y CC del columModel para sustituirlo por el dato del PLSQL
			//Creación de setters por reflection
			Class clase = Class.forName("es.eroski.misumi.model.GenericExcelVO");
			boolean codArtIncluido = false; //Control para saber si viene el campo de código de artículo
			Object objCodArticulo = null;
			Long codArticuloActual = null;
			int posicionCampoCodArticulo = 0; //Si no viene el artículo estará en la última posición 
			int posicionCampoCC = 0;
			int posicionCampoTemporada = 0;
			int posicionCampoAnioColeccion = 0;
			int posicionCampoModeloProveedor = 0;
			int posicionCampoTalla = 0;
			int posicionCampoColor = 0;
			int posicionCampoLote = 0;
			int posicionCampoModelo = 0;
			int posicionCampoOrden = 0;
			List<String> listColumns = Arrays.asList(columnModel);
	    	for(int i=0; i<listColumns.size();i++){
	    		if (listColumns.get(i)!= null && "codArticulo".equals(listColumns.get(i))){
	    			posicionCampoCodArticulo = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    			codArtIncluido = true;
	    		}
	    		if (listColumns.get(i)!= null && "cc".equals(listColumns.get(i))){
	    			posicionCampoCC = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "temporada".equals(listColumns.get(i))){
	    			posicionCampoTemporada = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "anioColeccion".equals(listColumns.get(i))){
	    			posicionCampoAnioColeccion = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "modeloProveedor".equals(listColumns.get(i))){
	    			posicionCampoModeloProveedor = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "talla".equals(listColumns.get(i))){
	    			posicionCampoTalla = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "color".equals(listColumns.get(i))){
	    			posicionCampoColor = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "lote".equals(listColumns.get(i))){
	    			posicionCampoLote = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "modelo".equals(listColumns.get(i))){
	    			posicionCampoModelo = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}
	    		if (listColumns.get(i)!= null && "orden".equals(listColumns.get(i))){
	    			posicionCampoOrden = i + 1; //Se corrige la posición del campo porque los métodos empiezan desde getField1
	    		}

	    	}
	    	//Si no viene el codarticulo en el colmodel se habrá puesto en la búsqueda como último campo
	    	if(!codArtIncluido){
	    		posicionCampoCodArticulo = 47;
	    	}
	    	
			Method getterCodArticulo = clase.getMethod("getField" + posicionCampoCodArticulo);
			Method setterCodArticulo = clase.getMethod("setField" + posicionCampoCodArticulo, new Class[]{Object.class});
			Method setterCC = null;
			Method setterTemporada = null;
			Method setterAnioColeccion = null;
			Method setterModeloProveedor = null;
			Method setterTalla = null;
			Method setterColor = null;
			Method setterLote = null;
			Method setterModelo = null;
			Method setterOrden = null;

			if (posicionCampoCC > 0){
				setterCC= clase.getMethod("setField" + posicionCampoCC, new Class[]{Object.class});
			}
			if (posicionCampoTemporada > 0){
				setterTemporada= clase.getMethod("setField" + posicionCampoTemporada, new Class[]{Object.class});
			}
			if (posicionCampoAnioColeccion > 0){
				setterAnioColeccion= clase.getMethod("setField" + posicionCampoAnioColeccion, new Class[]{Object.class});
			}
			if (posicionCampoModeloProveedor > 0){
				setterModeloProveedor= clase.getMethod("setField" + posicionCampoModeloProveedor, new Class[]{Object.class});
			}
			if (posicionCampoTalla > 0){
				setterTalla= clase.getMethod("setField" + posicionCampoTalla, new Class[]{Object.class});
			}
			if (posicionCampoColor > 0){
				setterColor= clase.getMethod("setField" + posicionCampoColor, new Class[]{Object.class});
			}
			if (posicionCampoLote > 0){
				setterLote= clase.getMethod("setField" + posicionCampoLote, new Class[]{Object.class});
			}
			if (posicionCampoModelo > 0){
				setterModelo= clase.getMethod("setField" + posicionCampoModelo, new Class[]{Object.class});
			}
			if (posicionCampoOrden > 0){
				setterOrden= clase.getMethod("setField" + posicionCampoOrden, new Class[]{Object.class});
			}

			//Recorrido de la lista para obtener el campo de CC del PLSQL PK_APR_MAESTROS_01.P_APR_OBT_CC_REF_CENTRO
			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				GenericExcelVO vArtCentroAltaActual = (GenericExcelVO) iterator.next();
				//Preparar parámetros para búsqueda de cc
				if (posicionCampoCC > 0 || posicionCampoTemporada > 0 || posicionCampoAnioColeccion > 0 || posicionCampoModeloProveedor > 0 || 
						posicionCampoTalla > 0 || posicionCampoColor > 0 || posicionCampoLote > 0 || posicionCampoModelo > 0 || posicionCampoOrden > 0){
					objCodArticulo = getterCodArticulo.invoke(vArtCentroAltaActual, new Object[0]);
					codArticuloActual = new Long(objCodArticulo.toString());
				}
				if (posicionCampoCC > 0){
					CcRefCentro ccRefCentro = new CcRefCentro(codArticuloActual, 
															  vArtCentroAlta.getCentro().getCodCentro(), 
															  new Date());
					
					setterCC.invoke(vArtCentroAltaActual, ccRefCentroDao.consultaCc(ccRefCentro));
				}
				
				if (posicionCampoTemporada > 0 || posicionCampoAnioColeccion > 0 || posicionCampoModeloProveedor > 0 || posicionCampoTalla > 0 ||
						posicionCampoColor > 0 || posicionCampoLote > 0 || posicionCampoOrden > 0){
					//Búsqueda de área del artículo
					VArtCentroAlta vArtCentroAltaBusquedaEstr = new VArtCentroAlta();
					vArtCentroAltaBusquedaEstr.setCodArticulo(codArticuloActual);
					vArtCentroAltaBusquedaEstr.setCentro(vArtCentroAlta.getCentro());
					
					vArtCentroAltaBusquedaEstr.setCatalogo(vArtCentroAlta.getCatalogo());
					vArtCentroAltaBusquedaEstr.setFacingCero(vArtCentroAlta.getFacingCero());
					vArtCentroAltaBusquedaEstr.setLoteSN(vArtCentroAlta.getLoteSN());
					vArtCentroAltaBusquedaEstr.setMarcaMaestroCentro(vArtCentroAlta.getMarcaMaestroCentro());
					vArtCentroAltaBusquedaEstr.setNivel(vArtCentroAlta.getNivel());
					
					List<VArtCentroAlta> listaVArtCentroAlta = vArtCentroAltaDao.findAll(vArtCentroAltaBusquedaEstr, null);
					if (listaVArtCentroAlta != null && listaVArtCentroAlta.size() > 0){
						vArtCentroAltaBusquedaEstr = listaVArtCentroAlta.get(0);
					}
					
					//Montaje de lista de consulta de textil si es una referencia del área comercial textil
					if (vArtCentroAltaBusquedaEstr != null && Constantes.AREA_TEXTIL.equals(vArtCentroAltaBusquedaEstr.getGrupo1().toString())){
						
						
						//QueHacerRef datosTextilActual = new QueHacerRef();
						//datosTextilActual.setCodArtFormlog(vArtCentroAltaBusquedaEstr.getCodArticulo());
						//datosTextilActual.setCodLoc(vArtCentroAltaBusquedaEstr.getCentro().getCodCentro());
	
						//datosTextilActual = queHacerRefDao.obtenerAccionRef(datosTextilActual);
						
						//Obtenemos los datos especificos de textil para el aticulos que corresonda
						vArtCentroAlta.setCodArticulo(codArticuloActual);
						List<VArtCentroAlta> datosTextilActual = new ArrayList<VArtCentroAlta>();
						datosTextilActual = this.vArtCentroAltaDao.findAllTextilN1(vArtCentroAlta, null);
						
						vArtCentroAltaBusquedaEstr.setTemporada(datosTextilActual.get(0).getTemporada());	
						vArtCentroAltaBusquedaEstr.setAnioColeccion(datosTextilActual.get(0).getAnioColeccion());
						vArtCentroAltaBusquedaEstr.setTalla(datosTextilActual.get(0).getTalla());
						vArtCentroAltaBusquedaEstr.setColor(datosTextilActual.get(0).getColor());
						vArtCentroAltaBusquedaEstr.setLote(datosTextilActual.get(0).getLote());
						vArtCentroAltaBusquedaEstr.setModeloProveedor(datosTextilActual.get(0).getModeloProveedor());
						vArtCentroAltaBusquedaEstr.setModelo(datosTextilActual.get(0).getModelo());
						vArtCentroAltaBusquedaEstr.setOrden(datosTextilActual.get(0).getTemporada() + " "+ datosTextilActual.get(0).getAnioColeccion() + " " +  datosTextilActual.get(0).getGrupo2() + datosTextilActual.get(0).getGrupo3() + datosTextilActual.get(0).getGrupo4() + datosTextilActual.get(0).getGrupo5() + " " + Utilidades.rellenarIzquierda(datosTextilActual.get(0).getNumOrden(), '0', 3));
					}
					
					if (posicionCampoTemporada > 0){
						setterTemporada.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getTemporada());
					}
					if (posicionCampoAnioColeccion > 0){
						setterAnioColeccion.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getAnioColeccion());
					}
					if (posicionCampoModeloProveedor > 0){
						setterModeloProveedor.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getModeloProveedor());
					}
					if (posicionCampoTalla > 0){
						setterTalla.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getTalla());
					}
					if (posicionCampoColor > 0){
						setterColor.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getColor());
					}
					if (posicionCampoLote > 0){
						setterLote.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getLote());
					}
					if (posicionCampoModelo > 0){
						setterModelo.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getModelo());
					}
					if (posicionCampoOrden > 0){
						setterOrden.invoke(vArtCentroAltaActual, vArtCentroAltaBusquedaEstr.getOrden());
					}
				}
				
				//En caso de no venir el codArticulo en el colmodel hay que limpiar el dato
				if (!codArtIncluido){
					setterCodArticulo.invoke(vArtCentroAltaActual, "");
				}
			}
		}
		
		return lista;
	}
	
	@Override
	public List<VArtCentroAlta> findAllExcelTextil(VArtCentroAlta vArtCentroAlta,String[] columnModel)
			throws Exception{
		
	      List<VArtCentroAlta> listaExcel = new ArrayList<VArtCentroAlta>();
	      List<VArtCentroAlta> listaN2 = new ArrayList<VArtCentroAlta>();
	      
	      if (!((vArtCentroAlta.getGrupo1() != null) && (vArtCentroAlta.getGrupo1() > 0))) { // Es una busqueda por estructura
	      
	    	  	//Consultaremos V_ART_CENTRO_ALTA_TEX_N2, si existe, sabemos que corresponde a una 
				//referencia hija (nivel 2) y obtendremos su referencia padre. Si no existe, sabemos que es una referencia de nivel 1
				listaN2 = this.vArtCentroAltaDao.isReferenciaTextilN2(vArtCentroAlta);
				
				if ((listaN2 !=null) && (listaN2.size() > 0)) { 
					//Si la lista no es vacia,es una referencia hija, obtenemos la referencia 
					//lote para consultar con esta ultima la vista V_ART_CENTRO_ALTA_TEX_N1
					vArtCentroAlta.setCodArticulo(listaN2.get(0).getCodArticuloLote());	
				}
		      
	      } 
	      
	      List<VArtCentroAlta> lista =  this.vArtCentroAltaDao.findAllTextilN1(vArtCentroAlta,null);

	      for(VArtCentroAlta articulo : lista){
	    	   
	    	   listaExcel.add(articulo);
//	           if (new Long(articulo.getNivelLote()) > 0) {
//	        	   		articulo.setId(String.valueOf(articulo.getCodArticulo()));
//	                    List<VArtCentroAlta> listaDetalle = this.vArtCentroAltaDao.findAllTextilN2ByLote(articulo);
//	                    listaExcel.addAll(listaDetalle); 
//	           }      
	       }

	      for(VArtCentroAlta articulo : listaExcel){
	    	  //articulo.setCampoOrdenacionExcel(articulo.getTemporada() + articulo.getAnioColeccion() + articulo.getModeloProveedor() + "-" + articulo.getColor()); 
	    	  
	    	  //(TEMPORADA (2 dígitos) + blanco + AÑO + blanco + COMERCIAL (todo menos área) + blanco + NUMERO ORDEN (3 dígitos)
	    	  articulo.setCampoOrdenacionExcel(articulo.getTemporada().substring(0, 2) + " " + articulo.getAnioColeccion() + " " +  articulo.getGrupo2() + articulo.getGrupo3() + articulo.getGrupo4() + articulo.getGrupo5() + " " + Utilidades.rellenarIzquierda(articulo.getNumOrden(), '0', 3));
	      }
		
		return listaExcel;
	}
	
	@Override
	public boolean esTextilPedible(Centro centro, Long codArticulo) throws Exception {
		boolean resultado = false;
	    VArtCentroAlta vArtCentroAlta = new VArtCentroAlta(centro,codArticulo,null,null,null,null,null,
	    													null,null,null,null,null,null,null,
	    													null,null,null,null,null,null,null,
	    													null,null,null,null,null,null,null,
	    													null,null,null,null,null,null,null,
	    													null,null,null,null,null,null,null,
	    													null,null,null,null,null,null,null,
	    													null,null
															);
		List<VArtCentroAlta> lista = this.vArtCentroAltaDao.findAll(vArtCentroAlta, null);
		if (lista != null && lista.size() == 1) {
			if (lista.get(0) != null && lista.get(0).getPedible() != null) {
				resultado = Constantes.REF_TEXTIL_PEDIBLE_SI.equals(lista.get(0).getPedible().toUpperCase());
			}
		}
		return resultado;
	}
	
	
	@Override
	 public List<VArtCentroAlta> findArticulo(VArtCentroAlta vArtCentroAlta) throws Exception {
		Pagination pagination = null;
		List<VArtCentroAlta> lista = this.vArtCentroAltaDao.findAll(vArtCentroAlta, pagination);
		
		return lista;
	}
	
	@Override
	public Long findAllTextilN1Cont(VArtCentroAlta vArtCentroAlta) throws Exception{
		return this.vArtCentroAltaDao.findAllTextilN1Cont(vArtCentroAlta);
	}
	
	@Override
	 public List<VArtCentroAlta> findAllTextilN2ByLote(VArtCentroAlta vArtCentroAlta) throws Exception {
		List<VArtCentroAlta> lista = this.vArtCentroAltaDao.findAllTextilN2ByLote(vArtCentroAlta);
		
		return lista;
	}
	
}
