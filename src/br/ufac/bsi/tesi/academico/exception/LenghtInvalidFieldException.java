package br.ufac.bsi.tesi.academico.exception;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
//caso se digite mais caracteres do que o limite max
public class LenghtInvalidFieldException extends RuntimeException {
		public LenghtInvalidFieldException (String fields){
			JOptionPane.showMessageDialog(null, fields, 
					"ERRO QUANTIDADE MAX DE CARACTERES ULTRAPASSADA " , JOptionPane.PLAIN_MESSAGE);
		}
}
