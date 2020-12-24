package persistence;

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


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterEmptyWorkRoom() {
        try {
            PatientDatabase patientDatabase = new PatientDatabase();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDatabase.json");
            writer.open();
            writer.write(patientDatabase);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDatabase.json");
            patientDatabase = reader.read();
            assertEquals(0, patientDatabase.numPatientsInDatabase());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRoom() {
        try {
            PatientDatabase patientDatabase = new PatientDatabase();

            LocalDate nbd1 = LocalDate.of(2000, 6, 1);
            Demographics demographics1 = new Demographics(nbd1, "white", "male");
            PersonalHistory personalHx1 = new PersonalHistory(false);
            personalHx1.addOtherDisease("Arthritis");
            personalHx1.addOtherDisease("Atherosclerosis");
            FamilyCancerHistory familyHx1 = new FamilyCancerHistory();
            familyHx1.addFamilyCase(1, "stomach");
            familyHx1.addFamilyCase(2, "breast");

            Patient p1 = new Patient(12345, "Norbert", "Banyi", demographics1, personalHx1, familyHx1);

            LocalDate nbd2 = LocalDate.of(1859, 11, 15);
            Demographics demographics2 = new Demographics(nbd2, "hispanic", "female");

            Patient p2 = new Patient(54321, "Rose", "Monroe", demographics2, personalHx1, familyHx1);

            patientDatabase.addPatientToDatabase(p1);
            patientDatabase.addPatientToDatabase(p2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPatientDatabase.json");
            writer.open();
            writer.write(patientDatabase);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPatientDatabase.json");
            patientDatabase = reader.read();
            assertEquals(2, patientDatabase.numPatientsInDatabase());
            checkPatient(12345, "Norbert", "Banyi", demographics1, personalHx1, familyHx1, p1);
            checkPatient(54321, "Rose", "Monroe", demographics2, personalHx1, familyHx1, p2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }
}
