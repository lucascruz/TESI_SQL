package br.ufac.bsi.tesi.academico.util;
// Classe ConexaoApp
import java.sql.*;
import java.util.*;

import br.ufac.bsi.tesi.academico.db.Conexao;

class ConexaoApp {

	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";

	// M�todo main inicia execu��o do aplicativo
	public static void main(String args[]){

		String usuario, senha, strInstrucoes;
		Conexao cnx =  new Conexao();
		Scanner leitor = new Scanner(System.in);
		ResultSet rs = null;
		ResultSetMetaData rsmd;

		System.out.println("Bem vindo ao banco de dados acad�mico!");
		System.out.println("Informe os dados do usu�rio para autenti��o:");

		System.out.print("Nome: ");
		usuario = leitor.nextLine();
		System.out.print("Senha: ");
		senha = leitor.nextLine();

		if (cnx.conecte(urlDB, usuario, senha)){
			System.out.println("Entre com os camados SQL (quit, para sair)!");
			System.out.print("> ");
			strInstrucoes = leitor.nextLine();
			while(strInstrucoes.compareToIgnoreCase("quit") != 0){
				rs = cnx.consulte(strInstrucoes);
				if (rs != null){
					try{
						rsmd = rs.getMetaData();

						for(int i=1;i<= rsmd.getColumnCount();i++){
							System.out.printf("%-8s\t", rsmd.getColumnLabel(i).toUpperCase());
						}
						System.out.printf("\n");
						while(rs.next()){
							for(int i=1;i<= rsmd.getColumnCount();i++){
								System.out.printf("%-8s\t", rs.getObject(i));
							}
							System.out.printf("\n");
						}
					}catch(SQLException sqle){
						System.out.printf("Erro: #%d [%s]\n", 
								sqle.getErrorCode(), sqle.getMessage());
					}
				}else{
					System.out.println("O comando n�o produziu resultados!");
				}
				System.out.print("> ");
				strInstrucoes = leitor.nextLine();				
			}
		}
		
		leitor.close();
		cnx.desconecte();			

	} //Fim do m�todo main
} //Fim da classe ConexaoDemo


