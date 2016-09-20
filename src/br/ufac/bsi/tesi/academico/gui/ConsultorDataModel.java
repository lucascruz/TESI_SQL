package br.ufac.bsi.tesi.academico.gui;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel; 					//importando a classe


public class ConsultorDataModel extends AbstractTableModel {

	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	private int rowsNumber = 0;

	public ConsultorDataModel(ResultSet rs){
		try {
			this.rs = rs;
			rsmd = this.rs.getMetaData();
			rs.last();
			rowsNumber = rs.getRow();
		}catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", 
					sqle.getErrorCode(), sqle.getMessage());			
		}
	}
	
	@Override
	public int getRowCount(){
		return rowsNumber;
	}
	@Override
	public int getColumnCount(){
		try{
			return rsmd.getColumnCount();
		}catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", 
					sqle.getErrorCode(), sqle.getMessage());
		}
		return 0;
	}
	@Override
	public String getColumnName(int columnIndex){
		try{
			return rsmd.getColumnLabel(columnIndex + 1);
		}catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", 
					sqle.getErrorCode(), sqle.getMessage());
		}		
		return "Null";
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		
		try{
			rs.absolute(rowIndex + 1);
			return rs.getObject(columnIndex + 1);
		}catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", 
					sqle.getErrorCode(), sqle.getMessage());
		}
		
		return null;
	}
	@Override
	public boolean isCellEditable (int rowIndex, int columnIndex){
		return false;
	}
	@Override
	public Class getColumnClass (int columnIndex){
		return getValueAt(0, columnIndex).getClass();
	}

}