package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// ----------------- PURPOSE: Reading CSV files to POJO arrays -----------------

public abstract class CSVreader {

    protected List<String> lines;
    protected Path path;

    // defining the delimiters for all CSVs
    protected final static String VALUE_DELIMITER = ";";
    protected final static String TIME_DELIMITER = ":";

    public void setLines(Path path) {
        try {
            lines = Files.lines(path)
                                .skip(1) // first line in csv's are headers/labels
                                .toList();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    public abstract Object[] readCSV();
}
