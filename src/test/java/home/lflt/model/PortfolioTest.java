package home.lflt.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PortfolioTest {

    @Test
    void ustamp() {
        Portfolio portfolio = new Portfolio();
        log.info("portfolio created=" + portfolio.toString());

        portfolio.setName("dummy portfilio");
        portfolio.setInfo("to test ustamp");
        portfolio.setType("RANDOM");
        portfolio.setFunds(1000);
        portfolio.setEpochs(1);
        portfolio.setUstamp(LocalDateTime.now());
        log.info("portfolio initialized=" + portfolio.toString());
        LocalDateTime stamp = portfolio.getUstamp();

        portfolio.setUstamp(LocalDateTime.now());
        log.info("ustamp updated=" + portfolio.getUstamp());
        LocalDateTime stampAfter = portfolio.getUstamp();

        log.info(stamp + "=" + stampAfter + "?");
        assertFalse(stamp.isEqual(stampAfter));
    }
}