package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SocialHistoryTest {
    SocialHistory testSocialHistory;

    @BeforeEach
    public void setup() {
    testSocialHistory = new SocialHistory("Shoe Maker", true,5, 1, true);
    }

    @Test
    public void testSettersAndGetters() {
        testSocialHistory.setChildren(false);
        assertFalse(testSocialHistory.hasChildren());
        testSocialHistory.setDrinksPerWeek(15);
        assertEquals(15, testSocialHistory.getDrinksPerWeek());
        testSocialHistory.setDrugs(false);
        assertFalse(testSocialHistory.usesDrugs());
        testSocialHistory.setProfession("Rapper");
        assertEquals("Rapper", testSocialHistory.getProfession());
        testSocialHistory.setPackYear(0.5);
        assertEquals(0.5, testSocialHistory.getPackYear());
    }
    // I WILL DO THIS WHEN I START IMPLEMENTING THE TOSTRING STUFF
    /*  @Test
    public void testPrintSocialHistory() {
        assertEquals("PACK-YEAR: 1.0, CHILDREN: true, PROFESSION: Shoe Maker, DRINKS/WEEK: 5, DRUGS: true",
              testSocialHistory.toString());
        testSocialHistory.setChildren(false);
        testSocialHistory.setDrinksPerWeek(15);
        testSocialHistory.setDrugs(false);
        testSocialHistory.setProfession("Rapper");
        testSocialHistory.setPackYear(0.5);
        assertEquals("PACK-YEAR: 0.5, CHILDREN: false, PROFESSION: Rapper, DRINKS/WEEK: 15, DRUGS: false",
                testSocialHistory.toString());
    }*/
}
