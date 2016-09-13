package br.ufac.bsi.tesi.academico.logic;

import java.util.ArrayList;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.db.DisciplinaDB;

public class DisciplinaLogic {
private DisciplinaDB cdb = new DisciplinaDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}

	public boolean addDisciplina(String nome, String codigo, String ch){
		Disciplina disciplina = null;
		
		if (nome.isEmpty() || codigo.isEmpty()|| ch.isEmpty())
			return false;
		
		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		if (disciplina != null)
			return false;
		else
			disciplina = new Disciplina();
			disciplina.setCodigo(codigo); disciplina.setNome(nome); disciplina.setCh(ch);
			return cdb.addDisciplina(disciplina);
	}
	public boolean updDisciplina(String nome, String codigo, String ch){
		Disciplina disciplina = null;
		
		if (nome.isEmpty() || codigo.isEmpty()|| ch.isEmpty())
			return false;
		
		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		if (disciplina == null)
			return false;
		else{
			disciplina.setNome(nome);
			disciplina.setCh(ch);
			return cdb.updDisciplina(disciplina);
		}

	}

	public boolean delDisciplina(String nome, String codigo, String ch){
		Disciplina disciplina = null;
		
		if (nome.isEmpty() || codigo.isEmpty()|| ch.isEmpty())
			return false;
		
		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		if (disciplina == null)
			return false;
		else
			return cdb.delDisciplina(disciplina);
	}

	public Disciplina getDisciplina(String codigo){
		Disciplina disciplina = null;
		
		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		return disciplina;
	}

	public List<Disciplina> getTodasDisciplinas() {
		return cdb.getTodasDisciplinas();
	}

}
