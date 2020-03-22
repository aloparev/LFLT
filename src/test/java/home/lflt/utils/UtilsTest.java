package home.lflt.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static home.lflt.utils.Utils.checkPortfolio;

@Slf4j
class UtilsTest {
    private LocalDateTime date = LocalDateTime.now();
    private int day = 1;
    private int week = 7;
    private int month = 28;

//    @Before()
//    public void setUp() {
//
//    }

    @Test
    void yesterdayOneEpochShouldBeUpdated() {
        assertTrue(checkPortfolio(date.minusDays(day), day));
    }

    @Test
    void nowOneEpochShouldNotBeUpdated() {
        assertFalse(checkPortfolio(date, day));
    }

    @Test
    void oneWeekShouldBeUpdated() {
        assertTrue(checkPortfolio(date.minusDays(week), week));
    }

    @Test
    void oneWeekShouldNotBeUpdated() {
        assertFalse(checkPortfolio(date.minusDays(day), week));
    }

    @Test
    void lastMonthShouldBeUpdated() {
        assertTrue(checkPortfolio(date.minusMonths(1), month));
    }

    @Test
    void lastMonthShouldNotBeUpdated() {
        assertFalse(checkPortfolio(date.minusDays(week), month));
    }

    @Test
    void nullShouldBeUpdated() {
        assertTrue(checkPortfolio(null, month));
    }
}