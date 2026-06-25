package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameFormatter {

    private static final Pattern STARTS_WITH_LETTER = Pattern.compile("[a-zA-Z].*");
    private static final Pattern VALID_LENGTH       = Pattern.compile(".{3,20}");
    private static final Pattern VALID_CHARS        = Pattern.compile("[a-zA-Z0-9_]+");

    static void run(Scanner scanner) {
        String username = InputHelper.readLine(scanner, "Введите имя пользователя: ");

        Matcher startMatcher = STARTS_WITH_LETTER.matcher(username);
        if (!startMatcher.matches()) {
            System.out.println("Ошибка: имя пользователя должно начинаться с буквы");
            return;
        }

        Matcher lengthMatcher = VALID_LENGTH.matcher(username);
        if (!lengthMatcher.matches()) {
            System.out.println("Ошибка: имя пользователя должно содержать от 3 до 20 символов");
            return;
        }

        Matcher charMatcher = VALID_CHARS.matcher(username);
        if (!charMatcher.matches()) {
            System.out.println("Ошибка: имя пользователя может содержать только буквы, цифры и '_'");
            return;
        }

        String formatted = username.toLowerCase().replaceAll("_+", "_");
        System.out.println("Отформатированное имя: " + formatted);
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
