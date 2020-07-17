package utils;

import java.math.BigDecimal;

public class ConvertirNumero {
		 
	public static String cantidadConLetra(String s) {
		StringBuilder result = new StringBuilder();
	    BigDecimal totalBigDecimal = new BigDecimal(s).setScale(2, BigDecimal.ROUND_DOWN);
	    long parteEntera = totalBigDecimal.toBigInteger().longValue();
	    int triUnidades      = (int)((parteEntera % 1000));
	    int triMiles         = (int)((parteEntera / 1000) % 1000);
	    int triMillones      = (int)((parteEntera / 1000000) % 1000);
	    int triMilMillones   = (int)((parteEntera / 1000000000) % 1000);
	    if (parteEntera == 0) {
	    	result.append("Cero ");
	    	return result.toString();
	    }
	    if (triMilMillones > 0) {
	    	result.append(triTexto(triMilMillones).toString() + "Mil ");
	    }
	    if (triMillones > 0){    
	    	result.append(triTexto(triMillones).toString());
		}
	    if (triMilMillones == 0 && triMillones == 1) {
	    	result.append("Millón ");
	    }else{ 
	    	if (triMilMillones > 0 || triMillones > 0) result.append("Millones ");
	    }
	    if (triMiles > 0){
	    	result.append(triTexto(triMiles).toString() + "Mil ");
	    }
	    if (triUnidades > 0){   
	    	result.append(triTexto(triUnidades).toString());
		}
	        return result.toString();
	    }
	 
	private static StringBuilder triTexto(int n) {
		StringBuilder result = new StringBuilder();
		int centenas = n / 100;
		int decenas  = (n % 100) / 10;
		int unidades = (n % 10);
	 
		switch (centenas) {
			case 0: break;
			case 1:
				if (decenas == 0 && unidades == 0) {
					result.append("cien ");
					return result;
				}else result.append("ciento ");
				break;
		    case 2: result.append("doscientos "); break;
		    case 3: result.append("trescientos "); break;
		    case 4: result.append("cuatrocientos "); break;
		    case 5: result.append("quinientos "); break;
		    case 6: result.append("seiscientos "); break;
		    case 7: result.append("setecientos "); break;
		    case 8: result.append("ochocientos "); break;
		    case 9: result.append("novecientos "); break;
		}
	 
	    switch (decenas) {
	    	case 0: break;
	        case 1:
	        	if (unidades == 0) { result.append("diez "); return result; }
	            else if (unidades == 1) { result.append("once "); return result; }
	            else if (unidades == 2) { result.append("doce "); return result; }
	            else if (unidades == 3) { result.append("trece "); return result; }
	            else if (unidades == 4) { result.append("catorce "); return result; }
	            else if (unidades == 5) { result.append("quince "); return result; }
	            else result.append("dieci");
	            break;
            case 2:
	            if (unidades == 0) { result.append("veinte "); return result; }
	            else result.append("veinti");
	            break;
	        case 3: result.append("treinta "); break;
	        case 4: result.append("cuarenta "); break;
	        case 5: result.append("cincuenta "); break;
	        case 6: result.append("sesenta "); break;
	        case 7: result.append("setenta "); break;
	        case 8: result.append("ochenta "); break;
	        case 9: result.append("noventa "); break;
	    }
	 
	    if (decenas > 2 && unidades > 0)
	    	 result.append("y ");
	 
	    switch (unidades) {
	    	case 0: break;
	        case 1: result.append("un "); break;
	        case 2: result.append("dos "); break;
	        case 3: result.append("tres "); break;
	        case 4: result.append("cuatro "); break;
	        case 5: result.append("cinco "); break;
            case 6: result.append("seis "); break;
	        case 7: result.append("siete "); break;
	        case 8: result.append("ocho "); break;
	        case 9: result.append("nueve "); break;
	    }
	 
	    return result;
	}	
	
}
