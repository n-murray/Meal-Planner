package com.nmurray.mealplannerbackend.enums;

public enum Messages {
    INVALID_RECIPE("Invalid recipe, all fields must contain as valid string");

    private final String label;

    private Messages(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
