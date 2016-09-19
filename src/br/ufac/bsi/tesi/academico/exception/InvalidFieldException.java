package br.ufac.bsi.tesi.academico.exception;

public class InvalidFieldException extends RuntimeException {
	public InvalidFieldException(String fields){
		super("Campos Vazios: \n" + fields);
	}
}
