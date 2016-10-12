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
public class CentroDB {

	private Conexao conexao;
	private ResultSet rs; 

	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addCentro(Centro centro)throws SQLException, InvalidNameException, ParentHasChildrenException{
		String strIncluir = "INSERT INTO centro (sigla, nome) VALUES (" +
				"'" + centro.getSigla() +"', '" + centro.getNome() +"');";		

		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Centro: "+ centro.getSigla() + "Possui algum do campos fora do limite esperado");
			case 1062 :
				throw new EntityAlreadyExistException("Centro: " + centro.getSigla());
			case 1451 :
				throw new ParentHasChildrenException("Centro: " + centro.getSigla() + "possui professores vinculados!");
			case 1474:
				throw new InvalidNameException("Centro: " +centro.getSigla());
			}

		}
		return false;
	}

	public boolean updCentro(Centro centro) throws SQLException,InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strEditar = "UPDATE centro "
				+ "SET nome = '" + centro.getNome() +"' "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Centro: "+ centro.getSigla() + "Possui algum do campos fora do limite esperado");
			case 1244 :
				throw new EntityNotExistException("Centro: " + centro.getSigla());
			case 1451 :
				throw new ParentHasChildrenException("Centro: " + centro.getSigla() + "possui professores vinculados!");
			case 1474:
				throw new InvalidNameException("Centro: " +centro.getSigla());
			}
		}

		return false;
	}

	public boolean delCentro(Centro centro) throws SQLException, InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strExcluir = "DELETE FROM centro "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		try {
			return (conexao.atualize(strExcluir)>0);
		}catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Centro: "+ centro.getSigla() + "Possui algum do campos fora do limite esperado");
			case 1244 :
				throw new EntityNotExistException("Centro: " + centro.getSigla());
			case 1451 :
				throw new ParentHasChildrenException("Centro: " + centro.getSigla() + "possui professores vinculados!");
			case 1474:
				throw new InvalidNameException("Centro: " +centro.getSigla());
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
				case 3013 :
					throw new InvalidSizeCollumnsException("Centro: "+ centro.getSigla() + "Possui algum do campos fora do limite esperado");
				case 1244 :
					throw new EntityNotExistException("Centro: " + centro.getSigla());
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
				case 3013 :
					throw new InvalidSizeCollumnsException("Centro: "+ centro.getSigla() + "Possui algum do campos fora do limite esperado");
				case 1244 :
					throw new EntityNotExistException("Centro: " + centro.getSigla());
				}
			}
		}
		return centros;
	}

}


