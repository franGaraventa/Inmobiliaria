package utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import clases.Contrato;
import clases.Pagos;

public class GeneradorPDF {
	
	private static final String ciudad = "TANDIL";
	private JFileChooser archivo;
	private Contrato contrato;
	private Pagos pago;
	
	public GeneradorPDF(Contrato contrato,Pagos pago) {
		this.contrato = contrato;
		this.pago = pago;
	}
	
	private static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
	
	public void generarPDF() {
		archivo = new JFileChooser();
		int opcion = archivo.showSaveDialog(null);
		if (opcion == archivo.APPROVE_OPTION) {
			try {
				OutputStream texto_salida = new FileOutputStream(archivo.getSelectedFile()); 
				Document documento = new Document();
				PdfWriter.getInstance(documento, texto_salida);
				documento.open();
				
				/*TITULO*/
				Paragraph titulo = new Paragraph();
				titulo.add("RECIBO DE ALQUILER");
				titulo.setAlignment(Element.ALIGN_CENTER);
				documento.add(titulo);
				/*----------------------------------------------------*/
				/*FECHA RECIBO*/
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				Paragraph fecha_recibo = new Paragraph();
				fecha_recibo.setAlignment(Element.ALIGN_RIGHT);
				fecha_recibo.add(ciudad+","+Fechas.getDia(calendar.get(Calendar.DAY_OF_WEEK))+", "+calendar.get(Calendar.DAY_OF_MONTH)+" de "+Fechas.getMes(calendar.get(Calendar.MONTH))+" del "+calendar.get(Calendar.YEAR));
				documento.add(fecha_recibo);
				/*-----------------------------------------------------*/
				/*PERSONA*/
				Paragraph persona = new Paragraph();
				persona.add("Recibi de: "+contrato.getLocatario().getNombre().toUpperCase()+","+contrato.getLocatario().getApellido().toUpperCase());
				documento.add(persona);
				/*-----------------------------------------------------*/
				/*VALOR EN PESOS ESCRITO*/
				double num = formatearDecimales(contrato.getPrecio().getPrecio(),2);
				int p_ent= (int)num;
				double p_dec= num - p_ent;
				String valor = ConvertirNumero.cantidadConLetra(String.valueOf(num));
				String pesos_es = "";
				if (p_dec != 0) {
					String valor2 = valor +"con "+ConvertirNumero.cantidadConLetra(String.valueOf(p_dec*100));
					pesos_es = (valor2.toUpperCase());
				}else {
					pesos_es = (valor.toUpperCase());
				}
				Paragraph pesos_escrito = new Paragraph();
				pesos_escrito.add("La cantidad de PESOS: "+pesos_es.toUpperCase());
				documento.add(pesos_escrito);
				/*-----------------------------------------------------*/
				/*UBICACION*/
				Paragraph ubicacion = new Paragraph();
				ubicacion.add("en conceptos del alquiler que se encuentra ubicado en "+contrato.getLocacion().getUbicacion().getDireccion().toUpperCase());
				documento.add(ubicacion);
				/*-----------------------------------------------------*/
				Paragraph cfm = new Paragraph();
				String fecha = contrato.getFechaMaxPago() +"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
				cfm.add("de la ciudad de "+contrato.getLocacion().getUbicacion().getCiudad().toUpperCase()+". Correspondiente al mes "+Fechas.getMes(calendar.get(Calendar.MONTH))+" que vence el "+fecha);
				documento.add(cfm);
				/*-----------------------------------------------------*/
				/*ALQUILER*/
				Paragraph alquiler = new Paragraph();
				alquiler.add("Alquiler $: "+String.valueOf(contrato.getPrecio().getPrecio()));
				documento.add(alquiler);
				/*-----------------------------------------------------*/
				/*EXPENSAS*/
				Paragraph expensas = new Paragraph();
				expensas.add("Expensas $: "+String.valueOf(contrato.getPrecio().getExpensas()));
				documento.add(expensas);
				/*-----------------------------------------------------*/
				/*PRECIO FINAL*/
				Paragraph precio_final = new Paragraph();
				precio_final.add("Total $: "+pago.getMonto()+"         Recargo $: "+pago.getRecargo());
				documento.add(precio_final);
				/*-----------------------------------------------------*/
				documento.close();
				texto_salida.close();
			}catch(Exception e) {
			}
		}
	}
	
}
