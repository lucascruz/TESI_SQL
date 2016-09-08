package br.ufac.bsi.tesi.academico.gui;

import java.util.*;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.logic.*;

public class CursoUI {
	private static Scanner leitor = new Scanner(System.in);
	private static CursoLogic cl = new CursoLogic();
	
	public static void main(String args[], Conexao cnx) {
		int opcao;
		cl.setConexao(cnx);
			do {
				System.out.println("-----------------------------------");
				System.out.println("Painel de Curso");
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
		String Codigo;
		String Nome;
		System.out.println("Incluindo Curso");
		System.out.print("Codigo: ");
		Codigo = leitor.nextLine();
		System.out.print("Nome: ");
		Nome = leitor.nextLine();

		if (cl.addCurso(Codigo, Nome))
			System.out.println("Curso incluído com sucesso!");
		else
			System.out.println("Falha ao incluir o Curso!");

	}

	private static void editar() {

		System.out.println("Entre com o Codigo do curso a ser Editado:");
		String sigla = leitor.nextLine();
		if (mtdConsulta(sigla)) {
			System.out.println("Entre com o Novo nome do curso");
			String novoNome = leitor.nextLine();
			cl.updCurso(cl.getCurso(sigla).getCodigo_Curso(), novoNome);
			System.out.println("Curso atualizado.");
			System.out.println("Novas Informações: ");
			System.out.println("Codigo: " + cl.getCurso(sigla).getCodigo_Curso());
			System.out.println("Nome: " + cl.getCurso(sigla).getNome());
		}
	}

	private static void excluir() {
		System.out.println("Entre com a Codigo do curso a ser excluido:");
		String sigla = leitor.nextLine();
		if (mtdConsulta(sigla)) {
			System.out.println("Deletar curso?");
			System.out.printf("%-8s\t", "S - SIM");
			System.out.printf("%-8s\t", "N - NÃO");
			System.out.printf("\n");
			String opcao = leitor.nextLine();
			switch (opcao.toUpperCase()) {

			case "S":
				Curso curso = cl.getCurso(sigla);
				cl.delCurso(curso.getNome(), curso.getCodigo_Curso());
				System.out.println("Curso deletado.");
				break;
			case "N":
				break;
			default:
				System.out.println("Opção inválida!");
			}
		}
	}

	private static void consultar() {
		System.out.println("Codigo:");
		String sigla = leitor.nextLine();
		mtdConsulta(sigla);
	}

	private static void listar() {
		ArrayList<Curso> cursos = cl.lstCurso();
		for (int i = 0; i < cursos.size(); i++) {
			System.out.printf("%-8s\t", cursos.get(i).getCodigo_Curso());
			System.out.printf("%-8s\t", cursos.get(i).getNome());
			System.out.printf("\n");
		}
	}

	private static boolean mtdConsulta(String sigla) {
		Curso curso = null;
		curso = cl.getCurso(sigla);

		if (curso != null) {
			System.out.println("Curso Localizado");
			System.out.printf("Codigo: %s\n", curso.getCodigo_Curso());
			System.out.printf("Nome: %s\n", curso.getNome());
			return true;
		} else {
			System.out.println("Falha ao localizar o Curso!");
			return false;
		}
	}

}