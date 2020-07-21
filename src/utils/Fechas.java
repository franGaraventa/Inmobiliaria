package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fechas {

	public static int getMes(String valor) {
		int mes = 0;
		switch (valor) {
            case "Enero":  mes = 1;
                     break;
            case "Febrero":  mes = 2;
                     break;
            case "Marzo":  mes = 3;
                     break;
            case "Abril":  mes = 4;
                     break;
            case "Mayo":  mes = 5;
                     break;
            case "Junio":  mes = 6;
                     break;
            case "Julio":  mes = 7;
                     break;
            case "Agosto":  mes = 8;
            		 break;
            case "Septiembre":  mes = 9;
            		 break;
            case "Octubre":  mes = 10;
            		 break;
            case "Noviembre":  mes = 11;
            		 break;
            case "Diciembre":  mes = 12;
            	 	 break;
        } 	 	
		return mes;
	}
	
	public static String getMes(int valor) {
		String mes = "";
		switch (valor) {
            case 0:  mes = "Enero";
                     break;
            case 1:  mes = "Febrero";
                     break;
            case 2:  mes = "Marzo";
                     break;
            case 3:  mes = "Abril";
                     break;
            case 4:  mes = "Mayo";
                     break;
            case 5:  mes = "Junio";
                     break;
            case 6:  mes = "Julio";
                     break;
            case 7:  mes = "Agosto";
            		 break;
            case 8:  mes = "Septiembre";
            		 break;
            case 9:  mes = "Octubre";
            		 break;
            case 10:  mes = "Noviembre";
            		 break;
            case 11:  mes = "Diciembre";
            	 	 break;
        } 	 	
		return mes;
	}
	
	public static String getDia(int valor) {
        String Valor_dia = "";
		if (valor == 1) {
            Valor_dia = "Domingo";
        } else if (valor == 2) {
            Valor_dia = "Lunes";
        } else if (valor == 3) {
            Valor_dia = "Martes";
        } else if (valor == 4) {
            Valor_dia = "Miercoles";
        } else if (valor == 5) {
            Valor_dia = "Jueves";
        } else if (valor == 6) {
            Valor_dia = "Viernes";
        } else if (valor == 7) {
            Valor_dia = "Sabado";
        }
        return Valor_dia;
	}
	
	public static Date modificarFecha(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return (formato.parse(formato.format(fecha)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
