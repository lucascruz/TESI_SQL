package br.ufac.bsi.tesi.academico.db;

import java.util.*;
import javax.swing.JOptionPane;

import br.ufac.bsi.tesi.academico.exception.*;


import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufac.bsi.tesi.academico.logic.Centro;
public class CentroDB {

	private Conexao conexao = Conexao.getInstacia();
	private ResultSet rs; 


	public boolean addCentro(Centro centro)throws SQLException, NomeInvalidoException, ParentHasChildrenException{
		String strIncluir = "INSERT INTO centro (sigla, nome) VALUES (" +
				"'" + centro.getSigla() +"', '" + centro.getNome() +"');";		

		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 1062 :
				throw new EntityAlreadyExistException("Centro: " + centro.getSigla());
			case 1451 :
				throw new ParentHasChildrenException("Centro: " + centro.getSigla() + "possui professores vinculados!");
			case 1474:
				throw new NomeInvalidoException("Centro: " +centro.getSigla());
			}

		}
		return false;
	}

	public boolean updCentro(Centro centro) throws SQLException,NomeInvalidoException, ParentHasChildrenException, EntityDontExistException{
		String strEditar = "UPDATE centro "
				+ "SET nome = '" + centro.getNome() +"' "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityDontExistException("Centro: " + centro.getSigla());
			case 1451 :
				throw new ParentHasChildrenException("Centro: " + centro.getSigla() + "possui professores vinculados!");
			case 1474:
				throw new NomeInvalidoException("Centro: " +centro.getSigla());
			}
		}

		return false;
	}

	public boolean delCentro(Centro centro) throws SQLException, NomeInvalidoException, ParentHasChildrenException, EntityDontExistException{
		String strExcluir = "DELETE FROM centro "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 1244 :
				throw new EntityDontExistException("Centro: " + centro.getSigla());
			case 1451 :
				throw new ParentHasChildrenException("Centro: " + centro.getSigla() + "possui professores vinculados!");
			case 1474:
				throw new NomeInvalidoException("Centro: " +centro.getSigla());
			}
		}

		return false;
	}

	public Centro getCentro(String sigla) throws SQLException{

		Centro centro = null;

		String strConsultar = "SELECT sigla, nome FROM centro "
				+ "WHERE sigla = '" + sigla + "';"; 

		rs = conexao.consulte(strConsultar);

		if(rs != null){
			try{ 
				if (rs.next()){ 
					centro = new Centro();
					centro.setSigla(rs.getString(1));
					centro.setNome(rs.getString(2));									
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityDontExistException("Centro: " + centro.getSigla());
				}
			}
		}
		return centro;
	}

	public List<Centro> getTodosCentros() throws SQLException{

		List<Centro> centros = new ArrayList<Centro>();
		Centro centro = null;

		String strConsultar = "SELECT sigla, nome"
				+ " FROM centro;"; 

		rs = conexao.consulte(strConsultar);

		if(rs != null){
			try{ 
				while (rs.next()){
					centro = new Centro();
					centro.setSigla(rs.getString(1));
					centro.setNome(rs.getString(2));
					centros.add(centro);
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityDontExistException("Centro: " + centro.getSigla());
				}
			}
		}
		return centros;
	}
}


