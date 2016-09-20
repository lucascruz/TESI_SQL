package br.ufac.bsi.tesi.academico.logic;

import java.sql.SQLException;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.DisciplinaDB;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.NumberErroException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;

public class DisciplinaLogic {
	private DisciplinaDB cdb = new DisciplinaDB();


	public boolean addDisciplina(String nome, String codigo, String ch)throws InvalidFieldException , InvalidLenghtFieldException, NumberErroException, EntityAlreadyExistException, ParentHasChildrenException, SQLException, InvalidNameException{
		Disciplina disciplina = null;
		String camposInvalidos = "", camposInvalidosMax = "", camposNumericosInvalidos= "", entidadeJaExiste = "Disciplina de: ";
		boolean falha = false, falhaMax = false, falhaNumero = false;

		if (codigo.length()>7){
			camposInvalidosMax  = camposInvalidosMax  + "Codigo Ultrapassou o limite max de caracteres (Limite max 7)\n";
			falhaMax = true;
		}

		if (nome.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		if (ch.length()>3){
			camposInvalidosMax  = camposInvalidosMax  + "Ch Ultrapassou o limite max de digitos (Limite max 3)\n";
			falhaMax = true;
		}
		try {
			int ch2 = Integer.parseInt(ch); //Na tabela codigo é varchar então eu n verifico se ele é numero tb
		} catch (NumberFormatException e) {
			camposNumericosInvalidos = camposNumericosInvalidos +" Ch Não pode ser letras!!";
			falhaNumero = true;
		}
		if (codigo.isEmpty()){
			camposInvalidos = camposInvalidos + " Codigo: Vazio!\n";
			falha = true;
		}

		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + " Nome: Vazio!\n";
			falha = true;
		}
		if (ch.isEmpty()){
			camposInvalidos = camposInvalidos + " Ch: Vazio!";
			falha = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);

		if(falhaNumero)
			throw new NumberErroException(camposNumericosInvalidos);

		if(falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);

		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		if (disciplina != null){
			entidadeJaExiste = entidadeJaExiste + "Matricula: "+disciplina.getCodigo()+"\nNome: "+disciplina.getNome();
			throw new EntityAlreadyExistException(entidadeJaExiste);
		}
		else{
			disciplina = new Disciplina();
			disciplina.setCodigo(codigo); disciplina.setNome(nome); disciplina.setCh(ch);
			return cdb.addDisciplina(disciplina);
		}
	}
	@SuppressWarnings("unused")
	public boolean updDisciplina(String nome, String codigo, String ch)throws InvalidFieldException,EntityNotExistException,  NumberErroException, InvalidLenghtFieldException, ParentHasChildrenException, InvalidNameException, SQLException{
		Disciplina disciplina = null;
		String camposInvalidos = "", camposInvalidosMax = "", camposNumericosInvalidos= "",entidadeNaoExist = "Disciplina não existe no banco de dados";
		boolean falha = false, falhaMax = false, falhaNumero = false;

		if (codigo.length()>7){
			camposInvalidosMax  = camposInvalidosMax  + "Codigo Ultrapassou o limite max de caracteres (Limite max 7)\n";
			falhaMax = true;
		}

		if (nome.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		if (ch.length()>3){
			camposInvalidosMax  = camposInvalidosMax  + "Ch Ultrapassou o limite max de digitos (Limite max 3)\n";
			falhaMax = true;
		}
		try {
			int ch2 = Integer.parseInt(ch); //Na tabela codigo é varchar então eu n verifico se ele é numero tb
		} catch (NumberFormatException e) {
			camposNumericosInvalidos = camposNumericosInvalidos +" Ch Não pode ser letras!!";
			falhaNumero = true;
		}
		if (codigo.isEmpty()){
			camposInvalidos = camposInvalidos + " Codigo: Vazio!\n";
			falha = true;
		}

		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + " Nome: Vazio!\n";
			falha = true;
		}
		if (ch.isEmpty()){
			camposInvalidos = camposInvalidos + " Ch: Vazio!";
			falha = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);

		if(falhaNumero)
			throw new NumberErroException(camposNumericosInvalidos);

		if(falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);

		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		if (disciplina == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else{
			disciplina.setNome(nome);
			disciplina.setCh(ch);
			return cdb.updDisciplina(disciplina);
		}

	}

	public boolean delDisciplina(String nome, String codigo, String ch)throws EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Disciplina disciplina = null;
		String entidadeNaoExist = "Disciplina não existe no banco de dados";
		if (nome.isEmpty() || codigo.isEmpty()|| ch.isEmpty())
			return false;

		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		if (disciplina == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else
			return cdb.delDisciplina(disciplina);
	}

	public Disciplina getDisciplina(String codigo) throws SQLException{
		Disciplina disciplina = null;

		if (!codigo.isEmpty())
			disciplina = cdb.getDisciplina(codigo);

		return disciplina;
	}

	public List<Disciplina> getTodasDisciplinas() throws SQLException {
		return cdb.getTodasDisciplinas();
	}		
}
