package br.ufac.bsi.tesi.academico.db;
//importando as classes da api JDBC
import java.sql.*;

//Classe Conexao
public class Conexao {
	private static Connection con = null;
	private boolean conectado = false;
	private Statement smt = null;
	private static Conexao instacia = new Conexao();
	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	
	
	private Conexao (){
	}
	
	// Método para conectar-se ao banco
	public boolean conecte(String userName, String userPasswd) throws SQLException{
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

	public static Conexao getInstacia() {
		if (instacia == null)
			synchronized (Conexao.class) {
				 if (instacia == null) {
		                instacia = new Conexao();
		            }
	        }
		return instacia;
	}
	
	public void finalize() throws Throwable {
		if (instacia != null) {
			desconecte();
		}
	}
}//Fim da classe Conexao