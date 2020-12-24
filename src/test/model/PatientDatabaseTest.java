package model;




import exceptions.PatientNotInDatabaseException;
import exceptions.RepeatPhNException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PatientDatabaseTest {
    Patient p1;
    PatientDatabase testDatabase;
    Patient p2;
    Patient p4;

    @BeforeEach
    public void setup() {
        LocalDate nbd1 = LocalDate.of(2000, 6, 1);
        Demographics demographics1 = new Demographics(nbd1, "white", "male");
        PersonalHistory personalHx1 = new PersonalHistory(false);
        FamilyCancerHistory familyHx1 = new FamilyCancerHistory();
        familyHx1.addFamilyCase(1, "stomach");
        familyHx1.addFamilyCase(2, "breast");

        p1 = new Patient(12345, "Norbert", "Banyi", demographics1, personalHx1, familyHx1);

        LocalDate nbd2 = LocalDate.of(1859, 11, 15);
        Demographics demographics2 = new Demographics(nbd2, "hispanic", "female");

        p2 = new Patient(54321, "Rose", "Monroe", demographics2, personalHx1, familyHx1);
        p4 = new Patient(12345, "n", "n", demographics1, personalHx1, familyHx1);
        testDatabase = new PatientDatabase();
    }

    @Test
    public void testAddPatientToEmptyDatabase() {
        try {
            assertTrue(testDatabase.addPatientToDatabase(p1));
            // Should add
        } catch (Exception e) {
            fail("Should be good to add");
        }

        assertEquals(1, testDatabase.numPatientsInDatabase());

        try {
            assertEquals(p1, testDatabase.getPatient(12345));
        } catch (PatientNotInDatabaseException e) {
            fail();
        }

        try {
            testDatabase.getPatient(46920);
            fail();
        } catch (PatientNotInDatabaseException e) {
            //pass
        }
    }

    @Test
    public void testAddPatientToDatabaseWithSamePHN() {
        try {
            testDatabase.addPatientToDatabase(p1);
        } catch (Exception e) {
            fail("Should add to database");
        }
        assertEquals(1, testDatabase.numPatientsInDatabase());

        try {
            testDatabase.addPatientToDatabase(p1);
            fail();
        } catch (RepeatPhNException e) {
            //pass
        }
        assertEquals(1, testDatabase.numPatientsInDatabase());

        //Completely different patient with same PHN (shouldn't add)
        p2.setPersonalHealthNumber(12345);

        try {
            testDatabase.addPatientToDatabase(p2);
            fail("Failed cuz same phn exists in database...");
        } catch (RepeatPhNException e) {
            //pass
        }

        try {
            testDatabase.addPatientToDatabase(p4);
            fail("Failed cuz same phn exists in database...");
        } catch (Exception e) {
            //pass;
        }

        assertEquals(1, testDatabase.numPatientsInDatabase());
        assertTrue(testDatabase.removePatientFromDatabase(12345));
        assertEquals(0, testDatabase.numPatientsInDatabase());

    }

    @Test
    public void testAddDistinctPatientsToDataBase() {
        assertTrue(testDatabase.addPatientToDatabase(p2));
        assertEquals(1, testDatabase.numPatientsInDatabase());
        assertTrue(testDatabase.addPatientToDatabase(p1));
        assertTrue(testDatabase.patientWithPHN(12345));
        assertTrue(testDatabase.patientWithPHN(54321));
        assertEquals(2, testDatabase.numPatientsInDatabase());

        //Exact same patient, with dif PHN (should add).
        Patient p3 = new Patient(512345, p1.getFirstName(), p1.getLastName(), p1.getDemographics(),
                p1.getPersonalHx() , p1.getFamilyCancerHx());
        assertTrue(testDatabase.addPatientToDatabase(p3));
        assertEquals(3, testDatabase.numPatientsInDatabase());
        try {
            assertEquals(p3, testDatabase.getPatient(512345));
        } catch (PatientNotInDatabaseException e) {
            fail();
        }
        assertTrue(testDatabase.removePatientFromDatabase(12345));
        assertEquals(2, testDatabase.numPatientsInDatabase());
    }

    @Test
    public void testPatientsInDatabaseNone() {
        assertEquals(0, testDatabase.numPatientsInDatabase());
        assertEquals("No patients in database.", testDatabase.patientsInDatabase());
        assertEquals(0, testDatabase.numPatientsInDatabase());
    }

    @Test
    public void testPatientsInDatabaseTwo() {
        testDatabase.addPatientToDatabase(p1);
        testDatabase.addPatientToDatabase(p2);
        assertTrue(testDatabase.patientWithPHN(12345));
        assertTrue(testDatabase.patientWithPHN(54321));
        assertEquals(2, testDatabase.numPatientsInDatabase());
        assertEquals("Norbert Banyi 12345; Rose Monroe 54321; ", testDatabase.patientsInDatabase());

    }
    @Test
    public void testPatientsInDatabaseTwoAndCreateNew() {
        testDatabase.addPatientToDatabase(p1);
        testDatabase.addPatientToDatabase(p2);

        //creating new patient to add to database - using setters and getters to make sure they're working properly
        Patient p3 = new Patient(555666, "Elgar", "Chang", p1.getDemographics(), p1.getPersonalHx(),
                p1.getFamilyCancerHx());
        p3.setFirstName("Ella");
        assertEquals("Ella", p3.getFirstName());
        p3.setLastName("Chan");
        assertEquals("Chan", p3.getLastName());
        p3.setMiddleName("Katelyn");
        assertEquals("Katelyn", p3.getMiddleName());
        p3.setDemographics(p2.getDemographics());
        assertEquals(p2.getDemographics(), p3.getDemographics());
        FamilyCancerHistory fmh = new FamilyCancerHistory();
        p3.setFamilyCancerHx(fmh);
        assertEquals(fmh, p3.getFamilyCancerHx());
        p3.setPersonalHx(p2.getPersonalHx());
        assertEquals(p2.getPersonalHx(), p3.getPersonalHx());

        //adding new patient to database:
        testDatabase.addPatientToDatabase(p3);

        //making sure they got added:
        assertEquals(3, testDatabase.numPatientsInDatabase());
        assertEquals("Norbert Banyi 12345; Rose Monroe 54321; Ella Chan 555666; ",
                testDatabase.patientsInDatabase());

    }

    @Test
    public void testRemovePatientFromDatabaseNone() {
        assertEquals(0, testDatabase.numPatientsInDatabase());
        assertFalse(testDatabase.removePatientFromDatabase(12345));
        assertEquals(0, testDatabase.numPatientsInDatabase());
    }

    @Test
    public void testRemovePatientFromDatabaseMultiple() {
        try {
            testDatabase.addPatientToDatabase(p1);
        } catch (Exception e) {
            fail();
        }
        assertFalse(testDatabase.removePatientFromDatabase(54321));

        try {
            testDatabase.addPatientToDatabase(p2);
        } catch (Exception e) {
            fail();
        }

        assertFalse(testDatabase.removePatientFromDatabase(9999));
        assertTrue(testDatabase.removePatientFromDatabase(12345));
        assertTrue(testDatabase.removePatientFromDatabase(54321));

    }

    @Test
    public void testGetPatientCorrectPHN() {
        testDatabase.addPatientToDatabase(p1);
        Patient testPatient;
        try {
            testPatient = testDatabase.getPatient(p1.getPersonalHealthNumber());
            // good - should not throw exception because patient is there.
            assertEquals(p1, testPatient);
        } catch (PatientNotInDatabaseException e) {
            fail("Patient with PHN is present.");
        }

    }

    @Test
    public void testGetPatientIncorrectPHN() {
        testDatabase.addPatientToDatabase(p1);
        Patient testPatient = p2;
        try {
            testPatient = testDatabase.getPatient(1235125);
            fail("Should not be finding this phn");
        } catch (PatientNotInDatabaseException e) {
            //good - this phn doesn't exist anyways.
        }
        assertEquals(p2, testPatient);
    }
}