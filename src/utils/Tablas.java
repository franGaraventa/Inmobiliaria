package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clases.Cliente;
import clases.Contrato;
import clases.Pagos;
import clases.Persona;
import clases.Propiedad;
import interfaces.DAOCliente;
import interfaces.DAOClienteImpl;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;
import interfaces.DAOPersona;
import interfaces.DAOPersonaImpl;
import interfaces.DAOPropiedad;
import interfaces.DAOPropiedadImpl;

public class Tablas {

	private static boolean existeCliente(List<Persona> personas,Cliente c) {
		for(Persona p: personas) {
			if(p.getId() == c.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private static void cargarClientes(List<Persona> personas,List<Cliente> clientes,List<Cliente> aux) {
		for(Cliente c: clientes) {
			if (existeCliente(personas,c)) {
				aux.add(c);
			}
		}
	}
	
	public static void actualizarTClientes(JTable tabla) {
		DAOPersona ipersona = new DAOPersonaImpl();
		DAOCliente icliente = new DAOClienteImpl();
		List<Persona> personas = ipersona.getPersonas();
		List<Cliente> clientes = icliente.getClientes();
		List<Cliente> p_aux = new ArrayList<Cliente>();
		cargarClientes(personas,clientes,p_aux);
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!p_aux.isEmpty()){
			for (Cliente c : p_aux) {
				modelo.addRow(new Object[] {c.getId(),c.getDni(),c.getNombre(),c.getApellido(),c.getEmail(),c.getCodArea(),c.getTelefono(),c.getDireccion()});
			}
		}
		tabla.setModel(modelo);
	}
	
	public static void actualizarTClientes(JTable tabla, List<Cliente> clientes) {
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		modelo.getDataVector().removeAllElements();	//LIMPIO LOS ELEMENTOS EN LA TABLA
		modelo.fireTableDataChanged();
		if (!clientes.isEmpty()){
			for (Cliente c : clientes) {
				modelo.addRow(new Object[] {c.getId(),c.getDni(),c.getNombre(),c.getApellido(),c.getEmail(),c.getCodArea(),c.getTelefono(),c.getDireccion()});
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
				modelo.addRow(new Object[] {c.getId(),c.getPlazo(),c.getFechaFirma(),c.getFechaInicio(),c.getFechaFinalizacion(),c.getLocador().getApellido(),c.getFechaMaxPago()});
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
				modelo.addRow(new Object[] {p.getId(),p.getFecha(),p.getMonto(),p.getRecargo()});
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
