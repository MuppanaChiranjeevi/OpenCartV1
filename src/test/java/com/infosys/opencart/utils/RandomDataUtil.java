package com.infosys.opencart.utils;

import com.github.javafaker.Faker;

public class RandomDataUtil {

    private static final Faker faker = new Faker();

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhoneNumber() {
        // Returns 10-digit number only
        return faker.phoneNumber().subscriberNumber(10).replaceAll("[^0-9]", "");
    }

    public static String getPassword() {
        return faker.internet().password(8, 12, true, true, true);
    }
}
