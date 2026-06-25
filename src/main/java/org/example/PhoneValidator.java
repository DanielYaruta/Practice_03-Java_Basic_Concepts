package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PhoneValidator {

    private static final String PHONE_PATTERN =
            "\\+\\d{1,3}[ -]?\\d{1,3}[ -]?(\\d[ -]?){4,7}\\d";

    static boolean validate(String phone) {
        return Pattern.matches(PHONE_PATTERN, phone);
    }

    static void run(Scanner scanner) {
        String phone = InputHelper.readLine(scanner, "Введите номер телефона: ");
        if (validate(phone)) {
            System.out.println("Результат: \"" + phone + "\" — валидный номер.");
        } else {
            System.out.println("Результат: \"" + phone + "\" — невалидный номер.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            run(scanner);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
