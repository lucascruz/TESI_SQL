package br.ufac.bsi.tesi.academico.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufac.bsi.tesi.academico.db.*;
import br.ufac.bsi.tesi.academico.logic.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;


public class Tela_Busca_Cadastro extends JFrame {


	private JPanel contentPane, contentPanel;
	private JTextField textField, textField_Codigo, textField_RG, textField_Nome,textField_CPF, textField_Centro ;
	private JTable tblProfessores;
	private JComboBox escolhaBox;
	private String[] centroSiglas, centroNomes;
	private int numeroDeCentros, operacao;
	private static Conexao cnx;
	private ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela_Busca_Cadastro frame = new Tela_Busca_Cadastro(cnx);
					frame.setVisible(true);
					System.out.println("AKI"+cnx);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela_Busca_Cadastro(Conexao cnx) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel painel_controles = new JPanel();
		contentPane.add(painel_controles, BorderLayout.NORTH);
		painel_controles.setLayout(new BorderLayout(0, 0));

		JLabel lblBusca = new JLabel("Busca por:  ");
		painel_controles.add(lblBusca, BorderLayout.WEST);

		escolhaBox = new JComboBox(new String[] {"Matricula", "Nome"});
		painel_controles.add(escolhaBox, BorderLayout.CENTER);

