package br.ufac.bsi.tesi.academico.exception;

//caso se digite mais caracteres do que o limite max
public class InvalidLenghtFieldException extends RuntimeException {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public InvalidLenghtFieldException (String fields){
			super("Limite maximo alcan�ado: \n" + fields);
		}
}
