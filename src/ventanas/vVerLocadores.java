package ventanas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import clases.Locador;
import interfaces.DAOLocador;
import interfaces.DAOLocadorImpl;
import utils.Tablas;
import utils.TableModels;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class vVerLocadores extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	
	private void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(10, 11, 500, 230);
		contentPane.add(scroll);
	}
	
	private void cargarButtons() {
		JButton btnInformacion = new JButton("INFORMACION");
		btnInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun locador");
				}else {
					DAOLocador ilocador = new DAOLocadorImpl();
					Locador l = ilocador.getLocador((Integer)modelo.getValueAt(row, 0));
					if (l != null) {
						vLocador vlocador = new vLocador(l,table,true);
						vlocador.setVisible(true);
						table.clearSelection();
					}
				}
			}
		});
		btnInformacion.setBounds(281, 252, 122, 23);
		contentPane.add(btnInformacion);
		
		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setBounds(413, 252, 97, 23);
		contentPane.add(btnEliminar);
	}
	
	public vVerLocadores() {
		cargarVentana();
		cargarButtons();
		modelo = TableModels.crearModeloLocador(modelo);
		table.setModel(modelo);
		Tablas.actualizarTLocadores(table);
	}
}
