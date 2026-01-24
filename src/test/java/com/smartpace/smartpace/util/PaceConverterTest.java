package com.smartpace.smartpace.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for PaceConverter utility class.
 * Tests conversion between deciseconds and formatted pace strings.
 * Handles formats: "1:45.2", "1:45", and edge cases.
 */
public class PaceConverterTest {

    
    @Test
    void decisecondsToFormattedString_ZeroDeciseconds_ReturnsZeroFormatted() {
        
        int deciseconds = 0;

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("0:00.0", result);
    }

    @Test
    void decisecondsToFormattedString_SmallValues_FormatsCorrectly() {
        
        int deciseconds = 120; // 12.0 seconds

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("0:12.0", result);
    }

    @Test
    void decisecondsToFormattedString_TypicalRowingPace_FormatsCorrectly() {
        
        int deciseconds = 1052; // 1:45.2

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("1:45.2", result);
    }

    @Test
    void decisecondsToFormattedString_LargeValues_FormatsCorrectly() {
        
        int deciseconds = 3600; // 6:00.0

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("6:00.0", result);
    }

    @Test
    void decisecondsToFormattedString_VeryLargeValues_FormatsCorrectly() {
        
        int deciseconds = 12000; // 20:00.0

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("20:00.0", result);
    }

    @Test
    void decisecondsToFormattedString_SingleDigitSeconds_FormatsWithLeadingZero() {
        
        int deciseconds = 50; // 5.0 seconds

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("0:05.0", result);
    }

    @Test
    void decisecondsToFormattedString_ExactMinuteBoundary_FormatsCorrectly() {
        
        int deciseconds = 600; // Exactly 1:00.0

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("1:00.0", result);
    }

    @Test
    void decisecondsToFormattedString_NegativeValue_ThrowsException() {
        
        int deciseconds = -50;

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.decisecondsToFormattedString(deciseconds));
    }

    @Test
    void decisecondsToFormattedString_MaxValue_ThrowsException() {
        
        int deciseconds = Integer.MAX_VALUE;

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.decisecondsToFormattedString(deciseconds));
    }

    // ========== formattedStringToDeciseconds Tests ==========
    
    @Test
    void formattedStringToDeciseconds_FullFormat_ConvertsCorrectly() {
        
        String pace = "1:45.2";

        
        int result = PaceConverter.formattedStringToDeciseconds(pace);

        
        assertEquals(1052, result);
    }

    @Test
    void formattedStringToDeciseconds_WithoutTenths_ConvertsCorrectly() {
        
        String pace = "1:45";

        
        int result = PaceConverter.formattedStringToDeciseconds(pace);

        
        assertEquals(1050, result); // No tenths = .0
    }

    @Test
    void formattedStringToDeciseconds_ZeroFormat_ConvertsToZero() {
        
        String pace = "0:00.0";

        
        int result = PaceConverter.formattedStringToDeciseconds(pace);

        
        assertEquals(0, result);
    }

    @Test
    void formattedStringToDeciseconds_SingleDigitSeconds_ConvertsCorrectly() {
        
        String pace = "0:05.2";

        
        int result = PaceConverter.formattedStringToDeciseconds(pace);

        
        assertEquals(52, result);
    }

    @Test
    void formattedStringToDeciseconds_LargeMinutes_ConvertsCorrectly() {
        
        String pace = "15:30.5";

        
        int result = PaceConverter.formattedStringToDeciseconds(pace);

        
        assertEquals(9305, result); // (15*60 + 30)*10 + 5 = 9305
    }

    @Test
    void formattedStringToDeciseconds_NullInput_ThrowsException() {
        
        String pace = null;

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_EmptyString_ThrowsException() {
        
        String pace = "";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_InvalidFormat_ThrowsException() {
        
        String pace = "invalid";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_MissingColon_ThrowsException() {
        
        String pace = "145.2";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_NonNumericCharacters_ThrowsException() {
        
        String pace = "1:ab.2";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_InvalidSeconds_ThrowsException() {
        String pace = "1:75.2";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_InvalidTenths_ThrowsException() {
        String pace = "1:45.15";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    @Test
    void formattedStringToDeciseconds_WithWhitespace_ThrowsException() {
        
        String pace = " 1:45.2 ";

         
        assertThrows(IllegalArgumentException.class, 
            () -> PaceConverter.formattedStringToDeciseconds(pace));
    }

    
    @Test
    void roundTripConversion_TypicalValues_MaintainsPrecision() {
        
        int originalDeciseconds = 1052;

        
        String formatted = PaceConverter.decisecondsToFormattedString(originalDeciseconds);
        int convertedBack = PaceConverter.formattedStringToDeciseconds(formatted);

        
        assertEquals(originalDeciseconds, convertedBack);
        assertEquals("1:45.2", formatted);
    }

    @Test
    void roundTripConversion_EdgeValues_MaintainsPrecision() {
        
        int[] testValues = {0, 50, 120, 600, 1050, 3600};

        for (int original : testValues) {
            String formatted = PaceConverter.decisecondsToFormattedString(original);
            int convertedBack = PaceConverter.formattedStringToDeciseconds(formatted);
            assertEquals(original, convertedBack, 
                "Round-trip conversion failed for " + original + " deciseconds");
        }
    }

    // ========== Multiple Format Support Tests ==========
    
    @Test
    void formattedStringToDeciseconds_MultipleFormats_ConvertToSameValue() {
        String fullFormat = "1:45.0"; // 1050 deciseconds
        String shortFormat = "1:45";   // Should also be 1050 deciseconds

        
        int result1 = PaceConverter.formattedStringToDeciseconds(fullFormat);
        int result2 = PaceConverter.formattedStringToDeciseconds(shortFormat);

        
        assertEquals(1050, result1);
        assertEquals(1050, result2);
        assertEquals(result1, result2);
    }

    // ========== Boundary Value Tests ==========
    
    @Test
    void decisecondsToFormattedString_SingleTenth_FormatsCorrectly() {
        
        int deciseconds = 1; // 0.1 seconds

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("0:00.1", result);
    }

    @Test
    void decisecondsToFormattedString_ExactlyOneSecond_FormatsCorrectly() {
        
        int deciseconds = 10; // Exactly 1.0 seconds

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("0:01.0", result);
    }

    @Test
    void decisecondsToFormattedString_ExactlyOneMinute_FormatsCorrectly() {
        
        int deciseconds = 600; // Exactly 1:00.0

        
        String result = PaceConverter.decisecondsToFormattedString(deciseconds);

        
        assertNotNull(result);
        assertEquals("1:00.0", result);
    }

    @Test
    void formattedStringToDeciseconds_MinuteBoundary_ConvertsCorrectly() {
        
        String pace = "1:00.0";

        
        int result = PaceConverter.formattedStringToDeciseconds(pace);

        
        assertEquals(600, result);
    }

}
