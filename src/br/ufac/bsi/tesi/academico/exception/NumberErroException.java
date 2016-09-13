package br.ufac.bsi.tesi.academico.exception;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
//caso se digite uma letra em um campo que deve conter obrigatoriamente numeros
public class NumberErroException extends RuntimeException{
	
	public NumberErroException (String fields){
		JOptionPane.showMessageDialog(null, fields, 
				"ERRO " , JOptionPane.PLAIN_MESSAGE);
	}
}
