package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UsernameFormatter {

    private static final Pattern STARTS_WITH_LETTER = Pattern.compile("[a-zA-Z].*");
    private static final Pattern VALID_LENGTH       = Pattern.compile(".{3,20}");
    private static final Pattern VALID_CHARS        = Pattern.compile("[a-zA-Z0-9_]+");

    // Validates and formats username. Throws IllegalArgumentException with a
    // human-readable message on the first violated rule.
    static String process(String username) {
        if (!STARTS_WITH_LETTER.matcher(username).matches()) {
            throw new IllegalArgumentException("имя пользователя должно начинаться с буквы");
        }
        if (!VALID_LENGTH.matcher(username).matches()) {
            throw new IllegalArgumentException("имя пользователя должно содержать от 3 до 20 символов");
        }
        if (!VALID_CHARS.matcher(username).matches()) {
            throw new IllegalArgumentException("имя пользователя может содержать только буквы, цифры и '_'");
        }
        return username.toLowerCase().replaceAll("_+", "_");
    }

    static void run(Scanner scanner) {
        String username = InputHelper.readLine(scanner, "Введите имя пользователя: ");
        try {
            System.out.println("Отформатированное имя: " + process(username));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
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
