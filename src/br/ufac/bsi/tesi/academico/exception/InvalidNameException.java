package br.ufac.bsi.tesi.academico.exception;

public class InvalidNameException extends Exception {
	public InvalidNameException (String nome){
		super("Nome errado do: \n" + nome);
	}
}
