package net.longosz.PhoneBook.io;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.longosz.PhoneBook.model.Person;
import net.longosz.PhoneBook.ui.validation.UIValidationException;

import java.io.*;
import java.util.regex.Pattern;

/**
 * PhoneBook IO Service responsible for writing and reading
 * application runtime phone list changes to and from a file.
 */
public class IOService {
    /**
     * @param fileName  file path, absolute or relative to CWD
     * @param separator field separator sequence (could be more than one character)
     */
    public IOService(String fileName, String separator) {
        this.fileName = fileName;
        this.separator = separator;
    }

    /**
     * Write application data to a file.
     *
     * @param phoneList application data structure containing all entries
     * @throws UIValidationException throws wrapped IO Exceptions
     */
    public void write(ObservableList<Person> phoneList) throws UIValidationException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            for (Person person : phoneList) {
                writer.write(person.getName());
                writer.write(separator);
                writer.write(person.getPhone());
                writer.write('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new UIValidationException("Failed to find data file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UIValidationException("Unable to write to file: " + fileName);
        }
    }

    /**
     * Read application data from a file.
     *
     * @return Observable List w/ phone numbers
     */
    public ObservableList<Person> read() throws UIValidationException {
        ObservableList<Person> phoneList = FXCollections.observableArrayList();
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(fileName), "UTF-8")
                )
        ) {
            String line;
            String quotedSeparator = Pattern.quote(separator);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(quotedSeparator);
                if (parts.length == 2) {
                    phoneList.add(new Person(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            // if file does not exist, return empty data set
            return phoneList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UIValidationException("Unable to write to file: " + fileName);
        }
        return phoneList;
    }

    private String fileName;
    private String separator;
}
