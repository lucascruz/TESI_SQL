package br.ufac.bsi.tesi.academico.exception;


@SuppressWarnings("serial")
public class EntityDontExistException extends RuntimeException{
	
	public EntityDontExistException(String entidade){
		super("Entidade n�o existe ou est� corrupta \n" + entidade);
	}
}
