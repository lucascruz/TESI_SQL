package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import br.ufac.bsi.tesi.academico.logic.Curso;
import br.ufac.bsi.tesi.academico.logic.Disciplina;

public class DisciplinaDB {
private Conexao conexao;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}
	public boolean addDisciplina(Disciplina disciplina){
		String strIncluir = "INSERT INTO disciplina (codigo, nome, ch) VALUES ('" + disciplina.getCodigo() 
		 + "', '" + disciplina.getNome()+ "', '"+ disciplina.getCh() +"');";
	
		System.out.println("Disciplina inserida");	
		return (conexao.atualize(strIncluir)>0);
	}
	
	public boolean updDisciplina(Disciplina disciplina){
		String strEditar = "UPDATE disciplina " + "SET nome = '" + disciplina.getNome() +"' " + "SET ch = '" +
				disciplina.getCh()+ "' " + "WHERE codigo = '" + disciplina.getCodigo() + "';";
		
		return (conexao.atualize(strEditar)>0);
	}
	
	public boolean delDisciplina(Disciplina disciplina){
		String strExcluir = "DELETE FROM disciplina "
				+ "WHERE codigo = '" + disciplina.getCodigo() + "';";
		
		return (conexao.atualize(strExcluir)>0);
	}
	
	public Disciplina getDisciplina(String codigo){
	
		Disciplina disciplina = null;
				
		String strConsultar = "SELECT codigo, nome, ch FROM disciplina "
				+ "WHERE codigo = '" + codigo + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){ 
					disciplina = new Disciplina();
					disciplina.setCodigo(rs.getString(1));
					disciplina.setNome(rs.getString(2));
					disciplina.setCh(rs.getString(3));
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return disciplina;
	}
	public ArrayList<Disciplina> getTodasDisciplinas() {
		ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>(); 

		ResultSet rs = conexao.consulte("SELECT * FROM disciplina;");
		ResultSetMetaData rsrs;
		Disciplina disciplina = null;

		if (rs != null) {
			try {
				disciplina = new Disciplina();
				rsrs = rs.getMetaData();
				disciplina.setCodigo(rsrs.getColumnLabel(1).toUpperCase());
				disciplina.setNome(rsrs.getColumnLabel(2).toUpperCase());
				disciplinas.add(disciplina);

				while (rs.next()) {
					disciplina = new Disciplina();
					disciplina.setCodigo(rs.getString(1));
					disciplina.setNome(rs.getString(2));
					disciplinas.add(disciplina);

				}
			} catch (SQLException sqle) {
				System.out.printf("Erro: #%d [%s]\n", sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return disciplinas;
	}
}
