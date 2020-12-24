package model;

import exceptions.PatientNotInDatabaseException;
import exceptions.RepeatPhNException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encompasses the patient database and holds methods that can be used to manipulate the database and
 * the patients that are stored within the database.
 **/

public class PatientDatabase {
    public List<Patient> patientDatabase;

    //EFFECTS: creates a new empty patient database
    public PatientDatabase() {
        patientDatabase = new ArrayList<>();
    }

    //EFFECTS: creates a String of the patients and their PHN from the database. If no patients, then "No patients in
    //         database.
    public String patientsInDatabase() {
        if (patientDatabase.size() > 0) {
            StringBuilder patients = new StringBuilder();
            for (Patient p : patientDatabase) {
                patients.append(p.getFirstName()).append(" ")
                        .append(p.getLastName()).append(" ").append(p.getPersonalHealthNumber()).append("; ");
            }
            return patients.toString();
        } else {
            return "No patients in database.";
        }
    }


    //EFFECTS: returns true if there is a patient with that phn in the database. False otherwise.
    public boolean patientWithPHN(int phn) {
        boolean phnMatch = false;
        for (Patient each : patientDatabase) {
            if (each.getPersonalHealthNumber() == phn) {
                phnMatch = true;
            }
        }
        return phnMatch;
    }



    //EFFECTS: returns the patient with the Personal Health Number (phn) corresponding to the submitted integer.
    // Throws PatientNotInDatabaseException if submitted integer does not correspond to a patient's phn in the database.
    public Patient getPatient(int phn) throws PatientNotInDatabaseException {
        if (!patientWithPHN(phn)) {
            throw new PatientNotInDatabaseException();
        }

        Patient patient = null;
        for (Patient p : patientDatabase) {
            if (phn == p.getPersonalHealthNumber()) {
                patient = p;
            }
        }
        return patient;
    }

    //MODIFIES: this
    //EFFECTS: adds a patient to the database and returns true if no other patient has this personal health number.
    // If a patient in the database has the same phn as the patient being added, it throws a RepeatPhNException and
    // does not add the patient.
    public boolean addPatientToDatabase(Patient p) throws RepeatPhNException {
        if (patientWithPHN(p.getPersonalHealthNumber())) {
            throw new RepeatPhNException();
        }
        patientDatabase.add(p);
        return true;
    }


    //MODIFIES: this
    //EFFECTS: removes the first patient in the database with Personal Health Number phn and returns true. Otherwise
    // it returns false.
    public boolean removePatientFromDatabase(int phn) {
        int i = 0;
        for (Patient each : patientDatabase) {
            if (phn == each.getPersonalHealthNumber()) {
                patientDatabase.remove(i);
                return true;
            }
            i = i + 1;
        }
        return false;
    }


    //EFFECTS: returns the number of patients in the database.
    public int numPatientsInDatabase() {
        return patientDatabase.size();
    }


    //EFFECTS: returns the patient database in the form of a json object.
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("patients", patientsToJson());
        return json;
    }

    // EFFECTS: returns patients in this patient database as a JSON array.
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient patient : patientDatabase) {
            jsonArray.put(patient.toJson());
        }

        return jsonArray;
    }


}
