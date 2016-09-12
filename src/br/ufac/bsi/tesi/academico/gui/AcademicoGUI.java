package br.ufac.bsi.tesi.academico.gui;


import br.ufac.bsi.tesi.academico.db.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class AcademicoGUI extends JFrame implements ActionListener{

	ProfessorConsultaGUI professorConsultaGUI;
	
	private JMenu mnCadastro;
	private JMenuItem mntmProfessor;
	private JSeparator separator;
	private JMenuItem mntmSair;

	public AcademicoGUI(Conexao cnx) {
		setTitle("Controle Acadêmico");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);

		professorConsultaGUI = new ProfessorConsultaGUI(this, cnx);		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnCadastro = new JMenu("Cadastro");
		menuBar.add(mnCadastro);
		
		mntmProfessor = new JMenuItem("Professor");
		mntmProfessor .addActionListener(this);
		mnCadastro.add(mntmProfessor);
		
		
		
		separator = new JSeparator();
		mnCadastro.add(separator);
		
		mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(this);
		mnCadastro.add(mntmSair);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmSair){
			System.exit(0);			
		}
		if (e.getSource() == mntmProfessor){
			setVisible(false);
			professorConsultaGUI.setVisible(true);
		}
	}
	
}
