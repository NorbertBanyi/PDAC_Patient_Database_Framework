package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
This class creates the screen in which a patient can add their personal diseases as well as all their family's cancer
cases. This class remembers the information inputted in the PatientDatabaseScreen page.
 */

public class PatientEnterPersonalAndFamilyHxScreen extends StandardDataBaseWindow implements ActionListener {
    private Patient patient;
    private JTextField personalCase;
    JButton personalCaseSubmit;
    JButton cancerCaseSubmit;
    private JButton submitButton;

    int heightOfEnteredDisease;
    int heightOfEnteredCancer;
    JComboBox cancerType;
    JComboBox degreeRelative;
    static final int MAX_PERSONAL_DISEASE = 7;
    private int numFamilyCancer = 0;
    static final int MAX_FAMILY_CANCER = 7;
    private int numPersonalDisease = 0;
    private PatientDatabase patientDatabase;



    //EFFECTS: creates window where you input patient personal and family history. Creates patient object at the
    // position of the previous window with information passed in the previous window.
    public PatientEnterPersonalAndFamilyHxScreen(String phn, String first, String last, Demographics d,
                                                 boolean diabetes, Point p, PatientDatabase patientDatabase) {
        super();
        setFrame();
        setLocation(p);
        this.patientDatabase = patientDatabase;
        heightOfEnteredDisease = 55;
        heightOfEnteredCancer = PANEL_HEIGHT / 2 + 65;
        makePatientPanel();
        makeSubmitButton();
        patient = new Patient(Integer.parseInt(phn), first, last, d, new PersonalHistory(diabetes),
                new FamilyCancerHistory());
        makeAddPersonalHx();
        makeAddFamilyCancerHx();
        add(panel, BorderLayout.CENTER);
        add(makePatientTitleTwo());
        add(makeLabelBCC());
        setVisible(true);
    }


    //MODIFIES: patientTitle
    //EFFECTS: makes a title over the panel.
    private Component makePatientTitleTwo() {
        JLabel patientTitle = new JLabel();
        patientTitle.setText("Patient and Family Disease History");
        patientTitle.setFont(new Font("Futura-Medium", Font.PLAIN, 24));
        patientTitle.setBounds(DATABASE_WIDTH / 2 - 200, 5, 400, 150);
        return patientTitle;
    }

    //MODIFIES: this.
    //EFFECTS: makes a submit button at the bottom of the panel and adds to frame.
    private void makeSubmitButton() {
        submitButton = createSubmitButton();
        submitButton.addActionListener(this);
        add(submitButton);
    }

    //MODIFIES: this
    //EFFECTS: constructs the textfield in which people can add their personal history of diseases as well as the button
    // that allows the user to submit a disease.
    private void makeAddPersonalHx() {
        personalCase = new JTextField();
        personalCase.setBounds(5, 20, 330, 30);
        personalCase.setText("'enter your diseases here (up to " + MAX_PERSONAL_DISEASE + ")'");
        panel.add(personalCase);
        personalCaseSubmit = new JButton("Enter");
        personalCaseSubmit.setBounds(331, 23, 60, 30);
        personalCaseSubmit.addActionListener(this);
        panel.add(personalCaseSubmit);
    }

    //MODIFIES: this
    //EFFECTS: creates the dropdown's to choose cancer type and degree of relatives. Also creates the button that allows
    // the user to input these values.
    private void makeAddFamilyCancerHx() {
        String[] cancerTypes = {"select relative's cancer type", "Lung", "Breast", "Colorectal", "Prostate", "Ovarian",
                "Stomach", "Pancreatic", "Leukemia", "Skin", "Other"};
        cancerType = new JComboBox(cancerTypes);
        cancerType.setBounds(5, PANEL_HEIGHT / 2 + 10, 250, 30);
        panel.add(cancerType);
        String[] degrees = {"select degree of relative", "1", "2", "3", "4"};
        degreeRelative = new JComboBox(degrees);
        degreeRelative.setBounds(5, PANEL_HEIGHT / 2 + 35, 250, 30);
        panel.add(degreeRelative);
        cancerCaseSubmit = new JButton("Enter");
        cancerCaseSubmit.setBounds(331, PANEL_HEIGHT / 2 + 10, 60, 30);
        cancerCaseSubmit.addActionListener(this);
        panel.add(cancerCaseSubmit);
    }

    //MODIFIES: this; patient
    //EFFECT: if the personalCaseSubmit button is pressed then the entered personal case is added to the patient.
    // If the cancer case submit button is pressed, then the family case is added to the patient.
    // If the submit button is pressed, then the patient is prompted to give concent to have their information recorded.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == personalCaseSubmit) {
            if (numPersonalDisease < MAX_PERSONAL_DISEASE) {
                patient.getPersonalHx().addOtherDisease(personalCase.getText());
                displayDiseaseAdded(heightOfEnteredDisease, 1);
                numPersonalDisease = numPersonalDisease + 1;
            }
        }
        if (e.getSource() == cancerCaseSubmit) {
            if (numFamilyCancer < MAX_FAMILY_CANCER) {
                checkIfCorrectlySelectedDropDowns();
                patient.getFamilyCancerHx().addFamilyCase(Integer.parseInt(
                        String.valueOf(degreeRelative.getSelectedItem())),
                        String.valueOf(cancerType.getSelectedItem()));
                displayDiseaseAdded(heightOfEnteredCancer, 2);
                numFamilyCancer = numFamilyCancer + 1;
            }

        }

        if (e.getSource() == submitButton) {
            new PatientConsentScreen(patient, getLocation(), patientDatabase);
            dispose();
        }

    }

    //EFFECTS: checks to see if the dropdowns are not on the instruction element before adding the information to the
    // patient. Creates error popup if the incorrect one is selected.
    private void checkIfCorrectlySelectedDropDowns() {
        if (String.valueOf(cancerType.getSelectedItem()) == "select relative's cancer type") {
            JOptionPane.showMessageDialog(null, "Must select cancer type",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
        }
        if (String.valueOf(degreeRelative.getSelectedItem()) == "select degree of relative") {
            JOptionPane.showMessageDialog(null, "Must select degree",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECT: prints a message telling the user that they added a certain disease to the patient database!
    private void displayDiseaseAdded(int startHeight, int thingAdded) {
        JLabel addedDisease = new JLabel();
        addedDisease.setBounds(10, startHeight, 380, 17);
        addedDisease.setFont(new Font("Dialog", Font.PLAIN, 10));
        addedDisease.setForeground(new Color(116, 142, 111));

        if (thingAdded == 1) {
            heightOfEnteredDisease = startHeight + 17;
            addedDisease.setText("added disease: " + personalCase.getText());

        }
        if (thingAdded == 2) {
            heightOfEnteredCancer = startHeight + 17;
            addedDisease.setText("added case: " + "degree of relative - " + degreeRelative.getSelectedItem()
                    + ", type of cancer - " + cancerType.getSelectedItem());
        }

        panel.add(addedDisease);
        validate();
        repaint();

    }
}
