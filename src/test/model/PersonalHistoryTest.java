package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalHistoryTest {
    PersonalHistory testPersonalHistory;

    @BeforeEach
    public void setup(){
        testPersonalHistory = new PersonalHistory(true);
        testPersonalHistory.addOtherDisease("hypersexual disorder");
        testPersonalHistory.addOtherDisease("stroke");
    }
    @Test
    public void testSettersAndGetter() {
        assertTrue(testPersonalHistory.getDiabetesStatus());
        assertEquals(2, testPersonalHistory.numOtherDiseases());
        testPersonalHistory.noDiabetes();
        assertFalse(testPersonalHistory.getDiabetesStatus());
        testPersonalHistory.hasDiabetes();
        assertTrue(testPersonalHistory.getDiabetesStatus());
    }

    //@Test
    //public void testToStringMultipleDisease() {
    //    assertEquals("DIABETES: true, OTHER PERSONAL HX: hypersexual disorder, stroke, ",
    //            testPersonalHistory.toString());
    //}

    // I WILL DO THIS LATER WHEN IMPLEMENTING THE TOSTRING
    //@Test
    //public void testToStringNoDisease() {
    //    PersonalHistory testPersonalHistory2 = new PersonalHistory(true);
    //    assertEquals("DIABETES: true, OTHER PERSONAL HX: ",
    //            testPersonalHistory2.toString());
    //}


}
