package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            int choice;
            try {
                choice = InputHelper.readMenuChoice(scanner, 0, 3);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            System.out.println();

            switch (choice) {
                case 1 -> runSafely(() -> PhoneValidator.run(scanner));
                case 2 -> runSafely(() -> TextCleaner.run(scanner));
                case 3 -> runSafely(() -> UsernameFormatter.run(scanner));
                case 0 -> {
                    System.out.println("До свидания!");
                    scanner.close();
                    return;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=============================");
        System.out.println("       Выберите задачу       ");
        System.out.println("=============================");
        System.out.println("  1. Проверка номера телефона");
        System.out.println("  2. Очистка текста от букв и пробелов");
        System.out.println("  3. Форматирование имени пользователя");
        System.out.println("  0. Выход");
        System.out.println("=============================");
    }

    // Перехватывает ошибки ввода из run()-методов и печатает их,
    // не давая исключению «убить» главный цикл меню.
    private static void runSafely(Runnable task) {
        try {
            task.run();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }
}
