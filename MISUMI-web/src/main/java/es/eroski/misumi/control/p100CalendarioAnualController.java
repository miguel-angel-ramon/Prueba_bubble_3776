package es.eroski.misumi.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.eroski.misumi.model.CalendarioAnual;
import es.eroski.misumi.model.CalendarioTotal;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CalendarioService;

@Controller
@RequestMapping("/calendario/popup/calendarioAnual")
public class p100CalendarioAnualController {

	@Autowired
	private CalendarioService calendarioService;

	//Sirve para cargar los valores del combobox ordenados
	@RequestMapping(value = "/loadCalendarioAnual", method = RequestMethod.POST)
	public  @ResponseBody CalendarioAnual consultarCalendario(
			@RequestParam(value = "codigoEjercicio", required = true) Long codigoEjercicio,
			@RequestParam(value = "tipoEjercicio", required = true) String tipoEjercicio,
			HttpServletResponse response,
			HttpSession session) throws Exception{

		//Consultamos los días del calendario.

		User usuario= (User)session.getAttribute("user");

		//Obtenemos el centro.
		Long codCentro = usuario.getCentro().getCodCentro();

		//Obtiene la lista de meses y años del calendario actual.
		List<String> mesAnioLst = calendarioService.findMesAnioCalendario(session.getId(),codCentro, tipoEjercicio, codigoEjercicio);

		CalendarioAnual calendarioAnual = new CalendarioAnual();

		//Obtenemos cada mes y sus días
		if(mesAnioLst != null && mesAnioLst.size()>0){
			List<CalendarioTotal> lstMesAnual = new ArrayList<CalendarioTotal>();
			for(String mesAnio:mesAnioLst){
				//Obtenemos la lista de días del mes.
				List<TCalendarioDia> listTCalendarioDia = this.calendarioService.findDiasCalendario(session.getId(), codCentro, mesAnio, null,null,null,null, tipoEjercicio, codigoEjercicio);
				CalendarioTotal mesAnual =  new CalendarioTotal(0L, "", null, null, null, listTCalendarioDia);

				//Insertamos el mes en la lista.
				lstMesAnual.add(mesAnual);
			}
			//Ya tenemos todos los días del año separados por meses.
			calendarioAnual.setLstMesAnual(lstMesAnual);
		}
		return calendarioAnual;
	}
}
