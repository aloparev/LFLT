package home.lflt.utils;

import home.lflt.model.Quote;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

import static home.lflt.utils.Constants.*;
import static home.lflt.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UtilsTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final int one = 1;
    private final int two = 2;
    private final int three = 3;
    private final int five = 5;
    private final int seven = 7;
    private final int twelve = 12;

//    @Before()
//    public void setUp() {
//
//    }

    @Test
    void dayUpdateYesterday() {
        assertTrue(checkPortfolioUpdate(now.minusDays(one), 'd', one));
    }

    @Test
    void dayUpdateYesterdayLowerBound() {
        assertTrue(checkPortfolioUpdate(now.minusMinutes(DAY_IN_MINUTES - DAY_OFFSET_IN_MINUTES + one), 'd', one));
    }

    @Test
    void dayNoUpdateAlmostYesterday() {
        assertFalse(checkPortfolioUpdate(now.minusMinutes(DAY_IN_MINUTES - DAY_OFFSET_IN_MINUTES - one), 'd', one));
    }

    @Test
    void dayUpdateTwo() {
        assertTrue(checkPortfolioUpdate(now.minusDays(two), 'd', two));
    }

    @Test
    void dayUpdateFive() {
        assertTrue(checkPortfolioUpdate(now.minusDays(five), 'd', five));
    }

    @Test
    void weekUpdateLast() {
        assertTrue(checkPortfolioUpdate(now.minusDays(seven), 'w', one));
    }

    @Test
    void weekUpdateLastWeekLowerBound() {
        assertTrue(checkPortfolioUpdate(now.minusHours(WEEK_IN_HOURS - WEEK_OFFSET_IN_HOURS + one), 'w', one));
    }

    @Test
    void weekNoUpdateLessThanLast() {
        assertFalse(checkPortfolioUpdate(now.minusHours(WEEK_IN_HOURS - WEEK_OFFSET_IN_HOURS - one), 'w', one));
    }

    @Test
    void weekUpdateThree() {
        assertTrue(checkPortfolioUpdate(now.minusHours(WEEK_IN_HOURS * three), 'w', three));
    }

    @Test
    void weekNoUpdateThree() {
        assertFalse(checkPortfolioUpdate(now.minusHours(WEEK_IN_HOURS * three - WEEK_OFFSET_IN_HOURS - one), 'w', three));
    }

    @Test
    void monthUpdateLast() {
        assertTrue(checkPortfolioUpdate(now.minusMonths(one), 'M', one));
    }

    @Test
    void monthUpdateFive() {
        assertTrue(checkPortfolioUpdate(now.minusHours(MONTH_IN_HOURS * five), 'M', five));
    }

    @Test
    void monthUpdateTwelve() {
        assertTrue(checkPortfolioUpdate(now.minusHours(MONTH_IN_HOURS * twelve), 'M', twelve));
    }

    @Test
    void monthNoUpdateLessThanOne() {
        assertFalse(checkPortfolioUpdate(now.minusHours(MONTH_IN_HOURS - MONTH_OFFSET_IN_HOURS - one), 'M', one));
//        LocalDateTime then = now.minusHours(MONTH_IN_HOURS - one);
//        long duration = Duration.between(then, now).toHours();
//        long expected = MONTH_IN_HOURS - MONTH_OFFSET_IN_HOURS;
//        System.out.println(duration);
//        System.out.println(expected);
//        System.out.println(duration > expected);
    }

    @Test
    void updateNullDaily() {
        assertTrue(checkPortfolioUpdate(null, 'd', two));
    }

    @Test
    void updateNullWeekly() {
        assertTrue(checkPortfolioUpdate(null, 'w', three));
    }

    @Test
    void updateNullMonthly() {
        assertTrue(checkPortfolioUpdate(null, 'M', five));
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

    @Test
    void encryptor() {
        String text = "text";
        BasicTextEncryptor encryptor = Utils.encryptor();
        String code = encryptor.encrypt(text);
        assertFalse(Objects.equals(text, code));

        String decryption = encryptor.decrypt(code);
        assertTrue(Objects.equals(text, decryption));
    }
}