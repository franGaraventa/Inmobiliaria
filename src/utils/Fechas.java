package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fechas {

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
