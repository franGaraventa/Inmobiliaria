package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.Contrato;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import utils.GeneradorTexto;
import utils.Tablas;
import utils.TableModels;
import utils.TextoBusqueda;
import utils.ValidadorCampos;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import com.toedter.calendar.JDateChooser;

import Filtros.FContAND;
import Filtros.FContFMaxPago;
import Filtros.FContFinalizacion;
import Filtros.FContFirma;
import Filtros.FContInicio;
import Filtros.FContNOT;
import Filtros.FContOR;
import Filtros.FContPlazo;
import Filtros.FContrato;

import javax.swing.JLabel;
import java.awt.Font;

public class vVerContratos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField txtValor;
	private JPanel pnlBusqueda;
	private JComboBox<String> cbBuscador;
	private JComboBox<String> cbLogica;
	private int cParametros;
	private JCheckBox chkNegar;
	private JTextField txtComparador;
	private List<TextoBusqueda> busqueda;
	private List<String> conectores;
	private JDateChooser dcFecha2;
	private JDateChooser dcFecha1;
	private FContrato filtro;
	private JTextPane txtBusqueda;
	
	private boolean fechasValidas() {
		return ((dcFecha1.getDate() != null) && (dcFecha2.getDate() != null));
	}
	
	/*CREACION DE FILTRO*/
	private FContrato agregarFiltro(int seleccionado) {
		/*1 - Plazo, 2 - Fecha Inicio, 3 - Fecha Finalizacion, 4 - Fecha Firma, 5 - Fecha Maxima Pago*/
		SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd");
		switch(seleccionado) {
			case (1): {
				busqueda.add(new TextoBusqueda("Plazo "+ txtComparador.getText() + " " + txtValor.getText()));
				return new FContPlazo(Integer.parseInt(txtValor.getText()),txtComparador.getText()); 
			}
			case (2): {
				if (txtComparador.getText().equals("entre")) {
					busqueda.add(new TextoBusqueda("("+formato_fecha.format(dcFecha1.getDate())+" < Fecha Inicio > "+ formato_fecha.format(dcFecha2.getDate())+")"));
					return new FContInicio(dcFecha1.getDate(),dcFecha2.getDate(),txtComparador.getText());
				}else{
					busqueda.add(new TextoBusqueda("Fecha "+ txtComparador.getText() + " " + formato_fecha.format(dcFecha1.getDate())));
					return new FContInicio(dcFecha1.getDate(),txtComparador.getText());
				}
			}
			case (3): {
				if (txtComparador.getText().equals("entre")) {
					busqueda.add(new TextoBusqueda("("+formato_fecha.format(dcFecha1.getDate())+" < Fecha Finalizacion > "+ formato_fecha.format(dcFecha2.getDate())+")"));
						return new FContFinalizacion(dcFecha1.getDate(),dcFecha2.getDate(),txtComparador.getText());
				}else {
					busqueda.add(new TextoBusqueda("Fecha "+ txtComparador.getText() + " " + formato_fecha.format(dcFecha1.getDate())));
					return new FContFinalizacion(dcFecha1.getDate(),txtComparador.getText());
				}
			}
			case (4): {
				if (txtComparador.getText().equals("entre")) {
					busqueda.add(new TextoBusqueda("("+formato_fecha.format(dcFecha1.getDate())+" > Fecha Firma < "+ formato_fecha.format(dcFecha2.getDate())+")"));
					return new FContFirma(dcFecha1.getDate(),dcFecha2.getDate(),txtComparador.getText());
				}else {
					busqueda.add(new TextoBusqueda("Fecha "+ txtComparador.getText() + " " + formato_fecha.format(dcFecha1.getDate())));
					return new FContFirma(dcFecha1.getDate(),txtComparador.getText());
				}
			}
			case (5): {
				busqueda.add(new TextoBusqueda("Dias Maxima de Pago "+ txtComparador.getText() + " " + txtValor.getText()));
				return new FContFMaxPago(Integer.parseInt(txtValor.getText()),txtComparador.getText());
			}
		}
		return null;
	}
	
	private FContrato agregarFiltroLogica(int logica,FContrato filtro2) {
		/*1 - y, 2 - o*/
		switch(logica) {
			case (1): {
				conectores.add("y");
				return new FContAND(filtro,filtro2);
			}
			case (2): {
				conectores.add("o");
				return new FContOR(filtro,filtro2);
			}
		}
		return null;
	}

	private boolean textoVacio(JTextField txt) {
		if (txt.getText() != null) {
			return !txt.getText().isEmpty();
		}else {
			return true;
		}
	}
	
	private boolean camposValidos(int seleccionado) {
		if (cParametros == 0) {
			if ((seleccionado == 1) || (seleccionado == 5)) {
				if (textoVacio(txtValor) && textoVacio(txtComparador)) {
					return true;
				}else {
					return false;
				}
			}else {
				if (txtComparador.getText().equals("entre")) {
					return (fechasValidas());
				}else {
					if (dcFecha1.getDate() != null) {
						return (ValidadorCampos.comparadorValido(txtComparador));
					}else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean camposValidos(int seleccionado,int logica) {
		if (logica > 0) {
			if (((seleccionado == 1) || (seleccionado == 5))){
				if (textoVacio(txtValor) && textoVacio(txtComparador)) {
					return true;
				}else {
					return false;
				}
			}else {
				if (txtComparador.getText().equals("entre")) {
					return (fechasValidas());
				}else {
					if (dcFecha1.getDate() != null) {
						return (ValidadorCampos.comparadorValido(txtComparador));
					}else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private void setear() {
		cParametros++;
		txtValor.setText("");
		txtComparador.setText("");
		txtBusqueda.setText("");
		chkNegar.setSelected(false);
		cbLogica.setSelectedIndex(0);
		cbBuscador.setSelectedIndex(0);
		dcFecha1.setDate(null);
		dcFecha2.setDate(null);
	}
	
	private void definirButtons() {
		JButton btnInfo = new JButton("INFORMACION");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun contrato");
				}else {
					DAOContrato icontrato = new DAOContratoImpl();
					Contrato c = icontrato.getContrato((Integer)modelo.getValueAt(row, 0));
					vInfoContrato vinformacion = new vInfoContrato(c);
					vinformacion.setVisible(true);
				}
			}
		});
		btnInfo.setBounds(611, 478, 135, 23);
		contentPane.add(btnInfo);
		
		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun contrato");
				}else {
					DAOContrato icontrato = new DAOContratoImpl();
					if (!icontrato.contratoVigente((Integer)modelo.getValueAt(row, 0), new Date())) {
						Contrato c = icontrato.getContrato((Integer)modelo.getValueAt(row, 0));
						icontrato.eliminar(c);
						Tablas.actualizarTContratos(table);
					}else{
						JOptionPane.showMessageDialog(null, "No es posible eliminar el contrato, ya que no ha finalizado");
					}
				}
			}
		});
		btnEliminar.setBounds(466, 478, 135, 23);
		contentPane.add(btnEliminar);
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccionado = cbBuscador.getSelectedIndex();
				if (cParametros == 0) {
					if (seleccionado > 0) {
						if (camposValidos(seleccionado)) {
							if (chkNegar.isSelected()) {
								filtro = agregarFiltro(seleccionado);
								filtro = new FContNOT(filtro);
								busqueda.get(busqueda.size()-1).setNegado(true);
							}else {
								filtro = agregarFiltro(seleccionado);
							}
							cbLogica.setEnabled(true);
							setear();
							txtBusqueda.setText(GeneradorTexto.generarTexto(busqueda, conectores));
						}
					}else {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un campo a buscar");
					}
				}else {
					int logica = cbLogica.getSelectedIndex();
					if (GeneradorTexto.comprobarCampos(seleccionado, logica)){
						if (camposValidos(seleccionado,logica)) {
							if (chkNegar.isSelected()) {
								filtro = agregarFiltroLogica(logica,new FContNOT(agregarFiltro(seleccionado)));
								busqueda.get(busqueda.size()-1).setNegado(true);
							}else {
								filtro = agregarFiltroLogica(logica,agregarFiltro(seleccionado));
							}
							setear();
							txtBusqueda.setText(GeneradorTexto.generarTexto(busqueda, conectores));
						}
					}
				}
			}
		});
		btnAgregar.setBounds(480, 7, 118, 23);
		pnlBusqueda.add(btnAgregar);
		
		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOContrato icontrato = new DAOContratoImpl();
				List<Contrato> contratos = icontrato.getContratos();
				List<Contrato> fcontratos = new ArrayList<Contrato>();
				for(Contrato c: contratos) {
					if (filtro.cumple(c)) {
						fcontratos.add(c);
					}
				}
				Tablas.actualizarTContratos(table, fcontratos);
			}
		});
		btnBuscar.setBounds(608, 132, 118, 23);
		pnlBusqueda.add(btnBuscar);
		
		JButton btnReestablecer = new JButton("REESTABLECER");
		btnReestablecer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtValor.setText("");
				txtComparador.setText("");
				txtBusqueda.setText("");
				dcFecha1.setDate(null);
				dcFecha2.setDate(null);
				chkNegar.setSelected(false);
				cbLogica.setSelectedIndex(0);
				cbBuscador.setSelectedIndex(0);
				cbLogica.setEnabled(false);
				cParametros = 0;
				Tablas.actualizarTContratos(table);
				busqueda.clear();
				conectores.clear();
			}
		});
		btnReestablecer.setBounds(460, 132, 138, 23);
		pnlBusqueda.add(btnReestablecer);
	}
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 772, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		JScrollPane scrVigentes = new JScrollPane();
		scrVigentes.setViewportView(table);
		scrVigentes.setBounds(10, 188, 736, 282);
		contentPane.add(scrVigentes);
		
		modelo = TableModels.crearModeloContrato(modelo);
		table.setModel(modelo);
		Tablas.actualizarTContratos(table);
		
		pnlBusqueda = new JPanel();
		pnlBusqueda.setBounds(10, 11, 736, 166);
		contentPane.add(pnlBusqueda);
		pnlBusqueda.setLayout(null);
		
		chkNegar = new JCheckBox("");
		chkNegar.setBounds(6, 7, 26, 23);
		pnlBusqueda.add(chkNegar);
		
		cbBuscador = new JComboBox<String>();
		cbBuscador.setModel(new DefaultComboBoxModel<String>(new String[] {"Elija que ingresar a la busqueda", "Plazo", "Fecha Inicio", "Fecha Finalizacion", "Fecha Firma", "Fecha Maxima Pago"}));
		cbBuscador.setBounds(38, 10, 195, 20);
		pnlBusqueda.add(cbBuscador);
		
		txtValor = new JTextField();
		txtValor.setBounds(129, 41, 118, 20);
		pnlBusqueda.add(txtValor);
		txtValor.setColumns(10);
		
		cbLogica = new JComboBox<String>();
		cbLogica.setModel(new DefaultComboBoxModel<String>(new String[] {"Ingrese la combinacion logica", "y", "o"}));
		cbLogica.setBounds(257, 10, 195, 20);
		pnlBusqueda.add(cbLogica);
		
		txtBusqueda = new JTextPane();
		txtBusqueda.setBounds(257, 41, 469, 80);
		pnlBusqueda.add(txtBusqueda);
		
		dcFecha1 = new JDateChooser();
		dcFecha1.setBounds(20, 103, 180, 20);
		pnlBusqueda.add(dcFecha1);
		
		dcFecha2 = new JDateChooser();
		dcFecha2.setBounds(20, 132, 180, 20);
		pnlBusqueda.add(dcFecha2);
		
		definirButtons();
		cbLogica.setEnabled(false);
		
		txtComparador = new JTextField();
		txtComparador.setColumns(10);
		txtComparador.setBounds(129, 72, 71, 20);
		pnlBusqueda.add(txtComparador);
		
		JLabel lblNewLabel = new JLabel("VALOR");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 44, 109, 20);
		pnlBusqueda.add(lblNewLabel);
		
		JLabel lblComparador = new JLabel("COMPARADOR");
		lblComparador.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblComparador.setBounds(10, 72, 109, 20);
		pnlBusqueda.add(lblComparador);
		
		cParametros = 0;
		busqueda = new ArrayList<TextoBusqueda>();
		conectores = new ArrayList<String>();
	}
	
	public vVerContratos() {
		cargarVentana();
	}
}
