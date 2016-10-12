package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.InvalidSizeCollumnsException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;
import br.ufac.bsi.tesi.academico.logic.Centro;
import br.ufac.bsi.tesi.academico.logic.Professor;

public class ProfessorDB {

	private Conexao conexao;
	private CentroDB cdb = new CentroDB();
	private ResultSet rs; 
	
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
		cdb.setConexao(conexao);
	}

	public boolean addProfessor(Professor professor)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityAlreadyExistException{
		String strIncluir = "INSERT INTO professor (matricula, nome, rg, cpf, endereco, fone, centro_sigla) "
				+ "VALUES (" +
				+ professor.getMatricula() +", '" 
				+ professor.getNome() + "', "
				+ professor.getRg() + ", "
				+ professor.getCpf() + ", '"
				+ professor.getEndereco() + "', '"
				+ professor.getFone() + "', '"
				+ professor.getCentro().getSigla() + "');";
						
		try{		
			return (conexao.atualize(strIncluir)>0);
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Professor: "+ professor.getMatricula() + "Possui algum do campos fora do limite esperado");
			case 1062 :
				throw new EntityAlreadyExistException("Professor: " + professor.getMatricula());
			case 1451 :
				throw new ParentHasChildrenException("Professor: " + professor.getMatricula() + "possui chaves estrangeiras vinculada!");
			case 1474:
				throw new InvalidNameException("Professor: " +professor.getMatricula());
			}
			
		}
		return false;
	}
	public boolean updProfessor(Professor professor)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strEditar = "UPDATE professor "
				+ "SET nome = '" + professor.getNome() +"', "
				+ "    rg = '" + professor.getRg() + "', "
				+ "    cpf = '" + professor.getCpf() + "', "
				+ "    endereco = '" + professor.getEndereco() + "', "
				+ "    fone = '" + professor.getFone() + "', "
				+ "    centro_sigla = '" + professor.getCentro().getSigla() + "' "
				+ "WHERE matricula = " + professor.getMatricula() + ";";
		
		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityNotExistException("Professor: " + professor.getMatricula());
			case 3013 :
				throw new InvalidSizeCollumnsException("Professor: "+ professor.getMatricula() + "Possui algum do campos fora do limite esperado");
			case 1474:
				throw new InvalidNameException("Professor: " +professor.getMatricula());
			}
		}
		
		return false;
	}
	public boolean delProfessor(Professor professor)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strExcluir = "DELETE FROM professor "
				+ "WHERE matricula = " + professor.getMatricula() + ";";
		

		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityNotExistException("Professor: " + professor.getMatricula());
			case 1474:
				throw new InvalidNameException("Professor: " +professor.getMatricula());
			case 3013 :
				throw new InvalidSizeCollumnsException("Professor: "+ professor.getMatricula() + "Possui algum do campos fora do limite esperado");
			
			}
		}
		
		return false;
	}
	
	public Professor getProfessor(int matricula) throws SQLException{
	
		Professor professor = null;
		Centro centro = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, endereco, fone, centro_sigla"
				+ " FROM professor "
				+ " WHERE matricula = " + matricula + ";"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){
					professor = new Professor();
					professor.setMatricula(rs.getInt(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getInt(3));
					professor.setCpf(rs.getInt(4));
					professor.setEndereco(rs.getString(5)+"");
					professor.setFone(rs.getString(6)+"");

					centro = cdb.getCentro(rs.getString(7));
					
					if (centro != null){
						professor.setCentro(centro);
					}
					
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityNotExistException("Professor: " + professor.getMatricula());
				}
			}
		}
		return professor;
	}
	

	public Professor getProfessoresPorNome(String nome) throws SQLException{
		Professor professor = null;
		Centro centro = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, endereco, fone, centro_sigla"
				+ " FROM professor "
				+ " where nome = " + nome + ";"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){
					professor = new Professor();
					professor.setMatricula(rs.getInt(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getInt(3));
					professor.setCpf(rs.getInt(4));
					professor.setEndereco(rs.getString(5)+"");
					professor.setFone(rs.getString(6)+"");

					centro = cdb.getCentro(rs.getString(7));
					
					if (centro != null){
						professor.setCentro(centro);
					}
					
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityNotExistException("Professor: " + professor.getMatricula());
				}
			}
		}
		return professor;
	}

	public List<Professor> getTodosProfessores() throws SQLException{

		List<Professor> professores = new ArrayList<Professor>();
		Professor professor = null;
		Centro centro = null;
				
		String strConsultar = "SELECT matricula, nome, rg, cpf, endereco, fone, centro_sigla"
				+ " FROM professor;"; 

		rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				while (rs.next()){
					professor = new Professor();
					professor.setMatricula(rs.getInt(1));
					professor.setNome(rs.getString(2));
					professor.setRg(rs.getInt(3));
					professor.setCpf(rs.getInt(4));
					professor.setEndereco(rs.getString(5));
					professor.setFone(rs.getString(6));

					centro = cdb.getCentro(rs.getString(7));
					
					if (centro != null){
						professor.setCentro(centro);
					}
					professores.add(professor);
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityNotExistException("Professor: " + professor.getMatricula());
				}
			}
		}
		return professores;
	}

}
