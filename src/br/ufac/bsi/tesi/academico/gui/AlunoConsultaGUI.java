package br.ufac.bsi.tesi.academico.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.exception.InvalidNameException;
import br.ufac.bsi.tesi.academico.logic.Aluno;
import br.ufac.bsi.tesi.academico.logic.AlunoLogic;

class AlunoConsultaGUI extends JFrame implements ActionListener{

	private JTable tblalunos;
	private JPanel pnlControles, pnlRotulos, pnlCampos, pnlComandos, pnlOperacoes;
	private JComboBox<String> cmbCampos;
	private JTextField fldValor;
	private JButton btnBuscar, btnSair, btnIncluir, btnEditar, btnExcluir, btnListar;

	private Conexao cnx= Conexao.getInstacia();
	private AcademicoGUI pai;	
	private AlunoCadastroGUI pcgui;
	private AlunoLogic alunoLogic;

	public AlunoConsultaGUI(AcademicoGUI pai, Conexao cnx){
		super("Consulta de Aluno");
		setSize(800, 600); 
		setLocationRelativeTo(null);		

		this.cnx = cnx;
		this.pai = pai;

		try {
			pcgui = new AlunoCadastroGUI(this, cnx);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());	
		}		
		alunoLogic = new AlunoLogic();

		tblalunos = new JTable(0,0);
		tblalunos.setToolTipText("Lista de Alunos!");		
		tblalunos.setFocusable(false);
		tblalunos.addMouseListener(new MouseAdapter() {
			@Override
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

		cmbCampos = new JComboBox(new String[]{"Matricula", "Nome"});
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
		
		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);

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
		pnlOperacoes.add(btnListar);
		
		add(new JScrollPane(tblalunos));
		add(pnlControles, BorderLayout.NORTH);
		add(pnlOperacoes, BorderLayout.SOUTH);

   }
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
		if(e.getSource() == btnListar){
			try {
				Listar();
			} catch (InvalidNameException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	public void Listar() throws InvalidNameException{
		List<Aluno> alunos = new ArrayList<Aluno>();
		try {
			alunos = alunoLogic.getTodosAlunos();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		
		if(alunos != null){
			tblalunos.setModel(new AlunoTableModel(alunos));
		}else{
			tblalunos.setModel(null);
			 JOptionPane.showMessageDialog(null, "Sua consulta não produziu resultados!", 
					 "Consulta de Aluno", JOptionPane.PLAIN_MESSAGE);	
		}
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}
	public void atualize() throws InvalidNameException{
		List<Aluno> alunos = new ArrayList<Aluno>();
		try {
			alunos = alunoLogic.getTodosAlunos();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
		tblalunos.setModel(new AlunoTableModel(alunos));
	}
	public void buscar(){

		List<Aluno> alunos = new ArrayList<Aluno>();
		if (fldValor.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Você não digitou uma matricula", 
					 "Consulta de Professor FALHA", JOptionPane.PLAIN_MESSAGE);	
		else
			if(cmbCampos.getSelectedIndex() == 0)
				try{
					alunos.add(alunoLogic.getAluno(fldValor.getText()));
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "A matricula apenas pode conter Numeros!!!!", 
							 "Consulta de Professor FALHA", JOptionPane.PLAIN_MESSAGE);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());	
				}
			else
				if (fldValor.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Você não digitou um nome", 
							 "Consulta de Professor FALHA", JOptionPane.PLAIN_MESSAGE);
				else	
					if(cmbCampos.getSelectedIndex() == 1)
						try {
							alunos.add(alunoLogic.getAluno(fldValor.getText()));
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());	
						}
		
		tblalunos.setModel(new AlunoTableModel(alunos));
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);		
	}

	public void incluir(){
		setVisible(false);
		pcgui.incluir();
	}

	public void editar(){	
		String matricula = (tblalunos.getModel().getValueAt(tblalunos.getSelectedRow(), 
				0).toString());
		
		setVisible(false);
		try {
			pcgui.editar(matricula);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}

	public void excluir(){
		String matricula = (tblalunos.getModel().getValueAt(tblalunos.getSelectedRow(), 
				0).toString());
		
		setVisible(false);
		try {
			pcgui.excluir(matricula);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());	
		}
	}	
	
	private void sair(){
		setVisible(false);
		pai.setVisible(true);
	}

}
