package br.ufac.bsi.tesi.academico.gui;

import java.util.*;
import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

public class CentroUI {
	private static Scanner leitor = new Scanner(System.in);
	private static CentroLogic cl = new CentroLogic();

	public static void main(String args[], Conexao cnx2){
		int opcao;
		cl.setConexao(cnx2);
	
			do{
				System.out.println("-----------------------------------");
				System.out.println("Painel de Centro");
				System.out.println("1 - Incluir");			
				System.out.println("2 - Editar");
				System.out.println("3 - Excluir");
				System.out.println("4 - Consultar");
				System.out.println("5 - Listar");	
				System.out.println("6 - Sair");	
				System.out.print("Informe sua opção: ");
				opcao = Integer.parseInt(leitor.nextLine());				
				
				switch  (opcao){
				
					case 1 : incluir(); 
					break;
					case 2 : editar();
					break;
					case 3 : excluir(); 
					break;					
					case 4 : consultar(); 
					break;
					case 5 : listar();
					break;
					case 6 : 
						break;
					default : System.out.println("Opção invalida!");
				}
			} while(opcao != 6);	
			leitor.close();
			cnx2.desconecte();	
		}		


	private static void listar() {
		ArrayList<Centro> centros = cl.lstCentros();
		for (int i = 0; i < centros.size(); i++) {
			System.out.printf("%-8s\t", centros.get(i).getSigla());
			System.out.printf("%-8s\t", centros.get(i).getNome());
			System.out.printf("\n");
		}
	}
	

	private static void incluir() {
		String strSigla, strNome;
		System.out.println("Incluindo Centro");
		System.out.print("Sigla: ");
		strSigla = leitor.nextLine();
		System.out.print("Nome: ");
		strNome = leitor.nextLine();

		if (cl.addCentro(strSigla, strNome))
			System.out.println("Centro incluido com sucesso!");
		else
			System.out.println("Falha ao incluir o Centro!");
		
	}

	private static void editar() {
		System.out.println("Entre com a sigla do centro a ser Editado:");
		String sigla = leitor.nextLine();
		if (mtdConsulta(sigla)) {
			System.out.println("Entre com o Novo nome do centro");
			String novoNome = leitor.nextLine();
			cl.updCentro(cl.getCentro(sigla).getSigla(), novoNome);
			System.out.println("Centro atualizado.");
			System.out.println("Novas Informações: ");
			System.out.println("Sigla: " + cl.getCentro(sigla).getSigla());
			System.out.println("Nome: " + cl.getCentro(sigla).getNome());
		}
	}
			
	
	private static void excluir() {
		System.out.println("Entre com a sigla do centro a ser excluido:");
		String sigla = leitor.nextLine();
		if (mtdConsulta(sigla)) {
			System.out.println("Deletar centro?");
			System.out.printf("%-8s\t", "S - SIM");
			System.out.printf("%-8s\t", "N - NÃO");
			System.out.printf("\n");
			String opcao = leitor.nextLine();
			switch (opcao.toUpperCase()) {

			case "S":
				Centro centro = cl.getCentro(sigla);
				cl.delCentro(centro.getNome(), centro.getSigla());
				System.out.println("Centro deletado.");
				break;
			case "N":
				break;
			default:
				System.out.println("Opção inválida!");
			}
		}
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
	
	private static boolean mtdConsulta(String sigla) {
		Centro centro = null;
		centro = cl.getCentro(sigla);

		if (centro != null) {
			System.out.println("Centro localizado");
			System.out.printf("Sigla: %s\n", centro.getSigla());
			System.out.printf("Nome: %s\n", centro.getNome());
			return true;
		} else {
			System.out.println("Falha ao localizar o Centro!");
			return false;
		}
	}

}