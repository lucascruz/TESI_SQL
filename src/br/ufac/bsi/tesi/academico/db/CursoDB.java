package br.ufac.bsi.tesi.academico.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufac.bsi.tesi.academico.logic.Curso;

public class CursoDB {
	private Conexao conexao;
	private ResultSet rs;
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}
	public boolean addCurso(Curso curso){
		int codigo = Integer.parseInt(curso.getCodigo());
		String strIncluir = "INSERT INTO curso (codigo, nome) VALUES ('" + codigo 
		+ "', '" + curso.getNome() +"');";	
		
		return (conexao.atualize(strIncluir)>0);
	}
	
	public boolean updCurso(Curso curso){
		String strEditar = "UPDATE curso " + 
				"SET nome = '" + curso.getNome() + "' " + 
				"WHERE codigo = '" + curso.getCodigo() + "';";
			
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delCurso(Curso curso){
		String strExcluir = "DELETE FROM curso "
				+ "WHERE codigo = '" + curso.getCodigo() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	
	public Curso getCurso(String codigo){
	
		Curso curso = null;
				
		String strConsultar = "SELECT codigo, nome FROM curso "
				+ "WHERE codigo = '" + codigo + "';"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){ 
					curso = new Curso();
					curso.setCodigo(rs.getString(1));
					curso.setNome(rs.getString(2));	
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return curso;
	}
	public List<Curso> getTodosCursos() {
		List<Curso> cursos = new ArrayList<Curso>(); // fiquei com preguiça de sair mudando as variaveis aq oh, 
		Curso curso= null;

		String strConsultar = "SELECT codigo, nome"
				+ " FROM curso;"; 
		rs = conexao.consulte(strConsultar);
		
		if (rs != null) {
			try {
				while (rs.next()){
					curso = new Curso();
					curso.setCodigo(rs.getString(1));
					curso.setNome(rs.getString(2));
					cursos.add(curso);
				}
			}catch(SQLException sqle){
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return cursos;
	}
	
}