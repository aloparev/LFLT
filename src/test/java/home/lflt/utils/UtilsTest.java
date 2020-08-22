package home.lflt.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static home.lflt.utils.Constants.DAY_OFFSET_IN_MINUTES;
import static org.junit.jupiter.api.Assertions.*;
import static home.lflt.utils.Utils.checkPortfolio;

@Slf4j
class UtilsTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final int one = 1;
    private final int two = 2;
    private final int five = 5;
    private final int seven = 7;
    private int month = 28;

//    @Before()
//    public void setUp() {
//
//    }

    @Test
    void updateYesterday() {
        assertTrue(checkPortfolio(now.minusDays(one), 'd', one));
    }

    @Test
    void updateYesterdayLowerBound() {
        assertFalse(checkPortfolio(now.minusMinutes(DAY_OFFSET_IN_MINUTES + five), 'd', one));
    }

    @Test
    void noUpdateYesterday() {
        assertFalse(checkPortfolio(now.minusMinutes(DAY_OFFSET_IN_MINUTES + five), 'd', one));
    }

    @Test
    void updateTwoDaysAgo() {
        assertTrue(checkPortfolio(now.minusDays(two), 'd', two));
//        LocalDateTime then = now.minusDays(two);
//        Duration d = Duration.between(then, now);
//        long expected = DAY_IN_MINUTES * two - DAY_OFFSET_IN_MINUTES;
//        System.out.println(d.toMinutes());
//        System.out.println(expected);
//        System.out.println(d.toMinutes() > expected);
    }

    @Test
    void updateFiveDaysAgo() {
        assertTrue(checkPortfolio(now.minusDays(five), 'd', five));
    }

    @Test
    void updateWeekAgo() {
        assertTrue(checkPortfolio(now.minusDays(seven), 'w', seven));
    }

    @Test
    void noUpdateLessThatWeekAgo() {
        assertFalse(checkPortfolio(now.minusDays(one), 'w', seven));
    }

    @Test
    void lastMonthShouldBeUpdated() {
        assertTrue(checkPortfolio(now.minusMonths(one), 'M', one));
    }

    @Test
    void lastMonthShouldNotBeUpdated() {
        assertFalse(checkPortfolio(now.minusDays(seven), 'M', month));
    }

    @Test
    void nullShouldBeUpdated() {
        assertTrue(checkPortfolio(null, 'd', month));
    }

    @Test
    void givenTwoDatesInJava8_getDurationDifference() {
        //System.out.println(ChronoUnit.DAYS.between(Instant.ofEpochSecond(1490372528), Instant.now()));
        LocalDateTime then = LocalDateTime.parse("2020-12-26T08:38");
        LocalDateTime now = LocalDateTime.parse("2020-12-27T08:31");

        System.out.println(then);
        System.out.println(now);

        //     Period period = Period.between(now, then);
        // int diff = period.getDays();
        // System.out.println(diff);

        Duration duration = Duration.between(then, now);
        long diff = duration.toMinutes();
        System.out.println(diff);
//        assertEquals(diff, 7);
    }

    @Test
    void givenTwoDatesInJava8_whenDifferentiating_thenWeGetSix() {
        LocalDate now = LocalDate.parse("2020-08-27");
        LocalDate then = LocalDate.parse("2020-07-28");

        Period period = Period.between(then, now);
        int diff = period.getMonths();
        System.out.println(diff);
//        assertEquals(diff, 6);
    }
}