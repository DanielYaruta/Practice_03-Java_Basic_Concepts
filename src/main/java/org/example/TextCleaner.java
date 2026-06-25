package org.example;

import java.util.Scanner;

public class TextCleaner {

    static String clean(String text) {
        return text.replaceAll("[\\p{L}\\s]", "");
    }

    static void run(Scanner scanner) {
        String input = InputHelper.readLine(scanner, "Введите текст: ");
        System.out.println("Результат: " + clean(input));
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
