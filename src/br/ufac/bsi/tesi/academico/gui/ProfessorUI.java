package br.ufac.bsi.tesi.academico.gui;

import java.util.*;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.logic.*;

public class ProfessorUI {
	private static Scanner leitor = new Scanner(System.in);
	private static ProfessorLogic cl = new ProfessorLogic();
	
	public static void main(String args[], Conexao cnx) {
		int opcao;
		cl.setConexao(cnx);
			do {
				System.out.println("-----------------------------------");
				System.out.println("Cadastro de Professor!!");
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
		String matricula, nome, rg, cpf, endereco, fone, centro_sigla;
		
		System.out.println("Incluindo Professor");
		System.out.print("Matricula: ");
		matricula = leitor.nextLine();
		System.out.print("Nome: ");
		nome = leitor.nextLine();
		System.out.print("Rg: ");
		rg = leitor.nextLine();
		System.out.print("Cpf: ");
		cpf = leitor.nextLine();
		System.out.print("Endereço: ");
		endereco = leitor.nextLine();
		System.out.print("Fone: ");
		fone = leitor.nextLine();
		System.out.print("Centro: ");
		centro_sigla = leitor.nextLine();

		if (cl.addProfessor(matricula, nome, rg, cpf, endereco, fone, centro_sigla))
			System.out.println("Professor incluido com sucesso no banco de dados!\n");
		else
			System.out.println("Falha ao incluir o professor!\n");

	}

	private static void editar() {

		System.out.println("Entre com a matricula do professor a ser editado: ");
		String matricula = leitor.nextLine();
		if (mtdConsulta(matricula)) {
			System.out.println("Entre com nome do novo professor: ");
			String nomeNovo = leitor.nextLine();
			System.out.println("Entre com o rg do novo professor: ");
			String RgNovo = leitor.nextLine();
			System.out.println("Entre com o cpf do novo professor: ");
			String CpfNovo = leitor.nextLine();
			System.out.println("Entre com o endereço do novo professor: ");
			String enderecoNovo = leitor.nextLine();
			System.out.println("Entre com o telefone do novo professor: ");
			String FoneNovo = leitor.nextLine();
			System.out.println("Entre com a sigla do novo professor: ");
			String Centro_SiglaNovo = leitor.nextLine();
			
			cl.updProfessor(cl.getProfessor(matricula).getMatricula(), nomeNovo, RgNovo, CpfNovo, enderecoNovo, 
					FoneNovo, Centro_SiglaNovo);
			
			System.out.println("Dados do professor atualizado.");
			System.out.println("Os novos dados são: ");
			System.out.println("Matricula: " + cl.getProfessor(matricula).getMatricula());
			System.out.println("Nome: " + cl.getProfessor(nomeNovo).getNome());
			System.out.println("Rg: " + cl.getProfessor(RgNovo).getRg());
			System.out.println("Cpf: " + cl.getProfessor(CpfNovo).getCpf());
			System.out.println("Endereço: " + cl.getProfessor(enderecoNovo).getEndereco());
			System.out.println("Telefone: " + cl.getProfessor(FoneNovo).getFone());
			System.out.println("Centro: " + cl.getProfessor(Centro_SiglaNovo).getCentro_sigla());
			
		}
	}

	private static void excluir() {
		System.out.println("Entre com a matricula do professor que vai ser excluido:");
		String matricula = leitor.nextLine();
		if (mtdConsulta(matricula)) {
			System.out.println("Deletar Professor?");
			System.out.printf("%-8s\t", "S - SIM");
			System.out.printf("%-8s\t", "N - NÃO");
			System.out.printf("\n");
			String opcao = leitor.nextLine();
			switch (opcao.toUpperCase()) {

			case "S":
				Professor professor = cl.getProfessor(matricula);
				cl.delProfessor(professor.getMatricula(), professor.getNome(), professor.getRg(), professor.getCpf() 
						, professor.getEndereco(), professor.getFone(), professor.getCentro_sigla());
				System.out.println("Professor deletado do sistema.");
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
		ArrayList<Professor> professor = cl.lstProfessor();
		for (int i = 0; i < professor.size(); i++) {
			System.out.printf("%-8s\t", professor.get(i).getMatricula());
			System.out.printf("%-8s\t", professor.get(i).getNome());
			System.out.printf("%-8s\t", professor.get(i).getRg());
			System.out.printf("%-8s\t", professor.get(i).getCpf());
			System.out.printf("%-8s\t", professor.get(i).getFone());
			System.out.printf("%-8s\t", professor.get(i).getEndereco());
			System.out.printf("%-8s\t", professor.get(i).getCentro_sigla());
			System.out.printf("\n");
		}
	}

	private static boolean mtdConsulta(String matricula) {
		Professor professor = null;
		professor = cl.getProfessor(matricula);

		if (professor != null) {
			System.out.println("Professor localizado no banco de dados!! ");
			System.out.printf("Matricula: %s\n", professor.getMatricula());
			System.out.printf("Nome: %s\n", professor.getNome());
			System.out.printf("Rg: %s\n", professor.getRg());
			System.out.printf("Cpf: %s\n", professor.getCpf());
			System.out.printf("Endereço: %s\n", professor.getEndereco());
			System.out.printf("Telefone: %s\n", professor.getFone());
			System.out.printf("Centro: %s\n", professor.getCentro_sigla());
			return true;
		} else {
			System.out.println("O codigo inserido não condiz com nenhum professor cadastro no banco de dados. ");
			return false;
		}
	}
}
