package model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class defines a family case. This includes a both the degree
 *  * (e.g. parents, children, siblings = degree 1; cousins, uncles, aunts, grandparents = degree 2)
 *  * and the type of cancer that the relative had (one of "lung", "breast", "colorectal", "prostate", "skin",
 *  "ovarian", "stomach", "pancreatic", "leukemia", or "other")
 **/

public class FamilyCase {
    private int degreeRelative;
    private String cancerType;

    //REQUIRES: degreeRelative > 0, cancer type 'ct' is "lung", "breast", "colorectal", "prostate", "skin", "ovarian".
    //         "stomach", "pancreatic", "leukemia", or "other"
    //EFFECTS: makes a case of 'ct' type of cancer within a 'd' degree relative.
    public FamilyCase(int d, String ct) {
        degreeRelative = d;
        cancerType = ct;
    }

    //getters
    public String getCancerType() {
        return cancerType;
    }

    public int getDegreeRelative() {
        return degreeRelative;
    }


    //REQUIRES: degree 'd' > 0
    //MODIFIES: this
    //EFFECTS: sets the degree of the relative for a Family case
    public void setDegreeRelative(int d) {
        degreeRelative = d;
    }

    //REQUIRES: cancer type 'ct' is "lung", "breast", "colorectal", "prostate", "skin", "ovarian", "stomach",
    // "pancreatic", "leukemia", or "other"
    //MODIFIES: this
    //EFFECTS: sets the cancer type of a family case
    public void setCancerType(String ct) {
        cancerType = ct;
    }

    //EFFECTS: makes a family case into a jason object and returns it.
    public JSONObject caseJson() {
        JSONObject caseObject = new JSONObject();
        caseObject.put("degreeRelative", degreeRelative);
        caseObject.put("cancerType", cancerType);
        return caseObject;
    }


}
