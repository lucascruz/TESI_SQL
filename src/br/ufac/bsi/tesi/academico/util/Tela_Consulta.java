package br.ufac.bsi.tesi.academico.util;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufac.bsi.tesi.academico.db.Conexao;
import br.ufac.bsi.tesi.academico.gui.ConsultorDataModel;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Tela_Consulta extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static Conexao cnx;
	private String comandosOp;
	private ResultSet rs = null;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela_Consulta frame = new Tela_Consulta(cnx);
					System.out.println(cnx);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Tela_Consulta(Conexao cnx) {
		setTitle("SQL Consultor TABAJARA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("SQL Comandos");
		panel.add(lblNewLabel, BorderLayout.NORTH);

		JTextArea textArea = new JTextArea();
		panel.add(textArea, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnNewButton = new JButton("EXECUTAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cnx != null){
					comandosOp= textArea.getText();
					rs = cnx.consulte(comandosOp);
					System.out.println(cnx);
					if (rs != null){
						table.setModel(new ConsultorDataModel(rs));
					}else{
						System.out.println("O comando n�o produziu resultados!");
					}
				}
			}
		});
		panel_3.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("SAIR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cnx.desconecte();
				System.exit(0);
			}
		});
		panel_3.add(btnNewButton_1);

		JPanel panel_1 = new JPanel();
		
		contentPane.add(panel_1, BorderLayout.CENTER);



		table = new JTable(0,0);
		panel_1.add(new JScrollPane(table));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JLabel lblSqlConsultoriasTabajaras = new JLabel("SQL CONSULTORIAS TABAJARAS");
		panel_2.add(lblSqlConsultoriasTabajaras);
		
		JButton btnBdAdm = new JButton("BD / ADM");
		btnBdAdm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tela_Busca_Cadastro frame2 = new Tela_Busca_Cadastro(cnx);
				frame2.setVisible(true);
				setVisible(false);
				System.out.println("Enviando referencia "+cnx);
			}
		});
		panel_2.add(btnBdAdm);
	}

}
