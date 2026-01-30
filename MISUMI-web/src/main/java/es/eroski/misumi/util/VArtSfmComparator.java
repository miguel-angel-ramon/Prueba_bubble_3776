package es.eroski.misumi.util;

import java.lang.reflect.Constructor;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import es.eroski.misumi.model.VArtSfm;

public class VArtSfmComparator {
	
	public static Comparator<VArtSfm> getComparator(String index) throws Exception{
		index = StringUtils.capitalize(index);
		String className = index+"Comparator";
		String packageName = VArtSfmComparator.class.getName()+"$";
		//return (Comparator<VArtSfm>) Class.forName(packageName+className).newInstance();
		Class<?> enclosingClass = Class.forName(VArtSfmComparator.class.getName());
		Object enclosingInstance = enclosingClass.newInstance();
		Class<?> innerClass = Class.forName(packageName+className);
		Constructor<?> ctor = innerClass.getDeclaredConstructor(enclosingClass);
		return (Comparator<VArtSfm>) ctor.newInstance(enclosingInstance);
	}
	
	public class DescCodN1Comparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {
        	
        	Integer result = (r1.getCodN1() == null ? new Long(0):new Long(r1.getCodN1())).compareTo((r2.getCodN1() == null ? new Long(0):new Long(r2.getCodN1())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN1() == null ? "":r1.getDescCodN1()).compareTo((r2.getDescCodN1() == null ? "":(r2.getDescCodN1())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN2Comparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	Integer result = (r1.getCodN2() == null ? new Long(0):new Long(r1.getCodN2())).compareTo((r2.getCodN2() == null ? new Long(0):new Long(r2.getCodN2())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN2() == null ? "":r1.getDescCodN2()).compareTo((r2.getDescCodN2() == null ? "":(r2.getDescCodN2())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN3Comparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	Integer result = (r1.getCodN3() == null ? new Long(0):new Long(r1.getCodN3())).compareTo((r2.getCodN3() == null ? new Long(0):new Long(r2.getCodN3())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN3() == null ? "":r1.getDescCodN3()).compareTo((r2.getDescCodN3() == null ? "":(r2.getDescCodN3())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN4Comparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	Integer result = (r1.getCodN4() == null ? new Long(0):new Long(r1.getCodN4())).compareTo((r2.getCodN4() == null ? new Long(0):new Long(r2.getCodN4())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN4() == null ? "":r1.getDescCodN4()).compareTo((r2.getDescCodN4() == null ? "":(r2.getDescCodN4())));
            	
        	}
        	return result;
        }
    }
	
	public class DescCodN5Comparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	Integer result = (r1.getCodN5() == null ? new Long(0):new Long(r1.getCodN5())).compareTo((r2.getCodN5() == null ? new Long(0):new Long(r2.getCodN5())));
        	
        	if (result == 0){
        		result = (r1.getDescCodN5() == null ? "":r1.getDescCodN5()).compareTo((r2.getDescCodN5() == null ? "":(r2.getDescCodN5())));
            	
        	}
        	return result;
        }
    }
	
	public class CodArticuloComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getCodArticulo() == null ? new Long(0):r1.getCodArticulo()).compareTo((r2.getCodArticulo() == null ? new Long(0):r2.getCodArticulo()));
        }
    }
	
	public class DenomInformeComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getDenomInforme() == null ? "":r1.getDenomInforme()).compareTo((r2.getDenomInforme() == null ? "":r2.getDenomInforme()));
        }
    }
	
