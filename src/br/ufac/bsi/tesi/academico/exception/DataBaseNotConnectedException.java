package br.ufac.bsi.tesi.academico.exception;

public class DataBaseNotConnectedException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBaseNotConnectedException(String db){
		super("Banco de dados '"+ db + "' n�o conectado!");
	}
}
