package br.ufac.bsi.tesi.academico.exception;

public class ParentHasChildrenException extends RuntimeException {
	public ParentHasChildrenException (String chave){
		super("Erro chave estrangeira: \n" + chave);
	}
}
