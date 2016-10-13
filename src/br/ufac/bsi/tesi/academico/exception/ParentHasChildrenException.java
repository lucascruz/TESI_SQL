package br.ufac.bsi.tesi.academico.exception;

public class ParentHasChildrenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParentHasChildrenException (String chave){
		super("Erro chave estrangeira: \n" + chave);
	}
}
