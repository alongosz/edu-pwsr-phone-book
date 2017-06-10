package net.longosz.PhoneBook.ui.validation;

/**
 * UI Validation exception containing user-friendly message to be displayed.
 */
public class UIValidationException extends Exception {
    public UIValidationException(String message) {
        super(message);
    }
}
