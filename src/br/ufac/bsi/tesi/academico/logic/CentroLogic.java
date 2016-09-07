package br.ufac.bsi.tesi.academico.logic;

import br.ufac.bsi.tesi.academico.db.*;

public class CentroLogic {

	private CentroDB cdb = new CentroDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}

	public boolean addCentro(String sigla, String nome){
		Centro centro = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		if (centro != null)
			return false;
		else{
			centro = new Centro();
			centro.setSigla(sigla);
			centro.setNome(nome);
			return cdb.addCentro(centro);
		}

	}

	public boolean updCentro(String sigla, String nome){
		Centro centro = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		if (centro == null)
			return false;
		else{
			centro.setNome(nome);			
			return cdb.updCentro(centro);
		}

	}

	public boolean delCentro(String sigla, String nome){
		Centro centro = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		if (centro == null)
			return false;
		else
			return cdb.delCentro(centro);
	}

	public Centro getCentro(String sigla){
		Centro centro = null;
		
		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		return centro;
	}	
	
}
