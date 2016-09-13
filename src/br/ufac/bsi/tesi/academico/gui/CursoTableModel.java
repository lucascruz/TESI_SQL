package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.logic.*;
import javax.swing.table.*;
import java.util.*;

@SuppressWarnings("serial")
public class CursoTableModel extends AbstractTableModel {

	private List<Curso> cursoes;

	public CursoTableModel(List<Curso> cursoes) {

		this.cursoes = cursoes;
		
	}

	public String getColumnName(int columnIndex) {

		String nomeDaColuna = null;
		
		switch(columnIndex) {
			case 0:
				nomeDaColuna = "Cod. Curso"; break;
			case 1: 
				nomeDaColuna = "Nome"; break;
			default:
				nomeDaColuna = null;
		}
		return nomeDaColuna;		
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public int getColumnCount() {

		return 2;

	}

	public int getRowCount() {
		return cursoes.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Curso curso = cursoes.get(rowIndex);
		Object valor = null;
		
		switch(columnIndex) {
			case 0:
				valor = curso.getCodigo(); break;
			case 1:		
				valor = curso.getNome(); break;			
			default:
				valor = null; break;
		}
		return valor;
			
	}
}