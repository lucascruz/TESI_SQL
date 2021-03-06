package br.ufac.bsi.tesi.academico.logic;

import java.sql.SQLException;
import java.util.List;

import br.ufac.bsi.tesi.academico.db.CentroDB;
import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.exception.DataBaseNotConnectedException;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;

public class CentroLogic {

	private CentroDB cdb = new CentroDB();
	
	public void setConexao(Conexao conexao){
		cdb.setConexao(conexao);
	}
	
	public boolean addCentro(String sigla, String nome) throws InvalidFieldException, InvalidLenghtFieldException, 
	EntityAlreadyExistException, SQLException, ParentHasChildrenException, InvalidNameException, DataBaseNotConnectedException, EntityNotExistException{
		
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
			throw new InvalidFieldException(camposInvalidos, camposInvalidos);
		
		if (falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosTamanho);
		
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

	public boolean updCentro(String sigla, String nome)throws InvalidFieldException, InvalidLenghtFieldException, EntityNotExistException, ParentHasChildrenException, InvalidNameException, SQLException, DataBaseNotConnectedException, EntityAlreadyExistException{
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
			throw new InvalidFieldException(camposInvalidos, camposInvalidos);
		
		if (falhaMax)
			throw new InvalidLenghtFieldException(camposInvalidosTamanho);

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

	public boolean delCentro(String sigla, String nome) throws EntityNotExistException, ParentHasChildrenException, SQLException, InvalidNameException, DataBaseNotConnectedException, EntityAlreadyExistException{
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

	public Centro getCentro(String sigla) throws SQLException, DataBaseNotConnectedException, EntityNotExistException{
		Centro centro = null;
		
		if (!sigla.isEmpty())
			centro = cdb.getCentro(sigla);

		return centro;
	}	

	public List<Centro> getTodosCentros() throws SQLException, DataBaseNotConnectedException, EntityNotExistException{
		return cdb.getTodosCentros();
	}

}
