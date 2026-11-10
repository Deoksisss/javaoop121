package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class IntegerDecodeTests {

    @Test
    @DisplayName("Empty string test")
    void ShouldThrowNumberFormatExceptionWhenStringIsEmpty() {
        //Arrange
        String emptyString = "";

        //Act
        NumberFormatException exception = Assertions.assertThrows(NumberFormatException.class,
                () -> Integer.decode(emptyString),
                "Для пустой строки должно выбрасываться исключение NumberFormatException");

        // Assert
        Assertions.assertEquals("Zero length string", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("unsigned decimal number test")
    //Arrange
    @CsvSource({
            "42,42",
            "55,55",
            "100,100"
    })

    void ShouldDecodeUnsignedDecimalNumber(String input, int expected) {

        //Act
        Integer number = Integer.decode(input);

        //Assert
        Assertions.assertEquals(expected, number);
    }

    @ParameterizedTest
    @DisplayName("positive decimal number test")
    //Arrange
    @CsvSource({
            "+432,432",
            "+575,575",
            "+108780,108780"
    })

    void shouldDecodeExplicitlyPositiveNumberWithPlusSign(String input, int expected) {

        //Act
        Integer number = Integer.decode(input);

        //Assert
        Assertions.assertEquals(expected, number);
    }

    @ParameterizedTest
    @DisplayName("negative decimal number test")
    //Arrange
    @CsvSource({
            "-1,-1",
            "-7575,-7575",
            "-435,-435"
    })

    void ShouldDecodeNegativeDecimalNumber(String input, int expected) {

        //Act
        Integer number = Integer.decode(input);

        //Assert
        Assertions.assertEquals(expected, number);
    }


    @ParameterizedTest
    @DisplayName("Hexadecimal with 0x or 0X prefix number test")
    //Arrange
    @CsvSource({
            "0x42,66",
            "0xA,10",
            "0XF5, 245",
            "-0XF5, -245",
            "+0x42, 66"
    })

    void shouldDecodeHexadecimalNumbersWithZeroXPrefix(String input, int expected) {

        //Act
        Integer number = Integer.decode(input);

        //Assert
        Assertions.assertEquals(expected, number);
    }

    @ParameterizedTest
    @DisplayName("Hexadecimal with # prefix number test")
    //Arrange
    @CsvSource({
            "#42,66",
            "#ABC,2748",
            "#FE, 254",
            "-#DEF, -3567",
            "+#698, 1688"
    })

    void shouldDecodeHexadecimalNumbersWithHashPrefix(String input, int expected) {

        //Act
        Integer number = Integer.decode(input);

        //Assert
        Assertions.assertEquals(expected, number);
    }

    @ParameterizedTest
    @DisplayName("Octal number test")
    // Arrange
    @CsvSource({
            "01456,814",
            "0233, 155",
            "-0222, -146",
            "+0100, 64"
    })

    void shouldDecodeOctalPrefix(String input, int expected) {

        //Act
        Integer number = Integer.decode(input);

        //Assert
        Assertions.assertEquals(expected, number);
    }

    @ParameterizedTest
    @DisplayName("Wrong sign character position test")
    // Arrange
    @ValueSource(strings = {"0x-10", "0x+10", "#-5", "#+5", "0-7", "0+7", "--10", "-+10", "+-10", "++10"})

    void shouldThrowExceptionWhenSignIsInWrongPosition(String invalidSignPositionInput) {
        // Act
        NumberFormatException exception = Assertions.assertThrows(
                NumberFormatException.class,
                () -> Integer.decode(invalidSignPositionInput)
        );
        // Assert
        Assertions.assertEquals("Sign character in wrong position", exception.getMessage());
    }


    @ParameterizedTest
    @ValueSource(// Arrange
            strings = {"-2147483648", "-0x80000000", "-#80000000", "-020000000000"})
    @DisplayName("Integer.MIN_VALUE correct decode test")
    void shouldDecodeIntegerMinValueCorrectly(String minValInput) {
        // Act
        Integer actual = Integer.decode(minValInput);

        // Assert
        Assertions.assertEquals(Integer.valueOf(Integer.MIN_VALUE), actual);
    }

    @ParameterizedTest
    @DisplayName("Uncorrected strings or out of range values throws NumberFormatException test")
    @ValueSource(strings = {
            // Arrange
            "2147483648",
            "-2147483649",
            "not_a_number",
            "-not_a_number",
            "08",
            "0xG1",
            "0x",
            "-"
    })
    void shouldRethrowNumberFormatExceptionFromCatchBlockOnInvalidInput(String invalidInput) {
        // Act & Assert
        Assertions.assertThrows(
                NumberFormatException.class,
                () -> Integer.decode(invalidInput)
        );
    }

    @Test
    @DisplayName("null test")
    void shouldThrowNullPointerExceptionWhenInputIsNull() {
        // Act & Assert
        Assertions.assertThrows(
                NullPointerException.class,
                () -> Integer.decode(null)
        );
    }
}
