package ventanas;

import java.util.Calendar;

public class main {

	private static int getMesAnterior(int mes) {
		if (mes == 0) {
			return 11;
		}else {
			return mes-1;
		}
	}
	
	private static boolean cumpleRescision() {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(Calendar.MONTH)+1);
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(cumpleRescision());
	}

}
