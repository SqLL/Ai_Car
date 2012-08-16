package customer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cartago.INTERNAL_OPERATION;
import cartago.tools.GUIArtifact;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.toedter.calendar.JDateChooser;

/**
 * This class is GUI for a client to say what he wants
 * @author squall
 *  
 */
public class ContrainteGUI extends GUIArtifact {

	private customerFrame frame;

	/**
	 * Builder of GUIArtifact
	 */
	public void setup() {
		frame = new customerFrame();
		linkWindowClosingEventToOp(frame, "closed");
		linkActionEventToOp(frame.getValidationButton(), "validation");
		frame.setVisible(true);
	}

	/**
	 * 
	 * this void check the form
	 * @param ev here when you use the button Validation
	 *          
	 */
	@SuppressWarnings("static-access")
	@INTERNAL_OPERATION
	void validation(ActionEvent ev) {
		if (frame.getJcalBegin().getDate() != null
				&& frame.getJcalEnd().getDate() != null) {
			frame.request.setDate_depart(frame.getJcalBegin().getDate());
			frame.request.setDate_arriver(frame.getJcalEnd().getDate());
			for (JRadioButton radioButton : frame.getRadioType())
				if (radioButton.isSelected()) {
					frame.request.setTypeVoiture(radioButton.getText());
				}
			if(this.frame.getInputKm().getText().length() != 0)
			{
			if ((frame.request.date_arriver.after(frame.request.date_depart))) {
				frame.request.setNameCompany(this.frame.getlCompany().getSelectedItem().toString());
				frame.request.setNamePointsOfSales(this.frame.getlSalesPoints().getSelectedItem().toString());
				frame.request.setEstimationKilometers(Integer.parseInt((this.frame.getInputKm().getText().toString())));
				System.out.println(frame.request.estimationKilometers);
				signal("launch", frame.getRequest());
			} else {
				JOptionPane jop2 = new JOptionPane();
				jop2.showMessageDialog(null,
						frame.messages.getString("customer_error_date"),
						frame.messages.getString("customer_error"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
			else {
				System.out.println("ERROR dans l'estimation de kilometres");
			}
		} else {
			JOptionPane jop2 = new JOptionPane();
			jop2.showMessageDialog(null,
					frame.messages.getString("customer_error_field"),
					frame.messages.getString("customer_error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@INTERNAL_OPERATION
	void closed(WindowEvent ev) {
		dispose();
	}

}

/**
 * 
 * This class is the windows button placement etc
 * 
 * @author squall
 * 
 *       
 */
class customerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Bouton qui valide la fenetre
	 */
	private JButton validationButton;

	/**
	 * Liste des types de vehicules
	 */
	public final List<String> TYPES = Arrays.asList("Utilitaire",
			"Tout Terrain", "Automatique", "Tourisme", "Prestige and Fun");
	/**
	 * JRadiobuttons creer par les TYPES
	 */
	private List<JRadioButton> radioType = new ArrayList<JRadioButton>();
	/**
	 * Variable pour regrouper les JRadioButton
	 */
	private ButtonGroup typeConstraint = new ButtonGroup();

	JPanel begin;
	JPanel end;
	
	/**
	 * Variable utile pour GUI, pour la selection de la date
	 */
	private JDateChooser jcalBegin;
	/**
	 * Variable utile pour GUI, pour la selection de la date
	 */
	private JDateChooser jcalEnd;

	/**
	 * Element essentiel de l'interface c'est l'artefact qui vas représenté la
	 * requête du client
	 */
	protected Contrainte request = new Contrainte();

	/**
	 * Internationalisation
	 */
	Locale locale = new Locale("fr");
	ResourceBundle messages = ResourceBundle.getBundle("gui", locale);

	/**
	 * Menu et elements du menu pour switch le language
	 */
	private JMenuBar menuBar = new JMenuBar();
	private JMenu lang = new JMenu("Language");
	private JMenuItem langFr = new JMenuItem("Francais");
	private JMenuItem langEn = new JMenuItem("Anglais");
	private JPanel top;

	/**
	 * Variable pour la connexion à la Base de données
	 */
	private String userName = "root"; // change it to your username
	private String password = "root"; // change it to your password
	private String url = "jdbc:mysql://localhost:3306/AI_Data";
	private Connection dataLink;

	/**
	 * Variable useful to select the company and which sales points you want
	 */
	private JComboBox lCompany;
	private JComboBox lSalesPoints;
	private JComboBox lSalesPointsDst;
	private JCheckBox roundTrip;
	private JPanel panel_startpoint;
	private JPanel panel_endpoint;
	private JPanel infoCompany;
	/**
	 * Variable to put an estimation about km
	 */
	private JPanel panel_km;
	private JTextField inputKm;
	
	

	/**
	 * Fonction principale de creation d'une fenetre appellant toute les sous
	 * fonctions
	 */
	public customerFrame() {
		setTitle(messages.getString("customer_title"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		AddMenu();
		AddType(panel, gbc);
		AddCalendar(panel, gbc);
		dataLink = EtablishedConnection();
		AddInfoCompany(panel, gbc);
		AddInfoKm(panel,gbc);
		AddButton(panel, gbc);

		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * 
	 * @return Connection (Link to the dataBase to make statement and execute
	 *         Query)
	 */
	private Connection EtablishedConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = (Connection) DriverManager.getConnection(url, userName,
					password);
			System.out.println("[INFO] Database connection established");

		} catch (Exception e) {
			System.err.println("[INFO] Cannot connect to database server");
		}
		return conn;
	}
	
	/**
	 * Procedure d'ajout d'un indicateur d'estimation de kilomètres.
	 * 
	 * @param panel
	 *            Panel principale de la fenetre
	 * @param gbc
	 *            Gestionnaire de layout gridbaglayout
	 */
	public void AddInfoKm(JPanel panel, GridBagConstraints gbc)
	{
		panel_km=new JPanel();
		inputKm=new JTextField();
		panel_km.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_estimation_km")));
		inputKm.setPreferredSize(new Dimension(100, 25));
		panel_km.add(inputKm);
		

		/* Nous pouvons passé aux boutons. */
		gbc.gridy = 4; /* nouvelle ligne */
		gbc.gridx = 0;
		gbc.gridheight = 1; /* une seule cellule verticalement suffit */
		gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant de
														// sa
		gbc.anchor = GridBagConstraints.LINE_START; // pas WEST.
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(panel_km, gbc);
	}
	

	/**
	 * Procedure d'ajout d'une company
	 * 
	 * @param panel
	 *            Panel principale de la fenetre
	 * @param gbc
	 *            Gestionnaire de layout gridbaglayout
	 */
	public void AddInfoCompany(JPanel panel, GridBagConstraints gbc) {

		infoCompany = new JPanel();

		/*
		 * Mis en place de la liste de Company
		 */
		Statement stmt;
		try {
			stmt = (Statement) dataLink.createStatement();
			String requete = "SELECT * FROM Company";
			ResultSet rs = (ResultSet) stmt.executeQuery(requete);
			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString("Name"));
			}
			lCompany = new JComboBox(list.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lCompany.setPreferredSize(new Dimension(120, 25));
		lCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// When a Company is selected you have to update the JComboBox
				// SalesPoints
				Statement stmt;
				lSalesPoints.removeAllItems();
				lSalesPointsDst.removeAllItems();
				try {
					stmt = (Statement) dataLink.createStatement();
					String requete = "SELECT * FROM SalesPoints WHERE id_company="
							+ "(SELECT id FROM Company WHERE name='"
							+ lCompany.getSelectedItem().toString() + "'" + ")";
					ResultSet rs = (ResultSet) stmt.executeQuery(requete);

					while (rs.next()) {
						lSalesPoints.addItem(rs.getString("localisation"));
						lSalesPointsDst.addItem(rs.getString("localisation"));
					}
					if (!roundTrip.isSelected()) {
						lSalesPointsDst.removeItem(lSalesPoints
								.getSelectedItem());
					}

				} catch (SQLException f) {
					f.printStackTrace();
				}

			}
		});

		this.panel_startpoint = new JPanel();
		panel_startpoint.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_start")));
		panel_startpoint.add(lCompany);

		
		

		
		
		/*
		 * Mis en place de la liste de Sales points Pour cela on obtient l'id de
		 * la company
		 */

		try {
			stmt = (Statement) dataLink.createStatement();
			String requete = "SELECT * FROM SalesPoints WHERE id_company="
					+ "(SELECT id FROM Company WHERE name='"
					+ lCompany.getSelectedItem().toString() + "'" + ")";
			ResultSet rs = (ResultSet) stmt.executeQuery(requete);
			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString("localisation"));
			}
			this.lSalesPoints = new JComboBox(list.toArray());
			lSalesPointsDst = new JComboBox(list.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.lSalesPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// When a Company is selected you have to update the JComboBox
				// SalesPoints
				lSalesPointsDst.removeAllItems();

				for (int i = lSalesPoints.getItemCount(); i >= 0; i--) {
					if (i != lSalesPoints.getSelectedIndex()) {
						lSalesPointsDst.addItem(lSalesPoints.getItemAt(i));
					}
				}
				lSalesPointsDst.setSelectedIndex(0);
			}
		});

		/*
		 *	Ajout du sales points combobox
		 */
		lSalesPoints.setPreferredSize(new Dimension(120, 25));
		panel_startpoint.add(this.lSalesPoints);		
		lSalesPointsDst.setPreferredSize(new Dimension(120, 25));

		

		/*
		 * Mise en place de la checkbox
		 */
		roundTrip = new JCheckBox(messages.getString("customer_roundtrip"));
		if (!roundTrip.isSelected()) {
			lSalesPointsDst.removeItem(lSalesPoints.getSelectedItem());
		}
		
		roundTrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(roundTrip.isSelected())
						lSalesPointsDst.setEnabled(false);
					else
						lSalesPointsDst.setEnabled(true);
			}
		});

		
		/*
		 *	Mise en place du panel de gauche 
		 */
		this.panel_endpoint = new JPanel();
		panel_endpoint.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_dst")));
		panel_endpoint.add(roundTrip);
		panel_endpoint.add(lSalesPointsDst);




