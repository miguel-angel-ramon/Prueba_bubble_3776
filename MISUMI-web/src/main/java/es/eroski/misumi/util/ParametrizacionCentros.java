package es.eroski.misumi.util;

public class ParametrizacionCentros  {

	
	private ParametrizacionCentros(){
	}
	
	public static boolean contieneOpcion(String opcionesHabilitadas, String opcion) {
		
		if (opcionesHabilitadas != null){
		
			if (opcionesHabilitadas.indexOf(opcion) != -1){
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}

    }

}