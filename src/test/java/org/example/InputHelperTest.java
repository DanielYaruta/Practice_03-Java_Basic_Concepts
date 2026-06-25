package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InputHelper")
class InputHelperTest {

    private PrintStream originalOut;

    @BeforeEach
    void suppressOutput() {
        originalOut = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    private static Scanner scannerOf(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    // ── readLine ──────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("readLine")
    class ReadLine {

        @Test
        @DisplayName("обычный ввод — возвращает строку")
        void normalInput_returnsString() {
            assertEquals("hello", InputHelper.readLine(scannerOf("hello\n"), ""));
        }

        @Test
        @DisplayName("пробелы по краям — обрезает")
        void inputWithPadding_returnsTrimmed() {
            assertEquals("hello", InputHelper.readLine(scannerOf("  hello  \n"), ""));
        }

        @Test
        @DisplayName("пустая строка — бросает исключение")
        void emptyInput_throwsException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> InputHelper.readLine(scannerOf("\n"), ""));
            assertTrue(ex.getMessage().contains("пустым"));
        }

        @Test
        @DisplayName("только пробелы — бросает исключение")
        void onlySpaces_throwsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> InputHelper.readLine(scannerOf("   \n"), ""));
        }
    }

    // ── readMenuChoice ────────────────────────────────────────────────────────

    @Nested
    @DisplayName("readMenuChoice")
    class ReadMenuChoice {

        @Test
        @DisplayName("число в диапазоне — возвращает int")
        void validChoice_returnsInt() {
            assertEquals(2, InputHelper.readMenuChoice(scannerOf("2\n"), 0, 3));
        }

        @Test
        @DisplayName("нижняя граница диапазона")
        void minBoundary_returnsMin() {
            assertEquals(0, InputHelper.readMenuChoice(scannerOf("0\n"), 0, 3));
        }

        @Test
        @DisplayName("верхняя граница диапазона")
        void maxBoundary_returnsMax() {
            assertEquals(3, InputHelper.readMenuChoice(scannerOf("3\n"), 0, 3));
        }

        @ParameterizedTest(name = "\"{0}\" — не число")
        @ValueSource(strings = {"abc", "1a", "", " "})
        @DisplayName("нечисловой ввод — бросает исключение")
        void nonNumeric_throwsException(String input) {
            assertThrows(IllegalArgumentException.class,
                    () -> InputHelper.readMenuChoice(scannerOf(input + "\n"), 0, 3));
        }

        @Test
        @DisplayName("выше максимума — бросает исключение")
        void aboveMax_throwsException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> InputHelper.readMenuChoice(scannerOf("5\n"), 0, 3));
            assertTrue(ex.getMessage().contains("0") && ex.getMessage().contains("3"));
        }

        @Test
        @DisplayName("ниже минимума — бросает исключение")
        void belowMin_throwsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> InputHelper.readMenuChoice(scannerOf("-1\n"), 0, 3));
        }
    }
}
