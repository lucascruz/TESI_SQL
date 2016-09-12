package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*; 					//importando classes do swing

public class CursoConsultaGUI extends JFrame implements ActionListener{

	private JTable tblCursoes;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir;

	private Conexao cnx;
	private AcademicoGUI pai;	
	private CursoCadastroGUI pcgui;
	private CursoLogic cursoLogic;
	
//	ConsultaitorTableModel atm = new ConsultaitorTableModel();

	public CursoConsultaGUI(AcademicoGUI pai, Conexao cnx){ // método construtor
		super("Consulta de Curso");
		setSize(800, 600); // chamando construtor da classe mãe
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		pcgui = new CursoCadastroGUI(this, cnx);		
		cursoLogic = new CursoLogic();
		cursoLogic.setConexao(cnx);
		
		tblCursoes = new JTable(0,0);
		tblCursoes.setToolTipText("Lista de cursoes!");		
		tblCursoes.setFocusable(false);
		tblCursoes.addMouseListener(new MouseAdapter() {
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
		
		add(new JScrollPane(tblCursoes));
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

		List<Curso> cursoes = new ArrayList<Curso>();

		if (fldValor.getText().equals(""))
			cursoes = cursoLogic.lstCurso();
		else	
			if(cmbCampos.getSelectedIndex() == 0)
				cursoes.add(cursoLogic.getCurso(fldValor.getText()));
			else
				;// DEVERÁ CONSIDERAR O NOME E REALIZAR A CONSULTA COM O MÉTODO CORRESPODENTE
		
		if(cursoes != null){
			tblCursoes.setModel(new CursoTableModel(cursoes));
		}else{
			tblCursoes.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Curso", JOptionPane.PLAIN_MESSAGE);	
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
		
		matricula = tblCursoes.getModel().getValueAt(tblCursoes.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		pcgui.editar(matricula);
	}

	public void excluir(){
		String matricula;
		
		matricula = tblCursoes.getModel().getValueAt(tblCursoes.getSelectedRow(), 
				0).toString();
		
		setVisible(false);
		pcgui.excluir(matricula);
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}//Fim da classe CursoConsultaGUI



