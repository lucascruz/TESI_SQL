package br.ufac.bsi.tesi.academico.gui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import br.ufac.bsi.tesi.academico.logic.Disciplina;

public class DisciplinaTableModel extends AbstractTableModel {

	private List<Disciplina> disciplinas;
	private String[] colunas =   new String[] {"Codigo", "Nome", "Ch"};
	private String[] TipoColuna =  {"String", "String", "String"};

	public DisciplinaTableModel(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
		
	}

	@Override
	public String getColumnName(int columnIndex) {

		String nomeDaColuna = null;
		
		switch(columnIndex) {
			case 0:
				nomeDaColuna = "Codigo"; break;
			case 1: 
				nomeDaColuna = "Nome"; break;
			case 2: 
				nomeDaColuna = "Ch"; break;
			default:
				nomeDaColuna = null;
		}
		return nomeDaColuna;		
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		String className;
		try {
			className = TipoColuna[columnIndex];
			return Class.forName("java.lang."+ className);
		}catch (ClassNotFoundException cnfe) {
			JOptionPane.showMessageDialog(null, "ERRO", cnfe.getMessage(), 
					JOptionPane.PLAIN_MESSAGE);
		}catch (NullPointerException e){
			JOptionPane.showMessageDialog(null, "Nenhuma disciplina cadastrada possui o codigo buscado!", 
					 "Consulta de Disciplina", JOptionPane.PLAIN_MESSAGE);
		}
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {

		return 3;

	}

	@Override
	public int getRowCount() {
		return disciplinas.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Disciplina disciplina = disciplinas.get(rowIndex);
		Object valor = null;
		
		switch(columnIndex) {
			case 0:
				try{
					valor = disciplina.getCodigo(); break;
				}catch(NullPointerException e){
					JOptionPane.showMessageDialog(null, "Nenhuma disciplina cadastrada possui codigo buscado!", 
							 "Consulta de Professor", JOptionPane.PLAIN_MESSAGE);
				}
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