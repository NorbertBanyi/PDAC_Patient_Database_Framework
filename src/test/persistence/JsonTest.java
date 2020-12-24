package persistence;

import model.Demographics;
import model.FamilyCancerHistory;
import model.Patient;
import model.PersonalHistory;

import static org.junit.jupiter.api.Assertions.*;

/**
 *This entire test class was inspired by the project at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *Followed the same methods and hierarchy
 */

public class JsonTest {
    protected void checkPatient(Integer phn, String first, String last, Demographics d, PersonalHistory p,
                                FamilyCancerHistory f, Patient patient) {
        assertEquals(first, patient.getFirstName());
        assertEquals(phn, patient.getPersonalHealthNumber());
        assertEquals(last, patient.getLastName());
        testDemographicsSame(d, patient.getDemographics());
        testPersonalHistorySame(p, patient.getPersonalHx());
        testFamilyCancerHistorySame(f, patient.getFamilyCancerHx());

    }

    private void testFamilyCancerHistorySame(FamilyCancerHistory f, FamilyCancerHistory familyCancerHx) {
        assertEquals(f.numRelativesWithCancer(), familyCancerHx.numRelativesWithCancer());

    }

    private void testDemographicsSame(Demographics d, Demographics patient) {
        assertEquals(d.getDateOfBirth().getDayOfMonth(), patient.getDateOfBirth().getDayOfMonth());
        assertEquals(d.getEthnicity(), patient.getEthnicity());
        assertEquals(d.getSex(), patient.getSex());
        assertEquals(d.getDateOfBirth().getYear(), d.getDateOfBirth().getYear());
        assertEquals(d.getDateOfBirth().getMonthValue(), patient.getDateOfBirth().getMonthValue());
    }

    private void testPersonalHistorySame(PersonalHistory p, PersonalHistory patient) {
        for (int num = 0; num < p.getPatientDiseases().size(); num++) {
            assertEquals(p.getPatientDiseases().get(num), patient.getPatientDiseases().get(num));
        }
        assertEquals(p.getDiabetesStatus(), patient.getDiabetesStatus());

    }

}
