package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufac.bsi.tesi.academico.logic.Centro;

public class CentroDB {

	private Conexao conexao;
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addCentro(Centro centro){
		String strIncluir = "INSERT INTO centro (sigla, nome) VALUES (" +
				"'" + centro.getSigla() +"', '" + centro.getNome() +"');";
		System.out.println("tres");		
		return (conexao.atualize(strIncluir)>0);

	}
	
	public boolean updCentro(Centro centro){
		String strEditar = "UPDATE centro "
				+ "SET nome = '" + centro.getNome() +"' "
				+ "WHERE sigla = '" + centro.getSigla() + "';";
		
		return (conexao.atualize(strEditar)>0);

	}
	
	public boolean delCentro(Centro centro){
		String strExcluir = "DELETE CASCADE FROM centro "
				+ "WHERE sigla = '" + centro.getSigla() + "';";
		
		return (conexao.atualize(strExcluir)>0);

	}
	

	
	public Centro getCentro(String sigla){
	
		Centro centro = null;
				
		String strConsultar = "SELECT sigla, nome FROM centro "
				+ "WHERE sigla = '" + sigla + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ // entrar aqui significa que o ResultSet não é nulo, mas não implica
				 // que ele possua registros
				if (rs.next()){ // retorna false se puder moverse para o próximo regisro
								// o problema estava aqui, como não há registro, next()
								// retorna false, por isso só podemos istanciar o centro, 
								// se o next() retornar true, indicando que tem um registro
					centro = new Centro();
					centro.setSigla(rs.getString(1));
					centro.setNome(rs.getString(2));									
				}
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}
		return centro;
	}
	
}
