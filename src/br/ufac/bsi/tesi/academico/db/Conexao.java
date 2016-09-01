package br.ufac.bsi.tesi.academico.db;

//importando as classes da api JDBC
import java.sql.*;

//Classe Conexao
public class Conexao {
	private Connection con = null;
	private Statement smt = null;
	private boolean conectado = false;

	// Método para conectar-se ao banco
	public boolean conecte(String urlDb, String userName, String userPasswd){
		try {
			con = DriverManager.getConnection(urlDb, userName, userPasswd);
			smt = con.createStatement();
			System.out.printf("Conexão com o banco efetuada!\n");
			conectado = true;
		}catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", 
					sqle.getErrorCode(), sqle.getMessage());
			conectado = false;
		}
		return conectado;
	}

	// Método para desconectar-se ao banco
	public boolean desconecte(){
		if (conectado){
			try {
				con.close();
				System.out.printf("Conexão com o banco encerrada!\n");
				conectado = false;
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}else{
			System.out.printf("Não conectado, impossível encerrar!");			
		}
		return conectado;
	}

	public ResultSet consulte(String strQuery){

		if (conectado){
			try{
				return smt.executeQuery(strQuery);
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());				
			}
		}else{
			System.out.printf("Não conectado, impossível cosultar!");			
		}
		return null;
	}

	public int atualize(String sqlUpdate){
		try {
			smt = con.createStatement();
			return smt.executeUpdate(sqlUpdate);
		} catch (SQLException sqle) {
			System.out.printf("Erro: #%d[%s]\n", sqle.getErrorCode(), sqle.getMessage());
			return 0;
		}
	}



}//Fim da classe Conexao