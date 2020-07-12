package ventanas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import clases.Contrato;
import clases.FechaPautada;
import clases.PrecioCompleto;
import clases.PrecioPorcentual;
import clases.Propiedad;
import interfaces.DAOPropiedad;
import interfaces.DAOPropiedadImpl;
import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class vContrato extends JFrame {

	private static final int meses = 6;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPlazo;
	private JTextField txtLocador;
	private JTextField txtMaxPago;
	private JTable table;
	private JTextField txtGarantia;
	private JTextField txtGastosInmobiliaria;
	private DefaultTableModel modelo;
	private Panel pnlFechas;
	private JComboBox<String> chkFormaPago;
	private JDateChooser dcFechaInicio;
	private JDateChooser dcFechaFinalizacion;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vContrato frame = new vContrato();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*FORMA DE PAGO DEL CONTRATO*/
	private void agregarTipoPago(Contrato c,double expensas) {
		int seleccionado = chkFormaPago.getSelectedIndex();
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
				PrecioPorcentual pp = new PrecioPorcentual(precio,expensas);
				if (fechas.size() > 0) {
					for(Date fecha: fechas){
						String dato = JOptionPane.showInputDialog("Introduzca un porcentaje para la fecha: "+new SimpleDateFormat("dd/MM/yyyy").format(fecha));
						long d = fecha.getTime();
						java.sql.Date nfecha = new java.sql.Date(d);
						FechaPautada fp = new FechaPautada(nfecha,Double.parseDouble(dato),pp);
						pp.addFecha(fp);
					}
				}
				c.setPrecio(pp);
			}else {
				if (seleccionado == 2) {
					PrecioCompleto pc = new PrecioCompleto(c.getLocacion().getValor(),expensas,Integer.parseInt(txtPlazo.getText()));
					c.setPrecio(pc);
				}
			}
		}
	}
	
	private void calcularFechaFinalizacion(int meses) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dcFechaInicio.getDate());
		calendar.add(Calendar.MONTH, meses);
		dcFechaFinalizacion.setDate(calendar.getTime());
	}
	
	@SuppressWarnings("serial")
	private void crearModelo() {
		try {
        modelo = (new DefaultTableModel(null, new Object[]{"ID","Valor","Sup Lote","Sup Cubierta","Informacion"}){
            @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
                java.lang.Integer.class,
                java.lang.Double.class,
                java.lang.Double.class,
                java.lang.Double.class,
                java.lang.String.class,
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return canEdit[colIndex];
            }
        });
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
    	}
	}
	
	private void cargarTabla() {
		DAOPropiedad ipropiedad = new DAOPropiedadImpl();
		List<Propiedad> propiedades = ipropiedad.obtenerPropiedades(false);
		if (!propiedades.isEmpty()){
			for (Propiedad p : propiedades) {
				modelo.addRow(new Object[] {p.getId(),p.getValor(),p.getSupLote(),p.getSupCubierta(),p.getInformacion()});
			}
		}
		table.setModel(modelo);
	}
	
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
		
	}

	private void cargarComponentes() {
		txtPlazo = new JTextField();
		txtPlazo.setBounds(84, 21, 86, 20);
		contentPane.add(txtPlazo);
		txtPlazo.setColumns(10);
		
		pnlFechas = new Panel();
		pnlFechas.setBounds(0, 49, 391, 105);
		contentPane.add(pnlFechas);
		pnlFechas.setLayout(null);
		
		JDateChooser dcFechaFirma = new JDateChooser();
		dcFechaFirma.setBounds(173, 11, 139, 20);
		pnlFechas.add(dcFechaFirma);
		
		dcFechaInicio = new JDateChooser();
		dcFechaInicio.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				calcularFechaFinalizacion(Integer.valueOf(txtPlazo.getText()));
			}
		});
		dcFechaInicio.setBounds(175, 41, 139, 20);
		pnlFechas.add(dcFechaInicio);
		
		dcFechaFinalizacion = new JDateChooser();
		dcFechaFinalizacion.setBounds(235, 70, 139, 20);
		dcFechaFinalizacion.setEnabled(false);
		pnlFechas.add(dcFechaFinalizacion);
		
		txtLocador = new JTextField();
		txtLocador.setColumns(10);
		txtLocador.setBounds(108, 160, 188, 20);
		contentPane.add(txtLocador);
		
		JComboBox<String> cbLocatario = new JComboBox<String>();
		cbLocatario.setBounds(118, 191, 178, 20);
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
		crearModelo();
		cargarTabla();
		
	}

	private void cargarButtons() {
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calcularFechaFinalizacion(Integer.valueOf(txtPlazo.getText()));
			}
		});
		btnGuardar.setBounds(544, 557, 89, 23);
		contentPane.add(btnGuardar);
	}
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 659, 628);
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
