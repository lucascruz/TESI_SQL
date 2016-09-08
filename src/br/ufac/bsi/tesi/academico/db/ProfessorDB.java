package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufac.bsi.tesi.academico.logic.Professor;

public class ProfessorDB {

	private Conexao conexao;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addProfessor(Professor professor){
		String strIncluir = "INSERT INTO professor (matricula, nome, rg, cpf, endereco, fone, centro_sigla) VALUES (" +
				"'" + professor.getMatricula() +"', '" + professor.getNome() +"', '"+ professor.getRg()+"', '"+professor.getCpf()+"', '"+professor.getEndereco()+"', '"+professor.getFone()+"', '"+professor.getSigla()+"'"+");";
		System.out.println("tres");		
		return (conexao.atualize(strIncluir)>0);

	}
	
	public boolean updProfessor(Professor professor){
		String strEditar = "UPDATE professor "
				+ "SET nome = '" + professor.getNome() +"' "
				+ "WHERE sigla = '" + professor.getSigla() + "';";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delProfessor(Professor professor){
		String strExcluir = "DELETE FROM professor "
				+ "WHERE sigla = '" + professor.getSigla() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	

	
	public Professor getProfessor(String sigla){
	
		Professor professor = null;
				
		String strConsultar = "SELECT sigla, nome FROM professor "
				+ "WHERE sigla = '" + sigla + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ // entrar aqui significa que o ResultSet não é nulo, mas não implica
				 // que ele possua registros
				if (rs.next()){ // retorna false se puder moverse para o próximo regisro
								// o problema estava aqui, como não há registro, next()
								// retorna false, por isso só podemos istanciar o professor, 
								// se o next() retornar true, indicando que tem um registro
					professor = new Professor();
					professor.setSigla(rs.getString(1));
					professor.setNome(rs.getString(2));									
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return professor;
	}
	
}
