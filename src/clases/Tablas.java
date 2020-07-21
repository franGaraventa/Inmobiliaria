package clases;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;
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
	
	public static void actualizarTPersona(JTable tabla, List<Persona> personas) {
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
				modelo.addRow(new Object[] {p.getId(),p.getValor(),p.getSupLote(),p.getSupCubierta(),p.getInformacion()});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTContratos(JTable tabla) {
		DAOContrato icontrato = new DAOContratoImpl();
		List<Contrato> contratos = icontrato.getContratos();
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!contratos.isEmpty()){
			for (Contrato c : contratos) {
				modelo.addRow(new Object[] {c.getId(),c.getPlazo(),c.getFechaFirma(),c.getFechaInicio(),c.getFechaFinalizacion(),c.getLocador(),c.getFechaMaxPago()});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTContratos(JTable tabla,List<Contrato> contratos) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!contratos.isEmpty()){
			for (Contrato c : contratos) {
				modelo.addRow(new Object[] {c.getId(),c.getPlazo(),c.getFechaFirma(),c.getFechaInicio(),c.getFechaFinalizacion(),c.getLocador(),c.getFechaMaxPago()});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTPropiedad(JTable tabla,List<Propiedad> propiedades) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!propiedades.isEmpty()){
			for (Propiedad p : propiedades) {
				modelo.addRow(new Object[] {p.getId(),p.getValor(),p.getSupLote(),p.getSupCubierta(),p.getInformacion()});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTPagos(JTable tabla,int id) {
		DAOPagos ipagos = new DAOPagosImpl();
		List<Pagos> pagos = ipagos.getPagos(id);
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!pagos.isEmpty()){
			for (Pagos p : pagos) {
				modelo.addRow(new Object[] {p.getFecha(),p.getMonto(),p.getRecargo()});
			}
		}
		tabla.setModel(modelo);
	}
	
	private static int diasExcedido(int fechaMaxPago) {
		Calendar fecha = Calendar.getInstance();
		int dias = fechaMaxPago - fecha.get(Calendar.DAY_OF_MONTH);
		if (dias < 0) {
			return (-1)*dias;
		}else {
			return 0;
		}
	}
	
	private static int diasRestantes(int fechaMaxPago) {
		Calendar fecha = Calendar.getInstance();
		int dias = fechaMaxPago - fecha.get(Calendar.DAY_OF_MONTH);
		if (dias >= 0) {
			return dias;
		}else {
			return -1;
		}
	}
	
	public static void actualizarTContratosVencidos(JTable tabla,List<Contrato> contratos) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!contratos.isEmpty()){
			for (Contrato c : contratos) {
				modelo.addRow(new Object[] {c.getId(),c.getFechaMaxPago(),diasRestantes(c.getFechaMaxPago()),diasExcedido(c.getFechaMaxPago())});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTContratosConVencimientoProximo(JTable tabla,List<Contrato> contratos,List<Long> dias) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!contratos.isEmpty()) {
			int i = 0;
			while (i < contratos.size()) {
				Contrato c = contratos.get(i);
				modelo.addRow(new Object[] {c.getId(),c.getFechaFirma(),c.getFechaInicio(),c.getFechaFinalizacion(),dias.get(i)});
				i++;
			}
		}
		tabla.setModel(modelo);
	}
}
