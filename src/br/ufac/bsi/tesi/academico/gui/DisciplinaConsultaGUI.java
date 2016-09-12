package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 					//importando classes do swing

public class DisciplinaConsultaGUI extends JFrame implements ActionListener{

	private JTable tblDisciplinaes;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private DisciplinaCadastroGUI pcgui;
	private DisciplinaLogic disciplinaLogic;
	
//	ConsultaitorTableModel atm = new ConsultaitorTableModel();

	public DisciplinaConsultaGUI(AcademicoGUI pai, Conexao cnx){ // método construtor
		super("Consulta de Disciplina");
		setSize(800, 600); // chamando construtor da classe mãe
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		pcgui = new DisciplinaCadastroGUI(this, cnx);		
		disciplinaLogic = new DisciplinaLogic();
		disciplinaLogic.setConexao(cnx);
		
		tblDisciplinaes = new JTable(0,0);
		tblDisciplinaes.setToolTipText("Lista de disciplinaes!");		
		tblDisciplinaes.setFocusable(false);
		tblDisciplinaes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			}
		});

		pnlControles = new JPanel(new BorderLayout());
		pnlRotulos = new JPanel(new GridLayout(2,1));
		pnlCampos = new JPanel(new GridLayout(2,1));
		pnlComandos = new JPanel(new GridLayout(2,1));
		pnlOperacoes = new JPanel();

		cmbCampos = new JComboBox(new String[]{"Matrícula", "Nome"});
		fldValor = new JTextField();
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);		
		
		btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(this);

		btnEditar = new JButton("Editar");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(this);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(this);
		btnExcluir.setEnabled(false);

		pnlRotulos.add(new JLabel("Buscar por"));
		pnlRotulos.add(new JLabel("Chave de busca"));		

		pnlCampos.add(cmbCampos);
		pnlCampos.add(fldValor);
		
		pnlComandos.add(btnBuscar);
		pnlComandos.add(btnSair);
		
		pnlControles.add(pnlRotulos, BorderLayout.WEST);
		pnlControles.add(pnlCampos);
		pnlControles.add(pnlComandos, BorderLayout.EAST); 

		pnlOperacoes.add(btnIncluir);
		pnlOperacoes.add(btnEditar);		
		pnlOperacoes.add(btnExcluir);		
		
		add(new JScrollPane(tblDisciplinaes));
		add(pnlControles, BorderLayout.NORTH);
		add(pnlOperacoes, BorderLayout.SOUTH);

   } //Fim do método construtor

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnBuscar){
			buscar();
		}

		if (e.getSource() == btnIncluir){
			incluir();
		}
		
		if (e.getSource() == btnEditar){
			editar();
		}

		if (e.getSource() == btnExcluir){
			excluir();
		}		
		
		if (e.getSource() == btnSair){
			sair();			
		}		
		
	}

	public void buscar(){

		List<Disciplina> disciplinaes = new ArrayList<Disciplina>();

		if (fldValor.getText().equals(""))
			disciplinaes = disciplinaLogic.lstDisciplina();
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				disciplinaes.add(disciplinaLogic.getDisciplina(fldValor.getText()));
			else
				;// DEVERÁ CONSIDERAR O NOME E REALIZAR A CONSULTA COM O MÉTODO CORRESPODENTE
		
		if(disciplinaes != null){
			tblDisciplinaes.setModel(new DisciplinaTableModel(disciplinaes));
		}else{
			tblDisciplinaes.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Disciplina", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		pcgui.incluir();
	}

	public void editar(){
		String matricula;
		
		matricula = tblDisciplinaes.getModel().getValueAt(tblDisciplinaes.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		pcgui.editar(matricula);
	}

	public void excluir(){
		String matricula;
		
		matricula = tblDisciplinaes.getModel().getValueAt(tblDisciplinaes.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		pcgui.excluir(matricula);
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}//Fim da classe DisciplinaConsultaGUI



