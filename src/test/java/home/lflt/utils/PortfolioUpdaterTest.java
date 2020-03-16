package home.lflt.utils;

import home.lflt.model.Portfolio;
import home.lflt.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PortfolioUpdaterTest {

    @Test
    void update() {
        Portfolio portfolio = new Portfolio();
        portfolio.setName("dummy portfilio");
        portfolio.setInfo("to test ustamp");
        portfolio.setType("RANDOM");
        portfolio.setFunds(1000);
        portfolio.setEpochs(1);
        log.info("portfolio created=" + portfolio.toString());

//        portfolio.getLots().add(new BuyingAlgorithm(stockRepo, portfolio.getFunds()).buyStockRandomly());
//        log.info("portfolio updated=" + portfolio.toString());
    }
}