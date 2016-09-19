package br.ufac.bsi.tesi.academico.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import  br.ufac.bsi.tesi.academico.exception.*;

import br.ufac.bsi.tesi.academico.logic.Curso;

public class CursoDB {
	
	private Conexao conexao = Conexao.getInstacia();
	private ResultSet rs;

	public boolean addCurso(Curso curso) throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityAlreadyExistException{
		int codigo = Integer.parseInt(curso.getCodigo());
		String strIncluir = "INSERT INTO curso (codigo, nome) VALUES ('" + codigo 
		+ "', '" + curso.getNome() +"');";	
		
		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 1062 :
				throw new EntityAlreadyExistException("Curso: " + curso.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Curso: " + curso.getCodigo() + "possui alunos vinculados!");
			case 1474:
				throw new NomeInvalidoException("Curso: " +curso.getNome());
			}
			
		}
		return false;
	}
	
	public boolean updCurso(Curso curso) throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityDontExistException{
		String strEditar = "UPDATE curso " + 
				"SET nome = '" + curso.getNome() + "' " + 
				"WHERE codigo = '" + curso.getCodigo() + "';";
			
		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityDontExistException("Curso: " + curso.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Curso: " + curso.getCodigo() + "possui alunos vinculados!");
			case 1474:
				throw new NomeInvalidoException("Curso: " +curso.getCodigo());
			}
		}
		
		return false;
	}
	
	public boolean delCurso(Curso curso)throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityDontExistException{
		String strExcluir = "DELETE FROM curso "
				+ "WHERE codigo = '" + curso.getCodigo() + "';";
		
		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityDontExistException("Curso: " + curso.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Curso: " + curso.getCodigo() + "possui alunos vinculados!");
			case 1474:
				throw new NomeInvalidoException("Curso: " +curso.getCodigo());
			}
		}
		
		return false;
	}
	
	public Curso getCurso(String codigo) throws SQLException{
	
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
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityDontExistException("Curso: " + curso.getCodigo());
				}
			}
		}
		return curso;
	}
	public List<Curso> getTodosCursos() throws SQLException {
		List<Curso> cursos = new ArrayList<Curso>(); // fiquei com preguiça de sair mudando as variaveis aq oh, 
		//deixei centro msm kkkkk
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
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityDontExistException("Curso: " + curso.getCodigo());
				}
			}
		}
		return cursos;
	}
	
}