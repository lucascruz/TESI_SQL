package br.ufac.bsi.tesi.academico.exception;

@SuppressWarnings("serial")
//caso se digite uma letra em um campo que deve conter obrigatoriamente numeros
public class NumberErroException extends RuntimeException{
	
	public NumberErroException (String fields){
		super("ERRO DE CARACTER: \n" + fields);
	}
}
