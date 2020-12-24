package persistance;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
/**
 *This entire class was inspired by the project at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *Followed the reading patterns. Represents a reader that reads a Patient Database from JSON data stored in a chosen
 * file.
 */


public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PatientDatabase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePatientDatabase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private PatientDatabase parsePatientDatabase(JSONObject jsonObject) {
        PatientDatabase pd = new PatientDatabase();
        addPatients(pd, jsonObject);
        return pd;
    }

    // MODIFIES: patient database
    // EFFECTS: parses patients from JSON object and adds them to patient Database
    private void addPatients(PatientDatabase patientDatabase, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patients");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            addPatient(patientDatabase, nextPatient);
        }
    }

    // MODIFIES: patient database
    // EFFECTS: parses patient from JSON object and adds it to patient database
    private void addPatient(PatientDatabase pd, JSONObject jsonObject) {
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        Integer phn = jsonObject.getInt("personalHealthNumber");
        Demographics demographics = getDemographicsFromJson(jsonObject);
        PersonalHistory personalHistory = getPersonalHistoryFromJson(jsonObject);
        FamilyCancerHistory familyCases = getFamilyCancerHistoryFromJson(jsonObject);
        Patient patient = new Patient(phn, firstName, lastName, demographics, personalHistory, familyCases);
        pd.addPatientToDatabase(patient);
    }

    //EFFECTS: creates a demographics object that was saved as a json object previously
    private Demographics getDemographicsFromJson(JSONObject patient) {
        String ethnicity = patient.getString("ethnicity");
        String sex = patient.getString("sex");
        Integer day = patient.getInt("day");
        Integer month = patient.getInt("month");
        Integer year = patient.getInt("year");
        LocalDate dob = LocalDate.of(year, month, day);
        return new Demographics(dob, ethnicity, sex);
    }

    //EFFECTS: creates a personal history object that was saved as json Array previously
    private PersonalHistory getPersonalHistoryFromJson(JSONObject patient) {
        Boolean diabetes = patient.getBoolean("diabetes?");
        PersonalHistory personalHistory = new PersonalHistory(diabetes);
        JSONArray jsonPersonalCases = patient.getJSONArray("patientDiseases");
        for (Object jdisease : jsonPersonalCases) {
            JSONObject nextPatient = (JSONObject) jdisease;
            personalHistory.addOtherDisease(nextPatient.getString("disease"));
        }
        return personalHistory;
    }

    //EFFECTS: creates a FamilyCancerHistory from json object
    private FamilyCancerHistory getFamilyCancerHistoryFromJson(JSONObject patient) {
        FamilyCancerHistory familyCancerHx = new FamilyCancerHistory();
        JSONArray jsonPersonalCases = patient.getJSONArray("familyCancerHx");
        for (Object jdisease : jsonPersonalCases) {
            JSONObject nextPatient = (JSONObject) jdisease;
            String cancerType = nextPatient.getString("cancerType");
            Integer degreeRelative = nextPatient.getInt("degreeRelative");
            familyCancerHx.addFamilyCase(degreeRelative, cancerType);
        }
        return familyCancerHx;
    }
}
