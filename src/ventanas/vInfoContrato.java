package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.Contrato;
import clases.Pagos;
import clases.Tablas;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;
import utils.ConvertirNumero;
import utils.GeneradorPDF;
import utils.TableModels;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class vInfoContrato extends JFrame {

	private static final long serialVersionUID = 1L;
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
	}
	
	private void cargarButtons() {
		JButton btnPropiedad = new JButton("PROPIEDAD");
		btnPropiedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vPropiedad vpropiedad = new vPropiedad(contrato.getLocacion(),false);
				vpropiedad.setVisible(true);
			}
		});
		btnPropiedad.setBounds(10, 261, 114, 23);
		contentPane.add(btnPropiedad);
		
		JButton btnCliente = new JButton("CLIENTE");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vCliente vcliente = new vCliente(contrato.getLocatario(),false);
				vcliente.setVisible(true);
			}
		});
		btnCliente.setBounds(134, 261, 114, 23);
		contentPane.add(btnCliente);
		
		JButton btnCobrar = new JButton("NUEVO PAGO");
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vCobro vcobro = new vCobro(contrato,table);
				vcobro.setVisible(true);
			}
		});
		btnCobrar.setBounds(258, 261, 114, 23);
		contentPane.add(btnCobrar);
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
		txtLocador.setBounds(103, 135, 172, 20);
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
		
		JLabel lblHistorialPagos = new JLabel("PAGOS REALIZADOS");
		lblHistorialPagos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHistorialPagos.setBounds(392, 11, 172, 22);
		contentPane.add(lblHistorialPagos);
		
	}
	
	private void cargarCampos(Contrato c) {
		txtPlazo.setText(String.valueOf(c.getPlazo()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		txtFFirma.setText(format.format(c.getFechaFirma()));
		txtFInicio.setText(format.format(c.getFechaInicio()));
		txtFFinalizacion.setText(format.format(c.getFechaFinalizacion()));
		txtLocador.setText(c.getLocador());
		txtPago.setText(String.valueOf(c.getFechaMaxPago()));
		txtGarantia.setText(String.valueOf(c.getGarantia()));
		txtGastos.setText(String.valueOf(c.getGastosInmobiliaria()));
	}
	
	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
	
	private Pagos obtenerPrimerPago() {
		DAOContrato icontrato = new DAOContratoImpl();
		List<Pagos> pagos = icontrato.getPagos(contrato.getId());
		if (pagos != null) {
			return pagos.get(0);
		}else {
			return null;
		}
	}
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 693, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null);
		cargarLabels();
		cargarButtons();
		cargarTextFields(false);
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(384, 41, 279, 209);
		contentPane.add(scroll);
		
		modelo = TableModels.crearModeloPagos(modelo);
		table.setModel(modelo);
		Tablas.actualizarTPagos(table,contrato.getId());
		
		JButton btnGuardarCopia = new JButton("FACTURA");
		btnGuardarCopia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					
				}else {
					DAOPagos ipago = new DAOPagosImpl();
					GeneradorPDF pdf = new GeneradorPDF(contrato,ipago.getPago((Integer)modelo.getValueAt(row, 0)));
					pdf.generarPDF();
				}
			}
		});
		btnGuardarCopia.setBounds(522, 261, 141, 23);
		contentPane.add(btnGuardarCopia);
		
		JButton btnCopiaContrato = new JButton("COPIA CONTRATO");
		btnCopiaContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneradorPDF pdf = new GeneradorPDF(contrato,obtenerPrimerPago());
				pdf.generarPDFContrato("12345678", "DOMICILIO 1");
			}
		});
		btnCopiaContrato.setBounds(382, 261, 130, 23);
		contentPane.add(btnCopiaContrato);
	}
	
	public vInfoContrato(Contrato c) {
		contrato = c;
		cargarVentana();
		cargarCampos(c);
	}
}
