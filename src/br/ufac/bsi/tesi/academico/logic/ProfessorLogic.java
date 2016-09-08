package br.ufac.bsi.tesi.academico.logic;

import java.util.ArrayList;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.db.ProfessorDB;

public class ProfessorLogic {
private ProfessorDB cdb = new ProfessorDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}

	public boolean addProfessor(String matricula, String nome, String rg, String cpf, String endereco, String fone, String centro_sigla){
		Professor professor = null;
		
		if (nome.isEmpty() || matricula.isEmpty()||centro_sigla.isEmpty()||rg.isEmpty()||cpf.isEmpty())
			return false;
		
		if (!matricula.isEmpty())
			professor = cdb.getProfessor(matricula);

		if (professor != null)
			return false;
		
		else
			professor = new Professor();
			professor.setMatricula(matricula); 
			professor.setNome(nome); 
			professor.setRg(rg);
			professor.setCpf(cpf);
			professor.setEndereco(endereco);
			professor.setFone(fone);
			professor.setCentro_sigla(centro_sigla);
			return cdb.addProfessor(professor);
	}
	public boolean updProfessor(String matricula, String nome, String rg, String cpf, String endereco, String fone, String centro_sigla){
		Professor professor = null;
		Professor cl= new Professor();
		
		if (nome.isEmpty() || matricula.isEmpty()||centro_sigla.isEmpty()||rg.isEmpty()||cpf.isEmpty())
			return false;
		
		if (!matricula.isEmpty())
			professor = cdb.getProfessor(matricula);

		if (professor == null)
			return false;
		
		if(cl.getCentro_sigla()!=centro_sigla)
			return false;
		
		else{
			professor.setNome(nome); 
			professor.setRg(rg);
			professor.setCpf(cpf);
			professor.setEndereco(endereco);
			professor.setFone(fone);
			professor.setCentro_sigla(centro_sigla);
			return cdb.updProfessor(professor);
		}

	}

	public boolean delProfessor(String matricula, String nome, String rg, String cpf, String endereco, String fone, String centro_sigla){
		Professor professor = null;
		
		if (nome.isEmpty() || matricula.isEmpty()||centro_sigla.isEmpty()||rg.isEmpty()||cpf.isEmpty())
			return false;
		
		if (!matricula.isEmpty())
			professor = cdb.getProfessor(matricula);

		if (professor == null)
			return false;
		else
			return cdb.delProfessor(professor);
	}

	public Professor getProfessor(String matricula){
		Professor professor = null;
		
		if (!matricula.isEmpty())
			professor = cdb.getProfessor(matricula);

		return professor;
	}

	public ArrayList<Professor> lstProfessor() {
		ArrayList<Professor> Professores = cdb.getTodosProfessores();
		return Professores;
	}		
}
