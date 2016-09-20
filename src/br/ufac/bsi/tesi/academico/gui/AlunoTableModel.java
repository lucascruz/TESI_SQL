package br.ufac.bsi.tesi.academico.gui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import br.ufac.bsi.tesi.academico.logic.Aluno;

@SuppressWarnings("serial")
public class AlunoTableModel extends AbstractTableModel {

	private List<Aluno> alunos;

	public AlunoTableModel(List<Aluno> alunos) {

		this.alunos = alunos;
		
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
				nomeDaColuna = "Cep"; break;
			case 3: 
				nomeDaColuna = "Endereço"; break;
			case 4: 
				nomeDaColuna = "Sexo"; break;
			case 5: 
				nomeDaColuna = "Fone"; break;
			case 6: 
				nomeDaColuna = "Curso"; break;	
			default:
				nomeDaColuna = null;
		}
		return nomeDaColuna;		
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) throws NullPointerException {
		try{
			getValueAt(0, columnIndex).getClass();
		}catch(NullPointerException e){
			JOptionPane.showMessageDialog(null, "Nenhum aluno cadastro possui o nome ou matricula buscado!", 
					 "Consulta de Aluno", JOptionPane.PLAIN_MESSAGE);
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
		return alunos.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) throws NullPointerException {
		
		Aluno aluno = alunos.get(rowIndex);
		Object valor = null;
		
		switch(columnIndex) {
			case 0:
				valor = aluno.getMatricula(); break;
			case 1:		
				valor = aluno.getNome(); break;
			case 2: 
				valor = aluno.getCep(); break;
			case 3: 
				valor = aluno.getEndereco(); break;
			case 4: 
				valor =	aluno.getSexo(); break;
			case 5: 				
				valor = aluno.getFone(); break;
			case 6: 
				valor = aluno.getCurso().getNome(); break; // SE DESEJAR PODE SER O NOME				
			default:
				valor = null; break;
		}
		return valor;
			
	}
}