		gbc.gridy = 3; /* nouvelle ligne */
		gbc.gridx = 0;
		gbc.gridheight = 1; /* une seule cellule verticalement suffit */
		gbc.gridwidth = 1;
		// gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant de
		// sa
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 15, 10, 10);
		panel.add(panel_startpoint, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 15, 10, 10);
		panel.add(panel_endpoint, gbc);
		// ajout

		// Todo Checkbox and list of end point is the same than the Salespoints

		panel.add(infoCompany, gbc);
	}



	/**
	 * Void Which add MenuBar in Frame to switch language
	 */
	public void AddMenu() {
		lang.setText(messages.getString("customer_menu_lang"));
		langFr.setText(messages.getString("customer_item_fr"));
		langEn.setText(messages.getString("customer_item_en"));

		this.lang.add(langFr);
		langFr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(locale.getLanguage() == "fr")) {
					locale = new Locale("fr");
					messages = ResourceBundle.getBundle("gui", locale);
					updateLang();
				}
			}
		});
		this.lang.add(langEn);
		langEn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(locale.getLanguage() == "en")) {
					locale = new Locale("en");
					messages = ResourceBundle.getBundle("gui", locale);
					updateLang();
				}
			}
		});

		this.menuBar.add(lang);
		this.setJMenuBar(menuBar);
		this.setVisible(true);

	}

	/**
	 * Function who add the Button in frame
	 * 
	 * @param panel
	 *            Panel principale de la fenetre
	 * @param gbc
	 *            Gestionnaire de layout gridbaglayout
	 */
	private void AddButton(JPanel panel, GridBagConstraints gbc) {

		validationButton = new JButton(messages.getString("customer_button"));
		validationButton.setSize(80, 50);
		JPanel sud = new JPanel();
		sud.add(validationButton);

		/* Nous pouvons passé aux boutons. */
		gbc.gridy = 5; /* nouvelle ligne */
		gbc.gridx = 0;
		gbc.gridheight = 1; /* une seule cellule verticalement suffit */
		gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant de
														// sa
		gbc.anchor = GridBagConstraints.LINE_START; // pas WEST.
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(sud, gbc);

	}

	/**
	 * Function to add the RadioButton with all type
	 * 
	 * @param panel
	 *            Panel principale de la fenetre
	 * @param gbc
	 *            Gestionnaire de layout gridbaglayout
	 */
	public void AddType(JPanel panel, GridBagConstraints gbc) {

		// Creations des composants
		for (String type : TYPES) {
			radioType.add(new JRadioButton(type));
		}

		radioType.get(0).setSelected(true); // selection

		top = new JPanel();
		for (JRadioButton radioButton : radioType) {
			typeConstraint.add(radioButton);
			top.add(radioButton);
		}

		top.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_type")));

		// Creations des contraintes
		gbc.gridx = gbc.gridy = 0; // la grille commence en (0, 0)
		gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant sur
														// la colonne
		gbc.gridheight = 1; // valeur par défaut - peut s'étendre sur une
							// seule ligne
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(10, 15, 0, 0); // Marges

		// ajout
		panel.add(top, gbc);
	}

	/**
	 * Function to add the calendar with the JDateChooser from JCalendar
	 * 
	 * @see JDateChooser
	 * 
	 * @param panel
	 *            Panel principale de la fenetre
	 * @param gbc
	 *            Gestionnaire de layout gridbaglayout
	 */
	public void AddCalendar(JPanel panel, GridBagConstraints gbc) {

		begin = new JPanel();
		end = new JPanel();

		// Calendar part
		setJcalBegin(new JDateChooser()); // date
		begin.add(getJcalBegin());
		begin.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_date_begin")));
		// courante
		jcalEnd = new JDateChooser();

		// Nous pouvons passé aux boutons.
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 15, 10, 10);
		panel.add(begin, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 15, 10, 10);

		end.add(jcalEnd);
		end.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_date_end")));
		panel.add(end, gbc);

	}

	/**
	 * 
	 * Function to update the language when he is selected
	 * 
	 */
	public void updateLang() {
		this.setTitle(messages.getString("customer_title"));
		end.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_date_end")));
		begin.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_date_begin")));
		top.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_type")));
		validationButton.setText((messages.getString("customer_button")));
		lang.setText(messages.getString("customer_menu_lang"));
		langFr.setText(messages.getString("customer_item_fr"));
		langEn.setText(messages.getString("customer_item_en"));
		panel_startpoint.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_start")));
		panel_endpoint.setBorder(BorderFactory.createTitledBorder(messages
				.getString("customer_dst")));
	}

	public JDateChooser getJcalBegin() {
		return jcalBegin;
	}

	public void setJcalBegin(JDateChooser jcalBegin) {
		this.jcalBegin = jcalBegin;
	}

	public List<JRadioButton> getRadioType() {
		return this.radioType;
	}

	public JButton getValidationButton() {
		return validationButton;
	}

	public void setValidationButton(JButton validationButton) {
		this.validationButton = validationButton;
	}

	public Contrainte getRequest() {
		return request;
	}

	public JDateChooser getJcalEnd() {
		// TODO Auto-generated method stub
		return this.jcalEnd;
	}
	public JComboBox getlCompany() {
		return lCompany;
	}

	public void setlCompany(JComboBox lCompany) {
		this.lCompany = lCompany;
	}

	public JComboBox getlSalesPoints() {
		return lSalesPoints;
	}

	public void setlSalesPoints(JComboBox lSalesPoints) {
		this.lSalesPoints = lSalesPoints;
	}

	public JTextField getInputKm() {
		return inputKm;
	}

	public void setInputKm(JTextField inputKm) {
		this.inputKm = inputKm;
	}
	
	
}
