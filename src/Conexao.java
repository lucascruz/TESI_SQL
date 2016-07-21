import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Conexao {
	private Connection con = null;
	private Statement smt = null;
	ResultSet rs; 

	Conexao(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.printf("Classe para o driver carregada!\n");
		}catch(ClassNotFoundException cnfe){
			System.out.printf("Classe para o driver não foi encontrada!\n");
		}
	}

	public void conecte(){
		try{
			con = DriverManager.getConnection("jdbc:mysql://localhost/academico?useSSL=false", "aluno", "aluno");
			smt = con.createStatement();
			System.out.printf("Conexão com o banco efetuada!\n");
		}catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", sqle.getErrorCode(), sqle.getMessage());
		}
	}

	public void desconecte(){
		try{
			con.close();
			System.out.println("Conexao com o banco encerrada!\n");
		}
		catch(SQLException sqle){
			System.out.printf("Erro: #%d [%s]\n", sqle.getErrorCode(), sqle.getMessage());
		}
	}
	
	public ResultSet consulte(String sqlQuery){
	try {
		smt.executeQuery("select * from curso;");
		return smt.executeQuery(sqlQuery);
	} catch (SQLException sqle) {
		System.out.printf("Erro: #%d [%s]\n", sqle.getErrorCode(), sqle.getMessage());
		return null;
	}
	}

}