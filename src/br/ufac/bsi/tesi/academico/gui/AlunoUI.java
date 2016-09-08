package br.ufac.bsi.tesi.academico.gui;

import java.util.*;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

public class AlunoUI {
	// Classe ConexaoApp

	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	private static Conexao cnx =  new Conexao();
	private static Scanner leitor = new Scanner(System.in);
	private static AlunoLogic cl = new AlunoLogic();

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
				System.out.println("Cadastro de Aluno");
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
		String strNome,strEndereco, strSexo, strCurso_codigo, strMatricula;
		int intFone, intCep;
		System.out.println("Incluindo Aluno");
		System.out.print("Matricula: ");
		strMatricula = leitor.nextLine();
		System.out.print("Nome: ");
		strNome = leitor.nextLine();
		System.out.print("End: ");
		strEndereco = leitor.nextLine();
		System.out.print("CEP: ");
		intCep = leitor.nextInt();
		System.out.print("Sexo: ");
		strSexo = leitor.nextLine();
		System.out.print("Curso: ");
		strCurso_codigo = leitor.nextLine();
		System.out.print("Fone: ");
		intFone = leitor.nextInt();

		if (cl.addAluno(strNome,strEndereco, strSexo, strCurso_codigo, intFone, intCep, strMatricula))
			System.out.println("Aluno incluído com sucesso!");
		else
			System.out.println("Falha ao incluir o Aluno!");
		
	}

	private static void editar() {
		// TODO Auto-generated method stub
		// baseia-se no consultar, porém,
		// agora depois de mostrar o aluno
		// se ele existir, deve solicitar o 
		// novo nome para ele, para então,
		// solicitar à camada de persistência
		
		Aluno aluno = null;
		
		String strNome= null,strEndereco = null, strSexo= null, strCurso_codigo= null, strMatricula= null;
		int intFone = 0, intCep = 0;
		System.out.println("Consultando Aluno");
		System.out.print("Sigla: ");
		strMatricula = leitor.nextLine();

		aluno = cl.getAluno(strMatricula);
		
		if (aluno != null){
			System.out.println("Aluno Localizado");
			System.out.printf("Sigla: %s\n", aluno.getMatricula() );
			System.out.printf("Nome: %s\n", aluno.getNome() );	
			System.out.printf("Novo nome: \n");
			strNome = leitor.nextLine();
			if (cl.updAluno(strMatricula, strNome))
					cl.addAluno(strNome, strEndereco, strSexo, strCurso_codigo, intFone, intCep, strMatricula);
					System.out.println("Aluno atualizado com sucesso!");
		
		}else
			System.out.println("Falha ao consultar o Aluno!");
		
	}
		
	private static void excluir() {
		// TODO Auto-generated method stub
		// baseia-se no consultar, porém,
		// agora depois de mostrar o aluno
		// se ele existir, deve solicitar o 
		// a confirmacao do usuário, para então,
		// solicitar à camada de persistência
		
		Aluno aluno = null;
		
		String strSigla;
		System.out.println("Consultando Aluno");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		aluno = cl.getAluno(strSigla);
		
		if (aluno != null){
			System.out.println("Aluno Localizado");
			System.out.printf("Sigla: %s\n", aluno.getMatricula());
			System.out.printf("Nome: %s\n", aluno.getNome() );
		//	if(cl.delAluno(strSigla, aluno.getNome()))
				
		}else
			System.out.println("Falha ao consultar o Aluno!");
		
	}

	

	private static void consultar() {
		
		Aluno aluno = null;
		
		String strSigla;
		System.out.println("Consultando Aluno");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();

		aluno = cl.getAluno(strSigla);
		
		if (aluno != null){
			System.out.println("Aluno Localizado");
			System.out.printf("Sigla: %s\n", aluno.getMatricula() );
			System.out.printf("Nome: %s\n", aluno.getNome() );			
		}else
			System.out.println("Falha ao consultar o Aluno!");
		
	}
	
	private static void listar(){
		ArrayList<Aluno> alunos = cl.lstAlunos();
		for (int i = 0; i<alunos.size(); i++){
			System.out.printf("%-8s\t", alunos.get(i).getMatricula());
			System.out.printf("%-8s\t", alunos.get(i).getMatricula());
			System.out.println("\n");
		}
	}

}