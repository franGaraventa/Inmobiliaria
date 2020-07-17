package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import clases.Contrato;
import clases.Pagos;
import interfaces.DAOPagos;
import interfaces.DAOPagosImpl;
import utils.ConvertirNumero;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

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
	private JLabel lblExpensas;
	private JLabel lblTotal;
	private Contrato contrato;
	private JLabel lblMes;
	private JTextField txtRecargo;
	
	/*OBTENER MES Y DIA*/
	private int getMes(String valor) {
		int mes = 0;
		switch (valor) {
            case "Enero":  mes = 1;
                     break;
            case "Febrero":  mes = 2;
                     break;
            case "Marzo":  mes = 3;
                     break;
            case "Abril":  mes = 4;
                     break;
            case "Mayo":  mes = 5;
                     break;
            case "Junio":  mes = 6;
                     break;
            case "Julio":  mes = 7;
                     break;
            case "Agosto":  mes = 8;
            		 break;
            case "Septiembre":  mes = 9;
            		 break;
            case "Octubre":  mes = 10;
            		 break;
            case "Noviembre":  mes = 11;
            		 break;
            case "Diciembre":  mes = 12;
            	 	 break;
        } 	 	
		return mes;
	}
	
	private String getMes(int valor) {
		String mes = "";
		switch (valor) {
            case 0:  mes = "Enero";
                     break;
            case 1:  mes = "Febrero";
                     break;
            case 2:  mes = "Marzo";
                     break;
            case 3:  mes = "Abril";
                     break;
            case 4:  mes = "Mayo";
                     break;
            case 5:  mes = "Junio";
                     break;
            case 6:  mes = "Julio";
                     break;
            case 7:  mes = "Agosto";
            		 break;
            case 8:  mes = "Septiembre";
            		 break;
            case 9:  mes = "Octubre";
            		 break;
            case 10:  mes = "Noviembre";
            		 break;
            case 11:  mes = "Diciembre";
            	 	 break;
        } 	 	
		return mes;
	}
	
	private String getDia(int valor) {
        String Valor_dia = "";
		if (valor == 1) {
            Valor_dia = "Domingo";
        } else if (valor == 2) {
            Valor_dia = "Lunes";
        } else if (valor == 3) {
            Valor_dia = "Martes";
        } else if (valor == 4) {
            Valor_dia = "Miercoles";
        } else if (valor == 5) {
            Valor_dia = "Jueves";
        } else if (valor == 6) {
            Valor_dia = "Viernes";
        } else if (valor == 7) {
            Valor_dia = "Sabado";
        }
        return Valor_dia;
	}
	/*-----------------------------------------------------------------------*/
	
	/*FECHA VENCIMIENTO*/
	private boolean fechaValida() {
		Date fecha = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		@SuppressWarnings("deprecation")
		Date fechaPago = new Date(calendar.get(Calendar.YEAR)-1900,calendar.get(Calendar.MONTH),contrato.getFechaMaxPago());
		return (fechaPago.before(fecha) || fechaPago.equals(fecha));
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
		lblFechaDelRecibo.setText("TANDIL, "+getDia(calendar.get(Calendar.DAY_OF_WEEK))+", "+calendar.get(Calendar.DAY_OF_MONTH)+" de "+getMes(calendar.get(Calendar.MONTH))+" del "+calendar.get(Calendar.YEAR));
		lblDPropiedad.setText(contrato.getLocacion().getUbicacion().getDireccion().toUpperCase());
		lblCiudad.setText(contrato.getLocacion().getUbicacion().getCiudad().toUpperCase());
		lblValor.setText(String.valueOf(contrato.getPrecio().getPrecioBase()));
		lblExpensas.setText(String.valueOf(contrato.getPrecio().getExpensas()));
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
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(155, 291, 135, 2);
		contentPane.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(108, 336, 143, 2);
		contentPane.add(separator_7);
		
		JLabel label = new JLabel("$");
		label.setFont(new Font("Verdana", Font.PLAIN, 20));
		label.setBounds(129, 230, 18, 26);
		contentPane.add(label);
		
		JLabel label_2 = new JLabel("$");
		label_2.setFont(new Font("Verdana", Font.PLAIN, 20));
		label_2.setBounds(139, 258, 13, 45);
		contentPane.add(label_2);
		
		JLabel label_1 = new JLabel("$");
		label_1.setFont(new Font("Verdana", Font.PLAIN, 20));
		label_1.setBounds(85, 304, 13, 45);
		contentPane.add(label_1);
	}
	
	private void cargarCampos() {
		lblFechaDelRecibo = new JLabel();
		lblFechaDelRecibo.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblFechaDelRecibo.setBounds(512, 52, 514, 26);
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
			
		lblExpensas = new JLabel();
		lblExpensas.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblExpensas.setBounds(154, 267, 125, 26);
		contentPane.add(lblExpensas);
			
		lblTotal = new JLabel();
		lblTotal.setBackground(Color.WHITE);
		lblTotal.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblTotal.setBounds(108, 312, 143, 26);
		contentPane.add(lblTotal);
		
	}
	
	
	private void cargarButtons() {
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPagos ipagos = new DAOPagosImpl();
				if (!ipagos.existePago(contrato.getId(),getMes(lblMes.getText()))) {
					if (fechaValida()) {
						if ((!txtRecargo.getText().isEmpty())) {
							Pagos pago = new Pagos(contrato,new Date(),Double.valueOf(lblTotal.getText()),Double.valueOf(txtRecargo.getText()));
							ipagos.agregar(pago);
							dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Ingrese un valor al campo recargo");
						}
					}else {
						Pagos pago = new Pagos(contrato,new Date(),Double.valueOf(lblTotal.getText()),0);
						ipagos.agregar(pago);
						dispose();
					}
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
		lblMes.setText(getMes(fecha.getMonth()));
		
		txtRecargo = new JTextField();
		txtRecargo.setFont(new Font("Verdana", Font.PLAIN, 20));
		txtRecargo.setBounds(408, 311, 125, 27);
		contentPane.add(txtRecargo);
		txtRecargo.setColumns(10);
		
		JLabel lblRecargo = new JLabel("RECARGO");
		lblRecargo.setFont(new Font("Verdana", Font.PLAIN, 20));
		lblRecargo.setBounds(289, 312, 125, 26);
		contentPane.add(lblRecargo);
		
	}
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
	public vCobro(Contrato c) {
		contrato = c;
		cargarVentana();
		cargarCampos(c);
	}
}