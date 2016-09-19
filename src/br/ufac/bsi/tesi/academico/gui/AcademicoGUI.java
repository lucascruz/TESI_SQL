package br.ufac.bsi.tesi.academico.gui;

import br.ufac.bsi.tesi.academico.db.*;

import javax.swing.*;
import java.awt.event.*;

public class AcademicoGUI extends JFrame implements ActionListener{

	ProfessorConsultaGUI professorConsultaGUI;
	CentroConsultaGUI centroConsultaGUI;
	CursoConsultaGUI cursoConsultaGUI;
	DisciplinaConsultaGUI disciplinaConsultaGUI;
	AlunoConsultaGUI alunoConsultaGui;
	private JMenu mnCadastro, mnSobre;
	private JMenuItem mntmProfessor, mntmCentro, mntmCurso, mntmDisciplina, mntmAluno, mntmHelp;
	private JSeparator separator;
	private JMenuItem mntmSair;
	protected Conexao cnx = Conexao.getInstacia();

	public AcademicoGUI() {
		setTitle("Controle Academico");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);

		professorConsultaGUI = new ProfessorConsultaGUI(this, cnx);		
		centroConsultaGUI = new CentroConsultaGUI(this,cnx);
		cursoConsultaGUI = new CursoConsultaGUI(this,cnx);
		disciplinaConsultaGUI = new DisciplinaConsultaGUI(this,cnx);
		alunoConsultaGui = new AlunoConsultaGUI (this,cnx);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnCadastro = new JMenu("Cadastro");
		menuBar.add(mnCadastro);
		
		mnSobre = new JMenu("Sobre");
		menuBar.add(mnSobre);
		
		mntmProfessor = new JMenuItem("Professor");
		mntmProfessor .addActionListener(this);
		mnCadastro.add(mntmProfessor);
		
		mntmAluno = new JMenuItem("Aluno");
		mntmAluno .addActionListener(this);
		mnCadastro.add(mntmAluno);
		
		mntmDisciplina = new JMenuItem("Disciplina");
		mntmDisciplina .addActionListener(this);
		mnCadastro.add(mntmDisciplina);
		
		mntmCurso = new JMenuItem("Curso");
		mntmCurso.addActionListener(this);
		mnCadastro.add(mntmCurso);
		
		mntmCentro = new JMenuItem("Centro");
		mntmCentro.addActionListener(this);
		mnCadastro.add(mntmCentro);
		
		
		
		mntmHelp = new JMenuItem("Encontrou algum erro?");
		mntmHelp.addActionListener(this);
		mnSobre.add(mntmHelp);
		
		separator = new JSeparator();
		mnCadastro.add(separator);
			
		mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(this);
		menuBar.add(mntmSair);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmSair){
			try {
				cnx.finalize();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
			System.exit(0);			
		}
		if (e.getSource() == mntmProfessor){
			setVisible(false);
			professorConsultaGUI.setVisible(true);
		}
		if(e.getSource()== mntmCentro){
			setVisible(false);
			centroConsultaGUI.setVisible(true);
		}
		if(e.getSource()== mntmCurso){
			setVisible(false);
			cursoConsultaGUI.setVisible(true);
		}
		if(e.getSource()== mntmDisciplina){
			setVisible(false);
			disciplinaConsultaGUI.setVisible(true);
		}
		
		if(e.getSource()== mntmAluno){
			setVisible(false);
			alunoConsultaGui.setVisible(true);
		}

		if(e.getSource()== mntmHelp){
			JOptionPane.showMessageDialog(null, "Report qualquer erro github.com/lucascruz = )!", 
					"HELP", JOptionPane.PLAIN_MESSAGE);	
		}
	}
	
}
