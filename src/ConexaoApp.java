import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class ConexaoApp {
	public static void main(String args[]){
		
		Conexao cnx = new Conexao();
		
		ResultSet rs = null;
		ResultSetMetaData find = null;
		int numeroDeColunas;
		
		rs = cnx.consulte("select codigo, nome from curso;");
		if(rs != null){
			try {
				find = rs.getMetaData();
				numeroDeColunas = find.getColumnCount();
				for(int i=1; i<= numeroDeColunas; i++)
					System.out.printf("%-8s\t", find.getColumnLabel(i));
				System.out.println();
				while(rs.next()){
					for(int i=1; i<=numeroDeColunas; i++)
						System.out.printf("%-8s\t", rs.getObject(i));
					System.out.println();
				}
			} catch (SQLException sqle) {
				System.out.printf("Erro: #%d [%s]\n", sqle.getErrorCode(), sqle.getMessage());
			}
		}
		
		
		
		//cnx.conecte();
		//cnx.consulte("0");
		//cnx.desconecte();
	}
}
