

import java.util.Scanner;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.gui.DisciplinaUI;
import br.ufac.bsi.tesi.academico.logic.CentroLogic;

public class GerenciaUI {

	private static final String urlDB = "jdbc:mysql://localhost/academico?useSSL=false";
	private static Conexao cnx =  new Conexao();
	private static Scanner leitor = new Scanner(System.in);
	private static CentroLogic cl = new CentroLogic();
	
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
				System.out.println("PAINEL DE CONTROLE - ADMINISTRACAO");
				System.out.println("1 - Professor");			
				System.out.println("2 - Aluno");
				System.out.println("3 - Disciplina");
				System.out.println("4 - Curso");
				System.out.println("5 - Centro");		
				System.out.println("6 - Sair");
				System.out.print("Informe sua opção: ");
				opcao = Integer.parseInt(leitor.nextLine());				
				
				switch (opcao) {

				case 1:
					ProfessorUI.main(args, cnx);
					break;
				case 2:
					AlunoUI.main(args, cnx);
					break;
				case 3:
					DisciplinaUI.main(args, cnx);
					break;
				case 4:
					CursoUI.main(args, cnx);
					break;
				case 5:
					CentroUI.main(args, cnx);
					break;
				case 6:
					break;
				default:
					System.out.println("Opção inválida!");
				}
			} while(opcao != 6);	
		}
		
		
		leitor.close();
		cnx.desconecte();			

	} //Fim do método main
	
}
