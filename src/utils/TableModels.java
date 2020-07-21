package utils;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TableModels {

	public static DefaultTableModel crearModeloPersona(DefaultTableModel modelo) {
		try {
	        modelo = (new DefaultTableModel(null, new Object[]{"DNI","Nombre","Apellido","Email","Cod Area","Telefono"}){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
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
		return modelo;
	}

	public static DefaultTableModel crearModeloPropiedades(DefaultTableModel modelo) {
		try {
	        modelo = (new DefaultTableModel(null, new Object[]{"ID","Valor","Sup Lote","Sup Cubierta","Informacion"}){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
	                java.lang.Integer.class,
	                java.lang.Double.class,
	                java.lang.Double.class,
	                java.lang.Double.class,
	                java.lang.String.class
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
		return modelo;
	}

	public static DefaultTableModel crearModeloContrato(DefaultTableModel modelo) {
		try {
	        modelo = (new DefaultTableModel(null, new Object[]{"id","Plazo","Fecha Firma","Fecha Inicio","Fecha Finalizacion","Locador","Fecha Maxima de Pago"}){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
	                java.lang.Integer.class,
	                java.lang.Integer.class,
	                java.util.Date.class,
	                java.util.Date.class,
	                java.util.Date.class,
	                java.lang.String.class,
	                java.lang.Integer.class
	            };
	            boolean[] canEdit = new boolean[]{
	                false, false, false, false, false, false, false
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
		return modelo;
	}
	
	public static DefaultTableModel crearModeloPagos(DefaultTableModel modelo) {
		try {
	        modelo = (new DefaultTableModel(null, new Object[]{"Fecha","Monto","Recargo"}){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
	                java.util.Date.class,
	                java.lang.Double.class,
	                java.lang.Double.class
	            };
	            boolean[] canEdit = new boolean[]{
	                false, false, false
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
		return modelo;
	}
	
	public static DefaultTableModel crearModeloPagosVencidos(DefaultTableModel modelo) {
		try {
	        modelo = (new DefaultTableModel(null, new Object[]{"id","Fecha maxima de pago","Dias restantes","Dias excedido"}){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
	                java.lang.Integer.class,
	                java.lang.Integer.class,
	                java.lang.Integer.class,
	                java.lang.Integer.class
	            };
	            boolean[] canEdit = new boolean[]{
	                false, false, false, false
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
		return modelo;
	}
	
	public static DefaultTableModel crearModeloContratosConVencimientoProximo(DefaultTableModel modelo) {
		try {
	        modelo = (new DefaultTableModel(null, new Object[]{"id","Fecha Firma","Fecha Inicio","Fecha Finalizacion","Dias Restantes"}){
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
	                java.lang.Integer.class,
	                java.util.Date.class,
	                java.util.Date.class,
	                java.util.Date.class,
	                java.lang.Integer.class
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
		return modelo;
	}
}
