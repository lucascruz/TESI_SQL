package br.ufac.bsi.tesi.academico.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufac.bsi.tesi.academico.exception.*;

import br.ufac.bsi.tesi.academico.logic.Disciplina;

public class DisciplinaDB {
	private Conexao conexao = Conexao.getInstacia();
	

	public boolean addDisciplina(Disciplina disciplina)throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityAlreadyExistException{
		String strIncluir = "INSERT INTO disciplina (codigo, nome, ch) VALUES ('" + disciplina.getCodigo()
		 + "', '" + disciplina.getNome()+ "', '"+ disciplina.getCh() +"');";
	
		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 1062 :
				throw new EntityAlreadyExistException("Disciplina: " + disciplina.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Disciplina: " + disciplina.getCodigo() + "possui professores vinculados!");
			case 1474:
				throw new NomeInvalidoException("Disciplina: " +disciplina.getCodigo());
			}
			
		}
		return false;
	}
	
	public boolean updDisciplina(Disciplina disciplina)throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityDontExistException{
		String strEditar = "UPDATE disciplina " + "SET nome = '" + disciplina.getNome() +"' " + "SET ch = '" +
				disciplina.getCh()+ "' " + "WHERE codigo = '" + disciplina.getCodigo() + "';";
		
		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityDontExistException("Disciplina: " + disciplina.getCodigo());
			case 1451 :// essa n tem chave estangeira mas como é uma disciplina acredito que futuramente pode ser feita
				//uma chave assim para ela
				throw new ParentHasChildrenException("Disciplina: " + disciplina.getCodigo() + "possui cursos vinculados!");
			case 1474:
				throw new NomeInvalidoException("Disciplina: " +disciplina.getCodigo());
			}
		}
		
		return false;
	}
	
	public boolean delDisciplina(Disciplina disciplina)throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityDontExistException{
		String strExcluir = "DELETE FROM disciplina "
				+ "WHERE codigo = '" + disciplina.getCodigo() + "';";
		
		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityDontExistException("Disciplina: " + disciplina.getCodigo());
			case 1451 :
				throw new ParentHasChildrenException("Disciplina: " + disciplina.getCodigo() + "possui professores vinculados!");
			case 1474:
				throw new NomeInvalidoException("Disciplina: " +disciplina.getCodigo());
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
					throw new EntityDontExistException("Disciplina: " + disciplina.getCodigo());
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
				case 1244 :
					throw new EntityDontExistException("Disciplina: " + disciplina.getCodigo());
				}
			}
		}
		return disciplinas;
	}
}