package ui;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public abstract class StandardDataBaseWindow extends JFrame {
    protected static final int DATABASE_HEIGHT = 600;
    protected static final int DATABASE_WIDTH = 1000;
    protected static final int OPEN_PANEL_WIDTH = 300;
    protected static final int OPEN_PANEL_HEIGHT = 200;
    protected JPanel panel;
    protected static final int PANEL_WIDTH = 400;
    protected static final int PANEL_HEIGHT = 400;

    //EFFECTS: returns the BCC logo object.
    protected static JLabel makeLabelBCC() {
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("./data/BCCHLOGO.png").getImage()
                .getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        label.setIcon(imageIcon);
        label.setText("HCP PDAC Tool");
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setForeground(new Color(92, 92, 106));
        label.setFont(new Font("Futura-CondensedMedium", Font.PLAIN, 12));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setPreferredSize(new Dimension(10, 10));
        return label;
    }

    //EFFECTS: makes the big grey panel in the patient view.
    protected void makePatientPanel() {
        panel = new JPanel();
        panel.setBackground(new Color(230, 230, 230));
        panel.setBounds(getWidth() / 2 - (PANEL_WIDTH / 2),
                getHeight() / 2 - (PANEL_HEIGHT * 5 / 10),
                PANEL_WIDTH, PANEL_HEIGHT);
        panel.setLayout(null);
    }

    //MODIFIES: this
    //EFFECTS: Sets the output frame size and default options.
    protected void setFrame() {
        setTitle("HCP Database - Norbert Banyi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DATABASE_WIDTH, DATABASE_HEIGHT);
        setLayout(new BorderLayout());
    }

    //EFFECTS: returns true if response is an integer, true otherwise.
    protected boolean isResponseInt(JTextField field) {
        String s = field.getText();
        try {
            Integer.parseInt(s);
            return true;
            //is an integer
        } catch (NumberFormatException e) {
            return false;
            //is not integer
        }
    }

    //EFFECTS: returns true if the field only contains letters, false otherwise.
    protected boolean isResponseString(JTextField field) {
        String s = field.getText();
        return Pattern.matches("[a-zA-Z]+", s);
    }


    //EFFECTS: creates a submit button that goes to the next page.
    protected JButton createSubmitButton() {
        JButton submitButton = new JButton("Next");
        submitButton.setVisible(true);
        submitButton.setBounds(DATABASE_WIDTH / 2 - 30, DATABASE_HEIGHT * 6 / 7, 70, 35);
        return submitButton;
    }

    //MODIFIES: this
    //EFFECTS: creates a small panel on which small buttons can be placed.
    protected JPanel smallPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 230));
        panel.setBounds(getWidth() / 2 - (OPEN_PANEL_WIDTH / 2),
                getHeight() / 2 - (OPEN_PANEL_HEIGHT / 2),
                OPEN_PANEL_WIDTH, OPEN_PANEL_HEIGHT);
        panel.setLayout(null);
        return panel;
    }

}
