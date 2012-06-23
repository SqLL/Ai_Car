package seller;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cartago.INTERNAL_OPERATION;
import cartago.tools.GUIArtifact;

/**
 * 
 * @author squall
 * 
 *         In this class you can configure the windows administration GUI
 */

public class AdministrationGUI extends GUIArtifact {

	private AdminFrame frame;

	/**
	 * (non-Javadoc)
	 * 
	 * @see cartago.tools.GUIArtifact#setup()
	 * 
	 */
	public void setup() {
		frame = new AdminFrame();
		linkWindowClosingEventToOp(frame, "closed");
		linkActionEventToOp(frame.validationButton, "validation");
		frame.setVisible(true);
	}

	/**
	 * Check the forms
	 * 
	 */

	@INTERNAL_OPERATION
	@SuppressWarnings("static-access")
	void validation(ActionEvent ev) {
		Double probability;
		if (frame.probaRent.getValue() != null
				&& frame.capacityOfPointSale.getValue() != null) {
			// Verification double entre 0 et 1.
			System.out.println(frame.probaRent.getText());
			if (frame.probaRent.getText().equals("0")
					|| frame.probaRent.getText().equals("1")) {
				 probability = new Double(frame.probaRent.getText());
			} else {
				 probability = (Double) (frame.probaRent.getValue());
			}
				if (probability <= 1 && probability >= 0) {
					System.out.println(probability.toString());
					// Verification pour les capacités
					Long capacity = (Long) frame.capacityOfPointSale.getValue();
					if (capacity == 0) {
						JOptionPane jop2 = new JOptionPane();
						jop2.showMessageDialog(null,
								"La capacité du parc ne peut être égale à 0",
								"Attention", JOptionPane.WARNING_MESSAGE);
					} else {
						// Let's GO
						frame.getConfig().setProbRent(probability);
						frame.getConfig().setSizeParkPointOfSale(
								capacity.intValue());
						signal("waiting_client", frame.getConfig());
					}
				} else {
					JOptionPane jop2 = new JOptionPane();
					jop2.showMessageDialog(null,
							"Il faut compléter les champs", "Attention",
							JOptionPane.ERROR_MESSAGE);
				}
			
		} else {
			JOptionPane jop2 = new JOptionPane();
			jop2.showMessageDialog(null,
					"La probabilité doit être compris entre 0 et 1",
					"Attention", JOptionPane.WARNING_MESSAGE);
		}

	}

	@INTERNAL_OPERATION
	void closed(WindowEvent ev) {
		dispose();
	}

	/**
	 * 
	 * @author squall
	 * 
	 *         In this class you can configure the view
	 */
	class AdminFrame extends JFrame {

		private static final long serialVersionUID = 1L;
		JButton validationButton;
		private JLabel jTextCapacity;
		private JFormattedTextField capacityOfPointSale;
		private JLabel jTextProba;
		private JFormattedTextField probaRent;

		public JFormattedTextField getCapacityOfPointSale() {
			return capacityOfPointSale;
		}

		public void setCapacityOfPointSale(
				JFormattedTextField capacityOfPointSale) {
			this.capacityOfPointSale = capacityOfPointSale;
		}

		public JFormattedTextField getProbaRent() {
			return probaRent;
		}

		public void setProbaRent(JFormattedTextField probaRent) {
			this.probaRent = probaRent;
		}

		protected Administration config = new Administration();

		public Administration getConfig() {
			return config;
		}

		public void setConfig(Administration config) {
			this.config = config;
		}

		Locale locale = new Locale("fr");
		ResourceBundle messages = ResourceBundle.getBundle("gui", locale);
		private JMenuBar menuBar = new JMenuBar();
		private JMenu lang = new JMenu("Language");
		private JMenuItem langFr = new JMenuItem("Francais");
		private JMenuItem langEn = new JMenuItem("Anglais");
		private JPanel end;
		private JPanel top;

		public AdminFrame() {
			setTitle(messages.getString("admin_title"));
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();

			AddMenu();
			AddProbability(panel, gbc);
			AddConfiguration(panel, gbc);
			AddButton(panel, gbc);

			this.setContentPane(panel);
			this.pack();
			this.setVisible(true);
		}

		public void AddMenu() {
			lang.setText(messages.getString("admin_menu_lang"));
			langFr.setText(messages.getString("admin_item_fr"));
			langEn.setText(messages.getString("admin_item_en"));

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
					System.out.println(locale.getLanguage());
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

		private void AddButton(JPanel panel, GridBagConstraints gbc) {

			validationButton = new JButton(messages.getString("admin_button"));
			validationButton.setSize(80, 50);
			JPanel sud = new JPanel();
			sud.add(validationButton);

			/* Nous pouvons passé aux boutons. */
			gbc.gridy = 2; /* nouvelle ligne */
			gbc.gridx = 0;
			gbc.gridheight = 1; /* une seule cellule verticalement suffit */
			gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant de
															// sa
			gbc.anchor = GridBagConstraints.LINE_START; // pas WEST.
			gbc.fill = GridBagConstraints.BOTH;
			panel.add(sud, gbc);

		}

		public void AddProbability(JPanel panel, GridBagConstraints gbc) {
			top = new JPanel();
			this.jTextProba = new JLabel(
					messages.getString("admin_probability_label"));
			this.probaRent = new JFormattedTextField(
					NumberFormat.getNumberInstance());

			this.probaRent.setPreferredSize(new Dimension(100, 25));
			top.add(jTextProba);
			top.add(probaRent);

			top.setBorder(BorderFactory.createTitledBorder(messages
					.getString("admin_probability_border")));

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

		public void AddConfiguration(JPanel panel, GridBagConstraints gbc) {

			end = new JPanel();

			this.jTextCapacity = new JLabel(
					messages.getString("admin_sale_label"));
			this.capacityOfPointSale = new JFormattedTextField(
					NumberFormat.getNumberInstance());

			this.capacityOfPointSale.setPreferredSize(new Dimension(100, 25));
			end.add(jTextCapacity);
			end.add(capacityOfPointSale);
			end.setBorder(BorderFactory.createTitledBorder(messages
					.getString("admin_sale_border")));

			// Creations des contraintes
			gbc.gridx = 0; // la grille commence en (0, 0)
			gbc.gridy = 1;
			gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant sur
															// la colonne
			gbc.gridheight = 1; // valeur par défaut - peut s'étendre sur une
								// seule ligne
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.LINE_START;
			gbc.insets = new Insets(10, 15, 0, 0); // Marges

			panel.add(end, gbc);

		}

		public void updateLang() {
			this.setTitle(messages.getString("admin_title"));
			end.setBorder(BorderFactory.createTitledBorder(messages
					.getString("admin_probability_border")));
			top.setBorder(BorderFactory.createTitledBorder(messages
					.getString("admin_sale_border")));
			validationButton.setText((messages.getString("admin_button")));
			lang.setText(messages.getString("admin_menu_lang"));
			langFr.setText(messages.getString("admin_item_fr"));
			langEn.setText(messages.getString("admin_item_en"));

			this.jTextProba.setText(messages
					.getString("admin_probability_label"));
			this.jTextCapacity.setText(messages.getString("admin_sale_label"));
		}

	}
}
