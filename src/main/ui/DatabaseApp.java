package ui;

import exceptions.PatientNotInDatabaseException;
import model.*;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;


/**
 * This implementation is based off of the tellerApp provided as part of the deliverable 1 description.
 * The project can be found at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * This class is the console application that can create patients with all their information and print patients.
 */

// Patient database application
public class DatabaseApp {
    private static final String JSON_STORE = "./data/database.json";
    private Scanner input;
    private PatientDatabase patientDatabase;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: runs the database application
    public DatabaseApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        patientDatabase = new PatientDatabase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDatabase();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runDatabase() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    //MODIFIES: this
    //EFFECTS: processes user commands
    private void processCommand(String command) {
        if (command.equals("patient")) {
            doPatientInput();
        } else if (command.equals("information")) {
            doPatientViewing();
        } else if (command.equals("save")) {
            saveDatabase();
        } else if (command.equals("load")) {
            loadDatabase();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: Allows user to view patients in the database and see how many cases of familial cancer of nth degree
    //          there is for a certain patient.
    private void doPatientViewing() {
        patientViewingMenu();
        String choice = input.next();
        choice.toLowerCase();
        if (choice.equals("view")) {
            System.out.println(patientDatabase.patientsInDatabase());
        } else if (choice.equals("degree")) {
            Integer phn = getPHN();
            Integer degree = getDegree();
            if (!patientDatabase.patientWithPHN(phn)) {
                System.out.println("Patient not in Database");
            } else {
                try {
                    System.out.println(patientDatabase.getPatient(phn).getNumRelativesWithCancer(degree));
                } catch (PatientNotInDatabaseException e) {
                    System.out.println("There is no patient with the PHN.");
                }
            }
        }
    }

    //EFFECTS: prompts user to enter phn of the patient for which we want to look at num degree family members with
    // cancer.
    private Integer getPHN() {
        Integer selection = 0;
        while (!(selection > 0)) {
            System.out.println("Enter the patient's personal health number");
            selection = input.nextInt();
        }
        return selection;
    }

    //EFFECTS: prompts the user to enter the degree of relative of the patient
    private Integer getDegree() {
        Integer selection = 0;
        while (!(selection > 0)) {
            System.out.println("Enter the degree of relatives for which we are retrieving number of cancer cases");
            selection = input.nextInt();
        }
        return selection;
    }

    //EFFECTS: demonstrates which commands are possible to do when viewing the database
    private void patientViewingMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t'view' -> View all patients in database");
        System.out.println("\t'degree' -> Retrieve for a patient the number of relatives of a certain degree with"
                + " cancer");

    }


    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doPatientInput() {
        String firstName = patientFirstName();
        String lastName = patientLastName();
        int phn = patientPersonalHealthNumber();
        Demographics demographic = patientDemographic();
        PersonalHistory personalHx = patientHistory();
        FamilyCancerHistory familyHx = familyHistory();
        Patient patient = new Patient(phn, firstName, lastName, demographic, personalHx, familyHx);

        String choice = addPatient();
        if (choice.equals("yes")) {
            patientDatabase.addPatientToDatabase(patient);
            System.out.println("Patient added!");
        } else {
            System.out.println("Patient was not added!");
        }
    }

    //REQUIRES: user inputted patients name is not empty
    //EFFECTS: prompts users to enter the first name of the patient for which the profile is being made
    private String patientFirstName() {
        String selection = "";

        while (!(selection.length() > 0)) {
            System.out.println("Enter the patient's first name");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    //REQUIRES: user inputted patients last name is not empty
    //EFFECTS: prompts users to enter the last name of the patient for which the profile is being made
    private String patientLastName() {
        String selection = "";

        while (!(selection.length() > 0)) {
            System.out.println("Enter the patient's last name");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    //REQUIRES: user inputted patient phn > 0
    //EFFECTS: prompts users to enter the phn of the patient for which the profile is being made
    private Integer patientPersonalHealthNumber() {
        int selection = 0;

        while (!(selection > 0)) {
            System.out.println("Enter the patient's Personal Health Number");
            selection = input.nextInt();
        }
        return selection;
    }

    //REQUIRES: user inputted {year, month, day} > 0, sex is one of {"male", "female", "other"}, and ethnicity is one
    // of {"white", "native American", "asian", "black", "hispanic", or "pacific islander"}.
    //EFFECTS: prompts the user to enter year, month, day, ethnicity, and sex for the patient profile
    private Demographics patientDemographic() {
        int year = 0;
        int month = 0;
        int day = 0;
        String ethnicity;
        String sex;
        while (!(year > 0)) {
            System.out.println("Enter the year the patient was born (do not include leading 0's)");
            year = input.nextInt();
        }
        while (!(month > 0)) {
            System.out.println("Enter the month the patient was born (do not include leading 0's)");
            month = input.nextInt();
        }
        while (!(day > 0)) {
            System.out.println("Enter the day the patient was born (do not include leading 0's)");
            day = input.nextInt();
        }
        LocalDate pdate = LocalDate.of(year, month, day);
        ethnicity = getEthnicity();
        sex = getSex();
        return new Demographics(pdate, ethnicity, sex);
    }

    //REQUIRES: sex is one of {"male", "female", "other"}
    //EFFECTS: prompts user to input sex for patient
    private String getSex() {
        String sex = "";
        while (!(sex.length() > 0)) {
            System.out.println("Enter the patient's sex ('Male'/'Female'/'Other')");
            sex = input.next();
            sex = sex.toLowerCase();
        }
        return sex;
    }

    //REQUIRES: sex is one of {"white", "native American", "asian", "black", "hispanic", or "pacific islander"}
    //EFFECTS: prompts user to input ethnicity for patient
    private String getEthnicity() {
        String ethnicity = "";
        while (!(ethnicity.length() > 0)) {
            System.out.println("Enter the patient's ethnicity ('White'/'Native American'/'Asian'/'Black'/'Hispanic'/"
                    + "'Pacific Islander'/'Other')");
            ethnicity = input.next();
            ethnicity = ethnicity.toLowerCase();
        }
        return ethnicity;
    }

    //EFFECTS: prompts user to input all of the necessary information to create the patient history. Large amounts of
    //         diseases can be added.
    private PersonalHistory patientHistory() {
        String diabetesStatus;
        boolean hasDiabetes = true;
        String addDisease = "";
        String patientDisease;
        diabetesStatus = getDiabetesStatus();
        if (diabetesStatus.equals("no")) {
            hasDiabetes = false;
        }
        PersonalHistory personalHistory = new PersonalHistory(hasDiabetes);
        while (!(addDisease.equals("no"))) {
            System.out.println("Does the patient have other disease? ('yes'/'no')");
            String uinput = input.next();
            uinput.toLowerCase();

            if ("yes".equals(uinput)) {
                System.out.println("Enter the name of the disease");
                Scanner scanner1 = new Scanner(System.in).useDelimiter("\n");
                patientDisease = scanner1.next();
                personalHistory.addOtherDisease(patientDisease);
            } else if (uinput.equals("no")) {
                addDisease = "no";
            }
        }
        return personalHistory;
    }

    //EFFECTS: prompts the user to input the diabetes status of the patient.
    private String getDiabetesStatus() {
        String diabetesStatus = "";
        while (!(diabetesStatus.equals("yes") || diabetesStatus.equals("no"))) {
            System.out.println("Does the patient have diabetes? ('yes'/'no')");
            diabetesStatus = input.next();
            diabetesStatus = diabetesStatus.toLowerCase();
        }
        return diabetesStatus;
    }

    //EFFECTS: prompts the user to input family cancer history information including cancer type (as specified in the
    // FamilyCancerHistory class), and degree of the relative (d > 0).
    private FamilyCancerHistory familyHistory() {
        String familyCases = "";
        String cancerType;
        Integer degreeRelative;
        FamilyCancerHistory familyCancerHx = new FamilyCancerHistory();

        while (!(familyCases.equals("no"))) {
            System.out.println("Does the patient have family members with cancer that have not been entered? "
                    + "('yes'/'no')");
            String uinput = input.next();
            uinput.toLowerCase();
            if (uinput.equals("yes")) {
                System.out.println("What type of cancer does the patient's family member have? ('lung'/'breast'/"
                        + "'colorectal/'prostate'/'skin'/'ovarian'/'stomach'/pancreatic'/'leukemia'/'other')");
                cancerType = input.next();
                cancerType = cancerType.toLowerCase();
                System.out.println("What degree is the relative? ('1'/'2'/'3'/'4'/'5')");
                degreeRelative = input.nextInt();
                familyCancerHx.addFamilyCase(degreeRelative, cancerType);
            } else if (uinput.equals("no")) {
                familyCases = "no";
            }
        }
        return familyCancerHx;
    }

    //prompts user to decide to put the patient in the database
    private String addPatient() {
        String selection = "";
        while (!(selection.equals("yes") || selection.equals("no"))) {
            System.out.println("would you like to add patient to database? ('yes'/'no')");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        return selection;
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t'patient' -> Input patient information");
        System.out.println("\t'information' -> Retrieve database information");
        System.out.println("\t'save' -> Save database");
        System.out.println("\t'load' -> Load database");
        System.out.println("\t'quit' -> quit");
    }

    // EFFECTS: saves the current database to file
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


}
