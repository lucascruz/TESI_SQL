package br.ufac.bsi.tesi.academico.logic;

import br.ufac.bsi.tesi.academico.db.*;

public class DisciplinaLogic {

	private DisciplinaDB ddb = new DisciplinaDB();
	
	public void setConexao(Conexao conexao){
		ddb.setConexao(conexao);
	}

	public boolean addDisciplina(String nome, String codigo_curso){
		Disciplina disciplina = null;
		
		if (codigo_curso.isEmpty() || nome.isEmpty())
			return false;

		if (!codigo_curso.isEmpty())
			disciplina = ddb.getDisciplina(codigo_curso);

		if (disciplina != null)
			return false;
		else{
			disciplina = new Disciplina();
			disciplina.setCodigo_curso(codigo_curso);
			disciplina.setNome(nome);
			return ddb.addDisciplina(disciplina);
		}

	}

	public boolean updDisciplina(String codigo_curso, String nome){
		Disciplina disciplina = null;
		
		if (codigo_curso.isEmpty() || nome.isEmpty())
			return false;

		if (!codigo_curso.isEmpty())
			disciplina = ddb.getDisciplina(codigo_curso);

		if (disciplina == null)
			return false;
		else{
			disciplina.setNome(nome);			
			return ddb.updDisciplina(disciplina);
		}

	}

	public boolean delDisciplina(String codigo_curso, String nome){
		Disciplina disciplina = null;
		
		if (codigo_curso.isEmpty() || nome.isEmpty())
			return false;

		if (!codigo_curso.isEmpty())
			disciplina = ddb.getDisciplina(codigo_curso);

		if (disciplina == null)
			return false;
		else
			return ddb.delDisciplina(disciplina);
	}

	public Disciplina getDisciplina(String codigo_curso){
		Disciplina disciplina = null;
		
		if (!codigo_curso.isEmpty())
			disciplina = ddb.getDisciplina(codigo_curso);

		return disciplina;
	}

	
}
