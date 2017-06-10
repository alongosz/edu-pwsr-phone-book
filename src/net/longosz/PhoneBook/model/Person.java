package net.longosz.PhoneBook.model;

import net.longosz.PhoneBook.ui.validation.UIValidationException;

import java.util.regex.Pattern;

/**
 * Model class representing Person (PhoneBook entry).
 */
public class Person {
    private String name;
    private String phone;
    final private static Pattern phoneNumberPattern = Pattern.compile("\\+?[0-9# *-]+");

    public Person(String name, String phone) throws UIValidationException {
        // use setters for the purpose of no redundancy in validation
        setName(name);
        setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws UIValidationException {
        if (name.isEmpty()) {
            throw new UIValidationException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws UIValidationException {
        if (phone.isEmpty()) {
            throw new UIValidationException("Phone number cannot be empty");
        }
        if (!phoneNumberPattern.matcher(phone).matches()) {
            throw new UIValidationException("Phone number can start with plus sign and has to contain digits and can " +
                    "contain: +, -, #, *, spaces");
        }
        this.phone = phone;
    }
}
