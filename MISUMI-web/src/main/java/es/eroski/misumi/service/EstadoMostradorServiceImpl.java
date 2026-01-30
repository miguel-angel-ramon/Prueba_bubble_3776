package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.EstadoMostradorSIADao;
import es.eroski.misumi.model.EstadoMostrador;
import es.eroski.misumi.model.EstadoMostradorSIALista;
import es.eroski.misumi.model.EstructuraArtMostrador;
import es.eroski.misumi.service.iface.EstadoMostradorService;

/**
 * 
 * Clase que contiene los metodos de los estados de las estructuras de un centro
 *
 */
@Service(value = "EstadoMostradorService")
public class EstadoMostradorServiceImpl implements EstadoMostradorService {

	@Autowired
	EstadoMostradorSIADao estadoDao;

	@Override
	public EstadoMostrador getEstados(Long codCentro) throws Exception {
		// Llamada al servicio que llama al nuevo PL
		EstadoMostradorSIALista estados = estadoDao.consultaEstados(codCentro);

		EstadoMostrador estado = new EstadoMostrador();
		estado.setLstEstadoEstructurasMostrador(
				null != estados ? estados.getDatos() : new ArrayList<EstructuraArtMostrador>());

		// Calculo del estado final para mostrar el mensaje adecuado
		Integer codMensajeEstado = 0;
		
		// Si sólo hay una Estructura Comercial --> Mostrar el mensaje que proceda.
		if (estado.getLstEstadoEstructurasMostrador() != null && estado.getLstEstadoEstructurasMostrador().size() == 1){
			EstructuraArtMostrador estadoEstructura = estado.getLstEstadoEstructurasMostrador().get(0);
			// Si es 3 --> "Aun no es horario."
			if (estadoEstructura.getEstado() == 3) {
				codMensajeEstado = 1;
			// Si es 2 o 4 -> "Pedido generado."
			} else if (estadoEstructura.getEstado() == 2 || estadoEstructura.getEstado() == 4) {
				codMensajeEstado = 0;
			// Si es 1 -> "Sin pedido para hoy."
			} else if (estadoEstructura.getEstado() == 1) {
				codMensajeEstado = 2;
			}
			
		// Si más de una Estructura Comercial
		}else{
			
			for (EstructuraArtMostrador estadoEstructura : estado.getLstEstadoEstructurasMostrador()) {
				
				// Si alguno es 4 o 2 -> Pedido generado
				if (estadoEstructura.getEstado() == 2 || estadoEstructura.getEstado() == 4) {
					codMensajeEstado = 0;
					break;
				}else{
					if (estadoEstructura.getEstado() == 3) {
						// Si alguno es 3 -> Aun no es horario
						codMensajeEstado = 1;
					} else if (estadoEstructura.getEstado() == 1 && codMensajeEstado != 1) {
						// Si todos son 1 -> No procede
						codMensajeEstado = 2;
					}
				}
				
			}
		}
		
		estado.setCodMensajeEstado(codMensajeEstado);

		return estado;
	}
}