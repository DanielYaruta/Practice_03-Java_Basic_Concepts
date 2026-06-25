package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TextCleaner")
class TextCleanerTest {

    @ParameterizedTest(name = "\"{0}\" → \"{1}\"")
    @CsvSource({
            "Hello World,",                             // только буквы и пробел → пусто
            "abc 123,123",                              // буквы и пробел убраны, цифры остались
            "Привет 42!,42!",                           // кириллица и пробел убраны
            "123.456-789,123.456-789",                  // нет букв/пробелов — без изменений
            "test-2025!,-2025!",                        // буквы убраны, дефис/цифры/! остались
            "Hello!,!",                                 // буквы убраны, ! остался
    })
    @DisplayName("убирает буквы и пробелы, оставляет остальное")
    void clean_removesLettersAndSpaces(String input, String expected) {
        // @CsvSource заменяет null для пустого результата
        assertEquals(expected == null ? "" : expected, TextCleaner.clean(input));
    }

    @Test
    @DisplayName("пустая строка → пустая строка")
    void clean_emptyString_returnsEmpty() {
        assertEquals("", TextCleaner.clean(""));
    }

    @Test
    @DisplayName("только пробелы → пустая строка")
    void clean_onlySpaces_returnsEmpty() {
        assertEquals("", TextCleaner.clean("   "));
    }

    @Test
    @DisplayName("пример из задания: телефон и индекс")
    void clean_fullPhoneExample() {
        String input    = "Мой номер телефона: +7 (999) 123-45-67, а почтовый индекс: 123456.";
        String expected = ":+7(999)123-45-67,:123456.";
        assertEquals(expected, TextCleaner.clean(input));
    }

    @Test
    @DisplayName("знаки пунктуации не затрагиваются")
    void clean_keepsPunctuation() {
        assertEquals("!@#$%^&*().,;:", TextCleaner.clean("!@#$%^&*().,;:"));
    }
}
