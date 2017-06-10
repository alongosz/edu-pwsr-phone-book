package net.longosz.PhoneBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import net.longosz.PhoneBook.model.Person;
import net.longosz.PhoneBook.ui.validation.UIValidationException;

public class Controller {

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Person> phoneListView;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> phoneColumn;

    private final ObservableList<Person> phoneList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // disable update and delete until a PhoneBook entry gets selected
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        phoneListView.setItems(phoneList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // update input controls if a PhoneBook entry got selected
        phoneListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                name.setText(newValue.getName());
                phone.setText(newValue.getPhone());

                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                // clear input data if row gets unselected
                resetInputControls();
            }
        });
    }

    @FXML
    void addEntry(ActionEvent event) {
        try {
            phoneList.add(new Person(name.getText(), phone.getText()));
        } catch (UIValidationException e) {
            showErrorDialog(e.getLocalizedMessage(), event);
        }
    }

    private void resetInputControls() {
        name.setText("");
        phone.setText("");
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void updateEntry(ActionEvent event) {
        Person person = phoneListView.getSelectionModel().getSelectedItem();

        if (person != null) {
            try {
                person.setName(name.getText());
                person.setPhone(phone.getText());
                phoneListView.refresh();
            } catch (UIValidationException e) {
                showErrorDialog(e.getLocalizedMessage(), event);
            }
        } else {
            showErrorDialog("Select an entry before trying to update it", event);
        }
    }

    @FXML
    void deleteEntry(ActionEvent event) {
        Person person = phoneListView.getSelectionModel().getSelectedItem();

        if (person != null) {
            phoneList.remove(person);
            phoneListView.refresh();
        } else {
            showErrorDialog("Select an entry before trying to delete it", event);
        }
    }

    private void showErrorDialog(String message, ActionEvent errorSourceEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(((Node) errorSourceEvent.getTarget()).getScene().getWindow());
        alert.setTitle("Error");
        alert.setHeaderText(message);

        alert.showAndWait();
    }
}
