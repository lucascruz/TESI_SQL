package br.ufac.bsi.tesi.academico.logic;
import java.util.ArrayList;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.*;

public class CursoLogic {

	private CursoDB cdb = new CursoDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}

	public boolean addCurso(String codigo, String nome){
		Curso curso = null;
		
		if (nome.isEmpty() || codigo.isEmpty())
			return false;
	
		if(!codigo.isEmpty())
		curso = cdb.getCurso(codigo);

		if (curso != null)
			return false;
		else{
			curso = new Curso();
			curso.setCodigo(codigo);
			curso.setNome(nome);
			return cdb.addCurso(curso);
		}

	}

	public boolean updCurso(String codigo, String nome){
		Curso curso = null;
		
		if (nome.isEmpty() || codigo.isEmpty())
			return false;
	
		if(!codigo.isEmpty())
		curso = cdb.getCurso(codigo);

		if (curso == null)
			return false;
		else{
			curso.setNome(nome);
			return cdb.updCurso(curso);
		}

	}

	public boolean delCurso(String nome, String codigo){
		Curso curso = null;
		
		if (nome.isEmpty() || codigo.isEmpty())
			return false;
	
		if(!codigo.isEmpty())
		curso = cdb.getCurso(codigo);

		if (curso == null)
			return false;
		else
			return cdb.delCurso(curso);
	}

	public Curso getCurso(String codigo){
		Curso curso = null;
		
		if (!codigo.isEmpty())
			curso = cdb.getCurso(codigo);

		return curso;
	}

	public List<Curso> getTodosCursos() {
		return cdb.getTodosCursos();
	}		
}