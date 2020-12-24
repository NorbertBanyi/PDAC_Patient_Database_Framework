package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encompasses the family cancer cases of a patient and stores both the degree
 * (e.g. parents, children, siblings = degree 1; cousins, uncles, aunts, grandparents = degree 2)
 * and the type of cancer that the relative had (one of "lung", "breast", "colorectal", "prostate", "skin", "ovarian".
 * "stomach", "pancreatic", "leukemia", or "other")
 * Many family cases can be added to FamilyCancerHistory.
 **/

public class FamilyCancerHistory {
    List<FamilyCase> familyCases;

    //EFFECTS: creates a new object in which to store Family Cases
    public FamilyCancerHistory() {
        familyCases = new ArrayList<>();
    }

    //REQUIRES: c is one of "lung", "breast", "colorectal", "prostate", "skin", "ovarian".
    //"stomach", "pancreatic", "leukemia", or "other"
    //EFFECTS: gets the number of familial cases with 'c' type of cancer.
    public int numRelativesWithType(String c) {
        int numCases = 0;
        for (FamilyCase each : familyCases) {
            if (each.getCancerType() == c) {
                numCases = numCases + 1;
            }
        }
        return numCases;
    }

    //REQUIRES: cancer type 'ct' is one of:
    //MODIFIES: this
    //EFFECTS: adds a familial cancer case to this that is 'n' degree, with cancer type 'ct'.
    public void addFamilyCase(int n, String ct) {
        FamilyCase fc = new FamilyCase(n, ct);
        familyCases.add(fc);
    }

    //REQUIRES: ct is one of "lung", "breast", "colorectal", "prostate", "skin", "ovarian".
    // "stomach", "pancreatic", "leukemia", or "other" and relative degree > 0.
    //MODIFIES: this
    //EFFECTS: removes a case that has degree n and cancer type ct and returns true. otherwise returns false.
    public boolean removeFamilyCase(String ct, int n) {
        int i = 0;
        for (FamilyCase each: familyCases) {
            if (ct == each.getCancerType() && n == each.getDegreeRelative()) {
                familyCases.remove(i);
                return true;
            }
            i = i + 1;
        }
        return false;
    }

    //EFFECTS: returns the number of relatives with cancer.
    public int numRelativesWithCancer() {
        return familyCases.size();
    }

    //REQUIRES: n > 0.
    //EFFECTS: gets the number of nth degree relatives with cancer
    public int numRelativesWithCancer(int n) {
        int numCases = 0;
        for (FamilyCase each : familyCases) {
            if (each.getDegreeRelative() == n) {
                numCases = numCases + 1;
            }

        }
        return numCases;
    }

    //EFFECTS: makes a JSON array of the family cases that are json objects and returns the JSON array
    public JSONArray toJson() {
        JSONArray famCases = new JSONArray();
        for (FamilyCase famCase : familyCases) {
            famCases.put(famCase.caseJson());
        }
        return famCases;
    }



}
