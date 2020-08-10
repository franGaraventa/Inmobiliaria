package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import clases.Cliente;
import clases.Persona;
import interfaces.DAOPersona;
import interfaces.DAOPersonaImpl;
import utils.Tablas;
import utils.ValidadorCampos;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private Persona persona;
	
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
		txtDireccion.setEnabled(enabled);
	}
	
	private boolean camposValidos() {
		if (ValidadorCampos.campoNumeros(txtDNI,9) && ValidadorCampos.campoLetras(txtNombre,15) && ValidadorCampos.campoLetras(txtApellido,15) && 
				ValidadorCampos.campoVacio(txtEmail,40) && ValidadorCampos.campoNumeros(txtCodArea,4) && ValidadorCampos.campoNumeros(txtTelefono,8) 
				&& ValidadorCampos.campoVacio(txtDireccion,100)) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean camposModValidos() {
		if (ValidadorCampos.campoLetras(txtNombre,15) && ValidadorCampos.campoLetras(txtApellido,15) && ValidadorCampos.campoVacio(txtEmail,40) && 
				ValidadorCampos.campoNumeros(txtCodArea,4) && ValidadorCampos.campoNumeros(txtTelefono,8) && ValidadorCampos.campoVacio(txtDireccion,100)) {
			return true;
		}else {
			return false;
		}
	}
	
	private void definirButtons(){
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (camposValidos()) {
					DAOPersona ipersona = new DAOPersonaImpl();
					int id = ipersona.getUltimoIndice()+1;
					Persona p = new Cliente(id,txtDNI.getText(),txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCodArea.getText(),txtTelefono.getText(),txtDireccion.getText());
					if (!ipersona.existe(txtDNI.getText())) {
						ipersona.guardar(p);
						JOptionPane.showMessageDialog(null,
						        "Cliente agregado correctamente",
						        "Cliente agregado",
						        JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}else {
						txtDNI.setBorder(ValidadorCampos.getRBorder());
						txtDNI.setBorder(ValidadorCampos.getRBorder());
						JOptionPane.showMessageDialog(null,
						        "El DNI ya fue ingresado",
						        "DNI duplicado",
						        JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		btnGuardar.setBounds(208, 242, 110, 23);
		contentPane.add(btnGuardar);
		
		btnModificar = new JButton("MODIFICAR");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPersona ipersona = new DAOPersonaImpl();
				Persona p = new Cliente(persona.getId(),txtDNI.getText(),txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCodArea.getText(),txtTelefono.getText(),txtDireccion.getText());
				if (camposModValidos()) {
					ipersona.modificar(p);
					if (table != null)
						Tablas.actualizarTClientes(table);
					JOptionPane.showMessageDialog(null,
					        "Cliente modificado correctamente",
					        "Cliente modificado",
					        JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
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
		txtDireccion.setText(p.getDireccion());
	}
	
	/*CONTRUCTOR PARA NUEVO CLIENTE*/
	public vCliente() {
		definirVentana();
		setIconImage(Toolkit.getDefaultToolkit().getImage(vCliente.class.getResource("/Imagenes/icon_nuevoCliente.png")));
		super.setTitle("NUEVO CLIENTE");
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(true);
		btnModificar.setVisible(false);
	}
	
	/*CONTRUCTOR PARA MODIFICAR CLIENTE*/
	public vCliente(Persona p,JTable tabla) {
		persona = p;
		super.setTitle("INFORMACION: "+persona.getNombre()+","+persona.getApellido());
		definirVentana();
		cargarText(p);
		txtDNI.setEnabled(false);
		table = tabla;
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(false);
		btnModificar.setVisible(true);
	}
	
	public vCliente(Persona p,boolean visible) {
		definirVentana();
		setIconImage(Toolkit.getDefaultToolkit().getImage(vCliente.class.getResource("/Imagenes/icon_locatarioContrato.png")));
		super.setTitle("CLIENTE: "+p.getNombre()+","+p.getApellido());
		cargarText(p);
		deshabilitarTexts(visible);
		table = null;
		/*ACTIVO LOS BOTONES NECESARIOS*/
		btnGuardar.setVisible(visible);
		btnModificar.setVisible(visible);
	}
}
