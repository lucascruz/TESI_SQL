package br.ufac.bsi.tesi.academico.logic;

import java.util.ArrayList;

import br.ufac.bsi.tesi.academico.db.*;

public class ProfessorLogic {

	private ProfessorDB pdb = new ProfessorDB();
	
	public void setConexao(Conexao conexao){
		pdb.setConexao(conexao);
	}

	public boolean addProfessor(String sigla, String nome){
		Professor professor = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			professor = pdb.getProfessor(sigla);

		if (professor != null)
			return false;
		else{
			professor = new Professor();
			professor.setSigla(sigla);
			professor.setNome(nome);
			return pdb.addProfessor(professor);
		}

	}

	public boolean updProfessor(String sigla, String nome){
		Professor professor = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			professor = pdb.getProfessor(sigla);

		if (professor == null)
			return false;
		else{
			professor.setNome(nome);			
			return pdb.updProfessor(professor);
		}

	}

	public boolean delProfessor(String sigla, String nome){
		Professor professor = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			professor = pdb.getProfessor(sigla);

		if (professor == null)
			return false;
		else
			return pdb.delProfessor(professor);
	}

	public Professor getProfessor(String sigla){
		Professor professor = null;
		
		if (!sigla.isEmpty())
			professor = pdb.getProfessor(sigla);

		return professor;
	}

	public ArrayList<Professor> lstProfessor() {
		ArrayList<Professor> professor = pdb.getTodosProfessores();
		return professor;
	}

	
}
