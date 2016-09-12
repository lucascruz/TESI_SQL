

import java.util.*;


import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.logic.*;

public class AlunoUI {
	private static Scanner leitor = new Scanner(System.in);
	private static AlunoLogic cl = new AlunoLogic();
	
	public static void main(String args[], Conexao cnx) {
		int opcao;
		cl.setConexao(cnx);
			do {
				System.out.println("-----------------------------------");
				System.out.println("Painel de Aluno!!");
				System.out.println("1 - Incluir");
				System.out.println("2 - Editar");
				System.out.println("3 - Excluir");
				System.out.println("4 - Consultar");
				System.out.println("5 - Listar");
				System.out.println("6 - Sair");
				System.out.print("Informe sua opção: ");
				opcao = Integer.parseInt(leitor.nextLine());

				switch (opcao) {

				case 1: incluir();
					break;
				case 2: editar();
					break;
				case 3: excluir();
					break;
				case 4: consultar();
					break;
				case 5: listar();
					break;
				case 6:
					break;
				default:
					System.out.println("Opção inválida!");
				}
			} while (opcao != 6);
			leitor.close();
			cnx.desconecte();
	}
	

	private static void incluir() {
		String matricula, nome, fone, endereco, cep, sexo, curso_codigo;
		
		System.out.println("Incluindo Aluno");
		System.out.print("Matricula: ");
		matricula = leitor.nextLine();
		System.out.print("Nome: ");
		nome = leitor.nextLine();
		System.out.print("Telefone: ");
		fone = leitor.nextLine();
		System.out.print("Endereço: ");
		endereco = leitor.nextLine();
		System.out.print("Cep: ");
		cep = leitor.nextLine();
		System.out.print("Sexo: ");
		sexo = leitor.nextLine();
		System.out.print("Curso: ");
		curso_codigo = leitor.nextLine();

		if (cl.addAluno(matricula, nome, fone, endereco, cep, sexo, curso_codigo))
			System.out.println("Aluno incluido com sucesso no banco de dados!\n");
		else
			System.out.println("Falha ao incluir o Aluno!\n");

	}

	private static void editar() {

		System.out.println("Entre com a matricula do Aluno a ser editado: ");
		String matricula = leitor.nextLine();
		if (mtdConsulta(matricula)) {
			System.out.println("Diga o nome do aluno: ");
			String nomeNovo = leitor.nextLine();
			System.out.println("Diga o telefone:  ");
			String telefoneNovo = leitor.nextLine();
			System.out.println("Diga o endereço: ");
			String enderecoNovo = leitor.nextLine();
			System.out.println("Diga o cep:");
			String CepNovo = leitor.nextLine();
			System.out.println("Diga o sexo: ");
			String sexoNovo = leitor.nextLine();
			System.out.println("Diga o curso: ");
			String Curso_codigoNovo = leitor.nextLine();
			
			cl.updAluno(cl.getAluno(matricula).getMatricula(), nomeNovo, telefoneNovo, enderecoNovo, CepNovo, 
					sexoNovo, Curso_codigoNovo);
			
			System.out.println("Dados do aluno atualizado.");
			System.out.println("Os novos dados são: ");
			System.out.println("Matricula: " + cl.getAluno(matricula).getMatricula());
			System.out.println("Nome: " + cl.getAluno(nomeNovo).getNome());
			System.out.println("Telefone: " + cl.getAluno(telefoneNovo).getFone());
			System.out.println("Cpf: " + cl.getAluno(enderecoNovo).getEndereco());
			System.out.println("Endereço: " + cl.getAluno(enderecoNovo).getEndereco());
			System.out.println("Telefone: " + cl.getAluno(telefoneNovo).getFone());
			System.out.println("Centro: " + cl.getAluno(Curso_codigoNovo).getCurso_codigo());
			
		}
	}

	private static void excluir() {
		System.out.println("Entre com a matricula do aluno que vai ser excluido:");
		String matricula = leitor.nextLine();
		if (mtdConsulta(matricula)) {
			System.out.println("Deletar Aluno?");
			System.out.printf("%-8s\t", "S - SIM");
			System.out.printf("%-8s\t", "N - NÃO");
			System.out.printf("\n");
			String opcao = leitor.nextLine();
			switch (opcao.toUpperCase()) {

			case "S":
				Aluno aluno = cl.getAluno(matricula);
				cl.delAluno(aluno.getMatricula(), aluno.getNome(), aluno.getFone(), aluno.getEndereco() 
						, aluno.getCep(), aluno.getSexo(), aluno.getCurso_codigo());
				System.out.println("Aluno deletado do sistema.");
				break;
			case "N":
				break;
			default:
				System.out.println("Opção inválida! Escolha sim ou não!!!");
			}
		}
	}

	private static void consultar() {
		System.out.println("Matricula:");
		String matricula = leitor.nextLine();
		mtdConsulta(matricula);
	}

	private static void listar() {
		ArrayList<Aluno> alunos = cl.lstAluno();
		for (int i = 0; i < alunos.size(); i++) {
			System.out.printf("%-8s\t", alunos.get(i).getMatricula());
			System.out.printf("%-8s\t", alunos.get(i).getNome());
			System.out.printf("%-8s\t", alunos.get(i).getCep());
			System.out.printf("%-8s\t", alunos.get(i).getFone());
			System.out.printf("%-8s\t", alunos.get(i).getEndereco());
			System.out.printf("%-8s\t", alunos.get(i).getSexo());
			System.out.printf("%-8s\t", alunos.get(i).getCurso_codigo());
			System.out.printf("\n");
		}
	}

	private static boolean mtdConsulta(String matricula) {
		Aluno aluno = null;
		aluno = cl.getAluno(matricula);

		if (aluno != null) {
			System.out.println("Professor localizado no banco de dados!! ");
			System.out.printf("Matricula: %s\n", aluno.getMatricula());
			System.out.printf("Nome: %s\n", aluno.getNome());
			System.out.printf("Telefone: %s\n", aluno.getFone());
			System.out.printf("Endereço: %s\n", aluno.getEndereco());
			System.out.printf("Cep: %s\n", aluno.getCep());
			System.out.printf("Sexo: %s\n", aluno.getSexo());
			System.out.printf("Curso: %s\n", aluno.getCurso_codigo());
			return true;
		} else {
			System.out.println("O codigo inserido não representa o Aluno. ");
			return false;
		}
	}
}
