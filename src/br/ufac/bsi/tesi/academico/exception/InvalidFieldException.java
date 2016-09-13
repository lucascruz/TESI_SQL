package br.ufac.bsi.tesi.academico.exception;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
// caso n�o seja digitado nada e o campo fique vazio
public class InvalidFieldException extends RuntimeException {

	public InvalidFieldException(String fields){
		JOptionPane.showMessageDialog(null, fields, 
				"ERRO EXISTEM CAMPOS VAZIOS!!!! " , JOptionPane.PLAIN_MESSAGE);
	}
}
