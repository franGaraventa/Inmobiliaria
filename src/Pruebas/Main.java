package Pruebas;

import clases.Propiedad;

public class main {

	public static void main(String[] args) {
		Propiedad p = new Propiedad();
		p.setValor(50000);
		FPropiedad f1 = new FPropValor(50000,"==");
		System.out.println(f1.cumple(p));
	}

}
