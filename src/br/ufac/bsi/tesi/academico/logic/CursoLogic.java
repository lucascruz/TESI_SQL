package br.ufac.bsi.tesi.academico.logic;
import java.sql.SQLException;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.db.CursoDB;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.NumberErroException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;

public class CursoLogic {

	private CursoDB cdb = new CursoDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}
	
	
	public boolean addCurso(String codigo, String nome)throws InvalidFieldException, NumberErroException, InvalidLenghtFieldException, EntityAlreadyExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Curso curso = null;
		String camposInvalidos = "", camposInvalidosMax = "",camposNumericosInvalidos = "";
		String entidadeJaExiste = "Curso de: ";
		boolean falha = false;
		boolean falhaMax = false;
		boolean falhaNumero = false;
		
		try {
			int teste = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			camposNumericosInvalidos = "Codigo Não pode ser letras!!";
			falhaNumero = true;
		}
		if (codigo.isEmpty()){
			camposInvalidos = camposInvalidos + "Codigo: Vazio!";
			falha = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!";
			falha = true;
		}
		if (codigo.length()>3){
			camposInvalidosMax  = camposInvalidosMax  + "Codigo Ultrapassou o limite max de digitos (Limite max 3)\n";
			falhaMax = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);
	
		if (falhaNumero)
			throw new NumberErroException(camposNumericosInvalidos);
		if (falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);
		
		if(!codigo.isEmpty())
		curso = cdb.getCurso(codigo);

		if (curso != null){
			entidadeJaExiste = entidadeJaExiste + "Codigo: "+curso.getCodigo()+"\nNome: "+curso.getNome();
			throw new EntityAlreadyExistException(entidadeJaExiste);
		}
		else{
			curso = new Curso();
			curso.setCodigo(codigo);
			curso.setNome(nome);
			return cdb.addCurso(curso);
		}

	}

	@SuppressWarnings("unused")
	public boolean updCurso(String codigo, String nome)throws InvalidFieldException, InvalidLenghtFieldException, NumberErroException, EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Curso curso = null;
		String camposInvalidos = "";
		String camposInvalidosMax = "";
		String camposNumericosInvalidos = "";
		String entidadeNaoExist = "Curso não existe no banco de dados";
		boolean falha = false;
		boolean falhaMax = false;
		boolean falhaNumero = false;
		
		try {
			int teste = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			camposNumericosInvalidos = "Codigo Não pode ser letras!!";
			falhaNumero = true;
		}
		if (codigo.isEmpty()){
			camposInvalidos = camposInvalidos + "Codigo: Vazio!";
			falha = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!";
			falha = true;
		}
		if (codigo.length()>3){
			camposInvalidosMax  = camposInvalidosMax  + "Codigo Ultrapassou o limite max de digitos (Limite max 3)\n";
			falhaMax = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);
	
		if (falhaNumero)
		throw new NumberErroException(camposNumericosInvalidos);
		
		if (falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);
	
		if(!codigo.isEmpty())
		curso = cdb.getCurso(codigo);

		if (curso == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else{
			curso.setNome(nome);
			return cdb.updCurso(curso);
		}

	}

	public boolean delCurso(String nome, String codigo)throws EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Curso curso = null;
		String entidadeNaoExist = "Curso não existe no banco de dados";
		if (nome.isEmpty() || codigo.isEmpty())
			return false;
	
		if(!codigo.isEmpty())
			curso = cdb.getCurso(codigo);

		if (curso == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else
			return cdb.delCurso(curso);
	}

	public Curso getCurso(String codigo) throws SQLException{
		Curso curso = null;
		
		if (!codigo.isEmpty())
			curso = cdb.getCurso(codigo);

		return curso;
	}

	public List<Curso> getTodosCursos() throws SQLException {
		return cdb.getTodosCursos();
	}		
}