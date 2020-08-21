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
    private final LocalDateTime date = LocalDateTime.now();
    private int day = 1;
    private int week = 7;
    private int month = 28;

//    @Before()
//    public void setUp() {
//
//    }

    @Test
    void yesterdayOneEpochShouldBeUpdated() {
        assertTrue(checkPortfolio(date.minusDays(day), 'd', day));
    }

    @Test
    void nowOneEpochShouldNotBeUpdated() {
        assertFalse(checkPortfolio(date, 'd', day));
    }

    @Test
    void weekAgoShouldBeUpdated() {
        assertTrue(checkPortfolio(date.minusDays(week), 'd', week));
    }

    @Test
    void weekAgoNowMinusDayShouldNotBeUpdated() {
        assertFalse(checkPortfolio(date.minusDays(day), 'w', week));
    }

    @Test
    void lastMonthShouldBeUpdated() {
        assertTrue(checkPortfolio(date.minusMonths(1), 'M', month));
    }

    @Test
    void lastMonthShouldNotBeUpdated() {
        assertFalse(checkPortfolio(date.minusDays(week), 'M', month));
    }

    @Test
    void nullShouldBeUpdated() {
        assertTrue(checkPortfolio(null, 'd', month));
    }
}