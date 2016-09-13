package br.ufac.bsi.tesi.academico.logic;

import java.util.ArrayList;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.AlunoDB;
import br.ufac.bsi.tesi.academico.db.Conexao;

public class AlunoLogic {
	private AlunoDB cdb = new AlunoDB();

	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}

	public boolean addAluno(String matricula, String nome, String fone, String endereco, 
			String cep, String sexo, String curso_codigo){
		Aluno aluno = null;

		if (nome.isEmpty() || matricula.isEmpty()|| endereco.isEmpty()||curso_codigo.isEmpty()
				||fone.isEmpty()||cep.isEmpty()||sexo.isEmpty())
			return false;

		if (!matricula.isEmpty())
			aluno = cdb.getAluno(matricula);

		if (aluno != null)
			return false;
		else
			aluno = new Aluno();
		aluno.setMatricula(matricula);
		aluno.setNome(nome);
		aluno.setFone(fone);
		aluno.setEndereco(endereco);
		aluno.setCep(cep);
		aluno.setSexo(sexo);
		return cdb.addAluno(aluno);
	}
	public boolean updAluno(String matricula, String nome, String fone, String endereco, 
			String cep, String sexo, String curso_codigo){
		Aluno aluno = null;

		if (nome.isEmpty() || matricula.isEmpty()|| endereco.isEmpty()||curso_codigo.isEmpty()
				||fone.isEmpty()||cep.isEmpty()||sexo.isEmpty())
			return false;

		if (!matricula.isEmpty())
			aluno = cdb.getAluno(matricula);

		if (aluno == null)
			return false;
		else
			aluno.setNome(nome);
		aluno.setFone(fone);
		aluno.setEndereco(endereco);
		aluno.setCep(cep);
		aluno.setSexo(sexo);
		return cdb.updAluno(aluno);
	}

	public boolean delAluno(String matricula, String nome, String fone, String endereco, 
			String cep, String sexo, String curso_codigo){
		Aluno aluno = null;

		if (nome.isEmpty() || matricula.isEmpty()|| endereco.isEmpty()||curso_codigo.isEmpty()
				||fone.isEmpty()||cep.isEmpty()||sexo.isEmpty())
			return false;

		if (!matricula.isEmpty())
			aluno = cdb.getAluno(matricula);

		if (aluno == null)
			return false;
		else
			return cdb.delAluno(aluno);
	}

	public Aluno getAluno(String matricula){
		Aluno aluno = null;

		if (!matricula.isEmpty())
			aluno = cdb.getAluno(matricula);

		return aluno;
	}

	public List<Aluno> getTodosAlunos() {
		return cdb.getTodosAlunos();
	}		
}
