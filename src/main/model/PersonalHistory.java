package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encompasses the personal history of the patient. It is necessary to have the diabetes status of the
 * patient because its a major risk-factor pancreatic cancer. Other diseases are added by their names as they are less
 * indicative of pancreatic cancer.
 **/

public class PersonalHistory {
    private boolean diabetes;
    private List<String> otherDisease;

    //EFFECTS: sets diabetes status to true or false. Instantiates an empty list of otherDisease.
    public PersonalHistory(boolean diabetes) {
        this.diabetes = diabetes;
        otherDisease = new ArrayList<>();
    }

    //EFFECTS: returns the number of other conditions had in the personal history
    public int numOtherDiseases() {
        return otherDisease.size();
    }

    //setters:

    public void hasDiabetes() {
        diabetes = true;
    }

    public void noDiabetes() {
        diabetes = false;
    }

    //REQUIRES: string is not empty
    //MODIFIES: this
    //EFFECTS: adds the typed string to the past diseases of a patient.
    public void addOtherDisease(String disease) {
        otherDisease.add(disease);
    }

    // getter:
    public boolean getDiabetesStatus() {
        return diabetes;
    }

    public List<String> getPatientDiseases() {
        return otherDisease;
    }

}
