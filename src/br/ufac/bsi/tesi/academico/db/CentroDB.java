package br.ufac.bsi.tesi.academico.db;

import java.util.*;

import javax.swing.JOptionPane;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufac.bsi.tesi.academico.logic.Centro;
public class CentroDB {

	private Conexao conexao;
	private ResultSet rs; 
	public void setConexao(Conexao conexao){
		this.conexao = conexao;
	}

	public boolean addCentro(Centro centro){
		String strIncluir = "INSERT INTO centro (sigla, nome) VALUES (" +
				"'" + centro.getSigla() +"', '" + centro.getNome() +"');";		
		return (conexao.atualize(strIncluir)>0);

	}

	public boolean updCentro(Centro centro){
		String strEditar = "UPDATE centro "
				+ "SET nome = '" + centro.getNome() +"' "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		return (conexao.atualize(strEditar)>0);

	}

	public boolean delCentro(Centro centro){
		String strExcluir = "DELETE FROM centro "
				+ "WHERE sigla = '" + centro.getSigla() + "';";

		return (conexao.atualize(strExcluir)>0);

	}

	public Centro getCentro(String sigla){

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
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return centro;
	}

	public List<Centro> getTodosCentros(){

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
				JOptionPane.showMessageDialog(null, sqle.getErrorCode(), sqle.getMessage(), 
						JOptionPane.PLAIN_MESSAGE);
			}
		}
		return centros;
	}
}


