package org.example;

import java.util.Scanner;

public class TextCleaner {

    static void run(Scanner scanner) {
        String input = InputHelper.readLine(scanner, "Введите текст: ");
        String result = input.replaceAll("[\\p{L}\\s]", "");
        System.out.println("Результат: " + result);
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
