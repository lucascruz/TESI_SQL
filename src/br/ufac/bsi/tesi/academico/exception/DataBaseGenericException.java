package br.ufac.bsi.tesi.academico.exception;

public class DataBaseGenericException extends Exception {

	public DataBaseGenericException(int codigo, String mensagem){
		super("Exceção gerada pelo servidor do banco de dados: [" 
				+ codigo+ "]"
				+ mensagem+"!");
	}
}
