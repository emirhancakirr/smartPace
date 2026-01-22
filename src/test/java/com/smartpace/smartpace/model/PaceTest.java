package com.smartpace.smartpace.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import io.micrometer.core.annotation.TimedSet;

public class PaceTest {
    @Test
    void shouldCreatePaceFromDeciseconds() {
        // Given
        int deciseconds = 1052;

        // When
        Pace pace = Pace.ofDeciseconds(deciseconds);

        // Then
        assertNotNull(pace);
        assertEquals(1052, pace.getDeciseconds());
        assertEquals("1:45.2", pace.getFormattedString());
    }

    @Test
    void shouldcreatePaceFromFormattedString() {
        String formatedString = "1:45.2";

        Pace pace = pace.ofFormattedString(formatedString);

        assertNotNull(pace);
        assertEquals(1052, pace.getDeciseconds());
        assertEquals("1:45.2", pace.getFormattedString());
    }

    @Test
    void shouldHandleZeroDeciseconds() {
        int deciseconds = 0;

        Pace pace = pace.ofDeciseconds(deciseconds);

        assertNotNull(pace);
        assertEquals(0, pace.getDeciseconds());
        assertEquals("0:00.0", pace.getFormattedString());
    }

    @Test
    void shouldHandleSingleDigitSeconds() {
        int deciseconds = 120;

        Pace pace = Pace.ofDeciseconds(deciseconds);

        assertEquals(120, pace.getDeciseconds());
        assertEquals("0:12.0", pace.getFormattedString());
    }

    @Test
    void shouldHandleLargeValues() {
        int deciseconds = 3600;

        Pace pace = Pace.ofDeciseconds(deciseconds);

        assertEquals(3600, pace.getDeciseconds());
        assertEquals("6:00.0", pace.getFormattedString());
    }

    @Test
    void shouldThrowExceptionForNegativeValues(){
        int deciseconds = -100;

        Pace pace = Pace.ofDeciseconds(deciseconds);
        assertThrows(IllegalArgumentException.class, ()-> Pace.ofDeciseconds(deciseconds));

    }

    @Test
    void shouldThrowExceptionForInvalidFormattedString() {
        String invalid = "invalid";

        assertThrows(IllegalArgumentException.class,
                () -> Pace.ofFormattedString(invalid));
    }

    @Test
    void shouldHaveEqualsAndHashCode() {
        Pace pace1 = Pace.ofDeciseconds(1052);
        Pace pace2 = Pace.ofDeciseconds(1052);
        Pace pace3 = Pace.ofFormattedString("1:45.2");

        assertEquals(pace1, pace2);
        assertEquals(pace1, pace3);
        assertEquals(pace1.hashCode(), pace2.hashCode());
        assertEquals(pace1.hashCode(), pace3.hashCode());
    }

    @Test
    void shouldHandleDifferentFormats() {
        assertEquals("1:45.2", Pace.ofFormattedString("1:45.2").getFormattedString());
        assertEquals("0:12.0", Pace.ofFormattedString("0:12.0").getFormattedString());
        assertEquals("2:30.5", Pace.ofFormattedString("2:30.5").getFormattedString());
    }

}
