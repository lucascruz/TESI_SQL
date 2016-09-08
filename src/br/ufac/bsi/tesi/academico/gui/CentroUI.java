package br.ufac.bsi.tesi.academico.gui;

import java.util.*;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

public class CentroUI {
	// Classe ConexaoApp

	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	private static Conexao cnx =  new Conexao();
	private static Scanner leitor = new Scanner(System.in);
	private static CentroLogic cl = new CentroLogic();

	// Método main inicia execução do aplicativo
	public static void main(String args[]){

		String usuario, senha;
		int opcao;

		System.out.println("Bem vindo ao banco de dados acadêmico!");
		System.out.println("Informe os dados do usuário para autentição:");

		System.out.print("Nome: ");
		usuario = leitor.nextLine();
		System.out.print("Senha: ");
		senha = leitor.nextLine();

		if (cnx.conecte(urlDB, usuario, senha)){
			cl.setConexao(cnx);
	
			do{
				System.out.println("Cadastro de Centro");
				System.out.println("1 - Incluir");			
				System.out.println("2 - Editar");
				System.out.println("3 - Excluir");
				System.out.println("4 - Consultar");
				System.out.println("5 - Listar");		
				System.out.println("6 - Sair");
				System.out.print("Informe sua opção: ");
				opcao = Integer.parseInt(leitor.nextLine());				
				
				switch  (opcao){
				
					case 1 : incluir(); break;
					case 2 : editar(); break;
					case 3 : excluir(); break;					
					case 4 : consultar(); break;
					case 5 : listar(); break;
					case 6 : break;
					default : System.out.println("Opção inválida!");
				}
			} while(opcao != 6);	
		}
		leitor.close();
		cnx.desconecte();			

	} //Fim do método main

	private static void listar() {
		
		
		
		
		// TODO Auto-generated method stub
		// este método deve listar todos os centros cadastrados.
		// por ser da camada de interface/apresentação, ele
		// deve se comunicar com a camada lógica e esta com a 
		// camada de persistência. Na camada de persistência,
		// deve-se criar um método que retorne (para camada lógica) 
		// uma lista de centros (não resultset) e este deve-se repassar
		// a lista para a camada de interface (este método) para então
		// apreentá-los para o usuário.
		// código para listar no terminal já temos da Classe ConexaoApp,
		// porém usando ResultSet e ResultSetMetaData, agora você irá
		// usar as listas de objetos centros.
		
	}

	private static void incluir() {
		String strSigla, strNome;
		System.out.println("Incluindo Centro");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();
		System.out.print("Nome: ");
		strNome = leitor.nextLine();

		if (cl.addCentro(strSigla, strNome))
			System.out.println("Centro incluído com sucesso!");
		else
			System.out.println("Falha ao incluir o Centro!");
		
	}

	private static void editar() {
		// TODO Auto-generated method stub
		// baseia-se no consultar, porém,
		// agora depois de mostrar o centro
		// se ele existir, deve solicitar o 
		// novo nome para ele, para então,
		// solicitar à camada de persistência
		
		Centro centro = null;
		
		String strSigla, strNome;
		System.out.println("Consultando Centro");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		centro = cl.getCentro(strSigla);
		
		if (centro != null){
			System.out.println("Centro Localizado");
			System.out.printf("Sigla: %s\n", centro.getSigla() );
			System.out.printf("Nome: %s\n", centro.getNome() );	
			System.out.printf("Novo nome: \n");
			strNome = leitor.nextLine();
			if (cl.updCentro(strSigla, strNome))
					cl.addCentro(strSigla, strNome);
					System.out.println("Centro atualizado com sucesso!");
		
		}else
			System.out.println("Falha ao consultar o Centro!");
		
	}
		
		
		
		
		
		
	
	private static void excluir() {
		// TODO Auto-generated method stub
		// baseia-se no consultar, porém,
		// agora depois de mostrar o centro
		// se ele existir, deve solicitar o 
		// a confirmacao do usuário, para então,
		// solicitar à camada de persistência
		
		Centro centro = null;
		
		String strSigla;
		System.out.println("Consultando Centro");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		centro = cl.getCentro(strSigla);
		
		if (centro != null){
			System.out.println("Centro Localizado");
			System.out.printf("Sigla: %s\n", centro.getSigla() );
			System.out.printf("Nome: %s\n", centro.getNome() );
		//	if(cl.delCentro(strSigla, centro.getNome()))
				
		}else
			System.out.println("Falha ao consultar o Centro!");
		
	}

	

	private static void consultar() {
		
		Centro centro = null;
		
		String strSigla;
		System.out.println("Consultando Centro");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		centro = cl.getCentro(strSigla);
		
		if (centro != null){
			System.out.println("Centro Localizado");
			System.out.printf("Sigla: %s\n", centro.getSigla() );
			System.out.printf("Nome: %s\n", centro.getNome() );			
		}else
			System.out.println("Falha ao consultar o Centro!");
		
	}

}