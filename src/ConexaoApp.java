import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ConexaoApp {
	public static void main(String args[]){
		
		Conexao cnx = new Conexao();
		
		ResultSet rs = null;
		ResultSetMetaData find = null;
		
		cnx.conecte();
		cnx.consulte("0");
		cnx.desconecte();
	}
}
