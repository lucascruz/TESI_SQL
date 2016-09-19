package br.ufac.bsi.tesi.academico.exception;

public class FailedAtClosedException extends RuntimeException{
	public FailedAtClosedException(String db){
		super("Falha ao encerrar!!! Banco de dados n�o conectado " + db);
	}
}
