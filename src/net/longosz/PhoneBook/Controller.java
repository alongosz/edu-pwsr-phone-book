package net.longosz.PhoneBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.longosz.PhoneBook.model.Person;

public class Controller {

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TableView<Person> phoneListView;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> phoneColumn;

    private final ObservableList<Person> phoneList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        phoneListView.setItems(phoneList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    @FXML
    void addEntry(ActionEvent event) {
        phoneList.add(new Person(name.getText(), phone.getText()));
    }
}
