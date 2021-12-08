package com.nmurray.mealplannerbackend.enums;

public enum WeekDays {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String label;

    private WeekDays(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
