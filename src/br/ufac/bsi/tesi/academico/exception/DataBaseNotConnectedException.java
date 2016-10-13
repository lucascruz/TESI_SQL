package br.ufac.bsi.tesi.academico.exception;

public class DataBaseNotConnectedException extends Exception {

	public DataBaseNotConnectedException(String db){
		super("Banco de dados '"+ db + "' não conectado!");
	}
}
