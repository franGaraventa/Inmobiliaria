package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import clases.DAOImagen;
import clases.DAOImagenImpl;
import clases.DAOPropiedad;
import clases.DAOPropiedadImpl;
import clases.Imagen;
import clases.ImagenPropiedad;
import clases.Propiedad;
import clases.Tablas;
import clases.Ubicacion;
import utils.FileTypeFilter;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class vPropiedad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtValor;
	private JTextField txtSupLote;
	private JTextField txtSupCub;
	private JTextField txtCodPostal;
	private JTextField txtDistrito;
	private JTextField txtCiudad;
	private JTextField txtDireccion;
	private JPanel pnlUbicacion;
	private JLabel lblImagenes;
	private JTextPane txtInformacion;
	private JCheckBox chkAmoblado;
	private JCheckBox chkAlquilado;

	private List<ImagenPropiedad> list_img;
	private List<Imagen> list_imagenes;
	private String path;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnCargarImg;
	private boolean editable;
	private Propiedad p;
	private JTable table;
	
	/*SEPARAR PATH*/
	private String separarPath(String path) {
		String[] parts = path.split("\\\\");
		return parts[parts.length-1];
	}
	
	/*CARGAR IMAGEN PREDETERMINADA*/
	private void cargarImagenPredeterminada() {
		ImagenPropiedad img = new ImagenPropiedad("white_image.png",new ImageIcon(getClass().getResource("/imagenes/white_image.png")));
		ImageIcon icono = new ImageIcon(img.getImagen().getImage().getScaledInstance(lblImagenes.getWidth(),lblImagenes.getHeight(), Image.SCALE_DEFAULT));
		lblImagenes.setIcon(icono);
	}
	
	/*MOSTRAR IMAGENES*/
	private void mostrarElemento(List<ImagenPropiedad> list_img,int i) {
		ImageIcon icono = new ImageIcon(list_img.get(i).getImagen().getImage().getScaledInstance(lblImagenes.getWidth(),lblImagenes.getHeight(), Image.SCALE_DEFAULT));
		lblImagenes.setIcon(icono);
		path = list_img.get(i).getPath();
	}
	
	/*POSICION IMAGEN*/
	private int getPosicionImagen() {
		if (list_img == null) {
			return -1;
		}else {
			for (int i = 0; i < list_img.size();i++) {
				if (list_img.get(i).getPath().compareTo(path) == 0) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private boolean existeImagen(String path) {
		if (list_img == null) {
			return false;
		}else {
			for (int i = 0; i < list_img.size();i++) {
				if (list_img.get(i).getPath().compareTo(path) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*INSERTAR IMAGENES EN PROPIEDAD de ImagenPropiedad -> Imagen*/
	/*---------------------------------------------------------------------------------------------------*/
	
	public static BufferedImage getBufferedImage(Image img){
	    if (img instanceof BufferedImage){
	       return (BufferedImage) img;
	    }
	    BufferedImage bimage = new BufferedImage(img.getWidth(null),img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    return bimage;
	}
	
	private void insertar(Propiedad p) throws Exception {
		DAOImagen iimagen = new DAOImagenImpl();
		int ultimo_indice = iimagen.getUltimoIndice()+1;
		if (!list_img.isEmpty()) {
			list_imagenes = new ArrayList<Imagen>();
			for (ImagenPropiedad image: list_img) {
				BufferedImage bi = getBufferedImage(image.getImagen().getImage());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				String[] parts = image.getPath().split("\\.");
				ImageIO.write(bi, parts[1], baos);
				byte[] byteArray= baos.toByteArray();
				SerialBlob blob = new SerialBlob(byteArray);
				if (!iimagen.existeImagen(image.getPath(), p)) {
					list_imagenes.add(new Imagen(ultimo_indice,image.getPath(),blob,p));
					ultimo_indice++;
				}else {
					int id = iimagen.idImagen(image.getPath(), p);
					list_imagenes.add(new Imagen(id,image.getPath(),blob,p));
				}
			}
		}
	}
	
	private void cargarImg() {
		if (list_imagenes.size() != 0) {
			list_img = new ArrayList<ImagenPropiedad>();
			for (Imagen img: list_imagenes) {
				try {
					SerialBlob blob = (SerialBlob) img.getImagen();
					InputStream input = blob.getBinaryStream();
					byte[] imagen = new byte[new Long(blob.length()).intValue()];
					input.read(imagen);
					InputStream inputStream = new ByteArrayInputStream(imagen);
					BufferedImage image = ImageIO.read(inputStream);
					ImageIcon imageIcon = new ImageIcon(image);
					ImagenPropiedad imgProp = new ImagenPropiedad(img.getId(),img.getPathImg(),imageIcon);
					list_img.add(imgProp);
				}catch (SQLException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!list_img.isEmpty()) {
				mostrarElemento(list_img,0);
			}else {
				cargarImagenPredeterminada();
			}
		}else {
			list_img = new ArrayList<ImagenPropiedad>();
			cargarImagenPredeterminada();
		}
		
	}
	
	/*---------------------------------------------------------------------------------------------------*/
	
	public void cargarLabels() {
		JLabel lblNewLabel = new JLabel("VALOR");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 21, 65, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblSuperficieLote = new JLabel("SUPERFICIE LOTE");
		lblSuperficieLote.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSuperficieLote.setBounds(10, 49, 154, 22);
		contentPane.add(lblSuperficieLote);
		
		JLabel lblSuperficieCubierta = new JLabel("SUPERFICIE CUBIERTA");
		lblSuperficieCubierta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSuperficieCubierta.setBounds(10, 79, 197, 22);
		contentPane.add(lblSuperficieCubierta);
		
		JLabel lblInformacion = new JLabel("INFORMACION");
		lblInformacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInformacion.setBounds(10, 247, 131, 22);
		contentPane.add(lblInformacion);
		
		JLabel lblCodigoPostal = new JLabel("CODIGO POSTAL");
		lblCodigoPostal.setBounds(10, 11, 146, 22);
		pnlUbicacion.add(lblCodigoPostal);
		lblCodigoPostal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblDistrito = new JLabel("DISTRITO");
		lblDistrito.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDistrito.setBounds(10, 42, 99, 22);
		pnlUbicacion.add(lblDistrito);
		
		JLabel lblCiudad = new JLabel("CIUDAD");
		lblCiudad.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCiudad.setBounds(10, 71, 77, 22);
		pnlUbicacion.add(lblCiudad);
		
		JLabel lblDireccion = new JLabel("DIRECCION");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDireccion.setBounds(10, 102, 109, 22);
		pnlUbicacion.add(lblDireccion);
		
		lblImagenes = new JLabel("");
		lblImagenes.setBounds(479, 28, 333, 344);
		contentPane.add(lblImagenes);
	}
	
	public void cargarCheckBoxs() {
		chkAmoblado = new JCheckBox("AMOBLADO");
		chkAmoblado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chkAmoblado.setBounds(10, 444, 131, 23);
		contentPane.add(chkAmoblado);
		
		chkAlquilado = new JCheckBox("ALQUILADO");
		chkAlquilado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chkAlquilado.setBounds(143, 444, 131, 23);
		contentPane.add(chkAlquilado);
	}
	
	public void cargarButtons() {
		JButton btnAnt = new JButton("<");
		btnAnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = getPosicionImagen();
				if (i == -1) {
					System.out.println("No hay imagenes cargadas");
				}else {
					if (i-1 >= 0) {
						mostrarElemento(list_img,i-1);
					}
				}
			}
		});
		btnAnt.setBounds(505, 386, 47, 23);
		contentPane.add(btnAnt);
		
		JButton btnSig = new JButton(">");
		btnSig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = getPosicionImagen();
				if (i == -1) {
					System.out.println("No hay imagenes cargadas");
				}else {
					if (i+1 < list_img.size()) {
						mostrarElemento(list_img,i+1);
					}
				}
			}
		});
		btnSig.setBounds(739, 386, 47, 23);
		contentPane.add(btnSig);
		
		btnCargarImg = new JButton("CARGAR IMAGENES");
		btnCargarImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jc = new JFileChooser();
				jc.setDialogTitle("Seleccione una imagen");
				jc.setMultiSelectionEnabled(true);
				jc.setFileFilter(new FileTypeFilter(".jpg","JPG"));
				jc.setFileFilter(new FileTypeFilter(".jpeg","JPEG"));
				jc.setFileFilter(new FileTypeFilter(".gif","GIF"));
				jc.setFileFilter(new FileTypeFilter(".png","PNG"));
				if (jc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
					File [] imagenes = jc.getSelectedFiles();
					if (imagenes.length != 0) {
						if (list_img == null) {
							list_img = new ArrayList<ImagenPropiedad>();
						}
					}
					for (File f: imagenes) {
						ImagenPropiedad img = new ImagenPropiedad(separarPath(f.getAbsolutePath()),new ImageIcon(f.getAbsolutePath()));
						if (!existeImagen(img.getPath())) {
							list_img.add(img);
						}
					}
					habilitarBtn();
					mostrarElemento(list_img,0);
				}
			}
		});
		btnCargarImg.setBounds(562, 386, 167, 23);
		contentPane.add(btnCargarImg);
		
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DAOPropiedad ipropiedad = new DAOPropiedadImpl();
				double valor = Double.valueOf(txtValor.getText());
				double supLote = Double.valueOf(txtSupLote.getText());
				double supCub = Double.valueOf(txtSupCub.getText());
				int id = 0;
				if (!editable) {
					id = ipropiedad.getUltimoIndice()+1;
				}else {
					id = p.getId();
				}
				Propiedad p = new Propiedad(id,valor,supLote,supCub,txtInformacion.getText(),chkAmoblado.isSelected(),chkAlquilado.isSelected());
				Ubicacion u = new Ubicacion(id,txtCodPostal.getText(),txtDistrito.getText(),txtCiudad.getText(),txtDireccion.getText());
				p.setUbicacion(u);
				u.setPropiedad(p);
				try {
					insertar(p);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				p.setImagenes(list_imagenes);
				if (!editable) {
					ipropiedad.agregar(p);
					dispose();
				}else {
					ipropiedad.modificar(p);
					Tablas.actualizarTPropiedad(table);
					dispose();
				}
			}
		});
		btnGuardar.setBounds(700, 440, 112, 23);
		contentPane.add(btnGuardar);
		
		btnModificar = new JButton("MODIFICAR");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGuardar.setVisible(true);
				btnCargarImg.setVisible(true);
				btnModificar.setVisible(false);
				habilitarCampos(true);
				habilitarBtn();
			}
		});
		btnModificar.setBounds(700, 440, 112, 23);
		contentPane.add(btnModificar);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				if (!editable) {
					list_img.remove(getPosicionImagen());
					habilitarBtn();
				}else {
					DAOImagen iimagen = new DAOImagenImpl();
					if (iimagen.existeImagen(path, p)) {
						iimagen.eliminarImagen(path, p);
						list_img.remove(getPosicionImagen());
						habilitarBtn();
					}else {
						list_img.remove(getPosicionImagen());
						habilitarBtn();
					}
				}
				if (list_img.size() > 0) {
					mostrarElemento(list_img,0);
				}else {
					cargarImagenPredeterminada();
				}
			}
		});
		btnEliminar.setBounds(562, 409, 167, 23);
		contentPane.add(btnEliminar);
	}
	
	public void cargarTextBox() {
		txtCodPostal = new JTextField();
		txtCodPostal.setColumns(10);
		txtCodPostal.setBounds(166, 13, 99, 20);
		pnlUbicacion.add(txtCodPostal);
		
		txtDistrito = new JTextField();
		txtDistrito.setColumns(10);
		txtDistrito.setBounds(105, 42, 296, 20);
		pnlUbicacion.add(txtDistrito);
		
		txtCiudad = new JTextField();
		txtCiudad.setColumns(10);
		txtCiudad.setBounds(99, 73, 166, 20);
		pnlUbicacion.add(txtCiudad);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(119, 102, 282, 20);
		pnlUbicacion.add(txtDireccion);
		
		txtValor = new JTextField();
		txtValor.setBounds(85, 21, 136, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);
		
		txtSupLote = new JTextField();
		txtSupLote.setColumns(10);
		txtSupLote.setBounds(174, 49, 100, 20);
		contentPane.add(txtSupLote);
		
		txtSupCub = new JTextField();
		txtSupCub.setColumns(10);
		txtSupCub.setBounds(211, 79, 102, 20);
		contentPane.add(txtSupCub);
		
		txtInformacion = new JTextPane();
		txtInformacion.setBounds(10, 280, 404, 149);
		contentPane.add(txtInformacion);
		
	}
	
	public void cargarVentana() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		pnlUbicacion = new JPanel();
		pnlUbicacion.setBounds(0, 112, 411, 133);
		contentPane.add(pnlUbicacion);
		pnlUbicacion.setLayout(null);
	}
	
	private void cargarDatos() {
		txtValor.setText(String.valueOf(p.getValor()));
		txtSupLote.setText(String.valueOf(p.getSupLote()));
		txtSupCub.setText(String.valueOf(p.getSupCubierta()));
		txtCodPostal.setText(p.getUbicacion().getCodPostal());
		txtDistrito.setText(p.getUbicacion().getDistrito());
		txtCiudad.setText(p.getUbicacion().getCiudad());
		txtDireccion.setText(p.getUbicacion().getDireccion());
		txtInformacion.setText(p.getInformacion());
		chkAmoblado.setSelected(p.isAmoblado());
		chkAlquilado.setSelected(p.isAlquilado());
		DAOImagen iimagen = new DAOImagenImpl();
		list_imagenes = iimagen.getImagenes(p);
		cargarImg();
	}
	
	private void habilitarCampos(boolean enabled) {
		txtValor.setEnabled(enabled);
		txtSupLote.setEnabled(enabled);
		txtSupCub.setEnabled(enabled);
		txtCodPostal.setEnabled(enabled);
		txtDistrito.setEnabled(enabled);
		txtCiudad.setEnabled(enabled);
		txtDireccion.setEnabled(enabled);
		txtInformacion.setEnabled(enabled);
		chkAmoblado.setEnabled(enabled);
		chkAlquilado.setEnabled(enabled);
		btnEliminar.setEnabled(enabled);
	}
	
	public void habilitarBtn() {
		if (!editable) {
			btnEliminar.setVisible(false);
		}else {
			if ((list_img == null)) {
				btnEliminar.setVisible(false);
			}else if ((list_img.size() > 0)) {
				btnEliminar.setVisible(true);
			}else {
				btnEliminar.setVisible(false);
			}
		}
	}
	
	public vPropiedad() {
		this.editable = false;
		cargarVentana();
		cargarLabels();
		cargarCheckBoxs();
		cargarButtons();
		cargarTextBox();
		cargarImagenPredeterminada();
		habilitarBtn();
		btnModificar.setVisible(false);
	}
	
	public vPropiedad(Propiedad p,JTable tabla) {
		this.p = p;
		this.editable = true;
		this.table = tabla;
		cargarVentana();
		cargarLabels();
		cargarCheckBoxs();
		cargarButtons();
		cargarTextBox();
		cargarDatos();
		habilitarCampos(false);
		habilitarBtn();
		btnGuardar.setVisible(false);
		btnCargarImg.setVisible(false);
	}
}
