package br.ufac.bsi.tesi.academico.exception;

public class NomeInvalidoException extends Exception {
	public NomeInvalidoException (String nome){
		super("Nome errado do: \n" + nome);
	}
}
