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
import br.ufac.bsi.tesi.academico.logic.Aluno;
import br.ufac.bsi.tesi.academico.logic.Curso;

public class AlunoDB{
	private Conexao conexao = Conexao.getInstacia();
	private CursoDB cdb = new CursoDB();
	private ResultSet rs;


	public boolean addAluno(Aluno aluno) throws SQLException, InvalidNameException, ParentHasChildrenException, EntityAlreadyExistException{
		String strIncluir = "INSERT INTO aluno (matricula, nome, fone, endereco, cep, sexo, curso_codigo) VALUES ('" +
				aluno.getMatricula() + "', '" + 
				aluno.getNome() + "', '" + 
				aluno.getFone() + "', '" + 
				aluno.getEndereco() + "', '" + 
				aluno.getCep() + "', '" + 
				aluno.getSexo() + "', '" + 
				aluno.getCurso().getCodigo() + "');";

		try{		
			return conexao.atualize(strIncluir)>0;
		}catch(SQLException sqle){
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Aluno: "+aluno.getMatricula() + "Possui algum do campos fora do limite esperado");
			case 1062 :
				throw new EntityAlreadyExistException("Aluno: " + aluno.getMatricula());
			case 1451 :
				throw new ParentHasChildrenException("Aluno: " + aluno.getMatricula() + "Possui chave estrangeira vinculada!");
			case 1474:
				throw new InvalidNameException("Aluno: " +aluno.getMatricula());
			}

		}
		return false;
	}

	public boolean updAluno(Aluno aluno)throws SQLException, InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strEditar = "UPDATE aluno " +
				"SET nome = '" + aluno.getNome() + "', " + 
				"    fone = '" + aluno.getFone() + "', " + 
				"	 endereco = '" + aluno.getEndereco() + "', " + 
				"	 cep = '" + aluno.getCep() + "', " +
				"	 sexo = '" + aluno.getSexo() + "', " +
				"	 curso_codigo = '" + aluno.getCurso().getCodigo() + "' "+
				"WHERE matricula = " + aluno.getMatricula() + ";";

		try {
			return (conexao.atualize(strEditar)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Aluno: "+aluno.getMatricula() + "Possui algum do campos fora do limite esperado");
			case 1244 :
				throw new EntityNotExistException("Aluno: " + aluno.getMatricula());
			case 1451 :
				throw new ParentHasChildrenException("Aluno: " + aluno.getMatricula() + "Possui chave estrangeira vinculada!");
			case 1474:
				throw new InvalidNameException("Aluno: " +aluno.getMatricula());
			}
		}

		return false;
	}

	public boolean delAluno(Aluno aluno)throws SQLException, InvalidNameException, ParentHasChildrenException, EntityNotExistException{
		String strExcluir = "DELETE FROM aluno "
				+ "WHERE matricula = '" + aluno.getMatricula() + "';";

		try {
			return (conexao.atualize(strExcluir)>0);
		} catch (SQLException sqle) {
			switch (sqle.getErrorCode()){
			case 3013 :
				throw new InvalidSizeCollumnsException("Aluno: "+aluno.getMatricula() + "Possui algum do campos fora do limite esperado");
			case 1244 :
				throw new EntityNotExistException("Aluno: " + aluno.getMatricula());
			case 1451 :
				throw new ParentHasChildrenException("Aluno: " + aluno.getMatricula() + "Possui chave estrangeira vinculada!");
			case 1474:
				throw new InvalidNameException("Aluno: " +aluno.getMatricula());
			}
		}
		return false;
	}

	public Aluno getAluno(String matricula) throws SQLException{

		Aluno aluno = null;
		Curso curso = null;

		String strConsultar = "SELECT matricula, nome, fone, endereco, cep, sexo, curso_codigo" 
				+ " FROM aluno "
				+ " WHERE matricula = " + matricula + ";"; 

		rs = conexao.consulte(strConsultar);

		if(rs != null){
			try{ 
				if (rs.next()){ 
					aluno = new Aluno();
					aluno.setMatricula(rs.getString(1));
					aluno.setNome(rs.getString(2));
					aluno.setFone(rs.getString(3));
					aluno.setEndereco(rs.getString(4));
					aluno.setCep(rs.getString(5));
					aluno.setSexo(rs.getString(6));

					curso = cdb.getCurso(rs.getString(7));

					if (curso != null){
						aluno.setCurso(curso);
					}
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 1244 :
					throw new EntityNotExistException("Aluno: " + aluno.getMatricula());
				}
			}
		}
		return aluno;
	}
	public List<Aluno> getTodosAlunos() throws SQLException, InvalidNameException {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Curso curso = null;
		Aluno aluno = null;

		String strConsultar = "SELECT matricula, nome, fone, endereco, cep, sexo, curso_codigo"
				+ " FROM aluno;"; 

		rs = conexao.consulte(strConsultar);

		if(rs != null){
			try{ 
				while (rs.next()){
					aluno = new Aluno();
					aluno.setMatricula(rs.getString(1));
					aluno.setNome(rs.getString(2));
					aluno.setFone(rs.getString(3));
					aluno.setEndereco(rs.getString(4));
					aluno.setCep(rs.getString(5));
					aluno.setSexo(rs.getString(6));

					curso = cdb.getCurso(rs.getString(7));

					if (curso != null){
						aluno.setCurso(curso);
					}
					alunos.add(aluno);
				}
			}catch(SQLException sqle){
				switch (sqle.getErrorCode()){
				case 3013 :
					throw new InvalidSizeCollumnsException("Aluno: "+aluno.getMatricula() + "Possui algum do campos fora do limite esperado");
				case 1244 :
					throw new EntityNotExistException("Aluno: " + aluno.getMatricula());
				case 1474:
					throw new InvalidNameException("Aluno: " +aluno.getMatricula());
				}
			}
		}
		return alunos;
	}
}
