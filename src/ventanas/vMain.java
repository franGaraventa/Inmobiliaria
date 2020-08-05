package ventanas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.Contrato;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import utils.Tablas;
import utils.TableModels;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;


public class vMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int dias = 90;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloContrato;
	private JTable tableContratos;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vMain frame = new vMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void definirMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnClientes = new JMenu("Clientes");
		menuBar.add(mnClientes);
		
		JMenuItem mnNuevoCliente = new JMenuItem("Nuevo cliente");
		mnNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vCliente vcliente = new vCliente();
				vcliente.setVisible(true);
			}
		});
		mnClientes.add(mnNuevoCliente);
		
		JMenuItem mnVerClientes = new JMenuItem("Ver clientes");
		mnVerClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vVerClientes vclientes = new vVerClientes();
				vclientes.setVisible(true);
			}
		});
		mnClientes.add(mnVerClientes);
		
		JMenu mnPropiedades = new JMenu("Propiedades");
		menuBar.add(mnPropiedades);
		
		JMenuItem mnNuevaPropiedad = new JMenuItem("Nueva propiedad");
		mnNuevaPropiedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vPropiedad vpropiedad = new vPropiedad();
				vpropiedad.setVisible(true);
			}
		});
		mnPropiedades.add(mnNuevaPropiedad);
		
		JMenuItem mnVerPropiedades = new JMenuItem("Ver propiedades");
		mnVerPropiedades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vVerPropiedades vpropiedades = new vVerPropiedades();
				vpropiedades.setVisible(true);
			}
		});
		mnPropiedades.add(mnVerPropiedades);
		
		JMenu mnContratos = new JMenu("Contratos");
		menuBar.add(mnContratos);
		
		JMenuItem mnNuevoContrato = new JMenuItem("Nuevo Contrato");
		mnNuevoContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vContrato vcontrato = new vContrato();
				vcontrato.setVisible(true);
			}
		});
		mnContratos.add(mnNuevoContrato);
		
		JMenuItem mnVerContratos = new JMenuItem("Ver Contratos");
		mnVerContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vVerContratos vvercontratos = new vVerContratos();
				vvercontratos.setVisible(true);
			}
		});	
		mnContratos.add(mnVerContratos);
		
		JMenu mnLocadores = new JMenu("Locadores");
		menuBar.add(mnLocadores);
		
		JMenuItem mnLocador = new JMenuItem("Nuevo Locador");
		mnLocador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vLocador vlocador = new vLocador(false);
				vlocador.setVisible(true);
			}
		});	
		mnLocadores.add(mnLocador);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Ver Locadores");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vVerLocadores vverlocadores = new vVerLocadores();
				vverlocadores.setVisible(true);
			}
		});
		mnLocadores.add(mntmNewMenuItem);
	}
	
	private void verFechasPagos() {
		modelo = TableModels.crearModeloPagosVencidos(modelo);
		table.setModel(modelo);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		Tablas.actualizarTContratosVencidos(table);
	}
	
	private void verContratosAVencer() throws ParseException {
		DAOContrato icontrato = new DAOContratoImpl();
		List<Contrato> contratos = icontrato.getContratosVigentes(new Date());
		List<Contrato> vcontratos = new ArrayList<Contrato>();
		List<Long> ldias = new ArrayList<Long>();
		Calendar calendar = Calendar.getInstance();
		/*DEFINO MODELO*/
		modeloContrato = TableModels.crearModeloContratosConVencimientoProximo(modelo);
		tableContratos.setModel(modeloContrato);
		long mlsFActual = calendar.getTimeInMillis();
		/*-----------------------------------------------------------------*/
		for(Contrato c: contratos) {
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(c.getFechaFinalizacion());
			long mlsFFinalizacion = calendar2.getTimeInMillis();
			long diferencia_tiempo = mlsFFinalizacion - mlsFActual;
			long diferencia_dias = diferencia_tiempo / (24 * 60 * 60 * 1000);
			if (diferencia_dias < dias) {
				vcontratos.add(c);
				ldias.add(diferencia_dias);
			}
		}
		Tablas.actualizarTContratosConVencimientoProximo(tableContratos, vcontratos,ldias);
	}
	
	private void definirButtons() {
		JButton btnPago = new JButton("Nuevo pago");
		btnPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row != -1) {
					DAOContrato icontrato = new DAOContratoImpl();
					Contrato c = icontrato.getContrato((Integer) modelo.getValueAt(row, 0));
					vCobro vcobro = new vCobro(c,table,"main_pagos");
					vcobro.setVisible(true);
					table.clearSelection();
				}else {
					JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
				}
			}
		});
		btnPago.setBounds(407, 221, 118, 23);
		contentPane.add(btnPago);
		
		JButton btnInformacion = new JButton("Informacion");
		btnInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableContratos.getSelectedRow();
				if (row != -1) {
					DAOContrato icontrato = new DAOContratoImpl();
					Contrato c = icontrato.getContrato((Integer) modeloContrato.getValueAt(row, 0));
					vInfoContrato vinfocontrato = new vInfoContrato(c);
					vinfocontrato.setVisible(true);
					tableContratos.clearSelection();
				}else {
					JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
				}
			}
		});
		btnInformacion.setBounds(517, 464, 118, 23);
		contentPane.add(btnInformacion);
	}
	
	public vMain() throws ParseException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 558);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PAGOS POR VENCER");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(122, 11, 185, 14);
		contentPane.add(lblNewLabel);
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(122, 36, 403, 183);
		contentPane.add(scroll);
		
		JLabel lblContratosPorVencer = new JLabel("CONTRATOS POR VENCER");
		lblContratosPorVencer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContratosPorVencer.setBounds(10, 265, 229, 14);
		contentPane.add(lblContratosPorVencer);
		
		tableContratos = new JTable();
		JScrollPane scrollContrato = new JScrollPane();
		scrollContrato.setViewportView(tableContratos);
		scrollContrato.setBounds(10, 290, 625, 173);
		contentPane.add(scrollContrato);
		
		definirMenu();
		definirButtons();
		verFechasPagos();
		verContratosAVencer();
	}
}
