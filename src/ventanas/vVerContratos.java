package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.Contrato;
import clases.Tablas;
import interfaces.DAOContrato;
import interfaces.DAOContratoImpl;
import utils.TableModels;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class vVerContratos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	
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
		btnEliminar.setBounds(466, 478, 135, 23);
		contentPane.add(btnEliminar);
		
		JPanel pnlBusqueda = new JPanel();
		pnlBusqueda.setBounds(10, 11, 736, 166);
		contentPane.add(pnlBusqueda);
	}
	
	public vVerContratos() {
		cargarVentana();
	}
}
