package br.ufac.bsi.tesi.academico.exception;

public class DataBaseNotConnectedException  extends RuntimeException{

	public DataBaseNotConnectedException(String db){
		super("Banco de dados '"+ db + "' n�o conectado!");
	}
}
