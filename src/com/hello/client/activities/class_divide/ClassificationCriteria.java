package com.hello.client.activities.class_divide;

public enum ClassificationCriteria {
    BY_CLASS("Theo số lượng"),
    BY_MIN_MAX("Theo MAX/MIN sinh viên");

    private final String description;

    ClassificationCriteria(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
