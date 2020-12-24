package ui;

//elements are similar to the project found at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.PatientDatabase;
import persistance.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        new DatabaseAppOpeningScreen(new Point(0, 0), new PatientDatabase(), false);

        try {
            new DatabaseApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}
