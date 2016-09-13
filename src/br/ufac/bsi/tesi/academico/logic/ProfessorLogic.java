package br.ufac.bsi.tesi.academico.logic;

import java.util.List;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.db.ProfessorDB;
import br.ufac.bsi.tesi.academico.logic.CentroLogic;

public class ProfessorLogic {
	private ProfessorDB pdb = new ProfessorDB();
	private CentroLogic centroLogic = new CentroLogic();

	public void setConexao(Conexao conexao){
		pdb.setConexao(conexao);
		centroLogic.setConexao(conexao);
	}

	public boolean addProfessor(int matricula, String nome, int rg, int cpf, 
			String endereco, String fone, String centro_sigla){
		Professor professor = null;
		Centro centro = null;



		professor = pdb.getProfessor(matricula);

		professor = new Professor();
		professor.setMatricula(matricula);
		professor.setNome(nome);
		professor.setRg(rg);
		professor.setCpf(cpf);
		professor.setEndereco(endereco);
		professor.setFone(fone);

		centro = centroLogic.getCentro(centro_sigla);


		professor.setCentro(centro);


		return pdb.addProfessor(professor);
	}


	public boolean updProfessor(int matricula, String nome, int rg, int cpf, 
			String endereco, String fone, String centrosigla){
		Professor professor = null;
		Centro centro = null;

		professor = pdb.getProfessor(matricula);


		professor = new Professor();
		professor.setMatricula(matricula);
		professor.setNome(nome);
		professor.setRg(rg);
		professor.setCpf(cpf);
		professor.setEndereco(endereco);
		professor.setFone(fone);

		centro = centroLogic.getCentro(centrosigla);


		return pdb.updProfessor(professor);
	}


	public boolean delProfessor(int matricula, String nome, int rg, int cpf, 
			String endereco, String fone, String centro_sigla){

		Professor professor = null;
		Centro centro = null;

		if (matricula == 0 || rg == 0 || cpf == 0)
			return false;

		if (nome.isEmpty() || endereco.isEmpty() || fone.isEmpty() || centro_sigla.isEmpty())
			return false;

		if (matricula != 0)
			professor = pdb.getProfessor(matricula);

		if (professor == null)
			return false;
		else{
			professor = new Professor();
			professor.setMatricula(matricula);
			professor.setNome(nome);
			professor.setRg(rg);
			professor.setCpf(cpf);
			professor.setEndereco(endereco);
			professor.setFone(fone);

			centro = centroLogic.getCentro(centro_sigla);

			if (centro != null){
				professor.setCentro(centro);
			}			

			return pdb.delProfessor(professor);
		}
	}

	public Professor getProfessor(int matricula){
		Professor professor = null;

		if (matricula != 0)
			professor = pdb.getProfessor(matricula);

		return professor;
	}	
	public List<Professor> getTodosProfessores(){
		return pdb.getTodosProfessores();
	}	

	public Professor getProfessorPorNome(String nome){
		Professor professor = null;

		if (!nome.isEmpty())
			professor = pdb.getProfessoresPorNome(nome);

		return professor;
	}	
}
