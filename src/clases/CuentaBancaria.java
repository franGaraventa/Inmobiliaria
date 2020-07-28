package clases;

public class CuentaBancaria {

	private String nrocuenta;
	private String banco;
	private String tipoCuit;
	private String docempCuit;
	private String validadorCuit;
	private String cbu;
	
	public CuentaBancaria(String nrocuenta,String banco,String tipoCuit,String docempCuit,String validadorCuit,String cbu) {
		this.nrocuenta = nrocuenta;
		this.banco = banco;
		this.tipoCuit = tipoCuit;
		this.docempCuit = docempCuit;
		this.validadorCuit = validadorCuit;
		this.cbu = cbu;
	}

	public String getNrocuenta() {
		return nrocuenta;
	}

	public void setNrocuenta(String nrocuenta) {
		this.nrocuenta = nrocuenta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTipoCuit() {
		return tipoCuit;
	}

	public void setTipoCuit(String tipoCuit) {
		this.tipoCuit = tipoCuit;
	}

	public String getDocempCuit() {
		return docempCuit;
	}

	public void setDocempCuit(String docempCuit) {
		this.docempCuit = docempCuit;
	}

	public String getValidadorCuit() {
		return validadorCuit;
	}

	public void setValidadorCuit(String validadorCuit) {
		this.validadorCuit = validadorCuit;
	}

	public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}
	
	
	
}
