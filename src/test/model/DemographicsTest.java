package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


public class DemographicsTest {
    Demographics testDemographic;

    @BeforeEach
    public void setup() {
        LocalDate localDate = LocalDate.of(2001, 8, 9);
        testDemographic = new Demographics(localDate, "white", "female");
    }

    @Test
    public void testSettersAndGetters() {
        LocalDate localDate2 = LocalDate.of(2000, 9, 20);
        assertEquals("white", testDemographic.getEthnicity());
        assertEquals("female", testDemographic.getSex());
        testDemographic.setDateOfBirth(localDate2);
        assertEquals(localDate2, testDemographic.getDateOfBirth());
        testDemographic.setEthnicity("black");
        assertEquals("black", testDemographic.getEthnicity());
        testDemographic.setSex("other");
        assertEquals("other", testDemographic.getSex());
    }

}
