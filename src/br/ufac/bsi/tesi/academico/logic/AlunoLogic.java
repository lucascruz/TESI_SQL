package br.ufac.bsi.tesi.academico.logic;

import java.sql.SQLException;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.AlunoDB;
import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.NumberErroException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;

public class AlunoLogic {
	private AlunoDB adb = new AlunoDB();
	private CursoLogic cursoLogic = new CursoLogic();
	
	public void setConexao(Conexao conexao){
		adb.setConexao(conexao);
		cursoLogic.setConexao(conexao);
	}
	
	public boolean addAluno(String matricula, String nome, String fone, String endereco, 
			String cep, String sexo, String curso_nome)throws InvalidFieldException, InvalidLenghtFieldException, EntityAlreadyExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Aluno aluno = null;
		String camposInvalidos = "", camposNumericosInvalidos = "", camposInvalidosMax = "", entidadeJaExiste = "Aluno: ";
		boolean falha = false, falhaNumero = false, falhaMax = false;
		Curso curso = null;
		
		
		if (matricula.isEmpty()){
			camposInvalidos = camposInvalidos + "Matricula Vazia\n ";
			falha = true;
		}

		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome esta Vazio!\n";
			falha = true;
		}
		if (fone.isEmpty()){
			camposInvalidos = camposInvalidos + "Telefone está Vazio!\n";
			falha = true;
		}
		if (cep.isEmpty()){
			camposInvalidos = camposInvalidos + "Cep está vazio!\n";
			falha = true;
		}

		if (endereco.isEmpty()){
			camposInvalidos = camposInvalidos + "Endereco está Vazio!\n";
			falha = true;
		}
		if (sexo.isEmpty()){
			camposInvalidos = camposInvalidos + "Sexo está Vazio !\n";
			falha = true;
		}
		
		if (matricula.length()>10){
			camposInvalidosMax  = camposInvalidosMax  + "Matricula Ultrapassou o limite max de digitos (Limite max 10)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		if (fone.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "O telefone Ultrapassou o limite max de caracteres (Limite max 3)\n";
			falhaMax = true;
		}
		if (endereco.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Endereço Ultrapassou o limite max de digitos (Limite max 45)\n";
			falhaMax = true;
		}
		
		if (cep.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Cep Ultrapassou o limite max de caracteres (Limite max 7)\n";
			falhaMax = true;
		}
		if (sexo.length()>1){
			camposInvalidosMax  = camposInvalidosMax  + "O sexo só pode ter 1 caracter (M ou F por exemplo) \n";
			falhaMax = true;
		}
		
		if (falha)
			throw new InvalidFieldException(camposInvalidos);
		
		if(falhaNumero)
			throw new NumberErroException(camposNumericosInvalidos);
		
		if(falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);

		if (!matricula.isEmpty())
			aluno = adb.getAluno(matricula);

		if (aluno != null){
			entidadeJaExiste = entidadeJaExiste + "Matricula: "+aluno.getMatricula()+"\nNome: "+aluno.getNome();
			throw new EntityAlreadyExistException(entidadeJaExiste);
		}
		else
			aluno = new Aluno();
		aluno.setMatricula(matricula);
		aluno.setNome(nome);
		aluno.setFone(fone);
		aluno.setEndereco(endereco);
		aluno.setCep(cep);
		aluno.setSexo(sexo);

		curso = cursoLogic.getCurso(curso_nome);
		if (curso != null){
			aluno.setCurso(curso);
		}	
		return adb.addAluno(aluno);
	}

	public boolean updAluno(String matricula, String nome, String fone, String endereco, 
			String cep, String sexo, String curso_nome)throws InvalidFieldException, InvalidLenghtFieldException, NumberErroException, EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Aluno aluno = null;
		String camposInvalidos = "", camposNumericosInvalidos = "", camposInvalidosMax = "",entidadeNaoExist = "Aluno não existe no banco de dados";
		boolean falha = false, falhaNumero = false, falhaMax = false;
		Curso curso = null;
		
		
		if (matricula.isEmpty()){
			camposInvalidos = camposInvalidos + "Matricula Vazia\n ";
			falha = true;
		}

		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome esta Vazio!\n";
			falha = true;
		}
		if (fone.isEmpty()){
			camposInvalidos = camposInvalidos + "Telefone está Vazio!\n";
			falha = true;
		}
		if (cep.isEmpty()){
			camposInvalidos = camposInvalidos + "Cep está vazio!\n";
			falha = true;
		}

		if (endereco.isEmpty()){
			camposInvalidos = camposInvalidos + "Endereco está Vazio!\n";
			falha = true;
		}
		if (sexo.isEmpty()){
			camposInvalidos = camposInvalidos + "Sexo está Vazio !\n";
			falha = true;
		}
		
		if (matricula.length()>10){
			camposInvalidosMax  = camposInvalidosMax  + "Matricula Ultrapassou o limite max de digitos (Limite max 10)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		if (fone.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "O telefone Ultrapassou o limite max de caracteres (Limite max 3)\n";
			falhaMax = true;
		}
		if (endereco.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Endereço Ultrapassou o limite max de digitos (Limite max 45)\n";
			falhaMax = true;
		}
		
		if (cep.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Cep Ultrapassou o limite max de caracteres (Limite max 7)\n";
			falhaMax = true;
		}
		if (sexo.length()>1){
			camposInvalidosMax  = camposInvalidosMax  + "O sexo só pode ter 1 caracter (M ou F por exemplo) \n";
			falhaMax = true;
		}
		
		if (falha)
			throw new InvalidFieldException(camposInvalidos);
		
		if(falhaNumero)
			throw new NumberErroException(camposNumericosInvalidos);
		
		if(falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);

		if (!matricula.isEmpty())
			aluno = adb.getAluno(matricula);

		if (aluno == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else
			aluno.setNome(nome);
		aluno.setFone(fone);
		aluno.setEndereco(endereco);
		aluno.setCep(cep);
		aluno.setSexo(sexo);

		curso = cursoLogic.getCurso(curso_nome);

		if (curso != null){
			aluno.setCurso(curso);
		}	
		return adb.updAluno(aluno);
	}
	public boolean delAluno(String matricula, String nome, String fone, String endereco, 
			String cep, String sexo, String curso_nome)throws EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Aluno aluno = null;
		Curso curso = null;
		String entidadeNaoExist = "Aluno não existe no banco de dados";
		if (nome.isEmpty() || matricula.isEmpty()|| endereco.isEmpty()||curso_nome.isEmpty()
				||fone.isEmpty()||cep.isEmpty()||sexo.isEmpty())
			return false;

		if (!matricula.isEmpty())
			aluno = adb.getAluno(matricula);

		if (aluno == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else
			aluno = new Aluno();
		aluno.setMatricula(matricula);
		aluno.setNome(nome);
		aluno.setCep(cep);
		aluno.setEndereco(endereco);
		aluno.setFone(fone);
		aluno.setSexo(sexo);

		curso = cursoLogic.getCurso(curso_nome);

		if (curso != null){
			aluno.setCurso(curso);
		}	
		return adb.delAluno(aluno);
	}
	public Aluno getAluno(String matricula) throws SQLException{
		Aluno aluno = null;

		if (!matricula.isEmpty())
			aluno = adb.getAluno(matricula);

		return aluno;
	}
	public List<Aluno> getTodosAlunos() throws SQLException, InvalidNameException {
		return adb.getTodosAlunos();
	}		
}
