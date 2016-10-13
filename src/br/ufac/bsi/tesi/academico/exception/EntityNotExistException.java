package br.ufac.bsi.tesi.academico.exception;

public class EntityNotExistException extends Exception {

	public EntityNotExistException(String entidade){
		super("Entidade não existe: " + entidade);
	}
}
