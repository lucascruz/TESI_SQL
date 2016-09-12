package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.logic.*;
import javax.swing.table.*;
import java.util.*;

@SuppressWarnings("serial")
public class DisciplinaTableModel extends AbstractTableModel {

	private List<Disciplina> disciplinas;

	public DisciplinaTableModel(List<Disciplina> disciplinas) {

		this.disciplinas = disciplinas;
		
	}

	public String getColumnName(int columnIndex) {

		String nomeDaColuna = null;
		
		switch(columnIndex) {
			case 0:
				nomeDaColuna = "Matrícula"; break;
			case 1: 
				nomeDaColuna = "Nome"; break;
			case 2: 
				nomeDaColuna = "RG"; break;
			case 3: 
				nomeDaColuna = "CPF"; break;
			case 4: 
				nomeDaColuna = "Endereço"; break;
			case 5: 
				nomeDaColuna = "Fone"; break;
			case 6: 
				nomeDaColuna = "Centro"; break;	
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

		return 7;

	}

	public int getRowCount() {
		return disciplinas.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Disciplina disciplina = disciplinas.get(rowIndex);
		Object valor = null;
		
		switch(columnIndex) {
			case 0:
				valor = disciplina.getCodigo(); break;
			case 1:		
				valor = disciplina.getNome(); break;
			case 2: 
				valor = disciplina.getCh(); break;				
			default:
				valor = null; break;
		}
		return valor;
			
	}
}