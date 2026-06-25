package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UsernameFormatter")
class UsernameFormatterTest {

    @Nested
    @DisplayName("валидные имена — возвращает отформатированное")
    class Valid {

        @ParameterizedTest(name = "\"{0}\" → \"{1}\"")
        @CsvSource({
                "User__NAME123,user_name123",   // пример из задания
                "abc,abc",                      // минимальная длина (3)
                "Hello123,hello123",            // буквы и цифры
                "a_b___c,a_b_c",               // схлопывание подчёркиваний
                "ABC,abc",                      // только uppercase
                "a1b2c3,a1b2c3",               // чередование букв и цифр
                "A_B_C,a_b_c",                 // одиночные подчёркивания без изменений
        })
        void process_returnsFormatted(String input, String expected) {
            assertEquals(expected, UsernameFormatter.process(input));
        }

        @Test
        @DisplayName("максимальная длина (20 символов)")
        void process_maxLength_returnsFormatted() {
            String input = "A" + "b".repeat(19); // ровно 20 символов
            assertEquals(input.toLowerCase(), UsernameFormatter.process(input));
        }
    }

    @Nested
    @DisplayName("невалидные имена — бросает IllegalArgumentException")
    class Invalid {

        @ParameterizedTest(name = "{0} — начинается не с буквы")
        @ValueSource(strings = {"123user", "1abc", "_user", "9name"})
        @DisplayName("начинается не с буквы")
        void process_notStartingWithLetter_throws(String input) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> UsernameFormatter.process(input));
            assertTrue(ex.getMessage().contains("начинаться с буквы"));
        }

        @ParameterizedTest(name = "длина {0} символов")
        @ValueSource(strings = {"a", "ab"})
        @DisplayName("слишком короткое (меньше 3)")
        void process_tooShort_throws(String input) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> UsernameFormatter.process(input));
            assertTrue(ex.getMessage().contains("от 3 до 20"));
        }

        @Test
        @DisplayName("слишком длинное (больше 20)")
        void process_tooLong_throws() {
            String input = "a".repeat(21);
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> UsernameFormatter.process(input));
            assertTrue(ex.getMessage().contains("от 3 до 20"));
        }

        @ParameterizedTest(name = "\"{0}\" содержит запрещённый символ")
        @ValueSource(strings = {"user-name", "user name", "user@123", "name!"})
        @DisplayName("содержит запрещённые символы")
        void process_invalidChars_throws(String input) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class, () -> UsernameFormatter.process(input));
            assertTrue(ex.getMessage().contains("только буквы"));
        }
    }
}
