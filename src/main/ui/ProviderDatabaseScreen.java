package ui;

import exceptions.PatientNotInDatabaseException;
import model.Patient;
import model.PatientDatabase;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
The provider database screen allows the user to see all of the patients that are in the database and allows the
user to search patients from the database to see all of their information. The panel that appears when the patient
is searched for allows the user to also amend certain parts of the patient's information.
 */
public class ProviderDatabaseScreen extends StandardDataBaseWindow implements ActionListener {
    private JTextField searchBar;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox sexField;
    private JScrollPane pane;
    private Patient patientOfInterest;

    private JButton searchButton;
    private JButton backButton;
    private JButton updatePatient;
    private static final String JSON_STORE = "./data/database.json";
    private PatientDatabase patientDatabase;
    private JsonWriter jsonWriter;

    //EFFECTS: creates a new providerDatabase screen which immediately showcases all of the patients first and last
    // names as well as phn that are in the current (loaded) phn. There is a searchbar and BCC logo.
    public ProviderDatabaseScreen(Point p, PatientDatabase patientDatabase) {
        super();
        this.patientDatabase = patientDatabase;
        jsonWriter = new JsonWriter(JSON_STORE);
        setFrame();
        setLocation(p);
        makePatientSearch();
        showDatabaseEntries();
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates a panel and displays all of the names and phn's of the patients in the database.
    private void showDatabaseEntries() {
        makePatientPanel();
        if (patientDatabase.numPatientsInDatabase() * 25 + 25 > PANEL_HEIGHT) {
            panel.setBounds(getWidth() / 2 - (PANEL_WIDTH / 2),
                    getHeight() / 2 - (PANEL_HEIGHT * 5 / 10),
                    PANEL_WIDTH, patientDatabase.numPatientsInDatabase() * 25 + 25);
        }
        int height = 5;
        putPatientsOnPanel(height);
        pane = new JScrollPane(panel);
        pane.setBounds(getWidth() / 2 - (PANEL_WIDTH / 2),
                getHeight() / 2 - (PANEL_HEIGHT * 5 / 10),
                PANEL_WIDTH, PANEL_HEIGHT);
        pane.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane, BorderLayout.CENTER);
        add(makeLabelBCC());
    }

    //MODIFIES: this.
    //EFFECTS: takes each of the patients in patientDatabase and adds a label of their first, last, and phn to the
    // display panel.
    private void putPatientsOnPanel(int height) {
        for (Patient each : patientDatabase.patientDatabase) {
            JLabel addedPatient = new JLabel();
            addedPatient.setBounds(10, height, 330, 25);
            addedPatient.setFont(new Font("Dialog", Font.PLAIN, 15));
            addedPatient.setForeground(new Color(0, 0, 0));
            addedPatient.setText("PHN: "
                    + each.getPersonalHealthNumber().toString() + ", NAME: "
                    + each.getFirstName() + " " + each.getLastName());
            height = height + 25;
            panel.add(addedPatient);
            validate();
            repaint();
        }
    }

