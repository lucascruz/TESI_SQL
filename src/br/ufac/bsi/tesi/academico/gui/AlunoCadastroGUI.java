package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import javax.swing.*; 					//importando classes do Swing
import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
import java.util.ArrayList;

class AlunoCadastroGUI extends JFrame implements ActionListener {

	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Centro> cmbCentro;
	private JTextField fldMatricula, fldNome, fldRg, fldCpf, fldEndereco, fldFone;
	private JButton btnConfirmar, btnCancelar;

	private String[] operacoesNomes;
	private int operacao;

	private AlunoConsultaGUI pai;
	private Conexao cnx;

	private AlunoLogic alunoLogic = new AlunoLogic();
	private CentroLogic centroLogic = new CentroLogic();	

	AlunoCadastroGUI(AlunoConsultaGUI pai, Conexao cnx){ // método construtor
		super(""); 				// chamando construtor da classe mãe
		setSize(800, 600);		// definindo dimensões da janela	

		this.pai = pai;
		this.cnx = cnx;

		alunoLogic.setConexao(cnx);
		centroLogic.setConexao(cnx);		

		operacoesNomes = new String[]{"Inclusão", "Edicação", "Exclusão"};

		pnlRotulos = new JPanel(new GridLayout(7,1,5,5));
		pnlRotulos.add(new JLabel("Matrícula"));
		pnlRotulos.add(new JLabel("Nome"));
		pnlRotulos.add(new JLabel("RG"));		
		pnlRotulos.add(new JLabel("CPF"));
		pnlRotulos.add(new JLabel("Endereço"));		
		pnlRotulos.add(new JLabel("Fone"));		
		pnlRotulos.add(new JLabel("Centro"));		

		fldMatricula = new JTextField();
		fldNome = new JTextField();		
		fldRg = new JTextField();		
		fldCpf = new JTextField();
		fldEndereco = new JTextField();		
		fldFone = new JTextField();		
		

		cmbCentro = new JComboBox(centroLogic.lstCentros().toArray());
		
		
		
		pnlCampos = new JPanel(new GridLayout(7,1,5,5));
		pnlCampos.add(fldMatricula);
		pnlCampos.add(fldNome);
		pnlCampos.add(fldRg);
		pnlCampos.add(fldCpf);
		pnlCampos.add(fldEndereco);
		pnlCampos.add(fldFone);		
		pnlCampos.add(cmbCentro);		

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
		setTitle(operacoesNomes[operacao]+ " de Aluno");
		fldMatricula.setText("");
		fldNome.setText("");
		fldRg.setText("");
		fldCpf.setText("");		
		fldEndereco.setText("");
		fldFone.setText("");		
		cmbCentro.setSelectedIndex(0);		
		fldMatricula.setEnabled(true);
		fldNome.setEnabled(true);
		fldRg.setEnabled(true);
		fldCpf.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldFone.setEnabled(true);
		cmbCentro.setEnabled(true);
		setVisible(true);	
	}

	public void editar(String matricula){
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Aluno");

		carregarCampos(matricula);

		fldMatricula.setEnabled(false);
		fldNome.setEnabled(true);
		fldRg.setEnabled(true);
		fldCpf.setEnabled(true);
		fldEndereco.setEnabled(true);
		fldFone.setEnabled(true);		
		cmbCentro.setEnabled(true);
		setVisible(true);	
	}

	public void excluir(String matricula){
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Aluno");

		carregarCampos(matricula);

		fldMatricula.setEnabled(false);
		fldNome.setEnabled(false);
		fldRg.setEnabled(false);
		fldCpf.setEnabled(false);
		fldEndereco.setEnabled(false);
		fldFone.setEnabled(false);		
		cmbCentro.setEnabled(false);
		setVisible(true);	
	}

	public void carregarCampos(String matricula){

		Aluno aluno = alunoLogic.getAluno(matricula);
		Centro centro;
		
		if (aluno == null){
			JOptionPane.showMessageDialog(null, "Aluno não encontrado!", 
					"Cadastro de Aluno", JOptionPane.PLAIN_MESSAGE);	
		}else{
			fldMatricula.setText(""+aluno.getMatricula());
			fldNome.setText(aluno.getNome());
			fldRg.setText(""+aluno.getSexo());
			fldCpf.setText(""+aluno.getCurso_codigo());
			fldEndereco.setText(aluno.getEndereco());
			fldFone.setText(aluno.getFone());

			for (int i=0; i < cmbCentro.getModel().getSize(); i++){
				centro = cmbCentro.getModel().getElementAt(i);
				if (centro.getNome().equals(aluno.getMatricula().toString()))
					cmbCentro.setSelectedItem(centro.getNome().toString());
			}
		}

	}


	public void confirmar(){

		Centro centro;
		boolean confirmado;
		
		String matricula = fldMatricula.getText();
		String nome = fldNome.getText();
		String rg = fldRg.getText();
		String cpf = fldCpf.getText();
		String endereco = fldEndereco.getText();
		String fone = fldFone.getText();
	
		centro = (Centro)cmbCentro.getSelectedItem();
		String centro_sigla = centro.getSigla();
		
		switch (operacao) {
		case 0:
			confirmado = alunoLogic.addAluno(matricula, nome, rg, cpf, endereco, fone, centro_sigla);
			break;
		case 1:
			confirmado = alunoLogic.updAluno(matricula, nome, rg, cpf, endereco, fone, centro_sigla);
			break;
		case 2:
			confirmado = alunoLogic.delAluno(matricula, nome, rg, cpf, endereco, fone, centro_sigla);
			break;			
		default:
			confirmado = false;
			break;
		}

		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Aluno realizada com sucesso!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.buscar();
		}else{
			JOptionPane.showMessageDialog(this, "Falha ao incluir o aluno!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
		}

	}

}//Fim da classe AlunoCadastroGUI
