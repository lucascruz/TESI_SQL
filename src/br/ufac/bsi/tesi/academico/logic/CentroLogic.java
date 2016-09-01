package br.ufac.bsi.tesi.academico.logic;


import br.ufac.bsi.tesi.academico.db.Centro;
import br.ufac.bsi.tesi.academico.db.CentroDB;
import br.ufac.bsi.tesi.academico.db.Conexao;

public class CentroLogic {
	private CentroDB cdb = new CentroDB();
	
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}
	public boolean addCentro(String sigla, String nome){
		Centro centro = null;
		
		if(sigla.isEmpty() || nome.isEmpty())
			return false;
		if(!sigla.isEmpty())
			centro = cdb.getCentro(sigla);
		if(centro != null)
			return false;
		
		centro.setSigla(sigla);
		centro.setNome(nome);
		return cdb.addCentro(centro);
	}
	
	@SuppressWarnings("null")
	public boolean updCentro(String sigla, String nome){
		Centro centro = null;
		
		if(sigla.isEmpty() || nome.isEmpty())
			return false;
		if(!sigla.isEmpty())
			centro = cdb.getCentro(sigla);
		if(centro != null)
			return false;
		else{
			centro.setNome(nome);
			return cdb.updCentro(centro);
		}		
	}
	
	public boolean delCentro (String sigla, String nome){
		Centro centro = null;
		
		if(sigla.isEmpty() || nome.isEmpty())
			return false;
		if(!sigla.isEmpty())
			centro = cdb.getCentro(sigla);
		if(centro != null)
			return false;
		else{
			return cdb.delCentro(centro);
		}		
		
	}
}
