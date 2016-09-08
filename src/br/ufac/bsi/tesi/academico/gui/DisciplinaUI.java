package br.ufac.bsi.tesi.academico.gui;

import java.util.*;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

public class DisciplinaUI {
	// Classe ConexaoApp

	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	private static Conexao cnx =  new Conexao();
	private static Scanner leitor = new Scanner(System.in);
	private static DisciplinaLogic cl = new DisciplinaLogic();

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
				System.out.println("Cadastro de Disciplina");
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

	private static void incluir() {
		String strSigla, strNome;
		System.out.println("Incluindo Disciplina");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();
		System.out.print("Nome: ");
		strNome = leitor.nextLine();

		if (cl.addDisciplina(strSigla, strNome))
			System.out.println("Disciplina incluído com sucesso!");
		else
			System.out.println("Falha ao incluir o Disciplina!");
		
	}

	private static void editar() {
		// TODO Auto-generated method stub
		// baseia-se no consultar, porém,
		// agora depois de mostrar o disciplina
		// se ele existir, deve solicitar o 
		// novo nome para ele, para então,
		// solicitar à camada de persistência
		
		Disciplina disciplina = null;
		
		String strSigla, strNome;
		System.out.println("Consultando Disciplina");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		disciplina = cl.getDisciplina(strSigla);
		
		if (disciplina != null){
			System.out.println("Disciplina Localizado");
			System.out.printf("Sigla: %s\n", disciplina.getCodigo_curso() );
			System.out.printf("Nome: %s\n", disciplina.getNome() );	
			System.out.printf("Novo nome: \n");
			strNome = leitor.nextLine();
			if (cl.updDisciplina(strSigla, strNome))
					cl.addDisciplina(strSigla, strNome);
					System.out.println("Disciplina atualizado com sucesso!");
		
		}else
			System.out.println("Falha ao consultar o Disciplina!");
		
	}
		
		
		
		
		
		
	
	private static void excluir() {
		// TODO Auto-generated method stub
		// baseia-se no consultar, porém,
		// agora depois de mostrar o disciplina
		// se ele existir, deve solicitar o 
		// a confirmacao do usuário, para então,
		// solicitar à camada de persistência
		
		Disciplina disciplina = null;
		
		String strSigla;
		System.out.println("Consultando Disciplina");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		disciplina = cl.getDisciplina(strSigla);
		
		if (disciplina != null){
			System.out.println("Disciplina Localizado");
			System.out.printf("Sigla: %s\n", disciplina.getCodigo_curso() );
			System.out.printf("Nome: %s\n", disciplina.getNome() );
		//	if(cl.delDisciplina(strSigla, disciplina.getNome()))
				
		}else
			System.out.println("Falha ao consultar o Disciplina!");
		
	}

	

	private static void consultar() {
		
		Disciplina disciplina = null;
		
		String strSigla;
		System.out.println("Consultando Disciplina");
		System.out.print("Codigo: ");
		strSigla = leitor.nextLine();

		disciplina = cl.getDisciplina(strSigla);
		
		if (disciplina != null){
			System.out.println("Disciplina Localizado");
			System.out.printf("Sigla: %s\n", disciplina.getCodigo_curso() );
			System.out.printf("Nome: %s\n", disciplina.getNome() );			
		}else
			System.out.println("Falha ao consultar o Disciplina!");
		
	}
	
	private static void listar(){
		ArrayList<Disciplina> disciplinas = cl.lstDisciplinas();
		for (int i = 0; i<disciplinas.size(); i++){
			System.out.printf("%-8s\t", disciplinas.get(i).getCodigo_curso());
			System.out.printf("%-8s\t", disciplinas.get(i).getCodigo_curso());
			System.out.println("\n");
		}
	}

}
