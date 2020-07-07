package ventanas;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import clases.DAOPersona;
import clases.DAOPersonaImpl;
import clases.Persona;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class vVerClientes extends JFrame {

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
            public Class getColumnClass(int columnIndex) {
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
	}
	
	private Persona getPersona() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna persona");
		}else {
			DefaultTableModel model =(DefaultTableModel) table.getModel();
			String dni = (String)model.getValueAt(row, 0);
			String nombre =(String)model.getValueAt(row, 1);
			String apellido = (String)model.getValueAt(row, 2);
			String email = (String)model.getValueAt(row, 3);
			String codArea = (String)model.getValueAt(row, 4);
			String Telefono = (String)model.getValueAt(row, 5);
			Persona p = new Persona(dni,nombre,apellido,email,codArea,Telefono);
			return p;
		}
		return null;
	}
	
	public static boolean isEmpty(JTable jTable) {
        if (jTable != null && jTable.getModel() != null) {
            return jTable.getModel().getRowCount()<=0?true:false;
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
				Persona p = getPersona();
				if (p != null) {
					vCliente vcliente = new vCliente(p);
					vcliente.setVisible(true);
					table.clearSelection();
				}
			}
		});
		btnModificar.setBounds(766, 304, 108, 23);
		contentPane.add(btnModificar);
		habilitarButton(btnModificar);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPersona ipersona = new DAOPersonaImpl();
				Persona p = getPersona();
				if (p != null) {
					ipersona.eliminar(p);
					table.clearSelection();
				}
			}
		});
		btnEliminar.setBounds(648, 304, 108, 23);
		contentPane.add(btnEliminar);
		habilitarButton(btnEliminar);
	}
	
	public vVerClientes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 377);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(21, 11, 853, 282);
		contentPane.add(table);
		crearModelo();
		cargarTabla();
		definirButtons();
	}
}