    //MODIFIES: searchPanel, this
    //EFFECTS: makes the patient search bar that is used to view more information about a single patient in the
    // patient database.
    private void makePatientSearch() {
        searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(300, 30));
        searchBar.setMinimumSize(new Dimension(100, 25));
        searchBar.setText("'search with patient personal health number'");
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 30));
        searchButton.setMinimumSize(new Dimension(60, 25));
        searchButton.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(500, 40));
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 30));
        backButton.setMinimumSize(new Dimension(60, 25));
        backButton.addActionListener(this);
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);
        add(searchPanel, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: If the search button is pressed then if the patient is in the database, it displays their information
    // with certain components that are editable. If patient not in database or invalid phn entered, error is displayed.
    // If back button is pressed then a new DatabaseOpening screen is displayed. If update patient button is pressed,
    // then the patient information is saved there.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            int id = 0;
            try {
                id = Integer.parseInt(searchBar.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Not valid PHN!",
                        "Invalid search", JOptionPane.ERROR_MESSAGE);
                return;
            }
            createPatientDisplay(id);
        }
        if (e.getSource() == backButton) {
            new DatabaseAppOpeningScreen(getLocation(), patientDatabase, false);
            dispose();
        }
        if (e.getSource() == updatePatient) {
            patientOfInterest.setFirstName(firstNameField.getText());
            patientOfInterest.setLastName(lastNameField.getText());
            patientOfInterest.getDemographics().setSex(sexField.getSelectedItem().toString());
            JOptionPane.showMessageDialog(null, "Patient successfully updated!",
                    "Make sure to save.", JOptionPane.PLAIN_MESSAGE);
            saveDatabase();
        }

    }

    //MODIFIES: this
    //EFFECTS: creates the patient display where the user is able to modify the
    private void createPatientDisplay(int id) {
        if (patientDatabase.patientWithPHN(id)) {
            remove(pane);
            revalidate();
            makePatientPanel();
            Patient patient = null;

            try {
                patient = patientDatabase.getPatient(Integer.parseInt(searchBar.getText()));
            } catch (PatientNotInDatabaseException e) {
                e.printStackTrace();
            }

            patientOfInterest = patient;
            createPatientEntries(patient);
            add(panel, BorderLayout.CENTER);
            add(makeLabelBCC());
            validate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Patient not in database!",
                    "Invalid search", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates the fields in which patient information can be updated and puts it in the panel where the patient
    // is viewed.
    private void createPatientEntries(Patient patient) {
        firstNameField = new JTextField();
        firstNameField.setBounds(5, 20, 390, 25);
        firstNameField.setText(patient.getFirstName());
        panel.add(firstNameField);
        lastNameField = new JTextField();
        lastNameField.setBounds(5, 45, 390, 25);
        lastNameField.setText(patient.getLastName());
        panel.add(lastNameField);
        createConstantInfo(patient);
        String[] sexes = {"Male", "Female", "Other"};
        sexField = new JComboBox(sexes);
        sexField.setBounds(5, 145, 390, 25);
        sexField.setSelectedIndex(chooseIndex());
        panel.add(sexField);
        updatePatient = new JButton("Update");
        updatePatient.setBounds(PANEL_WIDTH / 2 - 40, 340, 80, 35);
        updatePatient.addActionListener(this);
        panel.add(updatePatient);

    }

    //EFFECTS: returns an index corresponding to the sex of the person.
    private int chooseIndex() {
        if ("Male".equals(patientOfInterest.getDemographics().getSex())) {
            return 0;
        } else if ("Female".equals(patientOfInterest.getDemographics().getSex())) {
            return 1;
        } else {
            return 2;
        }
    }

    //MODIFIES: this
    //EFFECTS: creates the fields of the patient that cannot be changed after a patient is searched up.
    private void createConstantInfo(Patient patient) {
        JLabel phnField = new JLabel();
        phnField.setBounds(10, 70, 390, 25);
        phnField.setText("PHN: " + patient.getPersonalHealthNumber().toString());
        panel.add(phnField);
        JLabel birthField = new JLabel();
        birthField.setBounds(10, 95, 390, 25);
        birthField.setText("DOB (yyyy-mm-dd): " + patient.getDemographics().getDateOfBirth().toString());
        panel.add(birthField);
        JLabel ethnicityField = new JLabel();
        ethnicityField.setBounds(10, 120, 390, 25);
        ethnicityField.setText("ETHNICITY: " + patient.getDemographics().getEthnicity());
        panel.add(ethnicityField);
        JLabel diseasesField = new JLabel();
        diseasesField.setBounds(10, 170, 390, 25);
        diseasesField.setText("Personal Hx: DIABETES: " + patient.getPersonalHx().getDiabetesStatus()
                + ", # Other Diseases: " + (patient.getPersonalHx().getPatientDiseases().size()));
        panel.add(diseasesField);
        JLabel familyCancerField = new JLabel();
        familyCancerField.setBounds(10, 195, 390, 25);
        familyCancerField.setText("# Familial cancer cases : First degree: "
                + patient.getFamilyCancerHx().numRelativesWithCancer(1) + ", Second degree: "
                + patient.getFamilyCancerHx().numRelativesWithCancer(2));
        panel.add(familyCancerField);
    }

    // EFFECTS: writes the current patient database to file, overriding any previous written patient database
    private void saveDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.write(patientDatabase);
            jsonWriter.close();
            System.out.println("Your database has been saved.");
        } catch (FileNotFoundException e) {
            System.out.println("Unable save your database.");
        }
    }
}

