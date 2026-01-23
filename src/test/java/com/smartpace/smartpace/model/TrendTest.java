package com.smartpace.smartpace.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrendTest {

    @Test
    void shouldHaveThreeValuess() {
        Trend[] values = Trend.values();

        assertEquals(3, values.length);
        assertTrue(containsValue(values, Trend.IMPROVING));
        assertTrue(containsValue(values, Trend.DECLINING));
        assertTrue(containsValue(values, Trend.STABLE));
    }

    @Test
    void shouldParseFromString() {
        assertEquals(Trend.IMPROVING, Trend.valueOf("IMPROVING"));
        assertEquals(Trend.DECLINING, Trend.valueOf("DECLINING"));
        assertEquals(Trend.STABLE, Trend.valueOf("STABLE"));

    }

    @Test
    void shouldBeComparable() {
        Trend improving = Trend.IMPROVING;
        Trend declining = Trend.DECLINING;
        Trend stable = Trend.STABLE;
        
        assertTrue(improving.compareTo(declining) < 0);
        assertTrue(declining.compareTo(stable) < 0);
        assertTrue(improving.compareTo(stable) < 0);
    }

    private boolean containsValue(Trend[] values, Trend trend) {
        for (Trend t : values) {
            if (t == trend) {
                return true;
            }
        }
        return false;

    }

}
