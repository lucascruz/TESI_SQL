package br.ufac.bsi.tesi.academico.db;
//importando as classes da api JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ufac.bsi.tesi.academico.exception.*;

//Classe Conexao
public class Conexao {
	private static Connection con = null;
	private boolean conectado = false;
	private Statement smt = null;
	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	
	
	// Método para conectar-se ao banco
	public boolean conecte(String userName, String userPasswd) 
			throws AccessDeniedForUserException, DataBaseGenericException{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			con = DriverManager.getConnection(urlDB, userName, userPasswd);
			System.out.printf("Conexão com o banco efetuada!\n");
			setConectado(true);			
		} catch (SQLException sqle) {
			setConectado(false);			
			switch (sqle.getErrorCode()){
			case 1045 :
				throw new AccessDeniedForUserException(userName);
			default :
				throw new DataBaseGenericException(sqle.getErrorCode(), sqle.getMessage());

			}
		} catch (ClassNotFoundException e) {
			throw new DataBaseGenericException(0, "Driver para o banco de dados não encontrado!");
		}
		return isConectado();

	}

	// Método para desconectar-se ao banco
	public boolean desconecte() 
			throws DataBaseNotConnectedException, DataBaseGenericException{
		if (isConectado()){
			try {
				con.close();
				System.out.printf("Conexão com o banco encerrada!\n");
				setConectado(false);				
			} catch (SQLException sqle) {
				throw new DataBaseGenericException(sqle.getErrorCode(), sqle.getMessage());
			}
		}else{
			throw new DataBaseNotConnectedException("academico");				
		}
		return isConectado();
	}

	public ResultSet consulte(String strQuery) 
			throws DataBaseNotConnectedException, SQLException{

		if (isConectado()){
			smt = con.createStatement();				
			return  smt.executeQuery(strQuery);
		}else{
			throw new DataBaseNotConnectedException("academico");		
		}

	}

	public int atualize(String sqlUpdate) 
			throws DataBaseNotConnectedException, SQLException{

		if (isConectado()){		
			smt = con.createStatement();				
			return  smt.executeUpdate(sqlUpdate);
		}else{			
			throw new DataBaseNotConnectedException("academico");			
		}		
	}

	public boolean isConectado(){
		return conectado;
	}
	private void setConectado(boolean conectado){
		this.conectado = conectado;
	}

}//Fim da classe Conexao