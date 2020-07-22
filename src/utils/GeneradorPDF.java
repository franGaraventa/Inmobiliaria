package utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
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
	
	public GeneradorPDF(Contrato contrato) {
		this.contrato = contrato;
	}
	
	private static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}
	
	public void generarPDF() {
		archivo = new JFileChooser();
		int opcion = archivo.showSaveDialog(null);
		if (opcion == JFileChooser.APPROVE_OPTION) {
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
	
	public void generarPDFContrato(String dni_locador,String domicilio_locador) {
		archivo = new JFileChooser();
		int opcion = archivo.showSaveDialog(null);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			try {
				OutputStream texto_salida = new FileOutputStream(archivo.getSelectedFile()); 
				Document documento = new Document();
				PdfWriter.getInstance(documento, texto_salida);
				documento.open();
				/*DATOS CONTRATO*/
				/*TITULO*/
				Paragraph titulo = new Paragraph();
				titulo.add("CONTRATO DE LOCACION");
				titulo.setAlignment(Element.ALIGN_CENTER);
				documento.add(titulo);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*PRIMER PARRAFO*/
				Paragraph parrafo_1 = new Paragraph();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(contrato.getFechaFirma());
				String apellidoynombre = contrato.getLocatario().getApellido()+","+contrato.getLocatario().getNombre();
				parrafo_1.add("En la ciudad de "+ciudad+" a los "+calendar.get(Calendar.DAY_OF_MONTH)+" del mes de "+Fechas.getMes(calendar.get(Calendar.MONTH))+" de "+
							   calendar.get(Calendar.YEAR)+" entre "+contrato.getLocador()+" con DNI Nº "+dni_locador+", con domicilio en la calle "+domicilio_locador+
							   " por una parte, en lo sucesivo denominado/a como 'LOCADOR/A' por una parte, y por la otra "+apellidoynombre+" DNI Nº "+contrato.getLocatario().getDni()+
							   ", con domicilio en el inmueble locado. En adelante denominado como 'LOCATARIO/A', convienen en celebrar el presente contrato de LOCACION de vivienda,"
							   + "sujeto a las clausulas siguientes y a las disposiciones del Codigo Civil y Comercial ------------------------------------------------------------");
				documento.add(parrafo_1);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 1*/
				Paragraph clausula_1 = new Paragraph();
				clausula_1.add("PRIMERA (OBJETO/DESTINO): EL/LA 'LOCADOR/A' cede en locacion al 'LOCATARIO/A, que acepta ocupar en tal caracter, el inmueble ubicado en la calle "+
								contrato.getLocacion().getUbicacion().getDireccion()+" de la ciudad de "+contrato.getLocacion().getUbicacion().getCiudad()+" . EL LOCATARIO/A se obliga "
										+ "a destinar el inmueble locado para vivienda familiar, no pudiendo ellos ser modificado, ni aun en forma temporaria, sin el consentimiento expreso"
										+ " de/la 'LOCADOR/A' ------------------------------------------------------------");
				documento.add(clausula_1);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 2*/
				calendar.setTime(contrato.getFechaInicio());
				int meses_contrato = contrato.getPlazo();
				String meses = ConvertirNumero.cantidadConLetra(String.valueOf(meses_contrato));
				String anio = ConvertirNumero.cantidadConLetra(String.valueOf(calendar.get(Calendar.YEAR)));
				Paragraph clausula_2 = new Paragraph();
				clausula_2.add("SEGUNDA (PLAZO): Las partes acuerdan que el plazo de vigencia de la locacion sera de "+meses_contrato+" ( "+meses.toUpperCase()+") meses a contar desde el dia "+
				               calendar.get(Calendar.DAY_OF_MONTH)+" del mes de "+Fechas.getMes(calendar.get(Calendar.MONTH))+" del año "+anio.toUpperCase()+" por lo que su vencimiento "
				               		+ "se producira de pleno derecho e indefectiblemente el dia ------------------------------------------------------------");
				documento.add(clausula_2);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 3*/
				Paragraph clausula_3 = new Paragraph();
				double valorpago = 0;
				String mespago = "";
				String aniopago ="";
				if (pago != null) {
					valorpago = pago.getMonto();
					calendar.setTime(pago.getFecha());
					mespago = Fechas.getMes(calendar.get(Calendar.MONTH));
					aniopago = String.valueOf(calendar.get(Calendar.YEAR));
				}else {
					calendar.setTime(new Date());
					mespago = Fechas.getMes(calendar.get(Calendar.MONTH));
					aniopago = String.valueOf(calendar.get(Calendar.YEAR));
					valorpago = contrato.getPrecio().getPrecio();
				}
				clausula_3.add("TERCERA (PRECIO Y AJUSTE): Las partes convienen un canon locativo de PESOS ($ "+valorpago+") mensuales para el primer año de contrato "
						+ "(12 meses iniciales). El canon se actualizara de forma anual, conforme el indice elaborado y publicado mensualmente por el Banco Central de la Republica Argentina"
						+ " (BCRA) determinado en el art ... del CCyCN");
				documento.add(clausula_3);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 4*/
				Paragraph clausula_4 = new Paragraph();
				clausula_4.add("CUARTA (FECHA/LUGAR DE PAGO): EL/LA LOCATARIO/A se obliga a abonar el alquiler convenido por mes entero y adelantado, entre el 1 y el "+contrato.getFechaMaxPago()+
						" de cada mes. El pago se efectuara por transferencia electronica o deposito bancario en la Cuenta Nº ................. del Banco .................., CUIT ___-____________-___"
						+ " CBU _____________________________, titular del LOCADOR/A \n"
						+ "En contrapartida, EL/LA LOCADOR/A extendera al LOCATARIO/A la factura electronica correspondiente, dentro de las 72 hs (Res. Nº 4004-E AFIP).");
				documento.add(clausula_4);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 5*/
				Paragraph clausula_5 = new Paragraph();
				clausula_5.add("QUINTA (INTERESES): La mora en el pago de los alquileres se producira de forma automatica por el mero transcurso del tiempo y sin necesidad de interpelacion ni gestion previa"
						+ " de ninguna naturaleza.\nProducida la mora los alquileres siempre deberan abonarse con una multa equivalente a la tasa activa por plazo fijo en el Banco de la Nacion Argentina, durante"
						+ " el tiempo que demore en efectivizar el pago de los alquileres adeudados, pudiendo el/la LOCADOR/A a rehusar el cobro del alquiler en mora sin el pago conjunto de este interes "
						+ "------------------------------------------------------------");
				documento.add(clausula_5);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 6*/
				Paragraph clausula_6 = new Paragraph();
				clausula_6.add("SEXTA (PROHIBICIONES/INTRANSFERIBILIDAD): El presente contrato de locacion es instransferible. Le queda prohibido al LOCATARIO/A cederlo o subarrendarlo total o parcialmente, sin"
						+ " consentimiento de/la LOCADOR/A, ya sea en forma gratuita u onerosa, ni se podra dar el inmueble en prestamo de uso, aunque sea gratuito ni permitir su ocupacion por terceros en ningun "
						+ "caracter -dicha restriccion no alcanza a las modificaciones en la conformacion del grupo familiar de/la LOCATARIO/A que pudieran suceder durante la relacion locativa-. Asimismo, queda "
						+ "estrictamente prohibido usarlo indebidamente por el/la LOCATARIO/A contrariando las leyes, ni darle otro destino que el establecido de vivienda personal unicamente (siendo especificamente "
						+ "determinado en el presente que no se podra dar otro destino que en antes mencionado -Habitacional- en los terminos del articulo 1194 del Codigo Civil y Comercial de la Nacion sin el consentimiento "
						+ "del LOCADOR/A ------------------------------------------------------------");
				documento.add(clausula_6);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 7*/
				Paragraph clausula_7 = new Paragraph();
				clausula_7.add("SEPTIMA (LA CLAUSULA DE GARANTIA)");
				documento.add(clausula_7);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 8*/
				Paragraph clausula_8 = new Paragraph();
				clausula_8.add("OCTABA (IMPUESTOS/SERVICIOS/EXPENSAS): Son obligaciones del LOCATARIO/A a) El pago de los siguientes servicios: I) electricidad, gas, agua potable, II) cargas y contribuciones asociadas al "
						+ "destino de vivienda del inmueble, III) las expensas que deriven de gastos habituales ordinarios, entendiendose por tales aquellos que se vinculan a los servicios normales y permanentes a disposicion " 
						+ "del LOCATARIO/A, independientemente de que sean considerados como expensas comunes ordinarias o extraordinarias. \n b) Transferir la titularidad de los servicios de energia electrica, agua, telefono, "
						+ "gas, TV x cable e internet, a su nombre y cargo en un plazo no superior a los sesenta (60) dias corridos, a partir de la suscripcion del presente, y abonar su suministro hasta la fecha de corte de los "
						+ "servicios al momento de entrega del inmueble al LOCADOR/A, en el que debera presentar la correspondiente baja. Para el caso de registrarse deuda previa a este contrato en alguno de estos servicios, "
						+ "EL/LA LOCATARIO/A podra cancelarla y compensar lo invertido de proximos alquileres. Esta decision debera anticiparsela por via electronica al LOCADOR/A (art. 1204 bis., CCyCN). \n c) Respetar la normativa "
						+ "local y exigencias de cualquier otra jurisdiccion o naturaleza, por lo que asume la responsabilidad por todas las consecuencias que deriven de su inobservancia. d) Abonar las costas judiciales y "
						+ "extrajudiciales que se originen en el incumplimiento de sus obligaciones contracturales. \n Son obligaciones del LOCADOR/A: a) Realizar todas las reparaciones que requiera el inmueble, asi como las "
						+ "necesarias para el normal funcionamiento de los servicios publicos, con excepcion de aquellos arreglos cuya necesidad surjan por la responsabilidad del LOCATARIO/A (excluyendo en este caso la que se "
						+ "produzca por el normal uso de la cosas. b) Notificar al LOCATARIO/A, con al menos 72 hs de antelacion, cualquier visita que quiera realizar sobre el inmueble. c) Restituir los gastos en que incurriere "
						+ "el/la LOCATARIO/A pore reparaciones urgentes y excepciones en el inmueble, siempre que el/la LOCATARIO/A haya previamente notificado al LOCADOR/A -por cualquier medio- y este no lo resolviera en el plazo "
						+ "que la urgencia requiera. Estos gastos deberan estar debidamente acreditados, pudiendo el/la LOCATARIO/A en estos casos descontarlos del importe del alquiler. d) Abonar las cargas y contribuciones que graven "
						+ "el inmueble (impuesto inmobiliario), y cualquier otro que grave a la propiedad. f) Abonar las costas judificales y extrajudiciales que se originen en el incumplimiento de sus obligaciones contractuales. -");
				documento.add(clausula_8);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 9*/
				Paragraph clausula_9 = new Paragraph();
				clausula_9.add("NOVENA (DEPOSITO): En garantia de las obligaciones contraidas mediante este contrato, el/la LOCATARIO/A da en deposito al LOCADOR/A la suma equivalente a un mes de alquiler correspondiente al valor del "
						+ "primer mes del contrato. En el momento mismo de restitucion del inmueble EL/LA LOCADOR/A debera devolver al LOCATARIO/A el deposito en garantia, actualizado su valor al del ultimo mes del contrato (art. 1996, CCYCN). \n"
						+ "Para el caso que EL/LA LOCATARIO/A no presente libre deuda o exista/n servicio/s publico/s domiciliario/s y/o expensas pendientes de pago o liquidacion al momento de la entrega del inmueble, EL/LA LOCADOR/A podra retener "
						+ "del deposito el valor de la ultima liquidacion del servicio o la expensa pendiente de pago.");
				documento.add(clausula_9);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 10*/
				Paragraph clausula_10 = new Paragraph();
				clausula_10.add("DECIMA (ESTADO DEL INMUEBLE E INVENTARIO):\nEl LOCATARIO/A declara que ha visitado el inmueble y comprobado que el mismo se encuentra desocupado, y en el siguiente estado de conservacion: \n");
				//clasula_10.add(DETALLE INMUEBLE)
				documento.add( Chunk.NEWLINE );
				clausula_10.add("El/la LOCATARIO/A se obliga a preserva el inmueble, y a restituirlo en iguales condiciones a las de recepcion, salvo por el normal desgaste propio de un uso de la cosa. Seran a exclusivo cargo "
						+ "del LOCATARIO/A todas las reparaciones, reconstrucciones y/o refacciones que fueran menester realizar para el debido cumplimiento de esta obligacion, cualquiera fuera la causa, naturaleza o cuantia del "
						+ "deterioro, y sin derecho a reembolso alguno a su favor. En caso de no hacerlo, el/la LOCADOR/A tendra derecho a realizarlo a cuenta del/la LOCATARIO/A.\n"
						+ "Seran a exclusivo cargo de/la LOCADOR/A las reparaciones, reconstrucciones y/o refaccioens que fueran menester realizar en el inmueble por daños causados por fuerza mayor, hechos por terceros, vicios "
						+ "redhibitorios, o por el normal uso de la cosa, y sin derecho a reembolso alguno a su favor. En caso de no hacerlo, el/la LOCATARIO/A tendra derecho a realizarlo a cuenta de/la LOCADOR/A pudiendo descontarlo "
						+ "del importe del alquiler.\nEn todos los casos, ambas partes podran presentar presupuestos para su comparacion y estipular de este modo el costo final de los arreglos.\nEL LOCATARIO declara conocer y aceptar"
						+ " el reglamento de copropiedad y su falta de cumplimiento sera causal de rescision.");
				documento.add(clausula_10);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 11*/
				Paragraph clausula_11 = new Paragraph();
				clausula_11.add("DECIMAPRIMERA (INCUMPLIMIENTO): La violacion por parte de EL/LA LOCATARIO/A de cualquiera de las obligaciones que asume en el presente, dara derecho a EL/LA LOCADOR/A para optar entre exigir su cabal "
						+ "cumplimiento o dar por resuelto el presente contrato y exigir el inmediato desalojo del inmueble con el pago de los daños y perjuicios pertinentes.------------------------------------------------------------");
				documento.add(clausula_11);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 12*/
				Paragraph clausula_12 = new Paragraph();
				clausula_12.add("DECIMASEGUNDA (FALTA DE PAGO): La falta de pago de 2 meses de alquiler consecutivos,dara derecho al LOCADOR/A a, previos los tramites establecidos por la ley, considerar irrevocablemten rescindido el presente "
						+ "contrato de locacion y convenio, pudiendo pedir el desalojo del bien y con derecho a reclamar las perdidas e intereses que ocasione el incumplimiento. Previo a ello, entodos los casos el/la LOCADOR/A debera "
						+ "intimar fehacientemente al LOCATARIO/A el pago de la cantidad debida, otorgando para ello un plazo que nunca deba ser inferior a diez dias corridos contados a partir de la recepcion de la intimacion, consignando "
						+ "el lugar de pago ------------------------------------------------------------");
				documento.add(clausula_12);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 13*/
				String valor1erMes = ConvertirNumero.cantidadConLetra(String.valueOf(valorpago));
				Paragraph clausula_13 = new Paragraph();
				clausula_13.add("DECIMATERCERA (PRIMER MES): EL/LA LOCATARIO/A abona en este acto la cantidad de PESOS "+valor1erMes.toUpperCase()+" ($"+valorpago+"-) en concepto del alquiler correspondiente al mes de "+mespago+" de "+aniopago+".\n"
						+"Por este primer canon, EL/LA LOCADOR/A remitira al LOCATARIO/A la correspondiente factura electronica. EL/LA LOCATARIO/A recibe en el presente acto las llaves y toma la tenencia de la locacion que les acuerda "
						+ "este contrato ------------------------------------------------------------");
				documento.add(clausula_13);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 14*/
				Paragraph clausula_14 = new Paragraph();
				clausula_14.add("DECIMACUARTA (ENTREGA DE LLAVES): Al vencimiento del contrato, el/la LOCATARIO/A debera devolver las llaves al LOCADOR/A. La recepcion por parte de/la LOCADOR/A del inmueble arrendado, aun sin reserva "
						+ "alguna de su parte, no importara conformidad con el estado del inmueble por lo que el/la LOCATARIO/A debera requerir comprobante de recepcion en el que conste expresamente dicha cirscunstancia para su liberacion "
						+ "------------------------------------------------------------");
				documento.add(clausula_14);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 15*/
				Paragraph clausula_15 = new Paragraph();
				clausula_15.add("DECIMAQUINTA (RESCISION ANTICIPADA): De acuerdo a lo establecido en el art. 1220 del Codigo Civil y Comercial, el/la LOCATARIO/A puede resolver el contrato en cualquier momento, sin costo alguno de su parte, "
						+ "si el/la LOCADOR/A incumple la obligacion de conservar el inmueble con aptitud para el uso y goce convenido; o por la garantia de eviccion o la de vicios redhibitorios. El/LA LOCATARIO/A podra, transcurridos los seis "
						+ "primeros meses de vigencia de la relacion locativa resolver la contratacion, debiendo notificar en forma fehacientemente su decision a EL/LA LOCADOR/A con una antelacion minima de treinta dias. 'EL LOCATARIO' de hacer "
						+ "uso de la opcion resolutoria, en el primer año de vigencia de la relacion locativa, debera abonar a 'EL/LA LOCADOR/A' en concepto de indemnizacion la suma equivalente a un mes y medio de alquiler al momento de desocupar "
						+ "la vivienda, y la de un solo mes si la opcion se ejercita transcurrido dicho lapso. Transcurridos los primeros seis (6) meses de relacion, si EL/LA LOCATARIO/A notifica al LOCADOR/A su decision con una anticipacion de tres "
						+ "(3) meses o mas, no corresponde el pago de indemnizacion por rescision anticipada (art 1221 CCyCN). ------------------------------------------------------------");
				documento.add(clausula_15);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 16*/
				Paragraph clausula_16 = new Paragraph();
				clausula_16.add("DECIMA SEXTA (RENOVACION): Dentro de los ultimos (3) meses del contrato cualquiera de las partes puede convocar, mediante notificacion fehaciente, a la otra a conversar sobre la renovacion de la locacion. La parte notificada "
						+ "tiene un plazo de 72 horas para responder a la solicitud. El silencio de EL/LA LOCADOR/A, o su negativa a renovar el contrato, habilitara al LOCATARIO/A a rescindir el contrato de forma anticipada, sin necesidad de preaviso ni "
						+ "obligacion de indemnizar al LOCADOR/A (art 1221 bis CCyCN). ------------------------------------------------------------");
				documento.add(clausula_16);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 17*/
				Paragraph clausula_17 = new Paragraph();
				clausula_17.add("DECIMA SEPTIMA (DOMICILIOS DE LAS PARTES):\n");
				clausula_17.add("Las partes establecen los siguiente domicilios para todo lo relativo al presente contrato:\n a) LOCADOR/A - en ___________________________; ______________________;\n"+
				                "b) LOCATARIO/A - en el inmueble locado; y "+contrato.getLocatario().getEmail()+"\n Ambas partes convienen que las comunicaciones y emplazamientos entre si con motivo del presente contrato se efectuaran por via electronica, "
				                +"exclusivamente. Las notificaciones cursadas de este modo se tendran por validas y plenamente eficaces (art. 75, CCyCN).\nDicho domicilio sera utilizado personal y exclusivamente por su titular quien se constituye responsable "
				                +"de su uso y custodio de la informacion y documentacion que al mismo se envie, asumiendo las consecuencias de su divulgacion a terceros. LAS PARTES asumen la responsabilidad por el uso indebido o inadecuado, haciendose cargo de todos "
				                + "los daños y perjuicios que su accionar generen. LAS PARTES aceptan la existencia de las notificacioens que electronicamente encien al domicilio electronico fijado, teniendolas como validas y plenamente eficaces a partir de la emision "
				                + "de la misma y comenzaran a correr los plazos procesales, en aquellos supuestos en que las notificaciones sean enviadas en dia inhabil, el plazo habra de comenzar a correr a partir del primer dia habil siguiente. LAS PARTES asumen la "
				                + "obligacion de abrir y/o revisar diariamente la casilla de correo electronico denunciada para su domicilio eletronico. LAS PARTES podran modificar su domicilio electronico, previa comunicacion fehaciente con antelacion no menor a 30 dias.");
				documento.add(clausula_17);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				/*CLAUSULA 18*/
				calendar.setTime(contrato.getFechaFirma());
				Paragraph clausula_18 = new Paragraph();
				clausula_18.add("DECIMA OCTAVA: Las partes se someten a la jurisdiccion y competencia de la Justicia Ordinaria en lo Civil de la Capital Federal con expresa renuncia a cualquier otro fuero y/o jurisdiccion que pudiera corresponderles. "
						+ "------------------------------------------------------------\nEn prueba de conformidad previa lectura y ratificacion se firman tres ejemplares de un mismo tenor y a un solo efecto, a los "+calendar.get(Calendar.DAY_OF_MONTH)+" dias del mes de "+
						Fechas.getMes(calendar.get(Calendar.MONTH))+" del "+calendar.get(Calendar.YEAR));
				documento.add(clausula_18);
				documento.add( Chunk.NEWLINE );
				/*--------------------------------------------------------------------------------------*/
				documento.close();
				texto_salida.close();
			}catch(Exception e) {
			}
		}
	}
	
}
