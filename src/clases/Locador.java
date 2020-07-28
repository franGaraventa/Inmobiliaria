package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="locador")
public class Locador extends Persona implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="nrocuenta")
	private String nrocuenta;
	
	@Column(name="banco")
	private String banco;
	
	@Column(name="tipo_cuit")
	private String tipoCuit;
	
	@Column(name="docemp_cuit")
	private String docempCuit;
	
	@Column(name="validador_cuit")
	private String validadorCuit;
	
	@Column(name="cbu")
	private String cbu;

	public Locador() {
	}

	public Locador(int id,String dni, String nombre, String apellido, String email, String codArea, String telefono,
			String direccion, String nrocuenta, String banco, String tipoCuit, String docempCuit, String validadorCuit,
			String cbu) {
		super(id,dni,nombre,apellido,email,codArea,telefono,direccion);
		this.nrocuenta = nrocuenta;
		this.banco = banco;
		this.tipoCuit = tipoCuit;
		this.docempCuit = docempCuit;
		this.validadorCuit = validadorCuit;
		this.cbu = cbu;
	}

	public String getNrocuenta() {
		return this.nrocuenta;
	}

	public void setNrocuenta(String nrocuenta) {
		this.nrocuenta = nrocuenta;
	}

	public String getBanco() {
		return this.banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTipoCuit() {
		return this.tipoCuit;
	}

	public void setTipoCuit(String tipoCuit) {
		this.tipoCuit = tipoCuit;
	}

	public String getDocempCuit() {
		return this.docempCuit;
	}

	public void setDocempCuit(String docempCuit) {
		this.docempCuit = docempCuit;
	}

	public String getValidadorCuit() {
		return this.validadorCuit;
	}

	public void setValidadorCuit(String validadorCuit) {
		this.validadorCuit = validadorCuit;
	}

	public String getCbu() {
		return this.cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	@Override
	public CuentaBancaria getCuentaBancaria() {
		return new CuentaBancaria(nrocuenta,banco,tipoCuit,docempCuit,validadorCuit,cbu);
	}

}
