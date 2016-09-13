package br.ufac.bsi.tesi.academico.gui;

import javax.swing.*; 					
import java.awt.*; 						
import java.awt.event.*;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

@SuppressWarnings("serial")
public class CentroCadastroGUI extends JFrame implements ActionListener {

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JTextField fldSigla, fldNome;
	private JButton btnConfirmar, btnCancelar;

	private String[] operacoesNomes;
	private int operacao;

	private CentroConsultaGUI pai;
	@SuppressWarnings("unused")
	private Conexao cnx;

	private CentroLogic centroLogic = new CentroLogic();	

	CentroCadastroGUI(CentroConsultaGUI pai, Conexao cnx){ 
		super(""); 				// chamando construtor da classe mãe
		setSize(600, 600);		// definindo dimensões da janela	

		this.pai = pai;
		this.cnx = cnx;

		centroLogic.setConexao(cnx);		

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

	} //Fim do método construtor

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

	public void editar(String sigla){
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Centro");

		carregarCampos(sigla);

		fldSigla.setEnabled(false);
		fldNome.setEnabled(true);
		setVisible(true);	
	}

	public void excluir(String sigla){
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Centro");

		carregarCampos(sigla);


		fldSigla.setEnabled(false);
		fldNome.setEnabled(false);
		setVisible(true);	
	}

	public void carregarCampos(String sigla){

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
		boolean confirmado;

		String sigla = fldSigla.getText();
		String nome = fldNome.getText();
		
		switch (operacao) {
		case 0:
			confirmado = centroLogic.addCentro(sigla, nome);
			break;
		case 1:
			confirmado = centroLogic.updCentro(sigla, nome);
			break;
		case 2:
			confirmado = centroLogic.delCentro(sigla, nome);
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
		}

	}

}