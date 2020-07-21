package utils;

import java.util.List;

import javax.swing.JOptionPane;

public class GeneradorTexto {

	private static final int par = 2;
	
	/*GENERADOR DE TEXTO DE BUSQUEDA*/
	public static String generarTexto(List<TextoBusqueda> busqueda,List<String> conectores) {
		String texto = "";
		if ((busqueda.size() == 1) && (conectores.isEmpty())) {
			if (busqueda.get(0).isNegado()) {
				texto = "¬("+busqueda.get(0).getTexto()+")";
			}else {
				texto = busqueda.get(0).getTexto()+" ";
			}
		}else {
			int index = 0;
			for(String conector: conectores) {
				if (index < par) {
					if (busqueda.get(index).isNegado()) {
						texto = texto +" ¬("+busqueda.get(index).getTexto() + ") ";
					}else {
						texto = texto + busqueda.get(index).getTexto() + " ";
					}
					index++;
					texto = texto + conector + " ";
					if (busqueda.get(index).isNegado()) {
						texto = texto +" ¬("+ busqueda.get(index).getTexto() + ") ";
					}else {
						texto = texto + busqueda.get(index).getTexto() + " ";
					}
					index++;
				}else {
					texto = texto + conector + " ";
					if (busqueda.get(index).isNegado()) {
						texto = texto +" ¬("+ busqueda.get(index).getTexto()+ ") ";
					}else {
						texto = texto + busqueda.get(index).getTexto()+ " ";
					}
					index++;
				}
			}
		}
		return texto;
	}
	
	public static boolean comprobarCampos(int seleccionado,int logica) {
		if ((seleccionado <= 0) && (logica > 0)) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un campo a buscar");
			return false;
		}else if ((seleccionado > 0) && (logica <= 0)) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un conector");
			return false;
		}else if ((seleccionado == 0) && (logica == 0)) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un campo a buscar y un conector");
			return false;
		}
		return true;
	}
	
}
