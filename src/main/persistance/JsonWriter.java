package persistance;

import model.PatientDatabase;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This entire class was inspired by the project at: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 * Followed the same method patterns. This class represents a  writer that writes JSON representation of patientDatabase
 * to a file specified.
 */

public class JsonWriter {

    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of patient database to file
    public void write(PatientDatabase pd) {
        JSONObject json = pd.toJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

