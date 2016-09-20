package br.ufac.bsi.tesi.academico.gui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import br.ufac.bsi.tesi.academico.logic.Curso;

@SuppressWarnings("serial")
public class CursoTableModel extends AbstractTableModel{

	private List<Curso> cursos;

	public CursoTableModel( List<Curso> cursos) {
		this.cursos = cursos;	
	}

	@Override
	public String getColumnName(int columnIndex) throws NullPointerException{

		String nomeDaColuna = null;

		switch(columnIndex) {
		case 0:
			try{
				nomeDaColuna = "Codigo"; break;
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(null, "Codigo não cadastrado no banco de dados!", 
						 "Consulta de curso", JOptionPane.PLAIN_MESSAGE);
			}
		case 1: 
			nomeDaColuna = "Nome"; break;	
		default:
			nomeDaColuna = null;
		}
		return nomeDaColuna;			
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {

		return 2;

	}

	@Override
	public int getRowCount() {
		return cursos.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Curso curso = cursos.get(rowIndex);
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

