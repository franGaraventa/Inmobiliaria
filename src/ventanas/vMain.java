package ventanas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class vMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vMain frame = new vMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void definirMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnClientes = new JMenu("Clientes");
		menuBar.add(mnClientes);
		
		JMenuItem mnNuevoCliente = new JMenuItem("Nuevo cliente");
		mnNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vCliente vcliente = new vCliente();
				vcliente.setVisible(true);
			}
		});
		mnClientes.add(mnNuevoCliente);
		
		JMenuItem mnVerClientes = new JMenuItem("Ver clientes");
		mnVerClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vVerClientes vclientes = new vVerClientes();
				vclientes.setVisible(true);
			}
		});
		mnClientes.add(mnVerClientes);
		
		JMenu mnPropiedades = new JMenu("Propiedades");
		menuBar.add(mnPropiedades);
		
		JMenuItem mnNuevaPropiedad = new JMenuItem("Nueva propiedad");
		mnPropiedades.add(mnNuevaPropiedad);
		
		JMenuItem mnVerPropiedades = new JMenuItem("Ver propiedades");
		mnPropiedades.add(mnVerPropiedades);
	}
	
	public vMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 472);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		definirMenu();
	}
}
