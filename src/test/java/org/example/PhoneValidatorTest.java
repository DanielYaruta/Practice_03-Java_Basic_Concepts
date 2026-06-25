package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PhoneValidator")
class PhoneValidatorTest {

    @Nested
    @DisplayName("валидные номера → true")
    class Valid {

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = {
                "+7 999 123-45-67",       // пробелы + дефисы
                "+1-555-123-4567",        // дефисы везде
                "+441234567890",          // без разделителей
                "+1 800 12345",           // основной: 5 цифр (минимум)
                "+1 800 12345678",        // основной: 8 цифр (максимум)
                "+380 50 1234567",        // код страны 3 цифры
                "+49-30-12345678",        // Германия
                "+7-999-1234567",         // дефисы, 7 цифр
                "+1 800 123-4567",        // смешанные разделители
        })
        void returnsTrue(String phone) {
            assertTrue(PhoneValidator.validate(phone));
        }
    }

    @Nested
    @DisplayName("невалидные номера → false")
    class Invalid {

        @ParameterizedTest(name = "{0}")
        @ValueSource(strings = {
                "7 999 123-45-67",        // нет +
                "+7 999 123456789",       // 9 цифр в основном (слишком много)
                "+7 999 1234567890",      // 10 цифр в основном
                "+7 (999) 123-45-67",     // скобки не допускаются
                "+7.999.1234567",         // точки не допускаются
                "++7 999 1234567",        // двойной плюс
                "+",                      // только плюс
                "not-a-phone",            // буквы
                "+7 999",                 // нет основного номера
                "+1 2 345",              // основной: только 3 цифры
        })
        void returnsFalse(String phone) {
            assertFalse(PhoneValidator.validate(phone));
        }

        @Test
        @DisplayName("пустая строка → false")
        void emptyString_returnsFalse() {
            assertFalse(PhoneValidator.validate(""));
        }
    }
}
