package clases;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.DAOPersona;
import interfaces.DAOPersonaImpl;
import interfaces.DAOPropiedad;
import interfaces.DAOPropiedadImpl;

public class Tablas {

	public static void actualizarTPersona(JTable tabla) {
		DAOPersona ipersona = new DAOPersonaImpl();
		List<Persona> personas = ipersona.getPersonas();
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!personas.isEmpty()){
			for (Persona p : personas) {
				modelo.addRow(new Object[] {p.getDni(),p.getNombre(),p.getApellido(),p.getEmail(),p.getCodArea(),p.getTelefono()});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTPropiedad(JTable tabla) {
		DAOPropiedad ipropiedad = new DAOPropiedadImpl();
		List<Propiedad> propiedades = ipropiedad.getPropiedades();
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!propiedades.isEmpty()){
			for (Propiedad p : propiedades) {
				modelo.addRow(new Object[] {p.getId(),p.getValor(),p.getSupLote(),p.getSupCubierta(),p.isAlquilado()});
			}
		}
		tabla.setModel(modelo);
	}
	
}
