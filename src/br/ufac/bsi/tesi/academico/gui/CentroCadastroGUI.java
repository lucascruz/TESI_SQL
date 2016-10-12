package br.ufac.bsi.tesi.academico.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.exception.EntityAlreadyExistException;
import br.ufac.bsi.tesi.academico.exception.EntityNotExistException;
import br.ufac.bsi.tesi.academico.exception.InvalidFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidLenghtFieldException;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.exception.ParentHasChildrenException;
import br.ufac.bsi.tesi.academico.logic.Centro;
import br.ufac.bsi.tesi.academico.logic.CentroLogic;

public class CentroCadastroGUI extends JFrame implements ActionListener {

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JTextField fldSigla, fldNome;
	private JButton btnConfirmar, btnCancelar;

	private String[] operacoesNomes;
	private int operacao;

	private CentroConsultaGUI pai;
	private Conexao cnx;

	private CentroLogic centroLogic = new CentroLogic();	

	public CentroCadastroGUI(CentroConsultaGUI pai, Conexao cnx){ 
		super(""); 				// chamando construtor da classe mÃ£e
		setSize(600, 600);		// definindo dimensÃµes da janela	

		this.pai = pai;
		this.cnx = cnx;

		operacoesNomes = new String[]{"Inclusao", "Edicao", "Exclusao"};

		pnlRotulos = new JPanel(new GridLayout(2,1,5,5));
		pnlRotulos.add(new JLabel("Sigla"));
		pnlRotulos.add(new JLabel("Nome"));		

		fldSigla = new JTextField();
		fldNome = new JTextField();			

		pnlCampos = new JPanel(new GridLayout(2,1,5,5));
		pnlCampos.add(fldSigla);
		pnlCampos.add(fldNome);	

		pnlControles = new JPanel(new BorderLayout(5,5));
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlCampos);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setMnemonic('f');		
		btnConfirmar.addActionListener(this);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setMnemonic('C');		
		btnCancelar.addActionListener(this);

		pnlOperacoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlOperacoes.add(btnConfirmar);
		pnlOperacoes.add(btnCancelar);

		add(pnlControles);
		add(pnlOperacoes, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);

	} //Fim do mÃ©todo construtor

	@Override
	public void actionPerformed(ActionEvent e){

		if (e.getSource() == btnConfirmar){
			confirmar();
		}	

		if (e.getSource() == btnCancelar){
			setVisible(false);
			pai.setVisible(true);
		}	

	}

	public void incluir(){
		operacao = 0;		
		setTitle(operacoesNomes[operacao]+ " de Centro");
		fldSigla.setText("");
		fldNome.setText("");		
		fldSigla.setEnabled(true);
		fldNome.setEnabled(true);
		setVisible(true);	
	}

	public void editar(String sigla) throws SQLException{
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Centro");

		carregarCampos(sigla);

		fldSigla.setEnabled(false);
		fldNome.setEnabled(true);
		setVisible(true);	
	}

	public void excluir(String sigla) throws SQLException{
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Centro");

		carregarCampos(sigla);


		fldSigla.setEnabled(false);
		fldNome.setEnabled(false);
		setVisible(true);	
	}

	public void carregarCampos(String sigla) throws SQLException{

		Centro centro = centroLogic.getCentro(sigla);

		if (centro == null){
			JOptionPane.showMessageDialog(null, "Centro nao encontrado!", 
					"Cadastro de Centro", JOptionPane.PLAIN_MESSAGE);	
		}else{
			fldSigla.setText(""+centro.getSigla());
			fldNome.setText(centro.getNome());
		}
	}

	public void confirmar(){
		boolean confirmado=true;

		String sigla = fldSigla.getText();
		String nome = fldNome.getText();
		
		switch (operacao) {
		case 0:
			try{
				confirmado = centroLogic.addCentro(sigla, nome);
			}catch(InvalidFieldException e){
				JOptionPane.showMessageDialog(null, e, 
						"Campos Vazio", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(InvalidLenghtFieldException eoo){
				JOptionPane.showMessageDialog(null, eoo, 
						"Tamanho maximo atingido", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(EntityAlreadyExistException exst){
				JOptionPane.showMessageDialog(null, exst, 
						"\nCentro Já Existe", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (ParentHasChildrenException parente) {
				JOptionPane.showMessageDialog(null, parente, 
						"ERRO CHAVE PRIMARIA", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (InvalidNameException nomee) {
				JOptionPane.showMessageDialog(null, nomee, 
						"ERRO NOME VAZIO", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, sqle, 
						"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}
			break;
		case 1:
			try{
				confirmado = centroLogic.updCentro(sigla, nome);
			}catch(InvalidFieldException e){
				JOptionPane.showMessageDialog(null, e, 
						"Campos Vazio", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(InvalidLenghtFieldException eoo){
				JOptionPane.showMessageDialog(null, eoo, 
						"Tamanho maximo atingido", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}catch(EntityNotExistException jamais){
				JOptionPane.showMessageDialog(null, jamais, 
						"CENTRO NÃO EXISTE", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (ParentHasChildrenException chave) {
				JOptionPane.showMessageDialog(null, chave, 
						"ERRO CHAVE ESTRANGEIRA", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (InvalidNameException nomee) {
				JOptionPane.showMessageDialog(null, nomee, 
						"ERRO NOME VAZIO", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, sqle, 
						"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}
			break;
		case 2:
			try{
				confirmado = centroLogic.delCentro(sigla, nome);
			}catch(EntityNotExistException jamais){
				JOptionPane.showMessageDialog(null, jamais, 
						"CENTRO NÃO EXISTE", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (ParentHasChildrenException chave) {
				JOptionPane.showMessageDialog(null, chave, 
						"ERRO CHAVE ESTRANGEIRA", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, sqle, 
						"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			} catch (InvalidNameException nomee) {
				JOptionPane.showMessageDialog(null, nomee, 
						"ERRO NOME VAZIO: \n", JOptionPane.PLAIN_MESSAGE);
				confirmado = false;
			}
			break;			
		default:
			confirmado = false;
			break;
		}

		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Centro realizada com sucesso!", "Academico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.atualize();
		}else{
			JOptionPane.showMessageDialog(this, "Falha na "+ operacoesNomes[operacao]+" do centro!", "Academico", JOptionPane.INFORMATION_MESSAGE);
			confirmado = true;
		}

	}

}