package utils;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public class ValidadorCampos {

	private static String[] comparadores = {"=",">=","<=","<",">"};
	private static Border rborder = BorderFactory.createLineBorder(Color.RED, 2);
	private static Border gborder = BorderFactory.createLineBorder(Color.GREEN, 2);
	
	private static boolean esSoloLetras(String cadena){
		for (int i = 0; i < cadena.length(); i++){
			char caracter = cadena.toUpperCase().charAt(i);
			int valorASCII = (int)caracter;
			if (valorASCII != 165 && (valorASCII < 65 || valorASCII > 90))
				return false; 
		}
		return true;
	}
	
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	public static boolean campoNumeros(JTextField txt,int length) {
		if ((!txt.getText().isEmpty()) && (isNumeric(txt.getText()))) {
			if ((txt.getText().length() <= length)) {
				txt.setBorder(gborder);
				return true;
			}
		}else {
			txt.setBorder(rborder);
			return false;
		}
		txt.setBorder(rborder);
		return false;
	}
	
	public static boolean campoNumeros(JTextField txt) {
		if ((!txt.getText().isEmpty()) && (isNumeric(txt.getText()))) {
			txt.setBorder(gborder);
			return true;
		}else {
			txt.setBorder(rborder);
			return false;
		}
	}

	private static boolean perteneceComparador(String comparador) {
		for (String comp: comparadores) {
			if (comp.equals(comparador)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean comparadorValido(JTextField txt) {
		if (!txt.getText().isEmpty()) {
			return perteneceComparador(txt.getText());
		}
		return false;
	}
	
	public static boolean textVacio(JTextPane txtPane,int length) {
		if ((!txtPane.getText().isEmpty())) {
			if (txtPane.getText().length() <= length) {
				txtPane.setBorder(gborder);
				return true;
			}
		}else {
			txtPane.setBorder(rborder);
			return false;
		}
		txtPane.setBorder(rborder);
		return false;
	}
	
	public static boolean campoVacio(JTextField txt,int length) {
		if ((!txt.getText().isEmpty())) {
			if (txt.getText().length() <= length) {
				txt.setBorder(gborder);
				return true;
			}
		}else {
			txt.setBorder(rborder);
			return false;
		}
		txt.setBorder(rborder);
		return false;
	}
	

	public static boolean campoLetras(JTextField txt,int length) {
		if ((!txt.getText().isEmpty()) && (esSoloLetras(txt.getText()))) {
			if ((txt.getText().length() <= length)) {
				txt.setBorder(gborder);
				return true;
			}
		}else {
			txt.setBorder(rborder);
			return false;
		}
		txt.setBorder(rborder);
		return false;
	}
	
	public static Border getRBorder() {
		return rborder;
	}
	
	public static Border getRGorder() {
		return gborder;
	}
}
