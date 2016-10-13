package br.ufac.bsi.tesi.academico.exception;

public class FailedAtClosedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FailedAtClosedException(String db){
		super("Falha ao encerrar!!! Banco de dados n�o conectado " + db);
	}
}
