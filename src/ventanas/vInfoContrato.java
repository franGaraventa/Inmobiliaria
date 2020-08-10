package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.Contrato;
import clases.EstadoInmueble;
import clases.Pagos;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import interfaces.DAOEstadoInmueble;
import interfaces.DAOEstadoInmuebleImpl;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;
import utils.Fechas;
import utils.GeneradorPDF;
import utils.GeneradorTexto;
import utils.Tablas;
import utils.TableModels;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class vInfoContrato extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int anioenMeses = 12;
	private JPanel contentPane;
	private JTextField txtPlazo;
	private JTextField txtFFirma;
	private JTextField txtFInicio;
	private JTextField txtFFinalizacion;
	private JTextField txtLocador;
	private JTextField txtPago;
	private JTextField txtGarantia;
	private JTextField txtGastos;
	private Contrato contrato;
	private JTable table;
	private DefaultTableModel modelo;
	private JButton btnRescindir;
	private JButton btnCobrar;
	private JTextPane txtEstadoInmueble;
	private JTable tabla;
	
	private void cargarLabels() {
		JLabel lblNewLabel = new JLabel("PLAZO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 94, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblFechaDeFirma = new JLabel("FECHA DE FIRMA");
		lblFechaDeFirma.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFechaDeFirma.setBounds(10, 41, 157, 22);
		contentPane.add(lblFechaDeFirma);
		
		JLabel lblFechaDeInicio = new JLabel("FECHA DE INICIO");
		lblFechaDeInicio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFechaDeInicio.setBounds(10, 71, 157, 22);
		contentPane.add(lblFechaDeInicio);
		
		JLabel lblFechaDeFinalizacion = new JLabel("FECHA DE FINALIZACION");
		lblFechaDeFinalizacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFechaDeFinalizacion.setBounds(10, 102, 224, 22);
		contentPane.add(lblFechaDeFinalizacion);
		
		JLabel lblLocador = new JLabel("LOCADOR");
		lblLocador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLocador.setBounds(10, 135, 94, 22);
		contentPane.add(lblLocador);
		
		JLabel lblFechaMaximaDe = new JLabel("FECHA MAXIMA DE PAGO");
		lblFechaMaximaDe.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFechaMaximaDe.setBounds(10, 162, 224, 22);
		contentPane.add(lblFechaMaximaDe);
		
		JLabel lblGarantia = new JLabel("GARANTIA");
		lblGarantia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGarantia.setBounds(10, 195, 94, 22);
		contentPane.add(lblGarantia);
		
		JLabel lblGastosInmobiliaria = new JLabel("GASTOS INMOBILIARIA");
		lblGastosInmobiliaria.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGastosInmobiliaria.setBounds(10, 228, 207, 22);
		contentPane.add(lblGastosInmobiliaria);
		
		JLabel lblHistorialPagos = new JLabel("PAGOS REALIZADOS");
		lblHistorialPagos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHistorialPagos.setBounds(10, 261, 172, 22);
		contentPane.add(lblHistorialPagos);
	}
	
	private void cargarButtons() {
		JButton btnPropiedad = new JButton("PROPIEDAD");
		btnPropiedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vPropiedad vpropiedad = new vPropiedad(contrato.getLocacion(),false);
				vpropiedad.setVisible(true);
			}
		});
		btnPropiedad.setBounds(315, 297, 130, 23);
		contentPane.add(btnPropiedad);
		
		JButton btnCliente = new JButton("CLIENTE");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vCliente vcliente = new vCliente(contrato.getLocatario(),false);
				vcliente.setVisible(true);
			}
		});
		btnCliente.setBounds(315, 331, 130, 23);
		contentPane.add(btnCliente);
		
		btnCobrar = new JButton("NUEVO PAGO");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vCobro vcobro = new vCobro(contrato,table,"infoContrato_pago");
				vcobro.setVisible(true);
			}
		});
		btnCobrar.setBounds(315, 365, 130, 23);
		contentPane.add(btnCobrar);
		
		JButton btnGuardarCopia = new JButton("FACTURA");
		btnGuardarCopia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna factura");
				}else {
					DAOPagos ipago = new DAOPagosImpl();
					GeneradorPDF pdf = new GeneradorPDF(contrato,ipago.getPago((Integer)modelo.getValueAt(row, 0)));
					pdf.generarPDFFactura();
				}
			}
		});
		btnGuardarCopia.setBounds(315, 399, 130, 23);
		contentPane.add(btnGuardarCopia);
		
		JButton btnCopiaContrato = new JButton("COPIA CONTRATO");
		btnCopiaContrato.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCopiaContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorPDF pdf = new GeneradorPDF(contrato,obtenerPrimerPago());
				pdf.generarPDFContrato();
			}
		});
		btnCopiaContrato.setBounds(315, 433, 130, 23);
		contentPane.add(btnCopiaContrato);
		
		btnRescindir = new JButton("RESCINDIR");
		btnRescindir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String seleccion = (String) JOptionPane.showInputDialog(
						   null,
						   "Seleccione opcion",
						   "Selector de opciones",
						   JOptionPane.QUESTION_MESSAGE,
						   null,  // null para icono defecto
						   new String[] { "Falta de Pago", "Incumplimiento contrato", "Rescision anticipada" },
						   "Falta de Pago");
				opcion(seleccion);
			}
		});
		btnRescindir.setBounds(315, 467, 130, 23);
		contentPane.add(btnRescindir);
	}
	
	private void cargarTextFields(boolean editable) {
		txtPlazo = new JTextField();
		txtPlazo.setBounds(86, 13, 73, 20);
		contentPane.add(txtPlazo);
		txtPlazo.setColumns(10);
		txtPlazo.setEditable(editable);
		
		txtFFirma = new JTextField();
		txtFFirma.setColumns(10);
		txtFFirma.setBounds(161, 41, 114, 20);
		contentPane.add(txtFFirma);
		txtFFirma.setEditable(editable);
		
		txtFInicio = new JTextField();
		txtFInicio.setColumns(10);
		txtFInicio.setBounds(171, 71, 114, 20);
		contentPane.add(txtFInicio);
		txtFInicio.setEditable(editable);
		
		txtFFinalizacion = new JTextField();
		txtFFinalizacion.setColumns(10);
		txtFFinalizacion.setBounds(229, 102, 114, 20);
		contentPane.add(txtFFinalizacion);
		txtFFinalizacion.setEditable(editable);
		
		txtLocador = new JTextField();
		txtLocador.setColumns(10);
		txtLocador.setBounds(103, 135, 215, 20);
		contentPane.add(txtLocador);
		txtLocador.setEditable(editable);
		
		txtPago = new JTextField();
		txtPago.setColumns(10);
		txtPago.setBounds(229, 164, 46, 20);
		contentPane.add(txtPago);
		txtPago.setEditable(editable);
		
		txtGarantia = new JTextField();
		txtGarantia.setColumns(10);
		txtGarantia.setBounds(113, 197, 104, 20);
		contentPane.add(txtGarantia);
		txtGarantia.setEditable(editable);
		
		txtGastos = new JTextField();
		txtGastos.setColumns(10);
		txtGastos.setBounds(214, 230, 104, 20);
		contentPane.add(txtGastos);
		txtGastos.setEditable(editable);
		
	}
	
	private void cargarEstadoInmueble(Contrato c) {
		DAOEstadoInmueble iestado = new DAOEstadoInmuebleImpl();
		List<EstadoInmueble> estado_inmueble = iestado.getEstadoInmueble(c.getId());
		if (estado_inmueble.size() != 0) {
			txtEstadoInmueble.setText(GeneradorTexto.generarTexto(estado_inmueble));
		}
		txtEstadoInmueble.setEditable(false);
	}
	
	private void cargarCampos(Contrato c) {
		txtPlazo.setText(String.valueOf(c.getPlazo()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		txtFFirma.setText(format.format(c.getFechaFirma()));
		txtFInicio.setText(format.format(c.getFechaInicio()));
		txtFFinalizacion.setText(format.format(c.getFechaFinalizacion()));
		String nombre = c.getLocador().getNombre().toUpperCase();
		String apellido = c.getLocador().getApellido().toUpperCase();
		txtLocador.setText(nombre+","+apellido);
		txtPago.setText(String.valueOf(c.getFechaMaxPago()));
		txtGarantia.setText(String.valueOf(c.getGarantia()));
		txtGastos.setText(String.valueOf(c.getGastosInmobiliaria()));
		cargarEstadoInmueble(c);
	}
	
	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
	
	private Pagos obtenerPrimerPago() {
		DAOContrato icontrato = new DAOContratoImpl();
		List<Pagos> pagos = icontrato.getPagos(contrato.getId());
		if (pagos.size() != 0) {
			return pagos.get(0);
		}else {
			return null;
		}
	}
	
	private int confirmarAccion() {
		int input = JOptionPane.showConfirmDialog(null, "¿Desea confirmar la accion elegida?", "Elija una opcion...",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
		return input;
	}
	
	private int diferenciaMeses() {
		Calendar inicio = new GregorianCalendar();
        Calendar fin = new GregorianCalendar();
        inicio.setTime(contrato.getFechaInicio());
        fin.setTime(new Date());
        int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        int difM = difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
        return difM;
	}
	
	private void opcion(String texto) {
		switch(texto) {
			case("Falta de Pago"):{
				if (confirmarAccion() == 0) {
					System.out.println(contrato.cumpleFaltaDePago());
					if (contrato.cumpleFaltaDePago()) {
						contrato.setFechaFinalizacion(new Date());
						DAOContrato icontrato = new DAOContratoImpl();
						icontrato.modificar(contrato);
						Tablas.actualizarTContratos(tabla);
					}
				}
				break;
			}
			case("Incumplimiento contrato"):{
				if (confirmarAccion() == 0) {
					contrato.setFechaFinalizacion(new Date());
					DAOContrato icontrato = new DAOContratoImpl();
					icontrato.modificar(contrato);
					Tablas.actualizarTContratos(tabla);
				}
				break;
			}
			case("Rescision anticipada"):{
				if (confirmarAccion() == 0) {
					double mescompleto = contrato.getPrecio().getPrecio();
					double mediomes = mescompleto/2;
					contrato.setFechaFinalizacion(new Date());
					if (diferenciaMeses() <= anioenMeses) {
						double total = mescompleto + mediomes;
						JOptionPane.showMessageDialog(null, "Debera abonar: $"+String.format("%.2f", total));
						contrato.setFechaFinalizacion(new Date());
						DAOContrato icontrato = new DAOContratoImpl();
						icontrato.modificar(contrato);
					}else {
						JOptionPane.showMessageDialog(null, "Debera abonar: $"+String.format("%.2f", mescompleto));
						contrato.setFechaFinalizacion(new Date());
						DAOContrato icontrato = new DAOContratoImpl();
						icontrato.modificar(contrato);
					}
					Tablas.actualizarTContratos(tabla);
				}
				break;
			}
		}
	}
	
	private void cargarVentana() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(vMain.class.getResource("/Imagenes/icon_infoContrato.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 596, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		super.setTitle("CONTRATO: "+contrato.getId());
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null);
		cargarLabels();
		cargarButtons();
		cargarTextFields(false);
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(10, 294, 279, 209);
		contentPane.add(scroll);
		
		modelo = TableModels.crearModeloPagos(modelo);
		table.setModel(modelo);
		Tablas.actualizarTPagos(table,contrato.getId());
		
		txtEstadoInmueble = new JTextPane();
		JScrollPane scroll2 = new JScrollPane(txtEstadoInmueble);
		scroll2.setBounds(359, 41, 211, 225);
		contentPane.add(scroll2);
		
		JLabel lblEstadoInmueble = new JLabel("ESTADO INMUEBLE");
		lblEstadoInmueble.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstadoInmueble.setBounds(359, 11, 211, 22);
		contentPane.add(lblEstadoInmueble);
		
	}
	
	/*VENTANA INFO CONTRATO DESDE VENTANA vVerContratos*/
	public vInfoContrato(Contrato c,JTable table) {
		tabla = table;
		contrato = c;
		cargarVentana();
		cargarCampos(c);
		if ((Fechas.compararFechas(contrato.getFechaFinalizacion())) < 0) {
			btnRescindir.setEnabled(false);
			btnCobrar.setEnabled(false);
		}
	}
	
	/*VENTANA INFO CONTRATO DESDE VENTANA vMain*/
	public vInfoContrato(Contrato c) {
		tabla = table;
		contrato = c;
		cargarVentana();
		cargarCampos(c);
		btnRescindir.setEnabled(false);
		if ((Fechas.compararFechas(contrato.getFechaFinalizacion())) <= 0) {
			btnCobrar.setEnabled(false);
		}
	}
}
