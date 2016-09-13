package br.ufac.bsi.tesi.academico.util;

import java.util.*;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.logic.*;

public class DisciplinaUI {
	private static Scanner leitor = new Scanner(System.in);
	private static DisciplinaLogic cl = new DisciplinaLogic();
	
	public static void main(String args[], Conexao cnx) {
		int opcao;
		cl.setConexao(cnx);
			do {
				System.out.println("-----------------------------------");
				System.out.println("Painel de Disciplina");
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
		String codigo, ch, nome;
		System.out.println("Incluindo Disciplina");
		System.out.print("Codigo: ");
		codigo = leitor.nextLine();
		System.out.print("Nome: ");
		nome = leitor.nextLine();
		System.out.print("Ch: ");
		ch = leitor.nextLine();

		if (cl.addDisciplina(nome, codigo, ch))
			System.out.println("Disciplina incluida com sucesso no banco de dados!\n");
		else
			System.out.println("Falha ao incluir a disciplina!\n");

	}

	private static void editar() {

		System.out.println("Entre com o Codigo da disciplina a ser editada: ");
		String codigo = leitor.nextLine();
		if (mtdConsulta(codigo)) {
			System.out.println("Entre com o Novo nome da disciplina: ");
			String nomeNovo = leitor.nextLine();
			System.out.println("Entre com o Novo ch da disciplina: ");
			String chNovo = leitor.nextLine();
			cl.updDisciplina(cl.getDisciplina(codigo).getCodigo(), nomeNovo, chNovo);
			
			System.out.println("Curso atualizado.");
			System.out.println("Os novos dados são: ");
			System.out.println("Codigo: " + cl.getDisciplina(codigo).getCodigo());
			System.out.println("Nome: " + cl.getDisciplina(codigo).getNome());
		}
	}

	private static void excluir() {
		System.out.println("Entre com a Codigo da disciplina que será excluida:");
		String codigo = leitor.nextLine();
		if (mtdConsulta(codigo)) {
			System.out.println("Deletar Disciplina?");
			System.out.printf("%-8s\t", "S - SIM");
			System.out.printf("%-8s\t", "N - NÃO");
			System.out.printf("\n");
			String opcao = leitor.nextLine();
			switch (opcao.toUpperCase()) {

			case "S":
				Disciplina disciplina = cl.getDisciplina(codigo);
				cl.delDisciplina(disciplina.getNome(), disciplina.getCodigo(), disciplina.getCh());
				System.out.println("Curso deletado.");
				break;
			case "N":
				break;
			default:
				System.out.println("Opção inválida! Escolha sim ou não!!!");
			}
		}
	}

	private static void consultar() {
		System.out.println("Codigo:");
		String codigo = leitor.nextLine();
		mtdConsulta(codigo);
	}

	private static void listar() {
		ArrayList<Disciplina> cursos = cl.lstDisciplina();
		for (int i = 0; i < cursos.size(); i++) {
			System.out.printf("%-8s\t", cursos.get(i).getCodigo());
			System.out.printf("%-8s\t", cursos.get(i).getNome());
			System.out.printf("\n");
		}
	}

	private static boolean mtdConsulta(String codigo) {
		Disciplina disciplina = null;
		disciplina = cl.getDisciplina(codigo);

		if (disciplina != null) {
			System.out.println("Disciplina localizada no banco de dados!! ");
			System.out.printf("Codigo: %s\n", disciplina.getCodigo());
			System.out.printf("Nome: %s\n", disciplina.getNome());
			System.out.printf("Ch: %s\n", disciplina.getCh());
			return true;
		} else {
			System.out.println("O codigo inserido não condiz com nenhum curso cadastro no banco de dados. ");
			return false;
		}
	}
}
