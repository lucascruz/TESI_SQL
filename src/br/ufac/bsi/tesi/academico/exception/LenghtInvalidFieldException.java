package br.ufac.bsi.tesi.academico.exception;

//caso se digite mais caracteres do que o limite max
public class LenghtInvalidFieldException extends RuntimeException {
		public LenghtInvalidFieldException (String fields){
			super("Limite maximo alcan�ado: \n" + fields);
		}
}
