package br.ufac.bsi.tesi.academico.logic;

import java.sql.SQLException;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.db.ProfessorDB;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;

public class ProfessorLogic {
	private ProfessorDB pdb = new ProfessorDB();
	private CentroLogic centroLogic = new CentroLogic();
	
	public void setConexao(Conexao conexao){
		pdb.setConexao(conexao);
		centroLogic.setConexao(conexao);
	}
	
	public boolean addProfessor(int matricula, String nome, int rg, int cpf, 
			String endereco, String fone, String centro_sigla)throws InvalidFieldException, InvalidLenghtFieldException, ParentHasChildrenException, EntityAlreadyExistException, InvalidNameException, SQLException{
		Professor professor = null;
		String camposInvalidos = "", camposInvalidosMax="", entidadeJaExiste = "Professor de: ";
		boolean falhaVazio = false, falhaMax = false;
		Centro centro = null;

		if (matricula == 0 ){
			camposInvalidos = camposInvalidos + "Matricula está vazia!\n";
			falhaVazio = true;
		}
		if(rg == 0 ){
			camposInvalidos = camposInvalidos + "Rg está vazio!\n";
			falhaVazio = true;
		}
		if(cpf == 0){
			camposInvalidos = camposInvalidos + "Cpf está vazio!\n";
			falhaVazio= true;
		}
		if (endereco.isEmpty()){
			camposInvalidos = camposInvalidos + "Endereço: Vazio!\n";
			falhaVazio = true;	
		}
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!\n";
			falhaVazio = true;
		}
		if(fone.isEmpty()){
			camposInvalidos = camposInvalidos + "Fone: Vazio!\n";
			falhaVazio= true;
		}
		String matriculaa=Integer.toString(matricula);
		if (matriculaa.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "Matricula Ultrapassou o limite max de digitos (Limite max 11)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		String rgb=Integer.toString(rg);
		if (rgb.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "O RG Ultrapassou o limite max de digitos (Limite max 11)\n";
			falhaMax = true;
		}
		if (endereco.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Endereço Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		String cpfb=Integer.toString(cpf);
		if (cpfb.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "Cpf Ultrapassou o limite max de digitos(Limite max 11)\n";
			falhaMax = true;
		}
		if (fone.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "O Telefone Ultrapassou o limite max de caracteres (Limite 11) \n";
			falhaMax = true;
		}
		
		if (falhaVazio)
			throw new InvalidFieldException(camposInvalidos);
		
		if(falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);
		
		if (matricula != 0)
			professor = pdb.getProfessor(matricula);

		if (professor != null){
			entidadeJaExiste = entidadeJaExiste + "Matricula: "+professor.getMatricula()+"\nNome: "+professor.getNome();
			throw new EntityAlreadyExistException(entidadeJaExiste);
		}
		else{
			professor = new Professor();
			professor.setMatricula(matricula);
			professor.setNome(nome);
			professor.setRg(rg);
			professor.setCpf(cpf);
			professor.setEndereco(endereco);
			professor.setFone(fone);

			centro = centroLogic.getCentro(centro_sigla);
			
			if (centro != null){
				professor.setCentro(centro);
			}			
			
			return pdb.addProfessor(professor);
		}

	}

	public boolean updProfessor(int matricula, String nome, int rg, int cpf, 
			String endereco, String fone, String centrosigla)throws InvalidFieldException, InvalidLenghtFieldException, EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		Professor professor = null;
		String camposInvalidos = "", camposInvalidosMax="",entidadeNaoExist = "Professor não existe no banco de dados";
		boolean falhaVazio = false, falhaMax = false;
		Centro centro = null;

		if (matricula == 0 ){
			camposInvalidos = camposInvalidos + "Matricula está vazia!\n";
			falhaVazio = true;
		}
		if(rg == 0 ){
			camposInvalidos = camposInvalidos + "Rg está vazio!\n";
			falhaVazio = true;
		}
		if(cpf == 0){
			camposInvalidos = camposInvalidos + "Cpf está vazio!\n";
			falhaVazio= true;
		}
		if (endereco.isEmpty()){
			camposInvalidos = camposInvalidos + "Endereço: Vazio!\n";
			falhaVazio = true;	
		}
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!\n";
			falhaVazio = true;
		}
		if(fone.isEmpty()){
			camposInvalidos = camposInvalidos + "Fone: Vazio!\n";
			falhaVazio= true;
		}
		String matriculaa=Integer.toString(matricula);
		if (matriculaa.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "Matricula Ultrapassou o limite max de digitos (Limite max 11)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Nome Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		String rgb=Integer.toString(rg);
		if (rgb.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "O RG Ultrapassou o limite max de digitos (Limite max 11)\n";
			falhaMax = true;
		}
		if (endereco.length()>45){
			camposInvalidosMax  = camposInvalidosMax  + "Endereço Ultrapassou o limite max de caracteres (Limite max 45)\n";
			falhaMax = true;
		}
		String cpfb=Integer.toString(cpf);
		if (cpfb.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "Cpf Ultrapassou o limite max de digitos(Limite max 11)\n";
			falhaMax = true;
		}
		if (fone.length()>11){
			camposInvalidosMax  = camposInvalidosMax  + "O Telefone Ultrapassou o limite max de caracteres (Limite 11) \n";
			falhaMax = true;
		}
		
		if (falhaVazio)
			throw new InvalidFieldException(camposInvalidos);
		
		if(falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosMax);
		
		if (matricula != 0)
			professor = pdb.getProfessor(matricula);

		if (professor == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else{
			professor = new Professor();
			professor.setMatricula(matricula);
			professor.setNome(nome);
			professor.setRg(rg);
			professor.setCpf(cpf);
			professor.setEndereco(endereco);
			professor.setFone(fone);

			centro = centroLogic.getCentro(centrosigla);
			
			if (centro != null){
				professor.setCentro(centro);
			}			
			
			return pdb.updProfessor(professor);
		}
	}

	public boolean delProfessor(int matricula, String nome, int rg, int cpf, 
			String endereco, String fone, String centro_sigla)throws EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException{
		String entidadeNaoExist = "Professor não existe no banco de dados";
		Professor professor = null;
		Centro centro = null;
		
		if (matricula == 0 || rg == 0 || cpf == 0)
			return false;
		
		if (nome.isEmpty() || endereco.isEmpty() || fone.isEmpty() || centro_sigla.isEmpty())
			return false;

		if (matricula != 0)
			professor = pdb.getProfessor(matricula);

		if (professor == null)
			throw new EntityNotExistException(entidadeNaoExist);
		else{
			professor = new Professor();
			professor.setMatricula(matricula);
			professor.setNome(nome);
			professor.setRg(rg);
			professor.setCpf(cpf);
			professor.setEndereco(endereco);
			professor.setFone(fone);

			centro = centroLogic.getCentro(centro_sigla);
			
			if (centro != null){
				professor.setCentro(centro);
			}			
			
			return pdb.delProfessor(professor);
		}
	}

	public Professor getProfessor(int matricula) throws SQLException{
		Professor professor = null;
		
		if (matricula != 0)
			professor = pdb.getProfessor(matricula);

		return professor;
	}	
	public List<Professor> getTodosProfessores() throws SQLException{
		return pdb.getTodosProfessores();
	}	
	
	public Professor getProfessorPorNome(String nome) throws SQLException{
		Professor professor = null;
		
		if (!nome.isEmpty())
			professor = pdb.getProfessoresPorNome(nome);

		return professor;
	}

}
