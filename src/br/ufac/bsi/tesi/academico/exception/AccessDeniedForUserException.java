package br.ufac.bsi.tesi.academico.exception;

public class AccessDeniedForUserException extends Exception {

	public AccessDeniedForUserException(String usuario){
		super("Acesso negado para o usuário '"+ usuario + "'!");
	}
}
