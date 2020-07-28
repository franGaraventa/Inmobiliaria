package ventanas;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import clases.Locador;
import clases.Persona;
import interfaces.DAOPersona;
import interfaces.DAOPersonaImpl;
import utils.ValidadorCampos;

import java.awt.event.ActionListener;
import java.util.Objects;
import java.awt.event.ActionEvent;

public class vLocador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtEmail;
	private JTextField txtCodArea;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtNCuenta;
	private JTextField txtBanco;
	private JTextField txtTipo;
	private JTextField txtDoc;
	private JTextField txtVerificador;
	private JTextField txtCBU;
	private JPanel panel;
	
	private void definirLabels() {
		contentPane.setLayout(null);
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
		
		JLabel lblCuentaBancaria = new JLabel("CUENTA BANCARIA");
		lblCuentaBancaria.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCuentaBancaria.setBounds(10, 236, 176, 20);
		contentPane.add(lblCuentaBancaria);
		
		JLabel lblNDeCuenta = new JLabel("N\u00BA DE CUENTA");
		lblNDeCuenta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNDeCuenta.setBounds(10, 11, 151, 20);
		panel.add(lblNDeCuenta);
		
		JLabel lblBanco = new JLabel("BANCO");
		lblBanco.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBanco.setBounds(10, 42, 87, 20);
		panel.add(lblBanco);
		
		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCuit.setBounds(10, 68, 61, 20);
		panel.add(lblCuit);
		
		JLabel lblCbu = new JLabel("CBU");
		lblCbu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCbu.setBounds(10, 97, 50, 20);
		panel.add(lblCbu);
	}
	
	private boolean validarCuit(String cuit) {
	    //Controlamos si son 11 n�meros los que quedaron, si no es el caso, ya devuelve falso
	    if (cuit.length() != 11){
	        return false;
	    }
	    //Convertimos la cadena que qued� en una matriz de caracteres
	    char[] cuitArray = cuit.toCharArray();
	    //Inicializamos una matriz por la cual se multiplicar�n cada uno de los d�gitos
	    Integer[] serie = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
	    //Creamos una variable auxiliar donde guardaremos los resultados del c�lculo del n�mero validador
	    Integer aux = 0;
	    //Recorremos las matrices de forma simult�nea, sumando los productos de la serie por el n�mero en la misma posici�n
	    for (int i=0; i<10; i++){
	        aux += Character.getNumericValue(cuitArray[i]) * serie[i];
	    }
	    //Hacemos como se especifica: 11 menos el resto de de la divisi�n de la suma de productos anterior por 11
	    aux = 11 - (aux % 11);
	    //Si el resultado anterior es 11 el c�digo es 0
	    if (aux == 11){
	        aux = 0;
	    }
	    //Si el resultado anterior es 10 el c�digo no tiene que validar, cosa que de todas formas pasa
	    //en la siguiente comparaci�n.
	    //Devuelve verdadero si son iguales, falso si no lo son
	    return Objects.equals(Character.getNumericValue(cuitArray[10]), aux);
	}
	
	private boolean validarCuentaBancaria() {
		if(ValidadorCampos.campoNumeros(txtNCuenta, 20) && ValidadorCampos.campoVacio(txtBanco, 50) && ValidadorCampos.campoNumeros(txtCBU, 22) &&
		   ValidadorCampos.campoNumeros(txtTipo, 2) && ValidadorCampos.campoNumeros(txtDoc, 8) && ValidadorCampos.campoNumeros(txtTipo, 1)) {
			String cuit = txtTipo.getText()+txtDoc.getText()+txtDoc.getText();
			if (validarCuit(cuit)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	private boolean camposValidos() {
		if(ValidadorCampos.campoNumeros(txtDNI,9) && ValidadorCampos.campoLetras(txtNombre,15) && ValidadorCampos.campoLetras(txtApellido,15) && 
			ValidadorCampos.campoVacio(txtEmail,40) && ValidadorCampos.campoNumeros(txtCodArea,4) && ValidadorCampos.campoNumeros(txtTelefono,8) 
			&& ValidadorCampos.campoVacio(txtDireccion,100) && validarCuentaBancaria()) {
			return true;
		}else {
			return false;
		}
	}
	
	private void definirButtons() {
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (camposValidos()) {
					DAOPersona ipersona = new DAOPersonaImpl();
					int id = ipersona.getUltimoIndice()+1;
					Persona p = new Locador(id,txtDNI.getText(),txtNombre.getText(),txtApellido.getText(),txtEmail.getText(),txtCodArea.getText(),txtTelefono.getText(),txtDireccion.getText(),
							txtNCuenta.getText(),txtBanco.getText(),txtTipo.getText(),txtDoc.getText(),txtVerificador.getText(),txtCBU.getText());
					ipersona.guardar(p);
					dispose();
				}
			}
		});
		btnGuardar.setBounds(258, 400, 100, 23);
		contentPane.add(btnGuardar);
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
		
		panel = new JPanel();
		panel.setBounds(10, 261, 348, 128);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtNCuenta = new JTextField();
		txtNCuenta.setColumns(10);
		txtNCuenta.setBounds(148, 11, 176, 20);
		panel.add(txtNCuenta);
		
		txtBanco = new JTextField();
		txtBanco.setColumns(10);
		txtBanco.setBounds(99, 42, 225, 20);
		panel.add(txtBanco);
		
		txtTipo = new JTextField();
		txtTipo.setColumns(10);
		txtTipo.setBounds(66, 71, 41, 20);
		panel.add(txtTipo);
		
		txtDoc = new JTextField();
		txtDoc.setColumns(10);
		txtDoc.setBounds(117, 71, 128, 20);
		panel.add(txtDoc);
		
		txtVerificador = new JTextField();
		txtVerificador.setColumns(10);
		txtVerificador.setBounds(255, 71, 41, 20);
		panel.add(txtVerificador);
		
		txtCBU = new JTextField();
		txtCBU.setColumns(10);
		txtCBU.setBounds(66, 97, 258, 20);
		panel.add(txtCBU);
	
	}
	
	private void definirVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 383, 473);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		definirTextFields();
		definirLabels();
		definirButtons();
	}
	
	public vLocador() {
		definirVentana();
	}
}
