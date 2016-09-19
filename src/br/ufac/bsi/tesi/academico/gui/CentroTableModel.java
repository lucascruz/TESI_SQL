package br.ufac.bsi.tesi.academico.gui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import br.ufac.bsi.tesi.academico.logic.Centro;
	
	public class CentroTableModel extends AbstractTableModel {

		private List<Centro> centros;

		public CentroTableModel(List<Centro> centros) {
			this.centros = centros;
			
		}

		public String getColumnName(int columnIndex) throws NullPointerException{

			String nomeDaColuna = null;
			
			switch(columnIndex) {
				case 0:
					try{
						nomeDaColuna = "Sigla"; break;
					}catch(NullPointerException e){
						JOptionPane.showMessageDialog(null, "Sigla não cadastrado no banco", 
								 "Consulta de Professor", JOptionPane.PLAIN_MESSAGE);
					}
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
			return centros.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			
			Centro centro = centros.get(rowIndex);
			Object valor = null;
			
			switch(columnIndex) {
				case 0:
					try{
						valor = centro.getSigla(); break;
					}catch(NullPointerException e){
						JOptionPane.showMessageDialog(null, "Nenhum centro cadastro possui a sigla buscada!", 
								 "Consulta de Professor", JOptionPane.PLAIN_MESSAGE);
					}
				case 1:		
					valor = centro.getNome(); break;				
				default:
					valor = null; break;
			}
			return valor;	
		}
}
