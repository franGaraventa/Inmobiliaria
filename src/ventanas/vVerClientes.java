package ventanas;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.DAOPersona;
import clases.DAOPersonaImpl;
import clases.Persona;
import clases.Tablas;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class vVerClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel modelo;
	private JTable table;
	private JButton btnEliminar;
	
	private void crearModelo() {
		try {
        modelo = (new DefaultTableModel(null, new Object[]{"DNI","Nombre","Apellido","Email","Cod Area","Telefono"}){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
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
		DAOPersona ipersona = new DAOPersonaImpl();
		List<Persona> clientes = ipersona.getPersonas();
		if (!clientes.isEmpty()){
			for (Persona p : clientes) {
				modelo.addRow(new Object[] {p.getDni(),p.getNombre(),p.getApellido(),p.getEmail(),p.getCodArea(),p.getTelefono()});
			}
		}
		table.setModel(modelo);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
	}
	
	public static boolean isEmpty(JTable table) {
        if (table != null && table.getModel() != null) {
            return table.getModel().getRowCount()<=0?true:false;
        }
        return false;
    }
	
	private void habilitarButton(JButton btn) {
		if (isEmpty(table)) {
			btn.setEnabled(false);
		}else {
			btn.setEnabled(true);
		}
	}
	
	private void definirButtons() {
		JButton btnModificar = new JButton("MODIFICAR");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna persona");
				}else {
					DAOPersona ipersona = new DAOPersonaImpl();
					Persona p = ipersona.obtenerPersona((String)modelo.getValueAt(row, 0));
					if (p != null) {
						vCliente vcliente = new vCliente(p,table);
						vcliente.setVisible(true);
						table.clearSelection();
					}
				}
			}
		});
		contentPane.setLayout(null);
		btnModificar.setBounds(420, 361, 97, 23);
		contentPane.add(btnModificar);
		habilitarButton(btnModificar);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna persona");
				}else {
					int input = JOptionPane.showConfirmDialog(null, "Desea eliminar el usuario seleccionado?","Elija una opcion",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
					if (input == 0){
						DAOPersona ipersona = new DAOPersonaImpl();
						Persona p = ipersona.obtenerPersona((String)modelo.getValueAt(row, 0));
						if (p != null) {
							ipersona.eliminar(p);
							table.clearSelection();	
							Tablas.actualizarTPersona(table);
						}
					}else {
						table.clearSelection();
					}
				}
			}
		});
		btnEliminar.setBounds(522, 361, 97, 23);
		contentPane.add(btnEliminar);
		habilitarButton(btnEliminar);
	}
	
	public void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 657, 430);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(31,5,588,345);
		contentPane.add(scroll);
	}
	
	public vVerClientes() {
		cargarVentana();
		crearModelo();
		cargarTabla();
		definirButtons();
	}
}
