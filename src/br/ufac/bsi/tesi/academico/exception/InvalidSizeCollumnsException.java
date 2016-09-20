package br.ufac.bsi.tesi.academico.exception;

import java.sql.SQLException;

public class InvalidSizeCollumnsException extends SQLException {
	public  InvalidSizeCollumnsException (String fields){
		super("Tamanho invalido para dispor no banco: \n"+fields);
	}

}
