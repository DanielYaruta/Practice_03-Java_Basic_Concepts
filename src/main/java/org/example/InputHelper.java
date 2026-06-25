package org.example;

import java.util.Scanner;

/**
 * Утилитарный класс для безопасного чтения и первичной валидации пользовательского ввода.
 * Все методы бросают IllegalArgumentException при некорректных данных —
 * вызывающий код решает, как отображать ошибку.
 */
public class InputHelper {

    private InputHelper() {}

    /**
     * Читает строку, отбрасывает пробелы по краям.
     * Бросает исключение, если строка пустая.
     */
    public static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Ввод не может быть пустым.");
        }
        return input;
    }

    /**
     * Читает выбор пункта меню и проверяет, что он находится в диапазоне [min, max].
     * Бросает исключение при нечисловом вводе или выходе за диапазон.
     */
    public static int readMenuChoice(Scanner scanner, int min, int max) {
        System.out.print("Ваш выбор: ");
        String raw = scanner.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "\"" + raw + "\" — не число. Введите значение от " + min + " до " + max + ".");
        }
        if (choice < min || choice > max) {
            throw new IllegalArgumentException(
                    "Значение " + choice + " вне диапазона. Допустимо: от " + min + " до " + max + ".");
        }
        return choice;
    }
}
