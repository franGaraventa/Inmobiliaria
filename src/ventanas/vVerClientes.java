package ventanas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Filtros.FPersAND;
import Filtros.FPersApell;
import Filtros.FPersCodyTel;
import Filtros.FPersDNI;
import Filtros.FPersEmail;
import Filtros.FPersNOT;
import Filtros.FPersNom;
import Filtros.FPersOR;
import Filtros.FPersona;
import clases.Persona;
import clases.Tablas;
import interfaces.DAOPersona;
import interfaces.DAOPersonaImpl;
import utils.GeneradorTexto;
import utils.TableModels;
import utils.TextoBusqueda;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class vVerClientes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel modelo;
	private JTable table;
	private JButton btnEliminar;
	private JTextField txtValor;
	private int cParametros;
	private FPersona filtro;
	private JComboBox<String> cbLogica;
	private List<TextoBusqueda> busqueda;
	private List<String> conectores;
	private JTextPane txtBusqueda;
	private JCheckBox chkNegar;
	private JComboBox<String> cbBuscador;
	
	public static boolean isEmpty(JTable table) {
        if (table != null && table.getModel() != null) {
            return table.getModel().getRowCount()<=0?true:false;
        }
        return false;
    }
	
	/*METODOS DE FILTRO*/
	private FPersona agregarFiltro(int seleccionado,String valor) {
		switch(seleccionado) {
			/*1 - DNI, 2 - Nombre, 3 - Apellido, 4 - Email, 5 - Codigo de Area-Telefono*/
			case (1): {
				busqueda.add(new TextoBusqueda("DNI: "+valor+" "));
				return new FPersDNI(valor);
			}
			case (2): {
				busqueda.add(new TextoBusqueda("Nombre: "+valor+" "));
				return new FPersNom(valor);
			}
			case (3):{
				busqueda.add(new TextoBusqueda("Apellido: "+valor+" "));
				return new FPersApell(valor);
			}
			case (4): {
				busqueda.add(new TextoBusqueda("Email: "+valor+" "));
				return new FPersEmail(valor);
			}
			case (5): {
				busqueda.add(new TextoBusqueda("Codigo de Area - Telefono: "+valor+" "));
				String[] parts = valor.split("-");
				return new FPersCodyTel(parts[0],parts[1]);
			}
		}
		return null;
	}
	
	private FPersona agregarFiltroLogica(int logica,FPersona filtro2) {
		/*1 - y, 2 - o*/
		switch(logica) {
			case (1): {
				conectores.add("y");
				return new FPersAND(filtro,filtro2);
			}
			case (2): {
				conectores.add("o");
				return new FPersOR(filtro,filtro2);
			}
		}
		return null;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	/*DATOS VALIDOS*/
	private boolean datosValidos(int seleccionado) {
		if (txtValor.getText() != ""){
			if (seleccionado == 5) {
				if (txtValor.getText().contains("-")) {
					String[] parts = txtValor.getText().split("-");
					if (parts.length == 2) {
						return true;
					}else {
						JOptionPane.showMessageDialog(null, "Codigo de area o telefono no ingresado");
						return false;
					}
				}else {
					JOptionPane.showMessageDialog(null, "Debe ingresar codigoDeArea-Telefono");
					return false;
				}
			}else {
				return true;
			}
		}else {
			JOptionPane.showMessageDialog(null, "Ingrese un valor al campo seleccionado");
			return false;
		}
	}
	
	/*RESETEAR CAMPOS*/
	private void resetearCampos() {
		chkNegar.setSelected(false);
		cbBuscador.setSelectedIndex(0);
		cbLogica.setSelectedIndex(0);
		cbLogica.setEnabled(false);
		txtValor.setText("");
		txtBusqueda.setText("");
		filtro = null;
		busqueda.clear();
		conectores.clear();
		cParametros = 0;
	}
	
	/*SETEAR CAMPOS*/
	private void setear() {
		chkNegar.setSelected(false);
		cbBuscador.setSelectedIndex(0);
		cbLogica.setSelectedIndex(0);
		txtValor.setText("");
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
		btnModificar.setBounds(419, 426, 97, 23);
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
		btnEliminar.setBounds(526, 426, 97, 23);
		contentPane.add(btnEliminar);
		habilitarButton(btnEliminar);
		
		JPanel pnlBusqueda = new JPanel();
		pnlBusqueda.setBounds(10, 11, 613, 200);
		contentPane.add(pnlBusqueda);
		pnlBusqueda.setLayout(null);
		
		cbBuscador = new JComboBox<String>();
		cbBuscador.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione un atributo", "DNI", "Nombre", "Apellido", "Email", "Codigo de area - Telefono"}));
		cbBuscador.setBounds(52, 10, 226, 20);
		pnlBusqueda.add(cbBuscador);
		
		chkNegar = new JCheckBox("");
		chkNegar.setBounds(16, 10, 30, 23);
		pnlBusqueda.add(chkNegar);
		
		JButton btnNewButton = new JButton("AGREGAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccionado = cbBuscador.getSelectedIndex();
				if (cParametros == 0) {
					if (seleccionado > 0) {
						if (datosValidos(seleccionado)) {
							if (chkNegar.isSelected()) {
								filtro = agregarFiltro(seleccionado,txtValor.getText());
								filtro = new FPersNOT(filtro);
								busqueda.get(busqueda.size()-1).setNegado(true);
							}else {
								filtro = agregarFiltro(seleccionado,txtValor.getText());
							}
							cbLogica.setEnabled(true);
							setear();
							txtBusqueda.setText(GeneradorTexto.generarTexto(busqueda, conectores));
							cParametros++;
						}
					}
				}else {
					int logica = cbLogica.getSelectedIndex();
					if (datosValidos(seleccionado)) {
						if (GeneradorTexto.comprobarCampos(seleccionado, logica)){
							if (chkNegar.isSelected()) {
								filtro = agregarFiltroLogica(logica,new FPersNOT(agregarFiltro(seleccionado,txtValor.getText())));
								busqueda.get(busqueda.size()-1).setNegado(true);
							}else {
								filtro = agregarFiltroLogica(logica,agregarFiltro(seleccionado,txtValor.getText()));
							}
							setear();
							txtBusqueda.setText(GeneradorTexto.generarTexto(busqueda, conectores));
						}
					}
				}
			}
		});
		btnNewButton.setBounds(478, 9, 125, 23);
		pnlBusqueda.add(btnNewButton);
		
		txtValor = new JTextField();
		txtValor.setBounds(52, 41, 217, 20);
		pnlBusqueda.add(txtValor);
		txtValor.setColumns(10);
		
		txtBusqueda = new JTextPane();
		txtBusqueda.setBounds(16, 72, 587, 83);
		pnlBusqueda.add(txtBusqueda);
		
		JButton btnNewButton_1 = new JButton("BUSCAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPersona ipersona = new DAOPersonaImpl();
				List<Persona> persona = ipersona.getPersonas();
				List<Persona> fpersonas = new ArrayList<Persona>();
				for(Persona p: persona) {
					if (filtro.cumple(p)) {
						fpersonas.add(p);
					}
				}
				Tablas.actualizarTPersona(table, fpersonas);
			}
		});
		btnNewButton_1.setBounds(514, 166, 89, 23);
		pnlBusqueda.add(btnNewButton_1);
		
		JButton btnReestablecer = new JButton("REESTABLECER");
		btnReestablecer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetearCampos();
				Tablas.actualizarTPersona(table);
			}
		});
		btnReestablecer.setBounds(373, 166, 131, 23);
		pnlBusqueda.add(btnReestablecer);
		
		cbLogica = new JComboBox<String>();
		cbLogica.setModel(new DefaultComboBoxModel<String>(new String[] {"Ingrese la combinacion logica", "y", "o"}));
		cbLogica.setBounds(288, 10, 180, 20);
		pnlBusqueda.add(cbLogica);
		cbLogica.setEnabled(false);
	}
	
	public void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 646, 499);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		/*INICIALIZACION*/
		busqueda = new ArrayList<TextoBusqueda>();
		conectores = new ArrayList<String>();
		cParametros = 0;
		filtro = null;
		
		table = new JTable();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table);
		scroll.setBounds(10,222,613,193);
		contentPane.add(scroll);
	}
	
	public vVerClientes() {
		cargarVentana();
		/*CARGO EL MODELO Y LOS DATOS DENTRO DE UNA TABLA*/
		modelo = TableModels.crearModeloPersona(modelo);
		table.setModel(modelo);
		Tablas.actualizarTPersona(table);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(75);
		/*---------------------------------------------------------*/
		definirButtons();
	}
}
