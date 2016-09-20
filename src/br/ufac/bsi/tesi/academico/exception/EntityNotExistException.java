package br.ufac.bsi.tesi.academico.exception;


@SuppressWarnings("serial")
public class EntityNotExistException extends RuntimeException{
	
	public EntityNotExistException(String entidade){
		super("Entidade n�o existe ou est� corrupta \n" + entidade);
	}
}
