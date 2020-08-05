package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.toedter.calendar.JDateChooser;

import clases.Cliente;
import clases.Contrato;
import clases.EstadoInmueble;
import clases.FechaPautada;
import clases.Locador;
import clases.Persona;
import clases.PrecioCompleto;
import clases.PrecioPorcentual;
import clases.Propiedad;
import interfaces.DAOCliente;
import interfaces.DAOClienteImpl;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import interfaces.DAOLocador;
import interfaces.DAOLocadorImpl;
import interfaces.DAOPropiedad;
import interfaces.DAOPropiedadImpl;
import interfaces.DAOTipoPrecio;
import interfaces.DAOTipoPrecioImpl;
import utils.TableModels;
import utils.ValidadorCampos;
import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JList;

public class vContrato extends JFrame {

	private static final int meses = 6;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPlazo;
	private JTextField txtMaxPago;
	private JTable table;
	private JTextField txtGarantia;
	private JTextField txtGastosInmobiliaria;
	private DefaultTableModel modelo;
	private Panel pnlFechas;
	private JComboBox<String> chkFormaPago;
	private JDateChooser dcFechaInicio;
	private JDateChooser dcFechaFinalizacion;
	private JDateChooser dcFechaFirma;
	private JComboBox<String> cbLocatario;
	private JTextField txtExpensas;
	private JComboBox<String> cbLocador;
	private JTextField txtEstado;
	private JTextField txtDescripcion;
	private JList<String> list;
	private DefaultListModel<String> listModel;
		
	private boolean existeContrato(List<Contrato> contratos,Cliente cliente) {
		for(Contrato c: contratos) {
			if (c.getLocatario().getId() == cliente.getId())
				return true;
		}
		return false;
	}
	
	private boolean existeContrato(List<Contrato> contratos,Propiedad propiedad) {
		for(Contrato c: contratos) {
			if (c.getLocacion().getId() == propiedad.getId())
				return true;
		}
			return false;
	}
	
	
	/*CARGAR LOCADORES*/
	private void cargarLocadores() {
		DAOLocador ilocador = new DAOLocadorImpl();
		List<Locador> locadores = ilocador.getLocadores();
		DefaultComboBoxModel<String> comboModelo = new DefaultComboBoxModel<String>();
		if (!locadores.isEmpty()) {
			comboModelo.addElement("Seleccione un locador...");
			for (Locador l : locadores) {
				comboModelo.addElement(l.getId()+"-"+l.getNombre()+","+l.getApellido());
			}
			cbLocador.setModel(comboModelo);
		}
	}
	
	/*CARGAR LOCATARIOS DISPONIBLES PARA CONTRATO*/
	private void cargarLocatarios() {
		DAOContrato icontrato = new DAOContratoImpl();
		DAOCliente iclientes = new DAOClienteImpl();
		List<Contrato> contratos = icontrato.getContratosVigentes(new Date());
		List<Cliente> clientes = iclientes.getClientes();
		List<Cliente> c_aux = new ArrayList<Cliente>();
		for(Cliente cliente: clientes) {
			if (!existeContrato(contratos,cliente)) {
				c_aux.add(cliente);
			}
		}
		DefaultComboBoxModel<String> comboModelo = new DefaultComboBoxModel<String>();
		if (!c_aux.isEmpty()) {
			comboModelo.addElement("Seleccione un cliente...");
			for (Cliente c : c_aux) {
				comboModelo.addElement(c.getId()+"-"+c.getNombre()+","+c.getApellido());
			}
			cbLocatario.setModel(comboModelo);
		}else {
			comboModelo.addElement("No existen clientes disponibles");
			cbLocatario.setModel(comboModelo);
		}
	}
	
	private void agregarPersona(Contrato c) {
		String persona = (String) cbLocatario.getSelectedItem();
		String[] parts = persona.split("-");
		DAOCliente icliente = new DAOClienteImpl();
		Persona p = icliente.getCliente(Integer.parseInt(parts[0]));
		c.setLocatario(p);
	}
	
	private void agregarEstadoInmueble(Contrato c) {
		List<EstadoInmueble> estadoInmueble = new ArrayList<EstadoInmueble>();
		for(int i=0; i < listModel.getSize(); i++){
			String[] parts = listModel.getElementAt(i).toString().split("-");
			EstadoInmueble ei = new EstadoInmueble(c,parts[0],parts[1]);
			estadoInmueble.add(ei);
		}
		c.setEstado_inmueble(estadoInmueble);
	}
	
	private void agregarLocador(Contrato c) {
		String persona = (String) cbLocador.getSelectedItem();
		String[] parts = persona.split("-");
		DAOLocador ilocador = new DAOLocadorImpl();
		Persona p = ilocador.getLocador(Integer.parseInt(parts[0]));
		c.setLocador(p);
	}

