package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import clases.Tablas;
import utils.TableModels;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;

public class vVerContratos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 772, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		JScrollPane scrVigentes = new JScrollPane();
		scrVigentes.setViewportView(table);
		scrVigentes.setBounds(10, 148, 736, 273);
		contentPane.add(scrVigentes);
		
		modelo = TableModels.crearModeloContrato(modelo);
		table.setModel(modelo);
		Tablas.actualizarTContratos(table);
	}
	
	public vVerContratos() {
		cargarVentana();
	}
}
