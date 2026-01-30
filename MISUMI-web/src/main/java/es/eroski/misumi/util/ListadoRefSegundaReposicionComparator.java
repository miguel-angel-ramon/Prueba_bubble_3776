package es.eroski.misumi.util;

import java.lang.reflect.Constructor;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import es.eroski.misumi.model.ListadoRefSegundaReposicionSalida;

public class ListadoRefSegundaReposicionComparator {
	
	public static Comparator<ListadoRefSegundaReposicionSalida> getComparator(String index) throws Exception{
		index = StringUtils.capitalize(index);
		String className = index+"Comparator";
		String packageName = ListadoRefSegundaReposicionComparator.class.getName()+"$";
		//return (Comparator<VArtSfm>) Class.forName(packageName+className).newInstance();
		Class<?> enclosingClass = Class.forName(ListadoRefSegundaReposicionComparator.class.getName());
		Object enclosingInstance = enclosingClass.newInstance();
		Class<?> innerClass = Class.forName(packageName+className);
		Constructor<?> ctor = innerClass.getDeclaredConstructor(enclosingClass);
		return (Comparator<ListadoRefSegundaReposicionSalida>) ctor.newInstance(enclosingInstance);
	}
	
	public class DescCodN1Comparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {
        	
        	Integer result = (r1.getCodN1() == null ? new Long(0):new Long(r1.getCodN1())).compareTo((r2.getCodN1() == null ? new Long(0):new Long(r2.getCodN1())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN1() == null ? "":r1.getDescCodN1()).compareTo((r2.getDescCodN1() == null ? "":(r2.getDescCodN1())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN2Comparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	Integer result = (r1.getCodN2() == null ? new Long(0):new Long(r1.getCodN2())).compareTo((r2.getCodN2() == null ? new Long(0):new Long(r2.getCodN2())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN2() == null ? "":r1.getDescCodN2()).compareTo((r2.getDescCodN2() == null ? "":(r2.getDescCodN2())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN3Comparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	Integer result = (r1.getCodN3() == null ? new Long(0):new Long(r1.getCodN3())).compareTo((r2.getCodN3() == null ? new Long(0):new Long(r2.getCodN3())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN3() == null ? "":r1.getDescCodN3()).compareTo((r2.getDescCodN3() == null ? "":(r2.getDescCodN3())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN4Comparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	Integer result = (r1.getCodN4() == null ? new Long(0):new Long(r1.getCodN4())).compareTo((r2.getCodN4() == null ? new Long(0):new Long(r2.getCodN4())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN4() == null ? "":r1.getDescCodN4()).compareTo((r2.getDescCodN4() == null ? "":(r2.getDescCodN4())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN5Comparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	Integer result = (r1.getCodN5() == null ? new Long(0):new Long(r1.getCodN5())).compareTo((r2.getCodN5() == null ? new Long(0):new Long(r2.getCodN5())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN5() == null ? "":r1.getDescCodN5()).compareTo((r2.getDescCodN5() == null ? "":(r2.getDescCodN5())));
            	
        	}
        	return result;
        }
    }
	
	public class ReferenciaComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

		 public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

			 Integer result = (r1.getReferencia() == null ? new Long(0):new Long(r1.getReferencia())).compareTo((r2.getReferencia() == null ? new Long(0):new Long(r2.getReferencia())));
	        	
			 if (result == 0){
				 result = (r1.getReferencia() == null ? "":r1.getReferencia()).compareTo((r2.getReferencia() == null ? "":(r2.getReferencia())));
	            	
			 }
			 return result;
		 }
    }
	
	public class ReferenciaDescComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	return (r1.getReferenciaDesc() == null ? "":r1.getReferenciaDesc()).compareTo((r2.getReferenciaDesc() == null ? "":r2.getReferenciaDesc()));
        }
    }
	
	public class FacingComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

		public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

			 Integer result = (r1.getFacing() == null ? new Long(0):new Long(r1.getFacing())).compareTo((r2.getFacing() == null ? new Long(0):new Long(r2.getFacing())));
	        	
			 if (result == 0){
				 result = (r1.getFacing() == null ? "":r1.getFacing()).compareTo((r2.getFacing() == null ? "":(r2.getFacing())));
	            	
			 }
			 return result;
		}
    }
	
	public class CapacidadComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

		public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

			 Integer result = (r1.getCapacidad() == null ? new Long(0):new Long(r1.getCapacidad())).compareTo((r2.getCapacidad() == null ? new Long(0):new Long(r2.getCapacidad())));
	        	
			 if (result == 0){
				 result = (r1.getCapacidad() == null ? "":r1.getCapacidad()).compareTo((r2.getCapacidad() == null ? "":(r2.getCapacidad())));
	            	
			 }
			 return result;
		}
    }
	
	public class CajaExpositoraComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	return (r1.getCajaExpositora() == null ? "":r1.getCajaExpositora()).compareTo((r2.getCajaExpositora() == null ? "":r2.getCajaExpositora()));
        }
    }
	

	public class TendenciaComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

		public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

	        	return (r1.getTendencia() == null ? new Double(0):r1.getTendencia()).compareTo((r2.getTendencia() == null ? new Double(0):r2.getTendencia()));
	    }
	}
	
	public class VentaPrevistaComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

		public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {

        	return (r1.getVentaPrevista() == null ? new Double(0):r1.getVentaPrevista()).compareTo((r2.getVentaPrevista() == null ? new Double(0):r2.getVentaPrevista()));
    }
    }
	
	public class EstructuraComparator implements Comparator<ListadoRefSegundaReposicionSalida> {

        public int compare(final ListadoRefSegundaReposicionSalida r1, final ListadoRefSegundaReposicionSalida r2) {
        	
        	CompareToBuilder compareToBuilder = new CompareToBuilder();
        	compareToBuilder.append(r1.getCodN1(), r2.getCodN1());
            compareToBuilder.append(r1.getCodN2(), r2.getCodN2());
            compareToBuilder.append(r1.getCodN3(), r2.getCodN3());
            compareToBuilder.append(r1.getCodN4(), r2.getCodN4());
            compareToBuilder.append(r1.getCodN5(), r2.getCodN5());
            compareToBuilder.append(r1.getReferencia(), r2.getReferencia());
        
            // moves are not compared
            return compareToBuilder.toComparison();
        
     
        
        }
    }
	
}
