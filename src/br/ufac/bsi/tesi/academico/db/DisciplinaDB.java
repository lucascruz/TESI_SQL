package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufac.bsi.tesi.academico.logic.Disciplina;

public class DisciplinaDB {

	private Conexao conexao;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addDisciplina(Disciplina disciplina){
		String strIncluir = "INSERT INTO disciplina (sigla, nome) VALUES (" +
				"'" + disciplina.getCodigo_curso() +"', '" + disciplina.getNome() +"');";
		System.out.println("tres");		
		return (conexao.atualize(strIncluir)>0);

	}
	
	public boolean updDisciplina(Disciplina disciplina){
		String strEditar = "UPDATE disciplina "
				+ "SET nome = '" + disciplina.getNome() +"' "
				+ "WHERE sigla = '" + disciplina.getCodigo_curso() + "';";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delDisciplina(Disciplina disciplina){
		String strExcluir = "DELETE CASCADE FROM disciplina "
				+ "WHERE sigla = '" + disciplina.getCodigo_curso() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	

	
	public Disciplina getDisciplina(String sigla){
	
		Disciplina disciplina = null;
				
		String strConsultar = "SELECT sigla, nome FROM disciplina "
				+ "WHERE sigla = '" + sigla + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ // entrar aqui significa que o ResultSet não é nulo, mas não implica
				 // que ele possua registros
				if (rs.next()){ // retorna false se puder moverse para o próximo regisro
								// o problema estava aqui, como não há registro, next()
								// retorna false, por isso só podemos istanciar o disciplina, 
								// se o next() retornar true, indicando que tem um registro
					disciplina = new Disciplina();
					disciplina.setCodigo_curso((rs.getString(1)));
					disciplina.setNome(rs.getString(2));									
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return disciplina;
	}
	
}
