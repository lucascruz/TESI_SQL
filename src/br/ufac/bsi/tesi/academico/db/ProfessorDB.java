package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import br.ufac.bsi.tesi.academico.logic.Professor;

public class ProfessorDB {
private Conexao conexao;

	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}
	public boolean addProfessor(Professor professor){
	
		String strIncluir = "INSERT INTO professor (matricula, nome, rg, cpf, endereco, fone, centro_sigla) VALUES ('" +
				professor.getMatricula() + "', '" + 
				professor.getNome() + "', '" + 
				professor.getRg()+ "', '" + 
				professor.getCpf() + "', '" + 
				professor.getEndereco() + "', '" + 
				professor.getFone() + "', '" + 
				professor.getCentro_sigla() + "');";
			
		System.out.println("Professor inserido");	
		return (conexao.atualize(strIncluir)>0);
	}
	
	public boolean updProfessor(Professor professor){
		String strEditar = "UPDATE professor " +
				"SET nome = '" + professor.getNome() + "' " + 
				"SET rg = '" + professor.getRg() + "' " + 
				"SET cpf = '" + professor.getCpf() + "' " + 
				"SET endereco = '" + professor.getEndereco() + "' " +
				"SET fone = '" + professor.getFone() + "' " +
				"SET centro_sigla = '" +professor.getCentro_sigla() + "' "+
				"WHERE matricula = '" + professor.getMatricula() + "';";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delProfessor(Professor professor){
		String strExcluir = "DELETE FROM professor "
				+ "WHERE matricula = '" + professor.getMatricula() + "';";
				
		return (conexao.atualize(strExcluir)>0);
	}
	
	public Professor getProfessor(String matricula){
	
		Professor professor = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, fone,endereco, centro_sigla FROM aluno "
				+ "WHERE matricula = '" + matricula + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){ 
					professor = new Professor();
					professor.setMatricula(rs.getString(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getString(3));
					professor.setCpf(rs.getString(4));
					professor.setEndereco(rs.getString(5));
					professor.setFone(rs.getString(6));
					professor.setCentro_sigla(rs.getString(7));
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return professor;
		
		/*Professor professor = null;
		
		String strConsultar = "SELECT matricula, nome, rg, cpf, fone,endereco, centro_sigla FROM aluno "
			+ "WHERE matricula = '" + matricula + "';"; 
		
		professor = new Professor();
		professor.setMatricula(strConsultar);
		return professor;*/
	}
	public ArrayList<Professor> getTodosProfessores() {
		ArrayList<Professor> professores = new ArrayList<Professor>();
		
		ResultSet rs = conexao.consulte("SELECT * FROM professor;");
		ResultSetMetaData rsrs;
		Professor professor = null;
		
		if (rs != null) {
			try {
				professor = new Professor();
				rsrs = rs.getMetaData();
				professor.setMatricula(rsrs.getColumnLabel(1).toUpperCase());
				professor.setNome(rsrs.getColumnLabel(2).toUpperCase());
				professor.setRg(rsrs.getColumnLabel(3).toUpperCase());
				professor.setCpf(rsrs.getColumnLabel(4).toUpperCase());
				professor.setEndereco(rsrs.getColumnLabel(5).toUpperCase());
				professor.setFone(rsrs.getColumnLabel(6).toUpperCase());
				professor.setCentro_sigla(rsrs.getColumnLabel(7).toUpperCase());
				professores.add(professor);
				
				while (rs.next()) {
					professor = new Professor();
					professor.setMatricula(rs.getString(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getString(3));
					professor.setCpf(rs.getString(4));
					professor.setEndereco(rs.getString(5));
					professor.setFone(rs.getString(6));
					professor.setCentro_sigla(rs.getString(7));
					professores.add(professor);
					
				}
			} catch (SQLException sqle) {
				System.out.printf("Erro: #%d [%s]\n", sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return professores;
	}
}
