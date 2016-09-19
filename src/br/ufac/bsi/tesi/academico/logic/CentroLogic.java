package br.ufac.bsi.tesi.academico.logic;

import java.sql.SQLException;
import java.util.List;

import br.ufac.bsi.tesi.academico.exception.*;
import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.Centro;
import br.ufac.bsi.tesi.academico.db.Conexao;

public class CentroLogic {

	private CentroDB cdb = new CentroDB();
	
	public boolean addCentro(String sigla, String nome) throws InvalidFieldException, LenghtInvalidFieldException, 
	EntityAlreadyExistException, SQLException, ParentHasChildrenException, NomeInvalidoException{
		
		Centro centro = null;
		String camposInvalidos = "", camposInvalidosTamanho = "", entidadeJaExiste = "Centro de: ";
		boolean falha = false, falhaMax = false;
		
		if (sigla.length()>4){
			camposInvalidosTamanho = camposInvalidosTamanho + "Numero max de caracteres exedido em sigla (Limite de 4)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosTamanho =camposInvalidosTamanho + "Numero max de caracteres exedido em Nome (Limite max de 45)\n";
			falha = true;
		}
		if (sigla.isEmpty()){
			camposInvalidos = camposInvalidos + "Sigla: Vazio!\n";
			falha = true;
		}
		
		if (nome.isEmpty()){
			camposInvalidos = camposInvalidos + "Nome: Vazio!\n";
			falha = true;
		}

		if (falha)
			throw new InvalidFieldException(camposInvalidos);
		
		if (falhaMax)
			throw new LenghtInvalidFieldException(camposInvalidosTamanho);
		
		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		if (centro != null){
			entidadeJaExiste = entidadeJaExiste + "Sigla: "+ centro.getSigla();
			throw new EntityAlreadyExistException(entidadeJaExiste);
		}
		else{
			centro = new Centro();
			centro.setSigla(sigla);
			centro.setNome(nome);
			return cdb.addCentro(centro);
		}

	}

	public boolean updCentro(String sigla, String nome)throws InvalidFieldException, LenghtInvalidFieldException, EntityDontExistException, ParentHasChildrenException, NomeInvalidoException, SQLException{
		Centro centro = null;
		String camposInvalidos = "", camposInvalidosTamanho = "", entidadeNaoExist = "Centro não existe no banco de dados";
		boolean falha = false, falhaMax = false;
		
		if (sigla.length()>4){
			camposInvalidosTamanho = camposInvalidosTamanho + "Numero max de caracteres exedido em sigla (Limite de 4)\n";
			falhaMax = true;
		}
		
		if (nome.length()>45){
			camposInvalidosTamanho =camposInvalidosTamanho + "Numero max de caracteres exedido em Nome (Limite max de 45)\n";
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

		if (centro == null){
			throw new EntityAlreadyExistException(entidadeNaoExist);
		}
		else{
			centro.setNome(nome);			
			return cdb.updCentro(centro);
		}

	}

	public boolean delCentro(String sigla, String nome) throws EntityDontExistException, ParentHasChildrenException, SQLException, NomeInvalidoException{
		String entidadeNaoExist = "Centro não existe no banco de dados";
		Centro centro = null;
		
		if (sigla.isEmpty() || nome.isEmpty())
			return false;

		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		if (centro == null)
			throw new EntityAlreadyExistException(entidadeNaoExist);
		else
			return cdb.delCentro(centro);
	}

	public Centro getCentro(String sigla) throws SQLException{
		Centro centro = null;
		
		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		return centro;
	}	

	public List<Centro> getTodosCentros() throws SQLException{
		return cdb.getTodosCentros();
	}
}
