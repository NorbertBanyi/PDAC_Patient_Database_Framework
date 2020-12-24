package ui;

import model.PatientDatabase;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * This is the opening screen of the patient database. It allows the user to save the most recently saved patient
 * database, save the current patient database (which replaces any previously saved version), access the patient
 * database, and access the provider database.
 */


public class DatabaseAppOpeningScreen extends StandardDataBaseWindow implements ActionListener {

    private static final String JSON_STORE = "./data/database.json";
    private JButton providerButton;
    private JButton patientButton;
    private JsonReader jsonReader;
    private PatientDatabase patientDatabase;


    //EFFECTS: Creates a database opening screen at the given Point p. The opening screen is aware of the patient
    // database, "patientDatabase". If play is true, then it plays an opening sound. If play is false,
    // there is no sound.
    public DatabaseAppOpeningScreen(Point p, PatientDatabase patientDatabase, boolean play) {
        super();
        this.patientDatabase = patientDatabase;
        initialGUI(p);
        jsonReader = new JsonReader(JSON_STORE);
        if (play) {
            playSound("./data/PatientAddedSound.wav");
        }
        loadDatabase();
    }

    //MODIFIES: this; initialPanel
    //EFFECTS: Sets up the initial GUI at point p with a small panel in the middle of the screen on which
    // there are two buttons that allow the user to go to the patient or provider interface. There are
    // also two buttons that load the last saved version of the database and save the current state. A BCC logo
    // is added to the top left corner.
    private void initialGUI(Point p) {
        setFrame();
        setLocation(p);
        JPanel initialPanel = smallPanel();

        getHomeScreenButtons();

        initialPanel.add(providerButton);
        initialPanel.add(patientButton);

        makeSaveLoadButtons();
        JLabel labelBCC = makeLabelBCC();
        add(initialPanel, BorderLayout.CENTER);
        add(labelBCC);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: makes the save and load buttons and adds them to the initial database screen.
    private void makeSaveLoadButtons() {
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(500, 40));
        add(searchPanel, BorderLayout.SOUTH);
    }


    //MODIFIES: this
    //EFFECTS: Creates buttons to allow the user to go to the patient or provider interface and places them on
    // the panel.
    private void getHomeScreenButtons() {
        providerButton = new JButton();
        providerButton.setBounds(10, 10, super.OPEN_PANEL_WIDTH - 20,
                super.OPEN_PANEL_HEIGHT * 4 / 9);
        providerButton.addActionListener(this);
        providerButton.setText("Provider Interface");
        patientButton = new JButton();
        patientButton.setBounds(10, super.OPEN_PANEL_HEIGHT / 2, super.OPEN_PANEL_WIDTH - 20,
                super.OPEN_PANEL_HEIGHT * 4 / 9);
        patientButton.addActionListener(this);
        patientButton.setText("Patient Interface");
    }

    //MODIFIES: this
    //EFFECTS: If the (patient/provider) button is pressed, then it creates a new (patient/provider) screen at position
    // that the Database Opening screen is at. If the load button is pressed, it loads the last saved version of
    // the program. If the save button is pressed, allows the user to save the current state of the program.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == patientButton) {
            new PatientDatabaseScreen(getLocation(), patientDatabase);
            dispose();
        }
        if (e.getSource() == providerButton) {
            new ProviderDatabaseScreen(getLocation(), patientDatabase);
            dispose();
        }

    }


    // MODIFIES: this
    // EFFECTS: loads the previous database saved from file
    private void loadDatabase() {
        try {
            patientDatabase = jsonReader.read();
            System.out.println("Your database has been loaded");
        } catch (IOException e) {
            System.out.println("Unable load your database");
        }
    }

    // Source of inspiration: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    //REQUIRES: the file path to be saved on your computer or in the program and that file is .wav
    //EFFECTS: Plays the audio file through 1 iteration. Outputs an error message if the file cannot be played.
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }



}
