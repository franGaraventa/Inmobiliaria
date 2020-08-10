package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import clases.Contrato;
import clases.Pagos;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;
import utils.ConvertirNumero;
import utils.Fechas;
import utils.Tablas;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class vCobro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFechaDelRecibo;
	private JLabel lblNombreApellido;
	private JLabel lblPesosVEscrita;
	private JLabel lblDPropiedad;
	private JLabel lblCiudad;
	private JLabel lblFechaVencimiento;
	private JLabel lblValor;
	private JLabel lblTotal;
	private Contrato contrato;
	private JLabel lblMes;
	private JTextField txtRecargo;
	private JTable table;
	private JTextField txtExpensas;
	private double total;
	private double expensas;
	private String nombre_tabla;
	
	private void cambiarCampoTotal(JTextField txtField) {
		double nuevo_valor = Double.valueOf(lblTotal.getText()) + Double.valueOf(txtField.getText());
		total = nuevo_valor;
		lblTotal.setText(String.valueOf(nuevo_valor));
		double num = formatearDecimales(nuevo_valor,2);
		int p_ent= (int)num;
		double p_dec= num - p_ent;
		String valor = ConvertirNumero.cantidadConLetra(String.valueOf(num));
		if (p_dec != 0) {
			String valor2 = valor +"con "+ConvertirNumero.cantidadConLetra(String.valueOf(p_dec*100));
			lblPesosVEscrita.setText(valor2.toUpperCase());
		}else {
			lblPesosVEscrita.setText(valor.toUpperCase());
		}
	}
	
	private boolean beforeDate(Calendar fecha1,Calendar fecha2) {
		return (((fecha1.get(Calendar.DAY_OF_MONTH) - fecha2.get(Calendar.DAY_OF_MONTH)) >= 0) && 
				(((fecha1.get(Calendar.MONTH) + 1) - (fecha2.get(Calendar.MONTH) + 1)) >= 0) && 
				((fecha1.get(Calendar.YEAR) - fecha2.get(Calendar.YEAR)) >= 0));
	}
	
	/*FECHA VENCIMIENTO*/
	private boolean fechaValida() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		@SuppressWarnings("deprecation")
		Date fechaPago = new Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),contrato.getFechaMaxPago());
		Calendar calendarFP = Calendar.getInstance();
		calendarFP.setTime(fechaPago);
		return (!beforeDate(calendarFP,calendar));
	}
	
	private void habilitarRecargo() {
		if (!fechaValida()) {
			txtRecargo.setEnabled(false);
		}
	}
	
	/*-----------------------------------------------------------------------*/
	
	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
	
	private void cargarCampos(Contrato contrato){
		String nyp = contrato.getLocatario().getNombre() + ", "+ contrato.getLocatario().getApellido();
		lblNombreApellido.setText(nyp.toUpperCase());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String fecha = contrato.getFechaMaxPago() +"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
		lblFechaVencimiento.setText(fecha);
		lblFechaDelRecibo.setText("TANDIL, "+Fechas.getDia(calendar.get(Calendar.DAY_OF_WEEK))+", "+calendar.get(Calendar.DAY_OF_MONTH)+" de "+Fechas.getMes(calendar.get(Calendar.MONTH))+" del "+calendar.get(Calendar.YEAR));
		lblDPropiedad.setText(contrato.getLocacion().getUbicacion().getDireccion().toUpperCase());
		lblCiudad.setText(contrato.getLocacion().getUbicacion().getCiudad().toUpperCase());
		lblValor.setText(String.valueOf(contrato.getPrecio().getPrecioBase()));
		lblTotal.setText(String.valueOf(formatearDecimales(contrato.getPrecio().getPrecio(),2)));
		double num = formatearDecimales(contrato.getPrecio().getPrecio(),2);
		int p_ent= (int)num;
		double p_dec= num - p_ent;
		String valor = ConvertirNumero.cantidadConLetra(String.valueOf(num));
		if (p_dec != 0) {
			String valor2 = valor +"con "+ConvertirNumero.cantidadConLetra(String.valueOf(p_dec*100));
			lblPesosVEscrita.setText(valor2.toUpperCase());
		}else {
			lblPesosVEscrita.setText(valor.toUpperCase());
		}
		txtExpensas.setText(String.valueOf(contrato.getPrecio().getExpensas()));
		/*PARAMETROS DE PRUEBA*/
		expensas = contrato.getPrecio().getExpensas();
		total = Double.valueOf(lblTotal.getText());
	}
	
	private void cargarLabels() {
		contentPane.setLayout(null);
		JLabel lblReciboDeAlquiler = new JLabel("RECIBO DE ALQUILER");
		lblReciboDeAlquiler.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblReciboDeAlquiler.setBounds(421, 21, 228, 26);
		contentPane.add(lblReciboDeAlquiler);
		
		JLabel lblRecibiDe = new JLabel("Recibi de:");
		lblRecibiDe.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblRecibiDe.setBounds(12, 74, 120, 26);
		contentPane.add(lblRecibiDe);
		
		JLabel lblLaCantidadDe = new JLabel("La cantidad de PESOS: ");
		lblLaCantidadDe.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblLaCantidadDe.setBounds(12, 115, 238, 26);
		contentPane.add(lblLaCantidadDe);
		
		JLabel lblEnConceptosDel = new JLabel("en conceptos del alquiler que se encuentra ubicado en");
		lblEnConceptosDel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblEnConceptosDel.setBounds(12, 154, 561, 26);
		contentPane.add(lblEnConceptosDel);
		
		JLabel lblDeLaCiudad = new JLabel("de la ciudad de ");
		lblDeLaCiudad.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblDeLaCiudad.setBounds(12, 193, 159, 26);
		contentPane.add(lblDeLaCiudad);
		
		JLabel lblcorrespondienteAlMes = new JLabel("Correspondiente al mes");
		lblcorrespondienteAlMes.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblcorrespondienteAlMes.setBounds(328, 193, 255, 26);
		contentPane.add(lblcorrespondienteAlMes);
		
		JLabel lblQueVenceEl = new JLabel("que vence el");
		lblQueVenceEl.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblQueVenceEl.setBounds(728, 193, 159, 26);
		contentPane.add(lblQueVenceEl);
		
		JLabel lblAlquiler = new JLabel("ALQUILER");
		lblAlquiler.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblAlquiler.setBounds(12, 230, 107, 26);
		contentPane.add(lblAlquiler);
		
		JLabel lblExpensas = new JLabel("EXPENSAS");
		lblExpensas.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblExpensas.setBounds(12, 267, 120, 26);
		contentPane.add(lblExpensas);
		
		JLabel lblTotal = new JLabel("TOTAL");
		lblTotal.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblTotal.setBounds(12, 312, 65, 26);
		contentPane.add(lblTotal);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(120, 98, 471, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(250, 139, 750, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(571, 181, 429, 2);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(171, 217, 150, 2);
		contentPane.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(872, 217, 120, 2);
		contentPane.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(144, 257, 135, 2);
		contentPane.add(separator_5);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(108, 336, 143, 2);
		contentPane.add(separator_7);
		
		JLabel label = new JLabel("$");
		label.setFont(new Font("Verdana", Font.PLAIN, 20));
		label.setBounds(129, 230, 18, 26);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("$");
		label_1.setFont(new Font("Verdana", Font.PLAIN, 20));
		label_1.setBounds(85, 304, 13, 45);
		contentPane.add(label_1);
	}
	
	private void cargarCampos() {
		lblFechaDelRecibo = new JLabel();
		lblFechaDelRecibo.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblFechaDelRecibo.setBounds(611, 52, 415, 26);
		contentPane.add(lblFechaDelRecibo);
			
		lblNombreApellido = new JLabel();
		lblNombreApellido.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblNombreApellido.setBounds(120, 74, 471, 26);
		contentPane.add(lblNombreApellido);
			
		lblPesosVEscrita = new JLabel("PESOS VERSION ESCRITA");
		lblPesosVEscrita.setFont(new Font("Verdana", Font.PLAIN, 19));
		lblPesosVEscrita.setBounds(271, 115, 729, 26);
		contentPane.add(lblPesosVEscrita);
			
		lblDPropiedad = new JLabel();
		lblDPropiedad.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblDPropiedad.setBounds(585, 154, 409, 26);
		contentPane.add(lblDPropiedad);
			
		lblCiudad = new JLabel();
		lblCiudad.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblCiudad.setBounds(190, 193, 147, 26);
		contentPane.add(lblCiudad);
			
		lblFechaVencimiento = new JLabel();
		lblFechaVencimiento.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblFechaVencimiento.setBounds(872, 193, 120, 26);
		contentPane.add(lblFechaVencimiento);
			
		lblValor = new JLabel();
		lblValor.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblValor.setBounds(159, 232, 120, 26);
		contentPane.add(lblValor);
			
		lblTotal = new JLabel();
		lblTotal.setBackground(Color.WHITE);
		lblTotal.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblTotal.setBounds(108, 312, 143, 26);
		contentPane.add(lblTotal);
		
	}
	
	@SuppressWarnings("deprecation")
	private void cargarButtons() {
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPagos ipagos = new DAOPagosImpl();
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				if (!ipagos.existePago(contrato.getId(),Fechas.getMes(lblMes.getText()),cal.get(Calendar.YEAR))) {
					if (fechaValida()) {
						if ((!txtRecargo.getText().isEmpty())) {
							Pagos pago = new Pagos(contrato,new Date(),Double.valueOf(lblTotal.getText()),Double.valueOf(txtRecargo.getText()));
							ipagos.agregar(pago);
						}else {
							JOptionPane.showMessageDialog(null, "Ingrese un valor al campo recargo");
						}
					}else {
						Pagos pago = new Pagos(contrato,new Date(),Double.valueOf(lblTotal.getText()),0);
						ipagos.agregar(pago);
					}
					if (table != null)
						if (nombre_tabla.equals("main_pagos")) {
							Tablas.actualizarTContratosVencidos(table);
						}else {
							Tablas.actualizarTPagos(table, contrato.getId());
						}
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "El pago correspondiente al mes: "+lblMes.getText()+" ya fue ingresado");
				}
			}
		});
		btnGuardar.setBounds(897, 319, 120, 23);
		contentPane.add(btnGuardar);
		
		lblMes = new JLabel("");
		lblMes.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblMes.setBounds(595, 194, 143, 24);
		contentPane.add(lblMes);
		
		Date fecha = new Date();
		lblMes.setText(Fechas.getMes(fecha.getMonth()));
		
		txtRecargo = new JTextField();
		txtRecargo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					if (!txtRecargo.getText().isEmpty()) {
						cambiarCampoTotal(txtRecargo);
					}
				}
			}
		});
		txtRecargo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRecargo.setBounds(408, 311, 125, 27);
		contentPane.add(txtRecargo);
		txtRecargo.setColumns(10);
	
		
		JLabel lblRecargo = new JLabel("RECARGO");
		lblRecargo.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblRecargo.setBounds(289, 312, 125, 26);
		contentPane.add(lblRecargo);
		
		txtExpensas = new JTextField();
		txtExpensas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char tecla = e.getKeyChar();
				if (tecla == KeyEvent.VK_ENTER) {
					double nuevo_valor = total - expensas;
					lblTotal.setText(String.valueOf(nuevo_valor));
					if (!txtExpensas.getText().isEmpty()) {
						expensas = Double.parseDouble(txtExpensas.getText());
						cambiarCampoTotal(txtExpensas);
					}
				}
			}
		});
		txtExpensas.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtExpensas.setColumns(10);
		txtExpensas.setBounds(139, 266, 125, 27);
		contentPane.add(txtExpensas);
		
	}
	
	private void cargarVentana() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(vMain.class.getResource("/Imagenes/icon_pago.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setTitle("RECIBO DE ALQUILER");
		setBounds(100, 100, 1052, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		cargarLabels();
		cargarCampos();
		cargarButtons();
		habilitarRecargo();
	}
	
	/*CONSTRUCTORES*/
	/*VENTANA COBRO DESDE MAIN O INFO CONTRATO*/
	public vCobro(Contrato c,JTable tabla,String nombre_tabla) {
		contrato = c;
		table = tabla;
		this.nombre_tabla = nombre_tabla;
		cargarVentana();
		cargarCampos(c);
	}
	
	public vCobro(Contrato c) {
		contrato = c;
		table = null;
		cargarVentana();
		cargarCampos(c);
	}
}
