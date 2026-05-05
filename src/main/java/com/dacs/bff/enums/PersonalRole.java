package com.dacs.bff.enums;

/**
 * Valid roles for Personal (medical staff).
 * Only ADMIN and PERSONAL_MEDICO are allowed.
 */
public enum PersonalRole {
    ADMIN("admin"),
    PERSONAL_MEDICO("personal_medico");

    private final String value;

    PersonalRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Validates if a given role string is valid
     * @param role the role to validate
     * @return true if the role is valid, false otherwise
     */
    public static boolean isValid(String role) {
        if (role == null) {
            return false;
        }
        for (PersonalRole validRole : PersonalRole.values()) {
            if (validRole.value.equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the enum from string value
     * @param value the string value
     * @return PersonalRole enum or null if not found
     */
    public static PersonalRole fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (PersonalRole role : PersonalRole.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        return null;
    }
}
