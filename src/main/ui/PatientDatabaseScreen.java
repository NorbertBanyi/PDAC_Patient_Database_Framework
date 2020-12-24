package ui;

import model.Demographics;
import model.PatientDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Year;
import java.util.regex.Pattern;

/*
This class is the first window of the patient data base series of windows. It is make in the same position as the
previous. The screen has textfields for the first name, last name, phn, day birth, month birth, and year birth. It also
has dropdowns for ethnicity and sex. If any of the things are not inputted correctly there will be an error message.
If everything is inputted correctly and the submitButton is pressed, all the information is passed to the next window
so that it can create a patient object and PatientEnterPersonalAndFamilyHxScreen pops up.

 */

public class PatientDatabaseScreen extends StandardDataBaseWindow implements ActionListener {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phnField;
    private JTextField yearBirthField;
    private JTextField monthBirthField;
    private JTextField dayBirthField;
    private JComboBox ethnicityBox;
    private JComboBox sexBox;
    private JCheckBox hasDiabetes;
    private JButton submitButton;
    private PatientDatabase patientDatabase;


    //EFFECTS: creates the patientdatabase screen at given point p, storing the (loaded) patientDatabase.
    PatientDatabaseScreen(Point p, PatientDatabase patientDatabase) {
        super();
        this.patientDatabase = patientDatabase;
        setFrame();
        setLocation(p);
        makePatientPanel();
        createTextFields();
        setLayout(new BorderLayout());
        makeHasDiabetesCheckBox();
        add(panel, BorderLayout.CENTER);
        add(makePatientTitle());
        add(makeLabelBCC());
        setVisible(true);
    }

    //MODIFIES: patientTitle
    //EFFECTS: makes a title for the patient input panel.
    private Component makePatientTitle() {
        JLabel patientTitle = new JLabel();
        patientTitle.setText("Patient Information");
        patientTitle.setFont(new Font("Futura-Medium", Font.PLAIN, 30));
        patientTitle.setBounds(DATABASE_WIDTH / 2 - 200, 0, 400, 150);
        return patientTitle;
    }



    //MODIFIES: this
    //EFFECTS: creates a checkbox that records whether a patient has diabetes. Adds the checkbox to the panel.
    private void makeHasDiabetesCheckBox() {
        hasDiabetes = new JCheckBox();
        hasDiabetes.setText("Select if you have diabetes");
        hasDiabetes.setFocusable(false);
        hasDiabetes.addActionListener(this);
        hasDiabetes.setBounds(5, 360, 300, 30);
        panel.add(hasDiabetes);

    }

    //MODIFIES: this
    //EFFECTS: creates all the textfields and dropdowns added to the panel as well as the submit button at the bottom of
    // the frame.
    private void createTextFields() {
        createPersonalInfoFields();
        createDemographicsFields();

        submitButton = createSubmitButton();
        submitButton.addActionListener(this);
        add(submitButton);

    }

    //MODIFIES: this
    //EFFECTS: creates textfields and adds them to the patient panels.
    private void createPersonalInfoFields() {
        firstNameField = new JTextField();
        firstNameField.setBounds(5, 20, 390, 27);
        firstNameField.setText("'first name'");
        panel.add(firstNameField);
        lastNameField = new JTextField();
        lastNameField.setBounds(5, 60, 390, 27);
        lastNameField.setText("'last name'");
        panel.add(lastNameField);
        phnField = new JTextField();
        phnField.setBounds(5, 100, 390, 27);
        phnField.setText("'personal health number'");
        panel.add(phnField);
    }

