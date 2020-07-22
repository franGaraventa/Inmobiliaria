package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import clases.Persona;
import interfaces.DAOPersona;
import interfaces.DAOPersonaImpl;
import utils.Tablas;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class vCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtEmail;
	private JTextField txtCodArea;
	private JTextField txtTelefono;
	private JButton btnGuardar;
	private JButton btnModificar;
	private char tipo_persona;
	private JCheckBox chkCliente;
	
	private JTable table;
	private JTextField txtDireccion;

	private void definirLabels() {
		JLabel lblNewLabel = new JLabel("DNI");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 19, 45, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombre.setBounds(10, 50, 87, 20);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblApellido.setBounds(10, 81, 100, 20);
		contentPane.add(lblApellido);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEmail.setBounds(10, 112, 62, 20);
		contentPane.add(lblEmail);
		
		JLabel lblCodigoDeArea = new JLabel("CODIGO DE AREA");
		lblCodigoDeArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCodigoDeArea.setBounds(10, 143, 158, 20);
		contentPane.add(lblCodigoDeArea);
		
		JLabel lblTelefono = new JLabel("TELEFONO");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTelefono.setBounds(10, 174, 110, 20);
		contentPane.add(lblTelefono);
		
		JLabel lblDireccion = new JLabel("DIRECCION");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDireccion.setBounds(10, 205, 110, 20);
		contentPane.add(lblDireccion);
	}
	
	private void definirTextFields() {
		txtDNI = new JTextField();
		txtDNI.setBounds(65, 19, 158, 20);
		contentPane.add(txtDNI);
		txtDNI.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(104, 50, 214, 20);
		contentPane.add(txtNombre);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(104, 81, 214, 20);
		contentPane.add(txtApellido);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(82, 112, 236, 20);
		contentPane.add(txtEmail);
		
		txtCodArea = new JTextField();
		txtCodArea.setColumns(10);
		txtCodArea.setBounds(174, 143, 78, 20);
		contentPane.add(txtCodArea);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(130, 174, 188, 20);
		contentPane.add(txtTelefono);
		
		chkCliente = new JCheckBox("CLIENTE");
		chkCliente.setBounds(10, 242, 72, 23);
		contentPane.add(chkCliente);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(130, 205, 188, 20);
		contentPane.add(txtDireccion);
	}
	
	private void deshabilitarTexts(boolean enabled) {
		txtDNI.setEnabled(enabled);
		txtNombre.setEnabled(enabled);
		txtApellido.setEnabled(enabled);
		txtEmail.setEnabled(enabled);
		txtCodArea.setEnabled(enabled);
		txtTelefono.setEnabled(enabled);
		chkCliente.setEnabled(enabled);
		txtDireccion.setEnabled(enabled);
	}
	
	private char campoCliente() {
		if (chkCliente.isSelected()) {
			return 'c';
		}else {
			return 'l';
		}
	}
	
	private void campoCliente(Persona p) {
		if (p.getTipo() == 'c') {
			chkCliente.setSelected(true);
		}else {
			chkCliente.setSelected(false);
		}
	}
	
	private void definirButtons(){
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DAOPersona ipersona = new DAOPersonaImpl();
				Persona p = new Persona(txtDNI.getText(),txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCodArea.getText(),txtTelefono.getText(),tipo_persona,txtDireccion.getText());
				ipersona.guardar(p);
				limpiarCampos();
				dispose();
			}
		});
		btnGuardar.setBounds(208, 242, 110, 23);
		contentPane.add(btnGuardar);
		
		btnModificar = new JButton("MODIFICAR");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPersona ipersona = new DAOPersonaImpl();
				Persona p = new Persona(txtDNI.getText(),txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCodArea.getText(),txtTelefono.getText(),campoCliente(),txtDireccion.getText());
				ipersona.modificar(p);
				if (table != null)
					Tablas.actualizarTPersona(table);
				limpiarCampos();
				dispose();
			}
		});
		btnModificar.setBounds(208, 242, 110, 23);
		contentPane.add(btnModificar);
	
	}
	
	private void definirVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 344, 315);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		definirLabels();
		definirTextFields();
		definirButtons();
	}
	
	private void cargarText(Persona p) {
		txtDNI.setText(p.getDni());
		txtNombre.setText(p.getNombre());
		txtApellido.setText(p.getApellido());
		txtEmail.setText(p.getEmail());
		txtCodArea.setText(p.getCodArea());
		txtTelefono.setText(p.getTelefono());
		campoCliente(p);
	}
	
	private void limpiarCampos() {
		txtCodArea.setText(null);
		txtTelefono.setText(null);
		txtEmail.setText(null);
		txtApellido.setText(null);
		txtNombre.setText(null);
		txtDNI.setText(null);
		txtDireccion.setText(null);
		chkCliente.setSelected(false);
	}
	
	/*CONTRUCTOR PARA NUEVO CLIENTE/LOCADOR*/
	public vCliente(char tipo) {
		definirVentana();
		tipo_persona = tipo;
		if (tipo_persona == 'l') {
			chkCliente.setEnabled(false);
		}else {
			if (tipo_persona == 'c') {
				chkCliente.setEnabled(false);
				chkCliente.setSelected(true);
			}
		}
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(true);
		btnModificar.setVisible(false);
	}
	
	/*CONTRUCTOR PARA MODIFICAR CLIENTE/LOCADOR*/
	public vCliente(Persona p,JTable tabla) {
		definirVentana();
		cargarText(p);
		txtDNI.setEnabled(false);
		chkCliente.setEnabled(false);
		table = tabla;
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(false);
		btnModificar.setVisible(true);
	}
	
	public vCliente(Persona p) {
		definirVentana();
		cargarText(p);
		txtDNI.setEnabled(false);
		chkCliente.setEnabled(false);
		table = null;
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(false);
		btnModificar.setVisible(true);
	}
	
	public vCliente(Persona p,boolean visible) {
		definirVentana();
		cargarText(p);
		deshabilitarTexts(visible);
		table = null;
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(visible);
		btnModificar.setVisible(visible);
	}
}
