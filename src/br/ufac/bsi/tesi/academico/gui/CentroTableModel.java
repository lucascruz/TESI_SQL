package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.logic.*;
import javax.swing.table.*;
import java.util.*;

@SuppressWarnings("serial")
public class CentroTableModel extends AbstractTableModel {

	private List<Centro> centroes;

	public CentroTableModel(List<Centro> centroes) {

		this.centroes = centroes;
		
	}

	public String getColumnName(int columnIndex) {

		String nomeDaColuna = null;
		
		switch(columnIndex) {
			case 0:
				nomeDaColuna = "Sigla"; break;
			case 1: 
				nomeDaColuna = "Nome"; break;
			default:
				nomeDaColuna = null;
		}
		return nomeDaColuna;		
		
	}

	public Class <?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public int getColumnCount() {

		return 2;

	}

	public int getRowCount() {
		return centroes.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Centro centro = centroes.get(rowIndex);
		Object valor = null;
		
		switch(columnIndex) {
			case 0:
				valor = centro.getSigla(); break;
			case 1:		
				valor = centro.getNome(); break;			
			default:
				valor = null; break;
		}
		return valor;
			
	}
}