		JPanel painel_padroes = new JPanel();
		painel_controles.add(painel_padroes, BorderLayout.EAST);
		painel_padroes.setLayout(new GridLayout(2, 1, 0, 0));

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cnx != null){
					System.out.println("Oia eu entrando no Buscar");
					System.out.println(cnx);
					buscar(cnx);
				}
			}
		});
		btnBuscar.setMnemonic('B');
		painel_padroes.add(btnBuscar);

		JButton btnNewButton = new JButton("Sair");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnx.desconecte();
				System.exit(0);
			}
		});
		btnNewButton.setMnemonic('S');
		painel_padroes.add(btnNewButton);

		textField = new JTextField();
		painel_controles.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);

		JPanel painel_resultado = new JPanel();
		contentPane.add(painel_resultado, BorderLayout.CENTER);

		tblProfessores = new JTable(0,0);
		painel_resultado.add(tblProfessores);

		JPanel painel_acoes = new JPanel();
		contentPane.add(painel_acoes, BorderLayout.SOUTH);
		painel_acoes.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton_1 = new JButton("Incluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton_1){
					incluir();
				}
			}
		});
		btnNewButton_1.setMnemonic('i');
		painel_acoes.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton_2){
					editar();
				}
			}
		});
		btnNewButton_2.setMnemonic('E');
		painel_acoes.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Remover");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton_3){
					excluir();
				}
			}

		});
		btnNewButton_3.setMnemonic('R');
		painel_acoes.add(btnNewButton_3);
	}


	public void incluir(){
		System.out.println("Cheguei aqui... Tela de incluir professor");
		JFrame f= new JFrame();
		f.setSize(200,200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBounds(100, 100, 450, 300);
		f.setTitle("Inclus\u00E3o de Professor");
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		f.setContentPane(contentPanel);

		JPanel panel2 = new JPanel();
		contentPanel.add(panel2, BorderLayout.CENTER);
		panel2.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblNewLabel = new JLabel("CODIGO: ");
		panel2.add(lblNewLabel);

		textField_Codigo = new JTextField();
		panel2.add(textField_Codigo);
		textField_Codigo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("NOME: ");
		panel2.add(lblNewLabel_1);

		textField_Nome = new JTextField();
		panel2.add(textField_Nome);
		textField_Nome.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("RG  : ");
		panel2.add(lblNewLabel_2);

		textField_RG = new JTextField();
		panel2.add(textField_RG);
		textField_RG.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("CPF : ");
		panel2.add(lblNewLabel_3);

		textField_CPF = new JTextField();
		panel2.add(textField_CPF);
		textField_CPF.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("CENTRO : ");
		panel2.add(lblNewLabel_4);


		JComboBox escolhaBox2 = new JComboBox(centroNomes);
		panel2.add(escolhaBox2);

		JPanel paniel_Controles = new JPanel();
		contentPanel.add(paniel_Controles, BorderLayout.SOUTH);


		JButton btnNewButton = new JButton("Confirmar");
		paniel_Controles.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton){
					confirmar();
				}
			}
		});

		JButton btnNewButton_1 = new JButton("Sair");
		paniel_Controles.add(btnNewButton_1);
		
		



	}
	public void editar(){
		System.out.println("Cheguei aqui... Tela de edição professor");
		JFrame f= new JFrame();
		f.setSize(200,200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBounds(100, 100, 450, 300);
		f.setTitle("Edic\u00E3o de Professor");
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		f.setContentPane(contentPanel);

		JPanel panel2 = new JPanel();
		contentPanel.add(panel2, BorderLayout.CENTER);
		panel2.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblNewLabel = new JLabel("CODIGO: ");
		panel2.add(lblNewLabel);

		textField_Codigo = new JTextField();
		panel2.add(textField_Codigo);
		textField_Codigo.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("NOME: ");
		panel2.add(lblNewLabel_1);

		textField_Nome = new JTextField();
		panel2.add(textField_Nome);
		textField_Nome.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("RG  : ");
		panel2.add(lblNewLabel_2);

		textField_RG = new JTextField();
		panel2.add(textField_RG);
		textField_RG.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("CPF : ");
		panel2.add(lblNewLabel_3);

		textField_CPF = new JTextField();
		panel2.add(textField_CPF);
		textField_CPF.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("CENTRO : ");
		panel2.add(lblNewLabel_4);

		escolhaBox = new JComboBox(centroNomes);
		panel2.add(escolhaBox);
		

		JPanel paniel_Controles = new JPanel();
		contentPanel.add(paniel_Controles, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Confirmar");
		paniel_Controles.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnNewButton){
					confirmar();
				}
			}
		});
		

		JButton btnNewButton_1 = new JButton("Cancelar");
		paniel_Controles.add(btnNewButton_1);





	}
	public void excluir(){}
	public void buscar(Conexao cnx){
		System.out.println("Entrei no buscar() e a ref:"+cnx);
		String sqlQuery = "SELECT p.matricula, p.nome AS 'Nome', c.nome AS 'Centro'"
				+ " FROM professor AS p INNER JOIN centro AS c ON (p.centro_sigla = c.sigla)";

		if(escolhaBox.getSelectedIndex() == 0)
			sqlQuery = sqlQuery + " WHERE p.matricula LIKE '%" + textField.getText() + "%'";
		else
			sqlQuery = sqlQuery + " WHERE p.nome LIKE '%" + textField.getText() + "%'";			

		rs = cnx.consulte(sqlQuery);

		if(rs != null){
			tblProfessores.setModel(new ConsultorDataModel(rs));
		}else{
			tblProfessores.setModel(null);
			System.out.printf("Sua consulta não produziu resultados!", 
					"Consultaitor");	
		}

	}
	public void confirmar(){
		String strAtualizar = null;
		switch (operacao) {
		case 0:
			strAtualizar = "INSERT INTO professor (matricula, nome, rg, cpf, centro_sigla) VALUES (" +
					textField_Codigo.getText() + ", '" + 
					textField_Nome.getText() + "'," + 
					textField_RG.getText() + ", " + 
					textField_CPF.getText() + ", '" + 
					centroSiglas[escolhaBox.getSelectedIndex()] + "');";
			
			break;
		case 1:
			strAtualizar = "INSERT INTO professor (matricula, nome, rg, cpf, centro_sigla) VALUES (" +
					textField_Codigo.getText() + ", '" + 
					textField_Nome.getText() + "'," + 
					textField_RG.getText() + ", " + 
					textField_CPF.getText() + ", '" + 
					centroSiglas[escolhaBox.getSelectedIndex()] + "');";			
			
			break;
		default:
			break;
		}
		

		if (cnx.atualize(strAtualizar) > 0){
			JOptionPane.showMessageDialog(this, "Professor incluído com sucesso!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			contentPane.setVisible(true);			
		}else{
			JOptionPane.showMessageDialog(this, "Falha ao incluir o professor!", "Acadêmico", JOptionPane.INFORMATION_MESSAGE);
		}
	}}

