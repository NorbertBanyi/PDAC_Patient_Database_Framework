package persistence;

import exceptions.PatientNotInDatabaseException;
import model.*;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

/**
 *This entire test class was inspired by the project at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *Followed the same test patterns with same test cases
 */

public class JsonReaderTest extends  JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFileExistsForNorbertData.json");
        try {
            PatientDatabase pd = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {

        try {
            PatientDatabase patientDatabase = new PatientDatabase();
            JsonWriter writer = new JsonWriter("./data/testJsonEmptyDatabase.json");
            writer.open();
            writer.write(patientDatabase);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonEmptyDatabase.json");
            PatientDatabase pd = reader.read();
            assertEquals(0, pd.numPatientsInDatabase());
            assertFalse(pd.removePatientFromDatabase(1234));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/testGeneralPatientDatabase.json");
        try {
            LocalDate nbd1 = LocalDate.of(2000, 6, 1);
            Demographics demographics1 = new Demographics(nbd1, "white", "male");
            PersonalHistory personalHx1 = new PersonalHistory(false);
            FamilyCancerHistory familyHx1 = new FamilyCancerHistory();
            familyHx1.addFamilyCase(1, "stomach");
            familyHx1.addFamilyCase(2, "breast");
            LocalDate nbd2 = LocalDate.of(1859, 11, 15);
            Demographics demographics2 = new Demographics(nbd2, "hispanic", "female");


            PatientDatabase pd = reader.read();
            assertEquals(2, pd.numPatientsInDatabase());
            Patient p1 = null;
            try {
                p1 = pd.getPatient(12345);
            } catch (PatientNotInDatabaseException e) {
                fail();
            }
            Patient p2 = null;

            try {
                p2 = pd.getPatient(54321);
            } catch (PatientNotInDatabaseException e) {
                fail();
            }

            checkPatient(12345, "Norbert", "Banyi", demographics1, personalHx1, familyHx1, p1);
            checkPatient(54321,"Rose", "Monroe", demographics2, personalHx1, familyHx1, p2 );
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }






}
