package br.ufac.bsi.tesi.academico.gui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import br.ufac.bsi.tesi.academico.logic.Professor;

@SuppressWarnings("serial")
public class ProfessorTableModel extends AbstractTableModel {
	private List<Professor> professores;

	public ProfessorTableModel(List<Professor> professores) {
		this.professores = professores;
	}

	@Override
	public String getColumnName(int columnIndex) {
		String nomeDaColuna = null;
		
		switch(columnIndex) {
			case 0:
				nomeDaColuna = "Matricula"; break;
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

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		try{
			getValueAt(0, columnIndex).getClass();
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "Nenhum professor cadastro possui o nome ou matricula buscado!", 
					 "Consulta de Professor", JOptionPane.PLAIN_MESSAGE);
		}
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return professores.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Professor professor = professores.get(rowIndex);
		Object valor = null;
		
		switch(columnIndex) {
			case 0:
				valor = professor.getMatricula(); break;
			case 1:		
				valor = professor.getNome(); break;
			case 2: 
				valor = professor.getRg(); break;
			case 3: 
				valor = professor.getCpf(); break;
			case 4: 
				valor = professor.getEndereco(); break;
			case 5: 				
				valor = professor.getFone(); break;
			case 6: 
				valor = professor.getCentro().getSigla(); break; // SE DESEJAR PODE SER O NOME				
			default:
				valor = null; break;
		}
		return valor;
			
	}
}