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

import br.ufac.bsi.tesi.academico.exception.*;
import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.logic.Curso;
import br.ufac.bsi.tesi.academico.logic.CursoLogic;

@SuppressWarnings("serial")
public class CursoCadastroGUI extends JFrame implements ActionListener{
		private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
		private JTextField fldCodigo, fldNome;
		private JButton btnConfirmar, btnCancelar;

		private String[] operacoesNomes;
		private int operacao;

		private CursoConsultaGUI pai;

		private Conexao cnx = Conexao.getInstacia();

		private CursoLogic cursoLogic = new CursoLogic();	

		public CursoCadastroGUI(CursoConsultaGUI pai, Conexao cnx){ 
			super(""); 				// chamando construtor da classe mÃ£e
			setSize(600, 600);		// definindo dimensÃµes da janela	

			this.pai = pai;
			this.cnx = cnx;


			operacoesNomes = new String[]{"Inclusao", "Edicao", "Exclusao"};

			pnlRotulos = new JPanel(new GridLayout(2,1,5,5));
			pnlRotulos.add(new JLabel("Codigo"));
			pnlRotulos.add(new JLabel("Nome"));		

			fldCodigo = new JTextField();
			fldNome = new JTextField();			

			pnlCampos = new JPanel(new GridLayout(2,1,5,5));
			pnlCampos.add(fldCodigo);
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
			setTitle(operacoesNomes[operacao]+ " de Curso");
			
			fldCodigo.setText("");
			fldNome.setText("");		
			fldCodigo.setEnabled(true);
			fldNome.setEnabled(true);
			setVisible(true);	
		}

		public void editar(String codigo) throws SQLException{
			operacao = 1;		
			setTitle(operacoesNomes[operacao]+ " de Curso");

			carregarCampos(codigo);

			fldCodigo.setEnabled(false);
			fldNome.setEnabled(true);
			setVisible(true);	
		}

		public void excluir(String codigo) throws SQLException{
			operacao = 2;		
			setTitle(operacoesNomes[operacao]+ " de Curso");

			carregarCampos(codigo);

			fldCodigo.setEnabled(false);
			fldNome.setEnabled(false);
			setVisible(true);	
		}

		public void carregarCampos(String codigo) throws SQLException{

			Curso curso = cursoLogic.getCurso(codigo);

			if (curso == null){
				JOptionPane.showMessageDialog(null, "Curso nao encontrado!", 
						"Cadastro de Curso", JOptionPane.PLAIN_MESSAGE);	
			}else{
				fldCodigo.setText(curso.getCodigo());
				fldNome.setText(curso.getNome());
			}
		}

		public void confirmar(){
			boolean confirmado=true;

			String codigo = fldCodigo.getText();
			String nome = fldNome.getText();
			
			switch (operacao) {
			case 0:
				try{
					confirmado = cursoLogic.addCurso(codigo, nome);
				}catch(InvalidFieldException e){
					JOptionPane.showMessageDialog(null, e, 
							"Campos Vazio", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(LenghtInvalidFieldException eoo){
					JOptionPane.showMessageDialog(null, eoo, 
							"Tamanho maximo atingido", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(NumberErroException number){
					JOptionPane.showMessageDialog(null, number, 
							"Erro De Caracter", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(EntityAlreadyExistException exst){
					JOptionPane.showMessageDialog(null, exst, 
							"\nCurso Já Existe", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (ParentHasChildrenException chave) {
					JOptionPane.showMessageDialog(null, chave, 
							"ERRO CHAVE ESTRANGEIRA", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}  catch (NomeInvalidoException nomee) {
					JOptionPane.showMessageDialog(null, nomee, 
							"ERRO NOME VAZIO: \n", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle, 
							"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}
				break;
			case 1:
				try{
					confirmado = cursoLogic.updCurso(codigo, nome);
				}catch(InvalidFieldException e){
					JOptionPane.showMessageDialog(null, e, 
							"Campos Vazio", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
					}catch(LenghtInvalidFieldException eoo){
					JOptionPane.showMessageDialog(null, eoo, 
							"Tamanho maximo atingido", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(NumberErroException number){
					JOptionPane.showMessageDialog(null, number, 
							"Erro De Caracter", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}catch(EntityDontExistException jamais){
					JOptionPane.showMessageDialog(null, jamais, 
							"CURSO NÃO EXISTE", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (ParentHasChildrenException chave) {
					JOptionPane.showMessageDialog(null, chave, 
							"ERRO CHAVE ESTRANGEIRA", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (NomeInvalidoException nomee) {
					JOptionPane.showMessageDialog(null, nomee, 
							"ERRO NOME VAZIO: \n", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle, 
							"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}
				break;
			case 2:
				try{
					confirmado = cursoLogic.delCurso(nome, codigo);
				}catch(EntityDontExistException jamais){
					JOptionPane.showMessageDialog(null, jamais, 
							"CURSO NÃO EXISTE", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (ParentHasChildrenException chave) {
					JOptionPane.showMessageDialog(null, chave, 
							"ERRO CHAVE ESTRANGEIRA", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				} catch (NomeInvalidoException nomee) {
					JOptionPane.showMessageDialog(null, nomee, 
							"ERRO NOME VAZIO: \n", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;   
				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(null, sqle, 
							"Erro de SQL", JOptionPane.PLAIN_MESSAGE);
					confirmado = false;
				}
				break;			
			default:
				confirmado = false;
				break;
			}

			if (confirmado){
				JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Curso realizada com sucesso!", "Academico", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				pai.setVisible(true);
				pai.atualize();
			}else{
				JOptionPane.showMessageDialog(this, "Falha na "+ operacoesNomes[operacao]+ " do Curso!", "Academico", JOptionPane.INFORMATION_MESSAGE);
				confirmado = true;
			}

		}

	}
