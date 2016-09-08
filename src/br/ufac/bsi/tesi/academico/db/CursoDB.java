package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;

import br.ufac.bsi.tesi.academico.logic.Curso;

public class CursoDB {

	private Conexao conexao;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addCurso(Curso curso){
		String strIncluir = "INSERT INTO curso (codigo_curso, nome) VALUES (" +
				"'" + curso.getCodigo_Curso() +"', '" + curso.getNome() +"');";
		System.out.println("tres");		
		return (conexao.atualize(strIncluir)>0);

	}
	
	public boolean updCurso(Curso curso){
		String strEditar = "UPDATE curso "
				+ "SET nome = '" + curso.getNome() +"' "
				+ "WHERE codigo_curso = '" + curso.getCodigo_Curso() + "';";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delCurso(Curso curso){
		String strExcluir = "DELETE FROM curso "
				+ "WHERE curso_codigo = '" + curso.getCodigo_Curso() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	

	
	public Curso getCurso(String sigla){
	
		Curso curso = null;
				
		String strConsultar = "SELECT sigla, nome FROM curso "
				+ "WHERE sigla = '" + sigla + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ // entrar aqui significa que o ResultSet não é nulo, mas não implica
				 // que ele possua registros
				if (rs.next()){ // retorna false se puder moverse para o próximo regisro
								// o problema estava aqui, como não há registro, next()
								// retorna false, por isso só podemos istanciar o curso, 
								// se o next() retornar true, indicando que tem um registro
					curso = new Curso();
					curso.setCodigo_Curso((rs.getString(1)));
					curso.setNome(rs.getString(2));									
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return curso;
	}
	
	public ArrayList<Curso>getTodosCursos(){
		ArrayList<Curso>cursos = new ArrayList<Curso>();
		ResultSet rs = conexao.consulte("SELECT * FROM curso;");
		ResultSetMetaData rsrs;
		Curso curso =null;
		
		if(rs != null){
			try{
				curso = new Curso();
				rsrs = (ResultSetMetaData) rs.getMetaData();
				curso.setCodigo_Curso((rsrs.getColumnLabel(1).toUpperCase()));
				curso.setNome(rsrs.getColumnLabel(2).toUpperCase());
				cursos.add(curso);
				while (rs.next()){
					curso = new Curso();
					curso.setCodigo_Curso((rs.getString(1)));
					curso.setNome(rs.getString(2));
					cursos.add(curso);
					
				}
			}
			catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
			
		}
		return cursos;
	}
	
}

