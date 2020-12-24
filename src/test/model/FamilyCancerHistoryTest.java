package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class FamilyCancerHistoryTest {

    FamilyCancerHistory testCancerHistory;

    @BeforeEach
    public void setup() {
        testCancerHistory = new FamilyCancerHistory();
    }

    @Test
    public void testNumRelativesWithTypeNone() {
        assertEquals(0, testCancerHistory.numRelativesWithType("ovarian"));
        assertEquals(0, testCancerHistory.numRelativesWithType("breast"));
        assertEquals(0, testCancerHistory.numRelativesWithType("lung"));
        assertEquals(0, testCancerHistory.numRelativesWithType("pancreatic"));
        assertEquals(0, testCancerHistory.numRelativesWithType("colorectal"));
    }

    @Test
    public void testNumRelativesWithTypeMany() {
        testCancerHistory.addFamilyCase(1, "breast");
        assertEquals(1, testCancerHistory.numRelativesWithType("breast"));
        testCancerHistory.addFamilyCase(2, "breast");
        assertEquals(0, testCancerHistory.numRelativesWithType("lung"));
        assertEquals(0, testCancerHistory.numRelativesWithType("pancreatic"));
        assertEquals(0, testCancerHistory.numRelativesWithType("colorectal"));
        assertEquals(2, testCancerHistory.numRelativesWithType("breast"));
        testCancerHistory.addFamilyCase(6, "breast");
        assertEquals(3, testCancerHistory.numRelativesWithType("breast"));
        testCancerHistory.addFamilyCase(1, "lung");
        assertTrue(testCancerHistory.removeFamilyCase("lung", 1));
        assertEquals(0, testCancerHistory.numRelativesWithType("lung"));

    }

    @Test
    public void testRemoveFamilyCaseNone() {
        assertFalse(testCancerHistory.removeFamilyCase("lung", 1));
        assertFalse(testCancerHistory.removeFamilyCase("breast", 1));
        assertFalse(testCancerHistory.removeFamilyCase("lung", 2));
        assertFalse(testCancerHistory.removeFamilyCase("colorectal", 3));

    }

    @Test
    public void testRemoveFamilyCaseSome() {
        testCancerHistory.addFamilyCase(2, "lung");
        testCancerHistory.addFamilyCase(2, "lung");
        assertFalse(testCancerHistory.removeFamilyCase("lung", 1));
        assertFalse(testCancerHistory.removeFamilyCase("breast", 2));
        assertTrue(testCancerHistory.removeFamilyCase("lung", 2));
        assertEquals(1, testCancerHistory.numRelativesWithCancer());
        assertFalse(testCancerHistory.removeFamilyCase("colorectal", 3));
        testCancerHistory.addFamilyCase(1, "breast");
        testCancerHistory.addFamilyCase(3, "colorectal");
        assertTrue(testCancerHistory.removeFamilyCase("breast", 1));
        assertEquals(2, testCancerHistory.numRelativesWithCancer());
    }

    @Test
    public void testNumRelativesWithCancerNone() {
        assertEquals(0, testCancerHistory.numRelativesWithCancer());
        assertEquals(0, testCancerHistory.numRelativesWithCancer(1));
        assertEquals(0, testCancerHistory.numRelativesWithCancer(2));
        assertEquals(0, testCancerHistory.numRelativesWithCancer(3));
    }

    @Test
    public void testNumRelativesWithCancerMany() {
        testCancerHistory.addFamilyCase(1, "breast");
        testCancerHistory.addFamilyCase(1, "colorectal");
        testCancerHistory.addFamilyCase(1, "breast");
        testCancerHistory.addFamilyCase(2, "lung");
        testCancerHistory.addFamilyCase(2, "colorectal");
        assertEquals(5, testCancerHistory.numRelativesWithCancer());
        assertEquals(2, testCancerHistory.numRelativesWithCancer(2));
        assertEquals(3, testCancerHistory.numRelativesWithCancer(1));
        testCancerHistory.removeFamilyCase("breast", 1);
        assertEquals(2, testCancerHistory.numRelativesWithCancer(1));
    }

}
