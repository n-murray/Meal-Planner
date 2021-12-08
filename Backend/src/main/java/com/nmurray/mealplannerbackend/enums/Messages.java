package com.nmurray.mealplannerbackend.enums;

public enum Messages {
    INVALID_RECIPE("Invalid recipe, all fields must contain a valid string"),
    INVALID_NAME("Invalid name, a meal by that name already exists"),
    INVALID_TAKEAWAY("Invalid takeaway, all fields must contain a valid string"),
    INVALID_DATE("Invalid date, weekly plans must commence on a Monday"),
    INVALID_DATE_EXISTS("Invalid date, a weekly plan already exists for that date"),
    INVALID_MEAL("Invalid meal(s), weekly plans must contain valid names for existing meals."),
    INVALID_WEEKDAY("Invalid weekday, weekly plans must contain valid weekdays in capital letters.eg MONDAY, TUESDAY...");

    private final String label;

    private Messages(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
