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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class UtilsTestWithContext {
    @Autowired
    private StockRepo stockRepo;

    @Test
    void miQuoteShallDeliverValues() {
        Quote quote = getQuoteMi("GOOG");
        System.out.println(quote);
        assertTrue(quote.getPrice() != 0);
        assertTrue(quote.getChange() != -111);
    }

    @Test
    void miQuoteTenRandomCalls() {
        Quote quote;
        int id;

        for (int i = 0; i < 10; i++) {
            id = getRandomIntBetween(0, stockRepo.getCount());
            quote = getQuoteMi(stockRepo.getByIndex(id).getSymbol());
            System.out.println("quote #" + i + ": " + quote);
            assertTrue(quote.getPrice() != 0);
            assertTrue(quote.getChange() != -111);
        }
    }
}