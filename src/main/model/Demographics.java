package model;

import java.time.LocalDate;


/**
 * This class encompasses demographic factors related to the patient
 * including their DOB, ethnicity ("white", "native American", "asian", "black", "hispanic", or "pacific islander")
 * and sex ("male", "female", or "other").
 **/


public class Demographics {
    private LocalDate dateOfBirth;
    private String ethnicity;
    private String sex;

    //REQUIRES: ethnicity is "white", "native American", "asian", "black", "hispanic", or "pacific islander" and
    //          sex is "male", "female", or "other"
    public Demographics(LocalDate d, String e, String s) {
        dateOfBirth = d;
        ethnicity = e;
        sex = s;
    }

    //setters
    public void setDateOfBirth(LocalDate date) {
        dateOfBirth = date;
    }

    //REQUIRES: sex is "male", "female", or "other"
    //MODIFIES: this
    //EFFECTS: updates patient sex to one of "male", "female", or "other"
    public void setSex(String sex) {
        this.sex = sex;
    }

    //REQUIRES: ethnicity is "white", "native american", "asian", "black", "hispanic", or "pacific islander"
    //MODIFIES: this
    //EFFECTS: updates patient ethnicity to be one of "white", "native american", "asian", "black", "hispanic",
    // "pacific islander", or "other".
    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    //getters:
    public String getEthnicity() {
        return ethnicity;
    }

    public String getSex() {
        return sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


}