	public class MarcaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getMarca() == null ? "":r1.getMarca()).compareTo((r2.getMarca() == null ? "":r2.getMarca()));
        }
    }
	
	public class LminComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getLmin() == null ? new Double(0):r1.getLmin()).compareTo((r2.getLmin() == null ? new Double(0):r2.getLmin()));
        }
    }
	
	public class LsfComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getLsf() == null ? new Double(0):r1.getLsf()).compareTo((r2.getLsf() == null ? new Double(0):r2.getLsf()));
        }
    }
	
	public class SfmComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getSfm() == null ? new Double(0):r1.getSfm()).compareTo((r2.getSfm() == null ? new Double(0):r2.getSfm()));
        }
    }
	
	public class CoberturaSfmComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getCoberturaSfm() == null ? new Double(0):r1.getCoberturaSfm()).compareTo((r2.getCoberturaSfm() == null ? new Double(0):r2.getCoberturaSfm()));
        }
    }
	
	public class VentaMediaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getVentaMedia() == null ? new Double(0):r1.getVentaMedia()).compareTo((r2.getVentaMedia() == null ? new Double(0):r2.getVentaMedia()));
        }
    }
	
	public class VentaAnticipadaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getVentaAnticipada() == null ? new Double(0):r1.getVentaAnticipada()).compareTo((r2.getVentaAnticipada() == null ? new Double(0):r2.getVentaAnticipada()));
        }
    }
	
	public class DiasStockComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	return (r1.getDiasStock() == null ? new Double(0):r1.getDiasStock()).compareTo((r2.getDiasStock() == null ? new Double(0):r2.getDiasStock()));
        }
    }
	
	public class StockComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getStock() == null ? new Double(0):r1.getStock()).compareTo((r2.getStock() == null ? new Double(0):r2.getStock()));
        }
    }
	
	public class VidaUtilComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getVidaUtil() == null ? new Long(0):r1.getVidaUtil()).compareTo((r2.getVidaUtil() == null ? new Long(0):r2.getVidaUtil()));
        }
    }
	
	public class MensajeComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

            return (r2.getCodError() == null ? new Long(0):r2.getCodError()).compareTo((r1.getCodError() == null ? new Long(0):r1.getCodError()));
        }
    }
	
	public class CcComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getCc() == null ? new Long(0):r1.getCc()).compareTo((r2.getCc() == null ? new Long(0):r2.getCc()));
        }
    }
	
	public class PedirComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getPedir() == null ? "":r1.getPedir()).compareTo((r2.getPedir() == null ? "":r2.getPedir()));
        }
    }
	
	public class TipoGamaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getTipoGama() == null ? "":r1.getTipoGama()).compareTo((r2.getTipoGama() == null ? "":r2.getTipoGama()));
        }
    }
	
	public class TipoAprovComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getTipoAprov() == null ? "":r1.getTipoAprov()).compareTo((r2.getTipoAprov() == null ? "":r2.getTipoAprov()));
        }
    }
	
	public class UcComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getUc() == null ? new Double(0):r1.getUc()).compareTo((r2.getUc() == null ? new Double(0):r2.getUc()));
        }
    }
	
	public class FacingCentroComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getFacingCentro() == null ? new Long(0):r1.getFacingCentro()).compareTo((r2.getFacingCentro() == null ? new Long(0):r2.getFacingCentro()));
        }
    }
	
	public class FacingPrevioComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

            return (r1.getFacingPrevio() == null ? new Long(0):r1.getFacingPrevio()).compareTo((r2.getFacingPrevio() == null ? new Long(0):r2.getFacingPrevio()));
        }
    }
	
	public class CapacidadComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {
        	
            return(r1.getCapacidad() == null ? new Double(0):r1.getCapacidad()).compareTo((r2.getCapacidad() == null ? new Double(0):r2.getCapacidad()));
        }
    }

	public class TemporadaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getTemporada() == null ? "":r1.getTemporada()).compareTo((r2.getTemporada() == null ? "":r2.getTemporada()));
        }
    }
	
	public class AnioColeccionComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getAnioColeccion() == null ? "":r1.getAnioColeccion()).compareTo((r2.getAnioColeccion() == null ? "":r2.getAnioColeccion()));
        }
    }
	
	public class TallaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getTalla() == null ? "":r1.getTalla()).compareTo((r2.getTalla() == null ? "":r2.getTalla()));
        }
    }
	
	public class ColorComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getColor() == null ? "":r1.getColor()).compareTo((r2.getColor() == null ? "":r2.getColor()));
        }
    }
	
	public class LoteComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getLote() == null ? "":r1.getLote()).compareTo((r2.getLote() == null ? "":r2.getLote()));
        }
    }
	
	public class ModeloProveedorComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getModeloProveedor() == null ? "":r1.getModeloProveedor()).compareTo((r2.getModeloProveedor() == null ? "":r2.getModeloProveedor()));
        }
    }
	
	public class TempColNumOrdenComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getTempColNumOrden() == null ? "":r1.getTempColNumOrden()).compareTo((r2.getTempColNumOrden() == null ? "":r2.getTempColNumOrden()));
        }
    }
	
	public class PedibleComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getPedible() == null ? "":r1.getPedible()).compareTo((r2.getPedible() == null ? "":r2.getPedible()));
        }
    }
	
	public class FlgNsrComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {
        
        	return (r1.getFlgNsr() == null ? "":r1.getFlgNsr()).compareTo((r2.getFlgNsr() == null ? "":r2.getFlgNsr()));
        
        }
    }
	
	public class CcEstrComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {

        	 return (r1.getCcEstr() == null ? new Long(0):r1.getCcEstr()).compareTo((r2.getCcEstr() == null ? new Long(0):r2.getCcEstr()));
        }
    }
	
	public class FlgNuevaComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {
        
        	return (r1.getFlgNueva() == null ? "":r1.getFlgNueva()).compareTo((r2.getFlgNueva() == null ? "":r2.getFlgNueva()));
        
        }
    }
	
	public class EstructuraComparator implements Comparator<VArtSfm> {

        public int compare(final VArtSfm r1, final VArtSfm r2) {
        	
        	CompareToBuilder compareToBuilder = new CompareToBuilder();
            compareToBuilder.append(r1.getCodN1(), r2.getCodN1());
            compareToBuilder.append(r1.getCodN2(), r2.getCodN2());
            compareToBuilder.append(r1.getCodN3(), r2.getCodN3());
            compareToBuilder.append(r1.getCodN4(), r2.getCodN4());
            compareToBuilder.append(r1.getCodN5(), r2.getCodN5());
            compareToBuilder.append(r1.getCodArticulo(), r2.getCodArticulo());
        
            // moves are not compared
            return compareToBuilder.toComparison();
        
     
        
        }
    }
	
}
