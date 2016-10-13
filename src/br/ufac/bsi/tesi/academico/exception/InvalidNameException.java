package br.ufac.bsi.tesi.academico.exception;

public class InvalidNameException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidNameException (String nome){
		super("Nome errado do: \n" + nome);
	}
}
