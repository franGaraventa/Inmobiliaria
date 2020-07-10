package ventanas;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import clases.DAOPropiedad;
import clases.DAOPropiedadImpl;
import clases.Propiedad;
import clases.Tablas;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class vVerPropiedades extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;

	@SuppressWarnings("serial")
	private void crearModelo() {
		try {
        modelo = (new DefaultTableModel(null, new Object[]{"ID","Valor","Sup Lote","Sup Cubierta","Alquilado"}){
            @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
                java.lang.Integer.class,
                java.lang.Double.class,
                java.lang.Double.class,
                java.lang.Double.class,
                java.lang.Boolean.class,
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
		List<Propiedad> propiedades = ipropiedad.getPropiedades();
		if (!propiedades.isEmpty()){
			for (Propiedad p : propiedades) {
				modelo.addRow(new Object[] {p.getId(),p.getValor(),p.getSupLote(),p.getSupCubierta(),p.isAlquilado()});
			}
		}
		table.setModel(modelo);
	}
	
	private Propiedad getPropiedad() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna propiedad");
		}else {
			DAOPropiedad ipropiedad = new DAOPropiedadImpl();
			return ipropiedad.obtenerPropiedad((Integer)modelo.getValueAt(row, 0));
		}
		return null;
	}
	
	public void cargarButtons() {
		JButton btnInformacion = new JButton("INFORMACION");
		btnInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Propiedad p = getPropiedad();
				if (p != null) {
					vPropiedad vpropiedad = new vPropiedad(p,table);
					vpropiedad.setVisible(true);
					table.clearSelection();
				}
			}
		});
		btnInformacion.setBounds(393, 344, 127, 23);
		contentPane.add(btnInformacion);
		
		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna propiedad");
				}else {
					int input = JOptionPane.showConfirmDialog(null, "Desea eliminar la propiedad seleccionada?","Elija una opcion",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
					if (input == 0){
						DAOPropiedad ipropiedad = new DAOPropiedadImpl();
						Propiedad p = ipropiedad.obtenerPropiedad((Integer)modelo.getValueAt(row, 0));
						if (p != null) {
							ipropiedad.eliminar(p);
							table.clearSelection();	
							Tablas.actualizarTPropiedad(table);
						}
					}else {
						table.clearSelection();
					}
				}
			}
		});
		btnEliminar.setBounds(258, 344, 127, 23);
		contentPane.add(btnEliminar);
	}
	
	public void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 548, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(10, 11, 510, 322);
		contentPane.add(scroll);
		
		crearModelo();
		cargarTabla();
		cargarButtons();
	}
	
	public vVerPropiedades() {
		cargarVentana();
	}
}
