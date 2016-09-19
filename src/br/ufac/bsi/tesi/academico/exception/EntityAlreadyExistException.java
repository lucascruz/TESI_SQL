package br.ufac.bsi.tesi.academico.exception;

@SuppressWarnings("serial")
public class EntityAlreadyExistException extends RuntimeException {

	public EntityAlreadyExistException(String entidade){
		super("Entidade j� existe: " + entidade);
	}
}