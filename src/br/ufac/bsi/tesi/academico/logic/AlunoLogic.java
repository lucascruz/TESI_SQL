package br.ufac.bsi.tesi.academico.logic;

import br.ufac.bsi.tesi.academico.db.*;

public class AlunoLogic {

	private AlunoDB aldb = new AlunoDB();
	
	public void setConexao(Conexao conexao){
		aldb.setConexao(conexao);
	}
	
	
	public boolean addAluno(String nome, String endereco, String sexo, String curso_codigo, int fone, int cep, String matricula){
		Aluno aluno = null;
		
		if (matricula.isEmpty() || nome.isEmpty())
			return false;

		if (!matricula.isEmpty())
			aluno = aldb.getAluno(matricula);

		if (aluno != null)
			return false;
		else{
			aluno = new Aluno();
			aluno.setMatricula(matricula);
			aluno.setNome(nome);
			aluno.setEndereco(endereco);
			aluno.setCep(cep);
			aluno.setSexo(sexo);
			return aldb.addAluno(aluno);
		}

	}

	public boolean updAluno(String sigla, String nome){
		Aluno aluno = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			aluno = aldb.getAluno(sigla);

		if (aluno == null)
			return false;
		else{
			aluno.setNome(nome);			
			return aldb.updAluno(aluno);
		}

	}

	public boolean delAluno(String sigla, String nome){
		Aluno aluno = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			aluno = aldb.getAluno(sigla);

		if (aluno == null)
			return false;
		else
			return aldb.delAluno(aluno);
	}

	public Aluno getAluno(String sigla){
		Aluno aluno = null;
		
		if (!sigla.isEmpty())
			aluno = aldb.getAluno(sigla);

		return aluno;
	}

	
}
