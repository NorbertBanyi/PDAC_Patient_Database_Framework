package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

/**
 * This class encompasses the profile of a patient. Each patient must have data associated with:
 * personal health number, first name, last name, demographics, personalHx, and familial cancer history.
 * personal health numbers must be > 0, and first and last names must not be empty strings.
 **/


public class Patient {

    private int personalHealthNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Demographics demographics;
    private PersonalHistory personalHx;
    private FamilyCancerHistory familyCancerHx;

    //REQUIRES: first and last must not be the empty string, phn > 0.
    //EFFECTS: Creates a patient
    public Patient(int phn, String first, String last, Demographics d, PersonalHistory p, FamilyCancerHistory f) {
        personalHealthNumber = phn;
        firstName = first;
        lastName = last;
        middleName = "";
        demographics = d;
        personalHx = p;
        familyCancerHx = f;
    }

    //REQUIRES: degree > 0
    //EFFECTS: returns the number of relatives of nth degree with cancer
    public int getNumRelativesWithCancer(int n) {
        return familyCancerHx.numRelativesWithCancer(n);
    }

    //REQUIRES: degree > 0
    //MODIFIES: this
    //EFFECTS: adds a case of familial cancer that is 'ct' type of 'degree' degree to the patient
    public void addCaseOfFamilialCancer(int degree, String type) {
        familyCancerHx.addFamilyCase(degree, type);
    }


    // setters


    public void setDemographics(Demographics d) {
        demographics = d;

    }

    public void setPersonalHx(PersonalHistory ph) {
        personalHx = ph;

    }

    public void setFamilyCancerHx(FamilyCancerHistory fch) {
        familyCancerHx = fch;
    }

    //REQUIRES: personal Health Number > 0
    //MODIFIES: this
    //EFFECTS: sets the personal health number of the patient.
    public void setPersonalHealthNumber(int n) {
        personalHealthNumber = n;
    }

    //REQUIRES: string is not empty
    //MODIFIES: this
    //EFFECTS: sets the first name of the patient
    public void setFirstName(String f) {
        firstName = f;
    }

    //REQUIRES: string is not empty
    //MODIFIES: this
    //EFFECTS: sets the middle name of the patient
    public void setMiddleName(String m) {
        middleName = m;
    }

    //REQUIRES: string is not empty
    //MODIFIES: this
    //EFFECTS: sets the last name of the patient
    public void setLastName(String l) {
        lastName = l;
    }


    //getters:
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Integer getPersonalHealthNumber() {
        return personalHealthNumber;
    }

    public Demographics getDemographics() {
        return demographics;
    }

    public PersonalHistory getPersonalHx() {
        return personalHx;
    }

    public FamilyCancerHistory getFamilyCancerHx() {
        return familyCancerHx;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("personalHealthNumber", personalHealthNumber);
        json.put("middleName", middleName);
        json.put("ethnicity", demographics.getEthnicity());
        json.put("sex", demographics.getSex());
        LocalDate dob = demographics.getDateOfBirth();
        json.put("day", dob.getDayOfMonth());
        json.put("month", dob.getMonthValue());
        json.put("year", dob.getYear());
        json.put("diabetes?", personalHx.getDiabetesStatus());
        json.put("patientDiseases", jsonPersonalHistory(personalHx.getPatientDiseases()));
        json.put("familyCancerHx", familyCancerHx.toJson());
        return json;
    }

    //EFFECTS: makes the patient disease history into a JSONArray of their history
    private JSONArray jsonPersonalHistory(List<String> diseases) {
        JSONArray array = new JSONArray();

        for (String disease : diseases) {
            JSONObject jdisease = new JSONObject();
            jdisease.put("disease", disease);
            array.put(jdisease);
        }
        return array;
    }


}
