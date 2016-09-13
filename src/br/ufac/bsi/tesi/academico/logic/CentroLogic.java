package br.ufac.bsi.tesi.academico.logic;

import java.util.List;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.Centro;
import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.exception.*;
public class CentroLogic {

	private CentroDB cdb = new CentroDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}

	public boolean addCentro(String sigla, String nome) throws InvalidFieldException, LenghtInvalidFieldException {
		Centro centro = null;
		String camposInvalidos = "", camposInvalidosTamanho = "";
		String entidadeJaExiste = "Centro: ";
		boolean falha = false, falhaMax = false;
		
		
		if (nome.length()>45){
			camposInvalidosTamanho =camposInvalidosTamanho + "Numero max de caracteres exedido em Nome (Limite max de 45)\n";
			falha = true;
		}
		
		if (sigla.length()>4){
			camposInvalidosTamanho = camposInvalidosTamanho + "Numero max de caracteres exedido em sigla (Limite de 4)\n";
			falhaMax = true;
		}
		
		if (sigla.isEmpty()){
			camposInvalidos = camposInvalidos + "Sigla: Vazio!";
			falha = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!";
			falha = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);
		
		if (falhaMax)
			throw new LenghtInvalidFieldException(camposInvalidosTamanho);
		
		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		if (centro != null){
			entidadeJaExiste= entidadeJaExiste+ centro.getSigla();
			throw new EntityAlreadyExistException(entidadeJaExiste);
			}
		else{
			centro = new Centro();
			centro.setSigla(sigla);
			centro.setNome(nome);
			return cdb.addCentro(centro);
		}

	}

	public boolean updCentro(String sigla, String nome)throws InvalidFieldException, LenghtInvalidFieldException{
		Centro centro = null;
		String camposInvalidos = "", camposInvalidosTamanho = "";
		boolean falha = false, falhaMax = false;
		
		if (sigla.length()>4){
			camposInvalidosTamanho = camposInvalidosTamanho + "Numero max de caracteres em sigla (Limite de 4)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosTamanho =camposInvalidosTamanho + "Numero max de caracteres em Nome (Limite max de 45)\n";
			falha = true;
		}
		if (sigla.isEmpty()){
			camposInvalidos = camposInvalidos + "Sigla: Vazio!";
			falha = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!";
			falha = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);
		
		if (falhaMax)
			throw new LenghtInvalidFieldException(camposInvalidosTamanho);

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

	public List<Centro> getTodosCentros(){
		return cdb.getTodosCentros();
	}
}
