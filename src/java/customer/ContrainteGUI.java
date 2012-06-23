package customer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cartago.INTERNAL_OPERATION;
import cartago.tools.GUIArtifact;
import com.toedter.calendar.JDateChooser;



/**
 * 
 * @author squall
 *
 * This class is GUI for a client to say what he wants
 */
public class ContrainteGUI extends GUIArtifact {

	private MyFrame frame;

	/*
	 * Builder of GUIArtifact
	 * 
	 */
	public void setup() {
		frame = new MyFrame();
		linkWindowClosingEventToOp(frame, "closed");
		linkActionEventToOp(frame.validationButton, "validation");
		frame.setVisible(true);
	}

	/**
	 * 
	 * @param ev here when you use the button Validation
	 * 
	 * this void try to check forms
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
			if (frame.request.date_arriver.after(frame.request.date_depart)) {
				signal("launch", frame.getRequest());
			} else {
				JOptionPane jop2 = new JOptionPane();
				jop2.showMessageDialog(null,
						frame.messages.getString("customer_error_date"),
						frame.messages.getString("customer_error"),
						JOptionPane.ERROR_MESSAGE);
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
 * @author squall
 *
 * This class is the windows button placement
 */
class MyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JButton validationButton;
	public final List<String> TYPES = Arrays.asList("Utilitaire",
			"Tout Terrain", "Automatique", "Tourisme", "Prestige and Fun");
	private List<JRadioButton> radioType = new ArrayList<JRadioButton>();
	private ButtonGroup typeConstraint = new ButtonGroup();
	JPanel begin;
	JPanel end;
	
	private JDateChooser jcalBegin;
	private JDateChooser jcalEnd;

	protected Contrainte request = new Contrainte();

	Locale locale = new Locale("fr");
	ResourceBundle messages = ResourceBundle.getBundle("gui", locale);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu lang = new JMenu("Language");
	private JMenuItem langFr = new JMenuItem("Francais");
	private JMenuItem langEn = new JMenuItem("Anglais");
	private JPanel top;

	public MyFrame() {
		setTitle(messages.getString("customer_title"));

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		AddMenu();
		AddType(panel, gbc);
		AddCalendar(panel, gbc);
		AddButton(panel, gbc);

		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
	}

	public void AddMenu() {
		lang.setText(messages.getString("customer_menu_lang"));
		langFr.setText(messages.getString("customer_item_fr"));
		langEn.setText(messages.getString("customer_item_en"));
	
		this.lang.add(langFr);
		langFr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(locale.getLanguage() == "fr")) {
					locale=new Locale("fr");
					messages= ResourceBundle.getBundle("gui",locale);
					updateLang();
				}
			}
		});
		this.lang.add(langEn);
		langEn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(locale.getLanguage());
				if (!(locale.getLanguage() == "en")) {
					locale=new Locale("en");
					messages= ResourceBundle.getBundle("gui",locale);
					updateLang();
				}
			}
		});

		this.menuBar.add(lang);
		this.setJMenuBar(menuBar);
		this.setVisible(true);

	}

	public Contrainte getRequest() {
		return request;
	}

	public JDateChooser getJcalEnd() {
		// TODO Auto-generated method stub
		return this.jcalEnd;
	}

	private void AddButton(JPanel panel, GridBagConstraints gbc) {

		validationButton = new JButton(messages.getString("customer_button"));
		validationButton.setSize(80, 50);
		validationButton.addActionListener(new UpdateListener());
		JPanel sud = new JPanel();
		sud.add(validationButton);

		/* Nous pouvons passé aux boutons. */
		gbc.gridy = 3; /* nouvelle ligne */
		gbc.gridx = 0;
		gbc.gridheight = 1; /* une seule cellule verticalement suffit */
		gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant de
														// sa
		gbc.anchor = GridBagConstraints.LINE_START; // pas WEST.
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(sud, gbc);

	}

	class UpdateListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (JRadioButton radioButton : radioType) {
				System.out.println("source : " + radioButton.getText()
						+ " - état : " + radioButton.isSelected());
			}

			System.out.println(getJcalBegin().getDate());
			System.out.println(jcalEnd.getDate());

		}
	}

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
	
	public void updateLang(){
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
}
