package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.User;

public interface UserService {
	public User find(User user) throws Exception ;
	public User findPda(User user) throws Exception ;
	public List<String> obtenerAvisosCentro(Long codCentro, HttpSession session) throws Exception;
	public boolean mostrarAvisoValidarCantidadesExtra(Long codCentro, HttpSession session) throws Exception;
	public List<String> mostrarAvisoPedidoAdicional(Long codCentro) throws Exception;
	public boolean mostrarAvisoDevoluciones(Long codCentro) throws Exception;
	public DevolucionAvisos mostrarAvisoDevolucionesUrgente(Long codCentro) throws Exception;
	public String mostrarAvisoAlarmasPLU(Long codCentro) throws Exception;
}
