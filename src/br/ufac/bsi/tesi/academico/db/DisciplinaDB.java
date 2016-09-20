package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.InvalidSizeCollumnsException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;
import br.ufac.bsi.tesi.academico.logic.Disciplina;

public class DisciplinaDB {
	private Conexao conexao = Conexao.getInstacia();
	

	public boolean addDisciplina(Disciplina disciplina)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityAlreadyExistException{
		String strIncluir = "INSERT INTO disciplina (codigo, nome, ch) VALUES ('" + disciplina.getCodigo()
		 + "', '" + disciplina.getNome()+ "', '"+ disciplina.getCh() +"');";
	
		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 1062 :
				throw new EntityAlreadyExistException("Disciplina: " + disciplina.getCodigo());
			case 3013 :
				throw new InvalidSizeCollumnsException("Disciplina: "+ disciplina.getCodigo() + "Possui algum do campos fora do limite esperado");
			case 1474:
				throw new InvalidNameException("Disciplina: " +disciplina.getCodigo());
			}
			
		}
		return false;
	}
	
	public boolean updDisciplina(Disciplina disciplina)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strEditar = "UPDATE disciplina " + "SET nome = '" + disciplina.getNome() +"' " + "SET ch = '" +
				disciplina.getCh()+ "' " + "WHERE codigo = '" + disciplina.getCodigo() + "';";
		
		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityNotExistException("Disciplina: " + disciplina.getCodigo());
			case 3013 :
				throw new InvalidSizeCollumnsException("Disciplina: "+ disciplina.getCodigo() + "Possui algum do campos fora do limite esperado");
			case 1474:
				throw new InvalidNameException("Disciplina: " +disciplina.getCodigo());
			}
		}
		
		return false;
	}
	
	public boolean delDisciplina(Disciplina disciplina)throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strExcluir = "DELETE FROM disciplina "
				+ "WHERE codigo = '" + disciplina.getCodigo() + "';";
		
		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityNotExistException("Disciplina: " + disciplina.getCodigo());
			case 3013 :
				throw new InvalidSizeCollumnsException("Disciplina: "+ disciplina.getCodigo() + "Possui algum do campos fora do limite esperado");
			case 1451 :
				throw new ParentHasChildrenException("Disciplina: " + disciplina.getCodigo() + "possui professores vinculados!");
			case 1474:
				throw new InvalidNameException("Disciplina: " +disciplina.getCodigo());
			}
		}
		
		return false;
	}
	
	public Disciplina getDisciplina(String codigo) throws SQLException{
	
		Disciplina disciplina = null;
				
		String strConsultar = "SELECT codigo, nome, ch FROM disciplina "
				+ "WHERE codigo = '" + codigo + "';"; 

		ResultSet rs = conexao.consulte(strConsultar);
		
		if(rs != null){
			try{ 
				if (rs.next()){ 
					disciplina = new Disciplina();
					disciplina.setCodigo(rs.getString(1));
					disciplina.setNome(rs.getString(2));
					disciplina.setCh(rs.getString(3));
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityNotExistException("Disciplina: " + disciplina.getCodigo());
				}
			}
		}
		return disciplina;
	}
	public List<Disciplina> getTodasDisciplinas() throws SQLException {
		List<Disciplina> disciplinas = new ArrayList<Disciplina>(); 

		ResultSet rs = conexao.consulte("SELECT * FROM disciplina;");
		ResultSetMetaData rsrs;
		Disciplina disciplina = null;

		if (rs != null) {
			try {
				disciplina = new Disciplina();
				rsrs = rs.getMetaData();
				disciplina.setCodigo(rsrs.getColumnLabel(1).toUpperCase());
				disciplina.setNome(rsrs.getColumnLabel(2).toUpperCase());
				disciplinas.add(disciplina);

				while (rs.next()) {
					disciplina = new Disciplina();
					disciplina.setCodigo(rs.getString(1));
					disciplina.setNome(rs.getString(2));
					disciplinas.add(disciplina);

				}
			} catch (SQLException sqle) {
				switch (sqle.getErrorCode()){
				case 3013 :
					throw new InvalidSizeCollumnsException("Disciplina: "+ disciplina.getCodigo() + "Possui algum do campos fora do limite esperado");
				case 1244 :
					throw new EntityNotExistException("Disciplina: " + disciplina.getCodigo());
				}
			}
		}
		return disciplinas;
	}
}