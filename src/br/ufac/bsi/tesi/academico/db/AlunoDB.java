package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;

import br.ufac.bsi.tesi.academico.logic.Aluno;
import br.ufac.bsi.tesi.academico.logic.Aluno;

public class AlunoDB {

	private Conexao conexao;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addAluno(Aluno aluno){
		String strIncluir = "INSERT INTO aluno (matricula, nome, fone, endereco, cep, sexo, curso_codigo) VALUES (" +
				"'" + aluno.getMatricula() +"', '" + aluno.getNome() +"', '"+ aluno.getFone()+"', '"+aluno.getEndereco()+"', '"+aluno.getCep()+"', '"+aluno.getSexo()+"', '"+aluno.getCurso_codigo()+"'"+");";
		System.out.println("tres");		
		return (conexao.atualize(strIncluir)>0);

	}
	
	public boolean updAluno(Aluno aluno){
		String strEditar = "UPDATE aluno "
				+ "SET nome = '" + aluno.getNome() +"' "
				+ "WHERE curso_codigo = '" + aluno.getCurso_codigo() + "';";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delAluno(Aluno aluno){
		String strExcluir = "DELETE FROM aluno "
				+ "WHERE curso_codigo = '" + aluno.getCurso_codigo() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	

	
	public Aluno getAluno(String sigla){
	
		Aluno aluno = null;
				
		String strConsultar = "SELECT sigla, nome FROM aluno "
				+ "WHERE sigla = '" + sigla + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ // entrar aqui significa que o ResultSet não é nulo, mas não implica
				 // que ele possua registros
				if (rs.next()){ // retorna false se puder moverse para o próximo regisro
								// o problema estava aqui, como não há registro, next()
								// retorna false, por isso só podemos istanciar o Aluno, 
								// se o next() retornar true, indicando que tem um registro
					aluno = new Aluno();
					aluno.setCurso_codigo(rs.getString(1));
					aluno.setNome(rs.getString(2));									
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return aluno;
	}

	public ArrayList<Aluno>getTodosAlunos(){
		ArrayList<Aluno>alunos = new ArrayList<Aluno>();
		ResultSet rs = conexao.consulte("SELECT * FROM aluno;");
		ResultSetMetaData rsrs;
		Aluno aluno =null;
		
		if(rs != null){
			try{
				aluno = new Aluno();
				rsrs = (ResultSetMetaData) rs.getMetaData();
				aluno.setMatricula((rsrs.getColumnLabel(1).toUpperCase()));
				aluno.setNome(rsrs.getColumnLabel(2).toUpperCase());
				alunos.add(aluno);
				while (rs.next()){
					aluno = new Aluno();
					aluno.setMatricula((rs.getString(1)));
					aluno.setNome(rs.getString(2));
					alunos.add(aluno);
					
				}
			}
			catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
			
		}
		return alunos;
	}
	
}
