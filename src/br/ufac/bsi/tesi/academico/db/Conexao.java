package br.ufac.bsi.tesi.academico.db;
//importando as classes da api JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Classe Conexao
public class Conexao {
	private static Connection con = null;
	private boolean conectado = false;
	private Statement smt = null;
	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	
	
	// Método para conectar-se ao banco
	public boolean conecte(String userName, String userPasswd){
		try {
			con = DriverManager.getConnection(urlDB, userName, userPasswd);
			conectado = true;
		}catch(SQLException sqle){
			conectado = false;
		}
		return conectado;
	}

	// Método para desconectar-se ao banco
	public boolean desconecte() throws SQLException{
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

	public ResultSet consulte(String strQuery) throws SQLException{

		if (conectado){
			try{
				smt = con.createStatement();				
				return  smt.executeQuery(strQuery);
			}catch(SQLException sqle){			
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());				
			}
		}else{
			System.out.printf("Não conectado, impossível cosultar!");			
		}
		return null;
	}

	public int atualize(String sqlUpdate) throws SQLException{

		if (conectado){		
			try {
				smt = con.createStatement();				
				return smt.executeUpdate(sqlUpdate);
			}catch(SQLException sqle){
				System.out.printf("Erro: #%d [%s]\n", 
						sqle.getErrorCode(), sqle.getMessage());
			}
		}else{
			System.out.printf("Não conectado, impossível atualizar!");			
		}		

		return 0;
	}

	@Override
	public void finalize() throws Throwable {
		if (conectado) {
			desconecte();
		}
	}     
}//Fim da classe Conexao