	private void agregarLocacion(Contrato c) {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna propiedad");
		}else {
			DAOPropiedad ipropiedad = new DAOPropiedadImpl();
			Propiedad p = ipropiedad.obtenerPropiedad((Integer)modelo.getValueAt(row, 0));
			c.setLocacion(p);
		}
	}
	
	/*FORMA DE PAGO DEL CONTRATO*/
	private void agregarTipoPago(Contrato c) {
		int seleccionado = chkFormaPago.getSelectedIndex();
		DAOTipoPrecio iprecio = new DAOTipoPrecioImpl();
		int id = iprecio.getUltimoIndice()+1;
		if (seleccionado != -1) {
			if (seleccionado == 1) {
				Date fechaInicio = dcFechaInicio.getDate();
				int plazo = Integer.parseInt(txtPlazo.getText());
				int cantMeses = (plazo / meses) - 1;
				List<Date> fechas = new ArrayList<Date>();
				if (cantMeses > 0) {
					for (int i = 1; i <= cantMeses;i++) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fechaInicio);
						calendar.add(Calendar.MONTH, i*meses);
						fechas.add(calendar.getTime());
					}
				}
				Double precio = c.getLocacion().getValor() / c.getPlazo();
				PrecioPorcentual pp = new PrecioPorcentual(id,precio,Double.valueOf(txtExpensas.getText()));
				if (fechas.size() > 0) {
					for(Date fecha: fechas){
						String dato = JOptionPane.showInputDialog("Introduzca un porcentaje para la fecha: "+new SimpleDateFormat("dd/MM/yyyy").format(fecha));
						long d = fecha.getTime();
						java.sql.Date nfecha = new java.sql.Date(d);
						FechaPautada fp = new FechaPautada(nfecha,Double.parseDouble(dato),pp);
						pp.addFecha(fp);
					}
				}
				iprecio.agregar(pp);
				c.setPrecio(pp);
			}else {
				if (seleccionado == 2) {
					PrecioCompleto pc = new PrecioCompleto(id,c.getLocacion().getValor(),Double.valueOf(txtExpensas.getText()),Integer.parseInt(txtPlazo.getText()));
					iprecio.agregar(pc);
					c.setPrecio(pc);
				}
			}
		}
	}
	
	/*CREACION DE MODELO Y CARGADO DE ELEMENTOS A LA TABLA DE PROPIEDADES DISPONIBLES*/
	private void cargarTabla() {
		DAOPropiedad ipropiedad = new DAOPropiedadImpl();
		DAOContrato icontrato = new DAOContratoImpl();
		List<Contrato> contratos = icontrato.getContratosVigentes(new Date());
		List<Propiedad> propiedades = ipropiedad.getPropiedades();
		List<Propiedad> p_aux = new ArrayList<Propiedad>();
		for(Propiedad propiedad: propiedades) {
			if (!existeContrato(contratos,propiedad)) {
				p_aux.add(propiedad);
			}
		}
		if(!p_aux.isEmpty()) {
			for (Propiedad p : p_aux) {
				modelo.addRow(new Object[] {p.getId(),p.getValor(),p.getSupLote(),p.getSupCubierta(),p.getInformacion()});
			}
			table.setModel(modelo);
		}
	}
	/*-------------------------------------------------------------------------------*/
	
	/*CALCULA LA CONDICION PARA CAMBIAR EL VALOR DEL DATECHOOSER FECHA FINALIZACION*/
	private void calcularCondicion() {
		Date fechaInicio = dcFechaInicio.getDate();
		Date fechaFirma = dcFechaFirma.getDate();
		if ((fechaInicio != null) && (fechaFirma != null)){
			if ((fechaFirma.before(fechaInicio)) && (!txtPlazo.getText().isEmpty())){
				calcularFechaFinalizacion(Integer.parseInt(txtPlazo.getText()));
			}else {
				dcFechaFinalizacion.setDate(null);
			}
		}
	}
	
	private void calcularFechaFinalizacion(int meses) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dcFechaInicio.getDate());
		calendar.add(Calendar.MONTH, meses);
		dcFechaFinalizacion.setDate(calendar.getTime());
	}
	/*-------------------------------------------------------------------------------*/
	
	private void cargarLabels() {
		JLabel lblNewLabel = new JLabel("PLAZO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 21, 64, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblLocador = new JLabel("LOCADOR");
		lblLocador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLocador.setBounds(10, 160, 88, 22);
		contentPane.add(lblLocador);
		
		JLabel lblLocatario = new JLabel("LOCATARIO");
		lblLocatario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLocatario.setBounds(10, 191, 105, 22);
		contentPane.add(lblLocatario);
		
		JLabel lblLocacion = new JLabel("LOCACION");
		lblLocacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLocacion.setBounds(10, 290, 97, 22);
		contentPane.add(lblLocacion);
		
		JLabel lblFechaMaximaDe = new JLabel("FECHA MAXIMA DE PAGO");
		lblFechaMaximaDe.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFechaMaximaDe.setBounds(10, 257, 215, 22);
		contentPane.add(lblFechaMaximaDe);
		
		JLabel lblFormaDePago = new JLabel("FORMA DE PAGO");
		lblFormaDePago.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFormaDePago.setBounds(10, 224, 155, 22);
		contentPane.add(lblFormaDePago);
		
		JLabel lblGarantia = new JLabel("GARANTIA");
		lblGarantia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGarantia.setBounds(10, 525, 105, 22);
		contentPane.add(lblGarantia);
		
		JLabel lblGastosInmobiliaria = new JLabel("GASTOS INMOBILIARIA");
		lblGastosInmobiliaria.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGastosInmobiliaria.setBounds(10, 558, 215, 22);
		contentPane.add(lblGastosInmobiliaria);
		
		JLabel lblFechaFirma = new JLabel("FECHA DE FIRMA");
		lblFechaFirma.setBounds(10, 11, 139, 22);
		pnlFechas.add(lblFechaFirma);
		lblFechaFirma.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblFechaDeInicio = new JLabel("FECHA DE INICIO");
		lblFechaDeInicio.setBounds(10, 41, 155, 22);
		pnlFechas.add(lblFechaDeInicio);
		lblFechaDeInicio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblFechaDeFinalizacion = new JLabel("FECHA DE FINALIZACION");
		lblFechaDeFinalizacion.setBounds(10, 70, 215, 22);
		pnlFechas.add(lblFechaDeFinalizacion);
		lblFechaDeFinalizacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblExpensas = new JLabel("EXPENSAS");
		lblExpensas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblExpensas.setBounds(156, 290, 105, 22);
		contentPane.add(lblExpensas);
		
		JLabel lblEstado = new JLabel("ESTADO");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstado.setBounds(416, 21, 105, 22);
		contentPane.add(lblEstado);
		
		JLabel lblDescripcion = new JLabel("DESCRIPCION");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescripcion.setBounds(416, 49, 150, 22);
		contentPane.add(lblDescripcion);
		
	}

	private void cargarComponentes() {
		txtPlazo = new JTextField();
		txtPlazo.setBounds(84, 21, 86, 20);
		txtPlazo.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  calcularCondicion();
		      }
		    });
		contentPane.add(txtPlazo);
		txtPlazo.setColumns(10);
		
		pnlFechas = new Panel();
		pnlFechas.setBounds(0, 49, 391, 105);
		contentPane.add(pnlFechas);
		pnlFechas.setLayout(null);
		
		dcFechaFirma = new JDateChooser();
		dcFechaFirma.setBounds(173, 11, 139, 20);
		pnlFechas.add(dcFechaFirma);
		
		dcFechaInicio = new JDateChooser();
		dcFechaInicio.setBounds(175, 41, 139, 20);
		pnlFechas.add(dcFechaInicio);
		
		dcFechaFinalizacion = new JDateChooser();
		dcFechaInicio.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
			        public void propertyChange(PropertyChangeEvent e) {
						if ("date".equals(e.getPropertyName())){
							calcularCondicion();
						}
			        }
		});
		dcFechaFinalizacion.setBounds(235, 70, 139, 20);
		dcFechaFinalizacion.setEnabled(false);
		pnlFechas.add(dcFechaFinalizacion);
		
		cbLocatario = new JComboBox<String>();
		cbLocatario.setBounds(118, 191, 253, 20);
		contentPane.add(cbLocatario);
		
		chkFormaPago = new JComboBox<String>();
		chkFormaPago.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione una forma de pago", "PAGO PORCENTUAL", "PAGO COMPLETO"}));
		chkFormaPago.setBounds(156, 226, 215, 20);
		contentPane.add(chkFormaPago);
		
		txtMaxPago = new JTextField();
		txtMaxPago.setColumns(10);
		txtMaxPago.setBounds(226, 257, 64, 20);
		contentPane.add(txtMaxPago);
		
		Panel pnlPropiedades = new Panel();
		pnlPropiedades.setBounds(10, 318, 623, 198);
		contentPane.add(pnlPropiedades);
		pnlPropiedades.setLayout(null);
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 11, 603, 176);
		pnlPropiedades.add(scroll);
		scroll.setViewportView(table);
		
		txtGarantia = new JTextField();
		txtGarantia.setColumns(10);
		txtGarantia.setBounds(116, 525, 97, 20);
		contentPane.add(txtGarantia);
		
		txtGastosInmobiliaria = new JTextField();
		txtGastosInmobiliaria.setColumns(10);
		txtGastosInmobiliaria.setBounds(213, 558, 97, 20);
		contentPane.add(txtGastosInmobiliaria);
		
		cbLocador = new JComboBox<String>();
		cbLocador.setBounds(118, 160, 253, 20);
		contentPane.add(cbLocador);
		
		txtExpensas = new JTextField();
		txtExpensas.setColumns(10);
		txtExpensas.setBounds(271, 292, 86, 20);
		contentPane.add(txtExpensas);
		
		txtEstado = new JTextField();
		txtEstado.setColumns(10);
		txtEstado.setBounds(516, 21, 199, 20);
		contentPane.add(txtEstado);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(576, 49, 244, 20);
		contentPane.add(txtDescripcion);
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane scroll2 = new JScrollPane(list);
		scroll2.setBounds(416, 110, 402, 191);
		contentPane.add(scroll2);
		
		modelo = TableModels.crearModeloPropiedades(modelo);
		cargarTabla();
		cargarLocatarios();
		cargarLocadores();
	}

	/*COMPRUEBA QUE LOS CAMPOS A CARGAR SEAN VALIDOS*/
	private boolean camposEstadoVacios() {
		if (ValidadorCampos.campoVacio(txtEstado, 20) && ValidadorCampos.campoVacio(txtDescripcion, 30)) {
			return true;
		}else {
			return false;
		}
	}
	
	/*VALIDACION DE CAMPOS PARA INSERCION DE CONTRATO*/
	private boolean camposVacios() {
		if (ValidadorCampos.campoNumeros(txtPlazo) && ValidadorCampos.campoNumeros(txtExpensas) && ValidadorCampos.campoNumeros(txtMaxPago) && 
			ValidadorCampos.campoNumeros(txtGarantia) && ValidadorCampos.campoNumeros(txtGastosInmobiliaria)) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean fechasVacias() {
		Date fechaInicio = dcFechaInicio.getDate();
		Date fechaFirma = dcFechaFirma.getDate();
		return ((fechaInicio != null) && (fechaFirma != null));
	}
	
	private boolean fechasValidas() {
		if (fechasVacias()) {
			return ((dcFechaFirma.getDate().before(dcFechaInicio.getDate())) || (dcFechaFirma.getDate().equals(dcFechaInicio.getDate())));
		}
		return false;
	}
	
	private boolean camposValidos() {
		return ((camposVacios()) && (fechasVacias()) && (cbLocatario.getSelectedIndex() > 0) && (chkFormaPago.getSelectedIndex() > 0) && (fechasValidas()) && (table.getSelectedRow() != -1)); 
	}
	
	/*-----------------------------------------------------------------------------------*/
	
	private void cargarButtons() {
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (camposValidos()) {
					int plazo = Integer.parseInt(txtPlazo.getText());
					double garantia = Double.valueOf(txtGarantia.getText());
					double gastosInmobiliaria = Double.valueOf(txtGastosInmobiliaria.getText());
					int fechaMaxPago = Integer.parseInt(txtMaxPago.getText());
					Contrato c = new Contrato(plazo,fechaMaxPago,dcFechaFirma.getDate(),dcFechaInicio.getDate(),dcFechaFinalizacion.getDate(),garantia,gastosInmobiliaria);
					agregarLocacion(c);
					agregarPersona(c);
					agregarLocador(c);
					agregarTipoPago(c);
					agregarEstadoInmueble(c);
					DAOContrato icontrato = new DAOContratoImpl();
					icontrato.agregar(c);
					dispose();
				}
			}
		});
		btnGuardar.setBounds(723, 557, 97, 23);
		contentPane.add(btnGuardar);
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (camposEstadoVacios()) {
					String estado = txtEstado.getText()+"-"+txtDescripcion.getText();
					listModel.addElement(estado);
				}
			}
		});
		btnAgregar.setBounds(723, 80, 97, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indice = list.getSelectedIndex();
				if (indice == -1) {
					JOptionPane.showMessageDialog(null,"No ha seleccionado un elemento");
				}else {
					listModel.remove(indice);
					list.clearSelection();
				}
			}
		});
		btnEliminar.setBounds(616, 80, 97, 23);
		contentPane.add(btnEliminar);
	}
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 843, 628);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		cargarComponentes();
		cargarLabels();
		cargarButtons();
		
	}
	
	public vContrato() {
		cargarVentana();
	}
}
