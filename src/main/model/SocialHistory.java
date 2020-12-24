package model;
/**
 * This class encompasses social history of the patient and must include amount of cigarettes, profession,
 * drinksPerWeek, drug-use, and children because these social factors influence the willingness to undergo
 * genetic testing and receive other life-saving treatments!
 *
 * This class will be used in later iterations of this application
 **/


public class SocialHistory {
    private double packYear;
    private String profession;
    private int drinksPerWeek;
    private boolean drugs;
    private boolean children;


    //REQUIRES: pack-year and drinks per week >= 0
    //EFFECTS: constructs the social history of a patient.
    public SocialHistory(String job, boolean children, int drinks, int packYear, boolean drugs) {
        profession = job;
        this.children = children;
        this.drugs = drugs;
        drinksPerWeek = drinks;
        this.packYear = packYear;

    }


    //setters:

    public void setDrugs(boolean b) {
        drugs = b;
    }

    public void setChildren(boolean b) {
        children = b;
    }

    //REQUIRES: String is not empty
    //MODIFIES: this
    //EFFECTS: sets the profession of the patient.
    public void setProfession(String profession) {
        this.profession = profession;
    }

    //REQUIRES: packyear >= 0
    //MODIFIES: this
    //EFFECTS: updates the pack-year of the patient's social history
    public void setPackYear(double packyear) {
        packYear = packyear;
    }

    //REQUIRES: drinks per week >= 0
    //MODIFIES: this
    //EFFECTS: updates the drinks/week of the patient's social history
    public void setDrinksPerWeek(int dpw) {
        drinksPerWeek = dpw;
    }

    // getters:

    public double getPackYear() {
        return packYear;
    }

    public int getDrinksPerWeek() {
        return drinksPerWeek;
    }

    public boolean usesDrugs() {
        return drugs;
    }

    public boolean hasChildren() {
        return children;
    }

    public String getProfession() {
        return profession;
    }


}
