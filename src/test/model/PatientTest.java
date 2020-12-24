package model;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class PatientTest {
    Patient p1;

    @BeforeEach
    public void setup() {
        LocalDate nbd1 = LocalDate.of(2020, 7, 11);
        Demographics demographics1 = new Demographics(nbd1, "black", "female");
        PersonalHistory personalHx1 = new PersonalHistory(false);
        FamilyCancerHistory familyHx1 = new FamilyCancerHistory();
        FamilyCase fc1 = new FamilyCase(1, "colorectal");
        FamilyCase fc2 = new FamilyCase(2, "pancreatic");

        p1 = new Patient(3303, "Ella", "Chan", demographics1, personalHx1, familyHx1);
    }

    @Test
    public void testGetNumRelativesWithCancerNone() {
        assertEquals(0, p1.getNumRelativesWithCancer(1));
        assertEquals(0, p1.getNumRelativesWithCancer(2));
    }

    @Test
    public void testGetNumRelativesWithCancerSome() {
        p1.addCaseOfFamilialCancer(1, "colorectal");
        p1.addCaseOfFamilialCancer(2, "pancreatic");
        assertEquals(1, p1.getNumRelativesWithCancer(1));
        assertEquals(1, p1.getNumRelativesWithCancer(2));
        p1.addCaseOfFamilialCancer(2, "breast");
        assertEquals(2, p1.getNumRelativesWithCancer(2));
    }
}

