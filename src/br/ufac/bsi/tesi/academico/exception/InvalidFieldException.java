package br.ufac.bsi.tesi.academico.exception;

public class InvalidFieldException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFieldException(String fields){
		super("Campos Vazios: \n" + fields);
	}
}
