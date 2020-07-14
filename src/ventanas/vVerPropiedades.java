package ventanas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Pruebas.FPropSupLote;
import Pruebas.FPropValor;
import Pruebas.FPropiedad;
import Pruebas.TextoBusqueda;
import Pruebas.FPropAND;
import Pruebas.FPropAmoblado;
import Pruebas.FPropNOT;
import Pruebas.FPropOR;
import Pruebas.FPropSupCubierta;
import clases.Propiedad;
import clases.Tablas;
import interfaces.DAOPropiedad;
import interfaces.DAOPropiedadImpl;
import utils.TableModels;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class vVerPropiedades extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int par = 2;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	private int cParametros;
	private JComboBox<String> cbLogica;
	private JComboBox<String> cbBuscador;
	private FPropiedad filtro;
	private JTextField txtValor;
	private JTextField txtOperador;
	private List<TextoBusqueda> busqueda;
	private List<String> conectores;
	private JTextPane txtBusqueda;
	private JCheckBox chkNegar;
	
	/*GENERADOR DE TEXTO DE BUSQUEDA*/
	private String generarTexto() {
		String texto = "";
		if ((busqueda.size() == 1) && (conectores.isEmpty())) {
			if (busqueda.get(0).isNegado()) {
				texto = "¬("+busqueda.get(0).getTexto()+")";
			}else {
				texto = busqueda.get(0).getTexto()+" ";
			}
		}else {
			int index = 0;
			for(String conector: conectores) {
				if (index < par) {
					if (busqueda.get(index).isNegado()) {
						texto = texto +" ¬("+busqueda.get(index).getTexto() + ") ";
					}else {
						texto = texto + busqueda.get(index).getTexto() + " ";
					}
					index++;
					texto = texto + conector + " ";
					if (busqueda.get(index).isNegado()) {
						texto = texto +" ¬("+ busqueda.get(index).getTexto() + ") ";
					}else {
						texto = texto + busqueda.get(index).getTexto() + " ";
					}
					index++;
				}else {
					texto = texto + conector + " ";
					if (busqueda.get(index).isNegado()) {
						texto = texto +" ¬("+ busqueda.get(index).getTexto()+ ") ";
					}else {
						texto = texto + busqueda.get(index).getTexto()+ " ";
					}
					index++;
				}
			}
		}
		return texto;
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
	
	private FPropiedad agregarFiltro(int seleccionado,String valor,String comparador) {
		switch(seleccionado) {
			/*1 - Valor, 2 - Superficie Lote, 3 - Superficie Cubierta,4 - Amoblado*/
			case (1): {
				busqueda.add(new TextoBusqueda("Valor "+ comparador + " " +valor));
				return new FPropValor(Double.parseDouble(valor),comparador); 
			}
			case (2): {
				busqueda.add(new TextoBusqueda("Superficie Lote "+ comparador + " " +valor));
				return new FPropSupLote(Double.parseDouble(valor),comparador);
			}
			case (3):{
				busqueda.add(new TextoBusqueda("Superficie Cubierta "+ comparador + " " +valor));
				return new FPropSupCubierta(Double.parseDouble(valor),comparador);
			}
			case (4): if ((Integer.parseInt(valor) == 0) || (Integer.parseInt(valor) == 1)) {
				if ((Integer.parseInt(valor) == 0)) {
					busqueda.add(new TextoBusqueda("Amoblado = NO"));
					return new FPropAmoblado(false);
				}else {
					busqueda.add(new TextoBusqueda("Amoblado = SI"));
					return new FPropAmoblado(true);
				}
			}
		}
		return null;
	}
	
	private FPropiedad agregarFiltroLogica(int logica,FPropiedad filtro2) {
		/*1 - y, 2 - o, 3 - no*/
		switch(logica) {
			case (1): {
				conectores.add("y");
				return new FPropAND(filtro,filtro2);
			}
			case (2): {
				conectores.add("o");
				return new FPropOR(filtro,filtro2);
			}
		}
		return null;
	}
	
	private void resetearCampos() {
		filtro = null;
		txtValor.setText("");
		txtOperador.setText("");
		cParametros = 0;
		cbLogica.setSelectedIndex(0);
		cbBuscador.setSelectedIndex(0);
		txtBusqueda.setText("");
		cbLogica.setEnabled(false);
		busqueda.clear();
		conectores.clear();
	}
	
	private void setear() {
		cParametros++;
		txtValor.setText("");
		txtOperador.setText("");
		txtBusqueda.setText("");
		chkNegar.setSelected(false);
		cbLogica.setSelectedIndex(0);
		cbBuscador.setSelectedIndex(0);
	}
	
	private boolean comprobarCampos(int seleccionado,int logica) {
		if ((seleccionado <= 0) && (logica > 0)) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un campo a buscar");
			return false;
		}else if ((seleccionado > 0) && (logica <= 0)) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un conector");
			return false;
		}else if ((seleccionado == 0) && (logica == 0)) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un campo a buscar y un conector");
			return false;
		}
		return true;
	}
	
	/*VALIDA LOS DATOS INGRESADOR EN LOS CAMPOS VALOR Y CONDICIONAL*/
	public static boolean isNumeric(String str){
	    try{
	        double d = Double.parseDouble(str);
	    }
	    catch(NumberFormatException nfe){  
	        return false;
	    }
	    return true;
	}
	
	public static boolean simbolosValidos(String str) {
		String[] simbolos = {">","<",">=","=","<="};
		for(String s: simbolos) {
			if (s.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean datosValidos() {
		if ((!txtValor.getText().isEmpty()) && (cbBuscador.getSelectedIndex() == 4)){
			if ((isNumeric(txtValor.getText()))){
				return true;
			}
		}else {
			if ((!txtValor.getText().isEmpty()) && (!txtOperador.getText().isEmpty())) {
				if ((isNumeric(txtValor.getText())) && (simbolosValidos(txtOperador.getText()))){
					return true;
				}
			}
		}
		return false;
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
		btnInformacion.setBounds(448, 446, 127, 23);
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
							resetearCampos();
						}
					}else {
						table.clearSelection();
					}
				}
			}
		});
		btnEliminar.setBounds(311, 446, 127, 23);
		contentPane.add(btnEliminar);
		
		JPanel pnlBusqueda = new JPanel();
		pnlBusqueda.setBounds(13, 11, 561, 178);
		contentPane.add(pnlBusqueda);
		pnlBusqueda.setLayout(null);
		
		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPropiedad ipropiedades = new DAOPropiedadImpl();
				List<Propiedad> propiedades = ipropiedades.getPropiedades();
				List<Propiedad> fpropiedades = new ArrayList<Propiedad>();
				for(Propiedad p: propiedades) {
					if (filtro.cumple(p)) {
						fpropiedades.add(p);
					}
				}
				Tablas.actualizarTPropiedad(table, fpropiedades);
			}
		});
		btnBuscar.setBounds(462, 143, 89, 23);
		pnlBusqueda.add(btnBuscar);
		
		JButton btnReestablecer = new JButton("REESTABLECER");
		btnReestablecer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tablas.actualizarTPropiedad(table);
				resetearCampos();
			}
		});
		btnReestablecer.setBounds(318, 143, 128, 23);
		pnlBusqueda.add(btnReestablecer);
		
		cbBuscador = new JComboBox<String>();
		cbBuscador.setModel(new DefaultComboBoxModel<String>(new String[] {"Elija que ingresar a la busqueda", "Valor", "Superficie Lote", "Superficie Cubierta", "Amoblado"}));
		cbBuscador.setBounds(92, 11, 175, 20);
		pnlBusqueda.add(cbBuscador);
		
		JButton btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccionado = cbBuscador.getSelectedIndex();
				if (cParametros == 0) {
					if (seleccionado > 0) {
						if (datosValidos()) {
							if (chkNegar.isSelected()) {
								filtro = agregarFiltro(seleccionado,txtValor.getText(),txtOperador.getText());
								filtro = new FPropNOT(filtro);
								busqueda.get(busqueda.size()-1).setNegado(true);
							}else {
								filtro = agregarFiltro(seleccionado,txtValor.getText(),txtOperador.getText());
							}
							cbLogica.setEnabled(true);
							setear();
							txtBusqueda.setText(generarTexto());
						}else {
							JOptionPane.showMessageDialog(null, "Datos invalidos");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un campo a buscar");
					}
				}else {
					int logica = cbLogica.getSelectedIndex();
					if (comprobarCampos(seleccionado,logica)){
						if (datosValidos()) {
							if (chkNegar.isSelected()) {
								filtro = agregarFiltroLogica(logica,new FPropNOT(agregarFiltro(seleccionado,txtValor.getText(),txtOperador.getText())));
								busqueda.get(busqueda.size()-1).setNegado(true);
							}else {
								filtro = agregarFiltroLogica(logica,agregarFiltro(seleccionado,txtValor.getText(),txtOperador.getText()));
							}
							setear();
							txtBusqueda.setText(generarTexto());
						}else {
							JOptionPane.showMessageDialog(null, "Datos invalidos");
						}
					}
				}
			}
		});
		btnAgregar.setBounds(456, 10, 95, 23);
		pnlBusqueda.add(btnAgregar);
		
		cbLogica = new JComboBox<String>();
		cbLogica.setModel(new DefaultComboBoxModel(new String[] {"Ingrese la combinacion logica", "y", "o"}));
		cbLogica.setBounds(271, 11, 175, 20);
		pnlBusqueda.add(cbLogica);
		
		txtValor = new JTextField();
		txtValor.setBounds(78, 39, 70, 20);
		pnlBusqueda.add(txtValor);
		txtValor.setColumns(10);
		
		txtOperador = new JTextField();
		txtOperador.setBounds(281, 39, 43, 20);
		pnlBusqueda.add(txtOperador);
		txtOperador.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("VALOR");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 39, 70, 20);
		pnlBusqueda.add(lblNewLabel_2);
		
		JLabel lblComparador = new JLabel("COMPARADOR");
		lblComparador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblComparador.setBounds(158, 39, 133, 20);
		pnlBusqueda.add(lblComparador);
		
		txtBusqueda = new JTextPane();
		txtBusqueda.setBounds(10, 70, 541, 62);
		pnlBusqueda.add(txtBusqueda);
		
		chkNegar = new JCheckBox("negar");
		chkNegar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chkNegar.setBounds(10, 10, 86, 23);
		pnlBusqueda.add(chkNegar);
		
	}
	
	public void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		cParametros = 0;
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(11, 200, 564, 243);
		contentPane.add(scroll);
		
		busqueda = new ArrayList<TextoBusqueda>();
		conectores = new ArrayList<String>();
		
		modelo = TableModels.crearModeloPropiedades(modelo);
		table.setModel(modelo);
		Tablas.actualizarTPropiedad(table);
		cargarButtons();
		
		cbLogica.setEnabled(false);
	}
	
	public vVerPropiedades() {
		cargarVentana();
	}
}
