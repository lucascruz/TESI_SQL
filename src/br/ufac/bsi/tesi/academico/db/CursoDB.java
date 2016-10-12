package br.ufac.bsi.tesi.academico.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.InvalidSizeCollumnsException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;
import br.ufac.bsi.tesi.academico.logic.Curso;

public class CursoDB {
	
	private Conexao conexao;
	private ResultSet rs;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addCurso(Curso curso) throws SQLException,InvalidNameException, ParentHasChildrenException, EntityAlreadyExistException{
		int codigo = Integer.parseInt(curso.getCodigo());
		String strIncluir = "INSERT INTO curso (codigo, nome) VALUES ('" + codigo 
		+ "', '" + curso.getNome() +"');";	
		
		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Curso: "+ curso.getCodigo() + "Possui algum do campos fora do limite esperado");
			case 1062 :
				throw new EntityAlreadyExistException("Curso: " + curso.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Curso: " + curso.getCodigo() + "possui alunos vinculados!");
			case 1474:
				throw new InvalidNameException("Curso: " +curso.getNome());
			}
			
		}
		return false;
	}
	
	public boolean updCurso(Curso curso) throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strEditar = "UPDATE curso " + 
				"SET nome = '" + curso.getNome() + "' " + 
				"WHERE codigo = '" + curso.getCodigo() + "';";
			
		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Curso: "+ curso.getCodigo() + "Possui algum do campos fora do limite esperado");
			case 1244 :
				throw new EntityNotExistException("Curso: " + curso.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Curso: " + curso.getCodigo() + "possui alunos vinculados!");
			case 1474:
				throw new InvalidNameException("Curso: " +curso.getCodigo());
			}
		}
		
		return false;
	}
	
	public boolean delCurso(Curso curso)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strExcluir = "DELETE FROM curso "
				+ "WHERE codigo = '" + curso.getCodigo() + "';";
		
		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Curso: "+ curso.getCodigo() + "Possui algum do campos fora do limite esperado");
			case 1244 :
				throw new EntityNotExistException("Curso: " + curso.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Curso: " + curso.getCodigo() + "possui alunos vinculados!");
			case 1474:
				throw new InvalidNameException("Curso: " +curso.getCodigo());
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
					throw new EntityNotExistException("Curso: " + curso.getCodigo());
				}
			}
		}
		return curso;
	}
	public List<Curso> getTodosCursos() throws SQLException {
		List<Curso> cursos = new ArrayList<Curso>();
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
					throw new EntityNotExistException("Curso: " + curso.getCodigo());
				}
			}
		}
		return cursos;
	}
	
}