    //MODIFIES: this
    //EFFECTS: creates textfields and comboboxes for filling in patient information related to their demographics. Adds
    // the textfields and comboboxes to the patient in descending order.
    private void createDemographicsFields() {
        yearBirthField = new JTextField();
        yearBirthField.setBounds(5, 140, 390, 27);
        yearBirthField.setText("'year of birth (yyyy)'");
        panel.add(yearBirthField, BorderLayout.CENTER);
        monthBirthField = new JTextField();
        monthBirthField.setBounds(5, 180, 390, 27);
        monthBirthField.setText("'month of birth (mm)'");
        panel.add(monthBirthField, BorderLayout.CENTER);
        dayBirthField = new JTextField();
        dayBirthField.setBounds(5, 220, 390, 27);
        dayBirthField.setText("'day of birth (dd)'");
        panel.add(dayBirthField);
        String[] ethnicities = {"select ethnicity", "White", "Native American", "Asian", "Black",
                "Hispanic", "Pacific islander", "Other"};
        ethnicityBox = new JComboBox(ethnicities);
        ethnicityBox.setBounds(0, 270, 400, 30);
        panel.add(ethnicityBox);
        String[] sexes = {"select sex", "Male", "Female", "Other"};
        sexBox = new JComboBox(sexes);
        sexBox.setBounds(0, 310, 400, 30);
        panel.add(sexBox);
    }


    //MODIFIES: this.
    //EFFECTS: if the submit button is pressed then it checks checks for correct inputs (alerts user if not) and
    // creates a new PatientEnterPersonalAndFamilyHxScreen screen with the patient info at the point of the current
    // screen if correctly inputted.
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
            checkIfCorrectlyInputted();


            Demographics demographics = makeFieldDemographics();
            new PatientEnterPersonalAndFamilyHxScreen(phnField.getText(), firstNameField.getText(),
                    lastNameField.getText(), demographics, hasDiabetes.isSelected(), getLocation(), patientDatabase);
            dispose();
        }


    }

    //EFFECTS: Creates an error message if the selected item from the drop down panels is one of the instruction
    // panels. Throws a number format exception if instruction panel selected.
    private void checkIfCorrectlySelected() {
        if (String.valueOf(ethnicityBox.getSelectedItem()) == "select ethnicity") {
            JOptionPane.showMessageDialog(null, "Must select ethnicity",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
            Integer.parseInt("%^*");
        } else if (String.valueOf(sexBox.getSelectedItem()) == "select sex") {
            JOptionPane.showMessageDialog(null, "Must select sex",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
            Integer.parseInt("%^*");
        }
    }

    //EFFECTS: uses the text inputs to make a demographic.
    private Demographics makeFieldDemographics() {
        int day = Integer.parseInt(dayBirthField.getText());
        int month = Integer.parseInt(monthBirthField.getText());
        int year = Integer.parseInt(yearBirthField.getText());
        LocalDate birth = LocalDate.of(year, month, day);
        return new Demographics(birth, String.valueOf(ethnicityBox.getSelectedItem()),
                String.valueOf(sexBox.getSelectedItem()));
    }

    //EFFECTS: returns error message if an incorrect format is entered for any of the fields. Throws a number format
    // exception in that case too.
    @SuppressWarnings({"checkstyle:MethodLength"})
    private void checkIfCorrectlyInputted() {
        if (!isResponseString(firstNameField)) {
            JOptionPane.showMessageDialog(null, "First name must only contain letters",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
            Integer.parseInt("%^*");
        } else if (!isResponseString(lastNameField)) {
            JOptionPane.showMessageDialog(null, "Last name must only contain letters",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
            Integer.parseInt("%^*");
        } else if (!isResponseInt(phnField)) {
            JOptionPane.showMessageDialog(null, "PHN must be an integer.",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isResponseInt(yearBirthField)
                || Year.now().getValue() < Integer.parseInt(yearBirthField.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid birth year",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isResponseInt(monthBirthField) || 12 < Integer.parseInt(monthBirthField.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid birth month",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isResponseInt(dayBirthField) || 31 < Integer.parseInt(dayBirthField.getText())) {
            JOptionPane.showMessageDialog(null, "Invalid birth day",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
        } else {
            checkIfCorrectlySelected();
        }

    }



}

