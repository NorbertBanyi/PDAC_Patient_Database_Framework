package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FamilyCaseTest {
    FamilyCase testFamilyCase;

    @BeforeEach
    public void setup() {
        testFamilyCase = new FamilyCase(2, "ovarian");
    }

    @Test
    public void testSettersAndGetter() {
        assertEquals(2, testFamilyCase.getDegreeRelative());
        assertEquals("ovarian", testFamilyCase.getCancerType());
        testFamilyCase.setCancerType("breast");
        testFamilyCase.setDegreeRelative(1);
        assertEquals(1, testFamilyCase.getDegreeRelative());
        assertEquals("breast", testFamilyCase.getCancerType());

    }
}
