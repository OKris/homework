package com.example.homework_Kristina.util;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validations {

    private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    private static final Pattern namePattern = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ' -]{2,50}$");

    public static String phoneNumberConversion(String rawNumber, String region) {
        try {
            Phonenumber.PhoneNumber number = util.parse(rawNumber, region);
            if (!util.isValidNumber(number)) {
                throw new IllegalArgumentException("Invalid phone number");
            }

            return util.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (Exception e) {
            throw new IllegalArgumentException("Phone parsing failed, " + e.getMessage());
        }
    }

    public static boolean isValidName(String name) {
        return namePattern.matcher(name.trim()).matches();
    }
}
