package ui;

import exceptions.RepeatPhNException;
import model.Patient;
import model.PatientDatabase;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
This screen is displayed after the patient has inputted all of their information. They can choose to consent to their
information being stored, in which case a patient object with their entered information is added to the patient database
, a sound is played, a confirmation popup appears, the GUI returns to the opening screen. The patient can also choose to
not consent and their information is not added to the patient database. There is a message that says their info was not
saved and the homepage of the gui is prompted.
 */

public class PatientConsentScreen extends StandardDataBaseWindow implements ActionListener {
    private Patient patient;
    private JButton consentButton;
    private JButton rejectConsentButton;
    private static final String JSON_STORE = "./data/database.json";
    private PatientDatabase patientDatabase;
    private JsonWriter jsonWriter;

    //EFFECTS: creates the patient consent screen at the same point as the previous screen with a BCC logo.
    // a consent button and non-consent button are displayed which are used to decide whether the patient info is added
    // to the database.
    PatientConsentScreen(Patient patient, Point point, PatientDatabase patientDatabase) {
        super();
        this.patient = patient;
        jsonWriter = new JsonWriter(JSON_STORE);
        this.patientDatabase = patientDatabase;
        setFrame();
        setLocation(point);
        JPanel consentPanel = smallPanel();
        getConstentScreenButtons();
        consentPanel.add(consentButton);
        consentPanel.add(rejectConsentButton);
        add(consentPanel);
        add(makeLabelBCC());
        setVisible(true);
    }

    //MODIFIES: this.
    //EFFECTS: Creates consent and not consent buttons and places them on panel.
    private void getConstentScreenButtons() {
        consentButton = new JButton();
        consentButton.setBounds(10, 10, super.OPEN_PANEL_WIDTH - 20,
                super.OPEN_PANEL_HEIGHT * 4 / 9);
        consentButton.addActionListener(this);
        consentButton.setText("I CONSENT to storing my data.");
        rejectConsentButton = new JButton();
        rejectConsentButton.setBounds(10, super.OPEN_PANEL_HEIGHT / 2, super.OPEN_PANEL_WIDTH - 20,
                super.OPEN_PANEL_HEIGHT * 4 / 9);
        rejectConsentButton.addActionListener(this);
        rejectConsentButton.setText("I do NOT CONSENT to storing my data.");
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

    //MODIFIES: this; patientDatabase.
    //EFFECTS: If the consent button is pressed, then the patient's info is added to the patient database,
    // a sound is played, a confirmation popup appears, the GUI returns to the opening screen. If the reject consent
    // button is pressed, then their information is not saved, there is a message that says their info was not
    // saved, and the homepage of the gui is prompted.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consentButton) {
            patientConsented();
        }
        if (e.getSource() == rejectConsentButton) {
            new DatabaseAppOpeningScreen(getLocation(), patientDatabase, false);
            JOptionPane.showMessageDialog(null, "Data disposed of!",
                    "Patient Data Not Saved", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void patientConsented() {
        try {
            patientDatabase.addPatientToDatabase(patient);
        } catch (RepeatPhNException repeatPhNException) {
            JOptionPane.showMessageDialog(null, "This patient is already on file.",
                    "Patient Data Not Saved", JOptionPane.ERROR_MESSAGE);
            new DatabaseAppOpeningScreen(getLocation(), patientDatabase, false);
            JOptionPane.showMessageDialog(null, "Data disposed of!",
                    "Patient Data Not Saved", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        saveDatabase();
        new DatabaseAppOpeningScreen(getLocation(), patientDatabase, true);
        JOptionPane.showMessageDialog(null, "Patient successfully added!",
                "Make sure to save.", JOptionPane.PLAIN_MESSAGE);
        dispose();
    }


}
