package br.ufac.bsi.tesi.academico.gui;


import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;
import javax.swing.*; 					//importando classes do Swing
import java.awt.*; 						//importando classes do AWT
import java.awt.event.*; 				//importando classes de EVENTOS do AWT
@SuppressWarnings("serial")
class ProfessorCadastroGUI extends JFrame implements ActionListener {
	int matricula =0, cpf=0, rg=0;
	private JPanel pnlControles, pnlOperacoes, pnlRotulos, pnlCampos;
	private JComboBox<Centro> cmbCentro;
	private JTextField fldMatricula, fldNome, fldRg, fldCpf, fldEndereco, fldFone;
	private JButton btnConfirmar, btnCancelar;

	private String[] operacoesNomes;
	private int operacao;

	private ProfessorConsultaGUI pai;
	@SuppressWarnings("unused")
	private Conexao cnx;

	private ProfessorLogic professorLogic = new ProfessorLogic();
	private CentroLogic centroLogic = new CentroLogic();	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	ProfessorCadastroGUI(ProfessorConsultaGUI pai, Conexao cnx){ // mÃ©todo construtor
		super(""); 				// chamando construtor da classe mÃ£e
		setSize(600, 350);		// definindo dimensÃµes da janela	

		this.pai = pai;
		this.cnx = cnx;

		professorLogic.setConexao(cnx);
		centroLogic.setConexao(cnx);		

		operacoesNomes = new String[]{"Inclusao", "Edicao", "Exclusao"};

		pnlRotulos = new JPanel(new GridLayout(7,1,5,5));
		pnlRotulos.add(new JLabel("Matricula"));
		pnlRotulos.add(new JLabel("Nome"));
		pnlRotulos.add(new JLabel("RG"));		
		pnlRotulos.add(new JLabel("CPF"));
		pnlRotulos.add(new JLabel("Endereco"));		
		pnlRotulos.add(new JLabel("Telefone"));		
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
		setTitle(operacoesNomes[operacao]+ " de Professor");
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

	public void editar(int matricula){
		operacao = 1;		
		setTitle(operacoesNomes[operacao]+ " de Professor");

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


	public void excluir(int matricula){
		operacao = 2;		
		setTitle(operacoesNomes[operacao]+ " de Professor");

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

	public void carregarCampos(int matricula){

		Professor professor = professorLogic.getProfessor(matricula);
		Centro centro;
		
		if (professor == null){
			JOptionPane.showMessageDialog(null, "Professor nÃ£o encontrado!", 
					"Cadastro de Professor", JOptionPane.PLAIN_MESSAGE);	
		}else{
			fldMatricula.setText(""+professor.getMatricula());
			fldNome.setText(professor.getNome());
			fldRg.setText(""+professor.getRg());
			fldCpf.setText(""+professor.getCpf());
			fldEndereco.setText(professor.getEndereco());
			fldFone.setText(professor.getFone());

			for (int i=0; i < cmbCentro.getModel().getSize(); i++){
				centro = cmbCentro.getModel().getElementAt(i);
				if (centro.getNome().equals(professor.getCentro().getNome()))
					cmbCentro.setSelectedItem(centro);
			}
		}

	}


	public void confirmar()throws NumberFormatException{
		Centro centro;
		boolean confirmado;
		String camposNumericosInvalidos = "";
		boolean teste = false;

			matricula = Integer.parseInt(fldMatricula.getText());
			rg = Integer.parseInt(fldRg.getText());
			
			cpf = Integer.parseInt(fldCpf.getText());
		

		String nome = fldNome.getText();
		String endereco = fldEndereco.getText();
		String fone = fldFone.getText();
		centro = (Centro)cmbCentro.getSelectedItem();
		String centroSigla = centro.getSigla();
		
		switch (operacao) {
		case 0:
			confirmado = professorLogic.addProfessor(matricula, nome, rg, cpf, endereco, fone, centroSigla);
			break;
		case 1:
			confirmado = professorLogic.updProfessor(matricula, nome, rg, cpf, endereco, fone, centroSigla);
			break;
		case 2:
			confirmado = professorLogic.delProfessor(matricula, nome, rg, cpf, endereco, fone, centroSigla);
			break;			
		default:
			confirmado = false;
			break;
		}
		
		if (confirmado){
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao]+ " de Professor realizada com sucesso!", "Academico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			pai.setVisible(true);
			pai.atualize();
		}else{
			JOptionPane.showMessageDialog(this, operacoesNomes[operacao] +" de professor falhou", "Academico", JOptionPane.INFORMATION_MESSAGE);
		}

	}

}
